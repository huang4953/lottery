/**
 * Title: LotteryTermSwitchDaoImpl.java
 * @Package com.success.lottery.term.dao.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-15 ����11:42:31
 * @version V1.0
 */
package com.success.lottery.term.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.success.lottery.term.dao.interf.BaseSqlMapDao;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.domain.LotteryTermSwitchDomain;


/**
 * com.success.lottery.term.dao.impl
 * LotteryTermSwitchDaoImpl.java
 * LotteryTermSwitchDaoImpl
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-15 ����11:42:31
 * 
 */

public class LotteryTermSwitchDaoImpl extends BaseSqlMapDao {
	
	public int updateTermSwitch(LotteryTermSwitchDomain termSwitch){
		return super.smcTemplate.update("lotteryTerm.updateTermSwitch", termSwitch);
	}
	
	@SuppressWarnings("unchecked")
	public List<LotteryTermModel> queryTermSwitchBeginInfo(int lotteryId,int limitNum){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("lotteryId", lotteryId);
		param.put("limitNum", limitNum<=0?1:limitNum);
		return super.smcTemplate.queryForList("lotteryTerm.queryTermSwitchBeginInfo", param);
	}


}
