package com.daylight.devleague.services.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.daylight.devleague.dto.users.login.LoggedUserDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

	private static final String AUTHORITIES = "authorities";
	private static final String SOFTTEK_JWT = "softtekJWT";
	
	@Value("security.secret.key")
	private String secretKey;

	public String getToken(String username, LoggedUserDTO loggedUser) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		String token = Jwts.builder().setId(SOFTTEK_JWT)
				.setSubject(username)
				.claim(AUTHORITIES, grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.claim("user-details", loggedUser)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 60000000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
		return token;
	}

	public Claims validateToken(String token) {
		return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
	}
	
}
