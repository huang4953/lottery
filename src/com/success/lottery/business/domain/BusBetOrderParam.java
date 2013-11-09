package com.success.lottery.business.domain;

/**
 * 
 * com.success.lottery.business.domain
 * BusBetOrderDomain.java
 * BusBetOrderDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-24 下午03:17:16
 *
 */
public class BusBetOrderParam implements java.io.Serializable{

	private static final long	serialVersionUID	= 1L;
	
	
	private String accountId;
	private String userName;
	private String lotteryId; 
	private String termNo;
	private String planSource;
	private String betStatus;
	private String winStatus;
	private String begin_time;
	private String end_time;
	private int startPageNumber;
	private int endPageNumber;
	
	
	
	public String getAccountId() {
		return this.accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getBegin_time() {
		return this.begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getBetStatus() {
		return this.betStatus;
	}
	public void setBetStatus(String betStatus) {
		this.betStatus = betStatus;
	}
	
	public String getEnd_time() {
		return this.end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getLotteryId() {
		return this.lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getTermNo() {
		return this.termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWinStatus() {
		return this.winStatus;
	}
	public void setWinStatus(String winStatus) {
		this.winStatus = winStatus;
	}
	public int getEndPageNumber() {
		return endPageNumber;
	}
	public void setEndPageNumber(int endPageNumber) {
		this.endPageNumber = endPageNumber;
	}
	public int getStartPageNumber() {
		return startPageNumber;
	}
	public void setStartPageNumber(int startPageNumber) {
		this.startPageNumber = startPageNumber;
	}
	public String getPlanSource() {
		return planSource;
	}
	public void setPlanSource(String planSource) {
		this.planSource = planSource;
	}
	
}
