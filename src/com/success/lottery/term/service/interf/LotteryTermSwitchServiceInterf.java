/**
 * Title: LotteryTermSwitchServiceInterf.java
 * @Package com.success.lottery.term.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-15 上午10:08:18
 * @version V1.0
 */
package com.success.lottery.term.service.interf;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.term.service.interf
 * LotteryTermSwitchServiceInterf.java
 * LotteryTermSwitchServiceInterf
 * 彩期切换服务接口
 * @author gaoboqin
 * 2010-4-15 上午10:08:18
 * 
 */

public interface LotteryTermSwitchServiceInterf {
	
	public static final int E_301101_CODE = 301101;
	public static final String E_301101_DESC = "切换彩期时未找到彩种的符合条件的当前期信息！";
	
	public static final int E_301102_CODE = 301102;
	public static final String E_301102_DESC = "切换彩期时未找到彩种的符合条件的下一期信息！";
	
	public static final int E_301103_CODE = 301103;
	public static final String E_301103_DESC = "切换彩期时数据库发生错误！";
	
	public static final int E_301104_CODE = 301104;
	public static final String E_301104_DESC = "切换彩期时发生程序错误！";
	
	
	/**
	 * 
	 * Title: termSwitch<br>
	 * Description: <br>
	 *            彩期切换<br>
	 * @throws LotteryException
	 */
	public void termSwitch() throws LotteryException;
	/**
	 * 
	 * Title: termSwitchByStartTime<br>
	 * Description: <br>
	 *              <br>根据需要将当前期的销售状态是未到销售时间的彩期的销售状态改为正在销售
	 * @throws LotteryException
	 */
	public void termSwitchByStartTime() throws LotteryException;

}
