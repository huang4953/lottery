/**
 * Title: QueryTermModel.java
 * @Package com.success.lottery.ehand.ehandserver.bussiness.model
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-20 下午12:17:34
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.bussiness.model;

/**
 * com.success.lottery.ehand.ehandserver.bussiness.model
 * QueryTermModel.java
 * QueryTermModel
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-20 下午12:17:34
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
