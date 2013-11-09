package com.success.lottery.account.model;

import java.sql.Timestamp;
public class IPSOrderModel{

	private String		orderId;
	private int			amount			= 0;
	private long		userId			= 0;
	private int			orderDate		= 0;
	private String		currencyType	= "RMB";
	private String		gatewayType		= "01";
	private Timestamp	orderTime;
	private byte		succFlag		= 0;
	private String		ipsBillNo;
	private String		ipsBankTime;
	private String		ipsMsg;
	private String		attach;
	private int			orderStatus		= 0;
	private String		orderMessage;
	private String		accountTransactionId;
	private int			checkedStatus	= 0;
	private Timestamp	checkedTime;
	private String		checkedMessage;
	private String		reserve;
	private String loginName;
	private String mobilePhone;
	private String realName;
	
	
	public String getOrderId(){
		return orderId;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public int getAmount(){
		return amount;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public long getUserId(){
		return userId;
	}

	public void setUserId(long userId){
		this.userId = userId;
	}

	public int getOrderDate(){
		return orderDate;
	}

	public void setOrderDate(int orderDate){
		this.orderDate = orderDate;
	}

	public String getCurrencyType(){
		return currencyType;
	}

	public void setCurrencyType(String currencyType){
		this.currencyType = currencyType;
	}

	public String getGatewayType(){
		return gatewayType;
	}

	public void setGatewayType(String gatewayType){
		this.gatewayType = gatewayType;
	}

	public Timestamp getOrderTime(){
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime){
		this.orderTime = orderTime;
	}

	public byte getSuccFlag(){
		return succFlag;
	}

	public void setSuccFlag(byte succFlag){
		this.succFlag = succFlag;
	}

	public String getIpsBillNo(){
		return ipsBillNo;
	}

	public void setIpsBillNo(String ipsBillNo){
		this.ipsBillNo = ipsBillNo;
	}

	public String getIpsBankTime(){
		return ipsBankTime;
	}

	public void setIpsBankTime(String ipsBankTime){
		this.ipsBankTime = ipsBankTime;
	}

	public String getIpsMsg(){
		return ipsMsg;
	}

	public void setIpsMsg(String ipsMsg){
		this.ipsMsg = ipsMsg;
	}

	public String getAttach(){
		return attach;
	}

	public void setAttach(String attach){
		this.attach = attach;
	}

	public int getOrderStatus(){
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus){
		this.orderStatus = orderStatus;
	}

	public String getOrderMessage(){
		return orderMessage;
	}

	public void setOrderMessage(String orderMessage){
		this.orderMessage = orderMessage;
	}

	public String getAccountTransactionId(){
		return accountTransactionId;
	}

	public void setAccountTransactionId(String accountTransactionId){
		this.accountTransactionId = accountTransactionId;
	}

	public int getCheckedStatus(){
		return checkedStatus;
	}

	public void setCheckedStatus(int checkedStatus){
		this.checkedStatus = checkedStatus;
	}

	public Timestamp getCheckedTime(){
		return checkedTime;
	}

	public void setCheckedTime(Timestamp checkedTime){
		this.checkedTime = checkedTime;
	}

	public String getCheckedMessage(){
		return checkedMessage;
	}

	public void setCheckedMessage(String checkedMessage){
		this.checkedMessage = checkedMessage;
	}

	public String getReserve(){
		return reserve;
	}

	public void setReserve(String reserve){
		this.reserve = reserve;
	}

	
	public String getLoginName(){
		return loginName;
	}

	
	public void setLoginName(String loginName){
		this.loginName = loginName;
	}

	
	public String getRealName(){
		return realName;
	}

	
	public void setRealName(String realName){
		this.realName = realName;
	}

	
	public String getMobilePhone(){
		return mobilePhone;
	}

	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	
}
