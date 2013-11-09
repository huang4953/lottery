package com.success.lottery.sms.push.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.model.SmsPushData;
import com.success.lottery.sms.push.model.SmsPushTask;

public interface SmsPushDataService {
	public static final int NOTSMSPUSHDATA=870151;
	public static final String NOTSMSPUSHDATA_STR="��ѯ����Ⱥ�������쳣";
	
	public static final int NOTSMSPUSHDATACOUNT=870152;
	public static final String NOTSMSPUSHDATACOUNT_STR="��ѯ����Ⱥ�����ݵ��������쳣";
	
	public static final int NOTSMSPUSHDATAPER=870153;
	public static final String NOTSMSPUSHDATAPER_STR="��ѯ����Ⱥ��������ϸ��Ϣ�쳣";

	public static final int CHECKTIMEOUTEXCEPTION = 871154;
	public static final String CHECKTIMEOUTEXCEPTION_STR="���³�ʱȺ������ʱ�����쳣��";

	public static final int SENDPUSHDATAEXCEPTION = 871155;
	public static final String SENDPUSHDATAEXCEPTION_STR="����Ⱥ������ʱ�����쳣��";


	/*
	 * ͨ����ʶ��taskId, sendStatus, mobilePhone,serviceId,beginTime
	 * endTime����ö���Ⱥ�����ݱ���Ϣ��������
	 * 
	 * 	@param taskId
	 *           ����Ⱥ����������id��ʶ
	 *  @param sendStatus
	 *          ����Ⱥ�����ݷ���״̬��ʶ
	 *  @param mobilePhone
	 *          ���͵�Ŀ���û��ֻ������ʶ
	 *  @param serviceId
	 *         ����Ⱥ�����ݷ���ҵ������ʶ
	 *  @param beginTime
	 *          ����Ⱥ�����ݷ��͵����翪ʼʱ���ʶ
	 *  @param endTime
	 *          ����Ⱥ�����ݷ��͵��������ʱ���ʶ
	 * @return  ���������Ķ���Ⱥ�����ݱ���Ϣ��������
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public List<SmsPushData> getSmsPushDataList(String taskId,long sendStatus,String mobilePhone,
			String serviceId,Date beginTime,Date endTime,int pageIndex, int perPageNumber) throws LotteryException;
	/*
	 * ͨ����ʶ��taskId, sendStatus, mobilePhone,serviceId,beginTime
	 * endTime����ö���Ⱥ�����ݱ���Ϣ��������
	 * 
	 * 	@param taskId
	 *           ����Ⱥ����������id��ʶ
	 *  @param sendStatus
	 *          ����Ⱥ�����ݷ���״̬��ʶ
	 *  @param mobilePhone
	 *          ���͵�Ŀ���û��ֻ������ʶ
	 *  @param serviceId
	 *         ����Ⱥ�����ݷ���ҵ������ʶ
	 *  @param beginTime
	 *          ����Ⱥ�����ݷ��͵����翪ʼʱ���ʶ
	 *  @param endTime
	 *          ����Ⱥ�����ݷ��͵��������ʱ���ʶ
	 * @return  ���������Ķ���Ⱥ�����ݱ���Ϣ��������
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public int getSmsPushDataListCount(String taskId,long sendStatus,String mobilePhone,
			String serviceId,Date beginTime,Date endTime)throws LotteryException;
	/*
	 * ͨ����ʶ��mobilePhone����ö���Ⱥ�����ݱ���Ϣ
	 * 
	 * 	@param mobilePhone
	 *           ���͵�Ŀ���û��ֻ������ʶ
	 * @return  ����Ⱥ���������ϸ��Ϣ
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public SmsPushData getSmsPushData(Long id) throws LotteryException;
	/*
	 * ͨ����ʶ��taskId����ö���Ⱥ�����ݱ���Ϣ��������
	 * 
	 * 	@param taskId
	 *           Ⱥ�������ʶ
	 * @return  ����Ⱥ�����ݵ�������
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public int getSmsPushTaskDataCount(String taskId)throws LotteryException;
	/*
	 * ͨ����ʶ��taskId����ö���Ⱥ�����ݱ���Ϣ���б�
	 * 
	 * 	@param taskId
	 *           Ⱥ�������ʶ
	 * @return  ����Ⱥ�����ݵ��б�
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public List<SmsPushData> getSmsPushTaskData(String taskId, int page,
			int perPageNumber)throws LotteryException;

	/**
	 * �����ڵ�Ⱥ�����ݣ�����Ⱥ�����ݷ���״̬Ϊ��ʱ
	 * @return
	 * @throws LotteryException
	 */
	public int checkTimeoutData() throws LotteryException;

	/**
	 * ��������Id��ѯnumber�����ݽ��ж��ŷ���
	 * @param taskId
	 * @param number
	 * @return
	 * @throws LotteryException
	 */
	public String sendPushData(String taskId, int number) throws LotteryException;
}
