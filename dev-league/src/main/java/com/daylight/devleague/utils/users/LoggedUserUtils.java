package com.daylight.devleague.utils.users;

import org.springframework.security.core.context.SecurityContextHolder;

import com.daylight.devleague.dto.users.login.LoggedUserDTO;
import com.daylight.devleague.exceptions.user.login.NotLoggedUserException;

public class LoggedUserUtils {

	public static LoggedUserDTO getLoggedUser() throws NotLoggedUserException {
		validateIfUserIsLoggedIn();
		return (LoggedUserDTO) getUserDataFromContext();
	}

	private static Object getUserDataFromContext() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private static void validateIfUserIsLoggedIn() throws NotLoggedUserException {
		boolean userIsLoggedIn = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
		if (!userIsLoggedIn) {
			throw new NotLoggedUserException();
		}
	}

}
