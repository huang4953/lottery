/**
 * Title: TicketQueryResult.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-12 ����03:20:42
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * TicketQueryResult.java
 * TicketQueryResult
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-12 ����03:20:42
 * 
 */

public class TicketQueryResult {
	
	private String errorCode;//�������
	private String ticketSequence;//ͶעϵͳƱ��ˮ
	private String ticketId;//��Ưϵͳ���ص�Ʊ��ˮ
	private String printStatus;//��Ʊ״̬,0:�ȴ���Ʊ,1:��Ʊ�ɹ�,����ֵ��ʾ��Ʊ����
	
	
	
	
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
		sb.append("������:").append(this.errorCode);
		sb.append("ͶעϵͳƱ��ˮ:").append(this.ticketSequence);
		sb.append("��ƱϵͳƱ��ˮ:").append(this.ticketId);
		sb.append("Ʊ״̬:").append(this.printStatus);
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
