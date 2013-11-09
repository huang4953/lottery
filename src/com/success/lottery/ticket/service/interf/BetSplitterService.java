package com.success.lottery.ticket.service.interf;

import java.io.FileInputStream;
import java.util.List;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;


public interface BetSplitterService{
	//��Ʊʱ�����쳣
	public static final int BETSPLITEXCEPTION = 411501;
	public static final String BETSPLITEXCEPTION_STR = "��Ʊʱ���ֳ����쳣��";

	public static final int BETSPLITENQUEEXCEPTION = 411502;
	public static final String BETSPLITENQUEEXCEPTION_STR = "��Ʊ������д���";

	//
	public static final int CREATEFILETOPRINTEXCEPTION = 411550;
	public static final String CREATEFILETOPRINTEXCEPTION_STR = "���ɴ�Ӯ�Ҵ�ӡ�ļ�ʱ����ʱ�����쳣��";

	//���ɳ�Ʊ�ļ�ʱδ�ҵ����Գ�Ʊ���ļ�
	public static final int NOTFOUNDPRINTTICKET = 410501;
	public static final String NOTFOUNDPRINTTICKET_STR = "δ�ҵ���Ҫ�����ļ��Ķ���";
	
	//���س�Ʊ�ļ�ʱ��������ļ�inputstream����
	public static final int GETDOWNLOADFILEEXCEPTION = 411502;
	public static final String GETDOWNLOADFILEEXCEPTION_STR = "��������ļ�����쳣��";
	
	
	//���س�Ʊ�ļ�ʱ��������ļ�inputstream����
	public static final int NOTFOUNDCONFIRMTICKET = 410502;
	public static final String NOTFOUNDCONFIRMTICKET_STR = "δ�ҵ���Ҫȷ�ϴ�ӡ����Ĳ�Ʊ��Ϣ";

	public static final int CONFIRMTICKETEXCEPTION = 411503;
	public static final String CONFIRMTICKETEXCEPTION_STR = "ȷ�ϴ�ӡ��������쳣��";
	
	/**
	 * �Զ������в�Ʊ�����������ļ���ȡ��ͬ��Splitter�����ຣ��ʻ�õ��� QHTCOrderSplitter
	 * ��Ʊֻ�Ǹ��ݲ�ͬ��Ʊ��Ĺ�������BetTicket�����Ӧ��¼��
	 * @param order
	 * @return
	 * @throws LotteryException
	 */
	public String orderSplit(BetOrderDomain order) throws LotteryException;
	
	/**
	 * ���ɳ�Ʊ�ļ�����TicketProcessor��һ�֣�����͵��ֱ�Ӷ����˸ýӿڣ��ýӿ����Ƹ����ʵ�Ӧ����ticketProcess
	 * ���ﶨ��Ľӿ�ר���ڴ�Ӯ�ҳ�Ʊ�ļ����ɣ�����ǰ̨ҳ����÷����
	 * @param lotteryId
	 * @param term
	 * @param username
	 * @return
	 * @throws LotteryException
	 */
	public String createPrintFile(int lotteryId, String term, String username) throws LotteryException;
	
	/**
	 * ��������ļ����ļ�������
	 * @param fileName
	 * @return
	 * @throws LotteryException
	 */
	public FileInputStream getPrintFileInputStream(String fileName) throws LotteryException;
	
	public int confirmTicket(List<String> ticketSequences, int confirmFlag, String confirmMsg, String username) throws LotteryException;
}
