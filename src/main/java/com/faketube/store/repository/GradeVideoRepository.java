package com.faketube.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faketube.store.entity.stats.GradeVideo;
import com.faketube.store.entity.stats.GradeVideoStatus;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.entity.video.VideoEntity;

@Repository
public interface GradeVideoRepository extends JpaRepository<GradeVideo, Long>{

	Optional<GradeVideo> findByUserAndVideo(UserEntity user, VideoEntity videoAll);

	Long countByVideoAndStatus(VideoEntity videoEntity, GradeVideoStatus dislike);

}
