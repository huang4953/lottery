package com.success.lottery.util;

import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends javax.mail.Authenticator {
	    private String strUser;
	    private String strPwd;
	    public MailAuthenticator(String user, String password) {
	      this.strUser = user;
	      this.strPwd = password;
	    }

	    protected PasswordAuthentication getPasswordAuthentication() {
	      return new PasswordAuthentication(strUser, strPwd);
	    }



}
