package com.success.lottery.term.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.web.formbean.RaceBean;

public interface ToHtmlInterf {
	/**
	 * ����͸���Ʒ�������html�ķ���
	 * @param list ����͸���Ѿ���������������Ϣ�б�
	 * @param name ��������
	 * @param enc  �����ʽ
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	
	/**
	 * ���ǲ����Ʒ�������html�ķ���
	 * @param list ���ǲʵ��Ѿ���������������Ϣ�б�
	 * @param name ��������
	 * @param enc  �����ʽ
	 * @return
	 * @throws LotteryException
	 */
	public boolean qxcfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	/**
	 * ���������Ʒ�������html�ķ���
	 * @param list ���������Ѿ���������������Ϣ�б�
	 * @param name ��������
	 * @param enc  �����ʽ
	 * @return
	 * @throws LotteryException
	 */
	public boolean plsfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	/**
	 * ���������Ʒ�������html�ķ���
	 * @param list ��������Ѿ���������������Ϣ�б�
	 * @param name ��������
	 * @param enc  �����ʽ
	 * @return
	 * @throws LotteryException
	 */
	public boolean plwfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	
	/**
	 * ����͸����ĳһ����ϸ��Ϣ��ѯ����html�ķ���
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltkjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel)throws LotteryException;
	/**
	 * ���ǲʿ���ĳһ����ϸ��Ϣ��ѯ����html�ķ���
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean qxckjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel)throws LotteryException;
	/**
	 * ʤ���ʿ���ĳһ����ϸ��Ϣ��ѯ����html�ķ���
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean zckjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel,List<RaceBean> raceList)throws LotteryException;
	
	/**
	 * ����������ĳһ����ϸ��Ϣ��ѯ����html�ķ���
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean plskjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel)throws LotteryException;
	
	/**
	 * �����忪��ĳһ����ϸ��Ϣ��ѯ����html�ķ���
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean plwkjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel)throws LotteryException;
	
	/**
	 * ����͸������ѯ����ҳ
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltlistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	/**
	 * ���ǲʿ�����ѯ����ҳ
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean qxclistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	
	/**
	 * ������������ѯ����ҳ
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean plslistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	/**
	 * �����忪����ѯ����ҳ
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean plwlistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	/**
	 * ʤ���ʿ�����ѯ����ҳ
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean zclistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	
	/**
	 * ������ѯ��ҳ
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean kjlistfileSource(String name, String enc, String url,
			Map<Integer,LotteryTermModel> termMap)throws LotteryException;
	
	/**
	 * ���ֲʺ���ʵ������б�
	 * @param termName
	 * @param enc
	 * @param url
	 * @param numlist
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltseletfileSource(String termName, String enc, String url,List<String> numlist)throws LotteryException;
	/**
	 * ��ҳ���������
	 * @param name
	 * @param enc
	 * @param url
	 * @param termMap
	 * @return
	 * @throws LotteryException
	 */
	public boolean kjlistfileSourceOnIndex(String name, String enc, String url,Map<Integer,LotteryTermModel> termMap)
	throws LotteryException;
	
	/**
	 * ����͸���ʴ�����̬ҳ��
	 * @param currentTerm
	 * @param historyTermList
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltzjkaijianSource(String name, String enc, String url,LotteryTermModel currentTerm,List<LotteryTermModel> historyTermList,List<String[]> WinResultList)throws LotteryException;
	
	/**
	 * ���ǲʹ��ʴ�����̬ҳ��
	 * @param currentTerm
	 * @param historyTermList
	 * @return
	 * @throws LotteryException
	 */
	public boolean qxczjkaijianSource(String name, String enc, String url,LotteryTermModel currentTerm,List<LotteryTermModel> historyTermList,List<String[]> WinResultList)throws LotteryException;
	
	/**
	 * ���������ʴ�����̬ҳ��
	 * @param currentTerm
	 * @param historyTermList
	 * @return
	 * @throws LotteryException
	 */
	public boolean plszjkaijianSource(String name, String enc, String url,LotteryTermModel currentTerm,List<LotteryTermModel> historyTermList)throws LotteryException;
	/**
	 * �����幺�ʴ�����̬ҳ��
	 * @param currentTerm
	 * @param historyTermList
	 * @return
	 * @throws LotteryException
	 */
	public boolean plwzjkaijianSource(String name, String enc, String url,LotteryTermModel currentTerm,List<LotteryTermModel> historyTermList)throws LotteryException;
	
	/**
	 * 
	 * @param name �ļ�����
	 * @param enc  �����ʽ
	 * @param url  ����·��
	 * @param list ��ʷ�ڼ��� 
	 * @return
	 * @throws LotteryException
	 */
	public boolean jq4listfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)
			throws LotteryException;
	
	/**
	 * �ĳ����򿪽�ĳһ����ϸ��Ϣ��ѯ����html�ķ���
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean jq4kjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,List<RaceBean> raceList)throws LotteryException;
	
	
	/**
	 * 
	 * @param name �ļ�����
	 * @param enc  �����ʽ
	 * @param url  ����·��
	 * @param list ��ʷ�ڼ��� 
	 * @return
	 * @throws LotteryException
	 */
	public boolean zc6listfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)
			throws LotteryException;
	
	/**
	 * ������ȫ����ĳһ����ϸ��Ϣ��ѯ����html�ķ���
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean zc6kjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,List<RaceBean> raceList)throws LotteryException;
	
	/**
	 * ���ֲ������б�
	 * @param enc
	 * @param url
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlcseletfileSource(String enc, String url)throws LotteryException;
	/**
	 * ��ѡ�������Ʒ�������html�ķ���
	 * @param list ���ֲ���ѡ�������Ѿ���������������Ϣ�б�
	 * @param name ��������
	 * @param enc  �����ʽ
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_rxfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	/**
	 * ��ѡǰ��ֱѡ���Ʒ�������html�ķ���
	 * @param list ���ֲ���ѡ�������Ѿ���������������Ϣ�б�
	 * @param name ��������
	 * @param enc  �����ʽ
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_qe_zhifileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	/**
	 * ��ѡǰ��ֱѡ���Ʒ�������html�ķ���
	 * @param list ���ֲ���ѡ�������Ѿ���������������Ϣ�б�
	 * @param name ��������
	 * @param enc  �����ʽ
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_qs_zhifileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	/**
	 * ��ѡǰ����ѡ���Ʒ�������html�ķ���
	 * @param list ���ֲ���ѡ�������Ѿ���������������Ϣ�б�
	 * @param name ��������
	 * @param enc  �����ʽ
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_qe_zufileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	/**
	 * ��ѡǰ����ѡ���Ʒ�������html�ķ���
	 * @param list ���ֲ���ѡ�������Ѿ���������������Ϣ�б�
	 * @param name ��������
	 * @param enc  �����ʽ
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_qs_zufileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;

	/**
	 * ������ѯ��ҳ
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlclistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	
	
	

}
