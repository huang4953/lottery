package com.success.lottery.lbapserver;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.success.protocol.lbap.DESPlus;
import com.success.protocol.lbap.MD5Util;
import com.success.protocol.smip.SMIP_DataPack;



public class TestHttp {

	/**
	 * Title: main<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	
	public static String registarReq(){
		String command = "1001";
		String clientId = "wap";
		String messageId = String.valueOf(System.currentTimeMillis());
		String encryptionType = "0";
		
		String messBody = "<mobilePhone>13585692299</mobilePhone><password>111111</password><realName>秦高波测试中文</realName><email>qingaobo@hotmail.com</email>";
		
		
		String md = MD5Util.getMD5String(messBody);
		//String md = MD5Util.getMD5String(new String(clientId.getBytes(), "UTF-8"));
		System.out.println("md==="+md);
		
		StringBuffer rsb = new StringBuffer();
		rsb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><message><head><version>1</version>");
		rsb.append("<command>").append(command).append("</command>");
		//rsb.append("<clientId>").append(new String(clientId.getBytes(), "UTF-8")).append("</clientId>");
		rsb.append("<clientId>").append(clientId).append("</clientId>");
		rsb.append("<messageId>").append(messageId).append("</messageId>");
		rsb.append("<encryptionType>").append(encryptionType).append("</encryptionType>");
		rsb.append("<md>").append(md).append("</md>");
		rsb.append("</head>");
		rsb.append("<body>").append(messBody).append("</body>");
		rsb.append("</message>");
		String requestStr = rsb.toString();
		return requestStr;
	}
	
	public static String modifyUserReq(){
		String command = "1005";
		String clientId = "wap";
		String messageId = String.valueOf(System.currentTimeMillis());
		String encryptionType = "0";
		
		String messBody = "<userId>47</userId><realName>中文修改</realName><email>qingaobo99@yahoo.com.cn</email>";
		
		
		String md = MD5Util.getMD5String(messBody);
		//String md = MD5Util.getMD5String(new String(clientId.getBytes(), "UTF-8"));
		System.out.println("md==="+md);
		
		StringBuffer rsb = new StringBuffer();
		rsb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><message><head><version>1</version>");
		rsb.append("<command>").append(command).append("</command>");
		//rsb.append("<clientId>").append(new String(clientId.getBytes(), "UTF-8")).append("</clientId>");
		rsb.append("<clientId>").append(clientId).append("</clientId>");
		rsb.append("<messageId>").append(messageId).append("</messageId>");
		rsb.append("<encryptionType>").append(encryptionType).append("</encryptionType>");
		rsb.append("<md>").append(md).append("</md>");
		rsb.append("</head>");
		rsb.append("<body>").append(messBody).append("</body>");
		rsb.append("</message>");
		String requestStr = rsb.toString();
		return requestStr;
	}
	
	public static String UserLogin() throws Exception{
		String command = "2001";
		String clientId = "wap";
		String messageId = String.valueOf(System.currentTimeMillis());
		String encryptionType = "0"; 
		
		
		String messBody = "<userIdentify>13761083826</userIdentify><password>111111</password>";
		
		String md = MD5Util.getMD5String(messBody);
		System.out.println("md==="+md);
		
		StringBuffer rsb = new StringBuffer();
		rsb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><message><head><version>1</version>");
		rsb.append("<command>").append(command).append("</command>");
		//rsb.append("<clientId>").append(new String(clientId.getBytes(), "UTF-8")).append("</clientId>");
		rsb.append("<clientId>").append(clientId).append("</clientId>");
		rsb.append("<messageId>").append(messageId).append("</messageId>");
		rsb.append("<encryptionType>").append(encryptionType).append("</encryptionType>");
		rsb.append("<md>").append(md).append("</md>");
		rsb.append("</head>");
		rsb.append("<body>").append(messBody).append("</body>");
		rsb.append("</message>");
		String requestStr = rsb.toString();
		return requestStr;
	}
	
	public static String getUser() throws Exception{
		String command = "2003";
		String clientId = "wap";
		String messageId = String.valueOf(System.currentTimeMillis());
		String encryptionType = "1";
		DESPlus des = new DESPlus("success");//默认密钥   
		String messBody = "<userId>11</userId>";
		
		//messBody = LBAP_Util.sunBase64Encode(des.encrypt(messBody.getBytes()));
		
		String md = MD5Util.getMD5String(messBody);
		//String md = MD5Util.getMD5String(new String(clientId.getBytes(), "UTF-8"));
		System.out.println("md==="+md);
		
		StringBuffer rsb = new StringBuffer();
		rsb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><message><head><version>1</version>");
		rsb.append("<command>").append(command).append("</command>");
		rsb.append("<clientId>").append(clientId).append("</clientId>");
		rsb.append("<messageId>").append(messageId).append("</messageId>");
		rsb.append("<encryptionType>").append(encryptionType).append("</encryptionType>");
		rsb.append("<md>").append(md).append("</md>");
		rsb.append("</head>");
		rsb.append("<body>").append(messBody).append("</body>");
		rsb.append("</message>");
		String requestStr = rsb.toString();
		return requestStr;
	}
	
	public static String getCurrentTerm(){
		String command = "2002";
		String clientId = "wap";
		String messageId = String.valueOf(System.currentTimeMillis());
		String encryptionType = "0";
		
		String messBody = "<lotteryId>1000001</lotteryId>";
		
		String aaa = "中文";
		String md = MD5Util.getMD5String(messBody.getBytes());
		//String md = MD5Util.getMD5String(new String(clientId.getBytes(), "UTF-8"));
		System.out.println("md==="+md);
		
		StringBuffer rsb = new StringBuffer();
		rsb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><message><head><version>1</version>");
		rsb.append("<command>").append(command).append("</command>");
		rsb.append("<clientId>").append(clientId).append("</clientId>");
		rsb.append("<messageId>").append(messageId).append("</messageId>");
		rsb.append("<encryptionType>").append(encryptionType).append("</encryptionType>");
		rsb.append("<md>").append(md).append("</md>");
		rsb.append("</head>");
		rsb.append("<body>").append(messBody).append("</body>");
		rsb.append("</message>");
		String requestStr = rsb.toString();
		return requestStr;
	}
	
	public static String getTermInfo(){
		String command = "2004";
		String clientId = "wap";
		String messageId = String.valueOf(System.currentTimeMillis());
		String encryptionType = "0";
		
		String messBody = "<lotteryId>1300001</lotteryId><termNo>10055</termNo>";
		
		String aaa = "中文";
		String md = MD5Util.getMD5String(messBody);
		//String md = MD5Util.getMD5String(new String(clientId.getBytes(), "UTF-8"));
		System.out.println("md==="+md);
		
		StringBuffer rsb = new StringBuffer();
		rsb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><message><head><version>1</version>");
		rsb.append("<command>").append(command).append("</command>");
		rsb.append("<clientId>").append(clientId).append("</clientId>");
		rsb.append("<messageId>").append(messageId).append("</messageId>");
		rsb.append("<encryptionType>").append(encryptionType).append("</encryptionType>");
		rsb.append("<md>").append(md).append("</md>");
		rsb.append("</head>");
		rsb.append("<body>").append(messBody).append("</body>");
		rsb.append("</message>");
		String requestStr = rsb.toString();
		return requestStr;
	}
	
	public static String clientRecharge() throws Exception{
		String command = "1003";
		String clientId = "wap";
		String messageId = String.valueOf(System.currentTimeMillis());
		
		DESPlus des = new DESPlus("success");//默认密钥 
		String billNo = String.valueOf(System.currentTimeMillis());
		
		
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		
		Element message = doc.addElement("message");
		Element head = message.addElement("head");
		head.addElement("version").setText("1");
		head.addElement("command").setText(command);
		head.addElement("clientId").setText(clientId);
		head.addElement("messageId").setText(messageId);
		head.addElement("encryptionType").setText("1");
		
		Element body = message.addElement("body");
		body.addElement("userId").setText("11");
		body.addElement("billNo").setText("1234567890");
		body.addElement("amount").setText("2000");
		body.addElement("commission").setText("0");
		
		String messBody = body.asXML().replaceAll("<body>", "").replaceAll("</body>", "").replace("<body/>", "");
		
		String messBodyBase = com.success.utils.Base64.encode(des.encrypt(messBody.getBytes()));

		String md = MD5Util.getMD5String(messBodyBase.getBytes());
		//String md = MD5Util.getMD5String(messBody.getBytes());
		System.out.println("####messBody=="+messBody);
		System.out.println("####messBodyBase=="+messBodyBase);
		System.out.println("####md=="+md);
		
		head.addElement("md").setText(md);
		
		message.remove(body);
		message.addElement("body").setText(messBodyBase);
		
		String requestStr = doc.asXML();
		return requestStr;
	}
	
	public static String clientDraw() throws Exception{
		String command = "1004";
		String clientId = "wap";
		String messageId = String.valueOf(System.currentTimeMillis());
		String encryptionType = "1";
		DESPlus des = new DESPlus("success");//默认密钥 
		String billNo = String.valueOf(System.currentTimeMillis());
		String messBody = "<userId>11</userId><billNo>"+billNo+"</billNo><bank>秦高波银行</bank><bankProvince>河南</bankProvince><bankCity>郑州</bankCity><bankName>测试银行</bankName><bankCardId>123456678</bankCardId><cardUserName>秦高波</cardUserName><reason>wap端提现申请</reason><amount>2000</amount><commission>100</commission><reserve>111</reserve>";
		
		messBody = com.success.utils.Base64.encode(des.encrypt(messBody.getBytes()));
		
		String md = MD5Util.getMD5String(messBody);
		
		System.out.println("md==="+md);
		
		StringBuffer rsb = new StringBuffer();
		rsb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><message><head><version>1</version>");
		rsb.append("<command>").append(command).append("</command>");
		rsb.append("<clientId>").append(clientId).append("</clientId>");
		rsb.append("<messageId>").append(messageId).append("</messageId>");
		rsb.append("<encryptionType>").append(encryptionType).append("</encryptionType>");
		rsb.append("<md>").append(md).append("</md>");
		rsb.append("</head>");
		rsb.append("<body>").append(messBody).append("</body>");
		rsb.append("</message>");
		String requestStr = rsb.toString();
		return requestStr;
	}
	
	
	public static String bet(){
		String command = "1002";
		String clientId = "wap";
		String messageId = String.valueOf(System.currentTimeMillis());
		String encryptionType = "0";
		
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("UTF-8");
		
		Element message = doc.addElement("message");
		Element head = message.addElement("head");
		head.addElement("version").setText("1");
		head.addElement("command").setText(command);
		head.addElement("clientId").setText(clientId);
		head.addElement("messageId").setText(messageId);
		head.addElement("encryptionType").setText("0");
		
		Element body = message.addElement("body");
		body.addElement("userId").setText("11");
		body.addElement("sourceType").setText("2");
		body.addElement("lotteryId").setText("1000003");
		body.addElement("playType").setText("0");
		body.addElement("betType").setText("0");
		body.addElement("betMultiple").setText("1");
		body.addElement("currentTerm").setText("10148");
		body.addElement("betCode").setText("1*2*3");
		body.addElement("betNum").setText("1");
		body.addElement("amount").setText("200");
		Element chaseInfo = body.addElement("chaseInfo");
		chaseInfo.addAttribute("winStopped", "1");
		Element chase = chaseInfo.addElement("chase");
		chase.addElement("termNo").setText("10149");
		chase.addElement("multiple").setText("1");
		
		String messBody = body.asXML().replaceAll("<body>", "").replaceAll("</body>", "").replace("<body/>", "");
		System.out.println("####messBody=="+messBody);
		String md = MD5Util.getMD5String(messBody);
		System.out.println("####md=="+md);
		head.addElement("md").setText(md);
		
		String requestStr = doc.asXML();
		return requestStr;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException,Exception {
		String url = "http://127.0.0.1:6666/lbap/";
		//String url = "http://www.google.com.hk/";
		//用户注册
		//String requestStr = registarReq();
		
		
		//测试用户修改
		//String requestStr = modifyUserReq();
		
		//用户登录
		//String requestStr = UserLogin();
		//String requestStr = "";
		
		//获取彩民信息
		//String requestStr = getUser();
		
		//获取当前期信息
		String requestStr = getCurrentTerm();
		
		//获取指定彩期信息
		//String requestStr = getTermInfo();
		
        //投注
		//String requestStr = bet();
		
		 //客户端充值
		//String requestStr = clientRecharge();
		
		 //客户端提现申请
		//String requestStr = clientDraw();
		
		
		try {
			HttpParams params = new BasicHttpParams();
	        //HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, "utf-8");
	        HttpProtocolParams.setUseExpectContinue(params, true);
			HttpClient httpClient = new DefaultHttpClient(params);
		
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity;
			
			entity = new StringEntity(requestStr, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity resentity = response.getEntity();
			
			System.out.println("----------------------------------------01");
	        System.out.println(response.getStatusLine());
	        Header[] headers = response.getAllHeaders();
	        for (int i = 0; i < headers.length; i++) {
	            System.out.println(headers[i]);
	        }
	        System.out.println("----------------------------------------02");
	        StringBuffer sb = new StringBuffer(); 
	        if (resentity != null) {
				System.out.println("content length === "
						+ resentity.getContentLength());
				System.out.println("content getContentType === "
						+ resentity.getContentType());
				System.out.println("content getContentEncoding === "
						+ resentity.getContentEncoding());
				System.out.println("响应消息:");
				
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(resentity.getContent(), "gbk"));
//				String line;
//				while ((line = reader.readLine()) != null) {
//					sb.append(line);
//				}
				byte[] aaa = new byte[10240];
				resentity.getContent().read(aaa);
				//System.out.println(new String(aaa));
				System.out.println(new String(aaa, "utf-8"));
				
				/*
				OutputStream  os = new FileOutputStream("e:\\result.xml");
				os.write(aaa);
				os.flush();
				os.close();
				*/
				//System.out.println(new String(aaa, "gbk"));
				//SMIP_DataPack.dumpPack(aaa, 0, aaa.length);
				//reader.close();
				//System.out.println(sb.toString());
				// System.out.println(EntityUtils.toString(resentity));
			}
			
			httpClient.getConnectionManager().shutdown(); 
			
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		


	}

}
