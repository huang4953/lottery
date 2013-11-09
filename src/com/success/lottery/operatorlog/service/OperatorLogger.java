/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.success.lottery.operatorlog.service;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.interf.OperatorLogService;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.LibingUtils;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author bing.li
 */
public class OperatorLogger {
	private static Log	logger			= LogFactory.getLog(OperatorLogger.class.getName());
	private static String resource = "com.success.lottery.operatorlog.service.OperatorLog";

	public static final int DEBUG = 10000;
	public static final int INFO = 20000;
	public static final int WARN = 30000;
	public static final int ERROR = 40000;
	public static final int FATAL = 50000;

	public static final int OTHERTYPE = 0;
	public static final int BACKSAFETY = 10000;
	public static final int FRONTSAFETY = 20000;
	public static final int CUSTOMER = 30000;
	public static final int PROCESSOR = 40000;

	public static final int NORMAL = 10000;
	public static final int NECESSARY = 20000;
	public static final int IMPORTANT = 30000;
	public static final int URGENT = 40000;

	/**
	 * 记录操作日志
	 * @param logId：参看“操作日志记录.xml”，相关操作日志的编号；
	 * @param param：操作日志相关参数，参看“操作日志记录.xml”；
	 */
	public static void log(int logId, Map<String, String> param){
		try {
			if(param == null){
				logger.warn("get a operatorlog(" + logId + ") to record, but the param is null, don't log it.");
				return;
			}
			try{
				Long.parseLong(param.get("userId"));
			}catch(Exception e){
				logger.warn("get a operatorlog(" + logId + ") to record, but the param's userId(" + param.get("userId") + ") is error, don't log it.");
				return;
			}
			logger.debug("get a operatorlog(" + logId + ") to record, param info is:");
			for(String key : param.keySet()){
				logger.debug("the operatorlog(" + logId + ")'s " + key + " = " + param.get(key));
			}
			ParamInfo info = getParamInfo(logId, param);
			
			logger.debug("get the operatorlog(" + logId + ") paramInfo will insert into datebase, paramInfo is:");
			logger.debug("the operatorlog(" + logId + ") paramInfo's logIt = " + info.isLogIt());
			logger.debug("the operatorlog(" + logId + ") paramInfo's level = " + info.getLevel());
			logger.debug("the operatorlog(" + logId + ") paramInfo's rank = " + info.getRank());
			logger.debug("the operatorlog(" + logId + ") paramInfo's type = " + info.getType());
			logger.debug("the operatorlog(" + logId + ") paramInfo's userId = " + info.getUserId());
			logger.debug("the operatorlog(" + logId + ") paramInfo's name = " + info.getName());
			logger.debug("the operatorlog(" + logId + ") paramInfo's userKey = " + info.getUserKey());
			logger.debug("the operatorlog(" + logId + ") paramInfo's keyword1 = " + info.getKeyword1());
			logger.debug("the operatorlog(" + logId + ") paramInfo's keyword2 = " + info.getKeyword2());
			logger.debug("the operatorlog(" + logId + ") paramInfo's keyword3 = " + info.getKeyword3());
			logger.debug("the operatorlog(" + logId + ") paramInfo's keyword4 = " + info.getKeyword4());
			logger.debug("the operatorlog(" + logId + ") paramInfo's message = " + info.getMessage());

			if(info.isLogIt()){
				OperatorLogService service = ApplicationContextUtils.getService("operatorLogService", OperatorLogService.class);
				service.addLogRecord(info.getLevel(), info.getType(), info.getRank(), info.getUserId(), info.getName(), info.getUserKey(), info.getKeyword1(), info.getKeyword2(), info.getKeyword3(), info.getKeyword4(), info.getMessage());
				logger.debug("insert the operatorlog(" + logId + ") into datebase success.");
			}else{
				logger.debug("the operatorlog(" + logId + ") is set don't logit.");
			}
		} catch (LotteryException e) {
			logger.error("insert operator log occur lottery exception:" + e);
		} catch(Exception e){
			logger.error("insert operator log occur unknow exception:" + e);
		}
	}

	/**
	 * 记录一条操作日志，该操作不根据操作日志编号进行记录
	 * @param level：日志级别
	 * @param rank：日志
	 * @param type：日志类型
	 * @param message：要记录的消息内容
	 * @param param：其他要记录的关键字参数
	 */
	public static void log(int level, int rank, int type, String message, Map<String, String> param){
		try {
			if(message == null){
				logger.warn("get a operatorlog(-1) to record, but the message is null, don't log it.");
				return;
			}
			if(param == null){
				logger.warn("get a operatorlog(-1) to record, but the param is null, don't log it.");
				return;
			}
			String userKey = param.get("userKey");
			if(userKey == null || "".equals(userKey.trim())){
				logger.warn("the operatorlog(-1) param's userKey is null, use local IP.");
				userKey = LibingUtils.getLocalIPAddress();
			}
			ParamInfo info = new ParamInfo();
			info.setLogIt(true);
			info.setLevel(level);
			info.setRank(rank);
			info.setType(type);
			try{
				info.setUserId(Long.parseLong(param.get("userId")));
			}catch(Exception e){
				info.setUserId(-1);
			}
			info.setName(param.get("userName"));
			info.setUserKey(userKey);
			info.setKeyword1(param.get("keyword1"));
			info.setKeyword2(param.get("keyword2"));
			info.setKeyword3(param.get("keyword3"));
			info.setKeyword4(param.get("keyword4"));
			info.setMessage(message);

			logger.debug("get the operatorlog(-1) paramInfo will insert into datebase, paramInfo is:");
			logger.debug("the operatorlog(-1) paramInfo's logIt = " + info.isLogIt());
			logger.debug("the operatorlog(-1) paramInfo's level = " + info.getLevel());
			logger.debug("the operatorlog(-1) paramInfo's rank = " + info.getRank());
			logger.debug("the operatorlog(-1) paramInfo's type = " + info.getType());
			logger.debug("the operatorlog(-1) paramInfo's userId = " + info.getUserId());
			logger.debug("the operatorlog(-1) paramInfo's name = " + info.getName());
			logger.debug("the operatorlog(-1) paramInfo's userKey = " + info.getUserKey());
			logger.debug("the operatorlog(-1) paramInfo's keyword1 = " + info.getKeyword1());
			logger.debug("the operatorlog(-1) paramInfo's keyword2 = " + info.getKeyword2());
			logger.debug("the operatorlog(-1) paramInfo's keyword3 = " + info.getKeyword3());
			logger.debug("the operatorlog(-1) paramInfo's keyword4 = " + info.getKeyword4());
			logger.debug("the operatorlog(-1) paramInfo's message = " + info.getMessage());

			OperatorLogService service = ApplicationContextUtils.getService("operatorLogService", OperatorLogService.class);
			service.addLogRecord(info.getLevel(), info.getType(), info.getRank(), info.getUserId(), info.getName(), info.getUserKey(), info.getKeyword1(), info.getKeyword2(), info.getKeyword3(), info.getKeyword4(), info.getMessage());
			logger.debug("insert the operatorlog(-1) into datebase success.");
		} catch (LotteryException e) {
			logger.error("insert operator log(-1) occur lottery exception:" + e);
		} catch(Exception e){
			logger.error("insert operator log(-1) occur unknow exception:" + e);
		}
	}

	private static int getIntValue(int logId, String key){
		String value = AutoProperties.getString(logId + "." + key, "NA", resource);
		if("NA".equals(value)){
			value = AutoProperties.getString("default." + key, "NA", resource);
		}
		try{
			return Integer.parseInt(value);
		}catch(Exception e){
		}
		if("DEBUG".equalsIgnoreCase(value)){
			return OperatorLogger.DEBUG;
		}else if("INFO".equalsIgnoreCase(value)){
			return OperatorLogger.INFO;
		}else if("WARN".equalsIgnoreCase(value)){
			return OperatorLogger.WARN;
		}else if("ERROR".equalsIgnoreCase(value)){
			return OperatorLogger.ERROR;
		}else if("FALAT".equalsIgnoreCase(value)){
			return OperatorLogger.FATAL;
		}else if("NORMAL".equalsIgnoreCase(value)){
			return OperatorLogger.NORMAL;
		}else if("NECESSARY".equalsIgnoreCase(value)){
			return OperatorLogger.NECESSARY;
		}else if("IMPORTANT".equalsIgnoreCase(value)){
			return OperatorLogger.IMPORTANT;
		}else if("URGENT".equalsIgnoreCase(value)){
			return OperatorLogger.URGENT;
		}else if("OTHERTYPE".equalsIgnoreCase(value)){
			return OperatorLogger.OTHERTYPE;
		}else if("BACKSAFETY".equalsIgnoreCase(value)){
			return OperatorLogger.BACKSAFETY;
		}else if("FRONTSAFETY".equalsIgnoreCase(value)){
			return OperatorLogger.FRONTSAFETY;
		}else if("CUSTOMER".equalsIgnoreCase(value)){
			return OperatorLogger.CUSTOMER;
		}else if("PROCESSOR".equalsIgnoreCase(value)){
			return OperatorLogger.PROCESSOR;
		}else{
			return 0;
		}
	}

	private static String getMessage(int logId, Map<String, String> param){
		String message = AutoProperties.getString(logId + ".message", "NA", resource);
		if("NA".equals(message)){
			message = AutoProperties.getString("default.message", "没有定义日志内容", resource);
		}
		String errorMessage = param.get("errorMessage") == null ? "not error msg" : param.get("errorMessage");
		if(message.indexOf("{errorMessage}") >= 0){
			message = message.replaceAll("\\{errorMessage\\}", errorMessage);
		}
		String keyword1 = param.get("keyword1") == null ? "无" : param.get("keyword1");
		String keyword2 = param.get("keyword2") == null ? "无" : param.get("keyword2");
		String keyword3 = param.get("keyword3") == null ? "无" : param.get("keyword3");
		String keyword4 = param.get("keyword4") == null ? "无" : param.get("keyword4");

		if(message.indexOf("{old}") >= 0){
			message = message.replaceAll("\\{old\\}", keyword3);
		}
		if(message.indexOf("{new}") >= 0){
			message = message.replaceAll("\\{new\\}", keyword4);
		}
		if(message.indexOf("{loginName}") >= 0){
			message = message.replaceAll("\\{loginName\\}", keyword1);
		}
		if(message.indexOf("{userId}") >= 0){
			message = message.replaceAll("\\{userId\\}", keyword4);
		}
		if(message.indexOf("{orderId}") >= 0){
			message = message.replaceAll("\\{orderId\\}", keyword3);
		}

		if(message.indexOf("{email}") >= 0){
			message = message.replaceAll("\\{email\\}", keyword2);
		}

		if(message.indexOf("{mobilePhone}") >= 0){
			switch(logId){
				case 20001:
				case 21001:
				case 20002:
				case 21002:
				case 20003:
				case 21003:
				case 20004:
				case 21004:
					message = message.replaceAll("\\{mobilePhone\\}", keyword1);
					break;
				default:
					message = message.replaceAll("\\{mobilePhone\\}", keyword3);
			}
		}

		if(message.indexOf("{date}") >= 0){
			message = message.replaceAll("\\{date\\}", keyword1);
		}

		if(message.indexOf("{taskId}") >= 0){
			switch(logId){
				case 40020:
				case 40021:
				case 41020:
				case 41021:
				case 40022:
				case 41022:
					message = message.replaceAll("\\{taskId\\}", keyword2);
					break;
				case 40023:
				case 40024:
				case 41024:
				case 40025:
				case 41025:
					message = message.replaceAll("\\{taskId\\}", keyword3);
					break;
				default:
					message = message.replaceAll("\\{taskId\\}", "无");
			}
		}

		if(message.indexOf("{pageName}") >= 0){
			message = message.replaceAll("\\{pageName\\}", keyword3);
		}

		if(message.indexOf("{pageFile}") >= 0){
			message = message.replaceAll("\\{pageFile\\}", keyword4);
		}

		if(message.indexOf("{lotteryId}") >= 0){
			message = message.replaceAll("\\{lotteryId\\}", keyword1);
		}

		if(message.indexOf("{lotteryTerm}") >= 0){
			message = message.replaceAll("\\{lotteryTerm\\}", keyword2);
		}
		
		if(message.indexOf("{reportBeginTime}") >= 0){
			message = message.replaceAll("\\{reportBeginTime\\}", keyword1);
		}

		if(message.indexOf("{reportEndTime}") >= 0){
			message = message.replaceAll("\\{reportEndTime\\}", keyword1);
		}
		if(message.indexOf("{totalNum}") >= 0){
			message = message.replaceAll("\\{totalNum\\}", keyword3);
		}
		if(message.indexOf("{successNum}") >= 0){
			message = message.replaceAll("\\{successNum\\}", keyword4);
		}
		if(message.indexOf("{planId}") >= 0){
			message = message.replaceAll("\\{planId\\}", keyword3);
		}
		
		return message;
	}

	private static ParamInfo getParamInfo(int logId, Map<String, String> param){
		int type = getIntValue(logId, "type");
		int rank = getIntValue(logId, "rank") == 0 ? OperatorLogger.NORMAL : getIntValue(logId, "rank");
		int levle = getIntValue(logId, "level") == 0 ? OperatorLogger.INFO : getIntValue(logId, "level");
		boolean logIt = "true".equals(AutoProperties.getString(logId + ".isLog", "true", resource));
		boolean selfMsg = "true".equals(AutoProperties.getString(logId + ".selfMsg", "true", resource));

		String userKey = param.get("userKey");
		if(userKey == null || "".equals(userKey.trim())){
			logger.warn("the operatorlog(" + logId + ") param's userKey is null, use local IP.");
			userKey = LibingUtils.getLocalIPAddress();
		}
		ParamInfo info = new ParamInfo();
		info.setLogIt(logIt);
		info.setType(type);
		info.setRank(rank);
		info.setLevel(levle);
		info.setUserId(Long.parseLong(param.get("userId")));
		info.setName(param.get("userName"));
		info.setUserKey(userKey);
		info.setKeyword1(param.get("keyword1"));
		info.setKeyword2(param.get("keyword2"));
		info.setKeyword3(param.get("keyword3"));
		info.setKeyword4(param.get("keyword4"));
		if(selfMsg){
			info.setMessage(param.get("message") == null ? "未提供日志消息" : param.get("message"));
		}else{
			info.setMessage(getMessage(logId, param));
		}
		return info;
	}

	public static void main(String[] args){
		Map<String, String> param = new HashMap<String, String>();

//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "libing");
//		OperatorLogger.log(10001, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "libing");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(11001, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "libing");
//		OperatorLogger.log(10002, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2222222");
//		OperatorLogger.log(10003, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2222222");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(11003, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2222222");
//		OperatorLogger.log(10004, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2222222");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(11004, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "13761874366");
//		param.put("keyword2", "bing.li@chinatlt.com");
//		OperatorLogger.log(20001, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "13761874366");
//		param.put("keyword2", "bing.li@chinatlt.com");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(21001, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "13761874366");
//		param.put("keyword2", "bing.li@chinatlt.com");
//		OperatorLogger.log(20002, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "13761874366");
//		param.put("keyword2", "bing.li@chinatlt.com");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(21002, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "13761874366");
//		OperatorLogger.log(20003, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "13761874366");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(21003, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "13761874366");
//		OperatorLogger.log(20004, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "13761874366");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(21004, param);

//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(40001, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(41001, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1300001");
//		param.put("keyword2", "10076");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(40002, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1300001");
//		param.put("keyword2", "10076");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(41002, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		param.put("keyword3", "234,345");
//		param.put("keyword4", "555,6666");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(40003, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		param.put("keyword4", "555,6666");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(41003, param);

//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(40004, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(40005, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		param.put("keyword3", "DD832342332323232342332");
//		param.put("keyword4", "999999");
//		param.put("errorMessage", "兑奖出错");
//		OperatorLogger.log(41004, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(41005, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		OperatorLogger.log(40006, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000001");
//		param.put("keyword2", "10076");
//		param.put("errorMessage", "兑奖完毕更新彩期信息错误");
//		OperatorLogger.log(41006, param);

//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2010-05-10");
//		param.put("keyword2", "TT8942432432432432");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(40020, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2010-05-10");
//		param.put("keyword2", "TT8942432432432432");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(40021, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2010-05-10");
//		param.put("keyword2", "TT8942432432432432");
//		param.put("keyword3", "13765765463");
//		param.put("keyword4", "999999");
//		param.put("errorMessage", "兑奖出错");
//		OperatorLogger.log(41020, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2010-05-10");
//		param.put("keyword2", "TT8942432432432432");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(41021, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2010-05-10");
//		param.put("keyword2", "TT8942432432432432");
//		OperatorLogger.log(40022, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2010-05-10");
//		param.put("keyword2", "TT8942432432432432");
//		param.put("errorMessage", "兑奖完毕更新彩期信息错误");
//		OperatorLogger.log(41022, param);
//
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		param.put("keyword3", "CT3234324324324232");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(40023, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		param.put("keyword3", "CT3234324324324232");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(40024, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		param.put("keyword3", "DD3234324324324232");
//		param.put("keyword4", "999999");
//		param.put("errorMessage", "单个彩民中奖通知出错");
//		OperatorLogger.log(41023, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		param.put("keyword3", "CT3234324324324232");
//		param.put("errorMessage", "这是错误信息\n错误了error！！！");
//		OperatorLogger.log(41024, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		param.put("keyword3", "CT3234324324324232");
//		OperatorLogger.log(40025, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "1111111");
//		param.put("userName", "李冰");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		param.put("keyword3", "CT3234324324324232");
//		param.put("errorMessage", "兑奖完毕更新彩期信息错误");
//		OperatorLogger.log(41025, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "0");
//		param.put("userName", "Robot");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		param.put("keyword3", "大乐透详细信息");
//		param.put("keyword4", "dlt10876.html");
//		OperatorLogger.log(40030, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "0");
//		param.put("userName", "Robot");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		param.put("keyword3", "大乐透详细信息");
//		param.put("keyword4", "dlt10876.html");
//		param.put("errorMessage", "生成页面错误");
//		OperatorLogger.log(41030, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "0");
//		param.put("userName", "Robot");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		OperatorLogger.log(40040, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "0");
//		param.put("userName", "Robot");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "1000005");
//		param.put("keyword2", "10876");
//		param.put("errorMessage", "生成页面错误");
//		OperatorLogger.log(41040, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "0");
//		param.put("userName", "Robot");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2010-06-23 15:00:00");
//		param.put("keyword2", "2010-06-24 15:00:00");
//		OperatorLogger.log(40041, param);
//
//		param = new HashMap<String, String>();
//		param.put("userId", "0");
//		param.put("userName", "Robot");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "2010-06-23 15:00:00");
//		param.put("keyword2", "2010-06-24 15:00:00");
//		param.put("errorMessage", "统计错误");
//		OperatorLogger.log(41041, param);

//		param = new HashMap<String, String>();
//		param.put("userId", "0");
//		param.put("userName", "Robot");
//		param.put("userKey", LibingUtils.getLocalIPAddress());
//		param.put("keyword1", "AAAAA");
//		param.put("keyword2", "BBBBB");
//		param.put("keyword3", "CCCCC");
//		param.put("keyword4", "DDDDD");
//		param.put("message", "我就是要记录，怎么了啊哈得十分；理解方式大法倒萨发撒的发上的发。");
//		param.put("errorMessage", "统计错误");
//		OperatorLogger.log(77777, param);

		
		param = new HashMap<String, String>();
		param.put("keyword1", "DIRECT");
		OperatorLogger.log(OperatorLogger.DEBUG, OperatorLogger.IMPORTANT, OperatorLogger.OTHERTYPE, "直到萨拉方法打三分撒大法师的发生的法律；纠纷；了大事发生大法师的发生的", param);

		param = new HashMap<String, String>();
		param.put("userId", "999");
		param.put("keyword1", "DIRECT");
		param.put("keyword2", "DIRECT");
		param.put("keyword3", "DIRECT");
		OperatorLogger.log(OperatorLogger.DEBUG, OperatorLogger.IMPORTANT, OperatorLogger.OTHERTYPE, "直到萨拉方法打三分撒大法师的发生的法律；纠纷；了大事发生大法师的发生的", param);
	}
}
