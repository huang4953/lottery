package com.success.lottery.util.core;

import java.util.List;

import com.success.lottery.util.LotteryInfo;

public interface LotteryInterf {
	
	public static final long SINGLEPRIZE = 200;//ÿһע�Ľ��Ϊ2Ԫ
	public static final long ADDPRIZE = 300;//׷��ÿһע�Ľ��Ϊ3Ԫ
	public static final String ZHUSIGN = "#";
	
	/**
	 * ���ݴ���Ĳ����淨id��Ͷע��ʽid��Ͷע�ַ�������У�����У��Ͷע�ַ����Ƿ���ȷ<br>
	 *                      
	 * @param playType �淨id
	 * @param betType  Ͷע��ʽid
	 * @param betString Ͷע�ַ���
	 * @return boolean   
	 *                    true Ͷע�ַ�������Ͷע��ʽ��false Ͷע�ַ���������Ͷע��ʽ <br>
	 *                    ���û�ж�Ӧ���淨�Ͷ�Ӧ��Ͷע��ʽ���׳�LotteryUnDefineException<br>
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	public boolean checkBetType(String playType,String betType,String betString) throws LotteryUnDefineException,Exception;
	
	/**
	 * 
	 * ���������ַ������Ϊ���ϣ����ϵĽṹ��LotteryInfo�ඨ��<br>
	 * @param winResult
	 * @param info ���������Ϣ������
	 * @return LotteryInfo ���������Ϣ������
	 */
	public LotteryInfo splitWinResult(String winResult,LotteryInfo info);
	
	/**
	 * 
	 * �ϲ�������Ϊ�ַ��������ϵĽṹ��LotteryInfo�ඨ��<br>
	 * @param info
	 * @return String �����ַ�����ʽ������򷵻غϲ�����ַ���
	 */
	public String mergeWinResult(LotteryInfo info);
	
	/**
	 * 
	 * ����������ַ������Ϊ������ʽ�����ϵĽṹ��LotteryInfo�ඨ��<br>
	 * @param lotteryResult �������ַ���
	 * @param info ������������Ϣ��ʵ������
	 * @return LotteryInfo  ������������Ϣ��ʵ������
	 */
	public LotteryInfo splitLotteryResult(String lotteryResult,LotteryInfo info);
	/**
	 * 
	 * �ϲ��������Ϊ�ַ��������ϵĽṹ��LotteryInfo�ඨ��<br>
	 * @param info ������������Ϣ��ʵ������
	 * @return String  ���ַ�����ʽ������򷵻غϲ�����ַ���
	 */
	public String mergeLotteryResult(LotteryInfo info);
	/**
	 * 
	 * Title: splitSalesInfoResult<br>
	 * Description: <br>
	 *            ���������Ϣ<br>
	 * @param salesInfo ������Ϣ�ַ���
	 * @param info ������������Ϣ��ʵ������
	 * @return ������������Ϣ��ʵ������
	 */
	public LotteryInfo splitSalesInfoResult(String salesInfo,LotteryInfo info);
	/**
	 * 
	 * Title: mergeSalesInfoResult<br>
	 * Description: <br>
	 *            �ϲ�������ϢΪ�ַ���<br>
	 * @param info ������������Ϣ��ʵ������
	 * @return �ϲ�����ַ���
	 */
	public String mergeSalesInfoResult(LotteryInfo info);
	/**
	 * 
	 * �����淨id��Ͷע��ʽid�������������������Ͷע�ַ������в�Ʊ�ҽ�<br>
	 * @param playType �淨id
	 * @param betType Ͷע��ʽid
	 * @param lotteryResult �������
	 * @param winResult ������
	 * @param betCode Ͷע�����ַ���
	 * @return List&lt;List&lt;String&gt;&gt; ���ضҽ�����ļ��ϣ�����ֻ�����н���Ͷע����   ������&lt;&lt;��עע��, ���𼶱�,������,׷�ӽ��&gt;&gt;
	 * @throws LotteryUnDefineException, Exception
	 */
	public List<List<String>> lotteryPrize(String playType,String betType,String lotteryResult,String winResult,String betCode) throws LotteryUnDefineException, Exception;
	
	/**
	 * 
	 * Title: lotterySplit<br>
	 * Description: <br>
	 *            ����Ͷע�ַ����ĵ�ʽע����Ͷע��Ͷע����Ѿ�ת��Ϊ��<br>
	 * @param playType �淨id
	 * @param betType  Ͷע��ʽid
	 * @param betCode  Ͷע�ַ���
	 * @return         ����Ͷעע���Ľ���'#'���ŷָ��ʽΪ��Ͷעע��#Ͷע���
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	public String lotterySplit(String playType,String betType,String betCode) throws LotteryUnDefineException, Exception;
	
	/**
	 * 
	 * Title: lotteryToSingle<br>
	 * Description: <br>
	 *            ��Ͷע�ַ�����Ϊ��ʽ�ļ���<br>
	 * @param playType �淨id
	 * @param betType Ͷע��ʽid
	 * @param betCode Ͷע�ַ���
	 * @return        ��Ӧ���ֵĵ�ʽͶע����
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 */
	public List<String> lotteryToSingle(String playType,String betType,String betCode) throws LotteryUnDefineException, Exception;
	
	/**
	 * Title: lotteryRandomBetCode<br>
	 * Description: <br>
	 *            �������ĳһ���ֵĵ�ʽ����һע<br>
	 * @return ���ز��������Ͷעע�롣����null����ַ���Ϊ�������ע��ʧ��
	 */
	public String lotteryRandomBetCode();
	
	/**
	 * Title: getMissCount<br>
	 * Description
	 * 		����µ���©�ַ������ַ����洢����ο�����״̬��ʽ���Ķ����ĵ���<br>
	 * @param lotteryResult
	 * 		��������ַ���
	 * @param lastMissCount
	 * 		���һ����©����ַ���
	 * @return
	 * 		���µ���©�ַ������������null�����ʾ�ò��ֲ���Ҫ��©�������©ʱ�����˴���
	 */
	public String getMissCount(String lotteryResult, String lastMissCount);
	
	/**
	 * 
	 * Title: splitMissCount<br>
	 * Description: <br>
	 *            �����©��Ϣ<br>
	 * @param missCount ��©��Ϣ�ַ���
	 * @param info ������������Ϣ��ʵ������
	 * @return ������������Ϣ��ʵ������
	 */
	public LotteryInfo splitMissCount(String missCount,LotteryInfo info);
	/**
	 * 
	 * Title: isLimitBet<br>
	 * Description: <br>
	 *            �Ƿ��޺��ж�<br>
	 * @param betType Ͷע��ʽ
	 * @param limitNumberArrary �޺Ŵ������ܺ�����޺ţ�������޺ŵ���","������
	 * @param betCode Ͷע�������ܺ���עͶע�ţ��������עͶע����"^"������
	 * @return true����false
	 */
	public boolean isLimitBet(String playType,String betType, String limitNumberArrary,
			String betCode) throws LotteryUnDefineException;
	
}
