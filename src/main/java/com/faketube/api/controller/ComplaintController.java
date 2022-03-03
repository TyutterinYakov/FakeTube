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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faketube.api.dto.AcceptedComplaintDto;
import com.faketube.api.dto.VideoComplaintDto;
import com.faketube.api.service.ComplaintService;
import com.faketube.store.entity.video.ReasonVideo;
import com.faketube.store.entity.video.VideoComplaintStatus;

@RestController
@CrossOrigin("*")
public class ComplaintController {

	private final ComplaintService complaintService;

	@Autowired
	public ComplaintController(ComplaintService complaintService) {
		super();
		this.complaintService = complaintService;
	}
	
	public static final String ADD_COMPLAINT_BY_VIDEO = "/api/video/complaint/{videoId}";
	public static final String GET_COMPLAINT_BY_VIDEO = "/api/video/complaint/{videoId}";
	public static final String GET_ALL_COMPLAINTS_BY_USER = "/api/video/complaint";
	public static final String UPDATE_STATUS_COMPLAINT = "/api/video/complaint/{complaintId}";
	public static final String DELETE_COMPLAINT_AND_RECOVERY_VIDEO = "/api/video/complaint/{complaintId}";
	public static final String GET_ALL_ACCEPTED_COMPLAINT = "/api/video/complaint/accepted";
	
	
	
	@GetMapping(GET_COMPLAINT_BY_VIDEO)
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<List<VideoComplaintDto>> getComplaintsVideo(@PathVariable("videoId") String videoId){
		return ResponseEntity.ok(complaintService.getComplaintsVideo(videoId));
	}
	
	
	@PostMapping(ADD_COMPLAINT_BY_VIDEO)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<?> addComplaintVideo(
			@PathVariable("videoId") String videoId,
			@RequestParam("description") String description, 
			@RequestParam("reason") ReasonVideo reason,
			Principal principal){
		complaintService.addComplaintVideo(videoId, description, reason, principal);
	
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(GET_ALL_COMPLAINTS_BY_USER)
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<List<VideoComplaintDto>> getComplaintsByUser(Principal principal){
		return ResponseEntity.ok(complaintService.getComplaintsByUser(principal.getName()));
	}
	
	@PutMapping(UPDATE_STATUS_COMPLAINT)
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<?> updateStatusComplaintStatus(
			@PathVariable("complaintId") Long complaintId,
			@RequestParam("status") VideoComplaintStatus status){
		complaintService.updateStatusComplaint(complaintId, status);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping(DELETE_COMPLAINT_AND_RECOVERY_VIDEO)
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<?> deleteComplaintAndRecoveryVideo(
			@PathVariable("complaintId") Long complaintId){
		complaintService.deleteComplaintAndRecoveryVideo(complaintId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(GET_ALL_ACCEPTED_COMPLAINT)
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<List<AcceptedComplaintDto>> getAllAcceptedComplaint(){
		return ResponseEntity.ok(complaintService.getAllAcceptedComplaint());
	}
	
	
	
	
	
	
	
	
	
	
	
}
