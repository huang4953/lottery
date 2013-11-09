package com.success.lottery.web.customer.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.web.trade.action.supper.LotteryWebBaseActon;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.LibingUtils;

public class FBPasswordAction extends LotteryWebBaseActon{
	private String msg;//
	private String email;
	private static String		resource	= "com.success.lottery.account.service.fbpassword";
	private UserAccountModel customer =  null;
	
	public String findBackPassword(){
		return "findBackPassword";
	}
	public String findPassword(){
		return null; 
	}
	
	@SuppressWarnings("unchecked")
	public void validateFindPassword(){
		//在数据库中找是否含有输入的邮箱
		
		if(null==this.getEmail()||"".equals(this.getEmail())){
			this.addFieldError("email", "邮箱不能为空！请填写你的邮箱");
		}else{
			AccountService acntService = ApplicationContextUtils.getService("accountService", AccountService.class);
			try {
				customer = acntService.getUserInfo(this.getEmail());
				Map param = new HashMap<String, String>();
				param.put("userId", 0+"");
				param.put("userName", customer.getRealName());
				param.put("userKey", this.getRemoteIp());
				param.put("keyword2", this.getEmail());
				OperatorLogger.log(20002, param);
			} catch (LotteryException e) {
				Map param = new HashMap<String, String>();
				param.put("userId", 0+"");
				param.put("userKey", this.getRemoteIp());
				param.put("errorMessage", "发送邮件关联失败");
				this.setMsg("发送邮件关联失败");
				OperatorLogger.log(21002, param);
				this.setMsg(e.getMessage());
				e.printStackTrace();
			}
			if (null==customer) {
				this.addFieldError("email", "对不起!你还没有注册！请你先注册");
				Map param = new HashMap<String, String>();
				param.put("userId", 0+"");
				param.put("userKey", this.getRemoteIp());
				this.setMsg("对不起!此还没有注册！");
				param.put("errorMessage", "对不起!你还没有注册！请你先注册");
				OperatorLogger.log(21001, param);
			}else{
				//生成6-15位的新密码
				int count = new Random().nextInt(16);
				if(count<6){
					count+=6;
				}
				String password = RandomStringUtils.random(count, true, true);
				
			
				//将生成的新密码保存到数据库中
				int pwdint = 0;
				try {
					pwdint = acntService.updateUserPasswordInfo(password, customer.getUserId());
				} catch (LotteryException e) {
					e.printStackTrace();
				}
				
				if(pwdint==1){
					System.out.println(1111111);
					try{
						acntService.mailFindPassword(this.getEmail(), customer.getLoginName(), password);
						Map param = new HashMap<String, String>();
						param.put("userId", customer.getUserId()+"");
						param.put("userName", customer.getRealName());
						param.put("userKey", this.getRemoteIp());
						param.put("keyword1", customer.getMobilePhone());
						param.put("keyword2", customer.getEmail());
						OperatorLogger.log(20001, param);
						this.addFieldError("email", "恭喜你成功修改密码!新置密码已经发送到你邮箱中，请登录你的邮箱查收");
					}catch(LotteryException e){
						this.addFieldError("email", "重新设置密码失败，失败原因：" + e.toString());
						Map param = new HashMap<String, String>();
						param.put("userId", customer.getUserId()+"");
						param.put("userName", customer.getRealName());
						param.put("userKey", this.getRemoteIp());
						param.put("keyword1", customer.getMobilePhone());
						param.put("keyword2", customer.getEmail());
						param.put("errorMessage", "重新设置密码失败，失败原因：" + e.toString());
						OperatorLogger.log(21001, param);
						e.printStackTrace();
					}
//					//将生成的新密码发送到用户邮件中
//					Properties props=new Properties();//也可用Properties props = System.getProperties();
//					//存储发送邮件服务器的信息
//					props.put(AutoProperties.getString("mailsmtp", null, resource), 
//							AutoProperties.getString("mailnet", null, resource));
//					//同时通过验证
//					props.put(AutoProperties.getString("mailauth", null, resource), 
//							AutoProperties.getString("mailbool", null, resource));
//					Authenticator au = new SmtpAuthenticator(AutoProperties.getString("mailuser", null, resource),
//							AutoProperties.getString("sendPassword", null, resource));
//					Session s=Session.getInstance(props,au);//根据属性新建一个邮件会话
//					s.setDebug(true);
//					MimeMessage message=new MimeMessage(s);//由邮件会话新建一个消息对象
//					
//					InternetAddress from;
//					Transport transport = null;
//					try {
//						from = new InternetAddress(AutoProperties.getString("sendEmail", null, resource));
//						message.setFrom(from);//设置发件人
//						InternetAddress to=new InternetAddress(this.getEmail());
//						message.setRecipient(Message.RecipientType.TO,to);//设置收件人,并设置其接收类型为TO
//						message.setSubject(AutoProperties.getString("emailtitle", null, resource));//设置主题
//						BodyPart mdp=new MimeBodyPart();//新建一个存放信件内容的BodyPart对象
//						String tcontent=AutoProperties.getString("emailName", null, resource)
//						+customer.getLoginName()+AutoProperties.getString("emailPassword", null, resource)
//						+password+AutoProperties.getString("emailContent", null, resource);
//						mdp.setContent(tcontent,AutoProperties.getString("textcontent", null, resource));//给BodyPart对象设置内容和格式/编码方式
//						Multipart mm=new MimeMultipart();//新建一个MimeMultipart对象用来存放BodyPart对
//						mm.addBodyPart(mdp);//将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
//						message.setContent(mm);//把mm作为消息对象的内容
//						message.setSentDate(new Date());//设置发信时间
//						message.saveChanges();//存储邮件信息
//						transport=s.getTransport(AutoProperties.getString("typest", null, resource));
//						transport.connect(AutoProperties.getString("mailcom", null, resource),
//								AutoProperties.getString("mailuser", null, resource),
//								AutoProperties.getString("sendPassword", null, resource));//以smtp方式登录邮箱
//						
//						System.out.println(AutoProperties.getString("mailcom", null, resource) + "," +
//									AutoProperties.getString("mailuser", null, resource) + "," +
//									AutoProperties.getString("sendPassword", null, resource));
//						
//						transport.sendMessage(message,message.getAllRecipients());//发送邮件,其中第二个参数是所有
//						transport.close();
//					} catch (Exception e) {
//						this.addFieldError("email", "重新设置密码失败，失败原因：" + e.toString());
//						e.printStackTrace();
//					}
//					this.addFieldError("email", "恭喜你成功修改密码!新置密码已经发送到你邮箱中，请登录你的邮箱查收");
				}else{
					Map param = new HashMap<String, String>();
					param.put("userId", customer.getUserId()+"");
					param.put("userName", customer.getRealName());
					param.put("userKey", this.getRemoteIp());
					param.put("keyword1", customer.getMobilePhone());
					param.put("keyword2", customer.getEmail());
					param.put("errorMessage", "重新设置密码失败");
					OperatorLogger.log(21001, param);
					this.addFieldError("email", "重新设置密码失败");
				}
				
			}
		}
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
