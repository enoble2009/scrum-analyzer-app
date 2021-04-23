package com.daylight.devleague.exceptions.login;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.daylight.devleague.exceptions.user.login.InvalidLoginException;

public class InvalidLoginExceptionTest {

	private InvalidLoginException exception;
	
	@Test
	public void should_assign_values_when_creating_exception() {
		String errorCode = "invalid_login";
		String errorMessage = "Login was invalid";
		
		exception = new InvalidLoginException();
		
		assertEquals(errorCode, exception.getErrorCode());
		assertEquals(errorMessage, exception.getErrorMessage());
	}
	
}
