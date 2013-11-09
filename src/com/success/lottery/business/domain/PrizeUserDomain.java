/**
 * Title: PrizeUserDomain.java
 * @Package com.success.lottery.business.domain
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-6-8 下午01:35:04
 * @version V1.0
 */
package com.success.lottery.business.domain;

import java.io.Serializable;

/**
 * com.success.lottery.business.domain
 * PrizeUserDomain.java
 * PrizeUserDomain
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-6-8 下午01:35:04
 * 
 */

public class PrizeUserDomain implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 4384462640433021005L;

	private int lotteryId;
	
	private String lotteryName;
	
	private String mobilePhone;
	
	private long prize;

	public int getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public long getPrize() {
		return prize;
	}

	public void setPrize(long prize) {
		this.prize = prize;
	}

}
