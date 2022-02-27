package com.faketube.api.service;

import java.util.Optional;

import com.faketube.api.dto.GradeVideoDto;
import com.faketube.store.entity.stats.GradeVideoStatus;

public interface StatsVideoService {

	void addVideoGrade(String userName, String videoId, Optional<GradeVideoStatus> grade);

	GradeVideoDto getVideoDtoByVideoId(String videoId, String userName);

}
