package com.faketube.api.dto.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.faketube.api.dto.VideoComplaintDto;
import com.faketube.store.entity.video.VideoComplaintEntity;

@Component
public class VideoComplaintDtoFactory {

	private final UserDtoFactory userDtoFactory;
	private final VideoDtoFactory videoDtoFactory;
	
	@Autowired
	public VideoComplaintDtoFactory(UserDtoFactory userDtoFactory, VideoDtoFactory videoDtoFactory) {
		super();
		this.userDtoFactory = userDtoFactory;
		this.videoDtoFactory = videoDtoFactory;
	}
	
	
	public VideoComplaintDto createVideoComplaintDto(VideoComplaintEntity entity) {
		return new VideoComplaintDto(
				entity.getComplaintId(),
				userDtoFactory.createUserDto(
						entity.getUser()),
				entity.getDescription(),
				entity.getReason(),
				videoDtoFactory.createVideoDto(
						entity.getVideo()),
				entity.getCreatedAt(),
				entity.getStatus()
				);
	}
	
	public List<VideoComplaintDto> createListVideoComplaintDto(List<VideoComplaintEntity> entities){
		return entities.stream().map(
				this::createVideoComplaintDto).collect(Collectors.toList());
	}
	
	
}
