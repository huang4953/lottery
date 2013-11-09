/**
 * @Title: LotteryTSScheduler.java
 * @Package com.success.lottery.term.service.impl
 * @Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-15 ����06:11:46
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
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-15 ����06:11:46
 * 
 */

public class LotteryTSScheduler {
	private static Log logger = LogFactory.getLog(LotteryTSScheduler.class);
	
	private LotteryTermSwitchServiceInterf termService;
	/**
	 * 
	 * Title: termSwitchScheduler<br>
	 * Description: <br>
	 *              <br>���ֲʵ�ǰ���л�Ϊ��ʷ�ڣ���һ���л�Ϊ��ǰ��
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
	 *              <br>���ֲʽ���ǰ�ڵ�����״̬��δ������ʱ���Ϊ��������
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
