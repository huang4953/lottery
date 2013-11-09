/**
 * Title: DispatchPrizeService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-26 下午02:28:08
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.service.interf.DispatchPrizeInterf;
import com.success.lottery.business.service.interf.PrizeInnerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.TermLog;

/**
 * com.success.lottery.business.service.impl
 * DispatchPrizeService.java
 * DispatchPrizeService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-26 下午02:28:08
 * 
 */

public class DispatchPrizeService implements DispatchPrizeInterf {
	private static Log logger = LogFactory.getLog(CashPrizeService.class);
	private static TermLog log = TermLog.getInstance("PJ");
	private PrizeInnerInterf innerService;

	/* (非 Javadoc)
	 *Title: dispatchAutoOrder
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.DispatchPrizeInterf#dispatchAutoOrder(int, java.lang.String)
	 */
	public List<String> dispatchAutoOrder(int lotteryId, String termNo)
			throws LotteryException {
		List<String> result = new ArrayList<String>();
		//校验彩期是否能派奖
		logger.debug("派奖---自动订单---开始---"+"彩种:"+lotteryId+"彩期:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanDispatch(lotteryId, termNo);
		logger.debug("派奖---自动订单---彩种:"+lotteryId+"彩期:"+termNo+"可以派奖");
		//获取可以派奖的订单编号信息
		List<String> orderList = this.getInnerService().getNotDispatchOrder(lotteryId, termNo);
		
       //循环对兑奖
		for(String singleOrder : orderList){//依次处理订单，该循环内的方法包含单独提交的事务
			try {
				String cashResult = this.getInnerService().dispatchPrize(lotteryId, termNo, singleOrder, false);
				result.add(cashResult);
				log.logInfo(lotteryId, termNo, singleOrder, cashResult);
			} catch (Exception e) {//该异常不能对外抛出
				result.add(lotteryId+"#"+termNo+"#"+singleOrder+"#"+"##派奖失败");
				log.logInfo(lotteryId, termNo, singleOrder, "##派奖失败");
				logger.error(lotteryId+"#"+termNo+"#"+singleOrder+"派奖失败原因:", e);
			}
		}
		
		//更新彩期状态
		this.getInnerService().updateDispatchPrizeCompleteStatus(lotteryId, termNo, lotteryTermInfo.getWinStatus());
		logger.debug("派奖---自动订单---完成---"+"彩种:"+lotteryId+"彩期:"+termNo);
		return result;
	}

	/* (非 Javadoc)
	 *Title: dispatchMultiOrder
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @param orderList
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.DispatchPrizeInterf#dispatchMultiOrder(int, java.lang.String, java.util.List)
	 */
	public List<String> dispatchMultiOrder(int lotteryId, String termNo,
			List<String> orderList) throws LotteryException {
		List<String> result = new ArrayList<String>();
        //获取彩期信息
		logger.debug("派奖---多个订单---开始---"+"彩种:"+lotteryId+"彩期:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanDispatch(lotteryId, termNo);
		logger.debug("派奖---多个订单---彩种:"+lotteryId+"彩期:"+termNo+"可以派奖");
		
		for(String singleOrder : orderList){//依次处理订单，该循环内的方法包含单独提交的事务
			try {
				String dispatchResult = this.getInnerService().dispatchPrize(lotteryId, termNo, singleOrder, true);
				result.add(dispatchResult);
				log.logInfo(lotteryId, termNo, singleOrder, dispatchResult);
			} catch (Exception e) {//该异常不能对外抛出
				result.add(lotteryId+"#"+termNo+"#"+singleOrder+"#"+"##派奖失败");
				log.logInfo(lotteryId, termNo, singleOrder, "##派奖失败");
				logger.error(lotteryId+"#"+termNo+"#"+singleOrder+"派奖失败原因:", e);
			}
		}
		this.getInnerService().updateDispatchPrizeCompleteStatus(lotteryId, termNo, lotteryTermInfo.getWinStatus());
		logger.debug("派奖---多个订单---完成---"+"彩种:"+lotteryId+"彩期:"+termNo);
		return result;
	}

	/*
	 * (非 Javadoc)
	*Title: dispatchSingleOrder
	*Description: 
	* @param heMaiOrDaiGou
	* @param chuPiaoorderId
	* @param paiJiangOrder
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.DispatchPrizeInterf#dispatchSingleOrder(int, java.lang.String, java.lang.String)
	 */
	public String dispatchSingleOrder(int heMaiOrDaiGou,String chuPiaoOrderId,String paiJiangOrder) throws LotteryException {
		logger.debug("派奖---单个订单---开始---"+paiJiangOrder);
		//对合买和代购出票订单是一样的
		BetOrderDomain lotteryOrderInfo = this.getInnerService().getOrderInfo(chuPiaoOrderId);//获取订单信息
		int lotteryId = lotteryOrderInfo.getLotteryId();//彩种
		String termNo = lotteryOrderInfo.getBetTerm();//彩期
		logger.debug("派奖---单个订单---彩种:"+lotteryId+"彩期:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanDispatch(lotteryId, termNo);
		logger.debug("兑奖---单个订单---彩种:"+lotteryId+"彩期:"+termNo+"可以派奖");
		
		String dispatchPrize = "";
		try {
			if(heMaiOrDaiGou == 0){//代购
				logger.debug("派奖---单个订单-代购派奖");
				dispatchPrize = this.getInnerService().dispatchPrize(lotteryId, termNo, paiJiangOrder, false);
				
			}else if(heMaiOrDaiGou == 1){//合买
				logger.debug("派奖---单个订单-合买派奖");
				dispatchPrize = this.getInnerService().dispatchCoopPrize(lotteryId, termNo, chuPiaoOrderId, paiJiangOrder, false);
			}
			
			log.logInfo(lotteryId, termNo, paiJiangOrder, dispatchPrize);
		} catch (LotteryException e) {
			log.logInfo(lotteryId, termNo, paiJiangOrder, "##派奖失败");
			logger.error(lotteryId+"#"+termNo+"#"+paiJiangOrder+"派奖失败原因:", e);
			throw e;
		}
		logger.debug("派奖结果:"+dispatchPrize);
		
		this.getInnerService().updateDispatchPrizeCompleteStatus(lotteryId, termNo, lotteryTermInfo.getWinStatus());
		logger.debug("彩种:"+lotteryId+"彩期:"+termNo+"派奖成功");
		logger.debug("派奖---单个订单---结束---"+"");
		return new StringBuffer().append(lotteryId).append("#").append(termNo).append("#").append(paiJiangOrder).append("#").append(dispatchPrize).toString();
	}

	private PrizeInnerInterf getInnerService() {
		return innerService;
	}

	public void setInnerService(PrizeInnerInterf innerService) {
		this.innerService = innerService;
	}

}
