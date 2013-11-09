/**
 * Title: LbapDaoImpl.java
 * @Package com.success.lottery.business.dao.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-7-5 下午06:15:32
 * @version V1.0
 */
package com.success.lottery.business.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.business.domain.BusBetOrderCountDomain;
import com.success.lottery.business.domain.LbapMsgDomain;
import com.success.lottery.order.domain.BetPlanDomain;

/**
 * com.success.lottery.business.dao.impl
 * LbapDaoImpl.java
 * LbapDaoImpl
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-7-5 下午06:15:32
 * 
 */

public class LbapDaoImpl extends SqlMapClientDaoSupport implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -1287846265976184466L;
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	public LbapMsgDomain getLbapMsg(LbapMsgDomain msg){
		return (LbapMsgDomain)this.smcTemplate.queryForObject("lbap.queryLbapMsg", msg);
	}
	
	public String insertLbapMsg(LbapMsgDomain msg){
		return (String)this.smcTemplate.insert("lbap.insertLbapMsg", msg);
	}

}
