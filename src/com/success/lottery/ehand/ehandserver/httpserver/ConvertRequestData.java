/**
 * Title: ConvertRequestData.java
 * @Package com.success.lottery.ehand.ehandserver.httpserver
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-20 ����05:22:02
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.httpserver;

import java.io.InputStream;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.success.protocol.ticket.zzy.util.EhandUtil;
/**
 * com.success.lottery.ehand.ehandserver.httpserver
 * ConvertRequestData.java
 * ConvertRequestData
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-20 ����05:22:02
 * 
 */

public class ConvertRequestData {
	/**
	 * 
	 * Title: convertLotteryResultNotice<br>
	 * Description: <br>
	 *              <br>������������������ת��Ϊxml���ݸ�ʽ
	 * @param requestParams
	 * @return
	 */
	public static byte[] convertLotteryResultNotice(Map<String, Object> requestParams){
		if(requestParams == null){
			return null;
		}
		//ȡ����
		Object tmpParam = null;
		String key = (tmpParam = requestParams.get("KEY")) == null ? null : tmpParam.toString();
		String code = (tmpParam = requestParams.get("code")) == null ? null : tmpParam.toString();
		String issue = (tmpParam = requestParams.get("issue")) == null ? null : tmpParam.toString();
		String lotteryId = (tmpParam = requestParams.get("lotteryid")) == null ? null : tmpParam.toString();
		//��װ��Ϣ
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element caipiaotv = doc.addElement("caipiaotv");
		//��Ϣͷ
		Element ctrl = caipiaotv.addElement("ctrl");
		ctrl.addElement("userid").setText(EhandUtil.SYS_DEFINE_USERID);
		ctrl.addElement("key").setText(key);
		ctrl.addElement("command").setText(EhandUtil.commd_90001);
		//��Ϣ��
		Element iBody = caipiaotv.addElement("ilist").addElement("ielement");
		iBody.addElement("LOTTERYID").setText(lotteryId);
		iBody.addElement("ISSUE").setText(issue);
		iBody.addElement("CODE").setText(code);
		
		return doc.asXML().getBytes();
	}
	/**
	 * 
	 * Title: convertNewTermNotice<br>
	 * Description: <br>
	 *              <br>������֪ͨ���������ת��Ϊxml���ݸ�ʽ
	 * @param requestParams
	 * @return
	 */
	public static byte[] convertNewTermNotice(Map<String, Object> requestParams){
		if(requestParams == null){
			return null;
		}
		//ȡ����
		Object tmpParam = null;
		String key = (tmpParam = requestParams.get("KEY")) == null ? null : tmpParam.toString();
		String lotteryId = (tmpParam = requestParams.get("lotteryid")) == null ? null : tmpParam.toString();
		String issue = (tmpParam = requestParams.get("issue")) == null ? null : tmpParam.toString();
		String inlinelotteryid = (tmpParam = requestParams.get("inlinelotteryid")) == null ? null : tmpParam.toString();
		String starttime = (tmpParam = requestParams.get("starttime")) == null ? null : tmpParam.toString();
		String endtime = (tmpParam = requestParams.get("endtime")) == null ? null : tmpParam.toString();
		String status = (tmpParam = requestParams.get("status")) == null ? null : tmpParam.toString();
		//��װ��Ϣ
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element caipiaotv = doc.addElement("caipiaotv");
		//��Ϣͷ
		Element ctrl = caipiaotv.addElement("ctrl");
		ctrl.addElement("userid").setText(EhandUtil.SYS_DEFINE_USERID);
		ctrl.addElement("key").setText(key);
		ctrl.addElement("command").setText(EhandUtil.commd_90002);
		//��Ϣ��
		Element iBody = caipiaotv.addElement("ilist").addElement("ielement");
		iBody.addElement("LOTTERYID").setText(lotteryId);
		iBody.addElement("ISSUE").setText(issue);
		iBody.addElement("INLINELOTTERYID").setText(inlinelotteryid);
		iBody.addElement("STARTTIME").setText(starttime);
		iBody.addElement("ENDTIME").setText(endtime);
		iBody.addElement("STATUS").setText(status);
		
		return doc.asXML().getBytes();
	}
	/**
	 * 
	 * Title: convertEndTermNotice<br>
	 * Description: <br>
	 *              <br>���ڽ�֪ͨ���������ת��Ϊxml���ݸ�ʽ
	 * @param requestParams
	 * @return
	 */
	public static byte[] convertEndTermNotice(Map<String, Object> requestParams){
		if(requestParams == null){
			return null;
		}
		//ȡ����
		Object tmpParam = null;
		String key = (tmpParam = requestParams.get("KEY")) == null ? null : tmpParam.toString();
		String lotteryId = (tmpParam = requestParams.get("lotteryid")) == null ? null : tmpParam.toString();
		String issue = (tmpParam = requestParams.get("issue")) == null ? null : tmpParam.toString();
		String status = (tmpParam = requestParams.get("status")) == null ? null : tmpParam.toString();
		//��װ��Ϣ
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element caipiaotv = doc.addElement("caipiaotv");
		//��Ϣͷ
		Element ctrl = caipiaotv.addElement("ctrl");
		ctrl.addElement("userid").setText(EhandUtil.SYS_DEFINE_USERID);
		ctrl.addElement("key").setText(key);
		ctrl.addElement("command").setText(EhandUtil.commd_90003);
		//��Ϣ��
		Element iBody = caipiaotv.addElement("ilist").addElement("ielement");
		iBody.addElement("LOTTERYID").setText(lotteryId);
		iBody.addElement("ISSUE").setText(issue);
		iBody.addElement("STATUS").setText(status);
		
		return doc.asXML().getBytes();
	}

}
