/**
 * Title: TicketBetResult.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-12 上午10:52:00
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * TicketBetResult.java
 * TicketBetResult
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-12 上午10:52:00
 * 
 */

public class TicketBetResult {
	private String headErrorCode;//返回的消息的消息头的返回码，可能只是通讯的返回码
	private String headdErrorMsg;//返回的消息的消息头的返回消息信息，可能只是通讯的返回
	private String errorCode;//具体的一个彩票的返回码,真正的业务逻辑错误会在这个状态返回
	private String opreateId;//交易流水
	private String ticketSequence;//投注系统定义的票id
	private String accountValue;//账户余额，分
	
	
	
	/**
	 *Title: 
	 *Description: 
	 * @param errorCode 错误代码
	 * @param opreateId 交易流水
	 * @param ticketSequence 投注系统定义的票ID
	 * @param accountValue 账户余额
	 */
	public TicketBetResult(String headErrorCode,String headdErrorMsg, String errorCode, String opreateId, String ticketSequence, String accountValue) {
		super();
		this.headErrorCode = headErrorCode;
		this.headdErrorMsg = headdErrorMsg;
		this.errorCode = errorCode;
		this.opreateId = opreateId;
		this.ticketSequence = ticketSequence;
		this.accountValue = accountValue;
	}
	
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("errorCode:").append(errorCode);
		sb.append("opreateId:").append(opreateId);
		sb.append("ticketSequence:").append(ticketSequence);
		sb.append("accountValue:").append(accountValue);
		return sb.toString();
	}
	public String getAccountValue() {
		return accountValue;
	}
	public void setAccountValue(String accountValue) {
		this.accountValue = accountValue;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getOpreateId() {
		return opreateId;
	}
	public void setOpreateId(String opreateId) {
		this.opreateId = opreateId;
	}

	public String getTicketSequence() {
		return ticketSequence;
	}
	public void setTicketSequence(String ticketSequence) {
		this.ticketSequence = ticketSequence;
	}


	public String getHeaddErrorMsg() {
		return headdErrorMsg;
	}


	public void setHeaddErrorMsg(String headdErrorMsg) {
		this.headdErrorMsg = headdErrorMsg;
	}


	public String getHeadErrorCode() {
		return headErrorCode;
	}


	public void setHeadErrorCode(String headErrorCode) {
		this.headErrorCode = headErrorCode;
	}
}
