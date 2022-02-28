package com.faketube.api.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	
	
			
			
	
	
	
	
	
	
	
	
}
