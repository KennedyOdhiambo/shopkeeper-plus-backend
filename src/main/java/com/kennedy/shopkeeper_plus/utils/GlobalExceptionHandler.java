package com.kennedy.shopkeeper_plus.utils;

import com.kennedy.shopkeeper_plus.dto.errors.ErrorResponseDTO;
import com.kennedy.shopkeeper_plus.dto.errors.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		List<FieldErrorDTO> fieldErrors = new ArrayList<>();

		ex.getBindingResult()
				.getFieldErrors()
				.forEach(error -> fieldErrors.add(new FieldErrorDTO(
								error.getField(),
								error.getDefaultMessage()
						))
				);

		ErrorResponseDTO errorResponse = new ErrorResponseDTO(
				"error",
				"Validation failed",
				fieldErrors
		);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
