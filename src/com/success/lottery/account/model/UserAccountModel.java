package com.success.lottery.account.model;

import java.sql.Timestamp;

public class UserAccountModel implements java.io.Serializable {

	/**
	 * �û��˻���Ϣʵ����
	 * @author suerguo
	 */
	private static final long serialVersionUID = 1L;
	private long userId;      
	private String loginName;//*�����ڵ�¼�������ظ�������ֱ��ʹ���ֻ�����
	private String mobilePhone;//*�����ڵ�¼��ҵ��Ҫ�����
	private boolean bindMobileFlag;//�Ƿ�����ֻ� false
	private String areaCode;//*�������-ʡ�ݴ��룬�����ֻ��źŶεõ����������ο�����״̬�����ĵ�AreaMap.properties����
	private int userLevel;//Ԥ���û�����
	private int status;//�û�״̬��1-���� 0-ע�� 2-����
	private String nickName;//��ʾ��ҳ������ƣ���ѡ����δ������ʾ�ֻ����룬��δ���ֻ�������ʾ�˻�Id
	private String password;//*6-16λ��ĸ���ֻ�ϣ�Ҫ��ȫ�ԣ����ֶ�Ҫ���ܴ��
	private String phone;//�̶��绰��ѡ��
	private String email;//ѡ���֤������ڵ�¼�������һ�
	private boolean bindEmailFlag;//�Ƿ��������
	private String relationSales;//Ԥ��ҵ��ͳ����
	private String relationUser;//Ԥ��
	private long fundsAccount;//ͨ�����ַ�ʽ��ֵ�����ı����˻����
	private long prizeAccount;//�н���Ľ����˻����
	private long frozenAccount;//����ԭ�򶳽���˻����
	private long commisionAccount;//Ԥ��������ʱ���ã������Ӷ��ֱ�ӽ������˻�
	private long advanceAccount;//Ԥ��
	private long awardAccount;//Ԥ��
	private long otherAccount1;//Ԥ��
	private long otherAccount2;//Ԥ��
	private String realName;//��ʵ��������������д
	private String idCard;//���֤���룬��������д
	private String bankName;//��������д
	private String bankCardId;//��������д
	private String address;//ͨѶ��ַ����ȡԭʼ��Ʊ����
	private String postcode;//�������룬��ȡԭʼ��Ʊ����
	private String qq;//
	private String msn;//
	private int sex;
	private Timestamp birthday;
	private Timestamp lastLoginTime;
	private String lastLoginIP;
	private Timestamp createTime;
	private String reserve;
	private String newpassword;//������
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
	 * ��ʵ���ӳ���˴�Ϊ�޸�����ʱʹ��
	 * @return
	 */
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	/**
	 * ��ʵ���ӳ���˴�Ϊ�Ӽ�����ʼ��
	 * @return
	 */
	public int getFirstResult() {
		return firstResult;
	}
	/**
	 * ��ʵ���ӳ���˴�Ϊ�Ӽ�����ʼ��
	 * @return
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	/**
	 * ��ʵ���ӳ���˴�Ϊ�Ӽ�������
	 * @return
	 */
	public int getMaxResult() {
		return maxResult;
	}
	/**
	 * ��ʵ���ӳ���˴�Ϊ�Ӽ�������
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
