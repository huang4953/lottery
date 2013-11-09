package com.success.protocol.smip;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;


public class SMIP_QueryResp extends SMIP_DataPack {
	public SMIP_QueryResp(byte[] b){
		super(b);
	}
	
	public SMIP_QueryResp(SMIP_DataPack dp){
		super(new byte[SMIP_DataPack.SMIP_QUERY_RESP_LEN + 512 + 512 + 256]);
		this.setTotalLength(SMIP_DataPack.SMIP_QUERY_RESP_LEN + 512 + 512 + 256);
		this.setCommandID(SMIP_DataPack.SMIP_QUERY_RESP);
		this.setSequenceID(dp.getSequenceID());

		SMIP_Query query = (SMIP_Query)dp;
		this.setSpNum(query.getSpNum().trim());
		this.setFeePhone(query.getFeePhone().trim());
		this.setFromPhone(query.getFromPhone().trim());
	}
	
	public void setSpNum(String spNum){
		setBytes(bMsg, 12, 21, spNum.getBytes());
	}
	
	public String getSpNum(){
		return new String(bMsg, 12, 21);
	}
	
	public void setFromPhone(String fromPhone){
		setBytes(bMsg, 33, 38, fromPhone.getBytes());
	}
	
	public String getFromPhone(){
		return new String(bMsg, 33, 38);
	}
	
	public void setFeePhone(String feePhone){
		setBytes(bMsg, 71, 38, feePhone.getBytes());
	}
	
	public String getFeePhone(){
		return new String(bMsg, 71, 38);
	}
	
	public void setCIDLength(int cidLength){
		setInt(bMsg, 109, 2, cidLength);
	}
	
	public int getCIDLength(){
		return getInt(bMsg, 109, 2);
	}
	
	public void setChannelIdList(String channelIdList){
		setChannelIdList(channelIdList.getBytes());
	}
	
	public void setChannelIdList(byte[] channelIdList){
		setCIDLength(channelIdList.length);
		setBytes(bMsg, 111, getCIDLength(), channelIdList);
		setTotalLength(SMIP_DataPack.SMIP_QUERY_RESP_LEN + getCIDLength() + getSNLength());
	}
	
	public byte[] getChannelIdListSource() {
		return getBytes(bMsg, 111, getCIDLength());
	}

	public String getChannelIdList() {
		try {
			return new String(getChannelIdListSource(), "GBK");
		} catch (UnsupportedEncodingException e) {
			return "Unsupported Encoding: " + e.toString();
		}
	}

	public void setSNLength(int snLength){
		setInt(bMsg, 111 + getCIDLength(), 2, snLength);
	}
	
	public int getSNLength(){
		return getInt(bMsg, 111 + getCIDLength(), 2);
	}
	
	public void setServiceNameList(String serviceNameList){
		setServiceNameList(serviceNameList.getBytes());
	}
	
	public void setServiceNameList(byte[] serviceNameList){
		setSNLength(serviceNameList.length);
		setBytes(bMsg, 113 + getCIDLength(), getSNLength(), serviceNameList);
		setTotalLength(SMIP_DataPack.SMIP_QUERY_RESP_LEN + getCIDLength() + getSNLength() + getErrLength());
	}
	
	public byte[] getServiceNameListSource() {
		return getBytes(bMsg, 113 + getCIDLength(), getSNLength());
	}

	public String getServiceNameList() {
		try {
			return new String(getServiceNameListSource(), "GBK");
		} catch (UnsupportedEncodingException e) {
			return "Unsupported Encoding: " + e.toString();
		}
	}
	
	public void setStatus(int status){
		setInt(bMsg, 113 + getCIDLength() + getSNLength(), 4, status);
	}
	
	public int getStatus(){
		return getInt(bMsg, 113 + getCIDLength() + getSNLength(), 4);
	}
	
	public void setErrLength(int errLength){
		setInt(bMsg, 117 + getCIDLength() + getSNLength(), 1, errLength);
	}
	
	public int getErrLength(){
		return getInt(bMsg, 117 + getCIDLength() + getSNLength(), 1);
	}
	
	public void setErrorDescription(String errorDescription) {
		this.setErrorDescription(errorDescription.getBytes());
	}
	
	public void setErrorDescription(byte[] errorDescription) {
		setErrLength(errorDescription.length);
		setBytes(bMsg, 118 + getCIDLength() + getSNLength(), getErrLength(), errorDescription);
		this.setTotalLength(SMIP_DataPack.SMIP_QUERY_RESP_LEN  + getCIDLength() + getSNLength() + getErrLength());
	}
	
	public byte[] getErrorDescriptionSource(){
		return getBytes(bMsg, 118 + getCIDLength() + getSNLength(), getErrLength());
	}
	
	public String getErrorDescription(){
		try {
			return new String(getErrorDescriptionSource(), "GBK");
		} catch (UnsupportedEncodingException e) {
			return new String(getErrorDescriptionSource());
		}
	}
	
	public void setReserve(String reserve) {
		setBytes(bMsg, 118 + getCIDLength() + getSNLength() + getErrLength(), 32, reserve.getBytes());
	}

	public String getReserve() {
		return new String(bMsg, 118 + getCIDLength() + getSNLength() + getErrLength(), 32);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_QueryResp")
			.append("[")
			.append((new Timestamp(this.footprint)).toString())
			.append("][")
			.append(super.toString())
			.append("][").append("SMIP_DataPack_Body{SpNum=")
			.append(this.getSpNum().trim())
			.append(",FromPhone=")
			.append(this.getFromPhone().trim())
			.append(",FeePhone=")
			.append(this.getFeePhone().trim())
			.append(",CIDLength=")
			.append(this.getCIDLength())
			.append(",ChannelIdList=<")
			.append(this.getChannelIdList().trim())
			.append(">,SNLength=")
			.append(this.getSNLength())
			.append(",ServiceNameList=<")
			.append(this.getServiceNameList().trim())
			.append(">,Status=")
			.append(this.getStatus())
			.append(",ErrLength=")
			.append(this.getErrLength())
			.append(",ErrorDescription=<")
			.append(this.getErrorDescription().trim())
			.append(",Reserve=")
			.append(this.getReserve().trim())
			.append("}]");
		return sb.toString();
	}
	public static void main(String[] args) {
		SMIP_Query query = new SMIP_Query(2);
		query.setSpNum("10662001");
		query.setFromPhone("13761874366");
		query.setFeePhone("13761874366");
		
		System.out.println(query.toString());
		System.out.println("----------------------------------------------------");
		query.dumpPack();
		System.out.println("----------------------------------------------------");
		
		SMIP_QueryResp queryResp = new SMIP_QueryResp(query);
		queryResp.setChannelIdList("33,99,10,45");
		queryResp.setServiceNameList("safasdfas,asdfsadfasd,asdfasfasd,asdfasdfasd");
		System.out.println(queryResp.toString());
		System.out.println("----------------------------------------------------");
		queryResp.dumpPack();
		System.out.println("----------------------------------------------------");

	}
}
