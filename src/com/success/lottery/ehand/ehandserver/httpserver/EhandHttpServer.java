/**
 * Title: EhandHttpServer.java
 * @Package com.success.lottery.ehandserver
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-18 上午11:13:23
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

/**
 * com.success.lottery.ehandserver
 * EhandHttpServer.java
 * EhandHttpServer
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-18 上午11:13:23
 * 
 */

public class EhandHttpServer {
	
	private static EhandHttpServer m_instance = null; 

	private static  HttpServer httpserver = null;
	
	private static int threadMinCount = 1;//最小线程数   
	private static int threadMaxCount = 3;//最大线程数   
	private static int checkPeriod = 1;//当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param port
	 * @param reqCount
	 * @throws IOException
	 */
	private EhandHttpServer(int port,int reqCount) throws IOException{
		HttpServerProvider provider = HttpServerProvider.provider();
		InetSocketAddress addr = new InetSocketAddress(port);
		httpserver = provider.createHttpServer(addr, reqCount < 1 ? 100:reqCount);//InetSocketAddress
	}
	
	/**
	 * 
	 * Title: start<br>
	 * Description: <br>
	 *              <br>启动
	 */
	public synchronized void start(){
		if(httpserver != null){
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
			//httpserver.createContext("/"+path+"/", handler);
			httpserver.createContext("/"+path, handler);
		}
	}
	/**
	 * 
	 * Title: getInstance<br>
	 * Description: <br>
	 *              <br>获取实例
	 * @param port
	 * @param reqCount
	 * @return
	 * @throws IOException
	 */
	public synchronized static EhandHttpServer getInstance(int port, int reqCount) throws IOException{
		if (m_instance == null) {
			try {
				m_instance = new EhandHttpServer(port, reqCount);
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return m_instance;
	}
}
