package com.success.lottery.web.test.action;

import com.success.lottery.account.service.IPSOrderService;
import com.success.lottery.util.LotterySequence;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
@SuppressWarnings("serial")
public class OrderAction extends LotteryWebBaseActon{
	private String pMerCert;
	private String pMerCode;
	private String pBillNo;
	private String pCurrency_Type;
	private String pAmount;
	private String pDate;
	private String pGateWayType;
	private IPSOrderService iPSOrderService;
	
	public String index(){
		return SUCCESS;
	}
	public String back(){ 
		return "back";
	}
	public String save(){ 	
//		iPSOrderService.addIPSOrder(1,pAmount,pCurrency_Type,pGateWayType,"");
		http://pay.ips.net.cn/ipayment.aspx?aaaa
		return "save";
	}
	public String getPAmount() {
		
		return pAmount;
	}
	public void setPAmount(String amount) {
		pAmount = amount;
	}
	public String getPBillNo() {
		return pBillNo;
	}
	public void setPBillNo(String billNo) {
		pBillNo = billNo;
	}
	public String getPCurrency_Type() {
		return pCurrency_Type;
	}
	public void setPCurrency_Type(String currency_Type) {
		pCurrency_Type = currency_Type;
	}
	public String getPDate() {
		return pDate;
	}
	public void setPDate(String date) {
		pDate = date;
	}
	public String getPGateWayType() {
		return pGateWayType;
	}
	public void setPGateWayType(String gateWayType) {
		pGateWayType = gateWayType;
	}
	public String getPMerCert() {
		return pMerCert;
	}
	public void setPMerCert(String merCert) {
		pMerCert = merCert;
	}
	public String getPMerCode() {
		return pMerCode;
	}
	public void setPMerCode(String merCode) {
		pMerCode = merCode;
	}
	public IPSOrderService getIPSOrderService() {
		return iPSOrderService;
	}
	public void setIPSOrderService(IPSOrderService orderService) {
		iPSOrderService = orderService;
	}
	
}
