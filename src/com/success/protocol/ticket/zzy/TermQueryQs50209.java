/**
 * Title: TermQueryQs50209.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-13 上午11:22:23
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
import com.success.protocol.ticket.zzy.model.QueryTermResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;

/**
 * com.success.protocol.ticket.zzy
 * TermQueryQs50209.java
 * TermQueryQs50209
 * 奖期状态查询
 * @author gaoboqin
 * 2011-1-13 上午11:22:23
 * 
 */

public class TermQueryQs50209 extends EhandDataPack {
	
	private List<QueryTerm> requestParam;
	private List<QueryTermResult> responseResult;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 * @param key
	 * @param messBody
	 */
	public TermQueryQs50209(String command,String userid,String key,String errCode,String errMsg,String messBody){
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
	public TermQueryQs50209(List<QueryTerm> requestParam) throws Exception{
		super();
		if(requestParam == null || requestParam.isEmpty()){
			throw new Exception("查询参数不能为空！");
		}
		this.userid = SYS_DEFINE_USERID;
		this.command = commd_50209;
		this.sendUrl = EhandUtil.url_50209;
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
			this.responseResult = new ArrayList<QueryTermResult> ();
			List<Node> ielemnt = listInfo.selectNodes("element");
			for(Node one : ielemnt){
				String lotteryId = one.selectSingleNode("LOTTERYID").getText();
				String termNo = one.selectSingleNode("ISSUE").getText();
				String status = one.selectSingleNode("STATUS").getText();
				
				QueryTermResult oneTermRe = new QueryTermResult(lotteryId,termNo,status);
				this.responseResult.add(oneTermRe);
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
			ielement.addElement("ISSUE").setText(term.getTermNo());
			ielement.addElement("LOTTERYID").setText(term.getLotteryId());
			//this.keyValue.append(term.getTermNo()).append(term.getLotteryId());
			this.keyValue.append(term.getLotteryId()).append(term.getTermNo());
		}
		
		this.setMessageBody(ilist.asXML());
	}

	public List<QueryTermResult> getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(List<QueryTermResult> responseResult) {
		this.responseResult = responseResult;
	}

}
