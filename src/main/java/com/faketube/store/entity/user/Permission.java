package com.faketube.store.entity.user;

public enum Permission {

	USER_READ("user:read"),
	USER_WRITE("user:write");
	
	private final String permission;

	private Permission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
