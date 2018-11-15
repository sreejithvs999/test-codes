package com.n26.api.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.n26.api.bean.TransactionBean;
import com.n26.api.exception.TransactionExpiredException;
import com.n26.api.exception.TransactionInFutureException;
import com.n26.api.exception.TransactionsApiException;
import com.n26.api.utils.TransactionUtils;
import com.n26.api.utils.TransactionsConstants;

public class TransactionServiceTest {

	@Test
	public void testDeleteAllTransactions() throws TransactionsApiException {
		TransactionService txService = getTransactionService();

		TransactionBean txBean = TransactionsTestHelper.getTxBean(100.05, TransactionUtils.getCurrentTimestamp());
		txService.createTransaction(txBean);
		txService.deleteAllTransactions();
		TransactionsStore txStore = (TransactionsStore) ReflectionTestUtils.getField(txService, "txStore");
		Assert.assertTrue("transactions should be empty", txStore.getNonExpiredTransactions().isEmpty());
	}

	@Test
	public void testCreateTransaction() throws TransactionsApiException {
		TransactionService txService = getTransactionService();

		TransactionBean txBean = TransactionsTestHelper.getTxBean(100.05, TransactionUtils.getCurrentTimestamp());
		txService.createTransaction(txBean);
		TransactionsStore txStore = (TransactionsStore) ReflectionTestUtils.getField(txService, "txStore");
		Assert.assertTrue("transaction should be in the store", txStore.getNonExpiredTransactions().contains(txBean));
	}

	@Test(expected = TransactionExpiredException.class)
	public void testCreateTransactionWithExpiredTs() throws TransactionsApiException {
		TransactionService txService = getTransactionService();

		TransactionBean txBean = TransactionsTestHelper.getTxBean(100.05,
				TransactionUtils.getCurrentTimestamp().minusSeconds(TransactionsConstants.TX_EXPIRY_SECONDS + 1));
		txService.createTransaction(txBean);
		TransactionsStore txStore = (TransactionsStore) ReflectionTestUtils.getField(txService, "txStore");
		Assert.assertTrue("transaction should be in the store", txStore.getNonExpiredTransactions().contains(txBean));
	}

	@Test(expected = TransactionInFutureException.class)
	public void testCreateTransactionWithFutureTs() throws TransactionsApiException {
		TransactionService txService = getTransactionService();

		TransactionBean txBean = TransactionsTestHelper.getTxBean(100.05,
				TransactionUtils.getCurrentTimestamp().plusSeconds(1));
		txService.createTransaction(txBean);
		TransactionsStore txStore = (TransactionsStore) ReflectionTestUtils.getField(txService, "txStore");
		Assert.assertTrue("transaction should be in the store", txStore.getNonExpiredTransactions().contains(txBean));
	}

	private TransactionService getTransactionService() {
		TransactionService txService = new TransactionServiceImpl();
		ReflectionTestUtils.setField(txService, "txStore", new TransactionsStore());
		return txService;
	}
}
