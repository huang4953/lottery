/**
 * Title: BetOrderWrapper.java
 * @Package com.success.lottery.web.admin.decorator
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-25 下午10:35:10
 * @version V1.0
 */
package com.success.lottery.web.admin.decorator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.business.domain.BusCpInfoDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.web.admin.decorator
 * BetOrderWrapper.java
 * BetOrderWrapper
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-25 下午10:35:10
 * 
 */

public class BetCoopOrderWrapper extends TableDecorator {
	
	private String lotteryCn_name;
	private String orderStatus_name;
	private String orderWinStatus_name;
	private String playBet_name;
	
	private String showOrderDetail;
	
	private String showAccountDetail;
	
	private String dispatchLink;
	
	private BigDecimal betAmount;
	
	private BigDecimal aftTaxPrize;
	
	private String orderWinStatusF;
	
	private String orderTypeName;
	
	private String coopInfoLink;
	
	private String coopPlanInfoLink;
	
	
	/**
	 *Title: 
	 *Description: 
	 */
	public BetCoopOrderWrapper() {
		super();
	}

	public String getLotteryCn_name() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		this.setLotteryCn_name(LotteryTools.getLotteryName(betOrder.getLotteryId()));
		return this.lotteryCn_name;
	}

	public void setLotteryCn_name(String lotteryCn_name) {
		this.lotteryCn_name = lotteryCn_name;
	}

	public String getOrderStatus_name() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		this.setOrderStatus_name(LotteryStaticDefine.coopOrderStatus.get(String.valueOf(betOrder.getOrderStatus())));
		return this.orderStatus_name;
	}

	public void setOrderStatus_name(String orderStatus_name) {
		this.orderStatus_name = orderStatus_name;
	}

	public String getOrderWinStatus_name() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		this.setOrderWinStatus_name(LotteryStaticDefine.orderWinStatus.get(String.valueOf(betOrder.getWinStatus())));
		return this.orderWinStatus_name;
	}

	public void setOrderWinStatus_name(String orderWinStatus_name) {
		this.orderWinStatus_name = orderWinStatus_name;
	}

	public String getPlayBet_name() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		this.setPlayBet_name(LotteryTools.getPlayBetName(betOrder.getLotteryId(),betOrder.getPlayType(),betOrder.getBetType()));
		return this.playBet_name;
	}

	public void setPlayBet_name(String playBet_name) {
		this.playBet_name = playBet_name;
	}

	public String getShowOrderDetail() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		String planId = betOrder.getPlanId();
		String orderId = betOrder.getCpInfoId();
		this.setShowOrderDetail(LotteryStaticDefine.getOrderDetailLink(orderId,orderId));
		return this.showOrderDetail;
	}

	public void setShowOrderDetail(String showOrderDetail) {
		this.showOrderDetail = showOrderDetail;
	}

	public String getShowAccountDetail() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		String mobilePhone = betOrder.getMobilePhone();
		this.setShowAccountDetail(LotteryStaticDefine.getUserDetailLink(mobilePhone));
		return this.showAccountDetail;
	}

	public void setShowAccountDetail(String showAccountDetail) {
		this.showAccountDetail = showAccountDetail;
	}

	public BigDecimal getBetAmount() {
		return new BigDecimal(((BusCpInfoDomain)this.getCurrentRowObject()).getCpAmount()).divide(new BigDecimal(100));
		
	}

	public void setBetAmount(BigDecimal betAmount) {
		this.betAmount = betAmount;
	}

	
	

	public String getDispatchLink() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		String planId = betOrder.getPlanId();
		String orderId = betOrder.getCpInfoId();
		int orderStatus = betOrder.getOrderStatus();
		int winStatus = betOrder.getWinStatus();
		if(orderStatus == 7 && (winStatus == 2 || winStatus==3)){
			this.setDispatchLink(LotteryStaticDefine.getOrderDiapatchLink(planId,orderId));
		}else{
			this.setDispatchLink("");
		}
		return this.dispatchLink;
	}

	public void setDispatchLink(String dispatchLink) {
		this.dispatchLink = dispatchLink;
	}

	public String getOrderWinStatusF() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		int winStatus = betOrder.getWinStatus();
		if(winStatus == 99){
			this.setOrderWinStatusF("已处理");
		}else{
			this.setOrderWinStatusF("未处理");
		}
		return this.orderWinStatusF;
	}

	public void setOrderWinStatusF(String orderWinStatusF) {
		this.orderWinStatusF = orderWinStatusF;
	}

	public BigDecimal getAftTaxPrize() {
		return new BigDecimal(((BusCpInfoDomain)this.getCurrentRowObject()).getPrize()).divide(new BigDecimal(100));
	}

	public void setAftTaxPrize(BigDecimal aftTaxPrize) {
		this.aftTaxPrize = aftTaxPrize;
	}

	public String getOrderTypeName() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		this.setOrderTypeName(LotteryStaticDefine.cpOrderType.get(String.valueOf(betOrder.getCpOrderType())));
		return this.orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getCoopInfoLink() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		String planId = betOrder.getPlanId();
		String orderId = betOrder.getCpInfoId();
		this.setCoopInfoLink(LotteryStaticDefine.getCoopOrderDetailLink(planId,orderId,orderId));
		return this.coopInfoLink;
	}

	public void setCoopInfoLink(String coopInfoLink) {
		this.coopInfoLink = coopInfoLink;
	}

	public String getCoopPlanInfoLink() {
		BusCpInfoDomain betOrder = (BusCpInfoDomain)super.getCurrentRowObject();
		String planId = betOrder.getPlanId();
		this.setCoopPlanInfoLink(LotteryStaticDefine.getCoopOrderDetailLink(planId,"",planId));
		return this.coopPlanInfoLink;
	}

	public void setCoopPlanInfoLink(String coopPlanInfoLink) {
		this.coopPlanInfoLink = coopPlanInfoLink;
	}
}
