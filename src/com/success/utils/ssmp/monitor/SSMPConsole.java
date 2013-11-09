package com.success.utils.ssmp.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.success.utils.AutoProperties;
import com.success.utils.LibingUtils;
import com.success.utils.ssmp.SSMPLoader;
import com.success.utils.ssmp.SSMPQueue;
import com.success.utils.ssmp.net.SocketConsumer;


public class SSMPConsole implements SocketConsumer, Runnable{

	private String confFileName = "com.success.utils.ssmp.monitor.resource";
	private Socket socket;
	
	/**
	 * SocketConsumer Name
	 * class.name-ip-port
	 */
	private String name;
	private Log logger;
	private String ip;
	private int port;
	private boolean isStop = false;

	BufferedReader br = null;
	PrintWriter pw = null;
	private String currentControlName;
	private ThreadSpectacle controlThread;
	private SSMPQueue controlQueue;
	private String username;
	
	public SSMPConsole(){
	}
	
	public SSMPConsole(Socket socket){
		this.socket = socket;
	}

	@Override
	public void consume(Socket socket){
		this.socket = socket;
		ip = socket.getInetAddress().getHostAddress();
		port = socket.getPort();
		this.name = "SSMPConsole-" + ip + "-" + port;
		logger = LogFactory.getLog(SSMPConsole.class.getName() + "." + ip + "." + port);
		new Thread(this, name).start();
	}

	private void close(){
		try {
			if (br != null)
				br.close();
		} catch (Exception e) {
		}
		try {
			if (pw != null)
				pw.close();
		} catch (Exception e) {
		}
		try {
			if (socket != null)
				socket.close();
		} catch (Exception x) {
		}
		logger.info("SSMPConsole " + this.getName() + " is shutdown!");
	}
	
	private boolean login(){
		//login
		if(AutoProperties.getInt("console." + ip, 0, confFileName) == 0){
			println(ip + " can not login!");
			close();
			return false;
		}else{
			for(int i = 0; i < 3; i++){
				print("Please input login name: ");
				try{
					String username = br.readLine();
					if(username != null){
						String password = AutoProperties.getString("console." + username, "", confFileName);
						print("Password: ");
						String passwd = br.readLine();
						if(StringUtils.isBlank(username) || StringUtils.isBlank(passwd) || !passwd.equals(password)){
							println("Login incorrect");
							continue;
						} else {
							println("Welcome to SSMP Console, " + username);
							this.currentControlName = "";
							print("SSMP>");
							this.username = username;
							return true;
						}
					}else{
						continue;
					}
				}catch(IOException e){
					close();
					logger.error(name + " activity io(in, out) io exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();						
					}
					return false;
				}
			}
			println("Sorry, login incorrect, bye!");
			close();
			return false;
		}
	}

	private void print(String str){
		pw.print(str);
		pw.flush();
	}
	private void println(String str){
		pw.println(str);
		pw.flush();
	}
	
	private String getPrompt(){
		if (StringUtils.isBlank(this.currentControlName)){
			return "SSMP> ";
		}else{
			return "SSMP@" + currentControlName + "> ";
		}
	}
	
	@Override
	public void run(){
		try{
			socket.setSoTimeout(AutoProperties.getInt("console." + "timeout", 300000, confFileName));
		}catch(SocketException e){
			logger.warn("set " + name + " timeout Exception:" + e.toString());
			e.printStackTrace();
		}
		try{
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		}catch(IOException e){
			close();
			logger.error("get " + name + " io(in, out) exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return;
		}

		if(!login()){
			close();
			return;
		}
		
		try{
			String input = null;
			int ctrl;
			while(!isStop && (input = br.readLine()) != null){
//			while(!isStop && (ctrl = br.read()) > -1){
//				if(ctrl == 27){
//					System.out.println("press key VK_UP:" + ctrl);
//					print("show history");
//					continue;
//				} else {
//					input = br.readLine();
//					input = (char)ctrl + input;
//				}
				if(StringUtils.isBlank(input)){
					print(getPrompt());
					continue;
				} else {
					input = input.trim();
				}
				if(input.startsWith("set")){
					//设置环境变量 to do
					setSystemProperties(input);

				} else if(input.startsWith("log4j.rootLogger")) {
					//修改Log4j rootLogger的log Level
					try {
						int equalIndex = input.indexOf('=');
						String logLevel = input.substring(equalIndex + 1).trim();
						setLogLevel(Logger.getRootLogger(), logLevel);
						println("rootLogger=" + Logger.getRootLogger().getLevel());
					} catch (Exception e) {
						println("ERROR: <" + input + "> format error:" + e.toString());
						if(logger.isDebugEnabled()){
							e.printStackTrace();
						}
					}
				} else if(input.startsWith("log4j.logger.")) {
					//修改Log4j logger的log Level
					try {
						String afterStr = input.substring(13);
						int equalIndex = afterStr.indexOf('=');
						String logName = afterStr.substring(0, equalIndex).trim();
						String logLevel = afterStr.substring(equalIndex + 1).trim();
						setLogLevel(Logger.getLogger(logName), logLevel);
						println(logName + "=" + Logger.getLogger(logName).getLevel());
					} catch (Exception e) {
						println("ERROR: <" + input + "> format error:" + e.toString());
						if(logger.isDebugEnabled()){
							e.printStackTrace();
						}
					}
				} else if(input.startsWith("locate")) {
					// locate Class|Properties name
					String className = input.substring(6);
					if(StringUtils.isBlank(className)){
						println("ERROR: Class|Properties name is blank");
					}else{
						println(LibingUtils.locateClassPath(className.trim()));
					}
				} else if(input.startsWith("use")) {
					changeControlThread(input);
				} else if(input.startsWith("show")) {
					//show all, show thread threadName, show queue QueueName
					show(input);
				} else if("ll".equals(input)) {
					//show all, show thread threadName, show queue QueueName
					show("show all");
				} else if("stopall".equals(input)) {
					stopAllThread();
				} else if(input.startsWith("stop")) {
					//stop threadName
					stopThread(input);
				} else if(input.startsWith("alive")) {
					//alive threadName
					aliveThread(input);
				} else if(input.startsWith("start")) {
					//start command line
					startThread(input);
				} else if(input.startsWith("help")) {
					if(controlThread != null){
						controlThread.spectacle(input, pw);
					} else if(controlQueue != null){
						controlQueue.spectacle(input, pw);
					} else{
						help();
					}
				} else if(input.startsWith("exit") || input.startsWith("quit")){
					if(controlThread != null){
						controlThread = null;
						currentControlName = null;
					} else if(controlQueue != null){
						controlQueue = null;
						currentControlName = null;
					} else {
						break;
					}
				} else {
					if(controlThread != null){
						controlThread.spectacle(input, pw);
					} else if(controlQueue != null){
						controlQueue.spectacle(input, pw);
					} else {
						println(input + ": command not found");
					}
				}
				print(getPrompt());
			}
			if(isStop){
				println("sorry, your console is stopped by manager!");
			} else {
				println("Bye!");
			}
		}catch(Exception e){
			logger.debug("SSMPConsole process exception: " + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			println("ERROR:" + e.toString() + ", will exit!");
			if(isStop){
				println("sorry, your console maybe stopped by manager!");
			} else {
				println("Bye!");
			}
		}finally{
			close();
		}
	}

	private void startThread(String input){
		String line = input.substring(5);
		if(StringUtils.isBlank(line)){
			println("ERROR: start command line is blank");
		} else {
			line = line.trim();
			println(SSMPLoader.startLine(line));
		}
	}

	private void aliveThread(String input){
		String threadName = input.substring(5);
		if(StringUtils.isBlank(threadName)){
			println("ERROR: thread name is blank");
		} else {
			threadName = threadName.trim();
			ThreadSpectacle thread = SSMPLoader.getThreadSpectacle(threadName.trim());
			if(thread == null){
				if(controlThread != null){
					controlThread.spectacle(input, pw);
				} else {
					println("ERROR: " + threadName + " not found!");
				}
			} else {
				if(thread.isAlive()){
					println(" The " + threadName + " is alive!");
				}else{
					println(" The " + threadName + " is not alive, maybe stopped!");
					//SSMPLoader.removeThread(threadName);
				}
			}
		}
		SSMPLoader.checkAliveThread();
	}

	private void stopAllThread(){
		SSMPLoader.stopAllForConsole(pw);
	}
	
	private void stopThread(String input){
		String threadName = input.substring(4);
		if(StringUtils.isBlank(threadName)){
			println("ERROR: thread name is blank");
		} else {
			threadName = threadName.trim();
			ThreadSpectacle thread = SSMPLoader.getThreadSpectacle(threadName.trim());
			if(thread == null){
				if(controlThread != null){
					controlThread.spectacle(input, pw);
				} else {
					println("ERROR: " + threadName + " not found!");
				}
			} else {
				println("stop " + threadName + " , threadName Detailed information :");
				println(thread.showDetail());
				print("please wait");
				try{
					thread.stop();
					while(thread.isAlive()){
						print(".");
						try{
							Thread.sleep(1000);
						}catch(InterruptedException e){
						}
					}
					println("");
					println("thread " + threadName + " is stopped. ");
				}catch(SSMPException e){
					println("");
					println(e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
				}
			}
		}
		SSMPLoader.checkAliveThread();
	}

	private void show(String input){
		//show all, show thread threadName, show queue QueueName
		String cmd = input.substring(4);
		if(StringUtils.isBlank(cmd)){
			println("ERROR: show parameter error:" + input);
			println("usage: show [all|thread|queue] [threadName|queueName]");
			return;
		}else{
			cmd = cmd.trim();
		}
		cmd = cmd.trim();
		if(cmd.startsWith("thread")){
			String threadName = cmd.substring(6);
			showThread(threadName);
		} else if(cmd.startsWith("queue")){
			String queueName = cmd.substring(5);
			showQueue(queueName);
		} else if(cmd.startsWith("all")){
			showAll();
		} else {
			println("ERROR: show parameter error:" + input);
			println("usage: show [all|thread|queue] [threadName|queueName]");
		}
	}

	private void showThread(String threadName){
		if(StringUtils.isBlank(threadName)){
			println("ERROR: thread name is blank");
		} else{
			threadName = threadName.trim();
			ThreadSpectacle thread = SSMPLoader.getThreadSpectacle(threadName);
			if(thread == null){
				println("ERROR: " + threadName + " not found!");
			}else{
				println(thread.showDetail());
			}
		}
	}

	private void showQueue(String queueName){
		if(StringUtils.isBlank(queueName)){
			println("ERROR: queue name is blank");
		} else{
			queueName = queueName.trim();
			if(!SSMPQueue.containsQueue(queueName)){
				println("ERROR: " + queueName + " not found!");
			}else{
				println(SSMPQueue.getQueue(queueName).showDetail());
			}
		}
	}
	
	private void showAll(){
		println("----------------------- Thread info -----------------------------------------");
		println(SSMPLoader.showAll());
		println("----------------------- Queue  info -----------------------------------------");
		println(SSMPQueue.showAll());
	}
	
	private void changeControlThread(String input){
		String threadName = input.substring(3);
		if(StringUtils.isBlank(threadName)){
			println("ERROR: use parameter error: " + input);
			println("usage: use [threadName|queueName]");
			return;
		} else {
			threadName = threadName.trim();
		}
		controlThread = SSMPLoader.getThreadSpectacle(threadName.trim());
		if(controlThread == null){
			if(SSMPQueue.containsQueue(threadName)){
				controlQueue = SSMPQueue.getQueue(threadName);
				controlThread = null;
				this.currentControlName = threadName;
			} else{
				println("ERROR: Unknown threadName " + threadName);
			}
		} else {
			this.currentControlName = threadName;
		}
	}

	private void setLogLevel(Logger logger, String logLevel) {
		if (logLevel == null)
			return;
		if ("ALL".equalsIgnoreCase(logLevel))
			logger.setLevel(Level.ALL);
		else if ("DEBUG".equalsIgnoreCase(logLevel))
			logger.setLevel(Level.DEBUG);
		else if ("ERROR".equalsIgnoreCase(logLevel))
			logger.setLevel(Level.ERROR);
		else if ("FATAL".equalsIgnoreCase(logLevel))
			logger.setLevel(Level.FATAL);
		else if ("INFO".equalsIgnoreCase(logLevel))
			logger.setLevel(Level.INFO);
		else if ("OFF".equalsIgnoreCase(logLevel))
			logger.setLevel(Level.OFF);
		else if ("WARN".equalsIgnoreCase(logLevel))
			logger.setLevel(Level.WARN);

//		if(!LogTools.haveAppender(logger)) {
//			LogTools.setAppenderAsRoot(logger);
//		}
//		logger.setAdditivity(false);
	}

	private void setSystemProperties(String command){
	}

	
	public void help(){
		println("SSMPConsole usage!");
	}

	@Override
	public String showDetail(){
		StringBuffer sb = new StringBuffer();
		sb
			.append(showInfo()).append("\n")
			.append("详细信息：").append("\n")
			.append("\tappend print detail information!").append("\n");
		return sb.toString();
	}

	@Override
	public String showInfo(){
		return "Consumer - " + this.name + " - " + this.username + " - " + this.isAlive();
	}

	@Override
	public void stop() throws SSMPException{
		this.isStop = true;
		close();
	}
	
	@Override
	public boolean isAlive(){
		if(socket == null){
			return false;
		}
		if(socket.isClosed()){
			return false;
		}
		if(!socket.isConnected()){
			return false;
		}
		return true;
	}

	@Override
	public void spectacle(String command, PrintWriter ps){
		return;
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
	
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
//		Map<String, String> map = new Hashtable<String, String>();
//		map.put("1111", "aaaa");
//		map.put("2222", "bbbb");
//		map.put("3333", "cccc");
//		map.put("4444", "dddd");
//		map.put("5555", "eeee");
//		map.put("6666", "ffff");
//		map.put("7777", "gggg");
//
//		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
//		while(iterator.hasNext()){
//			String s = iterator.next().getValue();
//			if(s != null){
//				System.out.println(s);
//				try{
//					Thread.sleep(1000);
//				}catch(InterruptedException e){
//				}
//				s = null;
//			}
//			iterator.remove();
//		}
		
		InputStream in = System.in;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		int a;
		while(!"exit".equals(line)){
			a = br.read();
			System.out.println("a=" + a);
			line = br.readLine();
			System.out.println("line=" + line);
			
			a = in.read();
			System.out.println("a1=" + a);
		}

		
	}

}


