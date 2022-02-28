package com.faketube.api.service;

import java.util.List;

import com.faketube.api.dto.UserDto;
import com.faketube.api.dto.VideoDto;

public interface UserService {

	List<VideoDto> getAccessVideoFromUser(Long userId);

	List<VideoDto> getAllGradeVideoUser(Long userId);

	UserDto getInfoFromUser(Long userId);

}
