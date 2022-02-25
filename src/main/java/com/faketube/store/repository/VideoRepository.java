package com.faketube.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.faketube.api.dto.VideoDto;
import com.faketube.store.entity.video.VideoEntity;
import com.faketube.store.entity.video.VideoStatus;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, String>{

	
	@Query(nativeQuery=true, value="SELECT * FROM video_metadata WHERE video_id=?1 AND (status=?2 OR status=?3)")
	Optional<VideoEntity> findVideoByIdAndStatus(String videoId, String delete, String private1);

//	Optional<VideoEntity> findByVideoIdAndStatusIsNot(Long videoId, VideoStatus delete);

}
