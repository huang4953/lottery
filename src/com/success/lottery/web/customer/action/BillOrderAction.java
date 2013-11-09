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
		long amount=(long)(Double.parseDouble(this.getAmount())*100);//��ֵ���
		String json="fail";//Ĭ��Ϊʧ��
		try {
			json=billOrderService.addBillOrder(useraccount.getUserId(),useraccount.getLoginName(), amount);
		} catch (LotteryException e) {
		   //�ǲ�  ���� 
			e.printStackTrace();
		}finally{
			//�ɹ����� �����ţ�ʧ�ܷ���fail
			out.println(json);
		}
		out.close();
	}
	/*
	 * ȥ��Ǯ֧��
	 */
	public String BillPayMoney() throws UnsupportedEncodingException{
		UserAccountModel useraccount=getCurCustomer();
		getRequest().setAttribute("merchantAcctId",BillOrderTools.getMerchantAcctId().trim());//����������˻���
		getRequest().setAttribute("key",BillOrderTools.getKey().trim());//�����������Կ ���ִ�Сд.
		getRequest().setAttribute("inputCharset", BillOrderTools.getInputCharset().trim());//�ַ���.�̶�ѡ��ֵ����Ϊ�ա�1����UTF-8; 2����GBK; 3����gb2312
		//����֧�������ҳ���ַ.��[bgUrl]����ͬʱΪ�ա������Ǿ��Ե�ַ��
		///���[bgUrl]Ϊ�գ���Ǯ��֧�����Post��[pageUrl]��Ӧ�ĵ�ַ��
		///���[bgUrl]��Ϊ�գ�����[bgUrl]ҳ��ָ����<redirecturl>��ַ��Ϊ�գ���ת��<redirecturl>��Ӧ�ĵ�ַ
		getRequest().setAttribute("pageUrl",BillOrderTools.getPageUrl().trim());
		//����������֧������ĺ�̨��ַ.��[pageUrl]����ͬʱΪ�ա������Ǿ��Ե�ַ��
		///��Ǯͨ�����������ӵķ�ʽ�����׽�����͵�[bgUrl]��Ӧ��ҳ���ַ�����̻�������ɺ������<result>���Ϊ1��ҳ���ת��<redirecturl>��Ӧ�ĵ�ַ��
		///�����Ǯδ���յ�<redirecturl>��Ӧ�ĵ�ַ����Ǯ����֧�����post��[pageUrl]��Ӧ��ҳ�档
		getRequest().setAttribute("bgUrl", BillOrderTools.getBgUrl().trim());
		//���ذ汾.�̶�ֵ
		///��Ǯ����ݰ汾�������ö�Ӧ�Ľӿڴ������
		///������汾�Ź̶�Ϊv2.0
		getRequest().setAttribute("version",BillOrderTools.getVersion().trim());
		//��������.�̶�ѡ��ֵ��
		///ֻ��ѡ��1��2��3
		///1�������ģ�2����Ӣ��
		///Ĭ��ֵΪ1
		getRequest().setAttribute("language",BillOrderTools.getLanguage().trim());
		//ǩ������.�̶�ֵ
		///1����MD5ǩ��
		///��ǰ�汾�̶�Ϊ1
		getRequest().setAttribute("signType",BillOrderTools.getSignType().trim());
		//֧��������
		///��Ϊ���Ļ�Ӣ���ַ�
		String payerName="";
		if(useraccount.getLoginName()==null)
			payerName=useraccount.getPhone();
		else
			payerName=useraccount.getLoginName();
			getRequest().setAttribute("payerName",payerName);
		getRequest().setAttribute("payerContactType", BillOrderTools.getPayerContactType().trim());
		getRequest().setAttribute("payerContact",useraccount.getEmail()==null?"":useraccount.getEmail().trim());
		getRequest().setAttribute("orderId",getOrderId().trim());
		//�������
		///�Է�Ϊ��λ����������������
		///�ȷ�2������0.02Ԫ
		getRequest().setAttribute("orderAmount", (long)(Double.parseDouble(this.getAmount())*100));
		getRequest().setAttribute("orderTime", getOrderTime().trim());
		getRequest().setAttribute("productName", BillOrderTools.getProductName().trim());
		///��Ϊ�գ��ǿ�ʱ����Ϊ����
		getRequest().setAttribute("productNum",BillOrderTools.getProductNum().trim());
		getRequest().setAttribute("productId", BillOrderTools.getProductId().trim());
		getRequest().setAttribute("productDesc",BillOrderTools.getProductDesc().trim());
		//��չ�ֶ�1
		///��֧��������ԭ�����ظ��̻�
		getRequest().setAttribute("ext1", BillOrderTools.getExt1().trim());
		getRequest().setAttribute("ext2", BillOrderTools.getExt2().trim());
		//֧����ʽ.�̶�ѡ��ֵ
		///ֻ��ѡ��00��10��11��12��13��14
		getRequest().setAttribute("payType",BillOrderTools.getPayType().trim());
		getRequest().setAttribute("bankId", BillOrderTools.getBankId().trim());
		getRequest().setAttribute("redoFlag",BillOrderTools.getRedoFlag().trim());
		getRequest().setAttribute("pid", BillOrderTools.getPid().trim());
		getRequest().setAttribute("sentUrl",BillOrderTools.getSentUrl().trim());
		String signMsgVal="";
		//���ɼ���ǩ����
		///����ذ�������˳��͹�����ɼ��ܴ���
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
	 * ��Ǯ������
	 * @throws UnsupportedEncodingException 
	 * @throws LotteryException 
	 */
	public String billReceive() throws UnsupportedEncodingException{
		String merchantAcctId=(String)getRequest().getParameter("merchantAcctId").trim();
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		//���������������Կ
		///���ִ�Сд
		String key="ZUZNJB8MF63GA83J";

		//��ȡ���ذ汾.�̶�ֵ
		///��Ǯ����ݰ汾�������ö�Ӧ�Ľӿڴ������
		///������汾�Ź̶�Ϊv2.0
		String version=(String)getRequest().getParameter("version").trim();

		//��ȡ��������.�̶�ѡ��ֵ��
		///ֻ��ѡ��1��2��3
		///1�������ģ�2����Ӣ��
		///Ĭ��ֵΪ1
		String language=(String)getRequest().getParameter("language").trim();

		//ǩ������.�̶�ֵ
		///1����MD5ǩ��
		///��ǰ�汾�̶�Ϊ1
		String signType=(String)getRequest().getParameter("signType").trim();

		//��ȡ֧����ʽ
		///ֵΪ��10��11��12��13��14
		///00�����֧��������֧��ҳ����ʾ��Ǯ֧�ֵĸ���֧����ʽ���Ƽ�ʹ�ã�10�����п�֧��������֧��ҳ��ֻ��ʾ���п�֧����.11���绰����֧��������֧��ҳ��ֻ��ʾ�绰֧����.12����Ǯ�˻�֧��������֧��ҳ��ֻ��ʾ��Ǯ�˻�֧����.13������֧��������֧��ҳ��ֻ��ʾ����֧����ʽ��.14��B2B֧��������֧��ҳ��ֻ��ʾB2B֧��������Ҫ���Ǯ���뿪ͨ����ʹ�ã�
		String payType=(String)getRequest().getParameter("payType").trim();

		//��ȡ���д���
		///�μ����д����б�
		String bankId=(String)getRequest().getParameter("bankId").trim();

		//��ȡ�̻�������
		String orderId=(String)getRequest().getParameter("orderId").trim();
//		http://localhost:8080/lottery/billReceive.jhtml?fee=55&bankDealId=110126936011&bankId=CMB&ext1=&payAmount=5500&dealId=5784610&orderTime=20110126173359&signMsg=F995F85CF2EB4989835547DF4277D641&payType=10&language=1&errCode=&version=v2.0&ext2=&signType=1&orderAmount=5500&orderId=99201101261733590001&dealTime=20110126173459&merchantAcctId=1001153656201&payResult=10
		//��ȡ�����ύʱ��
		///��ȡ�̻��ύ����ʱ��ʱ��.14λ���֡���[4λ]��[2λ]��[2λ]ʱ[2λ]��[2λ]��[2λ]
		///�磺20080101010101
		String orderTime=(String)getRequest().getParameter("orderTime").trim();

		//��ȡԭʼ�������
		///�����ύ����Ǯʱ�Ľ���λΪ�֡�
		///�ȷ�2 ������0.02Ԫ
		String orderAmount=(String)getRequest().getParameter("orderAmount").trim();

		//��ȡ��Ǯ���׺�
		///��ȡ�ý����ڿ�Ǯ�Ľ��׺�
		String dealId=(String)getRequest().getParameter("dealId").trim();

		//��ȡ���н��׺�
		///���ʹ�����п�֧��ʱ�������еĽ��׺š��粻��ͨ������֧������Ϊ��
		String bankDealId=(String)getRequest().getParameter("bankDealId").trim();

		//��ȡ�ڿ�Ǯ����ʱ��
		///14λ���֡���[4λ]��[2λ]��[2λ]ʱ[2λ]��[2λ]��[2λ]
		///�磻20080101010101
		String dealTime=(String)getRequest().getParameter("dealTime").trim();

		//��ȡʵ��֧�����
		///��λΪ��
		///�ȷ� 2 ������0.02Ԫ
		String payAmount=(String)getRequest().getParameter("payAmount").trim();

		//��ȡ����������
		///��λΪ��
		///�ȷ� 2 ������0.02Ԫ
		String fee=(String)getRequest().getParameter("fee").trim();

		//��ȡ��չ�ֶ�1
		String ext1=(String)getRequest().getParameter("ext1").trim();

		//��ȡ��չ�ֶ�2
		String ext2=(String)getRequest().getParameter("ext2").trim();

		//��ȡ������
		///10���� �ɹ�11���� ʧ��
		String payResult=(String)getRequest().getParameter("payResult").trim();

		//��ȡ�������
		///��ϸ���ĵ���������б�
		String errCode=(String)getRequest().getParameter("errCode").trim();

		//��ȡ����ǩ����
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
