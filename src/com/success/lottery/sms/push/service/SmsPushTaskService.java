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
	public static final String SMS01EXCEPTION_STR = "��ȡ�����ļ��쳣��";
	public static final int SMS02EXCEPTION = 880102;
	public static final String SMS02EXCEPTION_STR = "����ת���쳣��";
	public static final int NOTSMSPUSHTASk=870161;
	public static final String NOTSMSPUSHTASk_STR="��ѯ����Ⱥ�������쳣";
	
	public static final int NOTSMSPUSHTASkCOUNT=870162;
	public static final String NOTSMSPUSHTASkCOUNT_STR="��ѯ����Ⱥ��������������쳣";
	
	public static final int NOTSMSPUSHTASkPER=870163;
	public static final String NOTSMSPUSHTASkPER_STR="��ѯ����Ⱥ��������ϸ��Ϣ�쳣";

	public static final int CHECKTIMEOUTEXCEPTION=871002;
	public static final String CHECKTIMEOUTEXCEPTION_STR="���³�ʱȺ������ʱ�����쳣��";

	public static final int FINDTASKTOEXECEXCEPTION=871003;
	public static final String FINDTASKTOEXECEXCEPTION_STR="���ҿ�ִ�е�����ִ��ʱ�����쳣��";

	/*
	 * ͨ����ʶ��taskId, taskStatus, productId,serviceId,beginTime
	 * endTime,lotteryTerm,lotteryId����ö���Ⱥ���������Ϣ
	 * 
	 * 	@param taskId
	 *           ����Ⱥ������id��ʶ
	 *  @param taskStatus
	 *          ����Ⱥ����������״̬��ʶ
	 *  @param productId
	 *          ����Ⱥ�����������Ʒ�����ʶ
	 *  @param serviceId
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
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public List<SmsPushTask> getSmsPushTaskList(String taskId,long taskStatus,String productId,
			String serviceId,Date beginTime,Date endTime,Integer lotteryId,String p_termNo_begin, String p_termNo_end, 
			int pageIndex, int perPageNumber) throws LotteryException;
	/*
	 * ͨ����ʶ��taskId, taskStatus, productId,serviceId,beginTime
	 * endTime,lotteryTerm,lotteryId����ö���Ⱥ���������Ϣ
	 * 
	 * 	@param taskId
	 *           ����Ⱥ������id��ʶ
	 *  @param taskStatus
	 *          ����Ⱥ����������״̬��ʶ
	 *  @param productId
	 *          ����Ⱥ�����������Ʒ�����ʶ
	 *  @param serviceId
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
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public int getSmsPushTaskListCount(String taskId,long taskStatus,String productId,
			String serviceId,Date beginTime,Date endTime,Integer lotteryId,String p_termNo_begin, String p_termNo_end)throws LotteryException;
	/*
	 * ͨ����ʶ��taskId����ö���Ⱥ���������Ϣ
	 * 
	 * 	@param taskId
	 *           ����Ⱥ������id��ʶ  
	 * @return  ����Ⱥ���������ϸ��Ϣ
	 * @throws LotteryException
	 * @throws LotteryException
	 */
	public SmsPushTask getSmsPushTask(String taskId) throws LotteryException;
	
	/**
	 * ���һ������֪ͨ���񣬲����һ�����Ⱥ������
	 * @return
	 * @throws LotteryException
	 */
	public String insertSmsPush(String userId,String userName,String ip)throws LotteryException;
	
	
	/**
	 * ���һ���н�֪ͨ���񣬲����һ�����Ⱥ������
	 * @param lotteryId ����ID
	 * @param termNo ���ڱ��
	 * @return
	 * @throws LotteryException
	 */
	public String insertSmsPushZJGG(int lotteryId,String termNo,String userId,String userName,String ip)throws LotteryException;

	/**
	 * ��ӽ��������н���������Ⱥ����
	 * @return
	 * @throws LotteryException
	 */
	public List<String> insertSmsPushAll(String userId,String userName,String ip)throws LotteryException;
	
	
	public Map<String,Object> selectindex()throws LotteryException;

	/**
	 * �����ڵ�Ⱥ�����񣬸�������״̬Ϊ��ʱ
	 * @throws LotteryException
	 */
	public int checkTimeoutTask() throws LotteryException;


	public String findTaskAndExecution() throws LotteryException;

	public void executeTask(String taskId) throws LotteryException;

	//public int updateSentData(long id, int sendStatus) throws LotteryException;
}
