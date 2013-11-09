package com.success.lottery.util;

import java.util.Comparator;

import com.success.lottery.business.domain.PrizeUserDomain;

/**
 * 实现PrizeUserDomain对象排序的类
 * @author Administrator
 *
 */
public class MyComparator  implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		PrizeUserDomain p1=(PrizeUserDomain)o1;
		PrizeUserDomain p2=(PrizeUserDomain)o2;  
		  if(p1.getPrize()<p2.getPrize())
		   return 1;
		  else
		   return 0;

	}

}
