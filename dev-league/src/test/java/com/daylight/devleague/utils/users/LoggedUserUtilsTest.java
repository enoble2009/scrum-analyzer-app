package com.daylight.devleague.utils.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.daylight.devleague.dto.users.login.LoggedUserDTO;
import com.daylight.devleague.exceptions.user.login.NotLoggedUserException;

public class LoggedUserUtilsTest {
	
	@Test
	public void should_instantiate_when_create_object() {
		LoggedUserUtils utils = new LoggedUserUtils();
		assertNotNull(utils);
	}

	@Test
	public void should_return_logged_user_when_authentication_principal_exists() {
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		LoggedUserDTO principal = new LoggedUserDTO();

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(true);
		when(authentication.getPrincipal()).thenReturn(principal);
		
		LoggedUserDTO responseDto = LoggedUserUtils.getLoggedUser();
		
		assertEquals(responseDto, principal);
	}
	
	@Test
	public void should_throw_logged_user_when_user_is_not_authenticated() {
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.isAuthenticated()).thenReturn(false);
		
		try {
			LoggedUserUtils.getLoggedUser();
			Assert.fail();
		} catch (Exception e) {
			boolean isInstance = e instanceof NotLoggedUserException;
			Assert.assertTrue(isInstance);
		}
	}
	
}
