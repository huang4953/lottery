/**
 * Title: LBAP_Modify_User.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-2 下午02:17:23
 * @version V1.0
 */
package com.success.protocol.lbap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

/**
 * com.success.protocol.lbap
 * LBAP_Modify_User.java
 * LBAP_Modify_User
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-2 下午02:17:23
 * 
 */

public class LBAP_Modify_User extends LBAP_DataPack {
	
	private String userId;//	String	必填	用户Id，用户唯一标识
	private String password;//	String	选填	用户密码
	private String nickname;//	String	选填	用户昵称
	private String phone;//	String	选填	电话号码
	private String email;//	String	选填	邮件地址
	private String realName;//	String	选填	真实姓名
	private String idCard;//	String	选填	证件号码
	private String bankName;//	String	选填	银行名称
	private String bankCardId;//	String	选填	银行账号
	private String address;//	String	选填	地址
	private String postcode;//	String	选填	邮编
	private String qq;//	String	选填	
	private String msn;//	String	选填	
	private String sex;//	int	选填	性别
	private String birthday;//	Date	选填	出生日期
	private String reserve;//	String	选填	保留

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_Modify_User() {
		// TODO 自动生成构造函数存根
	}
	
	

	/**
	 *Title: 
	 *Description: 
	 * @param version
	 * @param command
	 * @param clientId
	 * @param messageId
	 * @param encyptionType
	 * @param md
	 * @param messageBody
	 */
	public LBAP_Modify_User(String version, String command, String clientId, String messageId, int encyptionType, String md, String messageBody) {
		super(version, command, clientId, messageId, encyptionType, md, messageBody);
		// TODO 自动生成构造函数存根
	}



	/* (非 Javadoc)
	 *Title: decodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.lbap.LBAP_DataPack#decodeMessageBody()
	 */
	@Override
	public void decodeMessageBody() throws Exception {
		Document document = DocumentHelper.parseText("<body>"+this.messageBody+"</body>");
		Node node = null;
		this.userId = (node = document.selectSingleNode("/body/userId")) == null ? null : node.getText();
		this.password = (node = document.selectSingleNode("/body/password")) == null ? null : node.getText();
		this.nickname = (node = document.selectSingleNode("/body/nickname")) == null ? null : node.getText();
		this.phone = (node = document.selectSingleNode("/body/phone")) == null ? null : node.getText();
		this.email = (node = document.selectSingleNode("/body/email")) == null ? null : node.getText();
		this.realName = (node = document.selectSingleNode("/body/realName")) == null ? null : node.getText();
		this.idCard = (node = document.selectSingleNode("/body/idCard")) == null ? null : node.getText();
		this.bankCardId = (node = document.selectSingleNode("/body/bankCardId")) == null ? null : node.getText();
		this.address = (node = document.selectSingleNode("/body/address")) == null ? null : node.getText();
		this.postcode = (node = document.selectSingleNode("/body/postcode")) == null ? null : node.getText();
		this.qq = (node = document.selectSingleNode("/body/qq")) == null ? null : node.getText();
		this.msn = (node = document.selectSingleNode("/body/msn")) == null ? null : node.getText();
		this.sex = (node = document.selectSingleNode("/body/sex")) == null ? null : node.getText();
		this.birthday = (node = document.selectSingleNode("/body/birthday")) == null ? null : node.getText();
		this.reserve = (node = document.selectSingleNode("/body/reserve")) == null ? null : node.getText();
	}

	/* (非 Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @see com.success.protocol.lbap.LBAP_DataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() {
		// TODO 自动生成方法存根

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
