/**
 * SyncNotifySPSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unicom.vac.bossagent.soap;

import java.rmi.RemoteException;
import javax.servlet.http.HttpServletRequest;
import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.service.AccountService;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.MO;
import com.success.lottery.sms.SMSDispatch;
import com.success.lottery.sms.SMSLog;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.LibingUtils;
import com.unicom.vac.bossagent.soap.sync.req.OrderRelationUpdateNotifyRequest;
import com.unicom.vac.bossagent.soap.sync.req.SubInfo;
import com.unicom.vac.bossagent.soap.sync.rsp.OrderRelationUpdateNotifyResponse;

public class SyncNotifySPSoapBindingImpl implements com.unicom.vac.bossagent.soap.SyncNotifySPService{

	private String clientIp;
	private String seq;
	private String resource = "com.unicom.vac.vac";
	public OrderRelationUpdateNotifyResponse orderRelationUpdateNotify(OrderRelationUpdateNotifyRequest request) throws RemoteException{
		Log logger = LogFactory.getLog("com.unicom.vac.bossagent.soap.SyncNotifySPSoapBindingImpl");
		
		MessageContext context = MessageContext.getCurrentContext();
		HttpServletRequest servletRequest = (HttpServletRequest)context.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		clientIp = servletRequest.getRemoteAddr();
		seq = request.getRecordSequenceId();
		int resultCode = 0;

		SubInfo notify = new SubInfo();
		int userIdType = request.getUserIdType();
		int updateType = request.getUpdateType();
		
		notify.setUserIdType(userIdType);
		notify.setUpdateType(updateType);
		
		notify.setUserId(request.getUserId());
		notify.setServiceType(request.getServiceType());
		notify.setSpId(request.getSpId());
		notify.setProductId(request.getProductId());
		notify.setUpdateTime(request.getUpdateTime());
		notify.setUpdateDesc(request.getUpdateDesc());
		notify.setLinkId(request.getLinkId());
		notify.setContent(request.getContent());
		notify.setEffectiveDate(request.getEffectiveDate());
		notify.setExpireDate(request.getExpireDate());
		notify.setTime_stamp(request.getTime_stamp());
		notify.setEncodeStr(request.getEncodeStr());
		
		
		String userId = notify.getUserId();
		String serviceType = notify.getServiceType();
		String spId = notify.getSpId();
		String productId = notify.getProductId();
		String updateTime = notify.getUpdateTime();//YYYYMMDDHHMISS
		String updateDesc = notify.getUpdateDesc();
		String linkId = notify.getLinkId();
		String content = notify.getContent();
		String effectiveDate = notify.getEffectiveDate();
		String expireDate = notify.getExpireDate();
		String timeStamp = notify.getTime_stamp();
		String encodeStr = notify.getEncodeStr();
		logger.debug("recived a soap message, updateType=" + notify.getUpdateType() + ", userIdType=" + userIdType);

		logger.debug("userIdType=" + userIdType);
		logger.debug("userId=" + userId);
		logger.debug("serviceType=" + serviceType);
		logger.debug("spId=" + spId);
		logger.debug("productId=" + productId);
		logger.debug("updateType=" + updateType);
		logger.debug("updateTime=" + updateTime);
		logger.debug("updateDesc=" + updateDesc);
		logger.debug("linkId=" + linkId);
		logger.debug("content=" + content);
		logger.debug("effectiveDate=" + effectiveDate);
		logger.debug("expireDate=" + expireDate);
		logger.debug("timeStamp=" + timeStamp);
		logger.debug("encodeStr=" + encodeStr);	

		String result = null;
		switch(updateType){
			case 1:
				result = subscribe(notify);
				break;
			case 2:
				result = unSubscribe(notify);
				break;
			case 3:
				result = iod(notify, seq);
				break;
			case 4:
				result = relationChanges(notify);
				break;
			case 5:
				result = transferredNumber(notify);
				break;
			default:
				result = "ERROR:UnknowUpdateType";
		}
		if(result == null){
			resultCode = 1;
		}
		log(notify, seq, result);
		return new OrderRelationUpdateNotifyResponse(seq, resultCode);
	}
	
	private String subscribe(SubInfo notify){
		int userIdType = notify.getUserIdType();
		String userId = notify.getUserId();
		if(userIdType == 2){
			userId = "ucqh-" + userId;
		}else if(userId.startsWith("86")){
			userId = userId.substring(2);
		}
		String content = notify.getContent();
		if(StringUtils.isBlank(content)){
			return "ERROR:moContentIsNull";
		}
		if(content.trim().matches("^[a|A](([\\s|,|;])*(.|\n)*)")){
			//订购手机彩票业务，注册绑定手机
//			try{
//				AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
//				accountService.registerBySMS(userId);
//			}catch(LotteryException e){
//				e.printStackTrace();
//				if(e.getType() != AccountService.USERISEXIST){
//					return e.getMessage();
//				}
//			}

			MO sms = new MO();
			try{
				sms.setSequence(Long.parseLong(seq));			
			}catch(Exception e){
				sms.setSequence(-1L);
			}
			sms.setFromIp(clientIp);
			sms.setFromPhone(userId);
			sms.setSpNum("10622001");
			sms.setLinkId(notify.getLinkId());
			sms.setMsgContent(notify.getContent());
			sms.setMsgLength(notify.getContent().length());

			sms.setClientName(AutoProperties.getString("SMSClientName", "SMSClient1", resource));
			sms.addRemark("vac", "ucqh");
			sms.setInTime(System.currentTimeMillis());	
			String rs = SMSDispatch.dispatch(sms);
			if(rs != null){
				sms.setErrMsg(rs);
			}
			sms.setOutTime(System.currentTimeMillis());
			SMSLog.getInstance("MO").log(sms);
			if(rs != null){
				return "ERROR:SystemBusy";
			}
		}else if(content.trim().matches("^[b|B](([\\s|,|;])*(.|\n)*)")){
			try{
				AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
				accountService.registerBySMS(userId, 2);
			}catch(LotteryException e){
				e.printStackTrace();
				if(e.getType() != AccountService.USERISEXIST){
					return e.getMessage();
				}
			}
		}else{
			return "ERROR:notMatchCMD";
		}
		return null;
	}
	
	private String unSubscribe(SubInfo notify){
		int userIdType = notify.getUserIdType();
		String userId = notify.getUserId();
		if(userIdType == 2){
			userId = "ucqh-" + userId;
		}else if(userId.startsWith("86")){
			userId = userId.substring(2);
		}
		String content = notify.getContent();
		if(StringUtils.isBlank(content)){
			return "ERROR:moContentIsNull";
		}
		if(content.trim().matches("^([q|Q][x|X][a|A])(([\\s|,|;])*(.|\n)*)")){
			//退订手机彩票业务，注册绑定手机
			try{
				AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
				accountService.unregisterBySMS(userId);
			}catch(LotteryException e){
				e.printStackTrace();
				if(e.getType() != AccountService.NOTFOUNDUSER && e.getType() != AccountService.USERISUNREGISTED){
					return e.getMessage();
				}
			}
		}else if(content.trim().matches("^([q|Q][x|X][b|B])(([\\s|,|;])*(.|\n)*)")){
			//退订彩票手机报业务
			try{
				AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
				accountService.unregisterBySMS(userId, 2);
			}catch(LotteryException e){
				e.printStackTrace();
				if(e.getType() != AccountService.NOTFOUNDUSER && e.getType() != AccountService.USERISUNREGISTED){
					return e.getMessage();
				}
			}
		}else{
			return "ERROR:notMatchCMD";
		}
		return null;
	}
	
	private String iod(SubInfo notify, String seq){
//		int userIdType = notify.getUserIdType();
//		String userId = notify.getUserId();
//		if(userId.startsWith("86")){
//			userId = userId.substring(2);
//		}
//		if(userIdType == 2){
//			userId = "ucqh-" + userId;
//		}
//		String content = notify.getContent();
//		if(StringUtils.isBlank(content)){
//			return "ERROR:moContentIsNull";
//		}
//		MO sms = new MO();
//		try{
//			sms.setSequence(Long.parseLong(seq));			
//		}catch(Exception e){
//			sms.setSequence(-1L);
//		}
//		sms.setFromIp(clientIp);
//		sms.setFromPhone(userId);
//		sms.setSpNum("10622001");
//		sms.setLinkId(notify.getLinkId());
//		sms.setMsgContent(notify.getContent());
//		sms.setMsgLength(notify.getContent().length());
//		sms.setClientName("ucqhvac");
//		sms.setInTime(System.currentTimeMillis());	
//		String rs = SMSDispatch.dispatch(sms);
//		if(rs != null){
//			sms.setErrMsg(rs);
//		}
//		sms.setOutTime(System.currentTimeMillis());
//		SMSLog.getInstance("MO").log(sms);
//		if(rs != null){
//			return "ERROR:SystemBusy";
//		}
		return null;
	}
	
	private String relationChanges(SubInfo notify){
		return null;
	}
	
	private String transferredNumber(SubInfo notify){
		return null;
	}
	private void log(SubInfo info, String seq, String result){
		Log logger = LogFactory.getLog("com.success.lottery.sms.VAC");
		String dlm = AutoProperties.getString("logDelimiter", ",", "com.success.lottery.sms.sms");
		StringBuffer sb=new StringBuffer();
		sb.append(LibingUtils.getFormatTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")).append(dlm);
		sb.append(clientIp).append(dlm);
		sb.append("ucqhvac").append(dlm);
		sb.append(seq).append(dlm);
		sb.append(info.getUserId()).append(dlm);
		sb.append(info.getServiceType()).append(dlm);
		sb.append(info.getSpId()).append(dlm);
		sb.append(info.getProductId()).append(dlm);
		sb.append(info.getUpdateType()).append(dlm);
		sb.append(info.getUpdateTime()).append(dlm).append(info.getEffectiveDate()).append(dlm).append(info.getExpireDate()).append(dlm).append(info.getTime_stamp()).append(dlm);
		sb.append(info.getLinkId()).append(dlm).append(info.getUpdateDesc()).append(dlm).append(info.getContent()).append(dlm);
		if(result == null){
			sb.append("(OK)");
		} else {
			sb.append(result);
		}
		logger.info(sb.toString());
	}
}
