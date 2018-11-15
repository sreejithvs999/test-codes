package com.n26.api.bean;

import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.constraints.NotNull;

/**
 * TransactionBean contain amount and timestamp of transaction.
 * 
 * @author Sreejith VS
 *
 */
public class TransactionBean {

	@NotNull(message = "amount is null")
	private BigDecimal amount;

	@NotNull(message = "timestamp is null")
	private Instant timestamp;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Tx [ amount: " + amount + ", timestamp: " + timestamp + "]";
	}
}
