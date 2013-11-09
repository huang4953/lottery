/**
 * Title: ReportQueryDao.java
 * @Package com.success.lottery.report.dao
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-10 上午10:13:15
 * @version V1.0
 */
package com.success.lottery.report.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.report.domain.ReportAccount;
import com.success.lottery.report.domain.ReportPrizeDomain;

/**
 * com.success.lottery.report.dao
 * ReportQueryDao.java
 * ReportQueryDao
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-10 上午10:13:15
 * 
 */

public class ReportQueryDao extends SqlMapClientDaoSupport implements
		Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 207501427290424744L;
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	/**
	 * 
	 * Title: getReportPrizeTerms<br>
	 * Description: <br>
	 *              <br>查询已经生成的彩期列表
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getReportPrizeTerms(String tableName){
		return this.smcTemplate.queryForList("reportPrize.getReportPrizeTerms", tableName);
	}
	/**
	 * 
	 * Title: getPrizeReports<br>
	 * Description: <br>
	 *              <br>分页查询中奖统计
	 * @param tableName
	 * @param beginTermNo
	 * @param endTermNo
	 * @param startPageNumber
	 * @param endPageNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportPrizeDomain> getPrizeReports(String tableName,String beginTermNo,String endTermNo,int startPageNumber,int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("tableName", tableName);
		param.put("beginTermNo", beginTermNo);
		param.put("endTermNo", endTermNo);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.smcTemplate.queryForList("reportPrize.getPrizeReports", param);
	}
	/**
	 * 
	 * Title: getPrizeReportsCount<br>
	 * Description: <br>
	 *              <br>分页查询中奖统计的条数
	 * @param tableName
	 * @param beginTermNo
	 * @param endTermNo
	 * @return
	 */
	public Integer getPrizeReportsCount(String tableName,String beginTermNo,String endTermNo){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("tableName", tableName);
		param.put("beginTermNo", beginTermNo);
		param.put("endTermNo", endTermNo);
		return (Integer)this.smcTemplate.queryForObject("reportPrize.getPrizeReportsCount", param);
	}
	/**
	 * 
	 * Title: getPrizeReportInfo<br>
	 * Description: <br>
	 *              <br>查询某一条中奖统计数据
	 * @param tableName
	 * @param reportId
	 * @return
	 */
	public ReportPrizeDomain getPrizeReportInfo(String tableName,int reportId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("tableName", tableName);
		param.put("reportId", reportId);
		return (ReportPrizeDomain)this.smcTemplate.queryForObject("reportPrize.getPrizeReportInfo", param);
	}
	/**
	 * 
	 * Title: getAccountReports<br>
	 * Description: <br>
	 *              <br>分页查询账户变动信息
	 * @param beginDate
	 * @param endDate
	 * @param startPageNumber
	 * @param endPageNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportAccount> getAccountReports(String beginDate,
			String endDate, int startPageNumber,int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.smcTemplate.queryForList("reportAccount.getAccountReports", param);
	}
	/**
	 * 
	 * Title: getAccountReportsCount<br>
	 * Description: <br>
	 *              <br>分页查询账户变动信息的条数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public int getAccountReportsCount(String beginDate, String endDate){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		return (Integer)this.smcTemplate.queryForObject("reportAccount.getAccountReportsCount", param);
	}
	/**
	 * 
	 * Title: getAccountReportInfo<br>
	 * Description: <br>
	 *              <br>查询一条账户变动信息
	 * @param reportId
	 * @return
	 */
	public ReportAccount getAccountReportInfo(String reportId){
		return (ReportAccount)this.smcTemplate.queryForObject("reportAccount.getAccountReportInfo", reportId);
	}

}
