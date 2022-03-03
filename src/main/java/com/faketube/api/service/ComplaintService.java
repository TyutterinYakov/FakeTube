package com.faketube.api.service;

import java.security.Principal;
import java.util.List;

import com.faketube.api.dto.AcceptedComplaintDto;
import com.faketube.api.dto.VideoComplaintDto;
import com.faketube.store.entity.video.ReasonVideo;
import com.faketube.store.entity.video.VideoComplaintStatus;

public interface ComplaintService {

	void addComplaintVideo(String videoId, String description, ReasonVideo reason, Principal principal);

	List<VideoComplaintDto> getComplaintsVideo(String videoId);

	List<VideoComplaintDto> getComplaintsByUser(String userName);

	void updateStatusComplaint(Long complaintId, VideoComplaintStatus status);

	void deleteComplaintAndRecoveryVideo(Long complaintId);

	List<AcceptedComplaintDto> getAllAcceptedComplaint();

}
