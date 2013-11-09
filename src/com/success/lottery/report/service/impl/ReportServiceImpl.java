/**
 * Title: ReportServiceImpl.java
 * @Package com.success.lottery.report.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-9 上午10:16:20
 * @version V1.0
 */
package com.success.lottery.report.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.report.dao.ReportAccountDao;
import com.success.lottery.report.dao.ReportPrizeDao;
import com.success.lottery.report.domain.ReportAccount;
import com.success.lottery.report.domain.ReportAccountCountDomain;
import com.success.lottery.report.domain.ReportPrizeDomain;
import com.success.lottery.report.domain.ReportSaleCount;
import com.success.lottery.report.domain.ReportTermDomain;
import com.success.lottery.report.service.ReportLog;
import com.success.lottery.report.service.ReportStaticDefine;
import com.success.lottery.report.service.interf.ReportServiceInterf;

/**
 * com.success.lottery.report.service.impl
 * ReportServiceImpl.java
 * ReportServiceImpl
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-9 上午10:16:20
 * 
 */

public class ReportServiceImpl implements ReportServiceInterf {
	
	private static final int DBLOG_40040 = 40040;//中奖统计,成功
	private static final int DBLOG_41040 = 41040;//失败
	private static final int DBLOG_40041 = 40041;
	private static final int DBLOG_41041 = 41041;
	
	private ReportLog logger = ReportLog.getInstance("PRIZE");
	
	private ReportLog logAccount = ReportLog.getInstance("ACCOUNT");
	
	private ReportPrizeDao reportPrizeDao;
	private ReportAccountDao reportAccountDao;
	
	/**
	 * 
	 * Title: createPrizeReportSuper<br>
	 * Description: <br>
	 *              <br>生成大乐透和生肖乐中奖统计数据
	 */
	public void createPrizeReportSuper() throws LotteryException{
		this.createPrizeReportByLottery(ReportStaticDefine.report_prize_super,1000001,1000005);
	}
	/**
	 * 
	 * Title: createPrizeReportBall<br>
	 * Description: <br>
	 *              <br>生成胜负彩和任意选9中奖统计数据
	 */
	public void createPrizeReportBall() throws LotteryException{
		this.createPrizeReportByLottery(ReportStaticDefine.report_prize_ball,1300001,1300002);//足彩，胜负彩，任选9
	}
	/**
	 * 
	 * Title: createPrizeReportArrange3<br>
	 * Description: <br>
	 *              <br>生成排列3中奖统计数据
	 */
	public void createPrizeReportArrange3() throws LotteryException {
		List<Integer> zhixuan = new ArrayList<Integer>();// 直选
		zhixuan.add(0);
		zhixuan.add(1);
		zhixuan.add(2);
		List<Integer> zuxuan = new ArrayList<Integer>();// 组选
		zuxuan.add(3);
		zuxuan.add(4);
		zuxuan.add(5);
		zuxuan.add(6);
		this.createPrizeReportByBetType(ReportStaticDefine.report_prize_arrange3, 1000003,zhixuan, zuxuan,1);
	}
	/**
	 * 
	 * Title: createPrizeReportArrange5<br>
	 * Description: <br>
	 * <br>
	 * 生成排列5中奖统计数据
	 */
	public void createPrizeReportArrange5() throws LotteryException{
		this.createPrizeReportByPlaySource(ReportStaticDefine.report_prize_arrange5, 1000004);
	}
	/**
	 * 
	 * Title: createPrizeReportSeven<br>
	 * Description: <br>
	 *              <br>生成七星彩中奖统计数据
	 */
	public void createPrizeReportSeven() throws LotteryException{
		this.createPrizeReportByPlaySource(ReportStaticDefine.report_prize_seven, 1000002);
	}
	/*
	 * (非 Javadoc)
	*Title: createPrizeReportBall4
	*Description: 
	* @throws LotteryException
	* @see com.success.lottery.report.service.interf.ReportServiceInterf#createPrizeReportBall4()
	 */
	public void createPrizeReportBall4() throws LotteryException {
		this.createPrizeReportByPlaySource(ReportStaticDefine.report_prize_ball4, 1300004);
	}
	/*
	 * (非 Javadoc)
	*Title: createPrizeReportBall6
	*Description: 
	* @throws LotteryException
	* @see com.success.lottery.report.service.interf.ReportServiceInterf#createPrizeReportBall6()
	 */
	public void createPrizeReportBall6() throws LotteryException {
		this.createPrizeReportByPlaySource(ReportStaticDefine.report_prize_ball6, 1300003);
	}
	/*
	 * (非 Javadoc)
	*Title: createPrizeReportDlc
	*Description: 
	* @throws LotteryException
	* @see com.success.lottery.report.service.interf.ReportServiceInterf#createPrizeReportDlc()
	 */
	public void createPrizeReportDlc() throws LotteryException {
		List<Integer> renXuan = new ArrayList<Integer>();//任选1到8
		renXuan.add(1);
		renXuan.add(2);
		renXuan.add(3);
		renXuan.add(4);
		renXuan.add(5);
		renXuan.add(6);
		renXuan.add(7);
		renXuan.add(8);
		List<Integer> zhiXuan = new ArrayList<Integer>();//直选
		zhiXuan.add(9);
		zhiXuan.add(10);
		List<Integer> zuXuan = new ArrayList<Integer>();//组选
		zuXuan.add(11);
		zuXuan.add(12);
		this.createPrizeReportByPlayType(ReportStaticDefine.report_prize_dlc, 1200001,renXuan, zhiXuan,zuXuan,65);
	}
	
	/**
	 * 
	 * Title: createBallPrizeReport<br>
	 * Description: <br>
	 *              <br>按照彩种分类统计中奖数据，生成中奖统计报表
	 * @throws LotteryException
	 */
	private void createPrizeReportByLottery(String tableName,int lotteryId,int addLotteryId) throws LotteryException{
		String termNo = "";
		try {
			List<ReportTermDomain> termInfo = this.getReportPrizeDao().getLeastCashTermInfo(lotteryId,1);
			if(termInfo != null && !termInfo.isEmpty()){
				for(ReportTermDomain oneTerm : termInfo){
					termNo = oneTerm.getTermNo();
					int isCreate = this.getReportPrizeDao().getPrizeIsCreated(tableName, termNo);
					
					if(isCreate == 0){//彩期数据没有生成过
						ReportPrizeDomain rePrizeDomain = new ReportPrizeDomain();
						rePrizeDomain.setTableName(tableName);
						rePrizeDomain.setTermNo(oneTerm.getTermNo());
						rePrizeDomain.setStartTime(oneTerm.getStartTime());
						rePrizeDomain.setDeadLine(oneTerm.getDeadLine());
						rePrizeDomain.setLotteryResult(oneTerm.getLotteryResult());
						
						List<Integer> lotteryIdList = new ArrayList<Integer>();
						lotteryIdList.add(lotteryId);
						lotteryIdList.add(addLotteryId);
						//添加销量数据
						this.addVolumeByLottery(rePrizeDomain, lotteryIdList, termNo, lotteryId, addLotteryId);
						//添加奖金数据
						this.addWinPrizeVolume(rePrizeDomain, lotteryIdList, termNo);
						
						//统计失败彩票数量
						this.addFailTicketCount(rePrizeDomain, lotteryIdList, termNo);
						
						//将数据写入数据库
						this.getReportPrizeDao().insertPrizeReportData(rePrizeDomain);
						logger.logInfo("中奖统计", lotteryId, termNo, "生成成功");
						this.dbLog(DBLOG_40040, lotteryId+"", termNo,addLotteryId+"", "");
					}else {
						logger.logInfo("中奖统计", lotteryId, termNo, "数据已经生成过，不再重复生成");
					}
					
				}
				
				
				
			}//end if(termInfo != null)
		} catch (Exception e) {
			logger.logInfo("中奖统计", lotteryId, termNo, "生成失败");
			this.dbLog(DBLOG_41040, lotteryId+"", termNo,addLotteryId+"", "中奖统计数据生成失败,失败原因:"+e.getMessage());
			throw new LotteryException(ReportServiceInterf.E_01_CODE,ReportServiceInterf.E_01_DESC);
		}
	}
	/**
	 * 
	 * Title: createPrizeReportByBetType<br>
	 * Description: <br>
	 *              <br>按照投注方式统计，生成中奖统计报表，目前用于排列3
	 * @param tableName
	 * @param betType1
	 * @param betType2
	 * @throws LotteryException
	 */
	private void createPrizeReportByBetType(String tableName,int lotteryId,List<Integer> betType1,List<Integer> betType2,int leastCashTerms) throws LotteryException{
		String termNo = "";
		try {
			List<ReportTermDomain> termInfo = this.getReportPrizeDao().getLeastCashTermInfo(lotteryId,leastCashTerms);
			
			if(termInfo != null && !termInfo.isEmpty()){
				for(ReportTermDomain oneTerm : termInfo){
					termNo = oneTerm.getTermNo();
					int isCreate = this.getReportPrizeDao().getPrizeIsCreated(tableName, termNo);
					
					if(isCreate == 0){//彩期数据没有生成过
						ReportPrizeDomain rePrizeDomain = new ReportPrizeDomain();
						rePrizeDomain.setTableName(tableName);
						rePrizeDomain.setTermNo(oneTerm.getTermNo());
						rePrizeDomain.setStartTime(oneTerm.getStartTime());
						rePrizeDomain.setDeadLine(oneTerm.getDeadLine());
						rePrizeDomain.setLotteryResult(oneTerm.getLotteryResult());
						
						List<Integer> lotteryIdList = new ArrayList<Integer>();
						lotteryIdList.add(lotteryId);
						
						//添加销量数据
						this.addVolumeByPlaySource(rePrizeDomain, lotteryIdList, termNo);
						
						//添加奖金数据
						this.addWinPrizeVolume(rePrizeDomain, lotteryIdList, termNo);
						
						//统计按照投注方式的销量
						List<ReportSaleCount> saleVolumeByPlayList = this.getReportPrizeDao().getSaleVolumnByBetType(lotteryIdList, termNo);
						
						if(saleVolumeByPlayList != null){
							for(ReportSaleCount volume : saleVolumeByPlayList){
								int betType = volume.getBetType();
								long betAmount = volume.getBetAmount();
								if(betType1.contains(betType)){
									rePrizeDomain.setVolume1(rePrizeDomain.getVolume1() + betAmount);
								}else if(betType2.contains(betType)){
									rePrizeDomain.setVolume2(rePrizeDomain.getVolume2() + betAmount);
								}
							}
						}
						//统计失败彩票数量
						this.addFailTicketCount(rePrizeDomain, lotteryIdList, termNo);
						
						//将数据写入数据库
						this.getReportPrizeDao().insertPrizeReportData(rePrizeDomain);
						logger.logInfo("中奖统计", lotteryId, termNo, "生成成功");
						this.dbLog(DBLOG_40040, lotteryId+"", termNo, "","");
					}else {
						logger.logInfo("中奖统计", lotteryId, termNo, "数据已经生成过，不再重复生成");
					}
				}
			}//end if(termInfo != null)
		} catch (Exception e) {
			logger.logInfo("中奖统计", lotteryId, termNo, "生成失败");
			this.dbLog(DBLOG_41040, lotteryId+"", termNo, "","中奖统计数据生成失败,失败原因:"+e.getMessage());
			throw new LotteryException(ReportServiceInterf.E_01_CODE,ReportServiceInterf.E_01_DESC);
		}
	}
	/**
	 * 
	 * Title: createPrizeReportByPlayType<br>
	 * Description: <br>
	 *              <br>按照玩法方式统计，生成中奖统计报表，用于江西多乐彩
	 * @param tableName
	 * @param lotteryId
	 * @param playType1
	 * @param playType2
	 * @param playType3
	 * @param leastCashTerms
	 * @throws LotteryException
	 */
	private void createPrizeReportByPlayType(String tableName,int lotteryId,List<Integer> playType1,List<Integer> playType2,List<Integer> playType3,int leastCashTerms) throws LotteryException{
		String termNo = "";
		try {
			List<ReportTermDomain> termInfo = this.getReportPrizeDao().getLeastCashTermInfo(lotteryId,leastCashTerms);
			
			if(termInfo != null && !termInfo.isEmpty()){
				for(ReportTermDomain oneTerm : termInfo){
					termNo = oneTerm.getTermNo();
					int isCreate = this.getReportPrizeDao().getPrizeIsCreated(tableName, termNo);
					
					if(isCreate == 0){//彩期数据没有生成过
						ReportPrizeDomain rePrizeDomain = new ReportPrizeDomain();
						rePrizeDomain.setTableName(tableName);
						rePrizeDomain.setTermNo(oneTerm.getTermNo());
						rePrizeDomain.setStartTime(oneTerm.getStartTime());
						rePrizeDomain.setDeadLine(oneTerm.getDeadLine());
						rePrizeDomain.setLotteryResult(oneTerm.getLotteryResult());
						
						List<Integer> lotteryIdList = new ArrayList<Integer>();
						lotteryIdList.add(lotteryId);
						
						//添加销量数据
						this.addVolumeByPlaySource(rePrizeDomain, lotteryIdList, termNo);
						
						//添加奖金数据
						this.addWinPrizeVolume(rePrizeDomain, lotteryIdList, termNo);
						
						//统计按照玩法方式的销量
						List<ReportSaleCount> saleVolumeByPlayList = this.getReportPrizeDao().getSaleVolumnByPlayType(lotteryIdList, termNo);
						
						if(saleVolumeByPlayList != null){
							for(ReportSaleCount volume : saleVolumeByPlayList){
								int playType = volume.getBetType();
								long betAmount = volume.getBetAmount();
								if(playType1.contains(playType)){
									rePrizeDomain.setVolume1(rePrizeDomain.getVolume1() + betAmount);
								}else if(playType2.contains(playType)){
									rePrizeDomain.setVolume2(rePrizeDomain.getVolume2() + betAmount);
								}else if(playType3.contains(playType)){
									rePrizeDomain.setVolume2(rePrizeDomain.getVolume3() + betAmount);
								}
							}
						}
						//统计失败彩票数量
						this.addFailTicketCount(rePrizeDomain, lotteryIdList, termNo);
						
						//将数据写入数据库
						this.getReportPrizeDao().insertPrizeReportData(rePrizeDomain);
						logger.logInfo("中奖统计", lotteryId, termNo, "生成成功");
						this.dbLog(DBLOG_40040, lotteryId+"", termNo, "","");
					}else {
						logger.logInfo("中奖统计", lotteryId, termNo, "数据已经生成过，不再重复生成");
					}
				}
			}//end if(termInfo != null)
		} catch (Exception e) {
			logger.logInfo("中奖统计", lotteryId, termNo, "生成失败");
			this.dbLog(DBLOG_41040, lotteryId+"", termNo, "","中奖统计数据生成失败,失败原因:"+e.getMessage());
			throw new LotteryException(ReportServiceInterf.E_01_CODE,ReportServiceInterf.E_01_DESC);
		}
	}
	/**
	 * 
	 * Title: createPrizeReportByPlaySource<br>
	 * Description: <br>
	 *              <br>直接按照销售渠道统计销量，目前排列5和七星彩使用
	 * @param tableName
	 * @param lotteryId
	 * @throws LotteryException
	 */
	private void createPrizeReportByPlaySource(String tableName,int lotteryId) throws LotteryException{
		String termNo = "";
		try {
			List<ReportTermDomain> termInfo = this.getReportPrizeDao().getLeastCashTermInfo(lotteryId,1);
			if(termInfo != null && !termInfo.isEmpty()){
				for(ReportTermDomain oneTerm : termInfo){
					termNo = oneTerm.getTermNo();
					
					int isCreate = this.getReportPrizeDao().getPrizeIsCreated(tableName, termNo);
					if(isCreate == 0){//彩期数据没有生成过
						ReportPrizeDomain rePrizeDomain = new ReportPrizeDomain();
						rePrizeDomain.setTableName(tableName);
						rePrizeDomain.setTermNo(oneTerm.getTermNo());
						rePrizeDomain.setStartTime(oneTerm.getStartTime());
						rePrizeDomain.setDeadLine(oneTerm.getDeadLine());
						rePrizeDomain.setLotteryResult(oneTerm.getLotteryResult());
						
						List<Integer> lotteryIdList = new ArrayList<Integer>();
						lotteryIdList.add(lotteryId);
						
						//添加销量数据
						this.addVolumeByPlaySource(rePrizeDomain, lotteryIdList, termNo);
						
						//添加奖金数据
						this.addWinPrizeVolume(rePrizeDomain, lotteryIdList, termNo);
						
						//统计失败彩票数量
						this.addFailTicketCount(rePrizeDomain, lotteryIdList, termNo);
						
						//将数据写入数据库
						this.getReportPrizeDao().insertPrizeReportData(rePrizeDomain);
						logger.logInfo("中奖统计", lotteryId, termNo, "生成成功");
						this.dbLog(DBLOG_40040, lotteryId+"", "",termNo, "");
					}else {
						logger.logInfo("中奖统计", lotteryId, termNo, "数据已经生成过，不再重复生成");
					}
				}
				
				
				
			}//end if(termInfo != null)
		} catch (Exception e) {
			logger.logInfo("中奖统计", lotteryId, termNo, "生成失败");
			this.dbLog(DBLOG_41040, lotteryId+"", termNo,"", "中奖统计数据生成失败,失败原因:"+e.getMessage());
			throw new LotteryException(ReportServiceInterf.E_01_CODE,ReportServiceInterf.E_01_DESC);
		}
	}
	
	/**
	 * 
	 * Title: addVolumeByPlaySource<br>
	 * Description: <br>
	 *              <br>按照销售渠道统计销售情况,目前用于排列3，排列5，七星彩
	 * @param rePrizeDomain
	 * @param lotteryIdList
	 * @param termNo
	 */
	private void addVolumeByPlaySource(ReportPrizeDomain rePrizeDomain,List<Integer> lotteryIdList,String termNo){
		//按照彩种、渠道统计销量，0 web，1 sms，2 wap，3 client
		List<ReportSaleCount> volumeByLotteryList = this.getReportPrizeDao().getSaleVolumnByLottery(lotteryIdList, termNo);
		if(volumeByLotteryList != null){
			for (ReportSaleCount volume : volumeByLotteryList) {
				int playSource = volume.getPlanSource();
				long betAmount = volume.getBetAmount();
				if (playSource == 0) {// web
					rePrizeDomain.setWebVolume(rePrizeDomain.getWebVolume() + betAmount);
				} else if (playSource == 1) {// sms
					rePrizeDomain.setSmsVolume(rePrizeDomain.getSmsVolume() + betAmount);
				} else if (playSource == 2) {// wap
					rePrizeDomain.setWapVolume(rePrizeDomain.getWapVolume() + betAmount);
				} else if (playSource == 3) {// client
					rePrizeDomain.setClientVoulme(rePrizeDomain.getClientVoulme() + betAmount);
				}
				rePrizeDomain.setSaleVolume(rePrizeDomain.getSaleVolume() + betAmount);//总的销量

			}//end for
		}
	}
	
	/**
	 * 
	 * Title: addVolumeByLottery<br>
	 * Description: <br>
	 *              <br>按照彩种、销售渠道添加报表的销量数据
	 * @param rePrizeDomain
	 * @param lotteryIdList
	 * @param termNo
	 * @param lotteryVolume1
	 * @param lotteryVolume2
	 */
	private void addVolumeByLottery(ReportPrizeDomain rePrizeDomain,List<Integer> lotteryIdList,String termNo,int lotteryVolume1,int lotteryVolume2){
		//按照彩种、渠道统计销量，0 web，1 sms，2 wap，3 client
		List<ReportSaleCount> volumeByLotteryList = this.getReportPrizeDao().getSaleVolumnByLottery(lotteryIdList, termNo);
		if(volumeByLotteryList != null){
			for (ReportSaleCount volume : volumeByLotteryList) {
				int playSource = volume.getPlanSource();
				long betAmount = volume.getBetAmount();
				if (playSource == 0) {// web
					rePrizeDomain.setWebVolume(rePrizeDomain.getWebVolume() + betAmount);
				} else if (playSource == 1) {// sms
					rePrizeDomain.setSmsVolume(rePrizeDomain.getSmsVolume() + betAmount);
				} else if (playSource == 2) {// wap
					rePrizeDomain.setWapVolume(rePrizeDomain.getWapVolume() + betAmount);
				} else if (playSource == 3) {// client
					rePrizeDomain.setClientVoulme(rePrizeDomain.getClientVoulme() + betAmount);
				}
				if (volume.getLotteryId() == lotteryVolume1) {//彩种1
					rePrizeDomain.setVolume1(rePrizeDomain.getVolume1() + betAmount);
				} else if (volume.getLotteryId() == lotteryVolume2) {//彩种2
					rePrizeDomain.setVolume2(rePrizeDomain.getVolume2() + betAmount);
				}

			}//end for
			rePrizeDomain.setSaleVolume(rePrizeDomain.getVolume1() + rePrizeDomain.getVolume2());//总的销量
		}
	}
	/**
	 * 
	 * Title: addWinPrizeVolume<br>
	 * Description: <br>
	 *              <br>添加报表的中奖奖金数据
	 * @param rePrizeDomain
	 * @param lotteryIdList
	 * @param termNo
	 */
	private void addWinPrizeVolume(ReportPrizeDomain rePrizeDomain,List<Integer> lotteryIdList,String termNo){
		//统计中奖情况,2 小奖，3 大奖
		ReportSaleCount volumnPrize = this.getReportPrizeDao().getSmallBigPrize(lotteryIdList, termNo);
		if(volumnPrize != null){
			rePrizeDomain.setTotalPrizeAmount(volumnPrize.getPretaxprize());
			rePrizeDomain.setAftTaxPrize(volumnPrize.getAftTaxPrize());
			rePrizeDomain.setTaxPrize(volumnPrize.getTaxPrize());
			rePrizeDomain.setTiChengPrize(volumnPrize.getTiChengPrize());
			rePrizeDomain.setCommPrize(volumnPrize.getCommPrize());
			rePrizeDomain.setSmallAmount(volumnPrize.getSmallPrize());
			rePrizeDomain.setBigAmount(volumnPrize.getBigPrize());
		}
	}
	/**
	 * 
	 * Title: addFailTicketCount<br>
	 * Description: <br>
	 *              <br>添加报表的失败彩票数据
	 * @param rePrizeDomain
	 * @param lotteryIdList
	 * @param termNo
	 */
	private void addFailTicketCount(ReportPrizeDomain rePrizeDomain,List<Integer> lotteryIdList,String termNo){
		int failTicketNum = this.getReportPrizeDao().getFailTicketNum(lotteryIdList, termNo);
		rePrizeDomain.setFailTicketCount(failTicketNum);
	}
	
	/*
	 * (非 Javadoc)
	*Title: createAccountReport
	*Description: 
	* @throws LotteryException
	* @see com.success.lottery.report.service.interf.ReportServiceInterf#createAccountReport()
	 */
	public void createAccountReport() throws LotteryException {
		String accountDate = "";
		Timestamp endTime = null;
		Timestamp beginTime = null;
		String beginTimeStr = "";
		String endTimeStr = "";
		
		try {
			ReportAccount reportAccountInfo = this.getReportAccountDao().getReportAccountCount();//账户统计对象，要写回数据库的
			if(reportAccountInfo != null){
				endTime = reportAccountInfo.getReportTime();
				long lastDayTime = endTime.getTime() - 24*60*60*1000;
				long curDayTime = endTime.getTime() - 2*60*60*1000;
				beginTime = new Timestamp(lastDayTime);
				Date curDay = new Date(curDayTime);
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
				accountDate = format.format(curDay);
				beginTimeStr = beginTime == null ? "" : ft.format(beginTime);
				endTimeStr = endTime == null ? "" : ft.format(endTime);
				int isCreated = this.getReportAccountDao().getReportIsCreateCount(accountDate);
				if(isCreated == 0){//没有生成改天的数据
					reportAccountInfo.setAccountDate(accountDate);//无法定义
					reportAccountInfo.setBeginTime(beginTime);
					reportAccountInfo.setEndTime(endTime);
					//按照交易类型和交易渠道统计交易变动
					List<ReportAccountCountDomain> accountTransCount = this.getReportAccountDao().getReportAccountTransaction(beginTime, endTime);
					if(accountTransCount != null){
						for(ReportAccountCountDomain accountTrs : accountTransCount){
							int transactionType = accountTrs.getTransactionType();
							int sourceType = accountTrs.getSourceType();
							long amount = accountTrs.getAmount();
							switch (transactionType) {
							case 11001://充值
								if(sourceType == 1004){//快钱充值
									reportAccountInfo.setIpsAmount(String.valueOf(amount));
								}
								break;
							case 11002://派奖
								reportAccountInfo.setDispatchAmount(String.valueOf(amount));
								break;
							case 11005://账户增加调整
								reportAccountInfo.setAdjustAdd(String.valueOf(amount));
								break;
							case 31003:
							case 31005:
							case 31009:
							case 31006://追号撤销
								reportAccountInfo.setZhuihaoCancel(String.valueOf(Long.parseLong(reportAccountInfo.getZhuihaoCancel())+amount));
								break;
							case 30002:
							case 30003:
							case 30006:
							case 10002://合买、代购购彩
								reportAccountInfo.setBetAmount(String.valueOf(Long.parseLong(reportAccountInfo.getBetAmount()) + amount));
								break;
							case 30001://追号购彩
								reportAccountInfo.setBetAmount(String.valueOf(Long.parseLong(reportAccountInfo.getBetAmount()) + amount));
								break;
							case 20007://提现申请
								reportAccountInfo.setDrawRequest(String.valueOf(amount));
								break;
							case 31007://提现拒绝
								reportAccountInfo.setDrawReject(String.valueOf(amount));
								break;
							case 30004://提现完成
								reportAccountInfo.setDrawAgree(String.valueOf(amount));
								break;
							case 10001://账户减少调整
								reportAccountInfo.setAdjustReduce(String.valueOf(amount));
								break;
							case 20003:
							case 20005:
							case 20009:
							case 20002://合买、追号冻结
								reportAccountInfo.setTrackAccount(String.valueOf(Long.parseLong(reportAccountInfo.getTrackAccount())+amount));
								break;
							default:
								break;
							}
						}
					}
					//将数据写入数据库
					this.getReportAccountDao().insertAccountReportData(reportAccountInfo);
					logAccount.logInfo("账户变动统计", accountDate, beginTime,endTime, "生成成功");
					this.dbLog(DBLOG_40041, beginTimeStr, endTimeStr,"", "");
				}else{
					logAccount.logInfo("账户变动统计", accountDate, beginTime,endTime, "数据已经生成过,不再重复生成");
				}
				
			}
		} catch (Exception e) {
			logAccount.logInfo("账户变动统计", accountDate, beginTime,endTime, "生成失败");
			this.dbLog(DBLOG_41041, beginTimeStr, endTimeStr, "","账户变动统计数据生成失败");
			e.printStackTrace();
			throw new LotteryException(ReportServiceInterf.E_01_CODE,ReportServiceInterf.E_01_DESC);
		}
	}
	/**
	 * 
	 * Title: getDbLogUser<br>
	 * Description: <br>
	 *              <br>数据库日志记录
	 * @return
	 */
	private void dbLog(int logId,String keyword1,String keyword2,String keyword3,String errorMessage){
		try{
			Map<String, String> param = new HashMap<String,String>();
			param.put("userId", "0");
			param.put("userName", "Robot");
			param.put("userKey", "");
			param.put("keyword1", keyword1);
			param.put("keyword2", keyword2);
			if(StringUtils.isNotEmpty(keyword3)){
				param.put("keyword3", keyword3);
			}
			if(StringUtils.isNotEmpty(errorMessage)){
				param.put("errorMessage", errorMessage);
			}
			OperatorLogger.log(logId, param);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ReportAccountDao getReportAccountDao() {
		return reportAccountDao;
	}
	public void setReportAccountDao(ReportAccountDao reportAccountDao) {
		this.reportAccountDao = reportAccountDao;
	}
	public ReportPrizeDao getReportPrizeDao() {
		return reportPrizeDao;
	}
	public void setReportPrizeDao(ReportPrizeDao reportPrizeDao) {
		this.reportPrizeDao = reportPrizeDao;
	}
}
