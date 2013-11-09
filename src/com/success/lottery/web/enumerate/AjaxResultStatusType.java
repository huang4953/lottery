package com.success.lottery.web.enumerate;
/**    
 * @{#} AjaxResultStatusType.java Create on Apr 9, 2010 2:03:25 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  ajax请求结果的返回状态
   

 */ 
@SuppressWarnings("unused")
public enum AjaxResultStatusType{
	//基本操作状态
	_0000("操作成功"),_0001("操作失败"),
	//用户相关状态
	_00000("未登录"),
	
	//异常
	_10000(""),
	;
	
	
	private String value;
	private AjaxResultStatusType(String val){
		this.value = val;
	}
	
	
}
