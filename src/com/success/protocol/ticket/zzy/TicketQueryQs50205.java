/**
 * Title: TicketQueryQs50205.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-14 上午10:07:32
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
import com.success.protocol.ticket.zzy.util.EhandUtil;

/**
 * com.success.protocol.ticket.zzy
 * TicketQueryQs50205.java
 * TicketQueryQs50205
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-14 上午10:07:32
 * 
 */

public class TicketQueryQs50205 extends EhandDataPack {
	
	private List<String> ticketList;
	private List<TicketQueryResult> betResultList;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 * @param key
	 * @param messBody
	 */
	public TicketQueryQs50205(String command,String userid,String key,String messBody){
		super();
		this.command = command;
		this.userid = userid;
		this.key = key;
		this.messageBody = messBody;
	}
	
	/**
	 *Title: 
	 *Description: 
	 * @param ticketList
	 */
	public TicketQueryQs50205(List<String> ticketList) throws Exception{
		super();
		if(ticketList == null || ticketList.isEmpty()){
			throw new Exception("查询参数不能为空！");
		}
		this.userid = SYS_DEFINE_USERID;
		this.command = commd_50205;
		this.sendUrl = EhandUtil.url_50205;
		this.ticketList = ticketList;
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
		
		Node listInfo = document.selectSingleNode("/list");
		if(listInfo != null){
			this.betResultList = new ArrayList<TicketQueryResult> ();
			List<Node> ielemnt = listInfo.selectNodes("element");
			for(Node one : ielemnt){
				String errorCode = one.selectSingleNode("ERRORCODE").getText();
				String ticketSequence = one.selectSingleNode("AGENTOPERATEID").getText();
				String ticketId = one.selectSingleNode("TICKETID")==null?null:one.selectSingleNode("TICKETID").getText();
				String printStatus = one.selectSingleNode("PRINTSTATUS")==null?null:one.selectSingleNode("PRINTSTATUS").getText();
				
				TicketQueryResult ticketRes = new TicketQueryResult(errorCode,ticketSequence,ticketId,printStatus);
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
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element ilist = doc.addElement("ilist");
		
		for(String ticketId : this.ticketList){
			Element ielement = ilist.addElement("ielement");
			ielement.addElement("AGENTOPERATEID").setText(ticketId);
			this.keyValue.append(ticketId);
		}

		this.setMessageBody(ilist.asXML());
	}

	public List<TicketQueryResult> getBetResultList() {
		return betResultList;
	}

	public void setBetResultList(List<TicketQueryResult> betResultList) {
		this.betResultList = betResultList;
	}

}
