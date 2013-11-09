/**
 * Title: ReportPrizeDao.java
 * @Package com.success.lottery.report.dao
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-9 ����10:17:39
 * @version V1.0
 */
package com.success.lottery.report.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.report.domain.ReportPrizeDomain;
import com.success.lottery.report.domain.ReportSaleCount;
import com.success.lottery.report.domain.ReportTermDomain;

/**
 * com.success.lottery.report.dao
 * ReportPrizeDao.java
 * ReportPrizeDao
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-9 ����10:17:39
 * 
 */

public class ReportPrizeDao extends SqlMapClientDaoSupport implements
		Serializable {

	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = -8879228013318991554L;
	
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	/**
	 * 
	 * Title: getLeastCashTermInfo<br>
	 * Description: <br>
	 *              <br>���ݲ��ֻ�ȡlimitNumberָ�����Ѿ��ҽ��ĵĲ�����Ϣ
	 * @param lotteryId
	 * @return
	 */
	public List<ReportTermDomain> getLeastCashTermInfo(int lotteryId,int limitNumber){
		Map<String,Integer> params = new HashMap<String,Integer>();
		params.put("lotteryId", lotteryId);
		params.put("limitNumber", limitNumber);
		return this.smcTemplate.queryForList("reportPrize.getHaveCashTerm", params);
	}
	
	/**
	 * 
	 * Title: getPrizeIsCreated<br>
	 * Description: <br>
	 *              <br>�жϲ����Ƿ��Ѿ����ɹ�����
	 * @param termNo
	 * @return
	 */
	public Integer getPrizeIsCreated(String tableName,String termNo){
		Map<String,String> params = new HashMap<String,String>();
		params.put("tableName", tableName);
		params.put("termNo", termNo);
		return (Integer)this.smcTemplate.queryForObject("reportPrize.getPrizeIsCreated", params);
	}
	/**
	 * 
	 * Title: insertPrizeReportData<br>
	 * Description: <br>
	 *              <br>�����н�ͳ�Ʊ�������
	 * @param reportDomain
	 */
	public void insertPrizeReportData(ReportPrizeDomain reportDomain){
		this.smcTemplate.insert("reportPrize.insert_report_prize", reportDomain);
	}
	/**
	 * 
	 * Title: getSmallBigPrize<br>
	 * Description: <br>
	 *              <br>ͳ�ƴ�С�����
	 * @param lotteryIdList
	 * @param TermNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ReportSaleCount getSmallBigPrize(List<Integer> lotteryIdList,String termNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lotteryIdList", lotteryIdList);
		params.put("termNo", termNo);
		return (ReportSaleCount)this.smcTemplate.queryForObject("reportPrize.getSmallBigPrize", params);
	}
	
	/**
	 * 
	 * Title: getSaleVolumnByPlay<br>
	 * Description: <br>
	 *              <br>����Ͷע��ʽͳ������
	 * @param lotteryIdList
	 * @param TermNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportSaleCount> getSaleVolumnByBetType(List<Integer> lotteryIdList,String termNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lotteryIdList", lotteryIdList);
		params.put("termNo", termNo);
		return this.smcTemplate.queryForList("reportPrize.getSaleVolumnByBetType", params);
	}
	/**
	 * 
	 * Title: getSaleVolumnByPlayType<br>
	 * Description: <br>
	 *              <br>�����淨ͳ������
	 * @param lotteryIdList
	 * @param termNo
	 * @return
	 */
	public List<ReportSaleCount> getSaleVolumnByPlayType(List<Integer> lotteryIdList,String termNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lotteryIdList", lotteryIdList);
		params.put("termNo", termNo);
		return this.smcTemplate.queryForList("reportPrize.getSaleVolumnByPlayType", params);
	}
	
	/**
	 * 
	 * Title: getSaleVolumnByLottery<br>
	 * Description: <br>
	 *              <br>���ղ��֣�����ͳ������
	 * @param lotteryIdList
	 * @param TermNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportSaleCount> getSaleVolumnByLottery(List<Integer> lotteryIdList,String termNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lotteryIdList", lotteryIdList);
		params.put("termNo", termNo);
		return this.smcTemplate.queryForList("reportPrize.getSaleVolumnByLottery", params);
	}
	
	/**
	 * 
	 * Title: getFailTicketNum<br>
	 * Description: <br>
	 *              <br>ͳ��ʧ�ܲ�Ʊ������
	 * @param lotteryIdList
	 * @param termNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer getFailTicketNum(List<Integer> lotteryIdList,String termNo){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lotteryIdList", lotteryIdList);
		params.put("termNo", termNo);
		return (Integer)this.smcTemplate.queryForObject("reportPrize.getFailTicketNum", params);
	}

}
