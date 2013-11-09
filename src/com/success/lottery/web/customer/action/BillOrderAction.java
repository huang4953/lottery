package com.success.lottery.web.customer.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.account.service.BillOrderService;
import com.success.lottery.account.service.BillOrderTools;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.bill.MD5Util;

public class BillOrderAction  extends LotteryWebBaseActon{
	private String amount;
	private String orderId;
	private String orderTime;
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String gobill(){
		return "success";
	}
	public void billorderadd() throws IOException{
		BillOrderService billOrderService=ApplicationContextUtils.getService("billService",BillOrderService.class);
		UserAccountModel useraccount=getCurCustomer();
		PrintWriter out = super.getResponse().getWriter();
		long amount=(long)(Double.parseDouble(this.getAmount())*100);//充值金额
		String json="fail";//默认为失败
		try {
			json=billOrderService.addBillOrder(useraccount.getUserId(),useraccount.getLoginName(), amount);
		} catch (LotteryException e) {
		   //是不  返回 
			e.printStackTrace();
		}finally{
			//成功返回 订单号，失败返回fail
			out.println(json);
		}
		out.close();
	}
	/*
	 * 去快钱支付
	 */
	public String BillPayMoney() throws UnsupportedEncodingException{
		UserAccountModel useraccount=getCurCustomer();
		getRequest().setAttribute("merchantAcctId",BillOrderTools.getMerchantAcctId().trim());//人民币网关账户号
		getRequest().setAttribute("key",BillOrderTools.getKey().trim());//人民币网关密钥 区分大小写.
		getRequest().setAttribute("inputCharset", BillOrderTools.getInputCharset().trim());//字符集.固定选择值。可为空。1代表UTF-8; 2代表GBK; 3代表gb2312
		//接受支付结果的页面地址.与[bgUrl]不能同时为空。必须是绝对地址。
		///如果[bgUrl]为空，快钱将支付结果Post到[pageUrl]对应的地址。
		///如果[bgUrl]不为空，并且[bgUrl]页面指定的<redirecturl>地址不为空，则转向到<redirecturl>对应的地址
		getRequest().setAttribute("pageUrl",BillOrderTools.getPageUrl().trim());
		//服务器接受支付结果的后台地址.与[pageUrl]不能同时为空。必须是绝对地址。
		///快钱通过服务器连接的方式将交易结果发送到[bgUrl]对应的页面地址，在商户处理完成后输出的<result>如果为1，页面会转向到<redirecturl>对应的地址。
		///如果快钱未接收到<redirecturl>对应的地址，快钱将把支付结果post到[pageUrl]对应的页面。
		getRequest().setAttribute("bgUrl", BillOrderTools.getBgUrl().trim());
		//网关版本.固定值
		///快钱会根据版本号来调用对应的接口处理程序。
		///本代码版本号固定为v2.0
		getRequest().setAttribute("version",BillOrderTools.getVersion().trim());
		//语言种类.固定选择值。
		///只能选择1、2、3
		///1代表中文；2代表英文
		///默认值为1
		getRequest().setAttribute("language",BillOrderTools.getLanguage().trim());
		//签名类型.固定值
		///1代表MD5签名
		///当前版本固定为1
		getRequest().setAttribute("signType",BillOrderTools.getSignType().trim());
		//支付人姓名
		///可为中文或英文字符
		String payerName="";
		if(useraccount.getLoginName()==null)
			payerName=useraccount.getPhone();
		else
			payerName=useraccount.getLoginName();
			getRequest().setAttribute("payerName",payerName);
		getRequest().setAttribute("payerContactType", BillOrderTools.getPayerContactType().trim());
		getRequest().setAttribute("payerContact",useraccount.getEmail()==null?"":useraccount.getEmail().trim());
		getRequest().setAttribute("orderId",getOrderId().trim());
		//订单金额
		///以分为单位，必须是整型数字
		///比方2，代表0.02元
		getRequest().setAttribute("orderAmount", (long)(Double.parseDouble(this.getAmount())*100));
		getRequest().setAttribute("orderTime", getOrderTime().trim());
		getRequest().setAttribute("productName", BillOrderTools.getProductName().trim());
		///可为空，非空时必须为数字
		getRequest().setAttribute("productNum",BillOrderTools.getProductNum().trim());
		getRequest().setAttribute("productId", BillOrderTools.getProductId().trim());
		getRequest().setAttribute("productDesc",BillOrderTools.getProductDesc().trim());
		//扩展字段1
		///在支付结束后原样返回给商户
		getRequest().setAttribute("ext1", BillOrderTools.getExt1().trim());
		getRequest().setAttribute("ext2", BillOrderTools.getExt2().trim());
		//支付方式.固定选择值
		///只能选择00、10、11、12、13、14
		getRequest().setAttribute("payType",BillOrderTools.getPayType().trim());
		getRequest().setAttribute("bankId", BillOrderTools.getBankId().trim());
		getRequest().setAttribute("redoFlag",BillOrderTools.getRedoFlag().trim());
		getRequest().setAttribute("pid", BillOrderTools.getPid().trim());
		getRequest().setAttribute("sentUrl",BillOrderTools.getSentUrl().trim());
		String signMsgVal="";
		//生成加密签名串
		///请务必按照如下顺序和规则组成加密串！
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"inputCharset" , BillOrderTools.getInputCharset().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"pageUrl" , BillOrderTools.getPageUrl().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"bgUrl" , BillOrderTools.getBgUrl().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"version" , BillOrderTools.getVersion().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"language" , BillOrderTools.getLanguage().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"signType" , BillOrderTools.getSignType().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"merchantAcctId" , BillOrderTools.getMerchantAcctId().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"payerName" , payerName.trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"payerContactType" , BillOrderTools.getPayerContactType().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"payerContact" , useraccount.getEmail()==null?"":useraccount.getEmail().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"orderId" ,getOrderId());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"orderAmount" ,(long)(Double.parseDouble(getAmount())*100)+"");
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"orderTime" , orderTime.trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"productName" , BillOrderTools.getProductName().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"productNum" , BillOrderTools.getProductNum().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"productId" , BillOrderTools.getProductId().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"productDesc" , BillOrderTools.getProductDesc().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"ext1" , BillOrderTools.getExt1().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"ext2" , BillOrderTools.getExt2().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"payType" , BillOrderTools.getPayType().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"bankId" , BillOrderTools.getBankId().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"redoFlag" , BillOrderTools.getRedoFlag().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"pid" , BillOrderTools.getPid().trim());
		signMsgVal=BillOrderTools.appendParam(signMsgVal,"key" , BillOrderTools.getKey().trim());
		String signMsg=MD5Util.md5Hex(signMsgVal.getBytes("gb2312")).toUpperCase();
		getRequest().setAttribute("signMsg", signMsg);
		return "success";
	}
	/**
	 * 快钱跳回来
	 * @throws UnsupportedEncodingException 
	 * @throws LotteryException 
	 */
	public String billReceive() throws UnsupportedEncodingException{
		String merchantAcctId=(String)getRequest().getParameter("merchantAcctId").trim();
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		//设置人民币网关密钥
		///区分大小写
		String key="ZUZNJB8MF63GA83J";

		//获取网关版本.固定值
		///快钱会根据版本号来调用对应的接口处理程序。
		///本代码版本号固定为v2.0
		String version=(String)getRequest().getParameter("version").trim();

		//获取语言种类.固定选择值。
		///只能选择1、2、3
		///1代表中文；2代表英文
		///默认值为1
		String language=(String)getRequest().getParameter("language").trim();

		//签名类型.固定值
		///1代表MD5签名
		///当前版本固定为1
		String signType=(String)getRequest().getParameter("signType").trim();

		//获取支付方式
		///值为：10、11、12、13、14
		///00：组合支付（网关支付页面显示快钱支持的各种支付方式，推荐使用）10：银行卡支付（网关支付页面只显示银行卡支付）.11：电话银行支付（网关支付页面只显示电话支付）.12：快钱账户支付（网关支付页面只显示快钱账户支付）.13：线下支付（网关支付页面只显示线下支付方式）.14：B2B支付（网关支付页面只显示B2B支付，但需要向快钱申请开通才能使用）
		String payType=(String)getRequest().getParameter("payType").trim();

		//获取银行代码
		///参见银行代码列表
		String bankId=(String)getRequest().getParameter("bankId").trim();

		//获取商户订单号
		String orderId=(String)getRequest().getParameter("orderId").trim();
//		http://localhost:8080/lottery/billReceive.jhtml?fee=55&bankDealId=110126936011&bankId=CMB&ext1=&payAmount=5500&dealId=5784610&orderTime=20110126173359&signMsg=F995F85CF2EB4989835547DF4277D641&payType=10&language=1&errCode=&version=v2.0&ext2=&signType=1&orderAmount=5500&orderId=99201101261733590001&dealTime=20110126173459&merchantAcctId=1001153656201&payResult=10
		//获取订单提交时间
		///获取商户提交订单时的时间.14位数字。年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
		///如：20080101010101
		String orderTime=(String)getRequest().getParameter("orderTime").trim();

		//获取原始订单金额
		///订单提交到快钱时的金额，单位为分。
		///比方2 ，代表0.02元
		String orderAmount=(String)getRequest().getParameter("orderAmount").trim();

		//获取快钱交易号
		///获取该交易在快钱的交易号
		String dealId=(String)getRequest().getParameter("dealId").trim();

		//获取银行交易号
		///如果使用银行卡支付时，在银行的交易号。如不是通过银行支付，则为空
		String bankDealId=(String)getRequest().getParameter("bankDealId").trim();

		//获取在快钱交易时间
		///14位数字。年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
		///如；20080101010101
		String dealTime=(String)getRequest().getParameter("dealTime").trim();

		//获取实际支付金额
		///单位为分
		///比方 2 ，代表0.02元
		String payAmount=(String)getRequest().getParameter("payAmount").trim();

		//获取交易手续费
		///单位为分
		///比方 2 ，代表0.02元
		String fee=(String)getRequest().getParameter("fee").trim();

		//获取扩展字段1
		String ext1=(String)getRequest().getParameter("ext1").trim();

		//获取扩展字段2
		String ext2=(String)getRequest().getParameter("ext2").trim();

		//获取处理结果
		///10代表 成功11代表 失败
		String payResult=(String)getRequest().getParameter("payResult").trim();

		//获取错误代码
		///详细见文档错误代码列表
		String errCode=(String)getRequest().getParameter("errCode").trim();

		//获取加密签名串
		String signMsg=(String)getRequest().getParameter("signMsg").trim();
		String merchantSignMsgVal="";
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"merchantAcctId",merchantAcctId);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"version",version);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"language",language);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"signType",signType);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"payType",payType);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"bankId",bankId);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"orderId",orderId);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"orderTime",orderTime);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"orderAmount",orderAmount);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"dealId",dealId);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"bankDealId",bankDealId);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"dealTime",dealTime);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"payAmount",payAmount);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"fee",fee);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"ext1",ext1);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"ext2",ext2);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"payResult",payResult);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"errCode",errCode);
		merchantSignMsgVal=BillOrderTools.appendParam(merchantSignMsgVal,"key",key);

	    String merchantSignMsg=MD5Util.md5Hex(merchantSignMsgVal.getBytes("gb2312")).toUpperCase();
	    BillOrderService billOrderService=ApplicationContextUtils.getService("billService",BillOrderService.class);
	    
	    
	    int rc=0;
	    String rtnStr="";
	    try {
	    	rc=billOrderService.processBILLOrder(orderId, dealId, fee, bankId, bankDealId, dealTime, payAmount, ext1, ext2, payResult, errCode, signMsg, merchantSignMsg);
	    	this.saveCurCustomer(userservice.getUserInfo(this.getCurCustomer().getMobilePhone()));
	    	if(rc>0)
	    		rtnStr="success";
	    	else
	    		rtnStr="fail";
	    } catch (LotteryException e) {
			e.getType();
			e.getMessage();
			rtnStr="error";
			e.printStackTrace();
		}
		return rtnStr;
	}
	
	public void billSuccess(){
		
	}
}
