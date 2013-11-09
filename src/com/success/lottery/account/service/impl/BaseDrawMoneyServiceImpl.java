/**
 * Title: DrawMoneyServiceImpl.java
 * @Package com.success.lottery.account.service.impl
 * Description: 提现表基础操作，没有处理业务逻辑
 * @author gaoboqin
 * @date 2010-4-27 上午10:27:12
 * @version V1.0
 */
package com.success.lottery.account.service.impl;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.dao.impl.DrayMoneyDaoImpl;
import com.success.lottery.account.model.DrawMoneyDomain;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.account.service.BaseDrawMoneyService;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.util.LotterySequence;

/**
 * com.success.lottery.account.service.impl
 * DrawMoneyServiceImpl.java
 * DrawMoneyServiceImpl
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-27 上午10:27:12
 * 
 */

public class BaseDrawMoneyServiceImpl implements BaseDrawMoneyService {
	private static Log logger = LogFactory.getLog(BaseDrawMoneyServiceImpl.class);
	public static final String PROFIX_SEQ = "TX";
	private DrayMoneyDaoImpl drawMoneyDao;

	/*
	* (非 Javadoc)
	*Title: insertDrawMoney
	*Description: 
	* @param drawMoneyDomain
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#insertDrawMoney(com.success.lottery.account.model.DrawMoneyDomain)
	 */
	public String insertDrawMoney(DrawMoneyDomain drawMoneyDomain) throws LotteryException {
		try {
			drawMoneyDomain.setDrawid(LotterySequence.getInstatce(PROFIX_SEQ).getSequence());
			return this.getDrawMoneyDao().insertDrawMoney(drawMoneyDomain);
		} catch (Exception e) {
			logger.error("insertDrawMoney error", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
	}

	/* (非 Javadoc)
	 *Title: queryDrawMoneyInfo
	 *Description: 
	 * @param drawId
	 * @return
	 * @throws LotteryException
	 * @see com.success.lottery.account.service.DrawMoneyService#queryDrawMoneyInfo(java.lang.String)
	 */
	public DrawMoneyDomain queryDrawMoneyInfo(String drawId)
			throws LotteryException {
		try {
			return this.getDrawMoneyDao().queryDrawMoneyInfo(drawId);
		} catch (Exception e) {
			logger.error("queryDrawMoneyInfo error", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
	}

	/*
	 * (非 Javadoc)
	*Title: queryDrawMoneyInfo
	*Description: 
	* @param userId
	* @param drawType
	* @param drawstatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#queryDrawMoneyInfo(long, java.util.List, java.util.List)
	 */
	public List<DrawMoneyDomain> queryDrawMoneyInfo(long userId,List<Integer> drawType,
			List<Integer> drawstatus) throws LotteryException {
		try {
			return this.getDrawMoneyDao().queryDrawMoneyInfo(userId, drawType,drawstatus);
		} catch (Exception e) {
			logger.error("queryDrawMoneyInfo error", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
	}

	/*
	 * (非 Javadoc)
	*Title: queryDrawMoneyInfo
	*Description: 
	* @param drawType
	* @param drawstatus
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#queryDrawMoneyInfo(java.util.List, java.util.List)
	 */
	public List<DrawMoneyDomain> queryDrawMoneyInfo(List<Integer> drawType,List<Integer> drawstatus)
			throws LotteryException {
		try {
			return this.getDrawMoneyDao().queryDrawMoneyInfo(drawType,drawstatus);
		} catch (Exception e) {
			logger.error("queryDrawMoneyInfo error", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
	}

	/*
	 * (非 Javadoc)
	*Title: updateDrawMoney
	*Description: 
	* @param drawid
	* @param opUser
	* @param drawstatus
	* @param dealreason
	* @param reserve
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#updateDrawMoney(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public int updateDrawMoney(String drawid,String opUser, int drawstatus,
			String dealreason, String reserve) throws LotteryException {
		try {
			DrawMoneyDomain draw = new DrawMoneyDomain();
			draw.setDrawid(drawid);
			draw.setDrawstatus(drawstatus);
			draw.setDealreason(dealreason);
			draw.setOpuser(opUser);
			draw.setReserve(reserve);
			return this.getDrawMoneyDao().updateDrawMoney(draw);
		} catch (Exception e) {
			logger.error("queryDrawMoneyInfo error", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
	}
	/*
	 * (非 Javadoc)
	*Title: queryDrawMoneyInfoForUpdate
	*Description: 
	* @param drawId
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#queryDrawMoneyInfoForUpdate(java.lang.String)
	 */
	public DrawMoneyDomain queryDrawMoneyInfoForUpdate(String drawId) throws LotteryException {
		try {
			return this.getDrawMoneyDao().queryDrawMoneyInfoForUpdate(drawId);
		} catch (Exception e) {
			logger.error("queryDrawMoneyInfoForUpdate error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
	}

	private DrayMoneyDaoImpl getDrawMoneyDao() {
		return drawMoneyDao;
	}

	public void setDrawMoneyDao(DrayMoneyDaoImpl drawMoneyDao) {
		this.drawMoneyDao = drawMoneyDao;
	}
	/*
	 * (非 Javadoc)
	*Title: queryDrawMoneyInfo
	*Description: 
	* @param province
	* @param city
	* @param userIdentify
	* @param userName
	* @param beginTime
	* @param endTime
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#queryDrawMoneyInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<DrawMoneyDomain> queryDrawMoneyInfo(String province, String city, String userIdentify, String userName, String beginTime, String endTime) throws LotteryException {
		List<DrawMoneyDomain> result = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(beginTime)){
				begin_time_f = sdf.format(sdf.parse(beginTime.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(endTime)){
				end_time_f = sdf.format(sdf.parse(endTime.trim() + " 24:00:00"));
			}
			List<Integer> drawStatus = new ArrayList<Integer>();
			drawStatus.add(0);
			result = this.getDrawMoneyDao().queryDrawWithUserInfo(province, city, userIdentify, userName,null, begin_time_f, end_time_f, drawStatus,null, 0, -1, 0, 0);
		}catch(Exception e){
			logger.error("queryDrawMoneyInfo error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		return result;
	}

	public int queryDrawMoneyHisCount(String province, String city, String userIdentify, String userName, String beginTime, String endTime, String opUser,String drawStatus) throws LotteryException {
		int result = 0;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(beginTime)){
				begin_time_f = sdf.format(sdf.parse(beginTime.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(endTime)){
				end_time_f = sdf.format(sdf.parse(endTime.trim() + " 24:00:00"));
			}
			List<Integer> drawStatusList = new ArrayList<Integer>();
			
			if(StringUtils.isEmpty(drawStatus) || "-1".equals(drawStatus)){
				drawStatusList.add(0);
				drawStatusList.add(1);
				drawStatusList.add(2);
			}else{
				drawStatusList.add(Integer.parseInt(drawStatus));
			}
			result = this.getDrawMoneyDao().queryDrawWithUserInfoCount(province, city, userIdentify, userName, null,begin_time_f, end_time_f, drawStatusList, opUser, 0);
		}catch(Exception e){
			logger.error("queryDrawMoneyHisCount error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		return result;
	}

	public List<DrawMoneyDomain> queryDrawMoneyHisInfo(String province, String city, String userIdentify, String userName, String beginTime, String endTime, String opUser,String drawStatus, int pageIndex, int perPageNumber) throws LotteryException {
		List<DrawMoneyDomain> result = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(beginTime)){
				begin_time_f = sdf.format(sdf.parse(beginTime.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(endTime)){
				end_time_f = sdf.format(sdf.parse(endTime.trim() + " 24:00:00"));
			}
			List<Integer> drawStatusList = new ArrayList<Integer>();
			
			if(StringUtils.isEmpty(drawStatus) || "-1".equals(drawStatus)){
				drawStatusList.add(0);
				drawStatusList.add(1);
				drawStatusList.add(2);
			}else{
				drawStatusList.add(Integer.parseInt(drawStatus));
			}
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			
			result = this.getDrawMoneyDao().queryDrawWithUserInfo(province, city, userIdentify, userName,null, begin_time_f, end_time_f, drawStatusList, opUser, 0, 0, startNumber, perPageNumber);
		}catch(Exception e){
			logger.error("queryDrawMoneyHisInfo error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: queryAdjustAccountUserCount
	*Description: 
	* @param userIdentify
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#queryAdjustAccountUserCount(java.lang.String)
	 */
	public int queryAdjustAccountUserCount(String userIdentify,String userName,String areaCode) throws LotteryException {
		int result = 0;
		try{
			result = this.getDrawMoneyDao().queryAdjustAccountUserCount(userIdentify,userName,areaCode);
		}catch(Exception e){
			logger.error("queryAdjustAccountUserCount error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: queryAdjustAccountUserInfo
	*Description: 
	* @param userIdentify
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#queryAdjustAccountUserInfo(java.lang.String, int, int)
	 */
	public List<UserAccountModel> queryAdjustAccountUserInfo(String userIdentify,String userName,String areaCode, int pageIndex, int perPageNumber) throws LotteryException {
		List<UserAccountModel> result = null;
		try{
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			result = this.getDrawMoneyDao().queryAdjustAccountUserInfo(userIdentify,userName,areaCode, startNumber, perPageNumber);
			
		}catch(Exception e){
			logger.error("queryAdjustAccountUserInfo error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: queryDrawAccountHisCount
	*Description: 
	* @param userIdentify
	* @param accountUserName
	* @param beginTime
	* @param endTime
	* @param opUser
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#queryDrawAccountHisCount(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int queryDrawAccountHisCount(String userIdentify, String accountUserName, String beginTime, String endTime, String opUser) throws LotteryException {
		int result = 0;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(beginTime)){
				begin_time_f = sdf.format(sdf.parse(beginTime.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(endTime)){
				end_time_f = sdf.format(sdf.parse(endTime.trim() + " 24:00:00"));
			}
			List<Integer> drawStatusList = new ArrayList<Integer>();
			drawStatusList.add(3);
			drawStatusList.add(4);
			
			result = this.getDrawMoneyDao().queryDrawWithUserInfoCount(null, null, userIdentify, null, accountUserName,begin_time_f, end_time_f, drawStatusList, opUser, 1);
		}catch(Exception e){
			logger.error("queryDrawMoneyHisCount error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		return result;
	}
	/*
	 * (非 Javadoc)
	*Title: queryDrawAccountHisInfo
	*Description: 
	* @param userIdentify
	* @param accountUserName
	* @param beginTime
	* @param endTime
	* @param opUser
	* @param pageIndex
	* @param perPageNumber
	* @return
	* @throws LotteryException
	* @see com.success.lottery.account.service.BaseDrawMoneyService#queryDrawAccountHisInfo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	public List<DrawMoneyDomain> queryDrawAccountHisInfo(String userIdentify, String accountUserName, String beginTime, String endTime, String opUser, int pageIndex, int perPageNumber) throws LotteryException {
		List<DrawMoneyDomain> result = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String begin_time_f = null;
			String end_time_f = null;
			if(StringUtils.isNotEmpty(beginTime)){
				begin_time_f = sdf.format(sdf.parse(beginTime.trim() + " 00:00:00"));
			}
			if(StringUtils.isNotEmpty(endTime)){
				end_time_f = sdf.format(sdf.parse(endTime.trim() + " 24:00:00"));
			}
			List<Integer> drawStatusList = new ArrayList<Integer>();
			drawStatusList.add(3);
			drawStatusList.add(4);
			
			int startNumber = 0;
			if(pageIndex > 0 && perPageNumber > 0){
				startNumber = perPageNumber * (pageIndex - 1);
			}
			
			result = this.getDrawMoneyDao().queryDrawWithUserInfo(null, null, userIdentify, null,accountUserName, begin_time_f, end_time_f, drawStatusList, opUser, 1, 0, startNumber, perPageNumber);
		}catch(Exception e){
			logger.error("queryDrawMoneyHisInfo error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		return result;
	}

	@Override
	public List<DrawMoneyDomain> getUserDrawMoney(long userId, Date startDate, Date endDate, int drawStatus, int start, int count) throws LotteryException{
		List<DrawMoneyDomain> result = null;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp startTime = null, endTime = null;
			if(startDate != null){
				startTime = Timestamp.valueOf(format.format(startDate) + " 00:00:00");
			}
			if(endDate != null){
				endTime = Timestamp.valueOf(format.format(endDate) + " 23:59:59");
			}
			List<Integer> drawStatusList = new ArrayList<Integer>();
			
			if(drawStatus == -1){
				drawStatusList.add(0);
				drawStatusList.add(1);
				drawStatusList.add(2);
			}else{
				drawStatusList.add(drawStatus);
			}
			result = this.getDrawMoneyDao().getUserDrawMoney(userId, startTime, endTime, drawStatusList, start, count);
			/*
			 *  * 		该列表中index为0的DrawMoneyDomain的以下字段中存放不同的值：
	 * 			drawstatus：提现成功的条数
	 * 			userId：提现成功的总金额
	 * 			drawmoney：提现被拒绝的总条数
	 * 			drawtype：提现被拒绝的金额
	 * 			procedurefee：提现待处理的条数
	 * 			operatetype：提现待处理的金额
	 * 			drawid：存放的为本次查询到的总条数，如没有查询到也可获得该值为0; 特别要注意该字段类型为String，需要自行转换为int
			 */
			if(result != null){
				int totalNum =0 ,drawstatus = 0,userIdS = 0,drawmoney = 0,drawtype = 0,procedurefee = 0,operatetype = 0,drawid = 0;
				DrawMoneyDomain totalDomain = new DrawMoneyDomain();
				totalDomain.setDrawid("0");
				List<DrawMoneyDomain> totalList = this.getDrawMoneyDao().getUserDrawMoney(userId, startTime, endTime, drawStatusList);
				if(totalList != null){
					for(DrawMoneyDomain one : totalList){
						totalNum += one.getDrawtype();
						int status = one.getDrawstatus();
						switch (status) {
						case 0://申请
							procedurefee += one.getDrawtype();
							operatetype += one.getUserid();
							break;
						case 1://通过
							drawstatus += one.getDrawtype();
							userIdS += one.getUserid();
							break;
						case 2://拒绝
							drawmoney += one.getDrawtype();
							drawtype += one.getUserid();
							break;
						default:
							break;
						}
					}
					totalDomain.setDrawid(String.valueOf(totalNum));
					totalDomain.setDrawstatus(drawstatus);
					totalDomain.setUserid(userIdS);
					totalDomain.setDrawmoney(drawmoney);
					totalDomain.setDrawtype(drawtype);
					totalDomain.setProcedurefee(procedurefee);
					totalDomain.setOperatetype(operatetype);
				}
				result.add(0, totalDomain);
			}
			
		}catch(Exception e){
			logger.error("getUserDrawMoney error:", e);
			throw new LotteryException(E_01_CODE,E_01_DESC);
		}
		return result;
	}
}
