package com.n26.api.service;

import java.math.BigDecimal;
import java.time.Instant;

import com.n26.api.bean.TransactionBean;
import com.n26.api.utils.TransactionUtils;
import com.n26.api.utils.TransactionsConstants;

public class TransactionsTestHelper {

	public static TransactionBean getTxBean(double amount, Instant timeStamp) {
		TransactionBean txBean = new TransactionBean();
		txBean.setAmount(new BigDecimal(amount));
		txBean.setTimestamp(timeStamp);
		return txBean;
	}

	public static TransactionsStore getTxStoreWithDummyTransactions() {

		TransactionsStore txStore = new TransactionsStore();

		// active transaction
		TransactionBean txBean = getTxBean(30.0, TransactionUtils.getCurrentTimestamp());
		txStore.addNewTransaction(txBean);

		// expired trans
		txBean = getTxBean(40.0,
				TransactionUtils.getCurrentTimestamp().minusSeconds(TransactionsConstants.TX_EXPIRY_SECONDS + 1));
		txStore.addNewTransaction(txBean);

		// active trans
		txBean = getTxBean(50.0,
				TransactionUtils.getCurrentTimestamp().minusSeconds(TransactionsConstants.TX_EXPIRY_SECONDS - 1));
		txStore.addNewTransaction(txBean);

		return txStore;
	}
}
