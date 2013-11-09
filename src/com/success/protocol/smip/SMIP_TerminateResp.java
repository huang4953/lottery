package com.success.protocol.smip;

import java.sql.Timestamp;

public class SMIP_TerminateResp extends SMIP_DataPack {

	public SMIP_TerminateResp(byte[] b){
		super(b);
	}
	
	public SMIP_TerminateResp(SMIP_DataPack dp){
		super(new byte[SMIP_DataPack.SMIP_TERMINATE_RESP_LEN]);
		this.setTotalLength(SMIP_DataPack.SMIP_TERMINATE_RESP_LEN);
		this.setCommandID(SMIP_DataPack.SMIP_TERMINATE_RESP);
		this.setSequenceID(dp.getSequenceID());
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_TerminateResp")
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

		SMIP_Terminate dp = new SMIP_Terminate(1);
		System.out.println(dp.toString()); 
		System.out.println("----------------------------------------------------");
		dp.dumpPack();
		System.out.println("----------------------------------------------------");
		System.out.println("");
		
		SMIP_TerminateResp dpResp = new SMIP_TerminateResp(dp);
		System.out.println(dpResp.toString());
		System.out.println("----------------------------------------------------");
		dpResp.dumpPack();
		System.out.println("----------------------------------------------------");
	}
}
