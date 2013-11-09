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
	 * 注册用户
	 * @param user
	 * @return
	 */
	public UserAccountModel addUser(UserAccountModel user);
	public List selectAlluserByUserpage(UserAccountModel user);
	public int selectAlluserByUserpageCount();
	public int updateUser(UserAccountModel user);
	public int deleteUser(long user_id);
	/**
	 * 用户登录验证
	 * @param name
	 * @param password
	 * @return
	 */
	public UserAccountModel selectUserByNamepsw(String name,String password);
	
	/**
	 * 检查手机号码，登录名，eml是否是唯一标示
	 * @param id
	 * @return
	 */
	public int selectCountByIdentifier2(String  id);
	/**
	 * 判断用户名是否重复，大于0为重复
	 * @param name
	 * @return
	 */
	public int selectUserByName(String name);
	/**
	 * 判断手机是否重复，大于0为重复
	 * @param name
	 * @return
	 */
	public int selectUserByMobile(String mobile);
	
	/**
	 * 修改用户登录密码
	 * @param loginName 登录名
	 * @param password 旧密码
	 * @param newPassword 新密码
	 * @param user 将上面三个变量集成到model里面
	 * @return
	 */
	public int updateUserPassword(UserAccountModel user);
	/**
	 * 根据用户ID查用户个人信心
	 * @param user_id 用户唯一标识ID
	 * @return
	 */
	public UserAccountModel selectUserByUserId(long user_id);
	/**
	 * 用户身份证是否重复
	 * @param IdCard 身份证号
	 * @return
	 */
	public int selectIdCard(String IdCard);
	/**
	 * 更新用户本金和冻结资金
	 * @param user
	 * @return
	 */
	public int updateUserFundsAccount(UserAccountModel user);
	/**
	 * 更新用户本金、奖金和冻结资金
	 * @param user
	 * @return
	 */
	public int updateUserFundsAndPrizeAccount(UserAccountModel user);
	/**
	 * 更新用户奖金和冻结资金
	 * @param user
	 * @return
	 */
	public int updatePrizeFrozenAccount(UserAccountModel user);
	/**
	 * 更新用户冻结资金
	 * @param user
	 * @return
	 */
	public int updateFrozenAccount(UserAccountModel user);
	/**
	 * 更新用户本金资金
	 * @param user
	 * @return
	 */
	public int updateOnlyFundsAccount(UserAccountModel user);
	/**
	 * 更新用户奖金资金
	 * @param user
	 * @return
	 */
	public int updateOnlyPrizeAccount(UserAccountModel user);
	/**
	 * 用户资金变更相关操作
	 * @param user
	 * @return
	 */
	public int updateAccountAbount(UserAccountModel user);
	/**
	 * 用户最后登录时间和IP
	 * @param user
	 * @return
	 */
	public int updateUserLastIp(UserAccountModel user);
	
	public UserAccountModel selectUserCountByUserId(long user_id);
	/**
	 * 根据手机号查UserId
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
	 * 查询会员数据总条数
	 */
	public int getUseresCount(String mobilePhone, String realName,
			String idCard, String areaCode, Date create_time_begin,
			Date create_time_ends);
	/*
	 * 查询会员数据列表
	 */
	public List<UserAccountModel> getUseresCountInfo(String mobilePhone,
			String realName, String idCard, String areaCode,
			Date create_time_begin, Date create_time_ends, int startNumber,
			int endNumber);
	/**
	 * 修改会员详细信息
	 * @throws LotteryException
	 */
	public int updateUserdetailInfo  (UserAccountModel user)throws LotteryException;
	
	/**
	 * 根据ID修改基本资料
	 * @param user
	 * @return
	 * @throws LotteryException
	 */
    public int updateUserInfoBaise(UserAccountModel user)throws LotteryException;	
    
    /**
     * 根据ID修改身份证 
     * @param user
     * @return
     */
    public int updateUserInfoID(UserAccountModel user)throws LotteryException;
    
    
    /**
     * 根据ID修改银行卡号，及银行名称
     * @param user
     * @return
     */
    public int updateUserInfoBank(UserAccountModel user)throws LotteryException;
	public int updateUserInfo(UserAccountModel user);
	
	/**
	 * 修改会员密码
	 * @param map
	 * @return
	 */
	public int updateUserPasswordInfo(Map map)throws LotteryException;
	
	public List<UserAccountModel> getUserAccountInfoForbindMobileFlag();
	/**
	 * 更新积分
	 * @param userId
	 * @param points
	 * @return
	 */
	public int updatePoints(long userId,long points);
	/**
	 * 积分交易记录
	 * @param pointTrans
	 * @return
	 */
	public String insertPointTrans(UserPointTrans pointTrans);
	
	
}
