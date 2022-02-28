package com.faketube.api.service;

import java.util.Map;

import com.faketube.api.model.AuthModel;

public interface AuthenticationService {
	public Map<Object, Object> getAuthenticate(AuthModel request);
}
