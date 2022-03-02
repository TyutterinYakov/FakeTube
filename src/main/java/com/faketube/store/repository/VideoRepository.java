package com.faketube.store.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.faketube.store.entity.video.VideoEntity;
import com.faketube.store.entity.video.VideoStatus;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, String>{

	
	@Query(nativeQuery=true, value="SELECT * FROM video_metadata WHERE video_id=?1 AND (status=?2 OR status=?3)")
	Optional<VideoEntity> findVideoByIdAndStatus(String videoId, String publ, String link);

//	Optional<VideoEntity> findByVideoIdAndStatusIsNot(Long videoId, VideoStatus delete);
	
	
	@Query(nativeQuery=true, value="SELECT * FROM video_metadata WHERE video_id=?1 AND status!=?2 AND status!=?3 AND user_user_id=?4")
	Optional<VideoEntity> findVideoByIdAndIsNotStatusAndUserId(String videoId, String delete, String block, Long userId);

	void deleteAllByDeletedAtBefore(LocalDateTime minusMonths);

	List<VideoEntity> findAllByDeletedAtBefore(LocalDateTime minusMonths);
	
	@Query(nativeQuery = true, value = "select video_metadata.* from video_metadata "
			+ "inner join grade_video "
			+ "on video_metadata.video_id=grade_video.video_video_id "
			+ "inner join user_ "
			+ "on user_.access_to_grade_video=?1 "
			+ "where grade_video.status=?2 "
			+ "and (video_metadata.status=?3 or video_metadata.status=?4) "
			+ "and grade_video.user_user_id=?5")
	List<VideoEntity> findAllGradeVideoUserAndStatus(boolean accesUser, String statusGrade, String statusVideo, String statusVideo2, Long userId);

	Optional<VideoEntity> findByVideoIdAndStatus(String videoId, VideoStatus delete);

}
