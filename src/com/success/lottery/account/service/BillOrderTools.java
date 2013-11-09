package com.success.lottery.account.service;

import com.success.utils.AutoProperties;

public class BillOrderTools {
	private static String resource = "com.success.lottery.account.service.bill";
	
	
	public static String getMerchantAcctId(){
		return AutoProperties.getString("bill.merchantAcctId", "", resource);
	}
	public static String getKey(){
		return AutoProperties.getString("bill.key", "", resource);
	}
	public static String getInputCharset(){
		return AutoProperties.getString("bill.inputCharset", "2", resource);
	}
	public static String getPageUrl(){
		return AutoProperties.getString("bill.pageUrl", "", resource);
	}
	public static String getBgUrl(){
		return AutoProperties.getString("bill.bgUrl", "", resource);
	}
	public static String getVersion(){
		return AutoProperties.getString("bill.version", "v2.0", resource);
	}
	public static String getLanguage(){
		return AutoProperties.getString("bill.language", "1", resource);
	}
	public static String getSignType(){
		return AutoProperties.getString("bill.signType", "1", resource);
	}
	public static String getPayerContactType(){
		return AutoProperties.getString("bill.payerContactType", "1", resource);
	}
	public static String getProductName(){
		return AutoProperties.getString("bill.productName","", resource);
	}
	public static String getProductId(){
		return AutoProperties.getString("bill.productId", "", resource);
	}
	public static String getProductDesc(){
		return AutoProperties.getString("bill.productDesc", "", resource);
	}
	public static String getExt1(){
		return AutoProperties.getString("bill.ext1", "", resource);
	}
	public static String getExt2(){
		return AutoProperties.getString("bill.ext2", "", resource);
	}
	public static String getPayType(){
		return AutoProperties.getString("bill.payType", "00", resource);
	}
	public static String getBankId(){
		return AutoProperties.getString("bill.bankId", "", resource);
	}
	public static String getRedoFlag(){
		return AutoProperties.getString("bill.redoFlag", "1", resource);
	}
	public static String getPid(){
		return AutoProperties.getString("bill.pid", "", resource);
	}
	public static String getSentUrl(){
		return AutoProperties.getString("bill.sentUrl", "", resource);
	}
	public static String getProductNum(){
		return AutoProperties.getString("bill.productNum", "", resource);
	}
	//功能函数。将变量值不为空的参数组成字符串
	public static String appendParam(String returnStr,String paramId,String paramValue)
	{
			if(!returnStr.equals(""))
			{
				if(!paramValue.equals(""))
				{
					returnStr=returnStr+"&"+paramId+"="+paramValue;
				}
			}
			else
			{
				if(!paramValue.equals(""))
				{
				returnStr=paramId+"="+paramValue;
				}
			}	
			return returnStr;
	}
	
	public static void main(String [] agr){
		System.out.print(BillOrderTools.getProductName());
	}
}
