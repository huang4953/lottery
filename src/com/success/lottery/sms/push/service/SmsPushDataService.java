package com.success.lottery.sms.push.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.model.SmsPushData;
import com.success.lottery.sms.push.model.SmsPushTask;

public interface SmsPushDataService {
	public static final int NOTSMSPUSHDATA=870151;
	public static final String NOTSMSPUSHDATA_STR="查询短信群发数据异常";
	
	public static final int NOTSMSPUSHDATACOUNT=870152;
	public static final String NOTSMSPUSHDATACOUNT_STR="查询短信群发数据的总条数异常";
	
	public static final int NOTSMSPUSHDATAPER=870153;
	public static final String NOTSMSPUSHDATAPER_STR="查询短信群发数据详细信息异常";

	public static final int CHECKTIMEOUTEXCEPTION = 871154;
	public static final String CHECKTIMEOUTEXCEPTION_STR="更新超时群发数据时出现异常：";

	public static final int SENDPUSHDATAEXCEPTION = 871155;
	public static final String SENDPUSHDATAEXCEPTION_STR="发送群发数据时出现异常：";


	/*
	 * 通过标识（taskId, sendStatus, mobilePhone,serviceId,beginTime
	 * endTime）获得短信群发数据表信息的总条数
	 * 
	 * 	@param taskId
	 *           短信群发数据任务id标识
	 *  @param sendStatus
	 *          短信群发数据发送状态标识
	 *  @param mobilePhone
	 *          发送的目标用户手机号码标识
	 *  @param serviceId
	 *         短信群发数据发送业务代码标识
	 *  @param beginTime
	 *          短信群发数据发送的最早开始时间标识
	 *  @param endTime
	 *          短信群发数据发送的最晚结束时间标识
	 * @return  符合条件的短信群发数据表信息的总条数
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public List<SmsPushData> getSmsPushDataList(String taskId,long sendStatus,String mobilePhone,
			String serviceId,Date beginTime,Date endTime,int pageIndex, int perPageNumber) throws LotteryException;
	/*
	 * 通过标识（taskId, sendStatus, mobilePhone,serviceId,beginTime
	 * endTime）获得短信群发数据表信息的总条数
	 * 
	 * 	@param taskId
	 *           短信群发数据任务id标识
	 *  @param sendStatus
	 *          短信群发数据发送状态标识
	 *  @param mobilePhone
	 *          发送的目标用户手机号码标识
	 *  @param serviceId
	 *         短信群发数据发送业务代码标识
	 *  @param beginTime
	 *          短信群发数据发送的最早开始时间标识
	 *  @param endTime
	 *          短信群发数据发送的最晚结束时间标识
	 * @return  符合条件的短信群发数据表信息的总条数
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public int getSmsPushDataListCount(String taskId,long sendStatus,String mobilePhone,
			String serviceId,Date beginTime,Date endTime)throws LotteryException;
	/*
	 * 通过标识（mobilePhone）获得短信群发数据表信息
	 * 
	 * 	@param mobilePhone
	 *           发送的目标用户手机号码标识
	 * @return  短信群发任务的详细信息
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public SmsPushData getSmsPushData(Long id) throws LotteryException;
	/*
	 * 通过标识（taskId）获得短信群发数据表信息的总条数
	 * 
	 * 	@param taskId
	 *           群发任务标识
	 * @return  短信群发数据的总条数
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public int getSmsPushTaskDataCount(String taskId)throws LotteryException;
	/*
	 * 通过标识（taskId）获得短信群发数据表信息的列表
	 * 
	 * 	@param taskId
	 *           群发任务标识
	 * @return  短信群发数据的列表
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public List<SmsPushData> getSmsPushTaskData(String taskId, int page,
			int perPageNumber)throws LotteryException;

	/**
	 * 检查过期的群发数据，更新群发数据发送状态为超时
	 * @return
	 * @throws LotteryException
	 */
	public int checkTimeoutData() throws LotteryException;

	/**
	 * 根据任务Id查询number条数据进行短信发送
	 * @param taskId
	 * @param number
	 * @return
	 * @throws LotteryException
	 */
	public String sendPushData(String taskId, int number) throws LotteryException;
}
