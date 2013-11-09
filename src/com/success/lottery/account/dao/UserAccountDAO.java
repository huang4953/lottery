package com.success.lottery.account.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.model.UserPointTrans;
import com.success.lottery.exception.LotteryException;

public interface UserAccountDAO {
	/**
	 * ע���û�
	 * @param user
	 * @return
	 */
	public UserAccountModel addUser(UserAccountModel user);
	public List selectAlluserByUserpage(UserAccountModel user);
	public int selectAlluserByUserpageCount();
	public int updateUser(UserAccountModel user);
	public int deleteUser(long user_id);
	/**
	 * �û���¼��֤
	 * @param name
	 * @param password
	 * @return
	 */
	public UserAccountModel selectUserByNamepsw(String name,String password);
	
	/**
	 * ����ֻ����룬��¼����eml�Ƿ���Ψһ��ʾ
	 * @param id
	 * @return
	 */
	public int selectCountByIdentifier2(String  id);
	/**
	 * �ж��û����Ƿ��ظ�������0Ϊ�ظ�
	 * @param name
	 * @return
	 */
	public int selectUserByName(String name);
	/**
	 * �ж��ֻ��Ƿ��ظ�������0Ϊ�ظ�
	 * @param name
	 * @return
	 */
	public int selectUserByMobile(String mobile);
	
	/**
	 * �޸��û���¼����
	 * @param loginName ��¼��
	 * @param password ������
	 * @param newPassword ������
	 * @param user �����������������ɵ�model����
	 * @return
	 */
	public int updateUserPassword(UserAccountModel user);
	/**
	 * �����û�ID���û���������
	 * @param user_id �û�Ψһ��ʶID
	 * @return
	 */
	public UserAccountModel selectUserByUserId(long user_id);
	/**
	 * �û����֤�Ƿ��ظ�
	 * @param IdCard ���֤��
	 * @return
	 */
	public int selectIdCard(String IdCard);
	/**
	 * �����û�����Ͷ����ʽ�
	 * @param user
	 * @return
	 */
	public int updateUserFundsAccount(UserAccountModel user);
	/**
	 * �����û����𡢽���Ͷ����ʽ�
	 * @param user
	 * @return
	 */
	public int updateUserFundsAndPrizeAccount(UserAccountModel user);
	/**
	 * �����û�����Ͷ����ʽ�
	 * @param user
	 * @return
	 */
	public int updatePrizeFrozenAccount(UserAccountModel user);
	/**
	 * �����û������ʽ�
	 * @param user
	 * @return
	 */
	public int updateFrozenAccount(UserAccountModel user);
	/**
	 * �����û������ʽ�
	 * @param user
	 * @return
	 */
	public int updateOnlyFundsAccount(UserAccountModel user);
	/**
	 * �����û������ʽ�
	 * @param user
	 * @return
	 */
	public int updateOnlyPrizeAccount(UserAccountModel user);
	/**
	 * �û��ʽ�����ز���
	 * @param user
	 * @return
	 */
	public int updateAccountAbount(UserAccountModel user);
	/**
	 * �û�����¼ʱ���IP
	 * @param user
	 * @return
	 */
	public int updateUserLastIp(UserAccountModel user);
	
	public UserAccountModel selectUserCountByUserId(long user_id);
	/**
	 * �����ֻ��Ų�UserId
	 * @param mobile
	 * @return
	 */
	public long selectUser_IdByMobile(String mobile);
	
	public int checkByIdentifier(String loginName, String mobilePhone, String email);
	
	public UserAccountModel selectUserInfo(long userId, String userIdentify);
	
	public UserAccountModel getUserAccountInfoForUpdate(long userId, String userIdentify, int amount);
	
	public int updateUserAccountBalance(long userId, int funds, int prize, int frozen);
	
	public int setBindUserMobileFlag(long userId, boolean isBind, int userLevel);
	
	public UserAccountModel getUserInfoForLogon(String userIdentify, String password);
	
	public int updateUserLogonInfo(long userId, String lastLoginIp);
	/*
	 * ��ѯ��Ա����������
	 */
	public int getUseresCount(String mobilePhone, String realName,
			String idCard, String areaCode, Date create_time_begin,
			Date create_time_ends);
	/*
	 * ��ѯ��Ա�����б�
	 */
	public List<UserAccountModel> getUseresCountInfo(String mobilePhone,
			String realName, String idCard, String areaCode,
			Date create_time_begin, Date create_time_ends, int startNumber,
			int endNumber);
	/**
	 * �޸Ļ�Ա��ϸ��Ϣ
	 * @throws LotteryException
	 */
	public int updateUserdetailInfo  (UserAccountModel user)throws LotteryException;
	
	/**
	 * ����ID�޸Ļ�������
	 * @param user
	 * @return
	 * @throws LotteryException
	 */
    public int updateUserInfoBaise(UserAccountModel user)throws LotteryException;	
    
    /**
     * ����ID�޸����֤ 
     * @param user
     * @return
     */
    public int updateUserInfoID(UserAccountModel user)throws LotteryException;
    
    
    /**
     * ����ID�޸����п��ţ�����������
     * @param user
     * @return
     */
    public int updateUserInfoBank(UserAccountModel user)throws LotteryException;
	public int updateUserInfo(UserAccountModel user);
	
	/**
	 * �޸Ļ�Ա����
	 * @param map
	 * @return
	 */
	public int updateUserPasswordInfo(Map map)throws LotteryException;
	
	public List<UserAccountModel> getUserAccountInfoForbindMobileFlag();
	/**
	 * ���»���
	 * @param userId
	 * @param points
	 * @return
	 */
	public int updatePoints(long userId,long points);
	/**
	 * ���ֽ��׼�¼
	 * @param pointTrans
	 * @return
	 */
	public String insertPointTrans(UserPointTrans pointTrans);
	
	
}
