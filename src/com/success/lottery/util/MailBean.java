package com.success.lottery.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.utils.AutoProperties;


public class MailBean {
	/**
	 * �����ʼ�����
	 * @param toAdderss �����ʼ��˵�ַ
	 * @param msg ��������
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public void sendMail(String toAdderss,String msg) throws AddressException, MessagingException{
		// Get system properties
	    Properties props = System.getProperties();

	    // Setup mail server
	    String fileAdderss="com.success.lottery.util.mialProperties";
	     System.out.println(AutoProperties.getString("mailStmp", "smtp.163.com",fileAdderss));
	    props.put("mail.smtp.host", AutoProperties.getString("mailStmp", "smtp.163.com",fileAdderss));

	    // Get session
	    props.put("mail.smtp.auth", "true"); //��������ͨ����֤

	    MailAuthenticator myauth = new MailAuthenticator(AutoProperties.getString("mailName", "",fileAdderss), AutoProperties.getString("mailPassword", "",fileAdderss));
	    
	    System.out.println(AutoProperties.getString("mailName", "smtp.163.com",fileAdderss));
	    
	    System.out.println(AutoProperties.getString("mailPassword", "",fileAdderss));
	    
	    Session session = Session.getDefaultInstance(props, myauth);
//	    session.setDebug(true);

	    // Define message
	    MimeMessage message = new MimeMessage(session);

	    // Set the from address
	    message.setFrom(new InternetAddress(AutoProperties.getString("mailName", "",fileAdderss)));

	    // Set the to address
	    message.addRecipient(Message.RecipientType.TO,new InternetAddress(toAdderss));

	    // ����
	    message.setSubject(AutoProperties.getString("mailTitle", "128��ע�ἤ��",fileAdderss));
	    // ����
	    message.setText(msg);
	    message.saveChanges();
	    Transport.send(message);
	    System.out.println("�ҳɹ��ˣ�����");
	      
	}
}
