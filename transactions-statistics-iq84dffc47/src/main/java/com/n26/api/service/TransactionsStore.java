package com.n26.api.service;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.n26.api.bean.TransactionBean;
import com.n26.api.utils.TransactionUtils;

@Component
public class TransactionsStore {

	private Queue<TransactionBean> transactions = new ConcurrentLinkedDeque<>();

	public void addNewTransaction(TransactionBean txBean) {
		transactions.add(txBean);
	}

	public List<TransactionBean> getNonExpiredTransactions() {

		Instant expiryLimit = TransactionUtils.getExpiryInstant();

		return transactions.stream().filter(txBean -> txBean.getTimestamp().isAfter(expiryLimit))
				.collect(Collectors.toList());
	}

	public void removeAll() {
		transactions.clear();
	}

	public void removeAllExpired() {
		Instant expiryLimit = TransactionUtils.getExpiryInstant();

		for (Iterator<TransactionBean> itr = transactions.iterator(); itr.hasNext();) {
			if (itr.next().getTimestamp().isBefore(expiryLimit)) {
				itr.remove();
			}
		}
	}
}
