package com.success.lottery.account.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.success.lottery.account.model.AccountTransactionModel;

public interface AccountTransactionDAO {
	/**
	 * 交易记录（投注，充值，以及本金奖金变化）如购买彩票，申请取款，账户充值等
	 * 注意：交易渠道和渠道道流水号必填，没有交易不允许完成
	 * @param UserId 操作人ID
	 * @param TransactionType 交易类型
	 * @param amount 金额
	 * @param sourceType 交易渠道
	 * @param sourceSequence 渠道流水号
	 * 流程确认。
	 */
	public AccountTransactionModel addAccountTransaction(AccountTransactionModel transaction);
	/**
	 * 得到所有账户交易记录
	 * @author suerguo
	 * @param startTime 查询开始时间
	 * @param endTime 查询结束时间
	 * @param firstResult 查询开始条数
	 * @param maxResult 查询
	 * @param 用AccountTransactionModel 实体类封装
	 * @return
	 */
	public List<AccountTransactionModel> selectAllAccountTRN(AccountTransactionModel transaction);
	/**
	 * 账户交易记录总数
	 * @param startTime
	 * @param endTime
	 * @param 用AccountTransactionModel 实体类封装
	 * @return
	 */
	public int selectAllAccountTRNCount(AccountTransactionModel transaction);
	
	/**
	 * 根据用户ID查出该用户所有交易记录数
	 * @param userId 交易用户ID
	 * @param startTime 查询开始时间
	 * @param endTime 查询结束时间
	 * @param 用AccountTransactionModel 实体类封装
	 * @return
	 */
	public int selectAllAccountByUserCount(AccountTransactionModel transaction);
	/**
	 * 根据用户ID查出该用户所有交易记录
	 * @param userId 交易用户ID
	 * @param startTime 查询开始时间
	 * @param endTime 查询结束时间
	 * @param firstResult 查询开始条数
	 * @param maxResult 查询
	 * @param 用AccountTransactionModel 实体类封装
	 * @return
	 */
	public List<AccountTransactionModel> selectAllAccountByUser(AccountTransactionModel transaction);
	/**
	 * 根据用户id、TransactionType 交易类型、sourceType 交易渠道查交易记录
	 * @param transaction
	 * @return
	 */
	public List<AccountTransactionModel> selectUserTransaction(AccountTransactionModel transaction);
	/**
	 * 根据用户id、TransactionType 交易类型、sourceType 交易渠道查交易记录数
	 * @param transaction
	 * @return
	 */
	public int selectUserTransactionCount(AccountTransactionModel transaction);

	public void addTransactionInfo(long userId, int amount, int transactionType, int fundsBalance, int prizeBalance, int frozenBalance, int sourceType, String sourceSequence, String remark);
	
	public List<AccountTransactionModel> getUserTransactiones(long userId,
			String userIdentify, Timestamp startDate, Timestamp endDate,
			int transcationType, int sourceType,int start, int count);
	
	public List<AccountTransactionModel> getUserTransactionesCount(long userId,
			String userIdentify, Timestamp startDate, Timestamp endDate,
			int transcationType,int sourceType);
	/**
	 * 
	 * @param map
	 * @return
	 */
	public AccountTransactionModel getUserTransactionBySourceSequence(Map map);
	
}
