package com.microservices.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PasswordValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6506139935233329152L;

}
