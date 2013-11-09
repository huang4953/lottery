/**
 * Title: LBAP_betResp.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-4 下午03:32:00
 * @version V1.0
 */
package com.success.protocol.lbap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * com.success.protocol.lbap
 * LBAP_betResp.java
 * LBAP_betResp
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-4 下午03:32:00
 * 
 */

public class LBAP_betResp extends LBAP_DataPack {
	
	private String planId;
	private String termNo;
	private int betNum;
	private int betAmount;
	private String reserve;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_betResp() {
		// TODO 自动生成构造函数存根
	}

	/**
	 *Title: 
	 *Description: 
	 * @param messageId
	 * @param encyptionType
	 */
	public LBAP_betResp(String messageId, int encyptionType) {
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
	 * @see com.success.protocol.lbap.LBAP_DataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() throws Exception{
		String result = "";
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element bet = doc.addElement("bet");
		bet.addElement("planId").setText(this.getPlanId());
		bet.addElement("termNo").setText(this.getTermNo());
		bet.addElement("betNum").setText(String.valueOf(this.getBetNum()));
		bet.addElement("betAmount").setText(String.valueOf(this.getBetAmount()));
		bet.addElement("reserve").setText(convert(this.getReserve()));
		result = bet.asXML().replaceAll("<bet>", "").replaceAll("</bet>", "").replace("<bet/>", "");
		this.setMessageBody(result);
	}
	
	private String convert(String str){
		return str == null ? "" : str.trim();
	}
	
	public int getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
	}

	public int getBetNum() {
		return betNum;
	}

	public void setBetNum(int betNum) {
		this.betNum = betNum;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

}
