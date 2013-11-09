/**
 * Title: BetPlanOrderService.java
 * @Package com.success.lottery.order.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-9 下午04:03:36
 * @version V1.0
 */
package com.success.lottery.order.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.success.lottery.business.service.interf.BetServiceInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.dao.impl.BetOrderDaoImpl;
import com.success.lottery.order.dao.impl.BetPlanDaoImpl;
import com.success.lottery.order.dao.impl.CpInfoDaoImpl;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.domain.CpInfoDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.util.LotterySequence;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.order.service.impl
 * BetPlanOrderService.java
 * BetPlanOrderService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-12 下午04:03:36
 * 
 */
public class BetPlanOrderService implements BetPlanOrderServiceInterf {
	private static Log logger = LogFactory.getLog(BetPlanOrderService.class);
	@SuppressWarnings("unused")
	private static final int PLANSTATUS_0 = 0;//进行中
	private static final int PLANSTATUS_1 = 1;//已生成
	private static final int PLANSTATUS_2 = 2;//部分完成
	private static final int PLANSTATUS_3 = 3;//已完成
	private static final int PLANSTATUS_4 = 4;//已撤销
	
	private static final int PLANTYPE_0 = 0;//代购
	private static final int PLANTYPE_1 = 1;//方案为合买方案
	
	private static final int ORDER_STARUS_0 = 0;//订单未开始
	private static final int ORDER_STARUS_1 = 1;//订单进行中
	private static final int ORDER_STARUS_13 = 13;//追号中
	
	private BetOrderDaoImpl betOrderDao;
	private BetPlanDaoImpl betPlanDao;
	private CpInfoDaoImpl cpInfoDao;//合买订单记录
	/* (非 Javadoc)
	 *Title: insertBetPlanOrder
	 *Description: 
	 * @param betPlan
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#insertBetPlanOrder(com.success.lottery.order.domain.BetPlanDomain)
	 */
	public Map<String,List<String>> insertBetPlanOrder(BetPlanDomain betPlan,BetOrderDomain chuPiaoOrder)
			throws LotteryException {
		Map<String,List<String>> planMap = new HashMap<String,List<String>>();
		checkBetPlanIsNull(betPlan);//对属性校验的方法没有写
		String betPlanKey = insertBetPlan(betPlan);//写入方案表
		
		List<String> betOrder = insertBetOrder(betPlan,chuPiaoOrder);//写入订单表
		planMap.put(betPlanKey, betOrder);
		return planMap;
	}
	/*
	 * (非 Javadoc)
	*Title: queryBetOrderByOrderId
	*Description: 
	* @param orderId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryBetOrderByOrderId(java.lang.String)
	 */
	public BetOrderDomain queryBetOrderByOrderId(String orderId) throws LotteryException {
		try {
			return this.getBetOrderDao().queryBetOrderByOrderId(orderId);
		} catch (Exception e) {
			logger.error("queryBetOrderByOrderId Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryBetOrderByOrderIdForUpdate
	*Description: 
	* @param orderId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryBetOrderByOrderIdForUpdate(java.lang.String)
	 */
	public BetOrderDomain queryBetOrderByOrderIdForUpdate(String orderId) throws LotteryException {
		try {
			return this.getBetOrderDao().queryBetOrderByOrderIdForUpdate(orderId);
		} catch (Exception e) {
			logger.error("queryBetPlanByPlanId Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryBetPlanByPlanId
	*Description: 
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryBetPlanByPlanId(java.lang.String)
	 */
	public BetPlanDomain queryBetPlanByPlanId(String planId) throws LotteryException {
		try {
			return this.getBetPlanDao().queryBetPlanByPlanId(planId);
		} catch (Exception e) {
			logger.error("queryBetPlanByPlanId Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateBetOrderStatus
	*Description: 
	* @param orderId
	* @param orderStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#updateBetOrderStatus(java.lang.String, int)
	 */
	public int updateBetOrderStatus(String orderId, int orderStatus) throws LotteryException {
		try {
			return this.getBetOrderDao().updateBetOrderStatus(orderId, orderStatus);
		} catch (Exception e) {
			logger.error("updateBetOrderStatus Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateBetOrderWinStatus
	*Description: 
	* @param orderId
	* @param winStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#updateBetOrderWinStatus(java.lang.String, int)
	 */
	public int updateBetOrderWinStatus(String orderId, int winStatus) throws LotteryException {
		try {
			return this.getBetOrderDao().updateBetOrderWinStatus(orderId, winStatus);
		} catch (Exception e) {
			logger.error("updateBetOrderWinStatus Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateBetPlanStatus
	*Description: 
	* @param planId
	* @param planStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#updateBetPlanStatus(java.lang.String, int)
	 */
	public int updateBetPlanStatus(String planId, int planStatus) throws LotteryException {
		try {
			return this.getBetPlanDao().updateBetPlanStatus(planId, planStatus);
		} catch (Exception e) {
			logger.error("updateBetPlanStatus Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateBetOrderAndWinStatus
	*Description: 
	* @param orderId
	* @param orderStatus
	* @param winStatus
	* @param prize
	* @param taxPrize
	* @param aftTaxPrize
	* @param deductPrize
	* @param commPrize
	* @param winCode
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#updateBetOrderAndWinStatus(java.lang.String, int, int, long, long, long, long, long, java.lang.String)
	 */
	public int updateBetOrderAndWinStatus(String orderId,int orderStatus, int winStatus, long prize,long taxPrize,long aftTaxPrize,long deductPrize,long commPrize,String winCode) throws LotteryException {
		try {
			BetOrderDomain order = new BetOrderDomain();
			order.setOrderId(orderId);
			order.setOrderStatus(orderStatus);
			order.setWinStatus(winStatus);
			order.setPreTaxPrize(prize);
			order.setTaxPrize(taxPrize);
			order.setAftTaxPrize(aftTaxPrize);
			order.setDeductPrize(deductPrize);
			order.setCommissionPrize(commPrize);
			order.setWinCode(winCode);
			return this.getBetOrderDao().updateBetOrderAndWinStatus(order);
		} catch (Exception e) {
			logger.error("updateBetOrderAndWinStatus Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryOrderByOrderIdForSamePlan
	*Description: 
	* @param planId
	* @param orderId
	* @param orderStatus
	* @param winStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryOrderByOrderIdForSamePlan(java.lang.String, java.lang.String, int, int)
	 */
	public List<BetOrderDomain> queryOrderByOrderIdForSamePlan(String planId,String orderId,List<String> nextTerm, List<Integer> orderStatus, int winStatus) throws LotteryException {
		try {
			return this.getBetOrderDao().queryOrderByOrderIdForSamePlan(planId, orderId,nextTerm, orderStatus, winStatus);
		} catch (Exception e) {
			logger.error("queryOrderByOrderIdForSamePlan Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryOrderNumByStatus
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param orderStatus
	* @param winStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryOrderNumByStatus(int, java.lang.String, int, int)
	 */
	public int queryOrderNumByStatus(int lotteryId, String termNo,int orderStatus,List<Integer> winStatus) throws LotteryException {
		try {
			List<Integer> orderStatusList = new ArrayList<Integer>();
			orderStatusList.add(orderStatus);
			return this.getBetOrderDao().queryOrderNumByStatus(lotteryId, termNo,orderStatusList,winStatus);
		} catch (Exception e) {
			logger.error("queryNotCashOrderNum Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryOrderByStatus
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param orderStatus
	* @param winStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryOrderByStatus(int, java.lang.String, int, int)
	 */
	public List<String> queryOrderByStatus(int lotteryId, String termNo,int orderStatus,List<Integer> winStatus) throws LotteryException {
		try {
			List<Integer> orderStatusList = new ArrayList<Integer>();
			orderStatusList.add(orderStatus);
			return this.getBetOrderDao().queryOrderByStatus(lotteryId, termNo, orderStatusList, winStatus);
		} catch (Exception e) {
			logger.error("queryNotCashOrder Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
		
	}
	/*
	 * (非 Javadoc)
	*Title: getOrders
	*Description: 
	* @param orderStatus
	* @param limitNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#getOrders(java.util.List, int)
	 */
	public List<BetOrderDomain> getOrders(List<Integer> orderStatus, String termInfo, int limitNumber) throws LotteryException {
		try {
			if(orderStatus == null || orderStatus.isEmpty()){
				return null;
			}
			return this.getBetOrderDao().queryUndeliverTicketQueOrder(orderStatus, termInfo, limitNumber);
		} catch (Exception e) {
			logger.error("queryUndeliverTicketQueOrder Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}

	


	/*
	 * (非 Javadoc)
	*Title: updateBetOrderStatus
	*Description: 
	* @param orderIds
	* @param orderStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#updateBetOrderStatus(java.util.List, int)
	 */
	public int updateBetOrderStatus(List<String> orderIds, int orderStatus) throws LotteryException {
		try {
			if(orderIds == null || orderIds.isEmpty()){
				return 0;
			}
			return this.getBetOrderDao().updateBetOrderStatus(orderIds, orderStatus);
		} catch (Exception e) {
			logger.error("updateBetOrderStatus Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	
	public int updateBetOrderStatus(List<String> orderIds, int orderStatus, List<Integer> whoes) throws LotteryException {
		try {
			if(orderIds == null || orderIds.isEmpty()){
				return 0;
			}
			return this.getBetOrderDao().updateBetOrderStatus(orderIds, orderStatus, whoes);
		} catch (Exception e) {
			logger.error("updateBetOrderStatus Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateBetOrderTicketStat
	*Description: 
	* @param orderId
	* @param orderStatus
	* @param ticketStat
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#updateBetOrderTicketStat(java.lang.String, int, java.lang.String)
	 */
	public int updateBetOrderTicketStat(String orderId, int orderStatus, String ticketStat) throws LotteryException{
		
		try {
			if(StringUtils.isEmpty(orderId)){
				return 0;
			}
			return this.getBetOrderDao().updateBetOrderTicketStat(orderId, orderStatus, ticketStat);
		} catch (Exception e) {
			logger.error("updateBetOrderTicketStat Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: getOrers
	*Description: 
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#getOrers(java.lang.String)
	 */
	public List<BetOrderDomain> getOrers(String planId) throws LotteryException {
		try{
			if(StringUtils.isEmpty(planId)){
				return null;
			}
			return this.getBetOrderDao().querySamePlanIdOrders(planId);
		}catch(Exception e){
			logger.error("getOrers Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/**
	 * 
	 * Title: insertBetPlan<br>
	 * Description: <br>
	 *            生成方案<br>
	 * @param betPlan
	 * @return String
	 * @throws LotteryException
	 */
	private String insertBetPlan(BetPlanDomain betPlan) throws LotteryException {
		String betPlanKey = LotterySequence.getInstatce("FA").getSequence();
		betPlan.setPlanId(betPlanKey);
		//对BetPlanDomain对象处理
		int chaseCount = betPlan.getTerms() == null ? 1 : (betPlan.getTerms().size() + 1);
		betPlan.setChaseCount(chaseCount);
		//设置方案状态
		if(betPlan.getPlanType() == PLANTYPE_1){//合买方案
			betPlan.setPlanStatus(PLANSTATUS_0);
			betPlan.setTerms(null);//合买方案不能追号
		}else if(betPlan.getPlanType() == PLANTYPE_0){
			betPlan.setPlanStatus(PLANSTATUS_1);
		}
		try {
			return getBetPlanDao().insertBetPlan(betPlan);
		} catch (Exception e) {
			logger.error("insertBetPlan Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1001_CODE,BetPlanOrderServiceInterf.E_1001_DESC);
		}
	}
	/**
	 * 
	 * Title: insertBetOrder<br>
	 * Description: <br>
	 *            生成投注订单表<br>
	 * @param betPlan
	 * @return  List<String>
	 * @throws LotteryException 
	 */
	private List<String> insertBetOrder(final BetPlanDomain betPlan,BetOrderDomain chuPiaoOrder) throws LotteryException{
		try {
			List<BetOrderDomain> orderList = convertBetPlanToBetOrders(betPlan);
			chuPiaoOrder = orderList.get(0);
			return this.getBetOrderDao().insertBetOrderBatch(orderList);
		} catch (DataAccessException e) {
			logger.error("插入订单表出错,message :",e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1002_CODE,BetPlanOrderServiceInterf.E_1002_DESC);
		}
	}
	
	/**
	 * 
	 * Title: convertBetPlanToBetOrders<br>
	 * Description: <br>
	 *            将一个方案对象拆分为多个订单对象<br>
	 * @param betPlan
	 * @return List<BetOrderDomain>
	 * @throws LotteryException
	 */
	private List<BetOrderDomain> convertBetPlanToBetOrders(final BetPlanDomain betPlan) throws LotteryException{
		List<BetOrderDomain> betOrderList = new ArrayList<BetOrderDomain>();
		
		int chaseCount = betPlan.getChaseCount();
		BetOrderDomain betOrder = new BetOrderDomain();
		betOrder.setOrderId(LotterySequence.getInstatce("DD").getSequence());
		betOrder.setPlanId(betPlan.getPlanId());
		if(chaseCount == 1){//方案中没有追号
			betOrder.setChaseNumber(0);
		}else{
			betOrder.setChaseNumber(1);
		}
		betOrder.setBetTerm(betPlan.getStartTerm());
		int orderStatus = betPlan.isPutQuery() ? ORDER_STARUS_1 : ORDER_STARUS_0;// 是否直接出票,如果直接出票，将订单状态设为进行中，否则设置为未开始
		betOrder.setOrderStatus(orderStatus);
		betOrder.setBetMultiple(betPlan.getBetMultiple());
		betOrder.setBetAmount(betPlan.getUnitAmount());
		
		betOrder.setPlanSource(betPlan.getPlanSource());
		betOrder.setUserId(betPlan.getUserId());
		betOrder.setAreaCode(betPlan.getAreaCode());
		betOrder.setLotteryId(betPlan.getLotteryId());
		betOrder.setPlayType(betPlan.getPlanType());
		betOrder.setBetType(betPlan.getBetType());
		betOrder.setBetCodeMode(betPlan.getBetCodeMode());
		betOrder.setBetCode(betPlan.getBetCode());
		betOrder.setOrderTime(betPlan.getPlanTime());
		
		betOrderList.add(0, betOrder);
		
		//处理追号
		Map<String,Integer> addOrderMap = betPlan.getTerms();
		if(addOrderMap != null && !addOrderMap.isEmpty()){
			int singleBetAmount = betPlan.getUnitAmount() / betPlan.getBetMultiple();
			int chaseNumber = 2;
			for(Map.Entry<String, Integer> oneAddTerm : addOrderMap.entrySet()){
				BetOrderDomain betOrderAdd = new BetOrderDomain();
				betOrderAdd.setOrderId(LotterySequence.getInstatce("DD").getSequence());
				betOrderAdd.setPlanId(betPlan.getPlanId());
				betOrderAdd.setChaseNumber(chaseNumber++);
				betOrderAdd.setBetTerm(oneAddTerm.getKey());
				betOrderAdd.setBetMultiple(oneAddTerm.getValue());
				betOrderAdd.setBetAmount(singleBetAmount * oneAddTerm.getValue());
				betOrderAdd.setOrderStatus(ORDER_STARUS_13);
				betOrderAdd.setPlanSource(betPlan.getPlanSource());
				betOrderAdd.setUserId(betPlan.getUserId());
				betOrderAdd.setAreaCode(betPlan.getAreaCode());
				betOrderAdd.setLotteryId(betPlan.getLotteryId());
				betOrderAdd.setPlayType(betPlan.getPlanType());
				betOrderAdd.setBetType(betPlan.getBetType());
				betOrderAdd.setBetCodeMode(betPlan.getBetCodeMode());
				betOrderAdd.setBetCode(betPlan.getBetCode());
				betOrderAdd.setOrderTime(betPlan.getPlanTime());
				betOrderList.add(betOrderAdd);
			}
		}
		return betOrderList;
	}
	
	
	/**
	 * 
	 * Title: checkBetPlan<br>
	 * Description: <br>
	 *            校验插入方案表数据的有效性，包括不允许为null的属性值<br>
	 * @param betPlan
	 */
	private void checkBetPlanIsNull(final BetPlanDomain betPlan) throws LotteryException{
		
	}

	public BetOrderDaoImpl getBetOrderDao() {
		return this.betOrderDao;
	}

	public void setBetOrderDao(BetOrderDaoImpl betOrderDao) {
		this.betOrderDao = betOrderDao;
	}

	public BetPlanDaoImpl getBetPlanDao() {
		return this.betPlanDao;
	}

	public void setBetPlanDao(BetPlanDaoImpl betPlanDao) {
		this.betPlanDao = betPlanDao;
	}
	
	@Override
	public List<BetOrderDomain> getUserChaseOrders(long userId, int lotteryId, String termNo, Date startDate, Date endDate, int start, int count) throws LotteryException{
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp startTime = null, endTime = null;
			if(startDate != null){
				startTime = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
			}
			if(endDate != null){
				endTime = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
			}
			int totalNumber = this.getBetOrderDao().getUserChaseOrdersCount(userId, lotteryId, termNo, startTime, endTime);
			List<BetOrderDomain> list = this.getBetOrderDao().getUserChaseOrders(userId, lotteryId, termNo, startTime, endTime, start, count);
			BetOrderDomain order = new BetOrderDomain();
			order.setLotteryId(totalNumber);
			if(list == null){
				list = new ArrayList<BetOrderDomain>();
			}
			list.add(0, order);
			return list;
		}catch(Exception e){
			logger.error(e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE, BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}

	@Override
	public List<BetOrderDomain> getUserOrders(long userId, int lotteryId, String termNo, int planSource, int orderStatus, int winStatus, Date startDate, Date endDate, int start, int count) throws LotteryException{
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp startTime = null, endTime = null;
			if(startDate != null){
				startTime = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
			}
			if(endDate != null){
				endTime = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
			}
			int totalNumber = this.getBetOrderDao().getUserOrdersCount(userId, lotteryId, termNo, planSource, orderStatus, winStatus, startTime, endTime).getChaseNumber();
			List<BetOrderDomain> list = this.getBetOrderDao().getUserOrders(userId, lotteryId, termNo, planSource, orderStatus, winStatus, startTime, endTime, start, count);
			BetOrderDomain order = new BetOrderDomain();
			order.setLotteryId(totalNumber);
			if(list == null){
				list = new ArrayList<BetOrderDomain>();
			}
			list.add(0, order);
			return list;
		}catch(Exception e){
			logger.error(e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE, BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	
	
	public static void main(String[] args) throws ParseException, LotteryException{
		BetPlanOrderServiceInterf orderService = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start = format.parse("2010-05-28");
		Date end = format.parse("2010-05-28");
		List<BetOrderDomain> list = orderService.getUserOrders(1, 1000001, "10061", 0, 0, 1, start, end, 0, 50);
		for(int i = 0; i < list.size(); i++){
			System.out.println("userorder: " + list.get(i).getLotteryId() + ", " + list.get(i).getOrderId());
		}
		System.out.println("--------------------------------------------");
		List<BetOrderDomain> list1 = orderService.getUserChaseOrders(1, 1000001, "10061", start, end, 0, 50);
		for(int i = 0; i < list1.size(); i++){
			System.out.println("userchaseorder: " + list1.get(i).getLotteryId() + ", " + list1.get(i).getOrderId());
		}
		

	}
	@Override
	public List<BetOrderDomain> getUserCashedOrders(long userId, int lotteryId, String termNo, Date startDate, Date endDate, int start, int count) throws LotteryException{
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp startTime = null, endTime = null;
			if(startDate != null){
				startTime = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
			}
			if(endDate != null){
				endTime = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
			}
			
			BetOrderDomain totalNumberDomain = this.getBetOrderDao().getUserOrdersTwoCount(userId, lotteryId, termNo,startTime, endTime);
			List<BetOrderDomain> list = this.getBetOrderDao().getUserOrdersTwo(userId, lotteryId, termNo,startTime, endTime, start, count);
			BetOrderDomain order = new BetOrderDomain();
			order.setPlanSource(totalNumberDomain.getChaseNumber());
			order.setPreTaxPrize(totalNumberDomain.getPreTaxPrize());
			if(list == null){
				list = new ArrayList<BetOrderDomain>();
			}
			
			list.add(0, order);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE, BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryOrderNumByStatus
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param orderStatus
	* @param winStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryOrderNumByStatus(int, java.lang.String, java.util.List, java.util.List)
	 */
	public int queryOrderNumByStatus(int lotteryId, String termNo, List<Integer> orderStatus, List<Integer> winStatus) throws LotteryException {
		try {
			return this.getBetOrderDao().queryOrderNumByStatus(lotteryId, termNo,orderStatus,winStatus);
		} catch (Exception e) {
			logger.error("queryNotCashOrderNum Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}

	@Override
	public List<BetOrderDomain> get2CheckOrders(List<Integer> orderStatus, List<Integer> ticketStatus, int limitNumber) throws LotteryException {
		try {
			if(orderStatus == null || orderStatus.isEmpty() || ticketStatus == null || ticketStatus.isEmpty()){
				return null;
			}
			return this.getBetOrderDao().query2CheckOrder(orderStatus, ticketStatus, limitNumber);
		} catch (Exception e) {
			logger.error("queryUndeliverTicketQueOrder Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryOrderByStatus
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param orderStatus
	* @param winStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryOrderByStatus(int, java.lang.String, java.util.List, java.util.List)
	 */
	public List<String> queryOrderByStatus(int lotteryId, String termNo, List<Integer> orderStatus, List<Integer> winStatus) throws LotteryException {
		try {
			return this.getBetOrderDao().queryOrderByStatus(lotteryId, termNo, orderStatus, winStatus);
		} catch (Exception e) {
			logger.error("queryNotCashOrder Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	
	/*
	 * (非 Javadoc)
	*Title: queryBetPlanForUpdate
	*Description: 
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryBetPlanForUpdate(java.lang.String)
	 */
	public BetPlanDomain queryBetPlanForUpdate(String planId) throws LotteryException {
		try {
			return this.getBetPlanDao().queryBetPlanForUpdate(planId);
		} catch (Exception e) {
			logger.error("queryBetPlanForUpdate Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateBetPlanSelledUnit
	*Description: 
	* @param planId
	* @param selledUnit
	* @param planStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#updateBetPlanSelledUnit(java.lang.String, int, int)
	 */
	public int updateBetPlanSelledUnit(String planId, int selledUnit, int planStatus) throws LotteryException {
		try {
			return this.getBetPlanDao().updatePlanSelledAndStatus(planId, selledUnit, planStatus);
		} catch (Exception e) {
			logger.error("updateBetPlanSelledUnit Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryCoopInfoByPlanId
	*Description: 
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryCoopInfoByPlanId(java.lang.String)
	 */
	public List<CpInfoDomain> queryCoopInfoByPlanId(String planId) throws LotteryException {
		try {
			return this.getCpInfoDao().getCoopInfoByPlanId(planId);
		} catch (Exception e) {
			logger.error("queryCoopInfoByPlanId Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: updateCoopInfoStatus
	*Description: 
	* @param coopInfoId
	* @param orderStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#updateCoopInfoStatus(java.lang.String, int)
	 */
	public int updateCoopInfoStatus(String coopInfoId, int orderStatus) throws LotteryException {
		try {
			return this.getCpInfoDao().upCoopInfostatus(coopInfoId, orderStatus);
		} catch (Exception e) {
			logger.error("updateCoopInfoStatus Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryCoopInfoByIdForUpdate
	*Description: 
	* @param coopInfoId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryCoopInfoByIdForUpdate(java.lang.String)
	 */
	public CpInfoDomain queryCoopInfoByIdForUpdate(String coopInfoId) throws LotteryException {
		try {
			return this.getCpInfoDao().getCoopInfoForUpdate(coopInfoId);
		} catch (Exception e) {
			logger.error("queryCoopInfoByPlanId Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	
	public String createCoopBetOrder(String planId, int planSource, long userId, String areaCode, int lotteryId, String betTerm, int playType, int betType, int betCodeMode, String betCode, int betMultiple, int betAmount, int orderStatus) throws LotteryException {
		String coopOrderId = null;
		try{
			BetOrderDomain betOrder = new BetOrderDomain();
			betOrder.setPlanId(planId);
			betOrder.setOrderId(LotterySequence.getInstatce("DD").getSequence());
			betOrder.setPlanSource(planSource);
			betOrder.setUserId(userId);
			betOrder.setAreaCode(areaCode);
			betOrder.setLotteryId(lotteryId);
			betOrder.setBetTerm(betTerm);
			betOrder.setPlayType(playType);
			betOrder.setBetType(betType);
			betOrder.setBetCodeMode(betCodeMode);
			betOrder.setBetCode(betCode);
			betOrder.setBetMultiple(betMultiple);
			betOrder.setBetAmount(betAmount);
			betOrder.setOrderStatus(orderStatus);
			coopOrderId = this.getBetOrderDao().insertBetCoopOrder(betOrder);;
		}catch(Exception e){
			throw new LotteryException(BetPlanOrderServiceInterf.E_1002_CODE,BetPlanOrderServiceInterf.E_1002_DESC);
		}
		return coopOrderId;
	}
	public String createCoopInfo(String planId, long userId, int lotteryId, String betTerm, int playType, int betType, int cpOrderType, int cpUnit, int cpAmount, int orderStatus) throws LotteryException {
		String infoId = null;
		try{
			CpInfoDomain cpInfo = new CpInfoDomain();
			cpInfo.setPlanId(planId);
			cpInfo.setCpInfoId(LotterySequence.getInstatce("CD").getSequence());
			cpInfo.setUserId(userId);
			cpInfo.setLotteryId(lotteryId);
			cpInfo.setBetTerm(betTerm);
			cpInfo.setPlayType(playType);
			cpInfo.setBetType(betType);
			cpInfo.setCpOrderType(cpOrderType);
			cpInfo.setCpUnit(cpUnit);
			cpInfo.setCpAmount(cpAmount);
			cpInfo.setOrderStatus(orderStatus);
			infoId = this.getCpInfoDao().insertCpOrderSingle(cpInfo);
		}catch(Exception e){
			throw new LotteryException(BetPlanOrderServiceInterf.E_1006_CODE,BetPlanOrderServiceInterf.E_1006_DESC);
		}
		return infoId;
	}
	public String createCoopPlan(long userId, String areaCode, int lotteryId, int playType, int betType, int betMultiple, int betTotalMoney, String term, Timestamp coopDeadLine, String betCode, int planOpenType, int totalUnit, int unitPrice, int selfBuyNum, int unitbuyself, int commisionPercent, int planSource, String planTitle, String planDescription, int planStatus) throws LotteryException {
		String planId = null;
		try{
			BetPlanDomain betPlan = new BetPlanDomain();
			betPlan.setPlanId(LotterySequence.getInstatce("FA").getSequence());
			betPlan.setPlanType(1);
			betPlan.setUserId(userId);
			betPlan.setAreaCode(areaCode);
			betPlan.setLotteryId(lotteryId);
			betPlan.setPlayType(playType);
			betPlan.setBetType(betType);
			betPlan.setBetCodeMode(0);
			betPlan.setBetMultiple(betMultiple);
			betPlan.setUnitAmount(betTotalMoney);
			betPlan.setStartTerm(term);
			betPlan.setStopTime(coopDeadLine);
			betPlan.setBetCode(betCode);
			betPlan.setPlanOpenType(planOpenType);
			betPlan.setTotalUnit(totalUnit);
			betPlan.setUnitPrice(unitPrice);
			betPlan.setSelledUnit(selfBuyNum);
			betPlan.setUnitBuySelf(unitbuyself);
			betPlan.setCommisionPercent(commisionPercent);
			betPlan.setPlanSource(planSource);
			betPlan.setPlanTitle(planTitle);
			betPlan.setPlanDescription(planDescription);
			betPlan.setPlanStatus(planStatus);
			betPlan.setChaseCount(1);
			planId = this.getBetPlanDao().insertBetPlan(betPlan);
		}catch(Exception e){
			e.printStackTrace();
			throw new LotteryException(BetPlanOrderServiceInterf.E_1001_CODE,BetPlanOrderServiceInterf.E_1001_DESC);
		}
		return planId;
	}
	/*
	 * (非 Javadoc)
	*Title: queryBetPlan
	*Description: 
	* @param lotteryAndTerm
	* @param planType
	* @param planStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryBetPlan(java.util.Map, java.util.List, java.util.List)
	 */
	public List<BetPlanDomain> queryBetPlan(Map<Integer, String> lotteryAndTerm, List<Integer> planType, List<Integer> planStatus) throws LotteryException {
		List<BetPlanDomain> planResult = null;
		try{
			if (lotteryAndTerm == null || lotteryAndTerm.isEmpty()
					|| planType == null || planType.isEmpty()
					|| planStatus == null || planStatus.isEmpty()) {
				throw new LotteryException(BetPlanOrderServiceInterf.E_1007_CODE,BetPlanOrderServiceInterf.E_1007_DESC);
			}
			List<String> lterms = new ArrayList<String>();
			for(Map.Entry<Integer, String> oneParam : lotteryAndTerm.entrySet()){
				lterms.add(String.valueOf(oneParam.getKey())+oneParam.getValue());
			}
			
			planResult = this.getBetPlanDao().getPlanInfoByLotteryTerm(lterms, planType, planStatus);
		}catch(LotteryException e){
			throw e;
		}catch(Exception ex){
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
		return planResult;
	}
	
	public CpInfoDaoImpl getCpInfoDao() {
		return cpInfoDao;
	}
	public void setCpInfoDao(CpInfoDaoImpl cpInfoDao) {
		this.cpInfoDao = cpInfoDao;
	}
	/*
	 * (非 Javadoc)
	*Title: updateCoopPrize
	*Description: 
	* @param coopInfoId
	* @param orderStatus
	* @param winStatus
	* @param prize
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#updateCoopPrize(java.lang.String, int, int, long)
	 */
	public int updateCoopPrize(String coopInfoId, int orderStatus, int winStatus, long prize) throws LotteryException {
		try {
			return this.getCpInfoDao().upCoopInfoPrize(coopInfoId, orderStatus, winStatus, prize);
		} catch (Exception e) {
			logger.error("updateCoopPrize Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1004_CODE,BetPlanOrderServiceInterf.E_1004_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryBetOrderByPlanId
	*Description: 
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryBetOrderByPlanId(java.lang.String)
	 */
	public List<BetOrderDomain> queryBetOrderByPlanId(String planId) throws LotteryException {
		try {
			return this.getBetOrderDao().getOrderByPlanId(planId);
		} catch (Exception e) {
			logger.error("queryBetOrderByPlanId Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryCoopInfoByInfoId
	*Description: 
	* @param coopInfoId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#queryCoopInfoByInfoId(java.lang.String)
	 */
	public CpInfoDomain queryCoopInfoByInfoId(String coopInfoId) throws LotteryException {
		try {
			return this.getCpInfoDao().getCoopInfoByInfoId(coopInfoId);
		} catch (Exception e) {
			logger.error("queryCoopInfoByInfoId Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: getNotDispatchCoopNum
	*Description: 
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.order.service.interf.BetPlanOrderServiceInterf#getNotDispatchCoopNum(java.lang.String)
	 */
	public int getNotDispatchCoopNum(String planId) throws LotteryException {
		try {
			return this.getCpInfoDao().getNotDispatchInfoNum(planId);
		} catch (Exception e) {
			logger.error("getNotDispatchCoopNum Error :", e);
			throw new LotteryException(BetPlanOrderServiceInterf.E_1003_CODE,BetPlanOrderServiceInterf.E_1003_DESC);
		}
	}
	
	
}
