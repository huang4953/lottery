/**
 * Title: LotteryManagerInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: ���ڹ������ӿڣ��ṩ���ڵĿ�����Ϣ���롢������Ϣ�����롢�޺���Ϣ������
 * @author gaoboqin
 * @date 2010-5-13 ����11:53:00
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.success.lottery.business.domain.BusBetOrderCountDomain;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.domain.BusCoopPlanDomain;
import com.success.lottery.business.domain.BusCpInfoDomain;
import com.success.lottery.business.domain.PrizeUserDomain;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;

/**
 * com.success.lottery.business.service.interf
 * LotteryManagerInterf.java
 * LotteryManagerInterf
 * ���ڹ������ӿڣ��ṩ���ڵĿ�����Ϣ���롢������Ϣ�����롢�޺���Ϣ������
 * @author gaoboqin
 * 2010-5-13 ����11:53:00
 * 
 */

public interface LotteryManagerInterf {
	//LotteryList=1000001,����͸|1000002,���ǲ�|1000003,������|1000004,������|1000005,��Ф��|1300001,ʤ����|1300002,��ѡ�ų�|1300003,6����ȫ��|1300004,4�������|1200001,ʮһ�˶��|1200002,�����˿�
	
	public static final int E_01_CODE = 530001;
	public static final String E_01_DESC = "[1]��������ȷ[2]��";
	
	public static final int E_02_CODE = 531001;
	public static final String E_02_DESC = "�������";
	
	public static final int E_03_CODE = 531002;
	public static final String E_03_DESC = "���뿪����Ϣʱδ���µ���Ӧ�����ݣ�";
	
	public static final int E_04_CODE = 531003;
	public static final String E_04_DESC = "����������Ϣʱδ���µ���Ӧ�����ݣ�";
	
	public static final int E_05_CODE = 531004;
	public static final String E_05_DESC = "�����޺���Ϣʱδ���µ���Ӧ�����ݣ�";
	
	public static final int E_06_CODE = 531005;
	public static final String E_06_DESC = "��ȡ������Ϣ�б����ݳ���";
	
	
	/**
	 * 
	 * Title: inputSuperWinInfo<br>
	 * Description: <br>
	 * ����͸������Ϣ����<br>
	 * ����Ĳ���������Ϊ��<br>
	 * @param termNo
	 *            ����
	 * @param lotteryResult
	 *            ������� ����Ϊ<ǰȥ|����,<���뼯��>>,����ǰ����1��ʾ��������2��ʾ����λһ�����룬������'0',ǰ�����뷶ΧΪ:01-35,�������뷶ΧΪ01-12,���뼯����˳��Ҫ��
	 * @param salesVolume
	 *            ��������
	 * @param jackpot
	 *            �ۼƽ���
	 * @param winResult
	 *            ������ ����λΪԪ ������˼Ϊ��<һ�Ƚ����˵Ƚ�(������1-8��ʾ),<������(A)��׷��(B)��,[ע��,���]>>,8�Ƚ���׷�����8�Ƚ��ɲ���д׷�ӵļ���Ԫ��
	 * @return int �ɹ����µļ�¼����
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputSuperWinInfo(String termNo,
			TreeMap<String,ArrayList<String>> lotteryResult, String salesVolume,
			String jackpot, TreeMap<String,TreeMap<String,String[]>> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputhappyZodiacWinInfo<br>
	 * Description: <br>
	 * ��Ф�ֿ�����Ϣ����<br>
	 * ����Ĳ���������Ϊ��<br>
	 * 
	 * @param termNo
	 *            ����
	 * @param lotteryResult
	 *            ������� ����Ϊ<����>,Ӧ�ð���2�����룬��λһ�����룬������0,���뷶ΧΪ:01-12����˳��Ҫ��
	 * @param salesVolume
	 *            ��������
	 * @param winResult
	 *            ������ ����λΪԪ ����Ϊ <A(ע��)B(���)��ʶ,ֵ>,��:<A,10> <B,200>
	 * @return int �ɹ����µļ�¼����
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputHappyZodiacWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			Map<String,String> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputSuperAndHappyWinInfo<br>
	 * Description: <br>
	 *              <br>����͸����Ф�ֿ�����Ϣ���룬�÷�����ͬʱ�������͸����Ч�ֵĿ�����Ϣ��ʧ��ʧ�ܻ�һ��ع�
	 * @param termNo
	 * @param lotteryResultS
	 * @param salesVolumeS
	 * @param jackpotS
	 * @param winResultS
	 * @param lotteryResultH
	 * @param salesVolumeH
	 * @param winResultH
	 * @return int
	 * @throws LotteryException
	 */
	public int inputSuperAndHappyWinInfo(String termNo,
			TreeMap<String,ArrayList<String>> lotteryResultS, String salesVolumeS,
			String jackpotS, TreeMap<String,TreeMap<String,String[]>> winResultS,
			ArrayList<String> lotteryResultH, String salesVolumeH,
			Map<String,String> winResultH) throws LotteryException;
	/**
	 * 
	 * Title: inputSevenColorWinInfo<br>
	 * Description: <br>
	 * ���ǲʿ����������<br>
	 * 
	 * @param termNo
	 *            ����
	 * @param lotteryResult
	 *            ������� ����Ϊ��<λ�����(1-7),����>,����˳�����7��λ�õ��н����룬�н����뷶ΧΪ:0-9
	 * @param salesVolume
	 *            ��������
	 * @param jackpot
	 *            �ۼƽ���
	 * @param winResult
	 *            ������ �����ֱ�Ϊ:<һ�Ƚ������Ƚ�(������1-6��ʶ),[ע��,���]>
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputSevenColorWinInfo(String termNo,
			Map<String,String> lotteryResult,String salesVolume,
			String jackpot, Map<String,String[]> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputArrangeThreeWinInfo<br>
	 * Description: <br>
	 * ����3������Ϣ����<br>
	 * 
	 * @param termNo
	 *            ����
	 * @param lotteryResult
	 *            ������� ����Ϊ<λ��(1-3),����>��λ�ñ�ʶ��1(��λ)��2(ʮλ),3(��λ),Ӧ�ð���3������,���뷶ΧΪ:0-9
	 * @param salesVolume
	 *            ��������
	 * @param winResult
	 *            ������ �����ֱ�Ϊ<ֱѡ��������������������(�ֱ���1,2,3����),[ע��,���]>�����û����3����6��ü���Ԫ�ز�����
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputArrangeThreeWinInfo(String termNo,
			Map<String,String> lotteryResult,String salesVolume,
			Map<String,String[]> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputArrangeFiveWinInfo<br>
	 * Description: <br>
	 * ����5������Ϣ����<br>
	 * 
	 * @param termNo
	 *            ����
	 * @param lotteryResult
	 *            ������� ������� ����Ϊ<λ��(1-5),����>��λ�ñ�ʶ��1-5�Ӹ�λ����λ,Ӧ�ð���5������,���뷶ΧΪ:0-9
	 * @param salesVolume
	 *            ��������
	 * @param winResult
	 *            ������ ����λΪԪ ����Ϊ <A(ע��)B(���)��ʶ,ֵ>,��:<A,10> <B,200>
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputArrangeFiveWinInfo(String termNo,
			Map<String,String> lotteryResult,String salesVolume,
			Map<String,String> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputArrange3and5WinInfo<br>
	 * Description: <br>
	 *              <br>����3������5������Ϣ���룬�÷�����ͬʱ��������3������5�Ŀ�����Ϣ��ʧ�ܻ�һ��ع�
	 * @param termNo
	 * @param lotteryResult3
	 * @param salesVolume3
	 * @param winResult3
	 * @param lotteryResult5
	 * @param salesVolume5
	 * @param winResult5
	 * @return int
	 * @throws LotteryException
	 */
	public int inputArrange3and5WinInfo(String termNo,
			Map<String,String> lotteryResult3,String salesVolume3,
			Map<String,String[]> winResult3,
			Map<String,String> lotteryResult5,String salesVolume5,
			Map<String,String> winResult5) throws LotteryException;
	/**
	 * 
	 * Title: inputWinOrFailWinInfo<br>
	 * Description: <br>
	 * ʤ���ʿ�����Ϣ����<br>
	 * 
	 * @param termNo
	 *            ����
	 * @param lotteryResult
	 *            ������� ����Ϊ<����>��Ӧ�ð���14�����룬��˳��Ҫ�� ���뷶ΧΪ0,1,3,9
	 * @param salesVolume
	 *            ��������
	 * @param jackpot
	 *            �ۼƽ���
	 * @param winResult
	 *            ������ ����Ϊ:<һ�Ƚ�(1)���Ƚ�(2),[ע��,���]>
	 * 
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputWinOrFailWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, TreeMap<String,String[]> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputArbitryNineWinInfo<br>
	 * Description: <br>
	 * ��ѡ��9������Ϣ����<br>
	 * 
	 * @param termNo
	 *            ����
	 * @param lotteryResult
	 *            ������� ������� ����Ϊ<����>��Ӧ�ð���14�����룬��˳��Ҫ�� ���뷶ΧΪ0,1,3,9
	 * @param salesVolume
	 *            ��������
	 * @param jackpot
	 *            �ۼƽ���
	 * @param winResult
	 *            ������ ����λΪԪ ����Ϊ <A(ע��)B(���)��ʶ,ֵ>,��:<A,10> <B,200>
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputArbitryNineWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, Map<String,String> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputWinAndNineWinInfo<br>
	 * Description: <br>
	 *              <br>ʤ���ʺ���ѡ9������Ϣ����
	 * @param termNo
	 * @param lotteryResultW
	 * @param salesVolumeW
	 * @param jackpotW
	 * @param winResultW
	 * @param lotteryResult9
	 * @param salesVolume9
	 * @param jackpot9
	 * @param winResult9
	 * @return int
	 * @throws LotteryException
	 */
	public int inputWinAndNineWinInfo(String termNo,
			ArrayList<String> lotteryResultW, String salesVolumeW,
			String jackpotW, TreeMap<String,String[]> winResultW,
			ArrayList<String> lotteryResult9, String salesVolume9,
			String jackpot9, Map<String,String> winResult9) throws LotteryException;
	
	/**
	 * û��ʵ�� 
	 * Title: inputHalfSixWinInfo<br>
	 * Description: <br>
	 * 6����ȫ��������Ϣ����<br>
	 * 
	 * @param termNo
	 * @param lotteryResult
	 * @param lotteryResultPlus
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputHalfSixWinInfo(String termNo,
			ArrayList<String> lotteryResult,String salesVolume,
			String jackpot, Map<String,String> winResult) throws LotteryException;
	/**
	 * û��ʵ��
	 * Title: inputBallFourWinInfo<br>
	 * Description: <br>
	 * 4������ʿ�����Ϣ����<br>
	 * 
	 * @param termNo
	 * @param lotteryResult
	 * @param salesVolume
	 * @param jackpot
	 * @param winResult
	 * @return int
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputBallFourWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, Map<String,String> winResult) throws LotteryException;
	
	/**
	 * 
	 * Title: inputJxDlcWinInfo<br>
	 * Description: <br>
	 * �������ֲʿ�����Ϣ����<br>
	 * 
	 * @param termNo ����
	 * @param lotteryResult �������
	 * @param salesVolume ��������
	 * @param jackpot �ۼƽ���
	 * @param winResult ������
	 * @return int �ɹ����µļ�¼����
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputJxDlcWinInfo(String termNo,
			ArrayList<String> lotteryResult, String salesVolume,
			String jackpot, LinkedHashMap<String,String[]> winResult) throws LotteryException;
	/**
	 * 
	 * Title: inputJxDlcWinInfo<br>
	 * Description: <br>
	 *              <br>�������ֲʿ�����Ϣ���£����������ʽ����λһ�����룬��������룬������λ��0��Ҫ�Լ���֤�����ʽ����ȷ
	 * @param termNo
	 * @param lotteryResult
	 * @return
	 * @throws LotteryException
	 */
	public int inputJxDlcWinInfo(String termNo,String lotteryResult) throws LotteryException;
	
	/**
	 * 
	 * Title: inputWinInfo<br>
	 * Description: <br>
	 *            ���ַ����ķ�ʽ���뿪����Ϣ<br>
	 * @param lotteryId ����
	 * @param termNo ����
	 * @param lotteryResult �������
	 * @param lotteryResultPlus �������˳��
	 * @param salesVolume ��������
	 * @param jackpot �ۼƽ���
	 * @param winResult ������
	 * @return int  ���³ɹ��ļ�¼��
	 * @throws LotteryException<br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>LotteryManagerInterf.E_02_CODE:���뿪����Ϣ�������
	 *                          <br>LotteryManagerInterf.E_03_CODE:���뿪����Ϣʱδ���µ���Ӧ�Ŀ�������
	 *                          <br>
	 */
	public int inputWinInfo(int lotteryId,String termNo,
			String lotteryResult,String lotteryResultPlus,String salesVolume, String jackpot,
			String winResult) throws LotteryException;
	
	/**
	 * 
	 * Title: inputSaleInfo<br>
	 * Description: <br>
	 *              <br>������Ϣ����
	 *              <br>ʱ���ʽΪ yyyy-MM-dd HH:mm:ss
	 *              <br>ֻ����ʿ�������������Ϣ������������ֵ��ý������쳣
	 *              <br>ֻ���Զ�δ���ڲ�������״̬Ϊδ������ʱ��Ĳ��ڿ�������������Ϣ
	 * @param lotteryId ���� ����Ϊ��
	 * @param termNo �ں� ����Ϊ��
	 * @param startTime ϵͳ����ʱ�� ����Ϊ��
	 * @param startTime2 �ٷ�����ʱ�� ����Ϊ��
	 * @param deadLine ϵͳֹ��ʱ�� ����Ϊ��
	 * @param deadLine3 �ٷ�ֹ��ʱ�� ����Ϊ��
	 * @param deadLine2 ����ֹ��ʱ�� 
	 * @param winLine ϵͳ����ʱ�� 
	 * @param winLine2 �ٷ�����ʱ��
	 * @param saleInfo ������Ϣ���� 
	 *            Map&lt;Integer,Map&lt;String,String&gt;&gt;<br>
	 *            Map&lt;����,Map&lt;key,value&gt;&gt;<br>
	 *            key����Ϊ��<br>
	 *            A ��ʾ����
	 *            B ��ʾ�ͳ�
	 *            C ��ʾ����
	 *            D ��ʾ����ʱ��
	 * @return int 
	 * @throws LotteryException
	 */
	public int inputSaleInfo(int lotteryId, String termNo, String startTime,
			String startTime2, String deadLine, String deadLine3,
			String deadLine2, String winLine, String winLine2, Map<Integer, Map<String,String>> saleInfo)
			throws LotteryException;
	
	/**
	 * 
	 * Title: inputSaleInfo<br>
	 * Description: <br>
	 *              <br>����ʤ���ʺ���ѡ��9��������Ϣ����������һ������
	 * @param termNo
	 * @param startTime
	 * @param startTime2
	 * @param deadLine
	 * @param deadLine3
	 * @param deadLine2
	 * @param winLine
	 * @param winLine2
	 * @param saleInfo
	 * @return
	 * @throws LotteryException
	 */
	public int inputSaleInfo(String termNo, String startTime,
			String startTime2, String deadLine, String deadLine3,
			String deadLine2, String winLine, String winLine2, Map<Integer, Map<String,String>> saleInfo)
			throws LotteryException;
	
	/**
	 * 
	 * Title: inputTermLimitNumber<br>
	 * Description: <br>
	 *            ������ڵ��޺���Ϣ��ֱ�������ַ�����Ŀǰֻ������3���޺ţ�����δ���ַ���������<br>
	 * @param lotteryId ����
	 * @param termNo ����
	 * @param limitNumber ���ƺ���
	 * @throws LotteryException<br>
	 *                          <br>LotteryManagerInterf.E_01_CODE:��������ȷ
	 *                          <br>otteryManagerInterf.E_05_CODE:�����޺���Ϣʱδ���µ���Ӧ������
	 *                          <br>otteryManagerInterf.E_02_CODE:�������
	 *                          <br>LotteryTermServiceInterf.E_1011_CODE:�����޺���Ϣʱ����
	 */
	public void inputTermLimitNumber(int lotteryId,String termNo,String limitNumber) throws LotteryException;
	
	/**
	 * 
	 * Title: getLotteryList<br>
	 * Description: <br>
	 *              <br>��ȡϵͳ������������õĲ���
	 * @return Map<Integer,String>
	 * @throws LotteryException
	 */
	public Map<Integer,String> getLotteryList() throws LotteryException;
	/**
	 * 
	 * Title: getLotteryLastTerm<br>
	 * Description: <br>
	 *              <br>��ȡ�Ѿ������ĺͿ��Կ����Ĳ����ں��б� 
	 * @param lotteryId
	 * @return List<String>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:��ȡ������Ϣ����
	 */
	public List<String> getLotteryLastTerm(int lotteryId,int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: getLotteryCurrentAndNextTerm<br>
	 * Description: <br>
	 *              <br>��ȡ��ǰ�ں�δ���ڵĲ����б�
	 * @param lotteryId
	 * @param limitNum
	 * @return List<String>
	 * @throws LotteryException
	 */
	public List<String> getLotteryCurrentAndNextTerm(int lotteryId,int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: getLotteryLastTermInfo<br>
	 * Description: <br>
	 *              <br>��ȡ�Ѿ������ĺͿ��Կ����Ĳ����ں��б� 
	 * @param lotteryId ����id ��Ϊnull���������еĲ���
	 * @param begin_term ��ʼ�ں� ��Ϊnull��� ����Ϊ������
	 * @param end_term �����ں� ��Ϊnull��� ����Ϊ������
	 * @param limitNum �������� ��Ϊ0����Ϊ����������
	 * @return List<LotteryTermModel>
	 * @throws LotteryException <br>LotteryTermServiceInterf.E_06_CODE:��ȡ������Ϣ�б����ݳ���
	 */
	public List<LotteryTermModel> getLotteryLastTermInfo(String lotteryId,String begin_term,String end_term,int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: getLotteryNextTermInfo<br>
	 * Description: <br>
	 *              <br>��ѯ��ǰ�ڵĺ�δ���ڵĲ�����Ϣ�������޸�������Ϣ
	 * @param lotteryId
	 * @param begin_term
	 * @param end_term
	 * @param limitNum
	 * @return  List<LotteryTermModel>
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> getLotteryNextTermInfo(String lotteryId,String begin_term,String end_term,int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: getLotteryLastWinInfo<br>
	 * Description: <br>
	 *              <br>��ȡ���ڵ�����Ŀ�����Ϣ 
	 * @param lotteryId ����
	 * @return LotteryTermModel
	 * @throws LotteryException
	 */
	public LotteryTermModel getLotteryLastWinInfo(int lotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: getbetOrderInfo<br>
	 * Description: <br>
	 *              <br>��̨��ѯͶע�����б�
	 * @param accountId
	 * @param userName
	 * @param lotteryId
	 * @param termNo
	 * @param betType
	 * @param betStatus
	 * @param winStatus
	 * @param begin_time
	 * @param end_time
	 * @param pageIndex
	 * @param perPageNumber
	 * @return List<BusBetOrderDomain>
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getbetOrderInfoS(String accountId,
			String userName, String lotteryId, String termNo, String planSource,
			String betStatus, String winStatus, String begin_time,
			String end_time, int pageIndex, int perPageNumber)
			throws LotteryException;
	/**
	 * 
	 * Title: getbetOrderInfosCount<br>
	 * Description: <br>
	 *              <br>��̨��ѯͶע�����б�������
	 * @param accountId
	 * @param userName
	 * @param lotteryId
	 * @param termNo
	 * @param planSource
	 * @param betStatus
	 * @param winStatus
	 * @param begin_time
	 * @param end_time
	 * @return int
	 * @throws LotteryException
	 */
	public int getbetOrderInfosCount(String accountId,
			String userName, String lotteryId, String termNo, String planSource,
			String betStatus, String winStatus, String begin_time,
			String end_time) throws LotteryException;
	/**
	 * 
	 * Title: getCanCashOrderInfoS<br>
	 * Description: <br>
	 *              <br>��ѯ���Զҽ��Ķ����б�,�÷��������޸ģ����Բ����Ʊ�ɹ�֮ǰ�����ж���
	 * @param lotteryId
	 * @param termNo
	 * @param pageIndex
	 * @param perPageNumber
	 * @return List<BusBetOrderDomain>
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getCanCashOrderInfoS(String lotteryId,String termNo,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCanCashOrderInfosCount<br>
	 * Description: <br>
	 *              <br>��ѯ���Զҽ��Ķ����б�ļ�¼��,,�÷��������޸ģ����Բ����Ʊ�ɹ�֮ǰ�����ж���
	 * @param lotteryId
	 * @param termNo
	 * @return int
	 * @throws LotteryException
	 */
	public int getCanCashOrderInfosCount(String lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: getCanDispatchOrderInfoS<br>
	 * Description: <br>
	 *              <br>��ѯ�����ɽ������б�
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getCanDispatchOrderInfoS(String lotteryId,String termNo,String userIdentify,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCanDispatchOrderInfosCount<br>
	 * Description: <br>
	 *              <br>��ѯ�����ɽ������б�����
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @return Map<String,Long>
	 * @throws LotteryException
	 */
	public BusBetOrderCountDomain getCanDispatchOrderInfosCount(String lotteryId,String termNo,String userIdentify) throws LotteryException;
	
	
	/**
	 * 
	 * Title: getOrdersByPlanId<br>
	 * Description: <br>
	 *              <br>���ݷ�����Ų��·����µ����ж����������˲��ڵ���Ϣ,������ʾ��������ϸ��Ϣ
	 * @return List<BusBetOrderDomain>
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getOrdersByPlanId(String planId) throws LotteryException;
	
	/**
	 * 
	 * Title: getPrizeUser<br>
	 * Description: <br>
	 *              <br>��ѯ�û������н�
	 * @param limitNum ������������<0����Ϊ������
	 * @return �н����û���Ϣ
	 * @throws LotteryException
	 */
	public List<PrizeUserDomain> getPrizeUser(int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: getPrizeOrder<br>
	 * Description: <br>
	 *              <br>��ҳ��ѯ�н�����
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<PrizeUserDomain> getPrizeUserOrder(int limitNum) throws LotteryException;
	
	/**
	 * Title: inputUpdatefailOrder<br>
	 * Description: <br>
	 *              <br>�����Ʊʧ�ܶ���
	 * @param orderId
	 * @param winStatus
	 * @param wincode
	 * @return
	 * @throws LotteryException
	 */
	public int inputUpdatefailOrder(String orderId,int winStatus,String wincode)throws LotteryException;
	/**
	 * 
	 * Title: getNeedLimitLotteryList<br>
	 * Description: <br>
	 *              <br>��ȡ��Ҫ�޺ŵĲ���
	 * @return
	 * @throws LotteryException
	 */
	public Map<Integer,String> getNeedLimitLotteryList() throws LotteryException;
	
	/**
	 * 
	 * Title: getCoopCanDispatch<br>
	 * Description: <br>
	 *              <br>��ѯ�����ɽ������б�
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCpInfoDomain> getCoopCanDispatch(String lotteryId,String termNo,String userIdentify,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanDispatchCount<br>
	 * Description: <br>
	 *              <br>��ѯ�����ɽ������б�����
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @return Map<String,Long>
	 * @throws LotteryException
	 */
	public BusBetOrderCountDomain getCoopCanDispatchCount(String lotteryId,String termNo,String userIdentify) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanJoinOrder<br>
	 * Description: <br>
	 *              <br>��ȡ�������ֵĿɲ������ķ����б�
	 *              <br>ҳ�淽�����ֱ�׼Ϊ��[0,100)100Ԫ����,[100,500),[500,1000),[1000,-1)1000Ԫ����,��λΪԪ,ȫ�������޺������ƶ���-1��ʶ
	 *              <br>ҳ����ɱ�׼:[0,1)���0��[1,5)���1%-5%��[5,100)���5%����
	 *              <br>
	 *              <br>
	 * @param lotteryId ����
	 * @param termNo ���ڣ�Ŀǰֻ���ڲ��ڿ���ʱ������ܲ���
	 * @param userIdentify ���������û���ʶ,����������null
	 * @param planProgress �������� 0-��Ա��1-δ��Ա��-1��ʶȫ����
	 * @param planMoneyDown ����������ޣ����ڵ�������ֵ����������-1��ʶ
	 * @param planMoneyUp ����������� С������ֵ����������-1��ʶ
	 * @param tiChengDown ������ޣ����ڵ������ߣ�
	 * @param tiChengUp ������ޣ�С������
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCoopPlanDomain> getCoopCanJoinOrder(int lotteryId, String termNo,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanJoinOrderCount<br>
	 * Description: <br>
	 *              <br>��ȡ�������ֵĿɲ������ķ���ͳ�ƣ����ڷ�ҳ��Ҫ
	 * @param lotteryId
	 * @param termNo
	 * @param userIdentify
	 * @param planProgress
	 * @param planMoneyDown
	 * @param planMoneyUp
	 * @param tiChengDown
	 * @param tiChengUp
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopCanJoinOrderCount(int lotteryId, String termNo,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp) throws LotteryException;
	
	/**
	 * 
	 * Title: getCoopPlans<br>
	 * Description: <br>
	 *              <br>��̨��ѯ���򷽰��б�
	 *              <br>ҳ�淽�����ֱ�׼Ϊ��[0,100)100Ԫ����,[100,500),[500,1000),[1000,-1)1000Ԫ����,��λΪԪ,ȫ�������޺������ƶ���-1��ʶ
	 *              <br>ҳ����ɱ�׼:[0,1)���0��[1,5)���1%-5%��[5,100)���5%����
	 * @param lotteryId ���� ����0��ʶ���в���
	 * @param termNo ���� ����0��ʶ���в���
	 * @param planId �������
	 * @param userIdentify �û���ʶ
	 * @param planProgress �������� 0-��Ա��1-δ��Ա��-1��ʶȫ����
	 * @param planMoneyDown ����������ޣ����ڵ�������ֵ����������-1��ʶ
	 * @param planMoneyUp ����������� С������ֵ����������-1��ʶ
	 * @param tiChengDown ������ޣ����ڵ�������
	 * @param tiChengUp ������ޣ�С������
	 * @param begin_time �����ύ��ʼʱ��
	 * @param end_time �����ύ����ʱ��
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCoopPlanDomain> getCoopPlans(int lotteryId, String termNo,String planId,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp,String begin_time,
			String end_time,int planStatus,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopPlansCount<br>
	 * Description: <br>
	 *              <br>��̨��ѯ���򷽰�ͳ������
	 * @param lotteryId
	 * @param termNo
	 * @param planId
	 * @param userIdentify
	 * @param planProgress
	 * @param planMoneyDown
	 * @param planMoneyUp
	 * @param tiChengDown
	 * @param tiChengUp
	 * @param begin_time
	 * @param end_time
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopPlansCount(int lotteryId, String termNo,String planId,
			String userIdentify, int planProgress, int planMoneyDown,
			int planMoneyUp, int tiChengDown,int tiChengUp,String begin_time,
			String end_time,int planStatus) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanYuInfos<br>
	 * Description: <br>
	 *              <br>��̨��ѯ�������Ĳ�����Ϣ�б�
	 * @param lotteryId 0 ���в���
	 * @param termNo 0 ���в���
	 * @param planId �������
	 * @param coopInfoId ���붩�����
	 * @param jionUser �����û���ʶ
	 * @param begin_date ���뿪ʼʱ��
	 * @param end_date �������ʱ��
	 * @param coopType �������� -1��ʶȫ��  0-�����Ϲ���1�����Ϲ���2-���𱣵ף�3-����תͶע
	 * @param orderStatus ������Ϣ״̬ -1 ��ʶȫ�� 0-�����У�1-ϵͳ������2-�����˳�����3-�����˳�����4-������Ա����Ͷע��5-���׶��ᣬ6-���׳���,7-�Ѿ��ҽ�,8-���ɽ�
	 * @param winStatus ����״̬ -1��ʶȫ�� 0-δ�ҽ���1-δ�н���2-��С����3-�д�
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCpInfoDomain> getCoopCanYuInfos(String lotteryId,
			String termNo, String planId, String coopInfoId, String jionUser,
			String begin_date, String end_date, String coopType,
			String orderStatus, String winStatus, int pageIndex,
			int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopCanYuInfosCount<br>
	 * Description: <br>
	 *              <br>��̨��ѯ�������Ĳ�����Ϣ��ͳ��
	 * @param lotteryId
	 * @param termNo
	 * @param planId
	 * @param coopInfoId
	 * @param jionUser
	 * @param begin_date
	 * @param end_date
	 * @param coopType
	 * @param orderStatus
	 * @param winStatus
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopCanYuInfosCount(String lotteryId,
			String termNo, String planId, String coopInfoId, String jionUser,
			String begin_date, String end_date, String coopType,
			String orderStatus, String winStatus) throws LotteryException;
	/**
	 * 
	 * Title: getCoopPlanForWeb<br>
	 * Description: <br>
	 *              <br>ǰ̨�˻����Ĳ�ѯ����Ļ��߲���ĺ���
	 *              <br>���صĶ����н���planSource��ʶ�н����,����chaseCount��ʶ˰ǰ�Ľ��𣬵�λ��
	 *              <br>������ʾ�ֶ�:lotteryId,startTerm,planId,planTitle,planTime(����ʱ��),
	 *              unitAmount(�������),totalUnit(�ܷ���),unitBuySelf(���׷���),selledUnit(���Ϲ�),planStatus(����״̬),
	 *              planSource(�н����),chaseCount(����˰ǰ����)
	 *              <br>�Ժ��������(unitBuySelf+selledUnit)*2 > totalUnit,������в��ܼ�"����"����,�������û�г�������������
	 *              
	 *              
	 * @param faQiOrCanYu 0-����1-����
	 * @param lotteryId ���� 0-���в���,�������治�ܰ������ֲ�
	 * @param betTerm ���� 0-���в���
	 * @param userId ����������û�id
	 * @param planStatus ����״̬��-1��ʶȫ����100-�����У�101-��Ʊ�У�102-�ѳ�Ʊ��103-�Ѷҽ���104-���ɽ���105-��Ʊʧ�ܣ�106-�ѳ���
	 * @param winStatus -1��ʶȫ��,0-δ�Խ���1-δ�н���2-��С����3-�д�
	 * @param begin_date ��ʼʱ��
	 * @param end_date ����ʱ��
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCoopPlanDomain> getCoopPlanCreateForWeb(int faQiOrCanYu,
			int lotteryId, String betTerm, int userId, int planStatus,
			int winStatus,String begin_date, String end_date,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopPlanCreateForWebCount<br>
	 * Description: <br>
	 *              <br>ǰ̨�˻����Ĳ�ѯ����ĺ���Ͳ���ĺ���
	 * @param faQiOrCanYu 
	 * @param lotteryId
	 * @param betTerm
	 * @param userId
	 * @param planStatus
	 * @param winStatus
	 * @param begin_date
	 * @param end_date
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopPlanCreateForWebCount(int faQiOrCanYu,int lotteryId, String betTerm, int userId, int planStatus,
			int winStatus,String begin_date, String end_date) throws LotteryException;
	/**
	 * 
	 * Title: getCoopForWebDetail<br>
	 * Description: <br>
	 *              <br>ǰ̨���������е����в�����Ϣ�б�
	 * @param planId �������
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCpInfoDomain> getCoopForWebDetail(String planId,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCoopForWebDetailCount<br>
	 * Description: <br>
	 *              <br>ǰ̨���������е����в�����Ϣ�б�ͳ��
	 * @param planId �������
	 * @return
	 * @throws LotteryException
	 */
	public int getCoopForWebDetailCount(String planId) throws LotteryException;
	/**
	 * 
	 * Title: getCoopSelfForWebDetail<br>
	 * Description: <br>
	 *              <br>ǰ̨�����������Լ�����ĺ��������Ϣ,����Ҫ��ҳ
	 * @param planId
	 * @param userId
	 * @return
	 * @throws LotteryException
	 */
	public List<BusCpInfoDomain> getCoopSelfForWebDetail(String planId,int userId) throws LotteryException;
	/**
	 * 
	 * Title: getNotChuPiaoOrders<br>
	 * Description: <br>
	 *              <br>��̨��ȡ�ٷ�ֹ�ۺ�û���ܲ�Ʊ�Ķ����б�
	 * @param lotteryId 0-ȫ��
	 * @param termNo 0-ȫ��
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getNotChuPiaoOrders(int lotteryId,String termNo,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getNotChuPiaoOrderCount<br>
	 * Description: <br>
	 *              <br>��̨��ȡ�ٷ�ֹ�ۺ�û���ܲ�Ʊ�Ķ���ͳ��
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 */
	public int getNotChuPiaoOrderCount(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: getChuPiaoErrorOrders<br>
	 * Description: <br>
	 *              <br>��̨��ѯ�������ߺ���ĳ�Ʊ����Ķ����б�
	 * @param daiOrHe 0-���� ��1-����
	 * @param lotteryId
	 * @param termNo
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<BusBetOrderDomain> getChuPiaoErrorOrders(int daiOrHe,int lotteryId,String termNo,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getChuPiaoErrorOrderCount<br>
	 * Description: <br>
	 *              <br>��̨��ѯ�������ߺ���ĳ�Ʊ����Ķ���ͳ��
	 * @param daiOrHe
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 */
	public int getChuPiaoErrorOrderCount(int daiOrHe,int lotteryId,String termNo) throws LotteryException;
	
	/**
	 * 
	 * Title: planBetCodeIsShow<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param planId
	 * @param userId ���δ��¼��-1��ʶ
	 * @return
	 * @throws LotteryException
	 */
	public String planBetCodeIsShow(String planId,int userId) throws LotteryException;
	/**
	 * 
	 * Title: getCoopPlanForWebShow<br>
	 * Description: <br>
	 *              <br>��ȡ����ķ�����Ϣ������ǰ̨�ķ�����ϸ��Ϣ��ʾ������������Ϣ�Լ��н���Ϣ
	 *              <br>������ʾ:
	 *              <br>�������ȡareaCode�ֶ�����
	 *              <br>�н����ȡreserve�ֶ�����
	 *              <br>����״̬ȡplanStatus�ֶΣ���LotteryStaticDefine.planStatusForWebHת
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public BusCoopPlanDomain getCoopPlanForWebShow(String planId) throws LotteryException;
	
}
