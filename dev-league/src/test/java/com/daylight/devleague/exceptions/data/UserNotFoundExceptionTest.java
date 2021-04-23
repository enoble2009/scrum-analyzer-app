package com.daylight.devleague.exceptions.data;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import com.daylight.devleague.exceptions.user.data.UserNotFoundException;

public class UserNotFoundExceptionTest {

	private UserNotFoundException exception;
	
	@Test
	public void should_assign_values_when_creating_exception() {
		String errorCode = "user_not_found";
		String errorMessage = "The user requested was not found";
		
		exception = new UserNotFoundException();
		
		assertEquals(errorCode, exception.getErrorCode());
		assertEquals(errorMessage, exception.getErrorMessage());
	}
	
}
