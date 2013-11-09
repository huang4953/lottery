/**
 * Title: TicketQueryResult.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-12 下午03:20:42
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * TicketQueryResult.java
 * TicketQueryResult
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-12 下午03:20:42
 * 
 */

public class TicketQueryResult {
	
	private String errorCode;//错误代码
	private String ticketSequence;//投注系统票流水
	private String ticketId;//打漂系统返回的票流水
	private String printStatus;//出票状态,0:等待出票,1:出票成功,其他值表示出票错误
	
	
	
	
	/**
	 *Title: 
	 *Description: 
	 * @param errorCode
	 * @param ticketSequence
	 * @param ticketId
	 * @param printStatus
	 */
	public TicketQueryResult(String errorCode, String ticketSequence, String ticketId, String printStatus) {
		super();
		this.errorCode = errorCode;
		this.ticketSequence = ticketSequence;
		this.ticketId = ticketId;
		this.printStatus = printStatus;
	}
	
	/**
	 *Title: 
	 *Description: 
	 * @param ticketSequence
	 * @param ticketId
	 * @param printStatus
	 */
	public TicketQueryResult(String ticketSequence, String ticketId, String printStatus) {
		super();
		this.ticketSequence = ticketSequence;
		this.ticketId = ticketId;
		this.printStatus = printStatus;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("错误码:").append(this.errorCode);
		sb.append("投注系统票流水:").append(this.ticketSequence);
		sb.append("出票系统票流水:").append(this.ticketId);
		sb.append("票状态:").append(this.printStatus);
		return sb.toString();
	}

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getPrintStatus() {
		return printStatus;
	}
	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketSequence() {
		return ticketSequence;
	}
	public void setTicketSequence(String ticketSequence) {
		this.ticketSequence = ticketSequence;
	}

}
