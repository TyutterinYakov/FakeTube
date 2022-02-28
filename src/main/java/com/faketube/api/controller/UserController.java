package com.faketube.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faketube.api.dto.UserDto;
import com.faketube.api.dto.VideoDto;
import com.faketube.api.service.UserService;

@RestController
@RequestMapping
@CrossOrigin("*")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	
	public static final String GET_ALL_VIDEO_USER_ACCESS = "/api/video/user/{userId}";
	public static final String GET_ALL_GRADE_VIDEO_USER = "/api/video/user/grade/{userId}";
	public static final String GET_INFO_FROM_USER = "/api/video/user/info/{userId}";
	
	
	
	@GetMapping(GET_ALL_VIDEO_USER_ACCESS)
	public ResponseEntity<List<VideoDto>> getAllAccessVideoFromUser(@PathVariable("userId") Long userId){
		return ResponseEntity.ok(userService.getAccessVideoFromUser(userId));
	}
	
	@GetMapping(GET_ALL_GRADE_VIDEO_USER)
	public ResponseEntity<List<VideoDto>> getAllGradeVideoUser(@PathVariable("userId") Long userId){
		return ResponseEntity.ok(userService.getAllGradeVideoUser(userId));
	}
	
	@GetMapping(GET_INFO_FROM_USER)
	public ResponseEntity<UserDto> getInfoFromUser(@PathVariable("userId") Long userId){
		return ResponseEntity.ok(userService.getInfoFromUser(userId));
	}
	
	
}
