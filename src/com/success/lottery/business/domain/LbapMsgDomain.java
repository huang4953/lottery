/**
 * Title: LbapMsgDomain.java
 * @Package com.success.lottery.business.domain
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-5 ����06:07:11
 * @version V1.0
 */
package com.success.lottery.business.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * com.success.lottery.business.domain
 * LbapMsgDomain.java
 * LbapMsgDomain
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-5 ����06:07:11
 * 
 */

public class LbapMsgDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = -1263933383701584861L;
	
	private int id;
	private String clientid;
	private String commandid;
	private String messageid;
	private String result;
	private String messagebody;
	private Timestamp msgtime;
	private String reserve;
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getCommandid() {
		return commandid;
	}
	public void setCommandid(String commandid) {
		this.commandid = commandid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessagebody() {
		return messagebody;
	}
	public void setMessagebody(String messagebody) {
		this.messagebody = messagebody;
	}
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
	public Timestamp getMsgtime() {
		return msgtime;
	}
	public void setMsgtime(Timestamp msgtime) {
		this.msgtime = msgtime;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	

}
