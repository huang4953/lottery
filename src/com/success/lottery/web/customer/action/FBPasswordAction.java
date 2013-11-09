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
		//�����ݿ������Ƿ������������
		
		if(null==this.getEmail()||"".equals(this.getEmail())){
			this.addFieldError("email", "���䲻��Ϊ�գ�����д�������");
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
				param.put("errorMessage", "�����ʼ�����ʧ��");
				this.setMsg("�����ʼ�����ʧ��");
				OperatorLogger.log(21002, param);
				this.setMsg(e.getMessage());
				e.printStackTrace();
			}
			if (null==customer) {
				this.addFieldError("email", "�Բ���!�㻹û��ע�ᣡ������ע��");
				Map param = new HashMap<String, String>();
				param.put("userId", 0+"");
				param.put("userKey", this.getRemoteIp());
				this.setMsg("�Բ���!�˻�û��ע�ᣡ");
				param.put("errorMessage", "�Բ���!�㻹û��ע�ᣡ������ע��");
				OperatorLogger.log(21001, param);
			}else{
				//����6-15λ��������
				int count = new Random().nextInt(16);
				if(count<6){
					count+=6;
				}
				String password = RandomStringUtils.random(count, true, true);
				
			
				//�����ɵ������뱣�浽���ݿ���
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
						this.addFieldError("email", "��ϲ��ɹ��޸�����!���������Ѿ����͵��������У����¼����������");
					}catch(LotteryException e){
						this.addFieldError("email", "������������ʧ�ܣ�ʧ��ԭ��" + e.toString());
						Map param = new HashMap<String, String>();
						param.put("userId", customer.getUserId()+"");
						param.put("userName", customer.getRealName());
						param.put("userKey", this.getRemoteIp());
						param.put("keyword1", customer.getMobilePhone());
						param.put("keyword2", customer.getEmail());
						param.put("errorMessage", "������������ʧ�ܣ�ʧ��ԭ��" + e.toString());
						OperatorLogger.log(21001, param);
						e.printStackTrace();
					}
//					//�����ɵ������뷢�͵��û��ʼ���
//					Properties props=new Properties();//Ҳ����Properties props = System.getProperties();
//					//�洢�����ʼ�����������Ϣ
//					props.put(AutoProperties.getString("mailsmtp", null, resource), 
//							AutoProperties.getString("mailnet", null, resource));
//					//ͬʱͨ����֤
//					props.put(AutoProperties.getString("mailauth", null, resource), 
//							AutoProperties.getString("mailbool", null, resource));
//					Authenticator au = new SmtpAuthenticator(AutoProperties.getString("mailuser", null, resource),
//							AutoProperties.getString("sendPassword", null, resource));
//					Session s=Session.getInstance(props,au);//���������½�һ���ʼ��Ự
//					s.setDebug(true);
//					MimeMessage message=new MimeMessage(s);//���ʼ��Ự�½�һ����Ϣ����
//					
//					InternetAddress from;
//					Transport transport = null;
//					try {
//						from = new InternetAddress(AutoProperties.getString("sendEmail", null, resource));
//						message.setFrom(from);//���÷�����
//						InternetAddress to=new InternetAddress(this.getEmail());
//						message.setRecipient(Message.RecipientType.TO,to);//�����ռ���,���������������ΪTO
//						message.setSubject(AutoProperties.getString("emailtitle", null, resource));//��������
//						BodyPart mdp=new MimeBodyPart();//�½�һ������ż����ݵ�BodyPart����
//						String tcontent=AutoProperties.getString("emailName", null, resource)
//						+customer.getLoginName()+AutoProperties.getString("emailPassword", null, resource)
//						+password+AutoProperties.getString("emailContent", null, resource);
//						mdp.setContent(tcontent,AutoProperties.getString("textcontent", null, resource));//��BodyPart�����������ݺ͸�ʽ/���뷽ʽ
//						Multipart mm=new MimeMultipart();//�½�һ��MimeMultipart�����������BodyPart��
//						mm.addBodyPart(mdp);//��BodyPart���뵽MimeMultipart������(���Լ�����BodyPart)
//						message.setContent(mm);//��mm��Ϊ��Ϣ���������
//						message.setSentDate(new Date());//���÷���ʱ��
//						message.saveChanges();//�洢�ʼ���Ϣ
//						transport=s.getTransport(AutoProperties.getString("typest", null, resource));
//						transport.connect(AutoProperties.getString("mailcom", null, resource),
//								AutoProperties.getString("mailuser", null, resource),
//								AutoProperties.getString("sendPassword", null, resource));//��smtp��ʽ��¼����
//						
//						System.out.println(AutoProperties.getString("mailcom", null, resource) + "," +
//									AutoProperties.getString("mailuser", null, resource) + "," +
//									AutoProperties.getString("sendPassword", null, resource));
//						
//						transport.sendMessage(message,message.getAllRecipients());//�����ʼ�,���еڶ�������������
//						transport.close();
//					} catch (Exception e) {
//						this.addFieldError("email", "������������ʧ�ܣ�ʧ��ԭ��" + e.toString());
//						e.printStackTrace();
//					}
//					this.addFieldError("email", "��ϲ��ɹ��޸�����!���������Ѿ����͵��������У����¼����������");
				}else{
					Map param = new HashMap<String, String>();
					param.put("userId", customer.getUserId()+"");
					param.put("userName", customer.getRealName());
					param.put("userKey", this.getRemoteIp());
					param.put("keyword1", customer.getMobilePhone());
					param.put("keyword2", customer.getEmail());
					param.put("errorMessage", "������������ʧ��");
					OperatorLogger.log(21001, param);
					this.addFieldError("email", "������������ʧ��");
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
