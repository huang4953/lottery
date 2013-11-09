/**
 * Title: LbapDaoImpl.java
 * @Package com.success.lottery.business.dao.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-7-5 ����06:15:32
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
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-7-5 ����06:15:32
 * 
 */

public class LbapDaoImpl extends SqlMapClientDaoSupport implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
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
