package com.success.lottery.ticket.domain;

import java.io.Serializable;

public class BetTicketAcountDomain extends BetTicketDomain implements Serializable {
	/*
	 * 用户信息
	 */
	private long userId; // bigint not null,
	private String loginName;//*可用于登录，不能重复；可以直接使用手机号码
	private String mobilePhone;//*可用于登录，业务要求必填
	private String nickName;//显示在页面的名称，可选，如未填则显示手机号码，如未填手机号码显示账户Id
	private String realName;//真实姓名，提款必须填写
	private int planSource = 0; // int comment '投注方案是从那个渠道来的，0-WEB，1-SMS，2-WAP',
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
