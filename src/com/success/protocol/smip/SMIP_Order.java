package com.success.protocol.smip;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;


public class SMIP_Order extends SMIP_DataPack {
	public SMIP_Order(byte[] b){
		super(b);
	}
	
	public SMIP_Order(int seq){
		super(new byte[SMIP_DataPack.SMIP_ORDER_LEN + 256]);
		this.setTotalLength(SMIP_DataPack.SMIP_ORDER_LEN + 256);
		this.setCommandID(SMIP_DataPack.SMIP_ORDER);
		this.setSequenceID(seq);
	}

	public void setTransactionId(String transactionId){
		setBytes(bMsg, 12, 32, transactionId.getBytes());
	}

	public String getTransactionId(){
		return new String(bMsg, 12, 32);
	}

	public void setSpNum(String spNum){
		setBytes(bMsg, 44, 21, spNum.getBytes());
	}
	
	public String getSpNum(){
		return new String(bMsg, 44, 21);
	}
	
	public void setSpNumExt(String spNumExt){
		setBytes(bMsg, 65, 21, spNumExt.getBytes());
	}
	
	public String getSpNumExt(){
		return new String(bMsg, 65, 21);
	}
	
	public void setFromPhone(String fromPhone){
		setBytes(bMsg, 86, 38, fromPhone.getBytes());
	}
	
	public String getFromPhone(){
		return new String(bMsg, 86, 38);
	}
	
	public void setFeePhone(String feePhone){
		setBytes(bMsg, 124, 38, feePhone.getBytes());
	}
	
	public String getFeePhone(){
		return new String(bMsg, 124, 38);
	}
	
	public void setServiceId(String serviceId){
		setBytes(bMsg, 162, 21, serviceId.getBytes());
	}
	
	public String getServiceId(){
		return new String(bMsg, 162, 21);
	}
	
	public void setChannelId(String channelId){
		setBytes(bMsg, 183, 21, channelId.getBytes());
	}
	
	public String getChannelId(){
		return new String(bMsg, 183, 21);
	}
	
	public void setActionId(int actionId){
		setInt(bMsg, 204, 1, actionId);
	}
	
	public int getActionId(){
		return getInt(bMsg, 204, 1);
	}
	
	public void setAccessMode(int accessMode){
		setInt(bMsg, 205, 1, accessMode);
	}
	
	public int getAccessMode(){
		return getInt(bMsg, 205, 1);
	}
	
	public void setLinkId(String linkId){
		setBytes(bMsg, 206, 32, linkId.getBytes());
	}
	
	public String getLinkId(){
		return new String(bMsg, 206, 32);
	}
	
	public void setRespFlag(int respFlag){
		setInt(bMsg, 238, 1, respFlag);
	}
	
	public int getRespFlag(){
		return getInt(bMsg, 238, 1);
	}
	
	public void setMsgLength(int msgLength){
		setInt(bMsg, 239, 1, msgLength);
	}
	
	public int getMsgLength(){
		return getInt(bMsg, 239, 1);
	}

	
	public void setMsgContent(String msgContent) {
		setMsgContent(msgContent.getBytes());
	}

	public void setMsgContent(byte[] msgContent) {
		setMsgLength(msgContent.length);
		setBytes(bMsg, 240, getMsgLength(), msgContent);
		setTotalLength(SMIP_DataPack.SMIP_ORDER_LEN + getMsgLength());
	}

	public byte[] getMsgContentSource() {
		return getBytes(bMsg, 240, getMsgLength());
	}

	public String getMsgContent() {
		try {
			return new String(getMsgContentSource(), "GBK");
		} catch (UnsupportedEncodingException e) {
			return "Unsupported Encoding: " + e.toString();
		}
	}

	public void setReserve(String reserve) {
		setBytes(bMsg, 240 + getMsgLength(), 32, reserve.getBytes());
	}

	public String getReserve() {
		return new String(bMsg, 240 + getMsgLength(), 32);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_Order")
			.append("[")
			.append((new Timestamp(this.footprint)).toString())
			.append("][")
			.append(super.toString())
			.append("][").append("SMIP_DataPack_Body{TransactionId=")
			.append(this.getTransactionId().trim())
			.append(",SpNum=")
			.append(this.getSpNum().trim())
			.append(",SpNumExt=")
			.append(this.getSpNumExt().trim())
			.append(",FromPhone=")
			.append(this.getFromPhone().trim())
			.append(",FeePhone=")
			.append(this.getFeePhone().trim())
			.append(",ServiceId=")
			.append(this.getServiceId().trim())
			.append(",ChannelId=")
			.append(this.getChannelId().trim())
			.append(",ActionId=")
			.append(this.getActionId())
			.append(",AccessMode=")
			.append(this.getAccessMode())
			.append(",LinkId=")
			.append(this.getLinkId().trim())
			.append(",RespFlag=")
			.append(this.getRespFlag())
			.append(",MsgLength=")
			.append(this.getMsgLength())
			.append(",MsgContent=<")
			.append(this.getMsgContent().trim())
			.append(">,Reserve=")
			.append(this.getReserve().trim())
			.append("}]");
		return sb.toString();
	}
	public static void main(String[] args) {
		SMIP_Order order = new SMIP_Order(1);
		order.setTransactionId("adfajsdlasf");
		order.setSpNum("10662001");
		order.setSpNumExt("33");
		order.setFeePhone("13761874366");
		order.setFromPhone("13761874366");
		order.setServiceId("CZCZ");
		order.setChannelId("99");
		order.setActionId(1);
		order.setAccessMode(1);
		order.setLinkId("2132131231221");
		order.setRespFlag(0);
		order.setMsgContent("this is a order msg!这是一个订购消息！！！");
		
		System.out.println(order.toString());
		System.out.println("----------------------------------------------------");
		order.dumpPack();
		System.out.println("----------------------------------------------------");
	}
}
