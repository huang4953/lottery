/**
 * Title: LBAP_getTermInfoResp.java
 * @Package com.success.protocol.lbap
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-4 ����02:20:07
 * @version V1.0
 */
package com.success.protocol.lbap;

/**
 * com.success.protocol.lbap
 * LBAP_getTermInfoResp.java
 * LBAP_getTermInfoResp
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-4 ����02:20:07
 * 
 */

public class LBAP_getTermInfoResp extends LBAP_DataPack {
	
	private LBAP_term lbapTerm;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_getTermInfoResp() {
		// TODO �Զ����ɹ��캯�����
	}

	/**
	 *Title: 
	 *Description: 
	 * @param messageId
	 * @param encyptionType
	 */
	public LBAP_getTermInfoResp(String messageId, int encyptionType) {
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
	 * @see com.success.protocol.lbap.LBAP_DataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() {
		String mess = this.getLbapTerm().encodeTermInfo();
		this.setMessageBody(mess);
	}

	public LBAP_term getLbapTerm() {
		return lbapTerm;
	}

	public void setLbapTerm(LBAP_term lbapTerm) {
		this.lbapTerm = lbapTerm;
	}

}
