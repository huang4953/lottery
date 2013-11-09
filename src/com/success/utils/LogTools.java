package com.success.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogTools {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static boolean isDebugLevel(Logger logger){
		return Level.DEBUG.equals(getLoggerLevel(logger));
	}

	public static void setAppenderAsRoot(Logger logger){
		Enumeration appenders = Logger.getRootLogger().getAllAppenders();
		for ( ; appenders.hasMoreElements();){
			Appender appender = (Appender)appenders.nextElement();
			if(!logger.isAttached(appender)){
				logger.addAppender(appender);
			}
		}
	}

	public static boolean haveAppender(Logger logger) {
		Enumeration appenders = Logger.getRootLogger().getAllAppenders();
		if (appenders == null) {
			return false;
		} else {
			boolean flag = false;
			for ( ; appenders.hasMoreElements();){
				Appender appender = (Appender)appenders.nextElement();
				return true;
			}
			return flag;
		}
	}

	public static Level getLoggerLevel(Logger logger){
		if (logger.getLevel() == null){
			int pos = -1;
			if (((pos = logger.getName().lastIndexOf("@")) > 0) || ((pos = logger.getName().lastIndexOf(".")) > 0)){
				return getLoggerLevel(Logger.getLogger(logger.getName().substring(0, pos)));
			} else {
				return logger.getRootLogger().getLevel();
			}
		} else {
			return logger.getLevel();
		}
	}

	public static void logSystemErr(String msg) {
		logSystemErr(msg, 0);
	}

	public static void logSystemErr(String msg, int len) {
		String temp = "";
		for (int i = 0; i < (len - msg.getBytes().length); i++) {
			temp = temp + " ";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		StackTraceElement stacks[] = (new Throwable()).getStackTrace();
		for (StackTraceElement stack : stacks) {
			if (!"com.success.sms.log.Log".equals(stack.getClassName())) {
				sb.append(stack.getClassName()).append("][");
				sb.append(stack.getLineNumber());
				break;
			}
		}
		sb.append("]");
		sb.append("[" + format.format(new Date()) + "]");
		sb.append(" - ");
		msg = LibingUtils.replace(msg, " ", "\n");
		sb.append("[" + msg + temp + "]");
		System.err.println(sb.toString());
	}

	public static void logSystemOut(String msg) {
		logSystemOut(msg, 0);
	}

	public static void logSystemOut(String msg, int len) {
		if(msg == null) {
			msg = "null";
		}
		String temp = "";
		for (int i = 0; i < (len - msg.getBytes().length); i++) {
			temp = temp + " ";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		StackTraceElement stacks[] = (new Throwable()).getStackTrace();
		for (StackTraceElement stack : stacks) {
			if (!"com.success.sms.log.Log".equals(stack.getClassName())) {
				sb.append(stack.getClassName()).append("][");
				sb.append(stack.getLineNumber());
				break;
			}
		}
		sb.append("]");
		sb.append("[" + format.format(new Date()) + "]");
		sb.append(" - ");
		//msg = StringUtil.replace(msg, "<CR>", "\n");
		sb.append("[" + msg + temp + "]");
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args) {
		System.out.println(getLoggerLevel(Logger.getLogger("com.success.sms.mo.MODispatcher")));
		System.out.println(isDebugLevel(Logger.getLogger("com.success.sms.mo.MODispatcher@127.0.0.1:7777-43454")));
	}
}
