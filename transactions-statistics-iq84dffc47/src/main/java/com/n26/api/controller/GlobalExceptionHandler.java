package com.n26.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.api.exception.TransactionsApiException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Void> handleParamsError(MethodArgumentNotValidException methArgEx) {
		log.info("handle exception ", methArgEx);
		return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<Void> handleHttpMessageError(HttpMessageNotReadableException ex) {
		log.info("handle exception ", ex);

		if (ex.getCause() instanceof InvalidFormatException) {// root cause field value not parsable.
			return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = TransactionsApiException.class)
	public ResponseEntity<Void> handleTransactionsApiException(TransactionsApiException txEx) {
		return new ResponseEntity<Void>(txEx.getClass().getAnnotation(ResponseStatus.class).value());
	}
}
