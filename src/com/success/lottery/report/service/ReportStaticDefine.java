/**
 * Title: ReportStaticDefine.java
 * @Package com.success.lottery.report.service
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-8-10 ����10:05:16
 * @version V1.0
 */
package com.success.lottery.report.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * com.success.lottery.report.service
 * ReportStaticDefine.java
 * ReportStaticDefine
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-8-10 ����10:05:16
 * 
 */

public class ReportStaticDefine {
	
	public static final String report_prize_super = "report_prize_super";
	public static final String report_prize_arrange3 = "report_prize_arrange3";
	public static final String report_prize_ball = "report_prize_ball";
	public static final String report_prize_arrange5 = "report_prize_arrange5";
	public static final String report_prize_seven = "report_prize_seven";
	public static final String report_prize_dlc = "report_prize_dlc";
	public static final String report_prize_ball6 = "report_prize_ball6";
	public static final String report_prize_ball4 = "report_prize_ball4";
	
	public static final String bet_Sort = "1001";
	public static final String prize_Sort = "1002";
	public static final String chongZhi_Sort = "1003";
	public static final String draw_Sort = "1004";
	
	public static Map<Integer,String> lotteryToReportTable;//���ֶ�Ӧ���н�ͳ�Ʊ��������
	
	public static Map<String,String> reportTransSortType;//�������������Ͷ���
	
	public static Map<String,String> prizeReportType;//������ֶ�Ӧ���н�ͳ�Ʊ�������
	
	public static Map<String,String> reportLotteryName;//���屨��Ĳ�����������
	
	static {
		
		lotteryToReportTable = new HashMap<Integer,String>();
		lotteryToReportTable.put(1000001, report_prize_super);//����͸
		lotteryToReportTable.put(1000002, report_prize_seven);//���ǲ�
		lotteryToReportTable.put(1000003, report_prize_arrange3);//������
		lotteryToReportTable.put(1000004, report_prize_arrange5);//������
		lotteryToReportTable.put(1000005, report_prize_super);//��Ф��
		lotteryToReportTable.put(1300001, report_prize_ball);//ʤ����
		lotteryToReportTable.put(1300002, report_prize_ball);//��ѡ�ų�
		lotteryToReportTable.put(1300003, report_prize_ball6);//6����ȫ��
		lotteryToReportTable.put(1300004, report_prize_ball4);//4�������
		lotteryToReportTable.put(1200001, report_prize_dlc);//�������ֲ�
		
		reportTransSortType = new LinkedHashMap<String, String>();
		reportTransSortType.put("1001", "Ͷע����");
		reportTransSortType.put("1002", "�н�����");
		reportTransSortType.put("1003", "��ֵ����");
		reportTransSortType.put("1004", "��������");
		
		prizeReportType = new HashMap<String, String>();
		prizeReportType.put("1000001", "1000001");//����͸����Ф��
		prizeReportType.put("1000002", "1000002");//���ǲ�
		prizeReportType.put("1000003", "1000003");//������
		prizeReportType.put("1000004", "1000004");//������
		prizeReportType.put("1000005", "1000001");//����͸����Ф��
		prizeReportType.put("1300001", "1300001");//ʤ���ʡ���ѡ�ų�
		prizeReportType.put("1300002", "1300001");//ʤ���ʡ���ѡ�ų�
		prizeReportType.put("1300003", "1300003");//6����ȫ��
		prizeReportType.put("1300004", "1300004");//4�������
		prizeReportType.put("1200001", "1200001");//�������ֲ�
		
		reportLotteryName = new HashMap<String, String>();
		reportLotteryName.put("1000001", "����͸");
		reportLotteryName.put("1000002", "���ǲ�");
		reportLotteryName.put("1000003", "����3");
		reportLotteryName.put("1000004", "����5");
		reportLotteryName.put("1000005", "����͸");
		reportLotteryName.put("1300001", "���");
		reportLotteryName.put("1300002", "���");
		reportLotteryName.put("1300003", "6����ȫ��");
		reportLotteryName.put("1300004", "4�������");
		reportLotteryName.put("1200001", "���ֲ�");
	}

}
