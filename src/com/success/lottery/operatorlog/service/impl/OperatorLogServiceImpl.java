package com.success.lottery.operatorlog.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.oro.util.Cache;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.dao.OperatorLogDaoImpl;
import com.success.lottery.operatorlog.domain.OperationLog;
import com.success.lottery.operatorlog.service.interf.OperatorLogService;
import com.success.lottery.sms.push.service.SmsPushDataService;

public class OperatorLogServiceImpl implements OperatorLogService{
	private OperatorLogDaoImpl operatorLogDaoImpl;
	@Override
	public void addLogRecord(int level, int type, int rank, long userId,
			String name, String userKey, String keyword1, String keyword2,
			String keyword3, String keyword4, String message)
			throws LotteryException {
		OperationLog operationLog = new OperationLog();
		if(0>=level){
			level = 20000;
		}
		if(0>=rank){
			rank = 10000;
		}
		if(StringUtils.isBlank(message)){
			throw  new LotteryException(EXCEPTION_INSERT_MESSAGE,EXCEPTION_INSERT_MESSAGE_STR);
		}
		operationLog.setLevel(level);
		operationLog.setType(type);
		operationLog.setRank(rank);
		operationLog.setUserid(userId);
		operationLog.setName(name);
		operationLog.setUserkey(userKey);
		operationLog.setKeyword1(keyword1);
		operationLog.setKeyword2(keyword2);
		operationLog.setKeyword3(keyword3);
		operationLog.setKeyword4(keyword4);
		operationLog.setOperattime(new Date());
		operationLog.setMessage(message);
		try {
			this.getOperatorLogDaoImpl().insertLogRecord(operationLog);
		} catch (Exception e) {
			throw new LotteryException(EXCEPTION_INSERT,EXCEPTION_INSERT_STR+e.toString());
		}
		
	}
	public OperatorLogDaoImpl getOperatorLogDaoImpl() {
		return operatorLogDaoImpl;
	}
	public void setOperatorLogDaoImpl(OperatorLogDaoImpl operatorLogDaoImpl) {
		this.operatorLogDaoImpl = operatorLogDaoImpl;
	}
	@Override
	public int getAllOperationLogCount(Integer level, Integer type, Integer rank,
			Long userId, String name, String userKey, String keyword1,
			String keyword2, String keyword3, String keyword4, String message,
			Date beginTime, Date endTime)
			throws LotteryException {
		int pageTotal = 0;
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
			pageTotal = this.getOperatorLogDaoImpl().queryAllOperationLogCount(level,  type,  rank,
			userId,  name,  userKey,  keyword1,
			 keyword2,  keyword3,  keyword4,  message,beginTimeCovent,endTimeCovent);
		}catch (Exception e) {
			throw new LotteryException(EXCEPTION_QUERY_TOTAL,XCEPTION_QUERY_TOTAL_STR+e.toString());
		}
		return pageTotal;
	}
	@Override
	public List<OperationLog> getAllOperationLogList(Integer level, Integer type, Integer rank,
			Long userId, String name, String userKey,
			String keyword1, String keyword2, String keyword3, String keyword4,
			String message, Date beginTime, Date endTime,
			int page, int perPageNumber) throws LotteryException {
		List<OperationLog> operationLogList  = null;
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
		int startNumber = perPageNumber * (page - 1);
		int endNumber = page * perPageNumber;
		operationLogList = this.getOperatorLogDaoImpl().queryAllOperationLogList(level,  type,  rank,
				 userId,  name,  userKey,  keyword1,
				 keyword2,  keyword3,  keyword4,  message, beginTimeCovent,endTimeCovent,startNumber,  endNumber);
		}catch (Exception e) {
			throw new LotteryException(EXCEPTION_QUERY_LIST,XCEPTION_QUERY_LIST_STR+e.toString());
		}
		return operationLogList;
	}
	
}
