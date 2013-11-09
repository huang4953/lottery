/**
 * Title: EhandDataPack.java
 * @Package com.success.protocol.ticket.zzy
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-11 ����11:49:41
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
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-11 ����11:49:41
 * 
 */

public abstract class EhandDataPack extends EhandUtil{
	private static Log logger = LogFactory.getLog(EhandDataPack.class);
	private static EhandLog sendLog = EhandLog.getInstance("E_SENDMSG");//��¼���͵������ȵ���Ϣ,xml��ʽ
	private static EhandLog noticeLog = EhandLog.getInstance("E_NOTICEMSG");//��¼�յ��������ȵ���Ϣ,xml��ʽ
	
	protected String sendUrl;//��������ĵ�ַ����ֹ�������·����һ��
	protected String userid;//
	protected String key;//
	protected String command;//
	protected String errorcode;//
	protected String errormsg;//
	
	protected String messageBody;
	
	protected String requestMessage;//���͵�������Ϣ
	protected String responseMessage;//���յ�����Ϣ
	
	protected StringBuffer keyValue = new StringBuffer();//������Ϣ��������ƴ�ɵ��ַ���
	
	/**
	 * 
	 * Title: decodeMessageBody<br>
	 * Description: <br>
	 *              <br>����Ϣ����Ϊ����
	 * @throws Exception
	 */
	public abstract void decodeMessageBody() throws Exception;
	/**
	 * 
	 * Title: encodeMessageBody<br>
	 * Description: <br>
	 *              <br>������ת������Ϣ
	 * @throws Exception
	 */
	public abstract void encodeMessageBody() throws Exception;
	
	/**
	 * 
	 * Title: validDataPack<br>
	 * Description: <br>
	 *              <br>У���յ���֪ͨ�Ƿ���ȷ
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
			//У��md5,���Խ׶��Ȳ�У��,���ݽ��յ���Ϣcommand��ͬУ��������Ҳ��ͬ
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
			//������Ϣ�ṹ
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
				logger.error("�յ�������֪ͨ,�����յ���֪ͨ��Ϣ����:", e);
				return new EhandErrorQs(resCmd,resUser,ERRORCODE01);
			}
			
			//У���յ���֪ͨ��Ϣ�Ƿ���ȷ,�Բ�ͬ��resCmd�ֱ���Ҫ����ͬ��md5,��û����
			
			String isValide = validDataPack(resCmd,resUser,resKey,resMessageBody);
			
			if(! isValide.equals(ERRORCODE00)){
				logger.error("�յ�������֪ͨ,����ϢУ��δͨ��,У����:" + isValide);
				dp = new EhandErrorQs(resCmd,resUser,isValide);
				return dp;
			}
			
			
			//���յ�����Ϣ��ҵ��
			if(commd_50204.equals(resCmd)){//��Ʊ״̬֪ͨ
				dp = new TicketNoticeQs50204(resCmd,resUser,resKey,resMessageBody);
			}else if(commd_90001.equals(resCmd)){//��������֪ͨ
				dp = new KjggNoticeQs(resCmd,resUser,resKey,resMessageBody);
			}else if(commd_90002.equals(resCmd)){//����֪ͨ
				dp = new TermNewNoticeQs(resCmd,resUser,resKey,resMessageBody);
			}else if(commd_90003.equals(resCmd)){//�ڽ�֪ͨ
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
	 *                            <br>EhandUtil.E_01_CODE,��������ͨѶ����
	 *                            <br>EhandUtil.E_02_CODE,��������ͨѶ���ص�HTTP��Ӧ״̬����ȷ
	 *                            <br>EhandUtil.E_03_CODE,��������ͨѶ��ȷ���ص���Ϣ��������
	 */
	public EhandDataPack writePack() throws LotteryException{
		EhandDataPack responseData = null;
		long requestTime = new java.util.Date().getTime();
		try{
			logger.debug("************begin��������������begin***************"+requestTime);
			logger.debug("����������������:"+this.getCommand());
			logger.debug("����������·��:"+this.getSendUrl());
			
			//��װ������Ϣ
			this.encodeMessage();
			
			sendLog.eSendMsgLog("0", this.requestMessage);
			logger.debug("����������,������Ϣ:" + this.requestMessage);
			logger.debug("����������,������ϢժҪ����:"+(SYS_DEFINE_KEY + this.keyValue.toString()));
			
			//��������
			HttpResponse response = null;
			try{
				response = sendMsgData(this.sendUrl,this.requestMessage,"utf-8");
				if(response == null){
					throw new Exception();
				}
			}catch(Exception e){
				logger.error("����������ͨѶ�쳣", e);
				throw new LotteryException(E_01_CODE,E_01_DESC);
			}
			
			int reponseStatus = response.getStatusLine().getStatusCode();//�õ���Ӧ��״̬��
			
			if(reponseStatus != 200){//��Ӧ����ȷ
				logger.error("����������ͨѶ���ص�״̬�벻��ȷ,���ص�״̬��:"+reponseStatus);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
			
			HttpEntity resentity = response.getEntity();
			
			//�����յ�����Ϣ
			SAXReader saxR = new SAXReader();
			saxR.setValidation(false);
			Document doc = saxR.read(resentity.getContent());
			sendLog.eSendMsgLog("1", (doc!=null?doc.asXML():""));
			logger.debug("����������,�յ�����Ϣ:" + (doc!=null?doc.asXML():""));
			
			//����ͨ�õ���Ϣͷ
			Node node = null;
			String resCommand = doc.selectSingleNode("/caipiaotv/ctrl/command").getText();
			String resUserid = doc.selectSingleNode("/caipiaotv/ctrl/userid").getText();
			String resKey = doc.selectSingleNode("/caipiaotv/ctrl/key").getText();
			String resErrorCode = (node = doc.selectSingleNode("/caipiaotv/ctrl/errorcode")) == null ? null : node.getText();
			String resErrorMsg = (node = doc.selectSingleNode("/caipiaotv/ctrl/errormsg")) == null ? null : node.getText();
			//������Ϣ��
			String resMessageBody = doc.selectSingleNode("/caipiaotv/list").asXML();
			
			logger.debug("����������,�յ�����Ϣ��:" + resMessageBody);
			
			// ��ϢУ�飬��û����,����յ��ķ�����ϢУ��û��ͨ����δ���
			String contentValue = new ZzyXmlUtlt().decodeKeyValue(resMessageBody);
			logger.debug("����������,�յ�����ϢժҪ����:"+(SYS_DEFINE_KEY + contentValue));
			String receiveDataMd5 = MD5Util.getMD5String(SYS_DEFINE_KEY + contentValue).toUpperCase();
			logger.debug("����������,�յ�����ϢժҪ:" + receiveDataMd5);
			
			if(! receiveDataMd5.equals(resKey)){//����յ�����ϢժҪУ�鲻��ȷ����û�д���
				logger.error("����������,�յ�����ϢժҪУ��δͨ��!");
			}
			
			//�����յ�������
			if(commd_50200.equals(this.command)){//���ڲ�ѯ
				responseData = new TermQueryQs50200(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50201.equals(this.command)){//���ֲʳ�Ʊ
				responseData = new DlcBetQs50201(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50203.equals(this.command)){//���������ѯ
				responseData = new KjggQueryQs50203(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50205.equals(this.command)){//��Ʊ״̬��ѯ
				responseData = new TicketQueryQs50205(resCommand,resUserid,resKey,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50206.equals(this.command)){//��ȡ�н�����
				responseData = new CashQueryQs50206(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50207.equals(this.command)){//�����淨��Ʊ
				responseData = new CommonBetQs50207(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50208.equals(this.command)){//���������ѯ
				responseData = new TermSaleQs50208(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}else if(commd_50209.equals(this.command)){//����״̬��ѯ
				responseData = new TermQueryQs50209(resCommand,resUserid,resKey,resErrorCode,resErrorMsg,resMessageBody);
				responseData.decodeMessageBody();
			}
			
			//���յ�����Ϣ����Ϊ�ַ���
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
			logger.error("�������������쳣", e);
			throw new LotteryException(E_03_CODE,E_03_DESC);
		}finally{
			logger.debug("************end��������������end***************"+requestTime);
		}
		
		return responseData;
	}
	
	/**
	 * 
	 * Title: encodeMessage<br>
	 * Description: <br>
	 *              <br>��װ������������Ϣ��
	 * @return
	 * @throws DocumentException
	 */
	public void encodeMessage() throws Exception {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element caipiaotv = doc.addElement("caipiaotv");
		//��Ϣͷ
		Element ctrl = caipiaotv.addElement("ctrl");
		ctrl.addElement("userid").setText(this.userid);
		ctrl.addElement("command").setText(this.command);
		
		//��Ϣ��
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
	 *              <br>��װ֪ͨ�ķ�����Ϣ
	 * @throws Exception
	 */
	public String encodeNoticeMessage() throws Exception {
		
		this.key = MD5Util.getMD5String(this.keyValue.toString());
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		Element caipiaotv = doc.addElement("caipiaotv");
		//��Ϣͷ
		Element ctrl = caipiaotv.addElement("ctrl");
		ctrl.addElement("userid").setText(StringUtils.isEmpty(this.userid)?"δ֪":this.userid);
		ctrl.addElement("key").setText(StringUtils.isEmpty(this.key)?"δ֪":this.key);
		ctrl.addElement("command").setText(StringUtils.isEmpty(this.command)?"δ֪":this.command);
		ctrl.addElement("errorcode").setText(StringUtils.isEmpty(this.errorcode)?"δ֪":this.errorcode);
		//��Ϣ��
		
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
	 *              <br>�������󲢵õ���Ӧ���
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
		//��������
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
 * (�ӿڽ���������)
 * @author gaoboqin
 * 2011-1-18 ����05:08:32
 *
 */
class ZzyXmlUtlt{
	private StringBuffer innerKeyValue = new StringBuffer();
	
	/**
	 * 
	 * Title: getKeyElementValue<br>
	 * Description: <br>
	 *              <br>������Ϣ�壬ȡ��ȫ���Ľڵ����ݣ�ƴ���ַ���
	 * @param element
	 */
	public void getKeyElementValue(Element element) {
		List elements = element.elements();
		if (elements.size() == 0) {
			// û����Ԫ��
			//String xpath = element.getPath();
			String value = element.getTextTrim();
			innerKeyValue.append(value);
		} else {
			// ����Ԫ��
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				// �ݹ����
				getKeyElementValue(elem);
			}
		}
	}
	/**
	 * 
	 * Title: decodeKeyValue<br>
	 * Description: <br>
	 *              <br>������Ϣ������нڵ��ֵ��ƴ���ַ���
	 * @param messageBody
	 * @return
	 * @throws Exception
	 */
	public String decodeKeyValue(String messageBody) throws Exception{
		try{
			Document document = DocumentHelper.parseText(messageBody);
			//������Ϣ�������
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
