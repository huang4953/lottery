package com.success.protocol.smip;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;


public class SMIP_OrderResp extends SMIP_DataPack {
	public SMIP_OrderResp(byte[] b){
		super(b);
	}
	public SMIP_OrderResp(SMIP_DataPack dp){
		super(new byte[SMIP_DataPack.SMIP_ORDER_RESP_LEN + 256 + 280]);
		this.setTotalLength(SMIP_DataPack.SMIP_ORDER_RESP_LEN + 256 + 280);
		this.setCommandID(SMIP_DataPack.SMIP_ORDER_RESP);
		this.setSequenceID(dp.getSequenceID());

		SMIP_Order order = (SMIP_Order)dp;
		this.setTransactionId(order.getTransactionId().trim());
		this.setSpNum(order.getSpNum().trim());
		this.setSpNumExt(order.getSpNumExt().trim());
		this.setFeePhone(order.getFeePhone().trim());
		this.setFromPhone(order.getFromPhone().trim());
		this.setServiceId(order.getServiceId());
		this.setLinkId(order.getLinkId().trim());
		this.setRespFlag(order.getRespFlag());
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

	public void setLinkId(String linkId){
		setBytes(bMsg, 204, 32, linkId.getBytes());
	}
	
	public String getLinkId(){
		return new String(bMsg, 204, 32);
	}
	
	public void setRespFlag(int respFlag){
		setInt(bMsg, 236, 1, respFlag);
	}
	
	public int getRespFlag(){
		return getInt(bMsg, 236, 1);
	}
	
	public void setStatus(int status){
		setInt(bMsg, 237, 4, status);
	}
	
	public int getStatus(){
		return getInt(bMsg, 237, 4);
	}
	
	public void setErrLength(int errLength){
		setInt(bMsg, 241, 1, errLength);
	}

	public int getErrLength(){
		return getInt(bMsg, 241, 1);
	}
	
	public void setErrorDescription(String errorDescription) {
		this.setErrorDescription(errorDescription.getBytes());
	}
	
	public void setErrorDescription(byte[] errorDescription) {
		setErrLength(errorDescription.length);
		setBytes(bMsg, 242, getErrLength(), errorDescription);
		this.setTotalLength(SMIP_DataPack.SMIP_ORDER_RESP_LEN + getErrLength() + getMsgLength());
	}
	
	public byte[] getErrorDescriptionSource(){
		return getBytes(bMsg, 242, getErrLength());
	}
	
	public String getErrorDescription(){
		try {
			return new String(getErrorDescriptionSource(), "GBK");
		} catch (UnsupportedEncodingException e) {
			return new String(getErrorDescriptionSource());
		}
	}
	
	public void setMsgLength(int msgLength) {
		setInt(bMsg, 242 + getErrLength(), 1, msgLength);
	}

	public int getMsgLength() {
		return getInt(bMsg, 242 + getErrLength(), 1);
	}

	public void setMsgContent(String msgContent) {
		setMsgContent(msgContent.getBytes());
	}

	public void setMsgContent(byte[] msgContent) {
		setMsgLength(msgContent.length);
		setBytes(bMsg, 243 + getErrLength(), getMsgLength(), msgContent);
		setTotalLength(SMIP_DataPack.SMIP_ORDER_RESP_LEN + getMsgLength() + getErrLength());
	}

	public byte[] getMsgContentSource() {
		return getBytes(bMsg, 243 + getErrLength(), getMsgLength());
	}

	public String getMsgContent() {
		try {
			return new String(getMsgContentSource(), "GBK");
		} catch (UnsupportedEncodingException e) {
			return "Unsupported Encoding: " + e.toString();
		}
	}

	public void setReserve(String reserve) {
		setBytes(bMsg, 243 + getErrLength() + getMsgLength(), 32, reserve.getBytes());
	}

	public String getReserve() {
		return new String(bMsg, 243 + getErrLength() + getMsgLength(), 32);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_OrderResp")
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
			.append(",LinkId=")
			.append(this.getLinkId().trim())
			.append(",RespFlag=")
			.append(this.getRespFlag())
			.append(",Status=")
			.append(this.getStatus())
			.append(",ErrLength=")
			.append(this.getErrLength())
			.append(",ErrorDescription=<")
			.append(this.getErrorDescription().trim())
			.append(">,MsgLength=")
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
		
		
		SMIP_OrderResp orderResp = new SMIP_OrderResp(order);
		orderResp.setStatus(3);
		orderResp.setErrorDescription("adfa出现了一dasfasd个大错误！！！");
		orderResp.setMsgContent("订购错误错误错误，哈哈哈哈哈！！！");
		
		System.out.println(orderResp.toString());
		System.out.println("----------------------------------------------------");
		orderResp.dumpPack();
		System.out.println("----------------------------------------------------");

	}

}
