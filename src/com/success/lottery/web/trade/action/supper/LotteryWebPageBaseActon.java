package com.success.lottery.web.trade.action.supper;

import com.success.lottery.web.formbean.Page;

/**    
 * @{#} LotteryWebPageBaseActon.java Create on Mar 24, 2010 5:41:38 PM    
 *    
 * Copyright (c) 2010 by success.    

 * @author Gavin

 * @version 1.0 

 * @description  ������WebUi��ѯ��ҳ��action����
   

 */ 
@SuppressWarnings("serial")
public class LotteryWebPageBaseActon extends LotteryWebBaseActon{

	//��ҳ����
	protected Page page;
	
	private int pageSize=10;
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}


