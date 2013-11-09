/**
 * Title: ReportTermDomain.java
 * @Package com.success.lottery.report.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 上午10:25:46
 * @version V1.0
 */
package com.success.lottery.report.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * com.success.lottery.report.domain
 * ReportTermDomain.java
 * ReportTermDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 上午10:25:46
 * 
 */

public class ReportTermDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -6717034714169864622L;
	
	private int lotteryId;//int not null,comment '彩种id'
	private String termNo;//varchar(32) not null,comment '当前期数'
	private Timestamp startTime;//datetime comment '系统开售时间',
	private Timestamp deadLine;//datetime comment '系统止售时间',
	private String lotteryResult;//varchar(64) comment '参照彩种玩法投注开奖结果规则表',
	public Timestamp getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Timestamp deadLine) {
		this.deadLine = deadLine;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getLotteryResult() {
		return lotteryResult;
	}
	public void setLotteryResult(String lotteryResult) {
		this.lotteryResult = lotteryResult;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

}
