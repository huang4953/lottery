/**
 * Title: SuperTermSwitchService.java
 * @Package com.success.lottery.term.service.impl
 * Description: (用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-15 上午10:10:45
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
 *                      一般的彩种彩期切换<br>
 *                      该类同时切换当前期和下一期<br>
 *                      
 * @author gaoboqin
 * 2010-4-15 上午10:10:45
 * 
 */

public class CommonTermSwitchService implements LotteryTermSwitchServiceInterf {
	private static Log logger = LogFactory.getLog(CommonTermSwitchService.class);
	private static TermLog log = TermLog.getInstance("TS");
	private List<String> lotteryIdList = new ArrayList<String>();
	private String isCheckTime = "0";//是否执行时间检查，0为不检查，1为检查
	private String timeSpaces = "3600";//切换的前后时间差，单位为秒
	private static final int OLD_CUR_TERM_STATUS = 1;//当前期原始的彩期状态
	private static final int OLD_CUR_SALES_STATUS = 1;//当前期原始的销售状态
	private static final int OLD_NEXT_TERM_STATUS = 0;//下一期原始的彩期状态
	private static final int OLD_NEXT_SALES_STATUS = 4;//下一期原始的销售状态
	private static final int NEW_CUR_TERM_STATUS = 2;//当前期更改的彩期状态
	private static final int NEW_CUR_SALES_STATUS = 2;//当前期更改的销售状态
	private static final int NEW_NEXT_TERM_STATUS = 1;//下一期更改的彩期状态
	private static final int NEW_NEXT_SALES_STATUS = 1;//下一期更改的销售状态
	private LotteryTermServiceInterf lotteryTermService;
	private LotteryTermSwitchDaoImpl termSwitchDao; 
	
	/* (非 Javadoc)
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
			 * 执行时间校验，如果不在定义的执行时间范围内，则直接退出
			 */
			if(this.getIsCheckTime().equals("1")){
				for (Map.Entry<Integer, List<String>> entry : lotteryMap.entrySet()) {
					String defineTime = entry.getValue().get(2);//定义的截至销售时间
					if(!isRunTime(defineTime,this.getTimeSpaces())){
						log.logInfo(entry.getKey(), entry.getValue().get(0), "彩期不到切换时间");
						return;
					}
				}
			}
			/*
			 * 执行具体的更新状态操作
			 */
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				this.updateTermNo(entry.getKey(), entry.getValue().get(0),
						entry.getValue().get(1), entry.getValue().get(2), entry
								.getValue().get(3));
			}
			
			//合买方案处理
			if( !lotteryMap.containsKey(1200001)){//高频没有合买，先判断一下，不用启处理线程
				//此处以后根据数据量的需要可根据彩种启动多个线程
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
			logger.error("切换彩期 termSwitch error:", e);
			/*
			 * 切换彩期失败要更新记录表的状态
			 */
			if(lotteryMap.isEmpty()){
				for(String lottery : this.lotteryIdList){
					log.logInfo(Integer.parseInt(lottery), "-1", "当前期切换失败[没有得到彩种的当前期]");
				}
			}
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				log.logInfo(entry.getKey(), entry.getValue().get(0), "当前期切换失败");
			}
			throw e;
		}
		
		for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
			log.logInfo(entry.getKey(), entry.getValue().get(0), "将当前期切换为历史期切换成功");
			log.logInfo(entry.getKey(), entry.getValue().get(1), "将下一期切换为当前期切换成功");
		}
	}
	/*
	 * (非 Javadoc)
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
			 * 执行时间校验，如果不在定义的执行时间范围内，则直接退出
			 */
			if(this.getIsCheckTime().equals("1")){
				for (Map.Entry<Integer, List<String>> entry : lotteryMap.entrySet()) {
					String defineTime = entry.getValue().get(1);//定义的系统开始销售时间
					if(!isRunTime(defineTime,this.getTimeSpaces())){
						log.logInfo(entry.getKey(), entry.getValue().get(0), "彩期不到切换时间"+"["+defineTime+"]");
						return;
					}
				}
			}
			
			/*
			 * 执行具体的更新状态操作
			 */
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				this.updateCurTermSaleStatus(entry.getKey(),entry.getValue().get(0));
			}
		} catch (LotteryException e) {
			logger.error("切换彩期 termSwitchByStartTime error:", e);
			if(lotteryMap.isEmpty()){
				for(String lottery : this.lotteryIdList){
					log.logInfo(Integer.parseInt(lottery), "-1", "当前期切换失败[没有得到彩种的当前期]");
				}
			}
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				log.logInfo(entry.getKey(), entry.getValue().get(0), "当前期切换失败");
			}
			throw e;
		}
		
		for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
			log.logInfo(entry.getKey(), entry.getValue().get(0), "将当前期切换为可销售状态切换成功");
		}	
	}
	
	/**
	 * 
	 * Title: convertLotteryList<br>
	 * Description: <br>
	 *            根据彩种列表获取彩种对应的当前期和下一期以及彩种当前期的系统止售时间,下一期的系统开售时间<br>
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
	 *            更新彩期，包括当前期和下一期<br>
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
			if(isEqualTime(curDeadLine,nextStartTime)){//如果上一期的止售时间和下一期的开售时间是同一个时间，则将销售状态直接设置为
				nextTermSwitch.setSaleStatus(NEW_NEXT_SALES_STATUS);
			}else{//否则，将销售状态还设置为原来的未到销售时间
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
	 *              <br>将当前期的销售状态由未到销售时间改为正在销售
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
	 *            更新彩期表DAO操作<br>
	 * @param termSwitch
	 * @return 成功更新的记录数
	 * @throws LotteryException
	 */
	private int updateTetmNo(LotteryTermSwitchDomain termSwitch) throws LotteryException{
		try {
			return this.getTermSwitchDao().updateTermSwitch(termSwitch);
		} catch (Exception e) {
			log.logInfo(termSwitch.getLotteryId(), termSwitch.getCurrentTermNo(), E_301103_DESC);
			logger.error("切换彩期 updateTetmNo error:", e);
			throw new LotteryException(E_301103_CODE,E_301103_DESC);
		}
	}
	/**
	 * 
	 * Title: isRunTime<br>
	 * Description: <br>
	 *            判断是否在限定的时间范围内运行<br>
	 * @param defineTime
	 * @param timeSpace
	 * @return  切换是否可以执行 true 可以
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
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param dealLine
	 * @param startTime
	 * @return
	 */
	private boolean isEqualTime(String dealLine,String startTime){
		try{
			if(StringUtils.isEmpty(dealLine) || StringUtils.isEmpty(startTime)){//只要有一个时间为空就认为两个时间不相同
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
