package com.faketube.store.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faketube.store.entity.stats.CommentEntity;
import com.faketube.store.entity.stats.CommentStatus;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.entity.video.VideoEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>{

	Optional<CommentEntity> findByUserAndVideoAndCommentIdAndStatus(
			UserEntity user, VideoEntity v, Long commentId, CommentStatus status);

	void deleteAllByDeletedAtBefore(LocalDateTime minusMonths);

}
