package com.faketube.api.model;

import com.faketube.store.entity.video.VideoStatus;

public class VideoModelUpdate {

	private String videoId;
	private String title;
	private String description;
	private VideoStatus status;
	
	
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public VideoStatus getStatus() {
		return status;
	}
	public void setStatus(VideoStatus status) {
		this.status = status;
	}
	
	
	
}
