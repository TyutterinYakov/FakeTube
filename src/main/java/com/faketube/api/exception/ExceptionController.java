package com.faketube.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	
	@ExceptionHandler
	public ResponseEntity<?> notFoundException(NotFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<?> userFoundException(UserFoundException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.FOUND);
	}
	@ExceptionHandler
	public ResponseEntity<?> badRequestException(BadRequestException ex){
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
}
