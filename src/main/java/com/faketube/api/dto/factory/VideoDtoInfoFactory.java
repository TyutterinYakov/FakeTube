package com.faketube.api.dto.factory;

import org.springframework.stereotype.Component;

import com.faketube.api.dto.VideoDtoInfo;
import com.faketube.store.entity.video.VideoEntity;

@Component
public class VideoDtoInfoFactory {

	
	public VideoDtoInfo createVideoDtoInfo(VideoEntity entity) {
		return new VideoDtoInfo(
				entity.getVideoId(), 
				entity.getTitle(), 
				entity.getDescription(),
				entity.getViews(), 
				entity.getStatus());
	}
	
}
