package com.success.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MathUtil {
	
	/**
	 * @描述:求组合C(n,r)
	 * @param n
	 * @param r
	 * @return
	 */
	final static public int getCombinationCount(int n,int r){
		if(r > n) return 0;
		if(r < 0 || n < 0) return 0;
		return getFactorial(n).divide(getFactorial(r),BigDecimal.ROUND_HALF_DOWN).divide(getFactorial((n-r)),BigDecimal.ROUND_HALF_DOWN).intValue();
	}
	
	public static void main(String[] args) {
		System.out.println(getCombinationCount(0,0));
	}
	
	/**
	 * @描述:求排列p(n,r)
	 * @param n
	 * @param r
	 * @return
	 */
	final static public int getPermutationCount(int n,int r){
		if(r > n) return 0;
		if(r < 0 || n < 0) return 0;
		return getFactorial(n).divide(getFactorial(n-r)).intValue();
	}   
	
	/**
	 * @描述:求n的阶乘
	 * @param n
	 * @return
	 */
	final static public BigDecimal getFactorial(int num) {
        BigDecimal sum = new BigDecimal(1.0);
        for(int i = 1; i <= num; i++)
        {
        	BigDecimal a = new BigDecimal(new BigInteger(i+""));
            sum =sum.multiply(a);
        }
        return sum;
    }

	
	/**
	 * @描述:随机取的01-36之间的数字
	 * @param n    取的个数
	 * @param area 区间
	 * @return
	 */
	final static public String getRandom(int area,int n) {
		  Random rd1 = new Random();
		  Set<String> set = new HashSet<String>();
		  for (int i = 1; i < area; i++) {
			   
			   int num = rd1.nextInt(area);
			   String strNum = "";
			   if(num < 10) {
				   if(num != 0) {
					   strNum = "0"+num;
					   set.add(strNum);
				   }
				   
			   } else {
				   strNum = ""+num;
				   set.add(strNum);
			   }
			   if(set != null && set.size() >= n) {
				   break;
			   }
		  }
		  
		  String str = "";//返回拼的字符串
		  Iterator<String> it = set.iterator();
		  int i = 0;
		  while(it.hasNext()) {
			  i++;
			  if(i < n) {
				  str += it.next()+",";
			  } else {
				  str += it.next()+"";
			  }
			  
		  }
		  return str;
	}
	
	/**
	 * 求幂
	 * @param m
	 * 		底数
	 * @param n
	 * 		幂
	 * @return
	 */
	public static final long getPower(int m, int n) {
		 assert n >= 0;   
		         if(n == 0) return 1;   
		         if(n == 1) return m;   
		         long temp = getPower(m,n/2);   
		         return n%2 == 0? temp * temp: temp * temp * m; 
	}
	/**生成一个随机密码*/
	public static final String getRandomPassword(int m) {
		String code="";
//		int codeNmb=10;
		for(int i=0;i<m;i++){
			code+="0";
		}
		DecimalFormat df = new DecimalFormat(code);
		double temp=Math.random()*Math.pow(10, m);
		return df.format(temp);
	}
	
	/**
	 * 组合排序算法(回溯方法)
	 * @param str 需要排序的字符串
	 * @param n   拖的个数 如果为-1，则默认为str的长度
	 * @param m   拖中要选的个数
	 * @return
	 */
	public static List<String> combineAppend(String str[], int n, int m,
			String composeCode) {
		if (n == -1) {
			n = str.length;
		}
		m = m > n ? n : m;
		List<String> list = new ArrayList<String>();
		int[] order = new int[m + 1];
		for (int i = 0; i <= m; i++)
			order[i] = i - 1; // 注意这里order[0]=-1用来作为循环判断标识
		int count = 0;
		int k = m;
		boolean flag = true; // 标志找到一个有效组合
		while (order[0] == -1) {
			if (flag){ // 输出符合要求的组合
				StringBuffer sb = new StringBuffer();
				for (int j = 1; j <= m; j++) {
					count++;
					if (j != m) {
						sb.append(str[order[j]] + composeCode);
					} else {
						sb.append(str[order[j]]);
					}
				}
				flag = false;
				list.add(sb.toString());
			}
			order[k]++; // 在当前位置选择新的数字
			if (order[k] == n) {// 当前位置已无数字可选，回溯
				order[k--] = 0;
				continue;
			}
			if (k < m) {// 更新当前位置的下一位置的数字
				order[++k] = order[k - 1];
				continue;
			}
			if (k == m) flag = true;
		}
		return list;
	}
}
