/**
 * Title: LbapVaildator.java
 * @Package com.success.lottery.lbapserver
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-2 ����01:57:56
 * @version V1.0
 */
package com.success.lottery.lbapserver;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;

/**
 * com.success.lottery.lbapserver
 * LbapVaildator.java
 * LbapVaildator
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-2 ����01:57:56
 * 
 */

public class LbapVaildator {
	
	public static boolean isEmail(String email){
		boolean result = true;
		if(StringUtils.isNotEmpty(email)){
			if(!EmailValidator.getInstance().isValid(email)){
				result = false;
			}
		}
		return result;
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
