package com.success.lottery.web.enumerate;

/**    
 * @{#} LotteryPlayType.java Create on Apr 7, 2010 5:20:54 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  ���ֵ��淨ö��
   

 */ 
public enum LotteryPlayType {
	//��ʽ
	ds,
	//��ʽ
	fs,
	//����
	dt,
	dlt12x2;
	
	public String getName(){
		return this.name();
	}
}
