package com.success.protocol.lbap;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.success.lottery.lbapserver.LbapBussinessServer;

public abstract class LBAP_DataPack{
	
	private static String DEFAULT_KEY = "success";
	
	protected static final String RESULT_0000 = "0000";//�ɹ�
	protected static final String RESULT_0001 = "0001";//��ϢУ�����
	protected static final String RESULT_0002 = "0002";//��Ϣ�ṹ��
	protected static final String RESULT_0003 = "0003";//��Ϣ������
	protected static final String RESULT_0004 = "0004";
	protected static final String RESULT_0998 = "0998";//����δ�ҵ�
	protected static final String RESULT_0999 = "0999";//�ò���ʧ��
	protected static final String RESULT_1001 = "1001";
	protected static final String RESULT_1002 = "1002";
	protected static final String RESULT_1003 = "1003";
	protected static final String RESULT_2001 = "2001";
	protected static final String RESULT_2002 = "2002";
	protected static final String RESULT_2003 = "2003";
	
	/*
	 * ϵͳ���Խ��ܵ�������
	 */
	protected static final String [] COMMAND = {"1001","1002","1003","1004","1005","2001","2002","2003","2004","9999"};

	/**
	 * ��Ϣ���ͣ�0-������Ϣ 1-��Ӧ��Ϣ
	 */
	protected int messageType;

	protected String	version;
	protected String	command;
	protected String	clientId;
	protected String	messageId;
	protected int	encryptionType;
	protected String	md;
	protected String result;
	protected String	messageBody;

	static{
		DEFAULT_KEY = StringUtils.isEmpty(LbapMess.getConfig("desKey")) ? "success" : LbapMess.getConfig("desKey");
	}
	
	/**
	 *Title: 
	 *Description: 
	 */
	public LBAP_DataPack() {
		super();
		// TODO �Զ����ɹ��캯�����
	}
	
	/**
	 *Title: 
	 *Description: 
	 * @param version
	 * @param command
	 * @param clientId
	 * @param messageId
	 * @param encyptionType
	 * @param md
	 * @param messageBody
	 */
	protected LBAP_DataPack(String version,String command,String clientId,String messageId,int encyptionType,String md,String messageBody) {
		super();
		this.version = version;
		this.command = command;
		this.clientId = clientId;
		this.messageId = messageId;
		this.encryptionType = encyptionType;
		this.md = md;
		this.messageBody = messageBody;
	}
	
	

	/**
	 *Title: 
	 *Description: 
	 * @param messageId
	 * @param encyptionType
	 */
	protected LBAP_DataPack(String messageId, int encyptionType) {
		super();
		this.messageId = messageId;
		this.encryptionType = encyptionType;
	}

	public abstract void decodeMessageBody() throws Exception;
	public abstract void encodeMessageBody() throws Exception;

	/**
	 * encode��Ϣ���ݣ���Ҫ����encyptionType���м��ܲ���
	 * �÷���ֻ����������Ӧ��Ϣ
	 * @return String
	 * @throws UnsupportedEncodingException 
	 * @throws UnsupportedEncodingException 
	 */
	public byte[] encode() throws UnsupportedEncodingException,Exception{
		
       // ��������������Ϣ��
		try {
			encodeMessageBody();
		} catch (Exception e) {//�鷵�ش�ʧ�ܸ÷���ʲô����ʱ�����Ѿ��ɹ�
			e.printStackTrace();
			this.setMessageBody("");
		}
		StringBuffer sb = new StringBuffer();
		String bodyMess = this.getMessageBody();

		if(this.getEncryptionType() == 1){//������Ϣ��
			DESPlus des = new DESPlus(DEFAULT_KEY);//Ĭ����Կ
			bodyMess = com.success.utils.Base64.encode(des.encrypt(bodyMess.getBytes()));
		}
		String bodyMD5 = MD5Util.getMD5String(bodyMess.getBytes("utf-8"));// �Լ��ܺ����Ϣ��md5

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append(
				"<message>");
		sb.append("<head>");
		sb.append("<messageId>").append(this.getMessageId()==null ? "" : this.getMessageId()).append(
				"</messageId>");
		sb.append("<result>").append(this.getResult()).append("</result>");
		sb.append("<encryptionType>").append(String.valueOf(this.getEncryptionType())).append(
				"</encryptionType>");
		sb.append("<md>").append(bodyMD5).append("</md>");
		sb.append("</head>");
		sb.append("<body>").append(bodyMess).append("</body>");
		sb.append("</message>");
		return sb.toString().getBytes("utf-8");
	}

	
	public void writePack(OutputStream os) throws IOException{
		//os.write(encode().getBytes());
		os.flush();
	}
	
	/**
	 * 
	 * Title: validDataPack<br>
	 * Description: <br>
	 *              <br>У����Ϣ����ȷ��
	 * @param version
	 * @param command
	 * @param clientId
	 * @param messageId
	 * @param encyptionType
	 * @param md
	 * @param messageBody
	 * @return String
	 */
	public static String validDataPack(String version,String command,String clientId,String messageId,String encyptionType,String md,byte[] messageBody){
		String result = RESULT_0000;
		
		if(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(messageId) || StringUtils.isEmpty(md) || StringUtils.isEmpty(command) || StringUtils.isEmpty(encyptionType)){
			result = RESULT_0001;
		}
		if(!(Arrays.asList(COMMAND).contains(command.trim()))){
			result = RESULT_0001;
		}
		if(!(encyptionType.equals("1"))){//Ҫ��ͻ��˷��͵����ݱ������
			result = RESULT_0001;
		}
		
		if(messageBody == null || messageBody.length == 0){
			result = RESULT_0001;
		}
		
		String messageBodyMd5 = MD5Util.getMD5String(messageBody);
		
		System.out.println("��Ϣ��md5��==="+messageBodyMd5);
		
		System.out.println("���յ���У����="+md);
		
		if(!md.trim().equals(messageBodyMd5)){
			System.out.println("md5У�����");
			result = RESULT_0001;
		}
		return result;
	}

	/**
	 * ����Ӧ��Ϣ
	 * @param in
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	
	public static LBAP_DataPack readPack(InputStream in, String cmd) throws IOException{
		LBAP_DataPack dp = null;
		return dp;
	}
	/**
	 * 
	 * Title: readPack<br>
	 * Description: <br>
	 *              <br>
	 * @param in
	 * @return
	 */
	public static LBAP_DataPack readPack(InputStream in){
		LBAP_DataPack dp = null;
		String messageId = null;
		String cmd = null;
		String version = null;
		String md = null;
		String clientId = null;
		String encyptionTypeStr = null;
		InputStream inputStream = null;
		String messageBody = null;
		String errorCode = "";
		try{
			/*
			 * ���յ�����Ϣת��Ϊ�ֽ�����
			 */
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100]; //buff���ڴ��ѭ����ȡ����ʱ����
			int rc = 0;
			while ((rc = in.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			byte[] msgByte = swapStream.toByteArray(); //�������ܵ�����Ϣ
			swapStream.close();//ת����ɹر������
			
			inputStream = new ByteArrayInputStream(msgByte);//�����µ��������ṩ��dom4j��ȡ
			
			byte [] originalBody = getMsgBody(msgByte);//ԭʼ����Ϣ��
			
			System.out.println("��Ϣ���ȣ�"+msgByte.length);
			System.out.println("�ֽ���ʽ��ԭʼ��Ϣutf-8:"+new String(msgByte,"utf-8"));
			
			/*
			 *  dom4j������Ϣ
			 */
			try {
				SAXReader saxR = new SAXReader();
				saxR.setValidation(false);
				Document doc = saxR.read(inputStream);
				Element eMessage = doc.getRootElement();
				Element eHead = eMessage.element("head");
				cmd = eHead.elementText("command");
				version = eHead.elementText("version");
				clientId = eHead.elementText("clientId");
				messageId = eHead.elementText("messageId");
				md = eHead.elementText("md");
				encyptionTypeStr = eHead.elementText("encryptionType");
				messageBody = eMessage.element("body").asXML().replaceAll("<body>", "").replaceAll("</body>", "");
			} catch (Exception e) {
				e.printStackTrace();
				return new LBAP_Error(RESULT_0002,cmd,clientId,messageId);
			}
			
			/*
			 * У����Ϣ
			 */
			String valid = validDataPack(version,cmd,clientId,messageId,encyptionTypeStr,md,originalBody);
			
			int encyptionType = Integer.parseInt(encyptionTypeStr);
			//У�����ֱ�ӷ���
			if(! valid.equals(RESULT_0000)){
				dp = new LBAP_Error(valid,cmd,clientId,messageId);
				return dp;
			}
			
			
			//����Ϣ����н��ܲ���
			 
			String DESmessageBody = messageBody;
			if(encyptionType == 1){
				DESPlus des = new DESPlus(DEFAULT_KEY);//Ĭ����Կ
				DESmessageBody = new String(des.decrypt(com.success.utils.Base64.decode(messageBody)));
			}
			
			System.out.println("���ܺ����Ϣ��==="+DESmessageBody);
			
			if("1001".equals(cmd.trim())){
				dp = new LBAP_Register(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else if("1002".equals(cmd.trim())){
				dp = new LBAP_bet(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else if("1003".equals(cmd.trim())){
				dp = new LBAP_Recharge(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else if("1004".equals(cmd.trim())){
				dp = new LBAP_draw(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else if("1005".equals(cmd.trim())){
				dp = new LBAP_Modify_User(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else if("2001".equals(cmd.trim())){
				dp = new LBAP_userLogin(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else if("2002".equals(cmd.trim())){
				dp = new LBAP_getCurrentTerm(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else if("2003".equals(cmd.trim())){
				dp = new LBAP_GetUserInfo(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else if("2004".equals(cmd.trim())){
				dp = new LBAP_getTermInfo(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else if("9999".equals(cmd.trim())){
				dp = new LBAP_getMsg(version,cmd,clientId,messageId,encyptionType,md,DESmessageBody);
			}else{
				throw new IllegalStateException("DataPack command is invalid:" + cmd);
			}
			//�������������Ϣ��
			dp.decodeMessageBody();
			
		}catch(Exception ex){
			ex.printStackTrace();
			dp = new LBAP_Error(RESULT_0003,cmd,clientId,messageId);
			return dp;
		}finally{
			try{
				if(inputStream != null){
					inputStream.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		return dp;
	}
	
	public static void main(String[] args){
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getVersion(){
		return version;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getCommand(){
		return command;
	}

	public void setCommand(String command){
		this.command = command;
	}

	public String getClientId(){
		return clientId;
	}

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public String getMessageId(){
		return messageId;
	}

	public void setMessageId(String messageId){
		this.messageId = messageId;
	}

	public String getMd(){
		return md;
	}

	public void setMd(String md){
		this.md = md;
	}

	public String getMessageBody(){
		return messageBody;
	}

	public void setMessageBody(String messageBody){
		this.messageBody = messageBody;
	}

	public int getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(int encryptionType) {
		this.encryptionType = encryptionType;
	}
	
	public static void resolveMsg(InputStream bis2){
//		try{
//			if(bis2 != null){
//				int abyte;
//				while((abyte = bis2.read()) != -1){
//					System.out.print(abyte);
//				}
//			}
//			
//		}catch(Exception  e){
//			e.printStackTrace();
//		}
		try{
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					bis2, "gbk"));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			System.out.println("ԭʼ��Ϣ:"+sb.toString());
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		 
	}
	/**
	 * 
	 * Title: getMsgBody<br>
	 * Description: <br>
	 *              <br>��ȡbody�м������
	 * @param msg
	 * @return
	 */
	private static byte[] getMsgBody(byte[] msg){
		if(msg == null){
			return null;
		}
		byte [] body = {60,98,111,100,121,62};
		byte [] endBody = {60,47,98,111,100,121,62};
		int pos1 = -1;
		int pos2 = -1;
		for(int i = 0 ; i < msg.length ; i++){
			if(pos1 != -1 && pos2 != -1){
				break;
			}
			if(pos1 == -1){
				int count = 0;
				for(int j = 0 ; j< body.length ; j++){
					if(i + j < msg.length && body[j] == msg[i + j]){
						count++;
					}
					if(count == body.length){
						pos1 = i + body.length;
					}
				}
			}
			
			if(pos2 == -1){
				int count = 0;
				for(int j = 0 ; j< endBody.length ; j++){
					if(i + j < msg.length && endBody[j] == msg[i + j]){
						count++;
					}
					if(count == endBody.length){
						pos2 = i - 1;
					}
				}
			}
			
		}
		if(pos1 != -1 && pos2 != -1 && pos2 > pos1){
			int dataLength = pos2 - pos1 + 1;
			byte [] result = new byte[dataLength];
			System.arraycopy(msg, pos1, result, 0, dataLength);
			return result;
			//return null;
		}else{
			return null;
		}
	}
}
