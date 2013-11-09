package com.success.lottery.web.customer.action;

import java.io.IOException;
import java.io.PrintWriter;

import com.success.lottery.account.model.IPSReturnInfo;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.account.service.IPSOrderService;
import com.success.lottery.account.service.IPSTools;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;

import java.text.DecimalFormat; 
/**
 * 
 * @author chenhao
 * @version 1.0 
 * @description  用户充值
 *
 */
@SuppressWarnings("serial")
public class IPSOdersAction extends LotteryWebBaseActon{
	private String amountf;
	private String billno;
	private String Currency_type;//币种
	private String amount;//金额
	private String date;//日期
	private String succ;//
	private String msg;//消息
	private String attach;//商户附加数据包
	private String ipsbillno;//IPS订单编号
	private String retencodetype;//编码
	private String signature;//效验码
	private String ipsbanktime;//交易时间

	public String getIpsbanktime() {
		return ipsbanktime;
	}
	public void setIpsbanktime(String ipsbanktime) {
		this.ipsbanktime = ipsbanktime;
	}
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	public String getAmountf() {
		return amountf;
	}
	public void setAmountf(String amountf) {
		this.amountf = amountf;
	}
	
	public String wycz(){
		return "success";
	}
	
	public void moniLogin(){
		//模拟登录
		/********************************/
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		try {//"admin1", "admin"
			UserAccountModel	customer = userservice.login("admin1","admin", getRemoteIp());
    		saveCurCustomer(customer);
		} catch (LotteryException e) {
		}
		/********************************/
	}
	/**
	 * 先添加IPSOrder 用于ajax调用
	 * @throws IOException 
	 */
	public void addIPSOrder() throws IOException{
		IPSOrderService	 ipsoderservice=ApplicationContextUtils.getService("ipsService",IPSOrderService.class);
		UserAccountModel useraccount=getCurCustomer();
		PrintWriter out = super.getResponse().getWriter();
		int amount=(int)(Double.parseDouble(getAmountf())*100);//充值金额
		String Billno="fail";//默认为失败
		try {
			Billno=ipsoderservice.addIPSOrder(useraccount.getUserId(), amount,useraccount.getMobilePhone()).trim();
		} catch (LotteryException e) {
		   //是不  返回 
			e.printStackTrace();
		}finally{
			//成功返回 订单号，失败返回fail
			out.println(Billno);
		}
		out.close();
	}
	/**
	 * 去环讯支付
	 */
	public String IPSPayMoney(){
		UserAccountModel useraccount=getCurCustomer();
		getRequest().setAttribute("Mer_code",IPSTools.getMer_Code());//商户号
		getRequest().setAttribute("Billno",getBillno().trim());//商户订单编号
		DecimalFormat currentNumberFormat=new DecimalFormat("#0.00"); 
		String Amount = currentNumberFormat.format(Double.parseDouble(getAmountf().trim()));
		getRequest().setAttribute("Amount",Amount);//订单金额(保留2位小数)
		String path = getRequest().getContextPath();
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+path+"/";
		//订单日期
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
		String date = formatter.format(currentTime); //将日期时间格式化
		getRequest().setAttribute("Date",date);//订单日期
		getRequest().setAttribute("Currency_Type",IPSTools.getCurrency_Type());//币种
		getRequest().setAttribute("Gateway_Type",IPSTools.getGateway_Type());//支付卡种
		getRequest().setAttribute("Lang",IPSTools.getLang());//言语
		getRequest().setAttribute("Merchanturl", basePath + IPSTools.getMerchanturl());//支付结果成功返回的商户URL
		getRequest().setAttribute("FailUrl",basePath + IPSTools.getFailUrl());////支付结果失败返回的商户URL
		getRequest().setAttribute("ErrorUrl",basePath + IPSTools.getErrorUrl());//支付结果错误返回的商户URL
		getRequest().setAttribute("Attach",useraccount.getMobilePhone());//商户数据包
		getRequest().setAttribute("DispAmount",getAmountf().trim());//显示金额
		getRequest().setAttribute("OrderEncodeType",IPSTools.getOrderEncodeType());//订单支付接口加密方式
		getRequest().setAttribute("RetEncodeType",IPSTools.getRetEncodeType());//交易返回接口加密方式 
		getRequest().setAttribute("Rettype",IPSTools.getRettype());//返回方式
		getRequest().setAttribute("ServerUrl",basePath + IPSTools.getServerUrl());//Server to Server 返回页面URL
		getRequest().setAttribute("SignMD5",IPSTools.getSignMD5(getBillno().trim(),Amount.trim(), date.trim()));//订单支付接口的Md5摘要，原文=订单号+金额+日期+支付币种+商户证书 
		getRequest().setAttribute("postUrl",IPSTools.getPostUrl());//数据提交到IPS的路径
		return "pagMoney";
	}
	/**
	 * 环讯支付_支付结果失败
	 */
	public String payFail(){
		return  "pagMoney";
	}
	/**
	 * 环讯支付_支付结果成功
	 */
	public String paySuccess(){
		IPSReturnInfo info=new IPSReturnInfo();
		info.setBillNo(getBillno());
		info.setCurrencyType(getCurrency_type());
		info.setSucc(getSucc().charAt(0));
		info.setDate(Integer.parseInt(getDate()));
		info.setIpsOrderId(getIpsbillno());
		info.setSignature(getSignature());
		info.setAttach(getAttach());
		info.setAmount(getAmount());
		info.setIpsBankTime(this.getIpsbanktime());
		info.setMsg(getMsg());
		boolean flg=IPSTools.verificationSignature(info);
		if(flg)
		{
			if(getSucc()!=null)
			{
				IPSOrderService	 ipsoderservice=ApplicationContextUtils.getService("ipsService",IPSOrderService.class);
				try {
					ipsoderservice.processIPSOrder(info);
				} catch (LotteryException e) {
					e.printStackTrace();
				}
				if("Y".equalsIgnoreCase(getSucc()))
					
					return "success";
			}
		}		
			return "fail";
		
	}
	/**
	 * 环讯支付_支付结果错误
	 */
	public String payError(){
		return "pagMoney";
	}
	public String getCurrency_type() {
		return Currency_type;
	}
	public void setCurrency_type(String currency_type) {
		Currency_type = currency_type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSucc() {
		return succ;
	}
	public void setSucc(String succ) {
		this.succ = succ;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getIpsbillno() {
		return ipsbillno;
	}
	public void setIpsbillno(String ipsbillno) {
		this.ipsbillno = ipsbillno;
	}
	public String getRetencodetype() {
		return retencodetype;
	}
	public void setRetencodetype(String retencodetype) {
		this.retencodetype = retencodetype;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
}
