/**
 * Title: BetOrderDaoImpl.java
 * @Package com.success.lottery.order.dao.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-9 下午04:05:00
 * @version V1.0
 */
package com.success.lottery.order.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.order.domain.CpInfoDomain;

/**
 * com.success.lottery.order.dao.impl
 * BetOrderDaoImpl.java
 * BetOrderDaoImpl
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-9 下午04:05:00
 * 
 */

public class CpInfoDaoImpl extends SqlMapClientDaoSupport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8669305716885479075L;
	
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	/**
	 * 写合买信息
	 * @param cpOrder
	 * @return
	 */
	public String insertCpOrderSingle(CpInfoDomain cpOrder){
		return (String)this.smcTemplate.insert("cpInfo.insertCpInfo", cpOrder);
	}
	
	/**
	 * 
	 * Title: getCoopInfoByPlanId<br>
	 * Description: <br>
	 *              <br>根据方案编号查询所有的参与信息
	 * @param planId
	 * @return
	 */
	public List<CpInfoDomain> getCoopInfoByPlanId(String planId){
		return this.smcTemplate.queryForList("cpInfo.getCoopInfoByPlanId", planId);
	}
	/**
	 * 
	 * Title: upCoopInfostatus<br>
	 * Description: <br>
	 *              <br>更新参与信息的状态
	 * @param coopInfoId
	 * @param orderStatus
	 * @return
	 */
	public int upCoopInfostatus(String coopInfoId,int orderStatus){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("coopInfoId", coopInfoId);
		params.put("orderStatus", orderStatus);
		return this.smcTemplate.update("cpInfo.updateInfoOrderStatus", params);
		
	}
	/**
	 * 
	 * Title: getCoopInfoForUpdate<br>
	 * Description: <br>
	 *              <br>查询合买参与信息并锁定
	 * @param coopInfoId
	 * @return
	 */
	public CpInfoDomain getCoopInfoForUpdate(String coopInfoId){
		return (CpInfoDomain)this.smcTemplate.queryForObject("cpInfo.getCoopInfoForUpdate", coopInfoId);
	}
	/**
	 * 
	 * Title: getCoopInfoByInfoId<br>
	 * Description: <br>
	 *              <br>查询合买参与信息
	 * @param coopInfoId
	 * @return
	 */
	public CpInfoDomain getCoopInfoByInfoId(String coopInfoId){
		return (CpInfoDomain)this.smcTemplate.queryForObject("cpInfo.getCoopInfoByInfoId", coopInfoId);
	}
	/**
	 * 
	 * Title: upCoopInfoPrize<br>
	 * Description: <br>
	 *              <br>更新合买参与信息的中奖情况
	 * @param coopInfoId
	 * @param orderStatus
	 * @param winStatus
	 * @param prize
	 * @return
	 */
	public int upCoopInfoPrize(String coopInfoId,int orderStatus,int winStatus,long prize){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("coopInfoId", coopInfoId);
		params.put("orderStatus", orderStatus);
		params.put("winStatus", winStatus);
		params.put("prize", prize);
		return this.smcTemplate.update("cpInfo.updateCoopInfoPrize", params);
		
	}
	/**
	 * 
	 * Title: getNotDispatchInfoNum<br>
	 * Description: <br>
	 *              <br>查询合买同一个方案下还未兑奖的参与数量
	 * @param planId
	 * @return
	 */
	public int getNotDispatchInfoNum(String planId){
		return (Integer)this.smcTemplate.queryForObject("cpInfo.getNotDispatchInfoNum",planId);
	}
	
}
