package com.faketube.store.entity.stats;

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
import com.faketube.store.entity.video.VideoEntity;

@Entity
@Table(name="comment")
public class CommentEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long commentId;
	private String message;
	private LocalDateTime createdAt=LocalDateTime.now();
	private LocalDateTime deletedAt;
	private Boolean change = false;
	@Enumerated(value = EnumType.STRING)
	private CommentStatus status = CommentStatus.ACTIVE;
	@Enumerated(value = EnumType.STRING)
	private CommentStatus oldStatus;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private UserEntity user;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private VideoEntity video;
	
	public CommentEntity() {
		super();
	}
	public CommentEntity(String message, UserEntity user, VideoEntity video) {
		super();
		this.message = message;
		this.user = user;
		this.video = video;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}
	public CommentStatus getStatus() {
		return status;
	}
	public void setStatus(CommentStatus status) {
		this.status = status;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public VideoEntity getVideo() {
		return video;
	}
	public void setVideo(VideoEntity video) {
		this.video = video;
	}
	public CommentStatus getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(CommentStatus oldStatus) {
		this.oldStatus = oldStatus;
	}
	public Boolean getChange() {
		return change;
	}
	public void setChange(Boolean change) {
		this.change = change;
	}
	
	
	
	
	
	
}
