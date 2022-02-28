package com.faketube.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faketube.api.dto.UserDto;
import com.faketube.api.dto.VideoDto;
import com.faketube.api.exception.BadRequestException;
import com.faketube.api.model.UserModel;
import com.faketube.api.model.UserModelRegister;
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
	public static final String UPDATE_USER_PROFILE = "/api/user/profile";
	public static final String CREATE_USER_PROFILE = "/api/user/profile";
	public static final String DELETE_USER_PROFILE = "/api/user/profile";
	public static final String BLOCK_USER_BY_USER_ID = "/api/user/profile/block";
	public static final String UNBLOCK_USER_BY_USER_ID = "/api/user/profile/ublock";;
	
	
	
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
	
	@PutMapping(UPDATE_USER_PROFILE)
	public ResponseEntity<?> updateUserProfile(			//TODO String principal to PRINCIPAL security
			@RequestParam("principal") String principal, @Valid UserModel userModel, BindingResult result){
		checkBindingResult(result);
		userService.updateUserProfile(principal, userModel);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@PostMapping(CREATE_USER_PROFILE)
	public ResponseEntity<?> createUserProfile(@Valid UserModelRegister userModel, BindingResult result){
		checkBindingResult(result);
		userService.createUserProfile(userModel);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping(DELETE_USER_PROFILE)
	public ResponseEntity<?> deleteUserProfile(String principal) { ///TODO PRINCIPAL
		userService.deleteUserProfile(principal);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping(BLOCK_USER_BY_USER_ID)
	public ResponseEntity<?> blockUserByUserId(@RequestParam("userId") Long userId){   //TODO ONLY ADMIN
		userService.blockUserByUserId(userId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	@PatchMapping(UNBLOCK_USER_BY_USER_ID)
	public ResponseEntity<?> unblockuserByUserId(@RequestParam("userId") Long userId){ //TODO ONLY ADMIN
		userService.unblockUserByUserId(userId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	
	
	
	
	private void checkBindingResult(BindingResult result) {
		if(result.hasErrors()) 
			throw new BadRequestException("Ошибка при вводе данных");
	}
	
	
}
