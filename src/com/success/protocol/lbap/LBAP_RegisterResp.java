package com.success.protocol.lbap;


public class LBAP_RegisterResp extends LBAP_DataPack{
	private LBAP_UserInfo userInfo;
	
	public LBAP_RegisterResp(LBAP_DataPack dataPack){
		this.setEncryptionType(dataPack.encryptionType);
		this.setMessageId(dataPack.getMessageId());
	}
	
	public LBAP_RegisterResp(String messageId,int encyptionType){
		this.setMessageId(messageId);
		this.setEncryptionType(encyptionType);
	}

	@Override
	public void decodeMessageBody(){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encodeMessageBody(){
		String mess = this.getUserInfo().encode();
		this.setMessageBody(mess);
	}

	public LBAP_UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(LBAP_UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	
}
