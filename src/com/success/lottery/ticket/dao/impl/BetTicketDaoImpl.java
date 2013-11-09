/**
 * @Title: BetTicketDaoImpl.java
 * @Package com.success.lottery.order.dao.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-13 上午10:50:18
 * @version V1.0
 */
package com.success.lottery.ticket.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.ticket.domain.BetTicketAcountDomain;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.domain.WinOrderTicketDomain;
/**
 * com.success.lottery.order.dao.impl BetTicketDaoImpl.java BetTicketDaoImpl
 * (这里用一句话描述这个类的作用)
 * 
 * @author gaoboqin 2010-4-13 上午10:50:18
 * 
 */
public class BetTicketDaoImpl extends SqlMapClientDaoSupport implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long		serialVersionUID	= -6306827182775686948L;
	private SqlMapClientTemplate	smcTemplate			= this.getSqlMapClientTemplate();

	public String insertBetTicket(BetTicketDomain betTicket){
		return (String)this.smcTemplate.insert("betTicket.insertBetTicket", betTicket);
	}

	public int updateBetTicketStatus(String ticketSequence, int ticketStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ticketSequence", ticketSequence);
		param.put("ticketStatus", ticketStatus);
		return this.smcTemplate.update("betTicket.updateBetTicketStatus", param);
	}

	public int updateBetTicketPrintInfo(BetTicketDomain betTicket){
		return this.smcTemplate.update("betTicket.updateBetTicketPrintInfo", betTicket);
	}

	public int updateBetTicketPrizeResult(String ticketSequence, long preTaxPrize, int prizeResult){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ticketSequence", ticketSequence);
		param.put("preTaxPrize", preTaxPrize);
		param.put("prizeResult", prizeResult);
		return this.smcTemplate.update("betTicket.updateBetTicketPrizeResult", param);
	}

	public int updateBetTicketStatus(List<String> ticketSequences, int ticketStatus, List<Integer> whoes){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ticketSequences", ticketSequences);
		param.put("ticketStatus", ticketStatus);
		param.put("whoes", whoes);
		return this.smcTemplate.update("betTicket.updateBetTicketStatusBySequences", param);
	}	

	public int queryBetOrderIdTicket(String orderId){
		return (Integer)this.smcTemplate.queryForObject("betTicket.queryBetOrderIdTicket", orderId);
	}

	@SuppressWarnings("unchecked")
	public List<String> insertBetOrderBatch(final List<BetTicketDomain> betTicketList) throws DataAccessException{
		List<String> insertTicketId = new ArrayList<String>();
		insertTicketId = (List<String>)this.smcTemplate.execute(new SqlMapClientCallback(){

			public Object doInSqlMapClient(final SqlMapExecutor executor) throws SQLException{
				List<String> ticketId = new ArrayList<String>();
				executor.startBatch();
				int batch = 0;
				for(final BetTicketDomain ticketDomain : betTicketList){
					ticketId.add(ticketDomain.getTicketSequence());
					executor.insert("betTicket.insertBetTicketBatch", ticketDomain);
					batch++;
					/*
					 * if(batch==50){ executor.executeBatch(); batch = 0; }
					 */
				}
				executor.executeBatch();
				return ticketId;
			}
		});
		return insertTicketId;
	}

	public List<BetTicketDomain> getTicketsByOrderId(String orderId){
		return this.smcTemplate.queryForList("betTicket.getTicketsByOrderId", orderId);
	}

	public BetTicketDomain getNotPrintTicket(){
		Object obj = this.smcTemplate.queryForObject("betTicket.queryNotPrintTicket");
		return(obj == null ? null : (BetTicketDomain)obj);
	}

	public List<BetTicketDomain> getTicketsToPrint(int lotteryId, String term){
		Map<String, String> param = new HashMap<String, String>();
		param.put("lotteryId", lotteryId + "");
		param.put("termNo", term);
		return this.smcTemplate.queryForList("betTicket.getTicketsToPrint", param);
	}

	public int updateTicketInfo(List<String> ticketSequence, int ticketStatus, String ticketData, String lastTicketTime, String printId, String printResult, String printTime, String ticketDataMD){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ticketSequence", ticketSequence);
		param.put("ticketStatus", ticketStatus + "");
		if(!StringUtils.isBlank(ticketData)){
			param.put("ticketData", ticketData);
		}
		if(!StringUtils.isBlank(lastTicketTime)){
			param.put("lastTicketTime", lastTicketTime);
		}
		if(!StringUtils.isBlank(printId)){
			param.put("printId", printId);
		}
		if(!StringUtils.isBlank(printResult)){
			param.put("printResult", printResult);
		}
		if(!StringUtils.isBlank(printTime)){
			param.put("printTime", printTime);
		}
		if(!StringUtils.isBlank(ticketDataMD)){
			param.put("ticketDataMD", ticketDataMD);
		}
		return this.smcTemplate.update("betTicket.updateBetTicketInfo", param);
	}
	
	public int getTicketesCount(int lotteryId, String term, List<Integer> ticketStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}
		if(term != null){
			param.put("term", term);
		}
		if(ticketStatus != null){
			param.put("ticketStatus", ticketStatus);
		}
		return (Integer)this.smcTemplate.queryForObject("betTicket.getTicketesCount", param);
	}

	public List<BetTicketDomain> getTicketes(int lotteryId, String term, List<Integer> ticketStatus, int start, int number){
		Map<String, Object> param = new HashMap<String, Object>();
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}
		if(term != null){
			param.put("term", term);
		}
		if(ticketStatus != null){
			param.put("ticketStatus", ticketStatus);
		}
		param.put("start", start);
		param.put("number", number);
		return (List<BetTicketDomain>)this.smcTemplate.queryForList("betTicket.getTicketesForWeb", param);
	}
	
	public int getTicketFilesCount(int lotteryId, String term){
		Map<String, Object> param = new HashMap<String, Object>();
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}
		if(term != null){
			param.put("term", term);
		}
		return (Integer)this.smcTemplate.queryForObject("betTicket.getTicketFilesCount", param);
	}
	
	public List<BetTicketDomain> getTicketFiles(int lotteryId, String term, int start, int number){
		Map<String, Object> param = new HashMap<String, Object>();
		if(lotteryId > 0){
			param.put("lotteryId", lotteryId);
		}
		if(term != null){
			param.put("term", term);
		}
		param.put("start", start);
		param.put("number", number);
		return (List<BetTicketDomain>)this.smcTemplate.queryForList("betTicket.getTicketFiles", param);
	}
	
	public int getTicketOrderesCount(int lotteryId, String term, String orderId, List<Integer> orderStatus, List<Integer> winStatus, List<Integer> ticketStatus){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", lotteryId);
		param.put("term", term);
		param.put("orderId", orderId);
		param.put("orderStatus", orderStatus);
		param.put("winStatus", winStatus);
		param.put("ticketStatus", ticketStatus);
		return (Integer)this.smcTemplate.queryForObject("betTicket.getTicketOrderesCount", param);
	}
	
	public List<WinOrderTicketDomain> getTicketOrderes(int lotteryId, String term, String orderId, List<Integer> orderStatus, List<Integer> winStatus, List<Integer> ticketStatus, int start, int number){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", lotteryId);
		param.put("term", term);
		param.put("orderId", orderId);
		param.put("orderStatus", orderStatus);
		param.put("winStatus", winStatus);
		param.put("ticketStatus", ticketStatus);
		param.put("start", start);
		param.put("number", number);
		
		return (List<WinOrderTicketDomain>)this.smcTemplate.queryForList("betTicket.getTicketOrderes", param);
	}
	
	public BetTicketDomain getTicket(String ticketSequence){
		return (BetTicketDomain)this.smcTemplate.queryForObject("betTicket.getTicket", ticketSequence);
	}

	public BetOrderDomain getOrder(String ticketSequence){
		return (BetOrderDomain)this.smcTemplate.queryForObject("betTicket.getOrderByTicketSequence", ticketSequence);
	}

	public int updateMultipleTicketPrintResult(List<String> ticketSequences, int ticketStatus, String ticketDataMD, String printResult){
		Map<String, Object> param = new HashMap<String, Object>();
		if(ticketSequences == null){
			return 0;
		}
		param.put("ticketSequences", ticketSequences);
		param.put("ticketStatus", ticketStatus);
		param.put("ticketDataMD", ticketDataMD);
		param.put("printResult", printResult);
		return this.smcTemplate.update("betTicket.updateMultipleTicketPrintResult", param);
	}

	
	//填充查询参数
	private void getBetTicketesParam(Map<String, Object> param,String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Timestamp beginTime,
			Timestamp endTime){
		if(null!=ticketStatus){
			if(0!=ticketStatus){
				List<Integer> temp = new ArrayList<Integer>();
				switch (ticketStatus) {
				case 10000:
					temp.add(0);
					break;
				case 10001:
					temp.add(1);
					temp.add(2);
					temp.add(3);
					temp.add(4);
					temp.add(5);
					break;
				case 10002:
					temp.add(6);
					break;
				case 10003:
					temp.add(7);
					temp.add(8);
					temp.add(9);
					break;
				default:
					temp.add(ticketStatus);
					break;
				}
				param.put("ticketStatus", temp);
			}
		}
		param.put("orderId", orderId);
		param.put("ticketSequence", ticketSequence);
		param.put("accountId", accountId);
		param.put("userName", userName);
		param.put("planSource", planSourceId);
		param.put("lotteryId", lotteryId);
		param.put("betTerm_begin", betTerm_begin);
		param.put("betTerm_end", betTerm_end);
		param.put("prizeResult", prizeResult);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
	}
	public int selectBetTicketesCount(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Timestamp beginTime,
			Timestamp endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		getBetTicketesParam(param,orderId,  ticketSequence,
				 lotteryId,  betTerm_begin,  betTerm_end,
				 ticketStatus,  prizeResult, accountId,
				 userName,  planSourceId, beginTime,
				 endTime);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return (Integer) sql.queryForObject("betTicketAcount.getBetTicketesCount", param);
	}

	public List selectBetTicketesList(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Timestamp beginTime,
			Timestamp endTime, int startNumber, int endNumber) {
		Map<String, Object> param = new HashMap<String, Object>();
		getBetTicketesParam(param,orderId,  ticketSequence,
				 lotteryId,  betTerm_begin,  betTerm_end,
				 ticketStatus,  prizeResult, accountId,
				 userName,  planSourceId, beginTime,
				 endTime);
		param.put("startPageNumber", Integer.valueOf(startNumber));
		param.put("endPageNumber", Integer.valueOf(endNumber-startNumber));
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return sql.queryForList("betTicketAcount.getBetTicketesList",param);
	}

	public long selectBetTicketesNumber(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Timestamp beginTime,
			Timestamp endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		getBetTicketesParam(param,orderId,  ticketSequence,
				 lotteryId,  betTerm_begin,  betTerm_end,
				 ticketStatus,  prizeResult, accountId,
				 userName,  planSourceId, beginTime,
				 endTime);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return (Long) sql.queryForObject("betTicketAcount.getBetTicketesNumber", param);
	}

	public long selectBetTicketesMoney(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Timestamp beginTime,
			Timestamp endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		getBetTicketesParam(param,orderId,  ticketSequence,
				 lotteryId,  betTerm_begin,  betTerm_end,
				 ticketStatus,  prizeResult, accountId,
				 userName,  planSourceId, beginTime,
				 endTime);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		Long longSum = (Long) sql.queryForObject("betTicketAcount.getBetTicketesMoney", param);
		if(null==longSum){
			return 0;
		}
		return longSum;
	}

	public int selectWinningBetTicketesCount(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Timestamp beginTime,
			Timestamp endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		getBetTicketesParam(param,orderId,  ticketSequence,
				 lotteryId,  betTerm_begin,  betTerm_end,
				 ticketStatus,  prizeResult, accountId,
				 userName,  planSourceId, beginTime,
				 endTime);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return (Integer) sql.queryForObject("betTicketAcount.getWinningBetTicketesCount", param);
	}

	public List selectWinningBetTicketes(String orderId, String ticketSequence,
			Integer lotteryId, String betTerm_begin, String betTerm_end,
			Integer ticketStatus, Integer prizeResult,String accountId,
			String userName, String planSourceId,Timestamp beginTime,
			Timestamp endTime, int startNumber, int endNumber) {
		Map<String, Object> param = new HashMap<String, Object>();
		getBetTicketesParam(param,orderId,  ticketSequence,
				 lotteryId,  betTerm_begin,  betTerm_end,
				 ticketStatus,  prizeResult, accountId,
				 userName,  planSourceId, beginTime,
				 endTime);
		param.put("startPageNumber", Integer.valueOf(startNumber));
		param.put("endPageNumber", Integer.valueOf(endNumber-startNumber));
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return sql.queryForList("betTicketAcount.getWinningBetTicketes",param);
	}

	public List<BetTicketAcountDomain> selectBetTicketes4OrderId(String orderId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return sql.queryForList("betTicketAcount.getBetTicketes4OrderId",param);
	}

	public long selectWinningBetTicketesMoney(String orderId,
			String ticketSequence, Integer lotteryId, String betTerm_begin,
			String betTerm_end, Integer ticketStatus, Integer prizeResult,
			String accountId,String userName, String planSourceId,Timestamp beginTime, Timestamp endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		getBetTicketesParam(param,orderId,  ticketSequence,
				 lotteryId,  betTerm_begin,  betTerm_end,
				 ticketStatus,  prizeResult, accountId,
				 userName,  planSourceId, beginTime,
				 endTime);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		Long longSum = (Long) sql.queryForObject("betTicketAcount.getWinningBetTicketesMoney", param);
		if(null==longSum){
			return 0;
		}
		return longSum;
	}

	public long selectWinningBetTicketesNumber(String orderId,
			String ticketSequence, Integer lotteryId, String betTerm_begin,
			String betTerm_end, Integer ticketStatus, Integer prizeResult,
			String accountId,String userName, String planSourceId,Timestamp beginTime, Timestamp endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		getBetTicketesParam(param,orderId,  ticketSequence,
				 lotteryId,  betTerm_begin,  betTerm_end,
				 ticketStatus,  prizeResult, accountId,
				 userName,  planSourceId, beginTime,
				 endTime);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		return (Long) sql.queryForObject("betTicketAcount.getWinningBetTicketesNumber", param);
	}

	public BetTicketAcountDomain selectBetTicketAcount(String ticketSequence) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ticketSequence", ticketSequence);
		return (BetTicketAcountDomain)this.smcTemplate.queryForObject("betTicketAcount.getTicketAcount", param);
	}

	public List<BetTicketDomain> queryUndeliverTicketQueTicket(List<Integer> ticketStatus, String condition, int limitNumber){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ticketStatus", ticketStatus);
		param.put("limitNumber", limitNumber);
		param.put("condition", condition);
		return this.smcTemplate.queryForList("betTicket.queryUndeliverTicketQueTicket", param);
	}
}
