package com.faketube.store.entity.user;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="block_user")
public class BlockUserEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long blockId;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private UserEntity blockUser;
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	private UserEntity authorChannel;
	private LocalDateTime blockedAt = LocalDateTime.now();
	private LocalDateTime blockedTime;
	
	
	public BlockUserEntity() {
		super();
	}
	public BlockUserEntity(UserEntity blockUser, UserEntity authorChannel, LocalDateTime blockedTime) {
		super();
		this.blockUser = blockUser;
		this.authorChannel = authorChannel;
		this.blockedTime = blockedTime;
	}
	public Long getBlockId() {
		return blockId;
	}
	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}
	public UserEntity getBlockUser() {
		return blockUser;
	}
	public void setBlockUser(UserEntity blockUser) {
		this.blockUser = blockUser;
	}
	public UserEntity getAuthorChannel() {
		return authorChannel;
	}
	public void setAuthorChannel(UserEntity authorChannel) {
		this.authorChannel = authorChannel;
	}
	public LocalDateTime getBlockedAt() {
		return blockedAt;
	}
	public void setBlockedAt(LocalDateTime blockedAt) {
		this.blockedAt = blockedAt;
	}
	public LocalDateTime getBlockedTime() {
		return blockedTime;
	}
	public void setBlockedTime(LocalDateTime blockedTime) {
		this.blockedTime = blockedTime;
	}
	
	
	
	
}
