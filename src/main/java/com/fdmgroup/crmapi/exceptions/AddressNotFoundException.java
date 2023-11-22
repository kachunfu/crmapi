package com.fdmgroup.crmapi.exceptions;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AddressNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 2366103374327966257L;

	public AddressNotFoundException(String msg) {
		super(msg);
	}

	
	
	
}
