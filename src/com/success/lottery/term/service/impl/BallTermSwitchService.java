/**
 * Title: BallTermSwitchService.java
 * @Package com.success.lottery.term.service.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-16 下午01:40:45
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
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-16 下午01:40:45
 * 
 */

public class BallTermSwitchService implements LotteryTermSwitchServiceInterf {
	private static Log logger = LogFactory.getLog(BallTermSwitchService.class);
	private static TermLog log = TermLog.getInstance("BTS");
	private String beginOrEnd = "0";//0表示切换足彩的预售期为当前期，1表示切换足彩的当前期为历史期
	private String timeSpaces = "3600";//切换的前后时间差，单位为秒
	private List<String> lotteryIdList = new ArrayList<String>();
	private LotteryTermServiceInterf lotteryTermService;
	private LotteryTermSwitchDaoImpl termSwitchDao; 
	private static final int OLD_CUR_TERM_STATUS = 1;//当前期原始的彩期状态
	private static final int OLD_CUR_SALES_STATUS = 1;//当前期原始的销售状态
	private static final int NEW_CUR_TERM_STATUS = 2;//当前期更改的彩期状态
	private static final int NEW_CUR_SALES_STATUS = 2;//当前期更改的销售状态
	
	private static final int OLD_NEXT_TERM_STATUS = 0;//预售期原始的彩期状态
	private static final int OLD_NEXT_SALES_STATUS = 4;//当预售期原始的销售状态
	private static final int NEW_NEXT_TERM_STATUS = 1;//当预售期更改的彩期状态
	private static final int NEW_NEXT_SALES_STATUS = 1;//预售期更改的销售状态

	/* (非 Javadoc)
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
	 *            将当前期切换为历史期<br>
	 *            是对所有设定的彩种一起操作，即如果有一步失败，则事务一起回滚，所有的指定的彩种切换都将失败<br>
	 *            <br>执行的操作;下面的每一步都是循环操作
	 *            <br>（1）从彩期表获取彩种对应的当前期号
	 *            <br>（2）检查是否到了切换的时间，只要有一个彩种没有到切换时间，则方法return,所有指定的彩种都将不切换
	 *            <br>（3）循环更新彩期表，将所有指定彩种的当前期的彩期状态和销售状态改为2，只要有一个更新失败则整个事务回滚
	 *            <br>（4）如果第3步没有出错，则循环更新彩期切换时间控制表，将截至销售的状态更新为1（成功）
	 *            <br>（5）如果上面的步骤出错，则将所有指定彩种的彩期切换时间控制表的销售截至状态更新为2（失败），此时要将前面步骤执行的操作回滚，此步执行的操作要提交
	 *            <br>
	 *            <br>遗留问题，彩期切换失败后该如何处理？
	 * @throws LotteryException
	 */
	private void updateCurTermToHistory() throws LotteryException{
		Map<Integer,List<String>> lotteryMap = new HashMap<Integer,List<String>>();
        int updateResult = -1;
        boolean isCanTerm = true;
        try {
			for (String lotteryId : this.lotteryIdList) {//取得彩种对应的当前期
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
					log.logInfo(lotteryIdInt, "-1", "将当前期切换为历史期时未得到彩种的当前期信息");
					isCanTerm = false;
					throw e;
				}
				
			}
			
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){//检查是否到切换时间
				//String defineTime = this.getTermSwitchDao().queryTermSwitchEndTime(entry.getKey(), entry.getValue());//取得定义的截至时间
				String defineTime = entry.getValue().get(1);
				if(!isRunTime(defineTime,this.getTimeSpaces())){
					log.logInfo(entry.getKey(), entry.getValue().get(0), "彩种当前期切换为历史期未到切换时间["+defineTime+"]");
					isCanTerm = false;
				}
				
			}
			
			if(!isCanTerm){
				return;
			}
			
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){//开始实际的切换操作
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
				//this.getTermSwitchDao().updateTermSwitchEndLog(lotteryid, curTermNo, 1, "将当前期切换为历史期切换成功");
			}
			
			//处理期结束时的合买方案
			try{
				//此处以后根据数据量的需要可根据彩种启动多个线程
				Map<Integer, String> lotteryTerms = new HashMap<Integer,String>();
				for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
					lotteryTerms.put(entry.getKey(), entry.getValue().get(0));
				}
				new SysDealCoopPlan(lotteryTerms).start();
			}catch(Exception e){
				e.printStackTrace();
			}
        
			
		} catch (Exception e) {
			//logger.error("切换彩期：将当前期切换为历史期 updateCurTermToHistory error:", e);
			/*
			 * 切换彩期失败要更新记录表的状态,此时要将前面的事务挂起，
			 */
			if(lotteryMap.isEmpty()){
				for(String lottery : this.lotteryIdList){
					log.logInfo(Integer.parseInt(lottery), "-1", "将当前期切换为历史期失败");
				}
			}
			for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
				//this.getLotteryTermService().updateTermSwitchLog(entry.getKey(), entry.getValue(), 2, "将当前期切换为历史期失败", "1");
				log.logInfo(entry.getKey(), entry.getValue().get(0), "将当前期切换为历史期失败");
			}
			if(e instanceof LotteryException) {
				throw (LotteryException)e;
			}else {
				throw new LotteryException(E_301104_CODE,E_301104_DESC);
			}
			
		}
		
		/*
		 * 写日志
		 */
		for(Map.Entry<Integer,List<String>> entry : lotteryMap.entrySet()){
			log.logInfo(entry.getKey(), entry.getValue().get(0), "将当前期切换为历史期切换成功");
		}
	}
	/**
	 * 
	 * Title: updateBeforeTermToCur<br>
	 * Description: <br>
	 *            将预售期切换为当前期<br>
	 *            <br>（1）根据彩种从彩期切换是将控制表取得所有的预定义的开始时间不为null，并且开始销售状态为0的预销售期以及开始时间，
	 *            <br>    此时要注意事务的超时以及另外一个进程的并发,该步骤不考虑会对同一彩种同时去到2个以上的时间相同的期数
	 *            <br>（2）检查是否到了可以销售的时间 
	 *            <br>                            只要有一个彩种没有在控制表中定义对应的开始销售时间则return，认为集合中的多个彩种都不可以切换
	 *            <br>                            只要有一个彩种没有到销售时间则return，认为集合中的多个彩种都不可以切换                  
	 *            <br>（3）循环更新彩期表，将所有指定彩种的预售期的彩期状态改为1和销售状态改为1，只要有一个更新失败则整个事务回滚
	 *            <br>（4）如果第3步没有出错，则循环更新彩期切换时间控制表，将开始销售的状态更新为1（成功）
	 *            <br>
	 *            <br>
	 *            <br>
	 *            <br>
	 * @throws LotteryException
	 */
	private void updateBeforeTermToCur() throws LotteryException{
		List<Map<Integer,String>> lotteryListMap = new ArrayList<Map<Integer,String>>();//该集合中存放是是可以进行切换的彩种彩期信息
		int updateResult = -1;
		boolean isCanTerm = true;
		try {
			/*
			 * 取得定义的预售期时间，并检查是否到了可以销售的时间
			 * 只要有一个彩种没有在控制表中定义对应的开始销售时间则return，认为集合中的多个彩种都不可以切换
			 * 只要有一个彩种没有到销售时间则return，认为集合中的多个彩种都不可以切换
			 */
			for (String lotteryId : this.lotteryIdList){
				int lotteryIdInt = Integer.parseInt(lotteryId);
				List<LotteryTermModel> termSwitchList = this.getTermSwitchDao().queryTermSwitchBeginInfo(lotteryIdInt,3);
				if(termSwitchList == null || termSwitchList.isEmpty()){
					log.logInfo(lotteryIdInt, "-1", "将预售期切换为当前期时未找到需要切换的彩期");
					isCanTerm = false;
				}else{
					isCanTerm = false;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for(LotteryTermModel termSwitch : termSwitchList){
						String defineTime = sdf.format(termSwitch.getStartTime());
						if(!this.isRunTime(defineTime, this.getTimeSpaces())){//不到切换时间
							log.logInfo(lotteryIdInt, termSwitch.getTermNo(), "彩种预售期切换为当前期未到切换时间["+defineTime+"]");
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
			 * 进行实际的预售期转当前期操作
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
				 * 更新切换记录表，对足彩要求事务一起回滚
				 */
				//this.getTermSwitchDao().updateTermSwitchBeforeLog(lotteryid, curTermNo, 1, "将预售期切换为当前期切换成功");
			}
			
		} catch (Exception  e) {
			logger.error("切换彩期：将预售期切换为当前期 updateBeforeTermToCur error:", e);
			/*
			 * 切换彩期失败要更新记录表的状态,此时要将前面的事务挂起，
			 */
			if(lotteryListMap.isEmpty()){
				for(String lottery : this.lotteryIdList){
					log.logInfo(Integer.parseInt(lottery), "-1", "将预售期切换为当前期失败");
				}
			}
			for(Map<Integer,String> oneCanTerm : lotteryListMap){
				Map.Entry<Integer, String> entry = oneCanTerm.entrySet().iterator().next();
				//this.getLotteryTermService().updateTermSwitchLog(entry.getKey(), entry.getValue(), 2, "将预售期切换为当前期失败", "0");
				log.logInfo(entry.getKey(), entry.getValue(), "将预售期切换为当前期失败");
			}
			if(e instanceof LotteryException) {
				throw (LotteryException)e;
			}else {
				throw new LotteryException(E_301104_CODE,E_301104_DESC);
			}
		}
		/*
		 * 写日志
		 */
		for(Map<Integer,String> oneCanTerm : lotteryListMap){
			Map.Entry<Integer, String> entry = oneCanTerm.entrySet().iterator().next();
			log.logInfo(entry.getKey(), entry.getValue(), "将预售期切换为当前期切换成功");
		}
	}
	/**
	 * 
	 * Title: updateTetmNo<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param termSwitch
	 * @return int
	 * @throws LotteryException
	 */
	private int updateTetmNo(LotteryTermSwitchDomain termSwitch) throws LotteryException{
		try {
			return this.getTermSwitchDao().updateTermSwitch(termSwitch);
		} catch (Exception e) {
			logger.error("切换彩期 updateTetmNo error:", e);
			throw new LotteryException(E_301103_CODE,E_301103_DESC);
		}
	}
	/**
	 * 
	 * Title: isRunTime<br>
	 * Description: <br>
	 *            切换时间检查，如果表中没有定义切换时间或者配置的时间不能正确的进行时间转化都返回false<br>
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
		// TODO 自动生成方法存根
		
	}
	
	

}
