package com.success.lottery.sms;


public class Order{

	private String clientName;
	private boolean isResp = false;
	private String transactionId;
	private String spNum;
	private String spNumExt;
	private String fromPhone;
	private String feePhone;
	private String serviceId;
	private String channelId;
	private int actionId;
	private int accessMode;
	private String linkId;
	private int respFlag;
	private int msgLength;
	private String msgContent;
	private String reserve;
	
	//以下为OrderResp消息特有内容
	private int status;
	private int errLength;
	private String errorDescription;

	

	
	public String getClientName(){
		return clientName;
	}



	
	public void setClientName(String clientName){
		this.clientName = clientName;
	}



	
	public boolean isResp(){
		return isResp;
	}



	
	public void setResp(boolean isResp){
		this.isResp = isResp;
	}



	
	public String getTransactionId(){
		return transactionId;
	}



	
	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}



	
	public String getSpNum(){
		return spNum;
	}



	
	public void setSpNum(String spNum){
		this.spNum = spNum;
	}



	
	public String getSpNumExt(){
		return spNumExt;
	}



	
	public void setSpNumExt(String spNumExt){
		this.spNumExt = spNumExt;
	}



	
	public String getFromPhone(){
		return fromPhone;
	}



	
	public void setFromPhone(String fromPhone){
		this.fromPhone = fromPhone;
	}



	
	public String getFeePhone(){
		return feePhone;
	}



	
	public void setFeePhone(String feePhone){
		this.feePhone = feePhone;
	}



	
	public String getServiceId(){
		return serviceId;
	}



	
	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}



	
	public String getChannelId(){
		return channelId;
	}



	
	public void setChannelId(String channelId){
		this.channelId = channelId;
	}



	
	public int getActionId(){
		return actionId;
	}



	
	public void setActionId(int actionId){
		this.actionId = actionId;
	}



	
	public int getAccessMode(){
		return accessMode;
	}



	
	public void setAccessMode(int accessMode){
		this.accessMode = accessMode;
	}



	
	public String getLinkId(){
		return linkId;
	}



	
	public void setLinkId(String linkId){
		this.linkId = linkId;
	}



	
	public int getRespFlag(){
		return respFlag;
	}



	
	public void setRespFlag(int respFlag){
		this.respFlag = respFlag;
	}



	
	public int getMsgLength(){
		return msgLength;
	}



	
	public void setMsgLength(int msgLength){
		this.msgLength = msgLength;
	}



	
	public String getMsgContent(){
		return msgContent;
	}



	
	public void setMsgContent(String msgContent){
		this.msgContent = msgContent;
	}



	
	public String getReserve(){
		return reserve;
	}



	
	public void setReserve(String reserve){
		this.reserve = reserve;
	}



	
	public int getStatus(){
		return status;
	}



	
	public void setStatus(int status){
		this.status = status;
	}



	
	public int getErrLength(){
		return errLength;
	}



	
	public void setErrLength(int errLength){
		this.errLength = errLength;
	}



	
	public String getErrorDescription(){
		return errorDescription;
	}



	
	public void setErrorDescription(String errorDescription){
		this.errorDescription = errorDescription;
	}



	public static void main(String[] args){
	}
}
