/**
 * Title: PlanOrderManagerService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-18 下午10:00:56
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.service.interf.PlanOrderManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;


/**
 * com.success.lottery.business.service.impl
 * PlanOrderManagerService.java
 * PlanOrderManagerService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-18 下午10:00:56
 * 
 */

public class PlanOrderManagerService implements PlanOrderManagerInterf {
	private static Log logger = LogFactory.getLog(PlanOrderManagerService.class);
	
	private static final int ORDER_STATUS_13 = 13;//追号中
	private static final int ORDER_STATUS_12 = 12;//手动撤销
	
	private static final int TRANSACTION_SOURCE_TYPE_2002 = 2002;//交易渠道，投注订单
	private static final int TRANSACTION_TYPE_31006 = 31006;//交易类别，追号订单撤销返还冻结资金
	
	private AccountService accountService;//账户服务
	private BetPlanOrderServiceInterf orderService;//订单服务

	/* (非 Javadoc)
	 *Title: cancelAddOrder
	 *Description: 
	 * @param orderId
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.PlanOrderManagerInterf#cancelAddOrder(java.lang.String)
	 */
	public void cancelAddOrder(String orderId) throws LotteryException {
		
		try{
			
			/*
			 * 校验参数
			 */
			if(StringUtils.isBlank(orderId)){
				throw new LotteryException(PlanOrderManagerInterf.E_02_CODE,PlanOrderManagerInterf.E_02_DESC.replace("1", "订单编号").replace("2", "不能为空"));
			}
			/*
			 * 获取订单并锁定订单
			 */
			BetOrderDomain betOrder = this.getOrderService().queryBetOrderByOrderIdForUpdate(orderId);
			
			if(betOrder == null){
				throw new LotteryException(PlanOrderManagerInterf.E_04_CODE,PlanOrderManagerInterf.E_04_DESC);
			}
			
			/*
			 * 撤销订单
			 */
			int orderStatus = betOrder.getOrderStatus();
			if(orderStatus != ORDER_STATUS_13){//订单不能撤销
				throw new LotteryException(PlanOrderManagerInterf.E_03_CODE,PlanOrderManagerInterf.E_03_DESC);
			}
			int result = this.getOrderService().updateBetOrderStatus(orderId, ORDER_STATUS_12);
			
			if(result <= 0){
				throw new LotteryException(PlanOrderManagerInterf.E_05_CODE,PlanOrderManagerInterf.E_05_DESC);
			}
			
			
			/*
			 * 释放冻结资金
			 */
			this.getAccountService().accountTransaction(betOrder.getUserId(),
					TRANSACTION_TYPE_31006, betOrder.getBetAmount(),
					TRANSACTION_SOURCE_TYPE_2002, betOrder.getOrderId(),"用户撤销");
			
			logger.debug("手工撤销订单成功,订单编号:" + betOrder.getOrderId());
		}catch(LotteryException ex){
			logger.error("cancelAddOrder error:", ex);
			throw ex;
		}catch(Exception e){
			logger.error("cancelAddOrder error:", e);
			throw new LotteryException(PlanOrderManagerInterf.E_01_CODE,PlanOrderManagerInterf.E_01_DESC);
		}

	}

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public BetPlanOrderServiceInterf getOrderService() {
		return this.orderService;
	}

	public void setOrderService(BetPlanOrderServiceInterf orderService) {
		this.orderService = orderService;
	}

}
