package com.faketube.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faketube.api.dto.UserDto;
import com.faketube.api.dto.VideoDto;
import com.faketube.api.dto.factory.UserDtoFactory;
import com.faketube.api.dto.factory.VideoDtoFactory;
import com.faketube.api.exception.NotFoundException;
import com.faketube.api.service.UserService;
import com.faketube.store.entity.stats.GradeVideoStatus;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.entity.video.VideoStatus;
import com.faketube.store.repository.GradeVideoRepository;
import com.faketube.store.repository.UserRepository;
import com.faketube.store.repository.VideoRepository;

@Service
public class UserServiceImpl implements UserService {

	
	private final UserRepository userDao;
	private final VideoDtoFactory videoDtoFactory;
	private final UserDtoFactory userDtoFactory;
	private final VideoRepository videoDao;
	
	@Autowired
	public UserServiceImpl(UserRepository userDao, VideoDtoFactory videoDtoFactory, UserDtoFactory userDtoFactory,
			VideoRepository videoDao) {
		super();
		this.userDao = userDao;
		this.videoDtoFactory = videoDtoFactory;
		this.userDtoFactory = userDtoFactory;
		this.videoDao = videoDao;
	}

	@Override
	public List<VideoDto> getAccessVideoFromUser(Long userId) {
		UserEntity user = findUserById(userId);
		return videoDtoFactory
				.createListDto(
						user.getVideos()
						.stream()
						.filter((v)->
						v.getStatus()
						.equals(VideoStatus.PUBLIC))
						.collect(Collectors.toList()));
	}

	@Override
	public List<VideoDto> getAllGradeVideoUser(Long userId) {
		return videoDtoFactory.createListDto(
				videoDao.findAllGradeVideoUserAndStatus(
						true,
						GradeVideoStatus.LIKE.name(), 
						VideoStatus.PUBLIC.name(), 
						VideoStatus.LINK.name(), 
						userId));
	}

	@Override
	public UserDto getInfoFromUser(Long userId) {
		return userDtoFactory
				.createUserDto(
						findUserById(userId));
	}
	
	

	
	private UserEntity findUserById(Long userId) {
		return userDao.findById(userId).orElseThrow(()->
				new NotFoundException("Пользователь с идентификатором \"%s\" не найден"));
	}
	
	
}
