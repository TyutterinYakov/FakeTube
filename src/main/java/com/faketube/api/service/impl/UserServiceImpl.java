package com.faketube.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faketube.api.dto.UserDto;
import com.faketube.api.dto.VideoDto;
import com.faketube.api.dto.factory.UserDtoFactory;
import com.faketube.api.dto.factory.VideoDtoFactory;
import com.faketube.api.exception.NotFoundException;
import com.faketube.api.exception.UserFoundException;
import com.faketube.api.model.UserModel;
import com.faketube.api.service.UserService;
import com.faketube.store.entity.stats.CommentStatus;
import com.faketube.store.entity.stats.GradeVideoStatus;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.entity.video.VideoStatus;
import com.faketube.store.repository.AcceptedComplaintRepository;
import com.faketube.store.repository.UserRepository;
import com.faketube.store.repository.VideoRepository;

@Service
public class UserServiceImpl implements UserService {

	
	private final UserRepository userDao;
	private final VideoDtoFactory videoDtoFactory;
	private final UserDtoFactory userDtoFactory;
	private final VideoRepository videoDao;
	private final AcceptedComplaintRepository acceptedComplaintDao;
	
	@Autowired
	public UserServiceImpl(UserRepository userDao, VideoDtoFactory videoDtoFactory, UserDtoFactory userDtoFactory,
			VideoRepository videoDao, AcceptedComplaintRepository acceptedComplaintDao) {
		super();
		this.userDao = userDao;
		this.videoDtoFactory = videoDtoFactory;
		this.userDtoFactory = userDtoFactory;
		this.videoDao = videoDao;
		this.acceptedComplaintDao = acceptedComplaintDao;
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
	
	@Override
	@Transactional
	public void updateUserProfile(String principal, UserModel userModel) {
		UserEntity user = findUserByEmail(principal);
		if(!user.getEmail().equals(userModel.getEmail())) {
			findUserByEmailAndThrowExceptionFound(userModel.getEmail());
			user.setEmail(userModel.getEmail());
		}
		user.setUsername(userModel.getUserName());
		user.setAccessToGradeVideo(userModel.isAccessToGradeVideo());
		if(!userModel.getPassword().isBlank()) {
			user.setPassword(userModel.getPassword()); //TODO code password
		}
	}
	
//	@Override
//	public void createUserProfile(UserModelRegister userModel) {
//		findUserByEmailAndThrowExceptionFound(userModel.getEmail());
//		userDao.save(new UserEntity(
//				userModel.getUserName(), 
//				userModel.getEmail(), 
//				userModel.getPassword())  //TODO code password
//		);
//	}
	
	
	@Override
	@Transactional
	public void deleteUserProfile(String principal) {
		UserEntity user = findUserByEmail(principal);
		user.getVideos().stream().filter(
				(v)->v.getStatus()!=VideoStatus.DELETE&&v.getStatus()!=VideoStatus.BLOCK).forEach((vid)->{
			vid.setDeletedAt(LocalDateTime.now());
			vid.setOldStatusVideo(vid.getStatus());
			vid.setStatus(VideoStatus.DELETE);
		});
		user.setDeletedAt(LocalDateTime.now());
		user.setActive(false);
		user.getComments().stream().filter((c)->c.getStatus()==CommentStatus.ACTIVE).forEach((com)->{
			com.setOldStatus(com.getStatus());
			com.setStatus(CommentStatus.DELETE);
			com.setDeletedAt(LocalDateTime.now());
		});;
	}
	
	@Override
	@Transactional
	public void blockUserByUserId(Long userId) {
		userDao.findById(userId).ifPresentOrElse((u)->{
			u.getComments().stream().filter((c)->c.getStatus()==CommentStatus.ACTIVE).forEach((com)->{
				com.setOldStatus(com.getStatus());
				com.setStatus(CommentStatus.BLOCK);
			});
			u.setActive(false);
			u.setBlockedAt(LocalDateTime.now());
			u.getVideos().stream().filter((vid)->vid.getBlockedAt()==null).forEach((v)->{
				v.setOldStatusVideo(v.getStatus());
				v.setStatus(VideoStatus.BLOCK);
				v.setBlockedAt(LocalDateTime.now());
			});
		}, ()->{
			throw new NotFoundException(
					String.format(
							"Пользователь с идентификатором \"%s\" не найден",
							userId));
		});;
	}
	
	@Override
	@Transactional
	public void unblockUserByUserId(Long userId) {
		userDao.findById(userId).ifPresentOrElse((u)->{
			u.setActive(true);
			u.setBlockedAt(null);
			u.getVideos().stream().filter((v)->
				v.getOldStatusVideo()!=null&&v.getOldStatusVideo()!=VideoStatus.BLOCK).forEach((v)->{
					if(!(acceptedComplaintDao.findAllByVideo(v).size()>0)) {
						v.setStatus(v.getOldStatusVideo());
						v.setOldStatusVideo(null);
						v.setBlockedAt(null);
					}
			});
			u.getComments().stream().filter((c)->c.getOldStatus()==CommentStatus.ACTIVE).forEach((com)->{
				com.setStatus(com.getOldStatus());
				com.setOldStatus(null);
			});
		}, ()->{
			throw new NotFoundException(
					String.format(
							"Пользователь с идентификатором \"%s\" не найден",
							userId));
		});;
	}
	
	@Override
	public List<UserDto> getBlockAndDeleteUsers() {
		return userDtoFactory
				.createListUserDto(
						userDao
						.findAllByActive(false));
	}
	
	
	
	
	

	private UserEntity findUserByEmail(String email) {
		return userDao.findByEmailAndDeletedAt(email, null).orElseThrow(()->
				new NotFoundException(String.format(
						"Ошибка! Пользователь с почтой \"%s\" не найден", email)));
	}
	
	
	private UserEntity findUserById(Long userId) {
		return userDao.findByUserIdAndActive(userId, true).orElseThrow(()->
				new NotFoundException(String.format(
						"Пользователь с идентификатором \"%s\" не найден", userId)));
	}
	
	private void findUserByEmailAndThrowExceptionFound(String email) {
		userDao.findByEmail(email).ifPresent((u)->{
			throw new UserFoundException(String.format(
					"Пользователь с email \"%s\" уже есть в системе",
							email));
			});
	}





	
	
}
