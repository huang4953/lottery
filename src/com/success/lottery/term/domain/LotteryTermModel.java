/**
 * @Title: LotteryTermModel.java
 * @Package com.success.lottery.order.model
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-6 下午02:59:48
 * @version V1.0
 */
package com.success.lottery.term.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.success.lottery.util.LotteryInfo;

/**
 * com.success.lottery.order.model
 * LotteryTermModel.java
 * LotteryTermModel
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-6 下午02:59:48
 * 
 */

public class LotteryTermModel implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4113812432754587666L;

	private int lotteryId;//int not null,comment '彩种id'
	private String termNo;//varchar(32) not null,comment '当前期数'
	private String beforeWinTermNo;//最近开奖的期号
	private int termStatus = 0;//int not null default 0 comment '参看彩种信息格式文档',
	private int winStatus = 1;//int not null default 1 comment '参看彩种信息格式文档',
	private int saleStatus = 3;//销售状态
	private String salesInfo;//varchar(2048) comment '参看彩种信息格式文档',
	private Timestamp startTime;//datetime comment '系统开售时间',
	private Timestamp deadLine;//datetime comment '系统止售时间',
	private Timestamp deadLine2;//datetime comment '合买止售时间',
	private Timestamp winLine;//datetime comment '系统开奖时间',
	private Timestamp startTime2;//datetime comment '官方开售时间',
	private Timestamp deadLine3;//datetime comment '官方止售时间',
	private Timestamp winLine2;//datetime comment '官方开奖时间',
	private Timestamp changeLine;//datetime comment '该彩种官方公布的最终兑奖截至时间',
	private String lotteryResult;//varchar(64) comment '参照彩种玩法投注开奖结果规则表',
	private String lotteryResultPlus;//varchar(64) comment '开奖结果顺序',
	private String missCount;//varchar(512) comment '遗漏信息',
	private String limitNumber;//varchar(512) comment '限号信息',
	private String salesVolume;//varchar(64) comment '该彩种该期的销售总量',
	private String jackpot;//varchar(64) comment '该彩种当前期的累计奖池',
	private String winResult;//varchar(1024) comment '参照彩种玩法投注开奖结果规则表',
	private String nextTerm;//varchar(32) comment '下期期号',
	private String reserve1;//varchar(32)
	private String lotteryName;//彩种中文名称
	private LotteryInfo lotteryInfo;//彩种附加信息
	private long startTimeSec = -1;//距离系统开售的时间秒
	private long deadLineSec = -1;//距离系统止售的时间秒
	
	
	
	/**
	 *Title: 
	 *Description: 
	 */
	public LotteryTermModel() {
		super();
		//this.setLotteryInfo(new LotteryInfo());
	}
	
	
	public Timestamp getChangeLine() {
		return changeLine;
	}
	public void setChangeLine(Timestamp changeLine) {
		this.changeLine = changeLine;
	}
	public Timestamp getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Timestamp deadLine) {
		this.deadLine = deadLine;
	}
	public String getJackpot() {
		return jackpot;
	}
	public void setJackpot(String jackpot) {
		this.jackpot = jackpot;
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
	public String getLotteryResultPlus() {
		return lotteryResultPlus;
	}
	public void setLotteryResultPlus(String lotteryResultPlus) {
		this.lotteryResultPlus = lotteryResultPlus;
	}
	public String getNextTerm() {
		return nextTerm;
	}
	public void setNextTerm(String nextTerm) {
		this.nextTerm = nextTerm;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getSalesInfo() {
		return salesInfo;
	}
	public void setSalesInfo(String salesInfo) {
		this.salesInfo = salesInfo;
	}
	public String getSalesVolume() {
		return salesVolume;
	}
	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public int getTermStatus() {
		return termStatus;
	}
	public void setTermStatus(int termStatus) {
		this.termStatus = termStatus;
	}
	public Timestamp getWinLine() {
		return winLine;
	}
	public void setWinLine(Timestamp winLine) {
		this.winLine = winLine;
	}
	public String getWinResult() {
		return winResult;
	}
	public void setWinResult(String winResult) {
		this.winResult = winResult;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public LotteryInfo getLotteryInfo() {
		return lotteryInfo;
	}
	public void setLotteryInfo(LotteryInfo lotteryInfo) {
		this.lotteryInfo = lotteryInfo;
	}


	public int getSaleStatus() {
		return saleStatus;
	}


	public void setSaleStatus(int saleStatus) {
		this.saleStatus = saleStatus;
	}


	public int getWinStatus() {
		return winStatus;
	}


	public void setWinStatus(int winStatus) {
		this.winStatus = winStatus;
	}


	public Timestamp getDeadLine2() {
		return deadLine2;
	}


	public void setDeadLine2(Timestamp deadLine2) {
		this.deadLine2 = deadLine2;
	}


	public Timestamp getDeadLine3() {
		return deadLine3;
	}


	public void setDeadLine3(Timestamp deadLine3) {
		this.deadLine3 = deadLine3;
	}


	public String getLimitNumber() {
		return limitNumber;
	}


	public void setLimitNumber(String limitNumber) {
		this.limitNumber = limitNumber;
	}


	public String getMissCount() {
		return missCount;
	}


	public void setMissCount(String missCount) {
		this.missCount = missCount;
	}


	public Timestamp getStartTime() {
		return startTime;
	}


	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}


	public Timestamp getStartTime2() {
		return startTime2;
	}


	public void setStartTime2(Timestamp startTime2) {
		this.startTime2 = startTime2;
	}


	public Timestamp getWinLine2() {
		return winLine2;
	}


	public void setWinLine2(Timestamp winLine2) {
		this.winLine2 = winLine2;
	}


	public String getBeforeWinTermNo() {
		return beforeWinTermNo;
	}


	public void setBeforeWinTermNo(String beforeWinTermNo) {
		this.beforeWinTermNo = beforeWinTermNo;
	}


	public long getDeadLineSec() {
		return deadLineSec;
	}


	public void setDeadLineSec(long deadLineSec) {
		this.deadLineSec = deadLineSec;
	}


	public long getStartTimeSec() {
		return startTimeSec;
	}


	public void setStartTimeSec(long startTimeSec) {
		this.startTimeSec = startTimeSec;
	}

}
