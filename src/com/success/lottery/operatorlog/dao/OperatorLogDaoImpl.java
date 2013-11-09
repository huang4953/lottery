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
	 * 添加一条操作日志记录
	 * @param operationLog
	 */
	public void insertLogRecord(OperationLog operationLog) {
		this.getSqlMapClientTemplate().insert("operationLog.insertOperationLog", operationLog);
	}
	/**
	 * 查询操作日志的数据
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
	 * return 日志数据列表
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
	 * 查询操作日志的总条数
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
	 * return 日志数据条数
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
