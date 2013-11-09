/**
 * Title: ReportPrizeDao.java
 * @Package com.success.lottery.report.dao
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 上午10:17:39
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
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 上午10:17:39
 * 
 */

public class ReportPrizeDao extends SqlMapClientDaoSupport implements
		Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -8879228013318991554L;
	
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	/**
	 * 
	 * Title: getLeastCashTermInfo<br>
	 * Description: <br>
	 *              <br>根据彩种获取limitNumber指定的已经兑奖的的彩期信息
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
	 *              <br>判断彩期是否已经生成过数据
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
	 *              <br>生成中奖统计报表数据
	 * @param reportDomain
	 */
	public void insertPrizeReportData(ReportPrizeDomain reportDomain){
		this.smcTemplate.insert("reportPrize.insert_report_prize", reportDomain);
	}
	/**
	 * 
	 * Title: getSmallBigPrize<br>
	 * Description: <br>
	 *              <br>统计大奖小奖情况
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
	 *              <br>按照投注方式统计销量
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
	 *              <br>按照玩法统计销量
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
	 *              <br>按照彩种，渠道统计销量
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
	 *              <br>统计失败彩票的数量
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
