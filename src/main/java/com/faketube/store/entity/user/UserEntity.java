package com.faketube.store.entity.user;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.faketube.store.entity.stats.CommentEntity;
import com.faketube.store.entity.stats.GradeVideo;
import com.faketube.store.entity.video.AcceptedComplaint;
import com.faketube.store.entity.video.VideoComplaintEntity;
import com.faketube.store.entity.video.VideoEntity;

@Entity
@Table(name="user_")
public class UserEntity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long userId;
	private String username;
	private String email;
	private String password;
	private Boolean active = true;
	private boolean accessToGradeVideo = true;
	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime deletedAt;
	private LocalDateTime blockedAt;
	@OneToMany(mappedBy="user", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	private Set<VideoEntity> videos = new HashSet<>();
	@Enumerated(value = EnumType.STRING)
	private UserRole role = UserRole.CREATOR;
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="user")
	private Set<GradeVideo> gradeVideo = new HashSet<>();
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="user")
	private List<CommentEntity> comments = new LinkedList<>();
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="user")
	private List<VideoComplaintEntity> complaints = new LinkedList<>();
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="authorVideo")
	private List<AcceptedComplaint> authorVideos = new LinkedList<>();
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="complaintUser")
	private List<AcceptedComplaint> complaintUsers = new LinkedList<>();
	
	public UserEntity() {
		super();
	}
	
	public UserEntity(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public List<VideoComplaintEntity> getComplaints() {
		return complaints;
	}
	public void setComplaints(List<VideoComplaintEntity> complaints) {
		this.complaints = complaints;
	}
	public LocalDateTime getBlockedAt() {
		return blockedAt;
	}
	public void setBlockedAt(LocalDateTime blockedAt) {
		this.blockedAt = blockedAt;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<VideoEntity> getVideos() {
		return videos;
	}
	public void setVideos(Set<VideoEntity> videos) {
		this.videos = videos;
	}
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public Set<GradeVideo> getGradeVideo() {
		return gradeVideo;
	}
	public void setGradeVideo(Set<GradeVideo> gradeVideo) {
		this.gradeVideo = gradeVideo;
	}
	public boolean isAccessToGradeVideo() {
		return accessToGradeVideo;
	}
	public void setAccessToGradeVideo(boolean accessToGradeVideo) {
		this.accessToGradeVideo = accessToGradeVideo;
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
	public List<CommentEntity> getComments() {
		return comments;
	}
	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
	}
	public List<AcceptedComplaint> getAuthorVideos() {
		return authorVideos;
	}
	public void setAuthorVideos(List<AcceptedComplaint> authorVideos) {
		this.authorVideos = authorVideos;
	}
	public List<AcceptedComplaint> getComplaintUsers() {
		return complaintUsers;
	}
	public void setComplaintUsers(List<AcceptedComplaint> complaintUsers) {
		this.complaintUsers = complaintUsers;
	}

	
	
	
	
	
	
}
