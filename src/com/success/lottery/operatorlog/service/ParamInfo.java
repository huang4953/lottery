/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.success.lottery.operatorlog.service;

/**
 *
 * @author bing.li
 */
public class ParamInfo {

	private int level = OperatorLogger.INFO;
	private int type = OperatorLogger.OTHERTYPE;
	private int rank = OperatorLogger.NORMAL;
	private long userId;
	private String name;
	private String userKey;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;
	private String message;
	private boolean logIt;

	public ParamInfo() {
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}

	public String getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public boolean isLogIt() {
		return logIt;
	}

	public void setLogIt(boolean logIt) {
		this.logIt = logIt;
	}
}
