/**
 * Title: QueryTerm.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-13 ����10:38:35
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * QueryTerm.java
 * QueryTerm
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-13 ����10:38:35
 * 
 */

public class QueryTerm {
	
	private String lotteryId;
	private String termNo;
	
	public QueryTerm(){
		
	}
	
	/**
	 *Title: 
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 */
	public QueryTerm(String lotteryId, String termNo) {
		super();
		this.lotteryId = lotteryId;
		this.termNo = termNo;
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
