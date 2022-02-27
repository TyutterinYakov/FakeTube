package com.faketube.store.entity.stats;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.faketube.store.entity.video.VideoEntity;

@Entity
@Table(name="video_views")
public class VideoUniqueViews {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="viewers_id")
	private Long viewersId;
	private String dataViewers;
	private LocalDateTime createdAt = LocalDateTime.now();
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private VideoEntity video;
	
	
	public VideoUniqueViews() {
		super();
	}
	public VideoUniqueViews(String dataViewers, VideoEntity video) {
		super();
		this.dataViewers = dataViewers;
		this.video = video;
	}
	public Long getViewersId() {
		return viewersId;
	}
	public void setViewersId(Long viewersId) {
		this.viewersId = viewersId;
	}
	public String getDataViewers() {
		return dataViewers;
	}
	public void setDataViewers(String dataViewers) {
		this.dataViewers = dataViewers;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public VideoEntity getVideo() {
		return video;
	}
	public void setVideo(VideoEntity video) {
		this.video = video;
	}
	
	
	
	
	
	
}
