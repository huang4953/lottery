package com.success.lottery.account.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.success.lottery.account.dao.AccountTransactionDAO;
import com.success.lottery.account.dao.UserAccountDAO;
import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.model.UserPointTrans;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.util.AreaMapTools;
import com.success.utils.AutoProperties;
import com.success.utils.MD5;
public class AccountServiceImpl implements AccountService{

	private Log						logger	= LogFactory.getLog("com.success.lottery.account.service.impl.AccountServiceImpl");
	private AccountTransactionDAO	accountTransactionDAO;
	private UserAccountDAO			userAccountDAO;

	private JavaMailSender mailSender;
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public AccountTransactionDAO getAccountTransactionDAO(){
		return accountTransactionDAO;
	}

	public void setAccountTransactionDAO(AccountTransactionDAO accountTransactionDAO){
		this.accountTransactionDAO = accountTransactionDAO;
	}

	public UserAccountDAO getUserAccountDAO(){
		return userAccountDAO;
	}

	public void setUserAccountDAO(UserAccountDAO userAccountDAO){
		this.userAccountDAO = userAccountDAO;
	}

	@Override
	public UserAccountModel registerBySMS(String mobile, String password, String loginName) throws LotteryException{
		if(StringUtils.isBlank(mobile)){
			throw new LotteryException(AccountService.MOBILEISNULL, AccountService.MOBILEISNULL_STR);
		}
		if(StringUtils.isBlank(password)){
			password = mobile;
		}
		if(StringUtils.isBlank(loginName)){
			loginName = mobile;
		}
		UserAccountModel user = null;
		try{
			user = userAccountDAO.selectUserInfo(-1, mobile);
			if(user == null){
				user = new UserAccountModel();
				user.setLoginName(loginName);
				user.setMobilePhone(mobile);
				user.setNickName(mobile);
				user.setUserLevel(0x00000000 | 0x00000001);
				user.setBindMobileFlag(true);
				user.setPassword(MD5.MD5Encode(password));
				String areaCode = AreaMapTools.getAreaCode(mobile);
				user.setAreaCode(areaCode);
				try{
					return userAccountDAO.addUser(user);
				}catch(Exception e){
					logger.error("addUser exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
					throw new LotteryException(AccountService.ADDUSEREXCEPTION, AccountService.ADDUSEREXCEPTION_STR);
				}
			}else{
				if(user.isBindMobileFlag()){
					throw new LotteryException(AccountService.USERISEXIST, AccountService.USERISEXIST_STR);
				}
				try{
					user.setBindMobileFlag(true);
					int rc = userAccountDAO.setBindUserMobileFlag(user.getUserId(), true, user.getUserLevel() | 0x00000001);
					if(rc != 1){
						throw new LotteryException(AccountService.NOTUPDATEUSERINFO, AccountService.NOTUPDATEUSERINFO_STR + "：" + rc);
					}
				}catch(Exception e){
					logger.error("setBindUserMobileFlag exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
					throw new LotteryException(AccountService.ADDUSEREXCEPTION, AccountService.ADDUSEREXCEPTION_STR);
				}
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("checkByIdentifier exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.USERREGISTEREXCEPTION, AccountService.USERREGISTEREXCEPTION_STR);
		}
		return user;
	}

	@Override
	public UserAccountModel registerBySMS(String mobile, String password) throws LotteryException{
		return registerBySMS(mobile, password, null);
	}

	@Override
	public UserAccountModel registerBySMS(String mobile) throws LotteryException{
		return registerBySMS(mobile, null, null);
	}
	
	@Override
	public UserAccountModel registerByWeb(String loginName, String mobilePhone, String nickName, String password, String email, String realName, String idCard) throws LotteryException{
		if(StringUtils.isBlank(loginName)){
			throw new LotteryException(AccountService.LOGINNAMEISNULL, AccountService.LOGINNAMEISNULL_STR);
		}
		
		if(StringUtils.isBlank(password)){
			throw new LotteryException(AccountService.PASSWORDISNULL, AccountService.PASSWORDISNULL_STR);
		}
		
		if(StringUtils.isBlank(email)){
			throw new LotteryException(AccountService.EMAILISNULL, AccountService.EMAILISNULL_STR);
		}
		
		try{
			int rc = userAccountDAO.checkByIdentifier(loginName, mobilePhone, email);
			if(rc > 0){
				throw new LotteryException(AccountService.USERISEXIST, AccountService.USERISEXIST_STR);
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("checkByIdentifier exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.USERREGISTEREXCEPTION, AccountService.USERREGISTEREXCEPTION_STR + e.toString());
		}
		UserAccountModel user = new UserAccountModel();
		user.setLoginName(loginName);
		user.setMobilePhone(mobilePhone);
		user.setEmail(email);
		user.setNickName(nickName);
		user.setPassword(MD5.MD5Encode(password));
		user.setRealName(realName);
		user.setIdCard(idCard);
		//String areaCode = AreaMapTools.getAreaCode(mobilePhone);
		//user.setAreaCode(areaCode);
		user.setStatus(2);
		try{
			return userAccountDAO.addUser(user);
		}catch(Exception e){
			logger.error("addUser exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.ADDUSEREXCEPTION, AccountService.ADDUSEREXCEPTION_STR + e.toString());
		}
	}

	@Override
	public UserAccountModel registerByWeb(String loginName, String mobilePhone, String nickName, String password, String email) throws LotteryException{
		if(StringUtils.isBlank(loginName))
		{
			throw new LotteryException(AccountService.USERNAMENULL,AccountService.USERNAMENULL_STR);
		}
		if(StringUtils.isBlank(mobilePhone))
			throw new LotteryException(AccountService.MOBILEISNULL,AccountService.MOBILEISNULL_STR);
		if(StringUtils.isBlank(password)){
			throw new LotteryException(AccountService.PASSWORDISNULL, AccountService.PASSWORDISNULL_STR);
		}
		if(StringUtils.isBlank(email)){
			throw new LotteryException(AccountService.EMAILNULL,AccountService.EMAILNULL_STR);
		}
		
		Pattern pattern=Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher matcher=pattern.matcher(email);
		if(!matcher.matches())
		{
			throw new LotteryException(AccountService.EMLVALIDATA,AccountService.EMLVALIDATA_STR);
		}
		if(this.userAccountDAO.selectCountByIdentifier2(loginName)>0)
			throw new LotteryException(AccountService.LOGINNAMEALREADYEXIST,AccountService.LOGINNAMEALREADYEXIST_STR);
		if(this.userAccountDAO.selectCountByIdentifier2(mobilePhone)>0)
			throw new LotteryException(AccountService.MOBILEPHONEALREADYEXIST,AccountService.MOBILEPHONEALREADYEXIST_STR);
		if(this.userAccountDAO.selectCountByIdentifier2(email)>0)
			throw new LotteryException(AccountService.EMAILALREADYEXIST,AccountService.EMAILALREADYEXIST_STR);
		try{
			int rc = userAccountDAO.checkByIdentifier(loginName, mobilePhone, email);
			if(rc > 0){
				throw new LotteryException(AccountService.USERISEXIST, AccountService.USERISEXIST_STR);
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("checkByIdentifier exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.USERREGISTEREXCEPTION, AccountService.USERREGISTEREXCEPTION_STR + e.toString());
		}
		UserAccountModel user = new UserAccountModel();
		
		user.setLoginName(loginName);
		user.setMobilePhone(mobilePhone);
		user.setEmail(email);
		user.setNickName(nickName);
		user.setPassword(MD5.MD5Encode(password));
		user.setRealName(null);
		user.setIdCard(null);
		user.setAreaCode(AreaMapTools.getAreaCode(mobilePhone));
		user.setStatus(1);//测试时候用这个
		//user.setStatus(2);//冻结用户，指未激活。
		
		//给用户发送激活邮件
		
		try{
			user=userAccountDAO.addUser(user);
//			MailBean bean=new MailBean();
//			String msg="";
//			bean.sendMail(user.getEmail(), "http://localhost:8080/lotterys/activationEml.jhtml?userid="+user.getUserId());
			return user;
		}catch(Exception e){
			logger.error("addUser exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.ADDUSEREXCEPTION, AccountService.ADDUSEREXCEPTION_STR + e.toString());
		}
		
	}

	@Override
	public UserAccountModel registerByWeb(String loginName, String password) throws LotteryException{
		return registerByWeb(loginName, null, null, password, null, null, null);
	}

	@Override
	public void unregisterBySMS(String mobile) throws LotteryException{
		if(StringUtils.isBlank(mobile)){
			throw new LotteryException(AccountService.MOBILEISNULL, AccountService.MOBILEISNULL_STR);
		}
		UserAccountModel user = null;
		try{
			user = userAccountDAO.selectUserInfo(-1, mobile);
			if(user == null){
				throw new LotteryException(AccountService.NOTFOUNDUSER, AccountService.NOTFOUNDUSER_STR);
			}else{
				if(!user.isBindMobileFlag()){
					throw new LotteryException(AccountService.USERISUNREGISTED, AccountService.USERISUNREGISTED_STR);
				}
				try{
					user.setBindMobileFlag(false);
					userAccountDAO.setBindUserMobileFlag(user.getUserId(), false, user.getUserLevel() & 0xfffffffe);
				}catch(Exception e){
					logger.error("setBindUserMobileFlag exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
					throw new LotteryException(AccountService.UPDATEUSERINFOEXCEPTION, AccountService.UPDATEUSERINFOEXCEPTION_STR + "：" + e.toString());
				}
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("checkByIdentifier exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.USERUNREGISTEREXCEPTION, AccountService.USERUNREGISTEREXCEPTION_STR);
		}
	}

	@Override
	public UserAccountModel getUserInfo(long userId) throws LotteryException{
		return getUserInfo(userId, null, -1);
	}

	@Override
	public UserAccountModel getUserInfo(long userId, int amount) throws LotteryException{
		return getUserInfo(userId, null, amount);
	}
	
	

	@Override
	public UserAccountModel getUserInfo(String userIdentify) throws LotteryException{
		return getUserInfo(-1L, userIdentify, -1);
	}

	@Override
	public UserAccountModel getUserInfo(String userIdentify, int amount) throws LotteryException{
		return getUserInfo(-1L, userIdentify, amount);
	}

	public UserAccountModel getUserInfo(long userId, String userIdentify, int amount) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.selectUserInfo(userId, userIdentify);
			if(user == null){
				throw new LotteryException(AccountService.NOTFOUNDUSER, AccountService.NOTFOUNDUSER_STR);
			}else{
				if(user.getFundsAccount() + user.getPrizeAccount() < amount){
					throw new LotteryException(AccountService.NOTENOUGHMONEY, AccountService.NOTENOUGHFROZENMONEY_STR + "：" + amount);
				}
			}
			if(user.getStatus() != 1){
				throw new LotteryException(AccountService.USERSTATUSERROR, AccountService.USERSTATUSERROR_STR + "：" + user.getStatus());
			}
			return user;
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("getUserInfo exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.FINDUSEREXCEPTION, AccountService.FINDUSEREXCEPTION_STR + e.toString());
		}
	}

	@Override
	public void accountTransaction(String userIdentify, int transactionType, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		accountTransaction(-1L, userIdentify, transactionType, amount, sourceType, sourceSequence, remark);
	}

	@Override
	public void accountTransaction(long userId, int transactionType, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		accountTransaction(userId, null, transactionType, amount, sourceType, sourceSequence, remark);
	}

	// 本金充值
	private void transaction11001(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)(user.getFundsAccount() + amount);
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 11001, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION11001EXCEPTION, AccountService.TRANSACTION11001EXCEPTION_STR + e.toString());
		}
	}

	// 投注订单派奖
	private void transaction11002(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)(user.getPrizeAccount() + amount);
			int frozenBalance = (int)user.getFrozenAccount();
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 11002, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION11002EXCEPTION, AccountService.TRANSACTION11002EXCEPTION_STR + e.toString());
		}
	}

	// 合买发起人佣金派送佣金交易
	private void transaction11003(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)(user.getPrizeAccount() + amount);
			int frozenBalance = (int)user.getFrozenAccount();
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 11003, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION11003EXCEPTION, AccountService.TRANSACTION11003EXCEPTION_STR + e.toString());
		}
	}

	// 合买中奖奖金派送交易
	private void transaction11004(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)(user.getPrizeAccount() + amount);
			int frozenBalance = (int)user.getFrozenAccount();
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 11004, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION11004EXCEPTION, AccountService.TRANSACTION11004EXCEPTION_STR + e.toString());
		}
	}

	// 内部增加调整
	private void transaction11005(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)(user.getFundsAccount() + amount);
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 11005, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION11005EXCEPTION, AccountService.TRANSACTION11005EXCEPTION_STR + e.toString());
		}
	}

	
	
	// 减少本金（通过财务内部调整进行）
	private void transaction10001(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			if(fundsBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHMONEYTOSUB, AccountService.NOTENOUGHMONEYTOSUB_STR + "：" + amount);
			}else{
				fundsBalance = (int)(fundsBalance - amount);
			}
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 10001, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION10001EXCEPTION, AccountService.TRANSACTION10001EXCEPTION_STR + e.toString());
		}
	}

	// 投注交易
	private void transaction10002(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			long otheraccount1 = user.getOtherAccount1();//用做投注金转积分
			if((fundsBalance + prizeBalance) < amount){
				throw new LotteryException(AccountService.NOTENOUGHMONEYTOBET, AccountService.NOTENOUGHMONEYTOBET_STR + "：" + amount);
			}else{
				if(fundsBalance >= amount){
					fundsBalance = fundsBalance - amount;
				}else{
					prizeBalance = (fundsBalance + prizeBalance) - amount;
					fundsBalance = 0;
				}
				otheraccount1 += this.betAmoutToPoints(amount);
			}
			int frozenBalance = (int)user.getFrozenAccount();
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			int rc1 = this.upUserPoinsts(userId, 100001L, 1001L, amount, otheraccount1, "10002", sourceType, "投注金转积分");
			
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 10002, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION10002EXCEPTION, AccountService.TRANSACTION10002EXCEPTION_STR + e.toString());
		}
	}

	// 追号订单资金冻结交易
	private void transaction20002(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			if((fundsBalance + prizeBalance) < amount){
				throw new LotteryException(AccountService.NOTENOUGHMONEYTOFROZEN, AccountService.NOTENOUGHMONEYTOFROZEN_STR + "：" + amount);
			}else{
				if(fundsBalance >= amount){
					fundsBalance = fundsBalance - amount;
				}else{
					prizeBalance = (fundsBalance + prizeBalance) - amount;
					fundsBalance = 0;
				}
			}
			int frozenBalance = (int)(user.getFrozenAccount() + amount);
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 20002, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION20002EXCEPTION, AccountService.TRANSACTION20002EXCEPTION_STR + e.toString());
		}
	}

	// 发起合买资金冻结交易
	private void transaction20003(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			if((fundsBalance + prizeBalance) < amount){
				throw new LotteryException(AccountService.NOTENOUGHMONEYTOFROZEN, AccountService.NOTENOUGHMONEYTOFROZEN_STR + "：" + amount);
			}else{
				if(fundsBalance >= amount){
					fundsBalance = fundsBalance - amount;
				}else{
					prizeBalance = (fundsBalance + prizeBalance) - amount;
					fundsBalance = 0;
				}
			}
			int frozenBalance = (int)(user.getFrozenAccount() + amount);
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 20003, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION20003EXCEPTION, AccountService.TRANSACTION20003EXCEPTION_STR + e.toString());
		}
	}

	// 参与合买资金冻结交易
	private void transaction20005(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			if((fundsBalance + prizeBalance) < amount){
				throw new LotteryException(AccountService.NOTENOUGHMONEYTOFROZEN, AccountService.NOTENOUGHMONEYTOFROZEN_STR + "：" + amount);
			}else{
				if(fundsBalance >= amount){
					fundsBalance = fundsBalance - amount;
				}else{
					prizeBalance = (fundsBalance + prizeBalance) - amount;
					fundsBalance = 0;
				}
			}
			int frozenBalance = (int)(user.getFrozenAccount() + amount);
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 20005, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION20005EXCEPTION, AccountService.TRANSACTION20005EXCEPTION_STR + e.toString());
		}
	}

	// 奖金提现申请资金冻结交易
	private void transaction20007(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			if((prizeBalance) < amount){
				throw new LotteryException(AccountService.NOTENOUGHMONEYTOFROZEN, AccountService.NOTENOUGHMONEYTOFROZEN_STR + "：" + amount);
			}else{
				prizeBalance = prizeBalance - amount;
			}
			int frozenBalance = (int)(user.getFrozenAccount() + amount);
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 20007, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION20007EXCEPTION, AccountService.TRANSACTION20007EXCEPTION_STR + e.toString());
		}
	}

	// 本金提现申请资金冻结交易
	private void transaction20008(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			if(fundsBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHMONEYTOFROZEN, AccountService.NOTENOUGHMONEYTOFROZEN_STR + "：" + amount);
			}else{
				fundsBalance = fundsBalance - amount;
			}
			int frozenBalance = (int)(user.getFrozenAccount() + amount);
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 20008, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION20008EXCEPTION, AccountService.TRANSACTION20008EXCEPTION_STR + e.toString());
		}
	}

	// 追号订单投注成功冻结资金扣除交易
	private void transaction30001(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			long otheraccount1 = user.getOtherAccount1();//用做投注金转积分
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEY, AccountService.NOTENOUGHFROZENMONEY_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
				otheraccount1 += this.betAmoutToPoints(amount);
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			int rc1 = this.upUserPoinsts(userId, 100001L, 1001L, amount, otheraccount1, "30001", sourceType, "投注金转积分");
			
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 30001, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION30001EXCEPTION, AccountService.TRANSACTION30001EXCEPTION_STR + e.toString());
		}
	}

	// 合买方案投注成功冻结资金扣除交易
	private void transaction30002(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			long otheraccount1 = user.getOtherAccount1();//用做投注金转积分
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEY, AccountService.NOTENOUGHFROZENMONEY_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
				otheraccount1 += this.betAmoutToPoints(amount);
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			int rc1 = this.upUserPoinsts(userId, 100001L, 1001L, amount, otheraccount1, "30002", sourceType, "投注金转积分");
			
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 30002, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION30002EXCEPTION, AccountService.TRANSACTION30002EXCEPTION_STR + e.toString());
		}
	}

	// 参与合买方案投注成功冻结资金扣除交易
	private void transaction30003(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			long otheraccount1 = user.getOtherAccount1();//用做投注金转积分
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEY, AccountService.NOTENOUGHFROZENMONEY_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
				otheraccount1 += this.betAmoutToPoints(amount);
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			int rc1 = this.upUserPoinsts(userId, 100001L, 1001L, amount, otheraccount1, "30003", sourceType, "投注金转积分");
			
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 30003, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION30003EXCEPTION, AccountService.TRANSACTION30003EXCEPTION_STR + e.toString());
		}
	}

	// 奖金提现申请完成冻结资金扣除交易
	private void transaction30004(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEY, AccountService.NOTENOUGHFROZENMONEY_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 30004, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION30004EXCEPTION, AccountService.TRANSACTION30004EXCEPTION_STR + e.toString());
		}
	}

	// 本金提现申请完成冻结资金扣除交易
	private void transaction30005(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEY, AccountService.NOTENOUGHFROZENMONEY_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 30005, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION30005EXCEPTION, AccountService.TRANSACTION30005EXCEPTION_STR + e.toString());
		}
	}

	// 投注订单失败退款交易
	private void transaction31001(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount() + amount;
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 31001, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION31001EXCEPTION, AccountService.TRANSACTION31001EXCEPTION_STR + e.toString());
		}
	}

	// 合买方案撤销返还冻结资金
	private void transaction31003(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEYTORETURN, AccountService.NOTENOUGHFROZENMONEYTORETURN_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
				fundsBalance = fundsBalance + amount;
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 31003, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION31003EXCEPTION, AccountService.TRANSACTION31003EXCEPTION_STR + e.toString());
		}
	}

	// 参与合买方案撤销返还冻结资金
	private void transaction31005(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEYTORETURN, AccountService.NOTENOUGHFROZENMONEYTORETURN_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
				fundsBalance = fundsBalance + amount;
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 31005, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION31005EXCEPTION, AccountService.TRANSACTION31005EXCEPTION_STR + e.toString());
		}
	}

	// 追号订单撤销返还冻结资金
	private void transaction31006(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEYTORETURN, AccountService.NOTENOUGHFROZENMONEYTORETURN_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
				fundsBalance = fundsBalance + amount;
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 31006, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION31006EXCEPTION, AccountService.TRANSACTION31006EXCEPTION_STR + e.toString());
		}
	}

	// 奖金提现申请拒绝返还冻结资金
	private void transaction31007(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEYTORETURN, AccountService.NOTENOUGHFROZENMONEYTORETURN_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
				prizeBalance = prizeBalance + amount;
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 31007, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION31007EXCEPTION, AccountService.TRANSACTION31007EXCEPTION_STR + e.toString());
		}
	}

	// 本金提现申请拒绝返还冻结资金
	private void transaction31008(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEYTORETURN, AccountService.NOTENOUGHFROZENMONEYTORETURN_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
				fundsBalance = fundsBalance + amount;
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 31008, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION31008EXCEPTION, AccountService.TRANSACTION31008EXCEPTION_STR + e.toString());
		}
	}

	private void accountTransaction(long userId, String userIdentify, int transactionType, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		if(StringUtils.isBlank(sourceSequence)){
			throw new LotteryException(AccountService.SOURCESEQUENCEISNULL, AccountService.SOURCESEQUENCEISNULL_STR);
		}
		if(amount <= 0){
			throw new LotteryException(AccountService.TRANSACTIONAMOUNTISZERO, AccountService.TRANSACTIONAMOUNTISZERO_STR);
		}
		try{
			switch(transactionType){
				case 10001:
					transaction10001(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 10002:
					transaction10002(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 11001:
					transaction11001(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 11002:
					transaction11002(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 11003:
					transaction11003(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 11004:
					transaction11004(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 11005:
					transaction11005(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 20002:
					transaction20002(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 20003:
					transaction20003(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 20005:
					transaction20005(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 20007:
					transaction20007(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 20008:
					transaction20008(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 30001:
					transaction30001(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 30002:
					transaction30002(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 30003:
					transaction30003(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 30004:
					transaction30004(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 30005:
					transaction30005(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 31001:
					transaction31001(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 31003:
					transaction31003(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 31005:
					transaction31005(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 31006:
					transaction31006(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 31007:
					transaction31007(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 31008:
					transaction31008(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 20009:
					transaction20009(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 30006:
					transaction30006(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 31009:
					transaction31009(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				case 31010:
					transaction31010(userId, userIdentify, amount, sourceType, sourceSequence, remark);
					break;
				default:
					throw new LotteryException(AccountService.NOTFOUNDTRANSACTIOYTYPE, AccountService.NOTENOUGHFROZENMONEY_STR + "：" + transactionType);
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("accountTransaction exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.TRANSACTIONEXCEPTION, AccountService.TRANSACTIONEXCEPTION_STR + e.toString());
		}
	}

	@Override
	public void accountTransaction(long userId, int transactionType, int amount, int sourceType, String sourceSequence) throws LotteryException{
		accountTransaction(userId, transactionType, amount, sourceType, sourceSequence, null);
	}

	@Override
	public void accountTransaction(String userIdentify, int transactionType, int amount, int sourceType, String sourceSequence) throws LotteryException{
		accountTransaction(userIdentify, transactionType, amount, sourceType, sourceSequence, null);
	}

	@Override
	public int getUseresCount(String mobilePhone, String realName,
			String idCard, Date create_time_begin, Date create_time_ends,
			String areaCode) throws LotteryException {
		try{
			int reInt = this.getUserAccountDAO().getUseresCount(mobilePhone,realName,idCard, areaCode,create_time_begin,create_time_ends);
			return reInt;
		}catch(Exception e){
			logger.error("getTermInfoCount(" + mobilePhone+ ", " + realName + 
					", " + idCard + ", "+create_time_begin+", " + create_time_ends + 
					", " + areaCode + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(GETACCOUNTEXCEPTION,GETACCOUNTEXCEPTION_STR);
		}
	}

	@Override
	public List<UserAccountModel> getUseresInfo(String mobilePhone,
			String realName, String idCard, Date create_time_begin,
			Date create_time_ends, String areaCode,  int pageIndex, int perPageNumber)
			throws LotteryException {
		List<UserAccountModel> terms = null;
		try{
			int startNumber = perPageNumber * (pageIndex - 1);
			int endNumber = pageIndex * perPageNumber;
			terms = this.getUserAccountDAO().getUseresCountInfo(mobilePhone,realName,idCard, areaCode,
					create_time_begin,create_time_ends,startNumber, endNumber);
			return terms;
		}catch(Exception e){
			logger.error("getTermInfoCount(" + mobilePhone+ ", " + realName + 
					", " + idCard + ", "+create_time_begin+", " + create_time_ends + 
					", " + areaCode +","+ pageIndex + ", " + perPageNumber + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(GETACCOUNTLISTEXCEPTION, GETACCOUNTLISTEXCEPTION_STR + e.toString());
		}
	}

	public UserAccountModel getUserInfoNoStatus(long userId) throws LotteryException {
		try{
			UserAccountModel user = userAccountDAO.selectUserInfo(userId, null);
			if(user == null){
				throw new LotteryException(AccountService.NOTFOUNDUSER, AccountService.NOTFOUNDUSER_STR);
			}
			return user;
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("getUserInfo exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.FINDUSEREXCEPTION, AccountService.FINDUSEREXCEPTION_STR + e.toString());
		}
	}

	public UserAccountModel getUserInfoNoStatus(String userIdentify) throws LotteryException {
		try{
			UserAccountModel user = userAccountDAO.selectUserInfo(-1L, userIdentify);
			if(user == null){
				throw new LotteryException(AccountService.NOTFOUNDUSER, AccountService.NOTFOUNDUSER_STR);
			}
			return user;
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("getUserInfo exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.FINDUSEREXCEPTION, AccountService.FINDUSEREXCEPTION_STR + e.toString());
		}
	}

	@Override
	public UserAccountModel login(String userIdentify, String password, String lastLoginIp) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserInfoForLogon(userIdentify, password);
			if(user == null){
				throw new LotteryException(AccountService.LOGINFAILED, AccountService.LOGINFAILED_STR);
			} else {
				if(StringUtils.isNotBlank(lastLoginIp)){
					try{
						userAccountDAO.updateUserLogonInfo(user.getUserId(), lastLoginIp);
					}catch(Exception e){
						logger.warn("login(" + userIdentify + ", " + password + ", " + lastLoginIp + ") update lastLoginInfo exception:" + e.toString());
						if(logger.isDebugEnabled()){
							e.printStackTrace();
						}
					}
					user.setLastLoginIP(lastLoginIp);
				}
				return user;
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("login(" + userIdentify + ", " + password + ", " + lastLoginIp + ") exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.LOGINEXCEPTION, AccountService.LOGINEXCEPTION_STR + e.toString());
		}
	}
	
	@Override
	public List<AccountTransactionModel> getUserTransactiones(long userId, Date startDate, Date endDate, int transcationType, int start, int count) throws LotteryException{
		List<AccountTransactionModel> result = this.getUserTransactiones(userId, null, startDate, endDate, transcationType, -1,start, count);
		if(result != null){
			result.add(0, this.getUserTransactionesCount(userId, null, startDate, endDate, transcationType,-1, start, count));
		}
		return result;
	}

	@Override
	public List<AccountTransactionModel> getUserTransactiones(String userIdentify, Date startDate, Date endDate, int transcationType, int start, int count) throws LotteryException{
		List<AccountTransactionModel> result = this.getUserTransactiones(-1, userIdentify, startDate, endDate, transcationType,-1, start, count);
		if(result != null){
			result.add(0, this.getUserTransactionesCount(-1, userIdentify, startDate, endDate, transcationType, -1,start, count));
		}
		return result;
	}
	
	@Override
	public List<AccountTransactionModel> getUser11001Transactiones(long userId, Date startDate, Date endDate, int sourceType, int start, int count) throws LotteryException{
		List<AccountTransactionModel> result = this.getUserTransactiones(userId, null, startDate, endDate, 11001, sourceType,start, count);
		if(result != null){
			result.add(0, this.getUserTransactionesCount(userId, null, startDate, endDate, 11001,sourceType, start, count));
		}
		return result;
	}
	/**
	 * 
	 * Title: getUserTransactiones<br>
	 * Description: <br>
	 *              <br>该方法不能直接对外提供
	 * @param userId
	 * @param userIdentify
	 * @param startDate
	 * @param endDate
	 * @param transcationType
	 * @param start
	 * @param count
	 * @return
	 * @throws LotteryException
	 */
	private List<AccountTransactionModel> getUserTransactiones(long userId,String userIdentify, Date startDate, Date endDate, int transcationType,int sourceType, int start, int count) throws LotteryException{
		List<AccountTransactionModel> result = null;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp startTime = null, endTime = null;
			if(startDate != null){
				startTime = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
			}
			if(endDate != null){
				endTime = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
			}
			result = this.getAccountTransactionDAO().getUserTransactiones(userId, userIdentify, startTime, endTime, transcationType,sourceType, start, count);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getUserTransactiones is error :",e);
			throw new LotteryException(AccountService.DATEFORMATEXCEPTION,AccountService.DATEFORMATEXCEPTION_STR+":"+e.toString());
		}
		return result;
	}
	
	private AccountTransactionModel getUserTransactionesCount(long userId,String userIdentify, Date startDate, Date endDate, int transcationType,int sourceType, int start, int count) throws LotteryException{
		AccountTransactionModel result = new AccountTransactionModel();
		try{
			result.setTransactionType(0);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp startTime = null, endTime = null;
			if(startDate != null){
				startTime = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
			}
			if(endDate != null){
				endTime = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
			}
			/*
			 * 			transactionType：存放的为本次查询到的总条数，如没有查询到也可获得该值为0;
	         * 			fundsAccount：收入交易笔数；
	         * 			prizeAccount：收入交易金额
	         * 			frozenAccount：支出交易笔数
	         * 			commisionAccount：支出交易金额
	         * 			advanceAccount：冻结交易笔数
	         * 			awardAccount：冻结交易金额
	         * 			otherAccount1：解冻交易笔数
	         * 			otherAccount2：解冻交易金额	
			 */
			List<AccountTransactionModel> accountTrans = this.getAccountTransactionDAO().getUserTransactionesCount(userId, userIdentify, startTime, endTime, transcationType,sourceType);
			if(accountTrans != null){
				long totalNum = 0L,fundsAccount = 0L,amount =0L,prizeAccount = 0L,frozenAccount = 0L,commisionAccount = 0L,advanceAccount = 0L,awardAccount = 0L,otherAccount1 = 0L,otherAccount2 = 0L;
				
				for(AccountTransactionModel oneAcc : accountTrans){
					int transactionType = oneAcc.getTransactionType();
					
					String flag = LotteryStaticDefine.transactionToOpType.get(transactionType);
					if(StringUtils.isEmpty(flag)){//如果没有对应到定义的交易类型，则要抛出异常，否则统计将不准确
						throw new LotteryException(TRANSACTION31010EXCEPTION,TRANSACTION31010EXCEPTION_STR+transactionType);
					}
					totalNum += oneAcc.getUserId();//总的记录数
					amount=oneAcc.getAmount();
					if("A".equals(flag)){//收入
						fundsAccount += oneAcc.getUserId();
						prizeAccount += oneAcc.getAmount();
					}else if("B".equals(flag)){//支出
						frozenAccount += oneAcc.getUserId();
						commisionAccount += oneAcc.getAmount();
					}else if("C".equals(flag)){//冻结
						advanceAccount += oneAcc.getUserId();
						awardAccount += oneAcc.getAmount();
					}else if("D".equals(flag)){//解冻
						otherAccount1 += oneAcc.getUserId();
						otherAccount2 += oneAcc.getAmount();
					}
				}
				result.setTransactionType((int)totalNum);
				result.setAmount(amount);
				result.setFundsAccount(fundsAccount);
				result.setPrizeAccount(prizeAccount);
				result.setFrozenAccount(frozenAccount);
				result.setCommisionAccount(commisionAccount);
				result.setAdvanceAccount(advanceAccount);
				result.setAwardAccount(awardAccount);
				result.setOtherAccount1(otherAccount1);
				result.setOtherAccount2(otherAccount2);
			}
			
		}catch(LotteryException ex){
			logger.error("getUserTransactionesCount is error :",ex);
			throw ex;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getUserTransactionesCount is error :",e);
			throw new LotteryException(DATEFORMATEXCEPTION,DATEFORMATEXCEPTION_STR+e.toString());
		}
		return result;
	}

	@Override
	public int updateUserdetailInfo(String nickName, long userId,
			String realName, String address, String idcard, String email)
			throws LotteryException {
		int flg=0;
		UserAccountModel userinfo=new UserAccountModel();
		userinfo.setNickName(nickName);
		userinfo.setAddress(address);
		userinfo.setUserId(userId);
		userinfo.setIdCard(idcard);
		userinfo.setRealName(realName);
		if(!StringUtils.isBlank(email))
			userinfo.setEmail(email);
		else
			userinfo.setEmail(null);
		try {
			flg=this.getUserAccountDAO().updateUserdetailInfo(userinfo);
		}catch(LotteryException ex){
			logger.error("updateUserdetailInfo is error :",ex);
			throw new LotteryException(USERINFOEXCEPTION,USERINFOEXCEPTION_STR+ex.toString());
		}
		
			return flg;
	}
	
	public int updateUserdetailInfo(long userId,String password, String nickName, String phone, String email, String realName, String idcard, String bankName, String bankCardId, String address, String postcode, String qq, String msn, int sex, Date birthday, String reserve) throws LotteryException {
		int result = -1;
		UserAccountModel userinfo=new UserAccountModel();
		userinfo.setUserId(userId);
		userinfo.setPassword(password);
		userinfo.setNickName(nickName);
		userinfo.setPhone(phone);
		userinfo.setEmail(email);
		userinfo.setRealName(realName);
		userinfo.setIdCard(idcard);
		userinfo.setBankName(bankName);
		userinfo.setBankCardId(bankCardId);
		userinfo.setAddress(address);
		userinfo.setPostcode(postcode);
		userinfo.setQq(qq);
		userinfo.setMsn(msn);
		userinfo.setSex(sex);
		userinfo.setReserve(reserve);
		if(birthday != null){
			userinfo.setBirthday(new Timestamp(birthday.getTime()));
		}
		try{
			result = this.getUserAccountDAO().updateUserInfo(userinfo);
			System.out.println("**********************result=="+result);
		}catch(Exception e){
			throw new LotteryException(999999,"修改用户信息出错!");
		}
		return result;
	}
	
	@Override
	public int updateUserPasswordInfo(String pwd, long userId)
			throws LotteryException {
		int flg=0;
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userid", userId);
		map.put("password", MD5.MD5Encode(pwd));
		try {
			flg=this.userAccountDAO.updateUserPasswordInfo(map);
		} catch (LotteryException e) {
			e.printStackTrace();
			logger.error("updateUserPasswordInfo is error :",e);
			throw new LotteryException(USERPASSWORDEXCEPTION,USERPASSWORDEXCEPTION_STR+e.toString());
		}
		return flg;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void mailFindPassword(String address, String username, String passwd) throws LotteryException{
		try{
			String resource = "com.success.lottery.account.service.fbpassword";
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "GB2312");
			helper.setTo(address);
			helper.setFrom(AutoProperties.getString("sendEmail", "info@chinatlt.com", resource));
			helper.setSubject(AutoProperties.getString("emailtitle", "密码找回", resource));
			String text = AutoProperties.getString("emailName", null, resource) + username + 
			AutoProperties.getString("emailPassword", null, resource) + passwd + 
			AutoProperties.getString("emailContent", null, resource);
			helper.setText(text, true);
			mailSender.send(msg);
		}catch(Exception e){
			logger.error("Notify mail send Exception:" + e.toString());
			e.printStackTrace();
			throw new LotteryException(AccountService.SENDMAILEXCEPTION, AccountService.SENDMAILEXCEPTION_STR + e.toString());
		}
	}
	
	@Override
	public UserAccountModel registerBySMS(String mobile, int smsServiceType) throws LotteryException{
		if(StringUtils.isBlank(mobile)){
			throw new LotteryException(AccountService.MOBILEISNULL, AccountService.MOBILEISNULL_STR);
		}
		UserAccountModel user = null;
		try{
			user = userAccountDAO.selectUserInfo(-1, mobile);
			if(user == null){
				user = new UserAccountModel();
				user.setLoginName(mobile);
				user.setMobilePhone(mobile);
				user.setNickName(mobile);
				switch(smsServiceType){
					case 1:
						user.setUserLevel(0x00000000 | 0x00000001);
						user.setBindMobileFlag(true);
						break;
					case 2:
						user.setUserLevel(0x00000000 | 0x00000002);
						user.setBindMobileFlag(false);
					default:
						user.setUserLevel(0);
						user.setBindMobileFlag(false);
						break;
				}
				String password = RandomStringUtils.random(8, true, true);
				user.setPassword(MD5.MD5Encode(password));
				String areaCode = AreaMapTools.getAreaCode(mobile);
				user.setAreaCode(areaCode);
				try{
					return userAccountDAO.addUser(user);
				}catch(Exception e){
					logger.error("addUser exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
					throw new LotteryException(AccountService.ADDUSEREXCEPTION, AccountService.ADDUSEREXCEPTION_STR);
				}
			}else{
				try{
					switch(smsServiceType){
						case 1:
							if((user.getUserLevel() & 0x00000001) == 1){
								throw new LotteryException(AccountService.USERISEXIST, AccountService.USERISEXIST_STR);
							} else {
								int rc = userAccountDAO.setBindUserMobileFlag(user.getUserId(), true, user.getUserLevel() | 0x00000001);
								if(rc != 1){
									throw new LotteryException(AccountService.NOTUPDATEUSERINFO, AccountService.NOTUPDATEUSERINFO_STR + "：" + rc);
								}
							}
							break;
						case 2:
							if((user.getUserLevel() & 0x00000002) == 1){
								throw new LotteryException(AccountService.USERISEXIST, AccountService.USERISEXIST_STR);
							} else {
								int rc = userAccountDAO.setBindUserMobileFlag(user.getUserId(), user.isBindMobileFlag(), user.getUserLevel() | 0x00000002);
								if(rc != 1){
									throw new LotteryException(AccountService.NOTUPDATEUSERINFO, AccountService.NOTUPDATEUSERINFO_STR + "：" + rc);
								}
							}
							break;
						default:
					}
				}catch(Exception e){
					logger.error("setBindUserMobileFlag exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
					throw new LotteryException(AccountService.ADDUSEREXCEPTION, AccountService.ADDUSEREXCEPTION_STR);
				}
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("checkByIdentifier exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.USERREGISTEREXCEPTION, AccountService.USERREGISTEREXCEPTION_STR);
		}
		return user;
	}

	@Override
	public void unregisterBySMS(String mobile, int smsServiceType) throws LotteryException{
		if(StringUtils.isBlank(mobile)){
			throw new LotteryException(AccountService.MOBILEISNULL, AccountService.MOBILEISNULL_STR);
		}
		UserAccountModel user = null;
		try{
			user = userAccountDAO.selectUserInfo(-1, mobile);
			if(user == null){
				throw new LotteryException(AccountService.NOTFOUNDUSER, AccountService.NOTFOUNDUSER_STR);
			}else{
				try{
					switch(smsServiceType){
						case 1:
							if((user.getUserLevel() & 0x00000001) == 0){
								throw new LotteryException(AccountService.USERISUNREGISTED, AccountService.USERISUNREGISTED_STR);
							} else {
								int rc = userAccountDAO.setBindUserMobileFlag(user.getUserId(), false, user.getUserLevel() & 0xfffffffe);
								if(rc != 1){
									throw new LotteryException(AccountService.NOTUPDATEUSERINFO, AccountService.NOTUPDATEUSERINFO_STR + "：" + rc);
								}
							}
							break;
						case 2:
							if((user.getUserLevel() & 0x00000002) == 0){
								throw new LotteryException(AccountService.USERISUNREGISTED, AccountService.USERISUNREGISTED_STR);
							} else {
								int rc = userAccountDAO.setBindUserMobileFlag(user.getUserId(), user.isBindMobileFlag(), user.getUserLevel() & 0xfffffffd);
								if(rc != 1){
									throw new LotteryException(AccountService.NOTUPDATEUSERINFO, AccountService.NOTUPDATEUSERINFO_STR + "：" + rc);
								}
							}
							break;
						default:
					}
				}catch(Exception e){
					logger.error("setBindUserMobileFlag exception:" + e.toString());
					if(logger.isDebugEnabled()){
						e.printStackTrace();
					}
					throw new LotteryException(AccountService.UPDATEUSERINFOEXCEPTION, AccountService.UPDATEUSERINFOEXCEPTION_STR + "：" + e.toString());
				}
			}
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			logger.error("checkByIdentifier exception:" + e.toString());
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
			throw new LotteryException(AccountService.USERUNREGISTEREXCEPTION, AccountService.USERUNREGISTEREXCEPTION_STR);
		}		
	}	
	
	/**
	 * @param args
	 * @throws LotteryException
	 */
	public static void main(String[] args) throws LotteryException{
		
//		System.out.println(0x00000000 | 0x00000001);
//		System.out.println(0x00000000 | 0x00000002);
//		System.out.println(0x00000001 | 0x00000002);
//		System.out.println(0x00000002 | 0x00000001);
//		System.out.println(0x00000001 | 0x00000001);
//		System.out.println(0x00000002 | 0x00000002);
//		AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
//		System.out.println(accountService.updateUserdetailInfo("aaaa", 1, "chenhao", "地址：宝山","123456789987456321","hao.chen@chinatlt.com"));
		 
		// UserAccountModel user = accountService.registerByWeb("bingbing",
		// "13761874312", "大赢家as", "123456", null, "冰李", "433243432423432");
		// System.out.println(user.getUserId());
		// UserAccountModel user = accountService.registerBySMS("13761874322");
		// System.out.println(user.getUserId());
		// UserAccountModel user = accountService.getUserInfo(4);
		// System.out.println("getUserinfo(userId) = " + user.getLoginName());
		//		
		// user = accountService.getUserInfo(4, 100);
		// System.out.println("getUserinfo(userId, amount) = " +
		// user.getLoginName());
		//		
		// user = accountService.getUserInfo(4, 10000);
		// System.out.println("getUserinfo(userId, amount) 资金不足= " +
		// user.getLoginName());
		// UserAccountModel user = accountService.getUserInfo("admin4");
		// System.out.println("getUserinfo(admin4) = " + user.getLoginName());
		//		
		// user = accountService.getUserInfo("admin4", 100);
		// System.out.println("getUserinfo(admin4, amount) = " +
		// user.getLoginName());
		//		
		// user = accountService.getUserInfo("admin4", 10000);
		// System.out.println("getUserinfo(admin4, amount) 资金不足= " +
		// user.getLoginName());
		// UserAccountModel user = accountService.getUserInfo(10L, 100);
		// System.out.println("getUserinfo(10L) = " + user.getLoginName());
		// accountService.accountTransaction("libing", 11001, 10, 1001,
		// "TEST1111111");
		// for(int i = 0; i < 3; i++){
		// new Thread("000" + i){
		// public void run(){
		// for(int j = 0; j < 5; j++){
		// try{
		// // AccountService accountService =
		// ApplicationContextUtils.getService("accountService",
		// AccountService.class);
		// // accountService.accountTransaction("libing", 11001, 10, 1001,
		// "TEST" + this.getName() + j);
		//							
		// AccountService accountService =
		// ApplicationContextUtils.getService("accountService",
		// AccountService.class);
		// // accountService.accountTransaction("libing", 11002, 5, 2002,
		// "ORDER" + this.getName() + j);
		// // accountService.accountTransaction("libing", 11003, 15, 2002,
		// "PLAN" + this.getName() + j);
		// // accountService.accountTransaction("libing", 11004, 20, 2002,
		// "PLANW" + this.getName() + j);
		// accountService.accountTransaction("libing", 10001, 10, 1004, "NB" +
		// this.getName() + j);
		// }catch(LotteryException e){
		// System.out.println("交易出现异常：" + e.toString());
		// e.printStackTrace();
		// }
		// }
		// }
		// }.start();
		// }
		// AccountService accountService =
		// ApplicationContextUtils.getService("accountService",
		// AccountService.class);
		// accountService.accountTransaction("libing", 11001, 100, 1003, "NB" +
		// System.currentTimeMillis());
		// accountService.accountTransaction("libing", 10002, 10, 2002, "TZ" +
		// System.currentTimeMillis());
		// accountService.accountTransaction("libing", 10002, 1000, 2002, "TZ" +
		// System.currentTimeMillis());
		// accountService.accountTransaction("libing", 10002, 90, 2002, "TZ" +
		// System.currentTimeMillis());
		// accountService.accountTransaction("libing", 10002, 75, 2002, "TZ" +
		// System.currentTimeMillis());
		// accountService.accountTransaction("libing", 11001, 100, 1003, "NB" +
		// System.currentTimeMillis());
		// accountService.accountTransaction("libing", 10002, 180, 2002, "TZ" +
		// System.currentTimeMillis());
		// accountService.accountTransaction("libing", 11001, 100, 1003, "NB" +
		// System.currentTimeMillis());
		// accountService.accountTransaction("libing", 10002, 620, 2002, "TZ" +
		// System.currentTimeMillis());
//		for(int i = 0; i < 3; i++){
//			new Thread("000" + i){
//
//				public void run(){
//					for(int j = 0; j < 5; j++){
//						try{
//							AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
//							// accountService.accountTransaction("libing",
//							// 11001, 10, 1001, "TEST" + this.getName() + j);
//							// AccountService accountService =
//							// ApplicationContextUtils.getService("accountService",
//							// AccountService.class);
//							// accountService.accountTransaction("libing",
//							// 11002, 5, 2002, "ORDER" + this.getName() + j);
//							// accountService.accountTransaction("libing",
//							// 11003, 15, 2002, "PLAN" + this.getName() + j);
//							// accountService.accountTransaction("libing",
//							// 11004, 20, 2002, "PLANW" + this.getName() + j);
//							// accountService.accountTransaction("libing",
//							// 10001, 10, 1004, "NB" + this.getName() + j);
//							accountService.accountTransaction("libing", 10002, 13, 2002, "TZ" + System.currentTimeMillis());
//							accountService.accountTransaction("libing", 20002, 16, 2002, "TZ" + System.currentTimeMillis());
//						}catch(LotteryException e){
//							System.out.println("交易出现异常：" + e.toString());
//							e.printStackTrace();
//						}
//					}
//				}
//			}.start();
//		}
		
//		AccountService accountService = ApplicationContextUtils.getService("accountService", AccountService.class);
//		//UserAccountModel user = accountService.registerBySMS("13761874366");
//		accountService.unregisterBySMS("13761874366");
//		UserAccountModel user = accountService.getUserInfo("13761874366");
//		System.out.println(user.isBindMobileFlag() + ", " + user.getMobilePhone());
	}

	@Override
	public AccountTransactionModel getUserTransactionBySourceSequence(
			String sourceSequence, int sourceType) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("sourceSequence", sourceSequence);
		map.put("sourceType", sourceType);
		return this.getAccountTransactionDAO().getUserTransactionBySourceSequence(map);
	}
	/**
	 * 投注金转为积分
	 * @param amount
	 * @return
	 */
	private int betAmoutToPoints(int amount){
		int toPoints = AutoProperties.getInt("amoutToP", 1, "com.success.lottery.account.service.amouttopoints");
		return (amount/100)*toPoints;
	}

	@Override
	public int updateUserdetailbaise(long userId, String qq, String address,
			String mobilePhone, int sex) throws LotteryException {
		int flg=0;
		UserAccountModel userinfo=new UserAccountModel();
		userinfo.setUserId(userId);
		userinfo.setQq(qq);
		userinfo.setAddress(address);
		userinfo.setSex(sex);
		userinfo.setMobilePhone(mobilePhone);
		try {
			flg=this.getUserAccountDAO().updateUserInfoBaise(userinfo);
		}catch(LotteryException ex){
			logger.error("updateUserdetailbaise is error :",ex);
			throw new LotteryException(USERINFOFORBAISE,USERINFOFORBAISE_STR+ex.toString());
		}
	
		return flg;
	}
    public int updateUserBankCard(long userId,String bankName,String detaileBankName,String bankCardId)throws LotteryException {
    	int flg=0;
        if(bankName==null||"".equals(bankName.trim()))
        	throw new LotteryException(BANKNAMEISNULL,BANKNAMEISNULL_STR);
        if(detaileBankName==null||"".equals(detaileBankName.trim()))
        	throw new LotteryException(DETAILEBANKNAMEISNULL,DETAILEBANKNAMEISNULL_STR);
        if(bankCardId==null||"".equals(bankCardId.trim()))
        	throw new LotteryException(BANKCARDIDISNULL,BANKCARDIDISNULL_STR);
    	UserAccountModel userinfo=new UserAccountModel();
    	userinfo.setUserId(userId);
    	userinfo.setBankCardId(bankCardId);
    	userinfo.setBankName(bankName+"-"+detaileBankName);//开户行与支行名称以-号隔开
    	try {
			flg=this.getUserAccountDAO().updateUserInfoBank(userinfo);
		}catch(LotteryException ex){
			logger.error("updateBankCard is error :",ex);
			throw new LotteryException(BINDINGBANKCARDISERR,BINDINGBANKCARDISERR_STR+ex.toString());
		}
    	
    	return flg;
    }
	@Override
	public int updateUserInfoID(long userId, String realName, String idCard) throws LotteryException {
		int flg=0;
		UserAccountModel userinfo=new UserAccountModel();
		userinfo.setUserId(userId);
		userinfo.setRealName(realName);
		userinfo.setIdCard(idCard);
		try {
			flg=this.getUserAccountDAO().updateUserInfoID(userinfo);
		}catch(LotteryException ex){
			logger.error("updateUserInfoID is error :",ex);
			throw new LotteryException(USERINFOFORIDCARD,USERINFOFORIDCARD_STR+ex.toString());
		}
	
		return flg;
	}

	@Override
	public int flgOnlylabeled(String id) {
		// TODO Auto-generated method stub
		return this.userAccountDAO.selectCountByIdentifier2(id);
	}
	
    //发起合买保底资金冻结
	private void transaction20009(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			if((fundsBalance + prizeBalance) < amount){
				throw new LotteryException(AccountService.NOTENOUGHMONEYTOFROZEN, AccountService.NOTENOUGHMONEYTOFROZEN_STR + "：" + amount);
			}else{
				if(fundsBalance >= amount){
					fundsBalance = fundsBalance - amount;
				}else{
					prizeBalance = (fundsBalance + prizeBalance) - amount;
					fundsBalance = 0;
				}
			}
			int frozenBalance = (int)(user.getFrozenAccount() + amount);
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 20009, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION20009EXCEPTION, AccountService.TRANSACTION20009EXCEPTION_STR + e.toString());
		}
	}
	
	//合买方案投注成功保底资金从本金扣除扣除交易
	private void transaction30006(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();//本金
			int prizeBalance = (int)user.getPrizeAccount();//奖金
			int frozenBalance = (int)user.getFrozenAccount();//冻结资金
			long otheraccount1 = user.getOtherAccount1();//用做投注金转积分
			if(fundsBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEY, AccountService.NOTENOUGHFROZENMONEY_STR + "：" + amount);
			}else{
				fundsBalance = fundsBalance - amount;
				otheraccount1 += this.betAmoutToPoints(amount);
			}
			
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			int rc1 = this.upUserPoinsts(userId, 100001L, 1001L, amount, otheraccount1, "30006", sourceType, "投注金转积分");
			
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 30002, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION30006EXCEPTION, AccountService.TRANSACTION30006EXCEPTION_STR + e.toString());
		}
	}
	/**
	 * 
	 * Title: upUserPoinsts<br>
	 * Description: <br>
	 *              <br>更新用户积分
	 * @param userId
	 * @param transType
	 * @param transSubType
	 * @param amount
	 * @param points
	 * @param accountType
	 * @param accoutSourceType
	 * @param remark
	 * @return
	 */
	private int upUserPoinsts(long userId,long transType,long transSubType,int amount,long points,String accountType,int accoutSourceType, String remark){
		UserPointTrans pointTrans = new UserPointTrans();
		pointTrans.setUserId(userId);
		pointTrans.setTransType(transType);
		pointTrans.setTransSubType(transSubType);
		pointTrans.setBetAmount(amount);
		pointTrans.setPoints(points);
		pointTrans.setReserve(accountType+"#"+accoutSourceType);
		pointTrans.setRemark(remark);
		String pointTransId = userAccountDAO.insertPointTrans(pointTrans);
		return userAccountDAO.updatePoints(userId, points);
	}
	
	//合买方案保底返还冻结资金
	private void transaction31009(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			if(frozenBalance < amount){
				throw new LotteryException(AccountService.NOTENOUGHFROZENMONEYTORETURN, AccountService.NOTENOUGHFROZENMONEYTORETURN_STR + "：" + amount);
			}else{
				frozenBalance = frozenBalance - amount;
				fundsBalance = fundsBalance + amount;
			}
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 31003, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION31009EXCEPTION, AccountService.TRANSACTION31009EXCEPTION_STR + e.toString());
		}
	}
	
	//订单失败退款
	private void transaction31010(long userId, String userIdentify, int amount, int sourceType, String sourceSequence, String remark) throws LotteryException{
		try{
			UserAccountModel user = userAccountDAO.getUserAccountInfoForUpdate(userId, userIdentify, -1);
			if(user == null){
				throw new LotteryException(AccountService.NOTTRANSACTIONUSER, AccountService.NOTTRANSACTIONUSER_STR);
			}
			int fundsBalance = (int)user.getFundsAccount();
			int prizeBalance = (int)user.getPrizeAccount();
			int frozenBalance = (int)user.getFrozenAccount();
			fundsBalance = fundsBalance + amount;
			
			int rc = userAccountDAO.updateUserAccountBalance(user.getUserId(), fundsBalance, prizeBalance, frozenBalance);
			if(rc != 1){
				throw new LotteryException(AccountService.NOTUPDATEACCOUNT, AccountService.NOTUPDATEACCOUNT_STR + "：" + rc);
			}
			accountTransactionDAO.addTransactionInfo(user.getUserId(), amount, 31010, fundsBalance, prizeBalance, frozenBalance, sourceType, sourceSequence, remark);
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(AccountService.TRANSACTION31009EXCEPTION, AccountService.TRANSACTION31009EXCEPTION_STR + e.toString());
		}
	}
}
