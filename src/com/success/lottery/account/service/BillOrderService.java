package com.success.lottery.account.service;

import java.util.Date;
import java.util.List;

import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.BillOrderModel;
import com.success.lottery.account.model.IPSOrderModel;
import com.success.lottery.exception.LotteryException;

/**
 * ��Ǯ֧���ӿ�
 * @author aaron
 *
 */
public interface BillOrderService {
	public static final String resource = "com.success.lottery.account.service.bill";
	
	// ����֧������ʱ�����쳣
	public static final int		ADDIPSORDEREXCEPTION				= 101301;
	public static final String	ADDIPSORDEREXCEPTION_STR			= "���ɶ���ʱ�����쳣��";
	
	// ��ѯ���������쳣
	public static final int		GETORDEREXCEPTION					= 101302;
	public static final String	GETORDEREXCEPTION_STR				= "��ѯ����ʱ���ֳ����쳣��";

	// ����IPS������Ϣʱ���ֳ����쳣
	public static final int		PROCESSIPSRETURNEXCEPTION			= 101303;
	public static final String	PROCESSIPSRETURNEXCEPTION_STR		= "����99bill������Ϣʱ���ֳ����쳣��";

	// ��ѯ������Ϣʱ���ֳ����쳣
	public static final int		GETORDERESEXCEPTION			= 101304;
	public static final String	GETORDERESEXCEPTION_STR		= "��ѯ������Ϣʱ���ֳ����쳣��";

	
	
	// û�в�ѯ������
	public static final int		ORDERNOTFOUND						= 100301;
	public static final String	ORDERNOTFOUND_STR					= "����δ�ҵ�";

	// ����Ŀ�Ǯ������Ϣ
	public static final int		IPSRETURNERROR						= 100302;
	public static final String	IPSRETURNERROR_STR					= "����Ŀ�Ǯ������Ϣ";
	
	// �ظ��Ŀ�Ǯ������Ϣ
	public static final int		DUPLICATIONIPSRETURN						= 100303;
	public static final String	DUPLICATIONIPSRETURN_STR					= "�ظ��Ŀ�Ǯ������Ϣ";
	
	// ��Ǯ������Ϣ�Ľ�����
	public static final int		AMOUNTERRORIPSRETURN						= 100304;
	public static final String	AMOUNTERRORIPSRETURN_STR					= "��Ǯ������Ϣ�г����˴���Ľ��";
	
	// ���¿�Ǯ����δ�ܸ��µ���¼
	public static final int		NOIPSORDERUPDATE							= 100305;
	public static final String	NOIPSORDERUPDATE_STR					= "û�ж�����¼������";
	
	// ���¿�Ǯ����δ�ܸ��µ���¼
	public static final int		VERIFYIPSRETURNERROR							= 100306;
	public static final String	VERIFYIPSRETURNERROR_STR					= "У���Ǯ������Ϣδͨ��";
	
	//û���ҵ�������ϸ��¼
	public static final int     ACCOUNTTRANSACTIONNOTFIND                    =100401;
	public static final String     ACCOUNTTRANSACTIONNOTFIND_STR                    ="������ϸ��¼û���ҵ�";
	
	/**
	 * ��ӿ�Ǯ��ֵ 
	 * @param billOrder
	 */
	public String addBillOrder(long userId, String username,long amount)throws LotteryException;
	
	/**
	 * ����orderId��ѯ ��Ǯ���׼�¼
	 * @param orderId
	 * @return
	 */
	public BillOrderModel getBillOrderByOrderId(String orderId) throws LotteryException;
	
	/**
	 * ����orderId�޸Ŀ�Ǯ���׼�¼ 
	 * @param orderId
	 * @param dealId
	 * @param fee
	 * @param bankId
	 * @param bankDealId
	 * @param dealTime
	 * @param payAmount
	 * @param ext1
	 * @param ext2
	 * @param payResult
	 * @param errCode
	 * @param signMsg
	 * @param merchantSignMsg
	 * @return
	 * @throws LotteryException
	 */
	public int processBILLOrder(String orderId,String dealId,String fee,String bankId,String bankDealId,String dealTime,String payAmount,String ext1,String ext2,String payResult,String errCode,String signMsg,String merchantSignMsg) throws LotteryException;
	
	public AccountTransactionModel getUserTransactionBySourceSequence(String sourceSequence, int sourceType);
	/**
	 * ��ÿ�Ǯ������Ϣ�����ڿ�ʼʱ����ȡ���ڵ��ڵ���0ʱ0��0�룬���ڽ���ʱ����ȡС�ڵ��ڵ��� 23:59:59
	 * @param userIdentify
	 * 		�û���ʶ����������loginname, mobilephone, email, realname
	 * @param startDate
	 * 		��ʼʱ�䣬ֻ�������գ����û�п�ʼʱ������д null 
	 * @param endDate
	 * 		����ʱ�䣬���û�н���ʱ������д null 
	 * @param orderStatus
	 * 		��ѯ������д-1����Ѹ֧������״̬���б����£�5��6��ʱ���ã���
	 * 			0 - �ȴ���Ѹ֧�����
	 * 			1 - ֧���ɹ���ֵ�ɹ�
	 * 			2 - ֧���ɹ���ֵʧ��
	 * 			3 - ֧��ʧ��
	 * 			4 - ֧��ʧ�ܳ�ֵ�ɹ�
	 * 			5 - ��ƽ�˴���ɹ�
	 * 			6 - ��ƽ�˴���ʧ��
	 * @param start
	 * @param count
	 * @return
	 * @throws LotteryException
	 */
	public List<BillOrderModel> getIPSOrderes(String userIdentify, Date startDate, Date endDate, int orderStatus, int start, int count) throws LotteryException;
	
	
}
