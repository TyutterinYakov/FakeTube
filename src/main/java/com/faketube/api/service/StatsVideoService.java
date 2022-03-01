package com.faketube.api.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.faketube.api.dto.CommentDto;
import com.faketube.api.dto.GradeVideoDto;
import com.faketube.store.entity.stats.GradeVideoStatus;

public interface StatsVideoService {

	void addVideoGrade(Principal principal, String videoId, Optional<GradeVideoStatus> grade);

	GradeVideoDto getVideoDtoByVideoId(String videoId, Principal principal);

	List<CommentDto> getCommentsVideo(String videoId, Principal principal);

	void addCommentVideo(String videoId, String message, String name);

	void deleteCommentVideo(Principal principal, String videoId, Long commentId);

	void updateCommentVideo(String message, String videoId, String name, Long commentId);

}
