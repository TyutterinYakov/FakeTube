package com.faketube.api.dto.factory;

import java.util.List;
import java.util.stream.Collectors;

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
	
	
	public List<UserDto> createListUserDto(List<UserEntity> entities){
		return entities
				.stream()
				.map(this::createUserDto)
				.collect(Collectors.toList());
	}
}
