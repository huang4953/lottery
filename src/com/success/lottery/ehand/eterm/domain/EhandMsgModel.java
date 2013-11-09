/**
 * Title: EhandMsgModel.java
 * @Package com.success.lottery.ehand.emsglog.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-25 下午12:56:00
 * @version V1.0
 */
package com.success.lottery.ehand.eterm.domain;

import java.io.Serializable;

/**
 * com.success.lottery.ehand.emsglog.domain
 * EhandMsgModel.java
 * EhandMsgModel
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-25 下午12:56:00
 * 
 */

public class EhandMsgModel implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -1111655645572331096L;
	private int id;
	private int msgType;
	private String msgId;
	private String msgUserId;
	private String msgCommand;
	private String msgKey;
	private String msgCode;
	private String msgContent;
	private java.sql.Timestamp msgTime;
	private String reserve;
	
	public EhandMsgModel(){
		super();
	}
	
	public EhandMsgModel(int msgType,String msgId,String msgUserId,String msgCommand,String msgKey,String msgCode,String msgContent,String reserve){
		this.msgType = msgType;
		this.msgId = msgId;
		this.msgUserId = msgUserId;
		this.msgCommand = msgCommand;
		this.msgKey = msgKey;
		this.msgCode = msgCode;
		this.msgContent = msgContent;
		this.reserve = reserve;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsgCommand() {
		return msgCommand;
	}
	public void setMsgCommand(String msgCommand) {
		this.msgCommand = msgCommand;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public java.sql.Timestamp getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(java.sql.Timestamp msgTime) {
		this.msgTime = msgTime;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getMsgUserId() {
		return msgUserId;
	}
	public void setMsgUserId(String msgUserId) {
		this.msgUserId = msgUserId;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
	

}
