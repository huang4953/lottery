/**
 * Title: AccountTransWrapper.java
 * @Package com.success.lottery.web.admin.decorator
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-6-4 下午01:51:38
 * @version V1.0
 */
package com.success.lottery.web.admin.decorator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.displaytag.decorator.TableDecorator;

import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.business.service.LotteryStaticDefine;

/**
 * com.success.lottery.web.admin.decorator
 * AccountTransWrapper.java
 * AccountTransWrapper
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-6-4 下午01:51:38
 * 
 */

public class AccountTransWrapper extends TableDecorator {
	
	private String prizeA = "--";
	private String prizeB = "--";
	private String prizeC = "--";
	private String prizeD = "--";
	
	private String fundsAccountF;
	private String prizeAccountF;
	private String frozenAccountF;
	
	private String transactionTypeF;
	
	private String accountDetailLink;

	public String getFrozenAccountF() {
		this.setFrozenAccountF(this.formatMoney(((AccountTransactionModel)this.getCurrentRowObject()).getFrozenAccount()));
		return this.frozenAccountF;
	}

	public void setFrozenAccountF(String frozenAccountF) {
		this.frozenAccountF = frozenAccountF;
	}

	public String getFundsAccountF() {
		this.setFundsAccountF(this.formatMoney(((AccountTransactionModel)this.getCurrentRowObject()).getFundsAccount()));
		return this.fundsAccountF;
	}

	public void setFundsAccountF(String fundsAccountF) {
		this.fundsAccountF = fundsAccountF;
	}

	public String getPrizeA() {
		String flag = LotteryStaticDefine.transactionToOpType.get(((AccountTransactionModel)this.getCurrentRowObject()).getTransactionType());
		String money = this.formatMoney(((AccountTransactionModel)this.getCurrentRowObject()).getAmount());
		if("A".equals(flag)){
			this.setPrizeA(money);
		}else{
			this.setPrizeA("--");
		}
		return this.prizeA;
	}

	public void setPrizeA(String prizeA) {
		this.prizeA = prizeA;
	}

	public String getPrizeAccountF() {
		this.setPrizeAccountF(this.formatMoney(((AccountTransactionModel)this.getCurrentRowObject()).getPrizeAccount()));
		return this.prizeAccountF;
	}

	public void setPrizeAccountF(String prizeAccountF) {
		this.prizeAccountF = prizeAccountF;
	}

	public String getPrizeB() {
		String flag = LotteryStaticDefine.transactionToOpType.get(((AccountTransactionModel)this.getCurrentRowObject()).getTransactionType());
		String money = this.formatMoney(((AccountTransactionModel)this.getCurrentRowObject()).getAmount());
		if("B".equals(flag)){
			this.setPrizeB(money);
		}else{
			this.setPrizeB("--");
		}
		return this.prizeB;
	}

	public void setPrizeB(String prizeB) {
		this.prizeB = prizeB;
	}

	public String getPrizeC() {
		String flag = LotteryStaticDefine.transactionToOpType.get(((AccountTransactionModel)this.getCurrentRowObject()).getTransactionType());
		String money = this.formatMoney(((AccountTransactionModel)this.getCurrentRowObject()).getAmount());
		if("C".equals(flag)){
			this.setPrizeC(money);
		}else{
			this.setPrizeC("--");
		}
		return this.prizeC;
	}

	public void setPrizeC(String prizeC) {
		this.prizeC = prizeC;
	}

	public String getPrizeD() {
		String flag = LotteryStaticDefine.transactionToOpType.get(((AccountTransactionModel)this.getCurrentRowObject()).getTransactionType());
		String money = this.formatMoney(((AccountTransactionModel)this.getCurrentRowObject()).getAmount());
		if("D".equals(flag)){
			this.setPrizeD(money);
		}else{
			this.setPrizeD("--");
		}
		return this.prizeD;
	}

	public void setPrizeD(String prizeD) {
		this.prizeD = prizeD;
	}

	public String getTransactionTypeF() {
		this.setTransactionTypeF(LotteryStaticDefine.transactionType.get(((AccountTransactionModel)this.getCurrentRowObject()).getTransactionType()));
		return this.transactionTypeF;
	}

	public void setTransactionTypeF(String transactionTypeF) {
		this.transactionTypeF = transactionTypeF;
	}
	
	private  String formatMoney(long money) {
		BigDecimal db = new BigDecimal(money);
		return new DecimalFormat("###,###,###.##").format(db.divide(new BigDecimal(100)));
	}

	public String getAccountDetailLink() {
		this.setAccountDetailLink(LotteryStaticDefine.getUserDetailLink(((AccountTransactionModel)this.getCurrentRowObject()).getMobilePhone()));
		return this.accountDetailLink;
	}

	public void setAccountDetailLink(String accountDetailLink) {
		this.accountDetailLink = accountDetailLink;
	}

}
