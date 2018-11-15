package com.n26.api.service;

import com.n26.api.bean.TransactionBean;
import com.n26.api.exception.TransactionsApiException;

/**
 * interface for transaction related services.
 * 
 * @author Sreejith VS
 *
 */
public interface TransactionService {

	/**
	 * remove all transactions from the transaction store.
	 */
	void deleteAllTransactions();

	/**
	 * store the transaction object in the transaction store.
	 * 
	 * @param transaction
	 */
	void createTransaction(TransactionBean transaction) throws TransactionsApiException;

}
