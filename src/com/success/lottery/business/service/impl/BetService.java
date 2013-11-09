/**
 * Title: BetService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-19 下午08:20:01
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
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-19 下午08:20:01
 * 
 */

public class BetService implements BetServiceInterf {
	private static Log logger = LogFactory.getLog(BetService.class);
	private static TermLog logTk = TermLog.getInstance("TK");
	
	private static final int CANSALE_TERMSTATUS = 1;//可以销售的的彩期状态
	private static final int CANSALE_SALESTATUS = 1;//可以销售的销售状态
	
	private static final int PLAN_TYPE = 0;//设为0 ，代表代购，暂时不做合买
	
	private static final int TRANSACTION_SOURCE_TYPE = 2002;//交易渠道，投注订单
	private static final int TRANSACTION_TYPE_20002 = 20002;//交易类别，追号投注冻结
	private static final int TRANSACTION_TYPE_10002 = 10002;//交易类别，投注扣除资金
	
	private static final int WEB_TYPE = 0;
	private static final int SMS_TYPE = 1;
	private static final int WAP_TYPE = 2;
	private static final int CLIENT_TYPE = 3;
	
	private boolean isPutQuery = false;//生成订单时是否直接写出票队列，默认为不直接写出票队列
	
	private LotteryTermServiceInterf termService;//彩期服务
	private AccountService userService;//用户账户服务
	private BetPlanOrderServiceInterf orderService;//订单服务
	private BetTicketServiceInterf ticketService;//彩票服务

	/* (非 Javadoc)
	 *Title: betWeb
	 *Description: 
	 *            <br>（1）校验投注字符串格式是否正确
	 *            <br>（2）传入的用户帐号是否存在
	 *            <br>（3）用户所在区域是否可以销售该彩票
	 *            <br>（4）校验当前期是否可以销售
	 *            <br>（5）根据传入的betNum和amount参数校验后台计算的注数和金额是否和传入的一致，如果这两个参数都传入-1则使用后台计算的数据
	 *            <br>（6）校验单笔订单金额是否超过定义的金额限制
	 *            <br>（7）生成方案和订单
	 *            <br>（7.1）判断账户金额是否够用，包括追加期数的金额
	 *            <br>（7.2）生成方案和订单
	 *            <br>（7.3）按照订单编号冻结账户金额
	 *            <br>（7.4）
	 *            <br> 以上步骤如果有一个步骤抛出异常，则整个事务回滚
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
	 * (非 Javadoc)
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
	

	/* (非 Javadoc)
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
	 * (非 Javadoc)
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
	 * (非 Javadoc)
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
	 *            投注<br>
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
			logger.debug("投注---开始");
			/*
			 * 调试时输出参数信息
			 */
			if(logger.isDebugEnabled()){
				logger.debug("投注参数:"
						+ this.paramToString(userId, userIdentify, lotteryId,
								playType, betType, betMultiple, betNum, amount,
								betTerm, supplementTerms, winStopped, betCode,source_type));
			}
			/*
			 * 将追号集合排序用于解决订单的追号计数
			 */
			TreeMap<String,Integer> sortSupplementTerms = null;
			if(supplementTerms != null && !supplementTerms.isEmpty()){
				sortSupplementTerms = new TreeMap<String,Integer>();
				sortSupplementTerms.putAll(supplementTerms);
			}
			/*
			 * 校验投注字符串格式是否正确
			 */
			this.checkBetFormat(lotteryId, playType, betType, betCode);
			logger.debug("投注---投注格式正确");
			/*
			 * 校验注数和金额是否正确,返回的是单个订单的注数和金额，不包含倍数
			 */
			String zhuAndMoney = this.checkBetNumAndMoney(lotteryId, playType,betType, betNum, amount, betCode);
			logger.debug("投注---注数和金额正确");
			
			String zhuNum = zhuAndMoney.split("#")[0];//单个订单的注数
			int singleBetAmount = Integer.parseInt(zhuAndMoney.split("#")[1]);//单个订单的金额，不包含倍数
			/*
			 * 计算本次投注总共的金额，包括追加的期数，也包含倍数
			 */
			int totalNeedMoney = this.getTotalNeedMoney(betMultiple, sortSupplementTerms, singleBetAmount);
			/*
			 * 校验投注倍数和金额是否超过了限制
			 */
			this.checkBetMoneyLimit(lotteryId, playType, betType, betMultiple, betCode,sortSupplementTerms);
			logger.debug("投注---投注倍数和金额没有超过限制");
			/*
			 * 开始涉及数据库的操作
			 */
			LotteryTermModel termInfo = this.getTermService().queryTermInfo(lotteryId, betTerm);
			/*
			 * 校验当前期是否可以销售
			 */
			this.checkCurTermCanSales(termInfo,lotteryId, betTerm); 
			logger.debug("投注---当前期可以销售");
			
			/*
			 * 校验限制号码不能投注
			 */
			//this.checkLimitNum(termInfo, lotteryId, betCode);
			//logger.debug("投注---限号校验--可以投注");
			/*
			 * 校验用户是否存在,账户金额是否够本次投注需要,并返回用户账户的信息
			 */
			UserAccountModel user = this.checkUserIsExistById(userId,userIdentify, totalNeedMoney);
			logger.debug("投注---用户存在,金额够用");
			String areaCode = user.getAreaCode();
			long zhangHuUserId = user.getUserId();
			/*
			 * 校验用户所在的地区是否可以购买指定的彩票
			 * 
			 */
			this.checkAreaCanSales(lotteryId, areaCode);
			logger.debug("投注---区域可以购买");
			/*
			 * 转换参数为投注方案对象
			 */
			BetPlanDomain planDomain = this.convertInputParam(zhangHuUserId,
					areaCode, lotteryId, betTerm, playType, betType, betCode,
					winStopped, betMultiple, singleBetAmount, sortSupplementTerms,source_type);
			/*
			 * 生成方案和订单,得到的返回结果为<方案编号,<订单编号#期数>>
			 */
			BetOrderDomain chuPiaoOrder = null;
			Map<String, List<String>> planResult = this.getOrderService().insertBetPlanOrder(planDomain,chuPiaoOrder);
			
			String planId = planResult.entrySet().iterator().next().getKey();
			logger.debug("投注---方案订单生成成功,方案编号:"+planId);
			List<String> orderList = planResult.entrySet().iterator().next().getValue();//订单编号列表,订单编号#期数
			logger.debug("订单个数:" + orderList.size());
			/*
			 * 处理账户资金
			 */
			this.frozenAccountCapital(lotteryId, zhangHuUserId, betMultiple,singleBetAmount, betTerm, orderList,sortSupplementTerms);
			logger.debug("投注---冻结资金完成");
			/*
			 * 根据是否直接出票标识写出票队列,只对当前期的投注写出票队列,追号的不写出票队列
			 * 该部分不能影响前面操作的事务
			 * 如果写出票队列失败：则还需要更新订单的状态为'未开始'
			 * 该部分程序后不允许再添加任何的其他处理逻辑
			 */
			if(this.isPutQuery){//直接送出票队列
				//送入出票队列
				boolean putQueryResult = true;//送出票队列结果
				if(!putQueryResult){//送出票失败
					//更新订单的状态为未开始
				}
			}
			result = betTerm + "#"+ planId + "#" + zhuNum + "#" + totalNeedMoney;
			logger.debug("投注结果:" + result);
			logger.debug("投注---完成");
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
	 *            校验投注号码格式<br>
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
	 *            校验投注串的注数和金额是否正确<br>
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betNum
	 * @param amount
	 * @param betCode
	 * @return 注数#金额
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
	 *            校验单倍单注投注金额是否超过了限定值<br>
	 *            如果定义的金额限制为空或为0认为不校验<br>
	 *            该方法按照投注期数中最大的投注倍数校验，如果最大的倍数都没有超过限制则认为投注的期数都没有超过限制<br>
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
			String betNumLimit = LotteryTools.getLotteryBetNumLimit(lotteryId);//获取定义的彩种倍数限制
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
							&& !"0".equals(betNumLimit) && maxBetMultiple > Integer.parseInt(betNumLimit))) {// 投注倍数超过限制
				throw new LotteryException(E_13_CODE, E_13_DESC);
			}
			
			
			String singleMoneyLimit = LotteryTools.getLotteryMoneyLimit(lotteryId);//单倍单注金额限制
			String multiMoneyLimit = LotteryTools.getLotteryMultiMoneyLimit(lotteryId);//多倍单注金额限制
			/*
			 * 如果单倍和多倍的参数都没有定义或者定义为0或空串，则认为不做校验，直接返回
			 */
			if ((singleMoneyLimit == null || "".equals(singleMoneyLimit.trim()) || "0"
					.equals(singleMoneyLimit))
					&& (multiMoneyLimit == null
							|| "".equals(multiMoneyLimit.trim()) || "0"
							.equals(multiMoneyLimit))) {//单倍
				return;
			}
			
			String bets[] = betCode.split("\\^");//将注码拆为单注
			for(String bet : bets){
				String splitResult = LotteryTools.lotteryBetToSingle(lotteryId, playType, betType, bet);
				String amount = splitResult.split("#")[1];
				if(multiMoneyLimit != null && !"".equals(multiMoneyLimit) && Long.parseLong(multiMoneyLimit) > 0){//投注倍数计算在内，多倍单注
					long realMoney = Long.parseLong(amount) * maxBetMultiple;
					if(realMoney > Long.parseLong(multiMoneyLimit)){
						throw new LotteryException(E_07_CODE,E_07_DESC);
					}
				}else if(singleMoneyLimit != null && !"".equals(singleMoneyLimit) && Long.parseLong(singleMoneyLimit) > 0){//投注倍数金额不计算在内,单倍单注
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
	 *            校验用户是否存在并校验用户账户内的金额是否够本次投注需要<br>
	 *            返回用户所在的区域<br>
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
	 *            计算投注倍数后的金额，即单个订单的金额<br>
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
	 *            计算本次投注总的金额<br>
	 * @param betMultiple 当前期的倍数
	 * @param supplementTerms 追加的信息
	 * @param amount 单个订单的金额
	 * @return int
	 */
	private int getTotalNeedMoney(int betMultiple,Map<String,Integer> supplementTerms,int amount){
		int TotalTermMultipe = betMultiple <=0?1:betMultiple;//得到总的投注期数，即订单的个数，处理有追加的情况
		if(supplementTerms != null && !supplementTerms.isEmpty()){
			for(Map.Entry<String, Integer> oneTerm : supplementTerms.entrySet()){
				TotalTermMultipe += oneTerm.getValue();
			}
		}
		
		int totalNeedMoney = this.getSingleBetPrize(TotalTermMultipe, amount);//总共需要的账户金额
		return totalNeedMoney;
	}
	/**
	 * 
	 * Title: frozenAccountCapital<br>
	 * Description: <br>
	 *            按照订单编号冻结账户资金<br>
	 * @param userId 用户账户id
	 * @param amount 每个订单的金额，包括倍数的金额
	 * @param betOrderId 订单编号
	 * @param lotteryId
	 * @param betMultiple
	 * @param curtermNo
	 * @param sortSupplementTerms
	 * @throws LotteryException
	 */
	private void frozenAccountCapital(int lotteryId,long userId,int betMultiple, int amount,String curtermNo,List<String> betOrderId,Map<String,Integer> sortSupplementTerms) throws LotteryException{
		logger.debug("投注---冻结资金");
		for(String orderid : betOrderId){
			logger.debug("投注---冻结资金---单笔冻结订单编号和期数:"+orderid);
			String curOrderId = orderid.split("#")[0];
			String termNO = orderid.split("#")[1];
			
			try {
				if(curtermNo.equals(orderid.split("#")[1])){//当前期的直接扣除
					int frozen = this.getSingleBetPrize(betMultiple, amount);
					logger.debug("投注---冻结资金---扣除当前期本金");
					this.getUserService().accountTransaction(
							userId,
							TRANSACTION_TYPE_10002,
							frozen,
							TRANSACTION_SOURCE_TYPE,
							curOrderId,
							LotteryTools.accountTransactionRemark(lotteryId,
									termNO, TRANSACTION_TYPE_10002));
					logger.debug("投注---冻结资金---单笔扣除金额:"+frozen);
				}else{//追号期的先冻结
					int frozen = this.getSingleBetPrize(sortSupplementTerms.get(termNO), amount);
					logger.debug("投注---冻结资金---冻结追号资金");
					this.getUserService().accountTransaction(
							userId,
							TRANSACTION_TYPE_20002,
							frozen,
							TRANSACTION_SOURCE_TYPE,
							curOrderId,
							LotteryTools.accountTransactionRemark(lotteryId,
									termNO, TRANSACTION_TYPE_20002));
					logger.debug("投注---冻结资金---单笔冻结金额:"+frozen);
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
	 *            转换投注参数为实体对象<br>
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
	 *            转换账户类的异常<br>
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
	 *            校验用户所在区域是否可以销售指定的彩种<br>
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
	 *             校验彩期是否可以销售<br>
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
	 *              <br>校验限制的号码不能投注
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
	 *            查看参数<br>
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
	 *              <br>合买发起参数
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
			sb.append("用户:").append(userId).append(sign);
			sb.append("彩种:").append(lotteryId).append(sign).append("玩法:").append(playType).append(sign);
			sb.append("投注方式:").append(betType).append(sign).append("倍数:").append(betMultiple).append(sign);
			sb.append("注数:").append(betNum).append(sign).append("金额:").append(amount).append(sign);
			sb.append("彩期:").append(term).append(sign).append("投注串:").append(betCode).append(sign).append("保密方式:").append(planopentype).append(sign);
			sb.append("总份数:").append(totalunit).append(sign).append("每份单价:").append(unitprice).append(sign);
			sb.append("发起认购:").append(selfBuyNum).append(sign).append("保底份数:").append(unitbuyself).append(sign);
			sb.append("提成:").append(commisionpercent).append(sign).append("方案来源:").append(plansource).append(sign);
			sb.append("标题:").append(plantitle).append(sign).append("简介:").append(plandescription);
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
	 * (非 Javadoc)
	*Title: coopBetCreate
	*Description: 
	* @param userId 彩民id
	 * @param lotteryId 彩种
	 * @param playType 玩法
	 * @param betType 投注方式
	 * @param betMultiple 投注倍数
	 * @param betNum 投注注数 如果不设置该值要传入-1
	 * @param amount 投注金额，单位为分,即投注内容的金额(不能包含投注倍数的金额) ,如果不设置该值要传入-1
	 * @param term 投注彩期
	 * @param planopentype 方案公开方式 0-公开 1-购买后公开； ? 2-截止后公开
	 * @param totalunit 总份数
	 * @param unitprice 每份单价
	 * @param selfBuyNum 自己认购份数
	 * @param unitbuyself 保底份数
	 * @param commisionpercent 佣金比例，方案中奖后的佣金比例、合买用，填写10则为 10%
	 * @param plansource 投注方案是从那个渠道来的，0-WEB，1-SMS，2-WAP
	 * @param plantitle 方案标题 可空
	 * @param plandescription 方案介绍 可空
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#coopBetCreate(long, int, int, int, int, long, long, java.lang.String, int, int, int, int, int, int, int, java.lang.String, java.lang.String)
	 */
	
	public String coopBetCreate(long userId, int lotteryId, int playType, int betType, int betMultiple, long betNum, long amount, String term, String betCode,int planOpenType, int totalUnit, int unitPrice, int selfBuyNum, int unitBuySelf, int commisionPercent, int planSource, String planTitle, String planDescription) throws LotteryException {
		String result=null;
		try{
			logger.debug("合买发起投注---开始");
			if(logger.isDebugEnabled()){
				logger.debug("合买发起参数："+this.coopParamToString(userId, lotteryId, playType, betType, betMultiple, betNum, amount, term,betCode,planOpenType, totalUnit, unitPrice, selfBuyNum, unitBuySelf, commisionPercent, planSource, planTitle, planDescription));
			}
			
			//校验佣金比例设置是否正确
			String sysDefinePercent = LotteryTools.getCoopPercent(lotteryId, betType);
			logger.debug("合买发起投注---系统定义提成比例范围:"+sysDefinePercent);
			if((Integer.parseInt(sysDefinePercent.split("-")[0]) <= commisionPercent) &&  (commisionPercent <= Integer.parseInt(sysDefinePercent.split("-")[1]))){
				logger.debug("合买发起投注---提成比例设置正确");
			}else{
				logger.debug("合买发起投注---提成比例设置错误");
				String tiChengBiLi = "";
				if(sysDefinePercent.split("-")[0].equals(sysDefinePercent.split("-")[1])){
					tiChengBiLi = sysDefinePercent.split("-")[1]+"%";
				}else{
					tiChengBiLi = sysDefinePercent+"%";
				}
				throw new LotteryException(BetServiceInterf.E_16_CODE,BetServiceInterf.E_16_DESC.replaceAll("A", tiChengBiLi));
			}
			//校验投注字符串格式是否正确
			this.checkBetFormat(lotteryId, playType, betType, betCode);
			logger.debug("合买发起投注---投注格式正确");
			
			//校验注数和金额是否正确,返回的是单个订单的注数和金额，不包含倍数
			String zhuAndMoney = this.checkBetNumAndMoney(lotteryId, playType,betType, betNum, amount, betCode);
			logger.debug("合买发起投注---注数和金额正确");
			
			String zhuNum = zhuAndMoney.split("#")[0];//单个订单的注数
			int singleBetAmount = Integer.parseInt(zhuAndMoney.split("#")[1]);//单个订单的金额，不包含倍数
			
			
			//校验投注单倍单注是否超过定义范围限制
			this.checkBetMoneyLimit(lotteryId, playType, betType, betMultiple, betCode,null);
			logger.debug("合买发起投注---投注倍数和金额没有超过限制");
			//方案总金额
			int betTotalMoney = (betMultiple<=0?1:betMultiple) * singleBetAmount;
			
			//校验总份数和每一份的金额
			if(totalUnit < 1){
				throw new LotteryException(BetServiceInterf.E_17_CODE,BetServiceInterf.E_17_DESC);
			}
			if( (betTotalMoney / totalUnit) != unitPrice ){
				throw new LotteryException(BetServiceInterf.E_18_CODE,BetServiceInterf.E_18_DESC);
			}
			logger.debug("合买发起投注---总份数和每一份金额正确");
			//校验发起认购份额是否达到要求,>5%
			if((selfBuyNum / (totalUnit*1.0))*100 < 5){
				throw new LotteryException(BetServiceInterf.E_19_CODE,BetServiceInterf.E_19_DESC);
			}
			logger.debug("合买发起投注---发起合买认购份数正确");
			//校验保底份数是否设置正确
			if(unitBuySelf > 0){
				if(totalUnit == selfBuyNum){//发起人已经全部认购了，不能再保底
					throw new LotteryException(BetServiceInterf.E_20_CODE,BetServiceInterf.E_20_DESC);
				}
				if((unitBuySelf / (totalUnit*1.0))*100 < 20){
					throw new LotteryException(BetServiceInterf.E_20_CODE,BetServiceInterf.E_20_DESC);
				}
			}
			logger.debug("合买发起投注---保底份数设置正确");

			//下面开始涉及数据库的操作
			LotteryTermModel termInfo = this.getTermService().queryTermInfo(lotteryId, term);
			
			//校验当前期是否可以销售
			this.checkCurTermCanSales(termInfo,lotteryId, term); 
			logger.debug("合买发起投注---当前期可以销售");
			
			//校验用户是否存在,账户金额是否够本次投注需要,资金为发起认购+保底
			int selfRenGouMoney = unitPrice * selfBuyNum;//发起人认购金额
			int baoDiMoney = unitPrice * (unitBuySelf > 0 ? unitBuySelf : 0);//保底需要的金额
			int faQiTotalNeedMoney = selfRenGouMoney + baoDiMoney;//发起人需要冻结的账户金额
			UserAccountModel user = this.checkUserIsExistById(userId,null,faQiTotalNeedMoney);
			logger.debug("合买发起投注---用户存在,金额够用");
			String areaCode = user.getAreaCode();
			logger.debug("合买发起投注---发起人区域:"+areaCode);
			this.checkAreaCanSales(lotteryId, areaCode);
			logger.debug("合买发起投注---区域可以购买");
			
			/*
			 * 方案订单操作
			 */
			
			//生成合买方案,返回方案编号
			String planId = this.getOrderService().createCoopPlan(userId,areaCode, lotteryId, playType,
					betType, betMultiple, betTotalMoney, term, termInfo
							.getDeadLine2(), betCode, planOpenType, totalUnit,
					unitPrice, selfBuyNum, unitBuySelf, commisionPercent,
					planSource, planTitle, planDescription,0);
			logger.debug("合买发起投注---生成方案成功,方案id："+planId);
			
			//生成合买信息记录,如果直接满员，状态设置为4
			String cpInfoId = this.getOrderService().createCoopInfo(planId, userId, lotteryId,
					term, playType, betType, 0, selfBuyNum, selfRenGouMoney,
					((totalUnit == selfBuyNum)?4:0));
			logger.debug("合买发起投注---生成合买信息成功,信息id："+cpInfoId);
			
			//先冻结资金认购资金
			try{
				//冻结发起认购资金
				this.getUserService().accountTransaction(userId,20003,selfRenGouMoney,2001,
						planId,LotteryTools.accountTransactionRemark(lotteryId,term, 20003));
				logger.debug("合买发起投注---冻结发起认购资金---冻结金额:"+selfRenGouMoney);
			}catch(LotteryException e){
				throw this.convertException(e);
			}
			
			
			if(totalUnit == selfBuyNum){//发起方案已经满员,生成合买出票订单，扣除资金
				String coopOrderId = this.getOrderService().createCoopBetOrder(planId, planSource, userId,areaCode, lotteryId, 
						term, playType, betType,0, betCode, betMultiple, betTotalMoney,0);
				//更新方案为已经生成订单
				this.getOrderService().updateBetPlanStatus(planId, 1);
				logger.debug("合买发起投注---方案满员生成合买出票订单成功---订单id:" + coopOrderId);
				
				//扣除个人认购的冻结资金
				try{
					this.getUserService().accountTransaction(userId,30002,selfRenGouMoney,2001,
							coopOrderId,LotteryTools.accountTransactionRemark(lotteryId,term, 30002));
					
					logger.debug("合买发起投注---方案满员扣除冻结资金---扣除金额:"+selfRenGouMoney);
				}catch(LotteryException e){
					throw this.convertException(e);
				}
				
			}else{
				//冻结保底资金,发起的方案没有满员
				if(baoDiMoney > 0){//有保底
					String baodiFrozenId = this.getOrderService().createCoopInfo(planId, userId,
							lotteryId, term, playType, betType, 2,
							unitBuySelf, baoDiMoney, 5);
					logger.debug("合买发起投注---冻结保底信息---信息id:" + baodiFrozenId);
					try{
						this.getUserService().accountTransaction(userId,20009,baoDiMoney,2001,
								baodiFrozenId,LotteryTools.accountTransactionRemark(lotteryId,term, 20009));
						logger.debug("合买发起投注---冻结发起保底资金---冻结金额:"+baoDiMoney);
					}catch(LotteryException e){
						throw this.convertException(e);
					}
				}
			}
			
			result = term + "#"+ planId + "#" + zhuNum + "#" + betTotalMoney;
			logger.debug("合买发起投注结果:" + result);
			logger.debug("合买发起投注---完成");
			
		}catch(LotteryException e){
			logger.error("合买发起投注发生异常:", e);
			throw e;
		}catch(Exception ex){
			logger.error("合买发起投注发生异常", ex);
			throw new LotteryException(E_14_CODE, E_14_DESC);
		}
		return result;
	}
	
	/*
	 * (非 Javadoc)
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
			logger.debug("合买参与投注---开始,参与用户:"+userId+"参与方案:"+planId+"认购份数:"+cpUnit+"认购金额:"+amount);
			BetPlanDomain coopPlan = this.getOrderService().queryBetPlanForUpdate(planId);
			if(coopPlan == null){
				throw new LotteryException(BetServiceInterf.E_24_CODE,BetServiceInterf.E_24_DESC);
			}
			logger.debug("合买参与投注---方案存在,方案状态:"+coopPlan.getPlanStatus());
			if(coopPlan.getPlanStatus() != 0){//方案已经不能买了
				throw new LotteryException(BetServiceInterf.E_25_CODE,BetServiceInterf.E_25_DESC);
			}
			logger.debug("合买参与投注---方案可以参与");
			//购买份数和金额是否匹配
			long unitPrize = coopPlan.getUnitPrice();
			if(cpUnit < 1){
				throw new LotteryException(BetServiceInterf.E_26_CODE,BetServiceInterf.E_26_DESC);
			}
			if(amount != (unitPrize * cpUnit)){
				throw new LotteryException(BetServiceInterf.E_27_CODE,BetServiceInterf.E_27_DESC);
			}
			logger.debug("合买参与投注---认购份数金额正确,每一份金额:"+unitPrize);
			
			//方案进度+购买份额是否超过100%
			int selledUnit = coopPlan.getSelledUnit();
			int totalUnit = coopPlan.getTotalUnit();
			logger.debug("合买参与投注---认购后方案总进度:"+(selledUnit + cpUnit)+"方案总份数:"+totalUnit);
			if((selledUnit + cpUnit) > totalUnit){
				throw new LotteryException(BetServiceInterf.E_28_CODE,BetServiceInterf.E_28_DESC);
			}
			
			logger.debug("合买参与投注---认购后方案不会超过100%");
			
			//账户资金是否够
			UserAccountModel user = this.checkUserIsExistById(userId,null,amount);
			logger.debug("合买参与投注---用户存在,金额够用");
			String areaCode = user.getAreaCode();
			logger.debug("合买参与投注--- 参与用户区域:"+areaCode);
			
			this.checkAreaCanSales(coopPlan.getLotteryId(), areaCode);
			logger.debug("合买参与投注---区域可以购买");
			
			if((selledUnit + cpUnit) == totalUnit){//本次参与后方案满员
				logger.debug("合买参与投注---参与后方案正好满员");
				//改方案进度和方案状态，一定要先改, 防止无事物读取
				int upPlanProgress = this.getOrderService()
						.updateBetPlanSelledUnit(planId, (selledUnit + cpUnit),1);
				logger.debug("合买参与投注---满员处理--更新方案进度状态完成,方案状态改为1");
				coopInfoId = this.getOrderService().createCoopInfo(planId, userId,coopPlan.getLotteryId(), 
						coopPlan.getStartTerm(), coopPlan.getPlayType(), coopPlan.getBetType(), 1,
						cpUnit, amount, 0);
				logger.debug("合买参与投注---满员处理--生成参与信息成功");
				
				this.getUserService().accountTransaction(userId,20005,amount,2001,coopInfoId,
						LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),coopPlan.getStartTerm(), 20005));
				logger.debug("合买参与投注---满员处理--冻结参与资金成功");
				
				//循环所有合买信息
				List<CpInfoDomain> allCoopInfo = this.getOrderService().queryCoopInfoByPlanId(planId);
				logger.debug("合买参与投注---满员处理--方案总的参与信息数:"+allCoopInfo.size());
				int dealInfoNum = 0;
				for(CpInfoDomain oneInfo : allCoopInfo){
					int orderType = oneInfo.getCpOrderType();
					int orderStatus = oneInfo.getOrderStatus();
					String infoId = oneInfo.getCpInfoId();
					logger.debug("合买参与投注---满员处理--参与订单:"+infoId+"参与类型:"+orderType+"订单状态:"+orderStatus);
					if(orderType == 2 && orderStatus == 5){//发起人保底资金
						int upInfo = this.getOrderService().updateCoopInfoStatus(infoId, 6);
						logger.debug("合买参与投注---保底订单状态修改成功，订单:" + infoId);
						this.getUserService().accountTransaction(userId,31009,oneInfo.getCpAmount(),2001,infoId,
								LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),oneInfo.getBetTerm(), 31009));
						logger.debug("合买参与投注---发起人保底资金撤销成功,订单:"+infoId);
						dealInfoNum++;
					}else{
						if(orderStatus == 0){//进行中的订单
							this.getOrderService().updateCoopInfoStatus(infoId, 4);//状态改为合买满员生成投注
							logger.debug("合买参与投注---订单状态修改成功，订单:" + infoId);
							if(orderType == 0){//合买发起认购
								this.getUserService().accountTransaction(userId,30002,oneInfo.getCpAmount(),2001,infoId,
										LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),oneInfo.getBetTerm(), 30002));
							}else if(orderType == 1){//合买参与认购
								this.getUserService().accountTransaction(userId,30003,oneInfo.getCpAmount(),2001,infoId,
										LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),oneInfo.getBetTerm(), 30003));
							}
							logger.debug("合买参与投注---参与资金撤销成功,订单:"+infoId);
							dealInfoNum++;
						}
					}
					logger.debug("合买参与投注---处理参与订单数:"+dealInfoNum);
				}
				//生成出票订单
				String chuPiaoOrderId = this.getOrderService().createCoopBetOrder(planId, coopPlan.getPlanSource(),
						coopPlan.getUserId(), coopPlan.getAreaCode(), coopPlan
								.getLotteryId(), coopPlan.getStartTerm(),
						coopPlan.getPlayType(), coopPlan.getBetType(), coopPlan
								.getBetCodeMode(), coopPlan.getBetCode(),
						coopPlan.getBetMultiple(), coopPlan.getUnitAmount(), 0);
				logger.debug("合买参与投注---方案满员生成出票订单成功,订单:" + chuPiaoOrderId);
				
			}else{//本次参与后方案未满员
				coopInfoId = this.getOrderService().createCoopInfo(planId, userId,coopPlan.getLotteryId(), 
						coopPlan.getStartTerm(), coopPlan.getPlayType(), coopPlan.getBetType(), 1,
						cpUnit, amount, 0);
				logger.debug("合买参与投注---生成参与信息成功");
				int upSelledUnit = selledUnit + cpUnit;
				int upPlanResult = this.getOrderService()
						.updateBetPlanSelledUnit(planId, upSelledUnit,coopPlan.getPlanStatus());
				logger.debug("合买参与投注---更新方案进度成功");
				try{
					this.getUserService().accountTransaction(userId,20005,amount,2001,coopInfoId,
							LotteryTools.accountTransactionRemark(coopPlan.getLotteryId(),coopPlan.getStartTerm(), 20005));
					logger.debug("合买参与投注---冻结参与资金成功");
				}catch(LotteryException e){
					throw this.convertException(e);
				}
				
			}
			logger.debug("合买参与投注---结束");
		}catch(LotteryException e){
			throw e;
		}catch(Exception ex){
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return coopInfoId;
	}
	
	/*
	 * (非 Javadoc)
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
			logger.debug("合买参与人撤单---开始,参与人:"+userId+"撤单id:"+coopInfoId);
			CpInfoDomain coopInfo = this.getOrderService().queryCoopInfoByIdForUpdate(coopInfoId);
			if(coopInfo == null){
				throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "参与人撤销订单未找到参与信息"));
			}
			logger.debug("合买参与人撤单---方案存在");
			if(userId != coopInfo.getUserId()){
				throw new LotteryException(BetServiceInterf.E_29_CODE,BetServiceInterf.E_29_DESC);
			}
			logger.debug("合买参与人撤单---用户一致");
			if(coopInfo.getCpOrderType() == 1){
				throw new LotteryException(BetServiceInterf.E_37_CODE,BetServiceInterf.E_37_DESC);
			}
			logger.debug("合买参与人撤单---不是发起认购");
			if(coopInfo.getCpOrderType() == 2 || coopInfo.getCpOrderType() == 3){
				throw new LotteryException(BetServiceInterf.E_38_CODE,BetServiceInterf.E_38_DESC);
			}
			logger.debug("合买参与人撤单---不是保底撤销");
			if(coopInfo.getOrderStatus() != 0){
				logger.debug("合买参与人撤单---订单状态:"+coopInfo.getOrderStatus());
				throw new LotteryException(BetServiceInterf.E_30_CODE,BetServiceInterf.E_30_DESC);
			}
			logger.debug("合买参与人撤单---订单状态可撤");
			String planId = coopInfo.getPlanId();
			BetPlanDomain coopPlan = this.getOrderService().queryBetPlanForUpdate(planId);//必须锁定方案
			logger.debug("合买参与人撤单---方案状态:"+coopPlan.getPlanStatus()+"方案总份数:"+coopPlan.getTotalUnit()+"方案已认购:"+coopPlan.getSelledUnit());
			int planTotalUnit = coopPlan.getTotalUnit();
			int planSelledUnit = coopPlan.getSelledUnit();
			
			if(coopPlan.getPlanStatus() != 0){
				throw new LotteryException(BetServiceInterf.E_31_CODE,BetServiceInterf.E_31_DESC);
			}
			int sysDefineProgress = LotteryTools.getJoinRevocate(coopPlan.getLotteryId());
			if((((planSelledUnit*1.0)/planTotalUnit) * 100) >= sysDefineProgress){
				throw new LotteryException(BetServiceInterf.E_31_CODE,BetServiceInterf.E_31_DESC+",方案进度已超过:"+sysDefineProgress+"%");
			}
			logger.debug("合买参与人撤单---方案满足参与撤销条件");
			
			//开始参与订单的撤销
			int newSelledUnit = planSelledUnit - coopInfo.getCpUnit();
			int upPlanResult = this.getOrderService().updateBetPlanSelledUnit(planId, newSelledUnit, coopPlan.getPlanStatus());
			if(upPlanResult <= 0){
				logger.error("合买参与人撤单---更新方案时未更新到对应的方案");
				throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "更新方案"));
			}
			logger.debug("合买参与人撤单---更新方案进度成功");
			
			int upCoopInfoResult = this.getOrderService().updateCoopInfoStatus(coopInfoId, 3);
			if(upCoopInfoResult <=0){
				logger.error("合买参与人撤单---更新参与订单时未更新到对应的信息");
				throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "撤销订单"));
			}
			logger.debug("合买参与人撤单---更新参与信息状态成功");
			
			try{
				this.getUserService().accountTransaction(userId,31005,coopInfo.getCpAmount(),2001,coopInfoId,
						LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31005));
				logger.debug("合买参与人撤单---释放冻结资金成功");
			}catch(LotteryException e){
				this.convertException(e);
			}
			logger.debug("合买参与人撤单---完成");
		}catch(LotteryException e){
			throw e;
		}catch(Exception ex){
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return coopInfoId;
	}
	/*
	 * (非 Javadoc)
	*Title: revocateCreatePlan
	*Description: 发起人撤方案
	* @param userId
	* @param planId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#revocateCreatePlan(long, java.lang.String)
	 */
	public String revocateCreatePlan(long userId, String planId) throws LotteryException {
		try{
			logger.debug("合买发起人撤销方案---开始,用户:"+userId+"方案:"+planId);
			BetPlanDomain coopPlan = this.getOrderService().queryBetPlanForUpdate(planId);
			if(userId != coopPlan.getUserId()){
				throw new LotteryException(BetServiceInterf.E_29_CODE,BetServiceInterf.E_29_DESC);
			}
			logger.debug("发起人撤消方案---方案与撤销人一致");
			if(coopPlan.getPlanStatus() != 0){
				throw new LotteryException(BetServiceInterf.E_30_CODE,BetServiceInterf.E_30_DESC);
			}
			logger.debug("发起人撤消方案---方案状态可以撤销");
			int sysDefinePlanRev = LotteryTools.getCreateRevocate(coopPlan.getLotteryId());
			
			logger.debug("发起人撤消方案---方案进度:"+((((coopPlan.getSelledUnit() + coopPlan
					.getUnitBuySelf())*1.0) / coopPlan.getTotalUnit()) * 100));
			if(((((coopPlan.getSelledUnit() + coopPlan
					.getUnitBuySelf())*1.0) / coopPlan.getTotalUnit()) * 100) >= sysDefinePlanRev){
				throw new LotteryException(BetServiceInterf.E_31_CODE,BetServiceInterf.E_31_DESC+",方案进度加保底已超过:"+sysDefinePlanRev+"%");
			}
			logger.debug("发起人撤消方案---方案进度可以撤销");
			this.destoryCoopPlan(planId, 1);
			logger.debug("发起人撤消方案---完成");
			
		}catch(LotteryException e){
			logger.error("发起人撤销方案发生异常,异常编号:"+e.getType()+"异常原因:"+e.getMessage());
			throw e;
		}catch(Exception ex){
			logger.error("发起人撤销方案发生程序异常:", ex);
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return planId;
	}
	/*
	 * (非 Javadoc)
	*Title: dealOnePlanBySys
	*Description: 
	* @param planId
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#dealOnePlanBySys(java.lang.String)
	 */
	public void dealOnePlanBySys(String planId) throws LotteryException {
		try{
			logger.debug("系统处理方案---开始处理:"+planId);
			BetPlanDomain coopPlan = this.getOrderService().queryBetPlanForUpdate(planId);
			if(coopPlan != null && coopPlan.getPlanStatus() == 0){//方案还没有提交出票
				int totalProgress = (coopPlan.getSelledUnit() + coopPlan.getUnitBuySelf());
				int totalUnit = coopPlan.getTotalUnit();
				List<CpInfoDomain> coopInfoList = this.getOrderService().queryCoopInfoByPlanId(planId);//先查到所有的参与信息
				
				if(totalProgress >= totalUnit){//方案可满员
					int k =0;
					if(coopInfoList != null && coopInfoList.size() > 0){
						logger.debug("系统处理方案---方案加保底可生成订单开始:"+planId +"总共需要处理的订单数:"+coopInfoList.size());
						for(CpInfoDomain coopInfo : coopInfoList){
							String coopInfoId = coopInfo.getCpInfoId();
							CpInfoDomain oneCoop = this.getOrderService().queryCoopInfoByIdForUpdate(coopInfoId);//一定要锁住订单
							logger.debug((++k) + "系统处理方案---处理参与订单:"+oneCoop.getCpInfoId()+"参与类型:"+oneCoop.getCpOrderType()+"订单状态:"+oneCoop.getOrderStatus());
							if ((oneCoop.getCpOrderType() == 1
									|| oneCoop.getCpOrderType() == 0 || oneCoop
									.getCpOrderType() == 3)
									&& oneCoop.getOrderStatus() == 0) {//投注并且订单还是进行中
								logger.debug("系统处理方案---处理参与订单开始:"+oneCoop.getCpInfoId());
								this.getOrderService().updateCoopInfoStatus(oneCoop.getCpInfoId(), 2);
								
								try{
									this.getUserService().accountTransaction(oneCoop.getUserId(),31003,coopInfo.getCpAmount(),2001,oneCoop.getCpInfoId(),
											LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31003));
								}catch(LotteryException e){
									this.convertException(e);
								}
								logger.debug("系统处理方案---处理参与订单:"+oneCoop.getCpInfoId()+"成功");
							}else if(oneCoop.getCpOrderType() == 2 && oneCoop.getOrderStatus() == 5){//发起保底冻结未撤销
								logger.debug("系统处理方案---处理保底订单开始:"+oneCoop.getCpInfoId());
								this.getOrderService().updateCoopInfoStatus(oneCoop.getCpInfoId(), 6);
								try{
									this.getUserService().accountTransaction(oneCoop.getUserId(),31009,coopInfo.getCpAmount(),2001,oneCoop.getCpInfoId(),
											LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31009));
								}catch(LotteryException e){
									this.convertException(e);
								}
								//生成新的保底转投注
								int baodiUseUnit = (totalUnit - coopPlan.getSelledUnit());
								int baodiUseMoney = (new Long(baodiUseUnit * coopPlan.getUnitPrice())).intValue();
								logger.debug("系统处理方案---保底转投注份数:"+baodiUseUnit+"金额:"+baodiUseMoney);
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
								logger.debug("系统处理方案---处理保底订单:"+oneCoop.getCpInfoId()+"成功");
							}
						}
						
						//生成出票订单
						String createChuPiaoOrder = this.getOrderService()
								.createCoopBetOrder(planId, coopPlan.getPlanSource(), coopPlan.getUserId(),
										coopPlan.getAreaCode(), coopPlan.getLotteryId(), coopPlan.getStartTerm(), coopPlan.getPlayType(),
										coopPlan.getBetType(), coopPlan.getBetCodeMode(), coopPlan.getBetCode(), coopPlan.getBetMultiple(),
										coopPlan.getUnitAmount(), 0);
						if(StringUtils.isEmpty(createChuPiaoOrder)){
							throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "系统处理方案，未能正确的生成出票订单"));
						}
						
						int upPlanStatus = this.getOrderService().updateBetPlanSelledUnit(planId, totalUnit, 1);//该方案状态为已经生成出票订单,还要更新进度
						if(upPlanStatus <= 0){
							throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "系统处理方案，未能正确的更新方案的进度和状态"));
						}
					}else{
						throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "系统处理方案，未找到方案对应的订单数据"));
					}
				}else{//方案不能满员
					logger.debug("系统处理方案---方案加保底不能生成订单开始:"+planId);
					this.destoryCoopPlan(planId, 0);
					logger.debug("系统处理方案---方案加保底不能生成订单完成:"+planId);
				}
			}else{
				logger.debug("系统处理方案---方案已经生成订单，不需要再处理");
			}
			logger.debug("系统处理方案---完成:"+planId);
		}catch(LotteryException e){
			logger.error("系统处理合买方案发生异常,异常编号:"+e.getType()+"异常原因:"+e.getMessage());
			throw e;
		}catch(Exception ex){
			logger.error("系统处理合买方案发生程序异常:", ex);
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		
	}
	/**
	 * 
	 * Title: destoryCoopPlan<br>
	 * Description: <br>
	 *              <br>完全撤销单个方案,一定要保证该方案可以撤销才能调用
	 * @param planId
	 * @param sysOrCreate 系统撤单或者发起人撤单，系统撤单用：0.发起人撤单用:1
	 * @throws LotteryException
	 */
	private void destoryCoopPlan(String planId,int sysOrCreate) throws LotteryException{
		int destoryType = (sysOrCreate == 0)?1:2;
		//开始撤销
		int upPlanStatus = this.getOrderService().updateBetPlanStatus(planId, 4);//先撤销方案
		if(upPlanStatus <=0 ){
			throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "撤销方案,更新方案未成功"));
		}
		logger.debug("撤消方案---修改方案状态成功");
		List<CpInfoDomain> coopInfoList = this.getOrderService().queryCoopInfoByPlanId(planId);//先查到所有的参与信息
		if(coopInfoList == null){
			throw new LotteryException(BetServiceInterf.E_100_CODE,BetServiceInterf.E_100_DESC.replaceAll("OP", "撤销方案，未查到方案对应的参与信息"));
		}
		logger.debug("撤消方案---总参与订单数:"+coopInfoList.size());
		int k = 0;
		for(CpInfoDomain coopInfo : coopInfoList){
			String coopInfoId = coopInfo.getCpInfoId();
			CpInfoDomain oneCoop = this.getOrderService().queryCoopInfoByIdForUpdate(coopInfoId);//一定要锁住订单
			logger.debug((++k) + "撤消方案---撤销参与订单:"+oneCoop.getCpInfoId()+"参与类型:"+oneCoop.getCpOrderType()+"订单状态:"+oneCoop.getOrderStatus());
			if ((oneCoop.getCpOrderType() == 1
					|| oneCoop.getCpOrderType() == 0 || oneCoop
					.getCpOrderType() == 3)
					&& oneCoop.getOrderStatus() == 0) {//投注并且订单还是进行中
				this.getOrderService().updateCoopInfoStatus(oneCoop.getCpInfoId(), destoryType);
				
				try{
					this.getUserService().accountTransaction(oneCoop.getUserId(),31003,coopInfo.getCpAmount(),2001,oneCoop.getCpInfoId(),
							LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31003));
				}catch(LotteryException e){
					this.convertException(e);
				}
				logger.debug("撤消方案---撤销参与订单:"+oneCoop.getCpInfoId()+"成功");
			}else if(oneCoop.getCpOrderType() == 2 && oneCoop.getOrderStatus() == 5){//发起保底冻结未撤销
				this.getOrderService().updateCoopInfoStatus(oneCoop.getCpInfoId(), 6);
				try{
					this.getUserService().accountTransaction(oneCoop.getUserId(),31009,coopInfo.getCpAmount(),2001,oneCoop.getCpInfoId(),
							LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),coopInfo.getBetTerm(), 31009));
				}catch(LotteryException e){
					this.convertException(e);
				}
				logger.debug("撤消方案---撤销保底订单:"+oneCoop.getCpInfoId()+"成功");
			}
		}
	}
	/*
	 * (非 Javadoc)
	*Title: daiGouErrOrderDeal
	*Description: 
	* @param orderId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#daiGouErrOrderDeal(java.lang.String)
	 */
	public String daiGouErrOrderDeal(String orderId) throws LotteryException {
		BetOrderDomain orderInfo = null;
		int needTuiKuai = 0;//需要退款的总钱数
		try{
			logger.debug("出票失败订单退款---退款订单:"+orderId+"开始");
			orderInfo = this.getOrderService().queryBetOrderByOrderIdForUpdate(orderId);//获取订单并锁定订单
			if(orderInfo == null){
				throw new LotteryException(BetServiceInterf.E_32_CODE,BetServiceInterf.E_32_DESC);
			}
			int orderStatus = orderInfo.getOrderStatus();
			int winStatus = orderInfo.getWinStatus();
			
			if((orderStatus != 4 && orderStatus != 6)){
				throw new LotteryException(BetServiceInterf.E_33_CODE,BetServiceInterf.E_33_DESC);
			}
			logger.debug("出票失败订单退款---退款订单:"+orderId+"订单状态正确,可以退款");
			if(winStatus > 3){
				throw new LotteryException(BetServiceInterf.E_34_CODE,BetServiceInterf.E_34_DESC);
			}
			logger.debug("出票失败订单退款---退款订单:"+orderId+"订单没有退过款,可以退款");
			//做具体的退款处理
			
			if(orderStatus == 6){//订单全部错误
				logger.debug("出票失败订单退款---退款订单:"+orderId+"订单全部票失败");
				needTuiKuai = orderInfo.getBetAmount();
			}else if(orderStatus == 4){//部分失败，部分成功
				logger.debug("出票失败订单退款---退款订单:"+orderId+"订单部分票失败");
				//查改订单对应的所有票
				List<BetTicketDomain> ticketList = this.getTicketService().getTickets(orderId);
				if(ticketList == null){
					throw new LotteryException(BetServiceInterf.E_35_CODE,BetServiceInterf.E_35_DESC);
				}
				for(BetTicketDomain oneTicket : ticketList){
					int ticketStatus = oneTicket.getTicketStatus();
					if(ticketStatus == 7){//出票失败
						needTuiKuai += oneTicket.getBetAmount();
					}
				}
			}
			logger.debug("出票失败订单退款---退款订单:"+orderId+"应该退款金额:" + needTuiKuai);
			
			//更新订单的状态标识订单已经做过退款处理
			int upWinStatus = (orderInfo.getWinStatus() * 100) + 99;
			int upOrderResult = this.getOrderService().updateBetOrderWinStatus(orderId, upWinStatus);
			logger.debug("出票失败订单退款---退款订单:"+orderId+"订单状态改为:" + upWinStatus);
			if(upOrderResult <=0 ){
				throw new LotteryException(BetServiceInterf.E_36_CODE,BetServiceInterf.E_36_DESC);
			}
			logger.debug("出票失败订单退款---退款订单:"+orderId+"修改订单状态成功");
			//写资金交易
			this.getUserService().accountTransaction(orderInfo.getUserId(), 31010, needTuiKuai, 2002, orderInfo.getOrderId(),
					LotteryTools.accountTransactionRemark(orderInfo.getLotteryId(),
							orderInfo.getBetTerm(), 31010));
			logger.debug("出票失败订单退款---退款订单:"+orderId+"资金交易成功");
			logTk.logInfo(orderInfo.getLotteryId(), orderInfo.getBetTerm(), orderInfo.getOrderId(), String.valueOf(needTuiKuai));
		}catch(LotteryException e){
			logger.error("代购错误订单退款发生异常:"+e.getType()+"错误描述:"+e.getMessage());
			throw e;
		}catch(Exception ex){
			logger.error("代购错误订单退款发生异常，异常原因:", ex);
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return new StringBuffer().append(orderInfo.getLotteryId()).append("#").append(orderInfo.getBetTerm()).append("#")
		.append(orderInfo.getOrderId()).append("#").append(needTuiKuai).append("#").append("退款成功").toString();
	}
	
	public BetTicketServiceInterf getTicketService() {
		return ticketService;
	}
	public void setTicketService(BetTicketServiceInterf ticketService) {
		this.ticketService = ticketService;
	}
	/*
	 * (非 Javadoc)
	*Title: heMaiErrOrderDeal
	*Description: 
	* @param orderId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.BetServiceInterf#heMaiErrOrderDeal(java.lang.String)
	 */
	public String heMaiErrOrderDeal(String orderId) throws LotteryException {
		BetOrderDomain orderInfo = null;
		int needTuiKuai = 0;//需要退款的总钱数
		StringBuffer tuiKuanBu = new StringBuffer();//退款结果
		try{
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"开始");
			orderInfo = this.getOrderService().queryBetOrderByOrderIdForUpdate(orderId);//获取订单并锁定订单
			if(orderInfo == null){
				throw new LotteryException(BetServiceInterf.E_32_CODE,BetServiceInterf.E_32_DESC);
			}
			int orderStatus = orderInfo.getOrderStatus();
			int winStatus = orderInfo.getWinStatus();
			
			if((orderStatus != 4 && orderStatus != 6)){
				throw new LotteryException(BetServiceInterf.E_33_CODE,BetServiceInterf.E_33_DESC);
			}
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"订单状态正确,可以退款");
			if(winStatus > 3){
				throw new LotteryException(BetServiceInterf.E_34_CODE,BetServiceInterf.E_34_DESC);
			}
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"订单没有退过款,可以退款");
			//做具体的退款处理
			
			if(orderStatus == 6){//订单全部错误
				logger.debug("合买出票失败订单退款---退款订单:"+orderId+"订单全部票失败");
				needTuiKuai = orderInfo.getBetAmount();
			}else if(orderStatus == 4){//部分失败，部分成功
				logger.debug("合买出票失败订单退款---退款订单:"+orderId+"订单部分票失败");
				//查订单对应的所有票
				List<BetTicketDomain> ticketList = this.getTicketService().getTickets(orderId);
				if(ticketList == null){
					throw new LotteryException(BetServiceInterf.E_35_CODE,BetServiceInterf.E_35_DESC);
				}
				for(BetTicketDomain oneTicket : ticketList){
					int ticketStatus = oneTicket.getTicketStatus();
					if(ticketStatus == 7){//出票失败
						needTuiKuai += oneTicket.getBetAmount();
					}
				}
			}
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"应该退款金额:" + needTuiKuai);
			
			//查该订单对应方案的所有参与份数
			BetPlanDomain betPlan = this.getOrderService().queryBetPlanByPlanId(orderInfo.getPlanId());
			if(betPlan == null){
				throw new LotteryException(BetServiceInterf.E_39_CODE,BetServiceInterf.E_39_DESC);
			}
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"对应的方案:" + orderInfo.getPlanId());
			
			int totalCanYuFei = betPlan.getTotalUnit();//该合买的总份数
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"订单的总份数:" + totalCanYuFei);
			//计算每一份应该退的金额
			BigDecimal oneFeiMoney = new BigDecimal(needTuiKuai).divide(new BigDecimal(totalCanYuFei));
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"每一份应该退的金额:" + oneFeiMoney);
			
			//循环处理所有的参与者应该退的钱
			List<CpInfoDomain> coopInfoList = this.getOrderService().queryCoopInfoByPlanId(orderInfo.getPlanId());
			
			if(coopInfoList == null || coopInfoList.isEmpty()){
				throw new LotteryException(BetServiceInterf.E_40_CODE,BetServiceInterf.E_40_DESC);
			}
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"总的参与合买记录数:" + coopInfoList.size());
			String createUserInfoId = null;
			int coopTuiKuan = 0;//参与合买应该退的金额
			int createUserPrize = 0;//合买发起人应该退的钱
			int totalJoinUserPrize = 0;//参与人退的总钱数
			long faQiUserId = 0L;
			int coopLotteryId = orderInfo.getLotteryId();
			String betTerm = orderInfo.getBetTerm();
			
			tuiKuanBu.append("总共退:").append((needTuiKuai*1.0)/100).append(",");
			for(CpInfoDomain oneCoop : coopInfoList){
				logger.info("合买出票失败订单退款,参与记录:"+oneCoop.getCpInfoId()+"类型:"+oneCoop.getCpOrderType()+"状态："+oneCoop.getOrderStatus());
				if(oneCoop.getCpOrderType() == 0 && oneCoop.getOrderStatus() == 4){//发起合买的并且已经生成票
					logger.info("发起合买人:" + +oneCoop.getUserId());
					createUserInfoId = oneCoop.getCpInfoId();
					faQiUserId = oneCoop.getUserId();
				}else if((oneCoop.getCpOrderType() == 1 || oneCoop.getCpOrderType() == 3) && oneCoop.getOrderStatus() == 4){//参与投注，保底转投注
					logger.info("参与合买人:" + oneCoop.getUserId());
					coopTuiKuan = (oneFeiMoney.multiply(new BigDecimal(oneCoop.getCpUnit()))).intValue();
					//写资金交易
					this.getUserService().accountTransaction(oneCoop.getUserId(), 31010, coopTuiKuan, 2002, oneCoop.getCpInfoId(),
							LotteryTools.accountTransactionRemark(coopLotteryId,betTerm, 31010));
					totalJoinUserPrize += coopTuiKuan;
					tuiKuanBu.append(oneCoop.getUserId()).append("退:").append((coopTuiKuan*1.0)/100).append(",");
					logger.debug("合买出票失败订单退款---退款订单:"+orderId+"参与人:"+oneCoop.getUserId()+"退款:" + coopTuiKuan);
				}
			}
			
			createUserPrize = needTuiKuai - totalJoinUserPrize;//合买发起人应该退的钱
			this.getUserService().accountTransaction(faQiUserId, 31010, createUserPrize, 2002, createUserInfoId,
					LotteryTools.accountTransactionRemark(coopLotteryId,betTerm, 31010));
			tuiKuanBu.append(faQiUserId).append(":").append(createUserPrize);
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"发起人退款:" + createUserPrize);
			
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"资金交易成功");
			//更新订单的状态标识订单已经做过退款处理
			int upWinStatus = (orderInfo.getWinStatus() * 100) + 99;
			int upOrderResult = this.getOrderService().updateBetOrderWinStatus(orderId, upWinStatus);
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"订单状态改为:" + upWinStatus);
			if(upOrderResult <=0 ){
				throw new LotteryException(BetServiceInterf.E_36_CODE,BetServiceInterf.E_36_DESC);
			}
			logger.debug("合买出票失败订单退款---退款订单:"+orderId+"修改订单状态成功");
			
			logTk.logInfo(orderInfo.getLotteryId(), orderInfo.getBetTerm(), orderInfo.getOrderId(), tuiKuanBu.toString());
		}catch(LotteryException e){
			logger.error("合买错误订单退款发生异常:"+e.getType()+"错误描述:"+e.getMessage());
			throw e;
		}catch(Exception ex){
			logger.error("合买错误订单退款发生异常，异常原因:", ex);
			throw new LotteryException(BetServiceInterf.E_14_CODE,BetServiceInterf.E_14_DESC);
		}
		return new StringBuffer().append(orderInfo.getLotteryId()).append("#").append(orderInfo.getBetTerm()).append("#")
		.append(orderInfo.getOrderId()).append("#").append(needTuiKuai).append("#").append(tuiKuanBu.toString()).append("#").append("退款成功").toString();
	}
}
