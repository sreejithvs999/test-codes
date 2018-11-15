package com.n26.api.utils;

import java.time.Clock;
import java.time.Instant;

import com.n26.api.bean.TransactionBean;

public class TransactionUtils {

	public static boolean isOlderTransaction(TransactionBean txBean) {
		return txBean.getTimestamp().isBefore(getExpiryInstant());
	}

	public static Instant getExpiryInstant() {
		return getCurrentTimestamp().minusSeconds(TransactionsConstants.TX_EXPIRY_SECONDS);
	}

	public static boolean isFutureTimestamp(Instant timestamp) {
		return getCurrentTimestamp().isBefore(timestamp);
	}

	public static Instant getCurrentTimestamp() {
		return Instant.now(Clock.systemUTC());
	}
}
