/**
 * Title: QueryTermResult.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-13 上午11:24:16
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.model;

/**
 * com.success.protocol.ticket.zzy
 * QueryTermResult.java
 * QueryTermResult
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-13 上午11:24:16
 * 
 */

public class QueryTermResult {
	
	private String lotteryId;
	private String termNo;
	private String startTimeStamp;
	private String endTimeStamp;
	private String status;
	private String lotteryResult;
	private String inlinelotteryid;//还不清楚什么意思
	
	
	/**
	 * 
	 *Title: 
	 *Description:
	 */
	public QueryTermResult(){
		super();
	}
	
	/**
	 *Title: 
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param startTimeStamp
	 * @param endTimeStamp
	 * @param status
	 */
	public QueryTermResult(String lotteryId, String termNo, String startTimeStamp, String endTimeStamp, String status) {
		super();
		this.lotteryId = lotteryId;
		this.termNo = termNo;
		this.startTimeStamp = startTimeStamp;
		this.endTimeStamp = endTimeStamp;
		this.status = status;
	}
	
	/**
	 *Title: 
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param status
	 */
	public QueryTermResult(String lotteryId, String termNo, String status) {
		super();
		this.lotteryId = lotteryId;
		this.termNo = termNo;
		this.status = status;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("彩种:").append(this.lotteryId);
		sb.append("彩期:").append(this.termNo);
		sb.append("开期时间:").append(this.startTimeStamp);
		sb.append("期结时间:").append(this.endTimeStamp);
		sb.append("状态:").append(this.status);
		sb.append("开奖结果:").append(this.lotteryResult);
		sb.append("inlinelotteryid:").append(this.inlinelotteryid);
		return sb.toString();
	}
	
	public String getEndTimeStamp() {
		return endTimeStamp;
	}
	public void setEndTimeStamp(String endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}
	
	public String getStartTimeStamp() {
		return startTimeStamp;
	}
	public void setStartTimeStamp(String startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getLotteryResult() {
		return lotteryResult;
	}

	public void setLotteryResult(String lotteryResult) {
		this.lotteryResult = lotteryResult;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getInlinelotteryid() {
		return inlinelotteryid;
	}

	public void setInlinelotteryid(String inlinelotteryid) {
		this.inlinelotteryid = inlinelotteryid;
	}
	
	

}
