package com.n26.api.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.n26.api.bean.StatisticsBean;
import com.n26.api.utils.TransactionUtils;
import com.n26.api.utils.TransactionsConstants;

public class StatisticsServiceTest {

	@Test
	public void testGetStatistics() {

		StatisticsService statService = getTransactionService();
		TransactionsStore txStore = (TransactionsStore) ReflectionTestUtils.getField(statService, "txStore");
		txStore.addNewTransaction(TransactionsTestHelper.getTxBean(11.105, TransactionUtils.getCurrentTimestamp()));
		txStore.addNewTransaction(TransactionsTestHelper.getTxBean(9.567, TransactionUtils.getCurrentTimestamp()));
		// expired tx in store
		txStore.addNewTransaction(TransactionsTestHelper.getTxBean(10.00,
				TransactionUtils.getCurrentTimestamp().minusSeconds(TransactionsConstants.TX_EXPIRY_SECONDS + 1)));
		txStore.addNewTransaction(TransactionsTestHelper.getTxBean(5.00, TransactionUtils.getCurrentTimestamp()));
		StatisticsBean statsBean = statService.getStatistics();
		Assert.assertEquals("sum should be 25.67", statsBean.getSum(),
				new BigDecimal(25.67).setScale(2, BigDecimal.ROUND_HALF_UP));
		Assert.assertEquals("avg should be 8.56", statsBean.getAvg(),
				new BigDecimal(8.56).setScale(2, BigDecimal.ROUND_HALF_UP));
		Assert.assertEquals("max should be 11.11", statsBean.getMax(),
				new BigDecimal(11.11).setScale(2, BigDecimal.ROUND_HALF_UP));
		Assert.assertEquals("min should be 5.00", statsBean.getMin(),
				new BigDecimal(5.00).setScale(2, BigDecimal.ROUND_HALF_UP));
		Assert.assertEquals("count should be 3", statsBean.getCount(), 3);
	}

	private StatisticsService getTransactionService() {
		StatisticsService statService = new TransactionServiceImpl();
		ReflectionTestUtils.setField(statService, "txStore", new TransactionsStore());
		return statService;
	}
}
