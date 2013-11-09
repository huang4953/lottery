/**
 * Title: BetOrderDaoImpl.java
 * @Package com.success.lottery.order.dao.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-9 ����04:05:00
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
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-4-9 ����04:05:00
 * 
 */

public class CpInfoDaoImpl extends SqlMapClientDaoSupport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8669305716885479075L;
	
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	/**
	 * д������Ϣ
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
	 *              <br>���ݷ�����Ų�ѯ���еĲ�����Ϣ
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
	 *              <br>���²�����Ϣ��״̬
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
	 *              <br>��ѯ���������Ϣ������
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
	 *              <br>��ѯ���������Ϣ
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
	 *              <br>���º��������Ϣ���н����
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
	 *              <br>��ѯ����ͬһ�������»�δ�ҽ��Ĳ�������
	 * @param planId
	 * @return
	 */
	public int getNotDispatchInfoNum(String planId){
		return (Integer)this.smcTemplate.queryForObject("cpInfo.getNotDispatchInfoNum",planId);
	}
	
}
