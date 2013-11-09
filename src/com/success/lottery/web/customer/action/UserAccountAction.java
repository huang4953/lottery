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
	private String oldpwd;//旧密码
	private String newpwd;//新密码
	private String nickName;//昵称
	private String realName;//真实姓名
	private String address;//地址
	private String idcard;//身份证号码
	private String email;//eml地址
	private String mobilePhone;//电话
	private String qq;//QQ
	private String bankName;//开户行名称
	private String detailedBankName;//支行名称
	private String bankCardId;//卡号
	private int sex;//性别
	private String message;//异常信息
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
	 * 用户修改密码
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
			msg="原始密码不正确！！！";
		else
		{
			try {
				  flg=userservice.updateUserPasswordInfo(this.getNewpwd(),useraccount.getUserId());
			} catch (LotteryException e) {
				e.printStackTrace();
			}
			if(flg==1){
				msg="修改密码成功！！！";
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
				msg="修改密码失败！！！";
				//pzc add
				Map param = new HashMap<String, String>();
				param.put("userId", useraccount.getUserId()+"");
				param.put("userName", useraccount.getRealName());
				param.put("userKey", this.getRemoteIp());
				param.put("keyword1", useraccount.getMobilePhone());
				param.put("errorMessage", msg);
				OperatorLogger.log(21003, param);
			}
			//设置编码格式
		
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
		String msg="个人信息修改失败！！！";
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
			msg="个人信息修改成功！！！";
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
		//设置编码格式
			out.println(msg);
			out.close();
	}
	
	public String goSafetyData(){
		return "goSafetyData";
	}
	public void updateUserInfoBaise() throws IOException{
		String msg="基本资料修改失败！！！";
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		UserAccountModel useraccount=getCurCustomer();
		
		//设置编码格式
		this.getResponse().setContentType( "text/html;charset=GBK "); 
		PrintWriter out=this.getResponse().getWriter();
		int flg=0;
			try {
				flg=userservice.updateUserdetailbaise(useraccount.getUserId(), qq, URLDecoder.decode(getAddress(),"UTF-8"), getMobilePhone(), getSex());
			} catch (LotteryException e) {
					e.printStackTrace();
			}
			if(flg==1){
				msg="基本资料修改成功！！！";
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
	 * 绑定身份证号码
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
	//先绑定身份证后跳转到绑定银行卡
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
	//绑定银行卡
	public String udateBankCard(){
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		UserAccountModel useraccount=getCurCustomer();
		if(!useraccount.getPassword().equals(MD5.MD5Encode(this.oldpwd)))
		{
			this.setMessage("密码不正确");
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
		String msg="此邮箱已经注册过,请更换一个!!!";
		AccountService userservice =  ApplicationContextUtils.getService("accountService",AccountService.class);
		try {
			UserAccountModel useraccount=userservice.getUserInfo(this.getEmail());
			if(null==useraccount)
				msg="此邮箱可用！！！";
		} catch (LotteryException e) {
			msg="此邮箱可用！！！";
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
	 * 访问彩民修改信息修改密码页面
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
