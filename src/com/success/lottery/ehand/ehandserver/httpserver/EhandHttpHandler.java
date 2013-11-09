/**
 * Title: EhandHttpHandler.java
 * @Package com.success.lottery.ehandserver
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-18 ����12:00:27
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.httpserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.ehand.ehandserver.bussiness.EhandLog;
import com.success.lottery.ehand.ehandserver.bussiness.EhandNoticeService;
import com.success.lottery.ehand.ehandserver.bussiness.EhandTicketService;
import com.success.protocol.ticket.zzy.EhandDataPack;
import com.success.protocol.ticket.zzy.EhandErrorQs;
import com.success.protocol.ticket.zzy.KjggNoticeQs;
import com.success.protocol.ticket.zzy.TermEndNoticeQs;
import com.success.protocol.ticket.zzy.TermNewNoticeQs;
import com.success.protocol.ticket.zzy.TicketNoticeQs50204;
import com.success.protocol.ticket.zzy.util.EhandUtil;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * com.success.lottery.ehandserver
 * EhandHttpHandler.java
 * EhandHttpHandler
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-18 ����12:00:27
 * 
 */

public class EhandHttpHandler implements HttpHandler {
	private static Log logger = LogFactory.getLog(EhandHttpHandler.class);
	
	private static EhandLog log = EhandLog.getInstance("EHAND");
	private static EhandLog noticeLog = EhandLog.getInstance("E_NOTICEMSG");//��¼�յ��������ȵ���Ϣ,xml��ʽ
	
	public EhandHttpHandler(){
	}

	/* (�� Javadoc)
	 *Title: handle
	 *Description: 
	 * @param arg0
	 * @throws IOException
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	public void handle(HttpExchange httpExchange) throws IOException {
		logger.debug("************�յ�������֪ͨ����************");
		
		InputStream inData = null;//����Ҫ������������
		byte[] responseMess = "".getBytes();//��Ӧ��Ϣ��
		
		//��������·������װ����������
		try {
			inData = routRequest(httpExchange);
		} catch (Exception e) {
			logger.error("������֪ͨ����ת���������ݳ���:",e);
			e.printStackTrace();
		}
		
		//���յ������ݸ���Э�����������ش���Ľ��
		if(inData != null){
			responseMess = dealDataPack(inData);
		}else{
			logger.debug("������֪ͨ����δ��������������!");
		}
		
		noticeLog.eNoticeMsgLog("1", new String(responseMess,"GBK"));
		
		//����������Ӧ
		try {
			Headers responseHeaders = httpExchange.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/xml;charset=UTF-8");
			responseHeaders.set("Content-Language ", "zh-cn");//
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,responseMess.length);//������Ӧͷ���Լ���Ӧ��Ϣ�ĳ��� 
			OutputStream out = httpExchange.getResponseBody(); //�������� 
			out.write(responseMess);
			out.flush();
			out.close();
			httpExchange.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("������֪ͨ�����յ�֪ͨ�󷵻���Ϣ����:"+e);
		}
		
		logger.debug("************������֪ͨ���������************");
	}
	/**
	 * 
	 * Title: dealDataPack<br>
	 * Description: <br>
	 *              <br>�����յ�����Ϣ����ҵ�������ش���Ľ��������Ľ��������Ӧhttp����
	 * @param inData
	 * @return
	 */
	private byte[] dealDataPack(InputStream inData){
		String command = "",userId = "",key = "",errCode = "",errMsg = "";
		String reqErrOrOk = "0";//�յ�����Ϣ�Ƿ���ȷ��Ĭ��Ϊ��ȷ
		String resErrOrOk = "0";//���ص���Ϣ�Ƿ���ȷ��Ĭ��Ϊ��ȷ
		
		byte[] responseMess = null;//��������Ӧ��Ϣ
		EhandDataPack retResult = null;//
		
		try{
			//����Э������յ��� ��Ϣ
			EhandDataPack dataPack = EhandDataPack.readPack(inData);
			inData.close();
			
			if(dataPack != null){//��Ϣ�ɹ�����
				command = dataPack.getCommand();
				userId = dataPack.getUserid();
				key = dataPack.getKey();
				errCode = dataPack.getErrorcode();
				errMsg = dataPack.getErrormsg();
			}
			
			//��������Ϣ���࣬�Բ�ͬ��������ò�ͬ��ҵ����
			if(dataPack instanceof EhandErrorQs){
				reqErrOrOk = "1";
				if(EhandUtil.commd_90001.equals(command) || EhandUtil.commd_90002.equals(command) || EhandUtil.commd_90003.equals(command)){
					responseMess = errCode.getBytes();
				}else{
					responseMess = dataPack.encodeNoticeMessage().getBytes();
				}
				logger.debug("������֪ͨ���س�����Ӧ��������Ϣ:" + new String(responseMess,"GBK"));
			}else if(dataPack instanceof TicketNoticeQs50204){//��Ʊ״̬֪ͨ
				retResult = EhandTicketService.notice50204(dataPack);
				responseMess = retResult.encodeNoticeMessage().getBytes();
				logger.debug("������֪ͨ����[��Ʊ״̬֪ͨ]ҵ�������,����״̬��:"+retResult.getErrorcode());
			}else if(dataPack instanceof KjggNoticeQs){//��������֪ͨ
				retResult = EhandNoticeService.noticeKjgg(dataPack);
				responseMess = retResult.encodeNoticeMessage().getBytes();
				logger.debug("������֪ͨ����[��������֪ͨ]ҵ�������,����״̬��:"+retResult.getErrorcode());
			}else if(dataPack instanceof TermNewNoticeQs){//����֪ͨ
				retResult = EhandNoticeService.noticeNewTerm(dataPack);
				responseMess = retResult.encodeNoticeMessage().getBytes();
				logger.debug("������֪ͨ����[����֪ͨ]ҵ�������,����״̬��:"+retResult.getErrorcode());
			}else if(dataPack instanceof TermEndNoticeQs){//�ڽ�֪ͨ
				retResult = EhandNoticeService.noticeEndTerm(dataPack);
				responseMess = retResult.encodeNoticeMessage().getBytes();
				logger.debug("������֪ͨ����[�ڽ�֪ͨ]ҵ�������,����״̬��:"+retResult.getErrorcode());
			}else{
				logger.debug("������֪ͨ����,δ֪��Э��");
			}
			
			/**
			 * ��¼����������
			 */
			
			log.logInfo("0",reqErrOrOk,command, userId, key, errCode, errMsg, dataPack.getMessageBody());//�յ�����Ϣ��־
			//��������־
			if(retResult instanceof EhandErrorQs){//����������
				//��û�������
				logger.debug("����������,��û�������");
			}else if(retResult != null){
				log.logInfo("1","0",command, userId, key, retResult.getErrorcode(), errMsg, retResult.getMessageBody());//���ص���Ϣ��־
			}
			
		}catch(Exception e){
			logger.error("������֪ͨ������Ϣ�������:", e);
		}finally{
			//�������ݿ����־��¼,��û����
		}
		return responseMess;
	}
	
	/**
	 * 
	 * Title: routRequest<br>
	 * Description: <br>
	 *              <br>��������·���ж����������
	 * @param httpExchange
	 * @return
	 * @throws Exception
	 */
	private InputStream routRequest(HttpExchange httpExchange) throws Exception{
		
		URI requestedUri = httpExchange.getRequestURI();//����URL
		String requestPath = requestedUri.getPath();//�����·��
		
		logger.debug("������֪ͨ����·��:" + requestPath);
		
		//���������·���жϽ��յ���������
		if(EhandUtil.PATH_01.equals(requestPath)){//����xml�������ķ�ʽ��������
			logger.debug("������֪ͨ�������������ķ�ʽ����");
			return httpExchange.getRequestBody();
		}else {//���������ķ�ʽ
			logger.debug("������֪ͨ��������������ķ�ʽ����");
			Map<String, Object> requestParams = new HashMap<String, Object>();//����������
			String queryData = requestedUri.getRawQuery();
			logger.debug("������֪ͨ����GET��ʽ���������:" + queryData);
			 
			parseQuery(queryData, requestParams);//�����������
		    logger.debug("������֪ͨ����GET��ʽ�������������Ϊ:" + requestParams);
			
			if(EhandUtil.PATH_02.equals(requestPath)){//��������֪ͨ
				byte[] data = ConvertRequestData.convertLotteryResultNotice(requestParams);
				logger.debug("������֪ͨ����[��������]����ת���ɹ�");
				return new ByteArrayInputStream(data);
			}else if(EhandUtil.PATH_03.equals(requestPath)){//����֪ͨ
				byte[] data = ConvertRequestData.convertNewTermNotice(requestParams);
				logger.debug("������֪ͨ����[����֪ͨ]����ת���ɹ�");
				return new ByteArrayInputStream(data);
			}else if(EhandUtil.PATH_04.equals(requestPath)){//�ڽ�֪ͨ
				byte[] data = ConvertRequestData.convertEndTermNotice(requestParams);
				logger.debug("������֪ͨ����[�ڽ�֪ͨ]����ת���ɹ�");
				return new ByteArrayInputStream(data);
			}else{
				logger.debug("������֪ͨ����GET��ʽϵͳδ���������·��");
				return httpExchange.getRequestBody();
			}
		}
	}
	/**
	 * 
	 * Title: parseQuery<br>
	 * Description: <br>
	 *              <br>����get��ʽ���������
	 * @param query
	 * @param parameters
	 * @throws UnsupportedEncodingException
	 */
	private void parseQuery(String query, Map<String, Object> parameters)
			throws UnsupportedEncodingException {
		if (query != null) {
			String pairs[] = query.split("[&]");

			for (String pair : pairs) {
				String param[] = pair.split("[=]");

				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
				}

				if (param.length > 1) {
					value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
				}

				if (parameters.containsKey(key)) {
					Object obj = parameters.get(key);
					if (obj instanceof List<?>) {
						List<String> values = (List<String>) obj;
						values.add(value);
					} else if (obj instanceof String) {
						List<String> values = new ArrayList<String>();
						values.add((String) obj);
						values.add(value);
						parameters.put(key, values);
					}
				} else {
					parameters.put(key, value);
				}
			}
		}
	}


}
