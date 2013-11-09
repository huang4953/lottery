/**
 * Title: TicketBet.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-12 ����10:42:38
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * TicketBet.java
 * TicketBet
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-12 ����10:42:38
 * 
 */

public class TicketBet {
	
	private String lotteryId;//����id
	private String termNo;//����
	private String playType;//�淨
	private String betType;//Ͷע��ʽ
	private String ticketId;//Ʊid
	private String betCode;//Ͷע��
	private String betmultiple;//Ͷע����
	private String betCount;//Ͷעע��
	private String betAmount;//Ͷע���
	
	
	
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
