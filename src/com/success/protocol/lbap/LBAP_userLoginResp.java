/**
 * Title: LBAP_userLoginResp.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-2 下午05:51:05
 * @version V1.0
 */
package com.success.protocol.lbap;

/**
 * com.success.protocol.lbap
 * LBAP_userLoginResp.java
 * LBAP_userLoginResp
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-2 下午05:51:05
 * 
 */

public class LBAP_userLoginResp extends LBAP_DataPack {
	private LBAP_UserInfo userInfo;
	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_userLoginResp(String messageId,int encyptionType) {
		this.setMessageId(messageId);
		this.setEncryptionType(encyptionType);
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
