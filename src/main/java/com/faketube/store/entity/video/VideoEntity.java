package com.faketube.store.entity.video;

import java.util.List;
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
	private Integer likes=0;
	private Integer dislikes=0;
	private String fileName;
	private Long fileSize;
	private Long videoLength;
	private String contentType;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private UserEntity user;
	@OneToMany(mappedBy="video", cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private List<VideoUniqueViews> viewsList;
	public VideoEntity() {
		super();
	}
	

	public VideoEntity(String title, String description, String fileName, Long fileSize,
			String contentType) {
		super();
		this.title = title;
		this.description = description;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
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
	
	
	
	
	
}
