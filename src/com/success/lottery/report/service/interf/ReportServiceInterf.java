/**
 * Title: ReportServiceInterf.java
 * @Package com.success.lottery.report.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 上午10:14:47
 * @version V1.0
 */
package com.success.lottery.report.service.interf;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.report.service.interf
 * ReportServiceInterf.java
 * ReportServiceInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 上午10:14:47
 * 
 */

public interface ReportServiceInterf {
	
	public static final int E_01_CODE = 800001;
	public static final String E_01_DESC = "生成报表失败";
	
	/**
	 * 
	 * Title: createPrizeReportSuper<br>
	 * Description: <br>
	 *              <br>生成大乐透中奖统计数据
	 * @throws LotteryException
	 */
	public void createPrizeReportSuper() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportBall<br>
	 * Description: <br>
	 *              <br>生成胜负彩中奖统计数据
	 * @throws LotteryException
	 */
	public void createPrizeReportBall() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportArrange3<br>
	 * Description: <br>
	 *              <br>生成排列3中奖统计数据
	 * @throws LotteryException
	 */
	public void createPrizeReportArrange3() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportArrange5<br>
	 * Description: <br>
	 *              <br>生成排列5中奖统计数据
	 * @throws LotteryException
	 */
	public void createPrizeReportArrange5() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportSeven<br>
	 * Description: <br>
	 *              <br>生成七星彩中奖统计数据
	 * @throws LotteryException
	 */
	public void createPrizeReportSeven() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportDlc<br>
	 * Description: <br>生成江西多乐彩中奖统计数据
	 *             <br>
	 * @throws LotteryException
	 */
	public void createPrizeReportDlc() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportBall6<br>
	 * Description: <br>
	 *              <br>生成6场半全场中奖统计数据
	 * @throws LotteryException
	 */
	public void createPrizeReportBall6() throws LotteryException;
	/**
	 * 
	 * Title: createPrizeReportBall4<br>
	 * Description: <br>
	 *              <br>生成4场进球彩中奖统计数据
	 * @throws LotteryException
	 */
	public void createPrizeReportBall4() throws LotteryException;
	/**
	 * 
	 * Title: createAccountReport<br>
	 * Description: <br>
	 *              <br>生成账户统计报表,该统计完全基于程序的运行时间
	 * @throws LotteryException
	 */
	public void createAccountReport() throws LotteryException;

}
