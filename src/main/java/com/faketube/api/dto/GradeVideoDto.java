package com.faketube.api.dto;

import com.faketube.store.entity.stats.GradeVideoStatus;

public class GradeVideoDto {

	private Long countLikes;
	private Long countDislikes;
	private GradeVideoStatus givenStatus;
	public Long getCountLikes() {
		return countLikes;
	}
	public void setCountLikes(Long countLikes) {
		this.countLikes = countLikes;
	}
	public Long getCountDislikes() {
		return countDislikes;
	}
	public void setCountDislikes(Long countDislikes) {
		this.countDislikes = countDislikes;
	}
	public GradeVideoStatus getGivenStatus() {
		return givenStatus;
	}
	public void setGivenStatus(GradeVideoStatus givenStatus) {
		this.givenStatus = givenStatus;
	}
	
	
	
	
	
	
	
	
	
}
