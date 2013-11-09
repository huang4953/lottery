package com.success.lottery.account.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.account.model.IPSOrderModel;

public class IPSOrderDAO extends SqlMapClientDaoSupport{
	/**
	 * 插入一条环迅支付记录，只在用户确认进行环迅支付时插入，SQL语句如下：<br>
	 * insert into ipsorder(orderid, amount, userid, orderdate, currencytype, gatewaytype, succflag, attach, orderstatus, ordermessage, 
     *  	checkedstatus, reserve) 
     * values(
     *   	#orderId#, #amount#, #userId#, #orderDate#, #currencyType#, #gatewayType#, #succFlag#, #attach#, #orderStatus#, #orderMessage#, 
     *   	#checkedStatus#, #reserve#)
	 * @param ipsOrder
	 */
	public void addIPSOrder(IPSOrderModel ipsOrder){
		getSqlMapClientTemplate().insert("ipsOrder.addIpsOrder", ipsOrder);
	}

	/**
	 * 修改环迅支付记录
	 * @param ipsOrder
	 * 		要修改的订单信息，SQL语句如下：<br>
	 *		update ipsorder set succflag=#succFlag#, ipsbillno=#ipsBillNo#, ipsbanktime=#ipsBankTime#, ipsmsg=#ipsMsg#, orderstatus=#orderStatus#, 
			ordermessage=#orderMessage#, accounttransactionid=#accountTransactionId#, checkedstatus=#checkStatus#, checkmessage=#checkMessage#
			, reserve=#reserve# where orderid=#orderId#  
	 * @return
	 * 		更新的信息条数，只有返回1时为正确更新了环迅支付记录信息，返回0没有找到要更新的记录。
	 * 		
	 */
	public int updateIPSOrder(IPSOrderModel ipsOrder){
		return this.getSqlMapClientTemplate().update("ipsOrder.updateIpsOrder", ipsOrder);
	}
	
	/**
	 * 根据环迅订单编号查询订单信息，用于在收到环迅返回后更新订单状态，进行环迅交易处理；
	 * @param orderId
	 * 		指定的订单编号
	 * @return
	 * 		查询不到返回null
	 */
	public IPSOrderModel getIPSOrderForUpdate(String orderId){
		return (IPSOrderModel)this.getSqlMapClientTemplate().queryForObject("ipsOrder.getIPSOrderByOrderIdforUpdate", orderId);
	}

	/**
	 * 根据环迅订单编号查询订单信息，用于普通查询
	 * @param orderId
	 * 		指定的订单编号
	 * @return
	 * 		查询不到返回null
	 */
	public IPSOrderModel getIPSOrder(String orderId){
		return (IPSOrderModel)this.getSqlMapClientTemplate().queryForObject("ipsOrder.getIPSOrderByOrderId", orderId);
	}

	public List<IPSOrderModel> getIPSOrderTotalInfo(String userIdentify, Timestamp startTime, Timestamp endTime, int orderStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdentify", userIdentify);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("orderStatus", orderStatus);
//		param.put("start", start <= 0 ? 0: start);
//		param.put("count", count <= 0 ? 0: count);
		return (List<IPSOrderModel>)this.getSqlMapClientTemplate().queryForList("ipsOrder.getIPSOrderesTotalInfo", param);
	}
	
	public List<IPSOrderModel> getIPSOrderes(String userIdentify, Timestamp startTime, Timestamp endTime, int orderStatus, int start, int count){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userIdentify", userIdentify);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("orderStatus", orderStatus);
		param.put("start", start <= 0 ? 0: start);
		param.put("count", count <= 0 ? 0: count);
		return (List<IPSOrderModel>)this.getSqlMapClientTemplate().queryForList("ipsOrder.getIPSOrderes", param);
	}
}
