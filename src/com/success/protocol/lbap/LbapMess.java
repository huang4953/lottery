/**
 * Title: LbapErrorMess.java
 * @Package com.success.lottery.lbapserver
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-2 ����09:54:57
 * @version V1.0
 */
package com.success.protocol.lbap;

import com.success.utils.AutoProperties;

/**
 * com.success.lottery.lbapserver
 * LbapErrorMess.java
 * LbapErrorMess
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-2 ����09:54:57
 * 
 */

public class LbapMess {
	private static String messConfig = "com.success.protocol.lbap.lbapMess";
	private static String lbapConfig = "com.success.protocol.lbap.lbapConfig";
	
	public static String getMess(String code){
		return AutoProperties.getString(code, "", messConfig);
	}
	
	public static String getConfig(String code){
		return AutoProperties.getString(code, "", lbapConfig);
	}

	/**
	 * Title: main<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO �Զ����ɷ������

	}

}
