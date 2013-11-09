/**
 * Title: LBAP_Modify_UserResp.java
 * @Package com.success.protocol.lbap
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-2 下午02:18:01
 * @version V1.0
 */
package com.success.protocol.lbap;

/**
 * com.success.protocol.lbap
 * LBAP_Modify_UserResp.java
 * LBAP_Modify_UserResp
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-2 下午02:18:01
 * 
 */

public class LBAP_Modify_UserResp extends LBAP_DataPack {
	
	private LBAP_UserInfo userInfo;

	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_Modify_UserResp(String messageId,int encyptionType) {
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
	
	private String convert(String str){
		return str == null ? "" : str.trim();
	}

	public LBAP_UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(LBAP_UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
