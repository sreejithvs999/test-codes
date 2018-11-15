package com.n26.api.utils;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;

import com.n26.api.bean.TransactionBean;

public class TransactionUtilsTest {

	@Test
	public void testIsOlderTransaction() {

		TransactionBean txBean = new TransactionBean();
		txBean.setTimestamp(Instant.now().minusSeconds(59));
		boolean isOlderTx = TransactionUtils.isOlderTransaction(txBean);
		Assert.assertTrue("Tx should not be older", isOlderTx == false);
	}

	@Test
	public void testIsOlderTransactionForExpiredTx() {

		TransactionBean txBean = new TransactionBean();
		txBean.setTimestamp(Instant.now().minusSeconds(61));
		boolean isOlderTx = TransactionUtils.isOlderTransaction(txBean);
		Assert.assertTrue("Tx should not be older", isOlderTx == true);
	}

}
