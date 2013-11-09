package com.success.protocol.smip;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

public class SMIP_MO extends SMIP_DataPack {

	public SMIP_MO(byte b[]) {
		super(b);
	}

	public SMIP_MO(int seq) {
		super(new byte[SMIP_DataPack.SMIP_MO_LEN + 256]);
		this.setTotalLength(SMIP_DataPack.SMIP_MO_LEN + 256);
		this.setCommandID(SMIP_DataPack.SMIP_MO);
		this.setSequenceID(seq);
	}

	public void setMsgId(String msgId) {
		setBytes(bMsg, 12, 16, msgId.getBytes());
	}

	public String getMsgId() {
		return new String(bMsg, 12, 16);
	}

	public void setFromPhone(String fromPhone) {
		setBytes(bMsg, 28, 38, fromPhone.getBytes());
	}

	public String getFromPhone() {
		return new String(bMsg, 28, 38);
	}

	public void setSpNum(String spNum) {
		setBytes(bMsg, 66, 21, spNum.getBytes());
	}

	public String getSpNum() {
		return new String(bMsg, 66, 21);
	}

	public void setSpNumExt(String spNumExt) {
		setBytes(bMsg, 87, 21, spNumExt.getBytes());
	}

	public String getSpNumExt() {
		return new String(bMsg, 87, 21);
	}

	public void setLinkId(String linkId) {
		setBytes(bMsg, 108, 32, linkId.getBytes());
	}

	public String getLinkId() {
		return new String(bMsg, 108, 32);
	}

	public void setMsgLength(int msgLength) {
		setInt(bMsg, 140, 1, msgLength);
	}

	public int getMsgLength() {
		return getInt(bMsg, 140, 1);
	}

	public void setMsgContent(String msgContent) {
		byte[] msgSour = "".getBytes();
		try {
			msgSour = msgContent.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			msgSour = msgContent.getBytes();
		}
		setMsgLength(msgSour.length);
		setBytes(bMsg, 141, getMsgLength(), msgSour);
		setTotalLength(SMIP_DataPack.SMIP_MO_LEN + getMsgLength());
	}

	public void setMsgContent(byte[] msgContent) {
		setMsgLength(msgContent.length);
		setBytes(bMsg, 141, getMsgLength(), msgContent);
		setTotalLength(SMIP_DataPack.SMIP_MO_LEN + getMsgLength());
	}

	public byte[] getMsgContentSource() {
		return getBytes(bMsg, 141, getMsgLength());
	}

	public String getMsgContent() {
		try {
			return new String(getMsgContentSource(), "GBK");
		} catch (UnsupportedEncodingException e) {
			return "Unsupported Encoding: " + e.toString();
		}
	}

	public void setReserve(String reserve) {
		setBytes(bMsg, 141 + getMsgLength(), 32, reserve.getBytes());
	}

	public String getReserve() {
		return new String(bMsg, 141 + getMsgLength(), 32);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb
			.append("SMIP_MO")
			.append("[")
			.append((new Timestamp(this.footprint)).toString())
			.append("][")
			.append(super.toString())
			.append("][").append("SMIP_DataPack_Body{MsgId=")
			.append(this.getMsgId().trim())
			.append(",FromPhone=")
			.append(this.getFromPhone().trim())
			.append(",SpNum=")
			.append(this.getSpNum().trim())
			.append(",SpNumExt=")
			.append(this.getSpNumExt().trim())
			.append(",LinkId=")
			.append(this.getLinkId().trim())
			.append(",MsgLength=")
			.append(this.getMsgLength())
			.append(",MsgContent=<")
			.append(this.getMsgContent().trim())
			.append(">,Reserve=")
			.append(this.getReserve().trim())
			.append("}]");
		return sb.toString();
	}

	public static void main(String[] args) {
		SMIP_MO deliver = new SMIP_MO(1);
		deliver.setMsgId("123456");
		deliver.setFromPhone("13761874366");
		deliver.setSpNum("10662001");
		deliver.setSpNumExt("");
		deliver.setLinkId("324032432");
		deliver.setMsgContent("我爱sa北京天安门1213");
		deliver.setMsgContent("abcdalkrejwql;kjdsalf;jdsafasd");
		System.out.println(deliver.toString());
		System.out.println("------------------------MO----------------------------");
		deliver.dumpPack();
		System.out.println("------------------------MO----------------------------");
	}
}

