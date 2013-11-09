package com.success.lottery.sms.push.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.MT;
import com.success.lottery.sms.SMSDispatch;
import com.success.lottery.sms.SMSLog;
import com.success.lottery.sms.push.dao.SmsPushDataDao;
import com.success.lottery.sms.push.model.SmsPushData;
import com.success.lottery.sms.push.model.SmsPushTask;
import com.success.lottery.sms.push.service.SmsPushDataService;
import com.success.utils.AutoProperties;

public class SmsPushDataServiceImpl implements SmsPushDataService{
	private Log logger = LogFactory.getLog(SmsPushDataServiceImpl.class.getName());
	private String resource = "com.success.lottery.sms.sms";
	private SmsPushDataDao smsPushDataDao;
	public SmsPushDataDao getSmsPushDataDao() {
		return smsPushDataDao;
	}
	public void setSmsPushDataDao(SmsPushDataDao smsPushDataDao) {
		this.smsPushDataDao = smsPushDataDao;
	}
	@Override
	public SmsPushData getSmsPushData(Long id)
			throws LotteryException {
		SmsPushData smsPushData = null;
		try {
			smsPushData = this.getSmsPushDataDao().selectSmsPushData(id);
		} catch (Exception e) {
			throw new LotteryException(SmsPushDataService.NOTSMSPUSHDATAPER,
					SmsPushDataService.NOTSMSPUSHDATAPER_STR+e.toString());
		}
		return smsPushData;
	}
	@Override
	public List<SmsPushData> getSmsPushDataList(String taskId, long sendStatus,
			String mobilePhone, String serviseId, Date beginTime,
			Date endTime,int pageIndex, int perPageNumber) throws LotteryException {
		List list = null;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			int startNumber = perPageNumber * (pageIndex - 1);
			int endNumber = pageIndex * perPageNumber;
			list = this.getSmsPushDataDao().selectSmsPushDataList(taskId, sendStatus, mobilePhone,
					serviseId, beginTimeCovent, endTimeCovent,startNumber,endNumber);
		} catch (Exception e) {
			throw new LotteryException(SmsPushDataService.NOTSMSPUSHDATA,SmsPushDataService.NOTSMSPUSHDATA_STR+e.toString());
		}
		return list;
	}
	@Override
	public int getSmsPushDataListCount(String taskId, long sendStatus,
			String mobilePhone, String serviseId, Date beginTime,
			Date endTime) throws LotteryException {
		int pageCount = 0;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			pageCount = this.getSmsPushDataDao().selectSmsPushDataListCount(taskId, sendStatus, 
					mobilePhone, serviseId, beginTimeCovent, endTimeCovent);
		} catch (Exception e) {
			throw new LotteryException(SmsPushDataService.NOTSMSPUSHDATACOUNT,SmsPushDataService.NOTSMSPUSHDATACOUNT_STR+e.toString());
		}
		return pageCount;
	}

	@Override
	public List<SmsPushData> getSmsPushTaskData(String taskId, int pageIndex,
			int perPageNumber) throws LotteryException {
		List list = null;
		try {
			int startNumber = perPageNumber * (pageIndex - 1);
			int endNumber = pageIndex * perPageNumber;
			list = this.getSmsPushDataDao().selectSmsPushTaskData(taskId,startNumber,endNumber);
		} catch (Exception e) {
			throw new LotteryException(SmsPushDataService.NOTSMSPUSHDATA,SmsPushDataService.NOTSMSPUSHDATA_STR+e.toString());
		}
		return list;
	}

	@Override
	public int getSmsPushTaskDataCount(String taskId) throws LotteryException {
		int pageCount = 0;
		try {
			pageCount = this.getSmsPushDataDao().selectSmsPushTaskDataCount(taskId);
		} catch (Exception e) {
			throw new LotteryException(SmsPushDataService.NOTSMSPUSHDATACOUNT,SmsPushDataService.NOTSMSPUSHDATACOUNT_STR+e.toString());
		}
		return pageCount;
	}

	@Override
	public int checkTimeoutData() throws LotteryException {
		try{
			return this.getSmsPushDataDao().updateTimeoutData();
		}catch(Exception e){
			throw new LotteryException(SmsPushDataService.CHECKTIMEOUTEXCEPTION, SmsPushDataService.CHECKTIMEOUTEXCEPTION_STR + e.toString());
		}
	}

	@Override
	public String sendPushData(String taskId, int number) throws LotteryException {
		try{
			List<SmsPushData> pushData = this.getSmsPushDataDao().getSendDataByTaskIdForUpdate(taskId, number);
			if(pushData == null || pushData.size() == 0){
				logger.debug("sendPushData get the task(" + taskId + ")'s push data is null, send data is finished.");
				return null;
			}
			int size = pushData.size();
			logger.debug("sendPushData send the task(" + taskId + ")'s push data, get " + size + " data to send.");
			int succ = 0, failed = 0, count = 1;
			for(SmsPushData data : pushData){
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") " + count++ + " of " + size + " is:");
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s taskId		= " + data.getTaskId());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s sendStatus	= " + data.getSendStatus());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s sendSummary = " + data.getSendSummary());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s serviceId	= " + data.getServiceId());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s mobilePhone = " + data.getMobilePhone());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s content		= " + data.getContent());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s sendCount	= " + data.getSendCount());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s beginTime	= " + data.getBeginTime());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s endTime		= " + data.getEndTime());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s sentTime	= " + data.getSentTime());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s remark		= " + data.getRemark());
				logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s reserve		= " + data.getReserve());
				if(data.getSendStatus() == 0 || data.getSendStatus() == 2){
					String clientName = AutoProperties.getString(data.getServiceId() + ".clientName", AutoProperties.getString("smspush.clientName", "SMSClient1", resource), resource);
					String spNum = AutoProperties.getString(data.getServiceId() + ".spNum", AutoProperties.getString("smspush.spNum", "10622001", resource), resource);
					String spNumExt = AutoProperties.getString(data.getServiceId() + ".spNumExt", AutoProperties.getString("smspush.spNumExt", "", resource), resource);
					String channelId = AutoProperties.getString(data.getServiceId() + ".channelId", AutoProperties.getString("smspush.channelId", "3100001", resource), resource);
					int type = AutoProperties.getInt(data.getServiceId() + ".type", 1, resource);
					MT mt = new MT(clientName, data.getMobilePhone(), spNum, spNumExt, channelId, type, data.getContent());
					mt.setInTime(System.currentTimeMillis());
					mt.setMsgId("");
					mt.setLinkId("");
					mt.setProcessorName("SmsPushData");
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message is:");
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's clientName	   = " + mt.getClientName());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's msgId		   = " + mt.getMsgId());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's toPhone	   = " + mt.getToPhone());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's feePhone	   = " + mt.getFeePhone());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's spNum		   = " + mt.getSpNum());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's spNumExt	   = " + mt.getSpNumExt());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's channelId	   = " + mt.getChannelId());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's type		   = " + mt.getType());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's msgLength	   = " + mt.getMsgLength());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's msgContent	   = " + mt.getMsgContent());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's processorName = " + mt.getProcessorName());
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") to create MT message's inTime		   = " + mt.getInTime());

					String mtRs = SMSDispatch.dispatch(mt);
					mt.setOutTime(System.currentTimeMillis());
					mt.setErrMsg(mtRs);
					SMSLog.getInstance("MT").log(mt);
					if(mtRs == null){
						succ++;
						logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s MT message put into SMSClient send queue success.");
						this.getSmsPushDataDao().updatePushData(data.getId(), 1);
					}else{
						failed++;
						logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ")'s MT message put into SMSClient send queue failed:" + mtRs + ".");
						this.getSmsPushDataDao().updatePushData(data.getId(), 2);
					}
				}else if(data.getSendStatus() == 1){
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") is sent ok.");
					succ++;
				}else{
					logger.debug("sendPushData get the task(" + taskId + ")'s push data(" + data.getId() + ") is sent failed: " + data.getSendStatus() + ".");
					failed++;
				}
			}
			return pushData.size() + "-" + succ + "-" + failed;
		}catch(Exception e){
			logger.error("sendPushData send task(" + taskId + ")occur exception:" + e);
			throw new LotteryException(SmsPushDataService.SENDPUSHDATAEXCEPTION, SmsPushDataService.SENDPUSHDATAEXCEPTION_STR + e.toString());
		}
	}
}
