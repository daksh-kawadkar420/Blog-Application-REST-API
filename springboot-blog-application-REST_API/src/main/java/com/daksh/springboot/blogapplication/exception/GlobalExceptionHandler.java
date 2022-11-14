package com.daksh.springboot.blogapplication.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.daksh.springboot.blogapplication.payload.ErrorDetails;

@ControllerAdvice //WE USE THIS ANNOTATION TO HANDLE EXCEPTIONS GLOBALLY
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	//HANDLE SPECIFIC EXEPTIONS
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
		ErrorDetails error = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception, WebRequest request) {
		ErrorDetails error = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}
	
	//HANDLE GLOBAL EXCEPTIONS
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleExceptions(Exception exception, WebRequest request) {
		ErrorDetails error = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
	}
	
}
