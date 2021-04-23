package com.daylight.devleague.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daylight.devleague.dto.errors.ErrorDTO;
import com.daylight.devleague.exceptions.user.login.InvalidLoginException;
import com.daylight.devleague.exceptions.user.login.NotLoggedUserException;

@RestControllerAdvice
public class UserExceptionsHandler {

	@ExceptionHandler(NotLoggedUserException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ErrorDTO handleNotLoggedUser(NotLoggedUserException ex) {
		return ErrorDTO.builder()
				.errorCode("user_not_logged_in")
				.errorMessage("User is not logged in")
				.build();
	}
	
	@ExceptionHandler(InvalidLoginException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ErrorDTO handleInvalidLogin(InvalidLoginException ex) {
		return ErrorDTO.builder()
				.errorCode("invalid_login")
				.errorMessage("Login was invalid")
				.build();
	}
	
}
