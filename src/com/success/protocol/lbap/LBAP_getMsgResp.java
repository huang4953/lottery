/**
 * Title: LBAP_getMsgResp.java
 * @Package com.success.protocol.lbap
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-5 ����09:40:53
 * @version V1.0
 */
package com.success.protocol.lbap;

/**
 * com.success.protocol.lbap
 * LBAP_getMsgResp.java
 * LBAP_getMsgResp
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-5 ����09:40:53
 * 
 */

public class LBAP_getMsgResp extends LBAP_DataPack {
	private String msgResult;
	private String oldMsg;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_getMsgResp() {
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
	public LBAP_getMsgResp(String version, String command, String clientId,
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
	public LBAP_getMsgResp(String messageId, int encyptionType) {
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
		// TODO �Զ����ɷ������

	}

	/* (�� Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.lbap.LBAP_DataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<msgResult>").append(this.getMsgResult()).append("</msgResult>");
		sb.append(this.getOldMsg());
		this.setMessageBody(sb.toString());

	}

	public String getMsgResult() {
		return msgResult;
	}

	public void setMsgResult(String msgResult) {
		this.msgResult = msgResult;
	}

	public String getOldMsg() {
		return oldMsg;
	}

	public void setOldMsg(String oldMsg) {
		this.oldMsg = oldMsg;
	}

}
