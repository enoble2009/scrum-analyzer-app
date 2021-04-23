package com.daylight.devleague.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -2642488011589484733L;
	
	private String errorCode;
	private String errorMessage;
	
	public BusinessException(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
