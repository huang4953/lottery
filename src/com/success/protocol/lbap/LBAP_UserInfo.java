/**
 * Title: LBAP_UserInfo.java
 * @Package com.success.protocol.lbap
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-3 ����04:33:21
 * @version V1.0
 */
package com.success.protocol.lbap;

/**
 * com.success.protocol.lbap
 * LBAP_UserInfo.java
 * LBAP_UserInfo
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-3 ����04:33:21
 * 
 */

public class LBAP_UserInfo {
	
	private String userId;   //String	����	ע��ɹ�����û�Id���û�Ψһ��ʶ
	private String loginName;   //	String	ѡ��	��¼�û���
	private String mobilePhone;   //	String	ѡ��	�û��ֻ�����
	private String email;   //	String	ѡ��	�û�email��ַ
	private String areaCode;   //	String	ѡ��	�û�������룬�ο���������
	private String status;//	int	����	�û�״̬��1-������0-ע����2-����
	private String nickname;   //	String	ѡ��	�û��ǳ�
	private String phone;   //	String	ѡ��	�û��绰
	private String realName;   //	String	ѡ��	�û���ʵ����
	private String idCard;   //	String	ѡ��	�û�֤������
	private String bankName;   //	String	ѡ��	
	private String bankCardId;   //	String	ѡ��	
	private String address;   //	String	ѡ��	
	private String postcode;   //	String	ѡ��	
	private String qq;   //	String	ѡ��	
	private String msn;   //	String	ѡ��	
	private String sex;   //	int	ѡ��	
	private String birthday;   //	Date	ѡ��	
	private String lastLoginTime;   //	DateTime	ѡ��	
	private String lastLoginIp;   //	String	ѡ��	
	private String createTime;   //	DateTime	ѡ��	
	private String fundsAccount;   //	int	����	�û������˻�����λ��
	private String prizeAccount;   //	int	����	�û������˻�����λ��
	private String frozenAccount;   //	int	����	�û������˻�����λ��
	private String commisionAccount;   //	int	ѡ��	�û�Ӷ���˻�����λ�֣�Ԥ����
	private String advanceAccount;   //	int	ѡ��	��λ�֣�Ԥ��
	private String awardAccount;   //	int	ѡ��	��λ�֣�Ԥ��
	private String otherAccount1;   //	int	ѡ��	��λ�֣�Ԥ��
	private String otherAccount2;   //	int	ѡ��	��λ�֣�Ԥ��
	private String reserve;   //	String	ѡ��	����

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_UserInfo() {
		// TODO �Զ����ɹ��캯�����
	}
	
	public String encode(){
		StringBuffer sb = new StringBuffer();
		sb.append("<userId>").append(this.getUserId()).append("</userId>");
		sb.append("<loginName>").append(convert(this.getLoginName())).append("</loginName>");
		sb.append("<mobilePhone>").append(convert(this.getMobilePhone())).append("</mobilePhone>");
		sb.append("<email>").append(convert(this.getEmail())).append("</email>");
		sb.append("<areaCode>").append(convert(this.getAreaCode())).append("</areaCode>");
		sb.append("<status>").append(this.getStatus()).append("</status>");
		sb.append("<nickname>").append(convert(this.getNickname())).append("</nickname>");
		sb.append("<phone>").append(convert(this.getPhone())).append("</phone>");
		sb.append("<realName>").append(convert(this.getRealName())).append("</realName>");
		sb.append("<idCard>").append(convert(this.getIdCard())).append("</idCard>");
		sb.append("<bankName>").append(convert(this.getBankName())).append("</bankName>");
		sb.append("<bankCardId>").append(convert(this.getBankCardId())).append("</bankCardId>");
		sb.append("<address>").append(convert(this.getAddress())).append("</address>");
		sb.append("<postcode>").append(convert(this.getPostcode())).append("</postcode>");
		sb.append("<qq>").append(convert(this.getQq())).append("</qq>");
		sb.append("<msn>").append(convert(this.getMsn())).append("</msn>");
		sb.append("<sex>").append(this.getSex()).append("</sex>");
		sb.append("<birthday>").append(convert(this.getBirthday())).append("</birthday>");
		sb.append("<lastLoginTime>").append(convert(this.getLastLoginTime())).append("</lastLoginTime>");
		sb.append("<lastLoginIp>").append(convert(this.getLastLoginIp())).append("</lastLoginIp>");
		sb.append("<createTime>").append(convert(this.getCreateTime())).append("</createTime>");
		sb.append("<fundsAccount>").append(this.getFundsAccount()).append("</fundsAccount>");
		sb.append("<prizeAccount>").append(this.getPrizeAccount()).append("</prizeAccount>");
		sb.append("<frozenAccount>").append(this.getFrozenAccount()).append("</frozenAccount>");
		sb.append("<commisionAccount>").append(convert(this.getCommisionAccount())).append("</commisionAccount>");
		sb.append("<advanceAccount>").append(convert(this.getAdvanceAccount())).append("</advanceAccount>");
		sb.append("<awardAccount>").append(convert(this.getAwardAccount())).append("</awardAccount>");
		sb.append("<otherAccount1>").append(convert(this.getOtherAccount1())).append("</otherAccount1>");
		sb.append("<otherAccount2>").append(convert(this.getOtherAccount2())).append("</otherAccount2>");
		sb.append("<reserve>").append(convert(this.getReserve())).append("</reserve>");
		return sb.toString();
	}
	private String convert(String str){
		return str == null ? "" : str.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdvanceAccount() {
		return advanceAccount;
	}

	public void setAdvanceAccount(String advanceAccount) {
		this.advanceAccount = advanceAccount;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAwardAccount() {
		return awardAccount;
	}

	public void setAwardAccount(String awardAccount) {
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCommisionAccount() {
		return commisionAccount;
	}

	public void setCommisionAccount(String commisionAccount) {
		this.commisionAccount = commisionAccount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFrozenAccount() {
		return frozenAccount;
	}

	public void setFrozenAccount(String frozenAccount) {
		this.frozenAccount = frozenAccount;
	}

	public String getFundsAccount() {
		return fundsAccount;
	}

	public void setFundsAccount(String fundsAccount) {
		this.fundsAccount = fundsAccount;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getOtherAccount1() {
		return otherAccount1;
	}

	public void setOtherAccount1(String otherAccount1) {
		this.otherAccount1 = otherAccount1;
	}

	public String getOtherAccount2() {
		return otherAccount2;
	}

	public void setOtherAccount2(String otherAccount2) {
		this.otherAccount2 = otherAccount2;
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

	public String getPrizeAccount() {
		return prizeAccount;
	}

	public void setPrizeAccount(String prizeAccount) {
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

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
