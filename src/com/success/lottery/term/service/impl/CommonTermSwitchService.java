/**
 * Title: SuperTermSwitchService.java
 * @Package com.success.lottery.term.service.impl
 * Description: (��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-15 ����10:10:45
 * @version V1.0
 */
package com.success.lottery.term.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
 * com.success.lottery.term.service.impl<br>
 * SuperTermSwitchService.java<br>
 * SuperTermSwitchService<br>
 *                      һ��Ĳ��ֲ����л�<br>
 *                      ����ͬʱ�л���ǰ�ں���һ��<br>
 *                      
 * @author gaoboqin
 * 2010-4-15 ����10:10:45
 * 
 */

public class CommonTermSwitchService implements LotteryTermSwitchServiceInterf {
	private static Log logger = LogFactory.getLog(CommonTermSwitchService.class);
	private static TermLog log = TermLog.getInstance("TS");
	private List<String> lotteryIdList = new ArrayList<String>();
	private String isCheckTime = "0";//�Ƿ�ִ��ʱ���飬0Ϊ����飬1Ϊ���
	private String timeSpaces = "3600";//�л���ǰ��ʱ����λΪ��
	private static final int OLD_CUR_TERM_STATUS = 1;//��ǰ��ԭʼ�Ĳ���״̬
	private static final int OLD_CUR_SALES_STATUS = 1;//��ǰ��ԭʼ������״̬
	private static final int OLD_NEXT_TERM_STATUS = 0;//��һ��ԭʼ�Ĳ���״̬
	private static final int OLD_NEXT_SALES_STATUS = 4;//��һ��ԭʼ������״̬
	private static final int NEW_CUR_TERM_STATUS = 2;//��ǰ�ڸ��ĵĲ���״̬
	private static final int NEW_CUR_SALES_STATUS = 2;//��ǰ�ڸ��ĵ�����״̬
	private static final int NEW_NEXT_TERM_STATUS = 1;//��һ�ڸ��ĵĲ���״̬
	private static final int NEW_NEXT_SALES_STATUS = 1;//��һ�ڸ��ĵ�����״̬
	private LotteryTermServiceInterf lotteryTermService;
	private LotteryTermSwitchDaoImpl termSwitchDao; 
	
	/* (�� Javadoc)
	 *Title: termSwitch
	 *Description: 
	 * @throws LotteryException
	 * @see com.success.lottery.term.service.interf.LotteryTermSwitchServiceInterf#termSwitch()
	 */
	public void termSwitch() throws LotteryException {
		Map<Integer,List<String>> lotteryMap = new HashMap<Integer,List<String>>();
		try {
			this.convertLotteryList(this.lotteryIdList, lotteryMap);
			/*
			 * ִ��ʱ��У�飬������ڶ����ִ��ʱ�䷶Χ�ڣ���ֱ���˳�
			 */
			if(this.getIsCheckTime().equals("1")){
				for (Map.Entry<Integer, List<String>> entry : lotteryMap.entrySet()) {
					String defineTime = entry.getValue().get(2);//����Ľ�������ʱ��
					if(!isRunTime(defineTime,this.getTimeSpaces())){
						log.logInfo(entry.getKey(), entry.getValue().get(0), "���ڲ����л�ʱ��");
						return;
					}
				}
			}
			/*
			 * ִ�о���ĸ���״̬����
			 */
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				this.updateTermNo(entry.getKey(), entry.getValue().get(0),
						entry.getValue().get(1), entry.getValue().get(2), entry
								.getValue().get(3));
			}
			
			//���򷽰�����
			if( !lotteryMap.containsKey(1200001)){//��Ƶû�к������ж�һ�£������������߳�
				//�˴��Ժ��������������Ҫ�ɸ��ݲ�����������߳�
				try{
					Map<Integer, String> lotteryTerms = new HashMap<Integer,String>();
					for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
						lotteryTerms.put(entry.getKey(), entry.getValue().get(0));
					}
					new SysDealCoopPlan(lotteryTerms).start();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		} catch (LotteryException e) {
			logger.error("�л����� termSwitch error:", e);
			/*
			 * �л�����ʧ��Ҫ���¼�¼���״̬
			 */
			if(lotteryMap.isEmpty()){
				for(String lottery : this.lotteryIdList){
					log.logInfo(Integer.parseInt(lottery), "-1", "��ǰ���л�ʧ��[û�еõ����ֵĵ�ǰ��]");
				}
			}
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				log.logInfo(entry.getKey(), entry.getValue().get(0), "��ǰ���л�ʧ��");
			}
			throw e;
		}
		
		for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
			log.logInfo(entry.getKey(), entry.getValue().get(0), "����ǰ���л�Ϊ��ʷ���л��ɹ�");
			log.logInfo(entry.getKey(), entry.getValue().get(1), "����һ���л�Ϊ��ǰ���л��ɹ�");
		}
	}
	/*
	 * (�� Javadoc)
	*Title: termSwitchByStartTime
	*Description: 
	* @throws LotteryException
	* @see com.success.lottery.term.service.interf.LotteryTermSwitchServiceInterf#termSwitchByStartTime()
	 */
	public void termSwitchByStartTime() throws LotteryException {
		Map<Integer,List<String>> lotteryMap = new HashMap<Integer,List<String>>();
		
		try {
			for (String lotteryId : lotteryIdList) {
				List<String> innerList = new ArrayList<String>();
				int lotteryIdInt = Integer.parseInt(lotteryId);
				LotteryTermModel curTermInfo = this.getLotteryTermService().queryTermCurrentInfo(lotteryIdInt);
				String curTermNo = curTermInfo.getTermNo();
				String defineTime = curTermInfo.getStartTime()==null?"":curTermInfo.getStartTime().toString();
				innerList.add(0, curTermNo);
				innerList.add(1, defineTime);
				lotteryMap.put(lotteryIdInt, innerList);
			}
			
			/*
			 * ִ��ʱ��У�飬������ڶ����ִ��ʱ�䷶Χ�ڣ���ֱ���˳�
			 */
			if(this.getIsCheckTime().equals("1")){
				for (Map.Entry<Integer, List<String>> entry : lotteryMap.entrySet()) {
					String defineTime = entry.getValue().get(1);//�����ϵͳ��ʼ����ʱ��
					if(!isRunTime(defineTime,this.getTimeSpaces())){
						log.logInfo(entry.getKey(), entry.getValue().get(0), "���ڲ����л�ʱ��"+"["+defineTime+"]");
						return;
					}
				}
			}
			
			/*
			 * ִ�о���ĸ���״̬����
			 */
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				this.updateCurTermSaleStatus(entry.getKey(),entry.getValue().get(0));
			}
		} catch (LotteryException e) {
			logger.error("�л����� termSwitchByStartTime error:", e);
			if(lotteryMap.isEmpty()){
				for(String lottery : this.lotteryIdList){
					log.logInfo(Integer.parseInt(lottery), "-1", "��ǰ���л�ʧ��[û�еõ����ֵĵ�ǰ��]");
				}
			}
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				log.logInfo(entry.getKey(), entry.getValue().get(0), "��ǰ���л�ʧ��");
			}
			throw e;
		}
		
		for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
			log.logInfo(entry.getKey(), entry.getValue().get(0), "����ǰ���л�Ϊ������״̬�л��ɹ�");
		}	
	}
	
	/**
	 * 
	 * Title: convertLotteryList<br>
	 * Description: <br>
	 *            ���ݲ����б��ȡ���ֶ�Ӧ�ĵ�ǰ�ں���һ���Լ����ֵ�ǰ�ڵ�ϵͳֹ��ʱ��,��һ�ڵ�ϵͳ����ʱ��<br>
	 * @param lotteryIdList
	 * @param lotteryMap
	 * @throws LotteryException
	 */
	private void convertLotteryList(List<String> lotteryIdList,Map<Integer, List<String>> lotteryMap) throws LotteryException {
		for (String lotteryId : lotteryIdList) {
			List<String> innerList = new ArrayList<String>();
			int lotteryIdInt = Integer.parseInt(lotteryId);
			LotteryTermModel curTermInfo = this.getLotteryTermService().queryTermCurrentInfo(lotteryIdInt);
			String curTermNo = curTermInfo.getTermNo();
			String nextTermoNo = curTermInfo.getNextTerm();
			LotteryTermModel nextTermInfo = this.getLotteryTermService().queryTermInfo(lotteryIdInt, nextTermoNo);
			String defineTime = curTermInfo.getDeadLine()==null?"":curTermInfo.getDeadLine().toString();
			String nextStartTime = nextTermInfo.getStartTime()==null?"":nextTermInfo.getStartTime().toString();
			innerList.add(0, curTermNo);
			innerList.add(1, nextTermoNo);
			innerList.add(2,defineTime);
			innerList.add(3,nextStartTime);
			lotteryMap.put(lotteryIdInt, innerList);
		}
	}
	
	/**
	 * 
	 * Title: updateTermNo<br>
	 * Description: <br>
	 *            ���²��ڣ�������ǰ�ں���һ��<br>
	 * @param lotteryId
	 * @param curTermNo
	 * @param nextTermNo
	 * @throws LotteryException
	 */
	private void updateTermNo(int lotteryId,String curTermNo,String nextTermNo,String curDeadLine,String nextStartTime) throws LotteryException{
		int updateResult = -1;
		
		LotteryTermSwitchDomain curTermSwitch = new LotteryTermSwitchDomain();
		curTermSwitch.setLotteryId(lotteryId);
		curTermSwitch.setCurrentTermNo(curTermNo);
		curTermSwitch.setOld_termStatus(OLD_CUR_TERM_STATUS);
		curTermSwitch.setOld_saleStatus(OLD_CUR_SALES_STATUS);
		curTermSwitch.setTermStatus(NEW_CUR_TERM_STATUS);
		curTermSwitch.setSaleStatus(NEW_CUR_SALES_STATUS);
		updateResult = updateTetmNo(curTermSwitch);
		if (updateResult <= 0) {
			log.logInfo(lotteryId, curTermNo, E_301101_DESC);
			throw new LotteryException(E_301101_CODE, E_301101_DESC + "["
					+ lotteryId + "]");
		} else {
			
			LotteryTermSwitchDomain nextTermSwitch = new LotteryTermSwitchDomain();
			nextTermSwitch.setLotteryId(lotteryId);
			nextTermSwitch.setCurrentTermNo(nextTermNo);
			nextTermSwitch.setOld_termStatus(OLD_NEXT_TERM_STATUS);
			nextTermSwitch.setOld_saleStatus(OLD_NEXT_SALES_STATUS);
			nextTermSwitch.setTermStatus(NEW_NEXT_TERM_STATUS);
			if(isEqualTime(curDeadLine,nextStartTime)){//�����һ�ڵ�ֹ��ʱ�����һ�ڵĿ���ʱ����ͬһ��ʱ�䣬������״ֱ̬������Ϊ
				nextTermSwitch.setSaleStatus(NEW_NEXT_SALES_STATUS);
			}else{//���򣬽�����״̬������Ϊԭ����δ������ʱ��
				nextTermSwitch.setSaleStatus(OLD_NEXT_SALES_STATUS);
			}
			
			updateResult = updateTetmNo(nextTermSwitch);
		}

		if (updateResult <= 0) {
			log.logInfo(lotteryId, nextTermNo, E_301102_DESC);
			throw new LotteryException(E_301102_CODE, E_301102_DESC + "["
					+ lotteryId + "]");
		}
		
	}
	/**
	 * 
	 * Title: updateCurTermSaleStatus<br>
	 * Description: <br>
	 *              <br>����ǰ�ڵ�����״̬��δ������ʱ���Ϊ��������
	 * @param lotteryId
	 * @param curTermNo
	 * @throws LotteryException
	 */
	private void updateCurTermSaleStatus(int lotteryId,String curTermNo) throws LotteryException{
		int updateResult = -1;
		LotteryTermSwitchDomain curTermSwitch = new LotteryTermSwitchDomain();
		curTermSwitch.setLotteryId(lotteryId);
		curTermSwitch.setCurrentTermNo(curTermNo);
		curTermSwitch.setOld_termStatus(1);
		curTermSwitch.setOld_saleStatus(4);
		curTermSwitch.setTermStatus(1);
		curTermSwitch.setSaleStatus(1);
		updateResult = updateTetmNo(curTermSwitch);
		if (updateResult <= 0) {
			log.logInfo(lotteryId, curTermNo, E_301101_DESC);
			throw new LotteryException(E_301101_CODE, E_301101_DESC + "["
					+ lotteryId + "]");
		}
	}
	/**
	 * 
	 * Title: updateTetmNo<br>
	 * Description: <br>
	 *            ���²��ڱ�DAO����<br>
	 * @param termSwitch
	 * @return �ɹ����µļ�¼��
	 * @throws LotteryException
	 */
	private int updateTetmNo(LotteryTermSwitchDomain termSwitch) throws LotteryException{
		try {
			return this.getTermSwitchDao().updateTermSwitch(termSwitch);
		} catch (Exception e) {
			log.logInfo(termSwitch.getLotteryId(), termSwitch.getCurrentTermNo(), E_301103_DESC);
			logger.error("�л����� updateTetmNo error:", e);
			throw new LotteryException(E_301103_CODE,E_301103_DESC);
		}
	}
	/**
	 * 
	 * Title: isRunTime<br>
	 * Description: <br>
	 *            �ж��Ƿ����޶���ʱ�䷶Χ������<br>
	 * @param defineTime
	 * @param timeSpace
	 * @return  �л��Ƿ����ִ�� true ����
	 */
	private boolean isRunTime(String defineTime,String timeSpace){
		
		try {
			if(defineTime == null || "".equals(defineTime)){
				return true;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String systemTime = sdf.format(new Date()).toString();
			java.util.Date begin = sdf.parse(systemTime);
			java.util.Date end = sdf.parse(defineTime);
			long between=(end.getTime()-begin.getTime())/1000;
			if(java.lang.Math.abs(between) <= Long.parseLong(timeSpace)){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			return true;
		}
		
	}
	/**
	 * 
	 * Title: isEqualTime<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param dealLine
	 * @param startTime
	 * @return
	 */
	private boolean isEqualTime(String dealLine,String startTime){
		try{
			if(StringUtils.isEmpty(dealLine) || StringUtils.isEmpty(startTime)){//ֻҪ��һ��ʱ��Ϊ�վ���Ϊ����ʱ�䲻��ͬ
				return false;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date dealLineDate = sdf.parse(dealLine);
			java.util.Date startTimeDate = sdf.parse(startTime);
			long between = startTimeDate.getTime()-dealLineDate.getTime();
			if(between == 0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
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
	
	public List<String> getLotteryIdList() {
		return this.lotteryIdList;
	}

	public void setLotteryIdList(List<String> lotteryIdList) {
		this.lotteryIdList = lotteryIdList;
	}

	public String getTimeSpaces() {
		return this.timeSpaces;
	}

	public void setTimeSpaces(String timeSpaces) {
		this.timeSpaces = timeSpaces;
	}
	public String getIsCheckTime() {
		return this.isCheckTime;
	}
	public void setIsCheckTime(String isCheckTime) {
		this.isCheckTime = isCheckTime;
	}
}
