package com.success.lottery.ticket.service;

import java.util.Hashtable;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.utils.AutoProperties;
import com.success.utils.LibingUtils;


public class TicketLogger{
	private Log logger;
	private String dlm = AutoProperties.getString("logDelimiter", ",", "com.success.lottery.ticket.service.TicketRouter");
	private static Map<String, TicketLogger> loggers = new Hashtable<String, TicketLogger>();
	
	public static TicketLogger getInstance(String type) {
		synchronized(type){
			TicketLogger log = loggers.get(type);
			if(log == null){
				log = new TicketLogger(type);
				loggers.put(type, log);
			}
			return log;
		}
	}
	
	private TicketLogger(String TYPE){
		if ("ORDER".equals(TYPE))
            logger = LogFactory.getLog("com.success.lottery.ticket.ORDER");
        else if ("TICKET".equals(TYPE))
        	logger = LogFactory.getLog("com.success.lottery.ticket.TICKET");
        else
        	logger = LogFactory.getLog("com.success.lottery.ticket.ORDER");
	}
	
	/**
	 * 记录订单出票日志，日志格式如下：<br>	
	 * ORDERSENDER<br>
	 * 		&nbsp;&nbsp;&nbsp;&nbsp;NAME, inTime, outTime, processorName, orderId, remark, , result<br>
	 * ORDERSPLITTER<br>
	 * 		&nbsp;&nbsp;&nbsp;&nbsp;NAME, inTime, outTime, processorName, orderId, remark, station, result<br>
	 * ORDERCHECKER<br>
	 * 		&nbsp;&nbsp;&nbsp;&nbsp;NAME, inTime, outTime, processorName, orderId, remark, , result<br>
	 * ORDERCHECK<br>
	 * 		&nbsp;&nbsp;&nbsp;&nbsp;NAME, inTime, outTime, processorName, orderId, remark, , result<br>
	 * ORDERMARKER<br>
	 * 		&nbsp;&nbsp;&nbsp;&nbsp;NAME, inTime, outTime, processorName, orderId, remark, , result<br>
	 * @param info
	 */
	public void log(OrderLogInfo info){
		StringBuffer sb=new StringBuffer();
		sb.append(info.getName()).append(dlm);
		sb.append(LibingUtils.getFormatTime(info.getInTime(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		sb.append(LibingUtils.getFormatTime(info.getOutTime(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		sb.append(info.getProcessorName()).append(dlm);
		sb.append(info.getOrderId()).append(dlm);
		
		info.trimSrcRemark();
		String remark = info.getRemark();
		remark = (remark == null ? "" : remark.trim().replaceAll(",", "<2C>"));
		sb.append(remark).append(dlm);
		
		if(!StringUtils.isBlank(info.getStation())){
			sb.append(info.getStation().trim()).append(dlm);
		}else{
			sb.append("").append(dlm);
		}
		if(StringUtils.isBlank(info.getResult())){
			sb.append("(OK)");
		}else{
			sb.append(info.getResult().trim());
		}
		logger.info(sb.toString());
	}

	/**
	 * 记录出票打印日志，日志格式如下：<br>	
	 * TICKETSENDER<br>
	 * 		&nbsp;&nbsp;&nbsp;&nbsp;NAME, inTime, outTime, processorName, ticketSequence, orderId, remark, station, result<br> 
	 * @param info
	 */
	public void log(TicketLogInfo info){
		StringBuffer sb=new StringBuffer();
		sb.append(info.getName()).append(dlm);
		sb.append(LibingUtils.getFormatTime(info.getInTime(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		sb.append(LibingUtils.getFormatTime(info.getOutTime(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		sb.append(info.getProcessorName()).append(dlm);
		sb.append(info.getTicketSequence()).append(dlm);
		sb.append(info.getOrderId()).append(dlm);

		info.trimSrcRemark();
		String remark = info.getRemark();
		remark = (remark == null ? "" : remark.trim().replaceAll(",", "<2C>"));
		sb.append(remark).append(dlm);
		
		if(!StringUtils.isBlank(info.getStation())){
			sb.append(info.getStation().trim()).append(dlm);
		}else{
			sb.append("").append(dlm);
		}
		if(StringUtils.isBlank(info.getResult())){
			sb.append("(OK)");
		}else{
			sb.append(info.getResult().trim());
		}
		logger.info(sb.toString());
	}
	
	public static void main(String[] args){
	}
}
