package com.faketube.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faketube.api.dto.AcceptedComplaintDto;
import com.faketube.store.entity.user.UserEntity;
import com.faketube.store.entity.video.AcceptedComplaint;
import com.faketube.store.entity.video.VideoEntity;

@Repository
public interface AcceptedComplaintRepository extends JpaRepository<AcceptedComplaint, Long>{

	Long countByAuthorVideo(UserEntity user);

	void deleteByVideoAndAuthorVideoAndComplaintUser(VideoEntity video, UserEntity user, UserEntity user2);

	Optional<AcceptedComplaint> findAllByAuthorVideo(UserEntity u);

	List<AcceptedComplaint> findAllByVideo(VideoEntity v);
	

}
