package com.success.lottery.account.model;

import java.sql.Timestamp;

/**
 * ��Ǯ֧��
 * @author aaron.chen
 *
 */
public class BillOrderModel  {
	private int maxCount=0;
	private String orderId; //ϵͳ����ID  ����
	private long userId;	//�û�ID	
	private String userName; //�û��� �û���Ϊ�������ֻ��Ŵ���	
	private Timestamp orderTime; //ϵͳ����ʱ��
	private String orderDate;//������Ǯ�Ķ���ʱ��  ��ʽyyyyMMddHHmmss
	private String accountTransactionId=null; //�˻����ױ�ID
	private int orderStatus = 0; //����״̬  ��ϸ��鿴�ĵ��뻷Ѷ�ж�Ӧ�ֶ�һֱ
	private String orderMessage = null;//������ע
	private long orderAmount =0; //��ֵ���  ��ϸ��鿴�ĵ��뻷Ѷ�ж�Ӧ�ֶ�һֱ
	private int checkedStatus =0; //����״̬
	private Timestamp checkedTime = null; //����ʱ��
	private String checkedMessage=null; //���˱�ע
	private String dealId=null; //��ǮID
	private String bankId= null; //���д��룬�û���ʵ�ʽ��׵����д���
	private int payResult=0; //��Ǯ��������0 �����ϵͳ֧����ȥ���ȴ����ؽ��10����ɹ���11����ʧ��
	private String bankDealId=null;//ʵ�����н��׺ţ��������ͨ������֧����Ϊ�ա�
	private String dealTime =null; //��Ǯ����ʱ��
	private String errCode =null; //�������  ��ϸ��鿴��Ǯ�����ĵ�
	private long  fee=0;    // ��Ǯ��ȡ�̻�����
	private String reserve=null; //��ע
	private String ext1 =null;//��չ�ֶ�1
	private String ext2 =null;//��չ�ֶ�2
	private long payAmount=0;//�û�ʵ��֧�����
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
