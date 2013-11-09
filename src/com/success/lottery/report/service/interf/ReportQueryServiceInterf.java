/**
 * Title: ReportQueryServiceInterf.java
 * @Package com.success.lottery.report.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-10 上午09:38:06
 * @version V1.0
 */
package com.success.lottery.report.service.interf;

import java.util.Date;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.report.domain.ReportAccount;
import com.success.lottery.report.domain.ReportPrizeDomain;
import com.success.lottery.report.domain.ReportTransSortDomain;

/**
 * com.success.lottery.report.service.interf
 * ReportQueryServiceInterf.java
 * ReportQueryServiceInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-10 上午09:38:06
 * 
 */

public interface ReportQueryServiceInterf {
	
	public static final int E_01_CODE = 800002;
	public static final String E_01_DESC = "查询报表出错";
	
	/**
	 * 
	 * Title: getPrizeReportTerms<br>
	 * Description: <br>
	 *              <br>根据彩种查询中奖统计报表中已经生成的彩期列表
	 * @param lotteryId
	 * @return
	 * @throws LotteryException
	 */
	public List<String> getPrizeReportTerms(int lotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: getPrizeReports<br>
	 * Description: <br>
	 *              <br>分页查询中奖统计报表数据
	 * @param lotteryId 彩种,不能有全部
	 * @param beginTermNo 彩期,如果为0查询全部
	 * @param endTermNo 彩期,如果为0查询全部
	 * @param pageIndex 
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<ReportPrizeDomain> getPrizeReports(int lotteryId,String beginTermNo,String endTermNo,int pageIndex, int perPageNumber)  throws LotteryException;
	/**
	 * 
	 * Title: getPrizeReportsCount<br>
	 * Description: <br>
	 *              <br>分页查询中奖统计的条数
	 * @param lotteryId
	 * @param beginTermNo
	 * @param endTermNo
	 * @return
	 * @throws LotteryException
	 */
	public int getPrizeReportsCount(int lotteryId,String beginTermNo,String endTermNo) throws LotteryException;
 	/**
	 * 
	 * Title: getPrizeReportInfo<br>
	 * Description: <br>
	 *              <br>根据彩种的报表主键查询报表的详细信息
	 * @param lotteryId
	 * @param reportId
	 * @return
	 * @throws LotteryException
	 */
	public ReportPrizeDomain getPrizeReportInfo(int lotteryId,int reportId) throws LotteryException;
	
	/**
	 * 
	 * Title: getAccountReports<br>
	 * Description: <br>
	 *              <br>根据开始日的结束日查询交易变动报表
	 * @param beginDate 开始日 格式yyyy-MM-dd
	 * @param endDate 结束日 格式yyyy-MM-dd
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<ReportAccount> getAccountReports(String beginDate,String endDate,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getAccountReportsCount<br>
	 * Description: <br>
	 *              <br>根据开始日的结束日查询交易变动报表的数据条数
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws LotteryException
	 */
	public int getAccountReportsCount(String beginDate,String endDate) throws LotteryException;
	/**
	 * 
	 * Title: getReportTransSortInfos<br>
	 * Description: <br>
	 *              <br>查询交易排名
	 * @param transType 排名类型
	 * @param maxSortNum 最大条数
	 * @param beginDate 开始日期  不能为空
	 * @param endDate 结束日期 不能为空
	 * @return
	 * @throws LotteryException
	 */
	public List<ReportTransSortDomain> getReportTransSortInfos(String transType,int maxSortNum,Date beginDate,Date endDate) throws LotteryException;
	/**
	 * 
	 * Title: getReportAccountInfo<br>
	 * Description: <br>
	 *              <br>查询一条交易信息
	 * @param reportId
	 * @return
	 * @throws LotteryException
	 */
	public ReportAccount getReportAccountInfo(String reportId) throws LotteryException;
	
	

}
