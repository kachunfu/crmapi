package com.fdmgroup.crmapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OfferNotFoundException extends RuntimeException{


	private static final long serialVersionUID = -3923453850171437582L;

	public OfferNotFoundException(String msg) {
		super(msg);
	}
}
