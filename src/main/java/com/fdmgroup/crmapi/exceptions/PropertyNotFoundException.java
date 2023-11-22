package com.fdmgroup.crmapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PropertyNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 6307668402226167065L;

	public PropertyNotFoundException(String msg) {
		super(msg);
	}
}
