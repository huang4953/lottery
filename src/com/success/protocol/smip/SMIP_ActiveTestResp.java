package com.success.protocol.smip;

import java.sql.Timestamp;

public class SMIP_ActiveTestResp extends SMIP_DataPack {
	public SMIP_ActiveTestResp(byte[] b){
		this.bMsg = b;
	}
	
	public SMIP_ActiveTestResp(SMIP_DataPack dp){
		super(new byte[SMIP_DataPack.SMIP_ACTIVE_TEST_RESP_LEN]);
		setTotalLength(SMIP_DataPack.SMIP_ACTIVE_TEST_RESP_LEN);
		setCommandID(SMIP_DataPack.SMIP_ACTIVE_TEST_RESP);
		setSequenceID(dp.getSequenceID());
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_ActiveTestResp")
			.append("[")
			.append((new Timestamp(this.footprint)).toString())
			.append("][")
			.append(super.toString())
			.append("][")
			.append("SMIP_DataPack_Body{")
			.append("]");
		return sb.toString();
	}

	public static void main(String[] args) {

		SMIP_ActiveTest dp = new SMIP_ActiveTest(1);
		System.out.println(dp.toString()); 
		System.out.println("----------------------------------------------------");
		dp.dumpPack();
		System.out.println("----------------------------------------------------");
		System.out.println("");
		
		SMIP_ActiveTestResp dpResp = new SMIP_ActiveTestResp(dp);
		System.out.println(dpResp.toString());
		System.out.println("----------------------------------------------------");
		dpResp.dumpPack();
		System.out.println("----------------------------------------------------");
	}
}
