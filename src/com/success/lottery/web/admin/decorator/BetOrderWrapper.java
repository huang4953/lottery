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

import com.success.lottery.business.domain.BusBetOrderDomain;
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

public class BetOrderWrapper extends TableDecorator {
	
	private String lotteryCn_name;
	private String planSource_name;
	private String orderStatus_name;
	private String orderWinStatus_name;
	private String ticketStatus_name;
	private String playBet_name;
	
	private String showOrderDetail;
	
	private String showAccountDetail;
	
	private String dispatchLink;
	
	private String daiGouErrLink;
	
	private String heMaiErrLink;
	
	private BigDecimal betAmount;
	
	private BigDecimal preTaxPrize;
	
	private BigDecimal aftTaxPrize;
	private BigDecimal taxPrize;
	private BigDecimal tiChengPrize;
	private BigDecimal commPrize;
	
	private String winCodeF;
	
	private String orderWinStatusF;
	
	
	/**
	 *Title: 
	 *Description: 
	 */
	public BetOrderWrapper() {
		super();
	}

	public String getLotteryCn_name() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		this.setLotteryCn_name(LotteryTools.getLotteryName(betOrder.getLotteryId()));
		return this.lotteryCn_name;
	}

	public void setLotteryCn_name(String lotteryCn_name) {
		this.lotteryCn_name = lotteryCn_name;
	}

	public String getPlanSource_name() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		this.setPlanSource_name(LotteryStaticDefine.planSource.get(String.valueOf(betOrder.getPlanSource())));
		return this.planSource_name;
	}

	public void setPlanSource_name(String planSource_name) {
		this.planSource_name = planSource_name;
	}

	public String getOrderStatus_name() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		this.setOrderStatus_name(LotteryStaticDefine.orderStatus.get(String.valueOf(betOrder.getOrderStatus())));
		return this.orderStatus_name;
	}

	public void setOrderStatus_name(String orderStatus_name) {
		this.orderStatus_name = orderStatus_name;
	}

	public String getOrderWinStatus_name() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		this.setOrderWinStatus_name(LotteryStaticDefine.orderWinStatus.get(String.valueOf(betOrder.getWinStatus())));
		return this.orderWinStatus_name;
	}

	public void setOrderWinStatus_name(String orderWinStatus_name) {
		this.orderWinStatus_name = orderWinStatus_name;
	}

	public String getTicketStatus_name() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		this.setTicketStatus_name(LotteryStaticDefine.ticketStatus.get(String.valueOf(betOrder.getTicketStat())));
		return this.ticketStatus_name;
	}

	public void setTicketStatus_name(String ticketStatus_name) {
		this.ticketStatus_name = ticketStatus_name;
	}

	public String getPlayBet_name() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		this.setPlayBet_name(LotteryTools.getPlayBetName(betOrder.getLotteryId(),betOrder.getPlayType(),betOrder.getBetType()));
		return this.playBet_name;
	}

	public void setPlayBet_name(String playBet_name) {
		this.playBet_name = playBet_name;
	}

	public String getShowOrderDetail() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		String orderId = betOrder.getOrderId();
		this.setShowOrderDetail(LotteryStaticDefine.getOrderDetailLink(orderId,orderId));
		return this.showOrderDetail;
	}

	public void setShowOrderDetail(String showOrderDetail) {
		this.showOrderDetail = showOrderDetail;
	}

	public String getShowAccountDetail() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		String mobilePhone = betOrder.getMobilePhone();
		this.setShowAccountDetail(LotteryStaticDefine.getUserDetailLink(mobilePhone));
		return this.showAccountDetail;
	}

	public void setShowAccountDetail(String showAccountDetail) {
		this.showAccountDetail = showAccountDetail;
	}

	public BigDecimal getBetAmount() {
		return new BigDecimal(((BusBetOrderDomain)this.getCurrentRowObject()).getBetAmount()).divide(new BigDecimal(100));
		
	}

	public void setBetAmount(BigDecimal betAmount) {
		this.betAmount = betAmount;
	}

	public BigDecimal getPreTaxPrize() {
		return new BigDecimal(((BusBetOrderDomain)this.getCurrentRowObject()).getPreTaxPrize()).divide(new BigDecimal(100));
	}

	public void setPreTaxPrize(BigDecimal preTaxPrize) {
		this.preTaxPrize = preTaxPrize;
	}

	public String getDispatchLink() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		String planId = betOrder.getPlanId();
		String orderId = betOrder.getOrderId();
		int orderStatus = betOrder.getOrderStatus();
		int winStatus = betOrder.getWinStatus();
		if(orderStatus == 8 && (winStatus == 2 || winStatus==3 || winStatus == 299 || winStatus==399)){
			this.setDispatchLink(LotteryStaticDefine.getOrderDiapatchLink(planId,orderId));
		}else{
			this.setDispatchLink("");
		}
		return this.dispatchLink;
	}

	public void setDispatchLink(String dispatchLink) {
		this.dispatchLink = dispatchLink;
	}

	public String getWinCodeF() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		int winStatus = betOrder.getWinStatus();
		if(winStatus == 2 || winStatus == 3 || winStatus == 299 || winStatus == 399){
			StringBuffer sb = new StringBuffer();
			String [] winCodeArr = betOrder.getWinCode().split(",");
			for(int k = 0 ; k < winCodeArr.length;k++){
				sb.append(winCodeArr[k]).append(",");
				if(((k+1) % 3) == 0){
					sb.append("<br>");
				}
			}
			//this.setWinCodeF(betOrder.getWinCode());
			this.setWinCodeF(sb.toString());
		}else{
			this.setWinCodeF("");
		}
		return this.winCodeF;
	}

	public void setWinCodeF(String winCodeF) {
		this.winCodeF = winCodeF;
	}

	public String getOrderWinStatusF() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		int winStatus = betOrder.getWinStatus();
		if(winStatus == 99 || winStatus == 199 || winStatus == 299 || winStatus == 399){
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
		return new BigDecimal(((BusBetOrderDomain)this.getCurrentRowObject()).getAftTaxPrize()).divide(new BigDecimal(100));
	}

	public void setAftTaxPrize(BigDecimal aftTaxPrize) {
		this.aftTaxPrize = aftTaxPrize;
	}

	public BigDecimal getCommPrize() {
		return new BigDecimal(((BusBetOrderDomain)this.getCurrentRowObject()).getCommissionPrize()).divide(new BigDecimal(100));
	}

	public void setCommPrize(BigDecimal commPrize) {
		this.commPrize = commPrize;
	}

	public BigDecimal getTaxPrize() {
		return new BigDecimal(((BusBetOrderDomain)this.getCurrentRowObject()).getTaxPrize()).divide(new BigDecimal(100));
	}

	public void setTaxPrize(BigDecimal taxPrize) {
		this.taxPrize = taxPrize;
	}

	public BigDecimal getTiChengPrize() {
		return new BigDecimal(((BusBetOrderDomain)this.getCurrentRowObject()).getDeductPrize()).divide(new BigDecimal(100));
	}

	public void setTiChengPrize(BigDecimal tiChengPrize) {
		this.tiChengPrize = tiChengPrize;
	}

	public String getDaiGouErrLink() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		String orderId = betOrder.getOrderId();
		int orderStatus = betOrder.getOrderStatus();
		int winStatus = betOrder.getWinStatus();
		if(orderStatus == 4 || orderStatus==6 && winStatus < 4){
			this.setDaiGouErrLink(LotteryStaticDefine.getDaiGouErrLink(orderId));
		}else{
			this.setDaiGouErrLink("");
		}
		return this.daiGouErrLink;
	}

	public void setDaiGouErrLink(String daiGouErrLink) {
		this.daiGouErrLink = daiGouErrLink;
	}

	public String getHeMaiErrLink() {
		BusBetOrderDomain betOrder = (BusBetOrderDomain)super.getCurrentRowObject();
		String orderId = betOrder.getOrderId();
		int orderStatus = betOrder.getOrderStatus();
		int winStatus = betOrder.getWinStatus();
		if(orderStatus == 4 || orderStatus==6 && winStatus < 4){
			this.setHeMaiErrLink(LotteryStaticDefine.heMaiGouErrLink(orderId));
		}else{
			this.setHeMaiErrLink("");
		}
		return heMaiErrLink;
	}

	public void setHeMaiErrLink(String heMaiErrLink) {
		this.heMaiErrLink = heMaiErrLink;
	}
}
