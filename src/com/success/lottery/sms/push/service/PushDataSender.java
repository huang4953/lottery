package com.success.lottery.sms.push.service;

import com.success.utils.ApplicationContextUtils;
import com.success.utils.ssmp.SSMPLoader;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PushDataSender implements ThreadSpectacle{
	private String name;
	private String bootTime;
	private String taskId;
	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(PushDataSender.class.getName());
	
//	private int ordersCount;
//	private int sendSucc;
//	private int sendFail;
	
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
		taskId = parameter;
	}

	@Override
	public String showDetail(){
		StringBuffer sb = new StringBuffer();
		sb.append(showInfo() + "\n");
		sb.append("\t").append(this.name + "'s Parameter:").append("\n");
		sb.append("\t\t");
		sb.append("isStop=" + this.isStop).append("\n");
		sb.append("\t\t");
		sb.append("isExit=").append(this.isExit).append("\n");
		sb.append("\t\t");
		sb.append("isAlive=").append(this.isAlive()).append("\n");
		sb.append("\n");
		sb.append("\t\t");
		sb.append("to be done");
		return sb.toString();
	}

	@Override
	public String showInfo(){
		return "T - PUSHDATASENDER - " + name + " - " + bootTime + " - TaskId[" + taskId + "] - " + isAlive();
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
		try{
			SmsPushTaskService smsPushTaskService = ApplicationContextUtils.getService("smsPushTaskService", com.success.lottery.sms.push.service.SmsPushTaskService.class);
			smsPushTaskService.executeTask(taskId);
		}catch(Exception e){
			logger.error(name + " send task(" + taskId + ") occur exception:" + e);
		}
		logger.info(name + " is shutdown!");
		this.isStop = true;
		this.isExit = true;
		SSMPLoader.checkAliveThread();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
	}
}
