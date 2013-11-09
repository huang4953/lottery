/**
 * Title: ReportAccount.java
 * @Package com.success.lottery.report.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 下午05:35:06
 * @version V1.0
 */
package com.success.lottery.report.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * com.success.lottery.report.domain
 * ReportAccount.java
 * ReportAccount
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 下午05:35:06
 * 
 */

public class ReportAccount implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 4922602347497285489L;
	
	private int reportId;
	private String accountDate;//账户统计日期
	private String ipsAmount = "0";  //   环迅充值
	private String kzAmount = "0";  //   空中充值 
	private String clientAmount = "0";  //客户端充值
	private String dispatchAmount = "0";//派奖
    private String adjustAdd = "0";   //  额度调整增加'
	private String zhuihaoCancel = "0"; //追号取消',
	private String betAmount = "0";    //投注
	private String drawRequest = "0";   //提现申请
	private String drawAgree = "0";     //提现通过
	private String drawReject = "0";    //提现拒绝
	private String adjustReduce = "0";  //额度调整减少
	private String fundsAccount = "0";  //本金余额
	private String prizeAccount = "0";  //奖金余额
	private String frozenAccount = "0"; //冻结余额
	private String trackAccount = "0";  //追号冻结
	private String createTime;    //生成数据时间
	private Timestamp beginTime;     //统计时间开始
	private Timestamp endTime;       //统计时间结束
	private String remark;        //
	private String reserve1;      //
	private String reserve2;      //
	private String reserve3;      //
	private Timestamp reportTime;//获取账户统计的时间
	
	public String getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	public String getAdjustAdd() {
		return adjustAdd;
	}
	public void setAdjustAdd(String adjustAdd) {
		this.adjustAdd = adjustAdd;
	}
	public String getAdjustReduce() {
		return adjustReduce;
	}
	public void setAdjustReduce(String adjustReduce) {
		this.adjustReduce = adjustReduce;
	}
	
	public String getClientAmount() {
		return clientAmount;
	}
	public void setClientAmount(String clientAmount) {
		this.clientAmount = clientAmount;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDispatchAmount() {
		return dispatchAmount;
	}
	public void setDispatchAmount(String dispatchAmount) {
		this.dispatchAmount = dispatchAmount;
	}
	public String getDrawAgree() {
		return drawAgree;
	}
	public void setDrawAgree(String drawAgree) {
		this.drawAgree = drawAgree;
	}
	public String getDrawReject() {
		return drawReject;
	}
	public void setDrawReject(String drawReject) {
		this.drawReject = drawReject;
	}
	public String getDrawRequest() {
		return drawRequest;
	}
	public void setDrawRequest(String drawRequest) {
		this.drawRequest = drawRequest;
	}
	public String getFrozenAccount() {
		return frozenAccount;
	}
	public void setFrozenAccount(String frozenAccount) {
		this.frozenAccount = frozenAccount;
	}
	public String getFundsAccount() {
		return fundsAccount;
	}
	public void setFundsAccount(String fundsAccount) {
		this.fundsAccount = fundsAccount;
	}
	public String getIpsAmount() {
		return ipsAmount;
	}
	public void setIpsAmount(String ipsAmount) {
		this.ipsAmount = ipsAmount;
	}
	public String getKzAmount() {
		return kzAmount;
	}
	public void setKzAmount(String kzAmount) {
		this.kzAmount = kzAmount;
	}
	public String getPrizeAccount() {
		return prizeAccount;
	}
	public void setPrizeAccount(String prizeAccount) {
		this.prizeAccount = prizeAccount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getReserve2() {
		return reserve2;
	}
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	public String getReserve3() {
		return reserve3;
	}
	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}
	public String getTrackAccount() {
		return trackAccount;
	}
	public void setTrackAccount(String trackAccount) {
		this.trackAccount = trackAccount;
	}
	public String getZhuihaoCancel() {
		return zhuihaoCancel;
	}
	public void setZhuihaoCancel(String zhuihaoCancel) {
		this.zhuihaoCancel = zhuihaoCancel;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public Timestamp getReportTime() {
		return reportTime;
	}
	public void setReportTime(Timestamp reportTime) {
		this.reportTime = reportTime;
	}
	public Timestamp getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(String betAmount) {
		this.betAmount = betAmount;
	}

}
