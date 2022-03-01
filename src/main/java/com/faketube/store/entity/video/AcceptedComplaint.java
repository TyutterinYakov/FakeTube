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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.faketube.store.entity.user.UserEntity;

@Entity
@Table
public class AcceptedComplaint {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Enumerated(value=EnumType.STRING)
	private ReasonVideo reason;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private VideoEntity video;
	private LocalDateTime createdAt = LocalDateTime.now();
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private UserEntity authorVideo;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private UserEntity complaintUser;
	
	public AcceptedComplaint() {
		super();
	}
	
	public AcceptedComplaint(ReasonVideo reason, VideoEntity video, 
			UserEntity authorVideo, UserEntity complaintUser) {
		super();
		this.reason = reason;
		this.video = video;
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
	public UserEntity getAuthorVideo() {
		return authorVideo;
	}
	public void setAuthorVideo(UserEntity authorVideo) {
		this.authorVideo = authorVideo;
	}
	public UserEntity getComplaintUser() {
		return complaintUser;
	}
	public void setComplaintUser(UserEntity complaintUser) {
		this.complaintUser = complaintUser;
	}
	
	
	
	
	
	
	
	
}
