package com.devsuperior.bds03.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Classe que vai interceptar exceções e tratá-las
//Para isso, usa-se a annotation ControllerAdvice
@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError error = new ValidationError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Error Validation Exception");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		e.getBindingResult().getFieldErrors().forEach(x -> error.addError(x.getField(), x.getDefaultMessage()));
		
		return ResponseEntity.status(status.value()).body(error);
	}
}
