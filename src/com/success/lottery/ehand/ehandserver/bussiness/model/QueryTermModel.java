/**
 * Title: QueryTermModel.java
 * @Package com.success.lottery.ehand.ehandserver.bussiness.model
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-20 ����12:17:34
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.bussiness.model;

/**
 * com.success.lottery.ehand.ehandserver.bussiness.model
 * QueryTermModel.java
 * QueryTermModel
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-20 ����12:17:34
 * 
 */

public class QueryTermModel {
	
	private String lotteryId;
	private String termNo;
	
	/**
	 * 
	 *Title: 
	 *Description:
	 */
	public QueryTermModel(){
		
	}
	/**
	 *Title: 
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 */
	public QueryTermModel(String lotteryId, String termNo) {
		super();
		this.lotteryId = lotteryId;
		this.termNo = termNo;
	}

	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

}
