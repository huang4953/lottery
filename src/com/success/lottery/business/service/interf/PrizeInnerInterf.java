/**
 * Title: PrizeInnerInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(�ҽ����ɽ��ڲ�������)
 * @author gaoboqin
 * @date 2010-4-21 ����05:00:57
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.term.domain.LotteryTermModel;

/**
 * com.success.lottery.business.service.interf
 * PrizeInnerInterf.java
 * PrizeInnerInterf
 * �ҽ����ɽ��ڲ�������
 * @author gaoboqin
 * 2010-4-21 ����05:00:57
 * 
 */

public interface PrizeInnerInterf {
	
	public static final int E_01_CODE = 510001;
	public static final String E_01_DESC = "�ö��������϶ҽ�������";
	
	public static final int E_04_CODE = 510002;
	public static final String E_04_DESC = "�ö�����Ӧ�Ĳ��ڲ����ҽ�ʱ�䣡";
	
	public static final int E_05_CODE = 510003;
	public static final String E_05_DESC = "�ö�����Ӧ�Ĳ�����Ҫ�ҽ���������һ�£�";
	
	public static final int E_09_CODE = 510009;
	public static final String E_09_DESC = "����[A]����[B]����[C]������δ����Ʊȷ�ϣ����ܶҽ���";
	
	
	/*
	 * �����쳣Ϊ�ɽ��쳣
	 */
	public static final int E_06_CODE = 510004;
	public static final String E_06_DESC = "�ö����������ɽ�������";
	
	public static final int E_07_CODE = 510005;
	public static final String E_07_DESC = "�ö�����Ӧ�Ĳ��ڲ����ɽ�ʱ�䣡";
	
	public static final int E_08_CODE = 510006;
	public static final String E_08_DESC = "�ö�����Ӧ�Ĳ�����Ҫ�ɽ���������һ�£�";
	
	/*
	 * �����쳣Ϊ�����쳣
	 */
	public static final int E_02_CODE = 511001;
	public static final String E_02_DESC = "���������쳣��";
	
	
	/**
	 * 
	 * Title: cashPrize<br>
	 * Description: <br>�Ե��������ҽ�,ֻ�Գ�Ʊ�ɹ��Ķ����ҽ�,�÷�����Ҫʵ�����²��裺
	 *            <br>(1)���ݶ�����źͶ���״̬Ϊ��Ʊ�ɹ����н�״̬Ϊ0��δ�ҽ�����ѯ��������ס�ö�����������������
	 *            <br>(2)�жϴ���Ĳ������ֺͲ����Ƿ�Ͷ����Ĳ��ֲ���һ��
	 *            <br>(3)���ݲ�����Ϣ�����н�����ƥ��
	 *            <br>(4)��ȡ�н����Ƿ�ֹͣ׷��
	 *            <br>(5)����׷��
	 *            <br>(6)���¶���״̬Ϊ�ҽ����
	 * @param lotteryId ����id
	 * @param cashTerm ����
	 * @param orderId �������
	 * @param termInfo ������Ϣ
	 * @param isCheckLotteryId �Ƿ���Ҫ�жϲ��ֺͲ��ڣ������������Զ�������Ҫ�жϣ����������Ҫ�ж�
	 * @return �н�����#���𼶱�#������
	 * @throws LotteryException<br>
	 *                          <br>E_01_CODE
	 *                          <br>E_05_CODE
	 *                          <br>E_02_CODE
	 *                          <br>
	 *                          <br>
	 *                          <br>
	 *                          <br>
	 */
	public String cashPrize(int lotteryId,String cashTerm, String orderId,LotteryTermModel termInfo,boolean isCheckLotteryId,Map<String,String> outResult) throws LotteryException;
	/**
	 * 
	 * Title: dispatchPrize<br>
	 * Description: <br>�����ɽ�
	 *              <br>�Ե��������ɽ���ֻ�Զҽ��ɹ��������ɽ��Ŀ��Խ����ɽ�<br>
	 * @param lotteryId
	 * @param dispatchTerm
	 * @param orderId
	 * @param isCheckLotteryId
	 * @return ���𼶱�#������#������
	 * @throws LotteryException<br>
	 *                          <br>510006:�ö�����Ӧ�Ĳ�����Ҫ�ɽ���������һ��
	 *                          
	 */
	public String dispatchPrize(int lotteryId,String dispatchTerm, String orderId,boolean isCheckLotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: dispatchCoopPrize<br>
	 * Description: <br>�����ɽ�
	 *              <br>�Ժ���ĵ��������ɽ�
	 * @param lotteryId ����
	 * @param dispatchTerm �ɽ�����
	 * @param chuPiaoOrder ��Ʊ������id
	 * @param paiJiangOrder �ɽ�������id 
	 * @param isCheckLotteryId �Ƿ�ȶԲ���
	 * @return
	 * @throws LotteryException
	 */
	public String dispatchCoopPrize(int lotteryId,String dispatchTerm,String chuPiaoOrder, String paiJiangOrder,boolean isCheckLotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: checkTermCanCash<br>
	 * Description: <br>
	 *            У������Ƿ���Զҽ������ز�����Ϣ<br>
	 * @param lotteryId ����id
	 * @param termNo �ں�
	 * @return ������Ϣ
	 * @throws LotteryException<br>
	 *                          <br>510002:�ö�����Ӧ�Ĳ��ڲ����ҽ�ʱ��
	 *                          <br>511001:�ҽ��������
	 */
	public LotteryTermModel checkTermCanCash(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: checkTermCanDispatch<br>
	 * Description: <br>
	 *            У������Ƿ�����ɽ��������ز�����Ϣ<br>
	 * @param lotteryId
	 * @param termNo
	 * @return LotteryTermModel
	 * @throws LotteryException<br>
	 *                          <br>510005:�ö�����Ӧ�Ĳ��ڲ����ɽ�ʱ��
	 *                          <br>511001:�ɽ��������
	 */
	public LotteryTermModel checkTermCanDispatch(int lotteryId, String termNo) throws LotteryException;
	/**
	 * 
	 * Title: updateCompleteStatus<br>
	 * Description: <br>
	 *            �����������²��ڱ��״̬Ϊ�ҽ���ɻ����ڶҽ�<br>
	 * @param lotteryId ����id
	 * @param termNo ����
	 * @throws LotteryException<br>
	 *                          <br>
	 */
	public String updateCashPrizeCompleteStatus(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: updateDispatchPrizeCompleteStatus<br>
	 * Description: <br>
	 *            �����������²��ڱ��״̬Ϊ�ɽ���ɻ������ɽ�<br>
	 * @param lotteryId ����id
	 * @param termNo ����
	 * @param termWinStatus ���ڵĵ�ǰ״̬
	 * @throws LotteryException
	 */
	public void updateDispatchPrizeCompleteStatus(int lotteryId, String termNo,int termWinStatus) throws LotteryException;
	/**
	 * 
	 * Title: getOrderInfo<br>
	 * Description: <br>
	 *            ���ݶ���id�õ�������Ϣ��������<br>
	 * @param orderId ����id
	 * @return BetOrderDomain
	 * @throws LotteryException<br>
	 *                          <br>511001:�������
	 */
	public BetOrderDomain getOrderInfo(String orderId) throws LotteryException;
	/**
	 * 
	 * Title: getNotCashOrder<br>
	 * Description: <br>
	 *            ��ȡ���Զҽ�������δ�ҽ��Ķ������<br>
	 * @param lotteryId ����id
	 * @param termNo ����
	 * @return �������
	 * @throws LotteryException<br>
	 *                          <br>511001:�ҽ��������
	 */
	public List<String> getNotCashOrder(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: getNotDispatchOrder<br>
	 * Description: <br>
	 *            ��ȡ�����ɽ�����δ�ɽ��Ķ������<br>
	 * @param lotteryId
	 * @param termNo
	 * @return List<String>
	 * @throws LotteryException
	 */
	public List<String> getNotDispatchOrder(int lotteryId,String termNo) throws LotteryException;
	
	/**
	 * 
	 * Title: dealNotTicketZhuiHao<br>
	 * Description: <br>
	 *              <br>������δ�����Ʊ��׷�Ŷ���
	 * @param lotteryId ����
	 * @param termNo �ҽ��ڵ���һ��
	 * @param limitNumber �޺���Ϣ
	 * @param orderId ��Ҫ����Ķ������
	 * @throws LotteryException
	 */
	public String dealNotTicketZhuiHao(int lotteryId,String termNo,String limitNumber,String orderId) throws LotteryException;
	
	/**
	 * 
	 * Title: getNotTicketSucessOrderNum<br>
	 * Description: <br>
	 *              <br>��ѯһ�������ж���״̬��0��1��2��3�Ķ�������������ܲ鵽����˵�����ж�����Ҫ��Ʊȷ�ϣ��ò��ڲ��ܶҽ�
	 * @param lotteryId
	 * @param termNo
	 * @throws LotteryException
	 */
	public void checkNotTicketSucessOrder(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: getTermInfo<br>
	 * Description: <br>
	 *              <br>��ȡ������Ϣ
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 */
	public LotteryTermModel getTermInfo(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: queryOrderByStatus<br>
	 * Description: <br>
	 *              <br>��ȡָ��״̬�Ķ���
	 * @param lotteryId
	 * @param termNo
	 * @param orderStatus
	 * @param winStatus
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryOrderByStatus(int lotteryId,String termNo,int orderStatus,List<Integer> winStatus) throws LotteryException;
	
	//public BetOrderDomain getCoopInfo(String coopInfoId) throws LotteryException;
	

}
