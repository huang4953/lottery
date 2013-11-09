/**
 * Title: SysDealCoopPlan.java
 * @Package com.success.lottery.business.service
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-4-14 下午02:14:23
 * @version V1.0
 */
package com.success.lottery.business.service;

import java.util.Map;

import com.success.lottery.business.service.interf.CashPrizeInterf;
import com.success.lottery.business.service.interf.DealCpPlanInterf;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.business.service
 * SysDealCoopPlan.java
 * SysDealCoopPlan
 * (合买期结束时系统处理合买订单，由彩期切换程序调用)
 * @author gaoboqin
 * 2011-4-14 下午02:14:23
 * 
 */

public class SysDealCoopPlan extends Thread {
	
	private Map<Integer, String> lotteryTerms = null;

	/**
	 *Title: 
	 *Description: 
	 */
	public SysDealCoopPlan(Map<Integer, String> dealLotteryTerms) {
		this.lotteryTerms = dealLotteryTerms;
	}
	
	public void run(){
		try{
			DealCpPlanInterf dealCpService = ApplicationContextUtils.getService("busCoopBySysService",DealCpPlanInterf.class);
			dealCpService.dealPlanBySys(this.lotteryTerms);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
