package com.success.lottery.sms.push.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.dao.SmsPushDataDao;
import com.success.lottery.sms.push.model.SmsPushData;
import com.success.lottery.sms.push.model.SmsPushTask;

public class SmsPushDataDaoImpl extends SqlMapClientDaoSupport implements SmsPushDataDao{


	@Override
	public SmsPushData selectSmsPushData(Long id) {
		// TODO Auto-generated method stub
		SmsPushData smsPushData = new SmsPushData();
		smsPushData.setId(id);	
		return (SmsPushData)getSqlMapClientTemplate().queryForObject("smsPushData.getSmsPushData", smsPushData);
	}

	@Override
	public List<SmsPushData> selectSmsPushDataList(String taskId,
			long sendStatus, String mobilePhone, String serviceId,
			Timestamp beginTime, Timestamp endTime,int startNumber,
			int endNumber) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskId", taskId);
		param.put("sendStatus", sendStatus);
		param.put("mobilePhone", mobilePhone);
		param.put("serviceId", serviceId);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("startPageNumber", Integer.valueOf(startNumber));
		param.put("endPageNumber", Integer.valueOf(endNumber-startNumber));
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return sql.queryForList("smsPushData.getSmsPushDataList",param);
	}

	@Override
	public int selectSmsPushDataListCount(String taskId, long sendStatus,
			String mobilePhone, String serviceId, Timestamp beginTime,
			Timestamp endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskId", taskId);
		param.put("sendStatus", sendStatus);
		param.put("mobilePhone", mobilePhone);
		param.put("serviceId", serviceId);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return (Integer) sql.queryForObject("smsPushData.getSmsPushDataListCount", param);
	}

	@Override
	public void insertSmsPushData(SmsPushData smspushData)
			throws LotteryException {
		this.getSqlMapClientTemplate().insert("smsPushData.insertSmsPushData", smspushData);
	}
	
	@Override
	public List selectSmsPushTaskData(String taskId, int startNumber,
			int endNumber) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskId", taskId);
		param.put("startPageNumber", Integer.valueOf(startNumber));
		param.put("endPageNumber", Integer.valueOf(endNumber-startNumber));
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		List<SmsPushTask> list = sql.queryForList("smsPushData.getSmsPushTaskData",param);
		return list;
	}

	@Override
	public int selectSmsPushTaskDataCount(String taskId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("taskId", taskId);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return (Integer) sql.queryForObject("smsPushData.getSmsPushTaskDataCount", param);
	}

	@Override
	public int selectMixId() {
		Object obj=this.getSqlMapClientTemplate().queryForObject("smsPushData.selectMixId");
		return  null==obj?0:(Integer)obj;
	}
	@Override
	public SmsPushData selectSmsPushDataByTaskId(String taskid,
			String mobilePhone, String serviceid) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String, String>();
		map.put("mobilePhone", mobilePhone);
		map.put("taskId",taskid );
		map.put("serviceId", serviceid);
		return (SmsPushData) this.getSqlMapClientTemplate().queryForObject("smsPushData.getSmsPushDatabytaskid",map);
	}

	@Override
	public int updateTimeoutData() {
		return this.getSqlMapClientTemplate().update("smsPushData.updateTimeoutData");
	}

	@Override
	public int updatePushData(long id, int sendStatus) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", Long.valueOf(id));
		paramMap.put("sendStatus", Integer.valueOf(sendStatus));
		return this.getSqlMapClientTemplate().update("smsPushData.updatePushData", paramMap);
	}

	@Override
	public List<SmsPushData> getSendDataByTaskIdForUpdate(String taskId, int number) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskId", taskId);
		paramMap.put("number", Integer.valueOf(number));
		return (List<SmsPushData>)this.getSqlMapClientTemplate().queryForList("smsPushData.getSendDataForUpdate", paramMap);
	}
}
