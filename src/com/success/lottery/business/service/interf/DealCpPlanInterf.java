/**
 * Title: DealCpPlanInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-4-13 ����07:02:57
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.Map;

/**
 * com.success.lottery.business.service.interf
 * DealCpPlanInterf.java
 * DealCpPlanInterf
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-4-13 ����07:02:57
 * 
 */

public interface DealCpPlanInterf {
	
	/**
	 * 
	 * Title: dealPlanBySys<br>
	 * Description: <br>
	 *              <br>ϵͳ�ڽ���ʱ�������ķ���,�÷������ܶ����׳��쳣
	 * @param lotteryTerms
	 */
	public void dealPlanBySys(Map<Integer,String> lotteryTerms);

}
