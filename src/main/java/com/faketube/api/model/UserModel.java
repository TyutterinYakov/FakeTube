package com.faketube.api.model;

import javax.validation.constraints.NotNull;


public class UserModel {

	@NotNull
	private String userName;
	@NotNull
	private String email;
	private String password="";
	private Boolean accessToGradeVideo=true;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public boolean isAccessToGradeVideo() {
		return accessToGradeVideo;
	}
	public void setAccessToGradeVideo(boolean accessToGradeVideo) {
		this.accessToGradeVideo = accessToGradeVideo;
	}
	
	
	
	
	
}
