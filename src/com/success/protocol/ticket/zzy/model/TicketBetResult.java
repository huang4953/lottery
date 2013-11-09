/**
 * Title: TicketBetResult.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-12 ����10:52:00
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * TicketBetResult.java
 * TicketBetResult
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-12 ����10:52:00
 * 
 */

public class TicketBetResult {
	private String headErrorCode;//���ص���Ϣ����Ϣͷ�ķ����룬����ֻ��ͨѶ�ķ�����
	private String headdErrorMsg;//���ص���Ϣ����Ϣͷ�ķ�����Ϣ��Ϣ������ֻ��ͨѶ�ķ���
	private String errorCode;//�����һ����Ʊ�ķ�����,������ҵ���߼�����������״̬����
	private String opreateId;//������ˮ
	private String ticketSequence;//Ͷעϵͳ�����Ʊid
	private String accountValue;//�˻�����
	
	
	
	/**
	 *Title: 
	 *Description: 
	 * @param errorCode �������
	 * @param opreateId ������ˮ
	 * @param ticketSequence Ͷעϵͳ�����ƱID
	 * @param accountValue �˻����
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
