package com.success.lottery.web.enumerate;

/**    
 * @{#} LotteryType.java Create on Apr 2, 2010 2:21:22 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 
 * @param <E>

 * @description  web层要访问的彩种类型class 
 */
public enum LotteryType{
	dlt,pls,plw,qxc,sfc;
	
	public String getName(){
		return this.name();
	}
}
