package com.success.utils.ssmp.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
/**
 * thread name: S-port name: SSMPLoader 配置的SocketServer名称
 * 
 * @author bing.li
 * 
 */
public class SocketServer implements SocketConsumer{

	private int						port;
	private String					ip;
	private int						backlog			= 50;
	private String					name;
	// private SocketConsumer consumer;
	/**
	 * SocketConsumer Name: SocketConsumer的类全名，含包路径
	 */
	private String					scName;
	private boolean					isStop			= false;
	private ServerSocket			serverSocket;
	private Log						logger			= LogFactory.getLog(SocketServer.class.getName());
	private List<SocketConsumer>	consumers		= new ArrayList<SocketConsumer>();
	private String					confFileName	= "com.success.utils.ssmp.monitor.resource";

	private SocketServer(){
	}

	public SocketServer(int port){
		this.port = port;
	}

	public SocketServer(String ip, int port){
		this.ip = ip;
		this.port = port;
	}

	public SocketServer(String ip, int port, int backlog){
		this.ip = ip;
		this.port = port;
		this.backlog = backlog;
	}

	public SocketServer(String ip, int port, int backlog, String scName){
		this.ip = ip;
		this.port = port;
		this.backlog = backlog;
		this.scName = scName;
	}

	@Override
	public void consume(Socket socket){
		try{
			socket.close();
		}catch(IOException e){
		}
	}

	private void init(){
		logger = LogFactory.getLog(SocketServer.class.getName() + "." + port);
		Thread.currentThread().setName(this.name);
	}

	private void close(){
		isStop = true;
		try{
			serverSocket.close();
		}catch(IOException e){
		}
	}

	private void checkConsumers(){
		Iterator<SocketConsumer> iterator = consumers.iterator();
		while(iterator.hasNext()){
			SocketConsumer sc = iterator.next();
			if(sc == null){
				iterator.remove();
			}else{
				if(!sc.isAlive()){
					iterator.remove();
				}
			}
		}
	}

	@Override
	public void run(){
		init();
		try{
			if(StringUtils.isBlank(ip)){
				serverSocket = new ServerSocket(port, backlog);
			}else{
				serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(ip));
			}
		}catch(IOException e){
			logger.error("start SocketServer-" + name + " Failed, create ServerSocket exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			close();
			return;
		}
		Socket socket = null;
		while(!isStop){
			try{
				try{
					serverSocket.setSoTimeout(AutoProperties.getInt(name + ".timeout", 300000, confFileName));
				}catch(SocketException e){
					logger.warn("set SocketServer-" + name + " timeout Exception:" + e.toString());
				}
				socket = serverSocket.accept();
				SocketConsumer consumer = null;
				try{
					consumer = (SocketConsumer)Class.forName(scName).newInstance();
				}catch(Exception e){
					logger.error("use default classloader create SocketConsumer-" + scName + " for SocketServer-" + name + " exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}					
				}
				if(consumer == null){
					try{
						consumer = (SocketConsumer)Class.forName(scName, true, Thread.currentThread().getContextClassLoader()).newInstance();
					}catch(Exception e){
						logger.error("use contextclassloader create SocketConsumer-" + scName + " for SocketServer-" + name + " exception:" + e.toString());
						if(logger.isDebugEnabled()){
							e.printStackTrace();
						}					
					}
				}
				if(consumer == null){
					logger.error("create SocketConsumer error!");
				} else {
					consumers.add(consumer);
					consumer.consume(socket);
					logger.info("SocketConsumer " + consumer.getName() + " for " + this.name + " is running...");
				}
			}catch(SocketTimeoutException e1){
				try{
					logger.debug("SocketServer-" + name + " accept() " + serverSocket.getSoTimeout() + " timeout exception: " + e1.toString());
				}catch(IOException e){
					logger.warn("logger debug timeout info occur exception when getSoTimeout: " + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
				}
				checkConsumers();
			}catch(IOException e){
				logger.error("SocketServer-" + name + " IOException:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}
		}
		close();
		logger.info("SocketServer " + this.name + " is shutdown!");
	}

	@Override
	public String showDetail(){
		StringBuffer sb = new StringBuffer();
		sb.append(showInfo() + "\n");
		sb.append("\t").append(this.name + "'s Parameter:").append("\n");
		sb.append("\t\t");
		try{
			sb.append("timeout=" + this.serverSocket.getSoTimeout()).append("\n");
		}catch(IOException e){
			sb.append("timeout=" + e.toString()).append("\n");
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		sb.append("\t\t");
		sb.append("isStop=" + this.isStop).append(" , ").append("isAlive=").append(this.isAlive()).append("\n");
		sb.append("\n");
		sb.append("\t").append(this.name + "'s Consumer list:").append("\n");
		Iterator<SocketConsumer> iterator = consumers.iterator();
		while(iterator.hasNext()){
			SocketConsumer sc = iterator.next();
			if(sc != null && sc.isAlive()){
				sb.append("\t\t").append(sc.showInfo()).append("\n");
			}else{
				iterator.remove();
			}
		}
		return sb.toString();
	}

	/**
	 * 返回SocketServer简要信息 S - 名称 - 监听端口 - 允许的连接数 - 已经链接数量
	 */
	@Override
	public String showInfo(){
		checkConsumers();
		return "S - " + this.name + " - " + port + " - " + this.scName + " - [" + backlog + " / " + consumers.size() + "] - " + isAlive();
	}

	@Override
	public void stop(){
		this.isStop = true;
	}

	@Override
	public boolean isAlive(){
		if(serverSocket == null){
			return false;
		}
		if(serverSocket.isClosed()){
			return false;
		}
		if(!serverSocket.isBound()){
			return false;
		}
		return true;
	}

	/************************************************************/
	// 以下为控制台进入此对象管理时的方法
	private void print(String str, PrintWriter pw){
		pw.print(str);
		pw.flush();
	}

	private void println(String str, PrintWriter pw){
		pw.println(str);
		pw.flush();
	}

	private void stopConsumer(String input, PrintWriter pw){
		String consumerName = input.substring(4);
		if(StringUtils.isBlank(consumerName)){
			println("ERROR: consumer name is blank", pw);
		}else{
			consumerName = consumerName.trim();
			SocketConsumer consumer = null;
			for(SocketConsumer sc : consumers){
				if(consumerName.equals(sc.getName())){
					consumer = sc;
					break;
				}
			}
			if(consumer == null){
				println("ERROR: " + consumerName + " not found!", pw);
			}else{
				println("stop " + consumerName + " ,consumer Detailed information :", pw);
				println(consumer.showDetail(), pw);
				print("please wait", pw);
				try{
					consumer.stop();
					while(consumer.isAlive()){
						print(".", pw);
						try{
							Thread.sleep(500);
						}catch(InterruptedException e){
						}
					}
					println("", pw);
					consumers.remove(consumer);
					println("consumer " + consumerName + " is stopped. ", pw);
				}catch(SSMPException e){
					println("\n" + e.toString(), pw);
				}
			}
		}
	}

	/**
	 * SSMP@name> [help|?] 显示调用此方法返回的信息
	 * 
	 */
	public void help(PrintWriter pw){
		println("SocketServer usage !", pw);
	}

	private void setTimeout(int timeout) throws SocketException{
		serverSocket.setSoTimeout(timeout);
	}

	/**
	 * SSMP@name> timeout=value 调用此方法设置timeout的值
	 * 
	 * @param timeout
	 * @throws SocketException
	 */
	public String setSoTimeout(String command){
		String str[] = command.split("=");
		if(str.length != 2){
			return "set timeout command parameter error!";
		}else{
			try{
				setTimeout(Integer.parseInt(str[1]));
			}catch(Exception e){
				return "set timeout Exception: " + e.toString();
			}
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public void spectacle(String command, PrintWriter pw){
		if("help".equalsIgnoreCase(command.trim()) || "?".equalsIgnoreCase(command.trim())){
			help(pw);
		}else if(command.startsWith("timeout")){
			// timeout=6000
			String rs;
			if((rs = setSoTimeout(command)) != null){
				println(rs, pw);
			}else{
				println("set " + name + " timeout ok !", pw);
			}
		}else if(command.startsWith("stop")){
			// stop consumer name;
			stopConsumer(command, pw);
		}else{
		}
	}

	@Override
	public void setName(String name){
		this.name = name;
	}

	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public void setParameter(String parameter){
		return;
	}
}
