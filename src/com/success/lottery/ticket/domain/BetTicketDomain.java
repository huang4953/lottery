/**
 * @Title: BetTicketDomain.java
 * @Package com.success.lottery.order.domain
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-13 上午09:31:39
 * @version V1.0
 */
package com.success.lottery.ticket.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * com.success.lottery.order.domain
 * BetTicketDomain.java
 * BetTicketDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-13 上午09:31:39
 * 
 */

public class BetTicketDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -5994774313166825341L;

	private String ticketSequence;

	private String orderId;

	private Integer lotteryId;

	private String betTerm;

	private Integer playType;

	private Integer betType;

	private Integer betMultiple;

	private String betCode;

	private Integer betCount;

	private long betAmount;

	private Timestamp ticketTime;

	private Timestamp lastTicketTime;

	private String areaCode;

	private String printStation;

	private Integer ticketStatus = 0;

	private String ticketId;

	private String printerId;

	private String printResult;

	private Timestamp printTime;

	private String ticketData;

	private String ticketDataMD;

	private String ticketPassword;

	private long preTaxPrize = 0;

	private Integer prizeResult = 0;

	private Integer saveStatus = 0;

	private String reserve;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getBetCount() {
		return betCount;
	}

	public void setBetCount(Integer betCount) {
		this.betCount = betCount;
	}

	public Integer getBetMultiple() {
		return betMultiple;
	}

	public void setBetMultiple(Integer betMultiple) {
		this.betMultiple = betMultiple;
	}

	public String getBetTerm() {
		return betTerm;
	}

	public void setBetTerm(String betTerm) {
		this.betTerm = betTerm;
	}

	public Integer getBetType() {
		return betType;
	}

	public void setBetType(Integer betType) {
		this.betType = betType;
	}

	public Timestamp getLastTicketTime() {
		return lastTicketTime;
	}

	public void setLastTicketTime(Timestamp lastTicketTime) {
		this.lastTicketTime = lastTicketTime;
	}

	public Integer getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	public String getPrinterId() {
		return printerId;
	}

	public void setPrinterId(String printerId) {
		this.printerId = printerId;
	}

	public String getPrintResult() {
		return printResult;
	}

	public void setPrintResult(String printResult) {
		this.printResult = printResult;
	}

	public String getPrintStation() {
		return printStation;
	}

	public void setPrintStation(String printStation) {
		this.printStation = printStation;
	}

	public Timestamp getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Timestamp printTime) {
		this.printTime = printTime;
	}

	public Integer getPrizeResult() {
		return prizeResult;
	}

	public void setPrizeResult(Integer prizeResult) {
		this.prizeResult = prizeResult;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public Integer getSaveStatus() {
		return saveStatus;
	}

	public void setSaveStatus(Integer saveStatus) {
		this.saveStatus = saveStatus;
	}

	public String getTicketData() {
		return ticketData;
	}

	public void setTicketData(String ticketData) {
		this.ticketData = ticketData;
	}

	public String getTicketDataMD() {
		return ticketDataMD;
	}

	public void setTicketDataMD(String ticketDataMD) {
		this.ticketDataMD = ticketDataMD;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketPassword() {
		return ticketPassword;
	}

	public void setTicketPassword(String ticketPassword) {
		this.ticketPassword = ticketPassword;
	}

	public String getTicketSequence() {
		return ticketSequence;
	}

	public void setTicketSequence(String ticketSequence) {
		this.ticketSequence = ticketSequence;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Timestamp getTicketTime() {
		return ticketTime;
	}

	public void setTicketTime(Timestamp ticketTime) {
		this.ticketTime = ticketTime;
	}

	public long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(long betAmount) {
		this.betAmount = betAmount;
	}

	public long getPreTaxPrize() {
		return preTaxPrize;
	}

	public void setPreTaxPrize(long preTaxPrize) {
		this.preTaxPrize = preTaxPrize;
	}

	public String getBetCode() {
		return betCode;
	}

	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}   

	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(o instanceof BetTicketDomain){
			return this.getTicketSequence().equals(((BetTicketDomain)o).getTicketSequence());
		}
		return false;
	}

	public int hashcode(){
		int result = 17;
		result = 37 * result + this.ticketSequence.hashCode();
		return result;
	}

}
