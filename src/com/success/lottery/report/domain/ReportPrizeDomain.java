/**
 * Title: ReportPrizeDomain.java
 * @Package com.success.lottery.report.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 上午10:53:04
 * @version V1.0
 */
package com.success.lottery.report.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * com.success.lottery.report.domain
 * ReportPrizeDomain.java
 * ReportPrizeDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 上午10:53:04
 * 
 */

public class ReportPrizeDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 2229381239691166973L;
	
	private int reportId;
	private String tableName;
	private String termNo;
	private Timestamp startTime;//datetime comment '系统开售时间',
	private Timestamp deadLine;//datetime comment '系统止售时间',
	private String lotteryResult;//varchar(64) comment '参照彩种玩法投注开奖结果规则表',
	private long saleVolume =0L;
	private long totalPrizeAmount =0L;
	private long volume1 =0L;
	private long volume2 =0L;
	private long volume3 =0L;
	private long webVolume =0L;
	private long smsVolume =0L;
	private long wapVolume =0L;
	private long clientVoulme =0L;
	private long smallAmount =0L;
	private long bigAmount =0L;
	private long failTicketCount =0L;
	private long reportStatus = 0;
	private String reportMark;
	private String reverse1;
	private String reverse2;
	private long aftTaxPrize =0L;
	private long taxPrize =0L;
	private long tiChengPrize =0L;
	private long commPrize =0L;
	
	public long getBigAmount() {
		return bigAmount;
	}
	public void setBigAmount(long bigAmount) {
		this.bigAmount = bigAmount;
	}
	
	public Timestamp getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Timestamp deadLine) {
		this.deadLine = deadLine;
	}
	public String getLotteryResult() {
		return lotteryResult;
	}
	public void setLotteryResult(String lotteryResult) {
		this.lotteryResult = lotteryResult;
	}
	public String getReportMark() {
		return reportMark;
	}
	public void setReportMark(String reportMark) {
		this.reportMark = reportMark;
	}
	public long getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(long reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getReverse1() {
		return reverse1;
	}
	public void setReverse1(String reverse1) {
		this.reverse1 = reverse1;
	}
	public String getReverse2() {
		return reverse2;
	}
	public void setReverse2(String reverse2) {
		this.reverse2 = reverse2;
	}
	public long getSaleVolume() {
		return saleVolume;
	}
	public void setSaleVolume(long saleVolume) {
		this.saleVolume = saleVolume;
	}
	public long getSmallAmount() {
		return smallAmount;
	}
	public void setSmallAmount(long smallAmount) {
		this.smallAmount = smallAmount;
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
	public long getTotalPrizeAmount() {
		return totalPrizeAmount;
	}
	public void setTotalPrizeAmount(long totalPrizeAmount) {
		this.totalPrizeAmount = totalPrizeAmount;
	}
	public long getFailTicketCount() {
		return failTicketCount;
	}
	public void setFailTicketCount(long failTicketCount) {
		this.failTicketCount = failTicketCount;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public long getClientVoulme() {
		return clientVoulme;
	}
	public void setClientVoulme(long clientVoulme) {
		this.clientVoulme = clientVoulme;
	}
	public long getSmsVolume() {
		return smsVolume;
	}
	public void setSmsVolume(long smsVolume) {
		this.smsVolume = smsVolume;
	}
	public long getVolume1() {
		return volume1;
	}
	public void setVolume1(long volume1) {
		this.volume1 = volume1;
	}
	public long getVolume2() {
		return volume2;
	}
	public void setVolume2(long volume2) {
		this.volume2 = volume2;
	}
	public long getWapVolume() {
		return wapVolume;
	}
	public void setWapVolume(long wapVolume) {
		this.wapVolume = wapVolume;
	}
	public long getWebVolume() {
		return webVolume;
	}
	public void setWebVolume(long webVolume) {
		this.webVolume = webVolume;
	}
	public long getVolume3() {
		return volume3;
	}
	public void setVolume3(long volume3) {
		this.volume3 = volume3;
	}
	public long getAftTaxPrize() {
		return aftTaxPrize;
	}
	public void setAftTaxPrize(long aftTaxPrize) {
		this.aftTaxPrize = aftTaxPrize;
	}
	public long getCommPrize() {
		return commPrize;
	}
	public void setCommPrize(long commPrize) {
		this.commPrize = commPrize;
	}
	public long getTaxPrize() {
		return taxPrize;
	}
	public void setTaxPrize(long taxPrize) {
		this.taxPrize = taxPrize;
	}
	public long getTiChengPrize() {
		return tiChengPrize;
	}
	public void setTiChengPrize(long tiChengPrize) {
		this.tiChengPrize = tiChengPrize;
	}

}
