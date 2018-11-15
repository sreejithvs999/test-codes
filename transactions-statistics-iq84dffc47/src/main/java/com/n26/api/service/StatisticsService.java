package com.n26.api.service;

import com.n26.api.bean.StatisticsBean;

/**
 * interface for statistics related services
 * 
 * @author Sreejith VS
 *
 */
public interface StatisticsService {

	/**
	 * get statistics about non expired transactions.
	 * 
	 * @return {@link StatisticsBean}
	 */
	StatisticsBean getStatistics();

}
