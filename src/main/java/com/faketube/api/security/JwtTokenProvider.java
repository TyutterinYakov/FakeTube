package com.faketube.api.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.faketube.api.exception.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	@Value("${jwt.header}")
	private String header;
	@Value("${jwt.secret}")
	private String secretkey;
	@Value("${jwt.expiration}")
	private Long validityInMilliseconds;
	
	private final UserDetailsService userDetailsService;
	
	
	
	public JwtTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}

	@PostConstruct
	protected void init() {
		secretkey = Base64.getEncoder().encodeToString(secretkey.getBytes());
	}
	
	public String createToken(String userName, String role) {
		Claims claims = Jwts.claims().setSubject(userName);
		claims.put("role", role);
		Date now = new Date();
		Date validity = new Date(now.getTime()+validityInMilliseconds * 1000);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, secretkey)
				.compact();
	}
	
	
	public boolean validateToken(String token) throws JwtAuthenticationException {
		try {
		Jws<Claims> claims = Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
		return !claims.getBody().getExpiration().before(new Date());
		} catch(JwtException | IllegalArgumentException ex) {
			throw new JwtAuthenticationException(token, HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public String getUserName(String token) {
		return  Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader(header);
	}
	
}
