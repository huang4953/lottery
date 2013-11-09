package com.success.lottery.sms;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.utils.AutoProperties;
import com.success.utils.LibingUtils;

public class SMSLog{

	private Log logger;
	private String dlm = AutoProperties.getString("logDelimiter", ",", "com.success.lottery.sms.sms");
	private static Map<String, SMSLog> loggers = new Hashtable<String, SMSLog>();
	
	public static SMSLog getInstance(String type) {
		synchronized(type){
			SMSLog log = loggers.get(type);
			if(log == null){
				log = new SMSLog(type);
				loggers.put(type, log);
			}
			return log;
		}
	}
	
	private SMSLog(String TYPE){
        if ("MT".equals(TYPE))
            logger = LogFactory.getLog("com.success.lottery.sms.MT");
        else if ("MO".equals(TYPE))
        	logger = LogFactory.getLog("com.success.lottery.sms.MO");
        else if ("MR".equals(TYPE))
        	logger = LogFactory.getLog("com.success.lottery.sms.ORDER");
        else if ("FW".equals(TYPE))
        	logger = LogFactory.getLog("com.success.lottery.sms.QUERY");
        else if ("ERR".equals(TYPE))
        	logger = LogFactory.getLog("com.success.lottery.sms.ORDERRESP");
        else if ("ORG".equals(TYPE))
        	logger = LogFactory.getLog("com.success.lottery.sms.QUERYRESP");
        else
        	logger = LogFactory.getLog("com.success.lottery.sms.MO");
	}
	
	/**
	 * SMS Application MO Log.
	 * Format: inTime,outTime,fromIp|ClientName,ClientName|ProcessorName,sequence,msgId,fromPhone,spNum,spNumExt,linkId,remark,msgContent,result
	 * ͬһ��MO������������¼��һ��ΪSMSClient��¼��һ����SMSProcessor��¼������result�ֱ�ΪSMSClient����������SMSProcessor������
	 * ��SMSClient��¼ʱ����¼fromIp�Լ�SMSClientName����SMSProcessor��¼ʱ����¼SMSClientName�Լ�SMSProcessorName
	 * ��SMSClient��¼ʱ��inTimeΪ�Ӷ���ƽ̨�õ���MO��ʱ�䣬outTimeΪ����MO���е�ʱ�䣻
	 * ��SMSProcessor��¼ʱ��inTimeΪ����MO���е�ʱ�䣬��SMSClient���õ�outTime��outTimeΪMO������ɵ�ʱ��
	 * @param mo
	 */
	public void log(MO mo){
		StringBuffer sb=new StringBuffer();
		sb.append(LibingUtils.getFormatTime(mo.getInTime(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		sb.append(LibingUtils.getFormatTime(mo.getOutTime(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		
		if(StringUtils.isBlank(mo.getProcessorName())){
			sb.append(mo.getFromIp()).append(dlm).append(mo.getClientName()).append(dlm);
		} else {
			sb.append(mo.getClientName()).append(dlm).append(mo.getProcessorName()).append(dlm);
		}
		
		sb.append(mo.getSequence()).append(dlm);
		sb.append(mo.getMsgId()).append(dlm).append(mo.getFromPhone()).append(dlm).append(mo.getSpNum()).append(dlm);
		sb.append(mo.getSpNumExt()).append(dlm).append(mo.getLinkId()).append(dlm);
		
		mo.trimSrcRemark();
		String remark = mo.getRemark();
		remark = (remark == null ? "" : remark.trim().replaceAll(",", "<2C>"));
	
		sb.append(remark).append(dlm);
		
		String msg = mo.getMsgContent();
		msg = (msg == null ? "" : msg.trim().replaceAll(",", "<2C>"));
		sb.append(msg).append(dlm);
		
		if(StringUtils.isBlank(mo.getErrMsg())){
			sb.append("(OK)");
		}else{
			sb.append(mo.getErrMsg().trim().replaceAll(",", "<2C>"));
		}
		logger.info(sb.toString());
	}

	/**
	 * SMS Application MT Log.
	 * Format: inTime,outTime,toIp|ClientName,ClientName|ProcessorName,sequence,msgId,toPhone,feePhone,spNum,spNumExt,channelId,linkId,remark,msgContent,result
	 * ͬһ��MT������������¼��һ��ΪSMSClient��¼��һ����SMSProcessor��¼������result�ֱ�ΪSMSClient����������SMSProcessor������
	 * ��SMSClient��¼ʱ����¼toIp�Լ�SMSClientName��inTimeΪ�������ʱ�䣬Ӧ����SMSProcessor��д��outTime��outTimeΪ�ύ������ƽ̨ʱ�䣬��SMSClient��д��
	 * ��SMSProcessor��¼ʱ����¼SMSClientName�Լ�SMSProcessorName��inTimeΪ�յ�MO��ʱ�䣬�����Push��Ϊ����MT��ʱ�䣬��SMSProcessorName��д��outTimeΪ�ύMT�����е�ʱ�䣬��SMSProcessor��д��
	 *  
	 * @param mt
	 */
	public void log(MT mt){
		StringBuffer sb=new StringBuffer();
		sb.append(LibingUtils.getFormatTime(mt.getInTime(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		sb.append(LibingUtils.getFormatTime(mt.getOutTime(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		if(StringUtils.isBlank(mt.getToIp())){
			sb.append(mt.getClientName()).append(dlm).append(mt.getProcessorName()).append(dlm);
		} else {
			sb.append(mt.getToIp()).append(dlm).append(mt.getClientName()).append(dlm);
		}
		
		sb.append(mt.getSequence()).append(dlm);
		sb.append(mt.getMsgId()).append(dlm).append(mt.getToPhone()).append(dlm).append(mt.getFeePhone()).append(dlm);
		sb.append(mt.getSpNum()).append(dlm).append(mt.getSpNumExt()).append(dlm).append(mt.getChannelId()).append(dlm);
		sb.append(mt.getLinkId()).append(dlm);
		
		mt.trimSrcRemark();
		String remark = mt.getRemark();
		remark = (remark == null ? "" : remark.trim().replaceAll(",", "<2C>"));
	
		sb.append(remark).append(dlm);
		
		String msg = mt.getMsgContent();
		msg = (msg == null ? "" : msg.trim().replaceAll(",", "<2C>"));
		sb.append(msg).append(dlm);
		
		if(StringUtils.isBlank(mt.getErrMsg())){
			sb.append("(OK)");
		}else{
			sb.append(mt.getErrMsg().trim().replaceAll(",", "<2C>"));
		}
		logger.info(sb.toString());
	}

	public void log(Order order, boolean isResp){
		
	}
	public void log(Query query, boolean isResp){
		
	}
	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException{
		MO mo = new MO();
		mo.setClientName("SMSClient1");
		mo.setFromIp("192.168.0.12:6789");
		mo.setMsgId("87274628482223");
		mo.setFromPhone("13761874366");
		mo.setSpNum("10662001");
		mo.setSpNumExt("333");
		mo.setLinkId("4444444444");
		mo.setMsgContent("�Ұ��������޹�Ŷ������");
		mo.setMsgLength("�Ұ��������޹�Ŷ������".getBytes("GBK").length);
		mo.setInTime(System.currentTimeMillis());
		mo.addRemark("aaa", "1111");
		mo.addRemark("bbb", "222");
		mo.addRemark("bbb", "dasfds");
		SMSLog.getInstance("MO").log(mo);

		mo.setErrMsg("dasf�������̷���,adsf����˵");
		mo.setProcessorName("MOProcess1");
		SMSLog.getInstance("MO").log(mo);
		
		MT mt = new MT(mo);
		mt.setChannelId("1000001");
		mt.setProcessorName("SMSProcessor1");
		mt.addRemark("fee", "1111");
		mt.addRemark("aaa", "222");
		mt.addRemark("ccc", "dasfds");
		mt.setInTime(mo.getInTime());
		mt.setOutTime(System.currentTimeMillis());
		mt.setMsgContent("רӪ��ͷ���ش�ʮ�ּ��ң��ǳ��÷���ˮ");
		mt.setErrMsg("");
		
		System.out.println(mt.toString());
		
		SMSLog.getInstance("MT").log(mt);

		mt.setToIp("192.168.0.12:7777");
		SMSLog.getInstance("MT").log(mt);
	}
}
