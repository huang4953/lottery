/**
 * Title: ReportAccountCountDomain.java
 * @Package com.success.lottery.report.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 下午07:53:22
 * @version V1.0
 */
package com.success.lottery.report.domain;

import java.io.Serializable;

/**
 * com.success.lottery.report.domain
 * ReportAccountCountDomain.java
 * ReportAccountCountDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 下午07:53:22
 * 
 */

public class ReportAccountCountDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 11537580417705127L;
	
	private int transactionType; 
	private int sourceType; 
	private long amount;
	
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getSourceType() {
		return sourceType;
	}
	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}
	public int getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}
}
