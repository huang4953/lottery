/**
 * Title: LBAP_draw.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-5 下午12:39:44
 * @version V1.0
 */
package com.success.protocol.lbap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

/**
 * com.success.protocol.lbap
 * LBAP_draw.java
 * LBAP_draw
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-5 下午12:39:44
 * 
 */

public class LBAP_draw extends LBAP_DataPack {
	
	private String userId;
	private String billNo;
	private String bank;
	private String bankProvince;
	private String bankCity;
	private String bankName;
	private String bankCardId;
	private String cardUserName;
	private String reason;
	private String reserve;
	private int amount;
	private int commission;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_draw() {
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
	public LBAP_draw(String version, String command, String clientId,
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
	public LBAP_draw(String messageId, int encyptionType) {
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
		Document document = DocumentHelper.parseText("<body>"+this.messageBody+"</body>");
		Node node = null;
		this.userId = (node = document.selectSingleNode("/body/userId")) == null ? null : node.getText();
		this.billNo = (node = document.selectSingleNode("/body/billNo")) == null ? null : node.getText();
		this.bank = (node = document.selectSingleNode("/body/bank")) == null ? null : node.getText();
		this.bankProvince = (node = document.selectSingleNode("/body/bankProvince")) == null ? null : node.getText();
		this.bankCity = (node = document.selectSingleNode("/body/bankCity")) == null ? null : node.getText();
		this.bankName = (node = document.selectSingleNode("/body/bankName")) == null ? null : node.getText();
		this.bankCardId = (node = document.selectSingleNode("/body/bankCardId")) == null ? null : node.getText();
		this.cardUserName = (node = document.selectSingleNode("/body/cardUserName")) == null ? null : node.getText();
		this.reason = (node = document.selectSingleNode("/body/reason")) == null ? null : node.getText();
		this.amount = (node = document.selectSingleNode("/body/amount")) == null ? -1 : Integer.parseInt(node.getText());
		this.commission = (node = document.selectSingleNode("/body/commission")) == null ? -1 : Integer.parseInt(node.getText());
		this.reserve = (node = document.selectSingleNode("/body/reserve")) == null ? null : node.getText();
	}

	/* (非 Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.lbap.LBAP_DataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() throws Exception {
		// TODO 自动生成方法存根

	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getCardUserName() {
		return cardUserName;
	}

	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}

	public int getCommission() {
		return commission;
	}

	public void setCommission(int commission) {
		this.commission = commission;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
