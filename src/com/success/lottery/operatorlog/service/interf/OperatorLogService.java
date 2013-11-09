/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.success.lottery.operatorlog.service.interf;

import java.util.Date;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.domain.OperationLog;

/**
 * 该模块异常编号以93开头.
 * @author bing.li
 */
public interface OperatorLogService {

	//public static final int EXCEPTION = 93XXXX;
	public static final int EXCEPTION_INSERT = 930001;
	public static final String EXCEPTION_INSERT_STR = "新增日志出错";
	
	public static final int EXCEPTION_INSERT_USER = 930002;
	public static final String EXCEPTION_INSERT_USER_STR = "新增日志操作员不存在";
	
	public static final int EXCEPTION_INSERT_MESSAGE = 930003;
	public static final String EXCEPTION_INSERT_MESSAGE_STR = "新增日志信息不能为空";
	
	public static final int EXCEPTION_QUERY_TOTAL = 930004;
	public static final String XCEPTION_QUERY_TOTAL_STR = "查询日志信息总条数异常";
	
	public static final int EXCEPTION_QUERY_LIST = 930005;
	public static final String XCEPTION_QUERY_LIST_STR = "查询日志信息列表异常";
	
	/**
	 * 添加一条操作日志记录
	 * @param level
	 * @param type
	 * @param rank
	 * @param userId
	 * @param name
	 * @param userKey
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param keyword4
	 * @param message
	 * @throws LotteryException
	 */
	public void addLogRecord(int level, int type, int rank, long userId, String name, String userKey, String keyword1, String keyword2, String keyword3, String keyword4, String message) throws LotteryException;


	/**
	 * 查询操作日志的总条数
	 * @param level
	 * @param type
	 * @param rank
	 * @param userId
	 * @param name
	 * @param userKey
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param keyword4
	 * @param message
	 * @throws LotteryException
	 */
	public int getAllOperationLogCount(Integer level, Integer type, Integer rank,Long userId, 
			String name, String userKey, String keyword1, String keyword2, 
			String keyword3, String keyword4, String message,Date beginTime,
			Date endTime)throws LotteryException;


	/**
	 * 查询操作日志的数据
	 * @param level
	 * @param type
	 * @param rank
	 * @param userId
	 * @param name
	 * @param userKey
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param keyword4
	 * @param message
	 * @throws LotteryException
	 */
	public List<OperationLog> getAllOperationLogList(Integer level, Integer type, Integer rank,
			Long userId,String name, String userKey, String keyword1, String keyword2, String keyword3,
			String keyword4, String message, Date beginTime,
			Date endTime,int page, int perPageNumber)throws LotteryException;
}
