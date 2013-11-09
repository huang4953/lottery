/**
 * Title: IpsWrapper.java
 * @Package com.success.lottery.web.admin.decorator
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-6-4 下午04:39:13
 * @version V1.0
 */
package com.success.lottery.web.admin.decorator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.account.model.IPSOrderModel;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.business.service.LotteryStaticDefine;

/**
 * com.success.lottery.web.admin.decorator
 * IpsWrapper.java
 * IpsWrapper
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-6-4 下午04:39:13
 * 
 */

public class IpsWrapper extends TableDecorator {
	
	private String amountF;
	
	private String orderStatusF;
	
	private String checkedStatusF;

	public String getAmountF() {
		this.setAmountF(this.formatMoney(((IPSOrderModel)this.getCurrentRowObject()).getAmount()));
		return this.amountF;
	}

	public void setAmountF(String amountF) {
		this.amountF = amountF;
	}

	public String getCheckedStatusF() {
		this.setCheckedStatusF(LotteryStaticDefine.ipsCheckStatus.get(((IPSOrderModel)this.getCurrentRowObject()).getCheckedStatus()));
		return this.checkedStatusF;
	}

	public void setCheckedStatusF(String checkedStatusF) {
		this.checkedStatusF = checkedStatusF;
	}

	public String getOrderStatusF() {
		this.setOrderStatusF(LotteryStaticDefine.ipsOrderStatus.get(((IPSOrderModel)this.getCurrentRowObject()).getOrderStatus()));
		return this.orderStatusF;
	}

	public void setOrderStatusF(String orderStatusF) {
		this.orderStatusF = orderStatusF;
	}
	
	private  String formatMoney(long money) {
		BigDecimal db = new BigDecimal(money);
		return new DecimalFormat("###,###,###.##").format(db.divide(new BigDecimal(100)));
	}

}
