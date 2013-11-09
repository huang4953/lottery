package com.success.lottery.sms;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.utils.AutoProperties;
import com.success.utils.ssmp.SSMPQueue;
/**
 * MOQue: clientName + MOQue MTQue: clientName + MTQue ORDERQue: clientName +
 * ORDERQue QUERYQue: clientName + QUERYQue ORDERRespQue: clientName +
 * ORDERRespQue QUERYRespQue: clientName + QUERYRespQue
 * 
 * @author bing.li
 */
public class SMSDispatch{

	private static Log	logger	= LogFactory.getLog(SMSDispatch.class.getName());

	/**
	 * 将MO对象放入队列，放入哪个队列取决于mo.clientName，如果mo.clientName为null或""，则放入名称为MOQue的队列。
	 * 
	 * @param mo
	 * @return
	 */
	public static String dispatch(MO mo){
		String rs = null;
		try{
			String queName = null;
			if(StringUtils.isBlank(mo.getClientName())){
				queName = "MOQue";
			}else{
				queName = mo.getClientName() + "MOQue";
			}
			SSMPQueue<MO> moQue = SSMPQueue.getQueue(queName);
			if(!moQue.offer(mo)){
				rs = mo.getClientName() + "MOQue full";
			}
		}catch(Exception e){
			rs = e.toString();
			logger.error("dispatch MO exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return rs;
	}

	/**
	 * 将MT对象放入队列，放入哪个队列取决于mt.clientName，如果mt.clientName为null或""，则放入名称为MTQue的队列。
	 * 
	 * @param mt
	 * @return
	 */
	public static String dispatch(MT mt){
		String rs = null;
		try{
			String queName = null;
			if(StringUtils.isBlank(mt.getClientName())){
				queName = "MTQue";
			}else{
				queName = mt.getClientName() + "MTQue";
			}
			SSMPQueue<MT> mtQue = SSMPQueue.getQueue(queName);
			mtQue.put(mt);
		}catch(Exception e){
			rs = e.toString();
			logger.error("dispatch MT exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return rs;
	}

	public static String dispatch(Order order){
		String rs = null;
		if(!order.isResp()){
			try{
				String queName = null;
				if(StringUtils.isBlank(order.getClientName())){
					queName = "ORDERQue";
				}else{
					queName = order.getClientName() + "ORDERQue";
				}
				SSMPQueue<Order> orderQue = SSMPQueue.getQueue(queName);
				orderQue.offer(order);
			}catch(Exception e){
				rs = e.toString();
				logger.error("dispatch ORDER exception:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}
		}else{
			try{
				String queName = null;
				if(StringUtils.isBlank(order.getClientName())){
					queName = "ORDERRespQue";
				}else{
					queName = order.getClientName() + "ORDERRespQue";
				}
				SSMPQueue<Order> orderQue = SSMPQueue.getQueue(queName);
				orderQue.put(order);
			}catch(Exception e){
				rs = e.toString();
				logger.error("dispatch ORDER exception:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}
		}
		return rs;
	}

	public static String dispatch(Query query){
		String rs = null;
		if(!query.isResp()){
			try{
				String queName = null;
				if(StringUtils.isBlank(query.getClientName())){
					queName = "QUERYQue";
				}else{
					queName = query.getClientName() + "QUERYQue";
				}
				SSMPQueue<Query> queryQue = SSMPQueue.getQueue(queName);
				queryQue.offer(query);
			}catch(Exception e){
				rs = e.toString();
				logger.error("dispatch QUERY exception:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}
		}else{
			try{
				String queName = null;
				if(StringUtils.isBlank(query.getClientName())){
					queName = "QUERYRespQue";
				}else{
					queName = query.getClientName() + "QUERYRespQue";
				}
				SSMPQueue<Query> queryQue = SSMPQueue.getQueue(queName);
				queryQue.put(query);
			}catch(Exception e){
				rs = e.toString();
				logger.error("dispatch QUERY exception:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
			}
		}
		return rs;
	}

	public static MO getMO(String clientName){
		String queName = "MOQue";
		if(!StringUtils.isBlank(clientName)){
			queName = clientName + queName;
		}
		try{
			SSMPQueue<MO> moQue = SSMPQueue.getQueue(queName);
			return moQue.poll();
		}catch(Exception e){
			logger.error("get MO from " + queName + " exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	public static MT getMT(String clientName){
		String queName = "MTQue";
		if(!StringUtils.isBlank(clientName)){
			queName = clientName + queName;
		}
		try{
			SSMPQueue<MT> mtQue = SSMPQueue.getQueue(queName);
			return mtQue.poll();
		}catch(Exception e){
			logger.error("get MT from " + queName + " exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	public static Order getOrder(String clientName){
		String queName = "ORDERQue";
		if(!StringUtils.isBlank(clientName)){
			queName = clientName + queName;
		}
		try{
			SSMPQueue<Order> orderQue = SSMPQueue.getQueue(queName);
			return orderQue.take();
		}catch(InterruptedException e){
			logger.error("get ORDER from " + queName + " exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	public static Query getQuery(String clientName){
		String queName = "QUERYQue";
		if(!StringUtils.isBlank(clientName)){
			queName = clientName + queName;
		}
		try{
			SSMPQueue<Query> queryQue = SSMPQueue.getQueue(queName);
			return queryQue.take();
		}catch(InterruptedException e){
			logger.error("get QUERY from " + queName + " exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	public static Order getOrderResp(String clientName){
		String queName = "ORDERRespQue";
		if(!StringUtils.isBlank(clientName)){
			queName = clientName + queName;
		}
		try{
			SSMPQueue<Order> orderRespQue = SSMPQueue.getQueue(queName);
			return orderRespQue.poll();
		}catch(Exception e){
			logger.error("get ORDERResp from " + queName + " exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	public static Query getQueryResp(String clientName){
		String queName = "QUERYRespQue";
		if(!StringUtils.isBlank(clientName)){
			queName = clientName + queName;
		}
		try{
			SSMPQueue<Query> queryRespQue = SSMPQueue.getQueue(queName);
			return queryRespQue.poll();
		}catch(Exception e){
			logger.error("get QUERYResp from " + queName + " exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			return null;
		}
	}

	public static void sendPushSMS(String phone, String msg) throws SMSException{
		sendPushSMS(null, phone, msg);
	}
	
	public static void sendPushSMS(String clientName, String phone, String msg) throws SMSException{
		sendPushSMS(clientName, phone, msg, "smspush");
	}

	public static void sendPushSMS(String clientName, String phone, String msg, String serviceId) throws SMSException{
		String resource = "com.success.lottery.sms.sms";
		if(StringUtils.isBlank(clientName)){
			clientName = AutoProperties.getString(serviceId + ".clientName", "", resource);
		}
		String spNum = AutoProperties.getString(serviceId + ".spNum", "10662001", resource);
		String spNumExt = AutoProperties.getString(serviceId + "smspush.spNumExt", "", resource);
		String channelId = AutoProperties.getString(serviceId + "smspush.channelId", "99", resource);
		String content = msg;
		if(StringUtils.isBlank(msg)){
			throw new SMSException("sms msg is null");
		}else{
			try{
				content = new String(msg.trim().getBytes(), "GBK");
			}catch(UnsupportedEncodingException e){
				logger.warn("sendSMS encoding exception:" + e.toString());
				if(logger.isDebugEnabled()){
					e.printStackTrace();
				}
				throw new SMSException("sms msg encoding error, must use GBK encoding");
			}
		}
		int type = 1;
		if(content.indexOf("$$") > -1){
			type = 11;
		}else{
			type = 1;
		}
		MT sms = new MT(clientName, phone, spNum, spNumExt, channelId, type, content);
		String rs = null;
		if((rs = SMSDispatch.dispatch(sms)) != null){
			throw new SMSException("send sms error: " + rs);
		}
	}

	public static void main(String[] args){
	}
}
