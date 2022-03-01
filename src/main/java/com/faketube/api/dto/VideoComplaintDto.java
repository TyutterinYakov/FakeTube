package com.faketube.api.dto;

import java.time.LocalDateTime;

import com.faketube.store.entity.video.ReasonVideo;
import com.faketube.store.entity.video.VideoComplaintStatus;

public class VideoComplaintDto {

	private Long complaintId;
	private UserDto user;
	private String description;
	private ReasonVideo reason;
	private VideoDto video;
	private LocalDateTime createdAt;
	private VideoComplaintStatus status;
	
	public VideoComplaintDto() {
		super();
	}
	public VideoComplaintDto(Long complaintId, UserDto user, String description, ReasonVideo reason, VideoDto video,
			LocalDateTime createdAt, VideoComplaintStatus status) {
		super();
		this.complaintId = complaintId;
		this.user = user;
		this.description = description;
		this.reason = reason;
		this.video = video;
		this.createdAt = createdAt;
		this.status=status;
	}
	public Long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ReasonVideo getReason() {
		return reason;
	}
	public void setReason(ReasonVideo reason) {
		this.reason = reason;
	}
	public VideoDto getVideo() {
		return video;
	}
	public void setVideo(VideoDto video) {
		this.video = video;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public VideoComplaintStatus getStatus() {
		return status;
	}
	public void setStatus(VideoComplaintStatus status) {
		this.status = status;
	}
	
	
	
	
	
}
