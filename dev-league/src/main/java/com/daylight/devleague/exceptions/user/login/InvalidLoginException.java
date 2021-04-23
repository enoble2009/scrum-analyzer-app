package com.daylight.devleague.exceptions.user.login;

import com.daylight.devleague.exceptions.BusinessException;

public class InvalidLoginException extends BusinessException {

	private static final long serialVersionUID = -2103196187968478192L;

	public InvalidLoginException() {
		super("invalid_login", "Login was invalid");
	}

}
