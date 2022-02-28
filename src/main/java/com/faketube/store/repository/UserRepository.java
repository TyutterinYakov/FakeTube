package com.faketube.store.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faketube.store.entity.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByEmailAndDeletedAt(String email, Object object);

	Optional<UserEntity> findByuserIdAndDeletedAt(Long userId, Object object);

	Optional<UserEntity> findByEmail(String email);

	void deleteAllByDeletedAtBefore(LocalDateTime minusMonths);

	Optional<UserEntity> findByEmailAndActive(String userName, boolean b);

	Optional<UserEntity> findByuserIdAndDeletedAtAndActive(Long userId, Object object, boolean b);

	Optional<UserEntity> findByUserIdAndActive(Long userId, boolean b);
	

}
