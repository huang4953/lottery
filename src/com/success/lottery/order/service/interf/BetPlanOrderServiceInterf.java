/**
 * Title: BetPlanOrderServiceInterf.java
 * @Package com.success.lottery.order.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-9 ����03:25:15
 * @version V1.0
 */
package com.success.lottery.order.service.interf;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;
import com.success.lottery.order.domain.CpInfoDomain;

/**
 * com.success.lottery.order.service.interf
 * BetPlanOrderServiceInterf.java
 * BetPlanOrderServiceInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-9 ����03:25:15
 * 
 */

public interface BetPlanOrderServiceInterf {
	
	public static final int E_1001_CODE = 401001;
	public static final String E_1001_DESC = "���ɷ���������ʱ��������";
	public static final int E_1002_CODE = 401002;
	public static final String E_1002_DESC = "���ɷ�������������ʱ��������";
	public static final int E_1003_CODE = 401003;
	public static final String E_1003_DESC = "��ȡ����ʱ��������";
	public static final int E_1004_CODE = 401004;
	public static final String E_1004_DESC = "��������ʱ��������";
	public static final int E_1005_CODE = 401005;
	public static final String E_1005_DESC = "���ɲ�Ʊ������ʱ����";
	public static final int E_1006_CODE = 401006;
	public static final String E_1006_DESC = "���ɺ��򶩵�������ʱ��������";
	
	public static final int E_1007_CODE = 401007;
	public static final String E_1007_DESC = "��ѯ����������Ϊ�գ�";
	
	/**
	 * 
	 * Title: insertBetPlanOrder<br>
	 * Description: <br>ֻ�ܶԴ���ʹ��
	 *              <br>���ɷ��������ݷ������ɶ���
	 * @param betPlan <br>
	 *               BetPlanDomain�������Ҫʵ�����ö������ñ�Ҫ��ֵ<br>
	 *               �÷���δ�Բ���Ϊnull��������У�飬���в�����Ϊnull���������δ���ý��׳�LotteryException<br>
	 *               ��Ͷע�ַ��������Ƿ�ϸ�Ҳδ��У��<br>
	 *               �Ժ����ʵ�ʵ�ҵ����Ҫ�ٲ�����ص�У��<br>
	 *               δ���˻�������У��<br>
	 *               BetPlanDomain������ List terms���������洢׷�ŵ����������б��ܰ�����ǰ�����������û��׷�ţ������ø�����Ϊnull<br>
	 *               boolean isPutQuery ���Ա�ʾ���ɷ���ʱ�Ƿ�ֱ��д��Ʊ���У�Ĭ��Ϊfalse<br>
	 * @param chuPiaoOrder ��׷�ŵĶ������󣬿���ֱ���ͳ�Ʊ����
	 *               
	 * @return Ͷע�����ı�źͶ�Ӧ�����ı��#����
	 * @throws LotteryException <br>
	 *                           <br>BetPlanOrderServiceInterf.E_1001_CODE:���ɷ���������ʱ��������<br>
	 *                           <br>BetPlanOrderServiceInterf.E_1002_CODE:���ɷ�������������ʱ��������<br>
	 */
	public Map<String,List<String>> insertBetPlanOrder(BetPlanDomain betPlan,BetOrderDomain chuPiaoOrder) throws LotteryException;
	
	/**
	 * 
	 * Title: updateBetPlanStatus<br>
	 * Description: <br>
	 *            ���·������з�����״̬<br>
	 *            �÷���ֻ���򵥵ĸ��²����������߼��ϵ��ж�<br>
	 * @param planId �������
	 * @param planStatus ����״̬
	 * @return ���³ɹ��ļ�¼��
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:��������ʱ��������
	 */
	public int updateBetPlanStatus(String planId,int planStatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateBetOrderStatus<br>
	 * Description: <br>
	 *            ���¶������еĶ���״̬<br>
	 *            �÷���ֻ���򵥵ĸ��²����������߼��ϵ��ж�<br>
	 * @param orderId �������
	 * @param orderStatus ����״̬
	 * @return ���³ɹ��ļ�¼��
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:��������ʱ��������
	 */
	public int updateBetOrderStatus(String orderId,int orderStatus) throws LotteryException;
	/**
	 * 
	 * Title: updateBetOrderWinStatus<br>
	 * Description: <br>
	 *             ���¶������е��н�״̬<br>
	 *             �÷���ֻ���򵥵ĸ��²����������߼��ϵ��ж�<br>
	 * @param orderId �������
	 * @param winStatus �н�״̬
	 * @return ���³ɹ��ļ�¼��
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:��������ʱ��������
	 */
	public int updateBetOrderWinStatus(String orderId,int winStatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateBetOrderAndWinStatus<br>
	 * Description: <br>
	 *            ���¶������н�״̬���н�����ע��Ϣ���ñ�ע��Ϣ����н��ĺ��봮<br>
	 * @param orderId ����id
	 * @param orderStatus ����״̬
	 * @param winStatus �н�״̬
	 * @param prize  ˰ǰ����
	 * @param taxPrize ��˰��
	 * @param aftTaxPrize ˰�󽱽�
	 * @param deductPrize ��ɽ����ں���
	 * @param commPrize Ӷ�𣬻�δʹ��
	 * @param winCode �н����봮
	 * @return ���³ɹ��ļ�¼��
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:��������ʱ��������
	 */
	public int updateBetOrderAndWinStatus(String orderId,int orderStatus,int winStatus,long prize,long taxPrize,long aftTaxPrize,long deductPrize,long commPrize,String winCode) throws LotteryException;
	
	/**
	 * 
	 * Title: queryBetPlanByPlanId<br>
	 * Description: <br>
	 *            ���ݷ�����Ų�ѯ������Ϣ<br>
	 * @param planId
	 * @return BetPlanDomain
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:��ȡ����ʱ��������
	 */
	public BetPlanDomain queryBetPlanByPlanId(String planId) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryBetOrderByOrderId<br>
	 * Description: <br>
	 *            ���ݶ�����Ų�ѯ������Ϣ<br>
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:��ȡ����ʱ��������
	 */
	public BetOrderDomain queryBetOrderByOrderId(String orderId) throws LotteryException;
	
	/**
	 * 
	 * Title: queryBetOrderByOrderIdForUpdate<br>
	 * Description: <br>
	 *            ���ݶ�����Ų�ѯ������Ϣͬʱ��ס������Ϣ<br>
	 *            <br>�÷����������������
	 * @param orderId
	 * @return BetOrderDomain
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:��ȡ����ʱ��������
	 */
	public BetOrderDomain queryBetOrderByOrderIdForUpdate(String orderId) throws LotteryException;
	
	/**
	 * 
	 * Title: queryOrderByOrderIdForSamePlan<br>
	 * Description: <br>
	 *            <br>���ݷ�����Ų�ѯ��ö�������ͬһ�����Ķ���������������Ķ������
	 *            <br>
	 * @param planId �������
	 * @param orderId �������
	 * @param nextTerm ��һ�ڵ��б����Ϊ�ջ�null���ʾ������
	 * @param orderStatus ����״̬
	 * @param winStatus �н�״̬
	 * @return List<BetOrderDomain>
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:��ȡ����ʱ��������
	 */
	public List<BetOrderDomain> queryOrderByOrderIdForSamePlan(String planId,String orderId,List<String> nextTerm,List<Integer> orderStatus,int winStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryNotCashOrderNum<br>
	 * Description: <br>
	 *            ��ȡָ������״̬�Ϳ���״̬�Ķ��������Ķ�������<br>
	 * @param lotteryId ����id
	 * @param termNo ����
	 * @param orderStatus ����״̬
	 * @param winStatus �н�״̬�б�
	 * @return  int
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:��ȡ����ʱ��������
	 */
	public int queryOrderNumByStatus(int lotteryId,String termNo,int orderStatus,List<Integer> winStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryOrderNumByStatus<br>
	 * Description: <br>
	 *              <br>��ȡָ������״̬�Ϳ���״̬�Ķ��������Ķ�������
	 * @param lotteryId
	 * @param termNo
	 * @param orderStatus
	 * @param winStatus
	 * @return
	 * @throws LotteryException
	 */
	public int queryOrderNumByStatus(int lotteryId,String termNo,List<Integer> orderStatus,List<Integer> winStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryNotCashOrder<br>
	 * Description: <br>
	 *            ��ȡָ������״̬�Ϳ���״̬�Ķ������<br>
	 * @param lotteryId ����id
	 * @param termNo ����
	 * @param orderStatus ����״̬
	 * @param winStatus �н�״̬�б�
	 * @return ��������б�
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:��ȡ����ʱ��������
	 */
	public List<String> queryOrderByStatus(int lotteryId,String termNo,int orderStatus,List<Integer> winStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryOrderByStatus<br>
	 * Description: <br>
	 *              <br>��ȡָ������״̬�Ϳ���״̬�Ķ������
	 * @param lotteryId ����id
	 * @param termNo ����
	 * @param orderStatus ����״̬
	 * @param winStatus �н�״̬�б�
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryOrderByStatus(int lotteryId,String termNo,List<Integer> orderStatus,List<Integer> winStatus) throws LotteryException;
	/**
	 * Title: queryUndeliverTicketQueOrder<br>
	 * Description: <br>��ѯָ��״̬�Ķ���
	 *              <br>�÷�����������Ʊ���У�������orderStatusΪnull�����ֱ�ӷ���null
	 *              <br>
	 * @param orderStatus ����״̬�б�
	 * @param termInfo ���Գ�Ʊ�Ĳ��ֲ�����������ʽ�磺(lotteryid=10000001 and betterm='10091') or (lotteryid=10000002 and betterm='10071')
	 * @param limitNumber ȡ�޶��������ļ�¼
	 * @return ���orderStatusΪnull�����ֱ�ӷ���null
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:��ȡ����ʱ��������
	 */
	public List<BetOrderDomain> getOrders(List<Integer> orderStatus, String termInfo, int limitNumber) throws LotteryException;

	/**
	 * Title: ��ѯҪ����Ʊ����Ķ���<br>
	 * Description: <br>��ѯָ��״̬�Ķ�����ͬʱ�ö���������Ʊ�Ĳ�Ʊ״̬�б�����һ��������ָ����ticketStatus
	 *              <br>�÷������ڻ�ȡ�Ѿ���Ʊ�����еõ���Ʊȷ�Ͻ���ĵ��Ƕ�����δ�õ����ս���Ķ�����������orderStatusΪnull��գ�����ticketStatusΪnull��գ���ֱ�ӷ���null
	 *              <br>
	 * @param orderStatus
	 * @param ticketStatus
	 * @param limitNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> get2CheckOrders(List<Integer> orderStatus, List<Integer> ticketStatus, int limitNumber) throws LotteryException;

	/**
	 * 
	 * Title: getOrers<br>
	 * Description: <br>
	 *              <br>���ݷ�����Ų�ѯ�÷����µ����ж��������Զ�����״̬������
	 * @param planId ������� ����Ϊ���򷵻�null
	 * @return  List<BetOrderDomain>
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1003_CODE:��ȡ����ʱ��������
	 */
	public List<BetOrderDomain> getOrers(String planId) throws LotteryException;

	/**
	 * ����ָ��������ˮ�ŵĶ���״̬
	 * @param orderIds ����id�б�
	 * @param orderStatus ����״̬
	 * @return �ɹ����µļ�¼������������б�Ϊnull��գ������ֱ�ӷ���0
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:��������ʱ��������
	 */
	public int updateBetOrderStatus(List<String> orderIds, int orderStatus) throws LotteryException;

	/**
	 * ����ָ��������ˮ�ŵĶ���״̬��ֻ�е��ö���״̬Ϊwhoes��ָ���Ķ���״̬ʱ�Ÿ���
	 * @param orderIds ����id�б�
	 * @param orderStatus ����״̬
	 * @param whoes ��ǰҪ���µĶ�����״̬����Ϊwhoes�е�״̬
	 * @return �ɹ����µļ�¼������������б�Ϊnull��գ������ֱ�ӷ���0
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:��������ʱ��������
	 */
	public int updateBetOrderStatus(List<String> orderIds, int orderStatus, List<Integer> whoes) throws LotteryException;
	
	/**
	 * <br>����ָ��������ˮ�ŵĶ���״̬�Լ���Ʊ���
	 * <br>δ�Զ���״̬��ҵ���ϵ��ж�
	 * @param orderId ����id
	 * @param orderStatus  ����״̬
	 * @param ticketStat ��Ʊ���
	 * @return int �ɹ����µļ�¼�����������idΪnull��գ������ֱ�ӷ���0
	 * @throws LotteryException<br>
	 *                          <br>BetPlanOrderServiceInterf.E_1004_CODE:��������ʱ��������
	 */
	public int updateBetOrderTicketStat(String orderId, int orderStatus, String ticketStat) throws LotteryException;
	
	/**
	 * ��ѯ�û��ķ�������������׷�Ŷ��������ڿ�ʼʱ����ȡ���ڵ��ڵ���0ʱ0��0�룬���ڽ���ʱ����ȡС�ڵ��ڵ��� 23:59:59
	 * @param userId
	 * 		�û�id
	 * @param lotteryId
	 * 		����Id�������ѯ���в�������д0��-1
	 * @param termNo
	 * 		���ڣ������ѯ���в�������д null
	 * @param startDate
	 * 		��ʼʱ�䣬ֻ�������գ����û�п�ʼʱ������д null
	 * @param endDate
	 * 		����ʱ�䣬���û�н���ʱ������д null
	 * @param start
	 * 		��ѯ��ʼ�ļ�¼����
	 * @param count
	 * 		��ѯ������
	 * @return
	 * 		���ز�ѯ���Ķ����б��ö����б���indexΪ0��BetOrderDomain.lotteryId ��ŵ�Ϊ���β�ѯ��������������û�в�ѯ��Ҳ�ɻ�ø�ֵΪ0;
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> getUserChaseOrders(long userId, int lotteryId, String termNo, Date startDate, Date endDate, int start, int count) throws LotteryException;
	
	/**
	 * ��ѯ�û��ķ������������ж��������ڿ�ʼʱ����ȡ���ڵ��ڵ���0ʱ0��0�룬���ڽ���ʱ����ȡС�ڵ��ڵ��� 23:59:59
	 * @param userId
	 * 		�û�id 
	 * @param lotteryId
	 * 		����Id�������ѯ���в�������д0��-1 
	 * @param termNo
	 * 		���ڣ������ѯ���в�������д null 
	 * @param planSource
	 * 		Ͷע�����������ѯ���������������д-1
	 * @param orderStatus
	 *		����״̬�������ѯ����״̬�������д-1
	 *		��д����ֵ��ʾ��ѯ����Ϊ�� 
	 *		100 - ��Ʊ�� 0,1,2
	 *		200 - �ѳ�Ʊ��ֻ������Ʊ�ɹ��ģ�5
	 *		300 - �Ѷҽ�8
	 *		400 - ���ɽ�10
	 *		500 - ׷����13
	 *		600 - ��ȡ��11,12,14
	 *		700 - ��Ʊʧ��3,4,6
	 *		800 - ��׷���Լ������Ķ������û�Ͷע���Ķ���1-10
	 * @param winStatus
	 * 		�н�״̬�������ѯ����״̬�������д-1 
	 * @param startDate
	 * 		��ʼʱ�䣬ֻ�������գ����û�п�ʼʱ������д null 
	 * @param endDate
	 * 		����ʱ�䣬���û�н���ʱ������д null 
	 * @param start
	 * 		��ѯ��ʼ�ļ�¼���� 
	 * @param count
	 * 		��ѯ������
	 * @return
	 * 		���ز�ѯ���Ķ����б��ö����б���indexΪ0��BetOrderDomain.lotteryId ��ŵ�Ϊ���β�ѯ��������������û�в�ѯ��Ҳ�ɻ�ø�ֵΪ0; 
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> getUserOrders(long userId, int lotteryId, String termNo, int planSource, int orderStatus, int winStatus, Date startDate, Date endDate, int start, int count) throws LotteryException;
	
	/**
	 * ��ѯ�û��Ѿ�����ɽ��Ķ�����¼�����ڿ�ʼʱ����ȡ���ڵ��ڵ���0ʱ0��0�룬���ڽ���ʱ����ȡС�ڵ��ڵ��� 23:59:59
	 * @param userId
	 * 		�û�id 
	 * @param lotteryId
	 * 		����Id�������ѯ���в�������д0��-1 
	 * @param termNo
	 * 		���ڣ������ѯ���в�������д null
	 * @param startDate
	 * 		��ʼʱ�䣬ֻ�������գ����û�п�ʼʱ������д null 
	 * @param endDate
	 * 		����ʱ�䣬���û�н���ʱ������д null 
	 * @param start
	 * 		��ѯ��ʼ�ļ�¼���� 
	 * @param count
	 * 		��ѯ������
	 * @return
	 * 		���ز�ѯ���Ķ����б�
	 *		���б���indexΪ0��BetOrderDomain�������ֶ��д�Ų�ͬ��ֵ��
	 *			planSource�����β�ѯ���������������β�ѯ�����ۼ��н�����
	 *			preTaxPrize�����β�ѯ���ܽ������β�ѯ�����ۼ��н����
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> getUserCashedOrders(long userId, int lotteryId, String termNo, Date startDate, Date endDate, int start, int count) throws LotteryException;
	
	/**
	 * 
	 * Title: queryBetPlanForUpdate<br>
	 * Description: <br>
	 *              <br>��ѯ���������������ں������
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public BetPlanDomain queryBetPlanForUpdate(String planId) throws LotteryException;
	/**
	 * 
	 * Title: updateBetPlanSelledUnit<br>
	 * Description: <br>
	 *              <br>���º��򷽰����Ⱥͷ���״̬
	 * @param planId
	 * @param selledUnit
	 * @param planStatus
	 * @return
	 * @throws LotteryException
	 */
	public int updateBetPlanSelledUnit(String planId,int selledUnit,int planStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryCoopInfoByPlanId<br>
	 * Description: <br>
	 *              <br>���ݷ�����Ų�ѯ���еĲ�����Ϣ
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public List<CpInfoDomain> queryCoopInfoByPlanId(String planId) throws LotteryException;
	/**
	 * 
	 * Title: updateCoopInfoStatus<br>
	 * Description: <br>
	 *              <br>���º��������Ϣ��״̬
	 * @param coopInfoId
	 * @param orderStatus
	 * @return
	 * @throws LotteryException
	 */
	public int updateCoopInfoStatus(String coopInfoId,int orderStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryCoopInfoByIdForUpdate<br>
	 * Description: <br>
	 *              <br>���ݲ��������Ϣid�������Ϣ
	 * @param coopInfoId
	 * @return
	 * @throws LotteryException
	 */
	public CpInfoDomain queryCoopInfoByIdForUpdate(String coopInfoId) throws LotteryException;
	/**
	 * 
	 * Title: createCoopBetOrder<br>
	 * Description: <br>
	 *              <br>���ɺ���ĳ�Ʊ����
	 * @param planId
	 * @param planSource
	 * @param userId
	 * @param areaCode
	 * @param lotteryId
	 * @param betTerm
	 * @param playType
	 * @param betType
	 * @param betCodeMode
	 * @param betCode
	 * @param betMultiple
	 * @param betAmount
	 * @param orderStatus
	 * @return
	 * @throws LotteryException
	 */
	public String createCoopBetOrder(String planId,int planSource,long userId,String areaCode,int lotteryId,String betTerm,int playType
			,int betType,int betCodeMode,String betCode,int betMultiple,int betAmount,int orderStatus) throws LotteryException;
	/**
	 * 
	 * Title: createCoopInfo<br>
	 * Description: <br>
	 *              <br>���ɺ��������Ϣ
	 * @param planId
	 * @param userId
	 * @param lotteryId
	 * @param betTerm
	 * @param playType
	 * @param betType
	 * @param cpOrderType
	 * @param cpUnit
	 * @param cpAmount
	 * @param orderStatus
	 * @return
	 * @throws LotteryException
	 */
	public String createCoopInfo(String planId,long userId,int lotteryId,String betTerm,int playType,int betType,int cpOrderType,int cpUnit,
			int cpAmount,int orderStatus) throws LotteryException;
	/**
	 * 
	 * Title: createCoopPlan<br>
	 * Description: <br>
	 *              <br>���ɺ��򷽰�
	 * @param userId
	 * @param areaCode
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betTotalMoney
	 * @param term
	 * @param coopDeadLine
	 * @param betCode
	 * @param planOpenType
	 * @param totalUnit
	 * @param unitPrice
	 * @param selfBuyNum
	 * @param unitbuyself
	 * @param commisionPercent
	 * @param planSource
	 * @param planTitle
	 * @param planDescription
	 * @param planStatus
	 * @return
	 * @throws LotteryException
	 */
	public String createCoopPlan(long userId,String areaCode, int lotteryId, int playType,
			int betType, int betMultiple, int betTotalMoney,String term,Timestamp coopDeadLine, String betCode,
			int planOpenType, int totalUnit, int unitPrice, int selfBuyNum,
			int unitbuyself, int commisionPercent, int planSource,
			String planTitle, String planDescription,int planStatus) throws LotteryException;
	/**
	 * 
	 * Title: queryBetPlan<br>
	 * Description: <br>
	 *              <br>��ȡָ�����ֲ��ڣ����ͣ�״̬�ķ���
	 * @param lotteryAndTerm ��ʽ�����֣�����
	 * @param planType 0:������1������
	 * @param planStatus 
	 * @return
	 * @throws LotteryException
	 */
	public List<BetPlanDomain> queryBetPlan(
			Map<Integer, String> lotteryAndTerm, List<Integer> planType,
			List<Integer> planStatus) throws LotteryException;
	/**
	 * 
	 * Title: updateCoopPrize<br>
	 * Description: <br>
	 *              <br>���º��������н����𣬽���Ϊ˰��Ĳ����Ѿ�����ɹ���
	 * @param coopInfoId ������Ϣid
	 * @param orderStatus ����״̬
	 * @param winStatus ����״̬
	 * @param prize ���𣬷�
	 * @return
	 * @throws LotteryException
	 */
	public int updateCoopPrize(String coopInfoId,int orderStatus,int winStatus,long prize) throws LotteryException;
	/**
	 * 
	 * Title: queryBetOrderByPlanId<br>
	 * Description: <br>
	 *              <br>���ݷ�����Ų��Ʊ����
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public List<BetOrderDomain> queryBetOrderByPlanId(String planId) throws LotteryException;
	/**
	 * 
	 * Title: queryCoopInfoByInfoId<br>
	 * Description: <br>
	 *              <br>���ݺ�����붩����Ų�ѯ������Ϣ
	 * @param coopInfoId
	 * @return
	 * @throws LotteryException
	 */
	public CpInfoDomain queryCoopInfoByInfoId(String coopInfoId) throws LotteryException;
	/**
	 * 
	 * Title: getNotDispatchCoopNum<br>
	 * Description: <br>
	 *              <br>��ѯһ�����򷽰���û���ɽ���ɵĲ�����
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public int getNotDispatchCoopNum(String planId) throws LotteryException;
	
	
}
