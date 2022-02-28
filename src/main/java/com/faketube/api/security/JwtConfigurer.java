package com.faketube.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	
	private final JwtTokenFilter jwtFilter;
	
	
	@Autowired
	public JwtConfigurer(JwtTokenFilter jwtFilter) {
		super();
		this.jwtFilter = jwtFilter;
	}


	@Override
	public void configure(HttpSecurity http) {
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
}