/**
 * Title: ReportLog.java
 * @Package com.success.lottery.report.service
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 下午04:02:05
 * @version V1.0
 */
package com.success.lottery.report.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * com.success.lottery.report.service
 * ReportLog.java
 * ReportLog
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 下午04:02:05
 * 
 */

public class ReportLog {
	private static final String LINK_SIGN = "#";
	private Log logger;
	private static Map<String, ReportLog> loggers = new Hashtable<String, ReportLog>();
	
	
	private ReportLog(String TYPE){
        if ("PRIZE".equals(TYPE)){
            this.logger = LogFactory.getLog("com.success.lottery.report.prizeCount");
        }else if("ACCOUNT".equals(TYPE)){
        	this.logger = LogFactory.getLog("com.success.lottery.report.accountCount");
        }else{
        	this.logger = LogFactory.getLog(ReportLog.class);
        }
	}
	
	
	public static ReportLog getInstance(String type) {
		ReportLog log = loggers.get(type);
		if(log == null){
			log = new ReportLog(type);
			loggers.put(type, log);
		}
		return log;
	}
	/**
	 * 
	 * Title: logInfo<br>
	 * Description: <br>
	 *              <br>
	 * @param reportType 报表类型
	 * @param lotteryId
	 * @param termNo
	 * @param msg 结果
	 */
	public void logInfo(String reportType,int lotteryId,String termNo,String msg){
		try {
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append(reportType).append(LINK_SIGN).append(lotteryId).append(LINK_SIGN);
			sb.append(termNo).append(LINK_SIGN).append(msg);
			this.logger.info(sb.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: logInfo<br>
	 * Description: <br>
	 *              <br>账户变动统计日志
	 * @param reportType
	 * @param accountDate
	 * @param beginTime
	 * @param endTime
	 * @param msg
	 */
	public void logInfo(String reportType,String accountDate,Timestamp beginTime,Timestamp endTime,String msg){
		try {
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append(reportType).append(LINK_SIGN).append(accountDate).append(LINK_SIGN);
			sb.append(beginTime).append(LINK_SIGN).append(endTime).append(LINK_SIGN).append(msg);
			this.logger.info(sb.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	

}
