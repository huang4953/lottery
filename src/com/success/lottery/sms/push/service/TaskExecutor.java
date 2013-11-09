package com.success.lottery.sms.push.service;

import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.monitor.SSMPException;
import com.success.utils.ssmp.monitor.ThreadSpectacle;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TaskExecutor implements ThreadSpectacle{
	private String name;
	private String bootTime;
	private boolean isStop = false;
	private boolean isExit = false;
	private Log logger = LogFactory.getLog(TaskExecutor.class.getName());
	
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
		return "T - TASKEXECUTOR - " + name + " - " + bootTime + " - " + isAlive();
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
		String resource = "com.success.lottery.sms.sms";
		SmsPushTaskService smsPushTaskService = ApplicationContextUtils.getService("smsPushTaskService", com.success.lottery.sms.push.service.SmsPushTaskService.class);
		SmsPushDataService smsPushDataService = ApplicationContextUtils.getService("smsPushDataService", com.success.lottery.sms.push.service.SmsPushDataService.class);
		while(!isStop){
			try{
				String rs = smsPushTaskService.findTaskAndExecution();
				if(rs == null){
					logger.debug("TaskExecutor get 0 task to execute, sleep " + AutoProperties.getInt("smspush.sleepInterval", 60000, resource) + "ms.");
					try{
						Thread.sleep(AutoProperties.getInt("smspush.sleepInterval", 60000, resource));
					}catch(Exception e){
					}
				} else {
					logger.debug("TaskExecutor get 1 task to execute result is:" + rs + ", sleep " + AutoProperties.getInt("smspush.interval", 10000, resource) + "ms.");
					try{
						Thread.sleep(AutoProperties.getInt("smspush.interval", 10000, resource));
					}catch(Exception e){
					}
				}
				int rc = 0;
				rc = smsPushTaskService.checkTimeoutTask();
				logger.debug("TaskExecutor checkTimeoutTask and update " + rc + " task.");
				rc = smsPushDataService.checkTimeoutData();
				logger.debug("TaskExecutor checkTimeoutData and update " + rc + " push data.");
			}catch(Exception e){
				logger.error(name + " execute task occur exception:" + e);
				try {
					Thread.sleep(AutoProperties.getInt("smspush.sleepInterval", 60000, resource));
				} catch (InterruptedException ex) {
				}
			}
		}
		logger.info(name + " is shutdown!");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
	}
}
