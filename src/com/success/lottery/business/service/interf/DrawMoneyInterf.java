/**
 * Title: DrawMoneyInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-27 上午11:31:14
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.HashMap;
import java.util.Map;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * DrawMoneyInterf.java
 * DrawMoneyInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-27 上午11:31:14
 * 
 */

public interface DrawMoneyInterf {
	/*
	 * 程序异常
	 */
	public static final int E_01_CODE = 521001;
	public static final String E_01_DESC = "程序发生异常！";
	
	/*
	 * 业务异常
	 */
	public static final int E_02_CODE = 520001;
	public static final String E_02_DESC = "[1]参数不正确(2)！";
	
	public static final int E_03_CODE = 520002;
	public static final String E_03_DESC = "提现记录不存在！";
	
	public static final int E_04_CODE = 520003;
	public static final String E_04_DESC = "提现记录已经处理不能[1]！";
	
	public static final int E_06_CODE=699001;
	public static final String E_06_DESC="调用快钱提现接口webservice出错！";
	/*
	 *快钱接口提现异常
	 */
	/**
	 * 
	 * Title: requestDrawMoney<br>
	 * Description: <br>
	 *              <br>提交奖金提现申请
	 * @param userId 用户账户id 
	 * @param bank 开卡银行 不能为空
	 * @param bankprovince 开卡银行所在省 不能为空
	 * @param bankcity 开卡银行所在城市 不能为空
	 * @param bankname 开户行名称 不能为空
	 * @param bankcardid 银行卡号 不能为空
	 * @param cardusername 持卡人姓名 不能为空
	 * @param drawMoney 提现金额，单位为分
	 * @param procedurefee 手续费 单位为分
	 * @param reason 提现原因不能为空
	 * @return 生成的提现流水号
	 * @throws LotteryException<br>
	 *                          <br>E_02_CODE
	 *                          <br>E_01_CODE
	 *                          <br>处理账户的异常
	 */
	public String requestDrawPrizeMoney(long userId, String bank,
			String bankprovince, String bankcity, String bankname,
			String bankcardid, String cardusername, int drawMoney,
			int procedurefee, String reason) throws LotteryException;
	/**
	 * 
	 * Title: requestDrawPrizeMoney<br>
	 * Description: <br>
	 *              <br>客户端提现
	 * @param userId 用户id
	 * @param bank 开卡银行 不能为空
	 * @param bankprovince 开卡银行所在省 不能为空
	 * @param bankcity 开卡银行所在城市 不能为空
	 * @param bankname 开户行名称 不能为空
	 * @param bankcardid 银行卡号 不能为空
	 * @param cardusername 持卡人姓名 不能为空
	 * @param drawMoney  提现金额，单位为分
	 * @param procedurefee 手续费 单位为分
	 * @param reason 提现原因不能为空
	 * @param clientId 客户端id
	 * @param clientSeq 客户端生成的流水号
	 * @return ordered#clientId#clientSeq#userId#prizeAccount#reserve
	 * @throws LotteryException
	 */
	public String requestDrawPrizeMoney(long userId, String bank,
			String bankprovince, String bankcity, String bankname,
			String bankcardid, String cardusername, int drawMoney,
			int procedurefee, String reason,String clientId,String clientSeq) throws LotteryException;
	/**
	 * 
	 * Title: agreeDrayMoney<br>
	 * Description: <br>
	 *              <br>同意奖金提现申请 并调用快钱提现接口
	 * @param drawId 提现流水号 不能为空
	 * @param opUser 操作员 不能为空
	 * @param reason 同意原因 不能为空
	 * @throws LotteryException<br>
	 *                          <br>E_02_CODE
	 *                          <br>E_03_CODE
	 *                          <br>E_04_CODE
	 *                          <br>E_01_CODE
	 *                          <br>处理账户的异常
	 */
	public void agreeDrawPrizeMoney(String drawId,String opUser,String reason) throws LotteryException;
	/**
	 * 
	 * Title: rejectDrawMoney<br>
	 * Description: <br>
	 *              <br>拒绝奖金提现申请
	 * @param drawId 提现流水号 不能为空
	 * @param opUser 操作员 不能为空
	 * @param reason 拒绝原因 不能为空
	 * @throws LotteryException<br>
	 *                          <br>E_02_CODE
	 *                          <br>E_03_CODE
	 *                          <br>E_04_CODE
	 *                          <br>E_01_CODE
	 *                          <br>处理账户的异常                       
	 */
	public void rejectDrawPrizeMoney(String drawId,String opUser,String reason) throws LotteryException;
	
	/**
	 * 
	 * Title: adjustAccount<br>
	 * Description: <br>
	 *            本金调整<br>
	 * @param userId 用户账户id 
	 * @param adjustType 调整标识，A:标识本金增加 B:标识本金减少 不能为空
	 * @param adjustMoney 调整金额，单位为分 不能为负数
	 * @param reason 调整说明 长度不能超过20个汉字 不能为空
	 * @param opUser 操作员 不能为空
	 * @return 调整流水号
	 * @throws LotteryException
	 */
	public String adjustAccount(long userId,String adjustType,int adjustMoney,String reason,String opUser) throws LotteryException;
	
	/**
	 * 
	 * Title: clientAdjustAccount<br>
	 * Description: <br>
	 *             <br> 客户端充值
	 * @param userId
	 * @param adjustMoney
	 * @param adjustFee
	 * @param clientId
	 * @param clientSeq
	 * @param reserve
	 * @return orderId#clientId#clientSeq#userId#time#fundsAccount#reserve
	 * @throws LotteryException
	 */
	public String clientAdjustAccount(long userId,int adjustMoney,int adjustFee,String clientId,String clientSeq,String reserve) throws LotteryException;
	
	

}
