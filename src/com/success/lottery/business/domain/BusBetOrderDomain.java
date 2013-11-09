package com.success.lottery.business.domain;

import java.sql.Timestamp;
/**
 * 
 * com.success.lottery.business.domain
 * BusBetOrderDomain.java
 * BusBetOrderDomain
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-24 ����03:17:16
 *
 */
public class BusBetOrderDomain implements java.io.Serializable{

	private static final long	serialVersionUID	= 1L;
	private String orderId; // varchar(32) not null comment'������ţ�Ψһ�����ظ���DDYYYYMMDDHHMISSnnnn',
	private String planId; // varchar(32) not null comment '�������',
	private int planSource = 0; // int comment 'Ͷע�����Ǵ��Ǹ��������ģ�0-WEB��1-SMS��2-WAP',
	private int chaseNumber; // int not null comment'����������д׷�Ŷ�����ţ�0-Ϊ��׷�Ŷ�����1-��ʼ�ںţ�֮��Ϊ׷�ű������Ϊ2��3...',
	private long userId; // bigint not null,
	private String areaCode; // varchar(10) not null comment '*�������-ʡ�ݴ���',
	private int lotteryId; // int not null,
	private String betTerm; // varchar(32) not null,
	private int playType; // int not null,
	private int betType; // int not null,
	private int betCodeMode = 0; // int not null comment 'Ͷע������Դ��ʽ��0-ֱ�����ݣ�Ĭ�ϡ�1-�ļ��ϴ����ݲ�����',
	private String betCode; // varchar(512) not null comment 'Ͷע���ݣ�ע��ע֮����"^"�ָ������codeMode=1��Ϊ�ļ�·�������ܳ���ָ���ĳ���512�ֽ�',
	private int betMultiple; // int not null comment 'Ͷע����������',
	private int betAmount; // numeric(12,0) not null comment 'Ͷע�����ܽ��',
	private Timestamp orderTime; // datetime not null,
	private int orderStatus = 0; // int not null,
	private Timestamp ticketTime; // datetime,
	private String ticketStat = ""; // varchar(32) not null,
	private int winStatus = 0; // int not null default 0,
	private long preTaxPrize = 0; // numeric(12,0) not null,
	private String reserve; // varchar(32),
	private String winCode; // varchar(512) not null comment '�н�����',
	
	private long taxPrize=0;//˰��
	private long aftTaxPrize=0;//˰�󽱽�
	private long deductPrize=0;//��ɽ����ں������
	private long commissionPrize=0;//Ӷ��Ԥ��
	
	/*
	 * �û���Ϣ
	 */
	private String loginName;//*�����ڵ�¼�������ظ�������ֱ��ʹ���ֻ�����
	private String mobilePhone;//*�����ڵ�¼��ҵ��Ҫ�����
	private int status;//�û�״̬��1-���� 0-ע�� 2-����
	private String nickName;//��ʾ��ҳ������ƣ���ѡ����δ������ʾ�ֻ����룬��δ���ֻ�������ʾ�˻�Id
	private String realName;//��ʵ��������������д
	
	/*
	 * ������Ϣ
	 */
	
	//private String deadLine;
	//private String startTime;
	//private String startTime2;
	//private String winLine;
	//private String winLine2;
	//private String deadLine3;
	//private String changeLine;
	private String lotteryResult;
	private String deadLine;
	
	/*
	 * ������Ϣ
	 */
	
	
	
	public int getBetAmount() {
		return this.betAmount;
	}
	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
	}
	public String getAreaCode() {
		return this.areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getBetCode() {
		return this.betCode;
	}
	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}
	public int getBetCodeMode() {
		return this.betCodeMode;
	}
	public void setBetCodeMode(int betCodeMode) {
		this.betCodeMode = betCodeMode;
	}
	public int getBetMultiple() {
		return this.betMultiple;
	}
	public void setBetMultiple(int betMultiple) {
		this.betMultiple = betMultiple;
	}
	public String getBetTerm() {
		return this.betTerm;
	}
	public void setBetTerm(String betTerm) {
		this.betTerm = betTerm;
	}
	public int getBetType() {
		return this.betType;
	}
	public void setBetType(int betType) {
		this.betType = betType;
	}
	public int getChaseNumber() {
		return this.chaseNumber;
	}
	public void setChaseNumber(int chaseNumber) {
		this.chaseNumber = chaseNumber;
	}
	public int getLotteryId() {
		return this.lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getOrderId() {
		return this.orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getOrderStatus() {
		return this.orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Timestamp getOrderTime() {
		return this.orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public String getPlanId() {
		return this.planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public int getPlanSource() {
		return this.planSource;
	}
	public void setPlanSource(int planSource) {
		this.planSource = planSource;
	}
	public int getPlayType() {
		return this.playType;
	}
	public void setPlayType(int playType) {
		this.playType = playType;
	}
	
	public String getReserve() {
		return this.reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
	public Timestamp getTicketTime() {
		return this.ticketTime;
	}
	public void setTicketTime(Timestamp ticketTime) {
		this.ticketTime = ticketTime;
	}
	public long getUserId() {
		return this.userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getWinStatus() {
		return this.winStatus;
	}
	public void setWinStatus(int winStatus) {
		this.winStatus = winStatus;
	}
	public long getPreTaxPrize() {
		return this.preTaxPrize;
	}
	public void setPreTaxPrize(long preTaxPrize) {
		this.preTaxPrize = preTaxPrize;
	}

	
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(o instanceof BusBetOrderDomain){
			return this.getOrderId().equals(((BusBetOrderDomain)o).getOrderId());
		}
		return false;
	}
	
	public int hashcode(){
		int result = 17;
		result = 37 * result + this.orderId.hashCode();
		return result;
	}
	
	public String getWinCode() {
		return this.winCode;
	}
	public void setWinCode(String winCode) {
		this.winCode = winCode;
	}
	public String getLoginName() {
		return this.loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMobilePhone() {
		return this.mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getNickName() {
		return this.nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return this.realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public int getStatus() {
		return this.status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTicketStat() {
		return ticketStat;
	}
	public void setTicketStat(String ticketStat) {
		this.ticketStat = ticketStat;
	}
	public String getLotteryResult() {
		return lotteryResult;
	}
	public void setLotteryResult(String lotteryResult) {
		this.lotteryResult = lotteryResult;
	}
	public String getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
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
