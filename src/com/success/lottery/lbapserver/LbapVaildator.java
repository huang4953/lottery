/**
 * Title: LbapVaildator.java
 * @Package com.success.lottery.lbapserver
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-2 下午01:57:56
 * @version V1.0
 */
package com.success.lottery.lbapserver;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;

/**
 * com.success.lottery.lbapserver
 * LbapVaildator.java
 * LbapVaildator
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-2 下午01:57:56
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
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO 自动生成方法存根

	}

}
