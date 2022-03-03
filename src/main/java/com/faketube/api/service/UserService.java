package com.faketube.api.service;

import java.util.List;

import com.faketube.api.dto.UserDto;
import com.faketube.api.dto.VideoDto;
import com.faketube.api.model.UserModel;
import com.faketube.api.model.UserModelRegister;
import com.faketube.store.entity.user.UserEntity;

public interface UserService {

	List<VideoDto> getAccessVideoFromUser(Long userId);

	List<VideoDto> getAllGradeVideoUser(Long userId);

	UserDto getInfoFromUser(Long userId);

	void updateUserProfile(String principal, UserModel userModel);

//	void createUserProfile(UserModelRegister userModel);

	void deleteUserProfile(String principal);

	void blockUserByUserId(Long userId);

	void unblockUserByUserId(Long userId);

	List<UserDto> getBlockAndDeleteUsers();

	void blockUserByAuthorChannel(String name, Long userId, Long timeSec);
	
	void unblockUserByAuthorChannel(UserEntity blockUser, UserEntity authorChannel);

	void unblockUserByAuthorChannel(String authorChannel, Long blockUserId);

}
