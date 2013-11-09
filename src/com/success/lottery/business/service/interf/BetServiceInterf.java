/**
 * Title: BetServiceInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-19 ����04:44:03
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * BetServiceInterf.java
 * BetServiceInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-19 ����04:44:03
 * 
 */

public interface BetServiceInterf {
	
	public static final int E_01_CODE = 500001;
	public static final String E_01_DESC = "Ͷע��ʽ����";
	public static final int E_02_CODE = 500002;
	public static final String E_02_DESC = "�û������ڣ�";
	public static final int E_03_CODE = 500003;
	public static final String E_03_DESC = "�û����ڵ����򲻿������۸ò��֣�";
	
	public static final int E_04_CODE = 500004;
	public static final String E_04_DESC = "��ǰ���ڲ��������ۣ�";
	
	public static final int E_05_CODE = 500005;
	public static final String E_05_DESC = "Ͷע��ע���ͽ��δͨ��У�飡";
	
	public static final int E_07_CODE = 500007;
	public static final String E_07_DESC = "Ͷע�������޶���";
	
	public static final int E_09_CODE = 500009;
	public static final String E_09_DESC = "�û��˻��ڵĽ�������Ͷע��";
	
	public static final int E_10_CODE = 500010;
	public static final String E_10_DESC = "Ͷע�۳��˻��ʽ����";
	
	public static final int E_11_CODE = 500011;
	public static final String E_11_DESC = "�û�״̬�����������ܱ������ע����";
	
	public static final int E_12_CODE = 500012;
	public static final String E_12_DESC = "׷�Ŷ����˻��ʽ����";
	
	public static final int E_13_CODE = 500013;
	public static final String E_13_DESC = "Ͷע�����������ƣ�";
	
	public static final int E_15_CODE = 500015;
	public static final String E_15_DESC = "���ƺ���[1]����Ͷע��";
	
	public static final int E_16_CODE = 500016;
	public static final String E_16_DESC = "������ɱ������ò���ȷ,��ɱ���ӦΪ:A";
	
	public static final int E_17_CODE = 500017;
	public static final String E_17_DESC = "�����ܷ������ò���ȷ��";
	
	public static final int E_18_CODE = 500018;
	public static final String E_18_DESC = "����ÿһ�ݽ�����ò���ȷ��";
	
	public static final int E_19_CODE = 500019;
	public static final String E_19_DESC = "���������Ϲ���������Ҫ��";
	
	public static final int E_20_CODE = 500020;
	public static final String E_20_DESC = "���򱣵׷�������С���ܷ�����20%��";
	
	public static final int E_21_CODE = 500021;
	public static final String E_21_DESC = "���򱣵׷�������С���ܷ�����20%��";
	
	public static final int E_22_CODE = 500022;
	public static final String E_22_DESC = "��������ʽ𶳽ύ�׹����г����쳣%��";
	
	public static final int E_23_CODE = 500023;
	public static final String E_23_DESC = "���򷽰�Ͷע�ɹ������ʽ�۳����׹����г����쳣��";
	
	public static final int E_24_CODE = 500024;
	public static final String E_24_DESC = "���򷽰�δ�鵽��";
	public static final int E_25_CODE = 500025;
	public static final String E_25_DESC = "���򷽰��Ѿ���ֹ�����ٲ��룡";
	public static final int E_26_CODE = 500026;
	public static final String E_26_DESC = "���򷽰����������Ϲ�1�ݣ�";
	public static final int E_27_CODE = 500027;
	public static final String E_27_DESC = "���򷽰������Ϲ�������";
	public static final int E_28_CODE = 500028;
	public static final String E_28_DESC = "���򷽰������Ϲ��ݶ���������ȣ�";
	
	public static final int E_29_CODE = 500029;
	public static final String E_29_DESC = "Ҫ�����Ķ����볷���˲�һ�£����ܳ�����";
	
	public static final int E_30_CODE = 500030;
	public static final String E_30_DESC = "�����Ѿ����ܳ�����";
	
	public static final int E_31_CODE = 500031;
	public static final String E_31_DESC = "�����Ѿ����ܳ���";
	
	public static final int E_32_CODE = 500032;
	public static final String E_32_DESC = "���󶩵��˿�ʱδ�ҵ�����";
	
	public static final int E_33_CODE = 500033;
	public static final String E_33_DESC = "���󶩵��˿�ʱ���������˿�,����";
	
	public static final int E_34_CODE = 500034;
	public static final String E_34_DESC = "���󶩵��˿�,�����Ѿ������˿��,�������˿�";
	
	public static final int E_35_CODE = 500035;
	public static final String E_35_DESC = "���󶩵��˿�ʱδ�ҵ�������Ӧ�Ĳ�Ʊ����";
	
	public static final int E_36_CODE = 500036;
	public static final String E_36_DESC = "���󶩵��˿�ʱδ���µ��������˿�ʧ��";
	
	
	public static final int E_37_CODE = 500037;
	public static final String E_37_DESC = "�������˲��ܳ����������Ϲ��ķݶ";
	
	public static final int E_38_CODE = 500038;
	public static final String E_38_DESC = "�������˲��ܳ������׵ķݶ";
	
	public static final int E_39_CODE = 500039;
	public static final String E_39_DESC = "���󶩵��˿�ʱδ�ҵ�������Ӧ�ķ�����Ϣ��";
	
	public static final int E_40_CODE = 500040;
	public static final String E_40_DESC = "���󶩵��˿�ʱδ�ҵ�������Ӧ�Ĳ��������Ϣ��";
	
	/*
	 * ���µ��쳣���ڳ������
	 */
	public static final int E_06_CODE = 501006;
	public static final String E_06_DESC = "����ע���ͽ��ʱ����";
	
	public static final int E_08_CODE = 501008;
	public static final String E_08_DESC = "�����ļ��е�Ͷע����������ò���ȷ��";
	
	public static final int E_14_CODE = 501014;
	public static final String E_14_DESC = "�������쳣��";
	//���쳣������»����������δ���µ����ݣ����߲�ѯʱδ�鵽��Ҫ����Ϣ
	public static final int E_100_CODE = 501100;
	public static final String E_100_DESC = "[OP]��";
	
	/**
	 * 
	 * Title: betWeb<br>
	 * Description: web��ʽ��Ͷע,�÷�����Ҫʵ�����²��裺
	 *            <br>��1��У��Ͷע�ַ�����ʽ�Ƿ���ȷ
	 *            <br>��2��������û��ʺ��Ƿ����
	 *            <br>��3���û����������Ƿ�������۸ò�Ʊ
	 *            <br>��4��У�鵱ǰ���Ƿ��������
	 *            <br>��5�����ݴ����betNum��amount����У���̨�����ע���ͽ���Ƿ�ʹ����һ�£��������������������-1��ʹ�ú�̨���������
	 *            <br>��6��У�鵥�ʶ�������Ƿ񳬹�����Ľ������
	 *            <br>��7�����ɷ����Ͷ���
	 *            <br>��7.1���ж��˻�����Ƿ��ã�����׷�������Ľ��
	 *            <br>��7.2�����ɷ����Ͷ���
	 *            <br>��7.3�����ն�����Ŷ����˻����(ֻҪ���ɶ����Ͷ����ʽ𣬲��ù��Ƿ��Ʊ�ɹ�,�Ե�ǰ�ڵ�ֱ�ӿ۳��ʽ𣬶�׷�ŵ��ȶ����ʽ�)
	 *            <br>��7.4��
	 *            <br> ���ϲ��������һ�������׳��쳣������������ع�
	 * @param userIdentify �û���ʶ
	 * @param lotteryId ����id
	 * @param playType �淨id
	 * @param betType  Ͷע��ʽid
	 * @param betMultiple Ͷע����  ���㵥�ʽ����Ϊ��amount*betMultiple����ֵֻ��ʾ����Ĳ���term���ںţ��ı�������ֵ������Ϊ����������Ϊ0�����򱨴�
	 *              
	 * @param betNum Ͷע��ע�� ��������ø�ֵҪ����-1
	 * @param amount Ͷע����λΪ��,����׷�ŵ�Ͷע����Ͷע���ݵĽ��(���ܰ���Ͷע�����Ľ��) ,��������ø�ֵҪ����-1
	 * @param term ҪͶע���ں�
	 * @param supplementTerms ����׷����Ϊ׷�������б���ֻ�Ե�ǰ��Ͷע��Ϊnull,������������Ϊ:<�ںţ�����>�����б�������Ϊ����������Ϊ0
	 * @param winStopped ׷��ʱ��Ч���н����Ƿ�ֹͣ׷��, true-ֹͣ false-��ֹͣ
	 * @param betCode ע�룬Ͷע��ע�룬��ʽ�ο� ����״̬��ʽ���Ķ���.xls �����淨Ͷע������������
	 * @return  �ں�#�������#ע��#�ܽ��
	 * @throws LotteryException<br>
	 *                          <br>401001:���ɷ���������ʱ��������(�������)
	 *                          <br>401002:���ɷ�������������ʱ��������(�������)
	 *                          <br>500001:Ͷע��ʽ����
	 *                          <br>500002:�û�������
	 *                          <br>500003:�û����ڵ����򲻿������۸ò���
	 *                          <br>500004:��ǰ���ڲ���������
	 *                          <br>500005:Ͷע��ע���ͽ��δͨ��У�� ���쳣�Զ���Ͷעû��
	 *                          <br>500007:Ͷע�������޶����
	 *                          <br>500009:�û��˻��ڵĽ�������Ͷע
	 *                          <br>500010:Ͷע�۳��˻��ʽ����
	 *                          <br>500011:�û�״̬�����������ܱ������ע��
	 *                          <br>500012:׷�Ŷ����˻��ʽ����
	 *                          <br>AccountService�ж���Ľ����쳣
	 *                          <br>501006:����ע���ͽ��ʱ����(�������)
	 *                          <br>501008:�����ļ��е�Ͷע����������ò���ȷ(�������)
	 *                          <br>501014:Ͷע�������쳣
	 */
	public String betWeb(String userIdentify, int lotteryId, int playType, int betType,
			int betMultiple,long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;
	/**
	 * 
	 * Title: betWeb<br>
	 * Description: <br>
	 *            web��ʽ��Ͷע<br>
	 * @param userId �û��ʺ�
	 * @param lotteryId ����id
	 * @param playType �淨id
	 * @param betType Ͷע��ʽid
	 * @param betMultiple Ͷע����  ���㵥�ʽ����Ϊ��amount*betMultiple����ֵֻ��ʾ����Ĳ���term���ںţ��ı�������ֵ������Ϊ����������Ϊ0�����򱨴�
	 *                   
	 * @param betNum Ͷע��ע�� ��������ø�ֵҪ����-1
	 * @param amount Ͷע����λΪ��,����׷�ŵ�Ͷע����Ͷע���ݵĽ��(���ܰ���Ͷע�����Ľ��) ,��������ø�ֵҪ����-1
	 * @param term ҪͶע���ں�
	 * @param supplementTerms ����׷����Ϊ׷�������б���ֻ�Ե�ǰ��Ͷע��Ϊnull,������������Ϊ:<�ںţ�����>�����б�������Ϊ����������Ϊ0
	 * @param winStopped ׷��ʱ��Ч���н����Ƿ�ֹͣ׷��, true-ֹͣ false-��ֹͣ
	 * @param betCode ע�룬Ͷע��ע�룬��ʽ�ο� ����״̬��ʽ���Ķ���.xls �����淨Ͷע������������
	 * @return �ں�#�������#ע��#�ܽ��
	 * @throws LotteryException
	 */
	public String betWeb(long userId, int lotteryId, int playType, int betType,
			int betMultiple,long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;

	
	/**
	 * 
	 * Title: betSms<br>
	 * Description: <br>
	 *            ����Ͷע<br>
	 * @param MobilePhone �绰����
	 * @param lotteryId ����id
	 * @param playType �淨id
	 * @param betType Ͷע��ʽ
	 * @param betMultiple Ͷע����  ���㵥�ʽ����Ϊ��amount*betMultiple����ֵֻ��ʾ����Ĳ���term���ںţ��ı�������ֵ������Ϊ����������Ϊ0�����򱨴�
	 *                   
	 * @param betCode ע�룬Ͷע��ע�룬��ʽ�ο� ����״̬��ʽ���Ķ���.xls �����淨Ͷע������������
	 * @return  �ں�#�������#ע��#�ܽ��
	 * @throws LotteryException<br>
	 *                          <br>401001:���ɷ���������ʱ��������
	 *                          <br>401002:���ɷ�������������ʱ��������
	 *                          <br>500001:Ͷע��ʽ����
	 *                          <br>500002:�û�������
	 *                          <br>500003:�û����ڵ����򲻿������۸ò���
	 *                          <br>500004:��ǰ���ڲ���������
	 *                          <br>500005:Ͷע��ע���ͽ��δͨ��У�� ���쳣�Զ���Ͷעû��
	 *                          <br>500006:����ע���ͽ��ʱ����
	 *                          <br>500007:Ͷע�������޶����
	 *                          <br>500008:�����ļ��е�Ͷע����������ò���ȷ
	 *                          <br>500009:�û��˻��ڵĽ�������Ͷע
	 *                          <br>500010:Ͷע�۳��˻��ʽ����
	 *                          <br>500011:�û�״̬�����������ܱ������ע��
	 *                          <br>500012:׷�Ŷ����˻��ʽ����
	 *                          <br>301005: ��ȡlotteryid�ĵ�ǰ����Ϣʱ���ݿ����
	 *                          <br>300005:��ȡlotteryid�ĵ�ǰ������Ϣʱδ�ҵ�����Ͷע�Ĳ���
	 *                          <br>AccountService�ж���Ľ����쳣
	 *                          
	 */
	public String betSms(String MobilePhone, int lotteryId, int playType,
			int betType, int betMultiple,String betCode)
			throws LotteryException;
	
	public String betSms(String MobilePhone, int lotteryId,String betTerm, int playType,
			int betType, int betMultiple,String betCode)
			throws LotteryException;
	
	/**
	 * 
	 * Title: betSms<br>
	 * Description: <br>
	 *            ����Ͷע<br>
	 * @param MobilePhone �绰����
	 * @param lotteryId ����id
	 * @param playType �淨id
	 * @param betType Ͷע��ʽ
	 * @param betMultiple Ͷע����  ���㵥�ʽ����Ϊ��amount*betMultiple����ֵֻ��ʾ����Ĳ���term���ںţ��ı�������ֵ������Ϊ����������Ϊ0�����򱨴�
	 *                   
	 * @param betCode ע�룬Ͷע��ע�룬��ʽ�ο� ����״̬��ʽ���Ķ���.xls �����淨Ͷע������������
	 * @return  �ں�#�������#ע��#�ܽ��
	 * @throws LotteryException<br />
	 *                          <br/>401001:���ɷ���������ʱ��������
	 *                          <br/>401002:���ɷ�������������ʱ��������
	 *                          <br/>500001:Ͷע��ʽ����
	 *                          <br/>500002:�û�������
	 *                          <br/>500003:�û����ڵ����򲻿������۸ò���
	 *                          <br/>500004:��ǰ���ڲ���������
	 *                          <br>500005:Ͷע��ע���ͽ��δͨ��У�� ���쳣�Զ���Ͷעû��
	 *                          <br>500006:����ע���ͽ��ʱ����
	 *                          <br>500007:Ͷע�������޶����
	 *                          <br>500008:�����ļ��е�Ͷע����������ò���ȷ
	 *                          <br>500009:�û��˻��ڵĽ�������Ͷע
	 *                          <br>500010:Ͷע�۳��˻��ʽ����
	 *                          <br>500011:�û�״̬�����������ܱ������ע��
	 *                          <br>500012:׷�Ŷ����˻��ʽ����
	 *                          <br>301005: ��ȡlotteryid�ĵ�ǰ����Ϣʱ���ݿ����
	 *                          <br>300005:��ȡlotteryid�ĵ�ǰ������Ϣʱδ�ҵ�����Ͷע�Ĳ���
	 *                          <br>AccountService�ж���Ľ����쳣
	 *                          
	 */
	public String betSms(long userId, int lotteryId, int playType,
			int betType, int betMultiple,String betCode)
			throws LotteryException;
	/**
	 * 
	 * Title: betSms<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param userId
	 * @param lotteryId
	 * @param betTerm
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betCode
	 * @return
	 * @throws LotteryException
	 */
	public String betSms(long userId, int lotteryId,String betTerm, int playType,
			int betType, int betMultiple,String betCode)
	throws LotteryException;
	
	
	/**
	 * 
	 * Title: betWap<br>
	 * Description: <br>
	 *            wap��ʽ��Ͷע<br>
	 * @param userIdentify �û���ʶ
	 * @param lotteryId ����id
	 * @param playType �淨id
	 * @param betType Ͷע��ʽid
	 * @param betMultiple Ͷע����  ���㵥�ʽ����Ϊ��amount*betMultiple����ֵֻ��ʾ����Ĳ���term���ںţ��ı�������ֵ������Ϊ����������Ϊ0�����򱨴�
	 *                   
	 * @param betNum Ͷע��ע�� ��������ø�ֵҪ����-1
	 * @param amount Ͷע����λΪ��,����׷�ŵ�Ͷע����Ͷע���ݵĽ��(���ܰ���Ͷע�����Ľ��) ,��������ø�ֵҪ����-1
	 * @param term ҪͶע���ں�
	 * @param supplementTerms ����׷����Ϊ׷�������б���ֻ�Ե�ǰ��Ͷע��Ϊnull,������������Ϊ:<�ںţ�����>�����б�������Ϊ����������Ϊ0
	 * @param winStopped ׷��ʱ��Ч���н����Ƿ�ֹͣ׷��, true-ֹͣ false-��ֹͣ
	 * @param betCode ע�룬Ͷע��ע�룬��ʽ�ο� ����״̬��ʽ���Ķ���.xls �����淨Ͷע������������
	 * @return  �ں�#�������#ע��#�ܽ��
	 * @throws LotteryException<br>
	 *                          <br>401001:���ɷ���������ʱ��������
	 *                          <br>401002:���ɷ�������������ʱ��������
	 *                          <br>500001:Ͷע��ʽ����
	 *                          <br>500002:�û�������
	 *                          <br>500003:�û����ڵ����򲻿������۸ò���
	 *                          <br>500004:��ǰ���ڲ���������
	 *                          <br>500005:Ͷע��ע���ͽ��δͨ��У�� ���쳣�Զ���Ͷעû��
	 *                          <br>500006:����ע���ͽ��ʱ����
	 *                          <br>500007:Ͷע�������޶����
	 *                          <br>500008:�����ļ��е�Ͷע����������ò���ȷ
	 *                          <br>500009:�û��˻��ڵĽ�������Ͷע
	 *                          <br>500010:Ͷע�۳��˻��ʽ����
	 *                          <br>500011:�û�״̬�����������ܱ������ע��
	 *                          <br>500012:׷�Ŷ����˻��ʽ����
	 *                          <br>AccountService�ж���Ľ����쳣
	 */
	public String betWap(String userIdentify, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;
	/**
	 * 
	 * Title: betWap<br>
	 * Description: <br>
	 *            wap��ʽ��Ͷע<br>
	 * @param userId �û��˻�
	 * @param lotteryId ����id
	 * @param playType �淨id
	 * @param betType Ͷע��ʽid
	 * @param betMultiple Ͷע����  ���㵥�ʽ����Ϊ��amount*betMultiple����ֵֻ��ʾ����Ĳ���term���ںţ��ı�������ֵ������Ϊ����������Ϊ0�����򱨴�
	 *                   
	 * @param betNum Ͷע��ע�� ��������ø�ֵҪ����-1
	 * @param amount Ͷע����λΪ��,����׷�ŵ�Ͷע����Ͷע���ݵĽ��(���ܰ���Ͷע�����Ľ��) ,��������ø�ֵҪ����-1
	 * @param term ҪͶע���ں�
	 * @param supplementTerms ����׷����Ϊ׷�������б���ֻ�Ե�ǰ��Ͷע��Ϊnull,������������Ϊ:<�ںţ�����>�����б�������Ϊ����������Ϊ0
	 * @param winStopped ׷��ʱ��Ч���н����Ƿ�ֹͣ׷��, true-ֹͣ false-��ֹͣ
	 * @param betCode ע�룬Ͷע��ע�룬��ʽ�ο� ����״̬��ʽ���Ķ���.xls �����淨Ͷע������������
	 * @return  �ں�#�������#ע��#�ܽ��
	 * @throws LotteryException
	 */
	public String betWap(long userId, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;
	
	/**
	 * 
	 * Title: betClient<br>
	 * Description: <br>
	 *              <br>�ͻ���Ͷע�ӿ�
	 * @param userId
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betNum
	 * @param amount
	 * @param term
	 * @param supplementTerms
	 * @param winStopped
	 * @param betCode
	 * @return
	 * @throws LotteryException
	 */
	public String betClient(long userId, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;
	/**
	 * 
	 * Title: coopBetCreate<br>
	 * Description: <br>������Ͷע
	 *              <br>
	 * @param userId ����id
	 * @param lotteryId ����
	 * @param playType �淨
	 * @param betType Ͷע��ʽ
	 * @param betMultiple Ͷע����
	 * @param betNum Ͷעע�� ��������ø�ֵҪ����-1
	 * @param amount Ͷע����λΪ��,��Ͷע���ݵĽ��(���ܰ���Ͷע�����Ľ��) ,��������ø�ֵҪ����-1
	 * @param term Ͷע����
	 * @param betCode Ͷע��
	 * @param planopentype ����������ʽ 0-���� 1-����󹫿��� ? 2-��ֹ�󹫿�
	 * @param totalunit �ܷ���
	 * @param unitprice ÿ�ݵ���
	 * @param selfBuyNum �Լ��Ϲ�����
	 * @param unitbuyself ���׷���
	 * @param commisionpercent Ӷ������������н����Ӷ������������ã���д10��Ϊ 10%
	 * @param plansource Ͷע�����Ǵ��Ǹ��������ģ�0-WEB��1-SMS��2-WAP
	 * @param plantitle �������� �ɿ�
	 * @param plandescription �������� �ɿ�
	 * @return
	 * @throws LotteryException
	 */
	public String coopBetCreate(long userId, int lotteryId, int playType, int betType,
			int betMultiple,long betNum, long amount,String term,String betCode,int planOpenType,
			int totalUnit,int unitPrice,int selfBuyNum,int unitBuySelf,int commisionPercent,
			int planSource,String planTitle,String planDescription) throws LotteryException;
	/**
	 * 
	 * Title: coopBetJoin<br>
	 * Description: <br>
	 *              <br>����������
	 * @param userId ����ID
	 * @param planId ���뷽�����
	 * @param cpUnit �Ϲ�����
	 * @param amount �Ϲ�����λ��
	 * @return
	 * @throws LotteryException
	 */
	public String coopBetJoin(long userId,String planId,int cpUnit,int amount) throws LotteryException;
	/**
	 * 
	 * Title: revocateJionOrder<br>
	 * Description: <br>
	 *              <br>�����˳����Լ��Ϲ��ķݶ�
	 * @param userId �û�id
	 * @param coopInfoId ������Ϣid
	 * @return Ҫ�����Ĳ�����Ϣid
	 * @throws LotteryException
	 */
	public String revocateJionOrder(long userId,String coopInfoId) throws LotteryException;
	/**
	 * 
	 * Title: revocateCreatePlan<br>
	 * Description: <br>
	 *              <br>�����ķ����˳����Լ������ķ���
	 * @param userId
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public String revocateCreatePlan(long userId,String planId) throws LotteryException;
	/**
	 * 
	 * Title: revocatePlanBySys<br>
	 * Description: <br>
	 *              <br>�����ֹ��ϵͳ�Ժ��򷽰����������������ñ���ʹ������Ա
	 *              <br>���ó���һ��Ҫ��֤�÷����ѽ����˿��Գ����������ɳ�Ʊ������ʱ��
	 * @param planId ������� 
	 * @throws LotteryException
	 */
	public void dealOnePlanBySys(String planId) throws LotteryException;
	/**
	 * 
	 * Title: daiGouErrOrderDeal<br>
	 * Description: <br>
	 *              <br>�����Ĵ��󶩵��˿�
	 * @param orderId
	 * @return ����#����#������#�˿���#������
	 * @throws LotteryException
	 */
	public String daiGouErrOrderDeal(String orderId) throws LotteryException;
	/**
	 * 
	 * Title: heMaiErrOrderDeal<br>
	 * Description: <br>
	 *              <br>������󶩵��˿��
	 * @param orderId
	 * @return ����#����#������#�˿���#�˿���ϸ#������
	 * @throws LotteryException
	 */
	public String heMaiErrOrderDeal(String orderId) throws LotteryException;

}
