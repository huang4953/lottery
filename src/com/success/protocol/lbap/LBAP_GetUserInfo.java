/**
 * Title: LBAP_GetUserInfo.java
 * @Package com.success.protocol
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-3 下午04:13:51
 * @version V1.0
 */
package com.success.protocol.lbap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.success.protocol.lbap.LBAP_DataPack;

/**
 * com.success.protocol
 * LBAP_GetUserInfo.java
 * LBAP_GetUserInfo
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-3 下午04:13:51
 * 
 */

public class LBAP_GetUserInfo extends LBAP_DataPack {
	//private String userId =  "11";
	private String userId;
	private String reserve;
	
	

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_GetUserInfo() {
		super();
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
	public LBAP_GetUserInfo(String version, String command, String clientId, String messageId, int encyptionType, String md, String messageBody) {
		super(version, command, clientId, messageId, encyptionType, md, messageBody);
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

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
