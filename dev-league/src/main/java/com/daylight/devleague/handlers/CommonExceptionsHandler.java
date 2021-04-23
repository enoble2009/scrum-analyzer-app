package com.daylight.devleague.handlers;

import java.net.URISyntaxException;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daylight.devleague.dto.errors.ErrorDTO;

@RestControllerAdvice
public class CommonExceptionsHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ErrorDTO handleMissingParameter(MissingServletRequestParameterException ex) {
		return ErrorDTO.builder()
				.errorCode("request_parameter_missing")
				.errorMessage(String.format("Parameter %s is needed", ex.getParameterName()))
				.build();
	}
	
	@ExceptionHandler(URISyntaxException.class)
	public ErrorDTO handleURISyntax(URISyntaxException ex) {
		return ErrorDTO.builder()
				.errorCode("unexpected_error")
				.errorMessage(String.format("Error %s was thrown", ex.getClass().getName()))
				.build();
	}
	
}
