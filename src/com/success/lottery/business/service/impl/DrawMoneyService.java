/**
 * Title: DrawMoneyService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-27 ����11:43:35
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account._99bill.sandbox.apipay.services.BatchPayWS.*;
import com.success.lottery.account.bill99.seashell.domain.dto.complatible.*;
import com.success.lottery.account.bill99.util.ParamOper;
import com.success.lottery.account.model.DrawMoneyDomain;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.AccountService;
import com.success.lottery.account.service.BaseDrawMoneyService;
import com.success.lottery.business.service.interf.DrawMoneyInterf;
import com.success.lottery.exception.LotteryException;
import com.success.utils.AutoProperties;

/**
 * com.success.lottery.business.service.impl
 * DrawMoneyService.java
 * DrawMoneyService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-27 ����11:43:35
 * 
 */

public class DrawMoneyService implements DrawMoneyInterf {
	private static Log logger = LogFactory.getLog(DrawMoneyService.class);
	
	private static final int DRAW_TYPE_PRIZE = 0;//��������
	private static final int DRAW_TYPE_ADJUST = 1;//�����ڲ�����
	private static final int DRAW_TYPE_CHONGZHI = 2;//�ͻ��˳�ֵ
	private static final int DRAWSTATUS_0 = 0;//�����ύ
	private static final int DRAWSTATUS_1 = 1;//����ͨ��
	private static final int DRAWSTATUS_2 = 2;//����ܾ�
	private static final int DRAWSTATUS_3 = 3;//��������
	private static final int DRAWSTATUS_4 = 4;//�������
	private static final int DRAWSTATUS_5 = 5;//�ͻ��˳�ֵ
	
	private static final int TRANSACTION_SOURCE_TYPE = 2003;//��������������
	private static final int TRANSACTION_TYPE_31007 = 31007;//������𣬽�����������ܾ����������ʽ�
	private static final int TRANSACTION_TYPE_30004 = 30004;//������𣬽����������
	private static final int TRANSACTION_TYPE_20007 = 20007;//������𣬽�����������
	
	private static final int TRANSACTION_SOURCE_TYPE_1003 = 1003;//�����������ڲ����ӵ���
	private static final int TRANSACTION_SOURCE_TYPE_1004 = 1004;//�����������ڲ����ٵ���
	private static final int TRANSACTION_TYPE_11005 = 11005;//������𣬱����ֵ
	private static final int TRANSACTION_TYPE_10001 = 10001;//������𣬱������
	private static final int TRANSACTION_TYPE_11001 = 11001;//������𣬱����ֵ
	private static Map<Integer,String> BILL_MAP=new HashMap<Integer,String>();//��Ǯ�����쳣MAP
	static{
		BILL_MAP.put(991000,"�����֤ʧ��");
		BILL_MAP.put(991001, "��Ϣ��д��ȫ");
		BILL_MAP.put(991002, "����ʺ���Ч");
		BILL_MAP.put(991003, "���ܷ�ʽ����ȷ");
		BILL_MAP.put(991004, "�տ�ʺ���Ч");
		BILL_MAP.put(991005, "�տ���ϵ��ʽ��Ч");
		BILL_MAP.put(991006, "�����������ܴ���100λ");
		BILL_MAP.put(991007, "�տ���޶�");
		BILL_MAP.put(991008, "���ô��ڸ�����");
		BILL_MAP.put(991009, "��ǩ����");
		BILL_MAP.put(991010, "IP ��ַ����");
		BILL_MAP.put(991011, "��ƥ��Ľӿ�����");
		BILL_MAP.put(991012, "֧������ʽ����ȷ");
		BILL_MAP.put(991014, "���п��Ų���Ϊ��");
		BILL_MAP.put(991005, "email ��mobile ��ʽ����ȷ");
		BILL_MAP.put(991016, "��ƥ��Ľ�������");
		BILL_MAP.put(991017, "���Ҵ������");
		BILL_MAP.put(991018, "���ܸ��Լ�����");
		BILL_MAP.put(991019, "��ѯ����Ϊ��");
		BILL_MAP.put(991020, "��ѯ������Ч");
		BILL_MAP.put(991021, "����������");
		BILL_MAP.put(991022, "δָ����ѯ��ʼʱ��");
		BILL_MAP.put(991023, "ʱ���ʽ����");
		BILL_MAP.put(991024, "��Ч����");
		BILL_MAP.put(991025, "�̼Ҷ���������0-9 a-z A-Z ��_���ַ����");
		BILL_MAP.put(991026, "��������ױ�������");
		BILL_MAP.put(992001, "�������Ʒ��쳣");
		BILL_MAP.put(992002, "�������쳣");
		BILL_MAP.put(992003, "�����˻�������");
		BILL_MAP.put(992004, "�����쳣");
		BILL_MAP.put(993001, "������Ȩ�Ļ�Ա");
		BILL_MAP.put(993002, "��ѯ�����Ϊ��");
		BILL_MAP.put(993003, "ƥ���տ�������");
		BILL_MAP.put(993004, "���ܸ��ǿ�Ǯ�û�֧��");
		BILL_MAP.put(993005, "֪ͨ�쳣");
		BILL_MAP.put(993006, "��ǩ����Ϊ��");
		BILL_MAP.put(993010, "������޶�");
		BILL_MAP.put(993014, "���ʸ�����޶�");
		BILL_MAP.put(993015, "�����տ���޶�");
		BILL_MAP.put(994007, "�����в���Ϊ��");
		BILL_MAP.put(994008, "ʡ�ݸ�ʽ����ȷ");
		BILL_MAP.put(994009, "���и�ʽ����ȷ");
		BILL_MAP.put(994013, "�������ƴ���");
		BILL_MAP.put(995201, "�������ܱ��˿�");
		BILL_MAP.put(995203, "���Ƕ�����������");
		BILL_MAP.put(995204, "�����˿�ʱ��");
		BILL_MAP.put(995211, "�����ű�������");
		BILL_MAP.put(995212, "�̼Ҷ������Ѿ�����");
		
		BILL_MAP.put(996001, "����");
		BILL_MAP.put(996003, "�տ��˻�������");
		BILL_MAP.put(996006, "�����������");
		BILL_MAP.put(997000, "���κű�����A-Z /0-9 ��_���ַ����");
		BILL_MAP.put(997001, "���κ��Ѿ�����");
		BILL_MAP.put(997002, "�����Ա�Ų���Ϊ��");
		BILL_MAP.put(997003, "�����˻�����Ϊ��");
		BILL_MAP.put(997004, "�����˻�������");
		BILL_MAP.put(997005, "�����˻�����������˻�");
		BILL_MAP.put(997006, "��Ա�ź��˻��Ų�ƥ��");
		BILL_MAP.put(997007, "��ƥ����ַ���");
		BILL_MAP.put(997008, "������Ϣ��ǩ����Ϊ��");
		BILL_MAP.put(997009, "������Ϣ��ǩ����");
	}
	
	private BaseDrawMoneyService baseDrawService;
	private AccountService accountService;

	/*
	 * (�� Javadoc)
	*Title: agreeDrawPrizeMoney
	*Description: 
	* @param drawId
	* @param opUser
	* @param reason
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.DrawMoneyInterf#agreeDrawPrizeMoney(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void agreeDrawPrizeMoney(String drawId, String opUser,String reason)
			throws LotteryException {
		
		
		DrawMoneyDomain drawDomain = null;
		if(StringUtils.isBlank(drawId)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "������ˮ��").replace("2", "����Ϊ��"));
		}
		if(StringUtils.isBlank(reason)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "��������ͨ��ԭ��").replace("2", "����Ϊ��"));
		}
		if(StringUtils.isBlank(opUser)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "����Ա").replace("2", "����Ϊ��"));
		}
		try {
			drawDomain = this.getBaseDrawService().queryDrawMoneyInfoForUpdate(drawId);
			if(drawDomain == null){
				throw new LotteryException(DrawMoneyInterf.E_03_CODE,DrawMoneyInterf.E_03_DESC);
			}
			
		}catch(LotteryException ex){
			throw ex;
		} catch (Exception e) {
			logger.error("agreeDrawPrizeMoney error", e);
			throw new LotteryException(DrawMoneyInterf.E_01_CODE,DrawMoneyInterf.E_01_DESC);
		}
		
		if(drawDomain != null && drawDomain.getDrawtype() == DRAW_TYPE_PRIZE && drawDomain.getDrawstatus() == DRAWSTATUS_0){
			this.getBaseDrawService().updateDrawMoney(drawId, opUser,DRAWSTATUS_1, reason, "");
			this.getAccountService().accountTransaction(drawDomain.getUserid(),
					TRANSACTION_TYPE_30004, drawDomain.getDrawmoney(),
					TRANSACTION_SOURCE_TYPE, drawDomain.getDrawid(), reason);
		}else{
			throw new LotteryException(DrawMoneyInterf.E_04_CODE,DrawMoneyInterf.E_04_DESC.replace("1", "ͨ��"));
		}
		String province_city =	drawDomain.getBankcity();
		String bankName = drawDomain.getBank();		
		String kaihuhang = drawDomain.getBankname();
		String creditName = drawDomain.getCardusername();		
		String bankCardNumber = drawDomain.getBankcardid();		
		double amount = drawDomain.getDrawmoney()*1.0/100;
		String description = "����Ӯ��ȡ��";			
		String orderId =drawDomain.getDrawid();
		String key = AutoProperties.getString("billBankPay.key", "","com.success.lottery.business.service.99billBankPay");
		String merchant_id=AutoProperties.getString("billBankPay.id", "","com.success.lottery.business.service.99billBankPay");
		String merchant_ip=AutoProperties.getString("billBankPay.ip", "","com.success.lottery.business.service.99billBankPay");
		StringBuffer signMsgVal=new StringBuffer();
		signMsgVal.append(bankCardNumber).append(amount).append(orderId).append(key);
		String mac = ParamOper.signMsg(signMsgVal.toString(),"utf-8");		
		BankRequestBean bankrequestbean = new BankRequestBean();
		bankrequestbean.setProvince_city(province_city);
		bankrequestbean.setBankName(bankName);
		bankrequestbean.setKaihuhang(kaihuhang);
		bankrequestbean.setCreditName(creditName);
		bankrequestbean.setBankCardNumber(bankCardNumber);
		bankrequestbean.setAmount(amount);
		bankrequestbean.setDescription(description);
		bankrequestbean.setOrderId(orderId);
		bankrequestbean.setMac(mac);
		BatchPayService locator = new BatchPayServiceLocator();
		try {
			BatchPay batchpayWS = locator.getBatchPayWS();
			BankResponseBean[] bankresponsebean= batchpayWS.bankPay(new BankRequestBean[] { bankrequestbean },merchant_id,merchant_ip);	///��д�̼ҵĸ����˻����󶨵ķ�����IP
			if(bankresponsebean==null||bankresponsebean.length==0)
			{
				logger.error("agreeDrawPrizeMoney:"+this.E_06_DESC);
				throw new LotteryException(this.E_06_CODE,"agreeDrawPrizeMoney:"+this.E_06_DESC);
			}
			signMsgVal=new StringBuffer();
			signMsgVal.append(bankresponsebean[0].getBankCardNumber()).append(bankresponsebean[0].getAmount()).append(bankresponsebean[0].getOrderId()).append(key);
			String mymac = ParamOper.signMsg(signMsgVal.toString(),"utf-8");
			if(!"0000".equals(bankresponsebean[0].getFailureCause()))
			{
				logger.error("agreeDrawPrizeMoney:"+this.BILL_MAP.get(Integer.parseInt(99+bankresponsebean[0].getFailureCause())));
				throw new LotteryException(Integer.parseInt(99+bankresponsebean[0].getFailureCause()),this.BILL_MAP.get(Integer.parseInt(99+bankresponsebean[0].getFailureCause())));
			}
		} catch (ServiceException e) {
			logger.error("agreeDrawPrizeMoney:"+this.E_06_DESC);
			throw new LotteryException(this.E_06_CODE,"agreeDrawPrizeMoney:"+this.E_06_DESC);
		}catch (RemoteException e) {
			logger.error("agreeDrawPrizeMoney:"+this.E_06_DESC);
			throw new LotteryException(this.E_06_CODE,"agreeDrawPrizeMoney:"+this.E_06_DESC);
		}

				
		
		
	}
	/*
	 * (�� Javadoc)
	*Title: rejectDrawPrizeMoney
	*Description: 
	* @param drawId
	* @param opUser
	* @param reason
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.DrawMoneyInterf#rejectDrawPrizeMoney(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectDrawPrizeMoney(String drawId,String opUser, String reason)
			throws LotteryException {
		DrawMoneyDomain drawDomain = null;
		if(StringUtils.isBlank(drawId)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "������ˮ��").replace("2", "����Ϊ��"));
		}
		if(StringUtils.isBlank(reason)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "��������ܾ�ԭ��").replace("2", "����Ϊ��"));
		}
		if(StringUtils.isBlank(opUser)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "����Ա").replace("2", "����Ϊ��"));
		}
		
		try {
			drawDomain = this.getBaseDrawService().queryDrawMoneyInfoForUpdate(drawId);
			if(drawDomain == null){
				throw new LotteryException(DrawMoneyInterf.E_03_CODE,DrawMoneyInterf.E_03_DESC);
			}
			
		}catch(LotteryException ex){
			throw ex;
		} catch (Exception e) {
			logger.error("rejectDrawPrizeMoney error", e);
			throw new LotteryException(DrawMoneyInterf.E_01_CODE,DrawMoneyInterf.E_01_DESC);
		}
		
		if(drawDomain != null && drawDomain.getDrawtype() == DRAW_TYPE_PRIZE && drawDomain.getDrawstatus() == DRAWSTATUS_0){
			this.getBaseDrawService().updateDrawMoney(drawId,opUser, DRAWSTATUS_2, reason, "");
			this.getAccountService().accountTransaction(drawDomain.getUserid(),
					TRANSACTION_TYPE_31007, drawDomain.getDrawmoney(),
					TRANSACTION_SOURCE_TYPE, drawDomain.getDrawid(), reason);
		}else{
			throw new LotteryException(DrawMoneyInterf.E_04_CODE,DrawMoneyInterf.E_04_DESC.replace("1", "�ܾ�"));
		}
	}

	/*
	 * (�� Javadoc)
	*Title: requestDrawPrizeMoney
	*Description: 
	* @param userId
	* @param bank
	* @param bankprovince
	* @param bankcity
	* @param bankname
	* @param bankcardid
	* @param cardusername
	* @param drawMoney
	* @param procedurefee
	* @param reason
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.DrawMoneyInterf#requestDrawPrizeMoney(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String)
	 */
	public String requestDrawPrizeMoney(long userId, String bank,
			String bankprovince, String bankcity, String bankname,
			String bankcardid, String cardusername, int drawMoney,
			int procedurefee, String reason)
			throws LotteryException {
		String drawId = this.requestDrawMoney(userId, bank, bankprovince, bankcity, bankname, bankcardid, cardusername, drawMoney, procedurefee, reason, "", "");
		return drawId;
	}
	
	private BaseDrawMoneyService getBaseDrawService() {
		return this.baseDrawService;
	}

	public void setBaseDrawService(BaseDrawMoneyService baseDrawService) {
		this.baseDrawService = baseDrawService;
	}

	public AccountService getAccountService() {
		return this.accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	/*
	 * (�� Javadoc)
	*Title: adjustAccount
	*Description: 
	* @param userId
	* @param adjustType
	* @param adjustMoney
	* @param reason
	* @param opUser
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.DrawMoneyInterf#adjustAccount(long, java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public String adjustAccount(long userId, String adjustType, int adjustMoney, String reason,String opUser) throws LotteryException {
		String drawId = "";
		/*
		 * ����У��
		 */
		if(StringUtils.isBlank(adjustType)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "���������ʶ").replace("2", "����Ϊ��"));
		}
		if(StringUtils.isBlank(reason)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "����ԭ��").replace("2", "����Ϊ��"));
		}
		if(StringUtils.isBlank(opUser)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "����Ա").replace("2", "����Ϊ��"));
		}
		if(!adjustType.equals("A") && !adjustType.equals("B")){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "���������ʶ").replace("2", "����ȷ"));
		}
		if(adjustMoney < 0){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "����������").replace("2", "����Ϊ����"));
		}
		
		/*
		 * д����������
		 */
		try{
			DrawMoneyDomain drawMoneyDomain = new DrawMoneyDomain();
			drawMoneyDomain.setUserid(userId);
			drawMoneyDomain.setDrawmoney(adjustMoney);
			drawMoneyDomain.setDrawreason(reason);
			drawMoneyDomain.setOpuser(opUser);
			if(adjustType.equals("A")){
				drawMoneyDomain.setDrawstatus(DRAWSTATUS_3);
			}else if(adjustType.equals("B")){
				drawMoneyDomain.setDrawstatus(DRAWSTATUS_4);
			}
			drawMoneyDomain.setDrawtype(DRAW_TYPE_ADJUST);
			drawId = this.getBaseDrawService().insertDrawMoney(drawMoneyDomain);
			if(StringUtils.isBlank(drawId)){
				throw new LotteryException(E_01_CODE,E_01_DESC);
			}
		}catch(Exception e){
			logger.error("adjustAccount error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		/*
		 * �����˻�����
		 */
		try {
			if(adjustType.equals("A")){//��������
				this.getAccountService().accountTransaction(userId, TRANSACTION_TYPE_11005, adjustMoney, TRANSACTION_SOURCE_TYPE_1003, drawId, reason);
			}else if(adjustType.equals("B")){//�������
				this.getAccountService().accountTransaction(userId, TRANSACTION_TYPE_10001, adjustMoney, TRANSACTION_SOURCE_TYPE_1004, drawId, reason);
			}
		}catch(LotteryException e){
			logger.error(e.getType()+e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("adjustAccount error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		
		return drawId;
	}
	/*
	 * (�� Javadoc)
	*Title: clientAdjustAccount
	*Description: 
	* @param userId
	* @param adjustMoney
	* @param adjustFee
	* @param clientId
	* @param clientSeq
	* @param reserve
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.DrawMoneyInterf#clientAdjustAccount(long, int, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String clientAdjustAccount(long userId, int adjustMoney, int adjustFee, String clientId, String clientSeq, String reserve) throws LotteryException {
		StringBuffer sb = new StringBuffer();
		String drawId = "";
		/*
		 * ����У��
		 */
		
		if(adjustMoney < 0){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "��ֵ���").replace("2", "����Ϊ����"));
		}
		
		if(adjustFee < 0){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "������").replace("2", "����Ϊ����"));
		}
		
		/*
		 * д����������
		 */
		String reason = clientId + "��ֵ";
		try{
			int frozenMoney = adjustMoney + adjustFee;
			DrawMoneyDomain drawMoneyDomain = new DrawMoneyDomain();
			drawMoneyDomain.setUserid(userId);
			drawMoneyDomain.setDrawmoney(frozenMoney);
			drawMoneyDomain.setProcedurefee(adjustFee);
			drawMoneyDomain.setDrawreason(reason);
			drawMoneyDomain.setDrawstatus(DRAWSTATUS_5);
			drawMoneyDomain.setDrawtype(DRAW_TYPE_CHONGZHI);
			drawMoneyDomain.setSource(clientId);
			drawMoneyDomain.setSourcesequence(clientSeq);
			drawMoneyDomain.setReserve(reserve);
			drawId = this.getBaseDrawService().insertDrawMoney(drawMoneyDomain);
			if(StringUtils.isBlank(drawId)){
				throw new LotteryException(E_01_CODE,E_01_DESC);
			}
		}catch(Exception e){
			logger.error("clientAdjustAccount error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		/*
		 * �����˻�����,��ֵ���ͻ��˳�ֵ
		 */
		try {
			this.getAccountService().accountTransaction(userId, TRANSACTION_TYPE_11001, adjustMoney, TRANSACTION_SOURCE_TYPE_1003, drawId, reason);
		}catch(LotteryException e){
			logger.error(e.getType()+e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("clientAdjustAccount error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		
		try{
			UserAccountModel user = this.getAccountService().getUserInfo(userId);
			Long fundsAccount = user.getFundsAccount();
			//orderId#clientId#clientSeq#userId#time#fundsAccount#reserve
			Date now = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			String nowStr = sf.format(now);
			sb.append(drawId).append("#").append(clientId).append("#").append(clientSeq).append("#").append(userId).append("#").append(nowStr).append("#").append(fundsAccount).append("#").append("");
		}catch(LotteryException e){
			throw e;
		}catch (Exception e) {
			logger.error("clientAdjustAccount error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		
		return sb.toString();
	}
	/*
	 * (�� Javadoc)
	*Title: requestDrawPrizeMoney
	*Description: 
	* @param userId
	* @param bank
	* @param bankprovince
	* @param bankcity
	* @param bankname
	* @param bankcardid
	* @param cardusername
	* @param drawMoney
	* @param procedurefee
	* @param reason
	* @param clientId
	* @param clientSeq
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.DrawMoneyInterf#requestDrawPrizeMoney(long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String requestDrawPrizeMoney(long userId, String bank, String bankprovince, String bankcity, String bankname, String bankcardid, String cardusername, int drawMoney, int procedurefee, String reason, String clientId, String clientSeq) throws LotteryException {
		StringBuffer sb = new StringBuffer();
		long prizeAccount = 0L;
		String drawId = this.requestDrawMoney(userId, bank, bankprovince, bankcity, bankname, bankcardid, cardusername, drawMoney, procedurefee, reason, clientId, clientSeq);
		
		/*
		 * ��ѯ�˻��������
		 */
		try {
			UserAccountModel user = this.getAccountService().getUserInfo(userId);
			prizeAccount = user.getPrizeAccount();
            //ordered#clientId#clientSeq#userId#prizeAccount#reserve
			sb.append(drawId).append("#").append(clientId).append("#").append(clientSeq).append("#").append(userId).append("#").append(prizeAccount).append("#").append("");
		}catch(LotteryException e){
			logger.error(e.getType()+e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("requestDrawPrizeMoney error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		
		return sb.toString();
	}
	/**
	 * 
	 * Title: requestDrawMoney<br>
	 * Description: <br>
	 *              <br>������������
	 * @param userId
	 * @param bank
	 * @param bankprovince
	 * @param bankcity
	 * @param bankname
	 * @param bankcardid
	 * @param cardusername
	 * @param drawMoney
	 * @param procedurefee
	 * @param reason
	 * @param clientId
	 * @param clientSeq
	 * @return
	 * @throws LotteryException
	 */
	private String requestDrawMoney(long userId, String bank, String bankprovince, String bankcity, String bankname, String bankcardid, String cardusername, int drawMoney, int procedurefee, String reason, String clientId, String clientSeq) throws LotteryException{
		String drawId = "";
		if(drawMoney <=0){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "���ֽ��").replace("2", "��Ҫ����0"));
		}
		if(procedurefee <= 0){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "����������").replace("2", "��Ҫ����0"));
		}
		if(StringUtils.isBlank(bank)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "������").replace("2", "����Ϊ��"));
		}
//		if(StringUtils.isBlank(bankprovince)){
//			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "������������ʡ").replace("2", "����Ϊ��"));
//		}
//		if(StringUtils.isBlank(bankcity)){
//			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "�����������ڳ���").replace("2", "����Ϊ��"));
//		}
		if(StringUtils.isBlank(bankname)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "����������").replace("2", "����Ϊ��"));
		}
		if(StringUtils.isBlank(bankcardid)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "���п���").replace("2", "����Ϊ��"));
		}
		if(StringUtils.isBlank(cardusername)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "�ֿ�������").replace("2", "����Ϊ��"));
		}
		if(StringUtils.isBlank(reason)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "��������ԭ��").replace("2", "����Ϊ��"));
		}
		int frozenMoney = drawMoney + procedurefee;
		try {
			DrawMoneyDomain drawMoneyDomain = new DrawMoneyDomain();
			drawMoneyDomain.setUserid(userId);
			drawMoneyDomain.setBank(bank);
			drawMoneyDomain.setBankprovince(bankprovince);
			drawMoneyDomain.setBankcity(bankcity);
			drawMoneyDomain.setBankname(bankname);
			drawMoneyDomain.setBankcardid(bankcardid);
			drawMoneyDomain.setCardusername(cardusername);
			drawMoneyDomain.setDrawmoney(frozenMoney);
			drawMoneyDomain.setProcedurefee(procedurefee);
			drawMoneyDomain.setDrawstatus(DRAWSTATUS_0);
			drawMoneyDomain.setDrawreason(reason);
			drawMoneyDomain.setDrawtype(DRAW_TYPE_PRIZE);
			drawMoneyDomain.setSource(clientId);
			drawMoneyDomain.setSourcesequence(clientSeq);
			
			drawId = this.getBaseDrawService().insertDrawMoney(drawMoneyDomain);
			if(StringUtils.isBlank(drawId)){
				throw new LotteryException(E_01_CODE,E_01_DESC);
			}
		} catch (Exception e) {
			logger.error("requestDrawPrizeMoney error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		
		try {
			this.getAccountService().accountTransaction(userId, TRANSACTION_TYPE_20007, frozenMoney, TRANSACTION_SOURCE_TYPE, drawId, reason);
		}catch(LotteryException e){
			logger.error(e.getType()+e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("requestDrawPrizeMoney error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		
		return drawId;
	}

}
