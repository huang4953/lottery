/**
 * @Title: DrayMoneyDaoImpl.java
 * @Package com.success.lottery.account.dao.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-27 上午10:28:25
 * @version V1.0
 */
package com.success.lottery.account.dao.impl;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.success.lottery.account.model.DrawMoneyDomain;
import com.success.lottery.account.model.UserAccountModel;

/**
 * com.success.lottery.account.dao.impl
 * DrayMoneyDaoImpl.java
 * DrayMoneyDaoImpl
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-27 上午10:28:25
 * 
 */

public class DrayMoneyDaoImpl extends SqlMapClientDaoSupport implements
		Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 3245525550549497052L;
	
	public String insertDrawMoney(DrawMoneyDomain draw){
		return (String)this.getSqlMapClientTemplate().insert("drawMoney.insertDrawMoney", draw);
	}
	
	public int updateDrawMoney(DrawMoneyDomain draw){
		return this.getSqlMapClientTemplate().update("drawMoney.updateDrawMoney", draw);
	}
	
	public DrawMoneyDomain queryDrawMoneyInfo(String drawId){
		return (DrawMoneyDomain)this.getSqlMapClientTemplate().queryForObject("drawMoney.queryInfoById", drawId);
	}
	
	@SuppressWarnings("unchecked")
	public List<DrawMoneyDomain> queryDrawMoneyInfo(long userId,List<Integer> drawType,List<Integer> drawstatus){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("drawType", drawType);
		param.put("drawstatus", drawstatus);
		return this.getSqlMapClientTemplate().queryForList("drawMoney.queryInfoByUserStatus", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<DrawMoneyDomain> queryDrawMoneyInfo(List<Integer> drawType,List<Integer> drawstatus){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("drawType", drawType);
		param.put("drawstatus", drawstatus);
		return this.getSqlMapClientTemplate().queryForList("drawMoney.queryInfoByStatus", param);
	}
	
	public DrawMoneyDomain queryDrawMoneyInfoForUpdate(String drawId){
		return (DrawMoneyDomain)this.getSqlMapClientTemplate().queryForObject("drawMoney.queryInfoByIdForUpdate", drawId);
	}
	
	@SuppressWarnings("unchecked")
	public List<DrawMoneyDomain> queryDrawWithUserInfo(String province,
			String city, String accountId, String cardusername,String accountusername,
			String beginTime, String endTime, List<Integer> drawstatus,String opUser,
			int drawType, int isLimit, int startPageNumber, int endPageNumber) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("province", province);
		param.put("city", city);
		param.put("accountId", accountId);
		param.put("cardusername", cardusername);
		param.put("accountusername", accountusername);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("drawstatus", drawstatus);
		param.put("drawType", drawType);
		param.put("opUser", opUser);
		param.put("isLimit", isLimit);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.getSqlMapClientTemplate().queryForList("drawMoney.queryDrawWithUserInfo", param);
	}
	
	public int queryDrawWithUserInfoCount(String province,
			String city, String accountId, String cardusername,String accountusername,
			String beginTime, String endTime, List<Integer> drawstatus,String opUser,
			int drawType){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("province", province);
		param.put("city", city);
		param.put("accountId", accountId);
		param.put("cardusername", cardusername);
		param.put("accountusername", accountusername);
		param.put("beginTime", beginTime);
		param.put("endTime", endTime);
		param.put("drawstatus", drawstatus);
		param.put("opUser", opUser);
		param.put("drawType", drawType);
		return (Integer)this.getSqlMapClientTemplate().queryForObject("drawMoney.queryDrawWithUserInfoCount", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserAccountModel> queryAdjustAccountUserInfo(String userIdentify, String userName,String areaCode,int startPageNumber, int endPageNumber){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("accountId", userIdentify);
		param.put("userName", userName);
		param.put("areaCode", areaCode);
		param.put("startPageNumber", startPageNumber);
		param.put("endPageNumber", endPageNumber);
		return this.getSqlMapClientTemplate().queryForList("drawMoney.queryAdjustAccountUserInfo", param);
	}
	
	public int queryAdjustAccountUserCount(String userIdentify,String userName,String areaCode){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("accountId", userIdentify);
		param.put("userName", userName);
		param.put("areaCode", areaCode);
		return (Integer)this.getSqlMapClientTemplate().queryForObject("drawMoney.queryAdjustAccountUserCount", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<DrawMoneyDomain> getUserDrawMoney(long userId, Timestamp startDate, Timestamp endDate, List<Integer> drawStatus, int start, int count){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("drawStatus", drawStatus);
		param.put("start", start <= 0 ? 0 : start);
		param.put("count", count <= 0 ? 0 : count);
		return this.getSqlMapClientTemplate().queryForList("drawMoney.getUserDrawMoney", param);
	}
	
	@SuppressWarnings("unchecked")
	public List<DrawMoneyDomain> getUserDrawMoney(long userId, Timestamp startDate, Timestamp endDate, List<Integer> drawStatus){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("drawStatus", drawStatus);
		return this.getSqlMapClientTemplate().queryForList("drawMoney.getUserDrawMoneyCount", param);
	}
}
