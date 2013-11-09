package com.success.protocol.smip;

import java.sql.Timestamp;

public class SMIP_Terminate extends SMIP_DataPack {
	public SMIP_Terminate(byte[] b){
		super(b);
	}
	
	public SMIP_Terminate(int sequence){
		super(new byte[SMIP_DataPack.SMIP_TERMINATE_LEN]);
		this.setTotalLength(SMIP_DataPack.SMIP_TERMINATE_LEN);
		this.setCommandID(SMIP_DataPack.SMIP_TERMINATE);
		this.setSequenceID(sequence);
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_Terminate")
			.append("[")
			.append((new Timestamp(this.footprint)).toString())
			.append("][")
			.append(super.toString())
			.append("][")
			.append("SMIP_DataPack_Body{")
			.append("}]");
		return sb.toString();
	}

	public static void main(String[] args) {
		SMIP_Terminate dp = new SMIP_Terminate(1);
		System.out.println(dp.toString());
		System.out.println("----------------------------------------------------");
		dp.dumpPack();
		System.out.println("----------------------------------------------------");
	}
}
