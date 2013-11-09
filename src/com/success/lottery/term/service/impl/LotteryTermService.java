/**
 * @Package com.success.lottery.term.service.impl
 * @author gaoboqin
 * @date 2010-4-7 上午09:48:21
 * @version V1.0
 */
package com.success.lottery.term.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.dao.impl.LotteryTermDaoImpl;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.LotteryInfo;
import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.term.service.impl
 * LotteryTermService.java
 * LotteryTermService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-7 上午09:48:21
 * 
 */

public class LotteryTermService implements LotteryTermServiceInterf {
	private static Log logger = LogFactory.getLog(LotteryTermService.class);
	
	private LotteryTermDaoImpl lotteryTermDao;

	/* (非 Javadoc)
	 *Title: queryTermInfo
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryTermInfo(int, java.lang.String)
	 */
	public LotteryTermModel queryTermInfo(int lotteryId, String termNo)
			throws LotteryException {
		LotteryTermModel retLotteryModel = null;
		try {
			LotteryTermModel lotteryModel = new LotteryTermModel();
			lotteryModel.setLotteryId(lotteryId);
			lotteryModel.setTermNo(termNo);
			retLotteryModel = getLotteryTermDao().queryTermInfo(lotteryModel);
			if(retLotteryModel == null){
				logger.error("获取彩期信息出错！错误原因：未找到彩种："+lotteryId+" 期数："+termNo);
				//throw new LotteryException(E_1004_CODE,E_1004_DESC);
			}else{
				//处理彩种的附加信息
				retLotteryModel.setLotteryName(LotteryTools.getLotteryName(lotteryId));//中文名称
				retLotteryModel.setLotteryInfo(LotteryTools.splitTermAllResult(
						lotteryId, retLotteryModel.getLotteryResult(), retLotteryModel.getWinResult(),
						retLotteryModel.getSalesInfo(),retLotteryModel.getMissCount()));//彩种的开奖信息，奖金结果，销售信息,遗漏信息
			}
			
		} catch (Exception e) {
			logger.error("获取彩期信息出错！错误原因：" + e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return retLotteryModel;
	}

	/* (非 Javadoc)
	 *Title: queryTermInfoList
	 *Description: 
	 * @param lotteryId
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryTermInfoList(int)
	 */
	public List<LotteryTermModel> queryTermInfoList(int lotteryId,int startPageSize,int pageSize)
			throws LotteryException {
		List<LotteryTermModel> retList = null;
		try {
			retList = getLotteryTermDao().queryTermInfoList(lotteryId, startPageSize, pageSize);
			for(LotteryTermModel lotteryTerm : retList){
				lotteryTerm.setLotteryName(LotteryTools.getLotteryName(lotteryId));
			}
		} catch (Exception e) {
			logger.error("获取"+lotteryId+"彩种的所有期数信息！错误原因：" + e.getMessage());
			throw new LotteryException(E_1006_CODE,E_1006_DESC + lotteryId);
		}
		return retList;
	}

	/* (非 Javadoc)
	 *Title: queryTermNoInfo
	 *Description: 
	 * @param lotteryId
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryTermNoInfo(int)
	 */
	public LotteryTermModel queryTermCurrentInfo(int lotteryId)
			throws LotteryException {
		LotteryTermModel retLotteryModel = null;
		LotteryTermModel lotteryLastCashInfo = null;
		try {
			retLotteryModel = getLotteryTermDao().queryTermCurrentInfo(lotteryId);
			if(retLotteryModel != null){
				retLotteryModel.setLotteryName(LotteryTools.getLotteryName(lotteryId));
				lotteryLastCashInfo = this.getLotteryTermDao().queryTermLastCashInfo(lotteryId);
				if(lotteryLastCashInfo != null){
					retLotteryModel.setBeforeWinTermNo(lotteryLastCashInfo.getTermNo());
					retLotteryModel.setMissCount(lotteryLastCashInfo.getMissCount());
					retLotteryModel.setSalesVolume(lotteryLastCashInfo.getSalesVolume());
					retLotteryModel.setJackpot(lotteryLastCashInfo.getJackpot());
					retLotteryModel.setLotteryResult(lotteryLastCashInfo.getLotteryResult());
					retLotteryModel.setWinResult(lotteryLastCashInfo.getWinResult());
					retLotteryModel.setLotteryInfo(LotteryTools.splitTermAllResult(
							lotteryId, lotteryLastCashInfo.getLotteryResult(), lotteryLastCashInfo.getWinResult(),
							retLotteryModel.getSalesInfo(),lotteryLastCashInfo.getMissCount()));//彩种的开奖信息，奖金结果，销售信息,遗漏信息
				}
			}else {
				logger.info("未找到彩种："+lotteryId+"的当前期信息!");
				throw new LotteryException(E_0005_CODE,E_0005_DESC +lotteryId);
			}
		} catch (Exception e) {
			logger.error("获取彩期当前期信息出错！错误原因：" + e.getMessage());
			if(e instanceof LotteryException) {
				throw (LotteryException)e;
			}else{
				throw new LotteryException(E_1005_CODE,E_1005_DESC);
			}
		}
		return retLotteryModel;
	}
	/*
	 * (非 Javadoc)
	*Title: queryTermCurrentInfo
	*Description: 
	* @param lotteryId
	* @param nextTermNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryTermCurrentInfo(int, int)
	 */
	public List<LotteryTermModel> queryTermCurrentInfo(int lotteryId, int nextTermNum) throws LotteryException {
		List<LotteryTermModel> result = null;
		LotteryTermModel lastCashInfo = null;
		try {
			result = getLotteryTermDao().queryTermCurrentInfo(lotteryId, nextTermNum);
			if(result != null){
				lastCashInfo = this.getLotteryTermDao().queryTermLastCashInfo(lotteryId);
				String lotteryName = LotteryTools.getLotteryName(lotteryId);
				for(LotteryTermModel oneTerm : result){
					oneTerm.setLotteryName(lotteryName);
					if(lastCashInfo != null){
						oneTerm.setBeforeWinTermNo(lastCashInfo.getTermNo());
						oneTerm.setMissCount(lastCashInfo.getMissCount());
						oneTerm.setSalesVolume(lastCashInfo.getSalesVolume());
						oneTerm.setJackpot(lastCashInfo.getJackpot());
						oneTerm.setLotteryResult(lastCashInfo.getLotteryResult());
						oneTerm.setWinResult(lastCashInfo.getWinResult());
						oneTerm.setLotteryInfo(LotteryTools.splitTermAllResult(
								lotteryId, lastCashInfo.getLotteryResult(), lastCashInfo.getWinResult(),
								oneTerm.getSalesInfo(),lastCashInfo.getMissCount()));//彩种的开奖信息，奖金结果，销售信息,遗漏信息
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取彩期当前期信息出错！错误原因：" + e.getMessage());
			throw new LotteryException(E_1005_CODE,E_1005_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: queryLastTermInfo
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryLastTermInfo(int, java.lang.String)
	 */
	public LotteryTermModel queryLastTermInfo(int lotteryId, String termNo) throws LotteryException {
		LotteryTermModel retLotteryModel = null;
		try {
			retLotteryModel = this.getLotteryTermDao().queryLastTermInfo(lotteryId, termNo);
			if(retLotteryModel == null){
				logger.error("获取彩期信息出错！错误原因：未找到彩种："+lotteryId+" 期数："+termNo+"的上一期信息");
				throw new LotteryException(E_1004_CODE,E_1004_DESC);
			}
			//处理彩种的附加信息
			retLotteryModel.setLotteryName(LotteryTools.getLotteryName(lotteryId));//中文名称
		} catch (Exception e) {
			logger.error("获取彩期信息出错！错误原因：",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return retLotteryModel;
	}
	/* (非 Javadoc)
	 *Title: updateTermSaleStatus
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param saleStatus
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#updateTermSaleStatus(int, java.lang.String, int)
	 */
	public boolean updateTermSaleStatus(int lotteryId, String termNo,
			int saleStatus) throws LotteryException {
		// TODO 自动生成方法存根
		return false;
	}

	/*
	 * (非 Javadoc)
	*Title: updateTermSalesInfo
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param beginSaleTime
	* @param endSaleTime
	* @param infoMap
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#updateTermSalesInfo(int, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	public int updateTermSalesInfo(LotteryTermModel termInfo) throws LotteryException {
		int result = 0;
		try {
			if(termInfo == null ){
				return 0;
			}
			//校验只有足彩可以更新销售信息
			if(!LotteryTools.getSoccerLotteryList().containsKey(termInfo.getLotteryId())){
				logger.info("只有足彩可以更新销售信息["+LotteryTools.getLotteryName(termInfo.getLotteryId())+"]");
				return 0;
			}
			result = getLotteryTermDao().updateTermSalesInfo(termInfo);
		} catch (Exception e) {
			logger.error("更新彩期销售信息出错！错误原因：",e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(E_1002_CODE,E_1002_DESC);
			}
		}
		return result;
	}
	
	

	/* (非 Javadoc)
	 *Title: updateTermStatus
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param termStatus
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#updateTermStatus(int, java.lang.String, int)
	 */
	public int updateTermStatus(int lotteryId, String termNo, int termStatus)
			throws LotteryException {
		int result = 0;
		try {
			LotteryTermModel lotteryModel = new LotteryTermModel();
			lotteryModel.setLotteryId(lotteryId);
			lotteryModel.setTermNo(termNo);
			lotteryModel.setTermStatus(termStatus);
			result = getLotteryTermDao().updateTermStatus(lotteryModel);
		} catch (Exception e) {
			logger.error("更新彩期状态出错！错误原因：" + e.getMessage());
			throw new LotteryException(E_1001_CODE,E_1001_DESC);
		}
		return result;
	}

	/*
	 * (非 Javadoc)
	*Title: updateTermWinInfo
	*Description: 
	* @param lotteryId
	* @param term
	* @param lotteryResult
	* @param lotteryResultPlus
	* @param salesVolume
	* @param jackpot
	* @param winResult
	* @param missCount
	* @return int
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#updateTermWinInfo(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int updateTermWinInfo(int lotteryId, String termNo,
			String lotteryResult, String lotteryResultPlus, String salesVolume,
			String jackpot, String winResult, String missCount)
			throws LotteryException {
		int result = 0;
		/*
		 * 更新数据
		 */
		try {
			LotteryTermModel lotteryModel = new LotteryTermModel();
			lotteryModel.setLotteryId(lotteryId);
			lotteryModel.setTermNo(termNo);
			lotteryModel.setLotteryResult(lotteryResult);
			lotteryModel.setLotteryResultPlus(lotteryResultPlus);
			lotteryModel.setSalesVolume(salesVolume);
			lotteryModel.setJackpot(jackpot);
			lotteryModel.setWinResult(winResult);
			lotteryModel.setMissCount(missCount);
			lotteryModel.setWinStatus(2);
			lotteryModel.setSaleStatus(5);
			result = getLotteryTermDao().updateTermWinInfo(lotteryModel);
		} catch (Exception e) {
			logger.error("更新开奖信息出错！错误原因：" ,e);
			throw new LotteryException(E_1010_CODE,E_1010_DESC);
		}
		
		return result;
	}

	/* (非 Javadoc)
	 *Title: updateTermWinStatus
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param winstatus
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#updateTermWinStatus(int, java.lang.String, int)
	 */
	public int updateTermWinStatus(int lotteryId, String termNo,
			int winstatus) throws LotteryException {
		int result = 0;
		try {
			LotteryTermModel lotteryModel = new LotteryTermModel();
			lotteryModel.setLotteryId(lotteryId);
			lotteryModel.setTermNo(termNo);
			lotteryModel.setWinStatus(winstatus);
			result = getLotteryTermDao().updateTermWinStatus(lotteryModel);
		} catch (Exception e) {
			logger.error("更新彩期开奖状态出错！错误原因：" + e.getMessage());
			throw new LotteryException(E_1003_CODE,E_1003_DESC);
		}
		return result;
	}

	private LotteryTermDaoImpl getLotteryTermDao() {
		return this.lotteryTermDao;
	}

	public void setLotteryTermDao(LotteryTermDaoImpl lotteryTermDao) {
		this.lotteryTermDao = lotteryTermDao;
	}
	
	@SuppressWarnings("unused")
	private LotteryInfo assembleLotteryInfo(LotteryTermModel lotteryModel){
		LotteryInfo lotteryInfo = new LotteryInfo();
		
		return lotteryInfo;
	}
	/*
	 * (非 Javadoc)
	*Title: queryAllLastTermInfo
	*Description: 获取所有彩种的最近的开奖信息
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryAllLastTermInfo()
	 */
	public List<LotteryTermModel> queryAllLastTermInfo() throws LotteryException {
		List<LotteryTermModel> result = new ArrayList<LotteryTermModel>();
		try {
			Map<Integer, String> lotteryList = LotteryTools.getLotteryList();
			for(Map.Entry<Integer, String> oneLottery : lotteryList.entrySet()){
				int lotteryId = oneLottery.getKey();
				if(LotteryTools.isLotteryStart(lotteryId)){
					LotteryTermModel lotteryLastCashInfo = this.getLotteryTermDao().queryTermLastCashInfo(lotteryId);
					if(lotteryLastCashInfo != null){
						lotteryLastCashInfo.setLotteryName(oneLottery.getValue());
						lotteryLastCashInfo.setLotteryInfo(LotteryTools.splitTermAllResult(
								lotteryId, lotteryLastCashInfo.getLotteryResult(), lotteryLastCashInfo.getWinResult(),
								lotteryLastCashInfo.getSalesInfo(),lotteryLastCashInfo.getMissCount()));//彩种的开奖信息，奖金结果，销售信息,遗漏信息
						result.add(lotteryLastCashInfo);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取所有彩种的最近的开奖信息出错！错误原因：",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: queryCanCashPrizeTermNo
	*Description: 
	* @param lotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryCanCashPrizeTermNo(int)
	 */
	public List<String> queryCanCashPrizeTermNo(int lotteryId) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "D",0);
	}
	/*
	 * (非 Javadoc)
	*Title: queryCanDispatchPrizeTermNo
	*Description: 
	* @param lotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryCanDispatchPrizeTermNo(int)
	 */
	public List<String> queryCanDispatchPrizeTermNo(int lotteryId) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "P",0);
	}
	/*
	 * (非 Javadoc)
	*Title: queryCanInputWinTermNo
	*Description: 
	* @param lotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryCanInputWinTermNo(int)
	 */
	public List<String> queryCanInputWinTermNo(int lotteryId) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "K",0);
	}
	/*
	 * (非 Javadoc)
	*Title: queryHaveWinTermInfo
	*Description: 
	* @param lotteryId
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryHaveWinTermInfo(int, int)
	 */
	public List<LotteryTermModel> queryHaveWinTermInfo(int lotteryId, int limitNum) throws LotteryException {
		List<LotteryTermModel> result = null;
		try {
			result = getLotteryTermDao().queryHaveWinTermInfo(lotteryId, limitNum);
			if(result != null){
				String lotteryName = LotteryTools.getLotteryName(lotteryId);
				for(LotteryTermModel oneTerm : result){
					oneTerm.setLotteryName(lotteryName);
					oneTerm.setLotteryInfo(LotteryTools.splitTermAllResult(
							lotteryId, oneTerm.getLotteryResult(), oneTerm.getWinResult(),
							oneTerm.getSalesInfo(),oneTerm.getMissCount()));//彩种的开奖信息，奖金结果，销售信息,遗漏信息
				}
			}
		} catch (Exception e) {
			logger.error("获取某一个彩种的已经开奖的已售期信息列表！错误原因：",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return result;
	}
	/**
	 * 
	 * Title: queryCanOpTermNoList<br>
	 * Description: <br>
	 *            获取可以满足条件的彩期列表<br>
	 * @param lotteryId 彩种
	 * @param opType 获取方式 K:可以开奖 D:可以兑奖 P:可以派奖
	 * @return List<String>
	 * @throws LotteryException
	 */
	private List<String> queryCanOpTermNoList(int lotteryId,String opType,int limitNum) throws LotteryException{
		List<String> result = null;
		try {
			result = this.getLotteryTermDao().queryCanOpTermNoList(lotteryId, opType,limitNum);
		} catch (Exception e) {
			logger.error("获取期号列表出错列表！错误原因：",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: queryTermLimitNumberInfo
	*Description: 
	* @param lotteryId
	* @param begin_term
	* @param end_term
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryTermLimitNumberInfo(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public List<LotteryTermModel> queryTermLimitNumberInfo(String lotteryId,String begin_term,String end_term,int limitNum) throws LotteryException {
		List<LotteryTermModel> result = null;
		try {
			List<Integer> lotteryIds = new ArrayList<Integer>();;
			if(StringUtils.isNotEmpty(lotteryId)){
				lotteryIds.add(Integer.parseInt(lotteryId));
			}else{
				lotteryIds.add(LotteryStaticDefine.LOTTERY_1000003);
				lotteryIds.add(LotteryStaticDefine.LOTTERY_1200001);
			}
			//result = getLotteryTermDao().queryTermLimitNumberInfo(Integer.parseInt(lotteryId));
			result = this.queryCanOpTermInfoList(lotteryIds,begin_term,end_term,null,limitNum,"LIMIT");
//			if(result != null){
//				String lotteryName = LotteryTools.getLotteryName(Integer.parseInt(lotteryId));
//				for(LotteryTermModel oneTerm : result){
//					oneTerm.setLotteryName(lotteryName);
//				}
//			}
		} catch (Exception e) {
			logger.error("获取限号信息列表出错！错误原因：",e);
			throw new LotteryException(E_1012_CODE,E_1012_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: updateTermLimitNumber
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param limitNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#updateTermLimitNumber(int, java.lang.String, java.lang.String)
	 */
	public int updateTermLimitNumber(int lotteryId, String termNo, String limitNumber) throws LotteryException {
		int result = 0;
		try {
			result = getLotteryTermDao().updateTermLimitNumber(lotteryId, termNo, limitNumber);
		} catch (Exception e) {
			logger.error("更新彩期限号信息出错！错误原因：" ,e);
			throw new LotteryException(E_1011_CODE,E_1011_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: queryLastTermNo
	*Description: 
	* @param lotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryLastTermNo(int)
	 */
	public List<String> queryLastTermNo(int lotteryId,int limitNum) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "L",limitNum);
	}
	/*
	 * (非 Javadoc)
	*Title: queryLastTermInfo
	*Description: 
	* @param lotteryIds
	* @param begin_term
	* @param end_term
	* @param limitNums
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryLastTermInfo(java.util.List, java.lang.String, java.lang.String, java.util.Map, int)
	 */
	public List<LotteryTermModel> queryLastTermInfo(List<Integer> lotteryIds,String begin_term,String end_term,Map<Integer, Integer> limitNums,int limitNum) throws LotteryException {
		return this.queryCanOpTermInfoList(lotteryIds,begin_term,end_term,limitNums,limitNum,"L");
	}
	/**
	 * 
	 * Title: queryCanOpTermInfoList<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param lotteryIds
	 * @param begin_term
	 * @param end_term
	 * @param limitNums
	 * @param opType
	 * @return
	 * @throws LotteryException
	 */
	private List<LotteryTermModel> queryCanOpTermInfoList(List<Integer> lotteryIds,String begin_term,String end_term,Map<Integer, Integer> limitNums,int limitNum,String opType) throws LotteryException{
		List<LotteryTermModel> result = null;
		if (lotteryIds == null || lotteryIds.isEmpty()) {
			lotteryIds = new ArrayList<Integer>();
			Map<Integer, String> lotteryIdMap = LotteryTools.getLotteryList();
			//lotteryIds.addAll(lotteryIdMap.keySet());
			for(Map.Entry<Integer, String> lottery : lotteryIdMap.entrySet()){
				if(LotteryTools.isLotteryStart(lottery.getKey())){
					lotteryIds.add(lottery.getKey());
				}
			}
		}
		
		result = new ArrayList<LotteryTermModel>();
		for (int lotteryId : lotteryIds) {
			List<LotteryTermModel> tmp = this.queryCanOpTermInfoList(lotteryId, begin_term,
					end_term, (limitNums==null?limitNum:(limitNums.get(lotteryId) == null ? -1 : limitNums.get(lotteryId))), opType);
			if(tmp != null && !tmp.isEmpty()){
				result.addAll(tmp);
			}
			
		}
		return result;
	}
	/**
	 * 
	 * Title: queryCanOpTermInfoList<br>
	 * Description: <br>
	 *            <br>
	 * @param lotteryId
	 * @param begin_term
	 * @param end_term
	 * @param limitNum
	 * @return List<LotteryTermModel
	 * @throws LotteryException
	 */
	private List<LotteryTermModel> queryCanOpTermInfoList(int lotteryId,String begin_term,String end_term,int limitNum,String opType) throws LotteryException{
		List<LotteryTermModel> result = null;
		try {
			result = this.getLotteryTermDao().queryCanOpTermInfoList(lotteryId, begin_term, end_term, limitNum,opType);
		} catch (Exception e) {
			logger.error("获取彩期列表出错！错误原因：",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return result;
	}

	@Override
	public int getTermInfoCount(int lotteryId, String startTerm, String endTerm) throws LotteryException{
//		try{
//			String where = null;
//			if(lotteryId > 0){
//				where = " and (termno >= '" + startTerm + "' and termno <= '" + endTerm + "')";
//			}
//			return this.getLotteryTermDao().getLotteryTermCount(lotteryId, where);
//		}catch(Exception e){
//			logger.error("getTermInfoCount(" + lotteryId + ", " + startTerm + ", " + endTerm + ") exception:" + e.toString());
//			if(logger.isDebugEnabled()){
//				e.printStackTrace();
//			}
//			throw new LotteryException(GETTERMCOUNTEXCEPTION, GETTERMCOUNTEXCEPTION_STR + e.toString());
//		}
		//潘祖朝修改
		try{
//			String where = null;
//			if(lotteryId > 0){
//				where = " and (termno >= '" + startTerm + "' and termno <= '" + endTerm + "')";
//			}
			return this.getLotteryTermDao().getLotteryTermCount(lotteryId, startTerm,endTerm);
		}catch(Exception e){
			logger.error("getTermInfoCount(" + lotteryId + ", " + startTerm + ", " + endTerm + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(GETTERMCOUNTEXCEPTION, GETTERMCOUNTEXCEPTION_STR + e.toString());
		}
	}

	@Override
	public List<LotteryTermModel> gettermInfo(int lotteryId, String startTerm, String endTerm, int pageIndex, int perPageNumber) throws LotteryException{
		List<LotteryTermModel> terms = null;
//		try{
//			String where = null;
//			if(lotteryId > 0){
//				where = " and (termno >= '" + startTerm + "' and termno <= '" + endTerm + "')";
//			}
//			int startNumber = perPageNumber * (pageIndex - 1);
//			int endNumber = pageIndex * perPageNumber;
//			return this.getLotteryTermDao().getLotteryTermInfo(lotteryId, where, startNumber, endNumber);
//		}catch(Exception e){
//			logger.error("getTermInfo(" + lotteryId + ", " + startTerm + ", " + endTerm + ", " + pageIndex + ", " + perPageNumber + ") exception:" + e.toString());
//			if(logger.isDebugEnabled()){
//				e.printStackTrace();
//			}
//			throw new LotteryException(GETTERMINFOEXCEPTION, GETTERMINFOEXCEPTION_STR + e.toString());
//		}
		//潘祖朝修改
		try{
//			String where = null;
//			if(lotteryId > 0){
//				where = " and (termno >= '" + startTerm + "' and termno <= '" + endTerm + "')";
//			}
			int startNumber = perPageNumber * (pageIndex - 1);
			int endNumber = pageIndex * perPageNumber;
			return this.getLotteryTermDao().getLotteryTermInfo(lotteryId, startTerm,endTerm, startNumber, endNumber);
		}catch(Exception e){
			logger.error("getTermInfo(" + lotteryId + ", " + startTerm + ", " + endTerm + ", " + pageIndex + ", " + perPageNumber + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(GETTERMINFOEXCEPTION, GETTERMINFOEXCEPTION_STR + e.toString());
		}
	}

	@Override
	public List<String> getTermnos(int lotteryId, int limitNum) throws LotteryException{
		return this.queryCanOpTermNoList(lotteryId, null, limitNum);
	}
	/*
	 * (非 Javadoc)
	*Title: queryCanSaleTermNos
	*Description: 
	* @param lotteryId
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryCanSaleTermNos(int, int)
	 */
	public List<String> queryCanSaleTermNos(int lotteryId, int limitNum) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "S",limitNum);
	}
	/*
	 * (非 Javadoc)
	*Title: queryTermLastCashInfo
	*Description: 
	* @param lotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryTermLastCashInfo(int)
	 */
	public LotteryTermModel queryTermLastCashInfo(int lotteryId) throws LotteryException {
		LotteryTermModel lotteryLastCashInfo = null;
		try {
			lotteryLastCashInfo = this.getLotteryTermDao().queryTermLastCashInfo(lotteryId);
			if(lotteryLastCashInfo != null){
				lotteryLastCashInfo.setLotteryName(LotteryTools.getLotteryName(lotteryId));
				lotteryLastCashInfo.setLotteryInfo(LotteryTools.splitTermAllResult(
						lotteryId, lotteryLastCashInfo.getLotteryResult(), lotteryLastCashInfo.getWinResult(),
						lotteryLastCashInfo.getSalesInfo(),lotteryLastCashInfo.getMissCount()));//彩种的开奖信息，奖金结果，销售信息,遗漏信息
			}
		} catch (Exception e) {
			logger.error("获取彩种的最近的开奖信息出错！错误原因：",e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return lotteryLastCashInfo;
	}
	/*
	 * (非 Javadoc)
	*Title: queryCanUpdateSaleTermInfo
	*Description: 
	* @param lotteryIds
	* @param begin_term
	* @param end_term
	* @param limitNums
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryCanUpdateSaleTermInfo(java.util.List, java.lang.String, java.lang.String, java.util.Map, int)
	 */
	public List<LotteryTermModel> queryCanUpdateSaleTermInfo(List<Integer> lotteryIds, String begin_term, String end_term, Map<Integer, Integer> limitNums, int limitNum) throws LotteryException {
		return this.queryCanOpTermInfoList(lotteryIds,begin_term,end_term,limitNums,limitNum,"X");
	}
	/*
	 * (非 Javadoc)
	*Title: queryCurrentAndNextTermNo
	*Description: 
	* @param lotteryId
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryCurrentAndNextTermNo(int, int)
	 */
	public List<String> queryCurrentAndNextTermNo(int lotteryId, int limitNum) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "X",limitNum);
	}

	@Override
	public List<String> queryTermNoIsOneOrTwo(int lotteryId, int limitNum) throws LotteryException{
		return this.queryCanOpTermNoList(lotteryId, "C", limitNum);
	}
	/*
	 * (非 Javadoc)
	*Title: queryCanAddTermNo
	*Description: 
	* @param lotteryId
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryCanAddTermNo(int, int)
	 */
	public List<String> queryCanAddTermNo(int lotteryId, int limitNum) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "Z",limitNum);
	}

	public List<String> queryHaveDispathTermNos(int lotteryId, int limitNum) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "HP",limitNum);
	}

	public List<String> queryHaveWinTermNos(int lotteryId, int limitNum) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "HK",limitNum);
	}
	/*
	 * (非 Javadoc)
	*Title: getCanPrintCondition
	*Description: 
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#getCanPrintCondition()
	 */
	public String getCanPrintCondition() throws LotteryException {
		StringBuffer sb = new StringBuffer();
		try{
			List<String> lotteryTerms = this.getLotteryTermDao().getCanPrintCondition();
			String condition = " or ";
			if(lotteryTerms != null && !lotteryTerms.isEmpty()){
				for(String one : lotteryTerms){
					sb.append(one).append(condition);
				}
				if(sb.lastIndexOf(condition) != -1){
					sb.delete(sb.lastIndexOf(condition), sb.length());
				}
			}
		}catch(Exception e){
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return sb.toString();
	}
	/*
	 * (非 Javadoc)
	*Title: queryLimitTermNo
	*Description: 
	* @param lotteryId
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryLimitTermNo(int, int)
	 */
	public List<String> queryLimitTermNo(int lotteryId, int limitNum) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "LIMIT", limitNum);
	}

	/*
	 * (非 Javadoc)
	*Title: queryHaveWinTermInfoToDate
	*Description: 
	* @param date
	* @return
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryHaveWinTermInfoToDate(java.lang.String)
	 */
	public List<LotteryTermModel> queryHaveWinTermInfoToDate(String date) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("termno", date + "%");
		map.put("lotteryId",1200001);
		return this.getLotteryTermDao().queryHaveWinTermInfoToDate(map);
	}
	/*
	 * (非 Javadoc)
	*Title: queryDealLine3TermNo
	*Description: 
	* @param lotteryId
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryDealLine3TermNo(int, int)
	 */
	public List<String> queryDealLine3TermNo(int lotteryId, int limitNum) throws LotteryException {
		return this.queryCanOpTermNoList(lotteryId, "DL3", limitNum);
	}
	/*
	 * (非 Javadoc)
	*Title: queryTermInfoNotSplit
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermServiceInterf#queryTermInfoNotSplit(int, java.lang.String)
	 */
	public LotteryTermModel queryTermInfoNotSplit(int lotteryId, String termNo) throws LotteryException {
		LotteryTermModel retLotteryModel = null;
		try {
			LotteryTermModel lotteryModel = new LotteryTermModel();
			lotteryModel.setLotteryId(lotteryId);
			lotteryModel.setTermNo(termNo);
			retLotteryModel = getLotteryTermDao().queryTermInfo(lotteryModel);
			if(retLotteryModel == null){
				logger.error("获取彩期信息出错！错误原因：未找到彩种："+lotteryId+" 期数："+termNo);
				//throw new LotteryException(E_1004_CODE,E_1004_DESC);
			}else{
				//处理彩种的附加信息
				retLotteryModel.setLotteryName(LotteryTools.getLotteryName(lotteryId));//中文名称
			}
			
		} catch (Exception e) {
			logger.error("获取彩期信息出错！错误原因：" + e);
			throw new LotteryException(E_1004_CODE,E_1004_DESC);
		}
		return retLotteryModel;
	}
}
