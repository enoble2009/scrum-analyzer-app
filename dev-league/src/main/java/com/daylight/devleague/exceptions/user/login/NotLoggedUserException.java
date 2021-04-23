package com.daylight.devleague.exceptions.user.login;

import com.daylight.devleague.exceptions.BusinessException;

public class NotLoggedUserException extends BusinessException {

	private static final long serialVersionUID = -2103196187968478192L;

	public NotLoggedUserException() {
		super("not_logged_user", "There was not logged user");
	}
	
}
