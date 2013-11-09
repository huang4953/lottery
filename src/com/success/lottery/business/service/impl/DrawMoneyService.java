/**
 * Title: DrawMoneyService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-27 上午11:43:35
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
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-27 上午11:43:35
 * 
 */

public class DrawMoneyService implements DrawMoneyInterf {
	private static Log logger = LogFactory.getLog(DrawMoneyService.class);
	
	private static final int DRAW_TYPE_PRIZE = 0;//奖金提现
	private static final int DRAW_TYPE_ADJUST = 1;//本金内部调整
	private static final int DRAW_TYPE_CHONGZHI = 2;//客户端充值
	private static final int DRAWSTATUS_0 = 0;//申请提交
	private static final int DRAWSTATUS_1 = 1;//申请通过
	private static final int DRAWSTATUS_2 = 2;//申请拒绝
	private static final int DRAWSTATUS_3 = 3;//本金增加
	private static final int DRAWSTATUS_4 = 4;//本金减少
	private static final int DRAWSTATUS_5 = 5;//客户端充值
	
	private static final int TRANSACTION_SOURCE_TYPE = 2003;//交易渠道，提现
	private static final int TRANSACTION_TYPE_31007 = 31007;//交易类别，奖金提现申请拒绝返还冻结资金
	private static final int TRANSACTION_TYPE_30004 = 30004;//交易类别，奖金提现完成
	private static final int TRANSACTION_TYPE_20007 = 20007;//交易类别，奖金提现申请
	
	private static final int TRANSACTION_SOURCE_TYPE_1003 = 1003;//交易渠道，内部增加调整
	private static final int TRANSACTION_SOURCE_TYPE_1004 = 1004;//交易渠道，内部减少调整
	private static final int TRANSACTION_TYPE_11005 = 11005;//交易类别，本金充值
	private static final int TRANSACTION_TYPE_10001 = 10001;//交易类别，本金减少
	private static final int TRANSACTION_TYPE_11001 = 11001;//交易类别，本金充值
	private static Map<Integer,String> BILL_MAP=new HashMap<Integer,String>();//快钱提现异常MAP
	static{
		BILL_MAP.put(991000,"身份验证失败");
		BILL_MAP.put(991001, "信息填写不全");
		BILL_MAP.put(991002, "付款方帐号无效");
		BILL_MAP.put(991003, "加密方式不正确");
		BILL_MAP.put(991004, "收款方帐号无效");
		BILL_MAP.put(991005, "收款联系方式无效");
		BILL_MAP.put(991006, "交易描述不能大于100位");
		BILL_MAP.put(991007, "收款超过限额");
		BILL_MAP.put(991008, "费用大于付款金额");
		BILL_MAP.put(991009, "验签错误");
		BILL_MAP.put(991010, "IP 地址不符");
		BILL_MAP.put(991011, "不匹配的接口类型");
		BILL_MAP.put(991012, "支付金额格式不正确");
		BILL_MAP.put(991014, "银行卡号不能为空");
		BILL_MAP.put(991005, "email 或mobile 格式不正确");
		BILL_MAP.put(991016, "不匹配的交易类型");
		BILL_MAP.put(991017, "货币代码错误");
		BILL_MAP.put(991018, "不能给自己付款");
		BILL_MAP.put(991019, "查询请求为空");
		BILL_MAP.put(991020, "查询类型无效");
		BILL_MAP.put(991021, "订单不存在");
		BILL_MAP.put(991022, "未指定查询起始时间");
		BILL_MAP.put(991023, "时间格式错误");
		BILL_MAP.put(991024, "无效数组");
		BILL_MAP.put(991025, "商家订单必须是0-9 a-z A-Z 和_的字符组合");
		BILL_MAP.put(991026, "超过最大交易笔数限制");
		BILL_MAP.put(992001, "服务器计费异常");
		BILL_MAP.put(992002, "服务处理异常");
		BILL_MAP.put(992003, "付款账户被冻结");
		BILL_MAP.put(992004, "其他异常");
		BILL_MAP.put(993001, "不是授权的会员");
		BILL_MAP.put(993002, "查询结果集为空");
		BILL_MAP.put(993003, "匹配收款人名称");
		BILL_MAP.put(993004, "不能给非快钱用户支付");
		BILL_MAP.put(993005, "通知异常");
		BILL_MAP.put(993006, "验签不能为空");
		BILL_MAP.put(993010, "付款超过限额");
		BILL_MAP.put(993014, "单笔付款超过限额");
		BILL_MAP.put(993015, "单笔收款超过限额");
		BILL_MAP.put(994007, "开户行不能为空");
		BILL_MAP.put(994008, "省份格式不正确");
		BILL_MAP.put(994009, "城市格式不正确");
		BILL_MAP.put(994013, "银行名称错误");
		BILL_MAP.put(995201, "订单不能被退款");
		BILL_MAP.put(995203, "不是订单的所有者");
		BILL_MAP.put(995204, "超出退款时限");
		BILL_MAP.put(995211, "订单号必须输入");
		BILL_MAP.put(995212, "商家订单号已经存在");
		
		BILL_MAP.put(996001, "余额不足");
		BILL_MAP.put(996003, "收款账户被冻结");
		BILL_MAP.put(996006, "交易引擎出错");
		BILL_MAP.put(997000, "批次号必须是A-Z /0-9 和_的字符组合");
		BILL_MAP.put(997001, "批次号已经存在");
		BILL_MAP.put(997002, "付款会员号不能为空");
		BILL_MAP.put(997003, "付款账户不能为空");
		BILL_MAP.put(997004, "付款账户不存在");
		BILL_MAP.put(997005, "付款账户不是人民币账户");
		BILL_MAP.put(997006, "会员号和账户号不匹配");
		BILL_MAP.put(997007, "不匹配的字符集");
		BILL_MAP.put(997008, "主题信息验签不能为空");
		BILL_MAP.put(997009, "主题信息验签错误");
	}
	
	private BaseDrawMoneyService baseDrawService;
	private AccountService accountService;

	/*
	 * (非 Javadoc)
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
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "提现流水号").replace("2", "不能为空"));
		}
		if(StringUtils.isBlank(reason)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "提现申请通过原因").replace("2", "不能为空"));
		}
		if(StringUtils.isBlank(opUser)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "操作员").replace("2", "不能为空"));
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
			throw new LotteryException(DrawMoneyInterf.E_04_CODE,DrawMoneyInterf.E_04_DESC.replace("1", "通过"));
		}
		String province_city =	drawDomain.getBankcity();
		String bankName = drawDomain.getBank();		
		String kaihuhang = drawDomain.getBankname();
		String creditName = drawDomain.getCardusername();		
		String bankCardNumber = drawDomain.getBankcardid();		
		double amount = drawDomain.getDrawmoney()*1.0/100;
		String description = "掌上赢家取现";			
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
			BankResponseBean[] bankresponsebean= batchpayWS.bankPay(new BankRequestBean[] { bankrequestbean },merchant_id,merchant_ip);	///填写商家的付款账户及绑定的服务器IP
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
	 * (非 Javadoc)
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
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "提现流水号").replace("2", "不能为空"));
		}
		if(StringUtils.isBlank(reason)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "提现申请拒绝原因").replace("2", "不能为空"));
		}
		if(StringUtils.isBlank(opUser)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "操作员").replace("2", "不能为空"));
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
			throw new LotteryException(DrawMoneyInterf.E_04_CODE,DrawMoneyInterf.E_04_DESC.replace("1", "拒绝"));
		}
	}

	/*
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
		 * 参数校验
		 */
		if(StringUtils.isBlank(adjustType)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "本金调整标识").replace("2", "不能为空"));
		}
		if(StringUtils.isBlank(reason)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "调整原因").replace("2", "不能为空"));
		}
		if(StringUtils.isBlank(opUser)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "操作员").replace("2", "不能为空"));
		}
		if(!adjustType.equals("A") && !adjustType.equals("B")){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "本金调整标识").replace("2", "不正确"));
		}
		if(adjustMoney < 0){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "本金调整金额").replace("2", "不能为负数"));
		}
		
		/*
		 * 写调整操作表
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
		 * 处理账户交易
		 */
		try {
			if(adjustType.equals("A")){//本金增加
				this.getAccountService().accountTransaction(userId, TRANSACTION_TYPE_11005, adjustMoney, TRANSACTION_SOURCE_TYPE_1003, drawId, reason);
			}else if(adjustType.equals("B")){//本金减少
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
	 * (非 Javadoc)
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
		 * 参数校验
		 */
		
		if(adjustMoney < 0){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "充值金额").replace("2", "不能为负数"));
		}
		
		if(adjustFee < 0){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "手续费").replace("2", "不能为负数"));
		}
		
		/*
		 * 写调整操作表
		 */
		String reason = clientId + "充值";
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
		 * 处理账户交易,充值，客户端充值
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
	 * (非 Javadoc)
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
		 * 查询账户奖金余额
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
	 *              <br>奖金提现申请
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
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "提现金额").replace("2", "需要大于0"));
		}
		if(procedurefee <= 0){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "提现手续费").replace("2", "需要大于0"));
		}
		if(StringUtils.isBlank(bank)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "开卡银").replace("2", "不能为空"));
		}
//		if(StringUtils.isBlank(bankprovince)){
//			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "开卡银行所在省").replace("2", "不能为空"));
//		}
//		if(StringUtils.isBlank(bankcity)){
//			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "开卡银行所在城市").replace("2", "不能为空"));
//		}
		if(StringUtils.isBlank(bankname)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "开户行名称").replace("2", "不能为空"));
		}
		if(StringUtils.isBlank(bankcardid)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "银行卡号").replace("2", "不能为空"));
		}
		if(StringUtils.isBlank(cardusername)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "持卡人姓名").replace("2", "不能为空"));
		}
		if(StringUtils.isBlank(reason)){
			throw new LotteryException(E_02_CODE,E_02_DESC.replace("1", "提现申请原因").replace("2", "不能为空"));
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
