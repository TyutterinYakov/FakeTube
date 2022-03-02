package com.faketube.store.entity.video;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.faketube.store.entity.stats.CommentEntity;
import com.faketube.store.entity.stats.GradeVideo;
import com.faketube.store.entity.stats.VideoUniqueViews;
import com.faketube.store.entity.user.UserEntity;

@Entity
@Table(name="video_metadata")
public class VideoEntity {

	@Id
	@Column(name="video_id")
	private String videoId = UUID.randomUUID().toString().substring(0, 8);
	private String title;
	private String description;
	@Enumerated(value=EnumType.STRING)
	private VideoStatus status = VideoStatus.PUBLIC;
	private Integer views=0;
	private String fileName;
	private Long fileSize;
	private Long videoLength;
	private String contentType;
	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime deletedAt;
	private LocalDateTime blockedAt;
	@Enumerated(value=EnumType.STRING)
	private VideoStatus oldStatusVideo;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private UserEntity user;
	@OneToMany(mappedBy="video", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	private List<VideoUniqueViews> viewsList;
	@OneToMany(mappedBy="video", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	private Set<GradeVideo> grades = new HashSet<>();
	@OneToMany(mappedBy="video", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	private List<CommentEntity> comments = new LinkedList<>();
	
	public VideoEntity() {
		super();
	}
	

	public VideoEntity(String title, String description, String fileName, Long fileSize,
			String contentType, UserEntity user) {
		super();
		this.title = title;
		this.description = description;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.user=user;
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
	public LocalDateTime getBlockedAt() {
		return blockedAt;
	}
	public void setBlockedAt(LocalDateTime blockedAt) {
		this.blockedAt = blockedAt;
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
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public Long getVideoLength() {
		return videoLength;
	}
	public void setVideoLength(Long videoLength) {
		this.videoLength = videoLength;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public List<VideoUniqueViews> getViewsList() {
		return viewsList;
	}
	public void setViewsList(List<VideoUniqueViews> viewsList) {
		this.viewsList = viewsList;
	}
	public Set<GradeVideo> getGrades() {
		return grades;
	}
	public void setGrades(Set<GradeVideo> grades) {
		this.grades = grades;
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
	public VideoStatus getOldStatusVideo() {
		return oldStatusVideo;
	}
	public void setOldStatusVideo(VideoStatus oldStatusVideo) {
		this.oldStatusVideo = oldStatusVideo;
	}
	public List<CommentEntity> getComments() {
		return comments;
	}


	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}


	@Override
	public String toString() {
		return String.format("%s %s %s %s %s", videoId, title, fileName, deletedAt, user.getUserId());
	}
	
	
	
}
