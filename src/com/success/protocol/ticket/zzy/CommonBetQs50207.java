/**
 * Title: CommonBetQs50207.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-13 上午11:41:36
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

import com.success.protocol.ticket.zzy.model.CommonTicketBet;
import com.success.protocol.ticket.zzy.model.TicketBetResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;

/**
 * com.success.protocol.ticket.zzy
 * CommonBetQs50207.java
 * CommonBetQs50207
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-13 上午11:41:36
 * 
 */

public class CommonBetQs50207 extends EhandDataPack {
	
	private List<CommonTicketBet> betRequest;
	private List<TicketBetResult> betResult;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 * @param key
	 * @param messBody
	 */
	public CommonBetQs50207(String command,String userid,String key,String errCode,String errMsg,String messBody){
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
	 * @param betRequest
	 */
	public CommonBetQs50207(List<CommonTicketBet> betRequest) throws Exception{
		super();
		if(betRequest == null || betRequest.isEmpty()){
			throw new Exception("投注参数不能为空！");
		}
		this.userid = SYS_DEFINE_USERID;
		this.command = EhandUtil.commd_50207;
		this.sendUrl = EhandUtil.url_50207;
		this.betRequest = betRequest;
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
				String ticketErrorCode = one.selectSingleNode("ERRORCODE")==null?null:one.selectSingleNode("ERRORCODE").getText();
				String operateId = one.selectSingleNode("OPERATEID")==null?null:one.selectSingleNode("OPERATEID").getText();
				String accountValue = one.selectSingleNode("ACCOUNTVALUE")==null?null:one.selectSingleNode("ACCOUNTVALUE").getText();
				String agentOperateId = one.selectSingleNode("AGENTOPERATEID")==null?null:one.selectSingleNode("AGENTOPERATEID").getText();
				
				TicketBetResult ticketRes = new TicketBetResult(this.errorcode,this.errormsg,ticketErrorCode,operateId,agentOperateId,accountValue);
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
		
		for(CommonTicketBet ticket : this.betRequest){
			Element ielement = ilist.addElement("ielement");
			ielement.addElement("ISSUE").setText(ticket.getTermNo());
			ielement.addElement("LOTTERYID").setText(ticket.getLotteryId());
			ielement.addElement("SCHEMETITLE").setText(ticket.getLotteryId());
			ielement.addElement("SCHEMEDESCRIPTION").setText(ticket.getLotteryId());
			ielement.addElement("SCHEMEQUOTIENT").setText(ticket.getSchemeQuotient());
			
			String[] betCodeArr = ticket.getSchemeLotteryInfo().split("\\^",-1);
			Arrays.fill(betCodeArr, ticket.getLotterySaleid());
			StringBuffer ehandBetType = new StringBuffer();
			for(int i = 0; i < betCodeArr.length ; i++){
				ehandBetType.append(betCodeArr[i]);
				if(i != betCodeArr.length -1){
					ehandBetType.append("^");
				}
			}
			ielement.addElement("LOTTERYSALEID").setText(ehandBetType.toString());
			
			ielement.addElement("SCHEMELOTTERYINFO").setText(ticket.getSchemeLotteryInfo());
			ielement.addElement("SCHEMENUMBERS").setText(ticket.getSchemeNumbers());
			ielement.addElement("LOTTERYNUMBERS").setText(ticket.getLotteryNumbers());
			ielement.addElement("SCHEMEVALUE").setText(ticket.getSchemeValue());
			ielement.addElement("AGENTOPERATEID").setText(ticket.getAgentOperateId());
			ielement.addElement("VIEWFLAG").setText(ticket.getViewFlag());
			ielement.addElement("VIEWLIMIT").setText(ticket.getViewlimit());
			ielement.addElement("ISCHECKOFF").setText(ticket.getIsCheckOff());
			ielement.addElement("CHECKOFFQUOTIENT").setText(ticket.getCheckOffQuotient());
			ielement.addElement("QUOTIENTS").setText(ticket.getQuotients());
			ielement.addElement("INVESTTYPEID").setText(ticket.getInvestTypeId());
			ielement.addElement("ISAPPEND").setText(ticket.getIsAppend());
			
			this.keyValue.append(ticket.getTermNo()).append(ticket.getLotteryId());
			this.keyValue.append(ticket.getSchemeTitle()).append(ticket.getSchemeQuotient()).append(ehandBetType.toString());
			this.keyValue.append(ticket.getSchemeLotteryInfo()).append(ticket.getSchemeNumbers()).append(ticket.getLotteryNumbers());
			this.keyValue.append(ticket.getSchemeValue()).append(ticket.getAgentOperateId()).append(ticket.getViewFlag());
			this.keyValue.append(ticket.getViewlimit()).append(ticket.getIsCheckOff()).append(ticket.getCheckOffQuotient());
			this.keyValue.append(ticket.getQuotients()).append(ticket.getInvestTypeId());
			this.keyValue.append(ticket.getIsAppend());
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
