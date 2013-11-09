/**
 * Title: ReportTransSortDao.java
 * @Package com.success.lottery.report.dao
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-10 ����11:43:53
 * @version V1.0
 */
package com.success.lottery.report.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.report.domain.ReportTransSortDomain;

/**
 * com.success.lottery.report.dao
 * ReportTransSortDao.java
 * ReportTransSortDao
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-10 ����11:43:53
 * 
 */

public class ReportTransSortDao extends SqlMapClientDaoSupport implements
		Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = -1406120395836677023L;
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	@SuppressWarnings("unchecked")
	public List<ReportTransSortDomain> getBetTransSortReport(Timestamp beginDate,Timestamp endDate,int maxSortNum){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("maxSortNum", maxSortNum);
		return this.smcTemplate.queryForList("reportTransSort.getBetTransSortReport", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReportTransSortDomain> getPrizeTransSortReport(Timestamp beginDate,Timestamp endDate,int maxSortNum){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("maxSortNum", maxSortNum);
		return this.smcTemplate.queryForList("reportTransSort.getPrizeTransSortReport", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReportTransSortDomain> getChongZhiTransSortReport(Timestamp beginDate,Timestamp endDate,int maxSortNum){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("maxSortNum", maxSortNum);
		return this.smcTemplate.queryForList("reportTransSort.getChongZhiTransSortReport", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<ReportTransSortDomain> getDrawTransSortReport(Timestamp beginDate,Timestamp endDate,int maxSortNum){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("maxSortNum", maxSortNum);
		return this.smcTemplate.queryForList("reportTransSort.getDrawTransSortReport", params);
	}

}
