package com.faketube.api.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faketube.api.dto.UserDto;
import com.faketube.api.exception.BadRequestException;
import com.faketube.api.model.AuthModel;
import com.faketube.api.model.UserModel;
import com.faketube.api.service.AuthenticationService;
import com.faketube.api.service.impl.UserDetailsServiceImpl;

@RestController
@RequestMapping
@CrossOrigin("*")
public class AuthController {

	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationService authService;
	
	@Autowired
	public AuthController(UserDetailsServiceImpl userDetailsService, AuthenticationService authService) {
		super();
		this.userDetailsService = userDetailsService;
		this.authService = authService;
	}
	
	public static final String REGISTER_USER = "/api/auth/register";
	public static final String LOGOUT_USER = "/api/auth/logout";
	public static final String GET_USER_BY_PRINCIPAL = "/api/auth/current-user";
	public static final String GENERATE_TOKEN_USER = "/api/auth/generate-token";
	
	
	@PostMapping(GENERATE_TOKEN_USER)
	public ResponseEntity<?> authenticate(@Valid AuthModel request, BindingResult result){
		checkBindingResult(result);
		return ResponseEntity.ok(authService.getAuthenticate(request));
	}

	@PostMapping(LOGOUT_USER)
	public void logout(HttpServletRequest request, HttpServletResponse response){
		SecurityContextLogoutHandler securityHandler = new SecurityContextLogoutHandler();
		securityHandler.logout(request, response, null);
	}
	
	@GetMapping(GET_USER_BY_PRINCIPAL)
	public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
		return ResponseEntity.ok(userDetailsService.getUser(principal.getName()));
	}
	
	@PostMapping(REGISTER_USER)
	public ResponseEntity<?> createUser(@Valid UserModel user, BindingResult result) {
		checkBindingResult(result);
		return new ResponseEntity<>(userDetailsService.createUser(user), HttpStatus.CREATED);
	}
		
	
	private void checkBindingResult(BindingResult result) {
		if(result.hasErrors()) 
			throw new BadRequestException("Ошибка при вводе данных");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
