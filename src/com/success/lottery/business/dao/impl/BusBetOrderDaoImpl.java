/**
 * Title: BetOrderDaoImpl.java
 * @Package com.success.lottery.order.dao.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-9 下午04:05:00
 * @version V1.0
 */
package com.success.lottery.business.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.success.lottery.business.domain.BusBetOrderCountDomain;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.domain.BusBetOrderParam;
import com.success.lottery.business.domain.BusCoopPlanDomain;
import com.success.lottery.business.domain.BusCpInfoDomain;
import com.success.lottery.business.domain.PrizeUserDomain;
import com.success.lottery.order.domain.BetOrderDomain;

/**
 * com.success.lottery.order.dao.impl
 * BetOrderDaoImpl.java
 * BetOrderDaoImpl
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-9 下午04:05:00
 * 
 */

public class BusBetOrderDaoImpl extends SqlMapClientDaoSupport implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 8409676705059725431L;
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	
	@SuppressWarnings("unchecked")
	public List<BusBetOrderDomain> queryBetOrderInfoS(BusBetOrderParam param){
		return this.smcTemplate.queryForList("busBetOrder.queryBetOrderInfoS", param);
	}
	
	public int getBetOrderInfosCount(BusBetOrderParam param){
		return (Integer)this.smcTemplate.queryForObject("busBetOrder.getBetOrderInfosCount", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<BusBetOrderDomain> queryBetOrderCanCashInfo(List<String> lotteryAndTerm,int startPageNumber,int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryIdTerms", lotteryAndTerm);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.smcTemplate.queryForList("busBetOrder.queryBetOrderCanCashInfo", param);
	}
	
	public int getBetOrderCanCashInfoCount(List<String> lotteryAndTerm){
		Map<String,List<String>> param = new HashMap<String,List<String>>();
		param.put("lotteryIdTerms", lotteryAndTerm);
		return (Integer)this.smcTemplate.queryForObject("busBetOrder.getBetOrderCanCashInfoCount", param);
	}
	/**
	 * 
	 * Title: queryBetOrderCanDispatchInfo<br>
	 * Description: <br>
	 *              <br>代购派奖
	 * @param lotteryAndTerm
	 * @param userIdentify
	 * @param startPageNumber
	 * @param endPageNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BusBetOrderDomain> queryBetOrderCanDispatchInfo(List<String> lotteryAndTerm,String userIdentify,int startPageNumber,int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryIdTerms", lotteryAndTerm);
		param.put("userIdentify", userIdentify);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.smcTemplate.queryForList("busBetOrder.queryBetOrderCanDispatchInfo", param);
	}
	
	/**
	 * 
	 * Title: getBetOrderCanDispatchInfoCount<br>
	 * Description: <br>
	 *              <br>代购派奖
	 * @param lotteryAndTerm
	 * @param userIdentify
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BusBetOrderCountDomain getBetOrderCanDispatchInfoCount(List<String> lotteryAndTerm,String userIdentify){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryIdTerms", lotteryAndTerm);
		param.put("userIdentify", userIdentify);
		return (BusBetOrderCountDomain)this.smcTemplate.queryForObject("busBetOrder.getBetOrderCanDispatchInfoCount", param);
	}
	/**
	 * 
	 * Title: queryCoopCanDispatchInfo<br>
	 * Description: <br>
	 *              <br>合买派奖查询列表
	 * @param lotteryAndTerm
	 * @param userIdentify
	 * @param startPageNumber
	 * @param endPageNumber
	 * @return
	 */
	public List<BusCpInfoDomain> queryCoopCanDispatchInfo(List<String> lotteryAndTerm,String userIdentify,int startPageNumber,int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryIdTerms", lotteryAndTerm);
		param.put("userIdentify", userIdentify);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.smcTemplate.queryForList("busCoopBetOrder.queryCoopInfoCanDispatch", param);
	}
	/**
	 * 
	 * Title: getCoopCanDispatchInfoCount<br>
	 * Description: <br>
	 *              <br>合买派奖查询统计
	 * @param lotteryAndTerm
	 * @param userIdentify
	 * @return
	 */
	public BusBetOrderCountDomain getCoopCanDispatchInfoCount(List<String> lotteryAndTerm,String userIdentify){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryIdTerms", lotteryAndTerm);
		param.put("userIdentify", userIdentify);
		return (BusBetOrderCountDomain)this.smcTemplate.queryForObject("busCoopBetOrder.getCoopInfoCanDispatchCount", param);
	}
	
	/**
	 * 
	 * Title: getCoopCanYuBet<br>
	 * Description: <br>
	 *              <br>合买参与投注列表
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @param planProgress
	 * @param planMoneyDown
	 * @param planMoneyUp
	 * @param tiChengDown
	 * @param tiChengUp
	 * @param startPageNumber
	 * @param endPageNumber
	 * @return
	 */
	public List<BusCoopPlanDomain> getCoopPlanBet(int planType,int lotteryId, String termNo,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp,String begin_Time,String end_Time,
			String planId,int planStatus,int startPageNumber, int endPageNumber) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("planType", planType);
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		param.put("planProgress", planProgress);
		param.put("planMoneyDown", planMoneyDown);
		param.put("planMoneyUp", planMoneyUp);
		param.put("tiChengDown", tiChengDown);
		param.put("tiChengUp", tiChengUp);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		param.put("begin_Time", begin_Time);
		param.put("end_Time", end_Time);
		param.put("planId", planId);
		param.put("planStatus", planStatus);
		return this.smcTemplate.queryForList("busCoopBetOrder.queryCoopPlan", param);
	}
	/**
	 * 
	 * Title: getCoopCanYuBetCount<br>
	 * Description: <br>
	 *              <br>合买参与投注列表统计
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @param planProgress
	 * @param planMoneyDown
	 * @param planMoneyUp
	 * @param tiChengDown
	 * @param tiChengUp
	 * @return
	 */
	public int getCoopPlanBetCount(int planType,int lotteryId, String termNo,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp,String begin_Time,String end_Time,
			String planId,int planStatus){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("planType", planType);
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		param.put("planProgress", planProgress);
		param.put("planMoneyDown", planMoneyDown);
		param.put("planMoneyUp", planMoneyUp);
		param.put("tiChengDown", tiChengDown);
		param.put("tiChengUp", tiChengUp);
		param.put("begin_Time", begin_Time);
		param.put("end_Time", end_Time);
		param.put("planId", planId);
		param.put("planStatus", planStatus);
		return (Integer)this.smcTemplate.queryForObject("busCoopBetOrder.queryCoopPlanCount", param);
	}
	
	/**
	 * 
	 * Title: getCoopCanYuInfos<br>
	 * Description: <br>
	 *              <br>查询合买参与信息列表
	 * @param isQianOrHou 0-前台查询，1-后台查询
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
	 * @param startPageNumber
	 * @param endPageNumber
	 * @return
	 */
	public List<BusCpInfoDomain> getCoopCanYuInfos(int isQianOrHou,String lotteryId,
			String termNo, String planId, String coopInfoId, String jionUser,
			String begin_date, String end_date, String coopType,
			String orderStatus, String winStatus, int startPageNumber,
			int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("isQianOrHou", isQianOrHou);
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		param.put("planId", planId);
		param.put("coopInfoId", coopInfoId);
		param.put("jionUser", jionUser);
		param.put("begin_Time", begin_date);
		param.put("end_Time", end_date);
		param.put("coopType", coopType);
		param.put("orderStatus", orderStatus);
		param.put("winStatus", winStatus);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.smcTemplate.queryForList("busCoopBetOrder.queryCoopInfos", param);
	}
	/**
	 * 
	 * Title: getCoopCanYuInfosCount<br>
	 * Description: <br>
	 *              <br>查询合买参与信息统计
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
	 */
	public int getCoopCanYuInfosCount(int isQianOrHou,String lotteryId,
			String termNo, String planId, String coopInfoId, String jionUser,
			String begin_date, String end_date, String coopType,
			String orderStatus, String winStatus){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("isQianOrHou", isQianOrHou);
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		param.put("planId", planId);
		param.put("coopInfoId", coopInfoId);
		param.put("jionUser", jionUser);
		param.put("begin_Time", begin_date);
		param.put("end_Time", end_date);
		param.put("coopType", coopType);
		param.put("orderStatus", orderStatus);
		param.put("winStatus", winStatus);
		return (Integer)this.smcTemplate.queryForObject("busCoopBetOrder.getCoopInfosCount", param);
	}
	/**
	 * 
	 * Title: queryCoopPlanCreateInfos<br>
	 * Description: <br>
	 *              <br>前台查询我发起的合买
	 * @param lotteryId
	 * @param betTerm
	 * @param userId
	 * @param planStatus
	 * @param winStatus
	 * @param begin_date
	 * @param end_date
	 * @param startPageNumber
	 * @param endPageNumber
	 * @return
	 */
	public List<BusCoopPlanDomain> queryCoopPlanCreateInfos(int faQiOrCanYu,int lotteryId, String betTerm, int userId, int planStatus,
			int winStatus,String begin_date, String end_date,int startPageNumber, int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("faQiOrCanYu", faQiOrCanYu);
		param.put("lotteryId", lotteryId);
		param.put("betTerm", betTerm);
		param.put("userId", userId);
		param.put("planStatus", planStatus);
		param.put("winStatus", winStatus);
		param.put("begin_Time", begin_date);
		param.put("end_Time", end_date);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.smcTemplate.queryForList("busCoopBetOrder.queryCoopPlanCreateInfos", param);
		
	}
	/**
	 * 
	 * Title: getCoopPlanCreateCount<br>
	 * Description: <br>
	 *              <br>前台查询我发起的合买
	 * @param lotteryId
	 * @param betTerm
	 * @param userId
	 * @param planStatus
	 * @param winStatus
	 * @param begin_date
	 * @param end_date
	 * @return
	 */
	public int getCoopPlanCreateCount(int faQiOrCanYu,int lotteryId, String betTerm, int userId, int planStatus,
			int winStatus,String begin_date, String end_date){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("faQiOrCanYu", faQiOrCanYu);
		param.put("lotteryId", lotteryId);
		param.put("betTerm", betTerm);
		param.put("userId", userId);
		param.put("planStatus", planStatus);
		param.put("winStatus", winStatus);
		param.put("begin_Time", begin_date);
		param.put("end_Time", end_date);
		return (Integer)this.smcTemplate.queryForObject("busCoopBetOrder.getCoopPlanCreateCount", param);
	}
	@SuppressWarnings("unchecked")
	public List<BusBetOrderDomain> getOrdersByPlanId(String planId){
		return this.smcTemplate.queryForList("busBetOrder.getOrdersByPlanId", planId);
	}
	@SuppressWarnings("unchecked")
	public List<PrizeUserDomain> getPrizeUser(int limitNum){
		return this.smcTemplate.queryForList("busBetOrder.getPrizeUser", limitNum <= 0 ? Integer.MAX_VALUE : limitNum);
	}
	
	@SuppressWarnings("unchecked")
	public int updateWinState(Map map){
		return this.smcTemplate.update("busBetOrder.updateWinState",map);
	}
	
	public List<BusBetOrderDomain> getNotChuPiaoOrders(int lotteryId,String termNo,int startPageNumber, int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.smcTemplate.queryForList("busBetOrder.getNotChuPiaoOrder", param);
	}
	
	public int getNotChuPiaoOrdersCount(int lotteryId,String termNo){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		return (Integer)this.smcTemplate.queryForObject("busBetOrder.getNotChuPiaoOrderCount", param);
	}
	
	public List<BusBetOrderDomain> getChuPiaoErrorOrder(int daiOrHe,int lotteryId,String termNo,int startPageNumber, int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("daiOrHe", daiOrHe);
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.smcTemplate.queryForList("busBetOrder.getChuPiaoErrorOrder", param);
	}
	
	public int getChuPiaoErrorOrderCount(int daiOrHe,int lotteryId,String termNo){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("daiOrHe", daiOrHe);
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		return (Integer)this.smcTemplate.queryForObject("busBetOrder.getChuPiaoErrorOrderCount", param);
	}
	
	/**
	 * 
	 * Title: getCoopPlanForWeb<br>
	 * Description: <br>
	 *              <br>查询合买方案的详情
	 * @param planId
	 * @return
	 */
	public BusCoopPlanDomain getCoopPlanForWeb(String planId){
		Object obj = this.smcTemplate.queryForObject("busCoopBetOrder.getCoopPlanForWeb", planId);
		return (obj==null)?null:(BusCoopPlanDomain)obj;
	}
	
}
