package com.success.lottery.sms;


public class Query{

	private String clientName;
	private boolean isResp = false;
	private String spNum;
	private String fromPhone;
	private String feePhone;
	private String reserve;
	
	private int cidLength;
	private String channelIdList;
	private int snLength;
	private String serviceNameList;
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

	
	public String getSpNum(){
		return spNum;
	}

	
	public void setSpNum(String spNum){
		this.spNum = spNum;
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

	
	public String getReserve(){
		return reserve;
	}

	
	public void setReserve(String reserve){
		this.reserve = reserve;
	}

	
	public int getCidLength(){
		return cidLength;
	}

	
	public void setCidLength(int cidLength){
		this.cidLength = cidLength;
	}

	
	public String getChannelIdList(){
		return channelIdList;
	}

	
	public void setChannelIdList(String channelIdList){
		this.channelIdList = channelIdList;
	}

	
	public int getSnLength(){
		return snLength;
	}

	
	public void setSnLength(int snLength){
		this.snLength = snLength;
	}

	
	public String getServiceNameList(){
		return serviceNameList;
	}

	
	public void setServiceNameList(String serviceNameList){
		this.serviceNameList = serviceNameList;
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

	/**
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
	}
}
