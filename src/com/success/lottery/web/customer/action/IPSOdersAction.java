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
 * @description  �û���ֵ
 *
 */
@SuppressWarnings("serial")
public class IPSOdersAction extends LotteryWebBaseActon{
	private String amountf;
	private String billno;
	private String Currency_type;//����
	private String amount;//���
	private String date;//����
	private String succ;//
	private String msg;//��Ϣ
	private String attach;//�̻��������ݰ�
	private String ipsbillno;//IPS�������
	private String retencodetype;//����
	private String signature;//Ч����
	private String ipsbanktime;//����ʱ��

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
		//ģ���¼
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
	 * �����IPSOrder ����ajax����
	 * @throws IOException 
	 */
	public void addIPSOrder() throws IOException{
		IPSOrderService	 ipsoderservice=ApplicationContextUtils.getService("ipsService",IPSOrderService.class);
		UserAccountModel useraccount=getCurCustomer();
		PrintWriter out = super.getResponse().getWriter();
		int amount=(int)(Double.parseDouble(getAmountf())*100);//��ֵ���
		String Billno="fail";//Ĭ��Ϊʧ��
		try {
			Billno=ipsoderservice.addIPSOrder(useraccount.getUserId(), amount,useraccount.getMobilePhone()).trim();
		} catch (LotteryException e) {
		   //�ǲ�  ���� 
			e.printStackTrace();
		}finally{
			//�ɹ����� �����ţ�ʧ�ܷ���fail
			out.println(Billno);
		}
		out.close();
	}
	/**
	 * ȥ��Ѷ֧��
	 */
	public String IPSPayMoney(){
		UserAccountModel useraccount=getCurCustomer();
		getRequest().setAttribute("Mer_code",IPSTools.getMer_Code());//�̻���
		getRequest().setAttribute("Billno",getBillno().trim());//�̻��������
		DecimalFormat currentNumberFormat=new DecimalFormat("#0.00"); 
		String Amount = currentNumberFormat.format(Double.parseDouble(getAmountf().trim()));
		getRequest().setAttribute("Amount",Amount);//�������(����2λС��)
		String path = getRequest().getContextPath();
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+path+"/";
		//��������
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		java.util.Date currentTime = new java.util.Date();//�õ���ǰϵͳʱ�� 
		String date = formatter.format(currentTime); //������ʱ���ʽ��
		getRequest().setAttribute("Date",date);//��������
		getRequest().setAttribute("Currency_Type",IPSTools.getCurrency_Type());//����
		getRequest().setAttribute("Gateway_Type",IPSTools.getGateway_Type());//֧������
		getRequest().setAttribute("Lang",IPSTools.getLang());//����
		getRequest().setAttribute("Merchanturl", basePath + IPSTools.getMerchanturl());//֧������ɹ����ص��̻�URL
		getRequest().setAttribute("FailUrl",basePath + IPSTools.getFailUrl());////֧�����ʧ�ܷ��ص��̻�URL
		getRequest().setAttribute("ErrorUrl",basePath + IPSTools.getErrorUrl());//֧��������󷵻ص��̻�URL
		getRequest().setAttribute("Attach",useraccount.getMobilePhone());//�̻����ݰ�
		getRequest().setAttribute("DispAmount",getAmountf().trim());//��ʾ���
		getRequest().setAttribute("OrderEncodeType",IPSTools.getOrderEncodeType());//����֧���ӿڼ��ܷ�ʽ
		getRequest().setAttribute("RetEncodeType",IPSTools.getRetEncodeType());//���׷��ؽӿڼ��ܷ�ʽ 
		getRequest().setAttribute("Rettype",IPSTools.getRettype());//���ط�ʽ
		getRequest().setAttribute("ServerUrl",basePath + IPSTools.getServerUrl());//Server to Server ����ҳ��URL
		getRequest().setAttribute("SignMD5",IPSTools.getSignMD5(getBillno().trim(),Amount.trim(), date.trim()));//����֧���ӿڵ�Md5ժҪ��ԭ��=������+���+����+֧������+�̻�֤�� 
		getRequest().setAttribute("postUrl",IPSTools.getPostUrl());//�����ύ��IPS��·��
		return "pagMoney";
	}
	/**
	 * ��Ѷ֧��_֧�����ʧ��
	 */
	public String payFail(){
		return  "pagMoney";
	}
	/**
	 * ��Ѷ֧��_֧������ɹ�
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
	 * ��Ѷ֧��_֧���������
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
