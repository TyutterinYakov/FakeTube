package com.faketube.store.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faketube.store.entity.user.BlockUserEntity;
import com.faketube.store.entity.user.UserEntity;

@Repository
public interface BlockUserRepository extends JpaRepository<BlockUserEntity, Long>{

	Optional<BlockUserEntity> findByBlockUserAndAuthorChannel(UserEntity user, UserEntity user2);

	void deleteAllByBlockedTimeBefore(LocalDateTime now);

	List<BlockUserEntity> findAllByBlockedTimeBefore(LocalDateTime now);

}
