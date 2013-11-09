/**
 * Title: LBAP_getCurrentTermResp.java
 * @Package com.success.protocol.lbap
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-3 ����05:37:03
 * @version V1.0
 */
package com.success.protocol.lbap;

/**
 * com.success.protocol.lbap
 * LBAP_getCurrentTermResp.java
 * LBAP_getCurrentTermResp
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-3 ����05:37:03
 * 
 */

public class LBAP_getCurrentTermResp extends LBAP_DataPack {
	
	private LBAP_term lbapTerm;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_getCurrentTermResp() {
		// TODO �Զ����ɹ��캯�����
	}

	/**
	 *Title: 
	 *Description: 
	 * @param messageId
	 * @param encyptionType
	 */
	public LBAP_getCurrentTermResp(String messageId, int encyptionType) {
		super(messageId, encyptionType);
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
		String mess = this.getLbapTerm().encodeCurrent();
		this.setMessageBody(mess);
	}

	public LBAP_term getLbapTerm() {
		return lbapTerm;
	}

	public void setLbapTerm(LBAP_term lbapTerm) {
		this.lbapTerm = lbapTerm;
	}

}
