package com.success.lottery.account.service;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

//import com.success.lottery.account.model.CheckIpsorderModel;
import com.success.lottery.account.model.IPSOrderModel;
import com.success.lottery.account.model.IPSReturnInfo;
import com.success.lottery.exception.LotteryException;

public interface IPSOrderService {
//	orderid amount userid currencytype gatewaytype attach ordermessage accounttransactionid 

	public static final String resource = "com.success.lottery.account.service.ips";

	// ����֧������ʱ�����쳣
	public static final int		ADDIPSORDEREXCEPTION				= 101301;
	public static final String	ADDIPSORDEREXCEPTION_STR			= "���ɶ���ʱ�����쳣��";
	
	// ��ѯ���������쳣
	public static final int		GETORDEREXCEPTION					= 101302;
	public static final String	GETORDEREXCEPTION_STR				= "��ѯ����ʱ���ֳ����쳣��";

	// ����IPS������Ϣʱ���ֳ����쳣
	public static final int		PROCESSIPSRETURNEXCEPTION			= 101303;
	public static final String	PROCESSIPSRETURNEXCEPTION_STR		= "����IPS������Ϣʱ���ֳ����쳣��";

	// ��ѯ������Ϣʱ���ֳ����쳣
	public static final int		GETORDERESEXCEPTION			= 101304;
	public static final String	GETORDERESEXCEPTION_STR		= "��ѯ������Ϣʱ���ֳ����쳣��";

	
	
	// û�в�ѯ������
	public static final int		ORDERNOTFOUND						= 100301;
	public static final String	ORDERNOTFOUND_STR					= "����δ�ҵ�";

	// ����Ļ�Ѹ������Ϣ
	public static final int		IPSRETURNERROR						= 100302;
	public static final String	IPSRETURNERROR_STR					= "����Ļ�Ѹ������Ϣ";
	
	// �ظ��Ļ�Ѹ������Ϣ
	public static final int		DUPLICATIONIPSRETURN						= 100303;
	public static final String	DUPLICATIONIPSRETURN_STR					= "�ظ��Ļ�Ѹ������Ϣ";
	
	// ��Ѹ������Ϣ�Ľ�����
	public static final int		AMOUNTERRORIPSRETURN						= 100304;
	public static final String	AMOUNTERRORIPSRETURN_STR					= "��Ѹ������Ϣ�г����˴���Ľ��";
	
	// ���»�Ѹ����δ�ܸ��µ���¼
	public static final int		NOIPSORDERUPDATE							= 100305;
	public static final String	NOIPSORDERUPDATE_STR					= "û�ж�����¼������";
	
	// ���»�Ѹ����δ�ܸ��µ���¼
	public static final int		VERIFYIPSRETURNERROR							= 100306;
	public static final String	VERIFYIPSRETURNERROR_STR					= "У�黷Ѹ������Ϣδͨ��";
	
	
	/**
	 * ȷ�Ͻ��л�Ѹ֧�������ɻ�Ѹ֧������
	 * @param userId
	 * 		���л�Ѹ֧�����û�id
	 * @param amount
	 * 		֧������λ��
	 * @param attach
	 * 		�ύ��Ѹ��������ʱ���̻����ݰ�
	 * @return
	 * 		���ز���Ļ�Ѹ�����Ķ�����ţ�����Ҫ�ύ����Ѹ���̻��������Billno
	 * @throws LotteryException <br>
	 * 		 IPSOrderService.ADDIPSORDEREXCEPTION
	 */
	public String addIPSOrder(long userId, int amount, String attach) throws LotteryException;
	
	/**
	 * ���ݶ�����Ż�ȡ��Ѹ֧����¼��Ϣ���÷���������ͨ��ѯ������Ϣ
	 * @param orderId
	 * 		ָ���Ļ�Ѹ֧���������
	 * @return
	 * 		��Ѹ֧����Ϣ��nullΪû���ҵ�
	 * @throws LotteryException
	 */
	public IPSOrderModel getIPSOrder(String orderId) throws LotteryException;
	
	/**
	 * �Ի�Ѹ֧�����صĽ����Ϣ���д������ݶ�����Ų�����Ӧ��¼�������ݽ����״̬��������и��£�ͬʱ��¼��־��<br>
	 * �˲�����Ҫ�Լ�¼���������������ظ�����<br>
	 * �����������£�<br>
	 * 1��IPSOrderDAO.getIPSOrderForUpdate(orderId)����ѯ������¼���ڸ���<br>
	 * 2�����Ϊnull���¼��־�����׳��쳣<br>
	 * 3������succFlag�Լ���ǰ����״̬���д���<br>
	 * 4�����Ϊ�ɹ�����orderStatus = 0������б����ֵ<br>
	 * 5������orderStatus, checkedStatus�Լ�����״̬��Ϣ
	 * @param orderId
	 * @param ipsBillNo
	 * @param succFlag
	 * @param ipsMsg
	 * @param ipsBankTime
	 * @return
	 * @throws LotteryException
	 * 		IPSRETURNERROR
	 * 		ORDERNOTFOUND
	 * 		DUPLICATIONIPSRETURN
	 * 		AMOUNTERRORIPSRETURN
	 * 		NOIPSORDERUPDATE
	 * 		PROCESSIPSRETURNEXCEPTION
	 */
	public int processIPSOrder(String orderId, String ipsBillNo, char succFlag, String ipsMsg, String ipsBankTime) throws LotteryException;
	
	
	/**
	 * �Ի�Ѹ֧�����صĽ����Ϣ���д������ݶ�����Ų�����Ӧ��¼�������ݽ����״̬��������и��£�ͬʱ��¼��־��<br>
	 * �˲�����Ҫ�Լ�¼���������������ظ�����<br>
	 * �����������£�<br>
	 * 1��IPSOrderDAO.getIPSOrderForUpdate(orderId)����ѯ������¼���ڸ���<br>
	 * 2�����Ϊnull���¼��־�����׳��쳣<br>
	 * 3������У�飬У�鲻ͨ���׳��쳣
	 * 4������succFlag�Լ���ǰ����״̬���д���<br>
	 * 5�����Ϊ�ɹ�����orderStatus = 0������б����ֵ<br>
	 * 6������orderStatus, checkedStatus�Լ�����״̬��Ϣ
	 * @param orderId
	 * @param ipsBillNo
	 * @param succFlag
	 * @param ipsMsg
	 * @param ipsBankTime
	 * @param retEncodeType
	 * @param signature
	 * @return
	 * @throws LotteryException
	 * 		IPSRETURNERROR
	 * 		ORDERNOTFOUND
	 * 		DUPLICATIONIPSRETURN
	 * 		AMOUNTERRORIPSRETURN
	 * 		NOIPSORDERUPDATE
	 * 		PROCESSIPSRETURNEXCEPTION
	 * 		VERIFYIPSRETURNERROR
	 */
	public int processIPSOrder(String orderId, String ipsBillNo, char succFlag, String ipsMsg, String ipsBankTime, String retEncodeType, String signature) throws LotteryException;
	public int processIPSOrder(IPSReturnInfo info) throws LotteryException;

	/**
	 * ��û�Ѹ������Ϣ�����ڿ�ʼʱ����ȡ���ڵ��ڵ���0ʱ0��0�룬���ڽ���ʱ����ȡС�ڵ��ڵ��� 23:59:59
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
	public List<IPSOrderModel> getIPSOrderes(String userIdentify, Date startDate, Date endDate, int orderStatus, int start, int count) throws LotteryException;
}
