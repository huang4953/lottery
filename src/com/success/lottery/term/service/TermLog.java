/**
 * Title: TermSwitchLog.java
 * @Package com.success.lottery.term.service
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-15 下午02:16:37
 * @version V1.0
 */
package com.success.lottery.term.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.util.LotteryTools;


/**
 * com.success.lottery.term.service
 * TermSwitchLog.java
 * TermSwitchLog
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-15 下午02:16:37
 * 
 */

public class TermLog {
	private static final String LINK_SIGN = "#";
	private Log logger;
	private static Map<String, TermLog> loggers = new Hashtable<String, TermLog>();
	private static Map<Integer, String> lottery = new Hashtable<Integer, String>();
	
	public static TermLog getInstance(String type) {
		TermLog log = loggers.get(type);
		if(log == null){
			log = new TermLog(type);
			loggers.put(type, log);
		}
		if(lottery.isEmpty()){
			lottery.putAll(LotteryTools.getLotteryList());
		}
		return log;
	}
	
	private TermLog(String TYPE){
        if ("TS".equals(TYPE)){
            this.logger = LogFactory.getLog("com.success.lottery.term.termSwitch");
        }else if("DJ".equals(TYPE)){
        	this.logger = LogFactory.getLog("com.success.lottery.term.cashPrize");
        }else if("PJ".equals(TYPE)){
        	this.logger = LogFactory.getLog("com.success.lottery.term.dispatchPrize");
        }else if("BTS".equals(TYPE)){
        	this.logger = LogFactory.getLog("com.success.lottery.term.termSwitchB");
        }else if("COOPLOG".equals(TYPE)){
        	this.logger = LogFactory.getLog("com.success.lottery.term.hemailog");
        }else if("TK".equals(TYPE)){
        	this.logger = LogFactory.getLog("com.success.lottery.term.tk");
        }else{
        	this.logger = LogFactory.getLog(TermLog.class);
        }
	}
	
	public void logInfo(String msg){
		this.logger.info(msg);
	}
	
	public void logInfo(int lotteryId,String termNo,String msg){
		try {
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append(lotteryId).append(LINK_SIGN).append(lottery.get(lotteryId)).append(LINK_SIGN);
			sb.append(termNo).append(LINK_SIGN);
			sb.append(msg);
			this.logger.info(sb.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void logInfo(int lotteryId,String termNo,String orderId,String msg){
		try {
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append(lotteryId).append(LINK_SIGN).append(lottery.get(lotteryId)).append(LINK_SIGN);;
			sb.append(termNo).append(LINK_SIGN);
			sb.append(orderId).append(LINK_SIGN);
			sb.append(msg);
			this.logger.info(sb.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void logError(String msg){
		this.logger.error(msg);
	}
	
	public void logError(String msg,Throwable e){
		this.logger.error(msg,e);
	}
	
	public boolean isDebugEnabled(){
		return this.logger.isDebugEnabled();
	}

}
