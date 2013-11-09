/**
 * Title: LotteryTermSwitchDomain.java
 * @Package com.success.lottery.term.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-14 下午03:26:45
 * @version V1.0
 */
package com.success.lottery.term.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * com.success.lottery.term.domain
 * LotteryTermSwitchDomain.java
 * LotteryTermSwitchDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-14 下午03:26:45
 * 
 */

public class LotteryTermSwitchDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5393018340944807309L;
	private int lotteryId;
	private String currentTermNo;
	private String nextTermNo;
	private int old_termStatus;
	private int termStatus;
	private int old_saleStatus;
	private int saleStatus;
	private int time_space;
	private Timestamp begin_time;
	private Timestamp end_time;
	
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getCurrentTermNo() {
		return currentTermNo;
	}
	public void setCurrentTermNo(String currentTermNo) {
		this.currentTermNo = currentTermNo;
	}
	public String getNextTermNo() {
		return nextTermNo;
	}
	public void setNextTermNo(String nextTermNo) {
		this.nextTermNo = nextTermNo;
	}
	public int getOld_saleStatus() {
		return old_saleStatus;
	}
	public void setOld_saleStatus(int old_saleStatus) {
		this.old_saleStatus = old_saleStatus;
	}
	public int getOld_termStatus() {
		return old_termStatus;
	}
	public void setOld_termStatus(int old_termStatus) {
		this.old_termStatus = old_termStatus;
	}
	public int getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(int saleStatus) {
		this.saleStatus = saleStatus;
	}
	public int getTermStatus() {
		return termStatus;
	}
	public void setTermStatus(int termStatus) {
		this.termStatus = termStatus;
	}
	public int getTime_space() {
		return time_space;
	}
	public void setTime_space(int time_space) {
		this.time_space = time_space;
	}
	public Timestamp getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Timestamp begin_time) {
		this.begin_time = begin_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	

}
