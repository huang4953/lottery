/**
 * Title: EhandLog.java
 * @Package com.success.lottery.ehandserver
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-18 ����12:57:55
 * @version V1.0
 */
package com.success.lottery.ehand.ehandserver.bussiness;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.lbapserver.LbapLog;

/**
 * com.success.lottery.ehandserver
 * EhandLog.java
 * EhandLog
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-18 ����12:57:55
 * 
 */

public class EhandLog {
	
	private static final String LINK_SIGN = "#";
	private Log logger;
	private static Map<String, EhandLog> loggers = new Hashtable<String, EhandLog>();
	
	/**
	 * 
	 *Title: 
	 *Description: 
	 * @param type
	 */
	private EhandLog(String type){
		if ("EHAND".equals(type)){
            this.logger = LogFactory.getLog("com.success.lottery.ehandserver.ehandhttp");
        }else if("E_SENDTICKET".equals(type)){
        	this.logger = LogFactory.getLog("com.success.lottery.ehand.sendticket");
        }else if("E_SENDMSG".equals(type)){
        	this.logger = LogFactory.getLog("com.success.lottery.ehand.esendmsg");
        }else if("E_NOTICEMSG".equals(type)){
        	this.logger = LogFactory.getLog("com.success.lottery.ehand.enoticemsg");
        }else{
        	this.logger = LogFactory.getLog(EhandLog.class);
        }
	}
	
	/**
	 * 
	 * Title: getInstance<br>
	 * Description: <br>
	 *              <br>��ȡ��־ʵ��
	 * @param type
	 * @return
	 */
	public static EhandLog getInstance(String type) {
		EhandLog log = loggers.get(type);
		if(log == null){
			log = new EhandLog(type);
			loggers.put(type, log);
		}
		return log;
	}
	
	/**
	 * 
	 * Title: logInfo<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param reqOrRes ���������Ӧ��0��������1������Ӧ
	 * @param errOrOk ��ȷ�����0 ��ȷ��1 ����
	 * @param command ��Ϣ������
	 * @param clientId �ͻ��˱��
	 * @param messageId ��Ϣid
	 * @param result ���
	 */
	public void logInfo(String reqOrRes,String errOrOk,String command,String userId,String key,String errCode,String errMsg,String mess){
		try {
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append(reqOrRes).append(LINK_SIGN).append(errOrOk).append(LINK_SIGN);
			sb.append(command).append(LINK_SIGN).append(userId).append(LINK_SIGN);
			sb.append(key).append(LINK_SIGN);
			sb.append(errCode).append(LINK_SIGN).append(errMsg).append(LINK_SIGN).append(mess==null?"":mess);
			this.logger.info(sb.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: sendLog<br>
	 * Description: <br>
	 *              <br>���͹�ȥ��Ʊ��¼
	 * @param lotteryId
	 * @param betTerm
	 * @param ticketSequence
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betCount
	 * @param betAmount
	 * @param betCode
	 */
	public void sendTicketLog(int lotteryId,String betTerm,String ticketSequence,int playType,int betType,int betMultiple,int betCount,long betAmount,String betCode){
		try{
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append("send").append(LINK_SIGN);
			sb.append(lotteryId).append(LINK_SIGN);
			sb.append(betTerm).append(LINK_SIGN);
			sb.append(ticketSequence).append(LINK_SIGN);
			sb.append(playType).append(LINK_SIGN);
			sb.append(betType).append(LINK_SIGN);
			sb.append(betMultiple).append(LINK_SIGN);
			sb.append(betCount).append(LINK_SIGN);
			sb.append(betAmount).append(LINK_SIGN);
			sb.append(betCode);
			this.logger.info(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: noticeTicketLog<br>
	 * Description: <br>
	 *              <br>�յ�Ʊ��֪ͨ��¼��־
	 * @param ticketSequence
	 * @param ticketId
	 * @param ticketStatus
	 */
	public void noticeTicketLog(String ticketSequence,String ticketId,String ticketStatus){
		try{
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append("notice").append(LINK_SIGN);
			sb.append(ticketSequence).append(LINK_SIGN);
			sb.append(ticketId).append(LINK_SIGN);
			sb.append(ticketStatus);
			this.logger.info(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: receiveTicketLog<br>
	 * Description: <br>
	 *              <br>��Ʊ���ص���Ϣ��¼
	 * @param errorCode
	 * @param ticketSequence
	 * @param opreateId
	 * @param accountValue
	 */
	public void receiveTicketLog(String errorCode,String ticketSequence,String opreateId,String accountValue){
		try{
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append("receive").append(LINK_SIGN);
			sb.append(errorCode).append(LINK_SIGN);
			sb.append(ticketSequence).append(LINK_SIGN);
			sb.append(opreateId).append(LINK_SIGN);
			sb.append(accountValue);
			this.logger.info(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: querySendTicketLog<br>
	 * Description: <br>
	 *              <br>��ѯ��Ʊ���������־
	 * @param ticketSequence
	 */
	public void querySendTicketLog(String ticketSequence){
		try{
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append("querySend").append(LINK_SIGN);
			sb.append(ticketSequence);
			this.logger.info(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: queryReceiveTicketLog<br>
	 * Description: <br>
	 *              <br>��ѯ��ƱƱ���������Ϣ��־
	 * @param errCode
	 * @param ticketSequence
	 * @param ticketId
	 * @param printStatus
	 */
	public void queryReceiveTicketLog(String errCode,String ticketSequence,String ticketId,String printStatus){
		try{
			StringBuffer sb=new StringBuffer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			sb.append(systemTime).append(LINK_SIGN);
			sb.append("queryReceive").append(LINK_SIGN);
			sb.append(errCode).append(LINK_SIGN);
			sb.append(ticketSequence).append(LINK_SIGN);
			sb.append(ticketId).append(LINK_SIGN);
			sb.append(printStatus);
			this.logger.info(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: eSendMsgLog<br>
	 * Description: <br>
	 *              <br>��¼���͵������ȵ���Ϣ�Լ���Ӧ����Ϣ
	 * @param sendOrReceive��0�����ͣ�1����Ӧ
	 * @param sendMsg
	 */
	public void eSendMsgLog(String sendOrReceive,String sendMsg){
		StringBuffer sb=new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String systemTime = sdf.format(new Date()).toString();
		sb.append(systemTime).append(LINK_SIGN);
		sb.append("0".equals(sendOrReceive)?"send":"reponse").append(LINK_SIGN);
		sb.append(StringUtils.isEmpty(sendMsg)?sendMsg:sendMsg.replaceAll("\\n", ""));
		this.logger.info(sb.toString());
	}
	/**
	 * 
	 * Title: eNoticeMsgLog<br>
	 * Description: <br>
	 *              <br>��¼�յ������ȵ�֪ͨ
	 * @param flag
	 * @param noticedMsg
	 */
	public void eNoticeMsgLog(String flag,String noticedMsg){
		StringBuffer sb=new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String systemTime = sdf.format(new Date()).toString();
		sb.append(systemTime).append(LINK_SIGN);
		sb.append("0".equals(flag)?"receive":"reponse").append(LINK_SIGN);
		sb.append(StringUtils.isEmpty(noticedMsg)?noticedMsg:noticedMsg.replaceAll("\\n", ""));
		this.logger.info(sb.toString());
	}
	
}
