package com.success.lottery.order.domain;

import java.sql.Timestamp;
/**
 * 合买的订单记录
 * @author gaoboqin
 *
 */
public class CpInfoDomain implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5803574770233322153L;
	private String cpInfoId; // varchar(32) not null comment'合买信息编号，唯一不能重复，CIYYYYMMDDHHMISSnnnn',
	private String planId; // varchar(32) not null comment '方案编号',
	private long userId; // bigint not null,
	private int lotteryId; // int not null,
	private String betTerm; // varchar(32) not null,
	private int playType; // int not null,
	private int betType; // int not null,
	private int cpOrderType = 0; // int not null comment '合买订单方式,0-发起，1-参与',
	private int cpUnit;//认购份数
	private int cpAmount; // numeric(12,0) not null comment '认购金额',
	private Timestamp orderTime; // datetime not null,
	private int orderStatus = 0; // int not null,
	private int winStatus = 0; // int not null default 0,
	private long prize = 0; // numeric(12,0) not null,
	private String otherColumn1; // varchar(32) 预留,
	private String otherColumn2; // varchar(32) 预留,
	private long otherColumn3 = 0; // numeric(12,0)  预留,
	private long otherColumn4 = 0; // numeric(12,0) 预留,
	private String reserve; // varchar(32),
	
	public String getBetTerm() {
		return betTerm;
	}
	public void setBetTerm(String betTerm) {
		this.betTerm = betTerm;
	}
	public int getBetType() {
		return betType;
	}
	public void setBetType(int betType) {
		this.betType = betType;
	}
	public int getCpAmount() {
		return cpAmount;
	}
	public void setCpAmount(int cpAmount) {
		this.cpAmount = cpAmount;
	}
	
	public int getCpOrderType() {
		return cpOrderType;
	}
	public void setCpOrderType(int cpOrderType) {
		this.cpOrderType = cpOrderType;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public String getOtherColumn1() {
		return otherColumn1;
	}
	public void setOtherColumn1(String otherColumn1) {
		this.otherColumn1 = otherColumn1;
	}
	public String getOtherColumn2() {
		return otherColumn2;
	}
	public void setOtherColumn2(String otherColumn2) {
		this.otherColumn2 = otherColumn2;
	}
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public int getPlayType() {
		return playType;
	}
	public void setPlayType(int playType) {
		this.playType = playType;
	}
	public long getPrize() {
		return prize;
	}
	public void setPrize(long prize) {
		this.prize = prize;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getWinStatus() {
		return winStatus;
	}
	public void setWinStatus(int winStatus) {
		this.winStatus = winStatus;
	}
	public int getCpUnit() {
		return cpUnit;
	}
	public void setCpUnit(int cpUnit) {
		this.cpUnit = cpUnit;
	}
	public long getOtherColumn3() {
		return otherColumn3;
	}
	public void setOtherColumn3(long otherColumn3) {
		this.otherColumn3 = otherColumn3;
	}
	public long getOtherColumn4() {
		return otherColumn4;
	}
	public void setOtherColumn4(long otherColumn4) {
		this.otherColumn4 = otherColumn4;
	}
	public String getCpInfoId() {
		return cpInfoId;
	}
	public void setCpInfoId(String cpInfoId) {
		this.cpInfoId = cpInfoId;
	}
}
