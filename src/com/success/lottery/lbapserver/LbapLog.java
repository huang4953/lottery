/**
 * Title: LbapLog.java
 * @Package com.success.lottery.lbapserver
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-1 上午09:42:39
 * @version V1.0
 */
package com.success.lottery.lbapserver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.lbapserver
 * LbapLog.java
 * LbapLog
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-1 上午09:42:39
 * 
 */

public class LbapLog {
	
	private static final String LINK_SIGN = "#";
	private Log logger;
	private static Map<String, LbapLog> loggers = new Hashtable<String, LbapLog>();
	
	private LbapLog(String type){
		if ("LBAP".equals(type)){
            this.logger = LogFactory.getLog("com.success.lottery.lbapserver.lbaphttp");
        }else{
        	this.logger = LogFactory.getLog(LbapLog.class);
        }
	}
	
	public static LbapLog getInstance(String type) {
		LbapLog log = loggers.get(type);
		if(log == null){
			log = new LbapLog(type);
			loggers.put(type, log);
		}
		return log;
	}
	
	/**
	 * 
	 * Title: logInfo<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param command 消息命令码
	 * @param clientId 客户端编号
	 * @param messageId 消息id
	 * @param result 结果
	 */
	public void logInfo(String command,String clientId,String messageId,String result,String mess){
		try {
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append(command).append(LINK_SIGN).append(clientId).append(LINK_SIGN);
			sb.append(messageId).append(LINK_SIGN);
			sb.append(result).append(LINK_SIGN).append(mess==null?"":mess);
			this.logger.info(sb.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void logInfo(String command,String clientId,String messageId,String result){
		try {
			logInfo(command,clientId,messageId,result,"");
		} catch (RuntimeException e) {
			e.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Title: main<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO 自动生成方法存根

	}

}
