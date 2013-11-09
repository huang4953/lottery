/**
 * Title: TicketExDealService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-5 ����10:55:41
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.service.interf.TicketExDealInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.ticket.service.OrderDispatch;
import com.success.lottery.ticket.service.impl.BetTicketService;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;

/**
 * com.success.lottery.business.service.impl
 * TicketExDealService.java
 * TicketExDealService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-5 ����10:55:41
 * 
 */

public class TicketExDealService implements TicketExDealInterf {
	
	private static Log logger = LogFactory.getLog(TicketExDealService.class);
	
	private BetTicketServiceInterf ticketService;
	private BetPlanOrderServiceInterf orderService;

	/*
	 * (�� Javadoc)
	*Title: ticketExDeal
	*Description: 
	* @param ticketSequence
	* @param ticketStatus
	* @param ticketId
	* @param printerId
	* @param printResult
	* @param ticketData
	* @param ticketDataMD
	* @param ticketPassword
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.TicketExDealInterf#ticketExDeal(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void ticketExDeal(String ticketSequence,int ticketStatus, String ticketId,
			String printerId, String printResult, String ticketData,
			String ticketDataMD, String ticketPassword) throws LotteryException {
		try {
			BetOrderDomain ticketOrder = this.getTicketService().getOrderByTicketSequence(ticketSequence);
			String orderId = ticketOrder.getOrderId();
			int orderStatus = ticketOrder.getOrderStatus();
			if(orderStatus > 6){//�����Ѿ��ҽ��������ٶԶ������޸�
				throw new LotteryException(E_550010_CODE,E_550010_DESC);
			}
			
			//���¶���״̬Ϊ2
			this.getOrderService().updateBetOrderStatus(orderId, 2);
			//���²�Ʊ
			this.getTicketService().updateBetTicketPrintInfo(ticketSequence,ticketStatus, ticketId, printerId, printResult, ticketData, ticketDataMD, ticketPassword);
			BetOrderDomain order = this.getOrderService().queryBetOrderByOrderId(orderId);
			//�Ŷ���
			
			//�ж��Ƿ��Ѿ�����������У����������������У�����������п���ʧ��-ʧ��ԭ���Ƕ�������
			if(OrderDispatch.containsCheckOrderRequest(order)){
			      logger.debug("OrderChecker get the order(" + order.getOrderId() + ") is already in the CheckOrderRequestQueue.");
			} else {
			     String rs = null; 
			     if((rs = OrderDispatch.putCheckOrderRequest(order)) == null){
			            logger.debug("OrderChecker put the order(" + order.getOrderId() + ") into the CheckOrderRequestQueue success.");
			     } else {
			            logger.debug("OrderChecker put the order(" + order.getOrderId() + ") into the CheckOrderRequestQueue failed:" + rs + ".");
			     }
			}
			 
			 
			//����ڽ�������У���ӽ��������ɾ��
			if(OrderDispatch.containsCheckOrderResult(order)){
			      OrderDispatch.removeCheckOrderResult(order);
			}

			
			logger.info("��Ʊ�쳣����:����"+ticketSequence+"״̬Ϊ:"+ticketStatus);
		} catch (Exception e) {
			logger.error("ticketExDeal Error :", e);
			if(e instanceof LotteryException){
				throw (LotteryException)e;
			}else{
				throw new LotteryException(E_550001_CODE,E_550001_DESC);
			}
		}

	}

	public BetTicketServiceInterf getTicketService() {
		return ticketService;
	}

	public void setTicketService(BetTicketServiceInterf ticketService) {
		this.ticketService = ticketService;
	}

	public BetPlanOrderServiceInterf getOrderService() {
		return orderService;
	}

	public void setOrderService(BetPlanOrderServiceInterf orderService) {
		this.orderService = orderService;
	}

}
