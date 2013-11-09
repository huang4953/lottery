package com.success.lottery.account.model;

import java.sql.Timestamp;

public class UserAccountModel implements java.io.Serializable {

	/**
	 * 用户账户信息实体类
	 * @author suerguo
	 */
	private static final long serialVersionUID = 1L;
	private long userId;      
	private String loginName;//*可用于登录，不能重复；可以直接使用手机号码
	private String mobilePhone;//*可用于登录，业务要求必填
	private boolean bindMobileFlag;//是否绑定了手机 false
	private String areaCode;//*区域代码-省份代码，根据手机号号段得到，区域代码参考数据状态定义文档AreaMap.properties部分
	private int userLevel;//预留用户级别
	private int status;//用户状态，1-正常 0-注销 2-冻结
	private String nickName;//显示在页面的名称，可选，如未填则显示手机号码，如未填手机号码显示账户Id
	private String password;//*6-16位字母数字混合，要求安全性；此字段要加密存放
	private String phone;//固定电话、选填
	private String email;//选填，验证后可用于登录或密码找回
	private boolean bindEmailFlag;//是否绑定了邮箱
	private String relationSales;//预留业务统计用
	private String relationUser;//预留
	private long fundsAccount;//通过各种方式充值进来的本金账户金额
	private long prizeAccount;//中奖后的奖金账户金额
	private long frozenAccount;//各种原因冻结的账户金额
	private long commisionAccount;//预留，合买时设置，暂设计佣金直接进奖金账户
	private long advanceAccount;//预留
	private long awardAccount;//预留
	private long otherAccount1;//预留
	private long otherAccount2;//预留
	private String realName;//真实姓名，提款必须填写
	private String idCard;//身份证号码，提款必须填写
	private String bankName;//提款必须填写
	private String bankCardId;//提款必须填写
	private String address;//通讯地址，获取原始彩票必填
	private String postcode;//邮政编码，获取原始彩票必填
	private String qq;//
	private String msn;//
	private int sex;
	private Timestamp birthday;
	private Timestamp lastLoginTime;
	private String lastLoginIP;
	private Timestamp createTime;
	private String reserve;
	private String newpassword;//新密码
	private int firstResult;
	private int maxResult;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getAdvanceAccount() {
		return advanceAccount;
	}
	public void setAdvanceAccount(long advanceAccount) {
		this.advanceAccount = advanceAccount;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public long getAwardAccount() {
		return awardAccount;
	}
	public void setAwardAccount(long awardAccount) {
		this.awardAccount = awardAccount;
	}
	public String getBankCardId() {
		return bankCardId;
	}
	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Timestamp getBirthday() {
		return birthday;
	}
	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}
	public long getCommisionAccount() {
		return commisionAccount;
	}
	public void setCommisionAccount(long commisionAccount) {
		this.commisionAccount = commisionAccount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getFrozenAccount() {
		return frozenAccount;
	}
	public void setFrozenAccount(long frozenAccount) {
		this.frozenAccount = frozenAccount;
	}
	public long getFundsAccount() {
		return fundsAccount;
	}
	public void setFundsAccount(long fundsAccount) {
		this.fundsAccount = fundsAccount;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public long getOtherAccount1() {
		return otherAccount1;
	}
	public void setOtherAccount1(long otherAccount1) {
		this.otherAccount1 = otherAccount1;
	}
	public long getOtherAccount2() {
		return otherAccount2;
	}
	public void setOtherAccount2(long otherAccount2) {
		this.otherAccount2 = otherAccount2;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public long getPrizeAccount() {
		return prizeAccount;
	}
	public void setPrizeAccount(long prizeAccount) {
		this.prizeAccount = prizeAccount;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRelationSales() {
		return relationSales;
	}
	public void setRelationSales(String relationSales) {
		this.relationSales = relationSales;
	}
	public String getRelationUser() {
		return relationUser;
	}
	public void setRelationUser(String relationUser) {
		this.relationUser = relationUser;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * 非实体对映，此处为修改密码时使用
	 * @return
	 */
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	/**
	 * 非实体对映，此处为从几条开始查
	 * @return
	 */
	public int getFirstResult() {
		return firstResult;
	}
	/**
	 * 非实体对映，此处为从几条开始查
	 * @return
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	/**
	 * 非实体对映，此处为从几条结束
	 * @return
	 */
	public int getMaxResult() {
		return maxResult;
	}
	/**
	 * 非实体对映，此处为从几条结束
	 * @return
	 */
	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	public boolean isBindEmailFlag() {
		return bindEmailFlag;
	}
	public void setBindEmailFlag(boolean bindEmailFlag) {
		this.bindEmailFlag = bindEmailFlag;
	}
	public boolean isBindMobileFlag() {
		return bindMobileFlag;
	}
	public void setBindMobileFlag(boolean bindMobileFlag) {
		this.bindMobileFlag = bindMobileFlag;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public Timestamp getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Timestamp createTime){
		this.createTime = createTime;
	}
	
}
