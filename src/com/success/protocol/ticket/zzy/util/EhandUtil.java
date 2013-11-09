/**
 * Title: EhandUtil.java
 * @Package com.success.protocol.ticket.zzy.util
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-21 ����10:41:05
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.util;

import java.util.HashMap;
import java.util.Map;

import com.success.lottery.util.LotteryTools;
import com.success.utils.AutoProperties;

/**
 * com.success.protocol.ticket.zzy.util
 * EhandUtil.java
 * EhandUtil
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-21 ����10:41:05
 * 
 */

public class EhandUtil {
	
	//�����쳣
	public static final int E_01_CODE = 900001;
	public static final String E_01_DESC = "��������ͨѶ����";
	public static final int E_02_CODE = 900002;
	public static final String E_02_DESC = "��������ͨѶ���ص�HTTP��Ӧ״̬����ȷ��";
	public static final int E_03_CODE = 900003;
	public static final String E_03_DESC = "��������ͨѶ��ȷ���ص���Ϣ��������";
	public static final int E_04_CODE = 900004;
	public static final String E_04_DESC = "�����������ȷ��";
	public static final int E_05_CODE = 900005;
	public static final String E_05_DESC = "�������ݿⷢ���쳣��";
	public static final int E_06_CODE = 90006;
	public static final String E_06_DESC = "�������ݿ�ʱδ���µ����ݣ�";
	public static final int E_07_CODE = 90007;
	public static final String E_07_DESC = "�������󷵻ش�����[CODE],����ԭ��[MSG]��";
	public static final int E_08_CODE = 90008;
	public static final String E_08_DESC = "��������̷��������쳣��";
	public static final int E_09_CODE = 90009;
	public static final String E_09_DESC = "��ѯ�����������ص�δ�鵽���ݣ�";
	
	//������Ϣ�������ļ��ж���
	private static String eHandConfig = "com.success.protocol.ticket.zzy.ehand";
	public static String SYS_DEFINE_USERID;//ϵͳ��������Լ�����û�ID
	public static String SYS_DEFINE_KEY;//ϵͳ��������Լ���Ĵ�����Կ
	public static int reSendNum = 3;//������ط�����
	public static long reSendSleep = 5000L;//������ط�ʱ��������λΪ����
	public static String DLC_CASH = "0";//���ֲ��Ƿ�õ�����������Ƿ�ֱ�Ӷҽ���0��ʾ��ֱ�Ӷҽ���1��ʾֱ�Ӷҽ�
	//������֪ͨ·������
	public static String PATH_01;//xml�������ķ�ʽ
	public static String PATH_02;//��������֪ͨ
	public static String PATH_03;//����֪ͨ
	public static String PATH_04;//�ڽ�֪ͨ
	//�����������·��
	public static String url_50200;//���ڲ�ѯ
	public static String url_50201;//���ֲʳ�Ʊ
	public static String url_50203;//���������ѯ
	public static String url_50205;//��Ʊ״̬��ѯ
	public static String url_50206;//��ȡ�н�����
	public static String url_50207;//�����淨��Ʊ
	public static String url_50208;//���������ѯ
	public static String url_50209;//����״̬��ѯ
	
	public static Map<Integer,String> lotteryToZzy;//Ͷעϵͳ��������ϵͳ�Ĳ��ֶ�Ӧ
	
	public static String commd_50200;
	public static String commd_50201;
	public static String commd_50203;
	public static String commd_50204;
	public static String commd_50205;
	public static String commd_50206;
	public static String commd_50207;
	public static String commd_50208;
	public static String commd_50209;
	public static String commd_90001;//��������֪ͨ
	public static String commd_90002;//����֪ͨ
	public static String commd_90003;//�ڽ�֪ͨ
	
	//ϵͳ���Խ��յ�֪ͨ��Ϣ��
	public static String [] NOTICE_COMMAND;
	
	//�յ���Ϣ��ķ��ش�����
	public static final String ERRORCODE00 = "0";//�ɹ��յ���Ϣ
	public static final String ERRORCODE01 = "1";//�ɹ��յ���Ϣ��Ϣͷ�ṹ����
	public static final String ERRORCODE02 = "2";//��Ϣ������ϵͳδ����
	public static final String ERRORCODE03 = "3";//userid��Լ���Ĳ�һ��
	public static final String ERRORCODE04 = "4";//�ɹ��յ���Ϣmd5У�����
	public static final String ERRORCODE05 = "5";//��Ϣ�������쳣
	
	public static String check_md5;
	
	
	static {
		lotteryToZzy = LotteryTools.getLotteryEhand();
		
		commd_50200 = AutoProperties.getString("commd_50200", "50200", eHandConfig);
		commd_50201 = AutoProperties.getString("commd_50201", "50201", eHandConfig);
		commd_50203 = AutoProperties.getString("commd_50203", "50203", eHandConfig);
		commd_50204 = AutoProperties.getString("commd_50204", "50204", eHandConfig);
		commd_50205 = AutoProperties.getString("commd_50205", "50205", eHandConfig);
		commd_50206 = AutoProperties.getString("commd_50206", "50206", eHandConfig);
		commd_50207 = AutoProperties.getString("commd_50207", "50207", eHandConfig);
		commd_50208 = AutoProperties.getString("commd_50208", "50208", eHandConfig);
		commd_50209 = AutoProperties.getString("commd_50209", "50209", eHandConfig);
		commd_90001 = AutoProperties.getString("commd_90001", "90001", eHandConfig);
		commd_90002 = AutoProperties.getString("commd_90002", "90002", eHandConfig);
		commd_90003 = AutoProperties.getString("commd_90003", "90003", eHandConfig);
		
		NOTICE_COMMAND = new String[] {commd_50204,commd_90001,commd_90002,commd_90003};
		
		SYS_DEFINE_USERID = AutoProperties.getString("sys_define_userid", "", eHandConfig);
		SYS_DEFINE_KEY = AutoProperties.getString("sys_define_key", "", eHandConfig);
		
		reSendNum = AutoProperties.getInt("reSendNum", 3, eHandConfig);
		reSendSleep = AutoProperties.getInt("reSendSleep", 5000, eHandConfig);
		
		DLC_CASH = AutoProperties.getString("dlc_cash", "0", eHandConfig);
		
		PATH_01 = AutoProperties.getString("xml_url", "/", eHandConfig);
		PATH_02 = AutoProperties.getString("kj_url", "/accept_code.jsp", eHandConfig);
		PATH_03 = AutoProperties.getString("new_term_url", "/IsusesReceive", eHandConfig);
		PATH_04 = AutoProperties.getString("end_term_url", "/rev3", eHandConfig);
		
		url_50200 = AutoProperties.getString("url_50200", "", eHandConfig);
		url_50201 = AutoProperties.getString("url_50201", "", eHandConfig);
		url_50203 = AutoProperties.getString("url_50203", "", eHandConfig);
		url_50205 = AutoProperties.getString("url_50205", "", eHandConfig);
		url_50206 = AutoProperties.getString("url_50206", "", eHandConfig);
		url_50207 = AutoProperties.getString("url_50207", "", eHandConfig);
		url_50208 = AutoProperties.getString("url_50208", "", eHandConfig);
		url_50209 = AutoProperties.getString("url_50209", "", eHandConfig);
		
		check_md5 = AutoProperties.getString("check_md5", "1", eHandConfig);
	}
	
	/**
	 * 
	 * Title: lotteryToZzy<br>
	 * Description: <br>
	 *              <br>
	 * @param lotteryId
	 * @return
	 */
	public static String lotteryToZzy(int lotteryId){
		return lotteryToZzy.get(lotteryId);
	}
	/**
	 * 
	 * Title: zzyToLottery<br>
	 * Description: <br>
	 *              <br>
	 * @param lotteryId
	 * @return
	 */
	public static int zzyToLottery(String lotteryId){
		int lotteryInt = 0;
		for(Map.Entry<Integer, String> one : lotteryToZzy.entrySet()){
			Integer key = one.getKey();
			String value = one.getValue();
			if(value.equals(lotteryId)){
				lotteryInt = key;
				break;
			}
		}
		return lotteryInt;
	}
	

}
