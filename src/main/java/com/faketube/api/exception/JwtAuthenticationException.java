package com.faketube.api.exception;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;

public class JwtAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 1L;
	
	
	private HttpStatus httpStatus;

	public JwtAuthenticationException(String msg) {
		super(msg);
	}
	
	public JwtAuthenticationException(String msg, HttpStatus status) {
		super(msg);
		this.httpStatus=status;
		
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	
	
}
