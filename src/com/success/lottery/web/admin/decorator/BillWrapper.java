package com.success.lottery.web.admin.decorator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.account.model.BillOrderModel;
import com.success.lottery.business.service.LotteryStaticDefine;

public class BillWrapper  extends TableDecorator {
	private String amountF;
	
	private String orderStatusF;
	
	private String checkedStatusF;
	
	private String billTime;

	public String getAmountF() {
		this.setAmountF(this.formatMoney(((BillOrderModel)this.getCurrentRowObject()).getOrderAmount()));
		return amountF;
	}

	public void setAmountF(String amountF) {
		this.amountF = amountF;
	}

	public String getOrderStatusF() {
		this.setOrderStatusF(LotteryStaticDefine.billOrderStatus.get(((BillOrderModel)this.getCurrentRowObject()).getOrderStatus()));
		return orderStatusF;
	}

	public void setOrderStatusF(String orderStatusF) {
		this.orderStatusF = orderStatusF;
	}

	public String getCheckedStatusF() {
		this.setCheckedStatusF(LotteryStaticDefine.billCheckStatus.get(((BillOrderModel)this.getCurrentRowObject()).getCheckedStatus()));
		return checkedStatusF;
	}

	public void setCheckedStatusF(String checkedStatusF) {
		this.checkedStatusF = checkedStatusF;
	}
	
	private  String formatMoney(long money) {
		BigDecimal db = new BigDecimal(money);
		return new DecimalFormat("###,###,###.##").format(db.divide(new BigDecimal(100)));
	}

	public String getBillTime() {
		String str=((BillOrderModel)this.getCurrentRowObject()).getDealTime();
		if(str!=null&&str.length()>0)
		this.setBillTime(str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8)+" "+str.substring(8,10)+":"+str.substring(10, 12)+":"+str.substring(12,14));
		return billTime;
	}

	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}
	
}
