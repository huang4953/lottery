/**
 * Title: WinResult.java
 * @Package com.success.lottery.util.core
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-3 ����06:31:36
 * @version V1.0
 */
package com.success.lottery.util;

import java.util.Comparator;

/**
 * com.success.lottery.util.core
 * WinResult.java
 * WinResult
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-3 ����06:31:36
 * 
 */

public class WinResult implements Comparable{
	
	private int winId;
	private String winCount;
	private String winMoney;
	private String winAddCount;
	private String winAddMoney;
	
	
	public String getWinAddCount() {
		return winAddCount;
	}
	public void setWinAddCount(String winAddCount) {
		this.winAddCount = winAddCount;
	}
	public String getWinAddMoney() {
		return winAddMoney;
	}
	public void setWinAddMoney(String winAddMoney) {
		this.winAddMoney = winAddMoney;
	}
	public String getWinCount() {
		return winCount;
	}
	public void setWinCount(String winCount) {
		this.winCount = winCount;
	}
	public int getWinId() {
		return winId;
	}
	public void setWinId(int winId) {
		this.winId = winId;
	}
	public String getWinMoney() {
		return winMoney;
	}
	public void setWinMoney(String winMoney) {
		this.winMoney = winMoney;
	}
	
	public int compareTo(Object arg0) {
		return this.winId - ((WinResult)arg0).getWinId();
	}
	
	

}
