package com.success.lottery.account.dao.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.account.dao.AccountTransactionDAO;
import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.BillOrderModel;
import com.success.lottery.util.LotterySequence;

public class AccountTransactionDAOImpl extends SqlMapClientDaoSupport implements AccountTransactionDAO{

	public AccountTransactionModel addAccountTransaction(AccountTransactionModel transaction) {
		// TODO Auto-generated method stub
		return (AccountTransactionModel)getSqlMapClientTemplate().insert("AccountTransaction.addAccountTransaction",transaction);
	}

	public List<AccountTransactionModel> selectAllAccountTRN(AccountTransactionModel transaction) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("AccountTransaction.selectAllAccountTRN",transaction);
	}

	public int selectAllAccountTRNCount(AccountTransactionModel transaction) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("AccountTransaction.selectAllAccountTRNCount",transaction);
	}

	public int selectAllAccountByUserCount(AccountTransactionModel transaction) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("AccountTransaction.selectAllAccountByUserCount",transaction);
	}

	public List<AccountTransactionModel> selectAllAccountByUser(AccountTransactionModel transaction) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("AccountTransaction.selectAllAccountByUser",transaction);
	}

	public List<AccountTransactionModel> selectUserTransaction(AccountTransactionModel transaction) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("AccountTransaction.selectUserTransaction",transaction);
	}

	public int selectUserTransactionCount(AccountTransactionModel transaction) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("AccountTransaction.selectUserTransactionCount",transaction);
	}

	@Override
	public void addTransactionInfo(long userId, int amount, int transactionType, int fundsBalance, int prizeBalance, int frozenBalance, int sourceType, String sourceSequence, String remark){
		AccountTransactionModel transaction = new AccountTransactionModel();
		transaction.setUserId(userId);
		transaction.setAmount(amount);
		transaction.setTransactionType(transactionType);
		transaction.setFundsAccount(fundsBalance);
		transaction.setPrizeAccount(prizeBalance);
		transaction.setFrozenAccount(frozenBalance);
		transaction.setSourceType(sourceType);
		transaction.setSourceSequence(sourceSequence);
		transaction.setRemark(remark);
		transaction.setTransactionId(LotterySequence.getInstatce("JY").getSequence());
		getSqlMapClientTemplate().insert("AccountTransaction.addTransactionInfo",transaction);
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountTransactionModel> getUserTransactiones(long userId,
			String userIdentify, Timestamp startDate, Timestamp endDate,
			int transcationType, int sourceType,int start, int count) {
		Map<String, Object> param = this.getUserTransactionesParam(userId, userIdentify, startDate, endDate, transcationType, sourceType,start, count);
		
		return this.getSqlMapClientTemplate().queryForList("AccountTransaction.getUserTransactiones" , param);
	}

	@SuppressWarnings("unchecked")
	public List<AccountTransactionModel> getUserTransactionesCount(long userId,
			String userIdentify, Timestamp startDate, Timestamp endDate,
			int transcationType,int sourceType) {
		Map<String, Object> param = this.getUserTransactionesParam(userId, userIdentify, startDate, endDate, transcationType,sourceType, -1, -1);
		
		return this.getSqlMapClientTemplate().queryForList("AccountTransaction.getUserTransactionesCount" , param);
	}
	
	private Map<String,Object> getUserTransactionesParam(long userId,
			String userIdentify, Timestamp startDate, Timestamp endDate,
			int transcationType, int sourceType,int start, int count){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("userIdentify", userIdentify);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("transcationType", transcationType);
		param.put("sourceType", sourceType);
		param.put("start", start <= 0 ? 0: start);
		param.put("count", count <= 0 ? 0: count);
		return param;
	}
	
	
	public AccountTransactionModel getUserTransactionBySourceSequence(Map map){
		return (AccountTransactionModel)this.getSqlMapClientTemplate().queryForObject("AccountTransaction.getUserTransactionBySourceSequence", map);
	}
}
