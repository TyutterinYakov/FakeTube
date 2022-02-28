package com.faketube.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.faketube.api.exception.NotFoundException;
import com.faketube.api.model.AuthModel;
import com.faketube.api.security.JwtTokenProvider;
import com.faketube.api.service.AuthenticationService;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.repository.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class); 
	private final AuthenticationManager authManager;
	private final JwtTokenProvider jwtProvider;
	private final UserRepository userDao;
	
	@Autowired
	public AuthenticationServiceImpl(AuthenticationManager authManager, JwtTokenProvider jwtProvider,
			UserRepository userDao) {
		super();
		this.authManager = authManager;
		this.jwtProvider = jwtProvider;
		this.userDao = userDao;
	}
	
	@Transactional
	public Map<Object, Object> getAuthenticate(AuthModel request) {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
			UserEntity user = userDao.findByEmail(request.getUserName()).orElseThrow(()->
					new NotFoundException(request.getUserName()));
			logger.info(user.getEmail());
			String token = jwtProvider.createToken(request.getUserName(), user.getRole().name());
			Map<Object, Object> response = new HashMap<>();
			response.put("username", request.getUserName());
			response.put("token", token);
			return response;
	}
	
}
