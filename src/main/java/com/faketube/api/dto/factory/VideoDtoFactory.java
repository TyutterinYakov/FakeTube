package com.faketube.api.dto.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.faketube.api.dto.VideoDto;
import com.faketube.store.entity.video.VideoEntity;

@Component
public class VideoDtoFactory {

	
	public VideoDto createVideoDto(VideoEntity entity) {
		
		return new VideoDto(
				entity.getVideoId(),
				entity.getContentType(),
				entity.getTitle(),
				entity.getDescription(),
				entity.getViews(),
				entity.getStatus(),
				"http://localhost:8080/api/video/player/"+entity.getVideoId());
	}
	
	
	public List<VideoDto> createListDto(List<VideoEntity> entities){
	
		return entities
				.stream()
				.map(this::createVideoDto)
				.collect(Collectors.toList());
	}
}
