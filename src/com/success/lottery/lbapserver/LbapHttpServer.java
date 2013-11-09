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
	
	private static int threadMinCount = 1;//最小线程数   
	private static int threadMaxCount = 3;//最大线程数   
	private static int checkPeriod = 1;//当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param port 端口
	 * @param reqCount 最大请求数
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
	 *              <br>启动
	 */
	public synchronized void start(){
		if(httpserver != null){
			/*
			ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadMinCount, threadMaxCount, checkPeriod,
					TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(50),new ThreadPoolExecutor.CallerRunsPolicy());
			httpserver.setExecutor(threadPool);//设置线程池
			*/
            httpserver.setExecutor(null);//设置线程池
			httpserver.start();
		}
	}
	/**
	 * 
	 * Title: stop<br>
	 * Description: <br>
	 *              <br>停止
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
	 *              <br>设置服务
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
	 * @param port 端口号
	 * @param reqCount 最大请求数
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
	
	
	//以下为测试代码
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
			System.out.println("y[start]/n[stop]：");
			String str = "";
			try {
				BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
				str = buf.readLine();
				if (str.equalsIgnoreCase("y")) {
					System.out.println("启动服务");
					server.createContext("testApp", new MyHttpHandler());
					server.start();
				} else if (str.equalsIgnoreCase("n")) {
					System.out.println("正在停止服务");
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
			
			
			InputStream in = httpExchange.getRequestBody(); //获得输入流    
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String temp = null;
			
			while ((temp = reader.readLine()) != null) {
				System.out.println("客户段请求信息:" + temp);
			}
			
			//响应信息
			String responseMsg = "测试一下响应信息"; //响应信息 
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,responseMsg.getBytes().length);//设置响应头属性及响应信息的长度  
			OutputStream out = httpExchange.getResponseBody(); //获得输出流    
			out.write(responseMsg.getBytes());
			out.flush();
			httpExchange.close();
		}
	}  
	
}
