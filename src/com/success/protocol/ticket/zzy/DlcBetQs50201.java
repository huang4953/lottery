/**
 * Title: DlcBetQs50201.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-14 上午10:23:21
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.success.protocol.ticket.zzy.model.TicketBet;
import com.success.protocol.ticket.zzy.model.TicketBetResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;

/**
 * com.success.protocol.ticket.zzy
 * DlcBetQs50201.java
 * DlcBetQs50201
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-14 上午10:23:21
 * 
 */

public class DlcBetQs50201 extends EhandDataPack {
	
	private List<TicketBet> betTicket;
	private List<TicketBetResult> betResult;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 * @param key
	 */
	public DlcBetQs50201(String command,String userid,String key,String errCode,String errMsg,String messBody){
		super();
		this.command = command;
		this.userid = userid;
		this.key = key;
		this.errorcode = errCode;
		this.errormsg = errMsg;
		this.messageBody = messBody;
	}

	/**
	 *Title: 
	 *Description: 
	 * @param betTicket
	 */
	public DlcBetQs50201(List<TicketBet> betTicket) throws Exception{
		super();
		if(betTicket == null || betTicket.isEmpty()){
			throw new Exception("投注请求参数不能为空!");
		}
		this.userid = SYS_DEFINE_USERID;
		this.command = commd_50201;
		this.sendUrl = EhandUtil.url_50201;
		this.betTicket = betTicket;
		
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
			this.betResult = new ArrayList<TicketBetResult> ();
			List<Node> ielemnt = listInfo.selectNodes("element");
			for(Node one : ielemnt){
				String errorCode = one.selectSingleNode("ERRORCODE")==null?null:one.selectSingleNode("ERRORCODE").getText();
				String operateId = one.selectSingleNode("OPERATEID")==null?null:one.selectSingleNode("OPERATEID").getText();
				String accountValue = one.selectSingleNode("ACCOUNTVALUE")==null?null:one.selectSingleNode("ACCOUNTVALUE").getText();
				String agentOperateId = one.selectSingleNode("AGENTOPERATEID")==null?null:one.selectSingleNode("AGENTOPERATEID").getText();
				
				TicketBetResult ticketRes = new TicketBetResult(this.errorcode,this.errormsg,errorCode,operateId,agentOperateId,accountValue);
				this.betResult.add(ticketRes);
				
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
		
		for(TicketBet ticket : this.betTicket){
			Element ielement = ilist.addElement("ielement");
			ielement.addElement("ISSUE").setText(ticket.getTermNo());
			ielement.addElement("LOTTERYID").setText(ticket.getLotteryId());
			ielement.addElement("LOTTERYCHILDID").setText(ticket.getPlayType());
			//ielement.addElement("LOTTERYSALEID").setText(ticket.getBetType());
			String[] betCodeArr = ticket.getBetCode().split("\\^",-1);
			Arrays.fill(betCodeArr, ticket.getBetType());
			StringBuffer ehandBetType = new StringBuffer();
			for(int i = 0; i < betCodeArr.length ; i++){
				ehandBetType.append(betCodeArr[i]);
				if(i != betCodeArr.length -1){
					ehandBetType.append("^");
				}
			}
			ielement.addElement("LOTTERYSALEID").setText(ehandBetType.toString());
			ielement.addElement("LOTTERYCODE").setText(ticket.getBetCode());
			ielement.addElement("SCHEMENUMBERS").setText(ticket.getBetmultiple());
			ielement.addElement("LOTTERYNUMBERS").setText(ticket.getBetCount());
			ielement.addElement("SCHEMEVALUE").setText(ticket.getBetAmount());
			ielement.addElement("AGENTOPERATEID").setText(ticket.getTicketId());
			
			this.keyValue.append(ticket.getTermNo()).append(
					ticket.getLotteryId())
					.append(ticket.getPlayType()).append(
							ehandBetType.toString())
					.append(ticket.getBetCode()).append(
							ticket.getBetmultiple()).append(
							ticket.getBetCount()).append(
							ticket.getBetAmount()).append(
							ticket.getTicketId());
		}
		this.setMessageBody(ilist.asXML());
	}

	public List<TicketBetResult> getBetResult() {
		return betResult;
	}

	public void setBetResult(List<TicketBetResult> betResult) {
		this.betResult = betResult;
	}

}
