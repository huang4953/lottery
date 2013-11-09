/**
 * Title: BusBetOrderCountDomain.java
 * @Package com.success.lottery.business.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-31 下午03:38:53
 * @version V1.0
 */
package com.success.lottery.business.domain;

import java.io.Serializable;

/**
 * com.success.lottery.business.domain
 * BusBetOrderCountDomain.java
 * BusBetOrderCountDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-31 下午03:38:53
 * 
 */

public class BusBetOrderCountDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -8783373880126869575L;
	private int totalNum;
	private long totalPrize;
	private long totalAftTaxPrize;
	private long totalTaxPrize;
	private long totalTiChengPrize;
	private long totalCommPrize;
	
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public long getTotalPrize() {
		return totalPrize;
	}
	public void setTotalPrize(long totalPrize) {
		this.totalPrize = totalPrize;
	}
	public long getTotalAftTaxPrize() {
		return totalAftTaxPrize;
	}
	public void setTotalAftTaxPrize(long totalAftTaxPrize) {
		this.totalAftTaxPrize = totalAftTaxPrize;
	}
	public long getTotalCommPrize() {
		return totalCommPrize;
	}
	public void setTotalCommPrize(long totalCommPrize) {
		this.totalCommPrize = totalCommPrize;
	}
	public long getTotalTaxPrize() {
		return totalTaxPrize;
	}
	public void setTotalTaxPrize(long totalTaxPrize) {
		this.totalTaxPrize = totalTaxPrize;
	}
	public long getTotalTiChengPrize() {
		return totalTiChengPrize;
	}
	public void setTotalTiChengPrize(long totalTiChengPrize) {
		this.totalTiChengPrize = totalTiChengPrize;
	}

}
