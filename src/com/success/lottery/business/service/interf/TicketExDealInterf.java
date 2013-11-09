/**
 * Title: TicketExDealInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-5 上午10:53:00
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * TicketExDealInterf.java
 * TicketExDealInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-5 上午10:53:00
 * 
 */

public interface TicketExDealInterf {
	
	/*
	 * 以下异常为彩票异常处理需要的异常定义
	 */
	public static final int E_550010_CODE = 550010;
	public static final String E_550010_DESC = "彩票对应的订单已经兑奖，不能再修改订单状态！";
	
	
	public static final int E_550001_CODE = 550001;
	public static final String E_550001_DESC = "彩票异常处理程序发生异常！";
	
	/**
	 * 
	 * Title: ticketExDeal<br>
	 * Description: <br>
	 *              <br>异常彩票处理
	 * @param ticketSequence 彩票流水号
	 * @param ticketStatus 彩票状态
	 * @param ticketId 票号
	 * @param printerId 打票机编号
	 * @param printResult 出票结果
	 * @param ticketData 彩票数据流
	 * @param ticketDataMD 彩票摘要
	 * @param ticketPassword 票密码
	 * @throws LotteryException
	 */
	public void ticketExDeal(String ticketSequence,int ticketStatus,String ticketId,
			String printerId, String printResult, String ticketData,
			String ticketDataMD, String ticketPassword) throws LotteryException;

}
