/**
 * Title: CashPrizeService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-23 下午04:51:40
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.service.interf.CashPrizeInterf;
import com.success.lottery.business.service.interf.PrizeInnerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.TermLog;

/**
 * com.success.lottery.business.service.impl
 * CashPrizeService.java
 * CashPrizeService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-23 下午04:51:40
 * 
 */

public class CashPrizeService implements CashPrizeInterf {
	private static Log logger = LogFactory.getLog(CashPrizeService.class);
	private static TermLog log = TermLog.getInstance("DJ");
	
	private PrizeInnerInterf innerService;

	/* (非 Javadoc)
	 *Title: cashAutoOrder
	 *Description: 
	 * @param lotteryId
	 * @param termNo
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.CashPrizeInterf#cashAutoOrder(int, java.lang.String)
	 */
	public Map<String,String> cashAutoOrder(int lotteryId, String termNo,Map<String,String> cashNum,Map<String,String> dbLogMap)
			throws LotteryException {
		//List<String> result = null;
		Map<String,String> resultMap = new HashMap<String,String>();
		//成功(0)失败(1)标识#彩种#彩期#订单编号#中奖号码#奖金级别#奖金金额#投注金额
		int total_orders = 0;//总的订单数
		long total_tz_prize = 0L; //总订单的投注金额
		int sucess_orders = 0;//成功兑奖订单数
		long sucess_tz_prize = 0L;//成功兑奖投注金额
		int fail_orders = 0;//失败兑奖订单数
		long fail_tz_prize = 0L;//失败兑奖投注金额
		int zj_orders = 0;//中奖订单数
		long zj_prize = 0L;//中奖奖金数
		
		try {
			this.dbLog(40004,String.valueOf(lotteryId), termNo, "", "", "",dbLogMap);//开始
			//result = new ArrayList<String>();
			if(cashNum == null){
				cashNum = new HashMap<String,String>();
				cashNum.put("cashTotalNum", "0");//兑奖总条数
				cashNum.put("cashCurNum", "0");//当前已经兑奖条数
				cashNum.put("cashPersent", "0");//当前已经兑奖的百分比
			}
			//校验彩期是否能兑奖
			logger.info("兑奖---自动订单---开始---"+"彩种:"+lotteryId+"彩期:"+termNo);
			LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanCash(lotteryId, termNo);//判断彩期是否可以兑奖
			logger.info("兑奖---自动订单---彩种:"+lotteryId+"彩期:"+termNo+"可以兑奖");
			
			/*
			 * 
			 * 如果高频在得到得到开奖结果后直接兑奖，则还要讨论是否不检查该兑奖才的订单出票情况?
			 */
			this.getInnerService().checkNotTicketSucessOrder(lotteryId, termNo);//判断彩票是否还有没有确认的
			logger.info("兑奖---自动订单---彩种:"+lotteryId+"彩期:"+termNo+"没有需要出票确认的订单，可以兑奖");
			
			
			//获取可以兑奖的订单编号信息,获取条件为订单的状态为4、5，开奖状态为0和99（错误处理过的）
			List<String> orderList = this.getInnerService().getNotCashOrder(lotteryId, termNo);

			if(orderList != null && orderList.size() > 0){
				total_orders = orderList.size();
				cashNum.put("cashTotalNum", String.valueOf(total_orders));
			}else{
				cashNum.put("cashPersent", String.valueOf(100.0));//百分比设置为100.0
			}
			
			//循环对兑奖
			Map<String,String> outResult = new HashMap<String,String>();
			outResult.put("A", "0");
			outResult.put("B", "0");
			int curNum = 0;
			log.logInfo(lotteryId, termNo, "开始循环兑奖");
			for(String singleOrder : orderList){//依次处理订单，该循环内的方法包含单独提交的事务
				try {
					String cashResult = this.getInnerService().cashPrize(lotteryId, termNo, singleOrder, lotteryTermInfo,false,outResult);
					total_tz_prize += Long.parseLong(outResult.get("A"));
					sucess_orders++;
					sucess_tz_prize += Long.parseLong(outResult.get("A"));
					String [] prizeResult = cashResult.split("#");
					if(!prizeResult[1].equals("1")){//中奖了
						zj_orders++;
						zj_prize += Long.parseLong(prizeResult[2]);
					}
					//result.add("0" + "#" + lotteryId + "#" + termNo + "#" + singleOrder+"#"+ cashResult+"#"+outResult.get("A"));
					log.logInfo(lotteryId, termNo, singleOrder, cashResult);
				} catch (Exception e) {//该异常不能对外抛出
					total_tz_prize += Long.parseLong(outResult.get("A"));
					fail_orders++;
					fail_tz_prize += Long.parseLong(outResult.get("A"));
					
					//result.add("1"+"#"+lotteryId+"#"+termNo+"#"+singleOrder+"#"+"##0"+"#"+outResult.get("A"));
					log.logInfo(lotteryId, termNo, singleOrder, "##兑奖失败!");
					logger.error(lotteryId+"#"+termNo+"#"+singleOrder+"兑奖失败原因:", e);
					this.dbLog(41004, String.valueOf(lotteryId), termNo, singleOrder, outResult.get("B"), e.getMessage(),dbLogMap);//单个订单失败
				}
				cashNum.put("cashCurNum", String.valueOf(++curNum));
				String precent = new java.text.DecimalFormat("#.0").format(((curNum/(float)total_orders)*1000)/10);
				cashNum.put("cashPersent", precent);
			}
			log.logInfo(lotteryId, termNo, "结束循环兑奖");
			logger.info("兑奖---自动订单---完成---"+"彩种:"+lotteryId+"彩期:"+termNo);
		} catch (LotteryException e) {
			this.dbLog(41005, String.valueOf(lotteryId), termNo, "", "", e.getMessage(),dbLogMap);//失败结束
			throw e;
		}finally{
			resultMap.put("total_orders", String.valueOf(total_orders));
			resultMap.put("total_tz_prize", String.valueOf(total_tz_prize/100f));
			resultMap.put("sucess_orders", String.valueOf(sucess_orders));
			resultMap.put("sucess_tz_prize", String.valueOf(sucess_tz_prize/100f));
			resultMap.put("fail_orders", String.valueOf(fail_orders));
			resultMap.put("fail_tz_prize", String.valueOf(fail_tz_prize/100f));
			resultMap.put("zj_orders", String.valueOf(zj_orders));
			resultMap.put("zj_prize", String.valueOf(zj_prize/100f));
		}
		this.dbLog(40005, String.valueOf(lotteryId), termNo, "", "", "",dbLogMap);//成功结束
		return resultMap;
	}

	/*
	 * (非 Javadoc)
	*Title: cashMultiOrder
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param orderList
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.CashPrizeInterf#cashMultiOrder(int, java.lang.String, java.util.List)
	 */
	public List<String> cashMultiOrder(int lotteryId,String termNo,List<String> orderList) throws LotteryException {
		List<String> result = new ArrayList<String>();
		// 获取彩期信息
		logger.debug("兑奖---多个订单---开始---"+"彩种:"+lotteryId+"彩期:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanCash(lotteryId, termNo);
		logger.debug("兑奖---多个订单---彩种:"+lotteryId+"彩期:"+termNo+"可以开奖");
		Map<String,String> outResult = new HashMap<String,String>();
		outResult.put("A", "0");
		for(String singleOrder : orderList){//依次处理订单，该循环内的方法包含单独提交的事务
			try {
				String cashResult = this.getInnerService().cashPrize(lotteryId, termNo, singleOrder, lotteryTermInfo,true,outResult);
				result.add("0" + "#" + lotteryId + "#" + termNo + "#" + singleOrder+"#" + cashResult + "#" + outResult.get("A"));
				log.logInfo(lotteryId, termNo, singleOrder, cashResult);
			} catch (Exception e) {//该异常不能对外抛出
				result.add("1"+lotteryId+"#"+termNo+"#"+singleOrder+"#"+"##0" + "#" + outResult.get("A"));
				log.logInfo(lotteryId, termNo, singleOrder, "##兑奖失败!");
				logger.error(lotteryId+"#"+termNo+"#"+singleOrder+"兑奖失败原因:", e);
			}
		}
		Map<String,String> dbLogMap = new HashMap<String,String>();//此处日志数据没有设置
		dbLogMap.put("userId", "");
		dbLogMap.put("userName", "");
		dbLogMap.put("userKey", "");
		
		this.getInnerService().updateCashPrizeCompleteStatus(lotteryId, termNo);//更新彩期表状态，看是否已经兑奖完成
		logger.debug("兑奖---多个订单---完成---"+"彩种:"+lotteryId+"彩期:"+termNo);
		return result;
	}

	/* (非 Javadoc)
	 *Title: cashSingleOrder
	 *Description: 
	 * @param ordrId
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.CashPrizeInterf#cashSingleOrder(java.lang.String)
	 */
	public String cashSingleOrder(String orderId) throws LotteryException {
		logger.info("兑奖---单个订单---开始---"+orderId);
		BetOrderDomain lotteryOrderInfo = this.getInnerService().getOrderInfo(orderId);//获取订单信息
		int lotteryId = lotteryOrderInfo.getLotteryId();//彩种
		String termNo = lotteryOrderInfo.getBetTerm();//彩期
		logger.info("兑奖---单个订单---彩种:"+lotteryId+"彩期:"+termNo);
		LotteryTermModel lotteryTermInfo = this.getInnerService().checkTermCanCash(lotteryId, termNo);//校验彩期是否可以兑奖
		logger.info("兑奖---单个订单---彩种:"+lotteryId+"彩期:"+termNo+"可以兑奖");
		String cashResult = "";
		Map<String,String> outResult = new HashMap<String,String>();
		outResult.put("A", "0");
		try {
			
			cashResult = this.getInnerService().cashPrize(lotteryId, termNo, orderId, lotteryTermInfo,false,outResult);
			log.logInfo(lotteryId, termNo, orderId, cashResult);
		} catch (LotteryException e) {
			log.logInfo(lotteryId, termNo, orderId, "##兑奖失败");
			logger.error(lotteryId+"#"+termNo+"#"+orderId+"兑奖失败原因:", e);
			throw e;
		}
		logger.info("兑奖结果:"+cashResult);
		Map<String,String> dbLogMap = new HashMap<String,String>();//此处日志数据没有设置
		dbLogMap.put("userId", "");
		dbLogMap.put("userName", "");
		dbLogMap.put("userKey", "");
		this.getInnerService().updateCashPrizeCompleteStatus(lotteryId, termNo);
		logger.info("彩种:"+lotteryId+"彩期:"+termNo+"兑奖成功");
		logger.info("兑奖---单个订单---结束---"+orderId);
		return new StringBuffer().append("0").append(lotteryId).append("#").append(termNo).append("#").append(orderId).append("#").append(cashResult).append("#").append(outResult.get("A")).toString();
	}
	/**
	 * 
	 * Title: dbLog<br>
	 * Description: <br>
	 *              <br>数据库日志记录
	 * @param logId
	 * @param userId
	 * @param userName
	 * @param userKey
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param keyword4
	 * @param errorMessage
	 */
	private void dbLog(int logId,String keyword1,String keyword2,String keyword3,String keyword4,String errorMessage,Map<String,String> dbLogMap){
		try {
			Map<String,String> param = new HashMap<String,String>();
			param.put("userId", dbLogMap.get("userId"));
			param.put("userName", dbLogMap.get("userName"));
			param.put("userKey", dbLogMap.get("userKey"));
			param.put("keyword1", keyword1);
			param.put("keyword2", keyword2);
			if(StringUtils.isNotEmpty(keyword3)){
				param.put("keyword3", keyword3);
			}
			if(StringUtils.isNotEmpty(keyword4)){
				param.put("keyword4", keyword4);
			}
			if(StringUtils.isNotEmpty(errorMessage)){
				param.put("errorMessage", errorMessage);
			}
			
			OperatorLogger.log(logId, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * (非 Javadoc)
	*Title: dealNotTicketOrder
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param dbLogMap
	* @return
	 *         total_num,总共可以处理的订单数
	 *         sucess_num,成功处理的订单数
	 *         fail_num,处理失败的当单数
	 *         sucess_bet_num,转为投注的订单数
	 *         sucess_limit_num,转为限号的订单数
	 *         nextTerm,处理的彩期
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.CashPrizeInterf#dealNotTicketOrder(int, java.lang.String, java.util.Map)
	 */
	public Map<String, String> dealNotTicketOrder(int lotteryId, String termNo, Map<String, String> dbLogMap) throws LotteryException {
		Map<String,String> dealResult = new HashMap<String,String>();
		int total_num = 0;
		int sucess_num = 0;
		int fail_num = 0;
		int sucess_bet_num = 0;
		int sucess_limit_num = 0;
		String nextTerm = "";
		try {
			
			LotteryTermModel lotteryTermInfo = this.getInnerService().getTermInfo(lotteryId, termNo);
			nextTerm = lotteryTermInfo.getNextTerm();
			
			List<Integer> winStatus = new ArrayList<Integer>();
			winStatus.add(0);
			List<String> needDealOrders = this.getInnerService().queryOrderByStatus(lotteryId, nextTerm, 13, winStatus);
			
			logger.info("追号处理---开始---"+"彩种:"+lotteryId+"兑奖彩期:"+termNo+"追号彩期:"+nextTerm);
			this.dbLog(40007, String.valueOf(lotteryId), termNo, nextTerm, "", "", dbLogMap);//处理追号开始
			//取下期限号信息
			String limitNumber = this.getInnerService().getTermInfo(lotteryId,nextTerm).getLimitNumber();
			
			if(needDealOrders != null){
				total_num = needDealOrders.size();
			}
			//循环处理追号订单，循环中每一个都是一个单独的事务
			log.logInfo(lotteryId, termNo, "开始循环处理追号");
			for(String orderId : needDealOrders){
				try {
					//处理尚未送入出票队列的追号订单,只对下一期的处理,包括限号的处理
					String result = this.getInnerService().dealNotTicketZhuiHao(lotteryId, nextTerm, limitNumber, orderId);
					if(StringUtils.isNotEmpty(result)){
						if("0".equals(result)){
							sucess_bet_num++;
						}else{
							sucess_limit_num++;
						}
						sucess_num++;
					}
					log.logInfo(lotteryId, termNo, orderId, result);
				} catch (Exception e) {
					fail_num++;
					log.logInfo(lotteryId, termNo, orderId, "失败");
					this.dbLog(41007, String.valueOf(lotteryId), termNo, orderId, "", e.getMessage(), dbLogMap);
				}
			}
			log.logInfo(lotteryId, termNo, "结束循环处理追号");
		} catch (Exception e) {
			this.dbLog(41008, String.valueOf(lotteryId), termNo, nextTerm, "", e.getMessage(), dbLogMap);
		}finally{
			dealResult.put("total_num", String.valueOf(total_num));
			dealResult.put("sucess_num", String.valueOf(sucess_num));
			dealResult.put("fail_num", String.valueOf(fail_num));
			dealResult.put("sucess_bet_num", String.valueOf(sucess_bet_num));
			dealResult.put("sucess_limit_num", String.valueOf(sucess_limit_num));
			dealResult.put("nextTerm", nextTerm);
		}
		logger.info("追号处理---结束---"+"彩种:"+lotteryId+"兑奖彩期:"+termNo+"追号彩期:"+nextTerm);
		this.dbLog(40008, String.valueOf(lotteryId), termNo, nextTerm, "", "",
				dbLogMap);
		return dealResult;
	}
	/*
	 * (非 Javadoc)
	*Title: updateCashTermStatus
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param dbLogMap
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.CashPrizeInterf#updateCashTermStatus(int, java.lang.String, java.util.Map)
	 */
	public Map<String, String> updateCashTermStatus(int lotteryId, String termNo, Map<String, String> dbLogMap) throws LotteryException {
		Map<String, String> upResult = new HashMap<String,String>();
		String old_status = "";
		String new_status = "";
		try {
			String result = this.getInnerService().updateCashPrizeCompleteStatus(lotteryId, termNo);
			if(StringUtils.isNotEmpty(result)){
				old_status = result.split("#")[0];
				new_status = result.split("#")[1];
			}
			log.logInfo(lotteryId, termNo, "兑奖彩期修改成功,原状态:"+old_status+"修改后状态:"+new_status);
		} catch (Exception e) {
			log.logInfo(lotteryId, termNo, "兑奖彩期修改失败,失败原因:"+e);
			this.dbLog(41006, String.valueOf(lotteryId), termNo, "", "", e.getMessage(), dbLogMap);
		}finally{
			upResult.put("old_status", old_status);
			upResult.put("new_status", new_status);
		}
		this.dbLog(40006, String.valueOf(lotteryId), termNo, "", "", "", dbLogMap);
		return upResult;
	}

	private PrizeInnerInterf getInnerService() {
		return this.innerService;
	}

	public void setInnerService(PrizeInnerInterf innerService) {
		this.innerService = innerService;
	}
	
}
