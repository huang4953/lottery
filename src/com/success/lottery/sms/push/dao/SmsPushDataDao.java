package com.success.lottery.sms.push.dao;

import java.sql.Timestamp;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.model.SmsPushData;

public interface SmsPushDataDao {
	/*
	 * 通过标识（taskId, sendStatus, mobilePhone,serviseId,beginTime
	 * endTime）获得短信群发数据表信息的总条数
	 * 
	 * 	@param taskId
	 *           短信群发数据任务id标识
	 *  @param sendStatus
	 *          短信群发数据发送状态标识
	 *  @param mobilePhone
	 *          短信群发任务任务产品代码标识
	 *  @param serviseId
	 *         短信群发数据发送业务代码标识
	 *  @param beginTime
	 *          短信群发数据发送的最早开始时间标识
	 *  @param endTime
	 *          短信群发数据发送的最晚结束时间标识
	 * @return  符合条件的短信群发数据表信息的总条数
	 */
	public List<SmsPushData> selectSmsPushDataList(String taskId,long sendStatus,String mobilePhone,
			String serviseId,Timestamp beginTime,Timestamp endTime,int startNumber,
			int endNumber);
	/*
	 * 通过标识（taskId, sendStatus, mobilePhone,serviseId,beginTime
	 * endTime）获得短信群发数据表信息的总条数
	 * 
	 * 	@param taskId
	 *           短信群发数据任务id标识
	 *  @param sendStatus
	 *          短信群发数据发送状态标识
	 *  @param mobilePhone
	 *          短信群发任务任务产品代码标识
	 *  @param serviseId
	 *         短信群发数据发送业务代码标识
	 *  @param beginTime
	 *          短信群发数据发送的最早开始时间标识
	 *  @param endTime
	 *          短信群发数据发送的最晚结束时间标识
	 * @return  符合条件的短信群发数据表信息的总条数
	 */
	public int selectSmsPushDataListCount(String taskId,long sendStatus,String mobilePhone,
			String serviseId,Timestamp beginTime,Timestamp endTime);
	/*
	 * 通过标识（id）获得短信群发数据表信息
	 * 
	 * 	@param id
	 *           短信群发数据id标识
	 * @return  短信群发数据的详细信息
	 */
	public SmsPushData selectSmsPushData(Long id) ;
	
	/**
	 * 添加一条短信群发数据
	 * @param smspushTask
	 * @return
	 * @throws LotteryException
	 */
	public void insertSmsPushData(SmsPushData smspushData)throws LotteryException;
	
	public int selectSmsPushTaskDataCount(String taskId);
	public List selectSmsPushTaskData(String taskId, int startNumber,
			int endNumber);
	
	/**
	 * 查询最大ID
	 * @return
	 */
	public int selectMixId();
	
	
	/**
	 * 根据任务ID，用户电话号码，服务ID查询群发数据对象
	 * @param taskid
	 * @param mobilePhone
	 * @param serviceid
	 * @return
	 */
	public SmsPushData selectSmsPushDataByTaskId(String taskid,String mobilePhone,String serviceid) ;

	public int updateTimeoutData();

	public int updatePushData(long id, int sendStatus);

	public List<SmsPushData> getSendDataByTaskIdForUpdate(String taskId, int number);
}
