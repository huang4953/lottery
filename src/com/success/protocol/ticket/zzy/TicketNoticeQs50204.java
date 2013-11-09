/**
 * Title: TicketNoticeQs50204.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-14 上午10:40:49
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.success.protocol.ticket.zzy.model.TicketQueryResult;

/**
 * com.success.protocol.ticket.zzy
 * TicketNoticeQs50204.java
 * TicketNoticeQs50204
 * (出票情况的通知接口)
 * @author gaoboqin
 * 2011-1-14 上午10:40:49
 * 
 */

public class TicketNoticeQs50204 extends EhandDataPack {
	
	private List<TicketQueryResult> betResultList;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 */
	public TicketNoticeQs50204(String command,String userid){
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
	public TicketNoticeQs50204(String command,String userid,String key,String messBody){
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
		Node listInfo = document.selectSingleNode("/ilist");
		if(listInfo != null){
			this.betResultList = new ArrayList<TicketQueryResult> ();
			List<Node> ielemnt = listInfo.selectNodes("ielement");
			for(Node one : ielemnt){
				String ticketSequence = one.selectSingleNode("AGENTOPERATEID").getText();
				String ticketId = one.selectSingleNode("TICKETID").getText();
				String printStatus = one.selectSingleNode("PRINTSTATUS").getText();
				
				TicketQueryResult ticketRes = new TicketQueryResult(ticketSequence,ticketId,printStatus);
				this.betResultList.add(ticketRes);
			}
		}

	}

	/* (非 Javadoc)
	 *Title: encodeMessageBody
	 *Description: 
	 * @throws Exception
	 * @see com.success.protocol.ticket.zzy.EhandDataPack#encodeMessageBody()
	 */
	@Override
	public void encodeMessageBody() throws Exception {
		this.setMessageBody("");
	}
	public List<TicketQueryResult> getBetResultList() {
		return betResultList;
	}
	public void setBetResultList(List<TicketQueryResult> betResultList) {
		this.betResultList = betResultList;
	}

}
