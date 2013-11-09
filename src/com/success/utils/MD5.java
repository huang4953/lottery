package com.success.utils;

import java.security.MessageDigest;
/**
 * 通用MD5加密方法，登录注册时使用。
 * @author suerguo
 */
public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(")(*&^%"+":"+MD5Encode(")(*&^%").toUpperCase());
			
			System.out.println("19830127"+":"+MD5Encode("19830127").toUpperCase());
			System.out.println("118411"+":"+MD5Encode("118411").toUpperCase());
			System.out.println("happy3521"+":"+MD5Encode("happy3521").toUpperCase());
			System.out.println("226251"+":"+MD5Encode("226251").toUpperCase());
			System.out.println("198581"+":"+MD5Encode("198581").toUpperCase());
			System.out.println("198302"+":"+MD5Encode("198302").toUpperCase());
			System.out.println("123456"+":"+MD5Encode("123456").toUpperCase());
			System.out.println("nannan"+":"+MD5Encode("nannan").toUpperCase());
			System.out.println("62606861"+":"+MD5Encode("62606861").toUpperCase());
			System.out.println("ling1103"+":"+MD5Encode("ling1103").toUpperCase());
			System.out.println("qwerasdf"+":"+MD5Encode("qwerasdf").toUpperCase());
			
			System.out.println("198212"+":"+MD5Encode("198212").toLowerCase());
			
			System.out.println("111111"+":"+MD5Encode("111111").toLowerCase());
			System.out.println("600218"+":"+MD5Encode("600218").toUpperCase());
			
			System.out.println("600218"+":"+MD5Encode("600218").toUpperCase());
			
			System.out.println("123456"+":"+MD5Encode("123456").toUpperCase());
			
			
		} catch (Exception e) {

		}
	}
}
