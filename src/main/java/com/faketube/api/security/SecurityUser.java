package com.faketube.api.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.faketube.store.entity.user.UserEntity;

public class SecurityUser implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private final String username;
	private final String password;
	private final List<SimpleGrantedAuthority> authorities;
	private final boolean isActive;
	
	
	public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.isActive = isActive;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}
	
	public static UserDetails fromUser(UserEntity user) {
		
		return new User(
				user.getEmail(),
				user.getPassword(),
				user.isActive(),
				user.isActive(),
				user.isActive(),
				user.isActive(),
				user.getRole().getAuthorities());
	}

}
