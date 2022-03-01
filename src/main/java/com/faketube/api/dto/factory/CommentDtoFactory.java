package com.faketube.api.dto.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.faketube.api.dto.CommentDto;
import com.faketube.store.entity.stats.CommentEntity;

@Component
public class CommentDtoFactory {

	private final UserDtoFactory userDtoFactory;

	@Autowired
	public CommentDtoFactory(UserDtoFactory userDtoFactory) {
		super();
		this.userDtoFactory = userDtoFactory;
	}
	
	public CommentDto createCommentDto(CommentEntity entity) {
		return new CommentDto(
				entity.getCommentId(),
				entity.getMessage(),
				entity.getVideo().getVideoId(),
				entity.getCreatedAt(),
				userDtoFactory.createUserDto(
						entity.getUser()),
				entity.getChange());
	}
	
	public List<CommentDto> createListCommentDto(List<CommentEntity> entities){
		return entities
				.stream()
				.map(this::createCommentDto)
				.collect(Collectors.toList());
	}
	
	
	
	
	
}
