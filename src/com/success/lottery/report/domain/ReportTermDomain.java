/**
 * Title: ReportTermDomain.java
 * @Package com.success.lottery.report.domain
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-9 ����10:25:46
 * @version V1.0
 */
package com.success.lottery.report.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * com.success.lottery.report.domain
 * ReportTermDomain.java
 * ReportTermDomain
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-9 ����10:25:46
 * 
 */

public class ReportTermDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = -6717034714169864622L;
	
	private int lotteryId;//int not null,comment '����id'
	private String termNo;//varchar(32) not null,comment '��ǰ����'
	private Timestamp startTime;//datetime comment 'ϵͳ����ʱ��',
	private Timestamp deadLine;//datetime comment 'ϵͳֹ��ʱ��',
	private String lotteryResult;//varchar(64) comment '���ղ����淨Ͷע������������',
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
