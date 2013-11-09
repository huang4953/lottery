package com.success.protocol.smip;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

public class SMIP_MT extends SMIP_DataPack {
	public SMIP_MT(byte[] b){
		super(b);
	}

	public SMIP_MT(int seq){
		super(new byte[SMIP_DataPack.SMIP_MT_LEN + 1024]);
		this.setTotalLength(SMIP_DataPack.SMIP_MT_LEN + 1024);
		this.setCommandID(SMIP_DataPack.SMIP_MT);
		this.setSequenceID(seq);
	}

	public void setMsgId(String msgId){
		setBytes(bMsg, 12, 16, msgId.getBytes());
	}
	
	public String getMsgId(){
		return new String(bMsg, 12, 16);
	}
	
	public void setPkTotal(int pkTotal){
		setInt(bMsg, 28, 1, pkTotal);
	}
	
	public int getPkTotal(){
		return getInt(bMsg, 28, 1);
	}
	
	public void setPkNumber(int pkNumber){
		setInt(bMsg, 29, 1, pkNumber);
	}
	
	public int getPkNumber(){
		return getInt(bMsg, 29, 1);
	}
	
	public void setToPhone(String toPhone){
		setBytes(bMsg, 30, 38, toPhone.getBytes());
	}
	
	public String getToPhone(){
		return new String(bMsg, 30, 38);
	}
	
	public void setFeePhone(String feePhone){
		setBytes(bMsg, 68, 38, feePhone.getBytes());
	}
	
	public String getFeePhone(){
		return new String(bMsg, 68, 38);
	}
	
	public void setSpNum(String spNum){
		setBytes(bMsg, 106, 21, spNum.getBytes());
	}
	
	public String getSpNum(){
		return new String(bMsg, 106, 21);
	}
	
	public void setSpNumExt(String spNumExt){
		setBytes(bMsg, 127, 21, spNumExt.getBytes());
	}
	
	public String getSpNumExt(){
		return new String(bMsg, 127, 21);
	}
	
	public void setChannelId(String channelId){
		setBytes(bMsg, 148, 21, channelId.getBytes());
	}
	
	public String getChannelId(){
		return new String(bMsg, 148, 21);
	}
	
	public void setType(int type){
		setInt(bMsg, 169, 1, type);
	}
	
	public int getType(){
		return getInt(bMsg, 169, 1);
	}
	
	public void setLinkId(String linkId){
		setBytes(bMsg, 170, 32, linkId.getBytes());
	}
	
	public String getLinkId(){
		return new String(bMsg, 170, 32);
	}
	
	public void setMsgLength(int msgLength){
		setInt(bMsg, 202, 2, msgLength);
	}
	
	public int getMsgLength(){
		return getInt(bMsg, 202, 2);
	}
	
	public void setMsgContent(String msgContent) {
		setMsgContent(msgContent.getBytes());
	}

	public void setMsgContent(byte[] msgContent) {
		setMsgLength(msgContent.length);
		setBytes(bMsg, 204, getMsgLength(), msgContent);
		setTotalLength(SMIP_DataPack.SMIP_MT_LEN + getMsgLength());
	}

	public byte[] getMsgContentSource() {
		return getBytes(bMsg, 204, getMsgLength());
	}

	public String getMsgContent() {
		try {
			return new String(getMsgContentSource(), "GBK");
		} catch (UnsupportedEncodingException e) {
			return "Unsupported Encoding: " + e.toString();
		}
	}

	public void setReserve(String reserve) {
		setBytes(bMsg, 204 + getMsgLength(), 32, reserve.getBytes());
	}

	public String getReserve() {
		return new String(bMsg, 204 + getMsgLength(), 32);
	}
	
	public void setDefaults(){
		this.setPkTotal(1);
		this.setPkNumber(1);
		this.setType(1);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_MT")
			.append("[")
			.append((new Timestamp(this.footprint)).toString())
			.append("][")
			.append(super.toString())
			.append("][").append("SMIP_DataPack_Body{MsgId=")
			.append(this.getMsgId().trim())
			.append(",FromPhone=")
			.append(",PkTotal=")
			.append(this.getPkTotal())
			.append(",PkNumber=")
			.append(this.getPkNumber())
			.append(",ToPhone=")
			.append(this.getToPhone().trim())
			.append(",FeePhone=")
			.append(this.getFeePhone().trim())
			.append(",SpNum=")
			.append(this.getSpNum().trim())
			.append(",SpNumExt=")
			.append(this.getSpNumExt().trim())
			.append(",ChannelId=")
			.append(this.getChannelId().trim())
			.append(",Type=")
			.append(this.getType())
			.append(",LinkId=")
			.append(this.getLinkId().trim())
			.append(",MsgLength=")
			.append(this.getMsgLength())
			.append(",MsgContent=<")
			.append(this.getMsgContent().trim())
			.append(">,Reserve=")
			.append(this.getReserve().trim())
			.append("}]");
		return sb.toString();
	}

	public String validate(){
		if(SMIP_DataPack.SMIP_MT_LEN + this.getMsgLength() != this.getTotalLength()) {
			return "1|Invalid msg length";
		}
		if ("".equals(getToPhone().trim())){
			return "2|ToPhone is invalid";
		}
		if("".equals(getFeePhone().trim())) {
			return "2|FeePhone is invalid";
		}
		if (!getToPhone().trim().equals(getFeePhone().trim())) {
			return "2|ToPhone<>FeePhone";
		}
//		if("".equals(getSpNum().trim())) {
//			return "2|SpNum is invalid";
//		}
		if("".equals(getChannelId().trim())) {
			return "2|ChannelId is invalid";
		}
		if(getType() != 1 && getType() != 11){
			return "2|Type is invalid";
		}
		if (this.getMsgLength() == 0) {
			return "2|Message is null";
		}
		return null;
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
		mt.setMsgContent("");
		
		mt.validate();
	}
}
