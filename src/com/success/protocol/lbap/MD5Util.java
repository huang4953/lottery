/**
 * Title: MD5Util.java
 * @Package com.success.protocol.lbap
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-1 ����01:16:35
 * @version V1.0
 */
package com.success.protocol.lbap;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * com.success.protocol.lbap
 * MD5Util.java
 * MD5Util
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-1 ����01:16:35
 * 
 */

public class MD5Util {
	/**  
	 * Ĭ�ϵ������ַ�����ϣ��������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�,apacheУ�����ص��ļ�����ȷ���õľ���Ĭ�ϵ�������  
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messagedigest = null;
	
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(MD5Util.class.getName() + "��ʼ��ʧ�ܣ�MessageDigest��֧��MD5Util��");
			ex.printStackTrace();
		}
	}

	/**  
	 * �����ַ�����md5У��ֵ  
	 *   
	 * @param s  
	 * @return  
	 */
	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	/**  
	 * �ж��ַ�����md5У�����Ƿ���һ����֪��md5����ƥ��  
	 *   
	 * @param password ҪУ����ַ���  
	 * @param md5PwdStr ��֪��md5У����  
	 * @return  
	 */
	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}
	
	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];// ȡ�ֽ��и� 4 λ������ת��, >>> Ϊ�߼����ƣ�������λһ������,�˴�δ�������ַ����кβ�ͬ    
		char c1 = hexDigits[bt & 0xf];// ȡ�ֽ��е� 4 λ������ת��    
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	/**
	 * Title: main<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		long begin = System.currentTimeMillis();

		String md5 = getMD5String("a");   

		long end = System.currentTimeMillis();
		System.out.println("md5:" + md5 + " time:" + ((end - begin) / 1000) + "s");
	}
}
