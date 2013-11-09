package com.success.lottery.sms.push.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.model.SmsPushTask;

public interface SmsPushTaskDao {
	/*
	 * ͨ����ʶ��taskId, taskStatus, productId,serviseId,beginTime
	 * endTime,lotteryTerm,lotteryId����ö���Ⱥ���������Ϣ
	 * 
	 * 	@param taskId
	 *           ����Ⱥ������id��ʶ
	 *  @param taskStatus
	 *          ����Ⱥ����������״̬��ʶ
	 *  @param productId
	 *          ����Ⱥ�����������Ʒ�����ʶ
	 *  @param serviseId
	 *          ����Ⱥ��������ҵ������ʶ
	 *  @param beginTime
	 *          ����Ⱥ�������͵����翪ʼʱ���ʶ
	 *  @param endTime
	 *          ����Ⱥ�������͵��������ʱ���ʶ
	 *  @param lotteryTerm
	 *          ����Ⱥ������������ʶ  
	 *  @param lotteryId
	 *           ����Ⱥ���������id��ʶ       
	 * @return  ����Ⱥ��������Ϣ���б�List
	 */
	public List<SmsPushTask> selectSmsPushTaskList(String taskId,long taskStatus,String productId,
			String serviseId,Timestamp beginTime,Timestamp endTime,Integer lotteryId,String p_termNo_begin, 
			String p_termNo_end, int startNumber,int endNumber);
	/*
	 * ͨ����ʶ��taskId, taskStatus, productId,serviseId,beginTime
	 * endTime,lotteryTerm,lotteryId����ö���Ⱥ���������Ϣ
	 * 
	 * 	@param taskId
	 *           ����Ⱥ������id��ʶ
	 *  @param taskStatus
	 *          ����Ⱥ����������״̬��ʶ
	 *  @param productId
	 *          ����Ⱥ�����������Ʒ�����ʶ
	 *  @param serviseId
	 *          ����Ⱥ��������ҵ������ʶ
	 *  @param beginTime
	 *          ����Ⱥ�������͵����翪ʼʱ���ʶ
	 *  @param endTime
	 *          ����Ⱥ�������͵��������ʱ���ʶ
	 *  @param lotteryTerm
	 *          ����Ⱥ������������ʶ  
	 *  @param lotteryId
	 *           ����Ⱥ���������id��ʶ       
	 * @return  ���������Ķ���Ⱥ��������Ϣ��������
	 */
	public int selectSmsPushTaskListCount(String taskId,long taskStatus,String productId,
			String serviseId,Timestamp beginTime,Timestamp endTime,Integer lotteryId,String p_termNo_begin, String p_termNo_end);
	/*
	 * ͨ����ʶ��taskId����ö���Ⱥ���������Ϣ
	 * 
	 * 	@param taskId
	 *           ����Ⱥ������id��ʶ  
	 * @return  ����Ⱥ���������ϸ��Ϣ
	 */
	public SmsPushTask selectSmsPushTask(String taskId);
	
	/**
	 * ���һ��������
	 * @param smspushTask
	 * @return
	 * @throws LotteryException
	 */
	public void insertSmsPushTask(SmsPushTask smspushTask)throws LotteryException;
	
	/**
	 * ���ݲ��ֲ���ServiceID���Ҷ���Ⱥ������
	 * @param map
	 * @return
	 */
	public SmsPushTask querySmsPushTask(Map map); 
	
	/**
	 * ���ڣ����� ����ID��ѯ����
	 * @param map
	 * @return
	 */
	public SmsPushTask querySmsPushTashByLottery(Map map);

	public int updateTimeoutTask();

	public SmsPushTask getPushTaskForUpdate();

	public int updateTaskExecutionStat(String taskId, int taskStatus, boolean updateTime, String executionStat);

	public SmsPushTask getPushTaskForUpdate(String taskId);
}
