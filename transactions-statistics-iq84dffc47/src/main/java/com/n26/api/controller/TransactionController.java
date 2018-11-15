package com.n26.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.api.bean.StatisticsBean;
import com.n26.api.bean.TransactionBean;
import com.n26.api.exception.TransactionsApiException;
import com.n26.api.service.StatisticsService;
import com.n26.api.service.TransactionService;
import com.n26.api.utils.TransactionUtils;

/**
 * Controller for endpoint's of transactions related services.
 * 
 * @author Sreejith VS
 *
 */
@RestController
public class TransactionController {

	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	TransactionService transactionService;

	@Autowired
	StatisticsService statisticsService;

	/**
	 * 
	 * @param transaction
	 *            {@link TransactionBean}
	 * @return Returns: Empty body with one of the following:
	 * 
	 *         <br/>
	 *         201 – in case of success <br/>
	 *         204 – if the transaction is older than 60 seconds <br/>
	 *         400 – if the JSON is invalid <br/>
	 *         422 – if any of the fields are not parsable or the transaction date
	 *         is in the future
	 * 
	 */
	@PostMapping("/transactions")
	public ResponseEntity<Void> createTransaction(@Validated @RequestBody TransactionBean transaction)
			throws TransactionsApiException {

		log.info("create transaction end point called.");

		transactionService.createTransaction(transaction);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	/**
	 * Get statistics of transactions created since an expiration point. Includes
	 * sum, avg, max, min and count.
	 * 
	 * @return
	 */
	@GetMapping("/statistics")
	public StatisticsBean getStatistics() {

		log.info("get statistics end point called.");

		return statisticsService.getStatistics();
	}

	/**
	 * delete all transactions saved so far.
	 * 
	 * @return
	 */
	@DeleteMapping("/transactions")
	public ResponseEntity<Void> deleteAllTransactions() {

		log.info("delete transaction end point called.");

		transactionService.deleteAllTransactions();
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
