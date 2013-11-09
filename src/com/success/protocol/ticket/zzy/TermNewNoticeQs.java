/**
 * Title: TermNewNoticeQs.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-20 ����07:56:46
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.success.protocol.ticket.zzy.model.QueryTermResult;

/**
 * com.success.protocol.ticket.zzy
 * TermNewNoticeQs.java
 * TermNewNoticeQs
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-20 ����07:56:46
 * 
 */

public class TermNewNoticeQs extends EhandDataPack {
	
	private QueryTermResult newTermResult;
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 */
	public TermNewNoticeQs(String command,String userid){
		super();
		this.command = command;
		this.userid = userid;
	}
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 * @param key
	 * @param messBody
	 */
	public TermNewNoticeQs(String command,String userid,String key,String messBody){
		super();
		this.command = command;
		this.userid = userid;
		this.key = key;
		this.messageBody = messBody;
	}

	/* (�� Javadoc)
	 *Title: decodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.ticket.zzy.EhandDataPack#decodeMessageBody()
	 */
	@Override
	public void decodeMessageBody() throws Exception {
		Document document = DocumentHelper.parseText(this.messageBody);
		Node node = null;
		newTermResult = new QueryTermResult();
		newTermResult.setLotteryId((node = document.selectSingleNode("/ilist/ielement/LOTTERYID")) == null ? null : node.getText());
		newTermResult.setTermNo((node = document.selectSingleNode("/ilist/ielement/ISSUE")) == null ? null : node.getText());
		newTermResult.setInlinelotteryid((node = document.selectSingleNode("/ilist/ielement/INLINELOTTERYID")) == null ? null : node.getText());
		newTermResult.setStartTimeStamp((node = document.selectSingleNode("/ilist/ielement/STARTTIME")) == null ? null : node.getText());
		newTermResult.setEndTimeStamp((node = document.selectSingleNode("/ilist/ielement/ENDTIME")) == null ? null : node.getText());
		newTermResult.setStatus((node = document.selectSingleNode("/ilist/ielement/STATUS")) == null ? null : node.getText());
	}

	/* (�� Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.ticket.zzy.EhandDataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() throws Exception {
		this.setMessageBody(this.getErrorcode());
	}
	
	/**
	 * ��д���ص���Ϣ
	 */
	public String encodeNoticeMessage() throws Exception {
		this.encodeMessageBody();
		return this.getMessageBody();
	}

	public QueryTermResult getNewTermResult() {
		return newTermResult;
	}

	public void setNewTermResult(QueryTermResult newTermResult) {
		this.newTermResult = newTermResult;
	}

}
