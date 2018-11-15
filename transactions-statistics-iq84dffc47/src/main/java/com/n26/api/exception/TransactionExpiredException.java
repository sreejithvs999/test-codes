package com.n26.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT )
public class TransactionExpiredException extends TransactionsApiException {

	private static final long serialVersionUID = -1983500068452794651L;

	public TransactionExpiredException(String message) {
		super(message);
	}

}
