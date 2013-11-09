/**
 * Title: LbapHttpHandler.java
 * @Package com.success.lottery.lbapserver
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-6-30 上午11:29:11
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
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-6-30 上午11:29:11
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
		// TODO 自动生成构造函数存根
	}
	
	public void handle(HttpExchange httpExchange) throws IOException {
		System.out.println("开始接收消息:"+System.currentTimeMillis());
		/*
		 * 打印http头信息
		 */
		showMessHead(httpExchange);
		
		InputStream in = httpExchange.getRequestBody(); //获得输入流
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
		byte[] responseMess = null;//响应消息串
		
		/*
		 * 对请求信息分类，对不同的请求调用不同的业务处理
		 */
		LBAP_DataPack retResult = null;
		try {
			
			if(dataPack instanceof LBAP_Error){//请求消息出错
				responseMess = dataPack.encode();
				
			}else if(dataPack instanceof LBAP_Register){//用户注册
				retResult = new LbapBussinessServer().registerUser(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_Modify_User){//修改用户信息
				retResult = new LbapBussinessServer().modifyUser(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_userLogin){//用户登录
				retResult = new LbapBussinessServer().UserLogin(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_GetUserInfo){//获取用户信息
				retResult = new LbapBussinessServer().getUsrInfo(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_getCurrentTerm){//获取当前期彩期信息
				retResult = new LbapBussinessServer().getCurrentTerm(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_getTermInfo){//获取当前期彩期信息
				retResult = new LbapBussinessServer().getTermInfo(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_bet){//投注
				retResult = new LbapBussinessServer().lotteryBet(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_Recharge){//充值
				retResult = new LbapBussinessServer().recharge(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_draw){//提现
				retResult = new LbapBussinessServer().drawMoney(dataPack);
				responseMess = retResult.encode();
			}else if(dataPack instanceof LBAP_getMsg){//查询消息
				retResult = new LbapBussinessServer().getLbapMsg(dataPack);
				responseMess = retResult.encode();
			}
			/*
			 * 处理日志
			 */
			log.logInfo(command, clientId,messageId, dataPack.getResult(),dataPack.getMessageBody());//收到的消息日志
			//处理结果日志
			if(retResult instanceof LBAP_Error){//处理结果出错
				log.logInfo(command, clientId,messageId, retResult.getResult(),retResult.getMessageBody());
			}else if(retResult != null){
				log.logInfo(command, clientId,messageId, retResult.getResult(),retResult.getMessageBody());
			}
			
		} catch (Exception e) {
			logger.error("消息处理出错:", e);
			LBAP_DataPack error = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//错误响应消息
			try {
				responseMess = error.encode();
			} catch (Exception e1) {
				responseMess = "".getBytes();
				logger.error("消息处理出错:", e);
			}
		}finally{
			try{
				if(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(command) || StringUtils.isEmpty(command)){
					logger.error("收到消息的参数为空，不能对消息记录");
				}else{
					if(retResult != null){//消息处理正常
						if(Arrays.asList(logCommand).contains(command)){
							new LbapBussinessServer().writeLbapMsg(clientId, command, messageId, retResult.getResult(), retResult.getMessageBody(), "", String.valueOf(retResult
									.getEncryptionType()));
						}
						
					}else{//消息出错了
						new LbapBussinessServer().writeLbapMsg(clientId, command, messageId, dataPack.getResult(), dataPack
								.getMessageBody(), "", String.valueOf(dataPack
								.getEncryptionType()));
					}
				}
				
			}catch(Exception e){
				logger.error("消息处理出错:", e);
			}
		}
		
		//设置响应头信息
		Headers responseHeaders = httpExchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "text/xml;charset=UTF-8");
		//responseHeaders.set("Content-Encoding", "gzip");//不能设置，设置后浏览器不能解开
		//responseHeaders.set("Transfer-Encoding", "chunked");//
		responseHeaders.set("Content-Language ", "zh-cn");//
		
		System.out.println("响应消息体长度:"+responseMess.length);
		httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,responseMess.length);//设置响应头属性及响应信息的长度 
		
		OutputStream out = httpExchange.getResponseBody(); //获得输出流 
		
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
	 *              <br>打印消息头
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
	 *              <br>打印消息体
	 * @param in
	 */
	private void showMessBody(InputStream in){
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String temp = null;
		
		try {
			while ((temp = reader.readLine()) != null) {
				logger.debug("客户端请求信息 ： "+temp);
			}
		} catch (IOException e) {
			// TODO 自动生成 catch 块
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
