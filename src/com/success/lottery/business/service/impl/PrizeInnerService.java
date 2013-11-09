/**
 * Title: PrizeInnerService.java
 * @Package com.success.lottery.business.service.impl
 * Description: TODO(�ҽ����ɽ��ڲ�������)
 * @author gaoboqin
 * @date 2010-4-21 ����05:12:08
 * @version V1.0
 */
package com.success.lottery.business.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.service.AccountService;
import com.success.lottery.business.service.interf.PrizeInnerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.domain.CpInfoDomain;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.util.PrizeInfo;
import com.success.lottery.util.core.LotteryFactory;
import com.success.lottery.util.core.LotteryUnDefineException;

/**
 * com.success.lottery.business.service.impl
 * PrizeInnerService.java
 * PrizeInnerService
 * �ҽ����ɽ��ڲ�������
 * @author gaoboqin
 * 2010-4-21 ����05:12:08
 * 
 */

public class PrizeInnerService implements PrizeInnerInterf {
	private static Log logger = LogFactory.getLog(PrizeInnerService.class);
	private static final int ORDER_STATUS_0 = 0;//����δ��ʼ
	private static final int ORDER_STATUS_4 = 4;//������Ʊ��ɹ�
	private static final int ORDER_STATUS_5 = 5;//������Ʊ�ɹ�
	private static final int ORDER_STATUS_8 = 8;//�����ҽ����
	private static final int ORDER_STATUS_11 = 11;//����״̬Ϊ����
	private static final int ORDER_STATUS_13 = 13;//����״̬Ϊ׷����
	private static final int ORDER_STATUS_10 = 10;//����״̬Ϊ�ɽ����
	private static final int ORDER_STATUS_14 = 14;//����״̬Ϊ�޺�ȡ��
	
	private static final int TICKET_STATUS_6 = 6;//��Ʊ״̬Ϊ��Ʊ�ɹ�
	private static final int TICKET_STATUS_7 = 7;//��Ʊ״̬Ϊ��Ʊʧ��
	
	private static final int WIN_STATUS_0 = 0;//�н�״̬Ϊδ�ҽ�
	private static final int WIN_STATUS_1 = 1;//�н�״̬Ϊδ�н�
	private static final int WIN_STATUS_2 = 2;//�н�״̬Ϊ��С��
	private static final int WIN_STATUS_3 = 3;//�н�״̬Ϊ�д�
	
	private static final int TERM_WIN_STATUS_2 = 2;//���ڱ�Ŀ���״̬2����ʾ�Ѿ�����
	private static final int TERM_WIN_STATUS_3 = 3;//���ڱ�Ŀ���״̬3����ʾ���ڶҽ�
	private static final int TERM_WIN_STATUS_4 = 4;//���ڱ�Ŀ���״̬4����ʾ�ҽ����
	private static final int TERM_WIN_STATUS_7 = 7;//���ڱ�Ŀ���״̬7����ʾ�����ɽ�
	private static final int TERM_WIN_STATUS_8 = 8;//���ڱ�Ŀ���״̬8����ʾ�ɽ����
	
	private static final int WIN_STOPED = 1;//�н���ֹͣ׷�ű�־
	private static final int WIN_NOT_STOPED = 0;//�н���ֹͣ׷�ű�־
	
	private static final int TRANSACTION_SOURCE_TYPE = 2002;//����������Ͷע����
	
	private static final int TRANSACTION_TYPE_31006 = 31006;//�������׷�Ŷ����������������ʽ�
	private static final int TRANSACTION_TYPE_11002 = 11002;//�������Ͷע�����н�����30001
	private static final int TRANSACTION_TYPE_30001 = 30001;//�������Ͷע����׷��Ͷע�ɹ�
	//���泯���ӵ��޺ų���׷�Ŷ������������ʽ��Թ���ϸ��ѯʱ��ı�ע�ã�Ϊ�����ظ��¶����ͻȡΪ31117
	private static final int TRANSACTION_TYPE_31117 = 31117;//��������޺ų���׷�Ŷ������������ʽ�
	private static final String[] ZHUI_JIA_ARR = {"10000011"};
	
	
	
	/*
	 * ���������ķ���ӿ�
	 */
	private BetPlanOrderServiceInterf betOrderService;
	private LotteryTermServiceInterf termService;
	private AccountService userService;//�û��˻�����
	private BetTicketServiceInterf ticketService;//��Ʊ����

	/*
	 * (�� Javadoc)
	*Title: cashPrize
	*Description: 
	* @param lotteryId
	* @param cashTerm
	* @param orderId
	* @param termInfo
	* @param isCheckLotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#cashPrize(int, java.lang.String, java.lang.String, com.success.lottery.term.domain.LotteryTermModel, boolean)
	*/
	public String cashPrize(int lotteryId,String cashTerm, String orderId,LotteryTermModel termInfo,boolean isCheckLotteryId,Map<String,String> outResult)
			throws LotteryException { 
		/*
		 * ��ȡ��������������
		 */
		BetOrderDomain orderInfo = this.checkBetOrderCanCashForUpdate(orderId);
		int OrderLotteryId = 0;
		String OrderTermNo = "";
		
		if(orderInfo != null){
			OrderLotteryId = orderInfo.getLotteryId();
			OrderTermNo = orderInfo.getBetTerm();
			outResult.put("A", String.valueOf(orderInfo.getBetAmount()));
			outResult.put("B", String.valueOf(orderInfo.getUserId()));
		}
		/*
		 * �ж϶����Ƿ��ܶҽ��������жϲ����ҽ������
		 */
		if (orderInfo == null
				|| (orderInfo.getOrderStatus() != ORDER_STATUS_5 && orderInfo
						.getOrderStatus() != ORDER_STATUS_4)
				|| (orderInfo.getWinStatus() != WIN_STATUS_0 && orderInfo.getWinStatus() != 99)) {
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		
		/*
		 * �ж϶�����������Ҫ�Զҽ��������Ƿ�һ��
		 */
		if(isCheckLotteryId && (OrderLotteryId != lotteryId || !OrderTermNo.equals(cashTerm))){
			logger.info(E_05_CODE + ":" + E_05_DESC);
			throw new LotteryException(E_05_CODE,E_05_DESC);
		}
		
		/*
		 * ���ݶ�����ȡ������Ӧ�Ĳ�Ʊ,��ʱ��õĲ�Ʊֻ������2��״̬:6�ɹ� 7,ʧ��,Ҳ������ȡ�ÿյ�Ʊ�б�(ԭ�����߼�)
		 * ���߼�ȡ����Ʊ��������иö����µ�Ʊ������Ʊ״̬δȷ����Ʊ
		 */
		List<BetTicketDomain> ticketList = this.getTicketService().getTickets(orderId);
		
		/*
		 * ѭ����Ʊ�ҽ��������²�Ʊ����,ͳ���ܵ��н����
		 */
		
		/*
		 * 
		 * 1,ʧ�ܵĲ�Ʊ��ʲôʱ�򷵻��ʽ�,�������ʽ����´���
		 * 2�����²�Ʊ����н�״̬������֣��붩��״̬һ��
		 * 3����ʧ�ܵĲ�Ʊ��θ��¶�����ʶ��ʧ�ܵĲ�Ʊ��������
		 */
		long orderPrize = 0L;//�������н����,˰ǰ��
		long orderTaxPrize = 0L;//��������˰��
		long orderAftTaxPrize = 0L;//������˰�󽱽�
		long orderDeductPrize = 0L;//�������
		long orderCommPrize = 0L;//Ӷ��Ŀǰδʹ��
		int ticketFailNum = 0;//һ�������г�Ʊʧ�ܵĲ�Ʊ����
		long maxTicketPrize = 0;//��Ʊ�����˰ǰ����
		StringBuffer orderHitHaoMa = new StringBuffer();//�������н����봮
		for(BetTicketDomain ticket : ticketList){
			int ticketStatus = ticket.getTicketStatus();
			if(ticketStatus == TICKET_STATUS_6 && ticket.getPrizeResult() == 0){//��Ʊ�ɹ�
				long ticketPeize = 0L;
				/*
				 * �����н�������ܵ��н����
				 * ����  ��עע��[�н�����:����:��˰ǰ:��˰��], ��˰ǰ,��˰,��˰��,����н����
				 */
				String[] prizeResult = this.cash(lotteryId, ticket.getPlayType(),
						ticket.getBetType(), termInfo.getLotteryResult(), termInfo.getWinResult(), 
						ticket.getBetCode(), ticket.getBetMultiple());
				orderHitHaoMa.append(prizeResult[0]).append(",");
				ticketPeize = Long.parseLong(StringUtils.isEmpty(prizeResult[1])?"0":prizeResult[1]);//Ʊ��˰ǰ����
				orderPrize += ticketPeize;//�����ܵ��н����,˰ǰ
				orderTaxPrize += Long.parseLong(prizeResult[2]);
				orderAftTaxPrize += Long.parseLong(prizeResult[3]);
				long maxCodePrize = Long.parseLong(StringUtils.isEmpty(prizeResult[4])?"0":prizeResult[4]);
				maxTicketPrize = (maxTicketPrize > maxCodePrize) ? maxTicketPrize : maxCodePrize;
				//���²�Ʊ����н������н��ȼ�
				int ticketPrizeLevel = this.prizeLevel(lotteryId, prizeResult[4]);
				//�����Ϊ���������Ƿ�ȫ?
				this.getTicketService().updateBetTicketPrizeResult(ticket.getTicketSequence(), ticketPeize, ticketPrizeLevel);
			}else{//��Ʊʧ�ܵĲ�Ʊ����,Ŀǰͳ�Ƶ�������ʱ��ʹ��
				ticketFailNum++;
			}
		}
		
		int prizeLevel = this.prizeLevel(lotteryId,String.valueOf(maxTicketPrize));//ת���������𼶱�
		
		
		BetPlanDomain betPlan = this.getPlanInfo(orderInfo.getPlanId());//��ȡ������Ϣ
		
		if(betPlan.getPlanType() == 0){//����
			//����׷��,ֻ�����н�ֹͣ��׷��
			this.dealZhuiHao(termInfo.getNextTerm(),betPlan.getPlanId(),orderId,betPlan.getWinStoped(), prizeLevel);// ����׷��
		}else {//����
			logger.info("����##############"+"orderAftTaxPrize=="+orderAftTaxPrize+"betPlan.getUnitAmount()=="+betPlan.getUnitAmount()+"betPlan.getCommisionPercent()=="+betPlan.getCommisionPercent());
			if((orderAftTaxPrize > betPlan.getUnitAmount()) && betPlan.getCommisionPercent() > 0){
				//orderDeductPrize = ((orderAftTaxPrize -  betPlan.getUnitAmount()) * betPlan.getCommisionPercent())/100;
				
				orderDeductPrize = (new BigDecimal((orderAftTaxPrize - betPlan
						.getUnitAmount())
						* betPlan.getCommisionPercent()).divide(new BigDecimal(
						100))).longValue();
				logger.info("������ɣ�"+orderDeductPrize);
			}
			//long oneFenPrize = (orderAftTaxPrize - orderDeductPrize)/betPlan.getTotalUnit();//ÿһ�ݵĽ���
			BigDecimal oneFenPrize = new BigDecimal(orderAftTaxPrize - orderDeductPrize).divide(new BigDecimal(betPlan.getTotalUnit()));
			long createUserPrize = 0L;
			long totalJoinUserPrize = 0L;
			logger.info("�����ܷ���:"+betPlan.getTotalUnit());
			logger.info("����ûһ�ݽ���:"+oneFenPrize);
			
			//ѭ��������������Ϣ
			List<CpInfoDomain> coopInfoList = this.getBetOrderService().queryCoopInfoByPlanId(betPlan.getPlanId());
			if(coopInfoList != null){
				long coopPrize = 0;//�������Ľ�����ν綨��С����û�ж�
				String createUserInfoId = null;
				int coopLotteryId = OrderLotteryId;
				logger.info("�����ܲ��붩����:"+coopInfoList.size());
				for(CpInfoDomain oneCoop : coopInfoList){
					logger.info("�������:"+oneCoop.getCpInfoId()+"����:"+oneCoop.getCpOrderType()+"״̬��"+oneCoop.getOrderStatus());
					if(oneCoop.getCpOrderType() == 0 && oneCoop.getOrderStatus() == 4){//�������Ĳ����Ѿ�����Ʊ
						logger.info("�������");
						createUserInfoId = oneCoop.getCpInfoId();
						coopLotteryId = oneCoop.getLotteryId();
					}else if((oneCoop.getCpOrderType() == 1 || oneCoop.getCpOrderType() == 3) && oneCoop.getOrderStatus() == 4){//����Ͷע������תͶע
						logger.info("�������");
						coopPrize = (oneFenPrize.multiply(new BigDecimal(oneCoop.getCpUnit()))).longValue();
						int coopPrizeLevel = this.prizeLevel(oneCoop.getLotteryId(),String.valueOf(coopPrize));//ת���������𼶱�
						this.getBetOrderService().updateCoopPrize(oneCoop.getCpInfoId(), 7, coopPrizeLevel, coopPrize);
					}
					totalJoinUserPrize += coopPrize;
					logger.info("���²���ɹ�");
				}
				//���º������˵��н������𼶱��״̬
				createUserPrize = orderAftTaxPrize - totalJoinUserPrize;
				this.getBetOrderService().updateCoopPrize(
						createUserInfoId,
						7,
						this.prizeLevel(coopLotteryId, String
								.valueOf(createUserPrize)), createUserPrize);
			}
		}
		
		int upOrderPrizeLevel = prizeLevel;
		if(orderInfo.getOrderStatus() == 99){
			upOrderPrizeLevel = prizeLevel*100 + 99;
		}
		try {
			if (StringUtils.isNotEmpty(orderHitHaoMa.toString())) {
				if (orderHitHaoMa.toString().endsWith(",")) {
					orderHitHaoMa.deleteCharAt(orderHitHaoMa.lastIndexOf(","));
				}
			}
		} catch (Exception e) {

		}
		this.updateBetOrder(orderId, ORDER_STATUS_8, upOrderPrizeLevel, orderPrize,
				orderTaxPrize, orderAftTaxPrize, orderDeductPrize,
				orderCommPrize, orderHitHaoMa.toString());//���¶���״̬Ϊ�ҽ����
		
		//����ķ���δ��ƶҽ���״̬���ɲ����£���ѯʱ�ɲ�ѯ��Ʊ�������н����
		
		return orderHitHaoMa + "#" + prizeLevel + "#" + orderPrize;//���ؽ����Ҫ�޸�
	}
	/*
	 * (�� Javadoc)
	*Title: dispatchPrize
	*Description: 
	* @param heMaiOrDaiGou
	* @param lotteryId
	* @param dispatchTerm
	* @param orderId
	* @param isCheckLotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#dispatchPrize(int, int, java.lang.String, java.lang.String, boolean)
	 */
	public String dispatchPrize(int lotteryId, String dispatchTerm, String orderId,boolean isCheckLotteryId) throws LotteryException {
		BetOrderDomain orderInfo = this.checkBetOrderCanDispatchForUpdate(orderId);//��ȡ��������������
		
		int OrderLotteryId = orderInfo.getLotteryId();
		String OrderTermNo = orderInfo.getBetTerm();
		int orderWinStatus = orderInfo.getWinStatus();
		//20110421��Ϊ�Դ�Ҳ�ɽ�
		/*
		if(orderWinStatus == WIN_STATUS_3){//���д󽱵Ĳ�������
			return orderInfo.getWinStatus()+"#"+orderInfo.getPreTaxPrize()+"#��δ�ɽ�";//�д󽱵�ֱ�ӷ���
		}*/
		if(isCheckLotteryId && (OrderLotteryId != lotteryId || !OrderTermNo.equals(dispatchTerm))){//�ж϶�����������Ҫ�Զҽ��������Ƿ�һ��
			logger.info(E_08_CODE + ":" + E_08_DESC);
			throw new LotteryException(E_08_CODE,E_08_DESC);
		}
		if(orderWinStatus == WIN_STATUS_2 || orderWinStatus == WIN_STATUS_3 || orderWinStatus == 299 || orderWinStatus == 399){//��С�����д�,��Ҫ�����˻��ʽ�
			this.getUserService().accountTransaction(
					orderInfo.getUserId(),
					TRANSACTION_TYPE_11002,
					(int)orderInfo.getAftTaxPrize(),
					TRANSACTION_SOURCE_TYPE,
					orderInfo.getOrderId(),
					LotteryTools.accountTransactionRemark(orderInfo.getLotteryId(),
							orderInfo.getBetTerm(), TRANSACTION_TYPE_11002));
		}
		
		if (orderWinStatus == WIN_STATUS_1 || orderWinStatus == WIN_STATUS_2
				|| orderWinStatus == WIN_STATUS_3 || orderWinStatus == 199
				|| orderWinStatus == 299 || orderWinStatus == 399) {// ��δ�н�����С�����д󽱵ĸ��¶���״̬Ϊ����ɽ�
			try {
				this.getBetOrderService().updateBetOrderStatus(orderInfo.getOrderId(), ORDER_STATUS_10);
			} catch (Exception e) {
				logger.error("dispatchPrize error:", e);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
		}
		return orderInfo.getWinStatus()+"#"+orderInfo.getAftTaxPrize()+"#���ɽ����";
	}
	/*
	 * (�� Javadoc)
	*Title: dispatchCoopPrize
	*Description: 
	* @param lotteryId
	* @param dispatchTerm
	* @param chuPiaoOrder
	* @param paiJiangOrder
	* @param isCheckLotteryId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#dispatchCoopPrize(int, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public String dispatchCoopPrize(int lotteryId, String dispatchTerm, String chuPiaoOrder, String paiJiangOrder, boolean isCheckLotteryId) throws LotteryException {
		CpInfoDomain coopInfo = this.checkCoopOrderCanDispatchForUpdate(paiJiangOrder);//��ȡ������Ϣ����������
		
		int OrderLotteryId = coopInfo.getLotteryId();
		String OrderTermNo = coopInfo.getBetTerm();
		int orderWinStatus = coopInfo.getWinStatus();
		
		if(isCheckLotteryId && (OrderLotteryId != lotteryId || !OrderTermNo.equals(dispatchTerm))){//�ж϶�����������Ҫ�Զҽ��������Ƿ�һ��
			logger.info(E_08_CODE + ":" + E_08_DESC);
			throw new LotteryException(E_08_CODE,E_08_DESC);
		}
		if(orderWinStatus == WIN_STATUS_2 || orderWinStatus == WIN_STATUS_3){//��С�����д�,��Ҫ�����˻��ʽ�
			this.getUserService().accountTransaction(
					coopInfo.getUserId(),
					TRANSACTION_TYPE_11002,
					(int)coopInfo.getPrize(),
					TRANSACTION_SOURCE_TYPE,
					coopInfo.getCpInfoId(),
					LotteryTools.accountTransactionRemark(coopInfo.getLotteryId(),
							coopInfo.getBetTerm(), TRANSACTION_TYPE_11002));
		}
		
		if(orderWinStatus == WIN_STATUS_1 || orderWinStatus == WIN_STATUS_2 || orderWinStatus == WIN_STATUS_3){//��δ�н�����С�����д󽱵ĸ��¶���״̬Ϊ����ɽ�
			try {
				this.getBetOrderService().updateCoopInfoStatus(coopInfo.getCpInfoId(), 8);
			} catch (Exception e) {
				logger.error("dispatchCoopPrize error:", e);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
		}
		//��ķ�����ʱ����δ�ɽ���ɵĲ�����Ϣ
		int notDisNumInfo = this.getBetOrderService().getNotDispatchCoopNum(coopInfo.getPlanId());
		if(notDisNumInfo == 0){//�Ѿ�ȫ���ɽ�
			//���³�Ʊ������״̬Ϊ�Ѿ��ɽ�
			try {
				this.getBetOrderService().updateBetOrderStatus(chuPiaoOrder, ORDER_STATUS_10);
			} catch (Exception e) {
				logger.error("dispatchCoopPrize error:", e);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
		}
		return coopInfo.getWinStatus()+"#"+coopInfo.getPrize()+"#���ɽ����";
	}

	/* (�� Javadoc)
	 *Title: upCompleteStatus
	 *Description: 
	 * @param lotteryId
	 * @param termNO
	 * @throws LotteryException
	 * @see com.success.lottery.business.service.interf.CashPrizeInnerInterf#upCompleteStatus(int, java.lang.String)
	 */
	public String updateCashPrizeCompleteStatus(int lotteryId, String termNo)
			throws LotteryException {
		String old_status = "";
		String new_status = "";
		try {
			LotteryTermModel lotteryTermInfo = this.getTermService().queryTermInfo(lotteryId, termNo);
			String nextTerm = lotteryTermInfo.getNextTerm();
			int termWinStatus = lotteryTermInfo.getWinStatus();
			old_status = String.valueOf(termWinStatus);
			new_status = String.valueOf(termWinStatus);
			List<Integer> winStatus = new ArrayList<Integer>();
			winStatus.add(WIN_STATUS_0);//δ�ҽ�
			List<Integer> orderStatus = new ArrayList<Integer>();
			orderStatus.add(ORDER_STATUS_4);
			orderStatus.add(ORDER_STATUS_5);
			int notCompleteOrder = this.getBetOrderService().queryOrderNumByStatus(lotteryId, termNo, orderStatus, winStatus);//��Ʊ�ɹ�����δ�ҽ�
			int notCompleteZhuiHaoOrder = this.getBetOrderService().queryOrderNumByStatus(lotteryId, nextTerm, ORDER_STATUS_13, winStatus);//����׷���еĶ���
			
			logger.debug("�ҽ�---��δ��ɶҽ��Ķ�������:"+notCompleteOrder+"��δ�����׷�Ŷ�������:"+notCompleteZhuiHaoOrder);
			if(notCompleteOrder == 0 && notCompleteZhuiHaoOrder ==0){//�Ѿ�ȫ���ҽ���ɲ���׷��Ҳ�Ѿ��������
				List<Integer> winStatus2 = new ArrayList<Integer>();
				winStatus2.add(WIN_STATUS_2);//��С��
				int notCompleteOrder2 = this.getBetOrderService().queryOrderNumByStatus(lotteryId, termNo, ORDER_STATUS_8, winStatus2);//�ҽ���ɲ�����С��
				if(notCompleteOrder2 == 0){
					new_status = String.valueOf(TERM_WIN_STATUS_8);
					this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_8);
				}else{
					new_status = String.valueOf(TERM_WIN_STATUS_4);
					this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_4);
				}
			}else if(termWinStatus == TERM_WIN_STATUS_2){//����״̬�����ѿ���
				new_status = String.valueOf(TERM_WIN_STATUS_3);
				this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_3);
			}
		} catch (Exception e) {
			logger.error("�ҽ����²��ڱ�״̬����:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		return old_status + "#" + new_status;
	}
	/*
	 * (�� Javadoc)
	*Title: updateDispatchPrizeCompleteStatus
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param termWinStatus
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#updateDispatchPrizeCompleteStatus(int, java.lang.String, int)
	 */
	public void updateDispatchPrizeCompleteStatus(int lotteryId, String termNo,int termWinStatus) throws LotteryException{
		try {
			List<Integer> winStatus = new ArrayList<Integer>();
			//winStatus.add(WIN_STATUS_1);
			winStatus.add(WIN_STATUS_2);
			int notCompleteOrder = this.getBetOrderService().queryOrderNumByStatus(lotteryId, termNo, ORDER_STATUS_8, winStatus);
			logger.debug("�ɽ�---��δ����ɽ��Ķ�������:"+notCompleteOrder);
			if(notCompleteOrder == 0){//�Ѿ�ȫ���ɽ����
				this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_8);
			}else if(termWinStatus == TERM_WIN_STATUS_4){//����״̬���Ƕҽ��ɹ�
				this.getTermService().updateTermWinStatus(lotteryId, termNo, TERM_WIN_STATUS_7);
			}
		} catch (Exception e) {
			logger.error("�ɽ����²��ڱ�״̬����:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	
	/**
	 * 
	 * Title: cash<br>
	 * Description: <br>
	 *            ��Ͷע���ҽ�,���ذ��������Ľ��ֵ<br>
	 *            <br>��Ҫ�����Ƿ���㱶������ֵ������С���ʹ󽱵�����
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param lotteryResult
	 * @param winReslut
	 * @param betCode
	 * @param betMulti Ͷע����
	 * @return [�н�����,˰ǰ�ܽ��,��˰��,��˰��,���Ľ���] �н������Զ��ŷָ�,�н����Ϊ��
	 * @throws LotteryException<br>
	 *                          <br>E_02_DESC
	 */
	private String[] cash(int lotteryId, int playType,
			int betType, String lotteryResult, String winReslut, String betCode,int betMulti)throws LotteryException{
		String[] result = {"","","","",""};//���巵�ؽ��
		//��עע��[�н�����:����:��˰ǰ:��˰��], ��˰ǰ,��˰,��˰��,����н����
		List<PrizeInfo> prizeList = LotteryTools.lotteryPrize(lotteryId,
				playType, betType, lotteryResult, winReslut, betCode, betMulti);
		
		if(prizeList != null){
			StringBuffer sb = new StringBuffer();
			long totalPrePrize = 0;//��˰ǰ
			long totalTaxPrize = 0;//��˰��
			long totalAftTaxPrize = 0;//��˰����
			long maxPrePrize = 0;
			try {
				for(PrizeInfo onePrize : prizeList){//���㵥�������н����
					sb.append(onePrize.getWinCode().trim()).append(":").append(
							onePrize.getPrizeLevel()).append(":").append(
							((onePrize.getBasePrize() + onePrize.getAddPrize())/100))
							.append(":").append((onePrize.getAftTaxPrize())/100);
					sb.append(",");
					long ticketPrize = onePrize.getBasePrize() + onePrize.getAddPrize();
					maxPrePrize = (maxPrePrize > ticketPrize)?maxPrePrize:ticketPrize;
					totalPrePrize += ticketPrize;
					totalTaxPrize += onePrize.getTaxPrize();
					totalAftTaxPrize += onePrize.getAftTaxPrize();
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.lastIndexOf(","));
				}
			} catch (NumberFormatException e) {
				logger.error(E_02_DESC, e);
				throw new LotteryException(E_02_CODE,E_02_DESC);
			}
			result[0] = sb.toString();
			result[1] = String.valueOf(totalPrePrize);
			result[2] = String.valueOf(totalTaxPrize);
			result[3] = String.valueOf(totalAftTaxPrize);
			result[4] = String.valueOf(maxPrePrize);
			return result;
		}else{//û���н�,���öҽ�����ʱ�����쳣
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/**
	 * 
	 * Title: checkBetOrder<br>
	 * Description: <br>
	 *            ��ö�����Ϣ��У���ǹ����϶ҽ�����<br>
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException<br>
	 *                          <br>E_02_CODE
	 *                          <br>E_01_CODE
	 */
	private BetOrderDomain checkBetOrderCanCashForUpdate(String orderId) throws LotteryException{
		BetOrderDomain betOrdernfo = null;
		try {
			betOrdernfo = this.getBetOrderService().queryBetOrderByOrderIdForUpdate(orderId);
		} catch (Exception e) {
			logger.error("checkBetOrderCanCashForUpdate error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		return betOrdernfo;
	}
	/**
	 * 
	 * Title: checkBetOrderCanDispatchForUpdate<br>
	 * Description: <br>
	 *            ��ö�����Ϣ��У���ǹ������ɽ�����<br>
	 *            ֻҪ����״̬�Ƕҽ���ɵĶ���Ϊ�ǿ����ɽ�<br>
	 *            ���д󽱵Ĳ�������
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException
	 */
	private BetOrderDomain checkBetOrderCanDispatchForUpdate(String orderId) throws LotteryException{
		BetOrderDomain betOrdernfo = null;
		try {
			betOrdernfo = this.getBetOrderService().queryBetOrderByOrderIdForUpdate(orderId);
		} catch (Exception e) {
			logger.error("checkBetOrderCanDispatchForUpdate error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		if(betOrdernfo != null && betOrdernfo.getOrderStatus() == ORDER_STATUS_8){
			return betOrdernfo;
		}else{
			throw new LotteryException(E_06_CODE,E_06_DESC);
		}
	}
	/**
	 * 
	 * Title: checkCoopOrderCanDispatchForUpdate<br>
	 * Description: <br>
	 *              <br>��ȡ����������Ϣ������
	 * @param coopInfoId
	 * @return
	 * @throws LotteryException
	 */
	private CpInfoDomain checkCoopOrderCanDispatchForUpdate(String coopInfoId) throws LotteryException{
		CpInfoDomain coopInfo = null;
		try {
			coopInfo = this.getBetOrderService().queryCoopInfoByIdForUpdate(coopInfoId);
		} catch (Exception e) {
			logger.error("checkCoopOrderCanDispatchForUpdate error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		if(coopInfo != null && coopInfo.getOrderStatus() == 7){
			return coopInfo;
		}else{
			throw new LotteryException(E_06_CODE,E_06_DESC);
		}
	}
	
	/**
	 * 
	 * Title: checkTermInfo<br>
	 * Description: <br>
	 *            ��ȡ������Ϣ<br>
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException
	 */
	public BetOrderDomain getOrderInfo(String orderId) throws LotteryException{
		try {
			return this.getBetOrderService().queryBetOrderByOrderId(orderId);
		} catch (Exception e) {
			logger.error("getOrderInfo error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/*
	 * (�� Javadoc)
	*Title: getNotCashOrder
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#getNotCashOrder(int, java.lang.String)
	 */
	public List<String> getNotCashOrder(int lotteryId,String termNo) throws LotteryException{
		try{
			List<Integer> winStatus = new ArrayList<Integer>();
			winStatus.add(WIN_STATUS_0);
			winStatus.add(99);
			List<Integer> orderStatus = new ArrayList<Integer>();
			orderStatus.add(ORDER_STATUS_4);
			orderStatus.add(ORDER_STATUS_5);
			return this.getBetOrderService().queryOrderByStatus(lotteryId, termNo, orderStatus, winStatus);
		}catch(Exception e){
			logger.error("getNotCashOrder error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/*
	 * (�� Javadoc)
	*Title: getNotDispatchOrder
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#getNotDispatchOrder(int, java.lang.String)
	 */
	public List<String> getNotDispatchOrder(int lotteryId,String termNo) throws LotteryException{
		try{
			List<Integer> winStatus = new ArrayList<Integer>();
			winStatus.add(WIN_STATUS_1);
			winStatus.add(WIN_STATUS_2);
			winStatus.add(WIN_STATUS_3);
			return this.getBetOrderService().queryOrderByStatus(lotteryId, termNo, ORDER_STATUS_5, winStatus);
		}catch(Exception e){
			logger.error("getNotDispatchOrder error:", e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/**
	 * 
	 * Title: getPlanInfo<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param planId
	 * @return BetPlanDomain
	 * @throws LotteryException
	 */
	private BetPlanDomain getPlanInfo(String planId) throws LotteryException{
		try {
			return this.getBetOrderService().queryBetPlanByPlanId(planId);
		} catch (Exception e) {
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	/*
	 * (�� Javadoc)
	*Title: checkTermCanCash
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.CashPrizeInnerInterf#checkTermCanCash(int, java.lang.String)
	 */
	public LotteryTermModel checkTermCanCash(int lotteryId, String termNo)
			throws LotteryException {
		LotteryTermModel term = null;
		try {
			term = this.getTermService().queryTermInfo(lotteryId,termNo);
		} catch (Exception e) {
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		logger.debug("У������Ƿ��ܶҽ�����"+"WinStatus="+term.getWinStatus());
		if (term != null && (term.getWinStatus() == TERM_WIN_STATUS_2 || term.getWinStatus() == TERM_WIN_STATUS_3)) {// �ò��ֲ����Ѿ����Կ���
			return term;
		} else {
			throw new LotteryException(E_04_CODE, E_04_DESC);
		}
	}
	/*
	 * (�� Javadoc)
	*Title: checkTermCanDispatch
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#checkTermCanDispatch(int, java.lang.String)
	 */
	public LotteryTermModel checkTermCanDispatch(int lotteryId, String termNo) throws LotteryException{
		LotteryTermModel term = null;
		try {
			term = this.getTermService().queryTermInfo(lotteryId,termNo);
		} catch (Exception e) {
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		logger.debug("У������Ƿ����ɽ�����"+"WinStatus="+term.getWinStatus());
		if (term != null && (term.getWinStatus() == TERM_WIN_STATUS_4 || term.getWinStatus() == TERM_WIN_STATUS_7)) {// �ò��ֲ����Ѿ����Կ���
			return term;
		} else {
			throw new LotteryException(E_07_CODE, E_07_DESC);
		}
	}
	/**
	 * 
	 * Title: prizeLevel<br>
	 * Description: <br>
	 *            �����н������н�����<br>
	 * @param prizeMoney �н����
	 * @return 1:δ�н� 2:��С�� 3:�д�
	 */
	private int prizeLevel(int lotteryId,String prizeMoney){
		if(prizeMoney == null || "".equals(prizeMoney) || "0".equals(prizeMoney)){//δ�н�
			return WIN_STATUS_1;
		}else{
			String prizeLevelMoney = LotteryTools.getLotterySuperPrizeMoney(lotteryId);
			return (Long.parseLong(prizeMoney) > Long.parseLong(prizeLevelMoney)) ? WIN_STATUS_3 : WIN_STATUS_2;
		}
	}
	/**
	 * 
	 * Title: dealZhuiHao<br>
	 * Description: <br>
	 *            ׷�Ŵ���<br>
	 * @param planId �������
	 * @param orderId �������
	 * @param winStop �н����Ƿ�ֹͣ��־
	 * @param prizeLevel �н�����
	 * @throws LotteryException<br>E_02_CODE
	 *                          <br>AccountService�ж�����쳣
	 *                          <br>
	 */
	private void dealZhuiHao(String nextTerm,String planId,String orderId,int winStop,int prizeLevel) throws LotteryException{
		try {
			if(winStop == WIN_STOPED && prizeLevel != WIN_STATUS_1){//��Ҫ����׷��,ֹͣ׷�Ų����н���
				List<Integer> order_status_list = new ArrayList<Integer>();
				order_status_list.add(ORDER_STATUS_13);
				List<BetOrderDomain> orderList = this.getBetOrderService().queryOrderByOrderIdForSamePlan(planId, orderId,new ArrayList<String>(), order_status_list, WIN_STATUS_0);
				for(BetOrderDomain order : orderList){//ѭ�������������ͷŶ����ʽ�
					this.getBetOrderService().updateBetOrderStatus(order.getOrderId(), ORDER_STATUS_11);//��������
					//�ͷŶ����ʽ�
					this.getUserService().accountTransaction(
							order.getUserId(),
							TRANSACTION_TYPE_31006,
							order.getBetAmount(),
							TRANSACTION_SOURCE_TYPE,
							order.getOrderId(),
							LotteryTools.accountTransactionRemark(order.getLotteryId(),
									order.getBetTerm(), TRANSACTION_TYPE_31006));
				}
			}
		}catch(LotteryException ex){
			logger.error(ex.getType()+":"+ex.getMessage(), ex);
			throw ex;
		} catch (Exception e) {
			logger.error(E_02_DESC, e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	
	
	/**
	 * 
	 * Title: updateBetOrder<br>
	 * Description: <br>
	 *            ���¶����Ķ���״̬���н�״̬�ͽ���<br>
	 * @param orderId
	 * @param orderStatus
	 * @param winStatus
	 * @param prize
	 * @param reserve
	 * @throws LotteryException
	 */
	private void updateBetOrder(String orderId,int orderStatus,int winStatus,long prize,long taxPrize,long aftTaxPrize,long deductPrize,long commPrize,String winCode) throws LotteryException{
		try {
			this.getBetOrderService().updateBetOrderAndWinStatus(orderId, orderStatus, winStatus, prize,taxPrize,aftTaxPrize,deductPrize, commPrize,winCode);
		} catch (Exception e) {
			logger.error(E_02_DESC, e);
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}

	private LotteryTermServiceInterf getTermService() {
		return this.termService;
	}

	public void setTermService(LotteryTermServiceInterf termService) {
		this.termService = termService;
	}

	private AccountService getUserService() {
		return this.userService;
	}

	public void setUserService(AccountService userService) {
		this.userService = userService;
	}

	private BetPlanOrderServiceInterf getBetOrderService() {
		return this.betOrderService;
	}

	public void setBetOrderService(BetPlanOrderServiceInterf betOrderService) {
		this.betOrderService = betOrderService;
	}
	/*
	 * (�� Javadoc)
	*Title: dealNotTicketZhuiHao
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param limitNumber
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#dealNotTicketZhuiHao(int, java.lang.String, java.lang.String)
	 */
	public String dealNotTicketZhuiHao(int lotteryId, String termNo,
			String limitNumber, String orderId) throws LotteryException {
		String result = "";
		// ������׷�Ŷ�������ֹ�������ֹ�����
		BetOrderDomain orderInfo = this.getBetOrderService()
				.queryBetOrderByOrderIdForUpdate(orderId);
		if (orderInfo.getOrderStatus() == ORDER_STATUS_13) {// ��������׷����
			int modifyOrderStatus = ORDER_STATUS_0;
			boolean isLimit = false;
			// �ж϶����Ƿ��޺�,������޺ţ���Ҫ��isLimit����Ϊtrue
			try {
				isLimit = LotteryFactory.factory(String.valueOf(lotteryId))
						.isLimitBet(String.valueOf(orderInfo.getPlayType()),
								String.valueOf(orderInfo.getBetType()),
								limitNumber, orderInfo.getBetCode());
			} catch (LotteryUnDefineException e) {
				e.printStackTrace();
			}

			if (isLimit) {
				modifyOrderStatus = ORDER_STATUS_14;
			}
			result = String.valueOf(modifyOrderStatus);
			
			// ������״̬���˻�
			this.getBetOrderService().updateBetOrderStatus(
					orderInfo.getOrderId(), modifyOrderStatus);// ��������Ϊδ��ʼ����modifyOrderStatusΪ14ʱ�޺�ȡ������
			// �޺�׷�Ŷ������������ʽ�
			if (modifyOrderStatus == ORDER_STATUS_14) {
				// �ͷŶ����ʽ�
				this.getUserService().accountTransaction(
						orderInfo.getUserId(),
						TRANSACTION_TYPE_31006,
						orderInfo.getBetAmount(),
						TRANSACTION_SOURCE_TYPE,
						orderInfo.getOrderId(),
						LotteryTools.accountTransactionRemark(orderInfo
								.getLotteryId(), orderInfo.getBetTerm(),
								TRANSACTION_TYPE_31117));
			} else if (modifyOrderStatus == ORDER_STATUS_0) {
				// �۳��˻��ʽ�
				this.getUserService().accountTransaction(
						orderInfo.getUserId(),
						TRANSACTION_TYPE_30001,
						orderInfo.getBetAmount(),
						TRANSACTION_SOURCE_TYPE,
						orderInfo.getOrderId(),
						LotteryTools.accountTransactionRemark(orderInfo
								.getLotteryId(), orderInfo.getBetTerm(),
								TRANSACTION_TYPE_30001));
			}
		}

		return result;
	}
	/*
	 * (�� Javadoc) Title: checkNotTicketSucessOrder Description: @param
	 * lotteryId @param termNo @throws LotteryException
	 * 
	 * @see com.success.lottery.business.service.interf.PrizeInnerInterf#checkNotTicketSucessOrder(int,
	 *      java.lang.String)
	 */
	public void checkNotTicketSucessOrder(int lotteryId, String termNo) throws LotteryException {
		int result = 0;
		try{
			List<Integer> winStatus = new ArrayList<Integer>();
			List<Integer>  orderStatus = new ArrayList<Integer>();
			orderStatus.add(0);
			orderStatus.add(1);
			orderStatus.add(2);
			orderStatus.add(3);
			result = this.getBetOrderService().queryOrderNumByStatus(lotteryId, termNo, orderStatus, winStatus);// ���Ƿ�����Ҫȷ�ϳ�Ʊ����Ķ���
			if(result > 0){
				throw new LotteryException(E_09_CODE,E_09_DESC.replaceAll("A", String.valueOf(lotteryId)).replaceAll("B", termNo).replaceAll("C", String.valueOf(result)));
			}
		}catch(LotteryException ex){
			throw ex;
		}catch(Exception e){
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
	}
	
	public BetTicketServiceInterf getTicketService() {
		return ticketService;
	}
	public void setTicketService(BetTicketServiceInterf ticketService) {
		this.ticketService = ticketService;
	}
	/*
	 * (�� Javadoc)
	*Title: getTermInfo
	*Description: 
	* @param lotteryId
	* @param termNo
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#getTermInfo(int, java.lang.String)
	 */
	public LotteryTermModel getTermInfo(int lotteryId, String termNo) throws LotteryException {
		LotteryTermModel term = null;
		try {
			term = this.getTermService().queryTermInfo(lotteryId,termNo);
		} catch (Exception e) {
			throw new LotteryException(E_02_CODE,E_02_DESC);
		}
		return term;
	}
	/*
	 * (�� Javadoc)
	*Title: queryOrderByStatus
	*Description: 
	* @param lotteryId
	* @param termNo
	* @param orderStatus
	* @param winStatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.business.service.interf.PrizeInnerInterf#queryOrderByStatus(int, java.lang.String, int, java.util.List)
	 */
	public List<String> queryOrderByStatus(int lotteryId, String termNo, int orderStatus, List<Integer> winStatus) throws LotteryException {
		return this.getBetOrderService().queryOrderByStatus(lotteryId, termNo, orderStatus, winStatus);
	}
}
