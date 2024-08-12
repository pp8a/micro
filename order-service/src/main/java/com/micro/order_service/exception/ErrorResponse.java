package com.micro.order_service.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an error response.
 */
@Getter
@Setter
public class ErrorResponse {
	/**
     * The HTTP status code.
     */
	private int status;
	/**
     * The error message.
     */
    private String message;
    /**
     * Additional details about the error.
     */
    private String details;
    
	public ErrorResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ErrorResponse(int status, String message, String details) {
		super();
		this.status = status;
		this.message = message;
		this.details = details;
	}
}
