/**
 * Title: ReportQueryServiceImpl.java
 * @Package com.success.lottery.report.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-10 上午09:49:29
 * @version V1.0
 */
package com.success.lottery.report.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.report.dao.ReportQueryDao;
import com.success.lottery.report.dao.ReportTransSortDao;
import com.success.lottery.report.domain.ReportAccount;
import com.success.lottery.report.domain.ReportPrizeDomain;
import com.success.lottery.report.domain.ReportTransSortDomain;
import com.success.lottery.report.service.ReportStaticDefine;
import com.success.lottery.report.service.interf.ReportQueryServiceInterf;

/**
 * com.success.lottery.report.service.impl
 * ReportQueryServiceImpl.java
 * ReportQueryServiceImpl
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-10 上午09:49:29
 * 
 */

public class ReportQueryServiceImpl implements ReportQueryServiceInterf {
	
	private ReportQueryDao reportQuDao;
	
	private ReportTransSortDao reportTransDao;

	/* (非 Javadoc)
	 *Title: getAccountReports
	 *Description: 
	 * @param beginDate
	 * @param endDate
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.report.service.interf.ReportQueryServiceInterf#getAccountReports(java.lang.String, java.lang.String, int, int)
	 */
	public List<ReportAccount> getAccountReports(String beginDate,
			String endDate, int pageIndex, int perPageNumber)
			throws LotteryException {
		List<ReportAccount> result = null;
		try {
			result = this.getReportQuDao().getAccountReports(beginDate, endDate, pageIndex, perPageNumber);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryException(ReportQueryServiceInterf.E_01_CODE,
					ReportQueryServiceInterf.E_01_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getAccountReportsCount
	*Description: 
	* @param beginDate
	* @param endDate
	* @return
	* @throws LotteryException
	* @see com.success.lottery.report.service.interf.ReportQueryServiceInterf#getAccountReportsCount(java.lang.String, java.lang.String)
	 */
	public int getAccountReportsCount(String beginDate, String endDate) throws LotteryException {
		int result = 0;
		try {
			result = this.getReportQuDao().getAccountReportsCount(beginDate, endDate);
		} catch (Exception e) {
			throw new LotteryException(ReportQueryServiceInterf.E_01_CODE,
					ReportQueryServiceInterf.E_01_DESC);
		}
		return result;
	}

	/* (非 Javadoc)
	 *Title: getPrizeReportInfo
	 *Description: 
	 * @param lotteryId
	 * @param reportId
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.report.service.interf.ReportQueryServiceInterf#getPrizeReportInfo(int, int)
	 */
	public ReportPrizeDomain getPrizeReportInfo(int lotteryId, int reportId)
			throws LotteryException {
		ReportPrizeDomain result = null;
		try {
			String tableName = ReportStaticDefine.lotteryToReportTable.get(lotteryId);
			result = this.getReportQuDao().getPrizeReportInfo(tableName, reportId);
		} catch (Exception e) {
			throw new LotteryException(ReportQueryServiceInterf.E_01_CODE,
					ReportQueryServiceInterf.E_01_DESC);
		}
		return result;
	}

	/* (非 Javadoc)
	 *Title: getPrizeReportTerms
	 *Description: 
	 * @param lotteryId
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.report.service.interf.ReportQueryServiceInterf#getPrizeReportTerms(int)
	 */
	public List<String> getPrizeReportTerms(int lotteryId)
			throws LotteryException {
		List<String> terms = null;
		try {
			String tableName = ReportStaticDefine.lotteryToReportTable.get(lotteryId);
			terms =  this.getReportQuDao().getReportPrizeTerms(tableName);
		} catch (Exception e) {
			throw new LotteryException(ReportQueryServiceInterf.E_01_CODE,ReportQueryServiceInterf.E_01_DESC);
		}
		return terms;
	}
	/*
	 * (非 Javadoc)
	*Title: getPrizeReports
	*Description: 
	* @param lotteryId
	* @param beginTermNo
	* @param endTermNo
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.report.service.interf.ReportQueryServiceInterf#getPrizeReports(int, java.lang.String, java.lang.String, int, int)
	 */
	public List<ReportPrizeDomain> getPrizeReports(int lotteryId,
			String beginTermNo, String endTermNo, int pageIndex,
			int perPageNumber) throws LotteryException {
		List<ReportPrizeDomain> result = null;
		try {
			String tableName = ReportStaticDefine.lotteryToReportTable.get(lotteryId);
			result = this.getReportQuDao().getPrizeReports(tableName, beginTermNo, endTermNo, pageIndex, perPageNumber);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryException(ReportQueryServiceInterf.E_01_CODE,
					ReportQueryServiceInterf.E_01_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getPrizeReportsCount
	*Description: 
	* @param lotteryId
	* @param beginTermNo
	* @param endTermNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.report.service.interf.ReportQueryServiceInterf#getPrizeReportsCount(int, java.lang.String, java.lang.String)
	 */
	public int getPrizeReportsCount(int lotteryId, String beginTermNo, String endTermNo) throws LotteryException {
		int result = 0;
		try {
			String tableName = ReportStaticDefine.lotteryToReportTable.get(lotteryId);
			result = this.getReportQuDao().getPrizeReportsCount(tableName, beginTermNo, endTermNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryException(ReportQueryServiceInterf.E_01_CODE,
					ReportQueryServiceInterf.E_01_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getReportTransSortInfos
	*Description: 
	* @param transType
	* @param maxSortNum
	* @param beginDate
	* @param endDate
	* @return
	* @throws LotteryException
	* @see com.success.lottery.report.service.interf.ReportQueryServiceInterf#getReportTransSortInfos(java.lang.String, int, java.util.Date, java.util.Date)
	 */
	public List<ReportTransSortDomain> getReportTransSortInfos(String transType, int maxSortNum, Date beginDate, Date endDate) throws LotteryException {
		List<ReportTransSortDomain> result = null;
		try {
			if(StringUtils.isNotEmpty(transType)){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Timestamp startTime = null, endTime = null;
				startTime = Timestamp.valueOf(format.format(beginDate) + " 00:00:00");
				endTime = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
				
				if(transType.equals(ReportStaticDefine.bet_Sort)){//投注
					result = this.getReportTransDao().getBetTransSortReport(startTime, endTime, maxSortNum);
				}else if(transType.equals(ReportStaticDefine.prize_Sort)){//中奖
					result = this.getReportTransDao().getPrizeTransSortReport(startTime, endTime, maxSortNum);
				}else if(transType.equals(ReportStaticDefine.chongZhi_Sort)){//充值
					result = this.getReportTransDao().getChongZhiTransSortReport(startTime, endTime, maxSortNum);
				}else if(transType.equals(ReportStaticDefine.draw_Sort)){//提现
					result = this.getReportTransDao().getDrawTransSortReport(startTime, endTime, maxSortNum);
				}
			}
		} catch (Exception e) {
			throw new LotteryException(ReportQueryServiceInterf.E_01_CODE,
					ReportQueryServiceInterf.E_01_DESC);
		}
		return result;
	}
	
	/*
	 * (非 Javadoc)
	*Title: getReportAccountInfo
	*Description: 
	* @param reportId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.report.service.interf.ReportQueryServiceInterf#getReportAccountInfo(java.lang.String)
	 */
	public ReportAccount getReportAccountInfo(String reportId) throws LotteryException {
		ReportAccount result = null;
		try {
			result = this.getReportQuDao().getAccountReportInfo(reportId);
		} catch (Exception e) {
			throw new LotteryException(ReportQueryServiceInterf.E_01_CODE,
					ReportQueryServiceInterf.E_01_DESC);
		}
		return result;
	}
	
	public ReportQueryDao getReportQuDao() {
		return reportQuDao;
	}

	public void setReportQuDao(ReportQueryDao reportQuDao) {
		this.reportQuDao = reportQuDao;
	}
	public ReportTransSortDao getReportTransDao() {
		return reportTransDao;
	}
	public void setReportTransDao(ReportTransSortDao reportTransDao) {
		this.reportTransDao = reportTransDao;
	}
	
}
