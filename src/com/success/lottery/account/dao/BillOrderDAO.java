package com.success.lottery.account.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.account.model.BillOrderModel;
import com.success.lottery.account.model.IPSOrderModel;

public class BillOrderDAO extends SqlMapClientDaoSupport {
     
	/**
	 * ��ӳ�ֵ��Ϣ
	 * @param billOrder
	 */
	public void addBillOrder(BillOrderModel billOrder){
		getSqlMapClientTemplate().insert("billOrder.addBillOrder", billOrder);
	}
    
	/**
	 * ����ID��ѯ��ֵ��¼
	 * @param orderId
	 * @return
	 */
	public BillOrderModel findBillOrderByOrderid(String orderId){
		return (BillOrderModel)this.getSqlMapClientTemplate().queryForObject("billOrder.getBillOrderByOrderid", orderId);
	}
	/**
	 * ����ID�޸Ŀ�Ǯ��ֵ
	 * @param billOrder
	 * @return
	 */
	public int updateBillOrder(BillOrderModel billOrder){
		return this.getSqlMapClientTemplate().update("billOrder.updateBillOrder", billOrder);
	}
	public List<BillOrderModel> getBillOrderTotalInfo(String userIdentify, Timestamp startTime, Timestamp endTime, int orderStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdentify", userIdentify);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("orderStatus", orderStatus);
		return (List<BillOrderModel>)this.getSqlMapClientTemplate().queryForList("billOrder.getBillOrderesTotalInfo", param);
	}
	
	public List<BillOrderModel> getBillOrderes(String userIdentify, Timestamp startTime, Timestamp endTime, int orderStatus, int start, int count){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdentify", userIdentify);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("orderStatus", orderStatus);
		param.put("start", start <= 0 ? 0: start);
		param.put("count", count <= 0 ? 0: count);
		return (List<BillOrderModel>)this.getSqlMapClientTemplate().queryForList("billOrder.getBillOrderes", param);
	}
	
}
