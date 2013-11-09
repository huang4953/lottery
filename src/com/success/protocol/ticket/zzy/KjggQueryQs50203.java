/**
 * Title: KjggQueryQs50203.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-14 ����10:12:25
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
 * KjggQueryQs50203.java
 * KjggQueryQs50203
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-14 ����10:12:25
 * 
 */

public class KjggQueryQs50203 extends EhandDataPack {
	
	private List<QueryTerm> requestParam;
	private List<QueryTermResult> responseResult;
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param command
	 * @param userid
	 * @param key
	 * @param errCode
	 * @param errMsg
	 * @param messBody
	 */
	public KjggQueryQs50203(String command,String userid,String key,String errCode,String errMsg,String messBody){
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
	public KjggQueryQs50203(List<QueryTerm> requestParam) throws Exception{
		super();
		if(requestParam == null || requestParam.isEmpty()){
			throw new Exception("���������ѯ��������Ϊ��");
		}
		this.userid = SYS_DEFINE_USERID;
		this.command = commd_50203;
		this.sendUrl = EhandUtil.url_50203;
		this.requestParam = requestParam;
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
		
		Node listInfo = document.selectSingleNode("/list");
		if(listInfo != null){
			this.responseResult = new ArrayList<QueryTermResult> ();
			List<Node> ielemnt = listInfo.selectNodes("element");
			for(Node one : ielemnt){
				String lotteryId = one.selectSingleNode("LOTTERYID").getText();
				String termNo = one.selectSingleNode("ISSUE").getText();
				String lotteryResult = one.selectSingleNode("BONUSCODE").getText();
				
				QueryTermResult oneResult = new QueryTermResult();
				oneResult.setLotteryId(lotteryId);
				oneResult.setTermNo(termNo);
				oneResult.setLotteryResult(lotteryResult);
				this.responseResult.add(oneResult);
			}
		}

	}

	/* (�� Javadoc)
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



	public List<QueryTermResult> getResponseResult() {
		return responseResult;
	}



	public void setResponseResult(List<QueryTermResult> responseResult) {
		this.responseResult = responseResult;
	}

}
