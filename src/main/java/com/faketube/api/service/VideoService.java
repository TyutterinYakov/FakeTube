package com.faketube.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRange;

import com.faketube.api.dto.VideoDto;
import com.faketube.api.model.VideoModelAdd;
import com.faketube.api.model.VideoModelUpdate;
import com.faketube.store.entity.video.StreamBytesInfo;

public interface VideoService {

	VideoDto getVideoById(String videoId, HttpServletRequest request);

	Optional<StreamBytesInfo> getStreamBytes(String videoId, HttpRange object);
	
	void saveNewVideo(VideoModelAdd request);

	List<VideoDto> getAllGradeVideoFromUser(String principal);

	void deleteVideoById(String videoId, String principal);

	void updateVideo(VideoModelUpdate videoModel, String principal);

}
