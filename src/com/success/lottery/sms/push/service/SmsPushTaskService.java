package com.success.lottery.sms.push.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.model.SmsPushData;
import com.success.lottery.sms.push.model.SmsPushTask;

public interface SmsPushTaskService {
	public static final int SMS01EXCEPTION = 880101;
	public static final String SMS01EXCEPTION_STR = "读取属性文件异常！";
	public static final int SMS02EXCEPTION = 880102;
	public static final String SMS02EXCEPTION_STR = "日期转换异常！";
	public static final int NOTSMSPUSHTASk=870161;
	public static final String NOTSMSPUSHTASk_STR="查询短信群发任务异常";
	
	public static final int NOTSMSPUSHTASkCOUNT=870162;
	public static final String NOTSMSPUSHTASkCOUNT_STR="查询短信群发任务的总条数异常";
	
	public static final int NOTSMSPUSHTASkPER=870163;
	public static final String NOTSMSPUSHTASkPER_STR="查询短信群发任务详细信息异常";

	public static final int CHECKTIMEOUTEXCEPTION=871002;
	public static final String CHECKTIMEOUTEXCEPTION_STR="更新超时群发任务时出现异常：";

	public static final int FINDTASKTOEXECEXCEPTION=871003;
	public static final String FINDTASKTOEXECEXCEPTION_STR="查找可执行的任务并执行时出现异常：";

	/*
	 * 通过标识（taskId, taskStatus, productId,serviceId,beginTime
	 * endTime,lotteryTerm,lotteryId）获得短信群发任务表信息
	 * 
	 * 	@param taskId
	 *           短信群发任务id标识
	 *  @param taskStatus
	 *          短信群发任务任务状态标识
	 *  @param productId
	 *          短信群发任务任务产品代码标识
	 *  @param serviceId
	 *          短信群发任务发送业务代码标识
	 *  @param beginTime
	 *          短信群发任务发送的最早开始时间标识
	 *  @param endTime
	 *          短信群发任务发送的最晚结束时间标识
	 *  @param lotteryTerm
	 *          短信群发任务期数标识  
	 *  @param lotteryId
	 *           短信群发任务彩种id标识       
	 * @return  短信群发任务信息的列表List
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public List<SmsPushTask> getSmsPushTaskList(String taskId,long taskStatus,String productId,
			String serviceId,Date beginTime,Date endTime,Integer lotteryId,String p_termNo_begin, String p_termNo_end, 
			int pageIndex, int perPageNumber) throws LotteryException;
	/*
	 * 通过标识（taskId, taskStatus, productId,serviceId,beginTime
	 * endTime,lotteryTerm,lotteryId）获得短信群发任务表信息
	 * 
	 * 	@param taskId
	 *           短信群发任务id标识
	 *  @param taskStatus
	 *          短信群发任务任务状态标识
	 *  @param productId
	 *          短信群发任务任务产品代码标识
	 *  @param serviceId
	 *          短信群发任务发送业务代码标识
	 *  @param beginTime
	 *          短信群发任务发送的最早开始时间标识
	 *  @param endTime
	 *          短信群发任务发送的最晚结束时间标识
	 *  @param lotteryTerm
	 *          短信群发任务期数标识  
	 *  @param lotteryId
	 *           短信群发任务彩种id标识       
	 * @return  符合条件的短信群发任务信息的总条数
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public int getSmsPushTaskListCount(String taskId,long taskStatus,String productId,
			String serviceId,Date beginTime,Date endTime,Integer lotteryId,String p_termNo_begin, String p_termNo_end)throws LotteryException;
	/*
	 * 通过标识（taskId）获得短信群发任务表信息
	 * 
	 * 	@param taskId
	 *           短信群发任务id标识  
	 * @return  短信群发任务的详细信息
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public SmsPushTask getSmsPushTask(String taskId) throws LotteryException;
	
	/**
	 * 添加一条开奖通知任务，并添加一组短信群发数据
	 * @return
	 * @throws LotteryException
	 */
	public String insertSmsPush(String userId,String userName,String ip)throws LotteryException;
	
	
	/**
	 * 添加一条中将通知任务，并添加一组短信群发数据
	 * @param lotteryId 彩种ID
	 * @param termNo 彩期编号
	 * @return
	 * @throws LotteryException
	 */
	public String insertSmsPushZJGG(int lotteryId,String termNo,String userId,String userName,String ip)throws LotteryException;

	/**
	 * 添加今天所有中奖短信任务群数据
	 * @return
	 * @throws LotteryException
	 */
	public List<String> insertSmsPushAll(String userId,String userName,String ip)throws LotteryException;
	
	
	public Map<String,Object> selectindex()throws LotteryException;

	/**
	 * 检查过期的群发任务，更新任务状态为超时
	 * @throws LotteryException
	 */
	public int checkTimeoutTask() throws LotteryException;


	public String findTaskAndExecution() throws LotteryException;

	public void executeTask(String taskId) throws LotteryException;

	//public int updateSentData(long id, int sendStatus) throws LotteryException;
}
