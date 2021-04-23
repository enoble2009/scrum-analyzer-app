package com.daylight.devleague.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BusinessExceptionTest {

	private BusinessException exception;
	
	@SuppressWarnings("serial")
	@Test
	public void should_assign_values_when_creating_exception() {
		String errorCode = "errorCode";
		String errorMessage = "errorMessage";
		
		exception = new BusinessException(errorCode, errorMessage) { };
		
		assertEquals(errorCode, exception.getErrorCode());
		assertEquals(errorMessage, exception.getErrorMessage());
	}
	
}
