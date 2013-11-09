package com.success.protocol.smip;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public class SMIP_Connect extends SMIP_DataPack {

	public SMIP_Connect(byte[] b){
		super(b);
	}

	public SMIP_Connect(int seq) {
		super(new byte[SMIP_DataPack.SMIP_CONNECT_LEN]);
		this.setTotalLength(SMIP_DataPack.SMIP_CONNECT_LEN);
		this.setCommandID(SMIP_DataPack.SMIP_CONNECT);
		this.setSequenceID(seq);
	}

	public void setAppId(String appId) {
		setBytes(bMsg, 12, 6, appId.getBytes());
	}
	
	public String getAppId(){
		return new String(bMsg, 12, 6);
	}
	
	public void setAuthenticatorAppId(byte[] authenticatorAppId) {
		setBytes(bMsg, 18, 16, authenticatorAppId);
	}
	
	public byte[] getAuthenticatorAppId(){
		return getBytes(bMsg, 18, 16);
	}

	

	public boolean authenticatorAppId(String pswd){
		boolean pass = true;
		byte[] serverAuthentication = getMD5(getAppId().trim(), pswd.trim(), getTimestamp());
		byte[] clientAuthentication = getAuthenticatorAppId();
		if (serverAuthentication.length != clientAuthentication.length) {
			return false;
		}
		for (int i = 0; i < serverAuthentication.length; i++){
			if(serverAuthentication[i] != clientAuthentication[i]){
				return false;
			}
		}
		return pass;
	}

	public int authenticatorVersion(int version){
		return version - this.getVersion();
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
		setInt(bMsg, 34, 1, (int)ver);
	}
	
	public int getVersion(){
		int version = getInt(bMsg, 34, 1);
		return ((int)((byte)((version & 0xff) >> 4))) * 10 + ((int)((byte)(version & 0x0f)));
	}

	public void setTimestamp(int timestamp) {
		setInt(bMsg, 35, 4, timestamp);
	}
	
	public int getTimestamp() {
		return getInt(bMsg, 35, 4);
	}

	/**
	 * 
	 * @param spid
	 * @param pwsd
	 * @param ts
	 * @return
	 */
	public static byte[] getMD5(String appId, String pwsd, int ts){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
		md.update(appId.getBytes());
		md.update(new byte[9]);
		md.update(pwsd.getBytes());
		String sts = (ts < 1000000000 ? "0" : "") + ts;
		md.update(sts.getBytes());
		return md.digest();
	}

	/**
	 * 
	 * @param appId
	 * @param pswd
	 * @param loginMode
	 * @param version
	 */
	public void setFields(String appId, String pswd, int loginMode, int version){
		int ts = createTimeStamp();
		this.setAppId(appId);
		this.setAuthenticatorAppId(getMD5(appId, pswd, ts));
		this.setTimestamp(ts);
		this.setVersion(version);
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_Connect")
			.append("[")
			.append((new Timestamp(this.footprint)).toString())
			.append("][")
			.append(super.toString())
			.append("][")
			.append("SMIP_DataPack_Body{AppId=")
			.append(this.getAppId().trim())
			.append(",AuthenticatorAppId=<");
			for (int i = 0; i < this.getAuthenticatorAppId().length; i++){
				sb.append(this.getAuthenticatorAppId()[i]).append(",");
			}
		sb
			.append(">,Version=")
			.append(this.getVersion())
			.append(",Timestamp=")
			.append(this.getTimestamp())
			.append("}]");
		return sb.toString();
	}
	
	public String validate(){
		if(SMIP_DataPack.SMIP_CONNECT_LEN  != this.getTotalLength()) {
			return "1|Invalid message length";
		}
		return null;
	}



	public static void main(String[] args) {
		
//		SMIP_Connect connect = new SMIP_Connect(1);
//		connect.setFields("czpt", "libing", 0, 10);
//		System.out.println(connect.toString());
//		
//		System.out.println("----------------------------------------------------");
//		connect.dumpPack();
//		System.out.println("----------------------------------------------------");
//		System.out.println(connect.authenticatorAppId("libing"));
//		System.out.println(connect.authenticatorVersion(11));
		
		
		byte[] bytes = getMD5("czpt", "tltczpt", 1211121453);
		SMIP_DataPack.dumpPack(bytes, 0, bytes.length);

//		SMIP_Sequence seq = new SMIP_Sequence();
//		SMIP_Connect connect = new SMIP_Connect(seq.nextVal());
//		connect.setFields("911145", "SHLT", 1, 30);
//
//		System.out.println("");
//		System.out.println("");		
//		System.out.println(connect.toString());
//		
//		System.out.println("----------------------------------------------------");
//		connect.dumpPack();
//		System.out.println("----------------------------------------------------");
//		
//		System.out.println(connect.dumpPackString());
	}
}
