/**
 * Title: LBAP_userLogin.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-2 下午05:49:31
 * @version V1.0
 */
package com.success.protocol.lbap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

/**
 * com.success.protocol.lbap
 * LBAP_userLogin.java
 * LBAP_userLogin
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-2 下午05:49:31
 * 
 */

public class LBAP_userLogin extends LBAP_DataPack {
	
	private String userIdentify;//	String	必填	用户标识，可以是手机号码，登录名或email地址
	private String password;//	String	必填	用户密码
	private String lastLoginIp;//	String	选填	登录IP地址
	
	

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
	public LBAP_userLogin(String version, String command, String clientId, String messageId, int encyptionType, String md, String messageBody) {
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
		this.userIdentify = (node = document.selectSingleNode("/body/userIdentify")) == null ? null : node.getText();
		this.password = (node = document.selectSingleNode("/body/password")) == null ? null : node.getText();
		this.lastLoginIp = (node = document.selectSingleNode("/body/lastLoginIp")) == null ? null : node.getText();
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

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserIdentify() {
		return userIdentify;
	}

	public void setUserIdentify(String userIdentify) {
		this.userIdentify = userIdentify;
	}

}
