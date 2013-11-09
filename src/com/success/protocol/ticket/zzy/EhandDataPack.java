/**
 * Title: EhandDataPack.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-11 上午11:49:41
 * @version V1.0
 */
package com.success.protocol.ticket.zzy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.success.lottery.ehand.ehandserver.bussiness.EhandLog;
import com.success.lottery.ehand.ehandserver.bussiness.EhandQueryService;
import com.success.lottery.exception.LotteryException;
import com.success.protocol.lbap.LBAP_Error;
import com.success.protocol.ticket.zzy.util.EhandUtil;
import com.success.protocol.ticket.zzy.util.MD5Util;

/**
 * com.success.protocol.ticket.zzy
 * EhandDataPack.java
 * EhandDataPack
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-11 上午11:49:41
 * 
 */

public abstract class EhandDataPack extends EhandUtil{
	private static Log logger = LogFactory.getLog(EhandDataPack.class);
	private static EhandLog sendLog = EhandLog.getInstance("E_SENDMSG");//记录发送的掌中奕的消息,xml格式
	private static EhandLog noticeLog = EhandLog.getInstance("E_NOTICEMSG");//记录收到的掌中奕的消息,xml格式
	
	protected String sendUrl;//发送请求的地址，防止各请求的路径不一致
	protected String userid;//
	protected String key;//
	protected String command;//
	protected String errorcode;//
	protected String errormsg;//
	
	protected String messageBody;
	
	protected String requestMessage;//发送的请求消息
	protected String responseMessage;//接收到的消息
	
	protected StringBuffer keyValue = new StringBuffer();//发送消息体中内容拼成的字符串
	
	/**
	 * 
	 * Title: decodeMessageBody<br>
	 * Description: <br>
	 *              <br>将消息解析为对象
	 * @throws Exception
	 */
	public abstract void decodeMessageBody() throws Exception;
	/**
	 * 
	 * Title: encodeMessageBody<br>
	 * Description: <br>
	 *              <br>将对象转换成消息
	 * @throws Exception
	 */
	public abstract void encodeMessageBody() throws Exception;
	
	/**
	 * 
	 * Title: validDataPack<br>
	 * Description: <br>
	 *              <br>校验收到的通知是否正确
	 * @param commd
	 * @param userId
	 * @param key
	 * @param messageBody
	 * @return
	 */
	protected static String validDataPack(String commd,String userId,String key,String messageBody){
		
		if(StringUtils.isEmpty(commd) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(key)){
			return ERRORCODE01;
		}else{
			if(! Arrays.asList(NOTICE_COMMAND).contains(commd)){
				return ERRORCODE02;
			}
			if(! SYS_DEFINE_USERID.equals(userId)){
				return ERRORCODE03;
			}
			//校验md5,调试阶段先不校验,根据接收的消息command不同校验码的组成也不同
			if("0".equals(check_md5)){
				try {
					String sysMd5 = null;
					
					if(commd_90001.equals(commd) || commd_90003.equals(commd)){
						 String nowDateStr = new SimpleDateFormat("yyyy-mm-dd").format(new java.util.Date());
						 String sysKeyValue = SYS_DEFINE_USERID + SYS_DEFINE_KEY + nowDateStr;
						 sysMd5 = MD5Util.getMD5String(sysKeyValue).toUpperCase();
					}else{
						String messBodyKeyValue = new ZzyXmlUtlt().decodeKeyValue(messageBody).toString();
						String sysMsgBodyValue = SYS_DEFINE_KEY + messBodyKeyValue;
						sysMd5 = MD5Util.getMD5String(sysMsgBodyValue).toUpperCase();
					}
					
					if(! key.equals(sysMd5)){
						return ERRORCODE04;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return ERRORCODE04;
				}
			}
			return ERRORCODE00;
		}
	}
	/**
	 * 
	 * Title: readPack<br>
	 * Description: <br>
	 *              <br>
	 * @param in
	 * @return
	 */
	public static EhandDataPack readPack(InputStream in){
		EhandDataPack dp = null;
		String resCmd = null;
		String resUser = null;
		String resKey = null;
		String resMessageBody = null;
		
		try {
			//解析消息结构
			try {
				SAXReader saxR = new SAXReader();
				saxR.setValidation(false);
				Document doc = saxR.read(in);
				noticeLog.eNoticeMsgLog("0", (doc==null?"":doc.asXML()));
				Element eCaiPiaoTv = doc.getRootElement();
				Element eCtrl = eCaiPiaoTv.element("ctrl");
				resCmd = eCtrl.elementText("command");
				resUser = eCtrl.elementText("userid");
				resKey = eCtrl.elementText("key");
				resMessageBody = eCaiPiaoTv.element("ilist").asXML();
			} catch (Exception e) {
				logger.error("收到掌中奕通知,解析收到的通知消息出错:", e);
				return new EhandErrorQs(resCmd,resUser,ERRORCODE01);
			}
			
			//校验收到的通知消息是否正确,对不同的resCmd分别需要做不同的md5,还没有做
			
			String isValide = validDataPack(resCmd,resUser,resKey,resMessageBody);
			
			if(! isValide.equals(ERRORCODE00)){
				logger.error("收到掌中奕通知,但消息校验未通过,校验结果:" + isValide);
				dp = new EhandErrorQs(resCmd,resUser,isValide);
				return dp;
			}
			
			
			//对收到的消息做业务
			if(commd_50204.equals(resCmd)){//出票状态通知
				dp = new TicketNoticeQs50204(resCmd,resUser,resKey,resMessageBody);
			}else if(commd_90001.equals(resCmd)){//开奖公告通知
				dp = new KjggNoticeQs(resCmd,resUser,resKey,resMessageBody);
			}else if(commd_90002.equals(resCmd)){//新期通知
				dp = new TermNewNoticeQs(resCmd,resUser,resKey,resMessageBody);
			}else if(commd_90003.equals(resCmd)){//期结通知
				dp = new TermEndNoticeQs(resCmd,resUser,resKey,resMessageBody);
			}
			
			dp.decodeMessageBody();
			
		} catch (Exception e) {
			e.printStackTrace();
			return new EhandErrorQs(resCmd,resUser,ERRORCODE05);
		}
		
		return dp;
	}
	/**
	 * 
	 * Title: writePack<br>
	 * Description: <br>
	 *              <br>
	 * @return
	 * @exception LotteryException
	 *                            <br>EhandUtil.E_01_CODE,发送请求通讯错误
	 *                            <br>EhandUtil.E_02_CODE,发送请求通讯返回但HTTP响应状态不正确
	 *                            <br>EhandUtil.E_03_CODE,发送请求通讯正确返回但消息解析错误
	 */
	public EhandDataPack writePack() throws LotteryException{
		EhandDataPack responseData = null;
		long requestTime = new java.util.Date().getTime();
		try{
			logger.debug("************begin发送请求到掌中奕begin***************"+requestTime);
			logger.debug("掌中奕请求命令码:"+this.getCommand());
			logger.debug("掌中奕请求路径:"+this.getSendUrl());
			
			//组装请求消息
			this.encodeMessage();
			
			sendLog.eSendMsgLog("0", this.requestMessage);
			logger.debug("掌中奕请求,请求消息:" + this.requestMessage);
			logger.debug("掌中奕请求,请求消息摘要内容:"+(SYS_DEFINE_KEY + this.keyValue.toString()));
			
			//发送请求
			HttpResponse response = null;
			try{
				response = sendMsgData(this.sendUrl,this.requestMessage,"utf-8");
				if(response == null){
					throw new Exception();
				}
			}catch(Exception e){
				logger.error("掌中奕请求通讯异常", e);
				throw new LotteryException(E_01_CODE,E_01_DESC);
			}
			
			int reponseStatus = response.getStatusLine().getStatusCode();//得到响应的状态码
			
			if(reponseStatus != 200){//响应不正确
				logger.error("掌中奕请求通讯返回但状态码不正确,返回的状态码:"+reponseStatus);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
			
			HttpEntity resentity = response.getEntity();
			
			//解析收到的消息
			SAXReader saxR = new SAXReader();
			saxR.setValidation(false);
			Document doc = saxR.read(resentity.getContent());
			sendLog.eSendMsgLog("1", (doc!=null?doc.asXML():""));
			logger.debug("掌中奕请求,收到的消息:" + (doc!=null?doc.asXML():""));
			
			//解析通用的消息头
			Node node = null;
			String resCommand = doc.selectSingleNode("/caipiaotv/ctrl/command").getText();
			String resUserid = doc.selectSingleNode("/caipiaotv/ctrl/userid").getText();
			String resKey = doc.selectSingleNode("/caipiaotv/ctrl/key").getText();
			String resErrorCode = (node = doc.selectSingleNode("/caipiaotv/ctrl/errorcode")) == null ? null : node.getText();
			String resErrorMsg = (node = doc.selectSingleNode("/caipiaotv/ctrl/errormsg")) == null ? null : node.getText();
			//解析消息体
			String resMessageBody = doc.selectSingleNode("/caipiaotv/list").asXML();
			
			logger.debug("掌中奕请求,收到的消息体:" + resMessageBody);
			
			// 消息校验，还没有做,如果收到的返回消息校验没有通过如何处理
			String contentValue = new ZzyXmlUtlt().decodeKeyValue(resMessageBody);
			logger.debug("掌中奕请求,收到的消息摘要内容:"+(SYS_DEFINE_KEY + contentValue));
			String receiveDataMd5 = MD5Util.getMD5String(SYS_DEFINE_KEY + contentValue).toUpperCase();
			logger.debug("掌中奕请求,收到的消息摘要:" + receiveDataMd5);
			
			if(! receiveDataMd5.equals(resKey)){//如果收到的消息摘要校验不正确，还没有处理
				logger.error("掌中奕请求,收到的消息摘要校验未通过!");
			}
			
			//处理收到的数据
			if(commd_50200.equals(this.command)){//奖期查询
				responseData = new TermQueryQs50200(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50201.equals(this.command)){//多乐彩出票
				responseData = new DlcBetQs50201(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50203.equals(this.command)){//开奖公告查询
				responseData = new KjggQueryQs50203(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50205.equals(this.command)){//出票状态查询
				responseData = new TicketQueryQs50205(resCommand,resUserid,resKey,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50206.equals(this.command)){//获取中奖数据
				responseData = new CashQueryQs50206(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50207.equals(this.command)){//其他玩法出票
				responseData = new CommonBetQs50207(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50208.equals(this.command)){//销售情况查询
				responseData = new TermSaleQs50208(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50209.equals(this.command)){//奖期状态查询
				responseData = new TermQueryQs50209(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}
			
			//将收到的消息解析为字符串
//			StringBuffer sb = new StringBuffer(); 
//			BufferedReader reader = new BufferedReader(
//			new InputStreamReader(resentity.getContent(), "utf-8"));
//			String line;
//			while ((line = reader.readLine()) != null) {
//				sb.append(line);
//			}
			
		}catch(LotteryException ex){
			throw ex;
		}catch(Exception e){
			logger.error("掌中奕请求发生异常", e);
			throw new LotteryException(E_03_CODE,E_03_DESC);
		}finally{
			logger.debug("************end发送请求到掌中奕end***************"+requestTime);
		}
		
		return responseData;
	}
	
	/**
	 * 
	 * Title: encodeMessage<br>
	 * Description: <br>
	 *              <br>封装完整的请求消息体
	 * @return
	 * @throws DocumentException
	 */
	public void encodeMessage() throws Exception {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element caipiaotv = doc.addElement("caipiaotv");
		//消息头
		Element ctrl = caipiaotv.addElement("ctrl");
		ctrl.addElement("userid").setText(this.userid);
		ctrl.addElement("command").setText(this.command);
		
		//消息体
		this.encodeMessageBody();
		
		this.key = MD5Util.getMD5String(SYS_DEFINE_KEY+this.keyValue.toString()).toUpperCase();
		ctrl.addElement("key").setText(this.key);
		
		if(this.messageBody != null && StringUtils.isNotEmpty(this.messageBody)){
			Node listInfo = DocumentHelper.parseText(this.messageBody).selectSingleNode("/ilist");
			caipiaotv.add(listInfo);
		}
		
		this.requestMessage = doc.asXML();
		
	}
	
	/**
	 * 
	 * Title: encodeNoticeMessage<br>
	 * Description: <br>
	 *              <br>封装通知的返回消息
	 * @throws Exception
	 */
	public String encodeNoticeMessage() throws Exception {
		
		this.key = MD5Util.getMD5String(this.keyValue.toString());
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element caipiaotv = doc.addElement("caipiaotv");
		//消息头
		Element ctrl = caipiaotv.addElement("ctrl");
		ctrl.addElement("userid").setText(StringUtils.isEmpty(this.userid)?"未知":this.userid);
		ctrl.addElement("key").setText(StringUtils.isEmpty(this.key)?"未知":this.key);
		ctrl.addElement("command").setText(StringUtils.isEmpty(this.command)?"未知":this.command);
		ctrl.addElement("errorcode").setText(StringUtils.isEmpty(this.errorcode)?"未知":this.errorcode);
		//消息体
		
		this.encodeMessageBody();
		
		if(this.messageBody != null && StringUtils.isNotEmpty(this.messageBody)){
			Node listInfo = DocumentHelper.parseText(this.messageBody).selectSingleNode("/list");
			caipiaotv.add(listInfo);
		}
		
		return doc.asXML();
		//this.requestMessage = doc.asXML();
		
	}
	/**
	 * 
	 * Title: sendMsgData<br>
	 * Description: <br>
	 *              <br>发送请求并得到相应结果
	 * @param sendUrl
	 * @param sendMessage
	 * @param sendCharset
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private HttpResponse sendMsgData(String sendUrl,String sendMessage,String sendCharset) throws ClientProtocolException, IOException{
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setContentCharset(params, sendCharset);
	    HttpProtocolParams.setUseExpectContinue(params, true);
		HttpClient httpClient = new DefaultHttpClient(params);
		
		HttpPost httpPost = new HttpPost(sendUrl);
		StringEntity entity = new StringEntity(sendMessage, sendCharset);
		httpPost.setEntity(entity);
		//发送请求
		HttpResponse response = httpClient.execute(httpPost);
		return response;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public String getSendUrl() {
		return sendUrl;
	}
	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
}
/**
 * 
 * com.success.protocol.ticket.zzy
 * EhandDataPack.java
 * ZzyXmlUtlt
 * (接口解析公用类)
 * @author gaoboqin
 * 2011-1-18 下午05:08:32
 *
 */
class ZzyXmlUtlt{
	private StringBuffer innerKeyValue = new StringBuffer();
	
	/**
	 * 
	 * Title: getKeyElementValue<br>
	 * Description: <br>
	 *              <br>遍历消息体，取出全部的节点内容，拼成字符串
	 * @param element
	 */
	public void getKeyElementValue(Element element) {
		List elements = element.elements();
		if (elements.size() == 0) {
			// 没有子元素
			//String xpath = element.getPath();
			String value = element.getTextTrim();
			innerKeyValue.append(value);
		} else {
			// 有子元素
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				// 递归遍历
				getKeyElementValue(elem);
			}
		}
	}
	/**
	 * 
	 * Title: decodeKeyValue<br>
	 * Description: <br>
	 *              <br>解析消息体的所有节点的值，拼成字符串
	 * @param messageBody
	 * @return
	 * @throws Exception
	 */
	public String decodeKeyValue(String messageBody) throws Exception{
		try{
			Document document = DocumentHelper.parseText(messageBody);
			//解析消息体的内容
			Element rootElem = document.getRootElement();
			this.getKeyElementValue(rootElem);
			return this.innerKeyValue.toString();
		}catch(Exception e){
			throw e;
		}
	}
	public StringBuffer getInnerKeyValue() {
		return innerKeyValue;
	}
	public void setInnerKeyValue(StringBuffer innerKeyValue) {
		this.innerKeyValue = innerKeyValue;
	}
	
}
