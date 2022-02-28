package com.faketube.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.faketube.api.exception.JwtAuthenticationException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

	private final JwtTokenProvider tokenProvider;

	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		super();
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String token = tokenProvider.resolveToken((HttpServletRequest) request);
		
		try {
		if(token!=null&&tokenProvider.validateToken(token)) {
			Authentication auth = tokenProvider.getAuthentication(token);
			if(auth!=null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		} catch(JwtAuthenticationException ex) {
			SecurityContextHolder.clearContext();
			((HttpServletResponse) response).sendError(ex.getHttpStatus().value());
			ex.printStackTrace();
		}
		chain.doFilter(request, response);
	}
	
	
	
}
