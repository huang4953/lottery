package com.success.lottery.web.enumerate;

/**    
 * @{#} LotteryPlayType.java Create on Apr 7, 2010 5:20:54 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  菜种的玩法枚举
   

 */ 
public enum LotteryPlayType {
	//单式
	ds,
	//复式
	fs,
	//胆拖
	dt,
	dlt12x2;
	
	public String getName(){
		return this.name();
	}
}
