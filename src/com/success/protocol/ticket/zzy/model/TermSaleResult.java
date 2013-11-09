/**
 * Title: TermSaleResult.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-13 ����10:55:34
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * TermSaleResult.java
 * TermSaleResult
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-13 ����10:55:34
 * 
 */

public class TermSaleResult {
	private String errorCode;
	private String termNo;
	private String lotteryId;
	private String saleMoney;
	private String bonusMoney;
	
	
	/**
	 *Title: 
	 *Description: 
	 * @param errorCode
	 * @param termNo
	 * @param lotteryId
	 * @param saleMoney
	 * @param bonusMoney
	 */
	public TermSaleResult(String errorCode, String termNo, String lotteryId, String saleMoney, String bonusMoney) {
		super();
		this.errorCode = errorCode;
		this.termNo = termNo;
		this.lotteryId = lotteryId;
		this.saleMoney = saleMoney;
		this.bonusMoney = bonusMoney;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("������:").append(this.errorCode);
		sb.append("����:").append(this.termNo);
		sb.append("����:").append(this.lotteryId);
		sb.append("���۽��:").append(this.saleMoney);
		sb.append("�н����:").append(this.bonusMoney);
		return sb.toString();
	}
	                         
	
	public String getBonusMoney() {
		return bonusMoney;
	}
	public void setBonusMoney(String bonusMoney) {
		this.bonusMoney = bonusMoney;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getSaleMoney() {
		return saleMoney;
	}
	public void setSaleMoney(String saleMoney) {
		this.saleMoney = saleMoney;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	

}
