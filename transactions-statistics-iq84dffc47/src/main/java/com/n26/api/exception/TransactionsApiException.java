package com.n26.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "failed to process api request")
public class TransactionsApiException extends Exception {

	private static final long serialVersionUID = 1L;

	public TransactionsApiException(String message) {
		super(message);
	}
}
