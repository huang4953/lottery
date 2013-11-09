package com.success.protocol.smip;

import java.sql.Timestamp;


public class SMIP_Query extends SMIP_DataPack {
	public SMIP_Query(byte[] b){
		super(b);
	}
	
	public SMIP_Query(int seq){
		super(new byte[SMIP_DataPack.SMIP_QUERY_LEN]);
		this.setTotalLength(SMIP_DataPack.SMIP_QUERY_LEN);
		this.setCommandID(SMIP_DataPack.SMIP_QUERY);
		this.setSequenceID(seq);
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
	
	public void setReserve(String reserve) {
		setBytes(bMsg, 109, 32, reserve.getBytes());
	}

	public String getReserve() {
		return new String(bMsg, 109, 32);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_Query")
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
	}
}
