package com.faketube.api.dto;

import com.faketube.store.entity.video.VideoStatus;

public class VideoDtoInfo {

	private String videoId;
	private String title;
	private String description;
	private Integer views;
	private VideoStatus status;
	
	
	
	public VideoDtoInfo(String videoId, String title, String description, Integer views, VideoStatus status) {
		super();
		this.videoId = videoId;
		this.title = title;
		this.description = description;
		this.views = views;
		this.status = status;
	}
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
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public VideoStatus getStatus() {
		return status;
	}
	public void setStatus(VideoStatus status) {
		this.status = status;
	}
	
	
}
