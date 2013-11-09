package com.success.lottery.lbapserver;

import java.io.BufferedReader;    
import java.io.IOException;    
import java.io.InputStream;    
import java.io.InputStreamReader;    
import java.io.OutputStream;    
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;    
import com.sun.net.httpserver.HttpHandler;    
import com.sun.net.httpserver.HttpServer;    
import com.sun.net.httpserver.spi.HttpServerProvider;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LbapHttpServer {
	private static LbapHttpServer m_instance = null; 

	private static  HttpServer httpserver = null;
	
	private static int threadMinCount = 1;//��С�߳���   
	private static int threadMaxCount = 3;//����߳���   
	private static int checkPeriod = 1;//���߳������ں���ʱ����Ϊ��ֹǰ����Ŀ����̵߳ȴ���������ʱ��
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param port �˿�
	 * @param reqCount ���������
	 * @throws IOException
	 */
	private LbapHttpServer(int port,int reqCount) throws IOException{
		HttpServerProvider provider = HttpServerProvider.provider();
		System.out.println("init **********=="+port);
		InetSocketAddress addr = new InetSocketAddress(port);
		System.out.println("init **********       =="+port);
		httpserver = provider.createHttpServer(addr, reqCount < 1 ? 100:reqCount);//InetSocketAddress
		System.out.println("init end");
	}
	/**
	 * 
	 * Title: start<br>
	 * Description: <br>
	 *              <br>����
	 */
	public synchronized void start(){
		if(httpserver != null){
			/*
			ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadMinCount, threadMaxCount, checkPeriod,
					TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(50),new ThreadPoolExecutor.CallerRunsPolicy());
			httpserver.setExecutor(threadPool);//�����̳߳�
			*/
            httpserver.setExecutor(null);//�����̳߳�
			httpserver.start();
		}
	}
	/**
	 * 
	 * Title: stop<br>
	 * Description: <br>
	 *              <br>ֹͣ
	 */
	public synchronized void stop(){
		if(httpserver != null){
			httpserver.stop(10);
		}
	}
	/**
	 * 
	 * Title: createContext<br>
	 * Description: <br>
	 *              <br>���÷���
	 * @param path
	 * @param handler
	 */
	public void createContext(String path, HttpHandler handler){
		if(httpserver != null){
			httpserver.createContext("/"+path+"/", handler);
		}
	}
	/**
	 * 
	 * Title: getInstance<br>
	 * Description: <br>
	 *              <br>
	 * @param port �˿ں�
	 * @param reqCount ���������
	 * @return LbapHttpServer
	 */
	public synchronized static LbapHttpServer getInstance(int port, int reqCount) throws IOException{
		if (m_instance == null) {
			try {
				System.out.println("LbapHttpServer init");
				m_instance = new LbapHttpServer(port, reqCount);
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return m_instance;
	}
	
	
	//����Ϊ���Դ���
	/**
	 * 
	 * Title: main<br>
	 * Description: <br>
	 *              <br>
	 * @param args
	 * @throws IOException
	 */
	 public static void main(String[] args) throws IOException {
		LbapHttpServer server = LbapHttpServer.getInstance(7777, 20);
		while (true) {
			System.out.println("y[start]/n[stop]��");
			String str = "";
			try {
				BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
				str = buf.readLine();
				if (str.equalsIgnoreCase("y")) {
					System.out.println("��������");
					server.createContext("testApp", new MyHttpHandler());
					server.start();
				} else if (str.equalsIgnoreCase("n")) {
					System.out.println("����ֹͣ����");
					server.stop();
					System.out.println("is stopd");
					System.exit(-1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static class MyHttpHandler implements HttpHandler {
		
		public void handle(HttpExchange httpExchange) throws IOException {
			
			
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
			
			
			InputStream in = httpExchange.getRequestBody(); //���������    
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String temp = null;
			
			while ((temp = reader.readLine()) != null) {
				System.out.println("�ͻ���������Ϣ:" + temp);
			}
			
			//��Ӧ��Ϣ
			String responseMsg = "����һ����Ӧ��Ϣ"; //��Ӧ��Ϣ 
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,responseMsg.getBytes().length);//������Ӧͷ���Լ���Ӧ��Ϣ�ĳ���  
			OutputStream out = httpExchange.getResponseBody(); //��������    
			out.write(responseMsg.getBytes());
			out.flush();
			httpExchange.close();
		}
	}  
	
}
