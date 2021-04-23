package com.daylight.devleague.handlers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.daylight.devleague.dto.errors.ErrorDTO;
import com.daylight.devleague.exceptions.user.login.InvalidLoginException;
import com.daylight.devleague.exceptions.user.login.NotLoggedUserException;

public class UserExceptionsHandlerTest {

	private UserExceptionsHandler handler;
	
	@BeforeEach
	public void before() {
		handler = new UserExceptionsHandler();
	}
	
	@Test
	public void should_return_error_dto_when_not_logged_user_error_thrown() throws NoSuchMethodException, SecurityException {
		NotLoggedUserException ex = new NotLoggedUserException();
		
		ErrorDTO errorDto = handler.handleNotLoggedUser(ex);
		Method method = UserExceptionsHandler.class.getMethod("handleNotLoggedUser", NotLoggedUserException.class);
		ExceptionHandler handler = method.getAnnotation(ExceptionHandler.class);
		
		assertEquals(NotLoggedUserException.class, handler.value()[0]);
		assertEquals("user_not_logged_in", errorDto.getErrorCode());
		assertEquals("User is not logged in", errorDto.getErrorMessage());
	}

	@Test
	public void should_return_error_dto_when_invalid_login_error_thrown() throws NoSuchMethodException, SecurityException {
		InvalidLoginException ex = new InvalidLoginException();
		
		ErrorDTO errorDto = handler.handleInvalidLogin(ex);
		Method method = UserExceptionsHandler.class.getMethod("handleInvalidLogin", InvalidLoginException.class);
		ExceptionHandler handler = method.getAnnotation(ExceptionHandler.class);
		
		assertEquals(InvalidLoginException.class, handler.value()[0]);
		assertEquals("invalid_login", errorDto.getErrorCode());
		assertEquals("Login was invalid", errorDto.getErrorMessage());
	}
	
}
