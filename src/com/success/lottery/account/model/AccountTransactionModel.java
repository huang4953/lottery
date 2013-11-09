package com.success.lottery.account.model;

import java.sql.Timestamp;
public class AccountTransactionModel implements java.io.Serializable{

	/**
	 * �˻����ױ䶯��Ϣʵ����
	 * 
	 * @author suerguo
	 */
	private static final long	serialVersionUID	= 1L;
	private String				transactionId;				// ���ױ�ţ�Ψһ����ʽ��JYYYYYMMDDHHMISSnnnn
	private long				userId;						// �˻�Id
	private long				amount;
	private Timestamp			transactionTime;
	private int					transactionType;			// �ο�����״̬��ʽ���Ķ����˻�����״̬����
	private long				fundsAccount		= 0;
	private long				prizeAccount		= 0;
	private long				frozenAccount		= 0;
	private long				commisionAccount	= 0;
	private long				advanceAccount		= 0;
	private long				awardAccount		= 0;
	private long				otherAccount1		= 0;
	private long				otherAccount2		= 0;
	private int					sourceType;
	private String				sourceSequence;
	private String				remark;
	private String				reserve;
	private Timestamp			startTime;
	private Timestamp			endTime;
	private String				loginName;
	private String				mobilePhone;
	private String				realName;

	public long getAdvanceAccount(){
		return advanceAccount;
	}

	public void setAdvanceAccount(long advanceAccount){
		this.advanceAccount = advanceAccount;
	}

	public long getAmount(){
		return amount;
	}

	public void setAmount(long amount){
		this.amount = amount;
	}

	public long getAwardAccount(){
		return awardAccount;
	}

	public void setAwardAccount(long awardAccount){
		this.awardAccount = awardAccount;
	}

	public long getCommisionAccount(){
		return commisionAccount;
	}

	public void setCommisionAccount(long commisionAccount){
		this.commisionAccount = commisionAccount;
	}

	public long getFrozenAccount(){
		return frozenAccount;
	}

	public void setFrozenAccount(long frozenAccount){
		this.frozenAccount = frozenAccount;
	}

	public long getFundsAccount(){
		return fundsAccount;
	}

	public void setFundsAccount(long fundsAccount){
		this.fundsAccount = fundsAccount;
	}

	public long getOtherAccount1(){
		return otherAccount1;
	}

	public void setOtherAccount1(long otherAccount1){
		this.otherAccount1 = otherAccount1;
	}

	public long getOtherAccount2(){
		return otherAccount2;
	}

	public void setOtherAccount2(long otherAccount2){
		this.otherAccount2 = otherAccount2;
	}

	public long getPrizeAccount(){
		return prizeAccount;
	}

	public void setPrizeAccount(long prizeAccount){
		this.prizeAccount = prizeAccount;
	}

	public String getReserve(){
		return reserve;
	}

	public void setReserve(String reserve){
		this.reserve = reserve;
	}

	public String getSourceSequence(){
		return sourceSequence;
	}

	public void setSourceSequence(String sourceSequence){
		this.sourceSequence = sourceSequence;
	}

	public int getSourceType(){
		return sourceType;
	}

	public void setSourceType(int sourceType){
		this.sourceType = sourceType;
	}

	/**
	 * ���ױ�ţ�Ψһ����ʽ��JYYYYYMMDDHHMISSnnnn
	 * 
	 * @return
	 */
	public String getTransactionId(){
		return transactionId;
	}

	/**
	 * ���ױ�ţ�Ψһ����ʽ��JYYYYYMMDDHHMISSnnnn
	 * 
	 * @param transactionId
	 *            ���ױ��
	 */
	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public Timestamp getTransactionTime(){
		return transactionTime;
	}

	public void setTransactionTime(Timestamp transactionTime){
		this.transactionTime = transactionTime;
	}

	public int getTransactionType(){
		return transactionType;
	}

	public void setTransactionType(int transactionType){
		this.transactionType = transactionType;
	}

	public long getUserId(){
		return userId;
	}

	public void setUserId(long userId){
		this.userId = userId;
	}

	/**
	 * ��ӳ��ʵ�壬�˴����������ѯ����
	 * 
	 * @return
	 */
	public Timestamp getEndTime(){
		return endTime;
	}

	/**
	 * ��ӳ��ʵ�壬�˴����������ѯ����
	 * 
	 * @param endTime
	 */
	public void setEndTime(Timestamp endTime){
		this.endTime = endTime;
	}

	/**
	 * ��ӳ��ʵ�壬�˴�����ʼ��ѯ����
	 * 
	 * @return
	 */
	public Timestamp getStartTime(){
		return startTime;
	}

	/**
	 * ��ӳ��ʵ�壬�˴�����ʼ��ѯ����
	 * 
	 * @param startTime
	 */
	public void setStartTime(Timestamp startTime){
		this.startTime = startTime;
	}

	public String getRemark(){
		return remark;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	
	public String getLoginName(){
		return loginName;
	}

	
	public void setLoginName(String loginName){
		this.loginName = loginName;
	}

	
	public String getMobilePhone(){
		return mobilePhone;
	}

	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}

	
	public String getRealName(){
		return realName;
	}

	
	public void setRealName(String realName){
		this.realName = realName;
	}


}
