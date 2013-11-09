
package com.success.lottery.web.admin.decorator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.domain.BusCoopPlanDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.util.LotteryTools;
/**
 * 
 * com.success.lottery.web.admin.decorator
 * BetCoopPlanWrapper.java
 * BetCoopPlanWrapper
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-4-22 下午01:56:55
 *
 */

public class BetCoopPlanWrapper extends TableDecorator {
	
	private String lotteryCn_name;
	private String playBet_name;
	private String planSource_name;
	private String showAccountDetail;
	private BigDecimal unitAmountF;
	private BigDecimal unitPriceF;
	private String planStatus_name;
	private String planDetailLink;
	
	
	/**
	 *Title: 
	 *Description: 
	 */
	public BetCoopPlanWrapper() {
		super();
	}

	public String getLotteryCn_name() {
		BusCoopPlanDomain betplan = (BusCoopPlanDomain)super.getCurrentRowObject();
		this.setLotteryCn_name(LotteryTools.getLotteryName(betplan.getLotteryId()));
		return this.lotteryCn_name;
	}

	public void setLotteryCn_name(String lotteryCn_name) {
		this.lotteryCn_name = lotteryCn_name;
	}

	public String getPlayBet_name() {
		BusCoopPlanDomain bePlan = (BusCoopPlanDomain)super.getCurrentRowObject();
		this.setPlayBet_name(LotteryTools.getPlayBetName(bePlan.getLotteryId(),bePlan.getPlayType(),bePlan.getBetType()));
		return this.playBet_name;
	}

	public void setPlayBet_name(String playBet_name) {
		this.playBet_name = playBet_name;
	}

	public String getShowAccountDetail() {
		BusCoopPlanDomain betPlan = (BusCoopPlanDomain)super.getCurrentRowObject();
		String mobilePhone = betPlan.getMobilePhone();
		this.setShowAccountDetail(LotteryStaticDefine.getUserDetailLink(mobilePhone));
		return this.showAccountDetail;
	}

	public void setShowAccountDetail(String showAccountDetail) {
		this.showAccountDetail = showAccountDetail;
	}

	public String getPlanSource_name() {
		BusCoopPlanDomain betPlan = (BusCoopPlanDomain)super.getCurrentRowObject();
		this.setPlanSource_name(LotteryStaticDefine.planSource.get(String.valueOf(betPlan.getPlanSource())));
		return this.planSource_name;
	}

	public void setPlanSource_name(String planSource_name) {
		this.planSource_name = planSource_name;
	}

	public BigDecimal getUnitAmountF() {
		return new BigDecimal(((BusCoopPlanDomain)this.getCurrentRowObject()).getUnitAmount()).divide(new BigDecimal(100));
	}

	public void setUnitAmountF(BigDecimal unitAmountF) {
		this.unitAmountF = unitAmountF;
	}

	public BigDecimal getUnitPriceF() {
		return new BigDecimal(((BusCoopPlanDomain)this.getCurrentRowObject()).getUnitPrice()).divide(new BigDecimal(100));
	}

	public void setUnitPriceF(BigDecimal unitPriceF) {
		this.unitPriceF = unitPriceF;
	}

	public String getPlanStatus_name() {
		BusCoopPlanDomain betPlan = (BusCoopPlanDomain)super.getCurrentRowObject();
		this.setPlanStatus_name(LotteryStaticDefine.planStatusForWebH.get(betPlan.getPlanStatus()));
		return this.planStatus_name;
	}

	public void setPlanStatus_name(String planStatus_name) {
		this.planStatus_name = planStatus_name;
	}

	public String getPlanDetailLink() {
		BusCoopPlanDomain betPlan = (BusCoopPlanDomain)super.getCurrentRowObject();
		String planId = betPlan.getPlanId();
		this.setPlanDetailLink(LotteryStaticDefine.getCoopOrderDetailLink(planId,"",planId));
		return this.planDetailLink;
	}

	public void setPlanDetailLink(String planDetailLink) {
		this.planDetailLink = planDetailLink;
	}
}
