package com.success.lottery.sms;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Semaphore;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.sms.process.SMSService;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;


public class MOProcessor implements ThreadSpectacle{

	/**
	 * 线程名称，在SSMPLoader中登记的名称
	 */
	private String name;
	private String dlm1 = "[ ,，]";
	private String dlm2 = "[#]";
	private String dlm3 = "[-]";
	/**
	 * MO处理线程对应的SMSClient名称；
	 * 指定该名称后，MO处理线程仅处理来自于指定SMSClient的MO消息；
	 * 这样做的目的是为了保证Order和Query请求的Resp消息回到正确的SMSClient；
	 */
	private String clientName;
	private String bootTime;
	
	private boolean isStop;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(MOProcessor.class.getName());
	
	
	private int lock;
	private Semaphore semaphore;
	private int interval;
	
	private int moIn;
	private int moSucc;
	private int moFail;
	
	private int mtSend;
	private int mtSendSucc;
	private int mtSendFail;

	private long getMOTime;

	@Override
	public String getName(){
		return this.name;
	}

	@Override
	public boolean isAlive(){
		return !isStop && !isExit;
	}

	@Override
	public void setName(String name){
		this.name = name;
	}

	@Override
	public void setParameter(String parameter){
		bootTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		if(StringUtils.isBlank(parameter)){
			this.clientName = "";
		} else {
			this.clientName = parameter.trim();
		}
		readConfig();
	}

	@Override
	public String showDetail(){
		StringBuffer sb = new StringBuffer();
		sb.append(showInfo() + "\n");
		sb.append("\t").append(this.name + "'s Parameter:").append("\n");
		sb.append("\t\t");
		sb.append("lock=" + this.lock).append(", isExit=").append(this.isExit).append("\n");
		sb.append("\t\t");
		sb.append("isStop=" + this.isStop).append(" , ").append("isAlive=").append(this.isAlive()).append("\n");
		sb.append("\t\t");
		sb.append("clientName=" + this.clientName).append("\n");
		sb.append("\n");
		sb.append("\t").append(this.name + "'s Queue:").append("\n");
		sb.append("\t\t");
		sb.append("to be done");
		return sb.toString();
	}

	// T - name - clientName - bootTime - MO[in/succ/fail] - MT[send/succ/fail] - S[ing/all/wait] - alive
	@Override
	public String showInfo(){
		return "T - " + name + " - " + clientName + " - " + bootTime + " - MO[" + moIn + "/" + moSucc + "/" + moFail + "] - MT["
			+ mtSend + "/" + mtSendSucc + "/" + mtSendFail + "] - S[" 
			+ (lock - semaphore.availablePermits()) + "/" + lock + "/" + semaphore.getQueueLength() 
			+ "] - "	+ isAlive();
	}

	@Override
	public void spectacle(String command, PrintWriter pw){
	}

	@Override
	public void stop() throws SSMPException{
		this.isStop = true;
	}

	@Override
	public void run(){
		logger.info(name + " is running...");
		while(!isStop){
			try{
				MO mo = SMSDispatch.getMO(clientName);
				if(mo != null){
					getMOTime = System.currentTimeMillis();
					logger.debug("MOProcessor get a MO:" + mo.toString());
					moIn++;
					semaphore.acquire();
					mo.setProcessorName(this.name);
					mo.setInTime(System.currentTimeMillis());
					ProcessMO processor = new ProcessMO();
					processor.setMo(mo);
					new Thread(processor).start();
				} else {
					Thread.sleep(interval);
				}
			}catch(Exception e){
				logger.error("MOProcessor exception:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}
		}
		isExit = true;
		logger.info(name + " is shutdown!");
	}

	private void readConfig(){
		String resource = "com.success.lottery.sms.sms";
		this.lock = AutoProperties.getInt(name + ".lock", -1, resource);
		if(lock == -1){
			lock = AutoProperties.getInt("processMO.lock", 20, resource);
		}
		semaphore = new Semaphore(lock);
		interval = AutoProperties.getInt(name + ".interval", -1, resource);
		if(interval == -1){
			interval = AutoProperties.getInt("processMO.interval", 10000, resource);
		}
		
		dlm1 = AutoProperties.getString("dlm1", "[ ,，]", resource);
		dlm2 = AutoProperties.getString("dlm2", "[#]", resource);
		dlm3 = AutoProperties.getString("dlm3", "[-]", resource);
		
	}
	
	private class ProcessMO implements Runnable{

		private String resource = "com.success.lottery.sms.sms";
		private MO mo;
		public void setMo(MO mo){
			this.mo = mo;
		}

		@Override
		public void run(){
			logger.debug("process MO:" + mo.toString());
			String rs = null;
			mtSend++;
			try{
				rs = processMO(mo);
			}catch(Exception e){
				rs = "ProcessMO FAIL:" + e.toString() + "&&Unknow exception";
				logger.error("ProcessMO FAIL:" + e.toString() + "&&Unknow exception");
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}finally{
				semaphore.release();
			}
			String moRs = null;
			if(rs == null || StringUtils.isBlank(rs)){
				moRs = null;
				moSucc++;
				mtSendSucc++;
			}else{
				String[] rss = rs.split("&&");
				if(rss.length < 1){
					//不可能出现这种情况
					logger.error("MOProcessor Runtime error, process result must split by &&");
				} else if(rss.length == 1){
					mtSendSucc++;
					if(StringUtils.isBlank(rss[0])){
						moSucc++;
					}else{
						moRs = rss[0].trim();
						moFail++;
					}
				} else if(rss.length == 2){
					if(StringUtils.isBlank(rss[0])){
						moSucc++;
					}else{
						moFail++;
						moRs = rss[0].trim();
					}
					if(StringUtils.isBlank(rss[1])){
						mtSendSucc++;
					}else{
						mtSendFail++;
					}
				}
			}
			mo.setErrMsg(moRs);
			mo.setOutTime(System.currentTimeMillis());
			//记录日志
			SMSLog.getInstance("MO").log(mo);
		}
		
		private String processMO(MO mo){
			String rs = null;
			try{
				if(StringUtils.isBlank(mo.getMsgContent())){
					return  "ProcessMO FAIL: MO content is null&&" + sendError(mo);
				}
				String content = mo.getMsgContent().trim();
				String cmd[] = content.split(dlm1);
				mo.setOutTime(getMOTime);
				SMSService processor = SMSService.getProcessor(cmd[0].trim());
				if(processor == null){
					rs = "ProcessMO FAIL: not found processor SMSService" + cmd[0].trim().toUpperCase() + "&&" + sendError(mo);
				} else {
					rs = processor.process(SMSService.string2Ascii(cmd[0].trim()), mo);
				}				
			}catch(Exception e){
				rs = "ProcessMO FAIL:" + e.toString() + "&&Unknow exception";
			}
			return rs;
		}
		
		private String sendError(MO mo){
			String rs = null;
			MT mt = null;
			try{
				mt = new MT(mo);
				mt.setInTime(getMOTime);
				mt.setProcessorName(name);
				mt.setChannelId(AutoProperties.getString("cmderr.channel", "99", resource));
				String content = AutoProperties.getString("cmderr.respMsg", "错误的指令", resource);
				try{
					content = new String(content.getBytes(), "GBK");
				}catch(UnsupportedEncodingException e){
					logger.warn("MO Processor encoding content exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
				}
				mt.setMsgContent(content);
				mt.setMsgLength(content.getBytes().length);
				mt.setOutTime(System.currentTimeMillis());
				mt.setErrMsg(rs = SMSDispatch.dispatch(mt));
			}catch(Exception e){
				rs = "sendError MT FAIL:" + e.toString();
				logger.warn("sendError MT FAIL:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}
			SMSLog.getInstance("MT").log(mt);
			return rs;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
//		String resource = "com.success.lottery.sms.sms";
//		String dlm1 = AutoProperties.getString("dlm1", "[ ]", resource);
//		String dlm2 = AutoProperties.getString("dlm2", "[#]", resource);
//		String dlm3 = AutoProperties.getString("dlm3", "[-]", resource);
//		System.out.println(dlm1);
////		String content = "DLT，12,0102030405-111213#5555555-666";
////		//String cmd[] = content.split("[ ,，]");
////		String cmd[] = content.split(dlm1);
////		for(String s : cmd){
////			System.out.println(s);
////		}
////		System.out.println("--------------------------");
////		String ss[] = cmd[2].split(dlm2);
////		for(String s : ss){
////			System.out.println(s);
////		}
//		
//		String content = "D";
//		String cmd[] = content.split(dlm1);
//		for(String s : cmd){
//			System.out.println(s);
//		}
		
		String str = "sdsadf dsaf; fdasf ;       sdaf";
		String ss[] = null;
		ss = str.split("&&");
		for(String s : ss){
			System.out.println("1-" + s);
		}
		System.out.println("-----------------------------");
		str = "fadf:dasj;l dasf ;llk;z;,cs afdsf&&";
		ss = str.split("&&");
		for(String s : ss){
			System.out.println("1-" + s);
		}

		System.out.println("-----------------------------");
		str = "&&:ccif   FAasasdfasd     dfsdafasd";
		ss = str.split("&&");
		for(String s : ss){
			System.out.println("1-" + s);
		}

		
		System.out.println("-----------------------------");
		str = "fadf:dasj;l dasf ;llk;z;,cs afdsf&&:ccif   FAasasdfasd     dfsdafasd";
		ss = str.split("&&");
		for(String s : ss){
			System.out.println("1-" + s);
		}

		System.out.println("-----------------------------");
		str = "&&";
		ss = str.split("&&");
		for(String s : ss){
			System.out.println("1-" + s);
		}

		
	}
}
