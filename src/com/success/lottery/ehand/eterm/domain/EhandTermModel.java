/**
 * Title: EhandTermBussiModel.java
 * @Package com.success.lottery.ehand.eterm.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-19 下午05:24:54
 * @version V1.0
 */
package com.success.lottery.ehand.eterm.domain;

import java.io.Serializable;

/**
 * com.success.lottery.ehand.eterm.domain
 * EhandTermBussiModel.java
 * EhandTermBussiModel
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-19 下午05:24:54
 * 
 */

public class EhandTermModel implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 2332166808683135525L;
	
	private int lotteryId = 0;//对应lotteryterm中的lotteryId
	private String ehandLotteryId;//掌中弈彩种编号
	private String issue;//掌中弈彩种彩期
	private String startTime;//掌中弈接口返回的开始时间
	private String endTime;//掌中弈接口返回的结束时间
	private String printStart;//掌中弈可以出票的开始时间
	private String printEnd;//掌中弈可以出票的结束时间
	private int status = -99;//掌中弈接口返回彩期状态
	private String bonuscode;//掌中弈接口返回的本期开奖号码
	private String salemoney;//掌中弈接口返回的本期销售金额，单位分
	private String bonusmoney;//掌中弈接口返回的本期中奖额，单位分
	private String reserve;//掌中弈接口返回的本期中奖额，单位分
	
	public String getBonuscode() {
		return bonuscode;
	}
	public void setBonuscode(String bonuscode) {
		this.bonuscode = bonuscode;
	}
	public String getBonusmoney() {
		return bonusmoney;
	}
	public void setBonusmoney(String bonusmoney) {
		this.bonusmoney = bonusmoney;
	}
	public String getEhandLotteryId() {
		return ehandLotteryId;
	}
	public void setEhandLotteryId(String ehandLotteryId) {
		this.ehandLotteryId = ehandLotteryId;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getPrintEnd() {
		return printEnd;
	}
	public void setPrintEnd(String printEnd) {
		this.printEnd = printEnd;
	}
	public String getPrintStart() {
		return printStart;
	}
	public void setPrintStart(String printStart) {
		this.printStart = printStart;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public String getSalemoney() {
		return salemoney;
	}
	public void setSalemoney(String salemoney) {
		this.salemoney = salemoney;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
