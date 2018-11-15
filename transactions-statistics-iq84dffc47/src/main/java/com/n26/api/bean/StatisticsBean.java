package com.n26.api.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * StatisticsBean contain sum, avg, max, min and count of non expired
 * transaction at a particular time instant.
 * 
 * @author Sreejith VS
 *
 */
public class StatisticsBean {

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal sum;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal avg;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal max;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal min;

	private long count;

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public BigDecimal getAvg() {
		return avg;
	}

	public void setAvg(BigDecimal avg) {
		this.avg = avg;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Statistics: [sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count=" + count + "]";
	}
}
