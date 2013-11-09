/**
 * Title: CashQueryQs50206.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-14 上午09:56:22
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.success.protocol.ticket.zzy.model.CashResult;
import com.success.protocol.ticket.zzy.model.QueryTerm;
import com.success.protocol.ticket.zzy.util.EhandUtil;

/**
 * com.success.protocol.ticket.zzy
 * CashQueryQs50206.java
 * CashQueryQs50206
 * 获取中奖数据接口
 * @author gaoboqin
 * 2011-1-14 上午09:56:22
 * 
 */

public class CashQueryQs50206 extends EhandDataPack {
	
	private List<QueryTerm> requestParam;
	private List<CashResult> cashResultList;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 * @param key
	 * @param messBody
	 */
	public CashQueryQs50206(String command,String userid,String key,String errCode,String errMsg,String messBody){
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
	 * @param requestParam
	 */
	public CashQueryQs50206(List<QueryTerm> requestParam) throws Exception{
		super();
		if(requestParam == null || requestParam.isEmpty()){
			throw new Exception("兑奖结果请求参数不能为空！");
		}
		this.userid = SYS_DEFINE_USERID;
		this.command = commd_50206;
		this.sendUrl = EhandUtil.url_50206;
		this.requestParam = requestParam;
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
			this.cashResultList = new ArrayList<CashResult> ();
			List<Node> ielemnt = listInfo.selectNodes("element");
			for(Node one : ielemnt){
				String ticketSequence = one.selectSingleNode("AGENTOPERATEID").getText();
				String preTaxPrize = one.selectSingleNode("BONUSVALUE").getText();
				String taxPrize = one.selectSingleNode("TAXBONUSVALUE").getText();
				
				CashResult cashRes = new CashResult(ticketSequence,preTaxPrize,taxPrize);
				
				this.cashResultList.add(cashRes);
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
		
		for(QueryTerm term : this.requestParam){
			Element ielement = ilist.addElement("ielement");
			ielement.addElement("LOTTERYID").setText(term.getLotteryId());
			ielement.addElement("ISSUE").setText(term.getTermNo());
			this.keyValue.append(term.getLotteryId()).append(term.getTermNo());
		}
		
		this.setMessageBody(ilist.asXML());
	}



	public List<CashResult> getCashResultList() {
		return cashResultList;
	}



	public void setCashResultList(List<CashResult> cashResultList) {
		this.cashResultList = cashResultList;
	}

}
