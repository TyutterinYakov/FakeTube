package com.faketube.api.dto;

import java.time.LocalDateTime;

public class CommentDto {

	private Long commentId;
	private String message;
	private String videoId;
	private LocalDateTime createdAt;
	private UserDto user;
	
	public CommentDto() {
		super();
	}
	public CommentDto(Long commentId, String message, 
			String videoId, LocalDateTime createdAt, UserDto user) {
		super();
		this.commentId = commentId;
		this.message = message;
		this.videoId = videoId;
		this.createdAt=createdAt;
		this.user = user;
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
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	
}
