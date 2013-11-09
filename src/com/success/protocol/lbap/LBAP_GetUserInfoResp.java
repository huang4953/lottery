/**
 * Title: LBAP_GetUserInfoResp.java
 * @Package com.success.protocol.lbap
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-3 ����04:14:18
 * @version V1.0
 */
package com.success.protocol.lbap;

/**
 * com.success.protocol.lbap
 * LBAP_GetUserInfoResp.java
 * LBAP_GetUserInfoResp
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-3 ����04:14:18
 * 
 */

public class LBAP_GetUserInfoResp extends LBAP_DataPack {
	
	private LBAP_UserInfo userInfo;
	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_GetUserInfoResp() {
		// TODO �Զ����ɹ��캯�����
	}

	/**
	 *Title: 
	 *Description: 
	 * @param messageId
	 * @param encyptionType
	 */
	public LBAP_GetUserInfoResp(String messageId, int encyptionType) {
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
		String mess = this.getUserInfo().encode();
		this.setMessageBody(mess);
	}

	public LBAP_UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(LBAP_UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
