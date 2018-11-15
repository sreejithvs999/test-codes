package com.n26.api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.n26.api.bean.TransactionBean;
import com.n26.api.service.TransactionsStore;
import com.n26.api.utils.TransactionUtils;

public class TransactionsStoreTest {

	@Test
	public void testAddNewTransaction() {

		TransactionsStore txStore = new TransactionsStore();

		TransactionBean txBean = TransactionsTestHelper.getTxBean(10.0, TransactionUtils.getCurrentTimestamp());
		txStore.addNewTransaction(txBean);

		@SuppressWarnings("unchecked")
		Queue<TransactionBean> transactions = (Queue<TransactionBean>) ReflectionTestUtils.getField(txStore,
				"transactions");

		Assert.assertTrue("transactions should contain one element.", transactions.size() == 1);
		Assert.assertTrue("transaction object's amount should be 10.0.",
				transactions.element().getAmount().equals(new BigDecimal(10.0)));

	}

	@Test
	public void testGetNonExpiredTransactions() {

		TransactionsStore txStore = TransactionsTestHelper.getTxStoreWithDummyTransactions();

		List<TransactionBean> transactions = txStore.getNonExpiredTransactions();

		Assert.assertTrue("non expired transactions should contain two elements.", transactions.size() == 2);
		Assert.assertFalse("transaction object's amount should not be 40.0",
				transactions.get(0).getAmount().equals(new BigDecimal(40.0)));
		Assert.assertFalse("transaction object's amount should not be 40.0",
				transactions.get(1).getAmount().equals(new BigDecimal(40.0)));
	}

	@Test
	public void testRemoveAll() {

		TransactionsStore txStore = TransactionsTestHelper.getTxStoreWithDummyTransactions();

		txStore.removeAll();
		@SuppressWarnings("unchecked")
		Queue<TransactionBean> transactions = (Queue<TransactionBean>) ReflectionTestUtils.getField(txStore,
				"transactions");

		Assert.assertTrue("transactions should not contain any element.", transactions.size() == 0);
	}

	@Test
	public void testRemoveAllExpired() {

		TransactionsStore txStore = TransactionsTestHelper.getTxStoreWithDummyTransactions();

		txStore.removeAllExpired();
		@SuppressWarnings("unchecked")
		Queue<TransactionBean> transactions = (Queue<TransactionBean>) ReflectionTestUtils.getField(txStore,
				"transactions");

		Assert.assertTrue("transactions should contain two elements.", transactions.size() == 2);
		Assert.assertFalse("transaction object's amount should not be 40.0",
				transactions.poll().getAmount().equals(new BigDecimal(40.0)));
		Assert.assertFalse("transaction object's amount should not be 40.0",
				transactions.poll().getAmount().equals(new BigDecimal(40.0)));
	}

}
