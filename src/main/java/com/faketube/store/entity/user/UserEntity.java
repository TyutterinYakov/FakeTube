package com.faketube.store.entity.user;

import java.util.HashSet;
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
	@OneToMany(mappedBy="user", cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private Set<VideoEntity> videos = new HashSet<>();
	@Enumerated(value = EnumType.STRING)
	private UserRole role = UserRole.CREATOR;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
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
	
	
	
}
