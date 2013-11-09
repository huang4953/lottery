/**
 * Title: BetService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-19 ����08:20:01
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.service.interf.BetServiceInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.domain.CpInfoDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.TermLog;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.business.service.impl
 * BetService.java
 * BetService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-19 ����08:20:01
 * 
 */

public class BetService implements BetServiceInterf {
	private static Log logger = LogFactory.getLog(BetService.class);
	private static TermLog logTk = TermLog.getInstance("TK");
	
	private static final int CANSALE_TERMSTATUS = 1;//�������۵ĵĲ���״̬
	private static final int CANSALE_SALESTATUS = 1;//�������۵�����״̬
	
	private static final int PLAN_TYPE = 0;//��Ϊ0 �������������ʱ��������
	
	private static final int TRANSACTION_SOURCE_TYPE = 2002;//����������Ͷע����
	private static final int TRANSACTION_TYPE_20002 = 20002;//�������׷��Ͷע����
	private static final int TRANSACTION_TYPE_10002 = 10002;//�������Ͷע�۳��ʽ�
	
	private static final int WEB_TYPE = 0;
	private static final int SMS_TYPE = 1;
	private static final int WAP_TYPE = 2;
	private static final int CLIENT_TYPE = 3;
	
	private boolean isPutQuery = false;//���ɶ���ʱ�Ƿ�ֱ��д��Ʊ���У�Ĭ��Ϊ��ֱ��д��Ʊ����
	
	private LotteryTermServiceInterf termService;//���ڷ���
	private AccountService userService;//�û��˻�����
	private BetPlanOrderServiceInterf orderService;//��������
	private BetTicketServiceInterf ticketService;//��Ʊ����

	/* (�� Javadoc)
	 *Title: betWeb
	 *Description: 
	 *            <br>��1��У��Ͷע�ַ�����ʽ�Ƿ���ȷ
	 *            <br>��2��������û��ʺ��Ƿ����
	 *            <br>��3���û����������Ƿ�������۸ò�Ʊ
	 *            <br>��4��У�鵱ǰ���Ƿ��������
	 *            <br>��5�����ݴ����betNum��amount����У���̨�����ע���ͽ���Ƿ�ʹ����һ�£��������������������-1��ʹ�ú�̨���������
	 *            <br>��6��У�鵥�ʶ�������Ƿ񳬹�����Ľ������
	 *            <br>��7�����ɷ����Ͷ���
	 *            <br>��7.1���ж��˻�����Ƿ��ã�����׷�������Ľ��
	 *            <br>��7.2�����ɷ����Ͷ���
	 *            <br>��7.3�����ն�����Ŷ����˻����
	 *            <br>��7.4��
	 *            <br> ���ϲ��������һ�������׳��쳣������������ع�
	 * @param userid
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betNum
	 * @param amount
	 * @param term
	 * @param supplementTerms
	 * @param winStopped
	 * @param betCode
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.BetServiceInterf#betWeb(long, int, int, int, int, long, long, java.lang.String, java.util.List, boolean, java.lang.String)
	 */
	public String betWeb(long userId,int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount, String term,
			Map<String,Integer> supplementTerms, boolean winStopped, String betCode)
			throws LotteryException {
		String result = this.bet(userId,null, lotteryId, playType, betType, betMultiple, betNum, amount, term, supplementTerms, winStopped, betCode, WEB_TYPE);
		return result;
	}
	/*
	 * (�� Javadoc)
	*Title: betWeb
	*Description: 
	* @param userIdentify
	* @param lotteryId
	* @param playType
	* @param betType
	* @param betMultiple
	* @param betNum
	* @param amount
	* @param term
	* @param supplementTerms
	* @param winStopped
	* @param betCode
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#betWeb(java.lang.String, int, int, int, int, long, long, java.lang.String, java.util.List, boolean, java.lang.String, boolean)
	 */
	public String betWeb(String userIdentify,int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount, String term,
			Map<String,Integer> supplementTerms, boolean winStopped, String betCode)
			throws LotteryException {
		String result = this.bet(-1,userIdentify, lotteryId, playType, betType, betMultiple, betNum, amount, term, supplementTerms, winStopped, betCode, WEB_TYPE);
		return result;
	}
	

	/* (�� Javadoc)
	 *Title: betWap
	 *Description: 
	 * @param userid
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betNum
	 * @param amount
	 * @param term
	 * @param supplementTerms
	 * @param winStopped
	 * @param betCode
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.BetServiceInterf#betWap(long, int, int, int, int, long, long, java.lang.String, java.util.List, boolean, java.lang.String)
	 */
	public String betWap(long userId, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount, String term,
			Map<String,Integer> supplementTerms, boolean winStopped, String betCode)
			throws LotteryException {
		String result = this.bet(userId,null, lotteryId, playType, betType, betMultiple, betNum, amount, term, supplementTerms, winStopped, betCode, WAP_TYPE);
		return result;
	}
	/*
	 * (�� Javadoc)
	*Title: betWap
	*Description: 
	* @param userIdentify
	* @param lotteryId
	* @param playType
	* @param betType
	* @param betMultiple
	* @param betNum
	* @param amount
	* @param term
	* @param supplementTerms
	* @param winStopped
	* @param betCode
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#betWap(java.lang.String, int, int, int, int, long, long, java.lang.String, java.util.List, boolean, java.lang.String, boolean)
	 */
	public String betWap(String userIdentify, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount, String term,
			Map<String,Integer> supplementTerms, boolean winStopped, String betCode)
			throws LotteryException {
		String result = this.bet(-1,userIdentify, lotteryId, playType, betType, betMultiple, betNum, amount, term, supplementTerms, winStopped, betCode,WAP_TYPE);
		return result;
	}
	
	/*
	 * (�� Javadoc)
	*Title: betSms
	*Description: 
	* @param MobilePhone
	* @param lotteryId
	* @param playType
	* @param betType
	* @param betMultiple
	* @param term
	* @param betCode
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#betSms(java.lang.String, int, int, int, int, boolean, java.lang.String, java.lang.String)
	 */
	public String betSms(String MobilePhone, int lotteryId, int playType, int betType, int betMultiple,String betCode) throws LotteryException {
		String term = this.getTermService().queryTermCurrentInfo(lotteryId).getTermNo();
		String result = this.bet(-1, MobilePhone, lotteryId, playType, betType, betMultiple, -1, -1, term, null, true, betCode,SMS_TYPE);
		return result;
	}
	
	public String betSms(long userId, int lotteryId, int playType, int betType, int betMultiple,String betCode) throws LotteryException {
		String term = this.getTermService().queryTermCurrentInfo(lotteryId).getTermNo();
		String result = this.bet(userId, null, lotteryId, playType, betType, betMultiple, -1, -1, term, null, true, betCode,SMS_TYPE);
		return result;
	}
	
	public String betSms(String MobilePhone, int lotteryId, String betTerm, int playType, int betType, int betMultiple, String betCode) throws LotteryException {
		String result = this.bet(-1, MobilePhone, lotteryId, playType, betType, betMultiple, -1, -1, betTerm, null, true, betCode,SMS_TYPE);
		return result;
	}
	public String betSms(long userId, int lotteryId, String betTerm, int playType, int betType, int betMultiple, String betCode) throws LotteryException {
		String result = this.bet(userId, null, lotteryId, playType, betType, betMultiple, -1, -1, betTerm, null, true, betCode,SMS_TYPE);
		return result;
	}
	
	public String betClient(long userId, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount, String term,
			Map<String,Integer> supplementTerms, boolean winStopped, String betCode)
			throws LotteryException {
		String result = this.bet(userId,null, lotteryId, playType, betType, betMultiple, betNum, amount, term, supplementTerms, winStopped, betCode, CLIENT_TYPE);
		return result;
	}
	

	/**
	 * 
	 * Title: bet<br>
	 * Description: <br>
	 *            Ͷע<br>
	 * @param userid
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betNum
	 * @param amount
	 * @param betTerm
	 * @param supplementTerms
	 * @param winStopped
	 * @param betCode
	 * @param source_type
	 * @return String
	 * @throws LotteryException
	 */
	private String bet(long userId,String userIdentify, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount, String betTerm,
			Map<String,Integer> supplementTerms, boolean winStopped, String betCode,int source_type) throws LotteryException{
		
		String result=null;
		try{
			logger.debug("Ͷע---��ʼ");
			/*
			 * ����ʱ���������Ϣ
			 */
			if(logger.isDebugEnabled()){
				logger.debug("Ͷע����:"
						+ this.paramToString(userId, userIdentify, lotteryId,
								playType, betType, betMultiple, betNum, amount,
								betTerm, supplementTerms, winStopped, betCode,source_type));
			}
			/*
			 * ��׷�ż����������ڽ��������׷�ż���
			 */
			TreeMap<String,Integer> sortSupplementTerms = null;
			if(supplementTerms != null && !supplementTerms.isEmpty()){
				sortSupplementTerms = new TreeMap<String,Integer>();
				sortSupplementTerms.putAll(supplementTerms);
			}
			/*
			 * У��Ͷע�ַ�����ʽ�Ƿ���ȷ
			 */
			this.checkBetFormat(lotteryId, playType, betType, betCode);
			logger.debug("Ͷע---Ͷע��ʽ��ȷ");
			/*
			 * У��ע���ͽ���Ƿ���ȷ,���ص��ǵ���������ע���ͽ�����������
			 */
			String zhuAndMoney = this.checkBetNumAndMoney(lotteryId, playType,betType, betNum, amount, betCode);
			logger.debug("Ͷע---ע���ͽ����ȷ");
			
			String zhuNum = zhuAndMoney.split("#")[0];//����������ע��
			int singleBetAmount = Integer.parseInt(zhuAndMoney.split("#")[1]);//���������Ľ�����������
			/*
			 * ���㱾��Ͷע�ܹ��Ľ�����׷�ӵ�������Ҳ��������
			 */
			int totalNeedMoney = this.getTotalNeedMoney(betMultiple, sortSupplementTerms, singleBetAmount);
			/*
			 * У��Ͷע�����ͽ���Ƿ񳬹�������
			 */
			this.checkBetMoneyLimit(lotteryId, playType, betType, betMultiple, betCode,sortSupplementTerms);
			logger.debug("Ͷע---Ͷע�����ͽ��û�г�������");
			/*
			 * ��ʼ�漰���ݿ�Ĳ���
			 */
			LotteryTermModel termInfo = this.getTermService().queryTermInfo(lotteryId, betTerm);
			/*
			 * У�鵱ǰ���Ƿ��������
			 */
			this.checkCurTermCanSales(termInfo,lotteryId, betTerm); 
			logger.debug("Ͷע---��ǰ�ڿ�������");
			
			/*
			 * У�����ƺ��벻��Ͷע
			 */
			//this.checkLimitNum(termInfo, lotteryId, betCode);
			//logger.debug("Ͷע---�޺�У��--����Ͷע");
			/*
			 * У���û��Ƿ����,�˻�����Ƿ񹻱���Ͷע��Ҫ,�������û��˻�����Ϣ
			 */
			UserAccountModel user = this.checkUserIsExistById(userId,userIdentify, totalNeedMoney);
			logger.debug("Ͷע---�û�����,����");
			String areaCode = user.getAreaCode();
			long zhangHuUserId = user.getUserId();
			/*
			 * У���û����ڵĵ����Ƿ���Թ���ָ���Ĳ�Ʊ
			 * 
			 */
			this.checkAreaCanSales(lotteryId, areaCode);
			logger.debug("Ͷע---������Թ���");
			/*
			 * ת������ΪͶע��������
			 */
			BetPlanDomain planDomain = this.convertInputParam(zhangHuUserId,
					areaCode, lotteryId, betTerm, playType, betType, betCode,
					winStopped, betMultiple, singleBetAmount, sortSupplementTerms,source_type);
			/*
			 * ���ɷ����Ͷ���,�õ��ķ��ؽ��Ϊ<�������,<�������#����>>
			 */
			BetOrderDomain chuPiaoOrder = null;
			Map<String, List<String>> planResult = this.getOrderService().insertBetPlanOrder(planDomain,chuPiaoOrder);
			
			String planId = planResult.entrySet().iterator().next().getKey();
			logger.debug("Ͷע---�����������ɳɹ�,�������:"+planId);
			List<String> orderList = planResult.entrySet().iterator().next().getValue();//��������б�,�������#����
			logger.debug("��������:" + orderList.size());
			/*
			 * �����˻��ʽ�
			 */
			this.frozenAccountCapital(lotteryId, zhangHuUserId, betMultiple,singleBetAmount, betTerm, orderList,sortSupplementTerms);
			logger.debug("Ͷע---�����ʽ����");
			/*
			 * �����Ƿ�ֱ�ӳ�Ʊ��ʶд��Ʊ����,ֻ�Ե�ǰ�ڵ�Ͷעд��Ʊ����,׷�ŵĲ�д��Ʊ����
			 * �ò��ֲ���Ӱ��ǰ�����������
			 * ���д��Ʊ����ʧ�ܣ�����Ҫ���¶�����״̬Ϊ'δ��ʼ'
			 * �ò��ֳ��������������κε����������߼�
			 */
			if(this.isPutQuery){//ֱ���ͳ�Ʊ����
				//�����Ʊ����
				boolean putQueryResult = true;//�ͳ�Ʊ���н��
				if(!putQueryResult){//�ͳ�Ʊʧ��
					//���¶�����״̬Ϊδ��ʼ
				}
			}
			result = betTerm + "#"+ planId + "#" + zhuNum + "#" + totalNeedMoney;
			logger.debug("Ͷע���:" + result);
			logger.debug("Ͷע---���");
		}catch(LotteryException e){
			throw e;
		}catch(Exception e){
			throw new LotteryException(E_14_CODE, E_14_DESC);
		}
		return result;
	}
	/**
	 * 
	 * Title: checkBetFormat<br>
	 * Description: <br>
	 *            У��Ͷע�����ʽ<br>
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betCode
	 * @throws LotteryException
	 */
	private void checkBetFormat(int lotteryId, int playType, int betType,String betCode) throws LotteryException{
		boolean result = false;
		try {
			result = LotteryTools.checkLotteryBetFormat(lotteryId, playType, betType, betCode);
			if(!result){
				throw new LotteryException(E_01_CODE,E_01_DESC);
			}
		} catch (Exception e) {
			throw new LotteryException(E_01_CODE,e.getMessage());
		}
	}
	/**
	 * 
	 * Title: checkBetNumAndMoney<br>
	 * Description: <br>
	 *            У��Ͷע����ע���ͽ���Ƿ���ȷ<br>
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betNum
	 * @param amount
	 * @param betCode
	 * @return ע��#���
	 * @throws LotteryException
	 */
	private String checkBetNumAndMoney(int lotteryId, int playType,
			int betType,long betNum, long amount,String betCode) throws LotteryException{
		try {
			String splitResult = LotteryTools.lotteryBetToSingle(lotteryId, playType, betType, betCode);
			if(betNum == -1 || amount == -1){
				return splitResult;
			}else{
				if(betNum != Long.parseLong(splitResult.split("#")[0]) || amount != Long.parseLong(splitResult.split("#")[1])){
					throw new LotteryException(E_05_CODE,E_05_DESC);
				}else{
					return splitResult;
				}
			}
		}catch(LotteryException e){
			throw e;
		} catch (Exception ex) {
			throw new LotteryException(E_06_CODE,ex.getMessage());
		}
	}
	/**
	 * 
	 * Title: checkBetMoneyLimit<br>
	 * Description: <br>
	 *            У�鵥����עͶע����Ƿ񳬹����޶�ֵ<br>
	 *            �������Ľ������Ϊ�ջ�Ϊ0��Ϊ��У��<br>
	 *            �÷�������Ͷע����������Ͷע����У�飬������ı�����û�г�����������ΪͶע��������û�г�������<br>
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betCode
	 * @param supplementTerms 
	 * @param betMultiple
	 * @throws LotteryException
	 */
	private void checkBetMoneyLimit(int lotteryId,int playType,
			int betType,int betMultiple,String betCode,Map<String,Integer> supplementTerms) throws LotteryException{
		try {
			String betNumLimit = LotteryTools.getLotteryBetNumLimit(lotteryId);//��ȡ����Ĳ��ֱ�������
			int maxBetMultiple = betMultiple;
			if(supplementTerms != null && !supplementTerms.isEmpty()){
				for(Map.Entry<String, Integer> oneTerm : supplementTerms.entrySet()){
					if(oneTerm.getValue() > maxBetMultiple){
						maxBetMultiple = oneTerm.getValue();
					}
				}
			}
			
			if (maxBetMultiple < 0
					|| (betNumLimit != null && !"".equals(betNumLimit.trim())
							&& !"0".equals(betNumLimit) && maxBetMultiple > Integer.parseInt(betNumLimit))) {// Ͷע������������
				throw new LotteryException(E_13_CODE, E_13_DESC);
			}
			
			
			String singleMoneyLimit = LotteryTools.getLotteryMoneyLimit(lotteryId);//������ע�������
			String multiMoneyLimit = LotteryTools.getLotteryMultiMoneyLimit(lotteryId);//�౶��ע�������
			/*
			 * ��������Ͷ౶�Ĳ�����û�ж�����߶���Ϊ0��մ�������Ϊ����У�飬ֱ�ӷ���
			 */
			if ((singleMoneyLimit == null || "".equals(singleMoneyLimit.trim()) || "0"
					.equals(singleMoneyLimit))
					&& (multiMoneyLimit == null
							|| "".equals(multiMoneyLimit.trim()) || "0"
							.equals(multiMoneyLimit))) {//����
				return;
			}
			
			String bets[] = betCode.split("\\^");//��ע���Ϊ��ע
			for(String bet : bets){
				String splitResult = LotteryTools.lotteryBetToSingle(lotteryId, playType, betType, bet);
				String amount = splitResult.split("#")[1];
				if(multiMoneyLimit != null && !"".equals(multiMoneyLimit) && Long.parseLong(multiMoneyLimit) > 0){//Ͷע�����������ڣ��౶��ע
					long realMoney = Long.parseLong(amount) * maxBetMultiple;
					if(realMoney > Long.parseLong(multiMoneyLimit)){
						throw new LotteryException(E_07_CODE,E_07_DESC);
					}
				}else if(singleMoneyLimit != null && !"".equals(singleMoneyLimit) && Long.parseLong(singleMoneyLimit) > 0){//Ͷע��������������,������ע
					if(Long.parseLong(amount) > Long.parseLong(singleMoneyLimit)){
						throw new LotteryException(E_07_CODE,E_07_DESC);
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new LotteryException(E_08_CODE,E_08_DESC);
		}catch(LotteryException ex){
			throw ex;
		}
	}
	
	/**
	 * 
	 * Title: checkUserIsExistById<br>
	 * Description: <br>
	 *            У���û��Ƿ���ڲ�У���û��˻��ڵĽ���Ƿ񹻱���Ͷע��Ҫ<br>
	 *            �����û����ڵ�����<br>
	 * @param userid
	 * @return UserAccountModel
	 * @throws LotteryException
	 */
	private UserAccountModel checkUserIsExistById(long userId, String userIdentify,int totalNeedAmount) throws LotteryException{
		UserAccountModel user = null;
		logger.debug("checkUserIsExistById in userId or userIdentify == "+userId+"#"+userIdentify);
		try {
			user = this.getUserService().getUserInfo(userId, userIdentify,totalNeedAmount);
		} catch (LotteryException e) {
			logger.error(e.getType()+e.getMessage(),e);
			throw this.convertException(e);
		}
		return user;
	}
	/**
	 * 
	 * Title: getSingleBetPrize<br>
	 * Description: <br>
	 *            ����Ͷע������Ľ������������Ľ��<br>
	 * @param betMultiple
	 * @param amount
	 * @return int
	 */
	private int getSingleBetPrize(int betMultiple,int amount){
		return amount * (betMultiple <= 0 ? 1 : betMultiple);
	}
	/**
	 * 
	 * Title: getTotalNeedMoney<br>
	 * Description: <br>
	 *            ���㱾��Ͷע�ܵĽ��<br>
	 * @param betMultiple ��ǰ�ڵı���
	 * @param supplementTerms ׷�ӵ���Ϣ
	 * @param amount ���������Ľ��
	 * @return int
	 */
	private int getTotalNeedMoney(int betMultiple,Map<String,Integer> supplementTerms,int amount){
		int TotalTermMultipe = betMultiple <=0?1:betMultiple;//�õ��ܵ�Ͷע�������������ĸ�����������׷�ӵ����
		if(supplementTerms != null && !supplementTerms.isEmpty()){
			for(Map.Entry<String, Integer> oneTerm : supplementTerms.entrySet()){
				TotalTermMultipe += oneTerm.getValue();
			}
		}
		
		int totalNeedMoney = this.getSingleBetPrize(TotalTermMultipe, amount);//�ܹ���Ҫ���˻����
		return totalNeedMoney;
	}
	/**
	 * 
	 * Title: frozenAccountCapital<br>
	 * Description: <br>
	 *            ���ն�����Ŷ����˻��ʽ�<br>
	 * @param userId �û��˻�id
	 * @param amount ÿ�������Ľ����������Ľ��
	 * @param betOrderId �������
	 * @param lotteryId
	 * @param betMultiple
	 * @param curtermNo
	 * @param sortSupplementTerms
	 * @throws LotteryException
	 */
	private void frozenAccountCapital(int lotteryId,long userId,int betMultiple, int amount,String curtermNo,List<String> betOrderId,Map<String,Integer> sortSupplementTerms) throws LotteryException{
		logger.debug("Ͷע---�����ʽ�");
		for(String orderid : betOrderId){
			logger.debug("Ͷע---�����ʽ�---���ʶ��ᶩ����ź�����:"+orderid);
			String curOrderId = orderid.split("#")[0];
			String termNO = orderid.split("#")[1];
			
			try {
				if(curtermNo.equals(orderid.split("#")[1])){//��ǰ�ڵ�ֱ�ӿ۳�
					int frozen = this.getSingleBetPrize(betMultiple, amount);
					logger.debug("Ͷע---�����ʽ�---�۳���ǰ�ڱ���");
					this.getUserService().accountTransaction(
							userId,
							TRANSACTION_TYPE_10002,
							frozen,
							TRANSACTION_SOURCE_TYPE,
							curOrderId,
							LotteryTools.accountTransactionRemark(lotteryId,
									termNO, TRANSACTION_TYPE_10002));
					logger.debug("Ͷע---�����ʽ�---���ʿ۳����:"+frozen);
				}else{//׷���ڵ��ȶ���
					int frozen = this.getSingleBetPrize(sortSupplementTerms.get(termNO), amount);
					logger.debug("Ͷע---�����ʽ�---����׷���ʽ�");
					this.getUserService().accountTransaction(
							userId,
							TRANSACTION_TYPE_20002,
							frozen,
							TRANSACTION_SOURCE_TYPE,
							curOrderId,
							LotteryTools.accountTransactionRemark(lotteryId,
									termNO, TRANSACTION_TYPE_20002));
					logger.debug("Ͷע---�����ʽ�---���ʶ�����:"+frozen);
				}
			} catch (LotteryException e) {
				logger.error(e.getMessage(), e);
				throw this.convertException(e);
			}
			
		}
	}
	/**
	 * 
	 * Title: convertInputParam<br>
	 * Description: <br>
	 *            ת��Ͷע����Ϊʵ�����<br>
	 * @param userid
	 * @param areaCode
	 * @param lotteryId
	 * @param startTerm
	 * @param playType
	 * @param betType
	 * @param betCode
	 * @param winStoped
	 * @param betMultiple
	 * @param unitAmount
	 * @param planSource
	 * @return BetPlanDomain
	 * @throws LotteryException
	 */
	private BetPlanDomain convertInputParam(long userid, String areaCode,
			int lotteryId, String startTerm, int playType, int betType,
			String betCode,boolean winStoped,int betMultiple,int amount,Map<String,Integer> terms,int planSource) throws LotteryException {
		int unitAmount = this.getSingleBetPrize(betMultiple,amount);
		int winStop = winStoped ? 1: 0;
		BetPlanDomain plan = new BetPlanDomain();
		plan.setUserId(userid);
		plan.setAreaCode(areaCode);
		plan.setLotteryId(lotteryId);
		plan.setStartTerm(startTerm);
		plan.setPlayType(playType);
		plan.setBetType(betType);
		plan.setBetCode(betCode);
		plan.setWinStoped(winStop);
		plan.setBetMultiple(betMultiple);
		plan.setUnitAmount(unitAmount);
		plan.setPlanSource(planSource);
		plan.setPutQuery(this.isPutQuery);
		plan.setPlanType(PLAN_TYPE);
		plan.setTerms(terms);
		return plan;
	}
	
	/**
	 * 
	 * Title: convertException<br>
	 * Description: <br>
	 *            ת���˻�����쳣<br>
	 * @param e
	 * @return LotteryException
	 */
	private LotteryException convertException(LotteryException e){
		LotteryException ex = null;
		switch (e.getType()) {
		case AccountService.NOTFOUNDUSER:
			ex = new LotteryException(E_02_CODE,E_02_DESC);
			break;
		case AccountService.USERSTATUSERROR:
			ex = new LotteryException(E_11_CODE,E_11_DESC);
			break;
		case AccountService.NOTENOUGHMONEY:
			ex = new LotteryException(E_09_CODE,E_09_DESC);
			break;
		case AccountService.TRANSACTION10002EXCEPTION:
			ex = new LotteryException(E_10_CODE,E_10_DESC);
			break;
		case AccountService.TRANSACTION20002EXCEPTION:
			ex = new LotteryException(E_12_CODE,E_12_DESC);
			break;
		case AccountService.TRANSACTION20003EXCEPTION:
			ex = new LotteryException(E_21_CODE,E_21_DESC);
			break;
		case AccountService.TRANSACTION20005EXCEPTION:
			ex = new LotteryException(E_22_CODE,E_22_DESC);
			break;
		case AccountService.TRANSACTION30002EXCEPTION:
			ex = new LotteryException(E_23_CODE,E_23_DESC);
			break;
		default:
			ex = e;
			break;
		}
		return ex;
	}
	/**
	 * 
	 * Title: checkAreaIsSales<br>
	 * Description: <br>
	 *            У���û����������Ƿ��������ָ���Ĳ���<br>
	 * @param lotteryId
	 * @param areaCode
	 * @throws LotteryException
	 */
	private void checkAreaCanSales(int lotteryId,String areaCode) throws LotteryException{
		boolean result = false;
		result = LotteryTools.isAllowableUse(lotteryId, areaCode);
		if(!result){
			throw new LotteryException(E_03_CODE,E_03_DESC);
		}
	}
	/**
	 * 
	 * Title: checkCurTermCanSales<br>
	 * Description: <br>
	 *             У������Ƿ��������<br>
	 * @param lotteryId
	 * @param curTerm
	 * @throws LotteryException
	 */
	private void checkCurTermCanSales(LotteryTermModel term,int lotteryId,String curTerm) throws LotteryException{
		if(term.getTermStatus() != CANSALE_TERMSTATUS || term.getSaleStatus() != CANSALE_SALESTATUS){
			throw new LotteryException(E_04_CODE,E_04_DESC);
		}
	}
	/**
	 * 
	 * Title: checkLimitNum<br>
	 * Description: <br>
	 *              <br>У�����Ƶĺ��벻��Ͷע
	 * @param term
	 * @param lotteryId
	 * @param betCode
	 * @throws LotteryException
	 */
	private void checkLimitNum(LotteryTermModel term,int lotteryId,String betCode) throws LotteryException{
		switch (lotteryId) {
		case 1000003:
			String limitNumber = term.getLimitNumber();
			if(StringUtils.isNotEmpty(limitNumber) && StringUtils.isNotEmpty(betCode)){
				List<String> limitList = Arrays.asList(limitNumber.split(","));
				String [] betCodeArr = betCode.split("\\^");
				for(String oneBet : betCodeArr){
					if(limitList.contains(oneBet.replaceAll("\\*", ""))){
						throw new LotteryException(E_15_CODE,E_15_DESC.replaceAll("1", limitNumber));
					}
				}
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 
	 * Title: paramToString<br>
	 * Description: <br>
	 *            �鿴����<br>
	 * @param userId
	 * @param userIdentify
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betNum
	 * @param amount
	 * @param term
	 * @param supplementTerms
	 * @param winStopped
	 * @param betCode
	 * @param isAddMultiLimit
	 * @param source_type
	 * @return String
	 */
	private String paramToString(long userId,String userIdentify, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount, String term,
			Map<String,Integer> supplementTerms, boolean winStopped, String betCode,int source_type){
		StringBuffer sb = new StringBuffer();
		String sign = "#";
		sb.append("userId:").append(userId).append(sign).append("userIdentify:").append(userIdentify).append(sign);
		sb.append("lotteryId:").append(lotteryId).append(sign).append("playType:").append(playType).append(sign);
		sb.append("betType:").append(betType).append(sign).append("betMultiple:").append(betMultiple).append(sign);
		sb.append("betNum:").append(betNum).append(sign).append("amount:").append(amount).append(sign);
		sb.append("term:").append(term).append(sign).append("supplementTerms:").append(supplementTerms==null?"":supplementTerms.toString()).append(sign);
		sb.append("winStopped:").append(winStopped).append(sign).append("betCode:").append(betCode).append(sign);
		sb.append(sign).append("source_type:").append(source_type);
		return sb.toString();
	}
	/**
	 * 
	 * Title: coopParamToString<br>
	 * Description: <br>
	 *              <br>���������
	 * @return
	 */
	@SuppressWarnings("unused")
	private String coopParamToString(long userId, int lotteryId, int playType,
			int betType, int betMultiple, long betNum, long amount,
			String term, String betCode,int planopentype, int totalunit, int unitprice,
			int selfBuyNum, int unitbuyself, int commisionpercent,
			int plansource, String plantitle, String plandescription) {
		StringBuffer sb = new StringBuffer();
		String sign = "#";
		try {
			sb.append("�û�:").append(userId).append(sign);
			sb.append("����:").append(lotteryId).append(sign).append("�淨:").append(playType).append(sign);
			sb.append("Ͷע��ʽ:").append(betType).append(sign).append("����:").append(betMultiple).append(sign);
			sb.append("ע��:").append(betNum).append(sign).append("���:").append(amount).append(sign);
			sb.append("����:").append(term).append(sign).append("Ͷע��:").append(betCode).append(sign).append("���ܷ�ʽ:").append(planopentype).append(sign);
			sb.append("�ܷ���:").append(totalunit).append(sign).append("ÿ�ݵ���:").append(unitprice).append(sign);
			sb.append("�����Ϲ�:").append(selfBuyNum).append(sign).append("���׷���:").append(unitbuyself).append(sign);
			sb.append("���:").append(commisionpercent).append(sign).append("������Դ:").append(plansource).append(sign);
			sb.append("����:").append(plantitle).append(sign).append("���:").append(plandescription);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	

	private LotteryTermServiceInterf getTermService() {
		return this.termService;
	}

	public void setTermService(LotteryTermServiceInterf termService) {
		this.termService = termService;
	}
	
	private BetPlanOrderServiceInterf getOrderService() {
		return this.orderService;
	}

	public void setOrderService(BetPlanOrderServiceInterf orderService) {
		this.orderService = orderService;
	}

	private AccountService getUserService() {
		return this.userService;
	}

	public void setUserService(AccountService userService) {
		this.userService = userService;
	}
	public boolean isPutQuery() {
		return this.isPutQuery;
	}
	public void setPutQuery(boolean isPutQuery) {
		this.isPutQuery = isPutQuery;
	}
	/*
	 * (�� Javadoc)
	*Title: coopBetCreate
	*Description: 
	* @param userId ����id
	 * @param lotteryId ����
	 * @param playType �淨
	 * @param betType Ͷע��ʽ
	 * @param betMultiple Ͷע����
	 * @param betNum Ͷעע�� ��������ø�ֵҪ����-1
	 * @param amount Ͷע����λΪ��,��Ͷע���ݵĽ��(���ܰ���Ͷע�����Ľ��) ,��������ø�ֵҪ����-1
	 * @param term Ͷע����
	 * @param planopentype ����������ʽ 0-���� 1-����󹫿��� ? 2-��ֹ�󹫿�
	 * @param totalunit �ܷ���
	 * @param unitprice ÿ�ݵ���
	 * @param selfBuyNum �Լ��Ϲ�����
	 * @param unitbuyself ���׷���
	 * @param commisionpercent Ӷ������������н����Ӷ������������ã���д10��Ϊ 10%
	 * @param plansource Ͷע�����Ǵ��Ǹ��������ģ�0-WEB��1-SMS��2-WAP
	 * @param plantitle �������� �ɿ�
	 * @param plandescription �������� �ɿ�
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#coopBetCreate(long, int, int, int, int, long, long, java.lang.String, int, int, int, int, int, int, int, java.lang.String, java.lang.String)
	 */
	
	public String coopBetCreate(long userId, int lotteryId, int playType, int betType, int betMultiple, long betNum, long amount, String term, String betCode,int planOpenType, int totalUnit, int unitPrice, int selfBuyNum, int unitBuySelf, int commisionPercent, int planSource, String planTitle, String planDescription) throws LotteryException {
		String result=null;
		try{
			logger.debug("������Ͷע---��ʼ");
			if(logger.isDebugEnabled()){
				logger.debug("�����������"+this.coopParamToString(userId, lotteryId, playType, betType, betMultiple, betNum, amount, term,betCode,planOpenType, totalUnit, unitPrice, selfBuyNum, unitBuySelf, commisionPercent, planSource, planTitle, planDescription));
			}
			
			//У��Ӷ����������Ƿ���ȷ
			String sysDefinePercent = LotteryTools.getCoopPercent(lotteryId, betType);
			logger.debug("������Ͷע---ϵͳ������ɱ�����Χ:"+sysDefinePercent);
			if((Integer.parseInt(sysDefinePercent.split("-")[0]) <= commisionPercent) &&  (commisionPercent <= Integer.parseInt(sysDefinePercent.split("-")[1]))){
				logger.debug("������Ͷע---��ɱ���������ȷ");
			}else{
				logger.debug("������Ͷע---��ɱ������ô���");
				String tiChengBiLi = "";
				if(sysDefinePercent.split("-")[0].equals(sysDefinePercent.split("-")[1])){
					tiChengBiLi = sysDefinePercent.split("-")[1]+"%";
				}else{
					tiChengBiLi = sysDefinePercent+"%";
				}
				throw new LotteryException(BetServiceInterf.E_16_CODE,BetServiceInterf.E_16_DESC.replaceAll("A", tiChengBiLi));
			}
			//У��Ͷע�ַ�����ʽ�Ƿ���ȷ
			this.checkBetFormat(lotteryId, playType, betType, betCode);
			logger.debug("������Ͷע---Ͷע��ʽ��ȷ");
			
			//У��ע���ͽ���Ƿ���ȷ,���ص��ǵ���������ע���ͽ�����������
			String zhuAndMoney = this.checkBetNumAndMoney(lotteryId, playType,betType, betNum, amount, betCode);
			logger.debug("������Ͷע---ע���ͽ����ȷ");
			
			String zhuNum = zhuAndMoney.split("#")[0];//����������ע��
			int singleBetAmount = Integer.parseInt(zhuAndMoney.split("#")[1]);//���������Ľ�����������
			
			
			//У��Ͷע������ע�Ƿ񳬹����巶Χ����
			this.checkBetMoneyLimit(lotteryId, playType, betType, betMultiple, betCode,null);
			logger.debug("������Ͷע---Ͷע�����ͽ��û�г�������");
			//�����ܽ��
			int betTotalMoney = (betMultiple<=0?1:betMultiple) * singleBetAmount;
			
			//У���ܷ�����ÿһ�ݵĽ��
			if(totalUnit < 1){
				throw new LotteryException(BetServiceInterf.E_17_CODE,BetServiceInterf.E_17_DESC);
			}
			if( (betTotalMoney / totalUnit) != unitPrice ){
				throw new LotteryException(BetServiceInterf.E_18_CODE,BetServiceInterf.E_18_DESC);
			}
			logger.debug("������Ͷע---�ܷ�����ÿһ�ݽ����ȷ");
			//У�鷢���Ϲ��ݶ��Ƿ�ﵽҪ��,>5%
			if((selfBuyNum / (totalUnit*1.0))*100 < 5){
				throw new LotteryException(BetServiceInterf.E_19_CODE,BetServiceInterf.E_19_DESC);
			}
			logger.debug("������Ͷע---��������Ϲ�������ȷ");
			//У�鱣�׷����Ƿ�������ȷ
			if(unitBuySelf > 0){
				if(totalUnit == selfBuyNum){//�������Ѿ�ȫ���Ϲ��ˣ������ٱ���
					throw new LotteryException(BetServiceInterf.E_20_CODE,BetServiceInterf.E_20_DESC);
				}
				if((unitBuySelf / (totalUnit*1.0))*100 < 20){
					throw new LotteryException(BetServiceInterf.E_20_CODE,BetServiceInterf.E_20_DESC);
				}
			}
			logger.debug("������Ͷע---���׷���������ȷ");

			//���濪ʼ�漰���ݿ�Ĳ���
			LotteryTermModel termInfo = this.getTermService().queryTermInfo(lotteryId, term);
			
			//У�鵱ǰ���Ƿ��������
			this.checkCurTermCanSales(termInfo,lotteryId, term); 
			logger.debug("������Ͷע---��ǰ�ڿ�������");
			
			//У���û��Ƿ����,�˻�����Ƿ񹻱���Ͷע��Ҫ,�ʽ�Ϊ�����Ϲ�+����
			int selfRenGouMoney = unitPrice * selfBuyNum;//�������Ϲ����
			int baoDiMoney = unitPrice * (unitBuySelf > 0 ? unitBuySelf : 0);//������Ҫ�Ľ��
			int faQiTotalNeedMoney = selfRenGouMoney + baoDiMoney;//��������Ҫ������˻����
			UserAccountModel user = this.checkUserIsExistById(userId,null,faQiTotalNeedMoney);
			logger.debug("������Ͷע---�û�����,����");
			String areaCode = user.getAreaCode();
			logger.debug("������Ͷע---����������:"+areaCode);
			this.checkAreaCanSales(lotteryId, areaCode);
			logger.debug("������Ͷע---������Թ���");
			
			/*
			 * ������������
			 */
			
			//���ɺ��򷽰�,���ط������
			String planId = this.getOrderService().createCoopPlan(userId,areaCode, lotteryId, playType,
					betType, betMultiple, betTotalMoney, term, termInfo
							.getDeadLine2(), betCode, planOpenType, totalUnit,
					unitPrice, selfBuyNum, unitBuySelf, commisionPercent,
					planSource, planTitle, planDescription,0);
			logger.debug("������Ͷע---���ɷ����ɹ�,����id��"+planId);
			
			//���ɺ�����Ϣ��¼,���ֱ����Ա��״̬����Ϊ4
			String cpInfoId = this.getOrderService().createCoopInfo(planId, userId, lotteryId,
					term, playType, betType, 0, selfBuyNum, selfRenGouMoney,
					((totalUnit == selfBuyNum)?4:0));
			logger.debug("������Ͷע---���ɺ�����Ϣ�ɹ�,��Ϣid��"+cpInfoId);
			
			//�ȶ����ʽ��Ϲ��ʽ�
			try{
				//���ᷢ���Ϲ��ʽ�
				this.getUserService().accountTransaction(userId,20003,selfRenGouMoney,2001,
						planId,LotteryTools.accountTransactionRemark(lotteryId,term, 20003));
				logger.debug("������Ͷע---���ᷢ���Ϲ��ʽ�---������:"+selfRenGouMoney);
			}catch(LotteryException e){
				throw this.convertException(e);
			}
			
			
			if(totalUnit == selfBuyNum){//���𷽰��Ѿ���Ա,���ɺ����Ʊ�������۳��ʽ�
				String coopOrderId = this.getOrderService().createCoopBetOrder(planId, planSource, userId,areaCode, lotteryId, 
						term, playType, betType,0, betCode, betMultiple, betTotalMoney,0);
				//���·���Ϊ�Ѿ����ɶ���
				this.getOrderService().updateBetPlanStatus(planId, 1);
				logger.debug("������Ͷע---������Ա���ɺ����Ʊ�����ɹ�---����id:" + coopOrderId);
				
				//�۳������Ϲ��Ķ����ʽ�
				try{
					this.getUserService().accountTransaction(userId,30002,selfRenGouMoney,2001,
							coopOrderId,LotteryTools.accountTransactionRemark(lotteryId,term, 30002));
					
					logger.debug("������Ͷע---������Ա�۳������ʽ�---�۳����:"+selfRenGouMoney);
				}catch(LotteryException e){
					throw this.convertException(e);
				}
				
			}else{
				//���ᱣ���ʽ�,����ķ���û����Ա
				if(baoDiMoney > 0){//�б���
					String baodiFrozenId = this.getOrderService().createCoopInfo(planId, userId,
							lotteryId, term, playType, betType, 2,
							unitBuySelf, baoDiMoney, 5);
					logger.debug("������Ͷע---���ᱣ����Ϣ---��Ϣid:" + baodiFrozenId);
					try{
						this.getUserService().accountTransaction(userId,20009,baoDiMoney,2001,
								baodiFrozenId,LotteryTools.accountTransactionRemark(lotteryId,term, 20009));
						logger.debug("������Ͷע---���ᷢ�𱣵��ʽ�---������:"+baoDiMoney);
					}catch(LotteryException e){
						throw this.convertException(e);
					}
				}
			}
			
			result = term + "#"+ planId + "#" + zhuNum + "#" + betTotalMoney;
			logger.debug("������Ͷע���:" + result);
			logger.debug("������Ͷע---���");
			
		}catch(LotteryException e){
			logger.error("������Ͷע�����쳣:", e);
			throw e;
		}catch(Exception ex){
			logger.error("������Ͷע�����쳣", ex);
			throw new LotteryException(E_14_CODE, E_14_DESC);
		}
		return result;
	}
	
	/*
	 * (�� Javadoc)
	*Title: coopBetJoin
	*Description: 
	* @param userId
	* @param planId
	* @param cpUnit
	* @param amount
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#coopBetJoin(long, java.lang.String, int, long)
	 */
	public String coopBetJoin(long userId, String planId, int cpUnit, int amount) throws LotteryException {
		String coopInfoId = null;
		try{
			logger.debug("�������Ͷע---��ʼ,�����û�:"+userId+"���뷽��:"+planId+"�Ϲ�����:"+cpUnit+"�Ϲ����:"+amount);
			BetPlanDomain coopPlan = this.getOrderService().queryBetPlanForUpdate(planId);
			if(coopPlan == null){
				throw new LotteryException(BetServiceInterf.E_24_CODE,BetServiceInterf.E_24_DESC);
			}
			logger.debug("�������Ͷע---��������,����״̬:"+coopPlan.getPlanStatus());
			if(coopPlan.getPlanStatus() != 0){//�����Ѿ���������
				throw new LotteryException(BetServiceInterf.E_25_CODE,BetServiceInterf.E_25_DESC);
			}
			logger.debug("�������Ͷע---�������Բ���");
			//��������ͽ���Ƿ�ƥ��
			long unitPrize = coopPlan.getUnitPrice();
			if(cpUnit < 1){
				throw new LotteryException(BetServiceInterf.E_26_CODE,BetServiceInterf.E_26_DESC);
			}
			if(amount != (unitPrize * cpUnit)){
				throw new LotteryException(BetServiceInterf.E_27_CODE,BetServiceInterf.E_27_DESC);
			}
			logger.debug("�������Ͷע---�Ϲ����������ȷ,ÿһ�ݽ��:"+unitPrize);
			
			//��������+����ݶ��Ƿ񳬹�100%
			int selledUnit = coopPlan.getSelledUnit();
			int totalUnit = coopPlan.getTotalUnit();
			logger.debug("�������Ͷע---�Ϲ��󷽰��ܽ���:"+(selledUnit + cpUnit)+"�����ܷ���:"+totalUnit);
			if((selledUnit + cpUnit) > totalUnit){
				throw new LotteryException(BetServiceInterf.E_28_CODE,BetServiceInterf.E_28_DESC);
			}
			
			logger.debug("�������Ͷע---�Ϲ��󷽰����ᳬ��100%");
			
			//�˻��ʽ��Ƿ�
			UserAccountModel user = this.checkUserIsExistById(userId,null,amount);
			logger.debug("�������Ͷע---�û�����,����");
			String areaCode = user.getAreaCode();
			logger.debug("�������Ͷע--- �����û�����:"+areaCode);
			
			this.checkAreaCanSales(coopPlan.getLotteryId(), areaCode);
			logger.debug("�������Ͷע---������Թ���");
			
			if((selledUnit + cpUnit) == totalUnit){//���β���󷽰���Ա
				logger.debug("�������Ͷע---����󷽰�������Ա");
				//�ķ������Ⱥͷ���״̬��һ��Ҫ�ȸ�, ��ֹ�������ȡ
				int upPlanProgress = this.getOrderService()
						.updateBetPlanSelledUnit(planId, (selledUnit + cpUnit),1);
				logger.debug("�������Ͷע---��Ա����--���·�������״̬���,����״̬��Ϊ1");
				coopInfoId = this.getOrderService().createCoopInfo(planId, userId,coopPlan.getLotteryId(), 
						coopPlan.getStartTerm(), coopPlan.getPlayType(), coopPlan.getBetType(), 1,
						cpUnit, amount, 0);
				logger.debug("�������Ͷע---��Ա����--���ɲ�����Ϣ�ɹ�");
				
				this.getUserService().accountTransaction(userId,20005,amount,2001,coopInfoId,
						LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),coopPlan.getStartTerm(), 20005));
				logger.debug("�������Ͷע---��Ա����--��������ʽ�ɹ�");
				
				//ѭ�����к�����Ϣ
				List<CpInfoDomain> allCoopInfo = this.getOrderService().queryCoopInfoByPlanId(planId);
				logger.debug("�������Ͷע---��Ա����--�����ܵĲ�����Ϣ��:"+allCoopInfo.size());
				int dealInfoNum = 0;
				for(CpInfoDomain oneInfo : allCoopInfo){
					int orderType = oneInfo.getCpOrderType();
					int orderStatus = oneInfo.getOrderStatus();
					String infoId = oneInfo.getCpInfoId();
					logger.debug("�������Ͷע---��Ա����--���붩��:"+infoId+"��������:"+orderType+"����״̬:"+orderStatus);
					if(orderType == 2 && orderStatus == 5){//�����˱����ʽ�
						int upInfo = this.getOrderService().updateCoopInfoStatus(infoId, 6);
						logger.debug("�������Ͷע---���׶���״̬�޸ĳɹ�������:" + infoId);
						this.getUserService().accountTransaction(userId,31009,oneInfo.getCpAmount(),2001,infoId,
								LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),oneInfo.getBetTerm(), 31009));
						logger.debug("�������Ͷע---�����˱����ʽ����ɹ�,����:"+infoId);
						dealInfoNum++;
					}else{
						if(orderStatus == 0){//�����еĶ���
							this.getOrderService().updateCoopInfoStatus(infoId, 4);//״̬��Ϊ������Ա����Ͷע
							logger.debug("�������Ͷע---����״̬�޸ĳɹ�������:" + infoId);
							if(orderType == 0){//�������Ϲ�
								this.getUserService().accountTransaction(userId,30002,oneInfo.getCpAmount(),2001,infoId,
										LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),oneInfo.getBetTerm(), 30002));
							}else if(orderType == 1){//��������Ϲ�
								this.getUserService().accountTransaction(userId,30003,oneInfo.getCpAmount(),2001,infoId,
										LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),oneInfo.getBetTerm(), 30003));
							}
							logger.debug("�������Ͷע---�����ʽ����ɹ�,����:"+infoId);
							dealInfoNum++;
						}
					}
					logger.debug("�������Ͷע---������붩����:"+dealInfoNum);
				}
				//���ɳ�Ʊ����
				String chuPiaoOrderId = this.getOrderService().createCoopBetOrder(planId, coopPlan.getPlanSource(),
						coopPlan.getUserId(), coopPlan.getAreaCode(), coopPlan
								.getLotteryId(), coopPlan.getStartTerm(),
						coopPlan.getPlayType(), coopPlan.getBetType(), coopPlan
								.getBetCodeMode(), coopPlan.getBetCode(),
						coopPlan.getBetMultiple(), coopPlan.getUnitAmount(), 0);
				logger.debug("�������Ͷע---������Ա���ɳ�Ʊ�����ɹ�,����:" + chuPiaoOrderId);
				
			}else{//���β���󷽰�δ��Ա
				coopInfoId = this.getOrderService().createCoopInfo(planId, userId,coopPlan.getLotteryId(), 
						coopPlan.getStartTerm(), coopPlan.getPlayType(), coopPlan.getBetType(), 1,
						cpUnit, amount, 0);
				logger.debug("�������Ͷע---���ɲ�����Ϣ�ɹ�");
				int upSelledUnit = selledUnit + cpUnit;
				int upPlanResult = this.getOrderService()
						.updateBetPlanSelledUnit(planId, upSelledUnit,coopPlan.getPlanStatus());
				logger.debug("�������Ͷע---���·������ȳɹ�");
				try{
					this.getUserService().accountTransaction(userId,20005,amount,2001,coopInfoId,
							LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),coopPlan.getStartTerm(), 20005));
					logger.debug("�������Ͷע---��������ʽ�ɹ�");
				}catch(LotteryException e){
					throw this.convertException(e);
				}
				
			}
			logger.debug("�������Ͷע---����");
		}catch(LotteryException e){
			throw e;
		}catch(Exception ex){
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return coopInfoId;
	}
	
	/*
	 * (�� Javadoc)
	*Title: revocateJionOrder
	*Description: 
	* @param userId
	* @param coopInfoId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#revocateJionOrder(long, java.lang.String)
	 */
	public String revocateJionOrder(long userId, String coopInfoId) throws LotteryException {
		try{
			logger.debug("��������˳���---��ʼ,������:"+userId+"����id:"+coopInfoId);
			CpInfoDomain coopInfo = this.getOrderService().queryCoopInfoByIdForUpdate(coopInfoId);
			if(coopInfo == null){
				throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "�����˳�������δ�ҵ�������Ϣ"));
			}
			logger.debug("��������˳���---��������");
			if(userId != coopInfo.getUserId()){
				throw new LotteryException(BetServiceInterf.E_29_CODE,BetServiceInterf.E_29_DESC);
			}
			logger.debug("��������˳���---�û�һ��");
			if(coopInfo.getCpOrderType() == 1){
				throw new LotteryException(BetServiceInterf.E_37_CODE,BetServiceInterf.E_37_DESC);
			}
			logger.debug("��������˳���---���Ƿ����Ϲ�");
			if(coopInfo.getCpOrderType() == 2 || coopInfo.getCpOrderType() == 3){
				throw new LotteryException(BetServiceInterf.E_38_CODE,BetServiceInterf.E_38_DESC);
			}
			logger.debug("��������˳���---���Ǳ��׳���");
			if(coopInfo.getOrderStatus() != 0){
				logger.debug("��������˳���---����״̬:"+coopInfo.getOrderStatus());
				throw new LotteryException(BetServiceInterf.E_30_CODE,BetServiceInterf.E_30_DESC);
			}
			logger.debug("��������˳���---����״̬�ɳ�");
			String planId = coopInfo.getPlanId();
			BetPlanDomain coopPlan = this.getOrderService().queryBetPlanForUpdate(planId);//������������
			logger.debug("��������˳���---����״̬:"+coopPlan.getPlanStatus()+"�����ܷ���:"+coopPlan.getTotalUnit()+"�������Ϲ�:"+coopPlan.getSelledUnit());
			int planTotalUnit = coopPlan.getTotalUnit();
			int planSelledUnit = coopPlan.getSelledUnit();
			
			if(coopPlan.getPlanStatus() != 0){
				throw new LotteryException(BetServiceInterf.E_31_CODE,BetServiceInterf.E_31_DESC);
			}
			int sysDefineProgress = LotteryTools.getJoinRevocate(coopPlan.getLotteryId());
			if((((planSelledUnit*1.0)/planTotalUnit) * 100) >= sysDefineProgress){
				throw new LotteryException(BetServiceInterf.E_31_CODE,BetServiceInterf.E_31_DESC+",���������ѳ���:"+sysDefineProgress+"%");
			}
			logger.debug("��������˳���---����������볷������");
			
			//��ʼ���붩���ĳ���
			int newSelledUnit = planSelledUnit - coopInfo.getCpUnit();
			int upPlanResult = this.getOrderService().updateBetPlanSelledUnit(planId, newSelledUnit, coopPlan.getPlanStatus());
			if(upPlanResult <= 0){
				logger.error("��������˳���---���·���ʱδ���µ���Ӧ�ķ���");
				throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "���·���"));
			}
			logger.debug("��������˳���---���·������ȳɹ�");
			
			int upCoopInfoResult = this.getOrderService().updateCoopInfoStatus(coopInfoId, 3);
			if(upCoopInfoResult <=0){
				logger.error("��������˳���---���²��붩��ʱδ���µ���Ӧ����Ϣ");
				throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "��������"));
			}
			logger.debug("��������˳���---���²�����Ϣ״̬�ɹ�");
			
			try{
				this.getUserService().accountTransaction(userId,31005,coopInfo.getCpAmount(),2001,coopInfoId,
						LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31005));
				logger.debug("��������˳���---�ͷŶ����ʽ�ɹ�");
			}catch(LotteryException e){
				this.convertException(e);
			}
			logger.debug("��������˳���---���");
		}catch(LotteryException e){
			throw e;
		}catch(Exception ex){
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return coopInfoId;
	}
	/*
	 * (�� Javadoc)
	*Title: revocateCreatePlan
	*Description: �����˳�����
	* @param userId
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#revocateCreatePlan(long, java.lang.String)
	 */
	public String revocateCreatePlan(long userId, String planId) throws LotteryException {
		try{
			logger.debug("�������˳�������---��ʼ,�û�:"+userId+"����:"+planId);
			BetPlanDomain coopPlan = this.getOrderService().queryBetPlanForUpdate(planId);
			if(userId != coopPlan.getUserId()){
				throw new LotteryException(BetServiceInterf.E_29_CODE,BetServiceInterf.E_29_DESC);
			}
			logger.debug("�����˳�������---�����볷����һ��");
			if(coopPlan.getPlanStatus() != 0){
				throw new LotteryException(BetServiceInterf.E_30_CODE,BetServiceInterf.E_30_DESC);
			}
			logger.debug("�����˳�������---����״̬���Գ���");
			int sysDefinePlanRev = LotteryTools.getCreateRevocate(coopPlan.getLotteryId());
			
			logger.debug("�����˳�������---��������:"+((((coopPlan.getSelledUnit() + coopPlan
					.getUnitBuySelf())*1.0) / coopPlan.getTotalUnit()) * 100));
			if(((((coopPlan.getSelledUnit() + coopPlan
					.getUnitBuySelf())*1.0) / coopPlan.getTotalUnit()) * 100) >= sysDefinePlanRev){
				throw new LotteryException(BetServiceInterf.E_31_CODE,BetServiceInterf.E_31_DESC+",�������ȼӱ����ѳ���:"+sysDefinePlanRev+"%");
			}
			logger.debug("�����˳�������---�������ȿ��Գ���");
			this.destoryCoopPlan(planId, 1);
			logger.debug("�����˳�������---���");
			
		}catch(LotteryException e){
			logger.error("�����˳������������쳣,�쳣���:"+e.getType()+"�쳣ԭ��:"+e.getMessage());
			throw e;
		}catch(Exception ex){
			logger.error("�����˳����������������쳣:", ex);
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return planId;
	}
	/*
	 * (�� Javadoc)
	*Title: dealOnePlanBySys
	*Description: 
	* @param planId
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#dealOnePlanBySys(java.lang.String)
	 */
	public void dealOnePlanBySys(String planId) throws LotteryException {
		try{
			logger.debug("ϵͳ������---��ʼ����:"+planId);
			BetPlanDomain coopPlan = this.getOrderService().queryBetPlanForUpdate(planId);
			if(coopPlan != null && coopPlan.getPlanStatus() == 0){//������û���ύ��Ʊ
				int totalProgress = (coopPlan.getSelledUnit() + coopPlan.getUnitBuySelf());
				int totalUnit = coopPlan.getTotalUnit();
				List<CpInfoDomain> coopInfoList = this.getOrderService().queryCoopInfoByPlanId(planId);//�Ȳ鵽���еĲ�����Ϣ
				
				if(totalProgress >= totalUnit){//��������Ա
					int k =0;
					if(coopInfoList != null && coopInfoList.size() > 0){
						logger.debug("ϵͳ������---�����ӱ��׿����ɶ�����ʼ:"+planId +"�ܹ���Ҫ����Ķ�����:"+coopInfoList.size());
						for(CpInfoDomain coopInfo : coopInfoList){
							String coopInfoId = coopInfo.getCpInfoId();
							CpInfoDomain oneCoop = this.getOrderService().queryCoopInfoByIdForUpdate(coopInfoId);//һ��Ҫ��ס����
							logger.debug((++k) + "ϵͳ������---������붩��:"+oneCoop.getCpInfoId()+"��������:"+oneCoop.getCpOrderType()+"����״̬:"+oneCoop.getOrderStatus());
							if ((oneCoop.getCpOrderType() == 1
									|| oneCoop.getCpOrderType() == 0 || oneCoop
									.getCpOrderType() == 3)
									&& oneCoop.getOrderStatus() == 0) {//Ͷע���Ҷ������ǽ�����
								logger.debug("ϵͳ������---������붩����ʼ:"+oneCoop.getCpInfoId());
								this.getOrderService().updateCoopInfoStatus(oneCoop.getCpInfoId(), 2);
								
								try{
									this.getUserService().accountTransaction(oneCoop.getUserId(),31003,coopInfo.getCpAmount(),2001,oneCoop.getCpInfoId(),
											LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31003));
								}catch(LotteryException e){
									this.convertException(e);
								}
								logger.debug("ϵͳ������---������붩��:"+oneCoop.getCpInfoId()+"�ɹ�");
							}else if(oneCoop.getCpOrderType() == 2 && oneCoop.getOrderStatus() == 5){//���𱣵׶���δ����
								logger.debug("ϵͳ������---�����׶�����ʼ:"+oneCoop.getCpInfoId());
								this.getOrderService().updateCoopInfoStatus(oneCoop.getCpInfoId(), 6);
								try{
									this.getUserService().accountTransaction(oneCoop.getUserId(),31009,coopInfo.getCpAmount(),2001,oneCoop.getCpInfoId(),
											LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31009));
								}catch(LotteryException e){
									this.convertException(e);
								}
								//�����µı���תͶע
								int baodiUseUnit = (totalUnit - coopPlan.getSelledUnit());
								int baodiUseMoney = (new Long(baodiUseUnit * coopPlan.getUnitPrice())).intValue();
								logger.debug("ϵͳ������---����תͶע����:"+baodiUseUnit+"���:"+baodiUseMoney);
								String newCoopInfo = this.getOrderService().createCoopInfo(planId,
										oneCoop.getUserId(),
										oneCoop.getLotteryId(),
										oneCoop.getBetTerm(),
										oneCoop.getPlayType(),
										oneCoop.getBetType(), 3,
										baodiUseUnit,
										baodiUseMoney, 4);
								try{
									this.getUserService().accountTransaction(oneCoop.getUserId(),30006,baodiUseMoney,2001,newCoopInfo,
											LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 30006));
								}catch(LotteryException e){
									this.convertException(e);
								}
								logger.debug("ϵͳ������---�����׶���:"+oneCoop.getCpInfoId()+"�ɹ�");
							}
						}
						
						//���ɳ�Ʊ����
						String createChuPiaoOrder = this.getOrderService()
								.createCoopBetOrder(planId, coopPlan.getPlanSource(), coopPlan.getUserId(),
										coopPlan.getAreaCode(), coopPlan.getLotteryId(), coopPlan.getStartTerm(), coopPlan.getPlayType(),
										coopPlan.getBetType(), coopPlan.getBetCodeMode(), coopPlan.getBetCode(), coopPlan.getBetMultiple(),
										coopPlan.getUnitAmount(), 0);
						if(StringUtils.isEmpty(createChuPiaoOrder)){
							throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "ϵͳ��������δ����ȷ�����ɳ�Ʊ����"));
						}
						
						int upPlanStatus = this.getOrderService().updateBetPlanSelledUnit(planId, totalUnit, 1);//�÷���״̬Ϊ�Ѿ����ɳ�Ʊ����,��Ҫ���½���
						if(upPlanStatus <= 0){
							throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "ϵͳ��������δ����ȷ�ĸ��·����Ľ��Ⱥ�״̬"));
						}
					}else{
						throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "ϵͳ��������δ�ҵ�������Ӧ�Ķ�������"));
					}
				}else{//����������Ա
					logger.debug("ϵͳ������---�����ӱ��ײ������ɶ�����ʼ:"+planId);
					this.destoryCoopPlan(planId, 0);
					logger.debug("ϵͳ������---�����ӱ��ײ������ɶ������:"+planId);
				}
			}else{
				logger.debug("ϵͳ������---�����Ѿ����ɶ���������Ҫ�ٴ���");
			}
			logger.debug("ϵͳ������---���:"+planId);
		}catch(LotteryException e){
			logger.error("ϵͳ������򷽰������쳣,�쳣���:"+e.getType()+"�쳣ԭ��:"+e.getMessage());
			throw e;
		}catch(Exception ex){
			logger.error("ϵͳ������򷽰����������쳣:", ex);
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		
	}
	/**
	 * 
	 * Title: destoryCoopPlan<br>
	 * Description: <br>
	 *              <br>��ȫ������������,һ��Ҫ��֤�÷������Գ������ܵ���
	 * @param planId
	 * @param sysOrCreate ϵͳ�������߷����˳�����ϵͳ�����ã�0.�����˳�����:1
	 * @throws LotteryException
	 */
	private void destoryCoopPlan(String planId,int sysOrCreate) throws LotteryException{
		int destoryType = (sysOrCreate == 0)?1:2;
		//��ʼ����
		int upPlanStatus = this.getOrderService().updateBetPlanStatus(planId, 4);//�ȳ�������
		if(upPlanStatus <=0 ){
			throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "��������,���·���δ�ɹ�"));
		}
		logger.debug("��������---�޸ķ���״̬�ɹ�");
		List<CpInfoDomain> coopInfoList = this.getOrderService().queryCoopInfoByPlanId(planId);//�Ȳ鵽���еĲ�����Ϣ
		if(coopInfoList == null){
			throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "����������δ�鵽������Ӧ�Ĳ�����Ϣ"));
		}
		logger.debug("��������---�ܲ��붩����:"+coopInfoList.size());
		int k = 0;
		for(CpInfoDomain coopInfo : coopInfoList){
			String coopInfoId = coopInfo.getCpInfoId();
			CpInfoDomain oneCoop = this.getOrderService().queryCoopInfoByIdForUpdate(coopInfoId);//һ��Ҫ��ס����
			logger.debug((++k) + "��������---�������붩��:"+oneCoop.getCpInfoId()+"��������:"+oneCoop.getCpOrderType()+"����״̬:"+oneCoop.getOrderStatus());
			if ((oneCoop.getCpOrderType() == 1
					|| oneCoop.getCpOrderType() == 0 || oneCoop
					.getCpOrderType() == 3)
					&& oneCoop.getOrderStatus() == 0) {//Ͷע���Ҷ������ǽ�����
				this.getOrderService().updateCoopInfoStatus(oneCoop.getCpInfoId(), destoryType);
				
				try{
					this.getUserService().accountTransaction(oneCoop.getUserId(),31003,coopInfo.getCpAmount(),2001,oneCoop.getCpInfoId(),
							LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31003));
				}catch(LotteryException e){
					this.convertException(e);
				}
				logger.debug("��������---�������붩��:"+oneCoop.getCpInfoId()+"�ɹ�");
			}else if(oneCoop.getCpOrderType() == 2 && oneCoop.getOrderStatus() == 5){//���𱣵׶���δ����
				this.getOrderService().updateCoopInfoStatus(oneCoop.getCpInfoId(), 6);
				try{
					this.getUserService().accountTransaction(oneCoop.getUserId(),31009,coopInfo.getCpAmount(),2001,oneCoop.getCpInfoId(),
							LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31009));
				}catch(LotteryException e){
					this.convertException(e);
				}
				logger.debug("��������---�������׶���:"+oneCoop.getCpInfoId()+"�ɹ�");
			}
		}
	}
	/*
	 * (�� Javadoc)
	*Title: daiGouErrOrderDeal
	*Description: 
	* @param orderId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#daiGouErrOrderDeal(java.lang.String)
	 */
	public String daiGouErrOrderDeal(String orderId) throws LotteryException {
		BetOrderDomain orderInfo = null;
		int needTuiKuai = 0;//��Ҫ�˿����Ǯ��
		try{
			logger.debug("��Ʊʧ�ܶ����˿�---�˿��:"+orderId+"��ʼ");
			orderInfo = this.getOrderService().queryBetOrderByOrderIdForUpdate(orderId);//��ȡ��������������
			if(orderInfo == null){
				throw new LotteryException(BetServiceInterf.E_32_CODE,BetServiceInterf.E_32_DESC);
			}
			int orderStatus = orderInfo.getOrderStatus();
			int winStatus = orderInfo.getWinStatus();
			
			if((orderStatus != 4 && orderStatus != 6)){
				throw new LotteryException(BetServiceInterf.E_33_CODE,BetServiceInterf.E_33_DESC);
			}
			logger.debug("��Ʊʧ�ܶ����˿�---�˿��:"+orderId+"����״̬��ȷ,�����˿�");
			if(winStatus > 3){
				throw new LotteryException(BetServiceInterf.E_34_CODE,BetServiceInterf.E_34_DESC);
			}
			logger.debug("��Ʊʧ�ܶ����˿�---�˿��:"+orderId+"����û���˹���,�����˿�");
			//��������˿��
			
			if(orderStatus == 6){//����ȫ������
				logger.debug("��Ʊʧ�ܶ����˿�---�˿��:"+orderId+"����ȫ��Ʊʧ��");
				needTuiKuai = orderInfo.getBetAmount();
			}else if(orderStatus == 4){//����ʧ�ܣ����ֳɹ�
				logger.debug("��Ʊʧ�ܶ����˿�---�˿��:"+orderId+"��������Ʊʧ��");
				//��Ķ�����Ӧ������Ʊ
				List<BetTicketDomain> ticketList = this.getTicketService().getTickets(orderId);
				if(ticketList == null){
					throw new LotteryException(BetServiceInterf.E_35_CODE,BetServiceInterf.E_35_DESC);
				}
				for(BetTicketDomain oneTicket : ticketList){
					int ticketStatus = oneTicket.getTicketStatus();
					if(ticketStatus == 7){//��Ʊʧ��
						needTuiKuai += oneTicket.getBetAmount();
					}
				}
			}
			logger.debug("��Ʊʧ�ܶ����˿�---�˿��:"+orderId+"Ӧ���˿���:" + needTuiKuai);
			
			//���¶�����״̬��ʶ�����Ѿ������˿��
			int upWinStatus = (orderInfo.getWinStatus() * 100) + 99;
			int upOrderResult = this.getOrderService().updateBetOrderWinStatus(orderId, upWinStatus);
			logger.debug("��Ʊʧ�ܶ����˿�---�˿��:"+orderId+"����״̬��Ϊ:" + upWinStatus);
			if(upOrderResult <=0 ){
				throw new LotteryException(BetServiceInterf.E_36_CODE,BetServiceInterf.E_36_DESC);
			}
			logger.debug("��Ʊʧ�ܶ����˿�---�˿��:"+orderId+"�޸Ķ���״̬�ɹ�");
			//д�ʽ���
			this.getUserService().accountTransaction(orderInfo.getUserId(), 31010, needTuiKuai, 2002, orderInfo.getOrderId(),
					LotteryTools.accountTransactionRemark(orderInfo.getLotteryId(),
							orderInfo.getBetTerm(), 31010));
			logger.debug("��Ʊʧ�ܶ����˿�---�˿��:"+orderId+"�ʽ��׳ɹ�");
			logTk.logInfo(orderInfo.getLotteryId(), orderInfo.getBetTerm(), orderInfo.getOrderId(), String.valueOf(needTuiKuai));
		}catch(LotteryException e){
			logger.error("�������󶩵��˿���쳣:"+e.getType()+"��������:"+e.getMessage());
			throw e;
		}catch(Exception ex){
			logger.error("�������󶩵��˿���쳣���쳣ԭ��:", ex);
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return new StringBuffer().append(orderInfo.getLotteryId()).append("#").append(orderInfo.getBetTerm()).append("#")
		.append(orderInfo.getOrderId()).append("#").append(needTuiKuai).append("#").append("�˿�ɹ�").toString();
	}
	
	public BetTicketServiceInterf getTicketService() {
		return ticketService;
	}
	public void setTicketService(BetTicketServiceInterf ticketService) {
		this.ticketService = ticketService;
	}
	/*
	 * (�� Javadoc)
	*Title: heMaiErrOrderDeal
	*Description: 
	* @param orderId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#heMaiErrOrderDeal(java.lang.String)
	 */
	public String heMaiErrOrderDeal(String orderId) throws LotteryException {
		BetOrderDomain orderInfo = null;
		int needTuiKuai = 0;//��Ҫ�˿����Ǯ��
		StringBuffer tuiKuanBu = new StringBuffer();//�˿���
		try{
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"��ʼ");
			orderInfo = this.getOrderService().queryBetOrderByOrderIdForUpdate(orderId);//��ȡ��������������
			if(orderInfo == null){
				throw new LotteryException(BetServiceInterf.E_32_CODE,BetServiceInterf.E_32_DESC);
			}
			int orderStatus = orderInfo.getOrderStatus();
			int winStatus = orderInfo.getWinStatus();
			
			if((orderStatus != 4 && orderStatus != 6)){
				throw new LotteryException(BetServiceInterf.E_33_CODE,BetServiceInterf.E_33_DESC);
			}
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"����״̬��ȷ,�����˿�");
			if(winStatus > 3){
				throw new LotteryException(BetServiceInterf.E_34_CODE,BetServiceInterf.E_34_DESC);
			}
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"����û���˹���,�����˿�");
			//��������˿��
			
			if(orderStatus == 6){//����ȫ������
				logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"����ȫ��Ʊʧ��");
				needTuiKuai = orderInfo.getBetAmount();
			}else if(orderStatus == 4){//����ʧ�ܣ����ֳɹ�
				logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"��������Ʊʧ��");
				//�鶩����Ӧ������Ʊ
				List<BetTicketDomain> ticketList = this.getTicketService().getTickets(orderId);
				if(ticketList == null){
					throw new LotteryException(BetServiceInterf.E_35_CODE,BetServiceInterf.E_35_DESC);
				}
				for(BetTicketDomain oneTicket : ticketList){
					int ticketStatus = oneTicket.getTicketStatus();
					if(ticketStatus == 7){//��Ʊʧ��
						needTuiKuai += oneTicket.getBetAmount();
					}
				}
			}
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"Ӧ���˿���:" + needTuiKuai);
			
			//��ö�����Ӧ���������в������
			BetPlanDomain betPlan = this.getOrderService().queryBetPlanByPlanId(orderInfo.getPlanId());
			if(betPlan == null){
				throw new LotteryException(BetServiceInterf.E_39_CODE,BetServiceInterf.E_39_DESC);
			}
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"��Ӧ�ķ���:" + orderInfo.getPlanId());
			
			int totalCanYuFei = betPlan.getTotalUnit();//�ú�����ܷ���
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"�������ܷ���:" + totalCanYuFei);
			//����ÿһ��Ӧ���˵Ľ��
			BigDecimal oneFeiMoney = new BigDecimal(needTuiKuai).divide(new BigDecimal(totalCanYuFei));
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"ÿһ��Ӧ���˵Ľ��:" + oneFeiMoney);
			
			//ѭ���������еĲ�����Ӧ���˵�Ǯ
			List<CpInfoDomain> coopInfoList = this.getOrderService().queryCoopInfoByPlanId(orderInfo.getPlanId());
			
			if(coopInfoList == null || coopInfoList.isEmpty()){
				throw new LotteryException(BetServiceInterf.E_40_CODE,BetServiceInterf.E_40_DESC);
			}
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"�ܵĲ�������¼��:" + coopInfoList.size());
			String createUserInfoId = null;
			int coopTuiKuan = 0;//�������Ӧ���˵Ľ��
			int createUserPrize = 0;//��������Ӧ���˵�Ǯ
			int totalJoinUserPrize = 0;//�������˵���Ǯ��
			long faQiUserId = 0L;
			int coopLotteryId = orderInfo.getLotteryId();
			String betTerm = orderInfo.getBetTerm();
			
			tuiKuanBu.append("�ܹ���:").append((needTuiKuai*1.0)/100).append(",");
			for(CpInfoDomain oneCoop : coopInfoList){
				logger.info("�����Ʊʧ�ܶ����˿�,�����¼:"+oneCoop.getCpInfoId()+"����:"+oneCoop.getCpOrderType()+"״̬��"+oneCoop.getOrderStatus());
				if(oneCoop.getCpOrderType() == 0 && oneCoop.getOrderStatus() == 4){//�������Ĳ����Ѿ�����Ʊ
					logger.info("���������:" + +oneCoop.getUserId());
					createUserInfoId = oneCoop.getCpInfoId();
					faQiUserId = oneCoop.getUserId();
				}else if((oneCoop.getCpOrderType() == 1 || oneCoop.getCpOrderType() == 3) && oneCoop.getOrderStatus() == 4){//����Ͷע������תͶע
					logger.info("���������:" + oneCoop.getUserId());
					coopTuiKuan = (oneFeiMoney.multiply(new BigDecimal(oneCoop.getCpUnit()))).intValue();
					//д�ʽ���
					this.getUserService().accountTransaction(oneCoop.getUserId(), 31010, coopTuiKuan, 2002, oneCoop.getCpInfoId(),
							LotteryTools.accountTransactionRemark(coopLotteryId,betTerm, 31010));
					totalJoinUserPrize += coopTuiKuan;
					tuiKuanBu.append(oneCoop.getUserId()).append("��:").append((coopTuiKuan*1.0)/100).append(",");
					logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"������:"+oneCoop.getUserId()+"�˿�:" + coopTuiKuan);
				}
			}
			
			createUserPrize = needTuiKuai - totalJoinUserPrize;//��������Ӧ���˵�Ǯ
			this.getUserService().accountTransaction(faQiUserId, 31010, createUserPrize, 2002, createUserInfoId,
					LotteryTools.accountTransactionRemark(coopLotteryId,betTerm, 31010));
			tuiKuanBu.append(faQiUserId).append(":").append(createUserPrize);
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"�������˿�:" + createUserPrize);
			
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"�ʽ��׳ɹ�");
			//���¶�����״̬��ʶ�����Ѿ������˿��
			int upWinStatus = (orderInfo.getWinStatus() * 100) + 99;
			int upOrderResult = this.getOrderService().updateBetOrderWinStatus(orderId, upWinStatus);
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"����״̬��Ϊ:" + upWinStatus);
			if(upOrderResult <=0 ){
				throw new LotteryException(BetServiceInterf.E_36_CODE,BetServiceInterf.E_36_DESC);
			}
			logger.debug("�����Ʊʧ�ܶ����˿�---�˿��:"+orderId+"�޸Ķ���״̬�ɹ�");
			
			logTk.logInfo(orderInfo.getLotteryId(), orderInfo.getBetTerm(), orderInfo.getOrderId(), tuiKuanBu.toString());
		}catch(LotteryException e){
			logger.error("������󶩵��˿���쳣:"+e.getType()+"��������:"+e.getMessage());
			throw e;
		}catch(Exception ex){
			logger.error("������󶩵��˿���쳣���쳣ԭ��:", ex);
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return new StringBuffer().append(orderInfo.getLotteryId()).append("#").append(orderInfo.getBetTerm()).append("#")
		.append(orderInfo.getOrderId()).append("#").append(needTuiKuai).append("#").append(tuiKuanBu.toString()).append("#").append("�˿�ɹ�").toString();
	}
}
