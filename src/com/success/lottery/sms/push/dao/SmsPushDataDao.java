package com.success.lottery.sms.push.dao;

import java.sql.Timestamp;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.model.SmsPushData;

public interface SmsPushDataDao {
	/*
	 * ͨ����ʶ��taskId, sendStatus, mobilePhone,serviseId,beginTime
	 * endTime����ö���Ⱥ�����ݱ���Ϣ��������
	 * 
	 * 	@param taskId
	 *           ����Ⱥ����������id��ʶ
	 *  @param sendStatus
	 *          ����Ⱥ�����ݷ���״̬��ʶ
	 *  @param mobilePhone
	 *          ����Ⱥ�����������Ʒ�����ʶ
	 *  @param serviseId
	 *         ����Ⱥ�����ݷ���ҵ������ʶ
	 *  @param beginTime
	 *          ����Ⱥ�����ݷ��͵����翪ʼʱ���ʶ
	 *  @param endTime
	 *          ����Ⱥ�����ݷ��͵��������ʱ���ʶ
	 * @return  ���������Ķ���Ⱥ�����ݱ���Ϣ��������
	 */
	public List<SmsPushData> selectSmsPushDataList(String taskId,long sendStatus,String mobilePhone,
			String serviseId,Timestamp beginTime,Timestamp endTime,int startNumber,
			int endNumber);
	/*
	 * ͨ����ʶ��taskId, sendStatus, mobilePhone,serviseId,beginTime
	 * endTime����ö���Ⱥ�����ݱ���Ϣ��������
	 * 
	 * 	@param taskId
	 *           ����Ⱥ����������id��ʶ
	 *  @param sendStatus
	 *          ����Ⱥ�����ݷ���״̬��ʶ
	 *  @param mobilePhone
	 *          ����Ⱥ�����������Ʒ�����ʶ
	 *  @param serviseId
	 *         ����Ⱥ�����ݷ���ҵ������ʶ
	 *  @param beginTime
	 *          ����Ⱥ�����ݷ��͵����翪ʼʱ���ʶ
	 *  @param endTime
	 *          ����Ⱥ�����ݷ��͵��������ʱ���ʶ
	 * @return  ���������Ķ���Ⱥ�����ݱ���Ϣ��������
	 */
	public int selectSmsPushDataListCount(String taskId,long sendStatus,String mobilePhone,
			String serviseId,Timestamp beginTime,Timestamp endTime);
	/*
	 * ͨ����ʶ��id����ö���Ⱥ�����ݱ���Ϣ
	 * 
	 * 	@param id
	 *           ����Ⱥ������id��ʶ
	 * @return  ����Ⱥ�����ݵ���ϸ��Ϣ
	 */
	public SmsPushData selectSmsPushData(Long id) ;
	
	/**
	 * ���һ������Ⱥ������
	 * @param smspushTask
	 * @return
	 * @throws LotteryException
	 */
	public void insertSmsPushData(SmsPushData smspushData)throws LotteryException;
	
	public int selectSmsPushTaskDataCount(String taskId);
	public List selectSmsPushTaskData(String taskId, int startNumber,
			int endNumber);
	
	/**
	 * ��ѯ���ID
	 * @return
	 */
	public int selectMixId();
	
	
	/**
	 * ��������ID���û��绰���룬����ID��ѯȺ�����ݶ���
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
