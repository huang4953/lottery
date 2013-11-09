/**
 * Title: LbapBussinessServer.java
 * @Package com.success.lottery.lbapserver
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-2 ����02:01:22
 * @version V1.0
 */
package com.success.lottery.lbapserver;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.domain.LbapMsgDomain;
import com.success.lottery.business.service.interf.BetServiceInterf;
import com.success.lottery.business.service.interf.DrawMoneyInterf;
import com.success.lottery.business.service.interf.LbapServiceInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.protocol.lbap.DESPlus;
import com.success.protocol.lbap.LBAP_DataPack;
import com.success.protocol.lbap.LBAP_Error;
import com.success.protocol.lbap.LBAP_GetUserInfo;
import com.success.protocol.lbap.LBAP_GetUserInfoResp;
import com.success.protocol.lbap.LBAP_Modify_User;
import com.success.protocol.lbap.LBAP_Modify_UserResp;
import com.success.protocol.lbap.LBAP_Recharge;
import com.success.protocol.lbap.LBAP_RechargeResp;
import com.success.protocol.lbap.LBAP_Register;
import com.success.protocol.lbap.LBAP_RegisterResp;
import com.success.protocol.lbap.LBAP_UserInfo;
import com.success.protocol.lbap.LBAP_bet;
import com.success.protocol.lbap.LBAP_betResp;
import com.success.protocol.lbap.LBAP_draw;
import com.success.protocol.lbap.LBAP_drawResp;
import com.success.protocol.lbap.LBAP_getCurrentTerm;
import com.success.protocol.lbap.LBAP_getCurrentTermResp;
import com.success.protocol.lbap.LBAP_getMsg;
import com.success.protocol.lbap.LBAP_getMsgResp;
import com.success.protocol.lbap.LBAP_getTermInfo;
import com.success.protocol.lbap.LBAP_getTermInfoResp;
import com.success.protocol.lbap.LBAP_term;
import com.success.protocol.lbap.LBAP_userLogin;
import com.success.protocol.lbap.LBAP_userLoginResp;
import com.success.protocol.lbap.LbapMess;
import com.success.protocol.lbap.MD5Util;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.lbapserver
 * LbapBussinessServer.java
 * LbapBussinessServer
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-2 ����02:01:22
 * 
 */

public class LbapBussinessServer {
	
	private static Log logger = LogFactory.getLog(LbapBussinessServer.class);
	
	private static int isDES = 0;//������
	
	private static String DEFAULT_KEY = "success";//Ĭ�ϵļ�����Կ
	
	static{
		LbapBussinessServer.isDES = StringUtils.isEmpty(LbapMess.getConfig("isDes")) ? 0 : Integer.parseInt(LbapMess.getConfig("isDes"));
		LbapBussinessServer.DEFAULT_KEY = StringUtils.isEmpty(LbapMess.getConfig("desKey")) ? "success" : LbapMess.getConfig("desKey");
	}
	
	/**
	 *Title: 
	 *Description: 
	 */
	public LbapBussinessServer() {
		super();
	}

	/**
	 * 
	 * Title: registerUser<br>
	 * Description: <br>
	 *              <br>�û�ע��
	 * @param dataPack
	 * @return LBAP_DataPack
	 */
	public LBAP_DataPack registerUser(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		
		try {
			LBAP_Register register = (LBAP_Register)dataPack;
			LBAP_RegisterResp regResponse = new LBAP_RegisterResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			
			String password = StringUtils.isEmpty(register.getPassword())? register.getMobilePhone() : register.getPassword();
			//String passDM5 = MD5Util.getMD5String(password);
			
			/*
			 * У�����
			 */
			if(register.getEmail() != null && register.getEmail().trim().equals("")){
				throw new Exception("�û������ַ����Ϊ��");
			}
			if(!LbapVaildator.isEmail(register.getEmail())){
				throw new Exception("�û������ַ��ʽ����");
			}
			
			AccountService accountService =  ApplicationContextUtils.getService("accountService", AccountService.class);
			UserAccountModel User = accountService.registerByWeb(register.getLoginName(), register.getMobilePhone(), register.getNickName(), password, register.getEmail(), register.getRealName(), register.getIdCard());
			long userId = User.getUserId();
			
			UserAccountModel regUser = accountService.getUserInfo(userId);
			
			if(regUser != null){
				LBAP_UserInfo info = convertUserInfo(regUser);
				regResponse.setUserInfo(info);
			}
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ����û�ע�����:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ����û�ע�����:", ex);
			retResult = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),ex.getMessage());//������Ӧ��Ϣ
		}
		
		return retResult;
	}
	
	/**
	 * 
	 * Title: modifyUser<br>
	 * Description: <br>
	 *              <br>�޸��û���Ϣ
	 * @param dataPack
	 * @return LBAP_DataPack
	 */
	public LBAP_DataPack modifyUser(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		try {
			LBAP_Modify_User modifyUser = (LBAP_Modify_User)dataPack;
			LBAP_Modify_UserResp regResponse = new LBAP_Modify_UserResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			
			String password = StringUtils.isEmpty(modifyUser.getPassword())? null : MD5Util.getMD5String(modifyUser.getPassword());
			
			/*
			 * У�����
			 */
			if(!LbapVaildator.isEmail(modifyUser.getEmail())){
				throw new Exception("�û������ַ��ʽ����");
			}
			
			if(StringUtils.isEmpty(modifyUser.getUserId())){
				throw new Exception("�û�ID����Ϊ��");
			}
			
			String sex = modifyUser.getSex();
			
			if(StringUtils.isNotEmpty(sex) && !sex.equals("0") && !sex.equals("1")){
				throw new Exception("�û��Ա�ֻ��Ϊ0��1");
			}
			
			AccountService accountService =  ApplicationContextUtils.getService("accountService", AccountService.class);
			long userId = Long.parseLong(modifyUser.getUserId());
			
			int sexInt = Integer.parseInt(StringUtils.isEmpty(modifyUser.getSex()) ? "-1" : modifyUser.getSex());
			
			String birthday = modifyUser.getBirthday();
			
			Date birthdayDate = StringUtils.isNotEmpty(birthday) ? LbapUtils.string2Date("yyyyMMdd", birthday)  : null;
			
			int updateResult = accountService.updateUserdetailInfo(userId,password,
					modifyUser.getNickname(), modifyUser.getPhone(), modifyUser
							.getEmail(), modifyUser.getRealName(), modifyUser
							.getIdCard(), modifyUser.getBankName(), modifyUser
							.getBankCardId(), modifyUser.getAddress(),
					modifyUser.getPostcode(), modifyUser.getQq(), modifyUser
							.getMsn(), sexInt, birthdayDate, modifyUser
							.getReserve());
			if(updateResult <= 0 ){
				throw new Exception("�޸��û�ʧ��,�û�������");
			}
			
			UserAccountModel regUser = accountService.getUserInfo(userId);
			
			if(regUser != null){
				LBAP_UserInfo info = convertUserInfo(regUser);
				regResponse.setUserInfo(info);
			}
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ����û��޸ĳ���:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ����û��޸ĳ���:", ex);
			retResult = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),ex.getMessage());//������Ӧ��Ϣ
		}
		return retResult;
	}
	/**
	 * 
	 * Title: UserLogin<br>
	 * Description: <br>
	 *              <br>�û���¼ҵ����
	 * @param dataPack
	 * @return LBAP_DataPack
	 */
	public LBAP_DataPack UserLogin(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		try {
			LBAP_userLogin loginUser = (LBAP_userLogin)dataPack;
			LBAP_userLoginResp regResponse = new LBAP_userLoginResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			String password = StringUtils.isEmpty(loginUser.getPassword())? null : loginUser.getPassword();
			String userIdentify = loginUser.getUserIdentify();
			
			/*
			 * У�����
			 */
			if(StringUtils.isEmpty(password)){
				throw new Exception(" ��¼���벻��Ϊ��");
			}
			
			if(StringUtils.isEmpty(userIdentify)){
				throw new Exception("��¼�û�������Ϊ��");
			}
			
			AccountService accountService =  ApplicationContextUtils.getService("accountService", AccountService.class);
			
			UserAccountModel user = accountService.login(userIdentify, password, loginUser.getLastLoginIp());
			
			if(user != null){
				LBAP_UserInfo info = convertUserInfo(user);
				regResponse.setUserInfo(info);
			}
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ����û���¼����:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ����û���¼����:", ex);
			retResult = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),ex.getMessage());//������Ӧ��Ϣ
		}
		return retResult;
	}
	/**
	 * 
	 * Title: getUsrInfo<br>
	 * Description: <br>
	 *              <br>��ȡ������Ϣ
	 * @param dataPack
	 * @return
	 */
	public LBAP_DataPack getUsrInfo(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		try {
			LBAP_GetUserInfo loginUser = (LBAP_GetUserInfo)dataPack;
			LBAP_GetUserInfoResp regResponse = new LBAP_GetUserInfoResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			
			String userId = loginUser.getUserId();
			/*
			 * У�����
			 */
			
			if(StringUtils.isEmpty(userId)){
				throw new Exception("�û�ID����Ϊ��");
			}
			
			AccountService accountService =  ApplicationContextUtils.getService("accountService", AccountService.class);
			
			UserAccountModel user = accountService.getUserInfoNoStatus(Integer.parseInt(userId));
			
			if(user != null){
				LBAP_UserInfo info = convertUserInfo(user);
				regResponse.setUserInfo(info);
			}
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ��˻�ȡ�û�����:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ��˻�ȡ�û�����:", ex);
			retResult = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),ex.getMessage());//������Ӧ��Ϣ
		}
		return retResult;
	}
	/**
	 * 
	 * Title: getCurrentTerm<br>
	 * Description: <br>
	 *              <br>��ȡ���ڵ�ǰ����Ϣ
	 * @param dataPack
	 * @return
	 */
	public LBAP_DataPack getCurrentTerm(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		try {
			LBAP_getCurrentTerm currentTerm = (LBAP_getCurrentTerm)dataPack;
			LBAP_getCurrentTermResp regResponse = new LBAP_getCurrentTermResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			
			String lotteryId = currentTerm.getLotteryId();
			/*
			 * У�����
			 */
			
			if(StringUtils.isEmpty(lotteryId) || !LotteryTools.isLotteryStart(Integer.parseInt(lotteryId))){
				throw new Exception("����ID����");
			}
			
			LotteryTermServiceInterf termService =  ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			
			List<LotteryTermModel> currentTermList = termService.queryTermCurrentInfo(Integer.parseInt(lotteryId), 3);
			
			//���û��ȥ����ǰ�ڣ���Ҫ�������������Ϣ
			LBAP_term lbap_term = new LBAP_term();
			if(currentTermList != null && !currentTermList.isEmpty()){
				int count = 0;
				List<LBAP_term.Term> termList = new ArrayList<LBAP_term.Term>();
				for(LotteryTermModel one : currentTermList){
					if(count++ == 0){//��������Ŀ�����Ϣ
						lbap_term.setWinTermNo(one.getBeforeWinTermNo());
						lbap_term.setPreTermNo("");
						lbap_term.setLotteryResult(one.getLotteryResult());
						lbap_term.setMissCount(one.getMissCount());
						lbap_term.setLimitNumber(one.getLimitNumber());
						lbap_term.setSalesVolume(one.getSalesVolume());
						lbap_term.setJackpot(one.getJackpot());
						lbap_term.setWinResult(LotteryTools.splitWinResult(Integer.parseInt(lotteryId), one.getLotteryInfo()));
					}
					
					LBAP_term.Term oneTerm = new LBAP_term().getTerm();
					oneTerm.setChangeLine(LbapUtils.TimeStamp2String(one.getChangeLine()));
					oneTerm.setDeadLine(LbapUtils.TimeStamp2String(one.getDeadLine()));
					oneTerm.setDeadLine2(LbapUtils.TimeStamp2String(one.getDeadLine2()));
					oneTerm.setDeadLine3(LbapUtils.TimeStamp2String(one.getDeadLine3()));
					oneTerm.setReserve(one.getReserve1());
					oneTerm.setSaleStatus(one.getSaleStatus());
					oneTerm.setStartTime(LbapUtils.TimeStamp2String(one.getStartTime()));
					oneTerm.setStartTime2(LbapUtils.TimeStamp2String(one.getStartTime2()));
					oneTerm.setTermNo(one.getTermNo());
					oneTerm.setTermStatus(one.getTermStatus());
					oneTerm.setWinLine(LbapUtils.TimeStamp2String(one.getWinLine()));
					oneTerm.setWinLine2(LbapUtils.TimeStamp2String(one.getWinLine2()));
					oneTerm.setWinStatus(one.getWinStatus());
					oneTerm.setGameInfo(LotteryTools.splitSalesInfo(Integer.parseInt(lotteryId), one.getLotteryInfo()));
					termList.add(oneTerm);
				}
				lbap_term.setTermList(termList);
				
			}else{
				
				LotteryTermModel one = termService.queryTermLastCashInfo(Integer.parseInt(lotteryId));
				if(one != null){
					
					one.setLotteryInfo(LotteryTools.splitTermAllResult(
							Integer.parseInt(lotteryId), one.getLotteryResult(), one.getWinResult(),
							one.getSalesInfo(),one.getMissCount()));//���ֵĿ�����Ϣ����������������Ϣ,��©��Ϣ
					
					lbap_term.setWinTermNo(one.getTermNo());
					
					lbap_term.setLotteryResult(one.getLotteryResult());
					lbap_term.setMissCount(one.getMissCount());
					lbap_term.setLimitNumber(one.getLimitNumber());
					lbap_term.setSalesVolume(one.getSalesVolume());
					lbap_term.setJackpot(one.getJackpot());
					
					lbap_term.setWinResult(LotteryTools.splitWinResult(Integer.parseInt(lotteryId), one.getLotteryInfo()));
					//��ȡ���һ��Ԥ���ں�
					List<String> nextTermNoList = termService.queryCanAddTermNo(Integer.parseInt(lotteryId),1);
					if(nextTermNoList != null && ! nextTermNoList.isEmpty()){
						lbap_term.setPreTermNo(nextTermNoList.get(0));
					}
				}
				
			}
			regResponse.setLbapTerm(lbap_term);
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ��˻�ȡ��ǰ����Ϣ����:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ��˻�ȡ��ǰ����Ϣ����:", ex);
			retResult = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),ex.getMessage());//������Ӧ��Ϣ
		}
		return retResult;
	}
	
	/**
	 * 
	 * Title: getTermInfo<br>
	 * Description: <br>
	 *              <br>��ȡָ�����ڵ���Ϣ
	 * @param dataPack
	 * @return
	 */
	public LBAP_DataPack getTermInfo(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		try {
			LBAP_getTermInfo requestTerm = (LBAP_getTermInfo)dataPack;
			LBAP_getTermInfoResp regResponse = new LBAP_getTermInfoResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			
			String lotteryId = requestTerm.getLotteryId();
			/*
			 * У�����
			 */
			
			if(StringUtils.isEmpty(lotteryId) || !LotteryTools.isLotteryStart(Integer.parseInt(lotteryId))){
				throw new Exception("����ID����");
			}
			
			if(StringUtils.isEmpty(requestTerm.getTermNo())){
				throw new Exception("���ڴ���");
			}
			
			LotteryTermServiceInterf termService =  ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			
			LotteryTermModel TermInfo = termService.queryTermInfo(Integer.parseInt(lotteryId), requestTerm.getTermNo());
			
			//���û��ȥ����ǰ�ڣ���Ҫ�������������Ϣ
			LBAP_term lbap_term = new LBAP_term();
			if(TermInfo != null){
				List<LBAP_term.Term> termList = new ArrayList<LBAP_term.Term>();
				lbap_term.setLotteryId(String.valueOf(TermInfo.getLotteryId()));
				lbap_term.setLotteryResult(TermInfo.getLotteryResult());
				lbap_term.setMissCount(TermInfo.getMissCount());
				lbap_term.setLimitNumber(TermInfo.getLimitNumber());
				lbap_term.setSalesVolume(TermInfo.getSalesVolume());
				lbap_term.setJackpot(TermInfo.getJackpot());
				lbap_term.setWinResult(LotteryTools.splitWinResult(Integer.parseInt(lotteryId), TermInfo.getLotteryInfo()));
					
				LBAP_term.Term oneTerm = new LBAP_term().getTerm();
				oneTerm.setChangeLine(LbapUtils.TimeStamp2String(TermInfo.getChangeLine()));
				oneTerm.setDeadLine(LbapUtils.TimeStamp2String(TermInfo.getDeadLine()));
				oneTerm.setDeadLine2(LbapUtils.TimeStamp2String(TermInfo.getDeadLine2()));
				oneTerm.setDeadLine3(LbapUtils.TimeStamp2String(TermInfo.getDeadLine3()));
				oneTerm.setReserve(TermInfo.getReserve1());
				oneTerm.setSaleStatus(TermInfo.getSaleStatus());
				oneTerm.setStartTime(LbapUtils.TimeStamp2String(TermInfo.getStartTime()));
				oneTerm.setStartTime2(LbapUtils.TimeStamp2String(TermInfo.getStartTime2()));
				oneTerm.setTermNo(TermInfo.getTermNo());
				oneTerm.setTermStatus(TermInfo.getTermStatus());
				oneTerm.setWinLine(LbapUtils.TimeStamp2String(TermInfo.getWinLine()));
				oneTerm.setWinLine2(LbapUtils.TimeStamp2String(TermInfo.getWinLine2()));
				oneTerm.setWinStatus(TermInfo.getWinStatus());
				oneTerm.setGameInfo(LotteryTools.splitSalesInfo(Integer.parseInt(lotteryId), TermInfo.getLotteryInfo()));
				termList.add(oneTerm);
				
				lbap_term.setTermList(termList);
				
			}else{
				throw new Exception("δ��ѯ��ָ���Ĳ�����Ϣ");
			}
			regResponse.setLbapTerm(lbap_term);
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ��˻�ȡ������Ϣ����:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ��˻�ȡ������Ϣ����:", ex);
			retResult = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),ex.getMessage());//������Ӧ��Ϣ
		}
		return retResult;
	}
	/**
	 * 
	 * Title: lotteryBet<br>
	 * Description: <br>
	 *              <br>�ͻ���Ͷע
	 * @param dataPack
	 * @return
	 */
	public LBAP_DataPack lotteryBet(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		try {
			LBAP_bet requestBet = (LBAP_bet)dataPack;
			LBAP_betResp regResponse = new LBAP_betResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			
			int sourceType = requestBet.getSourceType();
			
			boolean isStop = (requestBet.getWinStopped() == null || requestBet.getWinStopped().equals("1"))?true:false;
			
			BetServiceInterf betService =  ApplicationContextUtils.getService("busLotteryBetService", BetServiceInterf.class);
			
			String betResult = null;
			
			switch (sourceType) {
			case 0://web
				betResult = betService.betWap(Integer.parseInt(requestBet.getUserId()),
						requestBet.getLotteryId(), requestBet.getPlayType(),
						requestBet.getBetType(), requestBet.getBetMultiple(),
						requestBet.getBetNum(), requestBet.getAmount(),
						requestBet.getCurrentTerm(), requestBet.getChaseInfo(),
						isStop, requestBet.getBetCode());
				break;
			case 1://sms
				betResult = betService.betSms(Integer.parseInt(requestBet.getUserId()),
						requestBet.getLotteryId(), requestBet.getCurrentTerm(),
						requestBet.getPlayType(), requestBet.getBetType(),
						requestBet.getBetMultiple(), requestBet.getBetCode());
				break;
			case 2://wap
				betResult = betService.betWap(Integer.parseInt(requestBet.getUserId()),
						requestBet.getLotteryId(), requestBet.getPlayType(),
						requestBet.getBetType(), requestBet.getBetMultiple(),
						requestBet.getBetNum(), requestBet.getAmount(),
						requestBet.getCurrentTerm(), requestBet.getChaseInfo(),
						isStop, requestBet.getBetCode());
				break;
			case 3://KJava
				betResult = betService.betClient(Integer.parseInt(requestBet.getUserId()),
						requestBet.getLotteryId(), requestBet.getPlayType(),
						requestBet.getBetType(), requestBet.getBetMultiple(),
						requestBet.getBetNum(), requestBet.getAmount(),
						requestBet.getCurrentTerm(), requestBet.getChaseInfo(),
						isStop, requestBet.getBetCode());
				break;
			default:
				break;
			}
			
			if(StringUtils.isNotEmpty(betResult)){
				//�ں�#�������#ע��#�ܽ��
				String [] resultArr = betResult.split("#");
				regResponse.setTermNo(resultArr[0]);
				regResponse.setPlanId(resultArr[1]);
				regResponse.setBetNum(Integer.parseInt(resultArr[2]));
				regResponse.setBetAmount(Integer.parseInt(resultArr[3]));
			}
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ���Ͷע����:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ���Ͷע����:", ex);
			retResult = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),ex.getMessage());//������Ӧ��Ϣ
		}
		return retResult;
	}
	/**
	 * 
	 * Title: recharge<br>
	 * Description: <br>
	 *              <br>�ͻ��˳�ֵ
	 * @param dataPack
	 * @return
	 */
	public LBAP_DataPack recharge(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		try {
			LBAP_Recharge reqRecharge = (LBAP_Recharge)dataPack;
			LBAP_RechargeResp regResponse = new LBAP_RechargeResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			
			String userId = reqRecharge.getUserId();
			String billNo = reqRecharge.getBillNo();
			int amount = reqRecharge.getAmount();
			/*
			 * У�����
			 */
			
			if(StringUtils.isEmpty(userId)){
				throw new Exception("��ֵ�û�ID����Ϊ��");
			}
			if(StringUtils.isEmpty(billNo)){
				throw new Exception("��ֵ��ˮ�Ų���Ϊ��");
			}
			if(amount <= 0){
				throw new Exception("��ֵ���������0");
			}
			
			DrawMoneyInterf reChargeService =  ApplicationContextUtils.getService("busDrawMoneyService", DrawMoneyInterf.class);
			
			String reChargeResult = reChargeService
					.clientAdjustAccount(Long.parseLong(userId), amount,
							reqRecharge.getCommission(), reqRecharge
									.getClientId(), billNo, reqRecharge
									.getReserve());
			
			if(reChargeResult != null){
				//orderId#clientId#clientSeq#userId#time#fundsAccount#reserve
				try {
					String [] res = reChargeResult.split("#",-1);
					regResponse.setOrderId(res[0]);
					regResponse.setBillNo(res[2]);
					regResponse.setUserId(res[3]);
					regResponse.setTime(res[4]);
					regResponse.setFundsAccount(Integer.parseInt(res[5]));
					regResponse.setReserve(res[6]);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ��˳�ֵ����:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ��˳�ֵ����:", ex);
			retResult = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),ex.getMessage());//������Ӧ��Ϣ
		}
		return retResult;
	}
	/**
	 * 
	 * Title: drawMoney<br>
	 * Description: <br>
	 *              <br>�ͻ�����������
	 * @param dataPack
	 * @return
	 */
	public LBAP_DataPack drawMoney(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		try {
			LBAP_draw reqDraw = (LBAP_draw)dataPack;
			LBAP_drawResp regResponse = new LBAP_drawResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			
			String userId = reqDraw.getUserId();
			String billNo = reqDraw.getBillNo();
			int amount = reqDraw.getAmount();
			int commission = reqDraw.getCommission();
			/*
			 * У�����
			 */
			
			if(StringUtils.isEmpty(userId)){
				throw new Exception("�����û�ID����Ϊ��");
			}
			if(StringUtils.isEmpty(billNo)){
				throw new Exception("������ˮ�Ų���Ϊ��");
			}
			if(amount <= 0){
				throw new Exception("���ֽ��������0");
			}
			
			if(commission <= 0){
				throw new Exception("���������ѱ������0");
			}
			
			DrawMoneyInterf reChargeService =  ApplicationContextUtils.getService("busDrawMoneyService", DrawMoneyInterf.class);
			
			
			String reDrawResult = reChargeService.requestDrawPrizeMoney(Long
					.parseLong(userId), reqDraw.getBank(), reqDraw
					.getBankProvince(), reqDraw.getBankCity(), reqDraw
					.getBankName(), reqDraw.getBankCardId(), reqDraw
					.getCardUserName(), amount, commission,
					reqDraw.getReason(), reqDraw.getClientId(), billNo);
			
			if(reDrawResult != null){
				//ordered#clientId#clientSeq#userId#prizeAccount#reserve
				try {
					String [] res = reDrawResult.split("#",-1);
					regResponse.setOrderId(res[0]);
					regResponse.setBillNo(res[2]);
					regResponse.setUserId(res[3]);
					regResponse.setPrizeAccount(Integer.parseInt(res[4]));
					regResponse.setReserve(res[5]);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ������ֳ���:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ������ֳ���:", ex);
			retResult = new LBAP_Error("0009",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),ex.getMessage());//������Ӧ��Ϣ
		}
		return retResult;
	}
	/**
	 * 
	 * Title: writeLbapMsg<br>
	 * Description: <br>
	 *              <br>��¼���յ�����Ϣ
	 * @param clientid
	 * @param commandid
	 * @param messageid
	 * @param result
	 * @param messagebody
	 * @param reserve
	 * @return
	 */
	public String writeLbapMsg(String clientid,String commandid,String messageid,String result,String messagebody,String reserve,String encryptionType){
		String ret = "";
		try{
			LbapServiceInterf lbapService =  ApplicationContextUtils.getService("busLbapService", LbapServiceInterf.class);
			if(encryptionType.equals("1")){//��Ϣ��Ҫ����
				DESPlus des = new DESPlus(DEFAULT_KEY);//Ĭ����Կ
				//DESmessageBody = new String(LBAP_Util.sunBase64Decode(des.decrypt(messageBody)));
				messagebody = new String(des.decrypt(com.success.utils.Base64.decode(messagebody)));
			}
			ret = lbapService.insertLbapMsg(clientid, commandid, messageid, result, messagebody, reserve);
		}catch(Exception e){
			logger.error("�ͻ��˼�¼��Ϣ����:", e);
		}
		return ret;
	}
	/**
	 * 
	 * Title: getLbapMsg<br>
	 * Description: <br>
	 *              <br>��ѯ��Ϣ
	 * @param dataPack
	 * @return
	 */
	public LBAP_DataPack getLbapMsg(LBAP_DataPack dataPack){
		LBAP_DataPack retResult = null;
		try {
			LBAP_getMsg reqMsg = (LBAP_getMsg)dataPack;
			LBAP_getMsgResp regResponse = new LBAP_getMsgResp(dataPack.getMessageId(),isDES);//��Ӧ��Ϣ
			
			/*
			 * У�����
			 */
			
			if(StringUtils.isEmpty(reqMsg.getOperateCommand())){
				throw new Exception("��ѯ�����벻��Ϊ��");
			}
			if(StringUtils.isEmpty(reqMsg.getOperateMessageId())){
				throw new Exception("��ѯ��Ϣ��ˮ�Ų���Ϊ��");
			}
			
			
			LbapServiceInterf lbapService =  ApplicationContextUtils.getService("busLbapService", LbapServiceInterf.class);
			
			
			LbapMsgDomain ret = lbapService.getLbapMsg(reqMsg.getClientId(), reqMsg.getOperateCommand(), reqMsg.getOperateMessageId());
			
			if(ret != null){
				regResponse.setMsgResult(ret.getResult());
				regResponse.setOldMsg(ret.getMessagebody());
			}else{
				return new LBAP_Error("0998",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId());//������Ӧ��Ϣ
			}
			
			retResult = regResponse;
			regResponse.setResult("0000");
		} catch (LotteryException e) {
			logger.error("�ͻ��˲�ѯ��Ϣ����:", e);
			retResult = new LBAP_Error(String.valueOf(e.getType()),dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId(),e.getMessage());//������Ӧ��Ϣ
		}catch(Exception ex){
			logger.error("�ͻ��˲�ѯ��Ϣ����:", ex);
			retResult = new LBAP_Error("0999",dataPack.getCommand(),dataPack.getClientId(),dataPack.getMessageId());//������Ӧ��Ϣ
		}
		return retResult;
	}
	/**
	 * 
	 * Title: convertUserInfo<br>
	 * Description: <br>
	 *              <br>ת���û�����
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private LBAP_UserInfo convertUserInfo(UserAccountModel user) throws Exception{
		LBAP_UserInfo info = new LBAP_UserInfo();
		if(user != null){
			info.setUserId(String.valueOf(user.getUserId()));
			info.setMobilePhone(user.getMobilePhone());
			info.setEmail(user.getEmail());
			info.setAreaCode(user.getAreaCode());
			info.setStatus(String.valueOf(user.getStatus()));
			info.setNickname(user.getNickName());
			info.setPhone(user.getPhone());
			info.setRealName(user.getRealName());
			info.setIdCard(user.getIdCard());
			info.setBankName(user.getBankName());
			info.setBankCardId(user.getBankCardId());
			info.setAddress(user.getAddress());
			info.setPostcode(user.getPostcode());
			info.setQq(user.getQq());
			info.setMsn(user.getMsn());
			info.setSex(String.valueOf(user.getSex()));
			Timestamp birthdayT = user.getBirthday();
			String birthdayStr = birthdayT == null? null : LbapUtils.Date2string("yyyyMMdd", birthdayT);
			info.setBirthday(birthdayStr);
			info.setLastLoginTime(LbapUtils.TimeStamp2String(user.getLastLoginTime()));
			info.setLastLoginIp(user.getLastLoginIP());
			String createTime = user.getCreateTime() == null ? null : LbapUtils.TimeStamp2String(user.getCreateTime());
			info.setCreateTime(createTime);
			info.setFundsAccount(String.valueOf(user.getFundsAccount()));
			info.setFrozenAccount(String.valueOf(user.getFrozenAccount()));
			info.setPrizeAccount(String.valueOf(user.getPrizeAccount()));
			info.setCommisionAccount(String.valueOf(user.getCommisionAccount()));
			info.setAdvanceAccount(String.valueOf(user.getAdvanceAccount()));
			info.setAwardAccount(String.valueOf(user.getAwardAccount()));
			info.setOtherAccount1(String.valueOf(user.getOtherAccount1()));
			info.setOtherAccount2(String.valueOf(user.getOtherAccount2()));
			info.setReserve(user.getReserve());
		}
		return info;
	}
	/**
	 * Title: main<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO �Զ����ɷ������

	}

}
