package com.success.lottery.ticket.domain;

import java.io.Serializable;

public class BetTicketAcountDomain extends BetTicketDomain implements Serializable {
	/*
	 * �û���Ϣ
	 */
	private long userId; // bigint not null,
	private String loginName;//*�����ڵ�¼�������ظ�������ֱ��ʹ���ֻ�����
	private String mobilePhone;//*�����ڵ�¼��ҵ��Ҫ�����
	private String nickName;//��ʾ��ҳ������ƣ���ѡ����δ������ʾ�ֻ����룬��δ���ֻ�������ʾ�˻�Id
	private String realName;//��ʵ��������������д
	private int planSource = 0; // int comment 'Ͷע�����Ǵ��Ǹ��������ģ�0-WEB��1-SMS��2-WAP',
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public int getPlanSource() {
		return planSource;
	}
	public void setPlanSource(int planSource) {
		this.planSource = planSource;
	}
}
