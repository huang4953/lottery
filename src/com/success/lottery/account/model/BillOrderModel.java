package com.success.lottery.account.model;

import java.sql.Timestamp;

/**
 * 快钱支付
 * @author aaron.chen
 *
 */
public class BillOrderModel  {
	private int maxCount=0;
	private String orderId; //系统订单ID  主键
	private long userId;	//用户ID	
	private String userName; //用户名 用户名为空则用手机号代替	
	private Timestamp orderTime; //系统订单时间
	private String orderDate;//传给快钱的订单时间  格式yyyyMMddHHmmss
	private String accountTransactionId=null; //账户交易表ID
	private int orderStatus = 0; //订单状态  详细请查看文档与环讯中对应字段一直
	private String orderMessage = null;//订单备注
	private long orderAmount =0; //充值金额  详细请查看文档与环讯中对应字段一直
	private int checkedStatus =0; //对账状态
	private Timestamp checkedTime = null; //对账时间
	private String checkedMessage=null; //对账备注
	private String dealId=null; //快钱ID
	private String bankId= null; //银行代码，用户在实际交易的银行代码
	private int payResult=0; //快钱处理结果，0 代表从系统支付过去，等待返回结果10代表成功，11代表失败
	private String bankDealId=null;//实际银行交易号，如果不是通过银行支付则为空。
	private String dealTime =null; //快钱交易时间
	private String errCode =null; //错误代码  详细请查看快钱开发文档
	private long  fee=0;    // 快钱收取商户费用
	private String reserve=null; //备注
	private String ext1 =null;//扩展字段1
	private String ext2 =null;//扩展字段2
	private long payAmount=0;//用户实际支付金额
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getAccountTransactionId() {
		return accountTransactionId;
	}
	public void setAccountTransactionId(String accountTransactionId) {
		this.accountTransactionId = accountTransactionId;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderMessage() {
		return orderMessage;
	}
	public void setOrderMessage(String orderMessage) {
		this.orderMessage = orderMessage;
	}
	public long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(long orderAmount) {
		this.orderAmount = orderAmount;
	}
	public int getCheckedStatus() {
		return checkedStatus;
	}
	public void setCheckedStatus(int checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
	public Timestamp getCheckedTime() {
		return checkedTime;
	}
	public void setCheckedTime(Timestamp checkedTime) {
		this.checkedTime = checkedTime;
	}
	public String getCheckedMessage() {
		return checkedMessage;
	}
	public void setCheckedMessage(String checkedMessage) {
		this.checkedMessage = checkedMessage;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public int getPayResult() {
		return payResult;
	}
	public void setPayResult(int payResult) {
		this.payResult = payResult;
	}
	public String getBankDealId() {
		return bankDealId;
	}
	public void setBankDealId(String bankDealId) {
		this.bankDealId = bankDealId;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public long getFee() {
		return fee;
	}
	public void setFee(long fee) {
		this.fee = fee;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public long getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
}
