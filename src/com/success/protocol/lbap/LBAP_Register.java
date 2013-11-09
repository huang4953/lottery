package com.success.protocol.lbap;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;



public class LBAP_Register extends LBAP_DataPack{

	private String mobilePhone;//必填项
	private String password;
	private String loginName;
	private String email;
	private String nickName;
	private String realName;
	private String idCard;
	
	

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
	public LBAP_Register(String version, String command, String clientId, String messageId, int encyptionType, String md, String messageBody) {
		super(version, command, clientId, messageId, encyptionType, md, messageBody);
		// TODO 自动生成构造函数存根
	}



	@Override
	public void decodeMessageBody() throws IOException, Exception {
		System.out.println("decodeMessageBody==="+this.messageBody);
		Document document = DocumentHelper.parseText("<body>"+this.messageBody+"</body>");
		Node node = null;
		this.mobilePhone = (node = document.selectSingleNode("/body/mobilePhone")) == null ? null
				: node.getText();
		System.out.println("mobilePhone===="+mobilePhone);
		this.password = (node = document.selectSingleNode("/body/password")) == null ? null
				: node.getText();
		this.loginName = (node = document.selectSingleNode("/body/loginName")) == null ? null
				: node.getText();
		this.email = (node = document.selectSingleNode("/body/email")) == null ? null
				: node.getText();
		this.nickName = (node = document.selectSingleNode("/body/nickName")) == null ? null
				: node.getText();
		this.realName = (node = document.selectSingleNode("/body/realName")) == null ? null
				: node.getText();
		this.idCard = (node = document.selectSingleNode("/body/idCard")) == null ? null
				: node.getText();
	}
	
	

	@Override
	public void encodeMessageBody(){
		this.messageBody = "";
		// TODO Auto-generated method stub
		
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
