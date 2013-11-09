/**
 * Title: LbapHttpHandler.java
 * @Package com.success.lottery.lbapserver
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-6-30 ����11:29:11
 * @version V1.0
 */
package com.success.lottery.lbapserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.protocol.lbap.LBAP_DataPack;
import com.success.protocol.lbap.LBAP_Error;
import com.success.protocol.lbap.LBAP_GetUserInfo;
import com.success.protocol.lbap.LBAP_Modify_User;
import com.success.protocol.lbap.LBAP_Recharge;
import com.success.protocol.lbap.LBAP_Register;
import com.success.protocol.lbap.LBAP_bet;
import com.success.protocol.lbap.LBAP_draw;
import com.success.protocol.lbap.LBAP_getCurrentTerm;
import com.success.protocol.lbap.LBAP_getMsg;
import com.success.protocol.lbap.LBAP_getMsgResp;
import com.success.protocol.lbap.LBAP_getTermInfo;
import com.success.protocol.lbap.LBAP_userLogin;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * com.success.lottery.lbapserver
 * LbapHttpHandler.java
 * LbapHttpHandler
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-6-30 ����11:29:11
 * 
 */

public class LbapHttpHandler implements HttpHandler {
	private static Log logger = LogFactory.getLog(LbapHttpHandler.class);
	
	private static LbapLog log = LbapLog.getInstance("LBAP");
	
	public static String [] logCommand = {"1001","1002","1003","1004","1005"};

	/**
	 *Title: 
	 *Description: 
	 */
	public LbapHttpHandler() {
		// TODO �Զ����ɹ��캯�����
	}
	
	public void handle(HttpExchange httpExchange) throws IOException {
		System.out.println("��ʼ������Ϣ:"+System.currentTimeMillis());
		/*
		 * ��ӡhttpͷ��Ϣ
		 */
		showMessHead(httpExchange);
		
		InputStream in = httpExchange.getRequestBody(); //���������
		LBAP_DataPack dataPack = LBAP_DataPack.readPack(in);
		in.close();
		
		String command = "";
		String clientId = "";
		String messageId = "";
		
		if(dataPack != null){
			command = dataPack.getCommand();
			clientId = dataPack.getClientId();
			messageId = dataPack.getMessageId();
		}
		byte[] responseMess = null;//��Ӧ��Ϣ��
		
		/*
		 * ��������Ϣ���࣬�Բ�ͬ��������ò�ͬ��ҵ����
		 */
		LBAP_DataPack retResult = null;
		try {
			
			if(dataPack instanceof LBAP_Error){//������Ϣ����
				responseMess = dataPack.encode();
				
			}else if(dataPack instanceof LBAP_Register){//�û�ע��
				retResult = new LbapBussinessServer().registerUser(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_Modify_User){//�޸��û���Ϣ
				retResult = new LbapBussinessServer().modifyUser(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_userLogin){//�û���¼
				retResult = new LbapBussinessServer().UserLogin(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_GetUserInfo){//��ȡ�û���Ϣ
				retResult = new LbapBussinessServer().getUsrInfo(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_getCurrentTerm){//��ȡ��ǰ�ڲ�����Ϣ
				retResult = new LbapBussinessServer().getCurrentTerm(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_getTermInfo){//��ȡ��ǰ�ڲ�����Ϣ
				retResult = new LbapBussinessServer().getTermInfo(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_bet){//Ͷע
				retResult = new LbapBussinessServer().lotteryBet(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_Recharge){//��ֵ
				retResult = new LbapBussinessServer().recharge(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_draw){//����
				retResult = new LbapBussinessServer().drawMoney(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_getMsg){//��ѯ��Ϣ
				retResult = new LbapBussinessServer().getLbapMsg(dataPack);
				responseMess = retResult.encode();
			}
			/*
			 * ������־
			 */
			log.logInfo(command, clientId,messageId, dataPack.getResult(),dataPack.getMessageBody());//�յ�����Ϣ��־
			//��������־
			if(retResult instanceof LBAP_Error){//����������
				log.logInfo(command, clientId,messageId, retResult.getResult(),retResult.getMessageBody());
			}else if(retResult != null){
				log.logInfo(command, clientId,messageId, retResult.getResult(),retResult.getMessageBody());
			}
			
		} catch (Exception e) {
			logger.error("��Ϣ�������:", e);
			LBAP_DataPack error = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
			try {
				responseMess = error.encode();
			} catch (Exception e1) {
				responseMess = "".getBytes();
				logger.error("��Ϣ�������:", e);
			}
		}finally{
			try{
				if(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(command) || StringUtils.isEmpty(command)){
					logger.error("�յ���Ϣ�Ĳ���Ϊ�գ����ܶ���Ϣ��¼");
				}else{
					if(retResult != null){//��Ϣ��������
						if(Arrays.asList(logCommand).contains(command)){
							new LbapBussinessServer().writeLbapMsg(clientId, command, messageId, retResult.getResult(), retResult.getMessageBody(), "", String.valueOf(retResult
									.getEncryptionType()));
						}
						
					}else{//��Ϣ������
						new LbapBussinessServer().writeLbapMsg(clientId, command, messageId, dataPack.getResult(), dataPack
								.getMessageBody(), "", String.valueOf(dataPack
								.getEncryptionType()));
					}
				}
				
			}catch(Exception e){
				logger.error("��Ϣ�������:", e);
			}
		}
		
		//������Ӧͷ��Ϣ
		Headers responseHeaders = httpExchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "text/xml;charset=UTF-8");
		//responseHeaders.set("Content-Encoding", "gzip");//�������ã����ú���������ܽ⿪
		//responseHeaders.set("Transfer-Encoding", "chunked");//
		responseHeaders.set("Content-Language ", "zh-cn");//
		
		System.out.println("��Ӧ��Ϣ�峤��:"+responseMess.length);
		httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,responseMess.length);//������Ӧͷ���Լ���Ӧ��Ϣ�ĳ��� 
		
		OutputStream out = httpExchange.getResponseBody(); //�������� 
		
//		System.out.println(responseMess.length);
//		System.out.println(new String(responseMess, "gbk"));
//		System.out.println(new String(responseMess, "utf-8"));
		//SMIP_DataPack.dumpPack(responseMess, 0, responseMess.length);

		out.write(responseMess);
		out.flush();
		out.close();
		httpExchange.close();
	}
	
	
	
	/**
	 * 
	 * Title: showMessHead<br>
	 * Description: <br>
	 *              <br>��ӡ��Ϣͷ
	 * @param httpExchange
	 */
	private void showMessHead(HttpExchange httpExchange){
		String requestMethod = httpExchange.getRequestMethod();
		System.out.println("requestMethod===="+requestMethod);
		
		Headers requestHeaders = httpExchange.getRequestHeaders();
		Set<String> keySet = requestHeaders.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			List values = requestHeaders.get(key);
			String s = key + " = " + values.toString() + "\n";
			System.out.println(s);
		}
	}
	/**
	 * 
	 * Title: showMessBody<br>
	 * Description: <br>
	 *              <br>��ӡ��Ϣ��
	 * @param in
	 */
	private void showMessBody(InputStream in){
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String temp = null;
		
		try {
			while ((temp = reader.readLine()) != null) {
				logger.debug("�ͻ���������Ϣ �� "+temp);
			}
		} catch (IOException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}
	
	private void test(){
		
		/*
		OutputFormat format = OutputFormat.createPrettyPrint();   
        format.setEncoding("UTF-8"); 
		
		XMLWriter xmlWriter = new XMLWriter(out, format);
	        
	    xmlWriter.write(responseMess.getBytes());
	    out.flush();
	    xmlWriter.close();
	    */
		//logger.debug("responseMess====="+responseMess);
	}

}
