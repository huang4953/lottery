package com.success.lottery.operatorlog.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.operatorlog.domain.OperationLog;

public class OperatorLogDaoImpl extends SqlMapClientDaoSupport implements Serializable {
	/**
	 * ���һ��������־��¼
	 * @param operationLog
	 */
	public void insertLogRecord(OperationLog operationLog) {
		this.getSqlMapClientTemplate().insert("operationLog.insertOperationLog", operationLog);
	}
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
	 * return ��־�����б�
	 */
	@SuppressWarnings("unchecked")
	public List<OperationLog> queryAllOperationLogList(Integer level, Integer type, Integer rank,Long userId, 
			String name, String userKey,String keyword1, String keyword2, String keyword3, String keyword4,
			String message, Timestamp beginTime, Timestamp endTime,int startNumber, int endNumber) {
		Map param = new HashMap<String, Object>();
		param.put("level", level);
		param.put("type", type);
		param.put("rank", rank);
		param.put("userId", userId);
		param.put("name", name);
		param.put("userKey", userKey);
		param.put("keyword1", keyword1);
		param.put("keyword2", keyword2);
		param.put("keyword3", keyword3);
		param.put("keyword4", keyword4);
		param.put("message", message);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("startPageNumber", startNumber);
		param.put("endPageNumber", endNumber);
		return this.getSqlMapClientTemplate().queryForList("operationLog.selectOperationLogList",param);
	}

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
	 * return ��־��������
	 */
	@SuppressWarnings("unchecked")
	public int queryAllOperationLogCount(Integer level, Integer type, Integer rank,Long userId,
			String name, String userKey, String keyword1,
			String keyword2, String keyword3, String keyword4, 
			String message,Timestamp beginTime, Timestamp endTime) {
		Map param = new HashMap<String, Object>();
		param.put("level", level);
		param.put("type", type);
		param.put("rank", rank);
		param.put("userId", userId);
		param.put("name", name);
		param.put("userKey", userKey);
		param.put("keyword1", keyword1);
		param.put("keyword2", keyword2);
		param.put("keyword3", keyword3);
		param.put("keyword4", keyword4);
		param.put("message", message);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		return (Integer) this.getSqlMapClientTemplate().queryForObject("operationLog.selectOperationLogCount", param);
	}

}
