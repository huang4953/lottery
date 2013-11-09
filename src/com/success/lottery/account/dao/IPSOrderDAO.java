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
	 * ����һ����Ѹ֧����¼��ֻ���û�ȷ�Ͻ��л�Ѹ֧��ʱ���룬SQL������£�<br>
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
	 * �޸Ļ�Ѹ֧����¼
	 * @param ipsOrder
	 * 		Ҫ�޸ĵĶ�����Ϣ��SQL������£�<br>
	 *		update ipsorder set succflag=#succFlag#, ipsbillno=#ipsBillNo#, ipsbanktime=#ipsBankTime#, ipsmsg=#ipsMsg#, orderstatus=#orderStatus#, 
			ordermessage=#orderMessage#, accounttransactionid=#accountTransactionId#, checkedstatus=#checkStatus#, checkmessage=#checkMessage#
			, reserve=#reserve# where orderid=#orderId#  
	 * @return
	 * 		���µ���Ϣ������ֻ�з���1ʱΪ��ȷ�����˻�Ѹ֧����¼��Ϣ������0û���ҵ�Ҫ���µļ�¼��
	 * 		
	 */
	public int updateIPSOrder(IPSOrderModel ipsOrder){
		return this.getSqlMapClientTemplate().update("ipsOrder.updateIpsOrder", ipsOrder);
	}
	
	/**
	 * ���ݻ�Ѹ������Ų�ѯ������Ϣ���������յ���Ѹ���غ���¶���״̬�����л�Ѹ���״���
	 * @param orderId
	 * 		ָ���Ķ������
	 * @return
	 * 		��ѯ��������null
	 */
	public IPSOrderModel getIPSOrderForUpdate(String orderId){
		return (IPSOrderModel)this.getSqlMapClientTemplate().queryForObject("ipsOrder.getIPSOrderByOrderIdforUpdate", orderId);
	}

	/**
	 * ���ݻ�Ѹ������Ų�ѯ������Ϣ��������ͨ��ѯ
	 * @param orderId
	 * 		ָ���Ķ������
	 * @return
	 * 		��ѯ��������null
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
