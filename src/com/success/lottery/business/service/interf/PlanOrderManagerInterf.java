/**
 * Title: PlanOrderManagerInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-18 下午09:47:49
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * PlanOrderManagerInterf.java
 * PlanOrderManagerInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-18 下午09:47:49
 * 
 */

public interface PlanOrderManagerInterf {
	/*
	 * 程序异常
	 */
	public static final int E_01_CODE = 521001;
	public static final String E_01_DESC = "程序发生异常！";
	
	/*
	 * 业务异常
	 */
	public static final int E_02_CODE = 520001;
	public static final String E_02_DESC = "[1]参数不正确(2)！";
	
	public static final int E_03_CODE = 520002;
	public static final String E_03_DESC = "订单不能撤销！";
	
	public static final int E_04_CODE = 520003;
	public static final String E_04_DESC = "订单不存在！";
	
	public static final int E_05_CODE = 520004;
	public static final String E_05_DESC = "订单撤销失败！";
	
	/**
	 * 
	 * Title: cancelAddOrder<br>
	 * Description: <br>
	 *              <br>追号订单撤销，只有尚未出票的订单才可以撤销订单
	 * @param orderId 订单编号
	 * @throws LotteryException<br>
	 *                          <br>PlanOrderManagerInterf.E_01_CODE:程序发生异常
	 *                          <br>PlanOrderManagerInterf.E_02_CODE:参数不正确
	 *                          <br>PlanOrderManagerInterf.E_03_CODE:订单不能撤销
	 *                          <br>PlanOrderManagerInterf.E_04_CODE:订单不存在
	 *                          <br>PlanOrderManagerInterf.E_05_CODE:订单撤销失败
	 *                          <br>处理账户的异常
	 */
	public void cancelAddOrder(String orderId) throws LotteryException;

}
