/**
 * 
 */
package com.success.lottery.account.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author gaoboqin
 *
 */
public class UserPointTrans implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1531073387164162942L;
	
	private long userId;
	private long pointId;
	private long transType;
	private long transSubType;
	private Timestamp transTime;
	private long betAmount;
	private long pointSource1;
	private long pointSource2;
	private long points;
	private String remark;
	private String reserve;
	
	public long getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(long betAmount) {
		this.betAmount = betAmount;
	}
	public long getPointId() {
		return pointId;
	}
	public void setPointId(long pointId) {
		this.pointId = pointId;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long points) {
		this.points = points;
	}
	public long getPointSource1() {
		return pointSource1;
	}
	public void setPointSource1(long pointSource1) {
		this.pointSource1 = pointSource1;
	}
	public long getPointSource2() {
		return pointSource2;
	}
	public void setPointSource2(long pointSource2) {
		this.pointSource2 = pointSource2;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public long getTransSubType() {
		return transSubType;
	}
	public void setTransSubType(long transSubType) {
		this.transSubType = transSubType;
	}
	public Timestamp getTransTime() {
		return transTime;
	}
	public void setTransTime(Timestamp transTime) {
		this.transTime = transTime;
	}
	public long getTransType() {
		return transType;
	}
	public void setTransType(long transType) {
		this.transType = transType;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	

}
