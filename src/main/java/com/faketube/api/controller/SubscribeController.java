package com.faketube.api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faketube.api.dto.UserDto;
import com.faketube.api.service.SubscribeService;

@RestController
@CrossOrigin("*")
public class SubscribeController {
	
	private final SubscribeService subscribeService;

	@Autowired
	public SubscribeController(SubscribeService subscribeService) {
		super();
		this.subscribeService = subscribeService;
	}
	
	public static final String GET_USER_SUBSCRIBERS = "/api/user/subscribers";
	public static final String GET_USER_SUBSCRIPTIONS = "/api/user/subscriptions";
	public static final String ADD_SUBSCRIPTION_BY_USER_ID = "/api/user/subscriptions";
	public static final String DELETE_SUBSCRIPTION_BY_USER_ID = "/api/user/subscriptions/{userId}";
	
	
	
	@GetMapping(GET_USER_SUBSCRIBERS)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<List<UserDto>> getUserSubscribers(Principal principal){
		return ResponseEntity.ok(
				subscribeService
				.getUserSubscribers(
						principal.getName()));
	}
	
	@GetMapping(GET_USER_SUBSCRIPTIONS)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<List<UserDto>> getUserSubscriptions(Principal principal){
		return ResponseEntity.ok(
				subscribeService
				.getUserSubscriptions(
						principal.getName()));
	}
	
	@PostMapping(ADD_SUBSCRIPTION_BY_USER_ID)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> addSubscription(
			@RequestParam("userId") Long userId, Principal principal){
		subscribeService.addSubscription(userId, principal.getName());
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping(DELETE_SUBSCRIPTION_BY_USER_ID)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> deleteSubscription(
			@PathVariable("userId") Long userId, Principal principal){
		subscribeService.deleteSubscription(userId, principal.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
	
}
