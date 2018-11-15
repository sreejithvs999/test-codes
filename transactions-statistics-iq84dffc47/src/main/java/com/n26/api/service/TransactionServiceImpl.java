package com.n26.api.service;

import java.math.BigDecimal;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.n26.api.bean.StatisticsBean;
import com.n26.api.bean.TransactionBean;
import com.n26.api.exception.TransactionExpiredException;
import com.n26.api.exception.TransactionInFutureException;
import com.n26.api.exception.TransactionsApiException;
import com.n26.api.utils.TransactionUtils;

@Service
public class TransactionServiceImpl implements TransactionService, StatisticsService {

	private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private TransactionsStore txStore;

	@Override
	public void createTransaction(TransactionBean bean) throws TransactionsApiException {

		if (TransactionUtils.isOlderTransaction(bean)) {
			throw new TransactionExpiredException("Transaction is expired.");
		}

		if (TransactionUtils.isFutureTimestamp(bean.getTimestamp())) {
			throw new TransactionInFutureException("Transaction is with future timestamp.");
		}

		txStore.addNewTransaction(bean);
		log.info("new transaction added to list: {}", bean);
	}

	@Override
	public StatisticsBean getStatistics() {

		StatisticsBean statistics = new StatisticsBean();
		BigDecimal sum, min, max, avg;
		long count = 0;

		log.info("getting statics of transactions");

		Iterator<TransactionBean> transIterator = txStore.getNonExpiredTransactions().iterator();

		if (transIterator.hasNext()) {

			TransactionBean txBean = transIterator.next();
			sum = min = max = avg = txBean.getAmount();
			count++;

			while (transIterator.hasNext()) {
				txBean = transIterator.next();
				sum = sum.add(txBean.getAmount());
				max = max.max(txBean.getAmount());
				min = min.min(txBean.getAmount());
				count++;
			}
		} else {

			if (log.isDebugEnabled()) {
				log.debug("no transactions eligible for calculating statistics.");
			}
			sum = min = max = avg = BigDecimal.ZERO;
		}

		if (count > 1) {
			avg = sum.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP);
		}
		statistics.setMax(roundedValue(max));
		statistics.setMin(roundedValue(min));
		statistics.setAvg(roundedValue(avg));
		statistics.setSum(roundedValue(sum));
		statistics.setCount(count);

		if (log.isDebugEnabled()) {
			log.debug("calculated statistics of transactions :{}.", statistics);
		}
		return statistics;
	}

	private BigDecimal roundedValue(BigDecimal bd) {
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public void deleteAllTransactions() {
		txStore.removeAll();
	}

	/**
	 * scheduled method to remove transactions which are not necessary for
	 * calculations.
	 */
	@Scheduled(fixedDelay = 10000)
	public void clearExpiredTransactions() {
		log.info("Scheduled :: clearing expired transactions.");
		txStore.removeAllExpired();
	}

}
