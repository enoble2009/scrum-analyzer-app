package com.daylight.devleague.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.daylight.devleague.dto.users.login.LoggedUserDTO;
import com.daylight.devleague.services.jwt.JwtTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	@Autowired
	private JwtTokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		try {
			if (existsJWTToken(request, response)) {
				String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
				Claims claims = tokenService.validateToken(jwtToken);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private void setUpSpringAuthentication(Claims claims) {
		List<String> authorities = (List<String>) claims.get("authorities");
		LoggedUserDTO loggedUser = new LoggedUserDTO((HashMap<String, Object>) claims.get("user-details"));

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loggedUser, null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private boolean existsJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}

}
