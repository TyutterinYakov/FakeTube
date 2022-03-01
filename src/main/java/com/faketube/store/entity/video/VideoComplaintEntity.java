package com.faketube.store.entity.video;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.faketube.store.entity.user.UserEntity;

@Entity
@Table(name="video_complaint")
public class VideoComplaintEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long complaintId;
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private UserEntity user;
	private String description;
	private LocalDateTime createdAt = LocalDateTime.now();
	@Enumerated(value = EnumType.STRING)
	private ReasonVideo reason;
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private VideoEntity video;
	@Enumerated(value = EnumType.STRING)
	private VideoComplaintStatus status = VideoComplaintStatus.CONSIDERATION;
	
	public VideoComplaintEntity() {
		super();
	}
	public VideoComplaintEntity(UserEntity user, String description, ReasonVideo reason, VideoEntity video) {
		super();
		this.user = user;
		this.description = description;
		this.reason = reason;
		this.video = video;
	}
	public Long getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(Long complaintId) {
		this.complaintId = complaintId;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
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
	public VideoEntity getVideo() {
		return video;
	}
	public void setVideo(VideoEntity video) {
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
