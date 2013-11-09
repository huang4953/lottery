/**
 * Title: ReportSaleCount.java
 * @Package com.success.lottery.report.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 下午12:45:45
 * @version V1.0
 */
package com.success.lottery.report.domain;

import java.io.Serializable;

/**
 * com.success.lottery.report.domain
 * ReportSaleCount.java
 * ReportSaleCount
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 下午12:45:45
 * 
 */

public class ReportSaleCount implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 3161521652912609329L;
	private int lotteryId;
	private int planSource;
	private long betAmount;
	private int betType;
	private int winStatus;
	private long pretaxprize;
	private int failTicketNum;
	private long aftTaxPrize;
	private long taxPrize;
	private long tiChengPrize;
	private long commPrize;
	private long smallPrize;
	private long bigPrize;
	
	public long getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(long betAmount) {
		this.betAmount = betAmount;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public int getPlanSource() {
		return planSource;
	}
	public void setPlanSource(int planSource) {
		this.planSource = planSource;
	}
	public int getBetType() {
		return betType;
	}
	public void setBetType(int betType) {
		this.betType = betType;
	}
	public long getPretaxprize() {
		return pretaxprize;
	}
	public void setPretaxprize(long pretaxprize) {
		this.pretaxprize = pretaxprize;
	}
	public int getWinStatus() {
		return winStatus;
	}
	public void setWinStatus(int winStatus) {
		this.winStatus = winStatus;
	}
	public int getFailTicketNum() {
		return failTicketNum;
	}
	public void setFailTicketNum(int failTicketNum) {
		this.failTicketNum = failTicketNum;
	}
	public long getAftTaxPrize() {
		return aftTaxPrize;
	}
	public void setAftTaxPrize(long aftTaxPrize) {
		this.aftTaxPrize = aftTaxPrize;
	}
	public long getCommPrize() {
		return commPrize;
	}
	public void setCommPrize(long commPrize) {
		this.commPrize = commPrize;
	}
	public long getTaxPrize() {
		return taxPrize;
	}
	public void setTaxPrize(long taxPrize) {
		this.taxPrize = taxPrize;
	}
	public long getTiChengPrize() {
		return tiChengPrize;
	}
	public void setTiChengPrize(long tiChengPrize) {
		this.tiChengPrize = tiChengPrize;
	}
	
	public long getSmallPrize() {
		return smallPrize;
	}
	public void setSmallPrize(long smallPrize) {
		this.smallPrize = smallPrize;
	}
	public long getBigPrize() {
		return bigPrize;
	}
	public void setBigPrize(long bigPrize) {
		this.bigPrize = bigPrize;
	}

}
