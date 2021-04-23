package com.daylight.devleague.handlers;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.daylight.devleague.dto.errors.ErrorDTO;

public class CommonExceptionsHandlerTest {

	private CommonExceptionsHandler handler;
	
	@BeforeEach
	public void before() {
		handler = new CommonExceptionsHandler();
	}
	
	@Test
	public void should_return_error_dto_when_missing_parameters_error_thrown() throws NoSuchMethodException, SecurityException {
		MissingServletRequestParameterException ex = new MissingServletRequestParameterException("lastName", "String");
		
		ErrorDTO errorDto = handler.handleMissingParameter(ex);
		Method method = CommonExceptionsHandler.class.getMethod("handleMissingParameter", MissingServletRequestParameterException.class);
		ExceptionHandler handler = method.getAnnotation(ExceptionHandler.class);
		
		assertEquals(MissingServletRequestParameterException.class, handler.value()[0]);
		assertEquals("request_parameter_missing", errorDto.getErrorCode());
		assertEquals("Parameter lastName is needed", errorDto.getErrorMessage());
	}

	@Test
	public void should_return_error_dto_when_uri_syntax_error_thrown() throws NoSuchMethodException, SecurityException {
		URISyntaxException ex = new URISyntaxException("", "");
		
		ErrorDTO errorDto = handler.handleURISyntax(ex);
		Method method = CommonExceptionsHandler.class.getMethod("handleURISyntax", URISyntaxException.class);
		ExceptionHandler handler = method.getAnnotation(ExceptionHandler.class);
		
		assertEquals(URISyntaxException.class, handler.value()[0]);
		assertEquals("unexpected_error", errorDto.getErrorCode());
		assertEquals(String.format("Error %s was thrown", ex.getClass().getName()), errorDto.getErrorMessage());
	}
	
}
