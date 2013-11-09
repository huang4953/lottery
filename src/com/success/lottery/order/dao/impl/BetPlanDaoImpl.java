/**
 * Title: BetPlanDaoImpl.java
 * @Package com.success.lottery.order.dao.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-9 下午04:04:30
 * @version V1.0
 */
package com.success.lottery.order.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.order.domain.BetPlanDomain;

/**
 * com.success.lottery.order.dao.impl
 * BetPlanDaoImpl.java
 * BetPlanDaoImpl
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-9 下午04:04:30
 * 
 */

public class BetPlanDaoImpl extends SqlMapClientDaoSupport implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 8691654295765862902L;
	private final SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	public String insertBetPlan(BetPlanDomain betPlanDomain){
		return (String)this.smcTemplate.insert("betPlan.insertBetPlan", betPlanDomain);
	}
	
	public int updateBetPlanStatus(String planId,int planStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("planId", planId);
		param.put("planStatus", planStatus);
		return this.smcTemplate.update("betPlan.updateBetPlanStatus", param);
	}
	
	public BetPlanDomain queryBetPlanByPlanId(String planId){
		return (BetPlanDomain)this.smcTemplate.queryForObject("betPlan.queryBetPlanByPlanId", planId);
	}
	
	public BetPlanDomain queryBetPlanForUpdate(String planId){
		return (BetPlanDomain)this.smcTemplate.queryForObject("betPlan.queryBetPlanForUpdate", planId);
	}
	
	public int updatePlanSelledAndStatus(String planId,int selledUnit,int planStatus){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("planId", planId);
		params.put("selledUnit", selledUnit);
		params.put("planStatus", planStatus);
		return this.smcTemplate.update("betPlan.updateSelledAndStatus", params);
		
	}
	
	public List<BetPlanDomain> getPlanInfoByLotteryTerm(List<String> lotteryAndTerm,List<Integer> planType,List<Integer> planStatus){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("lotteryIdTerms", lotteryAndTerm);
		params.put("planType", planType);
		params.put("planStatus", planStatus);
		return this.smcTemplate.queryForList("betPlan.getPlanInfoByLotteryTerm", params);
		
	}

}
