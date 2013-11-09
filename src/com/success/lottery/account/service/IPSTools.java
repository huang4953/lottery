package com.success.lottery.account.service;

import java.io.IOException;
import java.text.DecimalFormat;

import com.success.lottery.account.model.IPSReturnInfo;
import com.success.utils.AutoProperties;

import cryptix.jce.provider.MD5WithRSA;
import cryptix.jce.provider.MD5;


public class IPSTools{

	private static String resource = "com.success.lottery.account.service.ips";
	
	public static String getMer_Code(){
		return AutoProperties.getString("ips.Mer_Code", "000015", resource);
	}
	
	public static String getCurrency_Type(){
		return AutoProperties.getString("ips.Currency_Type", "RMB", resource);
	}
	
	public static String getGateway_Type(){
		return AutoProperties.getString("ips.Gateway_Type", "01", resource);
	}
	
	public static String getLang(){
		return AutoProperties.getString("ips.Lang", "GB", resource);
	}
	
	public static String getMerchanturl(){
		return AutoProperties.getString("ips.Merchanturl", "", resource);
	}
	
	public static String getFailUrl(){
		return AutoProperties.getString("ips.FailUrl", "", resource);
	}

	public static String getErrorUrl(){
		return AutoProperties.getString("ips.ErrorUrl", "", resource);
	}
	
	public static String getOrderEncodeType(){
		return AutoProperties.getString("ips.OrderEncodeType", "2", resource);
	}
	
	public static String getRetEncodeType(){
		return AutoProperties.getString("ips.RetEncodeType", "12", resource);
	}

	
	public static String getRettype(){
		return AutoProperties.getString("ips.Rettype", "1", resource);
	}

	public static String getServerUrl(){
		return AutoProperties.getString("ips.ServerUrl", "1", resource);
	}

	public static String getPostUrl(){
		return AutoProperties.getString("ips.postUrl", "http://pay.ips.net.cn/ipayment.aspx", resource);
	}

	public static String getSignMD5(String orderId, String amount, String date){
		MD5 md5 = new MD5();
		return md5.toMD5(orderId + amount + date + IPSTools.getCurrency_Type() + getMerCert()).toLowerCase();

	}

	public static boolean verificationSignature(IPSReturnInfo info, String retEncodeType){
		if("12".equals(retEncodeType)){
			String signature = info.getBillNo() + info.getAmount() + info.getDate() + info.getSucc() + info.getMsg() + info.getIpsOrderId() + info.getCurrencyType() + getMerCert();
			MD5 md5 = new MD5();
			signature = md5.toMD5(signature).toLowerCase();
			return signature.equals(info.getSignature());
		} else if("11".equals(retEncodeType)){
			String signature = info.getBillNo() + info.getAmount() + info.getDate() + info.getSucc() + info.getIpsOrderId() + info.getCurrencyType();
			MD5WithRSA md5WithRsa = new MD5WithRSA();
			md5WithRsa.verifysignature(signature, info.getSignature(), getMerCert());
			return md5WithRsa.getresult() == 0;
		} else if("10".equals(retEncodeType)){
			String signature = info.getBillNo() + info.getAmount() + info.getDate() + info.getSucc() + info.getCurrencyType();
			return signature.equals(info.getSignature());
		} else {
			return false;
		}
	}

	
	public static boolean verificationSignature(IPSReturnInfo info){
		if("12".equals(getRetEncodeType())){
			String signature = info.getBillNo() + info.getAmount() + info.getDate() + info.getSucc()+ info.getIpsOrderId() + info.getCurrencyType() + getMerCert();
			MD5 md5 = new MD5();
			signature = md5.toMD5(signature).toLowerCase();
			return signature.equals(info.getSignature());
		} else if("11".equals(getRetEncodeType())){
			String signature = info.getBillNo() + info.getAmount() + info.getDate() + info.getSucc() + info.getIpsOrderId() + info.getCurrencyType();
			MD5WithRSA md5WithRsa = new MD5WithRSA();
			md5WithRsa.verifysignature(signature, info.getSignature(), getMerCert());
			return md5WithRsa.getresult() == 0;
		} else if("10".equals(getRetEncodeType())){
			String signature = info.getBillNo() + info.getAmount() + info.getDate() + info.getSucc() + info.getCurrencyType();
			return signature.equals(info.getSignature());
		} else {
			return false;
		}
	}

	public static boolean verificationSignature(String orderId, String orderAmount, String date, String succ, String msg, String ipsOrderId, String currencyType, String signature, String retEncodeType){
		if("12".equals(retEncodeType)){
			String info = orderId + orderAmount + date + succ + msg + ipsOrderId + currencyType + getMerCert();
			MD5 md5 = new MD5();
			//signature = md5.toMD5(info);
			return signature.equals(md5.toMD5(info).toLowerCase());
		} else if("11".equals(retEncodeType)){
			String info = orderId + orderAmount + date + succ + msg + ipsOrderId + currencyType;
			MD5WithRSA md5WithRsa = new MD5WithRSA();
			md5WithRsa.verifysignature(info, signature, getMerCert());
			return md5WithRsa.getresult() == 0;
		} else if("10".equals(retEncodeType)){
			String info = orderId + orderAmount + date + succ + currencyType;			
			return info.equals(signature);
		} else {
			return false;
		}
	}
	
	public static boolean verificationSignature(String orderId, String orderAmount, String date, String succ, String msg, String ipsOrderId, String currencyType, String signature){
		if("12".equals(getRetEncodeType())){
			String info = orderId + orderAmount + date + succ + msg + ipsOrderId + currencyType + getMerCert();
			MD5 md5 = new MD5();
			return signature.equals(md5.toMD5(info).toLowerCase());
		} else if("11".equals(getRetEncodeType())){
			String info = orderId + orderAmount + date + succ + msg + ipsOrderId + currencyType;
			MD5WithRSA md5WithRsa = new MD5WithRSA();
			md5WithRsa.verifysignature(info, signature, getMerCert());
			return md5WithRsa.getresult() == 0;
		} else if("10".equals(getRetEncodeType())){
			String info = orderId + orderAmount + date + succ + currencyType;			
			return info.equals(signature);
		} else {
			return true;
		}
	}
	
	private static String getMerCert(){
		return AutoProperties.getString("ips.merCert", "", resource);
	}

	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
//        int   c=10; 
//        int   b=11; 
//
//        NumberFormat   formPercent; 
//
//        formPercent   =   NumberFormat.getPercentInstance(); 
//        formPercent.setMaximumFractionDigits(2); 
//        formPercent.setMinimumFractionDigits(2); 
//
//        System.out.println( "After   format   b   is:   "+formPercent.format(b)+ "   and   c   is:   "+formPercent.format(c)); 
		DecimalFormat   fmt   =   new   DecimalFormat( "0.00 "); 
		double a=1.00000;
		
	    
	}
}
