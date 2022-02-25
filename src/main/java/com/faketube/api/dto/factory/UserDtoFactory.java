package com.faketube.api.dto.factory;

import org.springframework.stereotype.Component;

import com.faketube.api.dto.UserDto;
import com.faketube.store.entity.user.UserEntity;

@Component
public class UserDtoFactory {

	
	public UserDto createUserDto(UserEntity entity) {
		
		return new UserDto(
				entity.getUserId(),
				entity.getUsername(),
				entity.getEmail(),
				entity.getRole());
	}
}
