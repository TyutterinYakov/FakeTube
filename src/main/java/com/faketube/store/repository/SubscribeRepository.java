package com.faketube.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faketube.store.entity.stats.SubscribeEntity;
import com.faketube.store.entity.user.UserEntity;

@Repository
public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Long>{

	List<SubscribeEntity> findAllBySubscription(UserEntity findUserByUsernameAndActive);

	List<SubscribeEntity> findAllBySubscribe(UserEntity findUserByUsernameAndActive);

	Optional<SubscribeEntity> findBySubscribeAndSubscription(UserEntity userSubscription, UserEntity userSubscriber);
	
	
	
}
