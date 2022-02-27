package com.faketube.store.entity.stats;

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
@Table
public class GradeVideo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long gradeId;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private UserEntity user;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private VideoEntity video;
	@Enumerated(value = EnumType.STRING)
	private GradeVideoStatus status;
	
	
	public GradeVideo() {
		super();
	}
	public GradeVideo(UserEntity user, VideoEntity video, GradeVideoStatus status) {
		super();
		this.user = user;
		this.video = video;
		this.status = status;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
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
	public GradeVideoStatus getStatus() {
		return status;
	}
	public void setStatus(GradeVideoStatus status) {
		this.status = status;
	}
	
	
	
	
	
	
}
