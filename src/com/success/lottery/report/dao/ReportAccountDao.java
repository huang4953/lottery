/**
 * Title: ReportAccountDao.java
 * @Package com.success.lottery.report.dao
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-9 ����07:34:52
 * @version V1.0
 */
package com.success.lottery.report.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.report.domain.ReportAccount;
import com.success.lottery.report.domain.ReportAccountCountDomain;
import com.success.lottery.report.domain.ReportPrizeDomain;

/**
 * com.success.lottery.report.dao
 * ReportAccountDao.java
 * ReportAccountDao
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-9 ����07:34:52
 * 
 */

public class ReportAccountDao extends SqlMapClientDaoSupport implements
		Serializable {

	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = -53504498494247904L;
	
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	/**
	 * 
	 * Title: insertAccountReportData<br>
	 * Description: <br>
	 *              <br>�����˻��䶯ͳ������
	 * @param reportDomain
	 */
	public void insertAccountReportData(ReportAccount reportDomain){
		this.smcTemplate.insert("reportAccount.insert_report_account", reportDomain);
	}
	/**
	 * 
	 * Title: getReportAccountCount<br>
	 * Description: <br>
	 *              <br>ͳ���˻����е��˻����
	 * @return
	 */
	public ReportAccount getReportAccountCount(){
		return (ReportAccount)this.smcTemplate.queryForObject("reportAccount.getAccountBalance");
	}
	
	/**
	 * 
	 * Title: getReportAccountTransaction<br>
	 * Description: <br>
	 *              <br>���ս������͵Ľ�������ͳ��һ��ʱ����ڵ��˻��䶯
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportAccountCountDomain> getReportAccountTransaction(Timestamp beginTime,Timestamp endTime){
		Map<String,Timestamp> params = new HashMap<String,Timestamp>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		return this.smcTemplate.queryForList("reportAccount.getAccountTransactionCount", params);
	}
	/**
	 * 
	 * Title: getReportIsCreateCount<br>
	 * Description: <br>
	 *              <br>�жϸ���������Ƿ��Ѿ�������
	 * @param accountDate
	 * @return
	 */
	public Integer getReportIsCreateCount(String accountDate){
		return (Integer)this.smcTemplate.queryForObject("reportAccount.getReportIsCreateCount", accountDate);
	}

}
