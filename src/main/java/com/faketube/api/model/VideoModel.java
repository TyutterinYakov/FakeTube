package com.faketube.api.model;

import org.springframework.web.multipart.MultipartFile;

public class VideoModel {

	private String title;
	private String description;
	private MultipartFile video;
//	private MultipartFile preview;
	public VideoModel() {
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipartFile getVideo() {
		return video;
	}
	public void setVideo(MultipartFile video) {
		this.video = video;
	}
//	public MultipartFile getPreview() {
//		return preview;
//	}
//	public void setPreview(MultipartFile preview) {
//		this.preview = preview;
//	}
	
	
	
	
}
