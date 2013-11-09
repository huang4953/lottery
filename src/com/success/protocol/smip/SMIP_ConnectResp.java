package com.success.protocol.smip;

import java.sql.Timestamp;


public class SMIP_ConnectResp extends SMIP_DataPack {

	public SMIP_ConnectResp(byte[] b){
		super(b);
	}
	
	public SMIP_ConnectResp(SMIP_DataPack dp) {
		super(new byte[SMIP_DataPack.SMIP_CONNECT_RESP_LEN]);
		this.setTotalLength(SMIP_DataPack.SMIP_CONNECT_RESP_LEN);
		this.setCommandID(SMIP_DataPack.SMIP_CONNECT_RESP);
		this.setSequenceID(dp.getSequenceID());
	}

	public void setStatus(int status){
		setInt(bMsg, 12, 4, status);
	}
	
	public int getStatus() {
		return getInt(bMsg, 12, 4);
	}

	public void setVersion(int version){
		int highVer = 0, lowVer = 0;
		if(version >= 10){
			highVer = version / 10;
			lowVer = version % 10;
		}else{
			highVer = version % 10;
			lowVer = 0;
		}

		byte ver = 0;
		ver = (byte)(((byte)((highVer & 0xff) << 4)) | ((byte)(lowVer & 0xff))); 
		setInt(bMsg, 16, 1, (int)ver);
	}

	public int getVersion(){
		int version = getInt(bMsg, 16, 1);
		return ((int)((byte)((version & 0xff) >> 4))) * 10 + ((int)((byte)(version & 0x0f)));
	}

	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_ConnectResp")
			.append("[")
			.append((new Timestamp(this.footprint)).toString())
			.append("][")
			.append(super.toString())
			.append("][")
			.append("SMIP_DataPack_Body{Status=")
			.append(this.getStatus())
			.append(",Version=")
			.append(getVersion())
			.append("}]");
		return sb.toString();		
	}
	
	
	public static void main(String[] args) {
		SMIP_Connect connect = new SMIP_Connect(1);
		connect.setFields("czpt", "libing", 0, 11);
		System.out.println(connect.toString());
		
		System.out.println("----------------------------------------------------");
		connect.dumpPack();
		System.out.println("----------------------------------------------------");

		SMIP_ConnectResp connectResp = new SMIP_ConnectResp(connect);
		if(connect.authenticatorAppId("Libing")){
			connectResp.setStatus(0);
			if(connect.authenticatorVersion(11) != 0) {
				connectResp.setStatus(4);
			}
			connectResp.setVersion(11);
		} else {
			connectResp.setStatus(3);
		}
		System.out.println(connectResp.toString());
		System.out.println("----------------------------------------------------");
		connectResp.dumpPack();
		System.out.println("----------------------------------------------------");
	}
}
