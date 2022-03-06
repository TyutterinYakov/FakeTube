package com.faketube.api.service;

import java.util.List;

import com.faketube.api.dto.UserDto;

public interface SubscribeService {

	List<UserDto> getUserSubscribers(String name);

	List<UserDto> getUserSubscriptions(String name);

	void addSubscription(Long userId, String name);

	void deleteSubscription(Long userId, String name);

}
