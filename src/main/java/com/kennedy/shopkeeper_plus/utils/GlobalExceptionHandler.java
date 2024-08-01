package com.kennedy.shopkeeper_plus.utils;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDto> handleValidationExceptions(
			MethodArgumentNotValidException ex) {

		List<String> errors = ex.getBindingResult()
				                      .getFieldErrors()
				                      .stream()
				                      .map(error -> error.getField() + ": " + error.getDefaultMessage())
				                      .toList();

		ResponseDto response = new ResponseDto(ResponseStatus.fail, "Validation failed", errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}


	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseDto> handleResourceNotFoundException(
			ResourceNotFoundException ex) {
		ResponseDto response = new ResponseDto(
				ResponseStatus.fail, ex.getMessage(), null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ResponseDto> handleResourceAlreadyExistsException(
			ResourceAlreadyExistsException ex) {
		ResponseDto response = new ResponseDto(
				ResponseStatus.fail,
				ex.getMessage(),
				null);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDto> handleGeneralException(
			Exception ex) {

		logger.error(ex.getMessage());

		ResponseDto response = new ResponseDto(
				ResponseStatus.fail,
				"An unexpected error occurred:" + ex.getMessage(),
				null);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ResponseDto> handleAuthenticationException(AuthenticationException ex) {
		ResponseDto response = new ResponseDto(
				ResponseStatus.fail,
				"Authentication failed: " + ex.getMessage(),
				null);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}


	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<ResponseDto> handleAuthenticationFailedException(AuthenticationFailedException ex) {
		ResponseDto response = new ResponseDto(
				ResponseStatus.fail,
				ex.getMessage(),
				null);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(JwtGenerationException.class)
	public ResponseEntity<ResponseDto> handleJwtGenerationException(JwtGenerationException ex) {
		ResponseDto response = new ResponseDto(
				ResponseStatus.fail,
				ex.getMessage(),
				null);


		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ResponseDto> handleInsufficientQuantityException(IllegalStateException ex) {
		var response = new ResponseDto(
				ResponseStatus.fail,
				ex.getMessage(),
				null
		);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}

}
