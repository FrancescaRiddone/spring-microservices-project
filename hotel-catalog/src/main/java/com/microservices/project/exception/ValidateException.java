package com.microservices.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8299104514884129208L;

	
}
