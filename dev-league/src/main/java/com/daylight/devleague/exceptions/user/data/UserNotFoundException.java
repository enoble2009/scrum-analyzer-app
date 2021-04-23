package com.daylight.devleague.exceptions.user.data;

import com.daylight.devleague.exceptions.BusinessException;

public class UserNotFoundException extends BusinessException {

	private static final long serialVersionUID = -4086701727291872413L;

	public UserNotFoundException() {
		super("user_not_found", "The user requested was not found");
	}

}
