package com.success.utils.ssmp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.success.utils.LibingUtils;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;
import com.success.utils.ssmp.net.SocketClient;
import com.success.utils.ssmp.net.SocketServer;
public class SSMPLoader{

	private static String						loadFileName	= "./ssmpLoad.conf";
	private static Map<String, ThreadSpectacle>	threads			= new Hashtable<String, ThreadSpectacle>();
	private static Log							logger			= LogFactory.getLog(SSMPLoader.class.getName());
	
	public static String getLoadFileName(){
		return loadFileName;
	}

	public static void setLoadFileName(String loadFileName){
		SSMPLoader.loadFileName = loadFileName;
	}

	public static void load() throws IOException{
		FileReader fr = new FileReader(loadFileName);
		BufferedReader br = new BufferedReader(fr);
		String line;

		while((line = br.readLine()) != null){
			logger.info("SSMPLoader read line:" + line);
			if(line.startsWith("#") || line.trim().equals("")){
				continue;
			}
			if(line.startsWith("log4j.configuration")){
				String fileName = line.substring(20);
				PropertyConfigurator.configure(fileName);
				logger.info("configure log4j conf:" + fileName);
			}
			if(line.startsWith("server")){
				try{
					startServer(line);
				}catch(SSMPException e){
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
				}
			}
			if(line.startsWith("client")){
				try{
					startClient(line);
				}catch(SSMPException e){
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
				}
			}
			if(line.startsWith("thread")){
				try{
					startThread(line);
				}catch(SSMPException e){
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static String startLine(String line){
		String rs = "start line is success!";
		if(StringUtils.isBlank(line)){
			return "ERROR: start command is blank";
		}
		if(line.startsWith("server")){
			try{
				startServer(line);
			}catch(SSMPException e){
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				rs = "ERROR: " + e.toString();
			}
		}
		if(line.startsWith("client")){
			try{
				startClient(line);
			}catch(SSMPException e){
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				rs = "ERROR: " + e.toString();
			}
		}
		if(line.startsWith("thread")){
			try{
				startThread(line);
			}catch(SSMPException e){
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				rs = "ERROR: " + e.toString();
			}
		}
		return rs;
	}
	
	private static void startThread(String line) throws SSMPException{
		String strs[] = LibingUtils.split(line, ":");
		if(strs.length != 5){
			logger.error("start thread parameter error, thread command line:" + line);
			throw new SSMPException("start thread parameter error, thread command line:" + line);
		}
		if(threads.containsKey(strs[1]) || StringUtils.isBlank(strs[1])){
			logger.error("start thread error: thread name is duplication or blank. name:" + strs[1]);
			throw new SSMPException("start thread error: thread name is duplication or blank. name:" + strs[1]);
		}
		if(StringUtils.isBlank(strs[2])){
			logger.error("start thread error: thread class is blank. thread command line:" + line);
			throw new SSMPException("start thread error: thread class is blank. thread command line:" + line);
		}
		ThreadSpectacle thread = null;
		try{
			thread = (ThreadSpectacle)Class.forName(strs[2]).newInstance();
		}catch(Exception e){
			logger.info("use default classloader start thread " + strs[1] + " failed, thread class:" + strs[2] + ", exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		if(thread == null){
			try{
				thread = (ThreadSpectacle)Class.forName(strs[2].trim(), true, Thread.currentThread().getContextClassLoader()).newInstance();
			}catch(Exception e){
				logger.info("use contextclassloader start thread " + strs[1] + " failed, thread class:" + strs[2] + ", exception:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}
		}
		if(thread == null){
			throw new SSMPException("start thread " + strs[1] + " failed, thread class:" + strs[2]);
		}
		thread.setName(strs[1].trim());
		thread.setParameter(strs[3]);
		(new Thread(thread, strs[1])).start();
		threads.put(strs[1].trim(), thread);
		logger.info("start thread " + strs[1] + " successed, thread class:" + strs[2]);
		
	}

	private static void startClient(String line) throws SSMPException{
		String strs[] = LibingUtils.split(line, ":");
		if(strs.length != 8){
			logger.error("start client parameter error, client command line:" + line);
			throw new SSMPException("start client parameter error, client command line:" + line);
		}
		if(threads.containsKey(strs[1]) || StringUtils.isBlank(strs[1])){
			logger.error("start client error: client name is duplication or blank. name:" + strs[1]);
			throw new SSMPException("start client error: client name is duplication or blank. name:" + strs[1]);
		}
		if(StringUtils.isBlank(strs[2]) || StringUtils.isBlank(strs[3]) || StringUtils.isBlank(strs[4]) || StringUtils.isBlank(strs[5])){
			logger.error("start client error: client parameter error. client command line:" + line);
			throw new SSMPException("start client error: client parameter error. client command line:" + line);
		}
		int port;
		try{
			port = Integer.parseInt(strs[4]);
		}catch(Exception e){
			logger.error("start client connect port error, port: " + strs[4]);
			throw new SSMPException("start client connect port error, port: " + strs[4]);
		}
		try{
			SocketClient client = (SocketClient)Class.forName(strs[2]).newInstance();
			client.setName(strs[1].trim());
			client.setParameter(strs[6]);
			client.open(strs[3], port, strs[5]);
			client.listen();
			threads.put(strs[1].trim(), client);
			logger.info("start client " + strs[1] + " successed, client class:" + strs[2] + ", ip:" + strs[3] + ", port:" + port);
		}catch(Exception e){
			logger.info("start client " + strs[1] + " failed, client class:" + strs[2] + ", exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new SSMPException("start client " + strs[1] + " failed, client class:" + strs[2] + ", exception:" + e.toString());
		}
	}

	private synchronized static void startServer(String line)throws SSMPException{
		String strs[] = LibingUtils.split(line, ":");
		if(strs.length != 8){
			logger.error("start server parameter error, server command line:" + line);
			throw new SSMPException("start server parameter error, server command line:" + line);
		}
		if(threads.containsKey(strs[1]) || StringUtils.isBlank(strs[1])){
			logger.error("start server error: server name is duplication or blank. name:" + strs[1]);
			throw new SSMPException("start server error: server name is duplication or blank. name:" + strs[1]);
		}
		int port;
		if(StringUtils.isBlank(strs[4]) || StringUtils.isBlank(strs[2])){
			logger.error("start server error: not set listen port or consumer, port: " + strs[4] + ", consumer:" + strs[2]);
			throw new SSMPException("start server error: not set listen port or consumer, port: " + strs[4] + ", consumer:" + strs[2]);
		}else{
			try{
				port = Integer.parseInt(strs[4]);
			}catch(Exception e){
				logger.error("start server error: can not set listen port, port: " + strs[4]);
				throw new SSMPException("start server error: can not set listen port, port: " + strs[4]);
			}
		}
		int backlog;
		try{
			backlog = Integer.parseInt(strs[5]);
		}catch(Exception e){
			backlog = 50;
		}
		try{
			SocketServer ss = new SocketServer(strs[3], port, backlog, strs[2]);
			ss.setName(strs[1].trim());
			ss.setParameter(strs[6]);
			new Thread(ss, "S-" + port).start();
			threads.put(strs[1].trim(), ss);
			logger.info("start SocketServer " + strs[1] + " successed, consumer class:" + strs[2]);
		}catch(Exception e){
			logger.error("start SocketServer " + strs[1] + " failed, exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new SSMPException("start SocketServer " + strs[1] + " failed, exception:" + e.toString());
		}
	}

	private synchronized static void removeThread(String threadName){
		threads.remove(threadName);
	}

	public static ThreadSpectacle getThreadSpectacle(String name){
		return threads.get(name);
	}

	public static String showAll(){
		checkAliveThread();
		StringBuffer sb = new StringBuffer();
		for(ThreadSpectacle thread : threads.values()){
			sb.append(thread.showInfo() + "\n");
		}
		return sb.toString();
	}

	protected static void stopAll(){
		Iterator<Entry<String, ThreadSpectacle>> iterator = threads.entrySet().iterator();
		while(iterator.hasNext()){
			ThreadSpectacle thread = iterator.next().getValue();
			if(thread != null && thread.isAlive()){
				try{
					thread.stop();
				}catch(SSMPException e){
					logger.error("SSMP stopAll thread[" + thread.getName() + "] occur to exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
				}
			}
		}
		while(!threads.isEmpty()){
			logger.debug("wait for threads shutdown, threads:" + threads.toString());
			checkAliveThread();
			try{
				Thread.sleep(30000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void stopAllForConsole(PrintWriter pw){
		
		Iterator<Entry<String, ThreadSpectacle>> iterator = threads.entrySet().iterator();
		while(iterator.hasNext()){
			ThreadSpectacle thread = iterator.next().getValue();
			if(thread != null && thread.isAlive()){
				String threadName = thread.getName();
				pw.println("stop " + threadName + " , threadName Detailed information :");
				pw.println(thread.showDetail());
				pw.flush();
				pw.print("please wait");
				try{
					thread.stop();
					while(thread.isAlive()){
						pw.print(".");
						pw.flush();
						try{
							Thread.sleep(1000);
						}catch(InterruptedException e){
						}
					}
					pw.println("");
					pw.println("thread " + threadName + " is stopped. ");
					pw.flush();
					iterator.remove();
				}catch(SSMPException e){
					pw.println("");
					pw.println(e.toString());
					pw.flush();
					logger.error("stop thread exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
				}
			}
		}
		checkAliveThread();
		pw.println("all threads is stopped! ");
		pw.flush();
	}

	public static void checkAliveThread(){
		Iterator<Entry<String, ThreadSpectacle>> iterator = threads.entrySet().iterator();
		while(iterator.hasNext()){
			ThreadSpectacle thread = iterator.next().getValue();
			if(thread == null || !thread.isAlive()){
				iterator.remove();
			}
		}
	}

	public static void main(String[] args) throws IOException{
		SSMPLoader.load();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while((line = br.readLine()) != null){
			if("exit".equals(line.trim())){
				br.close();
				System.out.println("SSMPLoader exit...");
				break;
			}else{
				System.out.println("input is:" + line);
			}
		}
	}
}
