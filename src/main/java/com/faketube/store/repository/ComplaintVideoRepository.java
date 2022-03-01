package com.faketube.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.entity.video.VideoComplaintEntity;
import com.faketube.store.entity.video.VideoEntity;

@Repository
public interface ComplaintVideoRepository extends JpaRepository<VideoComplaintEntity, Long>{

	List<VideoComplaintEntity> findAllByVideo(VideoEntity v);

	Optional<VideoComplaintEntity> findByUserAndVideo(UserEntity user, VideoEntity video);

}
