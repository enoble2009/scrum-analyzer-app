package com.daylight.devleague.services.jwt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.daylight.devleague.daos.users.UserDao;
import com.daylight.devleague.domain.users.User;
import com.daylight.devleague.dto.users.login.LoggedUserDTO;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
public class JwtTokenServiceTest {

	@InjectMocks
	private JwtTokenService service;

	@Mock
	UserDao dao;

	@BeforeEach
	public void before() {
		service = new JwtTokenService();
		ReflectionTestUtils.setField(service, "secretKey", "SECRET_KEY");
	}

	@Test
	public void should_return_valid_token_when_getting_token_from_dto() {
		User u = new User();
		LoggedUserDTO loggedUser = new LoggedUserDTO(u);
		LinkedHashMap<Object, Object> desiredDto = new LinkedHashMap<>();
		desiredDto.put("id", null);
		desiredDto.put("email", null);
		desiredDto.put("fullName", "null null");
		desiredDto.put("image", null);
		desiredDto.put("nickName", null);
		
		String actualToken = service.getToken("enoble2009", loggedUser);
		
		assertNotNull(actualToken);
		
		Claims claims = service.validateToken(actualToken);
		
		LinkedHashMap<?, ?> actualDto = claims.get("user-details", LinkedHashMap.class);
		
		assertEquals(desiredDto, actualDto);
	}

}
