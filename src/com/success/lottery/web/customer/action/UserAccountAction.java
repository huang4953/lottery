package com.success.lottery.web.customer.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.LibingUtils;
import com.success.utils.MD5;

public class UserAccountAction extends LotteryWebBaseActon{
	private String oldpwd;//������
	private String newpwd;//������
	private String nickName;//�ǳ�
	private String realName;//��ʵ����
	private String address;//��ַ
	private String idcard;//���֤����
	private String email;//eml��ַ
	private String mobilePhone;//�绰
	private String qq;//QQ
	private String bankName;//����������
	private String detailedBankName;//֧������
	private String bankCardId;//����
	private int sex;//�Ա�
	private String message;//�쳣��Ϣ
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getDetailedBankName() {
		return detailedBankName;
	}
	public void setDetailedBankName(String detailedBankName) {
		this.detailedBankName = detailedBankName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getOldpwd() {
		return oldpwd;
	}
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	/**
	 * �û��޸�����
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void updatePwdUserAccount() throws IOException{
		String msg="";
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		
		UserAccountModel useraccount=getCurCustomer();
		this.getResponse().setContentType( "text/html;charset=GBK "); 
		PrintWriter out=this.getResponse().getWriter();
		int flg=0;
		if(!MD5.MD5Encode(getOldpwd()).equals(useraccount.getPassword()))
			msg="ԭʼ���벻��ȷ������";
		else
		{
			try {
				  flg=userservice.updateUserPasswordInfo(this.getNewpwd(),useraccount.getUserId());
			} catch (LotteryException e) {
				e.printStackTrace();
			}
			if(flg==1){
				msg="�޸�����ɹ�������";
				//pzc add
				Map param = new HashMap<String, String>();
				param.put("userId", useraccount.getUserId()+"");
				param.put("userName", useraccount.getRealName());
				param.put("userKey", this.getRemoteIp());
				param.put("keyword1", useraccount.getMobilePhone());
				OperatorLogger.log(20003, param);
				this.saveCurCustomer(this.getCurCustomer());
			}
			else{
				msg="�޸�����ʧ�ܣ�����";
				//pzc add
				Map param = new HashMap<String, String>();
				param.put("userId", useraccount.getUserId()+"");
				param.put("userName", useraccount.getRealName());
				param.put("userKey", this.getRemoteIp());
				param.put("keyword1", useraccount.getMobilePhone());
				param.put("errorMessage", msg);
				OperatorLogger.log(21003, param);
			}
			//���ñ����ʽ
		
		}
			out.println(msg);
			out.close();
	}
	/**
	 * @throws IOException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void updateUserInfo() throws IOException{
		String msg="������Ϣ�޸�ʧ�ܣ�����";
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		UserAccountModel useraccount=getCurCustomer();
		this.getResponse().setContentType( "text/html;charset=GBK "); 
		PrintWriter out=this.getResponse().getWriter();
		int flg=0;
		System.out.println(getNickName());
		System.out.println(URLDecoder.decode(getNickName(),"UTF-8"));
		try {
			flg=userservice.updateUserdetailInfo(URLDecoder.decode(getNickName(),"UTF-8"),useraccount.getUserId(),URLDecoder.decode(getRealName(),"UTF-8"),URLDecoder.decode(getAddress(),"UTF-8"),getIdcard(),getEmail());
		} catch (LotteryException e) {
				e.printStackTrace();
		}
		if(flg==1){
			msg="������Ϣ�޸ĳɹ�������";
			//pzc add
			Map param = new HashMap<String, String>();
			param.put("userId", useraccount.getUserId()+"");
			param.put("userName", useraccount.getRealName());
			param.put("userKey", this.getRemoteIp());
			param.put("keyword1", useraccount.getMobilePhone());
			OperatorLogger.log(20004, param);
			this.saveCurCustomer(this.getCurCustomer());
		}else{
			//pzc add
			Map param = new HashMap<String, String>();
			param.put("userId", useraccount.getUserId()+"");
			param.put("userName", useraccount.getRealName());
			param.put("userKey", this.getRemoteIp());
			param.put("keyword1", useraccount.getMobilePhone());
			param.put("errorMessage", msg);
			OperatorLogger.log(21004, param);
		}
		//���ñ����ʽ
			out.println(msg);
			out.close();
	}
	
	public String goSafetyData(){
		return "goSafetyData";
	}
	public void updateUserInfoBaise() throws IOException{
		String msg="���������޸�ʧ�ܣ�����";
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		UserAccountModel useraccount=getCurCustomer();
		
		//���ñ����ʽ
		this.getResponse().setContentType( "text/html;charset=GBK "); 
		PrintWriter out=this.getResponse().getWriter();
		int flg=0;
			try {
				flg=userservice.updateUserdetailbaise(useraccount.getUserId(), qq, URLDecoder.decode(getAddress(),"UTF-8"), getMobilePhone(), getSex());
			} catch (LotteryException e) {
					e.printStackTrace();
			}
			if(flg==1){
				msg="���������޸ĳɹ�������";
				//pzc add
				Map param = new HashMap<String, String>();
				param.put("userId", useraccount.getUserId()+"");
				param.put("userName", useraccount.getRealName());
				param.put("userKey", this.getRemoteIp());
				param.put("keyword1", useraccount.getMobilePhone());
				OperatorLogger.log(20004, param);
				this.saveCurCustomer(this.getCurCustomer());
			}else{
				//pzc add
				Map param = new HashMap<String, String>();
				param.put("userId", useraccount.getUserId()+"");
				param.put("userName", useraccount.getRealName());
				param.put("userKey", this.getRemoteIp());
				param.put("keyword1", useraccount.getMobilePhone());
				param.put("errorMessage", msg);
				OperatorLogger.log(21004, param);
			}
			out.println(msg);
			out.close();
	}
	
	public String gobindingIdCard(){
		UserAccountModel useraccount=getCurCustomer();
		if(useraccount.getIdCard()!=null&&!"".equals(useraccount.getIdCard())&&useraccount.getRealName()!=null&&!"".equals(useraccount.getRealName()))
			return "successBinding";
		else
			return "gobindingIdCard";
	}
	public String gobindingBankCard(){
		UserAccountModel useraccount=getCurCustomer();
		if(useraccount.getIdCard()==null||"".equals(useraccount.getIdCard())||useraccount.getRealName()==null||"".equals(useraccount.getRealName()))
			return "gobindingIdCard";
		if(useraccount.getBankName()==null||useraccount.getBankCardId()==null||"".equals(useraccount.getBankName().trim())||"".equals(useraccount.getBankCardId().trim()))
		    return "gobindingBankCard";
		else
			return "successBinding";
		
	}
	/**
	 * �����֤����
	 * @return
	 */
	public String updateUserInfoIdCard(){
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		UserAccountModel useraccount=getCurCustomer();
		int flg=0;
		try {
			flg=userservice.updateUserInfoID(useraccount.getUserId(), this.getRealName(), this.getIdcard());
			if(flg==1)
				return "successBinding";
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.saveCurCustomer(this.getCurCustomer());
		return "gobindingIdCard";
	}
	//�Ȱ����֤����ת�������п�
	public String updateUserinforIdCardToBankCard(){
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		UserAccountModel useraccount=getCurCustomer();
		int flg=0;
		try {
			flg=userservice.updateUserInfoID(useraccount.getUserId(), this.getRealName(), this.getIdcard());
			if(flg==1)
				return "gobindingBankCard";
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			this.setMessage(e.getMessage());
		}
		this.saveCurCustomer(this.getCurCustomer());
		return "gobindingIdCard";
	}
	//�����п�
	public String udateBankCard(){
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		UserAccountModel useraccount=getCurCustomer();
		if(!useraccount.getPassword().equals(MD5.MD5Encode(this.oldpwd)))
		{
			this.setMessage("���벻��ȷ");
			return "gobindingBankCard";
		}
		int flg=0;
		
		try{
			flg=userservice.updateUserBankCard(useraccount.getUserId(),this.getBankName(),this.getDetailedBankName(), this.getBankCardId());
			this.saveCurCustomer(this.getCurCustomer());
			if(flg==1)
				return "successBinding";
		}catch(LotteryException e){
			this.setMessage(e.getMessage());
		}
		
		return "gobindingBankCard";
	}
	public void flgEml() throws IOException{
		String msg="�������Ѿ�ע���,�����һ��!!!";
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		try {
			UserAccountModel useraccount=userservice.getUserInfo(this.getEmail());
			if(null==useraccount)
				msg="��������ã�����";
		} catch (LotteryException e) {
			msg="��������ã�����";
		}
		this.getResponse().setContentType( "text/html;charset=GBK "); 
		PrintWriter out=this.getResponse().getWriter();
		out.print(msg);
		out.close();
	}
	public String gotest(){
		return "mytest";
	}
	public void test(){
		System.out.println(getEmail());
	}
	/**
	 * ���ʲ����޸���Ϣ�޸�����ҳ��
	 * @return
	 */
	public String updateUserInfoPage(){
		return "success";
	}

	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBankCardId() {
		return bankCardId;
	}
	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
