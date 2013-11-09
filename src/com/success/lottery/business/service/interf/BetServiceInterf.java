/**
 * Title: BetServiceInterf.java
 * @Package com.success.lottery.business.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-19 下午04:44:03
 * @version V1.0
 */
package com.success.lottery.business.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;

/**
 * com.success.lottery.business.service.interf
 * BetServiceInterf.java
 * BetServiceInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-19 下午04:44:03
 * 
 */

public interface BetServiceInterf {
	
	public static final int E_01_CODE = 500001;
	public static final String E_01_DESC = "投注格式错误！";
	public static final int E_02_CODE = 500002;
	public static final String E_02_DESC = "用户不存在！";
	public static final int E_03_CODE = 500003;
	public static final String E_03_DESC = "用户所在的区域不可以销售该彩种！";
	
	public static final int E_04_CODE = 500004;
	public static final String E_04_DESC = "当前彩期不可以销售！";
	
	public static final int E_05_CODE = 500005;
	public static final String E_05_DESC = "投注串注数和金额未通过校验！";
	
	public static final int E_07_CODE = 500007;
	public static final String E_07_DESC = "投注金额超过了限定金额！";
	
	public static final int E_09_CODE = 500009;
	public static final String E_09_DESC = "用户账户内的金额不够本次投注！";
	
	public static final int E_10_CODE = 500010;
	public static final String E_10_DESC = "投注扣除账户资金出错！";
	
	public static final int E_11_CODE = 500011;
	public static final String E_11_DESC = "用户状态非正常，可能被冻结或注销！";
	
	public static final int E_12_CODE = 500012;
	public static final String E_12_DESC = "追号冻结账户资金出错！";
	
	public static final int E_13_CODE = 500013;
	public static final String E_13_DESC = "投注倍数超过限制！";
	
	public static final int E_15_CODE = 500015;
	public static final String E_15_DESC = "限制号码[1]不能投注！";
	
	public static final int E_16_CODE = 500016;
	public static final String E_16_DESC = "合买提成比例设置不正确,提成比例应为:A";
	
	public static final int E_17_CODE = 500017;
	public static final String E_17_DESC = "合买总份数设置不正确！";
	
	public static final int E_18_CODE = 500018;
	public static final String E_18_DESC = "合买每一份金额设置不正确！";
	
	public static final int E_19_CODE = 500019;
	public static final String E_19_DESC = "合买发起人认购份数不够要求！";
	
	public static final int E_20_CODE = 500020;
	public static final String E_20_DESC = "合买保底份数不能小于总份数的20%！";
	
	public static final int E_21_CODE = 500021;
	public static final String E_21_DESC = "合买保底份数不能小于总份数的20%！";
	
	public static final int E_22_CODE = 500022;
	public static final String E_22_DESC = "参与合买资金冻结交易过程中出现异常%！";
	
	public static final int E_23_CODE = 500023;
	public static final String E_23_DESC = "合买方案投注成功冻结资金扣除交易过程中出现异常！";
	
	public static final int E_24_CODE = 500024;
	public static final String E_24_DESC = "合买方案未查到！";
	public static final int E_25_CODE = 500025;
	public static final String E_25_DESC = "合买方案已经截止不能再参与！";
	public static final int E_26_CODE = 500026;
	public static final String E_26_DESC = "合买方案参与至少认购1份！";
	public static final int E_27_CODE = 500027;
	public static final String E_27_DESC = "合买方案参与认购金额错误！";
	public static final int E_28_CODE = 500028;
	public static final String E_28_DESC = "合买方案参与认购份额超过方案进度！";
	
	public static final int E_29_CODE = 500029;
	public static final String E_29_DESC = "要撤销的订单与撤销人不一致，不能撤销！";
	
	public static final int E_30_CODE = 500030;
	public static final String E_30_DESC = "订单已经不能撤销！";
	
	public static final int E_31_CODE = 500031;
	public static final String E_31_DESC = "方案已经不能撤销";
	
	public static final int E_32_CODE = 500032;
	public static final String E_32_DESC = "错误订单退款时未找到订单";
	
	public static final int E_33_CODE = 500033;
	public static final String E_33_DESC = "错误订单退款时订单不能退款,请检查";
	
	public static final int E_34_CODE = 500034;
	public static final String E_34_DESC = "错误订单退款,订单已经做过退款处理,不能再退款";
	
	public static final int E_35_CODE = 500035;
	public static final String E_35_DESC = "错误订单退款时未找到订单对应的彩票数据";
	
	public static final int E_36_CODE = 500036;
	public static final String E_36_DESC = "错误订单退款时未更新到订单，退款失败";
	
	
	public static final int E_37_CODE = 500037;
	public static final String E_37_DESC = "合买发起人不能撤销发起是认购的份额！";
	
	public static final int E_38_CODE = 500038;
	public static final String E_38_DESC = "合买发起人不能撤销保底的份额！";
	
	public static final int E_39_CODE = 500039;
	public static final String E_39_DESC = "错误订单退款时未找到订单对应的方案信息！";
	
	public static final int E_40_CODE = 500040;
	public static final String E_40_DESC = "错误订单退款时未找到订单对应的参与合买信息！";
	
	/*
	 * 以下的异常属于程序错误
	 */
	public static final int E_06_CODE = 501006;
	public static final String E_06_DESC = "计算注数和金额时出错！";
	
	public static final int E_08_CODE = 501008;
	public static final String E_08_DESC = "配置文件中的投注金额限制配置不正确！";
	
	public static final int E_14_CODE = 501014;
	public static final String E_14_DESC = "程序发生异常！";
	//该异常代表更新或插入数据是未更新到数据，或者查询时未查到需要的信息
	public static final int E_100_CODE = 501100;
	public static final String E_100_DESC = "[OP]！";
	
	/**
	 * 
	 * Title: betWeb<br>
	 * Description: web方式的投注,该方法需要实现以下步骤：
	 *            <br>（1）校验投注字符串格式是否正确
	 *            <br>（2）传入的用户帐号是否存在
	 *            <br>（3）用户所在区域是否可以销售该彩票
	 *            <br>（4）校验当前期是否可以销售
	 *            <br>（5）根据传入的betNum和amount参数校验后台计算的注数和金额是否和传入的一致，如果这两个参数都传入-1则使用后台计算的数据
	 *            <br>（6）校验单笔订单金额是否超过定义的金额限制
	 *            <br>（7）生成方案和订单
	 *            <br>（7.1）判断账户金额是否够用，包括追加期数的金额
	 *            <br>（7.2）生成方案和订单
	 *            <br>（7.3）按照订单编号冻结账户金额(只要生成订单就冻结资金，不用管是否出票成功,对当前期的直接扣除资金，对追号的先冻结资金)
	 *            <br>（7.4）
	 *            <br> 以上步骤如果有一个步骤抛出异常，则整个事务回滚
	 * @param userIdentify 用户标识
	 * @param lotteryId 彩种id
	 * @param playType 玩法id
	 * @param betType  投注方式id
	 * @param betMultiple 投注倍数  计算单笔金额是为：amount*betMultiple，该值只表示传入的参数term（期号）的倍数，该值不能设为负数，不能为0，否则报错
	 *              
	 * @param betNum 投注的注数 如果不设置该值要传入-1
	 * @param amount 投注金额，单位为分,不含追号的投注金额，即投注内容的金额(不能包含投注倍数的金额) ,如果不设置该值要传入-1
	 * @param term 要投注的期号
	 * @param supplementTerms 如有追号则为追号期数列表，如只对当前期投注则为null,参数内容设置为:<期号，倍数>，其中倍数不能为负数，不能为0
	 * @param winStopped 追号时有效，中奖后是否停止追号, true-停止 false-不停止
	 * @param betCode 注码，投注的注码，格式参看 数据状态格式核心定义.xls 彩种玩法投注开奖结果规则表
	 * @return  期号#方案编号#注数#总金额
	 * @throws LotteryException<br>
	 *                          <br>401001:生成方案表数据时发生错误！(程序出错)
	 *                          <br>401002:生成方案订单表数据时发生错误(程序出错)
	 *                          <br>500001:投注格式错误
	 *                          <br>500002:用户不存在
	 *                          <br>500003:用户所在的区域不可以销售该彩种
	 *                          <br>500004:当前彩期不可以销售
	 *                          <br>500005:投注串注数和金额未通过校验 该异常对短信投注没有
	 *                          <br>500007:投注金额超过了限定金额
	 *                          <br>500009:用户账户内的金额不够本次投注
	 *                          <br>500010:投注扣除账户资金出错
	 *                          <br>500011:用户状态非正常，可能被冻结或注销
	 *                          <br>500012:追号冻结账户资金出错
	 *                          <br>AccountService中定义的交易异常
	 *                          <br>501006:计算注数和金额时出错(程序出错)
	 *                          <br>501008:配置文件中的投注金额限制配置不正确(程序出错)
	 *                          <br>501014:投注程序发生异常
	 */
	public String betWeb(String userIdentify, int lotteryId, int playType, int betType,
			int betMultiple,long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;
	/**
	 * 
	 * Title: betWeb<br>
	 * Description: <br>
	 *            web方式的投注<br>
	 * @param userId 用户帐号
	 * @param lotteryId 彩种id
	 * @param playType 玩法id
	 * @param betType 投注方式id
	 * @param betMultiple 投注倍数  计算单笔金额是为：amount*betMultiple，该值只表示传入的参数term（期号）的倍数，该值不能设为负数，不能为0，否则报错
	 *                   
	 * @param betNum 投注的注数 如果不设置该值要传入-1
	 * @param amount 投注金额，单位为分,不含追号的投注金额，即投注内容的金额(不能包含投注倍数的金额) ,如果不设置该值要传入-1
	 * @param term 要投注的期号
	 * @param supplementTerms 如有追号则为追号期数列表，如只对当前期投注则为null,参数内容设置为:<期号，倍数>，其中倍数不能为负数，不能为0
	 * @param winStopped 追号时有效，中奖后是否停止追号, true-停止 false-不停止
	 * @param betCode 注码，投注的注码，格式参看 数据状态格式核心定义.xls 彩种玩法投注开奖结果规则表
	 * @return 期号#方案编号#注数#总金额
	 * @throws LotteryException
	 */
	public String betWeb(long userId, int lotteryId, int playType, int betType,
			int betMultiple,long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;

	
	/**
	 * 
	 * Title: betSms<br>
	 * Description: <br>
	 *            短信投注<br>
	 * @param MobilePhone 电话号码
	 * @param lotteryId 彩种id
	 * @param playType 玩法id
	 * @param betType 投注方式
	 * @param betMultiple 投注倍数  计算单笔金额是为：amount*betMultiple，该值只表示传入的参数term（期号）的倍数，该值不能设为负数，不能为0，否则报错
	 *                   
	 * @param betCode 注码，投注的注码，格式参看 数据状态格式核心定义.xls 彩种玩法投注开奖结果规则表
	 * @return  期号#方案编号#注数#总金额
	 * @throws LotteryException<br>
	 *                          <br>401001:生成方案表数据时发生错误！
	 *                          <br>401002:生成方案订单表数据时发生错误
	 *                          <br>500001:投注格式错误
	 *                          <br>500002:用户不存在
	 *                          <br>500003:用户所在的区域不可以销售该彩种
	 *                          <br>500004:当前彩期不可以销售
	 *                          <br>500005:投注串注数和金额未通过校验 该异常对短信投注没有
	 *                          <br>500006:计算注数和金额时出错
	 *                          <br>500007:投注金额超过了限定金额
	 *                          <br>500008:配置文件中的投注金额限制配置不正确
	 *                          <br>500009:用户账户内的金额不够本次投注
	 *                          <br>500010:投注扣除账户资金出错
	 *                          <br>500011:用户状态非正常，可能被冻结或注销
	 *                          <br>500012:追号冻结账户资金出错
	 *                          <br>301005: 获取lotteryid的当前期信息时数据库出错
	 *                          <br>300005:获取lotteryid的当前期数信息时未找到可以投注的彩期
	 *                          <br>AccountService中定义的交易异常
	 *                          
	 */
	public String betSms(String MobilePhone, int lotteryId, int playType,
			int betType, int betMultiple,String betCode)
			throws LotteryException;
	
	public String betSms(String MobilePhone, int lotteryId,String betTerm, int playType,
			int betType, int betMultiple,String betCode)
			throws LotteryException;
	
	/**
	 * 
	 * Title: betSms<br>
	 * Description: <br>
	 *            短信投注<br>
	 * @param MobilePhone 电话号码
	 * @param lotteryId 彩种id
	 * @param playType 玩法id
	 * @param betType 投注方式
	 * @param betMultiple 投注倍数  计算单笔金额是为：amount*betMultiple，该值只表示传入的参数term（期号）的倍数，该值不能设为负数，不能为0，否则报错
	 *                   
	 * @param betCode 注码，投注的注码，格式参看 数据状态格式核心定义.xls 彩种玩法投注开奖结果规则表
	 * @return  期号#方案编号#注数#总金额
	 * @throws LotteryException<br />
	 *                          <br/>401001:生成方案表数据时发生错误！
	 *                          <br/>401002:生成方案订单表数据时发生错误
	 *                          <br/>500001:投注格式错误
	 *                          <br/>500002:用户不存在
	 *                          <br/>500003:用户所在的区域不可以销售该彩种
	 *                          <br/>500004:当前彩期不可以销售
	 *                          <br>500005:投注串注数和金额未通过校验 该异常对短信投注没有
	 *                          <br>500006:计算注数和金额时出错
	 *                          <br>500007:投注金额超过了限定金额
	 *                          <br>500008:配置文件中的投注金额限制配置不正确
	 *                          <br>500009:用户账户内的金额不够本次投注
	 *                          <br>500010:投注扣除账户资金出错
	 *                          <br>500011:用户状态非正常，可能被冻结或注销
	 *                          <br>500012:追号冻结账户资金出错
	 *                          <br>301005: 获取lotteryid的当前期信息时数据库出错
	 *                          <br>300005:获取lotteryid的当前期数信息时未找到可以投注的彩期
	 *                          <br>AccountService中定义的交易异常
	 *                          
	 */
	public String betSms(long userId, int lotteryId, int playType,
			int betType, int betMultiple,String betCode)
			throws LotteryException;
	/**
	 * 
	 * Title: betSms<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param userId
	 * @param lotteryId
	 * @param betTerm
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betCode
	 * @return
	 * @throws LotteryException
	 */
	public String betSms(long userId, int lotteryId,String betTerm, int playType,
			int betType, int betMultiple,String betCode)
	throws LotteryException;
	
	
	/**
	 * 
	 * Title: betWap<br>
	 * Description: <br>
	 *            wap方式的投注<br>
	 * @param userIdentify 用户标识
	 * @param lotteryId 彩种id
	 * @param playType 玩法id
	 * @param betType 投注方式id
	 * @param betMultiple 投注倍数  计算单笔金额是为：amount*betMultiple，该值只表示传入的参数term（期号）的倍数，该值不能设为负数，不能为0，否则报错
	 *                   
	 * @param betNum 投注的注数 如果不设置该值要传入-1
	 * @param amount 投注金额，单位为分,不含追号的投注金额，即投注内容的金额(不能包含投注倍数的金额) ,如果不设置该值要传入-1
	 * @param term 要投注的期号
	 * @param supplementTerms 如有追号则为追号期数列表，如只对当前期投注则为null,参数内容设置为:<期号，倍数>，其中倍数不能为负数，不能为0
	 * @param winStopped 追号时有效，中奖后是否停止追号, true-停止 false-不停止
	 * @param betCode 注码，投注的注码，格式参看 数据状态格式核心定义.xls 彩种玩法投注开奖结果规则表
	 * @return  期号#方案编号#注数#总金额
	 * @throws LotteryException<br>
	 *                          <br>401001:生成方案表数据时发生错误！
	 *                          <br>401002:生成方案订单表数据时发生错误
	 *                          <br>500001:投注格式错误
	 *                          <br>500002:用户不存在
	 *                          <br>500003:用户所在的区域不可以销售该彩种
	 *                          <br>500004:当前彩期不可以销售
	 *                          <br>500005:投注串注数和金额未通过校验 该异常对短信投注没有
	 *                          <br>500006:计算注数和金额时出错
	 *                          <br>500007:投注金额超过了限定金额
	 *                          <br>500008:配置文件中的投注金额限制配置不正确
	 *                          <br>500009:用户账户内的金额不够本次投注
	 *                          <br>500010:投注扣除账户资金出错
	 *                          <br>500011:用户状态非正常，可能被冻结或注销
	 *                          <br>500012:追号冻结账户资金出错
	 *                          <br>AccountService中定义的交易异常
	 */
	public String betWap(String userIdentify, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;
	/**
	 * 
	 * Title: betWap<br>
	 * Description: <br>
	 *            wap方式的投注<br>
	 * @param userId 用户账户
	 * @param lotteryId 彩种id
	 * @param playType 玩法id
	 * @param betType 投注方式id
	 * @param betMultiple 投注倍数  计算单笔金额是为：amount*betMultiple，该值只表示传入的参数term（期号）的倍数，该值不能设为负数，不能为0，否则报错
	 *                   
	 * @param betNum 投注的注数 如果不设置该值要传入-1
	 * @param amount 投注金额，单位为分,不含追号的投注金额，即投注内容的金额(不能包含投注倍数的金额) ,如果不设置该值要传入-1
	 * @param term 要投注的期号
	 * @param supplementTerms 如有追号则为追号期数列表，如只对当前期投注则为null,参数内容设置为:<期号，倍数>，其中倍数不能为负数，不能为0
	 * @param winStopped 追号时有效，中奖后是否停止追号, true-停止 false-不停止
	 * @param betCode 注码，投注的注码，格式参看 数据状态格式核心定义.xls 彩种玩法投注开奖结果规则表
	 * @return  期号#方案编号#注数#总金额
	 * @throws LotteryException
	 */
	public String betWap(long userId, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;
	
	/**
	 * 
	 * Title: betClient<br>
	 * Description: <br>
	 *              <br>客户端投注接口
	 * @param userId
	 * @param lotteryId
	 * @param playType
	 * @param betType
	 * @param betMultiple
	 * @param betNum
	 * @param amount
	 * @param term
	 * @param supplementTerms
	 * @param winStopped
	 * @param betCode
	 * @return
	 * @throws LotteryException
	 */
	public String betClient(long userId, int lotteryId, int playType, int betType,
			int betMultiple, long betNum, long amount,String term, Map<String,Integer> supplementTerms,
			boolean winStopped, String betCode) throws LotteryException;
	/**
	 * 
	 * Title: coopBetCreate<br>
	 * Description: <br>合买发起投注
	 *              <br>
	 * @param userId 彩民id
	 * @param lotteryId 彩种
	 * @param playType 玩法
	 * @param betType 投注方式
	 * @param betMultiple 投注倍数
	 * @param betNum 投注注数 如果不设置该值要传入-1
	 * @param amount 投注金额，单位为分,即投注内容的金额(不能包含投注倍数的金额) ,如果不设置该值要传入-1
	 * @param term 投注彩期
	 * @param betCode 投注串
	 * @param planopentype 方案公开方式 0-公开 1-购买后公开； ? 2-截止后公开
	 * @param totalunit 总份数
	 * @param unitprice 每份单价
	 * @param selfBuyNum 自己认购份数
	 * @param unitbuyself 保底份数
	 * @param commisionpercent 佣金比例，方案中奖后的佣金比例、合买用，填写10则为 10%
	 * @param plansource 投注方案是从那个渠道来的，0-WEB，1-SMS，2-WAP
	 * @param plantitle 方案标题 可空
	 * @param plandescription 方案介绍 可空
	 * @return
	 * @throws LotteryException
	 */
	public String coopBetCreate(long userId, int lotteryId, int playType, int betType,
			int betMultiple,long betNum, long amount,String term,String betCode,int planOpenType,
			int totalUnit,int unitPrice,int selfBuyNum,int unitBuySelf,int commisionPercent,
			int planSource,String planTitle,String planDescription) throws LotteryException;
	/**
	 * 
	 * Title: coopBetJoin<br>
	 * Description: <br>
	 *              <br>彩民参与合买
	 * @param userId 彩民ID
	 * @param planId 参与方案编号
	 * @param cpUnit 认购分数
	 * @param amount 认购金额，单位分
	 * @return
	 * @throws LotteryException
	 */
	public String coopBetJoin(long userId,String planId,int cpUnit,int amount) throws LotteryException;
	/**
	 * 
	 * Title: revocateJionOrder<br>
	 * Description: <br>
	 *              <br>参与人撤销自己认购的份额
	 * @param userId 用户id
	 * @param coopInfoId 参与信息id
	 * @return 要撤销的参与信息id
	 * @throws LotteryException
	 */
	public String revocateJionOrder(long userId,String coopInfoId) throws LotteryException;
	/**
	 * 
	 * Title: revocateCreatePlan<br>
	 * Description: <br>
	 *              <br>方案的发起人撤销自己创建的方案
	 * @param userId
	 * @param planId
	 * @return
	 * @throws LotteryException
	 */
	public String revocateCreatePlan(long userId,String planId) throws LotteryException;
	/**
	 * 
	 * Title: revocatePlanBySys<br>
	 * Description: <br>
	 *              <br>合买截止是系统对合买方案做处理，撤销或者用保底使方案满员
	 *              <br>调用程序一定要保证该方案已将到了可以撤销或者生成出票订单的时间
	 * @param planId 方案编号 
	 * @throws LotteryException
	 */
	public void dealOnePlanBySys(String planId) throws LotteryException;
	/**
	 * 
	 * Title: daiGouErrOrderDeal<br>
	 * Description: <br>
	 *              <br>代购的错误订单退款
	 * @param orderId
	 * @return 彩种#彩期#订单号#退款金额#处理结果
	 * @throws LotteryException
	 */
	public String daiGouErrOrderDeal(String orderId) throws LotteryException;
	/**
	 * 
	 * Title: heMaiErrOrderDeal<br>
	 * Description: <br>
	 *              <br>合买错误订单退款处理
	 * @param orderId
	 * @return 彩种#彩期#订单号#退款金额#退款明细#处理结果
	 * @throws LotteryException
	 */
	public String heMaiErrOrderDeal(String orderId) throws LotteryException;

}
