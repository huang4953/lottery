package com.success.protocol.smip;

import java.sql.Timestamp;

public class SMIP_ActiveTest extends SMIP_DataPack {

	public SMIP_ActiveTest(byte[] b){
		this.bMsg = b;
	}
	
	public SMIP_ActiveTest(int seq){
		super(new byte[SMIP_DataPack.SMIP_ACTIVE_TEST_LEN]);
		super.setTotalLength(SMIP_DataPack.SMIP_ACTIVE_TEST_LEN);
		setCommandID(SMIP_ACTIVE_TEST);
		setSequenceID(seq);
	}
	
	public void setFootPrint(long footprint){
		this.footprint = footprint;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_ActiveTest")
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
		SMIP_ActiveTest dp = new SMIP_ActiveTest(1);
		System.out.println(dp.toString());
		System.out.println("----------------------------------------------------");
		dp.dumpPack();
		System.out.println("----------------------------------------------------");
		System.out.println("You have a new email!");
	}

}
