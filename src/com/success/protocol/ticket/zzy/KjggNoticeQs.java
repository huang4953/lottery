/**
 * Title: KjggNoticeQs.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-20 下午07:56:01
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.success.protocol.ticket.zzy.model.QueryTermResult;
import com.success.protocol.ticket.zzy.model.TicketQueryResult;

/**
 * com.success.protocol.ticket.zzy
 * KjggNoticeQs.java
 * KjggNoticeQs
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-20 下午07:56:01
 * 
 */

public class KjggNoticeQs extends EhandDataPack {
	
	private QueryTermResult kjTermResult;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 */
	public KjggNoticeQs(String command,String userid){
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
	public KjggNoticeQs(String command,String userid,String key,String messBody){
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
		kjTermResult = new QueryTermResult();
		kjTermResult.setLotteryId((node = document.selectSingleNode("/ilist/ielement/LOTTERYID")) == null ? null : node.getText());
		kjTermResult.setTermNo((node = document.selectSingleNode("/ilist/ielement/ISSUE")) == null ? null : node.getText());
		kjTermResult.setLotteryResult((node = document.selectSingleNode("/ilist/ielement/CODE")) == null ? null : node.getText());
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

	public QueryTermResult getKjTermResult() {
		return kjTermResult;
	}

	public void setKjTermResult(QueryTermResult kjTermResult) {
		this.kjTermResult = kjTermResult;
	}

}
