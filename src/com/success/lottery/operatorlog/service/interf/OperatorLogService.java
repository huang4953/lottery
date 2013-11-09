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
 * ��ģ���쳣�����93��ͷ.
 * @author bing.li
 */
public interface OperatorLogService {

	//public static final int EXCEPTION = 93XXXX;
	public static final int EXCEPTION_INSERT = 930001;
	public static final String EXCEPTION_INSERT_STR = "������־����";
	
	public static final int EXCEPTION_INSERT_USER = 930002;
	public static final String EXCEPTION_INSERT_USER_STR = "������־����Ա������";
	
	public static final int EXCEPTION_INSERT_MESSAGE = 930003;
	public static final String EXCEPTION_INSERT_MESSAGE_STR = "������־��Ϣ����Ϊ��";
	
	public static final int EXCEPTION_QUERY_TOTAL = 930004;
	public static final String XCEPTION_QUERY_TOTAL_STR = "��ѯ��־��Ϣ�������쳣";
	
	public static final int EXCEPTION_QUERY_LIST = 930005;
	public static final String XCEPTION_QUERY_LIST_STR = "��ѯ��־��Ϣ�б��쳣";
	
	/**
	 * ���һ��������־��¼
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
	 * ��ѯ������־��������
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
	 * ��ѯ������־������
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
