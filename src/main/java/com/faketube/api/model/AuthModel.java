package com.faketube.api.model;

import javax.validation.constraints.NotNull;

public class AuthModel {
	@NotNull
	private String userName;
	@NotNull
	private String password;
	
	
	
	public AuthModel() {
		super();
	}

	public AuthModel(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
