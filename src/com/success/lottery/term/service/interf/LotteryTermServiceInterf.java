/**
 * Title: LotteryTermServiceInterf.java
 * @Package com.success.lottery.term.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-6 ����04:06:29
 * @version V1.0
 */
package com.success.lottery.term.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;

/**
 * com.success.lottery.term.service.interf
 * LotteryTermServiceInterf.java
 * LotteryTermServiceInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-6 ����04:06:29
 * 
 */

public interface LotteryTermServiceInterf {
	
	public static final int E_0005_CODE = 300005;
	public static final String E_0005_DESC = "δ�ҵ����ֵĵ�ǰ����Ϣ!";
	
	public static final int E_1001_CODE = 301001;
	public static final String E_1001_DESC = "���²���״̬����";
	
	public static final int E_1002_CODE = 301002;
	public static final String E_1002_DESC = "���²���������Ϣ����";
	
	public static final int E_1003_CODE = 301003;
	public static final String E_1003_DESC = "���²��ڿ���״̬����";
	
	public static final int E_1004_CODE = 301004;
	public static final String E_1004_DESC = "��ȡ������Ϣ����";
	
	public static final int E_1005_CODE = 301005;
	public static final String E_1005_DESC = "��ȡ���ڵ�ǰ����Ϣ����";
	
	public static final int E_1006_CODE = 301006;
	public static final String E_1006_DESC = "��ȡ���ֵ�����������Ϣ����";
	
	public static final int E_1007_CODE = 301007;
	public static final String E_1007_DESC = "���²����л�ʱ�䶨������";
	
	public static final int E_1008_CODE = 301008;
	public static final String E_1008_DESC = "��ȡ�����л�ʱ�䶨�����";
	
	public static final int E_1009_CODE = 300009;
	public static final String E_1009_DESC = "���²���������Ϣ��������Ϊ�գ�";
	
	public static final int E_1010_CODE = 301010;
	public static final String E_1010_DESC = "���¿�����Ϣ����";
	
	public static final int E_1011_CODE = 301011;
	public static final String E_1011_DESC = "�����޺���Ϣ����";
	
	public static final int E_1012_CODE = 301012;
	public static final String E_1012_DESC = "��ȡ�޺���Ϣ����";
	
	public static final int GETTERMCOUNTEXCEPTION = 301013;
	public static final String GETTERMCOUNTEXCEPTION_STR = "������ѯ������Ϣ����ʱ�����쳣��";
	
	public static final int GETTERMINFOEXCEPTION = 301014;
	public static final String GETTERMINFOEXCEPTION_STR = "������ѯ������Ϣʱ�����쳣��";

	
	/**
	 * 
	 * Title: updateTermStatus<br>
	 * Description: <br>
	 *            ���²�����Ϣ������״̬<br>
	 *            ����ǰ��ҪУ����Ⱦ�������<br>
	 *            <li>
	 *            <li>
	 * @param lotteryId ����id
	 * @param termNo ��������
	 * @param termStatus ����״̬ 0-Ԥ����,1-��ǰ��,2-��ʷ��
	 * @return int ���½�� 0 ��ʾû�и��µ����� 1..n ��ʾ���µļ�¼��Ŀ
	 * @throws LotteryException 
	 *                         <br>301001 ����ʱ���ݿ���������쳣
	 */
	public int updateTermStatus(int lotteryId,String termNo,int termStatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateTermSalesInfo<br>
	 * Description:<br>
	 *             ���²���������Ϣ<br>
	 *             ����������Ϣ���ϸ���ָ�����ֺ�������������Ϣ��������Ϣֻ����ʲ���<br>
	 *             �÷���ֻ�������ʹ�ã�����ֱ�ӷ���<br>
	 *             ����δ�Բ����ĵ���Ч����У�飬��˸÷�������ֱ�ӵ��á�
	 *             <li>
	 *             <li>
	 * @param termInfo
	 * @return int ���½�� 0 ��ʾû�и��µ����� 1..n ��ʾ���µļ�¼��Ŀ
	 * @throws LotteryException <br>
	 *                           301002,"���²���������Ϣ����"
	 */
	public int updateTermSalesInfo(LotteryTermModel termInfo) throws LotteryException;
	
	/**
	 * 
	 * Title: updateTermWinStatus<br>
	 * Description: <br>
	 *              ���²��ڵĿ���״̬<br>
	 *              ����ǰ��ҪУ����Ⱦ�������<br>
	 *              <li>
	 *              <li>
	 * @param lotteryId ����id
	 * @param termNo �������� 
	 * @param winstatus 
	 *                 <br>����״̬:
	 *                 <br>1:δ���� 2:�ѿ��� 3:���ڶҽ� 4:�ҽ��ɹ� 5:�ҽ�ʧ��
	 *                 <br>6:�ҽ��ɹ�����ʼ����׷�� 7:�����ɽ� 8:�ɽ��ɹ� 9:�ɽ�ʧ��
	 * @return �ɹ����µļ�¼��
	 * @throws LotteryException<br>
	 *                          301003,"���²��ڿ���״̬����"                      
	 */
	public int updateTermWinStatus(int lotteryId,String termNo,int winstatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateTermSaleStatus<br>
	 * Description: <br>
	 *            ���²��ڵ�����״̬<br>
	 * @param lotteryId ����id
	 * @param termNo �������� 
	 * @param saleStatus 
	 *                   <br>����״̬
	 *                   <br>1:�������� 2:ֹͣ���� 3:��ͣ���� 4:δ������ʱ�� 5:�Ѿ�����
	 * @return �����Ƿ�ɹ� true ���³ɹ� false ����ʧ��
	 * @throws LotteryException
	 */
	public boolean updateTermSaleStatus(int lotteryId,String termNo,int saleStatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateTermWinInfo<br>
	 * Description: <br>
	 *            ���²��ڵĿ�����Ϣ<br>
	 *            �÷�����Ҫ�Ĳ�����Ϣ��Ҫֱ���ṩ�������ڷ�������������<br>
	 *            �÷����Բ��ڻ����̶����������ƣ�����״̬Ϊ2(������),����״̬Ϊ2(ֹͣ����),����״̬Ϊ1(δ����)<br>
	 *            �����״̬������״̬��Ϊ5(�Ѿ�����),����״̬��Ϊ2(�Ѿ�����)<br>
	 *            ��һ���������뿪����Ϣ��Ͳ����ٸ�����<br>
	 *            �÷���Ҫ���������Ϊ�� �������׳��쳣,������©��Ϣ����Ϊ��(���û����©)<br>
	 * @param lotteryId ����id
	 * @param termNo ����
	 * @param lotteryResult �������
	 * @param lotteryResultPlus �������˳��
	 * @param salesVolume ��������
	 * @param jackpot �ۼƽ���
	 * @param winResult ������
	 * @param missCount ��©��Ϣ,����ʸ�������Ϊ""����
	 * @return int ����Ӱ��ļ�¼��
	 * @throws LotteryException<br>
	 *                          <br>E_1010_CODE:���¿�����Ϣ����
	 */
	public int updateTermWinInfo(int lotteryId, String termNo,
			String lotteryResult, String lotteryResultPlus, String salesVolume,
			String jackpot, String winResult, String missCount)
			throws LotteryException;
	/**
	 * 
	 * Title: updateTermLimitNumber<br>
	 * Description: <br>
	 *              <br>�����޺���Ϣ����������
	 * @param lotteryId ����
	 * @param termNo ����
	 * @param limitNumber ���ƺ���
	 * @return ����Ӱ��ļ�¼��
	 * @throws LotteryException<br>
	 *                          <br>E_1011_CODE:�����޺���Ϣ����
	 */
	public int updateTermLimitNumber(int lotteryId,String termNo,String limitNumber) throws LotteryException;
	
	/**
	 * 
	 * Title: queryTermLimitNumberInfo<br>
	 * Description: <br>
	 *              ��ȡ���ֵ��޺���Ϣ<br>
	 * @param lotteryId ����id ��Ϊnull���������еĲ���
	 * @param begin_term ��ʼ�ں� ��Ϊnull��� ����Ϊ������
	 * @param end_term �����ں� ��Ϊnull��� ����Ϊ������
	 * @param limitNum �������� ��Ϊ0����Ϊ����������
	 * @return �޺ŵ���Ϣ����
	 * @throws LotteryException<br>
	 *                          <br>E_1012_CODE:��ȡ�޺���Ϣ����
	 */
	public List<LotteryTermModel> queryTermLimitNumberInfo(String lotteryId,String begin_term,String end_term,int limitNum) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryTermInfo<br>
	 * Description: <br>
	 *              ������Ϣ��ѯ<br>
	 *              ���ݲ���id�Ͳ���������ѯָ���Ĳ��������Ϣ�����ڵ���Ϣ����LotteryTerm���ж����������Ϣ<br>
	 *              
	 * @param lotteryId ����id
	 * @param termNo ��������
	 * @return LotteryTermModel 
	 *                        <br>������Ϣ����ģ�ͣ�����lotteryTerm���е���Ϣ�Լ��ں���LotterInfo����
	 *                        <br>�ö���Բ��ڵĿ����������������������Ϣ����©��Ϣ�����˲�֣����ַ������Ϊ��Ӧ�ļ�����ʽ��
	 *                        <br>ע��÷������صĶ�������������Ϣ���ǲ���termNoָ����������Ϣ��û���ڶ���Ļ�ȡ������������Ϣ
	 * @throws LotteryException<br>
	 *                          301004,"��ȡ������Ϣ����"
	 *                          
	 */
	public LotteryTermModel queryTermInfo(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: queryTermInfoNotSplit<br>
	 * Description: <br>
	 *              <br>��ȡ���ڶ��󣬲��Բ��ڶ���������Ĳ��
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 */
	public LotteryTermModel queryTermInfoNotSplit(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: queryHaveWinTermInfo<br>
	 * Description: <br>
	 *            <br>��ȡĳһ�����ֵ��Ѿ���������������Ϣ�б�
	 *            <br>�ö���Բ��ڵĿ����������������������Ϣ����©��Ϣ�����˲�֣����ַ������Ϊ��Ӧ�ļ�����ʽ��
	 * @param lotteryId ����
	 * @param limitNum ��������
	 * @return List<LotteryTermModel>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:��ȡ������Ϣ����
	 */		
	public List<LotteryTermModel> queryHaveWinTermInfo(int lotteryId,int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryAllLastTermInfo<br>
	 * Description: <br>
	 *              <br>��ȡ���в��ֵ�����Ŀ�����Ϣ
	 *              <br>�ö���Բ��ڵĿ����������������������Ϣ����©��Ϣ�����˲�֣����ַ������Ϊ��Ӧ�ļ�����ʽ��
	 * @return List<LotteryTermModel>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:��ȡ������Ϣ����
	 */
	public List<LotteryTermModel> queryAllLastTermInfo() throws LotteryException;
	/**
	 * 
	 * Title: queryCanInputWinTermNo<br>
	 * Description: <br>
	 *            ��ȡ�������뿪����Ϣ�Ĳ����ں��б������ںŴ�С�����˳������<br>
	 * @param lotteryId ����
	 * @return List<String> �ں��б�
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:��ȡ������Ϣ����
	 */
	public List<String> queryCanInputWinTermNo(int lotteryId) throws LotteryException;
	/**
	 * 
	 * Title: queryCanCashPrizeTermNo<br>
	 * Description: <br>
	 *            ��ȡ���Զҽ��ŵĲ����ں��б������ںŴ�С�����˳������<br>
	 * @param lotteryId ����
	 * @return List<String>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:��ȡ������Ϣ����
	 */
	public List<String> queryCanCashPrizeTermNo(int lotteryId) throws LotteryException;
	
	/**
	 * ��ѯָ�����ֵĲ���
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> getTermnos(int lotteryId, int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: queryCanDispatchPrizeTermNo<br>
	 * Description: <br>
	 *            ��ȡ�����ɽ��Ĳ����ں��б������ںŴ�С�����˳������<br>
	 * @param lotteryId ����
	 * @return List<String>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:��ȡ������Ϣ����
	 */
	public List<String> queryCanDispatchPrizeTermNo(int lotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: queryCanSaleTermNos<br>
	 * Description: <br>
	 *              <br>���ݲ���id��ѯ�����۵������б�
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryCanSaleTermNos(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryCanAddTermNo<br>
	 * Description: <br>
	 *              <br>��׷�ŵ��ں�
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryCanAddTermNo(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryCurrentAndNextTermNo<br>
	 * Description: <br>
	 *              <br>��ѯ�����ں�δ���ڵĲ����б�
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryCurrentAndNextTermNo(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryLimitTermNo<br>
	 * Description: <br>
	 *              <br>���ݲ��ֻ�ȡ�޺���Ϣ��Ϊ�պ͵�ǰ�ڵĲ���
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryLimitTermNo(int lotteryId, int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: queryTermnoIsOneOrTwo<br>
	 * Description: <br>
	 *              <br>��ѯ����״̬Ϊ1����2�Ĳ����б�Ŀǰ���ڳ�Ʊ��ϵͳ
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryTermNoIsOneOrTwo(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryHaveWinTermNos<br>
	 * Description: <br>
	 *              <br>��ȡ�Ѿ����뿪����Ϣ�Ĳ����б����ڵ�������
	 * @param lotteryId ����id
	 * @param limitNum �������� ��-1��ʾ������
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryHaveWinTermNos(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryHaveDispathcTermNos<br>
	 * Description: <br>
	 *              <br>��ȡ�Ѿ��ҽ��Ĳ����б����ڵ�������
	 * @param lotteryId ����id
	 * @param limitNum �������� ��-1��ʾ������
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryHaveDispathTermNos(int lotteryId, int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: queryLastTermInfo<br>
	 * Description: <br>
	 *              <br>��ȡָ�����ڵ���һ����Ϣ
	 *              <br>ֻ�ǰ����ݲ������û�ж����������ݵĲ���Լ�
	 * @param lotteryId ����
	 * @param termNo ����
	 * @return LotteryTermModel
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:��ȡ������Ϣ����
	 */
	public LotteryTermModel queryLastTermInfo(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: queryTermNoInfo<br>
	 * Description: <br>
	 *            ���ݲ��ֲ�ѯ���ֵĵ�ǰ������Ϣ������Ϣ��Ҫ���ø��ٻ���<br>
	 *            �÷�������Ķ�����Ӧ�����ܵ��ٰ�����Ϣ<br>
	 *            �÷������صĶ�������©��Ϣ�������������ۼƽ��ء�������������������õ��Ǹò������һ�ڿ�����������ݣ�ʹ��ʱҪע��<br>
	 * @param lotteryId ����id
	 * @return LotteryTermModel
	 * @throws LotteryException<br>
	 *                          301005 ��ȡlotteryid�ĵ�ǰ������Ϣʱ���ݿ����
	 *                          300005 ��ȡlotteryid�ĵ�ǰ������Ϣʱδ�ҵ�������Ϣ
	 */
	public LotteryTermModel queryTermCurrentInfo(int lotteryId) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryTermCurrentInfoBall<br>
	 * Description: <br>
	 *            ���ݲ��ֻ�ȡ���ֵĵ�ǰ����Ϣ�б�����Ϣ����<br>
	 *            �÷������صĶ�������©��Ϣ�������������ۼƽ��ء�������������������õ��Ǹò������һ�ڿ�����������ݣ�ʹ��ʱҪע��<br>
	 * @param lotteryId ����id
	 * @param nextTermNum Ҫ��ȡ������
	 * @return List<LotteryTermModel>
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> queryTermCurrentInfo(int lotteryId,int nextTermNum) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryTermInfoList<br>
	 * Description: <br>
	 *              ��ȡĳһ���ֵ�����������Ϣ�����б���ʽ����<br>
	 *              ��Ҫ�ṩ��ҳ��Ŀǰ��ҳ��û�п��Ǻã���˷�����ԭ���п��ܸı�<br>
	 * @param lotteryId ����id
	 * @param startPageSize ��ʼ����
	 * @param pageSize ÿҳ����
	 * @return ����������Ϣ���б�
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> queryTermInfoList(int lotteryId,int startPageSize,int pageSize) throws LotteryException;
	/**
	 * 
	 * Title: queryLastTermNo<br>
	 * Description: <br>
	 *              <br>���ݲ��ֻ�ȡ�Ѿ������ĺͿ��Կ������ں�
	 * @param lotteryId
	 * @param limitNum 
	 * @return List<String>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:��ȡ������Ϣ����
	 */
	public List<String> queryLastTermNo(int lotteryId,int limitNum) throws LotteryException;
	/**
	 * Title: queryLastTermInfo<br>
	 * Description: <br>
	 *              <br>���ݲ��ֻ�ȡ�Ѿ������ĺͿ��Կ����Ĳ�����Ϣ
	 *              <br>���lotteryIdsΪnull��գ�����ѯ���еĲ���
	 * @param lotteryIds ����id�б� 
	 * @param begin_term ��ʼ�ں� ����Ϊ��
	 * @param end_term �����ں� ����Ϊ��
	 * @param limitNums �����������ϣ�����Ϊ<����id,��������>���������������Ϊ0��С��0����Ϊ������������ ����Ϊ�� ���Ϊ������Ϊ���еĲ��ֶ�����������
	 * @param limitNum �������� �����в�����Ч
	 * @return List<LotteryTermModel>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:��ȡ������Ϣ����
	 */
	public List<LotteryTermModel> queryLastTermInfo(List<Integer> lotteryIds,String begin_term,String end_term,Map<Integer, Integer> limitNums,int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: queryCanUpdateSaleTermInfo<br>
	 * Description: <br>
	 *              <br>��ѯ�����޸�������Ϣ�Ĳ�����Ϣ
	 * @param lotteryIds
	 * @param begin_term
	 * @param end_term
	 * @param limitNums
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> queryCanUpdateSaleTermInfo(List<Integer> lotteryIds,String begin_term,String end_term,Map<Integer, Integer> limitNums,int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryTermLastCashInfo<br>
	 * Description: <br>
	 *              <br>��ȡ�������һ�ڵĿ�����Ϣ
	 *              <br>�Կ����������������������Ϣ����©��Ϣ���˲��
	 * @param lotteryId
	 * @return
	 * @throws LotteryException
	 */
	public LotteryTermModel queryTermLastCashInfo(int lotteryId) throws LotteryException;
	
	/**
	 * ����������ò�����Ϣ�����������ڲ�����Ϣ��ѯ��ҳ
	 * @param lotteryId
	 * 		���ֱ�ţ�Ϊ-1ʱ��ѯ���в���
	 * @param startTerm
	 * 		ָ�����ֱ��ʱ��Ч����ʼ�ں�
	 * @param endTerm
	 * 		ָ�����ֱ��ʱ��Ч����������
	 * @return
	 * 		��ѯ���Ĳ�����Ϣ�����û�в�ѯ����Ϊ0
	 * @throws LotteryException
	 * 		GETTERMCOUNTEXCEPTION
	 */
	public int getTermInfoCount(int lotteryId, String startTerm, String endTerm) throws LotteryException;
	
	/**
	 * ����������ò�����Ϣ�����ڲ�����Ϣ��ѯ 
	 * @param lotteryId
	 * 		���ֱ�ţ�Ϊ-1ʱ��ѯ���в��� 		
	 * @param startTerm
	 * 		ָ�����ֱ��ʱ��Ч����ʼ�ں�
	 * @param endTerm
	 * 		ָ�����ֱ��ʱ��Ч����������
	 * @param pageIndex
	 * 		��ѯ��ҳ��ڼ�ҳ�����ڷ�ҳ��ѯ
	 * @param perPageNumber
	 * 		ÿҳ�ļ�¼��
	 * @return
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> gettermInfo(int lotteryId, String startTerm, String endTerm, int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCanPrintCondition<br>
	 * Description: <br>
	 *              <br>��ȡ�ܴ�ӡ��Ʊ���ڵ�����
	 * @return ���ظ�ʽΪlotteryid=1000001 and betterm='10971') or ��lotteryid=1000002 and betterm = '100972'��
	 * @throws LotteryException
	 */
	public String getCanPrintCondition() throws LotteryException;
	
	/**
	 * �������ڲ�ѯ���ֲ�ĳ��� �Ѿ������Ĳ���
	 * @param date  ��ʽ yyMMdd
	 * @return
	 */
	public List<LotteryTermModel> queryHaveWinTermInfoToDate(String date);
	/**
	 * 
	 * Title: queryDealLine3TermNo<br>
	 * Description: <br>
	 *              <br>���ݲ��ֻ�ȡ�ٷ�ֹ�۵Ĳ���
	 * @param lotteryId
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryDealLine3TermNo(int lotteryId,int limitNum) throws LotteryException;
	
	
}
