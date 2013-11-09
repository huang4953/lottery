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
	 * @����:�����C(n,r)
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
	 * @����:������p(n,r)
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
	 * @����:��n�Ľ׳�
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
	 * @����:���ȡ��01-36֮�������
	 * @param n    ȡ�ĸ���
	 * @param area ����
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
		  
		  String str = "";//����ƴ���ַ���
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
	 * ����
	 * @param m
	 * 		����
	 * @param n
	 * 		��
	 * @return
	 */
	public static final long getPower(int m, int n) {
		 assert n >= 0;   
		         if(n == 0) return 1;   
		         if(n == 1) return m;   
		         long temp = getPower(m,n/2);   
		         return n%2 == 0? temp * temp: temp * temp * m; 
	}
	/**����һ���������*/
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
	 * ��������㷨(���ݷ���)
	 * @param str ��Ҫ������ַ���
	 * @param n   �ϵĸ��� ���Ϊ-1����Ĭ��Ϊstr�ĳ���
	 * @param m   ����Ҫѡ�ĸ���
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
			order[i] = i - 1; // ע������order[0]=-1������Ϊѭ���жϱ�ʶ
		int count = 0;
		int k = m;
		boolean flag = true; // ��־�ҵ�һ����Ч���
		while (order[0] == -1) {
			if (flag){ // �������Ҫ������
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
			order[k]++; // �ڵ�ǰλ��ѡ���µ�����
			if (order[k] == n) {// ��ǰλ���������ֿ�ѡ������
				order[k--] = 0;
				continue;
			}
			if (k < m) {// ���µ�ǰλ�õ���һλ�õ�����
				order[++k] = order[k - 1];
				continue;
			}
			if (k == m) flag = true;
		}
		return list;
	}
}
