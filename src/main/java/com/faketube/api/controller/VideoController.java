package com.faketube.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.faketube.api.dto.VideoDto;
import com.faketube.api.exception.NotFoundException;
import com.faketube.api.model.VideoModelAdd;
import com.faketube.api.model.VideoModelUpdate;
import com.faketube.api.service.VideoService;
import com.faketube.store.entity.video.StreamBytesInfo;



@RestController
@RequestMapping
@CrossOrigin("*")
public class VideoController {

	private static final Logger logger =LoggerFactory.getLogger(VideoController.class);
	
	private final VideoService videoService;
	
	@Autowired
	public VideoController(VideoService videoService) {
		super();
		this.videoService = videoService;
	}
	
	
	public static final String GET_VIDEO_BY_ID="/api/video/{videoId}"; 
	public static final String GET_PLAYER_VIDEO_BY_ID="/api/video/player/{videoId}"; 
	public static final String UPLOAD_NEW_VIDEO_FROM_USER="/api/video/upload"; 
	public static final String GET_ALL_GRADE_VIDEO_FROM_USER = "/api/video/grade-videos";
	public static final String DELETE_VIDEO_FROM_USER_BY_VIDEO_ID = "/api/video/{videoId}";
	private static final String UPDATE_VIDEO_FROM_USER = "/api/video/";
	
	@GetMapping(GET_VIDEO_BY_ID)
	public ResponseEntity<?> getVideoById(@PathVariable("videoId") String videoId,
			HttpServletRequest request) {
		return ResponseEntity.ok(videoService.getVideoById(videoId, request));
	}
	
	@GetMapping(GET_PLAYER_VIDEO_BY_ID)
	public ResponseEntity<StreamingResponseBody> getPlayerVideoById(
			@RequestHeader(value="Range", required = false) String httpRangeHeader,
			@PathVariable("videoId") String videoId) {
		List<HttpRange> httpRangeList =  HttpRange.parseRanges(httpRangeHeader);
		StreamBytesInfo stream = videoService.getStreamBytes(
							videoId, httpRangeList.size()>0?httpRangeList.get(0):null)
				.orElseThrow(NotFoundException::new);
		Long byteLength = stream.getRangeEnd()-stream.getRangeStart()+1;
        ResponseEntity.BodyBuilder builder = ResponseEntity.
        		status(httpRangeList.size() > 0 ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
                .header("Content-Type", stream.getContentType())
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", Long.toString(byteLength));
		if(httpRangeList.size()>0) {
			builder.header("Content-Range", 
					"bytes "+stream.getRangeStart()+
					"-"+stream.getRangeEnd()+
					"/"+stream.getFileSize());
		}
		return builder.body(stream.getResponse());
	}
	
	@PostMapping(path=UPLOAD_NEW_VIDEO_FROM_USER, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadVideo(VideoModelAdd video){
		videoService.saveNewVideo(video);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping(GET_ALL_GRADE_VIDEO_FROM_USER)
	public ResponseEntity<List<VideoDto>> getAllGradeVideo(
			@RequestParam("principal") String principal){ //TODO
		
		return ResponseEntity.ok(videoService.getAllGradeVideoFromUser(principal));
		
	}
	
	@DeleteMapping(DELETE_VIDEO_FROM_USER_BY_VIDEO_ID)
	public ResponseEntity<?> deleteVideoById(@PathVariable("videoId") String videoId, //TODO change string principal to Pricipal security
			@RequestParam("principal") String principal){
		videoService.deleteVideoById(videoId, principal);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@PutMapping(UPDATE_VIDEO_FROM_USER)
	public ResponseEntity<?> updateVideo(VideoModelUpdate videoModel,
			@RequestParam("principal") String principal){ 						//TODO
		videoService.updateVideo(videoModel, principal);
		return new ResponseEntity<>("Video is update",HttpStatus.ACCEPTED);
	}
	
	
	
	
	
}
