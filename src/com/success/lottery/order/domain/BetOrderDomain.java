package com.success.lottery.order.domain;

import java.sql.Timestamp;
/**
 * 订单信息实体类
 * 
 * @author bing.li
 * 
 */
public class BetOrderDomain implements java.io.Serializable{

	private static final long	serialVersionUID	= 1L;
	private String orderId; // varchar(32) not null comment'订单编号，唯一不能重复，DDYYYYMMDDHHMISSnnnn',
	private String planId; // varchar(32) not null comment '方案编号',
	private int planSource = 0; // int comment '投注方案是从那个渠道来的，0-WEB，1-SMS，2-WAP',
	private int chaseNumber; // int not null comment'按照期数填写追号订单编号，0-为非追号订单，1-开始期号，之后为追号编号依次为2、3...',
	private long userId; // bigint not null,
	private String areaCode; // varchar(10) not null comment '*区域代码-省份代码',
	private int lotteryId; // int not null,
	private String betTerm; // varchar(32) not null,
	private int playType; // int not null,
	private int betType; // int not null,
	private int betCodeMode = 0; // int not null comment '投注内容来源格式，0-直接内容，默认、1-文件上传，暂不启用',
	private String betCode; // varchar(512) not null comment '投注内容，注与注之间用"^"分隔；如果codeMode=1则为文件路径；不能超过指定的长度512字节',
	private int betMultiple; // int not null comment '投注倍数，倍数',
	private int betAmount; // numeric(12,0) not null comment '投注订单总金额',
	private Timestamp orderTime; // datetime not null,
	private int orderStatus = 0; // int not null,
	private Timestamp ticketTime; // datetime,
	private String ticketStat = ""; // varchar(32) not null,
	private int winStatus = 0; // int not null default 0,
	private long preTaxPrize = 0; // numeric(12,0) not null,
	private String reserve; // varchar(32),
	private String winCode; // varchar(512) not null comment '中奖号码',
	private long taxPrize=0;//税金
	private long aftTaxPrize=0;//税后奖金
	private long deductPrize=0;//提成金，用于合买提成
	private long commissionPrize=0;//佣金，预留
	
	
	public int getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getBetCode() {
		return betCode;
	}
	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}
	public int getBetCodeMode() {
		return betCodeMode;
	}
	public void setBetCodeMode(int betCodeMode) {
		this.betCodeMode = betCodeMode;
	}
	public int getBetMultiple() {
		return betMultiple;
	}
	public void setBetMultiple(int betMultiple) {
		this.betMultiple = betMultiple;
	}
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
	public int getChaseNumber() {
		return chaseNumber;
	}
	public void setChaseNumber(int chaseNumber) {
		this.chaseNumber = chaseNumber;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public int getPlanSource() {
		return planSource;
	}
	public void setPlanSource(int planSource) {
		this.planSource = planSource;
	}
	public int getPlayType() {
		return playType;
	}
	public void setPlayType(int playType) {
		this.playType = playType;
	}
	
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
	public Timestamp getTicketTime() {
		return ticketTime;
	}
	public void setTicketTime(Timestamp ticketTime) {
		this.ticketTime = ticketTime;
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
	public long getPreTaxPrize() {
		return preTaxPrize;
	}
	public void setPreTaxPrize(long preTaxPrize) {
		this.preTaxPrize = preTaxPrize;
	}

	
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(o instanceof BetOrderDomain){
			return this.getOrderId().equals(((BetOrderDomain)o).getOrderId());
		}
		return false;
	}
	
	public int hashcode(){
		int result = 17;
		result = 37 * result + this.orderId.hashCode();
		return result;
	}
	
	public static void main(String[] args){
		
	}
	public String getWinCode() {
		return winCode;
	}
	public void setWinCode(String winCode) {
		this.winCode = winCode;
	}
	public String getTicketStat() {
		return ticketStat;
	}
	public void setTicketStat(String ticketStat) {
		this.ticketStat = ticketStat;
	}
	public long getAftTaxPrize() {
		return aftTaxPrize;
	}
	public void setAftTaxPrize(long aftTaxPrize) {
		this.aftTaxPrize = aftTaxPrize;
	}
	public long getCommissionPrize() {
		return commissionPrize;
	}
	public void setCommissionPrize(long commissionPrize) {
		this.commissionPrize = commissionPrize;
	}
	public long getDeductPrize() {
		return deductPrize;
	}
	public void setDeductPrize(long deductPrize) {
		this.deductPrize = deductPrize;
	}
	public long getTaxPrize() {
		return taxPrize;
	}
	public void setTaxPrize(long taxPrize) {
		this.taxPrize = taxPrize;
	}
}
