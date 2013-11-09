/**
 * Title: LotteryStaticDefine.java
 * @Package com.success.lottery.business.service
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-21 ����06:37:47
 * @version V1.0
 */
package com.success.lottery.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.business.service
 * LotteryStaticDefine.java
 * LotteryStaticDefine
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-21 ����06:37:47
 * 
 */

public class LotteryStaticDefine {
	
	/*
	 * ����͸
	 */
	public static final int LOTTERY_1000001 = 1000001;//����͸
	public static final int LOTTERY_1000002 = 1000002;//���ǲ�
	public static final int LOTTERY_1000003 = 1000003;//������
	public static final int LOTTERY_1000004 = 1000004;//������
	public static final int LOTTERY_1000005 = 1000005;//��Ф��
	public static final int LOTTERY_1300001 = 1300001;//ʤ����
	public static final int LOTTERY_1300002 = 1300002;//��ѡ�ų�
	public static final int LOTTERY_1300003 = 1300003;//6����ȫ��
	public static final int LOTTERY_1300004 = 1300004;//4�������
	public static final int LOTTERY_1200001 = 1200001;//ʮһ�˶��
	public static final int LOTTERY_1200002 = 1200002;//�����˿�
	
	public static Map<String,String> planSource;
	
	public static Map<String,String> orderStatus;
	
	public static Map<String,String> coopOrderStatus;
	public static Map<Integer,String> coopWinStatus;
	
	public static Map<String,String> cpOrderType;
	
	public static Map<String,String> orderStatusForWeb;
	
	public static Map<String,String> termWinStatus;
	
	public static Map<String,String> orderWinStatus;
	public static Map<String,String> orderWinStatusForWeb;
	
	public static Map<String,String> saleStatus;
	
	public static Map<String,String> termStatus;
	
	public static Map<String,String> ticketStatus;
	
	public static Map<String,String> userStatus;
	
	public static Map<Integer,String> drawStatus;
	
	public static Map<String,String> userSex;
	
	public static Map<String,String> adminStatus;
	
	public static Map<Boolean,String> mobileStatus;
	
	public static Map<Boolean,String> emailStatus;
	
	public static Map<String,String> ticketStatusForQuery;
	
	//��Ʊ��Ʊ״̬
	public static Map<String,String> ticketStatusForWeb;
	//��Ʊ�н�״̬
	public static Map<String,String> ticketWinStatus;
	//����Ⱥ������״̬
	public static Map<Long,String> taskStautsForSms;
	
	//����Ⱥ������״̬
	public static Map<Long,String> sendStatusForSms;
	
	//��־�ļ���״̬
	public static Map<Integer,String> operatorlogLevelStatus;
	public static Map<Integer,String> operatorlogTypeStatus;
	public static Map<Integer,String> operatorlogRankStatus;
	/*
	 * ��������ת�˻���������
	 * A:���룬B:֧����C:���ᣬD:�ⶳ
	 * 
	 */

	public static Map<Integer,String> transactionToOpType;
	
	public static Map<Integer,String> transactionType;
	
	public static Map<Integer,String> ipsOrderStatus;
	
	public static Map<Integer,String> billOrderStatus;
	
	public static Map<Integer,String> ipsCheckStatus;
	
	public static Map<Integer,String> billCheckStatus;
	
	public static Map<Integer,String> planStatus;
	
	public static Map<Integer,String> planStatusForQuery;//����״̬��Ϊ��ѯ����
	
	public static List<Integer> needLimitLottery;//��Ҫ�����޺���Ϣ�Ĳ���
	
	public static Map<Integer,String> planType;
	public static Map<Integer,String> planStop;
	public static Map<Integer,String> planOpenType;
	public static Map<Integer,String> coopOrderType;
	
	public static Map<Integer,String> planStatusForWebH;
	
	public static final String open_format = "height=500, width=900, " +
			"alwaysRaised=yes,dependent=yes,location=no, menubar=no, " +
			"toolbar =no, titlebar=no,scrollbars=yes, resizable=yes,status=no";
	
	static {
		planSource = new TreeMap<String,String>();
		planSource.put("0", "WEB");
		planSource.put("1", "SMS");
		planSource.put("2", "WAP");
		planSource.put("3", "KJAVA");
		//orderStatus = new TreeMap<String,String>();
		orderStatus = new LinkedHashMap<String,String>();
		orderStatus.put("0", "δ��ʼ");
		orderStatus.put("1", "������");
		orderStatus.put("2", "��Ʊ��");
		orderStatus.put("3", "��������");
		orderStatus.put("4", "��Ʊ����");
		orderStatus.put("5", "��Ʊ�ɹ�");
		orderStatus.put("6", "��Ʊʧ��");
		orderStatus.put("8", "�ҽ����");
		orderStatus.put("10", "�ɽ����");
		orderStatus.put("11", "�����Զ�����");
		orderStatus.put("12", "�����ֶ�����");
		orderStatus.put("13", "׷����");
		orderStatus.put("14", "�����޺ų���");
		coopOrderStatus = new LinkedHashMap<String,String>();
		//���򶩵�״̬,0-�����У�1-ϵͳ������2-�����˳�����3-�����˳�����4-������Ա����Ͷע��5-���׶��ᣬ6-���׳���,7-�Ѿ��ҽ�,8-���ɽ�
		coopOrderStatus.put("0", "������");
		coopOrderStatus.put("1", "ϵͳ����");
		coopOrderStatus.put("2", "�����˳���");
		coopOrderStatus.put("3", "�����˳���");
		coopOrderStatus.put("4", "������Ա����Ͷע");
		coopOrderStatus.put("5", "���׶���");
		coopOrderStatus.put("6", "���׳���");
		coopOrderStatus.put("7", "�Ѿ��ҽ�");
		coopOrderStatus.put("8", "���ɽ�");
		//���򶩵���ʽ,0-�����Ϲ���1-�����Ϲ�,2-���𱣵�,3-���𱣵�תͶע
		cpOrderType = new LinkedHashMap<String,String>();
		cpOrderType.put("0", "�����Ϲ�");
		cpOrderType.put("1", "�����Ϲ�");
		cpOrderType.put("2", "���𱣵�");
		cpOrderType.put("3", "���𱣵�תͶע");
		termWinStatus = new TreeMap<String,String>();
		termWinStatus.put("1", "δ����");
		termWinStatus.put("2", "�ѿ���");
		termWinStatus.put("3", "���ڶҽ�");
		termWinStatus.put("4", "�ҽ��ɹ�");
		termWinStatus.put("7", "�����ɽ�");
		termWinStatus.put("8", "�ɽ��ɹ�");
		orderWinStatus = new LinkedHashMap<String,String>();
		orderWinStatus.put("0", "δ�ҽ�");
		orderWinStatus.put("1", "δ�н�");
		orderWinStatus.put("2", "��С��");
		orderWinStatus.put("3", "�д�");
		orderWinStatus.put("99", "ʧ������");
		orderWinStatus.put("199", "δ������");
		orderWinStatus.put("299", "С������");
		orderWinStatus.put("399", "������");
		
		orderWinStatusForWeb = new TreeMap<String,String>();
		orderWinStatusForWeb.put("0", "----");
		orderWinStatusForWeb.put("1", "δ�н�");
		orderWinStatusForWeb.put("2", "��С��");
		orderWinStatusForWeb.put("3", "�д�");
		orderWinStatusForWeb.put("99", "----");
		orderWinStatusForWeb.put("199", "δ�н�");
		orderWinStatusForWeb.put("299", "��С��");
		orderWinStatusForWeb.put("399", "�д�");
		
		saleStatus = new TreeMap<String,String>();
		saleStatus.put("1", "��������");
		saleStatus.put("2", "ֹͣ����");
		saleStatus.put("3", "��ͣ����");
		saleStatus.put("4", "δ������ʱ��");
		saleStatus.put("5", "�Ѿ�����");
		termStatus = new TreeMap<String,String>();
		termStatus.put("0", "δ����");
		termStatus.put("1", "������");
		termStatus.put("2", "������");
		ticketStatus = new TreeMap<String,String>();
		ticketStatus.put("0", "δ��Ʊ");
		ticketStatus.put("1", "��Ʊ��");
		ticketStatus.put("2", "�����Ʊ");
		ticketStatus.put("3", "�����Ʊʧ��");
		ticketStatus.put("4", "�ȴ���Ʊ���");
		ticketStatus.put("5", "��Ʊ���δ֪");
		ticketStatus.put("6", "��Ʊ�ɹ�");
		ticketStatus.put("7", "��Ʊʧ��");
		ticketStatus.put("8", "��Ʊ��ʱ");
		ticketStatus.put("9", "���ܳ�Ʊ");
		
		
		userStatus =  new TreeMap<String, String>();
		userStatus.put("0", "ע��");
		userStatus.put("1", "����");
		userStatus.put("2", "����");
		
		drawStatus = new LinkedHashMap<Integer,String>();
		drawStatus.put(0, "������");
		drawStatus.put(1, "��ͨ��");
		drawStatus.put(2, "�Ѿܾ�");
		drawStatus.put(3, "��������");
		drawStatus.put(4, "�������");
		
		transactionType = new LinkedHashMap<Integer, String>();
		transactionType.put(11005, "�˻����ӵ���");
		transactionType.put(10001, "�˻����ٵ���");
		transactionType.put(11001, "��ֵ");
		transactionType.put(11002, "�ɽ�");
		transactionType.put(20007, "��������");
		transactionType.put(31007, "���־ܾ�");
		transactionType.put(30004, "�������");
		transactionType.put(10002, "����");
		transactionType.put(20002, "׷��");
		transactionType.put(31006, "׷�ų���");
		transactionType.put(20003, "������򶳽�");
		transactionType.put(20005, "������򶳽�");
		transactionType.put(20009, "���򱣵׶���");
		transactionType.put(30002, "�������Ͷע");
		transactionType.put(30003, "�������Ͷע");
		transactionType.put(30006, "���򱣵�Ͷע");
		transactionType.put(31003, "���򷽰�����");
		transactionType.put(31005, "������볷��");
		transactionType.put(31009, "���򱣵׽ⶳ");
		transactionType.put(31010, "ʧ�ܶ����˿�");
		
		
		transactionToOpType = new HashMap<Integer,String>();
		transactionToOpType.put(11005, "A");//����
		transactionToOpType.put(11001, "A");
		transactionToOpType.put(11002, "A");
		transactionToOpType.put(31010, "A");
		transactionToOpType.put(10001, "B");//֧��
		transactionToOpType.put(30004, "B");
		transactionToOpType.put(10002, "B");
		transactionToOpType.put(30001, "B");
		transactionToOpType.put(30006, "B");
		transactionToOpType.put(30002, "B");
		transactionToOpType.put(30003, "B");
		transactionToOpType.put(20002, "C");//����
		transactionToOpType.put(20007, "C");
		transactionToOpType.put(20009, "C");
		transactionToOpType.put(20003, "C");
		transactionToOpType.put(20005, "C");
		transactionToOpType.put(31007, "D");//�ⶳ
		transactionToOpType.put(31006, "D");
		transactionToOpType.put(31009, "D");
		transactionToOpType.put(31005, "D");
		transactionToOpType.put(31003, "D");
		
		ipsOrderStatus = new HashMap<Integer,String>();
		ipsOrderStatus.put(0, "�ѷ������󵽻�Ѹ�ȴ����");
		ipsOrderStatus.put(1, "��Ѹ�ɹ���ֵ�ɹ�");
		ipsOrderStatus.put(2, "��Ѹ�ɹ���ֵʧ��");
		ipsOrderStatus.put(3, "��Ѹʧ�ܳ�ֵʧ��");
		ipsOrderStatus.put(4, "��Ѹʧ����·�ɹ�");
		ipsOrderStatus.put(5, "��ƽ�˴���ɹ�");
		ipsOrderStatus.put(6, "��ƽ�˴���ʧ��");
		
		
		billOrderStatus = new HashMap<Integer,String>();
		billOrderStatus.put(0, "�ѷ������󵽻�Ѹ�ȴ����");
		billOrderStatus.put(1, "��Ǯ�ɹ���ֵ�ɹ�");
		billOrderStatus.put(2, "��Ǯ�ɹ���ֵʧ��");
		billOrderStatus.put(3, "��Ǯʧ�ܳ�ֵʧ��");
		billOrderStatus.put(4, "��Ǯʧ����·�ɹ�");
		billOrderStatus.put(5, "��ƽ�˴���ɹ�");
		billOrderStatus.put(6, "��ƽ�˴���ʧ��");
		
		ipsCheckStatus = new HashMap<Integer,String>();
		ipsCheckStatus.put(0, "δ����");
		ipsCheckStatus.put(1, "����ƽ��");
		ipsCheckStatus.put(2, "�ɴ���ƽ��");
		ipsCheckStatus.put(3, "������ƽ��");
		
		billCheckStatus = new HashMap<Integer,String>();
		billCheckStatus.put(0, "δ����");
		billCheckStatus.put(1, "����ƽ��");
		billCheckStatus.put(2, "�ɴ���ƽ��");
		billCheckStatus.put(3, "������ƽ��");
		
		userSex = new HashMap<String,String>();
		userSex.put("0", "��");
		userSex.put("1", "Ů");
		
		adminStatus = new HashMap<String,String>();
		adminStatus.put("1", "����");
		
		mobileStatus = new HashMap<Boolean,String>();
		mobileStatus.put(false, "��");
		mobileStatus.put(true, "��");
		
		emailStatus = new HashMap<Boolean,String>();
		emailStatus.put(false, "��");
		emailStatus.put(true, "��");
		
		planStatus = new TreeMap<Integer,String>();
		planStatus.put(0, "������");
		planStatus.put(1, "������");
		planStatus.put(2, "�������");
		planStatus.put(3, "�����");
		planStatus.put(4, "�ѳ���");
		
		orderStatusForWeb = new HashMap<String,String>();
		orderStatusForWeb.put("0", "��Ʊ��");
		orderStatusForWeb.put("1", "��Ʊ��");
		orderStatusForWeb.put("2", "��Ʊ��");
		orderStatusForWeb.put("3", "��Ʊʧ��");
		orderStatusForWeb.put("4", "���ֳɹ�");
		orderStatusForWeb.put("6", "��Ʊʧ��");
		orderStatusForWeb.put("5", "��Ʊ�ɹ�");
		orderStatusForWeb.put("8", "�Ѷҽ�");
		orderStatusForWeb.put("10", "���ɽ�");
		orderStatusForWeb.put("11", "�Զ�ȡ��");
		orderStatusForWeb.put("12", "�ֹ�ȡ��");
		orderStatusForWeb.put("13", "׷����");
		orderStatusForWeb.put("14", "�޺�ȡ��");
		
		taskStautsForSms = new HashMap<Long,String>();
		taskStautsForSms.put(0L, "δ��ʼ");
		taskStautsForSms.put(1L, "������");
		taskStautsForSms.put(2L, "�������");
		taskStautsForSms.put(3L, "���͹��̴���");
		taskStautsForSms.put(4L, "���ͳ�ʱ");
		
		sendStatusForSms = new HashMap<Long, String>();
		sendStatusForSms.put(0L, "δ����");
		sendStatusForSms.put(1L, "�ѷ���");
		sendStatusForSms.put(2L, "��Ҫ�ط�");
		sendStatusForSms.put(3L, "���ͳ�ʱ");
		sendStatusForSms.put(4L, "�������");
		sendStatusForSms.put(5L, "���ͳɹ�");
		
		ticketStatusForQuery = new HashMap<String,String>();
		ticketStatusForQuery.put("10000", "δ��Ʊ");
		ticketStatusForQuery.put("10001", "��Ʊ��");
		ticketStatusForQuery.put("10002", "��Ʊ�ɹ�");
		ticketStatusForQuery.put("10003", "��Ʊʧ��");
		
		ticketStatusForWeb = new HashMap<String,String>();
		ticketStatusForWeb.put("0", "δ��Ʊ");
		ticketStatusForWeb.put("1", "��Ʊ��");
		ticketStatusForWeb.put("2", "��Ʊ��");
		ticketStatusForWeb.put("3", "��Ʊ��");
		ticketStatusForWeb.put("4", "��Ʊ��");
		ticketStatusForWeb.put("5", "��Ʊ��");
		ticketStatusForWeb.put("6", "��Ʊ�ɹ�");
		ticketStatusForWeb.put("7", "��Ʊʧ��");
		ticketStatusForWeb.put("8", "��Ʊʧ��");
		ticketStatusForWeb.put("9", "��Ʊʧ��");
		
		ticketWinStatus = new HashMap<String,String>();
		ticketWinStatus.put("0", "δ�ҽ�");
		ticketWinStatus.put("1", "δ�н�");
		ticketWinStatus.put("2", "��С��");
		ticketWinStatus.put("3", "�д�");
		
		operatorlogLevelStatus = new HashMap<Integer,String>();
		operatorlogLevelStatus.put(10000, "DEBUG");
		operatorlogLevelStatus.put(20000, "INFO");
		operatorlogLevelStatus.put(30000, "WARN");
		operatorlogLevelStatus.put(40000, "ERROR");
		operatorlogLevelStatus.put(50000, "FATAL");
		operatorlogTypeStatus = new HashMap<Integer,String>();
		operatorlogTypeStatus.put(0, "�������");
		operatorlogTypeStatus.put(10000, "��̨��ȫ");
		operatorlogTypeStatus.put(20000, "ǰ̨��ȫ");
		operatorlogTypeStatus.put(30000, "�����ʽ����");
		operatorlogTypeStatus.put(40000, "ҵ����");
		operatorlogRankStatus = new HashMap<Integer,String>();
		operatorlogRankStatus.put(10000, "��ͨ");
		operatorlogRankStatus.put(20000, "��Ҫ");
		operatorlogRankStatus.put(30000, "��Ҫ");
		operatorlogRankStatus.put(40000, "�ǳ���Ҫ");
		
		needLimitLottery = new ArrayList<Integer>();
		needLimitLottery.add(LOTTERY_1000003);
		needLimitLottery.add(LOTTERY_1200001);
		
		planType = new HashMap<Integer,String>();
		planType.put(0, "����");
		planType.put(1, "����");
		planStop = new HashMap<Integer,String>();
		planStop.put(0, "��ֹͣ");
		planStop.put(1, "ֹͣ");
		planOpenType = new HashMap<Integer,String>();
		planOpenType.put(0, "����");
		planOpenType.put(1, "����󹫿�");
		planOpenType.put(2, "��ֹ�󹫿�");
		//0-δ�ҽ���1-δ�н���2-��С����3-�д�
		coopWinStatus = new HashMap<Integer,String>();
		coopWinStatus.put(0, "δ�ҽ�");
		coopWinStatus.put(1, "δ�н�");
		coopWinStatus.put(2, "��С��");
		coopWinStatus.put(3, "�д�");
		//���򶩵���ʽ,0-�����Ϲ���1-�����Ϲ�,2-���𱣵�,3-���𱣵�תͶע
		coopOrderType = new HashMap<Integer,String>();
		coopOrderType.put(0, "�����Ϲ�");
		coopOrderType.put(1, "�����Ϲ�");
		coopOrderType.put(2, "���𱣵�");
		coopOrderType.put(3, "���𱣵�תͶע");
		
		planStatusForWebH = new HashMap<Integer,String>();
		planStatusForWebH.put(900, "������");
		planStatusForWebH.put(901, "����������");
		planStatusForWebH.put(902, "�ѳ�Ʊ");
		planStatusForWebH.put(903, "�ѳ�Ʊ");
		planStatusForWebH.put(904, "�ѳ���");
		planStatusForWebH.put(0, "��Ʊ��");
		planStatusForWebH.put(1, "��Ʊ��");
		planStatusForWebH.put(2, "��Ʊ��");
		planStatusForWebH.put(3, "��Ʊʧ��");
		planStatusForWebH.put(4, "�ѳ�Ʊ");
		planStatusForWebH.put(5, "�ѳ�Ʊ");
		planStatusForWebH.put(6, "��Ʊʧ��");
		planStatusForWebH.put(8, "�Ѷҽ�");
		planStatusForWebH.put(10, "���ɽ�");
		
		planStatusForQuery = new LinkedHashMap<Integer, String>();
		planStatusForQuery.put(100, "������");
		planStatusForQuery.put(101, "��Ʊ��");
		planStatusForQuery.put(102, "�ѳ�Ʊ");
		planStatusForQuery.put(103, "�Ѷҽ�");
		planStatusForQuery.put(104, "���ɽ�");
		planStatusForQuery.put(105, "��Ʊʧ��");
		planStatusForQuery.put(106, "�ѳ���");
		
	}
	/**
	 * 
	 * Title: getLotteryList<br>
	 * Description: <br>
	 *              <br>��ȡϵͳ�������õĲ���
	 * @return Map<Integer,String>
	 */
	public static Map<Integer,String> getLotteryList(){
		Map<Integer,String> result = new TreeMap<Integer,String>();
		Map<Integer,String> tmp = LotteryTools.getLotteryList();
		for(Map.Entry<Integer, String> oneLottery : tmp.entrySet()){
			int lotteryId = oneLottery.getKey();
			String lotteryName = oneLottery.getValue();
			if(LotteryTools.isLotteryStart(lotteryId)){
				result.put(lotteryId, lotteryName);
			}
		}
		return result;
	}
	
	public static Map<Integer, String> getTicketStatusList(){
		Map<Integer,String> result = new TreeMap<Integer,String>();
		result.put(Integer.valueOf(1), "��Ʊ��");
		result.put(Integer.valueOf(6), "��Ʊ�ɹ�");
		result.put(Integer.valueOf(7), "��Ʊʧ��");
		return result;
	}
	
	public static String getTermDetailLink(String lotteryId,String termNo){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showTermInfoDetail.jhtml?l_lotteryId=").append(lotteryId).append("&l_termNo=")
		.append(termNo).append("','newwindow','").append(open_format).append("')\">��ϸ</a>");
		return sb.toString();
	}
	
	public static String getOrderDetailLink(String orderId,String show_str){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('showOrderInfoDetail.jhtml?orderId=").append(orderId)
		.append("','newwindow','").append(open_format).append("')\">").append(show_str).append("</a>");
		return sb.toString();
	}
	/**
	 * 
	 * Title: getCoopOrderDetailLink<br>
	 * Description: <br>
	 *              <br>
	 * @param planId
	 * @param coopOrderId
	 * @param show_str
	 * @return
	 */
	public static String getCoopOrderDetailLink(String planId,String coopOrderId,String show_str){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('showCoopInfoDetail.jhtml?planId=").append(planId)
		.append("&coopOrderId=").append(coopOrderId)
		.append("','coopInfoDetail','").append(open_format).append("')\">").append(show_str).append("</a>");
		return sb.toString();
	}
	
	public static String getUserStatusDefineMap(String status){
		return userStatus.get(status);
	}
	
	public static String getUserDetailLink(String userIdentify){
		StringBuffer sb = new StringBuffer();
		if(null==userIdentify){
			sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showUserInfoDetail.jhtml?userIdentify=")
			.append("").append("','newwindow','").append(open_format).append("')\"" ).append(">")
			.append("").append("</a>");
		}else{
			sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showUserInfoDetail.jhtml?userIdentify=")
			.append(userIdentify).append("','newwindow','").append(open_format).append("')\"" ).append(">")
			.append(userIdentify).append("</a>");
		}
		return sb.toString();
	}
	
	public static String getUserEditLink(long userId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showUserInfoDetail.jhtml?userIdentify=").append(userId)
		.append("','newwindow','").append(open_format).append("')\">����</a>").append("/")
		.append("<a  href=\"#\" onclick=\"javascript:window.open('showUserInfoDetail.jhtml?userIdentify=").append(userId)
		.append("','newwindow','").append(open_format).append("')\">��������</a>");
		return sb.toString();
	}

	public static String getAdminEditLink(long userId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('updateAdminUserShow.jhtml?userIdentify=").append(userId)
		.append("','newwindow','").append(open_format).append("')\">�޸�</a>").append("/")
		.append("<a  href=\"#\" onclick=\"javascript:window.open('changePerPasswordShow.jhtml?userIdentify=").append(userId)
		.append("','newwindow','").append(open_format).append("')\">��������</a>");
		return sb.toString();
	}
	
	public static String getOrderDiapatchLink(String planId,String orderId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('dispatchOrderConfig.jhtml?orderId=")
				.append(orderId).append("&planId=").append(planId)
		.append("','newwindow','").append(open_format).append("')\">").append("�ɽ�").append("</a>");
		return sb.toString();
	}
	
	public static String getDaiGouErrLink(String orderId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('daiGouErrOrderConfirm.jhtml?orderId=")
				.append(orderId)
		.append("','daigouerrconfirm','").append(open_format).append("')\">").append("�˿�").append("</a>");
		return sb.toString();
	}
	
	public static String heMaiGouErrLink(String orderId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('heMaiErrOrderConfirm.jhtml?orderId=")
				.append(orderId)
		.append("','hemaierrconfirm','").append(open_format).append("')\">").append("�˿�").append("</a>");
		return sb.toString();
	}
	//���Ⱥ��������ϸ������
	public static String getSmsPushDataLink(long id){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showSmsPushDataDetail.jhtml?id=")
		.append(id).append("','newwindow','").append(open_format).append("')\"" ).append(">")
		.append("��ϸ").append("</a>");
		return sb.toString();
	}
	//���Ⱥ��������ϸ������
	public static String getSmsPushTaskLink(String taskId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showSmsPushTaskDetail.jhtml?taskId=")
		.append(taskId).append("','newwindow','").append(open_format).append("')\"" ).append(">")
		.append("��ϸ").append("</a>");
		return sb.toString();
	}
	//���ȷ����Ⱥ��������ϸ�Լ�Ⱥ��������Ŀ������
	public static Object getSmsPushTaskIdLink(String taskId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showSmsList.jhtml?taskId=")
		.append(taskId).append("','newwindow','").append(open_format).append("')\"" ).append(">")
		.append(taskId).append("</a>");
		return sb.toString();
	}
	//��ȷ����Ⱥ��������ϸ�Լ�Ⱥ��������Ŀ��ҳ���е���������
	public static String getSmsPushTaskDataLink(long id){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showSmsPushDataDetail.jhtml?id=")
		.append(id).append("','subWind','").append(open_format).append("')\"" ).append(">")
		.append("��ϸ").append("</a>");
		return sb.toString();
	}
	
	//������ϸ�������ӡ�ֻ��Ҫ�������ӵ�URL�����ӵĵı�ʶ�����ӵ�id�����ӵ����ƣ���ҳ�浯��ҳ��ķ���
	public static String getAllTheLink(String linkPage,String linkTitle,String linkId,String linkName){
		StringBuffer allTheLink = new StringBuffer();
		allTheLink.append("<a href=\"#\" onclick=\"javascript:window.open('").append(linkPage).
			append("?").append(linkTitle).append("=").append(linkId).append("','newwindow','").
			append(open_format).append("')\">").append(linkName).append("</a>");
		return allTheLink.toString();
	}
	
	//������ϸ�������ӡ�ֻ��Ҫ�������ӵ�URL�����ӵĵı�ʶ�����ӵ�id�����ӵ����ƣ���ҳ�浯��ҳ��ķ���
	public static String getSubAllTheLink(String linkPage,String linkTitle,String linkId,String linkName) {
		StringBuffer allTheSubLink = new StringBuffer();
		allTheSubLink.append("<a href=\"#\" onclick=\"javascript:window.open('").append(linkPage).
			append("?").append(linkTitle).append("=").append(linkId).append("','subWind','").
			append(open_format).append("')\">").append(linkName).append("</a>");
		return allTheSubLink.toString();
	}
	
	public static Map<String,Map<String,String>> convertWinResultForPage(){
		return null;
	}

	

	

	

	

}
