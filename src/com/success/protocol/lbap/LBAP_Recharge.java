/**
 * Title: LBAP_Recharge.java
 * @Package com.success.protocol
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-5 ����12:38:43
 * @version V1.0
 */
package com.success.protocol.lbap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.success.protocol.lbap.LBAP_DataPack;

/**
 * com.success.protocol
 * LBAP_Recharge.java
 * LBAP_Recharge
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-5 ����12:38:43
 * 
 */

public class LBAP_Recharge extends LBAP_DataPack {
	
	private String userId;
	private String billNo;
	private int amount;
	private int commission;
	private String reserve;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_Recharge() {
		// TODO �Զ����ɹ��캯�����
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
	public LBAP_Recharge(String version, String command, String clientId,
			String messageId, int encyptionType, String md, String messageBody) {
		super(version, command, clientId, messageId, encyptionType, md,
				messageBody);
		// TODO �Զ����ɹ��캯�����
	}

	/**
	 *Title: 
	 *Description: 
	 * @param messageId
	 * @param encyptionType
	 */
	public LBAP_Recharge(String messageId, int encyptionType) {
		super(messageId, encyptionType);
		// TODO �Զ����ɹ��캯�����
	}

	/* (�� Javadoc)
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
		this.amount = (node = document.selectSingleNode("/body/amount")) == null ? -1 : Integer.parseInt(node.getText());
		this.commission = (node = document.selectSingleNode("/body/commission")) == null ? -1 : Integer.parseInt(node.getText());
		this.reserve = (node = document.selectSingleNode("/body/reserve")) == null ? null : node.getText();
	}

	/* (�� Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.lbap.LBAP_DataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() throws Exception {
		// TODO �Զ����ɷ������

	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public int getCommission() {
		return commission;
	}

	public void setCommission(int commission) {
		this.commission = commission;
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
