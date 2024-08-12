package com.micro.order_service.exception;

/**
 * Exception thrown when an entity cannot be deleted.
 */
public class EntityDeletionException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public EntityDeletionException(String message) {
		super(message);
	}
}
