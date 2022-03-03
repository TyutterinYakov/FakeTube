package com.faketube.api.dto;

import java.time.LocalDateTime;

import com.faketube.store.entity.video.ReasonVideo;

public class AcceptedComplaintDto {
	
	private Long id;
	private ReasonVideo reason;
	private VideoDtoInfo video;
	private LocalDateTime createdAt;
	private UserDto authorVideo;
	private UserDto complaintUser;
	
	public AcceptedComplaintDto() {
		super();
	}
	
	public AcceptedComplaintDto(Long id, ReasonVideo reason, VideoDtoInfo video, LocalDateTime createdAt,
			UserDto authorVideo, UserDto complaintUser) {
		super();
		this.id = id;
		this.reason = reason;
		this.video = video;
		this.createdAt = createdAt;
		this.authorVideo = authorVideo;
		this.complaintUser = complaintUser;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ReasonVideo getReason() {
		return reason;
	}
	public void setReason(ReasonVideo reason) {
		this.reason = reason;
	}
	public VideoDtoInfo getVideo() {
		return video;
	}
	public void setVideo(VideoDtoInfo video) {
		this.video = video;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public UserDto getAuthorVideo() {
		return authorVideo;
	}
	public void setAuthorVideo(UserDto authorVideo) {
		this.authorVideo = authorVideo;
	}
	public UserDto getComplaintUser() {
		return complaintUser;
	}
	public void setComplaintUser(UserDto complaintUser) {
		this.complaintUser = complaintUser;
	}
	
	
	
	
	
	
	
}
