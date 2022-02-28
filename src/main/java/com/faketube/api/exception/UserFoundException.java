package com.faketube.api.exception;

public class UserFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserFoundException() {
		super();
	}

	public UserFoundException(String message) {
		super(message);
	}

}
