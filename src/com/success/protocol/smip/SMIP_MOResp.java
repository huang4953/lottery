package com.success.protocol.smip;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

//import com.success.sms.log.Log;


public class SMIP_MOResp extends SMIP_DataPack {

	public SMIP_MOResp(byte[] b){
		super(b);
	}
	
	public SMIP_MOResp(SMIP_DataPack dp){
		super(new byte[SMIP_DataPack.SMIP_MO_RESP_LEN + 256]);
		this.setTotalLength(SMIP_DataPack.SMIP_MO_RESP_LEN + 256);
		this.setCommandID(SMIP_DataPack.SMIP_MO_RESP);
		this.setSequenceID(dp.getSequenceID());
		
		SMIP_MO mo = (SMIP_MO) dp;
		//check SMIP_MO
		this.setStatus(mo.getRespStatus());
		this.setErrorDescription(mo.getErrStr().trim());
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
		this.setTotalLength(SMIP_DataPack.SMIP_MO_RESP_LEN + getErrLength());
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
			.append("SMIP_MOResp")
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
		SMIP_MO deliver = new SMIP_MO(1);
		deliver.setMsgId("123456");
		deliver.setFromPhone("13761874366");
		deliver.setSpNum("10662001");
		deliver.setSpNumExt("");
		deliver.setLinkId("324032432");
		deliver.setMsgContent("我爱sa北京天安门1213");
		deliver.setMsgContent("abcdalkrejwql;kjdsalf;jdsafasd");

		SMIP_MOResp deliverResp = new SMIP_MOResp(deliver);
//		deliverResp.setStatus(3);
//		deliverResp.setErrorDescription("this is a error! 好厉害的错误啊！");
//		deliverResp.setStatus(0);
//		deliverResp.setErrorDescription("");
		
		System.out.println(deliverResp.toString());
		System.out.println("----------------------------------------------------");
		deliverResp.dumpPack();
		System.out.println("----------------------------------------------------");
	}
}
