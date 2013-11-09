/**
 * Title: DealCpPlanService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-4-13 下午07:08:06
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.success.lottery.business.service.interf.BetServiceInterf;
import com.success.lottery.business.service.interf.DealCpPlanInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.service.TermLog;

/**
 * com.success.lottery.business.service.impl
 * DealCpPlanService.java
 * DealCpPlanService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-4-13 下午07:08:06
 * 
 */

public class DealCpPlanService implements DealCpPlanInterf {
	private static TermLog cooplog = TermLog.getInstance("COOPLOG");
	private static int dbLogFlagS = 41041;
	private static int dbLogFlagF = 41042;
	
	private BetPlanOrderServiceInterf betOrderService;
	private BetServiceInterf betService;

	/* (非 Javadoc)
	 *Title: dealPlanBySys
	 *Description: 需要记录日志
	 * @param lotteryTerms
	 * @see com.success.lottery.business.service.interf.DealCpPlanInterf#dealPlanBySys(java.util.Map)
	 */
	public void dealPlanBySys(Map<Integer, String> lotteryTerms) {
		String lotteryIds = "";
		String terms = "";
		try{
			List<Integer> planTypes = new ArrayList<Integer>();
			planTypes.add(1);
			List<Integer> planStatus = new ArrayList<Integer>();
			planStatus.add(0);
			List<BetPlanDomain> needDealPlan = null;
			
			for(Map.Entry<Integer, String> params : lotteryTerms.entrySet()){
				lotteryIds = params.getKey() + ",";
				terms = params.getValue();
			}
			try{
				needDealPlan = this.getBetOrderService().queryBetPlan(lotteryTerms, planTypes, planStatus);
			}catch(LotteryException e1){
				cooplog.logInfo(lotteryIds +"#" + terms + "#"+"获取该彩种的方案信息发生异常");
				this.dbLog(dbLogFlagF, lotteryIds, terms, null, null, "获取彩种对应的合买方案失败:"+e1.getMessage());
				throw e1;
			}
			int totalNeedPlan = 0;
			int successPlan = 0;
			if(needDealPlan != null){
				totalNeedPlan = needDealPlan.size();
				cooplog.logInfo(lotteryIds +"#" + terms + "#"+"需要处理的方案数："+totalNeedPlan);
				for(BetPlanDomain oneNeedPlan : needDealPlan){
					try{
						String needPlanId = oneNeedPlan.getPlanId();
						this.getBetService().dealOnePlanBySys(needPlanId);
						successPlan++;
						cooplog.logInfo(lotteryIds +"#" + terms + "#" + oneNeedPlan.getPlanId() +"success");
					}catch(LotteryException e2){
						cooplog.logInfo(lotteryIds +"#" + terms + "#" + oneNeedPlan.getPlanId() +"fail");
						//写系统日志
						this.dbLog(dbLogFlagF, lotteryIds, terms, oneNeedPlan.getPlanId(), null, "合买方案处理失败:"+e2.getMessage());
					}catch(Exception e3){
						cooplog.logInfo(lotteryIds +"#" + terms + "#" + oneNeedPlan.getPlanId() +"fail");
						//写系统日志
						this.dbLog(dbLogFlagF, lotteryIds, terms, oneNeedPlan.getPlanId(), null, "合买方案处理失败:"+e3.getMessage());
					}
				}
				cooplog.logInfo(lotteryIds +"#" + terms + "#"+"成功处理的方案数："+successPlan);
			}else{
				//写一条系统日志，说明本彩种彩期无合买的订单需要处理
				this.dbLog(dbLogFlagS, lotteryIds, terms, String.valueOf(totalNeedPlan), String.valueOf(successPlan), "该彩期无需要处理的合买订单");
			}
		}catch(LotteryException e){
			//写系统日志
			this.dbLog(dbLogFlagF, lotteryIds, terms, null, null, "合买期结处理发生异常:"+e.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			//写系统日志
			this.dbLog(dbLogFlagF, lotteryIds, terms, null, null, "合买期结处理发生异常:"+ex.getMessage());
		}
	}
	/**
	 * 
	 * Title: dbLog<br>
	 * Description: <br>
	 *              <br>写系统日志
	 * @param logId
	 * @param keyword1
	 * @param keyword2
	 * @param keyword3
	 * @param keyword4
	 * @param errorMessage
	 */
	private void dbLog(int logId,String keyword1,String keyword2,String keyword3,String keyword4,String errorMessage){
		try {
			Map<String,String> param = new HashMap<String,String>();
			param.put("userId", "0");
			param.put("userName", "Robot");
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
	
	public BetServiceInterf getBetService() {
		return betService;
	}

	public void setBetService(BetServiceInterf betService) {
		this.betService = betService;
	}
	public BetPlanOrderServiceInterf getBetOrderService() {
		return betOrderService;
	}
	public void setBetOrderService(BetPlanOrderServiceInterf betOrderService) {
		this.betOrderService = betOrderService;
	}

}
