package com.faketube.api.dto.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.faketube.api.dto.VideoDto;
import com.faketube.store.entity.video.VideoEntity;

@Component
public class VideoDtoFactory {

	
	private final UserDtoFactory userDtoFactory;
	
	@Autowired
	public VideoDtoFactory(UserDtoFactory userDtoFactory) {
		super();
		this.userDtoFactory = userDtoFactory;
	}



	public VideoDto createVideoDto(VideoEntity entity) {
		
		return new VideoDto(
				entity.getVideoId(),
				entity.getContentType(),
				entity.getTitle(),
				entity.getDescription(),
				entity.getViews(),
				entity.getStatus(),
//				userDtoFactory
//					.createUserDto(entity.getUser()),
				"http://localhost:8080/api/video/player/"+entity.getVideoId());
	}
}
