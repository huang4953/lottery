/**
 * Title: LBAP_getCurrentTerm.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-3 下午05:36:26
 * @version V1.0
 */
package com.success.protocol.lbap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

/**
 * com.success.protocol.lbap
 * LBAP_getCurrentTerm.java
 * LBAP_getCurrentTerm
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-3 下午05:36:26
 * 
 */

public class LBAP_getCurrentTerm extends LBAP_DataPack {
	
	private String lotteryId;
	private String reserve;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_getCurrentTerm() {
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
	public LBAP_getCurrentTerm(String version, String command, String clientId,
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
	public LBAP_getCurrentTerm(String messageId, int encyptionType) {
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
		this.lotteryId = (node = document.selectSingleNode("/body/lotteryId")) == null ? null : node.getText();
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

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

}
