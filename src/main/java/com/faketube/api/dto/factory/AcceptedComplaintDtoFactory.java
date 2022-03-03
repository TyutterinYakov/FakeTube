package com.faketube.api.dto.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.faketube.api.dto.AcceptedComplaintDto;
import com.faketube.store.entity.video.AcceptedComplaint;

@Component
public class AcceptedComplaintDtoFactory {

	private final UserDtoFactory userDtoFactory;
	private final VideoDtoInfoFactory videoDtoInfoFactory;
	
	@Autowired
	public AcceptedComplaintDtoFactory(UserDtoFactory userDtoFactory, VideoDtoInfoFactory videoDtoInfoFactory) {
		super();
		this.userDtoFactory = userDtoFactory;
		this.videoDtoInfoFactory = videoDtoInfoFactory;
	}
	
	
	public AcceptedComplaintDto createAcceptedComplaintDto(AcceptedComplaint entity) {
		
		return new AcceptedComplaintDto(
				entity.getId(),
				entity.getReason(),
				videoDtoInfoFactory
					.createVideoDtoInfo(entity.getVideo()),
				entity.getCreatedAt(),
				userDtoFactory
					.createUserDto(entity.getAuthorVideo()),
				userDtoFactory
					.createUserDto(entity.getComplaintUser())
				);
	}
	
	public List<AcceptedComplaintDto> createListAcceptedCompalaintDto(List<AcceptedComplaint> entities){
		return entities.stream()
				.map(this::createAcceptedComplaintDto)
				.collect(Collectors.toList());
	}
	
	
	
	
	
	
}
