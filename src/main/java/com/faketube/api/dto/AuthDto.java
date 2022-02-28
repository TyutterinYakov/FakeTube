package com.faketube.api.dto;

public class AuthDto {
	private String token;

	public AuthDto() {
		super();
	}

	public AuthDto(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
