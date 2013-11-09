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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.success.lottery.order.domain.BetOrderDomain;

/**
 * com.success.lottery.order.dao.impl
 * BetOrderDaoImpl.java
 * BetOrderDaoImpl
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-9 下午04:05:00
 * 
 */

public class BetOrderDaoImpl extends SqlMapClientDaoSupport implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -6889349964428951975L;
	private SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();
	
	public Object insertBetOrderSingle(BetOrderDomain betOrder){
		return this.smcTemplate.insert("betOrder.insertBetOrder", betOrder);
	}
	
	public String insertBetCoopOrder(BetOrderDomain betOrder){
		return (String)this.smcTemplate.insert("betOrder.insertBetCoopOrder", betOrder);
	}
	
	/**
	 * 
	 * Title: insertBetOrderBatch<br>
	 * Description: <br>
	 *            批量插入投注订单表<br>
	 * @param betOrderList
	 * @return List<String>
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<String> insertBetOrderBatch(final List<BetOrderDomain> betOrderList) throws DataAccessException{
		List<String> insertOredrId = new ArrayList<String>();
		
		insertOredrId = (List<String>)this.smcTemplate.execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(final SqlMapExecutor executor) throws SQLException {
				List<String> orderId = new ArrayList<String>();
    		executor.startBatch();
    		int batch = 0;
    		for(final BetOrderDomain betOrder : betOrderList){
    			orderId.add(betOrder.getOrderId()+"#"+betOrder.getBetTerm());
    			executor.insert("betOrder.insertBetOrder", betOrder);
    			batch++;
    			/*
    			if(batch==50){
    				executor.executeBatch();
    				batch = 0;
    			}*/
    		}
    		executor.executeBatch();
    		return orderId;
    	    }
		});
		return insertOredrId;
	}
	
	public int updateBetOrderStatus(String orderId,int orderStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("orderStatus", orderStatus);
		return this.smcTemplate.update("betOrder.updateBetOrderStatus", param);
	}
	
	public int updateBetOrderWinStatus(String orderId,int winStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("winStatus", winStatus);
		return this.smcTemplate.update("betOrder.updateBetOrderWinStatus", param);
	}
	
	public BetOrderDomain queryBetOrderByOrderId(String orderId){
		return (BetOrderDomain)this.smcTemplate.queryForObject("betOrder.queryBetOrderByOrderId", orderId);
	}
	
	public BetOrderDomain queryBetOrderByOrderIdForUpdate(String orderId){
		return (BetOrderDomain)this.smcTemplate.queryForObject("betOrder.queryBetOrderByOrderIdForUpdate", orderId);
	}
	
	@SuppressWarnings("unchecked")
	public List<BetOrderDomain> queryOrderByOrderIdForSamePlan(String planId,String orderId,List<String> nextTerm,List<Integer> orderStatus,int winStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("planId", planId);
		param.put("orderId", orderId);
		param.put("nextTerm", nextTerm);
		param.put("orderStatus", orderStatus);
		param.put("winStatus", winStatus);
		return this.smcTemplate.queryForList("betOrder.queryOrderByOrderIdForSamePlan", param);
	}
	
	public int updateBetOrderAndWinStatus(BetOrderDomain betOrder){
		return this.smcTemplate.update("betOrder.updateBetOrderAndWinStatus", betOrder);
	}
	
	public int queryOrderNumByStatus(int lotteryId,String termNo,List<Integer> orderStatus, List<Integer> winStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		param.put("orderStatus", orderStatus);
		param.put("winStatus", winStatus);
		return (Integer)this.smcTemplate.queryForObject("betOrder.queryOrderNumByStatus", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> queryOrderByStatus(int lotteryId, String termNo, List<Integer> orderStatus, List<Integer> winStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		param.put("orderStatus", orderStatus);
		param.put("winStatus", winStatus);
		return this.smcTemplate.queryForList("betOrder.queryOrderByStatus", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<BetOrderDomain> queryUndeliverTicketQueOrder(List<Integer> orderStatus, String termInfo,int limitNumber){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderStatus", orderStatus);
		param.put("limitNumber", limitNumber);
		param.put("termInfo", termInfo);
		return this.smcTemplate.queryForList("betOrder.queryUndeliverTicketQueOrder", param);
	}

	public List<BetOrderDomain> query2CheckOrder(List<Integer> orderStatus, List<Integer> ticketStatus, int limitNumber){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderStatus", orderStatus);
		param.put("ticketStatus", ticketStatus);
		param.put("limitNumber", limitNumber);
		return this.smcTemplate.queryForList("betOrder.query2CheckOrder", param);
	}


	public int updateBetOrderStatus(List<String> orderIds, int orderStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderIds", orderIds);
		param.put("orderStatus", orderStatus);
		return this.smcTemplate.update("betOrder.updateBetOrderStatusByIdList", param);
	}
	
	public int updateBetOrderStatus(List<String> orderIds, int orderStatus, List<Integer> whoes){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderIds", orderIds);
		param.put("orderStatus", orderStatus);
		param.put("whoes", whoes);
		return this.smcTemplate.update("betOrder.updateBetOrderStatusByIdList", param);
	}
	
	public int updateBetOrderTicketStat(String orderId, int orderStatus, String ticketStat){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("orderStatus", orderStatus);
		param.put("ticketStat", ticketStat);
		return this.smcTemplate.update("betOrder.updateBetOrderTicketStat", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<BetOrderDomain> querySamePlanIdOrders(String planId){
		return this.smcTemplate.queryForList("betOrder.querySamePlanIdOrders", planId);
	}
	
	public int getUserChaseOrdersCount(long userId, int lotteryId, String term, Timestamp startTime, Timestamp endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}

		if(term != null){
			param.put("term", term);
		}
		if(startTime != null){
			param.put("startTime", startTime);
		}
		if(endTime != null){
			param.put("endTime", endTime);
		}
		return (Integer)this.smcTemplate.queryForObject("betOrder.getUserChaseOrdersCount", param);
	}
	
	public List<BetOrderDomain> getUserChaseOrders(long userId, int lotteryId, String term, Timestamp startTime, Timestamp endTime, int start, int number){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}
		if(term != null){
			param.put("term", term);
		}
		if(startTime != null){
			param.put("startTime", startTime);
		}
		if(endTime != null){
			param.put("endTime", endTime);
		}
		param.put("start", start < 0 ? 0 : start);
		param.put("number", number < 0 ? 0 : number);
		return (List<BetOrderDomain>)this.smcTemplate.queryForList("betOrder.getUserChaseOrders", param);		
	}

	public BetOrderDomain getUserOrdersCount(long userId, int lotteryId, String term, int planSource, int orderStatus, int winStatus, Timestamp startTime, Timestamp endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}
		if(term != null){
			param.put("term", term);
		}
		if(planSource > -1){
			param.put("planSource", planSource);
		}
		if(orderStatus > -1){
			List<Integer> temp = new ArrayList<Integer>();
			switch (orderStatus) {
			case 100:
				temp.add(0);
				temp.add(1);
				temp.add(2);
				break;
			case 200:
				temp.add(5);
				break;
			case 300:
				temp.add(8);
				break;
			case 400:
				temp.add(10);
				break;
			case 500:
				temp.add(13);
				break;
			case 600:
				temp.add(11);
				temp.add(12);
				//以下是潘祖朝修改的,我的投注中追号限号取消的查询
				temp.add(14);
				break;
			case 700:
				temp.add(3);
				temp.add(4);
				temp.add(6);
				break;
			default:
				temp.add(orderStatus);
				break;
			}
			param.put("orderStatus", temp);
		}
		
		if(winStatus > -1){
			param.put("winStatus", winStatus);
		}
		if(startTime != null){
			param.put("startTime", startTime);
		}
		if(endTime != null){
			param.put("endTime", endTime);
		}
		return (BetOrderDomain)this.smcTemplate.queryForObject("betOrder.getUserOrdersCount", param);
	}
	
	public List<BetOrderDomain> getUserOrders(long userId, int lotteryId, String term, int planSource, int orderStatus, int winStatus, Timestamp startTime, Timestamp endTime, int start, int number){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}
		if(term != null){
			param.put("term", term);
		}
		if(planSource > -1){
			param.put("planSource", planSource);
		}
		if(orderStatus > -1){
			List<Integer> temp = new ArrayList<Integer>();
			switch (orderStatus) {
			case 100:
				temp.add(0);
				temp.add(1);
				temp.add(2);
				break;
			case 200:
				temp.add(5);
				break;
			case 300:
				temp.add(8);
				break;
			case 400:
				temp.add(10);
				break;
			case 500:
				temp.add(13);
				break;
			case 600:
				temp.add(11);
				temp.add(12);
				//以下是潘祖朝修改的,我的投注中追号限号取消的查询
				temp.add(14);
				break;
			case 700:
				temp.add(3);
				temp.add(4);
				temp.add(6);
				break;
			case 800:
				for(int i = 0; i < 11; i++){
					temp.add(i);
				}
				break;
			default:
				temp.add(orderStatus);
				break;
			}
			param.put("orderStatus", temp);
		}
		if(winStatus > -1){
			param.put("winStatus", winStatus);
		}
		if(startTime != null){
			param.put("startTime", startTime);
		}
		if(endTime != null){
			param.put("endTime", endTime);
		}
		param.put("start", start < 0 ? 0 : start);
		param.put("number", number < 0 ? 0 : number);
		return (List<BetOrderDomain>)this.smcTemplate.queryForList("betOrder.getUserOrders", param);		
	}
	public List<BetOrderDomain> getUserOrdersTwo(long userId, int lotteryId, String term, Timestamp startTime, Timestamp endTime, int start, int number){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}
		
		if("-1".equals(term)){
			term = null;
		}
		if(term != null){
			param.put("term", term);
		}
		
		if(startTime != null){
			param.put("startTime", startTime);
		}
		if(endTime != null){
			param.put("endTime", endTime);
		}
		param.put("start", start < 0 ? 0 : start);
		param.put("number", number < 0 ? 0 : number);
		return (List<BetOrderDomain>)this.smcTemplate.queryForList("betOrder.getUserOrdersTwo", param);		
	}

	/*
	 * 潘祖朝增加的
	 */
	public BetOrderDomain getUserOrdersTwoCount(long userId, int lotteryId, String term, Timestamp startTime, Timestamp endTime){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}
		if("-1".equals(term)){
			term = null;
		}
		if(term != null){
			param.put("term", term);
		}
		
		if(startTime != null){
			param.put("startTime", startTime);
		}
		if(endTime != null){
			param.put("endTime", endTime);
		}
		
		return (BetOrderDomain)this.smcTemplate.queryForObject("betOrder.getUserOrdersCountTwo", param);		
	}
	/**
	 * 根据彩种彩期及中将状态查询订单列表 
	 */ 
	public List<BetOrderDomain> getWinOrderList(Map map){
		return this.smcTemplate.queryForList("betOrder.getWinOrderList",map);
	} 
	/**
	 * 
	 * Title: getOrderByPlanId<br>
	 * Description: <br>
	 *              <br>根据方案查询出票订单
	 * @param planId
	 * @return
	 */
	public List<BetOrderDomain> getOrderByPlanId(String planId){
		return this.smcTemplate.queryForList("betOrder.queryOrderByPlanId", planId);
	}
}
