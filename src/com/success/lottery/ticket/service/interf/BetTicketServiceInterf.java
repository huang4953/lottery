package com.success.lottery.ticket.service.interf;

import java.sql.Date;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.ticket.domain.BetTicketAcountDomain;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.domain.WinOrderTicketDomain;

/**
 * 
 * com.success.lottery.ticket.service.interf
 * BetTicketServiceInterf.java
 * BetTicketServiceInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-14 ����09:42:51
 *
 */

public interface BetTicketServiceInterf {
	
	public static final int E_411003_CODE = 411003;
	public static final String E_411003_DESC = "��ȡ����ʱ��������";
	public static final int E_411004_CODE = 411004;
	public static final String E_411004_DESC = "��������ʱ��������";
	public static final int E_411005_CODE = 411005;
	public static final String E_411005_DESC = "���ɲ�Ʊ������ʱ����";
	public static final int E_411007_CODE = 411007;
	public static final String E_411007_DESC = "��Ʊ�����ѯ����";
	public static final int E_411017_CODE = 411017;
	public static final String E_411017_DESC = "��Ʊ��������ѯ����";
	public static final int E_411009_CODE = 411009;
	public static final String E_411009_DESC = "�н���Ʊ��ѯ����";
	public static final int E_411019_CODE = 411019;
	public static final String E_411019_DESC = "�н���Ʊ��������ѯ����";
	public static final int E_4110021_CODE = 411021;
	public static final String E_411021_DESC = "��Ʊͳ�Ƴ���";
	public static final int E_4110023_CODE = 411023;
	public static final String E_411023_DESC = "��ƱͶע���ͳ�Ƴ���";
	public static final int E_4110031_CODE = 411031;
	public static final String E_411031_DESC = "�н���Ʊͳ�Ƴ���";
	public static final int E_4110033_CODE = 411033;
	public static final String E_411033_DESC = "�н���Ʊ����ͳ�Ƴ���";
	public static final int E_4110011_CODE = 411011;
	public static final String E_411011_DESC = "������ϸ��ѯ����";
	/**
	 * 
	 * Title: insertBetTicket<br>
	 * Description: <br>
	 *            ���ɲ�Ʊ��Ϣ<br>
	 *            �÷���ֻ�ǲ����Ʊ�������κε��߼�У��<br>
	 * @param betTicket ��Ʊ��Ϣʵ�������������ticketSequence����Ҫָ��
	 * @return ���ɱ����ݵ�����
	 * @throws LotteryException<br>
	 *                           411005:���ɲ�Ʊ������ʱ����
	 */
	public String insertBetTicket(BetTicketDomain betTicket) throws LotteryException;
	
	/**
	 * 
	 * Title: insertBetTicketBatch<br>
	 * Description: <br>
	 *            ���������Ʊ��<br>
	 * @param betTicketList ������Ʊ��Ϣ�����Լ���
	 * @return ���ɵĲ�Ʊ��ˮ�ż���
	 * @throws LotteryException<br>
	 *                          411005:���ɲ�Ʊ������ʱ����
	 */
	public List<String> insertBetTicketBatch(List<BetTicketDomain> betTicketList) throws LotteryException;
	/**
	 * 
	 * Title: updateBetTicketStatus<br>
	 * Description: <br>
	 *            ���²�Ʊ״̬<br>
	 * @param ticketSequence ��Ʊ��ˮ��
	 * @param ticketStatus ��Ʊ״̬
	 * @return �ɹ����µļ�¼��
	 * @throws LotteryException<br>
	 *                          411004:��������ʱ��������
	 */
	public int updateBetTicketStatus(String ticketSequence,int ticketStatus) throws LotteryException;

	/**
	 * ����ָ����Ʊ��ˮ�ŵĲ�Ʊ״̬��ֻ�е��ò�Ʊ״̬Ϊwhoes��ָ���Ĳ�Ʊ״̬ʱ�Ÿ���
	 * @param ticketSequences: ��Ʊ��ˮ���б�
	 * @param ticketStatus ��Ʊ״̬
	 * @param whoes ��ǰҪ���µĲ�Ʊ��״̬����Ϊwhoes�е�״̬����whoesΪnull����������
	 * @return �ɹ����µļ�¼���������Ʊ��ˮ���б�Ϊnull��գ������ֱ�ӷ���0
	 * @throws LotteryException<br>
	 *                          <br>BetTicketServiceInterf.E_411004_CODE:��������ʱ��������
	 */
	public int updateBetTicketesStatus(List<String> ticketSequences, int ticketStatus, List<Integer> whoes) throws LotteryException;

	/**
	 * 
	 * Title: updateBetTicketPrintInfo<br>
	 * Description: <br>
	 *            ���²�Ʊ��Ʊϵͳ���ؽ��<br>
	 * @param ticketSequence ��Ʊ��ˮ��
	 * @param ticketStatus ��Ʊ״̬
	 * @param ticketId Ʊ��
	 * @param printerId ��Ʊ�����
	 * @param printResult ��Ʊ���
	 * @param ticketData ��Ʊ������
	 * @param ticketDataMD ��ƱժҪ
	 * @param ticketPassword Ʊ����
	 * @return �ɹ����µļ�¼��
	 * @throws LotteryException<br>
	 *                           411004:��������ʱ��������
	 */
	public int updateBetTicketPrintInfo(String ticketSequence,int ticketStatus, String ticketId,
			String printerId, String printResult, String ticketData,
			String ticketDataMD, String ticketPassword) throws LotteryException;
	/**
	 * 
	 * Title: updateBetTicketPrizeResult<br>
	 * Description: <br>
	 *            ���²�Ʊ���н����<br>
	 * @param ticketSequence ��Ʊ��ˮ��
	 * @param preTaxPrize �н����
	 * @param prizeResult �ҽ����
	 * @return �ɹ����µļ�¼��
	 * @throws LotteryException<br>
	 *                           411004:��������ʱ��������
	 */
	public int updateBetTicketPrizeResult(String ticketSequence,long preTaxPrize,int prizeResult) throws LotteryException;
	
	/**
	 * 
	 * Title: queryBetOrderIdTicket<br>
	 * Description: <br>
	 *            ���ݶ�����Ų�ѯ������Ͷע��Ʊ��Ϣ���еļ�¼��<br>
	 *            �������жϸö����Ƿ��Ʊ<br>
	 * @param orderId �������
	 * @return ����count�ļ�¼��
	 * @throws LotteryException <br>
	 *                            411003:��ȡ����ʱ��������
	 */
	public int queryBetOrderIdTicket(String orderId) throws LotteryException;
	
	/**
	 * ���ݶ�����Ų�ѯ���иö����ĳ�Ʊ��Ϣ
	 * @param orderId
	 * 		ָ���Ķ������
	 * @return
	 * 		��ѯ��������null
	 * @throws LotteryException
	 */
	public List<BetTicketDomain> getTickets(String orderId) throws LotteryException;
	
	/**
	 * ��ÿ������ɳ�Ʊ�ļ��ĳ�Ʊ��¼����
	 * @param lotteryId
	 * 		����ѡ��lotteryId
	 * @param term
	 * 		����ѡ��һ������
	 * @return
	 * 		��ѯ���ļ�¼���������lotteryIdС�ڵ���0����termΪnull���򷵻�0
	 * @throws LotteryException
	 */
	public int getCreateFileTicketesCount(int lotteryId, String term) throws LotteryException;
	
	/**
	 * ��ÿ������ɳ�Ʊ�ļ��ĳ�Ʊ��¼ 
	 * @param lotteryId
	 * 		����ѡ��lotteryId 
	 * @param term
	 * 		����ѡ��һ������ 
	 * @param pageNumber
	 * 		��ѯ�ڼ�ҳ
	 * @param perPageNumber
	 * 		ÿҳ������
	 * @return
	 * 		��ѯ���ļ�¼�����û�в�ѯ����¼����lotteryIdС�ڵ���0����termΪnull���򷵻�null 
	 * @throws LotteryException
	 */
	public List<BetTicketDomain> getCreateFileTicketes(int lotteryId, String term, int pageNumber, int perPageNumber) throws LotteryException;
	
	/**
	 * ��ÿ������صĳ�Ʊ�ļ�����
	 * @param lotteryId
	 * 		����id
	 * @param term
	 * 		����
	 * @return
	 * 		��ѯ���ļ�¼����
	 * @throws LotteryException
	 */
	public int getTicketFilesCount(int lotteryId, String term) throws LotteryException;

	/**
	 * ��ÿ������صĳ�Ʊ�ļ���¼ 
	 * @param lotteryId
	 * 		����id
	 * @param term
	 * 		����
	 * @return
	 * 	��ѯ���ļ�¼��û�з���null
	 * @throws LotteryException
	 */
	public List<BetTicketDomain> getTicketFiles(int lotteryId, String term, int pageNumber, int perPageNumber) throws LotteryException;
	
	/**
	 * �����Ҫȷ�ϳ�Ʊ����Ķ�����¼������
	 * @param lotteryId
	 * 		����Id�������д-1�����ѯ����
	 * @param term
	 * 		���ڣ������дnull�����ѯ����
	 * @param ticketStatus
	 * 		��Ʊ״̬��1-�ѳ�Ʊ 6-��Ʊ�ɹ� 7-��Ʊʧ�ܣ������д-1�����ѯ����
	 * @return
	 * 		��������
	 * @throws LotteryException
	 */
	public int getConfirmTicketesCount(int lotteryId, String term, int ticketStatus) throws LotteryException;

	/**
	 * �����Ҫȷ�ϳ�Ʊ����Ķ�����¼
	 * @param lotteryId
	 * 		����Id�������д-1�����ѯ����
	 * @param term
	 * 		���ڣ������дnull�����ѯ����
	 * @param ticketStatus
	 * 		��Ʊ״̬��1-�ѳ�Ʊ 6-��Ʊ�ɹ� 7-��Ʊʧ�ܣ������д-1�����ѯ����
	 * @return
	 * 		���س�Ʊ���List��û��Ϊnull
	 * @throws LotteryException
	 */
	public List<BetTicketDomain> getConfirmTicketes(int lotteryId, String term, int ticketStatus, int pageNumber, int perPageNumber) throws LotteryException;

	/**
	 * ����н��Ĳ�Ʊ������Ϣ������
	 * @param lotteryId
	 * 		����Id����������д-1
	 * @param term
	 * 		���ڣ���������дnull
	 * @param orderId
	 * 		������ţ���������дnull
	 * @return
	 * 		���ط����������н���Ʊ��������
	 * @throws LotteryException
	 */
	public int getWinTicketesCount(int lotteryId, String term, String orderId) throws LotteryException;
	
	/**
	 * ����н��Ĳ�Ʊ������Ϣ������ 
	 * @param lotteryId
	 * 		����Id����������д-1
	 * @param term
	 * 		���ڣ���������дnull
	 * @param orderId
	 * 		������ţ���������дnull
	 * @return
	 * 		���ط����������н���Ʊ����
	 * @throws LotteryException
	 */
	public List<WinOrderTicketDomain> getWinTicketes(int lotteryId, String term, String orderId, int pageNumber, int perPageNumber) throws LotteryException;
	
	/**
	 * ���ݲ�Ʊ��ˮ�Ż�ò�Ʊ��Ϣ
	 * @param ticketSequence
	 * @return
	 * @throws LotteryException
	 */
	public BetTicketDomain getTicket(String ticketSequence) throws LotteryException;
	
	/**
	 * ���ݲ�Ʊ��ˮ�Ż�ö�Ӧ�Ķ�����Ϣ
	 * @param ticketSequence
	 * @return
	 * @throws LotteryException
	 */
	public BetOrderDomain getOrderByTicketSequence(String ticketSequence) throws LotteryException;
	/**
	 * ��Ʊ�����ѯ���ܼ�¼��
	 * @param orderId�������
	 * @param ticketSequence��Ʊ���
	 * @param lotteryId����id
	 * @param betTerm_begin������С��Χ
	 * @param betTerm_end�������Χ
	 * @param ticketStatus��Ʊ״̬
	 * @param prizeResult�н�״̬  
	 * @param beginTime��Ʊ����Сʱ�� 
	 * @param endTime��Ʊ�����ʱ�� 
	 * @return int�����б���ܼ�¼��
	 * @throws LotteryException
	 */
	public int getBetTicketesCount(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * ��Ʊ�����ѯ����Ŀ
	 * @param orderId�������
	 * @param ticketSequence��Ʊ���
	 * @param lotteryId����id
	 * @param betTerm_begin������С��Χ
	 * @param betTerm_end�������Χ
	 * @param ticketStatus��Ʊ״̬
	 * @param prizeResult�н�״̬  
	 * @param beginTime��Ʊ����Сʱ�� 
	 * @param endTime��Ʊ�����ʱ�� 
	 * @return List<BetTicketDomain>���ز�Ʊ���б�����
	 * @throws LotteryException
	 */
	public List<BetTicketAcountDomain> getBetTicketes(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime,
			int page, int perPageNumber)throws LotteryException;
	/**
	 * ��ѯ��Ʊ����Ͷע��
	 * @param orderId�������
	 * @param ticketSequence��Ʊ���
	 * @param lotteryId����id
	 * @param betTerm_begin������С��Χ
	 * @param betTerm_end�������Χ
	 * @param ticketStatus��Ʊ״̬
	 * @param prizeResult�н�״̬  
	 * @param beginTime��Ʊ����Сʱ�� 
	 * @param endTime��Ʊ�����ʱ�� 
	 * @return long���ز�Ʊ����Ŀͳ��
	 * @throws LotteryException
	 */
	public long getBetTicketesNumber(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult, String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * ��ѯ��Ʊ����Ͷע���
	 * @param orderId�������
	 * @param ticketSequence��Ʊ���
	 * @param lotteryId����id
	 * @param betTerm_begin������С��Χ
	 * @param betTerm_end�������Χ
	 * @param ticketStatus��Ʊ״̬
	 * @param prizeResult�н�״̬  
	 * @param beginTime��Ʊ����Сʱ�� 
	 * @param endTime��Ʊ�����ʱ�� 
	 * @return long���ز�Ʊ����Ͷע�����
	 * @throws LotteryException
	 */
	public long getBetTicketesMoney(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * �н���Ʊ��������
	* @param orderId�������
	 * @param ticketSequence��Ʊ���
	 * @param lotteryId����id
	 * @param betTerm_begin������С��Χ
	 * @param betTerm_end�������Χ
	 * @param ticketStatus��Ʊ״̬
	 * @param prizeResult�н�״̬  
	 * @param beginTime��Ʊ����Сʱ�� 
	 * @param endTime��Ʊ�����ʱ�� 
	 * @return int �����н���Ʊ���м�¼��
	 * @throws LotteryException
	 */
	public int getWinningBetTicketesCount(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * �н���Ʊ����Ŀ
	* @param orderId�������
	 * @param ticketSequence��Ʊ���
	 * @param lotteryId����id
	 * @param betTerm_begin������С��Χ
	 * @param betTerm_end�������Χ
	 * @param ticketStatus��Ʊ״̬
	 * @param prizeResult�н�״̬  
	 * @param beginTime��Ʊ����Сʱ�� 
	 * @param endTime��Ʊ�����ʱ�� 
	 * @return List<BetTicketDomain>�����н��Ĳ�Ʊ�б�
	 * @throws LotteryException
	 */
	public List<BetTicketAcountDomain> getWinningBetTicketes(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Date beginTime,
			Date endTime, int page, int perPageNumber)throws LotteryException;
	/**
	 * ��Ʊ��orderIdȥ��ѯ
	 * @param orderId
	 * @return List<BetTicketDomain>��orderIdȥ��ѯ��������orderId�����в�Ʊ�б�
	 * @throws LotteryException
	 */
	public List<BetTicketAcountDomain> getBetTicketes4OrderId(String orderId)throws LotteryException;
	/**
	 * �н���Ŀͳ��
	* @param orderId�������
	 * @param ticketSequence��Ʊ���
	 * @param lotteryId����id
	 * @param betTerm_begin������С��Χ
	 * @param betTerm_end�������Χ
	 * @param ticketStatus��Ʊ״̬
	 * @param prizeResult�н�״̬  
	 * @param beginTime��Ʊ����Сʱ�� 
	 * @param endTime��Ʊ�����ʱ�� 
	 * @return long�����н�����ͳ����
	 * @throws LotteryException
	 */
	public long getWinningBetTicketesNumber(String orderId,String ticketSequence, Integer lotteryId, String betTerm_begin,
			String betTerm_end, Integer ticketStatus, Integer prizeResult,
			String accountId,String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;
	/**
	 * �н���Ʊ���ͳ��
	* @param orderId�������
	 * @param ticketSequence��Ʊ���
	 * @param lotteryId����id
	 * @param betTerm_begin������С��Χ
	 * @param betTerm_end�������Χ
	 * @param ticketStatus��Ʊ״̬
	 * @param prizeResult�н�״̬  
	 * @param beginTime��Ʊ����Сʱ�� 
	 * @param endTime��Ʊ�����ʱ�� 
	 * @return long�����н��Ľ�����ͳ��
	 * @throws LotteryException
	 */
	public long getWinningBetTicketesMoney(String orderId,String ticketSequence, Integer lotteryId, String betTerm_begin,
			String betTerm_end, Integer ticketStatus, Integer prizeResult,
			String accountId,String userName, String planSourceId,Date beginTime, Date endTime)throws LotteryException;

	public BetTicketAcountDomain getBetTicketAcount(String ticketSequence)throws LotteryException;

	/**
	 * Title: getTicketes<br>
	 * Description: <br>��ѯָ��״̬�Ĳ�Ʊ
	 *              <br>�÷�����������Ʊ��Ʊ���У�������ticketStatusΪnull�����ֱ�ӷ���null
	 *              <br>
	 * @param ticketStatus: ��Ʊ״̬�б�
	 * @param condition: ��ȡ��Ʊ�ĸ�����������Ϊnullʱ����ֻʹ��ticketStatusΪ����
	 * @param limitNumber: ָ��ÿ�λ�ȡ�������������Ϊ0ʱ������������
	 * @return: ���ط��������Ĳ�Ʊ�б�
	 * @throws LotteryException<br>
	 *                          <br>BetTicketServiceInterf.E_411003_CODE:��ȡ����ʱ��������
	 */
	public List<BetTicketDomain> getTicketes(List<Integer> ticketStatus, String condition, int limitNumber) throws LotteryException;

	

}
