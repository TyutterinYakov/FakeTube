package com.faketube.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.faketube.store.entity.stats.VideoUniqueViews;
import com.faketube.store.entity.video.VideoEntity;

@Repository
public interface VideoUniqueViewsRepository extends JpaRepository<VideoUniqueViews, Long>{

	Optional<VideoUniqueViews> findByDataViewers(String sha1);

	Optional<VideoUniqueViews> findByDataViewersAndVideo(String viewersData, VideoEntity video);
}
