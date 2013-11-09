/**
 * Title: LBAP_drawResp.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-5 下午12:40:08
 * @version V1.0
 */
package com.success.protocol.lbap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * com.success.protocol.lbap
 * LBAP_drawResp.java
 * LBAP_drawResp
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-5 下午12:40:08
 * 
 */

public class LBAP_drawResp extends LBAP_DataPack {
	
	private String orderId;
	private String billNo;
	private String userId;
	private int prizeAccount;
	private String reserve;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_drawResp() {
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
	public LBAP_drawResp(String version, String command, String clientId,
			String messageId, int encyptionType, String md, String messageBody) {
		super(version, command, clientId, messageId, encyptionType, md,
				messageBody);
		// TODO 自动生成构造函数存根
	}

	/**
	 *Title: 
	 *Description: 
	 * @param messageId
	 * @param encyptionType
	 */
	public LBAP_drawResp(String messageId, int encyptionType) {
		super(messageId, encyptionType);
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
		// TODO 自动生成方法存根

	}

	/* (非 Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.lbap.LBAP_DataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() throws Exception {
		String result = "";
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element Info = doc.addElement("Info");
		
		Info.addElement("orderId").setText(this.getOrderId());
		Info.addElement("billNo").setText(convert(this.getBillNo()));
		Info.addElement("userId").setText(this.getUserId());
		Info.addElement("prizeAccount").setText(String.valueOf(this.getPrizeAccount()));
		Info.addElement("reserve").setText(convert(this.getReserve()));
		result = Info.asXML().replaceAll("<Info>", "").replaceAll("</Info>", "").replace("<Info/>", "");
		
		this.setMessageBody(result);
	}
	
	private String convert(String str){
		return str == null ? "" : str.trim();
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getPrizeAccount() {
		return prizeAccount;
	}

	public void setPrizeAccount(int prizeAccount) {
		this.prizeAccount = prizeAccount;
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
