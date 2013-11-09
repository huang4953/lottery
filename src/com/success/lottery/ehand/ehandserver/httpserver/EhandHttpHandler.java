/**
 * Title: EhandHttpHandler.java
 * @Package com.success.lottery.ehandserver
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-18 下午12:00:27
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
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-18 下午12:00:27
 * 
 */

public class EhandHttpHandler implements HttpHandler {
	private static Log logger = LogFactory.getLog(EhandHttpHandler.class);
	
	private static EhandLog log = EhandLog.getInstance("EHAND");
	private static EhandLog noticeLog = EhandLog.getInstance("E_NOTICEMSG");//记录收到的掌中奕的消息,xml格式
	
	public EhandHttpHandler(){
	}

	/* (非 Javadoc)
	 *Title: handle
	 *Description: 
	 * @param arg0
	 * @throws IOException
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	public void handle(HttpExchange httpExchange) throws IOException {
		logger.debug("************收到掌中奕通知请求************");
		
		InputStream inData = null;//最终要解析的数据流
		byte[] responseMess = "".getBytes();//响应消息串
		
		//根据请求路径重新装配请求数据
		try {
			inData = routRequest(httpExchange);
		} catch (Exception e) {
			logger.error("掌中奕通知请求转换请求数据出错:",e);
			e.printStackTrace();
		}
		
		//对收到的数据根据协议做处理并返回处理的结果
		if(inData != null){
			responseMess = dealDataPack(inData);
		}else{
			logger.debug("掌中奕通知请求未解析到请求数据!");
		}
		
		noticeLog.eNoticeMsgLog("1", new String(responseMess,"GBK"));
		
		//对请求做相应
		try {
			Headers responseHeaders = httpExchange.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/xml;charset=UTF-8");
			responseHeaders.set("Content-Language ", "zh-cn");//
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,responseMess.length);//设置响应头属性及响应信息的长度 
			OutputStream out = httpExchange.getResponseBody(); //获得输出流 
			out.write(responseMess);
			out.flush();
			out.close();
			httpExchange.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("掌中奕通知请求收到通知后返回消息出错:"+e);
		}
		
		logger.debug("************掌中奕通知请求处理结束************");
	}
	/**
	 * 
	 * Title: dealDataPack<br>
	 * Description: <br>
	 *              <br>解析收到的消息，做业务处理并返回处理的结果，处理的结果用来响应http请求
	 * @param inData
	 * @return
	 */
	private byte[] dealDataPack(InputStream inData){
		String command = "",userId = "",key = "",errCode = "",errMsg = "";
		String reqErrOrOk = "0";//收到的消息是否正确，默认为正确
		String resErrOrOk = "0";//返回的消息是否正确，默认为正确
		
		byte[] responseMess = null;//处理后的响应信息
		EhandDataPack retResult = null;//
		
		try{
			//调用协议解析收到的 消息
			EhandDataPack dataPack = EhandDataPack.readPack(inData);
			inData.close();
			
			if(dataPack != null){//消息成功解析
				command = dataPack.getCommand();
				userId = dataPack.getUserid();
				key = dataPack.getKey();
				errCode = dataPack.getErrorcode();
				errMsg = dataPack.getErrormsg();
			}
			
			//对请求信息分类，对不同的请求调用不同的业务处理
			if(dataPack instanceof EhandErrorQs){
				reqErrOrOk = "1";
				if(EhandUtil.commd_90001.equals(command) || EhandUtil.commd_90002.equals(command) || EhandUtil.commd_90003.equals(command)){
					responseMess = errCode.getBytes();
				}else{
					responseMess = dataPack.encodeNoticeMessage().getBytes();
				}
				logger.debug("掌中奕通知返回出错响应，错误消息:" + new String(responseMess,"GBK"));
			}else if(dataPack instanceof TicketNoticeQs50204){//出票状态通知
				retResult = EhandTicketService.notice50204(dataPack);
				responseMess = retResult.encodeNoticeMessage().getBytes();
				logger.debug("掌中奕通知请求[出票状态通知]业务处理完成,返回状态码:"+retResult.getErrorcode());
			}else if(dataPack instanceof KjggNoticeQs){//开奖公告通知
				retResult = EhandNoticeService.noticeKjgg(dataPack);
				responseMess = retResult.encodeNoticeMessage().getBytes();
				logger.debug("掌中奕通知请求[开奖公告通知]业务处理完成,返回状态码:"+retResult.getErrorcode());
			}else if(dataPack instanceof TermNewNoticeQs){//新期通知
				retResult = EhandNoticeService.noticeNewTerm(dataPack);
				responseMess = retResult.encodeNoticeMessage().getBytes();
				logger.debug("掌中奕通知请求[新期通知]业务处理完成,返回状态码:"+retResult.getErrorcode());
			}else if(dataPack instanceof TermEndNoticeQs){//期结通知
				retResult = EhandNoticeService.noticeEndTerm(dataPack);
				responseMess = retResult.encodeNoticeMessage().getBytes();
				logger.debug("掌中奕通知请求[期结通知]业务处理完成,返回状态码:"+retResult.getErrorcode());
			}else{
				logger.debug("掌中奕通知请求,未知的协议");
			}
			
			/**
			 * 记录交互的数据
			 */
			
			log.logInfo("0",reqErrOrOk,command, userId, key, errCode, errMsg, dataPack.getMessageBody());//收到的消息日志
			//处理结果日志
			if(retResult instanceof EhandErrorQs){//处理结果出错
				//还没有想清楚
				logger.debug("处理结果出错,还没有想清楚");
			}else if(retResult != null){
				log.logInfo("1","0",command, userId, key, retResult.getErrorcode(), errMsg, retResult.getMessageBody());//返回的消息日志
			}
			
		}catch(Exception e){
			logger.error("掌中奕通知请求消息处理出错:", e);
		}finally{
			//处理数据库的日志记录,还没有做
		}
		return responseMess;
	}
	
	/**
	 * 
	 * Title: routRequest<br>
	 * Description: <br>
	 *              <br>根据请求路径判断请求的种类
	 * @param httpExchange
	 * @return
	 * @throws Exception
	 */
	private InputStream routRequest(HttpExchange httpExchange) throws Exception{
		
		URI requestedUri = httpExchange.getRequestURI();//请求URL
		String requestPath = requestedUri.getPath();//请求的路径
		
		logger.debug("掌中奕通知请求路径:" + requestPath);
		
		//根据请求的路径判断接收的数据类型
		if(EhandUtil.PATH_01.equals(requestPath)){//按照xml数据流的方式传送数据
			logger.debug("掌中奕通知请求按照数据流的方式解析");
			return httpExchange.getRequestBody();
		}else {//非数据流的方式
			logger.debug("掌中奕通知请求按照请求参数的方式解析");
			Map<String, Object> requestParams = new HashMap<String, Object>();//存放请求参数
			String queryData = requestedUri.getRawQuery();
			logger.debug("掌中奕通知请求GET方式的请求参数:" + queryData);
			 
			parseQuery(queryData, requestParams);//解析请求参数
		    logger.debug("掌中奕通知请求GET方式的请求参数解析为:" + requestParams);
			
			if(EhandUtil.PATH_02.equals(requestPath)){//开奖公告通知
				byte[] data = ConvertRequestData.convertLotteryResultNotice(requestParams);
				logger.debug("掌中奕通知请求[开奖公告]数据转换成功");
				return new ByteArrayInputStream(data);
			}else if(EhandUtil.PATH_03.equals(requestPath)){//新期通知
				byte[] data = ConvertRequestData.convertNewTermNotice(requestParams);
				logger.debug("掌中奕通知请求[新期通知]数据转换成功");
				return new ByteArrayInputStream(data);
			}else if(EhandUtil.PATH_04.equals(requestPath)){//期结通知
				byte[] data = ConvertRequestData.convertEndTermNotice(requestParams);
				logger.debug("掌中奕通知请求[期结通知]数据转换成功");
				return new ByteArrayInputStream(data);
			}else{
				logger.debug("掌中奕通知请求GET方式系统未定义的请求路径");
				return httpExchange.getRequestBody();
			}
		}
	}
	/**
	 * 
	 * Title: parseQuery<br>
	 * Description: <br>
	 *              <br>解析get方式的请求参数
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
