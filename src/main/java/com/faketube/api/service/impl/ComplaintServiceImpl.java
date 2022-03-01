package com.faketube.api.service.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faketube.api.dto.VideoComplaintDto;
import com.faketube.api.dto.factory.VideoComplaintDtoFactory;
import com.faketube.api.exception.BadRequestException;
import com.faketube.api.exception.NotFoundException;
import com.faketube.api.service.ComplaintService;
import com.faketube.api.service.UserService;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.entity.video.AcceptedComplaint;
import com.faketube.store.entity.video.ReasonVideo;
import com.faketube.store.entity.video.VideoComplaintEntity;
import com.faketube.store.entity.video.VideoComplaintStatus;
import com.faketube.store.entity.video.VideoEntity;
import com.faketube.store.entity.video.VideoStatus;
import com.faketube.store.repository.AcceptedComplaintRepository;
import com.faketube.store.repository.ComplaintVideoRepository;
import com.faketube.store.repository.UserRepository;
import com.faketube.store.repository.VideoRepository;

@Service
public class ComplaintServiceImpl implements ComplaintService{

	
	private final ComplaintVideoRepository complaintVideoDao;
	private final VideoComplaintDtoFactory videoComplaintDtoFactory;
	private final UserRepository userDao;
	private final VideoRepository videoDao;
	private final AcceptedComplaintRepository acceptedComplaintDao;
	private final UserService userService;
	


	@Autowired
	public ComplaintServiceImpl(ComplaintVideoRepository complaintVideoDao,
			VideoComplaintDtoFactory videoComplaintDtoFactory, UserRepository userDao, VideoRepository videoDao,
			AcceptedComplaintRepository acceptedComplaintDao, UserService userService) {
		super();
		this.complaintVideoDao = complaintVideoDao;
		this.videoComplaintDtoFactory = videoComplaintDtoFactory;
		this.userDao = userDao;
		this.videoDao = videoDao;
		this.acceptedComplaintDao = acceptedComplaintDao;
		this.userService = userService;
	}


	@Override
	public void addComplaintVideo(String videoId, String description, ReasonVideo reason,
			Principal principal) {
		UserEntity user = findUserByEmailAndActiveTrue(principal.getName());
		VideoEntity video = findVideoByIdAndStatusLinkAndPublic(videoId);
		complaintVideoDao.findByUserAndVideo(user, video).ifPresentOrElse((c)->{
			throw new BadRequestException(
					String.format(
						"На видео с идентификатором \"%s\" пользователь %s уже подавал жалобу!",
						videoId, principal.getName()));
		}, ()->{
			complaintVideoDao.save(
					new VideoComplaintEntity(
							user,
							description,
							reason,
							video
				));
		});;
	}
	
	
	@Override
	public List<VideoComplaintDto> getComplaintsVideo(String videoId) {
		List<VideoComplaintDto> listComplaintDto = new LinkedList<>();
		videoDao.findById(videoId).ifPresentOrElse((v)->{listComplaintDto.addAll(
			videoComplaintDtoFactory.createListVideoComplaintDto(
					complaintVideoDao.findAllByVideo(v)));}, ()->{
						throw new NotFoundException(
								String.format("Видео с идентификаторм \"%s\" не найдено", 
										videoId));
					});
		return listComplaintDto;
	}
	
	@Override
	public List<VideoComplaintDto> getComplaintsByUser(String userName) {
		return videoComplaintDtoFactory
				.createListVideoComplaintDto(
						findUserByEmailAndActiveTrue(userName)
						.getComplaints());
	}
	
	@Override
	@Transactional
	public void updateStatusComplaint(Long complaintId, VideoComplaintStatus status) {
		complaintVideoDao.findById(complaintId).ifPresentOrElse((c)->{
			c.setStatus(status);
			if(status.equals(VideoComplaintStatus.RECEIVED)) {
				videoDao.findById(c.getVideo().getVideoId()).ifPresent((v)->{
					v.setBlockedAt(LocalDateTime.now());
					v.setOldStatusVideo(v.getStatus());
					v.setStatus(VideoStatus.BLOCK);
					acceptedComplaintDao.save(
						new AcceptedComplaint(
								c.getReason(),
								v,
								v.getUser(),
								c.getUser())
					);
					if(acceptedComplaintDao.countByAuthorVideo(v.getUser())>2L) {
						userService.blockUserByUserId(v.getUser().getUserId());
					}
				});
			}
		}, ()->{
			throw new NotFoundException(
					String.format(
							"Заявка с идентификатором \"%s\" не найдена", 
							complaintId));}
		);
		
		
		
	}

	
	
	
	private UserEntity findUserByEmailAndActiveTrue(String email) {
		return userDao.findByEmailAndActive(email, true).orElseThrow(()->
				new NotFoundException(String.format("Пользователь с email %s не найден", email)));
	}
	
	private VideoEntity findVideoByIdAndStatusLinkAndPublic(String videoId) {
		return videoDao.findVideoByIdAndStatus(
							videoId, 
							VideoStatus.PUBLIC.name(), 
							VideoStatus.LINK.name()).orElseThrow(()->
									new NotFoundException(
											String.format(
													"Видео с идентификатором %s не найдено",
													videoId)));
	}



	

}
