package com.faketube.store.entity.stats;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.faketube.store.entity.user.UserEntity;

@Entity
@Table(name="subscribe")
public class SubscribeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long subscribeId;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private UserEntity subscribe;  //Кто подписан
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH)
	private UserEntity subscription; //На кого подписан
	
	public SubscribeEntity() {
		super();
	}
	public SubscribeEntity(UserEntity subscribe, UserEntity subscription) {
		super();
		this.subscribe = subscribe;
		this.subscription = subscription;
	}
	public Long getSubscribeId() {
		return subscribeId;
	}
	public void setSubscribeId(Long subscribeId) {
		this.subscribeId = subscribeId;
	}
	public UserEntity getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(UserEntity subscribe) {
		this.subscribe = subscribe;
	}
	public UserEntity getSubscription() {
		return subscription;
	}
	public void setSubscription(UserEntity subscription) {
		this.subscription = subscription;
	}
	
	
	
	
}
