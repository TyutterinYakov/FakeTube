package com.faketube.api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faketube.api.dto.UserDto;
import com.faketube.api.dto.factory.UserDtoFactory;
import com.faketube.api.exception.BadRequestException;
import com.faketube.api.exception.NotFoundException;
import com.faketube.api.service.SubscribeService;
import com.faketube.store.entity.stats.SubscribeEntity;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.repository.SubscribeRepository;
import com.faketube.store.repository.UserRepository;

@Service
public class SubscribeServiceImpl implements SubscribeService {

	
	private final UserRepository userDao;
	private final UserDtoFactory userDtoFactory;
	private final SubscribeRepository subscribeDao;
	
	@Autowired
	public SubscribeServiceImpl(UserRepository userDao, UserDtoFactory userDtoFactory,
			SubscribeRepository subscribeDao) {
		super();
		this.userDao = userDao;
		this.userDtoFactory = userDtoFactory;
		this.subscribeDao = subscribeDao;
	}

	@Override
	public List<UserDto> getUserSubscribers(String userName) {
		List<UserEntity> list = 
				subscribeDao
				.findAllBySubscription(
						findUserByUsernameAndActive(userName))
				.stream()
				.map((s)->s.getSubscribe())
				.collect(Collectors.toList());
		return userDtoFactory.createListUserDto(list);
	}

	@Override
	public List<UserDto> getUserSubscriptions(String userName) {
		List<UserEntity> list = subscribeDao
				.findAllBySubscribe(
				findUserByUsernameAndActive(userName))
				.stream()
				.map((s)->s.getSubscription())
				.collect(Collectors.toList());
		return userDtoFactory.createListUserDto(list);
	}
	
	@Override
	public void addSubscription(Long userId, String userName) {
		UserEntity userSubscriber = findUserByUserIdAndActive(userId); //На кого подписка
		UserEntity userSubscription = findUserByUsernameAndActive(userName); //Кто подписывается
		if(userSubscriber.getUserId().equals(userSubscription.getUserId())) {
			throw new BadRequestException("Нельзя подписаться на самого себя");
		}
		findSubscriber(userSubscriber, userSubscription).ifPresentOrElse((s)->{
			throw new BadRequestException("Подписка на пользователя уже оформлена");
		}, ()->{
			subscribeDao.save(new SubscribeEntity(userSubscription, userSubscriber));
		});
		
		
	}

	@Override
	public void deleteSubscription(Long userId, String name) {
		findSubscriber(
				findUserByUserIdAndActive(userId), 
				findUserByUsernameAndActive(name))
		.ifPresentOrElse((s)->{
			subscribeDao.deleteById(s.getSubscribeId());
		}, ()->{
			throw new NotFoundException("Подписка на пользователя не найдена");
		});
	}
	
	
	
	
	
	
	
	
	private UserEntity findUserByUsernameAndActive(String username) {
		return userDao.findByEmailAndActive(username, true).orElseThrow(()->
				new BadRequestException(
						String.format(
								"Пользователь с именем %s не найден", username)));
	}
	
	private UserEntity findUserByUserIdAndActive(Long userId) {
		return userDao.findByUserIdAndActive(userId, true).orElseThrow(()->
				new BadRequestException(
						String.format(
								"Пользователь с идентификатором %s не найден", userId)));
	}
	
	
	private Optional<SubscribeEntity> findSubscriber(
			UserEntity userSubscriber, UserEntity userSubscription) {
		return subscribeDao
				.findBySubscribeAndSubscription(userSubscription, userSubscriber);
	}
	
	
	
	
	
	
	
	
	
	
}
