/**
 * Title: LotteryManagerService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-13 下午03:37:48
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.dao.impl.BusBetOrderDaoImpl;
import com.success.lottery.business.domain.BusBetOrderCountDomain;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.domain.BusBetOrderParam;
import com.success.lottery.business.domain.BusCoopPlanDomain;
import com.success.lottery.business.domain.BusCpInfoDomain;
import com.success.lottery.business.domain.PrizeUserDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.ReadPrizeFile;
import com.success.lottery.business.service.interf.CashPrizeInterf;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.LotteryInfo;
import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.business.service.impl
 * LotteryManagerService.java
 * LotteryManagerService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-13 下午03:37:48
 * 
 */

public class LotteryManagerService implements LotteryManagerInterf {
	
	private static Log logger = LogFactory.getLog(LotteryManagerService.class);
	
	private LotteryTermServiceInterf  termService;
	private BusBetOrderDaoImpl busBetOrderDao;
	private BetPlanOrderServiceInterf betOrderService;
	private CashPrizeInterf cashPrizeSevice;

	/* (非 Javadoc)
	 *Title: inputArbitryNineWinInfo
	 *Description: 
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputArbitryNineWinInfo(java.lang.String, java.util.ArrayList, java.lang.String, java.lang.String, java.util.Map, java.lang.String)
	 */
	public int inputArbitryNineWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, Map<String, String> winResult)
			throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1300002;
		/*
		 * 校验参数
		 */
		this.checkParam(termNo,salesVolume, jackpot,lotteryResult,winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			String [] arbitry9WinResult = {winResult.get("A"),winResult.get("B")};
			
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setArbitry9LotteryResult(lotteryResult);
			paramInfo.setArbitry9WinResult(arbitry9WinResult);
			
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.soccerLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, jackpot, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputSuperWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}

	/* (非 Javadoc)
	 *Title: inputArrangeFiveWinInfo
	 *Description: 
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param winResult
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputArrangeFiveWinInfo(java.lang.String, java.util.Map, java.lang.String, java.util.Map, java.lang.String)
	 */
	public int inputArrangeFiveWinInfo(String termNo,
			Map<String, String> lotteryResult, String salesVolume,
			Map<String, String> winResult)
			throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1000004;
		/*
		 * 校验参数
		 */
		this.checkParam(termNo, salesVolume, "jackpot", winResult, winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			ArrayList<String> arrangeFiveLotteryResult = new ArrayList<String>();
			for(int i = 1; i< 6;i++){
				arrangeFiveLotteryResult.add(lotteryResult.get(String.valueOf(i)));
			}
			String [] arrangeFiveWinResult = {winResult.get("A"),winResult.get("B")};
			
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setArrangeFiveLotteryResult(arrangeFiveLotteryResult);
			paramInfo.setArrangeFiveWinResult(arrangeFiveWinResult);
			
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, null, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputSuperWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}

	/* (非 Javadoc)
	 *Title: inputArrangeThreeWinInfo
	 *Description: 
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param winResult
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputArrangeThreeWinInfo(java.lang.String, java.util.Map, java.lang.String, java.util.Map, java.lang.String)
	 */
	public int inputArrangeThreeWinInfo(String termNo,
			Map<String, String> lotteryResult, String salesVolume,
			Map<String, String[]> winResult)
			throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1000003;
		/*
		 * 校验参数
		 */
		this.checkParam(termNo, salesVolume, "jackpots", lotteryResult, winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			ArrayList<String> arrangeThreeLotteryResult = new ArrayList<String>();
			for(int i = 1; i< 4;i++){
				arrangeThreeLotteryResult.add(lotteryResult.get(String.valueOf(i)));
			}
			TreeMap<String,String[]> arrangeThreeWinResult = new TreeMap<String,String[]>();
			arrangeThreeWinResult.putAll(winResult);
			
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setArrangeThreeLotteryResult(arrangeThreeLotteryResult);
			paramInfo.setArrangeThreeWinResult(arrangeThreeWinResult);
			
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, null, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputSuperWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}

	/* (非 Javadoc)
	 *Title: inputBallFourWinInfo
	 *Description: 
	 * @param termNo
	 * @param lotteryResult
	 * @param lotteryResultPlus
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputBallFourWinInfo(java.lang.String, java.util.ArrayList, java.lang.String, java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public int inputBallFourWinInfo(String termNo,
			ArrayList<String> lotteryResult,
			String salesVolume, String jackpot,  Map<String,String> winResult) throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1300004;
		/*
		 * 校验参数
		 */
		this.checkParam(termNo,salesVolume, jackpot,lotteryResult,winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			String [] ball4WinResult = {winResult.get("A"),winResult.get("B")};
			
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setBall4LotteryResult(lotteryResult);
			paramInfo.setBall4WinResult(ball4WinResult);
			
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.soccerLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, jackpot, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputBallFourWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}

	/* (非 Javadoc)
	 *Title: inputJxDlcWinInfo
	 *Description: 
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputElevenYunWinInfo(java.lang.String, java.util.ArrayList, java.lang.String, java.lang.String, java.lang.String, java.util.TreeMap, java.lang.String)
	 */
	public int inputJxDlcWinInfo(String termNo,
			ArrayList<String> lotteryResult,
			String salesVolume, String jackpot,
			LinkedHashMap<String, String[]> winResult)
			throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1200001;
		/*
		 * 校验参数
		 */
		this.checkParam(termNo, StringUtils.isEmpty(salesVolume)?"0":salesVolume, StringUtils.isEmpty(jackpot)?"0":jackpot, lotteryResult, winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setElevenYunLotteryResult(lotteryResult);
			paramInfo.setElevenYunWinResult(winResult);
			
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, jackpot, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputElevenYunWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}

	/* (非 Javadoc)
	 *Title: inputHalfSixWinInfo
	 *Description: 
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputHalfSixWinInfo(java.lang.String, java.util.ArrayList, java.lang.String, java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public int inputHalfSixWinInfo(String termNo,
			ArrayList<String> lotteryResult,
			String salesVolume, String jackpot, Map<String,String> winResult) throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1300003;
		/*
		 * 校验参数
		 */
		this.checkParam(termNo,salesVolume, jackpot,lotteryResult,winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			String [] ball6WinResult = {winResult.get("A"),winResult.get("B")};
			
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setHalf6LotteryResult(lotteryResult);
			paramInfo.setHalf6WinResult(ball6WinResult);
			
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.soccerLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, jackpot, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputHalfSixWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}

	/*
	 * (非 Javadoc)
	*Title: inputHappyZodiacWinInfo
	*Description: 
	* @param termNo
	* @param lotteryResult
	* @param salesVolume
	* @param winResult
	* @param opUser
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputHappyZodiacWinInfo(java.lang.String, java.util.ArrayList, java.lang.String, java.util.Map, java.lang.String)
	 */
	public int inputHappyZodiacWinInfo(String termNo,
			ArrayList<String> lotteryResult,
			String salesVolume, Map<String, String> winResult)
			throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1000005;
		/*
		 * 校验参数
		 */
		this.checkParam(termNo, salesVolume, "jackpot", lotteryResult, winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			String [] innerWinResult = {winResult.get("A"),winResult.get("B")};
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setHappyZodiacLotteryResult(lotteryResult);
			paramInfo.setHappyZodiacWinResult(innerWinResult);
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, null, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputSuperWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}

	/* (非 Javadoc)
	 *Title: inputSevenColorWinInfo
	 *Description: 
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputSevenColorWinInfo(java.lang.String, java.util.Map, java.lang.String, java.lang.String, java.util.Map, java.lang.String)
	 */
	public int inputSevenColorWinInfo(String termNo,
			Map<String, String> lotteryResult, String salesVolume,
			String jackpot, Map<String, String[]> winResult)
			throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1000002;
		
		/*
		 * 校验参数
		 */
		this.checkParam(termNo, salesVolume, jackpot, lotteryResult, winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			ArrayList<String> sevenColorLotteryResult = new ArrayList<String>();
			for(int i = 1; i< 8;i++){
				sevenColorLotteryResult.add(lotteryResult.get(String.valueOf(i)));
			}
			TreeMap<String,String[]> sevenColorWinResult = new TreeMap<String,String[]>();
			sevenColorWinResult.putAll(winResult);
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setSevenColorLotteryResult(sevenColorLotteryResult);
			paramInfo.setSevenColorWinResult(sevenColorWinResult);
			
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, jackpot, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputSuperWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}

	/*
	 * (非 Javadoc)
	*Title: inputSuperWinInfo
	*Description: 
	* @param termNo
	* @param lotteryResult
	* @param salesVolume
	* @param jackpot
	* @param winResult
	* @param opUser
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputSuperWinInfo(java.lang.String, java.util.TreeMap, java.lang.String, java.lang.String, java.util.TreeMap, java.lang.String)
	 */
	public int inputSuperWinInfo(String termNo,
			TreeMap<String, ArrayList<String>> lotteryResult,
			String salesVolume, String jackpot,
			TreeMap<String, TreeMap<String, String[]>> winResult)
			throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1000001;
		/*
		 * 校验参数
		 */
		this.checkParam(termNo, salesVolume, jackpot, lotteryResult, winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setSuperLotteryResult(lotteryResult);
			paramInfo.setSuperWinResult(winResult);
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, jackpot, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputSuperWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}

	/* (非 Javadoc)
	 *Title: inputWinOrFailWinInfo
	 *Description: 
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputWinOrFailWinInfo(java.lang.String, java.util.ArrayList, java.lang.String, java.lang.String, java.util.TreeMap, java.lang.String)
	 */
	public int inputWinOrFailWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, TreeMap<String, String[]> winResult)
			throws LotteryException {
		int result = 0;
		int lotteryId = LotteryStaticDefine.LOTTERY_1300001;
		/*
		 * 校验参数
		 */
		this.checkParam(termNo,salesVolume, jackpot,lotteryResult,winResult);
		
		try {
			/*
			 * 转换集合为字符串
			 */
			LotteryInfo paramInfo = new LotteryInfo();
			paramInfo.setWinOrFailLotteryResult(lotteryResult);
			paramInfo.setWinOrFailWinResult(winResult);
			
			String lotteryResultStr = LotteryTools.mergeLotteryResult(lotteryId, paramInfo);
			String lotteryWinResultStr = LotteryTools.mergeWinResult(lotteryId, paramInfo);
			/*
			 * 调用更新
			 */
			result = this.soccerLotteryWinInfoUpdate(lotteryId, termNo, lotteryResultStr, lotteryResultStr, salesVolume, jackpot, lotteryWinResultStr);
		} catch (Exception e) {
			logger.error("inputSuperWinInfo error ：", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}
	/**
	 * 
	 * Title: numberLotteryUpdate<br>
	 * Description: <br>
	 *            数字彩票的开奖信息输入<br>
	 * @param lotteryId
	 * @param termNo
	 * @param lotteryResult
	 * @param lotteryResultPlus
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @param opUser
	 * @return int
	 * @throws LotteryException
	 */
	private int numberLotteryWinInfoUpdate(int lotteryId,String termNo,
			String lotteryResult,String lotteryResultPlus,String salesVolume, String jackpot,
			String winResult) throws LotteryException {
		int result = 0;
		try {
			/*
			 * 获取彩期的上一期信息
			 */
			LotteryTermModel lastTermInfo = this.getTermService().queryLastTermInfo(lotteryId, termNo);
			String missCount = LotteryTools.getMissCount(lotteryId, lotteryResult, lastTermInfo.getMissCount());
			/*
			 * 调用写数据库方法
			 */
			result = this.getTermService().updateTermWinInfo(lotteryId,
					termNo, lotteryResult, lotteryResultPlus, salesVolume,
					jackpot, winResult, missCount);
			if(result <= 0){
				 throw new LotteryException(LotteryManagerInterf.E_03_CODE,LotteryManagerInterf.E_03_DESC);
			 }
			
			this.updateTermCompleteStatus(lotteryId, termNo);
			
		} catch (Exception e) {
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		return result;
	}
	/**
	 * 
	 * Title: updateTermCompleteStatus<br>
	 * Description: <br>
	 *              <br>如果彩期对应的订单表中没有订单状态是0，1，2，3，4，5的订单，则认为该彩期没有订单需要兑奖，直接将该彩期设为派奖完成
	 * @param lotteryId
	 * @param termNo
	 * @throws LotteryException
	 */
	private void updateTermCompleteStatus(int lotteryId, String termNo) throws LotteryException {
		try {
			/*
			List<Integer> winStatus = new ArrayList<Integer>();
			List<Integer>  orderStatus = new ArrayList<Integer>();
			orderStatus.add(0);
			orderStatus.add(1);
			orderStatus.add(2);
			orderStatus.add(3);
			orderStatus.add(4);
			orderStatus.add(5);
			int notCompleteOrder = this.getBetOrderService().queryOrderNumByStatus(lotteryId, termNo, orderStatus, winStatus);// 查是否还有出票成功以及需要处理的订单
			if (notCompleteOrder == 0) {// 已经全部兑奖完成
				this.getTermService().updateTermWinStatus(lotteryId, termNo, 8);// 将彩期的状态直接设置为派奖完成，该彩期所有的操作结束
				//this.getCashPrizeSevice().cashAutoOrder(lotteryId, termNo);//直接调用兑奖方法完成彩期状态的更改和追号订单的处理
				
			}
			*/
		} catch (Exception e) {
			logger.error("输入开奖信息更新彩期表状态出错:", e);
			throw new LotteryException(E_02_CODE, E_02_DESC);
		}

	}
	/**
	 * 
	 * Title: soccerLotteryWinInfoUpdate<br>
	 * Description: <br>
	 *            足彩彩票的开奖信息输入<br>
	 * @param lotteryId
	 * @param termNo
	 * @param lotteryResult
	 * @param lotteryResultPlus
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @return int
	 * @throws LotteryException
	 */
	private int soccerLotteryWinInfoUpdate(int lotteryId,String termNo,
			String lotteryResult,String lotteryResultPlus,String salesVolume, String jackpot,
			String winResult) throws LotteryException {
		int result = 0;
		try {
			
			/*
			 * 调用写数据库方法
			 */
			result = this.getTermService().updateTermWinInfo(lotteryId,
					termNo, lotteryResult, lotteryResultPlus, salesVolume,
					jackpot, winResult, null);
			if(result <= 0){
				 throw new LotteryException(LotteryManagerInterf.E_03_CODE,LotteryManagerInterf.E_03_DESC);
			 }
			
			this.updateTermCompleteStatus(lotteryId, termNo);
			
		} catch (Exception e) {
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		return result;
	}
	/**
	 * 
	 * Title: checkParam<br>
	 * Description: <br>
	 *            校验参数<br>
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @throws LotteryException
	 */
	private void checkParam(String termNo,String salesVolume,
			String jackpot, final Collection Result1, final Map Result2) throws LotteryException{
		
		if(StringUtils.isEmpty(termNo)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "彩期").replace("2", "不能为空"));
		}
		if(StringUtils.isEmpty(salesVolume)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "投注总额").replace("2", "不能为空"));
		}
		if(StringUtils.isEmpty(jackpot)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "滚入下期奖池").replace("2", "不能为空"));
		}
		if(Result1 == null || Result1.isEmpty()){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "开奖结果").replace("2", "不能为空"));
		}
		if(Result2 == null || Result2.isEmpty()){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "奖金结果").replace("2", "不能为空"));
		}
	}
	/**
	 * 
	 * Title: checkParam<br>
	 * Description: <br>
	 *            校验参数<br>
	 * @param termNo
	 * @param salesVolume
	 * @param jackpot
	 * @param Result1
	 * @param Result2
	 * @throws LotteryException
	 */
	private void checkParam(String termNo,String salesVolume,
			String jackpot, final Map Result1, final Map Result2) throws LotteryException{
		
		if(StringUtils.isEmpty(termNo)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "彩期").replace("2", "不能为空"));
		}
		if(StringUtils.isEmpty(salesVolume)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "投注总额").replace("2", "不能为空"));
		}
		if(StringUtils.isEmpty(jackpot)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "滚入下期奖池").replace("2", "不能为空"));
		}
		if(Result1 == null || Result1.isEmpty()){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "开奖结果").replace("2", "不能为空"));
		}
		if(Result2 == null || Result2.isEmpty()){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "奖金结果").replace("2", "不能为空"));
		}
	}
	/**
	 * 
	 * Title: checkParam<br>
	 * Description: <br>
	 *            校验参数<br>
	 * @param termNo
	 * @param salesVolume
	 * @param jackpot
	 * @param lotteryResult
	 * @param winResult
	 * @throws LotteryException
	 */
	private void checkParam(String termNo,String salesVolume,
			String jackpot, String lotteryResult, String winResult) throws LotteryException{
		if(StringUtils.isEmpty(termNo)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "彩期").replace("2", "不能为空"));
		}
		if(StringUtils.isEmpty(salesVolume)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "投注总额").replace("2", "不能为空"));
		}
		if(StringUtils.isEmpty(jackpot)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "滚入下期奖池").replace("2", "不能为空"));
		}
		if(StringUtils.isEmpty(lotteryResult)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "开奖结果").replace("2", "不能为空"));
		}
		if(StringUtils.isEmpty(winResult)){
			throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "奖金结果").replace("2", "不能为空"));
		}
	}
	
	/*
	 * (非 Javadoc)
	*Title: inputWinInfo
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param lotteryResult
	* @param lotteryResultPlus
	* @param salesVolume
	* @param jackpot
	* @param winResult
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputWinInfo(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int inputWinInfo(int lotteryId, String termNo, String lotteryResult, String lotteryResultPlus, String salesVolume, String jackpot, String winResult) throws LotteryException {
		int result = 0;
		
		switch (lotteryId) {
		case LotteryStaticDefine.LOTTERY_1000001:
			this.checkParam(termNo, salesVolume, jackpot, lotteryResult, winResult);
			result = this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResult, lotteryResultPlus, salesVolume, jackpot, winResult);
			break;
		case LotteryStaticDefine.LOTTERY_1000005:
			this.checkParam(termNo, salesVolume, "jackpot", lotteryResult, winResult);
			this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResult, lotteryResultPlus, salesVolume, null, winResult);
			break;
		case LotteryStaticDefine.LOTTERY_1000002:
			this.checkParam(termNo, salesVolume, jackpot, lotteryResult, winResult);
			result = this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResult, lotteryResultPlus, salesVolume, null, winResult);
			break;
		case LotteryStaticDefine.LOTTERY_1000003:
			this.checkParam(termNo, salesVolume, "jackpot", lotteryResult, winResult);
			this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResult, lotteryResultPlus, salesVolume, null, winResult);
			break;
		case LotteryStaticDefine.LOTTERY_1000004:
			this.checkParam(termNo, salesVolume, "jackpot", lotteryResult, winResult);
			this.numberLotteryWinInfoUpdate(lotteryId, termNo, lotteryResult, lotteryResultPlus, salesVolume, null, winResult);
			break;
		case LotteryStaticDefine.LOTTERY_1300001:
			this.checkParam(termNo, salesVolume, jackpot, lotteryResult, winResult);
			this.soccerLotteryWinInfoUpdate(lotteryId, termNo, lotteryResult, lotteryResultPlus, salesVolume, jackpot, winResult);
			break;
		case LotteryStaticDefine.LOTTERY_1300002:
			this.checkParam(termNo, salesVolume, jackpot, lotteryResult, winResult);
			this.soccerLotteryWinInfoUpdate(lotteryId, termNo, lotteryResult, lotteryResultPlus, salesVolume, jackpot, winResult);
			break;
		default:
			break;
		}
		return result;
	}

	public LotteryTermServiceInterf getTermService() {
		return termService;
	}

	public void setTermService(LotteryTermServiceInterf termService) {
		this.termService = termService;
	}
	/*
	 * (非 Javadoc)
	*Title: inputSaleInfo
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param startTime
	* @param startTime2
	* @param deadLine
	* @param deadLine3
	* @param deadLine2
	* @param winLine
	* @param winLine2
	* @param changeLine
	* @param saleInfo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputSaleInfo(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	public int inputSaleInfo(int lotteryId, String termNo, String startTime,
			String startTime2, String deadLine, String deadLine3,
			String deadLine2, String winLine, String winLine2,
			Map<Integer, Map<String, String>> saleInfo)
			throws LotteryException {
		int result = 0;
		try {
			
			/*
			 * 彩种校验，只有足彩的可以输入销售信息
			 */
			if(!LotteryTools.getSoccerLotteryList().containsKey(lotteryId)){
				logger.info("只有足彩可以更新销售信息["+LotteryTools.getLotteryName(lotteryId)+"]");
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "彩种").replace("2", "只有足彩可以输入销售信息"));
			}
			/*
			 * 参数校验
			 */
			if(StringUtils.isEmpty(termNo)){
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "彩期").replace("2", "不能为空"));
			}
			if(StringUtils.isEmpty(startTime)){
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "系统开售时间").replace("2", "不能为空"));
			}
			if(StringUtils.isEmpty(startTime2)){
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "官方开售时间").replace("2", "不能为空"));
			}
			if(StringUtils.isEmpty(deadLine)){
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "系统止售时间").replace("2", "不能为空"));
			}
			if(StringUtils.isEmpty(winLine)){
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "系统开奖时间").replace("2", "不能为空"));
			}
			if(StringUtils.isEmpty(winLine)){
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "官方开奖时间").replace("2", "不能为空"));
			}
			if(StringUtils.isEmpty(deadLine3)){
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "官方止售时间").replace("2", "不能为空"));
			}
			if(saleInfo == null || saleInfo.isEmpty()){
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "比赛场次信息").replace("2", "不能为空"));
			}
			/*
			 * 校验时间格式
			 */
			try {
				this.checkDateIsRight(startTime);
				this.checkDateIsRight(startTime2);
				this.checkDateIsRight(deadLine);
				this.checkDateIsRight(deadLine3);
				this.checkDateIsRight(deadLine2);
				this.checkDateIsRight(winLine);
				this.checkDateIsRight(winLine2);
			} catch (Exception e) {
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "时间").replace("2", "格式不正确"));
			}
			String salesInfoStr = LotteryTools.mergeSalesInfo(saleInfo);
			/*
			 * 生成写数据库对象
			 */
			LotteryTermModel termInfo = new LotteryTermModel();
			termInfo.setLotteryId(lotteryId);
			termInfo.setTermNo(termNo);
			termInfo.setStartTime(Timestamp.valueOf(startTime));
			termInfo.setStartTime2(Timestamp.valueOf(startTime2));
			termInfo.setDeadLine(Timestamp.valueOf(deadLine));
			termInfo.setDeadLine3(Timestamp.valueOf(deadLine3));
			termInfo.setDeadLine2((deadLine2==null||"".equals(deadLine2) ? Timestamp.valueOf(deadLine) : Timestamp.valueOf(deadLine2)));
			termInfo.setWinLine((winLine==null||"".equals(winLine) ? null:Timestamp.valueOf(winLine)));
			termInfo.setWinLine2((winLine2==null||"".equals(winLine2)?Timestamp.valueOf(winLine):Timestamp.valueOf(winLine2)));
			
			
			long l = Timestamp.valueOf(winLine).getTime() + 24*60*60*1000*59L;
			Timestamp t = new Timestamp(l);
			termInfo.setChangeLine(t);
			termInfo.setSalesInfo(salesInfoStr);
			//Timestamp.valueOf(winLine).getTime() + 24*60*60*1000*59;
			result = this.getTermService().updateTermSalesInfo(termInfo);
			if(result <= 0){
				 throw new LotteryException(LotteryManagerInterf.E_04_CODE,LotteryManagerInterf.E_04_DESC);
			 }
		} catch (Exception e) {
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
		
		return result;
	}
	
	private void checkDateIsRight(String date) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义时间格式
		//SimpleDateFormat df = new SimpleDateFormat(format_str);//定义时间格式
		try{
			df.setLenient(false);
			if(StringUtils.isNotEmpty(date)){
				df.format(df.parse(date));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	/*
	 * (非 Javadoc)
	*Title: inputTermLimitNumber
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param limitNumber
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputTermLimitNumber(int, java.lang.String, java.lang.String)
	 */
	public void inputTermLimitNumber(int lotteryId, String termNo, String limitNumber) throws LotteryException {
		int result = 0;
		try{
			/*
			 * 校验参数
			 */
			if(StringUtils.isEmpty(termNo)){
				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "彩期").replace("2", "不能为空"));
			}
			
			
			/*
			 * 潘祖朝注释
			 * 由于限号解除功能，限号信息可以为空
			 */
//			if(StringUtils.isEmpty(limitNumber)){
//				throw new LotteryException(LotteryManagerInterf.E_01_CODE,LotteryManagerInterf.E_01_DESC.replace("1", "限号信息").replace("2", "不能为空"));
//			}
			
			
			/*
			 * 更新数据
			 */
			result = this.getTermService().updateTermLimitNumber(lotteryId, termNo, limitNumber);
			if(result <= 0 ){
				throw new LotteryException(LotteryManagerInterf.E_05_CODE,LotteryManagerInterf.E_05_DESC);
			}
		}catch(Exception e){
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
			}
		}
	}
	/*
	 * (非 Javadoc)
	*Title: getLotteryList
	*Description: 
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getLotteryList()
	 */
	public Map<Integer, String> getLotteryList() throws LotteryException {
		Map<Integer,String> result = new TreeMap<Integer,String>();
		Map<Integer,String> tmp = LotteryTools.getLotteryList();
		for(Map.Entry<Integer, String> oneLottery : tmp.entrySet()){
			int lotteryId = oneLottery.getKey();
			String lotteryName = oneLottery.getValue();
			if(LotteryTools.isLotteryStart(lotteryId)){
				result.put(lotteryId, lotteryName);
			}
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getLotteryLastTerm
	*Description: 
	* @param lotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getLotteryLastTerm(int)
	 */
	public List<String> getLotteryLastTerm(int lotteryId,int limitNum) throws LotteryException {
		if(LotteryTools.getLotteryList().containsKey(lotteryId)){
			return this.getTermService().queryLastTermNo(lotteryId,limitNum);
		}else{
			return null;
		}
	}
	/*
	 * (非 Javadoc)
	*Title: getLotteryCurrentAndNextTerm
	*Description: 
	* @param lotteryId
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getLotteryCurrentAndNextTerm(int, int)
	 */
	public List<String> getLotteryCurrentAndNextTerm(int lotteryId, int limitNum) throws LotteryException {
		if(LotteryTools.getLotteryList().containsKey(lotteryId)){
			return this.getTermService().queryCurrentAndNextTermNo(lotteryId, limitNum);
		}else{
			return null;
		}
	}
	/*
	 * (非 Javadoc)
	*Title: getLotteryLastTermInfo
	*Description: 
	* @param lotteryId
	* @param begin_term
	* @param end_term
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getLotteryLastTermInfo(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public List<LotteryTermModel> getLotteryLastTermInfo(String lotteryId, String begin_term, String end_term, int limitNum) throws LotteryException {
		List<LotteryTermModel> result = null;
		try{
			List<Integer> lotteryIds = null;
			if(StringUtils.isNotEmpty(lotteryId)){
				lotteryIds = new ArrayList<Integer>();
				lotteryIds.add(Integer.parseInt(lotteryId));
			}
			result = this.getTermService().queryLastTermInfo(lotteryIds, begin_term, end_term, null, limitNum);
		}catch(LotteryException e){
			throw new LotteryException(LotteryManagerInterf.E_06_CODE,LotteryManagerInterf.E_06_DESC);
		}catch(Exception ex){
			throw new LotteryException(LotteryManagerInterf.E_06_CODE,LotteryManagerInterf.E_06_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getLotteryLastWinInfo
	*Description: 
	* @param lotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getLotteryLastWinInfo(int)
	 */
	public LotteryTermModel getLotteryLastWinInfo(int lotteryId) throws LotteryException {
		return this.getTermService().queryTermLastCashInfo(lotteryId);
	}
	/*
	 * (非 Javadoc)
	*Title: inputSuperAndHappyWinInfo
	*Description: 
	* @param termNo
	* @param lotteryResultS
	* @param salesVolumeS
	* @param jackpotS
	* @param winResultS
	* @param lotteryResultH
	* @param salesVolumeH
	* @param winResultH
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputSuperAndHappyWinInfo(java.lang.String, java.util.TreeMap, java.lang.String, java.lang.String, java.util.TreeMap, java.util.ArrayList, java.lang.String, java.util.Map)
	 */
	public int inputSuperAndHappyWinInfo(String termNo, TreeMap<String, ArrayList<String>> lotteryResultS, String salesVolumeS, String jackpotS, TreeMap<String, TreeMap<String, String[]>> winResultS, ArrayList<String> lotteryResultH, String salesVolumeH, Map<String, String> winResultH) throws LotteryException {
		int result = 0;
		result = this.inputSuperWinInfo(termNo, lotteryResultS, salesVolumeS, jackpotS, winResultS);
		result = result + this.inputHappyZodiacWinInfo(termNo, lotteryResultH, salesVolumeH, winResultH);
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: inputArrange3and5WinInfo
	*Description: 
	* @param termNo
	* @param lotteryResult3
	* @param salesVolume3
	* @param winResult3
	* @param lotteryResult5
	* @param salesVolume5
	* @param winResult5
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputArrange3and5WinInfo(java.lang.String, java.util.Map, java.lang.String, java.util.Map, java.util.Map, java.lang.String, java.util.Map)
	 */
	public int inputArrange3and5WinInfo(String termNo,
			Map<String, String> lotteryResult3, String salesVolume3,
			Map<String, String[]> winResult3,
			Map<String, String> lotteryResult5, String salesVolume5,
			Map<String, String> winResult5) throws LotteryException {
		int result = 0;
		result = this.inputArrangeThreeWinInfo(termNo, lotteryResult3,
				salesVolume3, winResult3);
		result = result
				+ this.inputArrangeFiveWinInfo(termNo, lotteryResult5,
						salesVolume5, winResult5);
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getbetOrderInfoS
	*Description: 
	* @param accountId
	* @param userName
	* @param lotteryId
	* @param termNo
	* @param betType
	* @param betStatus
	* @param winStatus
	* @param begin_time
	* @param end_time
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getbetOrderInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	public List<BusBetOrderDomain> getbetOrderInfoS(String accountId, String userName, String lotteryId, String termNo, String planSource, String betStatus, String winStatus, String begin_time, String end_time, int pageIndex, int perPageNumber) throws LotteryException {
		List<BusBetOrderDomain> result = null;
		try{
			BusBetOrderParam param  = this.convertBusBetOrderParam(accountId, userName, lotteryId, termNo, planSource, betStatus, winStatus, begin_time, end_time, pageIndex, perPageNumber);
			result = this.getBusBetOrderDao().queryBetOrderInfoS(param);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getbetOrderInfosCount
	*Description: 
	* @param accountId
	* @param userName
	* @param lotteryId
	* @param termNo
	* @param planSource
	* @param betStatus
	* @param winStatus
	* @param begin_time
	* @param end_time
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getbetOrderInfosCount(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int getbetOrderInfosCount(String accountId, String userName, String lotteryId, String termNo, String planSource, String betStatus, String winStatus, String begin_time, String end_time) throws LotteryException {
		int result = 0;
		try {
			BusBetOrderParam param  = this.convertBusBetOrderParam(accountId, userName, lotteryId, termNo, planSource, betStatus, winStatus, begin_time, end_time, -1, -1);
			result = this.getBusBetOrderDao().getBetOrderInfosCount(param);
		} catch (Exception e) {
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/**
	 * 
	 * Title: convertBusBetOrderParam<br>
	 * Description: <br>
	 *              <br>
	 * @param accountId
	 * @param userName
	 * @param lotteryId
	 * @param termNo
	 * @param planSource
	 * @param betStatus
	 * @param winStatus
	 * @param begin_time
	 * @param end_time
	 * @param pageIndex
	 * @param perPageNumber
	 * @return BusBetOrderParam
	 * @throws Exception
	 */
	private BusBetOrderParam convertBusBetOrderParam(String accountId,
			String userName, String lotteryId, String termNo,
			String planSource, String betStatus, String winStatus,
			String begin_time, String end_time, int pageIndex, int perPageNumber)
			throws Exception {
		BusBetOrderParam param = new BusBetOrderParam();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(begin_time)){
				begin_time_f = sdf.format(sdf.parse(begin_time.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(end_time)){
				end_time_f = sdf.format(sdf.parse(end_time.trim() + " 24:00:00"));
			}
			
			param.setAccountId(accountId);
			param.setUserName(userName);
			param.setLotteryId(lotteryId);
			param.setTermNo(termNo);
			param.setPlanSource(planSource);
			param.setBetStatus(betStatus);
			param.setWinStatus(winStatus);
			param.setBegin_time(begin_time_f);
			param.setEnd_time(end_time_f);
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
				param.setStartPageNumber(startNumber);
				param.setEndPageNumber(perPageNumber);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}
		return param;
	}

	public BusBetOrderDaoImpl getBusBetOrderDao() {
		return busBetOrderDao;
	}

	public void setBusBetOrderDao(BusBetOrderDaoImpl busBetOrderDao) {
		this.busBetOrderDao = busBetOrderDao;
	}
	/*
	 * (非 Javadoc)
	*Title: inputWinAndNineWinInfo
	*Description: 
	* @param termNo
	* @param lotteryResultW
	* @param salesVolumeW
	* @param jackpotW
	* @param winResultW
	* @param lotteryResult9
	* @param salesVolume9
	* @param jackpot9
	* @param winResult9
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputWinAndNineWinInfo(java.lang.String, java.util.ArrayList, java.lang.String, java.lang.String, java.util.TreeMap, java.util.ArrayList, java.lang.String, java.lang.String, java.util.Map)
	 */
	public int inputWinAndNineWinInfo(String termNo, ArrayList<String> lotteryResultW, String salesVolumeW, String jackpotW, TreeMap<String, String[]> winResultW, ArrayList<String> lotteryResult9, String salesVolume9, String jackpot9, Map<String, String> winResult9) throws LotteryException {
		int result = 0;
		result = this.inputWinOrFailWinInfo(termNo, lotteryResultW, salesVolumeW, jackpotW, winResultW);
		result = result
				+ this.inputArbitryNineWinInfo(termNo, lotteryResult9, salesVolume9, jackpot9, winResult9);
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getOrdersByPlanId
	*Description: 
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getOrdersByPlanId(java.lang.String)
	 */
	public List<BusBetOrderDomain> getOrdersByPlanId(String planId) throws LotteryException {
		List<BusBetOrderDomain> result = null;
		try{
			result = this.getBusBetOrderDao().getOrdersByPlanId(planId);
		}catch(Exception ex){
			//ex.printStackTrace();
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getLotteryNextTermInfo
	*Description: 
	* @param lotteryId
	* @param begin_term
	* @param end_term
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getLotteryNextTermInfo(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public List<LotteryTermModel> getLotteryNextTermInfo(String lotteryId, String begin_term, String end_term, int limitNum) throws LotteryException {
		List<LotteryTermModel> result = null;
		try{
			List<Integer> lotteryIds = new ArrayList<Integer>();
			if(StringUtils.isNotEmpty(lotteryId)){
				lotteryIds.add(Integer.parseInt(lotteryId));
			}else{
				lotteryIds.add(LotteryStaticDefine.LOTTERY_1300001);
				lotteryIds.add(LotteryStaticDefine.LOTTERY_1300002);
				lotteryIds.add(LotteryStaticDefine.LOTTERY_1300003);
				lotteryIds.add(LotteryStaticDefine.LOTTERY_1300004);
			}
			result = this.getTermService().queryCanUpdateSaleTermInfo(lotteryIds, begin_term, end_term, null, limitNum);
		}catch(Exception ex){
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: inputSaleInfo
	*Description: 
	* @param termNo
	* @param startTime
	* @param startTime2
	* @param deadLine
	* @param deadLine3
	* @param deadLine2
	* @param winLine
	* @param winLine2
	* @param saleInfo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputSaleInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	public int inputSaleInfo(String termNo, String startTime, String startTime2, String deadLine, String deadLine3, String deadLine2, String winLine, String winLine2, Map<Integer, Map<String, String>> saleInfo) throws LotteryException {
		int result = 0;
		result = this.inputSaleInfo(LotteryStaticDefine.LOTTERY_1300001, termNo, startTime, startTime2, deadLine, deadLine3, deadLine2, winLine, winLine2, saleInfo);
		result = result
				+ this.inputSaleInfo(LotteryStaticDefine.LOTTERY_1300002, termNo, startTime, startTime2, deadLine, deadLine3, deadLine2, winLine, winLine2, saleInfo);
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCanCashOrderInfoS
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCanCashOrderInfoS(java.lang.String, java.lang.String, int, int)
	 */
	public List<BusBetOrderDomain> getCanCashOrderInfoS(String lotteryId, String termNo, int pageIndex, int perPageNumber) throws LotteryException {
		List<BusBetOrderDomain> result = null;
		try{
			List<String> lotteryAndTerm = this.convertCanCashParam(lotteryId, termNo);
			
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			result = this.getBusBetOrderDao().queryBetOrderCanCashInfo(lotteryAndTerm, startNumber, perPageNumber);
		}catch(LotteryException e){
			throw e;
		}catch(Exception ex){
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCanCashOrderInfosCount
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCanCashOrderInfosCount(java.lang.String, java.lang.String)
	 */
	public int getCanCashOrderInfosCount(String lotteryId, String termNo) throws LotteryException {
		int result = 0;
		try {
			List<String> lotteryAndTerm = this.convertCanCashParam(lotteryId, termNo);
			result = this.getBusBetOrderDao().getBetOrderCanCashInfoCount(lotteryAndTerm);
		} catch (Exception e) {
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/**
	 * 
	 * Title: convertCanCashParam<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 * @throws Exception
	 */
	private List<String> convertCanCashParam(String lotteryId, String termNo) throws LotteryException,Exception{
		List<String> lotteryAndTerm = new ArrayList<String>();
		List<Integer> lotteryIdList = new ArrayList<Integer>();
		
		if(StringUtils.isEmpty(lotteryId) || "0".equals(lotteryId)){
			lotteryIdList.addAll(LotteryStaticDefine.getLotteryList().keySet());
		}else{
			lotteryIdList.add(Integer.parseInt(lotteryId));
		}
		if(StringUtils.isEmpty(termNo) || "0".equals(termNo)){
			for(int InnrelotteryId : lotteryIdList){
				List<String> tmpTerm = this.getTermService().queryCanCashPrizeTermNo(InnrelotteryId);
				if(tmpTerm != null && !tmpTerm.isEmpty()){
					for(String innerTerm : tmpTerm){
						lotteryAndTerm.add(String.valueOf(InnrelotteryId)+innerTerm);
					}
				}else{
					lotteryAndTerm.add(String.valueOf(InnrelotteryId));
				}
				
			}
		}else{
			for(int InnrelotteryId : lotteryIdList){
				lotteryAndTerm.add(String.valueOf(InnrelotteryId)+termNo);
			}
		}
		return lotteryAndTerm;
	}
	/*
	 * (非 Javadoc)
	*Title: getCanDispatchOrderInfoS
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param userIdentify
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCanDispatchOrderInfoS(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	public List<BusBetOrderDomain> getCanDispatchOrderInfoS(String lotteryId, String termNo, String userIdentify, int pageIndex, int perPageNumber) throws LotteryException {
		List<BusBetOrderDomain> result = null;
		try{
			List<String> lotteryAndTerm = this.convertCanDispatchParam(lotteryId, termNo,0);
			
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			result = this.getBusBetOrderDao().queryBetOrderCanDispatchInfo(lotteryAndTerm,userIdentify, startNumber, perPageNumber);
		}catch(LotteryException e){
			throw e;
		}catch(Exception ex){
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCanDispatchOrderInfosCount
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param userIdentify
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCanDispatchOrderInfosCount(java.lang.String, java.lang.String, java.lang.String)
	 */
	public BusBetOrderCountDomain getCanDispatchOrderInfosCount(String lotteryId, String termNo, String userIdentify) throws LotteryException {
		BusBetOrderCountDomain result = new BusBetOrderCountDomain();
		try {
			List<String> lotteryAndTerm = this.convertCanDispatchParam(lotteryId, termNo,0);
			result = this.getBusBetOrderDao().getBetOrderCanDispatchInfoCount(lotteryAndTerm,userIdentify);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/**
	 * 
	 * Title: convertCanDispatchParam<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param lotteryId
	 * @param termNo
	 * @param flag 0,代购 1，合买
	 * @return
	 * @throws LotteryException
	 * @throws Exception
	 */
	private List<String> convertCanDispatchParam(String lotteryId, String termNo,int flag) throws LotteryException,Exception{
		List<String> lotteryAndTerm = new ArrayList<String>();
		List<Integer> lotteryIdList = new ArrayList<Integer>();
		
		if(StringUtils.isEmpty(lotteryId) || "0".equals(lotteryId)){
			lotteryIdList.addAll(LotteryStaticDefine.getLotteryList().keySet());
			if(flag == 1){
				if(lotteryIdList.contains(new Integer(1200001))){
					lotteryIdList.remove(new Integer(1200001));
				}
			}
		}else{
			lotteryIdList.add(Integer.parseInt(lotteryId));
		}
		if(StringUtils.isEmpty(termNo) || "0".equals(termNo)){
			for(int InnrelotteryId : lotteryIdList){
				List<String> tmpTerm = this.getTermService().queryCanDispatchPrizeTermNo(InnrelotteryId);
				if(tmpTerm != null && !tmpTerm.isEmpty()){
					for(String innerTerm : tmpTerm){
						lotteryAndTerm.add(String.valueOf(InnrelotteryId)+innerTerm);
					}
				}else{
					//lotteryAndTerm.add(String.valueOf(InnrelotteryId));
				}
				
			}
		}else{
			for(int InnrelotteryId : lotteryIdList){
				lotteryAndTerm.add(String.valueOf(InnrelotteryId)+termNo);
			}
		}
		return lotteryAndTerm;
	}
	/*
	 * (非 Javadoc)
	*Title: getPrizeUser
	*Description: 
	* @param limitNum
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getPrizeUser(int)
	 */
	public List<PrizeUserDomain> getPrizeUser(int limitNum) throws LotteryException{
		List<PrizeUserDomain> result = new ArrayList<PrizeUserDomain>();
		try{
			result = this.getBusBetOrderDao().getPrizeUser(limitNum);
			
			if(result != null){
				for(PrizeUserDomain one : result){
					one.setLotteryName(LotteryTools.getLotteryName(one.getLotteryId()));
					String tmpMobile = StringUtils
							.isEmpty(one.getMobilePhone()) ? "" : one
							.getMobilePhone().substring(
									0,
									one.getMobilePhone().length() > 4 ? one
											.getMobilePhone().length() - 4
											: one.getMobilePhone().length())
							+ "****";
					one.setMobilePhone(tmpMobile);
				}
			}
			
			if(result == null || result.size() < limitNum){
				result = (result == null)?new ArrayList<PrizeUserDomain>():result;
				int needAdd = limitNum - result.size();
				List<PrizeUserDomain> txtResult = ReadPrizeFile.readLeastUserPrize();
				for(int k =0 ;k < needAdd; k++){
					if(txtResult != null && txtResult.size() > k){
						PrizeUserDomain one = txtResult.get(k);
						one.setLotteryName(LotteryTools.getLotteryName(one.getLotteryId()));
						result.add(one);
					}
				}
			}
		}catch(Exception e){
			logger.error("getPrizeUser is error :", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getPrizeUserOrder
	*Description: 
	* @param limitNum
	* @return
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getPrizeOrder(int)
	 */
	public List<PrizeUserDomain> getPrizeUserOrder(int limitNum) throws LotteryException{
		List<PrizeUserDomain> result = new ArrayList<PrizeUserDomain>();
		try{
			List<PrizeUserDomain> txtResult = ReadPrizeFile.readUserPrizeOrder();
			if(txtResult != null){
				int k = 1;
				 for(PrizeUserDomain one : txtResult){
					 if(k <= limitNum){
						 result.add(one);
					 }
					 k++;
				 }
			}
		}catch(Exception e){
			logger.error("getPrizeUserOrder is error :", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: inputUpdatefailOrder
	*Description: 
	* @param orderId
	* @param winStatus
	* @param wincode
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputUpdatefailOrder(java.lang.String, int, java.lang.String)
	 */
	@Override
	public int inputUpdatefailOrder(String orderId, int winStatus,
			String wincode) throws LotteryException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("winstatus",winStatus);
		map.put("wincode",wincode );
		map.put("orderid", orderId);
		int flg=0;
		try{
			flg=this.getBusBetOrderDao().updateWinState(map);
		}catch (Exception e) {
			logger.error("inputUpdatefailOrder is error :", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return flg;
	}

	public BetPlanOrderServiceInterf getBetOrderService() {
		return betOrderService;
	}

	public void setBetOrderService(BetPlanOrderServiceInterf betOrderService) {
		this.betOrderService = betOrderService;
	}

	public CashPrizeInterf getCashPrizeSevice() {
		return cashPrizeSevice;
	}

	public void setCashPrizeSevice(CashPrizeInterf cashPrizeSevice) {
		this.cashPrizeSevice = cashPrizeSevice;
	}
	/*
	 * (非 Javadoc)
	*Title: getNeedLimitLotteryList
	*Description: 
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getNeedLimitLotteryList()
	 */
	public Map<Integer, String> getNeedLimitLotteryList() throws LotteryException {
		Map<Integer,String> result = new TreeMap<Integer,String>();
		Map<Integer,String> tmp = LotteryTools.getLotteryList();
		for(Map.Entry<Integer, String> oneLottery : tmp.entrySet()){
			int lotteryId = oneLottery.getKey();
			String lotteryName = oneLottery.getValue();
			if(LotteryTools.isLotteryStart(lotteryId) && LotteryStaticDefine.needLimitLottery.contains(lotteryId)){
				result.put(lotteryId, lotteryName);
			}
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: inputJxDlcWinInfo
	*Description: 
	* @param termNo
	* @param lotteryResult
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#inputJxDlcWinInfo(java.lang.String, java.lang.String)
	 */
	public int inputJxDlcWinInfo(String termNo, String lotteryResult) throws LotteryException {
		//开奖结果拆分
		ArrayList<String> lotteryResultArr = new ArrayList<String>();
		Pattern p = Pattern.compile("\\w{2}");
		Matcher m = p.matcher(lotteryResult);
		while(m.find()) {
			lotteryResultArr.add(m.group());
		}
		//奖金结果定义
		LinkedHashMap<String, String[]> winResult = new LinkedHashMap<String,String[]>();
		winResult.put("1", new String[] {"","13"});
		winResult.put("2", new String[] {"","6"});
		winResult.put("3", new String[] {"","19"});
		winResult.put("4", new String[] {"","78"});
		winResult.put("5", new String[] {"","540"});
		winResult.put("6", new String[] {"","90"});
		winResult.put("7", new String[] {"","26"});
		winResult.put("8", new String[] {"","9"});
		winResult.put("9", new String[] {"","130"});
		winResult.put("10", new String[] {"","1170"});
		winResult.put("11", new String[] {"","65"});
		winResult.put("12", new String[] {"","195"});
		
		return this.inputJxDlcWinInfo(termNo, lotteryResultArr, null, null, winResult);
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopCanDispatch
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param userIdentify
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopCanDispatch(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	public List<BusCpInfoDomain> getCoopCanDispatch(String lotteryId, String termNo, String userIdentify, int pageIndex, int perPageNumber) throws LotteryException {
		List<BusCpInfoDomain> result = null;
		try{
			List<String> lotteryAndTerm = this.convertCanDispatchParam(lotteryId, termNo,1);
			
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			result = this.getBusBetOrderDao().queryCoopCanDispatchInfo(lotteryAndTerm,userIdentify, startNumber, perPageNumber);
		}catch(LotteryException e){
			throw e;
		}catch(Exception ex){
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopCanDispatchCount
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param userIdentify
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopCanDispatchCount(java.lang.String, java.lang.String, java.lang.String)
	 */
	public BusBetOrderCountDomain getCoopCanDispatchCount(String lotteryId, String termNo, String userIdentify) throws LotteryException {
		BusBetOrderCountDomain result = new BusBetOrderCountDomain();
		try {
			List<String> lotteryAndTerm = this.convertCanDispatchParam(lotteryId, termNo,1);
			result = this.getBusBetOrderDao().getCoopCanDispatchInfoCount(lotteryAndTerm,userIdentify);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopCanJoinOrder
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param userIdentify
	* @param planProgress
	* @param planMoneyDown
	* @param planMoneyUp
	* @param tiChengDown
	* @param tiChengUp
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopCanJoinOrder(int, java.lang.String, java.lang.String, int, int, int, int, int, int, int)
	 */
	public List<BusCoopPlanDomain> getCoopCanJoinOrder(int lotteryId, String termNo, String userIdentify, int planProgress, int planMoneyDown, int planMoneyUp, int tiChengDown, int tiChengUp, int pageIndex, int perPageNumber) throws LotteryException {
		List<BusCoopPlanDomain> result = null;
		try{
			
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			planMoneyDown = (planMoneyDown == -1)?planMoneyDown:planMoneyDown*100;
			planMoneyUp = (planMoneyUp == -1)?planMoneyUp:planMoneyUp*100;
			result = this.getBusBetOrderDao().getCoopPlanBet(1,lotteryId,
					termNo, userIdentify, planProgress, planMoneyDown,
					planMoneyUp, tiChengDown, tiChengUp,null,null,null,-1, startNumber,
					perPageNumber);
		}catch(Exception ex){
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopCanJoinOrderCount
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param userIdentify
	* @param planProgress
	* @param planMoneyDown
	* @param planMoneyUp
	* @param tiChengDown
	* @param tiChengUp
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopCanJoinOrderCount(int, java.lang.String, java.lang.String, int, int, int, int, int)
	 */
	public int getCoopCanJoinOrderCount(int lotteryId, String termNo, String userIdentify, int planProgress, int planMoneyDown, int planMoneyUp, int tiChengDown, int tiChengUp) throws LotteryException {
		int count = 0;
		try {
			planMoneyDown = (planMoneyDown == -1)?planMoneyDown:planMoneyDown*100;
			planMoneyUp = (planMoneyUp == -1)?planMoneyUp:planMoneyUp*100;
			count = this.getBusBetOrderDao().getCoopPlanBetCount(1,lotteryId,
					termNo, userIdentify, planProgress, planMoneyDown,
					planMoneyUp, tiChengDown, tiChengUp,null,null,null,-1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return count;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopPlans
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param planId
	* @param userIdentify
	* @param planProgress
	* @param planMoneyDown
	* @param planMoneyUp
	* @param tiChengDown
	* @param tiChengUp
	* @param begin_time
	* @param end_time
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopPlans(int, java.lang.String, java.lang.String, java.lang.String, int, int, int, int, int, java.lang.String, java.lang.String, int, int)
	 */
	public List<BusCoopPlanDomain> getCoopPlans(int lotteryId, String termNo,
			String planId, String userIdentify, int planProgress,
			int planMoneyDown, int planMoneyUp, int tiChengDown, int tiChengUp,
			String begin_time, String end_time, int planStatus, int pageIndex,
			int perPageNumber) throws LotteryException {
		List<BusCoopPlanDomain> result = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(begin_time)){
				begin_time_f = sdf.format(sdf.parse(begin_time.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(end_time)){
				end_time_f = sdf.format(sdf.parse(end_time.trim() + " 24:00:00"));
			}
			
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			
			planMoneyDown = (planMoneyDown == -1)?planMoneyDown:planMoneyDown*100;
			planMoneyUp = (planMoneyUp == -1)?planMoneyUp:planMoneyUp*100;
			
			result = this.getBusBetOrderDao().getCoopPlanBet(1,lotteryId, termNo,
					userIdentify, planProgress, planMoneyDown, planMoneyUp,
					tiChengDown, tiChengUp, begin_time_f, end_time_f, planId,planStatus,
					startNumber, perPageNumber);
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopPlansCount
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param planId
	* @param userIdentify
	* @param planProgress
	* @param planMoneyDown
	* @param planMoneyUp
	* @param tiChengDown
	* @param tiChengUp
	* @param begin_time
	* @param end_time
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopPlansCount(int, java.lang.String, java.lang.String, java.lang.String, int, int, int, int, int, java.lang.String, java.lang.String)
	 */
	public int getCoopPlansCount(int lotteryId, String termNo, String planId,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown, int tiChengUp, String begin_time,
			String end_time, int planStatus) throws LotteryException {
		int count = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(begin_time)){
				begin_time_f = sdf.format(sdf.parse(begin_time.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(end_time)){
				end_time_f = sdf.format(sdf.parse(end_time.trim() + " 24:00:00"));
			}
			
			planMoneyDown = (planMoneyDown == -1)?planMoneyDown:planMoneyDown*100;
			planMoneyUp = (planMoneyUp == -1)?planMoneyUp:planMoneyUp*100;
			
			count = this.getBusBetOrderDao().getCoopPlanBetCount(1,lotteryId,
					termNo, userIdentify, planProgress, planMoneyDown,
					planMoneyUp, tiChengDown, tiChengUp,begin_time_f,end_time_f,planId,planStatus);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return count;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopCanYuInfos
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param planId
	* @param coopInfoId
	* @param jionUser
	* @param begin_date
	* @param end_date
	* @param coopType
	* @param orderStatus
	* @param winStatus
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopCanYuInfos(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	public List<BusCpInfoDomain> getCoopCanYuInfos(String lotteryId,
			String termNo, String planId, String coopInfoId, String jionUser,
			String begin_date, String end_date, String coopType,
			String orderStatus, String winStatus, int pageIndex,
			int perPageNumber) throws LotteryException {
		List<BusCpInfoDomain> result = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(begin_date)){
				begin_time_f = sdf.format(sdf.parse(begin_date.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(end_date)){
				end_time_f = sdf.format(sdf.parse(end_date.trim() + " 24:00:00"));
			}
			
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			
			result = this.getBusBetOrderDao().getCoopCanYuInfos(1,lotteryId,
					termNo, planId, coopInfoId, jionUser, begin_time_f, end_time_f,
					coopType, orderStatus, winStatus, startNumber,
					perPageNumber);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopCanYuInfosCount
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param planId
	* @param coopInfoId
	* @param jionUser
	* @param begin_date
	* @param end_date
	* @param coopType
	* @param orderStatus
	* @param winStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopCanYuInfosCount(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int getCoopCanYuInfosCount(String lotteryId, String termNo, String planId, String coopInfoId, String jionUser, String begin_date, String end_date, String coopType, String orderStatus, String winStatus) throws LotteryException {
		int count = 0;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(begin_date)){
				begin_time_f = sdf.format(sdf.parse(begin_date.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(end_date)){
				end_time_f = sdf.format(sdf.parse(end_date.trim() + " 24:00:00"));
			}
			count = this.getBusBetOrderDao().getCoopCanYuInfosCount(1,lotteryId,
					termNo, planId, coopInfoId, jionUser, begin_time_f, end_time_f,
					coopType, orderStatus, winStatus);
		}catch(Exception e){
			e.printStackTrace();
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return count;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopPlanCreateForWeb
	*Description: 
	* @param lotteryId
	* @param betTerm
	* @param userId
	* @param planStatus
	* @param winStatus
	* @param begin_date
	* @param end_date
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopPlanCreateForWeb(int, java.lang.String, int, int, int, java.lang.String, java.lang.String, int, int)
	 */
	public List<BusCoopPlanDomain> getCoopPlanCreateForWeb(int faQiOrCanYu,int lotteryId,
			String betTerm, int userId, int planStatus, int winStatus,
			String begin_date, String end_date, int pageIndex, int perPageNumber)
			throws LotteryException {
		List<BusCoopPlanDomain> result = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(begin_date)){
				begin_time_f = sdf.format(sdf.parse(begin_date.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(end_date)){
				end_time_f = sdf.format(sdf.parse(end_date.trim() + " 24:00:00"));
			}
			
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
				
			}
			
			result = this.getBusBetOrderDao().queryCoopPlanCreateInfos(faQiOrCanYu,
					lotteryId, betTerm, userId, planStatus, winStatus,
					begin_time_f, end_time_f, startNumber, perPageNumber);
		}catch(Exception e){
			logger.error("getCoopPlanCreateForWeb is error :",e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopPlanCreateForWebCount
	*Description: 
	* @param lotteryId
	* @param betTerm
	* @param userId
	* @param planStatus
	* @param winStatus
	* @param begin_date
	* @param end_date
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopPlanCreateForWebCount(int, java.lang.String, int, int, int, java.lang.String, java.lang.String)
	 */
	public int getCoopPlanCreateForWebCount(int faQiOrCanYu,int lotteryId, String betTerm,
			int userId, int planStatus, int winStatus, String begin_date,
			String end_date) throws LotteryException {
		int count = 0;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(begin_date)){
				begin_time_f = sdf.format(sdf.parse(begin_date.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(end_date)){
				end_time_f = sdf.format(sdf.parse(end_date.trim() + " 24:00:00"));
			}
			
			count = this.getBusBetOrderDao().getCoopPlanCreateCount(faQiOrCanYu,lotteryId,
					betTerm, userId, planStatus, winStatus, begin_time_f,
					end_time_f);
			
		}catch(Exception e){
			logger.error("getCoopPlanCreateForWebCount is error ", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return count;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopForWebDetail
	*Description: 
	* @param planId
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopForWebDetail(java.lang.String, int, int)
	 */
	public List<BusCpInfoDomain> getCoopForWebDetail(String planId, int pageIndex, int perPageNumber) throws LotteryException {
		List<BusCpInfoDomain> result = null;
		try{
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			
			result = this.getBusBetOrderDao().getCoopCanYuInfos(0,"0",
					"0", planId, null, null, null, null,
					null, null, null, startNumber,perPageNumber);
			
		}catch(Exception e){
			logger.error("getCoopForWebDetail is error", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopForWebDetailCount
	*Description: 
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopForWebDetailCount(java.lang.String)
	 */
	public int getCoopForWebDetailCount(String planId) throws LotteryException {
		int count = 0;
		try{
			count = this.getBusBetOrderDao().getCoopCanYuInfosCount(0,"0",
					"0", planId, null, null, null, null,
					null, null, null);
		}catch(Exception e){
			logger.error("getCoopForWebDetailCount is error", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return count;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopSelfForWebDetail
	*Description: 
	* @param planId
	* @param userId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopSelfForWebDetail(java.lang.String, int)
	 */
	public List<BusCpInfoDomain> getCoopSelfForWebDetail(String planId, int userId) throws LotteryException {
		List<BusCpInfoDomain> result = null;
		try{
			int startNumber = 0;
			int perPageNumber = Integer.MAX_VALUE;
			
			result = this.getBusBetOrderDao().getCoopCanYuInfos(0,"0",
					"0", planId, null, String.valueOf(userId), null, null,
					null, null, null, startNumber,perPageNumber);
			
		}catch(Exception e){
			logger.error("getCoopForWebDetail is error", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getNotChuPiaoOrderCount
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getNotChuPiaoOrderCount(int, java.lang.String)
	 */
	public int getNotChuPiaoOrderCount(int lotteryId, String termNo) throws LotteryException {
		int count = 0;
		try{
			count = this.getBusBetOrderDao().getNotChuPiaoOrdersCount(lotteryId, termNo);
		}catch(Exception e){
			logger.error("getNotChuPiaoOrderCount is error", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return count;
	}
	/*
	 * (非 Javadoc)
	*Title: getNotChuPiaoOrders
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getNotChuPiaoOrders(int, java.lang.String, int, int)
	 */
	public List<BusBetOrderDomain> getNotChuPiaoOrders(int lotteryId, String termNo, int pageIndex, int perPageNumber) throws LotteryException {
		List<BusBetOrderDomain> result = null;
		try{
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			result = this.getBusBetOrderDao().getNotChuPiaoOrders(lotteryId, termNo, startNumber, perPageNumber);
		}catch(Exception e){
			logger.error("getNotChuPiaoOrders is error", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: getChuPiaoErrorOrderCount
	*Description: 
	* @param daiOrHe
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getChuPiaoErrorOrderCount(int, int, java.lang.String)
	 */
	public int getChuPiaoErrorOrderCount(int daiOrHe, int lotteryId, String termNo) throws LotteryException {
		int count = 0;
		try{
			count = this.getBusBetOrderDao().getChuPiaoErrorOrderCount(daiOrHe,lotteryId, termNo);
		}catch(Exception e){
			logger.error("getChuPiaoErrorOrderCount is error", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return count;
	}
	/*
	 * (非 Javadoc)
	*Title: getChuPiaoErrorOrders
	*Description: 
	* @param daiOrHe
	* @param lotteryId
	* @param termNo
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getChuPiaoErrorOrders(int, int, java.lang.String, int, int)
	 */
	public List<BusBetOrderDomain> getChuPiaoErrorOrders(int daiOrHe, int lotteryId, String termNo, int pageIndex, int perPageNumber) throws LotteryException {
		List<BusBetOrderDomain> result = null;
		try{
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			result = this.getBusBetOrderDao().getChuPiaoErrorOrder(daiOrHe,lotteryId, termNo, startNumber, perPageNumber);
		}catch(Exception e){
			logger.error("getChuPiaoErrorOrders is error", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: planBetCodeIsShow
	*Description: 
	* @param planId
	* @param userId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#planBetCodeIsShow(java.lang.String, int)
	 */
	public String planBetCodeIsShow(String planId, int userId) throws LotteryException {
		String betCode = "";
		try{
			BetPlanDomain betPlan = this.getBetOrderService().queryBetPlanByPlanId(planId);
			if(betPlan == null){
				return "";
			}
			
			int planOpenType = betPlan.getPlanOpenType();
			
			if(planOpenType == 0 || userId == betPlan.getUserId()){
				betCode = betPlan.getBetCode();
			}else if(planOpenType == 2){//截止后公开
				LotteryTermModel termInfo = this.getTermService().queryTermInfo(betPlan.getLotteryId(), betPlan.getStartTerm());
				if(termInfo == null){
					betCode = betPlan.getBetCode();
				}
				
				if(termInfo.getTermStatus() == 2){//彩期已经止售
					betCode = betPlan.getBetCode();
				}
			}else if(planOpenType == 1){//对跟单人公开
				List<BusCpInfoDomain> result = this.getBusBetOrderDao().getCoopCanYuInfos(0,"0",
						"0", planId, null, String.valueOf(userId), null, null,
						null, null, null, 0,1);
				if(result == null || result.isEmpty()){
					betCode = "";
				}else{
					betCode = betPlan.getBetCode();
				}
			}
		}catch(Exception e){
			logger.error("planBetCodeIsShow is error", e);
			throw new LotteryException(LotteryManagerInterf.E_02_CODE,LotteryManagerInterf.E_02_DESC);
		}
		return betCode;
	}
	/*
	 * (非 Javadoc)
	*Title: getCoopPlanForWebShow
	*Description: 
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.LotteryManagerInterf#getCoopPlanForWebShow(java.lang.String)
	 */
	public BusCoopPlanDomain getCoopPlanForWebShow(String planId) throws LotteryException {
		BusCoopPlanDomain planInfo = null;
		try{
			planInfo = this.getBusBetOrderDao().getCoopPlanForWeb(planId);
			if(planInfo != null){
				int planStatus = planInfo.getPlanStatus();
				int lotteryId = planInfo.getLotteryId();
				String betTerm = planInfo.getStartTerm();
				//获取开奖结果
				LotteryTermModel termInfo = this.getTermService().queryTermInfoNotSplit(lotteryId, betTerm);
				if(termInfo != null){
					planInfo.setAreaCode(termInfo.getLotteryResult());
				}
				StringBuffer sb = new StringBuffer();
				String [] dlcPrize = {"任选一中1","任选二中2","任选三中3","任选四中4","任选五中5","任选六中5","任选七中5",
						"任选八中5","选前二直选","选前三直选","选前二组选","选前三组选"};
				if(planStatus == 8){//方案对应的订单已经兑奖完成
					String prizeResult = planInfo.getReserve();
					if(StringUtils.isNotEmpty(prizeResult)){
						String[] prizeArr = prizeResult.split(",");
						for(String onePrize : prizeArr){
							//中奖号码:奖项:总税前:总税后
							String[] one = onePrize.split(":");
							sb.append("中奖号码:").append(one[0]).append("&nbsp;");
							String prizeLevel = one[1];
							if(lotteryId == 1200001){
								int prizeInt = Integer.parseInt(prizeLevel) -1 ;
								sb.append("奖项:").append(dlcPrize[prizeInt]).append("&nbsp;");
							}else{
								sb.append("奖项:").append(prizeLevel).append("等奖&nbsp;");
							}
							
							sb.append("税前:").append(one[2]).append("元&nbsp;");
							sb.append("税后:").append(one[3]).append("元&nbsp;");
						}
						sb.append("<br>");
						sb.append("共计:").append(planInfo.getChaseCount()/100).append("元(税后)&nbsp;");
						sb.append("发起人提成:").append(planInfo.getChasedCount()/100).append("元&nbsp;");
						int totalPrize = planInfo.getChaseCount() - planInfo.getChasedCount();
						int totalUnit = planInfo.getTotalUnit();
						//计算每一份的单价,单位分
						int oneFeiPrize = (new BigDecimal(totalPrize).divide(new BigDecimal(totalUnit))).intValue();
						float showPeize = new Float(oneFeiPrize)/100;
						sb.append("每份奖金:").append(showPeize).append("元&nbsp;");
						
					}
				}
				planInfo.setReserve(StringUtils.isEmpty(sb.toString())?"未中奖":sb.toString());
			}
		}catch(LotteryException e){
			logger.error("getCoopPlanForWebShow is error:"+e.getType()+":"+e.getMessage());
		}catch(Exception ex){
			logger.error("getCoopPlanForWebShow is error", ex);
		}
		return planInfo;
	}
	
}
