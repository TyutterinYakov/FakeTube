package com.faketube.api.dto;

import com.faketube.store.entity.user.UserRole;

public class UserDto {
	private Long userId;
	private String username;
	private String email;
	private UserRole role;
	
	public UserDto() {
		super();
	}

	public UserDto(Long userId, String username, String email, UserRole role) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.role = role;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
	
	
	
	
	
	
}
