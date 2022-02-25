package com.faketube.api.dto;

import com.faketube.store.entity.video.VideoStatus;


public class VideoDto {
	private String videoId;
	private String contentType;
	private String title;
	private String description;
	private Integer views;
	private Integer likes;
	private Integer dislikes;
	private VideoStatus status;
//	private UserDto user;
	private String streamUrl;
	
	
	public VideoDto() {
		super();
	}

	public VideoDto(String videoId, String contentType, String title, String description,
			Integer views, Integer likes, Integer dislikes, VideoStatus status, 
//			UserDto user,
			String streamUrl) {
		super();
		this.videoId = videoId;
		this.contentType = contentType;
		this.title = title;
		this.description = description;
		this.views = views;
		this.likes = likes;
		this.dislikes = dislikes;
		this.status = status;
//		this.user = user;
		this.streamUrl = streamUrl;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}


	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getDislikes() {
		return dislikes;
	}

	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}

	public VideoStatus getStatus() {
		return status;
	}

	public void setStatus(VideoStatus status) {
		this.status = status;
	}

//	public UserDto getUser() {
//		return user;
//	}
//
//	public void setUser(UserDto user) {
//		this.user = user;
//	}

	public String getStreamUrl() {
		return streamUrl;
	}

	public void setStreamUrl(String streamUrl) {
		this.streamUrl = streamUrl;
	}
	
	
	
	
	
	
	
	
}
