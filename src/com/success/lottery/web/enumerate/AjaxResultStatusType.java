package com.success.lottery.web.enumerate;
/**    
 * @{#} AjaxResultStatusType.java Create on Apr 9, 2010 2:03:25 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  ajax�������ķ���״̬
   

 */ 
@SuppressWarnings("unused")
public enum AjaxResultStatusType{
	//��������״̬
	_0000("�����ɹ�"),_0001("����ʧ��"),
	//�û����״̬
	_00000("δ��¼"),
	
	//�쳣
	_10000(""),
	;
	
	
	private String value;
	private AjaxResultStatusType(String val){
		this.value = val;
	}
	
	
}
