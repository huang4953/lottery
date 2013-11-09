package com.success.lottery.account.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.success.lottery.account.model.AccountTransactionModel;

public interface AccountTransactionDAO {
	/**
	 * ���׼�¼��Ͷע����ֵ���Լ����𽱽�仯���繺���Ʊ������ȡ��˻���ֵ��
	 * ע�⣺������������������ˮ�ű��û�н��ײ��������
	 * @param UserId ������ID
	 * @param TransactionType ��������
	 * @param amount ���
	 * @param sourceType ��������
	 * @param sourceSequence ������ˮ��
	 * ����ȷ�ϡ�
	 */
	public AccountTransactionModel addAccountTransaction(AccountTransactionModel transaction);
	/**
	 * �õ������˻����׼�¼
	 * @author suerguo
	 * @param startTime ��ѯ��ʼʱ��
	 * @param endTime ��ѯ����ʱ��
	 * @param firstResult ��ѯ��ʼ����
	 * @param maxResult ��ѯ
	 * @param ��AccountTransactionModel ʵ�����װ
	 * @return
	 */
	public List<AccountTransactionModel> selectAllAccountTRN(AccountTransactionModel transaction);
	/**
	 * �˻����׼�¼����
	 * @param startTime
	 * @param endTime
	 * @param ��AccountTransactionModel ʵ�����װ
	 * @return
	 */
	public int selectAllAccountTRNCount(AccountTransactionModel transaction);
	
	/**
	 * �����û�ID������û����н��׼�¼��
	 * @param userId �����û�ID
	 * @param startTime ��ѯ��ʼʱ��
	 * @param endTime ��ѯ����ʱ��
	 * @param ��AccountTransactionModel ʵ�����װ
	 * @return
	 */
	public int selectAllAccountByUserCount(AccountTransactionModel transaction);
	/**
	 * �����û�ID������û����н��׼�¼
	 * @param userId �����û�ID
	 * @param startTime ��ѯ��ʼʱ��
	 * @param endTime ��ѯ����ʱ��
	 * @param firstResult ��ѯ��ʼ����
	 * @param maxResult ��ѯ
	 * @param ��AccountTransactionModel ʵ�����װ
	 * @return
	 */
	public List<AccountTransactionModel> selectAllAccountByUser(AccountTransactionModel transaction);
	/**
	 * �����û�id��TransactionType �������͡�sourceType ���������齻�׼�¼
	 * @param transaction
	 * @return
	 */
	public List<AccountTransactionModel> selectUserTransaction(AccountTransactionModel transaction);
	/**
	 * �����û�id��TransactionType �������͡�sourceType ���������齻�׼�¼��
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
