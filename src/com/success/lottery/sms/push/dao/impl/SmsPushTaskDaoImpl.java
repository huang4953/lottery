package com.success.lottery.sms.push.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.dao.SmsPushTaskDao;
import com.success.lottery.sms.push.model.SmsPushTask;

import org.apache.commons.lang.StringUtils;

public class SmsPushTaskDaoImpl extends SqlMapClientDaoSupport implements SmsPushTaskDao{

	@Override
	public SmsPushTask selectSmsPushTask(String taskId) {
		// TODO Auto-generated method stub
		SmsPushTask smsPushTask = new SmsPushTask();
		smsPushTask.setTaskId(taskId);
		return (SmsPushTask)getSqlMapClientTemplate().queryForObject("smsPushTask.getSmsPushTask", smsPushTask);
	}

	@Override
	public List<SmsPushTask> selectSmsPushTaskList(String taskId,
			long taskStatus, String productId, String serviceId,
			Timestamp beginTime, Timestamp endTime, Integer lotteryId,
			String p_termNo_begin, String p_termNo_end,int startNumber,
			int endNumber) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskId", taskId);
		param.put("taskStatus", taskStatus);
		param.put("productId", productId);
		param.put("serviceId", serviceId);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("lotteryId", lotteryId);
		param.put("lotteryTerm_bigin", p_termNo_begin);
		param.put("lotteryTerm_end", p_termNo_end);
		param.put("startPageNumber", Integer.valueOf(startNumber));
		param.put("endPageNumber", Integer.valueOf(endNumber-startNumber));
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		List<SmsPushTask> list = sql.queryForList("smsPushTask.getSmsPushTaskList",param);
		return list;
	}

	@Override
	public int selectSmsPushTaskListCount(String taskId, long taskStatus,
			String productId, String serviceId, Timestamp beginTime,
			Timestamp endTime, Integer lotteryId, String p_termNo_begin, String p_termNo_end) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskId", taskId);
		param.put("taskStatus", taskStatus);
		param.put("productId", productId);
		param.put("serviceId", serviceId);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("lotteryId", lotteryId);
		param.put("lotteryTerm_bigin", p_termNo_begin);
		param.put("lotteryTerm_end", p_termNo_end);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return (Integer) sql.queryForObject("smsPushTask.getSmsPushTaskListCount", param);
	}

	@Override
	public void insertSmsPushTask(SmsPushTask smspushTask)
			throws LotteryException {
		this.getSqlMapClientTemplate().insert("smsPushTask.insertSmsPushTask",smspushTask);
	}
	@Override
	public SmsPushTask querySmsPushTask(Map map) {
		return (SmsPushTask) this.getSqlMapClientTemplate().queryForObject("smsPushTask.querySmsPushTask", map);
	}

	@Override
	public SmsPushTask querySmsPushTashByLottery(Map map) {
		return (SmsPushTask) this.getSqlMapClientTemplate().queryForObject("smsPushTask.querySmsPushTashByLottery", map);
	}

	@Override
	public int updateTimeoutTask() {
		return this.getSqlMapClientTemplate().update("smsPushTask.updateTimeoutTask");
	}

	@Override
	public SmsPushTask getPushTaskForUpdate() {
		return (SmsPushTask)this.getSqlMapClientTemplate().queryForObject("smsPushTask.getTaskForUpdate");
	}

	@Override
	public int updateTaskExecutionStat(String taskId, int taskStatus, boolean updateTime, String executionStat) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskId", taskId);
		paramMap.put("taskStatus", Integer.valueOf(taskStatus));
		if(updateTime){
			paramMap.put("executionTime", "NOW");
		}
		if(!StringUtils.isBlank(executionStat)){
			paramMap.put("executionStat", executionStat);
		}
		return this.getSqlMapClientTemplate().update("smsPushTask.updateTaskExecutionStat", paramMap);
	}

	@Override
	public SmsPushTask getPushTaskForUpdate(String taskId) {
		return (SmsPushTask)this.getSqlMapClientTemplate().queryForObject("smsPushTask.getTaskByIdForUpdate", taskId);
	}
}
