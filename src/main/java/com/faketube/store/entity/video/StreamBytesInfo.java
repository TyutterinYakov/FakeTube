package com.faketube.store.entity.video;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public class StreamBytesInfo {

	private final StreamingResponseBody response;
	private final Long fileSize;
	private final Long rangeStart;
	private final Long rangeEnd;
	private final String contentType;


	public StreamBytesInfo(StreamingResponseBody response, Long fileSize, Long rangeStart, Long rangeEnd,
			String contentType) {
		super();
		this.response = response;
		this.fileSize = fileSize;
		this.rangeStart = rangeStart;
		this.rangeEnd = rangeEnd;
		this.contentType = contentType;
	}

	public StreamingResponseBody getResponse() {
		return response;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public Long getRangeStart() {
		return rangeStart;
	}

	public Long getRangeEnd() {
		return rangeEnd;
	}

	public String getContentType() {
		return contentType;
	}
	
	
	
	
	
}
