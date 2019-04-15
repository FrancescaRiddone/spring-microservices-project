package com.microservices.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
public class ResourceUnavailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4482682149438872449L;


}
