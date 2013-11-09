/**
 * Title: DealCpPlanInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-4-13 下午07:02:57
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.Map;

/**
 * com.success.lottery.business.service.interf
 * DealCpPlanInterf.java
 * DealCpPlanInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-4-13 下午07:02:57
 * 
 */

public interface DealCpPlanInterf {
	
	/**
	 * 
	 * Title: dealPlanBySys<br>
	 * Description: <br>
	 *              <br>系统期结束时处理合买的方案,该方法不能对外抛出异常
	 * @param lotteryTerms
	 */
	public void dealPlanBySys(Map<Integer,String> lotteryTerms);

}
