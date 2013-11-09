package com.success.lottery.account.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.account.dao.UserAccountDAO;
import com.success.lottery.account.model.AccountTransactionModel;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.model.UserPointTrans;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.utils.MD5;

public class UserAccountDAOImpl extends SqlMapClientDaoSupport implements UserAccountDAO{

	public UserAccountModel addUser(UserAccountModel user) {
		// TODO 注册用户
		return (UserAccountModel)getSqlMapClientTemplate().insert("UserAccount.addUser",user);
	}

	public List selectAlluserByUserpage(UserAccountModel user) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("UserAccount.selectAlluserByUserpage",user);
	}

	public int selectAlluserByUserpageCount() {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("UserAccount.selectAlluserByUserpageCount");
	}

	public int updateUser(UserAccountModel user) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("UserAccount.updateUser",user);
	}

	public int deleteUser(long user_id) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().delete("UserAccount.deleteUser",user_id);
	}

	public UserAccountModel selectUserByNamepsw(String name, String password) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("password", password);
		return (UserAccountModel)getSqlMapClientTemplate().queryForObject("UserAccount.selectUserByNamepsw", map);
	}

	public int selectUserByName(String name) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("UserAccount.selectUserByName",name);
	}

	public int selectUserByMobile(String mobile) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("UserAccount.selectUserByMobile",mobile);
	}

	public UserAccountModel selectUserByUserId(long userId) {
		// TODO Auto-generated method stub
		return (UserAccountModel)getSqlMapClientTemplate().queryForObject("UserAccount.selectUserByUserId",userId);
	}

	public int updateUserPassword(UserAccountModel user) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateUserPassword",user);
	}

	public int selectIdCard(String IdCard) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("UserAccount.selectIdCard",IdCard);
	}

	public int updateUserFundsAccount(UserAccountModel user) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateUserFundsAccount",user);
	}

	public int updateUserFundsAndPrizeAccount(UserAccountModel user) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateUserFundsAndPrizeAccount",user);
	}

	public int updatePrizeFrozenAccount(UserAccountModel user) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updatePrizeFrozenAccount",user);
	}
 
	public int updateFrozenAccount(UserAccountModel user) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateFrozenAccount",user);
	}

	public int updateOnlyFundsAccount(UserAccountModel user) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateOnlyFundsAccount",user);
	}

	public int updateOnlyPrizeAccount(UserAccountModel user) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateOnlyPrizeAccount",user);
	}

	public int updateAccountAbount(UserAccountModel user) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateAccountAbount",user);
	}

	public int updateUserLastIp(UserAccountModel user) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateUserLastIp",user);
	}

	public UserAccountModel selectUserCountByUserId(long user_id) {
		// TODO Auto-generated method stub
		return (UserAccountModel)getSqlMapClientTemplate().queryForObject("UserAccount.selectUserCountByUserId",user_id);
	}

	public long selectUser_IdByMobile(String mobile) {
		// TODO Auto-generated method stub
		return (Long)getSqlMapClientTemplate().queryForObject("UserAccount.selectUser_IdByMobile",mobile);
	}

	@Override
	public int checkByIdentifier(String loginName, String mobilePhone, String email){
		UserAccountModel user = new UserAccountModel();
		user.setLoginName(loginName);
		user.setMobilePhone(mobilePhone);
		user.setEmail(email);
		return (Integer)getSqlMapClientTemplate().queryForObject("UserAccount.selectCountByIdentifier", user);
	}

	@Override
	public UserAccountModel selectUserInfo(long userId, String userIdentify){
		UserAccountModel user = new UserAccountModel();
		user.setUserId(userId);
		user.setLoginName(userIdentify);
		user.setMobilePhone(userIdentify);
		user.setEmail(userIdentify);
		return (UserAccountModel)getSqlMapClientTemplate().queryForObject("UserAccount.selectUserInfoByIdentifier", user);
	}

	@Override
	public UserAccountModel getUserAccountInfoForUpdate(long userId, String userIdentify, int amount){
		UserAccountModel user = new UserAccountModel();
		user.setUserId(userId);
		user.setLoginName(userIdentify);
		user.setMobilePhone(userIdentify);
		user.setEmail(userIdentify);
		//这里借用sex字段来进行金额校验
		user.setSex(amount);
		return (UserAccountModel)getSqlMapClientTemplate().queryForObject("UserAccount.getUserAccountInfoForUpdate", user);
	}

	@Override
	public int updateUserAccountBalance(long userId, int funds, int prize, int frozen){
		UserAccountModel user = new UserAccountModel();
		user.setUserId(userId);
		user.setFundsAccount(funds);
		user.setPrizeAccount(prize);
		user.setFrozenAccount(frozen);
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateUserAccountBalance",user);
	}
	
	public int updatePoints(long userId,long points){
		UserAccountModel user = new UserAccountModel();
		user.setUserId(userId);
		user.setOtherAccount1(points);
		return (Integer)getSqlMapClientTemplate().update("UserAccount.updateUserPoints",user);
	}
	
	public int setBindUserMobileFlag(long userId, boolean isBind, int userLevel){
		Map<String, String> map = new HashMap<String, String>();
		map.put("bindMobileFlag", isBind ? "1" : "0");
		map.put("userId", "" + userId);
		map.put("userLevel", "" + userLevel);
		return (Integer)getSqlMapClientTemplate().update("UserAccount.setBindMobileFlag", map);
	}

//	public int setBindUserMobileFlag(long userId, int bindService){
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("bindMobileFlag", bindService + "");
//		map.put("userId", "" + userId);
//		return (Integer)getSqlMapClientTemplate().update("UserAccount.setBindMobileFlag", map);
//	}

	
	@Override
	public UserAccountModel getUserInfoForLogon(String userIdentify, String password){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("loginName", userIdentify);
		paramMap.put("mobilePhone", userIdentify);
		paramMap.put("email", userIdentify);
		paramMap.put("password", MD5.MD5Encode(password));
		return (UserAccountModel) getSqlMapClientTemplate().queryForObject("UserAccount.getUserForLogon", paramMap);
	}
	
	public int updateUserLogonInfo(long userId, String lastLoginIp){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastLoginIp", lastLoginIp);
		paramMap.put("userId", Long.valueOf(userId));
		return getSqlMapClientTemplate().update("UserAccount.updateUserLoginInfo", paramMap);
	}
	
	@Override
	public int getUseresCount(String mobilePhone, String realName,
			String idCard, String areaCode, Date startDate,
			Date endDate) {
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp create_time_begin = null, create_time_ends = null;
		if(startDate != null){
			create_time_begin = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
		}
		if(endDate != null){
			create_time_ends = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
		}
		param.put("startTime", create_time_begin);
		param.put("endTime", create_time_ends);
		param.put("mobilePhone", mobilePhone);
		param.put("realName", realName);
		param.put("idCard", idCard);
		param.put("areaCode", areaCode);
		SqlMapClientTemplate sql = this.getSqlMapClientTemplate();
		Integer result = (Integer) sql.queryForObject("UserAccount.selectUserInfoForSex", param);
		return result;
	}

	@Override
	public List<UserAccountModel> getUseresCountInfo(String mobilePhone,
			String realName, String idCard, String areaCode,
			Date startDate, Date endDate, int startNumber,
			int endNumber) {
		if(endNumber <= startNumber){
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp create_time_begin = null, create_time_ends = null;
		if(startDate != null){
			create_time_begin = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
		}
		if(endDate != null){
			create_time_ends = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
		}
		param.put("startTime", create_time_begin);
		param.put("endTime", create_time_ends);
		param.put("mobilePhone", mobilePhone);
		param.put("realName", realName);
		param.put("idCard", idCard);
		param.put("areaCode", areaCode);
		param.put("startPageNumber", Integer.valueOf(startNumber));
		param.put("endPageNumber", Integer.valueOf(endNumber-startNumber));
		List<UserAccountModel> list = null;
		list = (List<UserAccountModel>)this.getSqlMapClientTemplate().queryForList("UserAccount.selectUserInfoForSexList" , param);
		return list;
	}

	@Override
	public int updateUserdetailInfo(UserAccountModel userinfo) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("UserAccount.updateUserdetailInfo", userinfo);
	}

	@Override
	public int updateUserPasswordInfo(Map map) {
		return getSqlMapClientTemplate().update("UserAccount.updateUserPasswordInfo", map);
	}

	public int updateUserInfo(UserAccountModel user) {
		return getSqlMapClientTemplate().update("UserAccount.updateUserInfo", user);
	}

	@Override
	public List<UserAccountModel> getUserAccountInfoForbindMobileFlag() {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("UserAccount.getUserAccountInfoForbindMobileFlag");
	}

	@Override
	public int updateUserInfoBaise(UserAccountModel user)
			throws LotteryException {
		return getSqlMapClientTemplate().update("UserAccount.updateUserInfoBaise", user);
	}

	@Override
	public int updateUserInfoID(UserAccountModel user) throws LotteryException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("UserAccount.updateUserInfoID", user);
	}
	/*
	 * （非 Javadoc）
	 * @see com.success.lottery.account.dao.UserAccountDAO#insertPointTrans(com.success.lottery.account.model.UserPointTrans)
	 */
	public String insertPointTrans(UserPointTrans pointTrans) {
		return (String) getSqlMapClientTemplate().insert("UserAccount.insertPointsTrans", pointTrans);
	}

	@Override
	public int updateUserInfoBank(UserAccountModel user)
			throws LotteryException {
		return getSqlMapClientTemplate().update("UserAccount.updateUserInfoBank", user);
	}

	@Override
	public int selectCountByIdentifier2(String id) {
		return (Integer)getSqlMapClientTemplate().queryForObject("UserAccount.selectCountByIdentifier2",id);
	}
 

}
