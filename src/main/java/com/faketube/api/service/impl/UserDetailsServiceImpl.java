package com.faketube.api.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faketube.api.dto.UserDto;
import com.faketube.api.dto.factory.UserDtoFactory;
import com.faketube.api.exception.UserFoundException;
import com.faketube.api.model.UserModel;
import com.faketube.api.security.SecurityUser;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userDao;
	private final UserDtoFactory userDtoFactory;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userDao, UserDtoFactory userDtoFactory) {
		super();
		this.userDao = userDao;
		this.userDtoFactory = userDtoFactory;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userDao.findByEmail(username).orElseThrow(()->
			new UsernameNotFoundException(
					String.format(
							"Пользователь с email %s не найден", username)));
		return  SecurityUser.fromUser(user);
	}


	public UserDto getUser(String userName) {
		loadUserByUsername(userName);
		return userDtoFactory.createUserDto(userDao.findByEmail(userName).orElseThrow(()->
			new UsernameNotFoundException(userName)));
	}


	public UserDto createUser(UserModel user){
		
		userDao.findByEmail(user.getEmail()).ifPresent((u)->{
			throw new UserFoundException(
					String.format("Пользователь с почтой %s уже зарегистрирован",
							user.getEmail()));});
		
		return userDtoFactory.createUserDto(userDao.saveAndFlush(
				new UserEntity(
						user.getUserName(), 
						user.getEmail(), 
						passwordEncoder()
							.encode(user.getPassword()))));
	}
	
	private PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder(12);
	}

}
