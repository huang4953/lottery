/**
 * Title: TermEndNoticeQs.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-20 下午07:57:33
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.success.protocol.ticket.zzy.model.QueryTermResult;

/**
 * com.success.protocol.ticket.zzy
 * TermEndNoticeQs.java
 * TermEndNoticeQs
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-20 下午07:57:33
 * 
 */

public class TermEndNoticeQs extends EhandDataPack {
	private QueryTermResult endTermResult;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 */
	public TermEndNoticeQs(String command,String userid){
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
	public TermEndNoticeQs(String command,String userid,String key,String messBody){
		super();
		this.command = command;
		this.userid = userid;
		this.key = key;
		this.messageBody = messBody;
	}

	/* (非 Javadoc)
	 *Title: decodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.ticket.zzy.EhandDataPack#decodeMessageBody()
	 */
	@Override
	public void decodeMessageBody() throws Exception {
		Document document = DocumentHelper.parseText(this.messageBody);
		Node node = null;
		endTermResult = new QueryTermResult();
		endTermResult.setLotteryId((node = document.selectSingleNode("/ilist/ielement/LOTTERYID")) == null ? null : node.getText());
		endTermResult.setTermNo((node = document.selectSingleNode("/ilist/ielement/ISSUE")) == null ? null : node.getText());
		endTermResult.setStatus((node = document.selectSingleNode("/ilist/ielement/STATUS")) == null ? null : node.getText());
	}

	/* (非 Javadoc)
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
	 * 复写返回的消息
	 */
	public String encodeNoticeMessage() throws Exception {
		this.encodeMessageBody();
		return this.getMessageBody();
	}

	public QueryTermResult getEndTermResult() {
		return endTermResult;
	}

	public void setEndTermResult(QueryTermResult endTermResult) {
		this.endTermResult = endTermResult;
	}

}
