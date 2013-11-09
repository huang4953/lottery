package com.success.lottery.web.customer.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.business.service.interf.DrawMoneyInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.MD5;

public class WithdrawalAction extends LotteryWebBaseActon{
	private String bank;
	private String bankProvince;
	private String bankCity;
	private String userName;
	private String reason;
	private float money;
	private float procedurefee;
	private String bankNo;
	private String cardName;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String withdrawal() throws IOException{
		UserAccountModel userAccount = getCurCustomer();
		if(null==userAccount.getBankName()||null==userAccount.getRealName()||null==userAccount.getIdCard()
				||"".equals(userAccount.getBankName())||"".equals(userAccount.getRealName())||"".equals(userAccount.getIdCard())){
			this.getRequest().setAttribute("flag", "true");
			return "withdrawal";
		}
		this.bankNo=userAccount.getBankCardId().substring(userAccount.getBankCardId().length()-4,userAccount.getBankCardId().length());
		ActionContext aContext = ActionContext.getContext();
		ValueStack valueStack = aContext.getValueStack();
		valueStack.set("customer",userAccount);
		return "withdrawalSuccess";
	}
	public String withdrawalFailed() {
		UserAccountModel userAccount = getCurCustomer();
		ActionContext aContext = ActionContext.getContext();
		ValueStack valueStack = aContext.getValueStack();
		valueStack.set("customer",userAccount);
		return "withdrawalFailed";
	}
	
	public String withdrawalAddFailed() {
		UserAccountModel userAccount = getCurCustomer();
		ActionContext aContext = ActionContext.getContext();
		ValueStack valueStack = aContext.getValueStack();
		valueStack.set("customer",userAccount);
		return "withdrawalAddFailed";
	}
	
	public String withdrawalSuccess() {
		UserAccountModel userAccount = getCurCustomer();
		ActionContext aContext = ActionContext.getContext();
		ValueStack valueStack = aContext.getValueStack();
		valueStack.set("customer",userAccount);
		return "withdrawalSuccess";
	}
	public String withdrawalConfirm(){
		UserAccountModel userAccount = getCurCustomer();
		this.bankNo=userAccount.getBankCardId().substring(userAccount.getBankCardId().length()-4,userAccount.getBankCardId().length());
		 return "withdrawalConfirm";
	}

	public String withdrawalAdd() throws UnsupportedEncodingException{
		DrawMoneyInterf drawMoneyService = ApplicationContextUtils.getService("busDrawMoneyService", DrawMoneyInterf.class);
		int procedurefeeFen = 0;
		try {
			UserAccountModel userAccount  = (UserAccountModel) this.getSession().get("customer");
			if(userAccount==null||userAccount.getUserId()==0)
				throw new LotteryException(10000,"登录超时");
			if(!userAccount.getPassword().equals(MD5.MD5Encode(password)))
				throw new LotteryException(20000,"密码不正确");
			long userId = userAccount.getUserId();
			procedurefeeFen = (int) (this.getProcedurefee()*100);
			int moneyFen = (int) (this.getMoney()*100);
			System.out.println("userAccount.getBankName()[0]"+userAccount.getBankName().split("-")[1]);
			drawMoneyService.requestDrawPrizeMoney(userId, userAccount.getBankName().split("-")[0], null, 
					null,userAccount.getBankName().split("-")[1],userAccount.getBankCardId(), userAccount.getRealName(), moneyFen, 
					procedurefeeFen, this.getReason());
		} catch (LotteryException e) {
			e.printStackTrace();
			return "withdrawalAddFailed";
		}
		return "withdrawalAddSuccess";
	}
	
//	public void validateWithdrawalAdd(){
//		UserAccountModel userAccount = getCurCustomer();
//		try {
//			bankStr = new String( this.getBank().getBytes("iso8859-1"),"gbk");
//			bankCityStr = new String( this.getBankCity().getBytes("iso8859-1"),"gbk");
//			bankProvinceStr = new String( this.getBankProvince().getBytes("iso8859-1"),"gbk");
//			userNameStr = new String( this.getUserName().getBytes("iso8859-1"),"gbk");
//			cardNameStr = new String( this.getCardName().getBytes("iso8859-1"),"gbk");
//			reasonStr = new String(this.getReason().getBytes("iso8859-1"),"gbk");
//			bankNoStr = new String( this.getBankNo().getBytes("iso8859-1"),"gbk");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//		ActionContext aContext = ActionContext.getContext();
//		ValueStack valueStack = aContext.getValueStack();
//		valueStack.set("customer",userAccount);
//		valueStack.set("userNameStr",userNameStr);
//		valueStack.set("bankNoStr",bankNoStr);
//		valueStack.set("money",money);
//		
//		if(null==bankCityStr||bankCityStr.equals("")){
//			this.addFieldError("bankCity", "请选择银行所在市或县");
//		}
//		if(null==bankProvinceStr||bankProvinceStr.equals("")){
//			this.addFieldError("bankProvince", "请选择银行所在省份");
//		}
//		if(null==userNameStr||userNameStr.equals("")){
//			this.addFieldError("userName", "请填写开户行的名称");
//		}
//		if(null==bankNoStr||bankNoStr.equals("")){
//			this.addFieldError("backNo", "请填写你的银行卡号");
//		}
//		if(this.getMoney()==0){
//			this.addFieldError("money", "请填写要提现的金额");
//		}
//		if(null==reasonStr||reasonStr.equals("")){
//			this.addFieldError("reason", "请填写提现的原因");
//		}
//	}
	
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public float getProcedurefee() {
		return procedurefee;
	}
	public void setProcedurefee(float procedurefee) {
		this.procedurefee = procedurefee;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
}
