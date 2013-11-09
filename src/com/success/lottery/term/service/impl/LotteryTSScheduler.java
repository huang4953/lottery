/**
 * @Title: LotteryTSScheduler.java
 * @Package com.success.lottery.term.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-15 下午06:11:46
 * @version V1.0
 */
package com.success.lottery.term.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.term.service.interf.LotteryTermSwitchServiceInterf;

/**
 * com.success.lottery.term.service.impl
 * LotteryTSScheduler.java
 * LotteryTSScheduler
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-15 下午06:11:46
 * 
 */

public class LotteryTSScheduler {
	private static Log logger = LogFactory.getLog(LotteryTSScheduler.class);
	
	private LotteryTermSwitchServiceInterf termService;
	/**
	 * 
	 * Title: termSwitchScheduler<br>
	 * Description: <br>
	 *              <br>数字彩当前期切换为历史期，下一期切换为当前期
	 */
	public void termSwitchScheduler(){
		try{
			getTermService().termSwitch();
		}catch(Exception e){
			//e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: termSwitchSaleScheduler<br>
	 * Description: <br>
	 *              <br>数字彩将当前期的销售状态由未到销售时间改为正在销售
	 */
	public void termSwitchSaleScheduler(){
		try{
			getTermService().termSwitchByStartTime();
		}catch(Exception e){
			//e.printStackTrace();
		}
	}

	public LotteryTermSwitchServiceInterf getTermService() {
		return termService;
	}

	public void setTermService(LotteryTermSwitchServiceInterf termService) {
		this.termService = termService;
	}

}
