package com.success.lottery.sms.push.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.model.SmsPushTask;

public interface SmsPushTaskDao {
	/*
	 * 通过标识（taskId, taskStatus, productId,serviseId,beginTime
	 * endTime,lotteryTerm,lotteryId）获得短信群发任务表信息
	 * 
	 * 	@param taskId
	 *           短信群发任务id标识
	 *  @param taskStatus
	 *          短信群发任务任务状态标识
	 *  @param productId
	 *          短信群发任务任务产品代码标识
	 *  @param serviseId
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
	 */
	public List<SmsPushTask> selectSmsPushTaskList(String taskId,long taskStatus,String productId,
			String serviseId,Timestamp beginTime,Timestamp endTime,Integer lotteryId,String p_termNo_begin, 
			String p_termNo_end, int startNumber,int endNumber);
	/*
	 * 通过标识（taskId, taskStatus, productId,serviseId,beginTime
	 * endTime,lotteryTerm,lotteryId）获得短信群发任务表信息
	 * 
	 * 	@param taskId
	 *           短信群发任务id标识
	 *  @param taskStatus
	 *          短信群发任务任务状态标识
	 *  @param productId
	 *          短信群发任务任务产品代码标识
	 *  @param serviseId
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
	 */
	public int selectSmsPushTaskListCount(String taskId,long taskStatus,String productId,
			String serviseId,Timestamp beginTime,Timestamp endTime,Integer lotteryId,String p_termNo_begin, String p_termNo_end);
	/*
	 * 通过标识（taskId）获得短信群发任务表信息
	 * 
	 * 	@param taskId
	 *           短信群发任务id标识  
	 * @return  短信群发任务的详细信息
	 */
	public SmsPushTask selectSmsPushTask(String taskId);
	
	/**
	 * 添加一条新任务
	 * @param smspushTask
	 * @return
	 * @throws LotteryException
	 */
	public void insertSmsPushTask(SmsPushTask smspushTask)throws LotteryException;
	
	/**
	 * 根据彩种彩期ServiceID查找短信群发任务
	 * @param map
	 * @return
	 */
	public SmsPushTask querySmsPushTask(Map map); 
	
	/**
	 * 彩期，彩种 服务ID查询任务
	 * @param map
	 * @return
	 */
	public SmsPushTask querySmsPushTashByLottery(Map map);

	public int updateTimeoutTask();

	public SmsPushTask getPushTaskForUpdate();

	public int updateTaskExecutionStat(String taskId, int taskStatus, boolean updateTime, String executionStat);

	public SmsPushTask getPushTaskForUpdate(String taskId);
}
