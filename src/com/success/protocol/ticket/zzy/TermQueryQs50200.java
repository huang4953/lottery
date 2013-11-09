/**
 * Title: TermQueryQs50200.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-14 ����10:29:41
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.success.protocol.ticket.zzy.model.QueryTermResult;
import com.success.protocol.ticket.zzy.util.EhandUtil;
import com.success.protocol.ticket.zzy.util.MD5Util;

/**
 * com.success.protocol.ticket.zzy
 * TermQueryQs50200.java
 * TermQueryQs50200
 * (���ڲ�ѯ����Ҫ�������������۵Ĳ���)
 * @author gaoboqin
 * 2011-1-14 ����10:29:41
 * 
 */

public class TermQueryQs50200 extends EhandDataPack {
	
	private List<String> LotteryIds;
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
	 */
	public TermQueryQs50200(String command,String userid,String key,String errCode,String errMsg,String messBody){
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
	 * @param lotteryIds
	 */
	public TermQueryQs50200(List<String> lotteryIds) throws Exception{
		super();
		if(lotteryIds == null || lotteryIds.isEmpty()){
			throw new Exception("���ڲ�ѯ��������Ϊ��!");
		}
		this.userid = SYS_DEFINE_USERID;
		this.command = commd_50200;
		this.sendUrl = EhandUtil.url_50200;
		LotteryIds = lotteryIds;
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
				String startTime = one.selectSingleNode("STARTTIMESTAMP").getText();
				String endTime = one.selectSingleNode("ENDTIMESTAMP").getText();
				String status = one.selectSingleNode("STATUS").getText();
				QueryTermResult oneTermRe = new QueryTermResult(lotteryId,termNo,startTime,endTime,status);
				this.responseResult.add(oneTermRe);
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
		
		for(String lotteryId : this.LotteryIds){
			Element ielement = ilist.addElement("ielement");
			ielement.addElement("LOTTERYID").setText(lotteryId);
			this.keyValue.append(lotteryId);
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
