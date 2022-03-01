package com.faketube.api.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faketube.api.dto.CommentDto;
import com.faketube.api.dto.GradeVideoDto;
import com.faketube.api.service.StatsVideoService;
import com.faketube.store.entity.stats.GradeVideoStatus;

@RestController
@RequestMapping
@CrossOrigin("*")
public class StatsVideoController {

	
	private final StatsVideoService statsVideoService;

	public StatsVideoController(StatsVideoService statsVideoService) {
		super();
		this.statsVideoService = statsVideoService;
	}
	
	
	
	public static final String GET_STATS_VIDEO_BY_VIDEO_ID_AND_PRINCIPAL = "/api/video/stats/{videoId}";
	public static final String ADD_VIDEO_GRADE_BY_PRINCIPAL_AND_VIDEO_ID = "/api/video/stats/{videoId}";
	public static final String GET_COMMENTS_VIDEO_BY_VIDEO_ID = "/api/video/comments/{videoId}";
	public static final String ADD_COMMENT_VIDEO_BY_VIDEO_ID = "/api/video/comments/{videoId}";
	public static final String DELETE_COMMENT_VIDEO= "/api/video/comments/{videoId}/{commentId}";
	public static final String UPDATE_COMMENT_VIDEO = "/api/video/comments/{videoId}/{commentId}";

	
	
	@PostMapping(ADD_VIDEO_GRADE_BY_PRINCIPAL_AND_VIDEO_ID)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> addVideoGrade(
			Principal principal, @PathVariable("videoId") String videoId,
			@RequestParam(required = false, name = "grade") Optional<GradeVideoStatus> grade){
		statsVideoService.addVideoGrade(principal, videoId, grade);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping(GET_STATS_VIDEO_BY_VIDEO_ID_AND_PRINCIPAL)
	public ResponseEntity<GradeVideoDto> getGradeVideo(@PathVariable("videoId") String videoId,
			Principal principal){
		return ResponseEntity.ok(statsVideoService.getVideoDtoByVideoId(videoId, principal));
	}
	
	@GetMapping(GET_COMMENTS_VIDEO_BY_VIDEO_ID)
	public ResponseEntity<List<CommentDto>> getCommentsVideo(@PathVariable("videoId") String videoId, Principal principal){
		return ResponseEntity.ok(statsVideoService.getCommentsVideo(videoId, principal));
	}
	
	@PostMapping(ADD_COMMENT_VIDEO_BY_VIDEO_ID)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> addCommentVideo(@RequestParam String message,
			@PathVariable("videoId") String videoId, Principal principal){
		statsVideoService.addCommentVideo(videoId, message, principal.getName());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping(DELETE_COMMENT_VIDEO)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> deleteCommentVideo(Principal principal, 
			@PathVariable("videoId") String videoId, @PathVariable("commentId") Long commentId){
		statsVideoService.deleteCommentVideo(principal, videoId, commentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(UPDATE_COMMENT_VIDEO)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> updateCommentVideo(
			Principal principal, @PathVariable("videoId") String videoId,
			@PathVariable("commentId") Long commentId, @RequestParam String message){
		statsVideoService.updateCommentVideo(message, videoId, principal.getName(), commentId);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
		
	}
	
	
	
	
	
	
			
			
	
	
	
	
	
	
	
	
}
