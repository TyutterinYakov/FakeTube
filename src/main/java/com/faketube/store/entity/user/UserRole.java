package com.faketube.store.entity.user;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
	
		CREATOR(Set.of(Permission.USER_READ)),
		ADMIN(Set.of(Permission.USER_READ, Permission.USER_WRITE));
		
		private final Set<Permission> permissions;

		private UserRole(Set<Permission> permissions) {
			this.permissions = permissions;
		}

		public Set<Permission> getPermissions() {
			return permissions;
		}
		
		public Set<SimpleGrantedAuthority> getAuthorities(){
			return getPermissions().stream()
					.map(perm -> new SimpleGrantedAuthority(perm.getPermission()))
					.collect(Collectors.toSet());
		}

}
