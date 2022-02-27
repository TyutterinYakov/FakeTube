package com.faketube.api.service.impl;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;

import com.faketube.api.dto.VideoDto;
import com.faketube.api.dto.factory.VideoDtoFactory;
import com.faketube.api.exception.NotFoundException;
import com.faketube.api.model.VideoModel;
import com.faketube.api.service.VideoService;
import com.faketube.store.entity.stats.VideoUniqueViews;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.entity.video.StreamBytesInfo;
import com.faketube.store.entity.video.VideoEntity;
import com.faketube.store.entity.video.VideoStatus;
import com.faketube.store.repository.UserRepository;
import com.faketube.store.repository.VideoRepository;
import com.faketube.store.repository.VideoUniqueViewsRepository;



@Service
public class VideoServiceImpl implements VideoService{

	private static final Logger logger =LoggerFactory.getLogger(VideoServiceImpl.class);
	
	@Value("${data.folder}")
	private String dataFolder;
	
	private final VideoRepository videoDao;
	private final VideoDtoFactory videoDtoFactory;
	private final VideoUniqueViewsRepository uniqueViewsDao;
	private final UserRepository userDao;
	
	@Autowired
	public VideoServiceImpl(VideoRepository videoDao, 
			VideoDtoFactory videoDtoFactory, VideoUniqueViewsRepository uniqueViewsDao,
			UserRepository userDao) {
		this.videoDao = videoDao;
		this.videoDtoFactory = videoDtoFactory;
		this.uniqueViewsDao = uniqueViewsDao;
		this.userDao = userDao;
	}

	private static final VideoStatus DELETE = VideoStatus.DELETE;
	private static final VideoStatus PRIVATE = VideoStatus.PRIVATE;
	private static final VideoStatus PUBLIC = VideoStatus.PUBLIC;
	private static final VideoStatus LINK = VideoStatus.LINK;
	
	@Override
	public VideoDto getVideoById(String videoId, HttpServletRequest request) {
		VideoEntity video = videoDao
				.findVideoByIdAndStatus(videoId, LINK.name(), PUBLIC.name())
					.orElseThrow(()->
						new NotFoundException(String.format(
								"Видео с идентификатором \"%s\" не найдено", 
								videoId))
			);
		String viewersData=sha1(request);
		if(!uniqueViewsDao.findByDataViewersAndVideo(viewersData, video).isPresent()) {
			uniqueViewsDao.save(new VideoUniqueViews(viewersData, video));
			video.setViews(video.getViews()+1);
		}
		return videoDtoFactory.createVideoDto(videoDao.saveAndFlush(video));
		
		
		
	}

	@Override
	public Optional<StreamBytesInfo> getStreamBytes(String videoId, HttpRange range) {
		VideoEntity video = videoDao.findVideoByIdAndStatus(videoId, PUBLIC.name(), LINK.name()).orElseThrow(()->
		new NotFoundException(
				String.format(
						"Видео с идентификатором \"%s\" не найдено", 
						videoId)));
		Path path = Path.of(dataFolder, video.getVideoId(), video.getFileName());
		if(!Files.exists(path)) {
			throw new NotFoundException();
		}
		try {
			Long fileSize =Files.size(path); 
			Long chunkSize = fileSize/20; 
			if(range==null) {
				return Optional.of(new StreamBytesInfo(
						out->Files.newInputStream(path).transferTo(out),
						fileSize, 0L, fileSize, video.getContentType()
						));
			}
			Long rangeStart = range.getRangeStart(0);
			Long rangeEnd = rangeStart+chunkSize;
			if(rangeEnd>=fileSize) {
				rangeEnd=fileSize-1;
			}
			Long finalRangeEnd = rangeEnd;
			return Optional.of(new StreamBytesInfo(
					out->{
						try(InputStream is = Files.newInputStream(path)) {
							is.skip(rangeStart);
							byte[] bytes = is.readNBytes((int)(finalRangeEnd-rangeStart+1));
							out.write(bytes);
						}
					}, fileSize, rangeStart, finalRangeEnd, video.getContentType()));
		} catch(IOException ex) {
			logger.error("",ex);
			throw new NotFoundException();
			
		}
	}
	
	@Override
	public void saveNewVideo(VideoModel request) {
		if(request.getVideo()==null) {
			throw new NotFoundException();
		}
		saveNewVideoFromDirectory(request, videoDao.saveAndFlush(
				new VideoEntity(
					request.getTitle(), 
					request.getDescription(),
					request.getVideo().getOriginalFilename(),
					request.getVideo().getSize(),
					request.getVideo().getContentType())
		));
		
	}
	
	
	@Override
	public List<VideoDto> getAllGradeVideoFromUser(String principal) {
		UserEntity user = userDao.findByEmail(principal).orElseThrow(()->
			new NotFoundException(
					String.format(
							"Ошибка! Пользователь с email \"%s\" не найден", 
							principal)));
		return videoDtoFactory.createListDto(
				user.getGradeVideo()
				.stream()
				.map((gr)->gr.getVideo())
				.filter((v)->!v.getStatus().equals(DELETE))
				.collect(Collectors.toList()));
	}
	
	
	
	
	
	private static String sha1(HttpServletRequest request) {
		Map<String, Object> headers = new TreeMap<>();
		request.getHeaderNames().asIterator().forEachRemaining((s)->{
			if(s.equals("user-agent")||s.equals("sec-ch-ua-platform")||s.equals("cookie")) {
				headers.put(s, request.getHeader(s));
			}
		});
		logger.info(headers.toString());
		StringBuilder strb = new StringBuilder(headers.toString().replaceAll(", ", "&"));
		strb.append(strb.substring(1, strb.length()-1));
		String input = strb.toString();
	    String sha1 = null;
	    
	        MessageDigest sh1Digest;
			try {
				sh1Digest = MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e) {
				logger.error("",e);
				throw new NotFoundException("Ошибка генерации информации о пользователе");
			}
	        byte[] bytes = sh1Digest.digest(input.getBytes());
	        StringBuilder sb = new StringBuilder();
	        for(byte b: bytes) {
	        	sb.append(String.format("%02X", b));
	        }
	        sha1=sb.toString();
	        logger.info(sha1);
	    return sha1;
	}


	private VideoEntity saveNewVideoFromDirectory(VideoModel request, VideoEntity video) {
		Path directory = Path.of(dataFolder, video.getVideoId());
		try {
			Files.createDirectories(directory);
			Path file = Path.of(directory.toString(), request.getVideo().getOriginalFilename());
			try(OutputStream os = Files.newOutputStream(file, CREATE, WRITE)){
				request.getVideo().getInputStream().transferTo(os);
			}
			return videoDao.saveAndFlush(video);
		} catch(IOException ex) {
			logger.error("", ex);
			throw new IllegalStateException();
		}
	}




}
