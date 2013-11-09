/**
 * Title: TicketBet.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-12 上午10:42:38
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * TicketBet.java
 * TicketBet
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-12 上午10:42:38
 * 
 */

public class TicketBet {
	
	private String lotteryId;//采种id
	private String termNo;//彩期
	private String playType;//玩法
	private String betType;//投注方式
	private String ticketId;//票id
	private String betCode;//投注串
	private String betmultiple;//投注倍数
	private String betCount;//投注注数
	private String betAmount;//投注金额
	
	
	
	/**
	 *Title: 
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param playType
	 * @param betType
	 * @param ticketId
	 * @param betCode
	 * @param betmultiple
	 * @param betCount
	 * @param betAmount
	 */
	public TicketBet(String lotteryId, String termNo, String playType,
			String betType, String ticketId, String betCode,
			String betmultiple, String betCount, String betAmount) {
		super();
		this.lotteryId = lotteryId;
		this.termNo = termNo;
		this.playType = playType;
		this.betType = betType;
		this.ticketId = ticketId;
		this.betCode = betCode;
		this.betmultiple = betmultiple;
		this.betCount = betCount;
		this.betAmount = betAmount;
	}
	
	public String getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(String betAmount) {
		this.betAmount = betAmount;
	}
	public String getBetCode() {
		return betCode;
	}
	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}
	public String getBetCount() {
		return betCount;
	}
	public void setBetCount(String betCount) {
		this.betCount = betCount;
	}
	public String getBetmultiple() {
		return betmultiple;
	}
	public void setBetmultiple(String betmultiple) {
		this.betmultiple = betmultiple;
	}
	public String getBetType() {
		return betType;
	}
	public void setBetType(String betType) {
		this.betType = betType;
	}
	
	public String getPlayType() {
		return playType;
	}
	public void setPlayType(String playType) {
		this.playType = playType;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	
	

}
