package com.micro.payment_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Global exception handler for handling exceptions in the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
     * Handles global exceptions.
     *
     * @param ex      the exception
     * @param request the web request
     * @return the error response
     */	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request){
		String requestDescription =  request.getDescription(false);
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				ex.getMessage(), 
				requestDescription);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
     * Handles runtime exceptions.
     *
     * @param ex      the runtime exception
     * @param request the web request
     * @return the error response
     */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException ex, WebRequest request){
		String requestDescription =  request.getDescription(false);
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(), 
				ex.getMessage(), 
				requestDescription);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	/**
     * Handles EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the error response
     */
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex){
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(), 
				ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	/**
     * Handles EntityDeletionException.
     *
     * @param ex the EntityDeletionException
     * @return the error response
     */
	@ExceptionHandler(EntityDeletionException.class)
	public ResponseEntity<ErrorResponse> handleEntityDeletionException(EntityDeletionException ex){
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(), 
				ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	/**
     * Handles JsonProcessingException.
     *
     * @param ex      the JsonProcessingException
     * @param request the web request
     * @return the error response
     */
	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<ErrorResponse> handleJsonProcessingException(JsonProcessingException ex, WebRequest request){
		String requestDescription =  request.getDescription(false);
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(), 
				"Error processing JSON: " + ex.getOriginalMessage(), 
				requestDescription);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
