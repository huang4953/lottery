/**
 * Title: TermSaleQs50208.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-13 上午11:34:54
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.success.protocol.ticket.zzy.model.QueryTerm;
import com.success.protocol.ticket.zzy.model.TermSaleResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;

/**
 * com.success.protocol.ticket.zzy
 * TermSaleQs50208.java
 * TermSaleQs50208
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-13 上午11:34:54
 * 
 */

public class TermSaleQs50208 extends EhandDataPack {
	
	private List<QueryTerm> queryTerms;
	private List<TermSaleResult> termSaleResult;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 * @param key
	 * @param messBody
	 */
	public TermSaleQs50208(String command,String userid,String key,String errCode,String errMsg,String messBody){
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
	 * @param queryTerms
	 */
	public TermSaleQs50208(List<QueryTerm> queryTerms) throws Exception{
		super();
		if(queryTerms == null || queryTerms.isEmpty()){
			throw new Exception("查询参数不能为空");
		}
		this.userid = SYS_DEFINE_USERID;
		this.command = commd_50208;
		this.sendUrl = EhandUtil.url_50208;
		this.queryTerms = queryTerms;
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
			this.termSaleResult = new ArrayList<TermSaleResult> ();
			List<Node> ielemnt = listInfo.selectNodes("element");
			for(Node one : ielemnt){
				String errorCode = one.selectSingleNode("ERRORCODE").getText();
				String lotteryId = one.selectSingleNode("LOTTERYID").getText();
				String termNo = one.selectSingleNode("ISSUE").getText();
				String saleMoney = one.selectSingleNode("SALEMONEY").getText();
				String winPrize = one.selectSingleNode("BONUSMONEY").getText();
				
				TermSaleResult oneTermRe = new TermSaleResult(errorCode,termNo,lotteryId,saleMoney,winPrize);
				this.termSaleResult.add(oneTermRe);
				
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
		
		for(QueryTerm term : this.queryTerms){
			Element ielement = ilist.addElement("ielement");
			ielement.addElement("ISSUE").setText(term.getTermNo());
			ielement.addElement("LOTTERYID").setText(term.getLotteryId());
			
			this.keyValue.append(term.getTermNo()).append(term.getLotteryId());
		}
		this.setMessageBody(ilist.asXML());
	}



	public List<TermSaleResult> getTermSaleResult() {
		return termSaleResult;
	}



	public void setTermSaleResult(List<TermSaleResult> termSaleResult) {
		this.termSaleResult = termSaleResult;
	}

}
