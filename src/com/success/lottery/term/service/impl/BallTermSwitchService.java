/**
 * Title: BallTermSwitchService.java
 * @Package com.success.lottery.term.service.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-16 ����01:40:45
 * @version V1.0
 */
package com.success.lottery.term.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.business.service.SysDealCoopPlan;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.dao.impl.LotteryTermSwitchDaoImpl;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.domain.LotteryTermSwitchDomain;
import com.success.lottery.term.service.TermLog;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.term.service.interf.LotteryTermSwitchServiceInterf;

/**
 * com.success.lottery.term.service.impl
 * BallTermSwitchService.java
 * BallTermSwitchService
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-16 ����01:40:45
 * 
 */

public class BallTermSwitchService implements LotteryTermSwitchServiceInterf {
	private static Log logger = LogFactory.getLog(BallTermSwitchService.class);
	private static TermLog log = TermLog.getInstance("BTS");
	private String beginOrEnd = "0";//0��ʾ�л���ʵ�Ԥ����Ϊ��ǰ�ڣ�1��ʾ�л���ʵĵ�ǰ��Ϊ��ʷ��
	private String timeSpaces = "3600";//�л���ǰ��ʱ����λΪ��
	private List<String> lotteryIdList = new ArrayList<String>();
	private LotteryTermServiceInterf lotteryTermService;
	private LotteryTermSwitchDaoImpl termSwitchDao; 
	private static final int OLD_CUR_TERM_STATUS = 1;//��ǰ��ԭʼ�Ĳ���״̬
	private static final int OLD_CUR_SALES_STATUS = 1;//��ǰ��ԭʼ������״̬
	private static final int NEW_CUR_TERM_STATUS = 2;//��ǰ�ڸ��ĵĲ���״̬
	private static final int NEW_CUR_SALES_STATUS = 2;//��ǰ�ڸ��ĵ�����״̬
	
	private static final int OLD_NEXT_TERM_STATUS = 0;//Ԥ����ԭʼ�Ĳ���״̬
	private static final int OLD_NEXT_SALES_STATUS = 4;//��Ԥ����ԭʼ������״̬
	private static final int NEW_NEXT_TERM_STATUS = 1;//��Ԥ���ڸ��ĵĲ���״̬
	private static final int NEW_NEXT_SALES_STATUS = 1;//Ԥ���ڸ��ĵ�����״̬

	/* (�� Javadoc)
	 *Title: termSwitch
	 *Description: 
	 * @throws LotteryException
	 * @see com.success.lottery.term.service.interf.LotteryTermSwitchServiceInterf#termSwitch()
	 */
	public void termSwitch() throws LotteryException {
		if(this.beginOrEnd.equals("0")){
			updateBeforeTermToCur();
		}else if(this.beginOrEnd.equals("1")){
			updateCurTermToHistory();
		}
	}
	/**
	 * 
	 * Title: updateCurTermToHistory<br>
	 * Description: <br>
	 *            ����ǰ���л�Ϊ��ʷ��<br>
	 *            �Ƕ������趨�Ĳ���һ��������������һ��ʧ�ܣ�������һ��ع������е�ָ���Ĳ����л�����ʧ��<br>
	 *            <br>ִ�еĲ���;�����ÿһ������ѭ������
	 *            <br>��1���Ӳ��ڱ��ȡ���ֶ�Ӧ�ĵ�ǰ�ں�
	 *            <br>��2������Ƿ����л���ʱ�䣬ֻҪ��һ������û�е��л�ʱ�䣬�򷽷�return,����ָ���Ĳ��ֶ������л�
	 *            <br>��3��ѭ�����²��ڱ�������ָ�����ֵĵ�ǰ�ڵĲ���״̬������״̬��Ϊ2��ֻҪ��һ������ʧ������������ع�
	 *            <br>��4�������3��û�г�����ѭ�����²����л�ʱ����Ʊ����������۵�״̬����Ϊ1���ɹ���
	 *            <br>��5���������Ĳ������������ָ�����ֵĲ����л�ʱ����Ʊ�����۽���״̬����Ϊ2��ʧ�ܣ�����ʱҪ��ǰ�沽��ִ�еĲ����ع����˲�ִ�еĲ���Ҫ�ύ
	 *            <br>
	 *            <br>�������⣬�����л�ʧ�ܺ����δ���
	 * @throws LotteryException
	 */
	private void updateCurTermToHistory() throws LotteryException{
		Map<Integer,List<String>> lotteryMap = new HashMap<Integer,List<String>>();
        int updateResult = -1;
        boolean isCanTerm = true;
        try {
			for (String lotteryId : this.lotteryIdList) {//ȡ�ò��ֶ�Ӧ�ĵ�ǰ��
				int lotteryIdInt = Integer.parseInt(lotteryId);
				LotteryTermModel curTermInfo = null;
				
				try {
					curTermInfo = this.getLotteryTermService().queryTermCurrentInfo(lotteryIdInt);
					String curTermNo = curTermInfo.getTermNo();
					List<String> infoList = new ArrayList<String>();
					infoList.add(0, curTermNo);
					infoList.add(1, curTermInfo.getDeadLine()==null?"":curTermInfo.getDeadLine().toString());
					lotteryMap.put(lotteryIdInt, infoList);
					
				} catch (LotteryException e) {
					//e.printStackTrace();
					log.logInfo(lotteryIdInt, "-1", "����ǰ���л�Ϊ��ʷ��ʱδ�õ����ֵĵ�ǰ����Ϣ");
					isCanTerm = false;
					throw e;
				}
				
			}
			
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){//����Ƿ��л�ʱ��
				//String defineTime = this.getTermSwitchDao().queryTermSwitchEndTime(entry.getKey(), entry.getValue());//ȡ�ö���Ľ���ʱ��
				String defineTime = entry.getValue().get(1);
				if(!isRunTime(defineTime,this.getTimeSpaces())){
					log.logInfo(entry.getKey(), entry.getValue().get(0), "���ֵ�ǰ���л�Ϊ��ʷ��δ���л�ʱ��["+defineTime+"]");
					isCanTerm = false;
				}
				
			}
			
			if(!isCanTerm){
				return;
			}
			
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){//��ʼʵ�ʵ��л�����
				int lotteryid = entry.getKey();
				String curTermNo = entry.getValue().get(0);
				LotteryTermSwitchDomain curTermSwitch = new LotteryTermSwitchDomain();
				curTermSwitch.setLotteryId(lotteryid);
				curTermSwitch.setCurrentTermNo(curTermNo);
				curTermSwitch.setOld_termStatus(OLD_CUR_TERM_STATUS);
				curTermSwitch.setOld_saleStatus(OLD_CUR_SALES_STATUS);
				curTermSwitch.setTermStatus(NEW_CUR_TERM_STATUS);
				curTermSwitch.setSaleStatus(NEW_CUR_SALES_STATUS);
				updateResult = updateTetmNo(curTermSwitch);
				if (updateResult <= 0) {//
					log.logInfo(lotteryid, curTermNo, E_301101_DESC);
					throw new LotteryException(E_301101_CODE, E_301101_DESC + "["
							+ lotteryid + "]");
				}
				//this.getTermSwitchDao().updateTermSwitchEndLog(lotteryid, curTermNo, 1, "����ǰ���л�Ϊ��ʷ���л��ɹ�");
			}
			
			//�����ڽ���ʱ�ĺ��򷽰�
			try{
				//�˴��Ժ��������������Ҫ�ɸ��ݲ�����������߳�
				Map<Integer, String> lotteryTerms = new HashMap<Integer,String>();
				for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
					lotteryTerms.put(entry.getKey(), entry.getValue().get(0));
				}
				new SysDealCoopPlan(lotteryTerms).start();
			}catch(Exception e){
				e.printStackTrace();
			}
        
			
		} catch (Exception e) {
			//logger.error("�л����ڣ�����ǰ���л�Ϊ��ʷ�� updateCurTermToHistory error:", e);
			/*
			 * �л�����ʧ��Ҫ���¼�¼���״̬,��ʱҪ��ǰ����������
			 */
			if(lotteryMap.isEmpty()){
				for(String lottery : this.lotteryIdList){
					log.logInfo(Integer.parseInt(lottery), "-1", "����ǰ���л�Ϊ��ʷ��ʧ��");
				}
			}
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				//this.getLotteryTermService().updateTermSwitchLog(entry.getKey(), entry.getValue(), 2, "����ǰ���л�Ϊ��ʷ��ʧ��", "1");
				log.logInfo(entry.getKey(), entry.getValue().get(0), "����ǰ���л�Ϊ��ʷ��ʧ��");
			}
			if(e instanceof LotteryException) {
				throw (LotteryException)e;
			}else {
				throw new LotteryException(E_301104_CODE,E_301104_DESC);
			}
			
		}
		
		/*
		 * д��־
		 */
		for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
			log.logInfo(entry.getKey(), entry.getValue().get(0), "����ǰ���л�Ϊ��ʷ���л��ɹ�");
		}
	}
	/**
	 * 
	 * Title: updateBeforeTermToCur<br>
	 * Description: <br>
	 *            ��Ԥ�����л�Ϊ��ǰ��<br>
	 *            <br>��1�����ݲ��ִӲ����л��ǽ����Ʊ�ȡ�����е�Ԥ����Ŀ�ʼʱ�䲻Ϊnull�����ҿ�ʼ����״̬Ϊ0��Ԥ�������Լ���ʼʱ�䣬
	 *            <br>    ��ʱҪע������ĳ�ʱ�Լ�����һ�����̵Ĳ���,�ò��費���ǻ��ͬһ����ͬʱȥ��2�����ϵ�ʱ����ͬ������
	 *            <br>��2������Ƿ��˿������۵�ʱ�� 
	 *            <br>                            ֻҪ��һ������û���ڿ��Ʊ��ж����Ӧ�Ŀ�ʼ����ʱ����return����Ϊ�����еĶ�����ֶ��������л�
	 *            <br>                            ֻҪ��һ������û�е�����ʱ����return����Ϊ�����еĶ�����ֶ��������л�                  
	 *            <br>��3��ѭ�����²��ڱ�������ָ�����ֵ�Ԥ���ڵĲ���״̬��Ϊ1������״̬��Ϊ1��ֻҪ��һ������ʧ������������ع�
	 *            <br>��4�������3��û�г�����ѭ�����²����л�ʱ����Ʊ�����ʼ���۵�״̬����Ϊ1���ɹ���
	 *            <br>
	 *            <br>
	 *            <br>
	 *            <br>
	 * @throws LotteryException
	 */
	private void updateBeforeTermToCur() throws LotteryException{
		List<Map<Integer,String>> lotteryListMap = new ArrayList<Map<Integer,String>>();//�ü����д�����ǿ��Խ����л��Ĳ��ֲ�����Ϣ
		int updateResult = -1;
		boolean isCanTerm = true;
		try {
			/*
			 * ȡ�ö����Ԥ����ʱ�䣬������Ƿ��˿������۵�ʱ��
			 * ֻҪ��һ������û���ڿ��Ʊ��ж����Ӧ�Ŀ�ʼ����ʱ����return����Ϊ�����еĶ�����ֶ��������л�
			 * ֻҪ��һ������û�е�����ʱ����return����Ϊ�����еĶ�����ֶ��������л�
			 */
			for (String lotteryId : this.lotteryIdList){
				int lotteryIdInt = Integer.parseInt(lotteryId);
				List<LotteryTermModel> termSwitchList = this.getTermSwitchDao().queryTermSwitchBeginInfo(lotteryIdInt,3);
				if(termSwitchList == null || termSwitchList.isEmpty()){
					log.logInfo(lotteryIdInt, "-1", "��Ԥ�����л�Ϊ��ǰ��ʱδ�ҵ���Ҫ�л��Ĳ���");
					isCanTerm = false;
				}else{
					isCanTerm = false;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for(LotteryTermModel termSwitch : termSwitchList){
						String defineTime = sdf.format(termSwitch.getStartTime());
						if(!this.isRunTime(defineTime, this.getTimeSpaces())){//�����л�ʱ��
							log.logInfo(lotteryIdInt, termSwitch.getTermNo(), "����Ԥ�����л�Ϊ��ǰ��δ���л�ʱ��["+defineTime+"]");
						}else{
							isCanTerm = true;
							Map<Integer,String> oneCanTerm = new HashMap<Integer,String>();
							oneCanTerm.put(lotteryIdInt, termSwitch.getTermNo());
							lotteryListMap.add(oneCanTerm);
						}
					}
				}
			}
			if(!isCanTerm){
				return;
			}
			/*
			 * ����ʵ�ʵ�Ԥ����ת��ǰ�ڲ���
			 */
			for(Map<Integer,String> oneCanTerm : lotteryListMap){
				Map.Entry<Integer, String> entry = oneCanTerm.entrySet().iterator().next();
				int lotteryid = entry.getKey();
				String curTermNo = entry.getValue();
				LotteryTermSwitchDomain curTermSwitch = new LotteryTermSwitchDomain();
				curTermSwitch.setLotteryId(lotteryid);
				curTermSwitch.setCurrentTermNo(curTermNo);
				curTermSwitch.setOld_termStatus(OLD_NEXT_TERM_STATUS);
				curTermSwitch.setOld_saleStatus(OLD_NEXT_SALES_STATUS);
				curTermSwitch.setTermStatus(NEW_NEXT_TERM_STATUS);
				curTermSwitch.setSaleStatus(NEW_NEXT_SALES_STATUS);
				updateResult = this.updateTetmNo(curTermSwitch);
				if (updateResult <= 0) {//
					log.logInfo(lotteryid, curTermNo, E_301102_DESC);
					throw new LotteryException(E_301102_CODE, E_301102_DESC + "["
							+ lotteryid + "]");
				}
				/*
				 * �����л���¼�������Ҫ������һ��ع�
				 */
				//this.getTermSwitchDao().updateTermSwitchBeforeLog(lotteryid, curTermNo, 1, "��Ԥ�����л�Ϊ��ǰ���л��ɹ�");
			}
			
		} catch (Exception  e) {
			logger.error("�л����ڣ���Ԥ�����л�Ϊ��ǰ�� updateBeforeTermToCur error:", e);
			/*
			 * �л�����ʧ��Ҫ���¼�¼���״̬,��ʱҪ��ǰ����������
			 */
			if(lotteryListMap.isEmpty()){
				for(String lottery : this.lotteryIdList){
					log.logInfo(Integer.parseInt(lottery), "-1", "��Ԥ�����л�Ϊ��ǰ��ʧ��");
				}
			}
			for(Map<Integer,String> oneCanTerm : lotteryListMap){
				Map.Entry<Integer, String> entry = oneCanTerm.entrySet().iterator().next();
				//this.getLotteryTermService().updateTermSwitchLog(entry.getKey(), entry.getValue(), 2, "��Ԥ�����л�Ϊ��ǰ��ʧ��", "0");
				log.logInfo(entry.getKey(), entry.getValue(), "��Ԥ�����л�Ϊ��ǰ��ʧ��");
			}
			if(e instanceof LotteryException) {
				throw (LotteryException)e;
			}else {
				throw new LotteryException(E_301104_CODE,E_301104_DESC);
			}
		}
		/*
		 * д��־
		 */
		for(Map<Integer,String> oneCanTerm : lotteryListMap){
			Map.Entry<Integer, String> entry = oneCanTerm.entrySet().iterator().next();
			log.logInfo(entry.getKey(), entry.getValue(), "��Ԥ�����л�Ϊ��ǰ���л��ɹ�");
		}
	}
	/**
	 * 
	 * Title: updateTetmNo<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param termSwitch
	 * @return int
	 * @throws LotteryException
	 */
	private int updateTetmNo(LotteryTermSwitchDomain termSwitch) throws LotteryException{
		try {
			return this.getTermSwitchDao().updateTermSwitch(termSwitch);
		} catch (Exception e) {
			logger.error("�л����� updateTetmNo error:", e);
			throw new LotteryException(E_301103_CODE,E_301103_DESC);
		}
	}
	/**
	 * 
	 * Title: isRunTime<br>
	 * Description: <br>
	 *            �л�ʱ���飬�������û�ж����л�ʱ��������õ�ʱ�䲻����ȷ�Ľ���ʱ��ת��������false<br>
	 * @param defineTime
	 * @param timeSpace
	 * @return boolean
	 */
	private boolean isRunTime(String defineTime, String timeSpace) {

		try {
			if (defineTime == null || "".equals(defineTime)) {
				return false;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			java.util.Date begin = sdf.parse(systemTime);
			java.util.Date end = sdf.parse(defineTime);
			long between = (end.getTime() - begin.getTime()) / 1000;
			if (java.lang.Math.abs(between) <= Long.parseLong(timeSpace)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}

	public String getBeginOrEnd() {
		return this.beginOrEnd;
	}

	public void setBeginOrEnd(String beginOrEnd) {
		this.beginOrEnd = beginOrEnd;
	}

	public List<String> getLotteryIdList() {
		return this.lotteryIdList;
	}

	public void setLotteryIdList(List<String> lotteryIdList) {
		this.lotteryIdList = lotteryIdList;
	}

	public LotteryTermServiceInterf getLotteryTermService() {
		return this.lotteryTermService;
	}

	public void setLotteryTermService(LotteryTermServiceInterf lotteryTermService) {
		this.lotteryTermService = lotteryTermService;
	}

	public LotteryTermSwitchDaoImpl getTermSwitchDao() {
		return this.termSwitchDao;
	}

	public void setTermSwitchDao(LotteryTermSwitchDaoImpl termSwitchDao) {
		this.termSwitchDao = termSwitchDao;
	}
	public String getTimeSpaces() {
		return this.timeSpaces;
	}
	public void setTimeSpaces(String timeSpaces) {
		this.timeSpaces = timeSpaces;
	}
	public void termSwitchByStartTime() throws LotteryException {
		// TODO �Զ����ɷ������
		
	}
	
	

}
