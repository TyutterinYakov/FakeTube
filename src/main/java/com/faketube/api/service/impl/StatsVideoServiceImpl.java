package com.faketube.api.service.impl;

import java.security.Principal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faketube.api.dto.GradeVideoDto;
import com.faketube.api.exception.NotFoundException;
import com.faketube.api.service.StatsVideoService;
import com.faketube.store.entity.stats.GradeVideo;
import com.faketube.store.entity.stats.GradeVideoStatus;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.entity.video.VideoEntity;
import com.faketube.store.entity.video.VideoStatus;
import com.faketube.store.repository.GradeVideoRepository;
import com.faketube.store.repository.UserRepository;
import com.faketube.store.repository.VideoRepository;

@Service
public class StatsVideoServiceImpl implements StatsVideoService{

	private final UserRepository userDao;
	private final VideoRepository videoDao;
	private final GradeVideoRepository gradeVideoDao;
	
	@Autowired
	public StatsVideoServiceImpl(UserRepository userDao, VideoRepository videoDao,
			GradeVideoRepository gradeVideoDao) {
		super();
		this.userDao = userDao;
		this.videoDao = videoDao;
		this.gradeVideoDao = gradeVideoDao;
	}
	
	
	@Transactional
	@Override
	public void addVideoGrade(Principal principal, String videoId,
			Optional<GradeVideoStatus> grade) {
		if(principal!=null) {
			String userName=principal.getName();
			UserEntity user = findUserByUsernameAndActive(userName);
			findVideoByIdAndStatus(videoId, VideoStatus.PUBLIC, 
					VideoStatus.LINK).ifPresentOrElse((vid)->{
				gradeVideoDao.findByUserAndVideo(user, vid).ifPresentOrElse((gr)->{
					grade.ifPresentOrElse((grReq)->{
						gr.setStatus(grReq);
					}, ()->{
						gradeVideoDao.deleteById(gr.getGradeId());
					});
				}, ()->{
					grade.ifPresent((gr)->{
						gradeVideoDao.save(new GradeVideo(user, vid, gr));
					});
				}
				);
			}, ()->{
				VideoEntity videoUser = findVideoByUserAndId(user, videoId);
				gradeVideoDao.findByUserAndVideo(user, videoUser).ifPresentOrElse((gradeVid)->{
					grade.ifPresentOrElse((gr)->{
						gradeVid.setStatus(gr);
					}, ()->{
						gradeVideoDao.deleteById(gradeVid.getGradeId());});
				}, ()->{
					grade.ifPresent((gr)->{
						gradeVideoDao.save(new GradeVideo(user, videoUser, gr));
					});
				});
			});;
		}
	}
	
	
	@Override
	public GradeVideoDto getVideoDtoByVideoId(String videoId, Principal principal) {
		GradeVideoDto gradeVideoDto = new GradeVideoDto();
			Optional<VideoEntity> videoOptional = findVideoByIdAndStatus(videoId,
					VideoStatus.PUBLIC, VideoStatus.LINK);
			if(videoOptional.isPresent()) {
				if(principal!=null) {
					String userName=principal.getName();
					UserEntity user = findUserByUsernameAndActive(userName);
					gradeVideoDao.findByUserAndVideo(user, videoOptional.get()).ifPresent((gr)->{
					gradeVideoDto.setGivenStatus(gr.getStatus());
				});
				}
				gradeVideoDto.setCountLikes(
						gradeVideoDao.countByVideoAndStatus(videoOptional.get(),
								GradeVideoStatus.LIKE));
				gradeVideoDto.setCountDislikes(
						gradeVideoDao.countByVideoAndStatus(videoOptional.get(),
								GradeVideoStatus.DISLIKE));
			} else {
				if(principal!=null) {
					String userName=principal.getName();
					UserEntity user = findUserByUsernameAndActive(userName);
					VideoEntity video = findVideoByUserAndId(user, videoId);
					gradeVideoDao.findByUserAndVideo(user, findVideoByUserAndId(user, 
							video.getVideoId())).ifPresentOrElse((gr)->{
						gradeVideoDto.setGivenStatus(gr.getStatus());
					}, ()->{
						throw new NotFoundException(
								String.format(
										"Видео с идентификатором \"%s\" не найдено", 
										videoId));});;
					gradeVideoDto.setCountLikes(
							gradeVideoDao.countByVideoAndStatus(video,
									GradeVideoStatus.LIKE));
					gradeVideoDto.setCountDislikes(
							gradeVideoDao.countByVideoAndStatus(video,
									GradeVideoStatus.DISLIKE));
				} else {
					throw new NotFoundException(
							String.format(
									"Видео с идентификатором \"%s\" не найдено", 
									videoId));
				}
			}
		return gradeVideoDto;
	}
	
	
	
	
	


	private Optional<VideoEntity> findVideoByIdAndStatus(String videoId, VideoStatus public1, VideoStatus link) {
		return videoDao.findVideoByIdAndStatus(videoId, public1.name(), link.name());
	}


	private UserEntity findUserByUsernameAndActive(String userName) {
		return userDao.findByEmailAndActive(userName, true).orElseThrow(()->
				new NotFoundException(
						String.format(
								"Пользователь с ником \"%s\" не найден", 
								userName))
				);
	}
	
	private VideoEntity findVideoByUserAndId(UserEntity user, String videoId) {
		
		return videoDao.findVideoByIdAndIsNotStatusAndUserId(videoId, VideoStatus.DELETE.name(), VideoStatus.BLOCK.name(), user.getUserId()).orElseThrow(()->
				new NotFoundException(
						String.format(
								"Видео с идентификатором \"%s\" удалено или не найдено", 
								videoId))
			);
		
	}
	
	
	
	
	
}
