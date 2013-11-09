/**
 * Title: DrawMoneyService.java
 * @Package com.success.lottery.account.service
 * Description: (用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-26 下午07:41:07
 * @version V1.0
 */
package com.success.lottery.account.service;

import java.util.Date;
import java.util.List;

import com.success.lottery.account.model.DrawMoneyDomain;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.account.service
 * DrawMoneyService.java
 * DrawMoneyService
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-26 下午07:41:07
 * 
 */

public interface BaseDrawMoneyService {
	
	public static final int E_01_CODE = 101601;
	public static final String E_01_DESC = "程序异常";

	/**
	 * 
	 * Title: insertDrawMoney<br>
	 * Description: <br>
	 *            插入提现表<br>
	 * @param drawMoneyDomain 提现申请对象
	 * @return 提现流水号
	 * @throws LotteryException
	 */
	public String insertDrawMoney(DrawMoneyDomain drawMoneyDomain) throws LotteryException;
	
	/**
	 * 
	 * Title: updateDrawMoney<br>
	 * Description: <br>
	 *            更新提现表<br>
	 * @param drawid 提现id
	 * @param opUser 操作员
	 * @param drawstatus 提现状态，0:申请，1:申请通过 ,2:申请拒绝
	 * @param dealreason 拒绝原因或通过原因
	 * @param reserve 备用字段
	 * @return 成功更新的记录数
	 * @throws LotteryException
	 */
	public int updateDrawMoney(String drawid,String opUser,int drawstatus,String dealreason,String reserve) throws LotteryException;
	
	/**
	 * 
	 * Title: queryDrawMoneyInfo<br>
	 * Description: <br>
	 *            根据提现id查询提现信息<br>
	 * @param drawId 提现id
	 * @return DrawMoneyDomain
	 * @throws LotteryException
	 */
	public DrawMoneyDomain queryDrawMoneyInfo(String drawId) throws LotteryException;
	
	/**
	 * 
	 * Title: queryDrawMoneyInfo<br>
	 * Description: <br>
	 *            根据用户账户和提现状态查询用户的提现信息<br>
	 * @param userId 用户账户
	 * @param drawType 提现类别 0:奖金提现
	 * @param drawstatus 状态 0默认申请提交状态,1申请通过,2申请不通过
	 * @return List<DrawMoneyDomain>
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawMoneyInfo(long userId,List<Integer> drawType,List<Integer> drawstatus) throws LotteryException;
	
	/**
	 * 
	 * Title: queryDrawMoneyInfo<br>
	 * Description: <br>
	 *              <br>查询提现记录，需要审核的
	 * @param province
	 * @param city
	 * @param userIdentify
	 * @param userName
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawMoneyInfo(String province,String city,String userIdentify,String userName,String beginTime,String endTime) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawMoneyHisInfo<br>
	 * Description: <br>
	 *              <br> 查询提现历史信息
	 * @param province
	 * @param city
	 * @param userIdentify
	 * @param userName
	 * @param beginTime
	 * @param endTime
	 * @param opUser
	 * @param drawStatus
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawMoneyHisInfo(String province,String city,String userIdentify,String userName,String beginTime,String endTime,String opUser,String drawStatus,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawAccountHisInfo<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param userIdentify
	 * @param accountUserName
	 * @param beginTime
	 * @param endTime
	 * @param opUser
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawAccountHisInfo(String userIdentify,String accountUserName,String beginTime,String endTime,String opUser,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawAccountHisCount<br>
	 * Description: <br>
	 *              <br>
	 * @param userIdentify
	 * @param accountUserName
	 * @param beginTime
	 * @param endTime
	 * @param opUser
	 * @return
	 * @throws LotteryException
	 */
	public int queryDrawAccountHisCount(String userIdentify,String accountUserName,String beginTime,String endTime,String opUser) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawMoneyHisCount<br>
	 * Description: <br>
	 *              <br>查询提现历史信息记录数
	 * @param province
	 * @param city
	 * @param userIdentify
	 * @param userName
	 * @param beginTime
	 * @param endTime
	 * @param opUser
	 * @param drawStatus
	 * @return
	 * @throws LotteryException
	 */
	public int queryDrawMoneyHisCount(String province,String city,String userIdentify,String userName,String beginTime,String endTime,String opUser,String drawStatus) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryDrawMoneyInfo<br>
	 * Description: <br>
	 *            根据提现状态查询提现信息<br>
	 * @param drawType 提现类别 0:奖金提现
	 * @param drawstatus 提现状态 0默认申请提交状态,1申请通过,2申请不通过
	 * @return  List<DrawMoneyDomain>
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> queryDrawMoneyInfo(List<Integer> drawType,List<Integer> drawstatus) throws LotteryException;
	/**
	 * 
	 * Title: queryAdjustAccountUserInfo<br>
	 * Description: <br>
	 *              <br>查询可以调整额度的用户信息
	 * @param userIdentify
	 * @param pageIndex
	 * @param perPageNumber
	 * @return
	 * @throws LotteryException
	 */
	public List<UserAccountModel> queryAdjustAccountUserInfo(String userIdentify,String userName,String areaCode,int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: queryAdjustAccountUserCount<br>
	 * Description: <br>
	 *              <br>查询可以调整额度的用户信息统计
	 * @param userIdentify
	 * @return
	 * @throws LotteryException
	 */
	public int queryAdjustAccountUserCount(String userIdentify,String userName,String areaCode) throws LotteryException;
	/**
	 * 
	 * Title: queryDrawMoneyInfoForUpdate<br>
	 * Description: <br>
	 *            获取提现信息并锁定该记录<br>
	 * @param drawId
	 * @return DrawMoneyDomain
	 * @throws LotteryException
	 */
	public DrawMoneyDomain queryDrawMoneyInfoForUpdate(String drawId) throws LotteryException;
	
	/**
	 * 查询用户提现记录，对于开始时间是取大于等于当日0时0分0秒，对于截至时间是取小于等于当日 23:59:59
	 * @param userId
	 *		用户id 
	 * @param startDate
	 * 		开始时间，只有年月日，如果没有开始时间则填写 null 
	 * @param endDate
	 * 		截至时间，如果没有截至时间则填写 null 
	 * @param drawStatus
	 * 		查询所有填写-1，提现状态，列表如下：
	 *          -1 - 全部
	 * 			0 - 待处理
	 * 			1 - 已通过
	 * 			2 - 已拒绝
	 * @param start
	 * @param count
	 * @return
	 * 		返回查询到的交易变动；
	 * 		其中的本次查询到的总条数可以通过 drawstatus + drawmoney + procedurefee计算得出；
	 * 		也可通过drawid字段得出，但是drawid字段为String类型，需要自行转换为int
	 * 		该列表中index为0的DrawMoneyDomain的以下字段中存放不同的值：
	 * 			drawstatus：提现成功的条数
	 * 			userId：提现成功的总金额
	 * 			drawmoney：提现被拒绝的总条数
	 * 			drawtype：提现被拒绝的金额
	 * 			procedurefee：提现待处理的条数
	 * 			operatetype：提现待处理的金额
	 * 			drawid：存放的为本次查询到的总条数，如没有查询到也可获得该值为0; 特别要注意该字段类型为String，需要自行转换为int
	 * @throws LotteryException
	 */
	public List<DrawMoneyDomain> getUserDrawMoney(long userId, Date startDate, Date endDate, int drawStatus, int start, int count) throws LotteryException;
	
}
