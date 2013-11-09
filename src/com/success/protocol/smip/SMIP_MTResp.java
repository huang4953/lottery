package com.success.protocol.smip;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;


public class SMIP_MTResp extends SMIP_DataPack {

	public SMIP_MTResp(byte[] b){
		super(b);
	}
	
	public SMIP_MTResp(SMIP_DataPack dp){
		super(new byte[SMIP_DataPack.SMIP_MT_RESP_LEN + 256]);
		this.setTotalLength(SMIP_DataPack.SMIP_MT_RESP_LEN + 256);
		this.setCommandID(SMIP_DataPack.SMIP_MT_RESP);
		this.setSequenceID(dp.getSequenceID());
		
		SMIP_MT mt = (SMIP_MT)dp;
		this.setStatus(mt.getRespStatus());
		this.setErrorDescription(mt.getErrStr().trim());
	}
	
	public void setStatus(int status){
		setInt(bMsg, 12, 4, status);
	}
	
	public int getStatus(){
		return getInt(bMsg, 12, 4);
	}
	
	public void setErrLength(int errLength){
		setInt(bMsg, 16, 1, errLength);
	}

	public int getErrLength(){
		return getInt(bMsg, 16, 1);
	}
	
	public void setErrorDescription(String errorDescription) {
		this.setErrorDescription(errorDescription.getBytes());
	}
	
	public void setErrorDescription(byte[] errorDescription) {
		setErrLength(errorDescription.length);
		setBytes(bMsg, 17, getErrLength(), errorDescription);
		this.setTotalLength(SMIP_DataPack.SMIP_MT_RESP_LEN + getErrLength());
	}

	public byte[] getErrorDescriptionSource(){
		return getBytes(bMsg, 17, getErrLength());
	}

	public String getErrorDescription(){
		try {
			return new String(getErrorDescriptionSource(), "GBK");
		} catch (UnsupportedEncodingException e) {
			return new String(getErrorDescriptionSource());
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_MTResp")
			.append("[")
			.append((new Timestamp(this.footprint)).toString())
			.append("][")
			.append(super.toString())
			.append("][")
			.append("SMIP_DataPack_Body{Status=")
			.append(this.getStatus())
			.append(",ErrLength=")
			.append(this.getErrLength())
			.append(",ErrorDescription=<")
			.append(this.getErrorDescription().trim())
			.append(">}]");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		SMIP_MT mt = new SMIP_MT(1);
		mt.setDefaults();
		mt.setMsgId("123456789");
		mt.setToPhone("13761874366");
		mt.setFeePhone("13761874366");
		mt.setSpNum("10662001");
		mt.setSpNumExt("");
		mt.setChannelId("99");
		mt.setLinkId("3456892332312");
		mt.setMsgContent("this is a test sms!好啊一个短消息sdlafkj");
		
		System.out.println(mt.toString());
		System.out.println("----------------------------------------------------");
		mt.dumpPack();
		System.out.println("----------------------------------------------------");
		
		
		SMIP_MTResp mtResp = new SMIP_MTResp(mt);
		mtResp.setStatus(3);
		mtResp.setErrorDescription("this is a error! 好厉害的错误啊！");
		System.out.println(mtResp.toString());
		System.out.println("----------------------------------------------------");
		mtResp.dumpPack();
		System.out.println("----------------------------------------------------");

	}
}
