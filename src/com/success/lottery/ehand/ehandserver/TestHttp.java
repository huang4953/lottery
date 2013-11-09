package com.success.lottery.ehand.ehandserver;

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
	
	public static String betNotices(){
		StringBuffer rsb = new StringBuffer();
		rsb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><message>");
		rsb.append("<ctrl>");
		rsb.append("<userid>").append("2009072700000000000000168133").append("</userid>");
		rsb.append("<key>").append("9F0DF5625EAA5F2086F625CCB171FA19").append("</key>");
		rsb.append("<command>").append("50204").append("</command>");
		rsb.append("</ctrl>");
		rsb.append("<ilist>");
		rsb.append("<ielement>");
		rsb.append("<AGENTOPERATEID>").append("2d010010111").append("</AGENTOPERATEID>");
		rsb.append("<TICKETID>").append("SCH20100202130911006887862").append("</TICKETID>");
		rsb.append("<PRINTSTATUS>").append("1").append("</PRINTSTATUS>");
		rsb.append("</ielement>");
		rsb.append("</ilist>");
		rsb.append("</message>");
		String requestStr = rsb.toString();
		return requestStr;
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException,Exception {
		String url = "http://127.0.0.1:7777/";
		//String url = "http://www.google.com.hk/";
		
		String requestStr = betNotices();
		
		
		
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
