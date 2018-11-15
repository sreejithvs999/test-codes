package com.n26.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class TransactionInFutureException extends TransactionsApiException {

	private static final long serialVersionUID = -1983500068452794651L;

	public TransactionInFutureException(String message) {
		super(message);
	}

}
