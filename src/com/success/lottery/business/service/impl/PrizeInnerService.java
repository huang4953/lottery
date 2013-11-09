/**
 * Title: PrizeInnerService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(兑奖和派奖内部处理类)
 * @author gaoboqin
 * @date 2010-4-21 下午05:12:08
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.service.interf.PrizeInnerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.domain.CpInfoDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.util.PrizeInfo;
import com.success.lottery.util.core.LotteryFactory;
import com.success.lottery.util.core.LotteryUnDefineException;

/**
 * com.success.lottery.business.service.impl
 * PrizeInnerService.java
 * PrizeInnerService
 * 兑奖和派奖内部处理类
 * @author gaoboqin
 * 2010-4-21 下午05:12:08
 * 
 */

public class PrizeInnerService implements PrizeInnerInterf {
	private static Log logger = LogFactory.getLog(PrizeInnerService.class);
	private static final int ORDER_STATUS_0 = 0;//订单未开始
	private static final int ORDER_STATUS_4 = 4;//订单出票半成功
	private static final int ORDER_STATUS_5 = 5;//订单出票成功
	private static final int ORDER_STATUS_8 = 8;//订单兑奖完成
	private static final int ORDER_STATUS_11 = 11;//订单状态为撤销
	private static final int ORDER_STATUS_13 = 13;//订单状态为追号中
	private static final int ORDER_STATUS_10 = 10;//订单状态为派奖完成
	private static final int ORDER_STATUS_14 = 14;//订单状态为限号取消
	
	private static final int TICKET_STATUS_6 = 6;//彩票状态为出票成功
	private static final int TICKET_STATUS_7 = 7;//彩票状态为出票失败
	
	private static final int WIN_STATUS_0 = 0;//中奖状态为未兑奖
	private static final int WIN_STATUS_1 = 1;//中奖状态为未中奖
	private static final int WIN_STATUS_2 = 2;//中奖状态为中小奖
	private static final int WIN_STATUS_3 = 3;//中奖状态为中大奖
	
	private static final int TERM_WIN_STATUS_2 = 2;//彩期表的开奖状态2，表示已经开奖
	private static final int TERM_WIN_STATUS_3 = 3;//彩期表的开奖状态3，表示正在兑奖
	private static final int TERM_WIN_STATUS_4 = 4;//彩期表的开奖状态4，表示兑奖完成
	private static final int TERM_WIN_STATUS_7 = 7;//彩期表的开奖状态7，表示正在派奖
	private static final int TERM_WIN_STATUS_8 = 8;//彩期表的开奖状态8，表示派奖完成
	
	private static final int WIN_STOPED = 1;//中奖后停止追号标志
	private static final int WIN_NOT_STOPED = 0;//中奖后不停止追号标志
	
	private static final int TRANSACTION_SOURCE_TYPE = 2002;//交易渠道，投注订单
	
	private static final int TRANSACTION_TYPE_31006 = 31006;//交易类别，追号订单撤销返还冻结资金
	private static final int TRANSACTION_TYPE_11002 = 11002;//交易类别，投注订单中奖奖金30001
	private static final int TRANSACTION_TYPE_30001 = 30001;//交易类别，投注订单追号投注成功
	//潘祖朝增加的限号撤销追号订单返还冻结资金以供明细查询时候的备注用，为不和秦高坡定义冲突取为31117
	private static final int TRANSACTION_TYPE_31117 = 31117;//交易类别，限号撤销追号订单返还冻结资金
	private static final String[] ZHUI_JIA_ARR = {"10000011"};
	
	
	
	/*
	 * 定义依赖的服务接口
	 */
	private BetPlanOrderServiceInterf betOrderService;
	private LotteryTermServiceInterf termService;
	private AccountService userService;//用户账户服务
	private BetTicketServiceInterf ticketService;//彩票服务

	/*
	 * (非 Javadoc)
	*Title: cashPrize
	*Description: 
	* @param lotteryId
	* @param cashTerm
	* @param orderId
	* @param termInfo
	* @param isCheckLotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#cashPrize(int, java.lang.String, java.lang.String, com.success.lottery.term.domain.LotteryTermModel, boolean)
	*/
	public String cashPrize(int lotteryId,String cashTerm, String orderId,LotteryTermModel termInfo,boolean isCheckLotteryId,Map<String,String> outResult)
			throws LotteryException { 
		/*
		 * 获取订单并锁定订单
		 */
		BetOrderDomain orderInfo = this.checkBetOrderCanCashForUpdate(orderId);
		int OrderLotteryId = 0;
		String OrderTermNo = "";
		
		if(orderInfo != null){
			OrderLotteryId = orderInfo.getLotteryId();
			OrderTermNo = orderInfo.getBetTerm();
			outResult.put("A", String.valueOf(orderInfo.getBetAmount()));
			outResult.put("B", String.valueOf(orderInfo.getUserId()));
		}
		/*
		 * 判断订单是否能兑奖，用于判断并发兑奖的情况
		 */
		if (orderInfo == null
				|| (orderInfo.getOrderStatus() != ORDER_STATUS_5 && orderInfo
						.getOrderStatus() != ORDER_STATUS_4)
				|| (orderInfo.getWinStatus() != WIN_STATUS_0 && orderInfo.getWinStatus() != 99)) {
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		
		/*
		 * 判断订单的期数和要对兑奖的期数是否一致
		 */
		if(isCheckLotteryId && (OrderLotteryId != lotteryId || !OrderTermNo.equals(cashTerm))){
			logger.info(E_05_CODE + ":" + E_05_DESC);
			throw new LotteryException(E_05_CODE,E_05_DESC);
		}
		
		/*
		 * 根据订单获取订单对应的彩票,此时获得的彩票只可能有2中状态:6成功 7,失败,也不可能取得空的票列表(原来的逻辑)
		 * 新逻辑取到的票会包含所有该订单下的票，包括票状态未确定的票
		 */
		List<BetTicketDomain> ticketList = this.getTicketService().getTickets(orderId);
		
		/*
		 * 循环彩票兑奖，并更新彩票数据,统计总的中奖金额
		 */
		
		/*
		 * 
		 * 1,失败的彩票在什么时候返还资金,不返还资金，线下处理
		 * 2，更新彩票表的中奖状态如何区分，与订单状态一样
		 * 3，有失败的彩票如何更新订单标识有失败的彩票，不更新
		 */
		long orderPrize = 0L;//订单的中奖金额,税前的
		long orderTaxPrize = 0L;//订单的总税金
		long orderAftTaxPrize = 0L;//订单的税后奖金
		long orderDeductPrize = 0L;//合买提成
		long orderCommPrize = 0L;//佣金，目前未使用
		int ticketFailNum = 0;//一个订单中出票失败的彩票数量
		long maxTicketPrize = 0;//彩票的最大税前奖金
		StringBuffer orderHitHaoMa = new StringBuffer();//订单的中奖号码串
		for(BetTicketDomain ticket : ticketList){
			int ticketStatus = ticket.getTicketStatus();
			if(ticketStatus == TICKET_STATUS_6 && ticket.getPrizeResult() == 0){//出票成功
				long ticketPeize = 0L;
				/*
				 * 计算中奖号码和总的中奖金额
				 * 返回  单注注码[中奖号码:奖项:总税前:总税后], 总税前,总税,总税后,最大中奖金额
				 */
				String[] prizeResult = this.cash(lotteryId, ticket.getPlayType(),
						ticket.getBetType(), termInfo.getLotteryResult(), termInfo.getWinResult(), 
						ticket.getBetCode(), ticket.getBetMultiple());
				orderHitHaoMa.append(prizeResult[0]).append(",");
				ticketPeize = Long.parseLong(StringUtils.isEmpty(prizeResult[1])?"0":prizeResult[1]);//票的税前奖金
				orderPrize += ticketPeize;//订单总的中奖金额,税前
				orderTaxPrize += Long.parseLong(prizeResult[2]);
				orderAftTaxPrize += Long.parseLong(prizeResult[3]);
				long maxCodePrize = Long.parseLong(StringUtils.isEmpty(prizeResult[4])?"0":prizeResult[4]);
				maxTicketPrize = (maxTicketPrize > maxCodePrize) ? maxTicketPrize : maxCodePrize;
				//更新彩票表的中奖金额和中奖等级
				int ticketPrizeLevel = this.prizeLevel(lotteryId, prizeResult[4]);
				//如果改为批量更新是否安全?
				this.getTicketService().updateBetTicketPrizeResult(ticket.getTicketSequence(), ticketPeize, ticketPrizeLevel);
			}else{//出票失败的彩票数量,目前统计的数量暂时不使用
				ticketFailNum++;
			}
		}
		
		int prizeLevel = this.prizeLevel(lotteryId,String.valueOf(maxTicketPrize));//转换订单奖金级别
		
		
		BetPlanDomain betPlan = this.getPlanInfo(orderInfo.getPlanId());//获取方案信息
		
		if(betPlan.getPlanType() == 0){//代购
			//处理追号,只处理中奖停止的追号
			this.dealZhuiHao(termInfo.getNextTerm(),betPlan.getPlanId(),orderId,betPlan.getWinStoped(), prizeLevel);// 处理追号
		}else {//合买
			logger.info("合买##############"+"orderAftTaxPrize=="+orderAftTaxPrize+"betPlan.getUnitAmount()=="+betPlan.getUnitAmount()+"betPlan.getCommisionPercent()=="+betPlan.getCommisionPercent());
			if((orderAftTaxPrize > betPlan.getUnitAmount()) && betPlan.getCommisionPercent() > 0){
				//orderDeductPrize = ((orderAftTaxPrize -  betPlan.getUnitAmount()) * betPlan.getCommisionPercent())/100;
				
				orderDeductPrize = (new BigDecimal((orderAftTaxPrize - betPlan
						.getUnitAmount())
						* betPlan.getCommisionPercent()).divide(new BigDecimal(
						100))).longValue();
				logger.info("合买提成："+orderDeductPrize);
			}
			//long oneFenPrize = (orderAftTaxPrize - orderDeductPrize)/betPlan.getTotalUnit();//每一份的奖金
			BigDecimal oneFenPrize = new BigDecimal(orderAftTaxPrize - orderDeductPrize).divide(new BigDecimal(betPlan.getTotalUnit()));
			long createUserPrize = 0L;
			long totalJoinUserPrize = 0L;
			logger.info("合买总份数:"+betPlan.getTotalUnit());
			logger.info("合买没一份奖金:"+oneFenPrize);
			
			//循环处理合买参与信息
			List<CpInfoDomain> coopInfoList = this.getBetOrderService().queryCoopInfoByPlanId(betPlan.getPlanId());
			if(coopInfoList != null){
				long coopPrize = 0;//合买参与的奖金如何界定大奖小奖还没有定
				String createUserInfoId = null;
				int coopLotteryId = OrderLotteryId;
				logger.info("合买总参与订单数:"+coopInfoList.size());
				for(CpInfoDomain oneCoop : coopInfoList){
					logger.info("处理合买:"+oneCoop.getCpInfoId()+"类型:"+oneCoop.getCpOrderType()+"状态："+oneCoop.getOrderStatus());
					if(oneCoop.getCpOrderType() == 0 && oneCoop.getOrderStatus() == 4){//发起合买的并且已经生成票
						logger.info("发起合买");
						createUserInfoId = oneCoop.getCpInfoId();
						coopLotteryId = oneCoop.getLotteryId();
					}else if((oneCoop.getCpOrderType() == 1 || oneCoop.getCpOrderType() == 3) && oneCoop.getOrderStatus() == 4){//参与投注，保底转投注
						logger.info("参与合买");
						coopPrize = (oneFenPrize.multiply(new BigDecimal(oneCoop.getCpUnit()))).longValue();
						int coopPrizeLevel = this.prizeLevel(oneCoop.getLotteryId(),String.valueOf(coopPrize));//转换订单奖金级别
						this.getBetOrderService().updateCoopPrize(oneCoop.getCpInfoId(), 7, coopPrizeLevel, coopPrize);
					}
					totalJoinUserPrize += coopPrize;
					logger.info("更新参与成功");
				}
				//更新合买发起人的中奖金额、奖金级别和状态
				createUserPrize = orderAftTaxPrize - totalJoinUserPrize;
				this.getBetOrderService().updateCoopPrize(
						createUserInfoId,
						7,
						this.prizeLevel(coopLotteryId, String
								.valueOf(createUserPrize)), createUserPrize);
			}
		}
		
		int upOrderPrizeLevel = prizeLevel;
		if(orderInfo.getOrderStatus() == 99){
			upOrderPrizeLevel = prizeLevel*100 + 99;
		}
		try {
			if (StringUtils.isNotEmpty(orderHitHaoMa.toString())) {
				if (orderHitHaoMa.toString().endsWith(",")) {
					orderHitHaoMa.deleteCharAt(orderHitHaoMa.lastIndexOf(","));
				}
			}
		} catch (Exception e) {

		}
		this.updateBetOrder(orderId, ORDER_STATUS_8, upOrderPrizeLevel, orderPrize,
				orderTaxPrize, orderAftTaxPrize, orderDeductPrize,
				orderCommPrize, orderHitHaoMa.toString());//更新订单状态为兑奖完成
		
		//合买的方案未设计兑奖的状态，可不更新，查询时可查询出票订单的中奖情况
		
		return orderHitHaoMa + "#" + prizeLevel + "#" + orderPrize;//返回结果需要修改
	}
	/*
	 * (非 Javadoc)
	*Title: dispatchPrize
	*Description: 
	* @param heMaiOrDaiGou
	* @param lotteryId
	* @param dispatchTerm
	* @param orderId
	* @param isCheckLotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#dispatchPrize(int, int, java.lang.String, java.lang.String, boolean)
	 */
	public String dispatchPrize(int lotteryId, String dispatchTerm, String orderId,boolean isCheckLotteryId) throws LotteryException {
		BetOrderDomain orderInfo = this.checkBetOrderCanDispatchForUpdate(orderId);//获取订单并锁定订单
		
		int OrderLotteryId = orderInfo.getLotteryId();
		String OrderTermNo = orderInfo.getBetTerm();
		int orderWinStatus = orderInfo.getWinStatus();
		//20110421改为对大奖也派奖
		/*
		if(orderWinStatus == WIN_STATUS_3){//对中大奖的不做处理
			return orderInfo.getWinStatus()+"#"+orderInfo.getPreTaxPrize()+"#大奖未派奖";//中大奖的直接返回
		}*/
		if(isCheckLotteryId && (OrderLotteryId != lotteryId || !OrderTermNo.equals(dispatchTerm))){//判断订单的期数和要对兑奖的期数是否一致
			logger.info(E_08_CODE + ":" + E_08_DESC);
			throw new LotteryException(E_08_CODE,E_08_DESC);
		}
		if(orderWinStatus == WIN_STATUS_2 || orderWinStatus == WIN_STATUS_3 || orderWinStatus == 299 || orderWinStatus == 399){//中小奖或中大奖,需要处理账户资金
			this.getUserService().accountTransaction(
					orderInfo.getUserId(),
					TRANSACTION_TYPE_11002,
					(int)orderInfo.getAftTaxPrize(),
					TRANSACTION_SOURCE_TYPE,
					orderInfo.getOrderId(),
					LotteryTools.accountTransactionRemark(orderInfo.getLotteryId(),
							orderInfo.getBetTerm(), TRANSACTION_TYPE_11002));
		}
		
		if (orderWinStatus == WIN_STATUS_1 || orderWinStatus == WIN_STATUS_2
				|| orderWinStatus == WIN_STATUS_3 || orderWinStatus == 199
				|| orderWinStatus == 299 || orderWinStatus == 399) {// 对未中奖和中小奖、中大奖的更新订单状态为完成派奖
			try {
				this.getBetOrderService().updateBetOrderStatus(orderInfo.getOrderId(), ORDER_STATUS_10);
			} catch (Exception e) {
				logger.error("dispatchPrize error:", e);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
		}
		return orderInfo.getWinStatus()+"#"+orderInfo.getAftTaxPrize()+"#已派奖完成";
	}
	/*
	 * (非 Javadoc)
	*Title: dispatchCoopPrize
	*Description: 
	* @param lotteryId
	* @param dispatchTerm
	* @param chuPiaoOrder
	* @param paiJiangOrder
	* @param isCheckLotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#dispatchCoopPrize(int, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public String dispatchCoopPrize(int lotteryId, String dispatchTerm, String chuPiaoOrder, String paiJiangOrder, boolean isCheckLotteryId) throws LotteryException {
		CpInfoDomain coopInfo = this.checkCoopOrderCanDispatchForUpdate(paiJiangOrder);//获取参与信息并锁定订单
		
		int OrderLotteryId = coopInfo.getLotteryId();
		String OrderTermNo = coopInfo.getBetTerm();
		int orderWinStatus = coopInfo.getWinStatus();
		
		if(isCheckLotteryId && (OrderLotteryId != lotteryId || !OrderTermNo.equals(dispatchTerm))){//判断订单的期数和要对兑奖的期数是否一致
			logger.info(E_08_CODE + ":" + E_08_DESC);
			throw new LotteryException(E_08_CODE,E_08_DESC);
		}
		if(orderWinStatus == WIN_STATUS_2 || orderWinStatus == WIN_STATUS_3){//中小奖或中大奖,需要处理账户资金
			this.getUserService().accountTransaction(
					coopInfo.getUserId(),
					TRANSACTION_TYPE_11002,
					(int)coopInfo.getPrize(),
					TRANSACTION_SOURCE_TYPE,
					coopInfo.getCpInfoId(),
					LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),
							coopInfo.getBetTerm(), TRANSACTION_TYPE_11002));
		}
		
		if(orderWinStatus == WIN_STATUS_1 || orderWinStatus == WIN_STATUS_2 || orderWinStatus == WIN_STATUS_3){//对未中奖和中小奖、中大奖的更新订单状态为完成派奖
			try {
				this.getBetOrderService().updateCoopInfoStatus(coopInfo.getCpInfoId(), 8);
			} catch (Exception e) {
				logger.error("dispatchCoopPrize error:", e);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
		}
		//查改方案下时候还有未派奖完成的参与信息
		int notDisNumInfo = this.getBetOrderService().getNotDispatchCoopNum(coopInfo.getPlanId());
		if(notDisNumInfo == 0){//已经全部派奖
			//更新出票订单的状态为已经派奖
			try {
				this.getBetOrderService().updateBetOrderStatus(chuPiaoOrder, ORDER_STATUS_10);
			} catch (Exception e) {
				logger.error("dispatchCoopPrize error:", e);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
		}
		return coopInfo.getWinStatus()+"#"+coopInfo.getPrize()+"#已派奖完成";
	}

	/* (非 Javadoc)
	 *Title: upCompleteStatus
	 *Description: 
	 * @param lotteryId
	 * @param termNO
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.CashPrizeInnerInterf#upCompleteStatus(int, java.lang.String)
	 */
	public String updateCashPrizeCompleteStatus(int lotteryId, String termNo)
			throws LotteryException {
		String old_status = "";
		String new_status = "";
		try {
			LotteryTermModel lotteryTermInfo = this.getTermService().queryTermInfo(lotteryId, termNo);
			String nextTerm = lotteryTermInfo.getNextTerm();
			int termWinStatus = lotteryTermInfo.getWinStatus();
			old_status = String.valueOf(termWinStatus);
			new_status = String.valueOf(termWinStatus);
			List<Integer> winStatus = new ArrayList<Integer>();
			winStatus.add(WIN_STATUS_0);//未兑奖
			List<Integer> orderStatus = new ArrayList<Integer>();
			orderStatus.add(ORDER_STATUS_4);
			orderStatus.add(ORDER_STATUS_5);
			int notCompleteOrder = this.getBetOrderService().queryOrderNumByStatus(lotteryId, termNo, orderStatus, winStatus);//出票成功并且未兑奖
			int notCompleteZhuiHaoOrder = this.getBetOrderService().queryOrderNumByStatus(lotteryId, nextTerm, ORDER_STATUS_13, winStatus);//尚在追号中的订单
			
			logger.debug("兑奖---尚未完成兑奖的订单数量:"+notCompleteOrder+"尚未处理的追号订单数量:"+notCompleteZhuiHaoOrder);
			if(notCompleteOrder == 0 && notCompleteZhuiHaoOrder ==0){//已经全部兑奖完成并且追号也已经处理完毕
				List<Integer> winStatus2 = new ArrayList<Integer>();
				winStatus2.add(WIN_STATUS_2);//中小奖
				int notCompleteOrder2 = this.getBetOrderService().queryOrderNumByStatus(lotteryId, termNo, ORDER_STATUS_8, winStatus2);//兑奖完成并且中小奖
				if(notCompleteOrder2 == 0){
					new_status = String.valueOf(TERM_WIN_STATUS_8);
					this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_8);
				}else{
					new_status = String.valueOf(TERM_WIN_STATUS_4);
					this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_4);
				}
			}else if(termWinStatus == TERM_WIN_STATUS_2){//彩期状态还是已开奖
				new_status = String.valueOf(TERM_WIN_STATUS_3);
				this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_3);
			}
		} catch (Exception e) {
			logger.error("兑奖更新彩期表状态出错:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		return old_status + "#" + new_status;
	}
	/*
	 * (非 Javadoc)
	*Title: updateDispatchPrizeCompleteStatus
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param termWinStatus
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#updateDispatchPrizeCompleteStatus(int, java.lang.String, int)
	 */
	public void updateDispatchPrizeCompleteStatus(int lotteryId, String termNo,int termWinStatus) throws LotteryException{
		try {
			List<Integer> winStatus = new ArrayList<Integer>();
			//winStatus.add(WIN_STATUS_1);
			winStatus.add(WIN_STATUS_2);
			int notCompleteOrder = this.getBetOrderService().queryOrderNumByStatus(lotteryId, termNo, ORDER_STATUS_8, winStatus);
			logger.debug("派奖---尚未完成派奖的订单数量:"+notCompleteOrder);
			if(notCompleteOrder == 0){//已经全部派奖完成
				this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_8);
			}else if(termWinStatus == TERM_WIN_STATUS_4){//彩期状态还是兑奖成功
				this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_7);
			}
		} catch (Exception e) {
			logger.error("派奖更新彩期表状态出错:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	
	/**
	 * 
	 * Title: cash<br>
	 * Description: <br>
	 *            对投注串兑奖,返回包含倍数的金额值<br>
	 *            <br>需要考虑是否计算倍数金额，该值决定了小奖和大奖的区别
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param lotteryResult
	 * @param winReslut
	 * @param betCode
	 * @param betMulti 投注倍数
	 * @return [中奖号码,税前总金额,总税金,总税后,最大的奖金] 中奖号码以逗号分割,中奖金额为分
	 * @throws LotteryException<br>
	 *                          <br>E_02_DESC
	 */
	private String[] cash(int lotteryId, int playType,
			int betType, String lotteryResult, String winReslut, String betCode,int betMulti)throws LotteryException{
		String[] result = {"","","","",""};//定义返回结果
		//单注注码[中奖号码:奖项:总税前:总税后], 总税前,总税,总税后,最大中奖金额
		List<PrizeInfo> prizeList = LotteryTools.lotteryPrize(lotteryId,
				playType, betType, lotteryResult, winReslut, betCode, betMulti);
		
		if(prizeList != null){
			StringBuffer sb = new StringBuffer();
			long totalPrePrize = 0;//总税前
			long totalTaxPrize = 0;//总税金
			long totalAftTaxPrize = 0;//总税后金额
			long maxPrePrize = 0;
			try {
				for(PrizeInfo onePrize : prizeList){//计算单倍的总中奖金额
					sb.append(onePrize.getWinCode().trim()).append(":").append(
							onePrize.getPrizeLevel()).append(":").append(
							((onePrize.getBasePrize() + onePrize.getAddPrize())/100))
							.append(":").append((onePrize.getAftTaxPrize())/100);
					sb.append(",");
					long ticketPrize = onePrize.getBasePrize() + onePrize.getAddPrize();
					maxPrePrize = (maxPrePrize > ticketPrize)?maxPrePrize:ticketPrize;
					totalPrePrize += ticketPrize;
					totalTaxPrize += onePrize.getTaxPrize();
					totalAftTaxPrize += onePrize.getAftTaxPrize();
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.lastIndexOf(","));
				}
			} catch (NumberFormatException e) {
				logger.error(E_02_DESC, e);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
			result[0] = sb.toString();
			result[1] = String.valueOf(totalPrePrize);
			result[2] = String.valueOf(totalTaxPrize);
			result[3] = String.valueOf(totalAftTaxPrize);
			result[4] = String.valueOf(maxPrePrize);
			return result;
		}else{//没有中奖,调用兑奖方法时发生异常
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/**
	 * 
	 * Title: checkBetOrder<br>
	 * Description: <br>
	 *            获得订单信息并校验是够符合兑奖条件<br>
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException<br>
	 *                          <br>E_02_CODE
	 *                          <br>E_01_CODE
	 */
	private BetOrderDomain checkBetOrderCanCashForUpdate(String orderId) throws LotteryException{
		BetOrderDomain betOrdernfo = null;
		try {
			betOrdernfo = this.getBetOrderService().queryBetOrderByOrderIdForUpdate(orderId);
		} catch (Exception e) {
			logger.error("checkBetOrderCanCashForUpdate error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		return betOrdernfo;
	}
	/**
	 * 
	 * Title: checkBetOrderCanDispatchForUpdate<br>
	 * Description: <br>
	 *            获得订单信息并校验是够符合派奖条件<br>
	 *            只要订单状态是兑奖完成的都认为是可以派奖<br>
	 *            对中大奖的不做处理
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException
	 */
	private BetOrderDomain checkBetOrderCanDispatchForUpdate(String orderId) throws LotteryException{
		BetOrderDomain betOrdernfo = null;
		try {
			betOrdernfo = this.getBetOrderService().queryBetOrderByOrderIdForUpdate(orderId);
		} catch (Exception e) {
			logger.error("checkBetOrderCanDispatchForUpdate error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		if(betOrdernfo != null && betOrdernfo.getOrderStatus() == ORDER_STATUS_8){
			return betOrdernfo;
		}else{
			throw new LotteryException(E_06_CODE,E_06_DESC);
		}
	}
	/**
	 * 
	 * Title: checkCoopOrderCanDispatchForUpdate<br>
	 * Description: <br>
	 *              <br>获取参与合买的信息并锁定
	 * @param coopInfoId
	 * @return
	 * @throws LotteryException
	 */
	private CpInfoDomain checkCoopOrderCanDispatchForUpdate(String coopInfoId) throws LotteryException{
		CpInfoDomain coopInfo = null;
		try {
			coopInfo = this.getBetOrderService().queryCoopInfoByIdForUpdate(coopInfoId);
		} catch (Exception e) {
			logger.error("checkCoopOrderCanDispatchForUpdate error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		if(coopInfo != null && coopInfo.getOrderStatus() == 7){
			return coopInfo;
		}else{
			throw new LotteryException(E_06_CODE,E_06_DESC);
		}
	}
	
	/**
	 * 
	 * Title: checkTermInfo<br>
	 * Description: <br>
	 *            获取订单信息<br>
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException
	 */
	public BetOrderDomain getOrderInfo(String orderId) throws LotteryException{
		try {
			return this.getBetOrderService().queryBetOrderByOrderId(orderId);
		} catch (Exception e) {
			logger.error("getOrderInfo error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: getNotCashOrder
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#getNotCashOrder(int, java.lang.String)
	 */
	public List<String> getNotCashOrder(int lotteryId,String termNo) throws LotteryException{
		try{
			List<Integer> winStatus = new ArrayList<Integer>();
			winStatus.add(WIN_STATUS_0);
			winStatus.add(99);
			List<Integer> orderStatus = new ArrayList<Integer>();
			orderStatus.add(ORDER_STATUS_4);
			orderStatus.add(ORDER_STATUS_5);
			return this.getBetOrderService().queryOrderByStatus(lotteryId, termNo, orderStatus, winStatus);
		}catch(Exception e){
			logger.error("getNotCashOrder error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: getNotDispatchOrder
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#getNotDispatchOrder(int, java.lang.String)
	 */
	public List<String> getNotDispatchOrder(int lotteryId,String termNo) throws LotteryException{
		try{
			List<Integer> winStatus = new ArrayList<Integer>();
			winStatus.add(WIN_STATUS_1);
			winStatus.add(WIN_STATUS_2);
			winStatus.add(WIN_STATUS_3);
			return this.getBetOrderService().queryOrderByStatus(lotteryId, termNo, ORDER_STATUS_5, winStatus);
		}catch(Exception e){
			logger.error("getNotDispatchOrder error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/**
	 * 
	 * Title: getPlanInfo<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param planId
	 * @return BetPlanDomain
	 * @throws LotteryException
	 */
	private BetPlanDomain getPlanInfo(String planId) throws LotteryException{
		try {
			return this.getBetOrderService().queryBetPlanByPlanId(planId);
		} catch (Exception e) {
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: checkTermCanCash
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.CashPrizeInnerInterf#checkTermCanCash(int, java.lang.String)
	 */
	public LotteryTermModel checkTermCanCash(int lotteryId, String termNo)
			throws LotteryException {
		LotteryTermModel term = null;
		try {
			term = this.getTermService().queryTermInfo(lotteryId,termNo);
		} catch (Exception e) {
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		logger.debug("校验彩期是否能兑奖奖："+"WinStatus="+term.getWinStatus());
		if (term != null && (term.getWinStatus() == TERM_WIN_STATUS_2 || term.getWinStatus() == TERM_WIN_STATUS_3)) {// 该彩种彩期已经可以开奖
			return term;
		} else {
			throw new LotteryException(E_04_CODE, E_04_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: checkTermCanDispatch
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#checkTermCanDispatch(int, java.lang.String)
	 */
	public LotteryTermModel checkTermCanDispatch(int lotteryId, String termNo) throws LotteryException{
		LotteryTermModel term = null;
		try {
			term = this.getTermService().queryTermInfo(lotteryId,termNo);
		} catch (Exception e) {
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		logger.debug("校验彩期是否能派奖奖："+"WinStatus="+term.getWinStatus());
		if (term != null && (term.getWinStatus() == TERM_WIN_STATUS_4 || term.getWinStatus() == TERM_WIN_STATUS_7)) {// 该彩种彩期已经可以开奖
			return term;
		} else {
			throw new LotteryException(E_07_CODE, E_07_DESC);
		}
	}
	/**
	 * 
	 * Title: prizeLevel<br>
	 * Description: <br>
	 *            根据中奖金额处理中奖级别<br>
	 * @param prizeMoney 中奖金额
	 * @return 1:未中奖 2:中小奖 3:中大奖
	 */
	private int prizeLevel(int lotteryId,String prizeMoney){
		if(prizeMoney == null || "".equals(prizeMoney) || "0".equals(prizeMoney)){//未中奖
			return WIN_STATUS_1;
		}else{
			String prizeLevelMoney = LotteryTools.getLotterySuperPrizeMoney(lotteryId);
			return (Long.parseLong(prizeMoney) > Long.parseLong(prizeLevelMoney)) ? WIN_STATUS_3 : WIN_STATUS_2;
		}
	}
	/**
	 * 
	 * Title: dealZhuiHao<br>
	 * Description: <br>
	 *            追号处理<br>
	 * @param planId 方案编号
	 * @param orderId 订单编号
	 * @param winStop 中奖后是否停止标志
	 * @param prizeLevel 中奖级别
	 * @throws LotteryException<br>E_02_CODE
	 *                          <br>AccountService中定义的异常
	 *                          <br>
	 */
	private void dealZhuiHao(String nextTerm,String planId,String orderId,int winStop,int prizeLevel) throws LotteryException{
		try {
			if(winStop == WIN_STOPED && prizeLevel != WIN_STATUS_1){//需要处理追号,停止追号并且中奖了
				List<Integer> order_status_list = new ArrayList<Integer>();
				order_status_list.add(ORDER_STATUS_13);
				List<BetOrderDomain> orderList = this.getBetOrderService().queryOrderByOrderIdForSamePlan(planId, orderId,new ArrayList<String>(), order_status_list, WIN_STATUS_0);
				for(BetOrderDomain order : orderList){//循环撤销订单并释放冻结资金
					this.getBetOrderService().updateBetOrderStatus(order.getOrderId(), ORDER_STATUS_11);//撤销订单
					//释放冻结资金
					this.getUserService().accountTransaction(
							order.getUserId(),
							TRANSACTION_TYPE_31006,
							order.getBetAmount(),
							TRANSACTION_SOURCE_TYPE,
							order.getOrderId(),
							LotteryTools.accountTransactionRemark(order.getLotteryId(),
									order.getBetTerm(), TRANSACTION_TYPE_31006));
				}
			}
		}catch(LotteryException ex){
			logger.error(ex.getType()+":"+ex.getMessage(), ex);
			throw ex;
		} catch (Exception e) {
			logger.error(E_02_DESC, e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	
	
	/**
	 * 
	 * Title: updateBetOrder<br>
	 * Description: <br>
	 *            更新订单的订单状态，中奖状态和奖金<br>
	 * @param orderId
	 * @param orderStatus
	 * @param winStatus
	 * @param prize
	 * @param reserve
	 * @throws LotteryException
	 */
	private void updateBetOrder(String orderId,int orderStatus,int winStatus,long prize,long taxPrize,long aftTaxPrize,long deductPrize,long commPrize,String winCode) throws LotteryException{
		try {
			this.getBetOrderService().updateBetOrderAndWinStatus(orderId, orderStatus, winStatus, prize,taxPrize,aftTaxPrize,deductPrize, commPrize,winCode);
		} catch (Exception e) {
			logger.error(E_02_DESC, e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}

	private LotteryTermServiceInterf getTermService() {
		return this.termService;
	}

	public void setTermService(LotteryTermServiceInterf termService) {
		this.termService = termService;
	}

	private AccountService getUserService() {
		return this.userService;
	}

	public void setUserService(AccountService userService) {
		this.userService = userService;
	}

	private BetPlanOrderServiceInterf getBetOrderService() {
		return this.betOrderService;
	}

	public void setBetOrderService(BetPlanOrderServiceInterf betOrderService) {
		this.betOrderService = betOrderService;
	}
	/*
	 * (非 Javadoc)
	*Title: dealNotTicketZhuiHao
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param limitNumber
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#dealNotTicketZhuiHao(int, java.lang.String, java.lang.String)
	 */
	public String dealNotTicketZhuiHao(int lotteryId, String termNo,
			String limitNumber, String orderId) throws LotteryException {
		String result = "";
		// 先锁定追号订单，防止订单被手工撤销
		BetOrderDomain orderInfo = this.getBetOrderService()
				.queryBetOrderByOrderIdForUpdate(orderId);
		if (orderInfo.getOrderStatus() == ORDER_STATUS_13) {// 订单还在追号中
			int modifyOrderStatus = ORDER_STATUS_0;
			boolean isLimit = false;
			// 判断订单是否被限号,如果被限号，则要奖isLimit设置为true
			try {
				isLimit = LotteryFactory.factory(String.valueOf(lotteryId))
						.isLimitBet(String.valueOf(orderInfo.getPlayType()),
								String.valueOf(orderInfo.getBetType()),
								limitNumber, orderInfo.getBetCode());
			} catch (LotteryUnDefineException e) {
				e.printStackTrace();
			}

			if (isLimit) {
				modifyOrderStatus = ORDER_STATUS_14;
			}
			result = String.valueOf(modifyOrderStatus);
			
			// 处理订单状态和账户
			this.getBetOrderService().updateBetOrderStatus(
					orderInfo.getOrderId(), modifyOrderStatus);// 将订单改为未开始或者modifyOrderStatus为14时限号取消订单
			// 限号追号订单返还冻结资金
			if (modifyOrderStatus == ORDER_STATUS_14) {
				// 释放冻结资金
				this.getUserService().accountTransaction(
						orderInfo.getUserId(),
						TRANSACTION_TYPE_31006,
						orderInfo.getBetAmount(),
						TRANSACTION_SOURCE_TYPE,
						orderInfo.getOrderId(),
						LotteryTools.accountTransactionRemark(orderInfo
								.getLotteryId(), orderInfo.getBetTerm(),
								TRANSACTION_TYPE_31117));
			} else if (modifyOrderStatus == ORDER_STATUS_0) {
				// 扣除账户资金
				this.getUserService().accountTransaction(
						orderInfo.getUserId(),
						TRANSACTION_TYPE_30001,
						orderInfo.getBetAmount(),
						TRANSACTION_SOURCE_TYPE,
						orderInfo.getOrderId(),
						LotteryTools.accountTransactionRemark(orderInfo
								.getLotteryId(), orderInfo.getBetTerm(),
								TRANSACTION_TYPE_30001));
			}
		}

		return result;
	}
	/*
	 * (非 Javadoc) Title: checkNotTicketSucessOrder Description: @param
	 * lotteryId @param termNo @throws LotteryException
	 * 
	 * @see com.success.lottery.business.service.interf.PrizeInnerInterf#checkNotTicketSucessOrder(int,
	 *      java.lang.String)
	 */
	public void checkNotTicketSucessOrder(int lotteryId, String termNo) throws LotteryException {
		int result = 0;
		try{
			List<Integer> winStatus = new ArrayList<Integer>();
			List<Integer>  orderStatus = new ArrayList<Integer>();
			orderStatus.add(0);
			orderStatus.add(1);
			orderStatus.add(2);
			orderStatus.add(3);
			result = this.getBetOrderService().queryOrderNumByStatus(lotteryId, termNo, orderStatus, winStatus);// 查是否还有需要确认出票处理的订单
			if(result > 0){
				throw new LotteryException(E_09_CODE,E_09_DESC.replaceAll("A", String.valueOf(lotteryId)).replaceAll("B", termNo).replaceAll("C", String.valueOf(result)));
			}
		}catch(LotteryException ex){
			throw ex;
		}catch(Exception e){
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	
	public BetTicketServiceInterf getTicketService() {
		return ticketService;
	}
	public void setTicketService(BetTicketServiceInterf ticketService) {
		this.ticketService = ticketService;
	}
	/*
	 * (非 Javadoc)
	*Title: getTermInfo
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#getTermInfo(int, java.lang.String)
	 */
	public LotteryTermModel getTermInfo(int lotteryId, String termNo) throws LotteryException {
		LotteryTermModel term = null;
		try {
			term = this.getTermService().queryTermInfo(lotteryId,termNo);
		} catch (Exception e) {
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		return term;
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
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#queryOrderByStatus(int, java.lang.String, int, java.util.List)
	 */
	public List<String> queryOrderByStatus(int lotteryId, String termNo, int orderStatus, List<Integer> winStatus) throws LotteryException {
		return this.getBetOrderService().queryOrderByStatus(lotteryId, termNo, orderStatus, winStatus);
	}
}
