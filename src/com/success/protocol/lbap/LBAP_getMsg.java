/**
 * Title: LBAP_getMsg.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-5 下午09:40:27
 * @version V1.0
 */
package com.success.protocol.lbap;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

/**
 * com.success.protocol.lbap
 * LBAP_getMsg.java
 * LBAP_getMsg
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-5 下午09:40:27
 * 
 */

public class LBAP_getMsg extends LBAP_DataPack {
	
	private String operateCommand;
	private String operateMessageId;
	private String reserve;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_getMsg() {
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
	public LBAP_getMsg(String version, String command, String clientId,
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
	public LBAP_getMsg(String messageId, int encyptionType) {
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
		this.operateCommand = (node = document.selectSingleNode("/body/operateCommand")) == null ? null : node.getText();
		this.operateMessageId = (node = document.selectSingleNode("/body/operateMessageId")) == null ? null : node.getText();
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

	public String getOperateCommand() {
		return operateCommand;
	}

	public void setOperateCommand(String operateCommand) {
		this.operateCommand = operateCommand;
	}

	public String getOperateMessageId() {
		return operateMessageId;
	}

	public void setOperateMessageId(String operateMessageId) {
		this.operateMessageId = operateMessageId;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

}
