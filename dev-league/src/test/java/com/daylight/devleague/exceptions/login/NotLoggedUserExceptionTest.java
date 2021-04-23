package com.daylight.devleague.exceptions.login;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.daylight.devleague.exceptions.user.login.NotLoggedUserException;

public class NotLoggedUserExceptionTest {

	private NotLoggedUserException exception;
	
	@Test
	public void should_assign_values_when_creating_exception() {
		String errorCode = "not_logged_user";
		String errorMessage = "There was not logged user";
		
		exception = new NotLoggedUserException();
		
		assertEquals(errorCode, exception.getErrorCode());
		assertEquals(errorMessage, exception.getErrorMessage());
	}
	
}
