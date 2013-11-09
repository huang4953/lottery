/**
 * Title: LotteryAbstractTool.java
 * @Package com.success.lottery.util
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-3-26 下午01:13:07
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * com.success.lottery.util.core
 * LotteryAbstractTool.java
 * LotteryAbstractTool
 * 彩种抽象类，定义一些彩种的投注方式的校验规则以及一些可以公用的方法实现
 * @author gaoboqin
 * 2010-3-26 下午01:13:07
 *
 */
public abstract class LotteryAbstractTool {
	
	/**
	 * 超级大乐透
	 */
	@SuppressWarnings("unused")
	////////////////////////////////////////////////////////////////////////////////////
	protected static final String SUPER_PLATTYPE_ADDITIONAL = "1";//追加
	protected static final String SUPER_PLATTYPE_NO_ADDITIONAL = "0";//不追加
	protected static final String SUPER_BETTYPE_SINGLE = "0";//单式
	protected static final String SUPER_BETTYPE_DUPLEX = "1";//复式
	protected static final String SUPER_BETTYPE_DANTUO = "2";//胆拖
	protected static final String SUPER_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	/**
	 * 0-单式
	 * 在1-35中任选5个不重复的号码作为前区，1-12中任选2个不重复的号码作为后区号码，1-9前面用0补齐。
	 * 重复问题采用另外的方法判断
	 */
	protected static final String SUPER_BET_0_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|3[0-5]){5}\\|(0[1-9]|1[0-2]){2}$";//0-单式
	/**
	 * 1-复式
	 * 在1-35中任选M个不重复的号码作为前区，1-12中任选N个不重复的号码作为后区号码，当满足（M>=6且N=2）或者（M=5且N>=3）或者(M>=6且N>=3)时即为复式
	 * 
	 * 变更后
	 * 在1-35中任选M个不重复的号码作为前区，1-12中任选N个不重复的号码作为后区号码，M>=5并且N>=2两位一个号码，
	 * 不足两位左补'0'，前区和后区使用"|"分隔；多注之间用"^"分隔010203040523|060711^08091011122425|080901
	 * 
	 */
	//protected static final String SUPER_BET_1_REGULAR = "^((0[1-9]|1\\d|2\\d|3[0-5]){6,}\\|(0[1-9]|1[0-2]){2}|(0[1-9]|1\\d|2\\d|3[0-5]){5}\\|(0[1-9]|1[0-2]){3,}|(0[1-9]|1\\d|2\\d|3[0-5]){6,}\\|(0[1-9]|1[0-2]){3,})$";//1-复式
	protected static final String SUPER_BET_1_REGULAR = "^((0[1-9]|1\\d|2\\d|3[0-5]){5,}\\|(0[1-9]|1[0-2]){2,})$";//1-复式
	/**
	 * 从1-35中选择M（0<M<=4）个数字作为胆码，选择N（N+M>=6且N>=2）个不重复且不包含胆码的数字作为拖码组成前区投注；
	 * 从1-12中选择M（0<M<=1）个数字作为胆码，选择N（N+M>=3且N>=2）个不重复且不包含胆码的数字作为拖码组成后区投注。
	 * 两位一个号码，不足两位左补'0'，前区和后区使用"|"分隔；胆码和拖码之间使用"*"分隔，胆码在前
	 * 号码的重复以及总数需要另外校验
	 * 
	 * 变更后
	 *  前区胆码M(1<=M<=4)，拖码N(N>=1 and M+N>=5)，并且胆码拖码不能重复；
	 *  后区胆码M(M>=0 and M<=1)，拖码N(N>=1 and M+N>=2)两位一个号码，
	 *  不足两位左补'0'，前区和后区使用"|"分隔，胆码拖码之间用*分隔；如果后区胆码为0，则后区胆码位置不填；胆码在前拖码在后；
	 *  多注之间用"^"分隔；普通胆拖：01020*3040506|01*0203^01020304*05|01*02当后区M=0时：01020*3040506|*020304
	 * 
	 */
	//protected static final String SUPER_BET_2_REGULAR = "^(0[1-9]|1\\d|2\\d|3[0-5]){1,4}\\*(0[1-9]|1\\d|2\\d|3[0-5]){2,}\\|(0[1-9]|1[0-2]){1}\\*(0[1-9]|1[0-2]){2,}$";//2-胆拖
	protected static final String SUPER_BET_2_REGULAR = "^(0[1-9]|1\\d|2\\d|3[0-5]){1,4}\\*(0[1-9]|1\\d|2\\d|3[0-5]){1,}\\|(0[1-9]|1[0-2]){0,1}\\*(0[1-9]|1[0-2]){1,}$";//2-胆拖
	
    ////////////////////////////////////////////////////////////////////////////////////
	//生肖乐
	protected static final String HAPPY_PLAYTYPE_NO_ADDITIONAL = "0";//不追加
	protected static final String HAPPY_BETTYPE_SINGLE = "0";//单式
	protected static final String HAPPY_BETTYPE_DUPLEX = "1";//复式
	protected static final String HAPPY_BETTYPE_DANTUO = "2";//胆拖
	protected static final String HAPPY_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/**
	 * 在1-12共12个数字中任意选择2个数进行投注，单票最多5注直选号码。号码1-9前面用0补齐
	 * 两位一个号码，不足两位左补'0'；最多可以5注，之间用"^"分隔
	 * 单票最多5注另外程序校验
	 */
	protected static final String HAPPY_BET_0_REGULAR = "^(0[1-9]|1[0-2]){2}$";//0-单式
	/**
	 * 在1-12共12个数字中任意选择2个数进行投注，号码1-9前面用0补齐。所选的号码中任意2个号码的组合数即为总注数。
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 * 
	 * 变更后
	 * 在1-12共12个数字中任意选择M(M>=2)个数进行投注两位一个号码，不足两位左补'0'，前区和后区使用"|"分隔；多注之间用"^"分隔01020304^0809
	 * 
	 */
	protected static final String HAPPY_BET_1_REGULAR = "^(0[1-9]|1[0-2]){2,}$";//1-复式
	/**
	 * 在1-12共12个数字中任意选择2个数进行投注，从1-12中选择M（0<M<=1）个数字作为胆码，选择N（N+M>=3且N>=2）个不重复且不包含胆码的数字作为拖码。
	 * 两位一个号码，不足两位左补'1'；胆码和拖码之间使用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔  01*0203^03*0506
	 * 
	 */
	protected static final String HAPPY_BET_2_REGULAR = "^(0[1-9]|1[0-2]){1}\\*(0[1-9]|1[0-2]){2,}$";//2-胆拖
	
	/////////////////////////////////////////////////////////////////////////////////////
	//七星彩
	protected static final String COLOR_PLAYTYPE_NO_ADDITIONAL = "0";//不追加
	protected static final String COLOR_BETTYPE_SINGLE = "0";//单式
	protected static final String COLOR_BETTYPE_DUPLEX = "1";//复式
	protected static final String COLOR_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/**
	 * 在0-9共10个数字中任意选择7个数进行投注，单票最多5注直选号码
	 * 按照顺序一位一个号码，号码之间用"*"隔开。做多可以5注，之间用"^"分隔。
	 * 0*1*2*3*4*5*6
	 * 
	 */
	protected static final String COLOR_BET_0_REGULAR = "^(\\d\\*){6}(\\d){1}$";//0-单式
	/**
	 * 在0-9共10个数字中任意选择7个数进行投注，每位均可选择1-10个号码。每一位号码的个数相乘即为总注数。
	 * 一位数字代表一位号码，各个位置号码之间用"*"隔开。每个位置可以填写多个号码。最多可以？注，之间用"^"分隔
	 * 01*23*4*56*65*8
	 * 
	 * 变更后
	 * 在0-9共10个数字中选择7个以上数字M(M>=7)进行投注，每个位置必须选择至少一个数字；不同位置的数字可以重复，同一位置的数字不能重复； 
	 * 按照顺序一位一个或多个号码，号码之间用"*"隔开。多注之间用"^"分隔。 
	 * 1*23*34*34*45*23*45^1*2*3*4*5*6*7
	 * 
	 */
	protected static final String COLOR_BET_1_REGULAR = "^(\\d{1,10}\\*){6}(\\d{1,10}){1}$";//1-复式
	////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 排列3
	 */
	protected static final String ARRANGE_3_PLAYTYPE_NO_ADDITIONAL = "0";// 不追加
	protected static final String ARRANGE_3_BETTYPE_0 = "0";// 0-直选单式
	protected static final String ARRANGE_3_BETTYPE_1 = "1";// 1-直选复式
	protected static final String ARRANGE_3_BETTYPE_2 = "2";// 2-直选合值
	protected static final String ARRANGE_3_BETTYPE_3 = "3";// 3-组选单式
	protected static final String ARRANGE_3_BETTYPE_4 = "4";// 4-组选六复式
	protected static final String ARRANGE_3_BETTYPE_5 = "5";// 5-组选三复式
	protected static final String ARRANGE_3_BETTYPE_6 = "6";// 6-组选合值
	protected static final String ARRANGE_3_BETTYPE_7 = "7";// 7-直选组合
	protected static final String ARRANGE_3_BETTYPE_8 = "8";// 8-直选组合胆拖
	protected static final String ARRANGE_3_BETTYPE_9 = "9";// 9-组选三胆拖
	protected static final String ARRANGE_3_BETTYPE_10 = "10";// 10-组选六胆拖
	protected static final String ARRANGE_3_BETTYPE_11 = "11";// 11-直选跨度
	protected static final String ARRANGE_3_BETTYPE_12 = "12";// 12-组选三跨度
	protected static final String ARRANGE_3_BETTYPE_13 = "13";// 13-组选六跨度
	protected static final String ARRANGE_3_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	/*
	 * 0-直选单式
	 * 在0-9共10个数字中任意选择3个数字进行投注，单票最多5注直选号码
	 * 按照顺序一位一个号码，号码之间用"*"隔开。最多可以5注，之间用"^"分隔
	 * 1*2*3
	 */
	protected static final String ARRANGE_3_BET_0_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}$";
	/*
	 * 1-直选复式
	 * 在0-9共10个数字中任意选择，个位、十位和百位的某位或多位的号码个数大于1，每位均可选择1-10个号码。例如个位选择X个号码，十位选择Y个，百位选择Z个，总金额=X*Y*Z*2。
	 * 一位数字代表一位号码，各个位置号码之间用"*"隔开。每个位置可以填写多个号码。最多可以？注，之间用"^"分隔
	 * 12*23*34
	 * 
	 * 变更后
	 * 在0-9共10个数字中任意选择3个或三个以上数字进行投注，不同位置数字可以相同，同一位置的数字不能相同；
	 * 按照顺序一位一个或多个号码，号码之间用"*"隔开；多注之间用"^"分隔；
	 * 
	 */
	protected static final String ARRANGE_3_BET_1_REGULAR = "^(\\d){1,10}\\*(\\d){1,10}\\*(\\d){1,10}$";
	/*
	 * 2-直选合值
	 * 在1-26中任选一个整数，如三位数字相加之和为5的所有直选号码，例如005、014、122……
	 * 1-26之间的1位数字。最多可以？注，之间用"^"分隔
	 * 5
	 * 
	 */
	protected static final String ARRANGE_3_BET_2_REGULAR = "([1-9]|1[0-9]|2[0-6]{1})";
	/*
	 * 3-组选单式
	 * 在0-9共10个数字中任意选择3个数进行投注，投注的三个数中，其中两个相同按组选三处理，三个数各不相同，按组选六处理。
	 * 按照顺序一位一个号码，号码之间用"*"隔开。最多可以？注，之间用"^"分隔
	 * 1*2*3
	 * 
	 * 变更后
	 *  在0-9共10个数字中选择3个数进行投注，不允许选三个相同的数；投注的三个数中，其中两个相同按组选三处理，三个数各不相同，按组选六处理。
	 *   一位一个号码，号码之间用"*"隔开。多注之间用"^"分隔；
	 * 
	 */
	protected static final String ARRANGE_3_BET_3_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}$";
	/*
	 * 4-组选六复式
	 * 在0-9共10个号码中至少选择4个号码进行投注，在所选的号码中，任意三个数的组合数即为投注的注数。例如：1*2*3*4形成的组合123、124、134、234即产生4注投注号码。
	 * 一位一个号码，号码之间用"*"隔开。最多可以？注，之间用"^"分隔
	 * 1*2*3*4*5
	 * 
	 * 变更后
	 * 在0-9共10个号码中至少选择M个号码（M>=3）进行投注，所选的号码必须不能相同。
	 * 例如：1*2*3*4形成的组合123、124、134、234即产生4注投注号码。 一位一个号码，号码之间用"*"隔开。多注之间用"^"分隔；1*2*3^2*4*5*6^3*8*7*4
	 */
	protected static final String ARRANGE_3_BET_4_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}(\\*\\d){0,}$";
	/*
	 * 5-组选三复式
	 * 在0-9共10个号码中任选2-10个号码进行投注，在所选的号码中，任意两个数的组合数*2即为投注的注数。例如：1*2*3 成的组合12、13、23。每个组合对应2注，即3*2=6注。
	 * 一位一个号码，号码之间用"*"隔开。最多可以？注，之间用"^"分隔
	 * 1*2*3*4
	 * 
	 * 变更后
	 * 在0-9共10个号码中至少选择M个号码（M>=2）进行投注，所选的号码必须不能相同。一位一个号码，号码之间用"*"隔开。多注之间用"^"分隔；
	 */
	protected static final String ARRANGE_3_BET_5_REGULAR = "^(\\d\\*){1,9}(\\d){1}$";
	/*
	 * 6-组选合值
	 * 在2-25中任意选择1个数进行投注，组选和值对应的投注号码，其中两个数字相同为组选三，三个数各不相同为组选六
	 * 2-25之间的1位数字。最多可以？注，之间用"^"分隔
	 * 6
	 * 
	 * 变更后
	 * 在2-25中选择1个数进行投注，和值拆分为三个数字相加等于所选数字的投注，但不包括三个一样数字的和；组选和值对应的投注号码，
	 * 其中两个数字相同为组选三，三个数各不相同为组选六。多注之间用"^"分隔；
	 */
	protected static final String ARRANGE_3_BET_6_REGULAR = "([2-9]|1[0-9]|2[0-5]{1})";;
	/*
	 * 7-直选组合
	 * 在0-9共10个数字中至少选择3个数进行投注，组合指选择号码中任意三个数组成单式投注。例如组合123 单式号码：123、132、213、231、312、321
	 * 一位一个号码，号码之间无需分隔。最多可以？注，之间用"^"分隔
	 * 1234
	 */
	protected static final String ARRANGE_3_BET_7_REGULAR = "^\\d{3,}$";
	/*
	 * 8-直选组合胆拖
	 * 在0-9共10个数字中选择1个或2个作为胆码，其他与胆码不重复的数字作为拖码，组成胆码*拖码的形式投注。例如01*2 对应的单式号码包括012、021、102、120、210、201共六注。
	 * 一位一个号码，号码之间无需分隔；胆码和拖码之间使用*分隔，胆码在前；最多可以？注，之间用"^"分隔
	 * 01*2
	 * (需要校验重复)
	 */
	protected static final String ARRANGE_3_BET_8_REGULAR = "^(\\d){1,2}\\*(\\d)$";
	/*
	 * 9-组选三胆拖
	 * 在0-9共10个数字中选择1作为胆码，其他与胆码不重复的数字作为拖码，组成胆码*拖码的形式投注。例如1*23对应的单式号码包括112、122、113、133共四注。
	 * 一位一个号码，号码之间无需分隔；胆码和拖码之间使用*分隔，胆码在前；最多可以？注，之间用"^"分隔
	 * 1*23
	 * (需要校验重复)
	 */
	protected static final String ARRANGE_3_BET_9_REGULAR = "^(\\d){1}\\*(\\d)$";
	/*
	 * 10-组选六胆拖
	 * 在0-9共10个数字中选择1个或2个作为胆码，其他与胆码不重复的数字作为拖码，组成胆码*拖码的形式投注。例如12*34 对应的单式号码包括123和124共两注投注号码。
	 * 一位一个号码，号码之间无需分隔；胆码和拖码之间使用*分隔，胆码在前；最多可以？注，之间用"^"分隔
	 * 12*34
	 * (需要校验重复)
	 * 是否需要校验胆码的重复
	 */
	protected static final String ARRANGE_3_BET_10_REGULAR = "^(\\d){1,2}\\*(\\d)$";
	/*
	 * 11-直选跨度
	 * 在0-9共10个数字中选择一个数进行投注，跨度=投注号码中最大号码 - 最小号码。例如跨度1的单式投注包括：112、667、343
	 * 0-9之间的1位数字。最多可以？注，之间用"^"分隔
	 * 7
	 */
	protected static final String ARRANGE_3_BET_11_REGULAR = "^(\\d){1}$";
	/*
	 * 12-组选三跨度
	 * 在1-9中任意选择1个号码进行投注，包括所有跨度相同的组三。例如跨度2，形成单式组三号码220、202、133、331等16注。
	 * 1-9之间的1位数字。最多可以？注，之间用"^"分
	 * 6
	 */
	protected static final String ARRANGE_3_BET_12_REGULAR = "^([1-9]){1}$";
	/*
	 * 13-组选六跨度
	 * 在2-9中任意选择1个号码进行投注，包括所有跨度相同的组六。例如跨度2，形成单式组六号码012、123、234、345等8注。
	 * 2-9之间的1位数字。最多可以？注，之间用"^"分隔
	 * 4
	 */
	protected static final String ARRANGE_3_BET_13_REGULAR = "^([2-9]){1}$";
	////////////////////////////////////////////////////////////////////////////////////
	//排列5
	protected static final String ARRANGE_5_PLAYTYPE_NO_ADDITIONAL = "0";// 不追加
	protected static final String ARRANGE_5_BETTYPE_SINGLE = "0";//单式
	protected static final String ARRANGE_5_BETTYPE_DUPLEX = "1";//复式
	protected static final String ARRANGE_5_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/**
	 * 在0-9共10个数字中任意选择5个数字进行投注，单票最多5注直选号码
	 * 按照顺序一位一个号码，号码之间用"*"隔开。最多可以5注，之间用"^"分隔。
	 * 1*2*3*4*5
	 */
	protected static final String ARRANGE_5_BET_0_REGULAR = "^(\\d\\*){4}(\\d){1}$";//0-单式
	/**
	 * 在0-9共10个数字中任意选择，每一位号码的个数相乘即为总注数
	 * 一位数字代表一位号码，各个位置号码之间用"*"隔开。每个位置可以填写多个号码。最多可以？注，之间用"^"分隔
	 * 1*23*34*34*45
	 * 变更后
	 * 
	 * 在0-9共10个数字中选择5个以上数字M(M>=5)进行投注，每个位置必须选择至少一个数字；
	 * 不同位置的数字可以重复，同一位置的数字不能重复；按照顺序一位一个或多个号码，号码之间用"*"隔开。多注之间用"^"分隔。
	 * 1*23*34*34*45^1*2*3*4*5
	 */
	protected static final String ARRANGE_5_BET_1_REGULAR = "^(\\d{1,}\\*){4}(\\d{1,}){1}$";//1-复式
	
    ////////////////////////////////////////////////////////////////////////////////////
	//胜负彩
	
	protected static final String WINORCLOSE_PLAYTYPE_NO_ADDITIONAL = "0";// 不追加
	protected static final String WINORCLOSE_BETTYPE_SINGLE = "0";//单式
	protected static final String WINORCLOSE_BETTYPE_DUPLEX = "1";//复式
	protected static final String WINORCLOSE_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/**
	 * 14场比赛，分别用3，1，0代表主队胜平负
	 * 按照场次一位一个号码，号码之间用"*"隔开。最多可以？注，之间用"^"分隔。
	 * 3*1*1*3*3*1*0*3*3*3*3*0*1*3
	 */
	protected static final String WINORCLOSE_BET_0_REGULAR = "^(([0]|[1]|[3]){1}\\*){13}([0]|[1]|[3]){1}$";//0-单式
	/**
	 * 14场比赛，分别用3，1，0代表主队胜平负
	 * 按照场次各个位置号码之间用"*"隔开。每个位置可以填写多个号码。最多可以？注，之间用"^"分隔
	 * 3*01*1*3*31*1*03*3*3*3*3*0*1*3
	 * 变更后
	 * 14场比赛，分别用3，1，0代表主队胜平负；选择14场比赛的结果
	 * 按照场次一位一个或多个号码，号码之间用"*"隔开。多注之间用"^"分隔。
	 * 
	 * 
	 */
	protected static final String WINORCLOSE_BET_1_REGULAR = "^(([0]|[1]|[3]){1,}\\*){13}([0]|[1]|[3]){1,}$";//1-复式
	//////////////////////////////////////////////////////////////////////////////////////
	//任选九场
	protected static final String ARBITRARY_9_PLAYTYPE_NO_ADDITIONAL = "0";// 不追加
	protected static final String ARBITRARY_9_BETTYPE_SINGLE = "0";//单式
	protected static final String ARBITRARY_9_BETTYPE_DUPLEX = "1";//复式
	protected static final String ARBITRARY_9_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/**
	 * 14场比赛，分别用3，1，0代表主队胜平负，不选的场次使用数字4
	 * 按照场次一位一个号码，号码之间用"*"隔开。最多可以？注，之间用"^"分隔。
	 * 3*4*1*3*4*1*0*4*3*4*3*4*1*3
	 */
	protected static final String ARBITRARY_9_BET_0_REGULAR = "^((([0]|[1]|[3]){1}\\*)|([4]{1}\\*)){13}(([0]|[1]|[3]){1}|([4]{1})){1}$";//0-单式
	/**
	 * 14场比赛，分别用3，1，0代表主队胜平负，不选的场次使用数字4
	 * 按照场次各个位置号码之间用"*"隔开。每个位置可以填写多个号码。最多可以？注，之间用"^"分隔
	 * 3*4*13*30*4*1*0*4*3*4*3*4*1*3
	 */
	protected static final String ARBITRARY_9_BET_1_REGULAR = "^((([0]|[1]|[3]){1,}\\*)|([4]{1}\\*)){13}(([0]|[1]|[3]){1,}|([4]{1})){1}$";//1-复式
	
	////////////////////////////////////////////////////////////////////////////////////////////
	//6场半全场
	protected static final String HALF_6_PLAYTYPE_NO_ADDITIONAL = "0";// 不追加
	protected static final String HALF_6_BETTYPE_SINGLE = "0";//单式
	protected static final String HALF_6_BETTYPE_DUPLEX = "1";//复式
	protected static final String HALF_6_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/**
	 * 6场比赛，12个半场，分别用3，1，0代表主队半场胜平负
	 * 按照场次一位一个号码，号码之间用"*"隔开。最多可以？注，之间用"^"分隔。
	 * 3*1*1*3*3*1*0*3*3*3*3*0
	 */
	protected static final String HALF_6_BET_0_REGULAR = "^(([0]|[1]|[3]){1}\\*){11}([0]|[1]|[3]){1}$";//0-单式
	/**
	 * 6场比赛，12个半场，分别用3，1，0代表主队半场胜平负
	 * 按照场次各个位置号码之间用"*"隔开。每个位置可以填写多个号码。最多可以？注，之间用"^"分隔
	 * 3*1*130*3*30*1*0*3*3*3*3*0
	 */
	protected static final String HALF_6_BET_1_REGULAR = "^(([0]|[1]|[3]){1,}\\*){11}([0]|[1]|[3]){1,}$";//1-复式
	
	////////////////////////////////////////////////////////////////////////////////////////
	//4场进球彩
	protected static final String BALL_4_PLAYTYPE_NO_ADDITIONAL = "0";// 不追加
	protected static final String BALL_4_BETTYPE_SINGLE = "0";//单式
	protected static final String BALL_4_BETTYPE_DUPLEX = "1";//复式
	protected static final String BALL_4_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/**
	 * 4场比赛，每场比赛按主队进球数和客队进球数，共八个数字，分别用0，1，2，3代表0、1、2、3个以上进球
	 * 按照场次一位一个号码，号码之间用"*"隔开。最多可以？注，之间用"^"分隔。
	 * 3*1*2*3*3*1*0*3
	 */
	protected static final String BALL_4_BET_0_REGULAR = "^([0-3]{1}\\*){7}([0-3]){1}$";//0-单式
	/**
	 * 4场比赛，每场比赛按主队进球数和客队进球数，共八个数字，分别用0，1，2，3代表0、1、2、3个以上进球
	 * 按照场次各个位置号码之间用"*"隔开。每个位置可以填写多个号码。最多可以？注，之间用"^"分隔
	 * 3*012*2*123*3*1*0*3
	 */
	protected static final String BALL_4_BET_1_REGULAR = "^([0-3]{1,}\\*){7}([0-3]){1,}$";//1-复式
	
	//////////////////////////////////////////////////////////////////////////////////////
	//十一运夺金
	protected static final String ELEVEN_PLAYTYPE_1 = "1";//任选1
	protected static final String ELEVEN_PLAYTYPE_2 = "2";//任选2
	protected static final String ELEVEN_PLAYTYPE_3 = "3";//任选3
	protected static final String ELEVEN_PLAYTYPE_4 = "4";//任选4
	protected static final String ELEVEN_PLAYTYPE_5 = "5";//任选5
	protected static final String ELEVEN_PLAYTYPE_6 = "6";//任选6
	protected static final String ELEVEN_PLAYTYPE_7 = "7";//任选7
	protected static final String ELEVEN_PLAYTYPE_8 = "8";//任选8
	protected static final String ELEVEN_PLAYTYPE_9 = "9";//选前二直选
	protected static final String ELEVEN_PLAYTYPE_10 = "10";//选前三直选
	protected static final String ELEVEN_PLAYTYPE_11 = "11";//选前二组选
	protected static final String ELEVEN_PLAYTYPE_12 = "12";//选前三组选
	
	protected static final String ELEVEN_BETTYPE_0 = "0";// 0-单式
	protected static final String ELEVEN_BETTYPE_1 = "1";// 1-复式
	protected static final String ELEVEN_BETTYPE_2 = "2";// 2-胆拖
	protected static final String ELEVEN_BETTYPE_3 = "3";// 3-定位复式
	protected static final String ELEVEN_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/*
	 * 任选一
	 * 0-单式	
     * 01-11任选一个号码	
     * 03	
     * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_1_0_REGULAR = "^(0[1-9]|1[0-1]){1}$";
	/*
	 * 任选二
	 * *0-单式	
     *01-11任选2个号码,号码不能重复
     *0305	
     *两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_2_0_REGULAR = "^(0[1-9]|1[0-1]){2}$";
	/*
	 * 任选二
	 *1-复式	
	 *01-11任选，多于2个号码，号码不能重复，可包含单式
	 *03040506	
	 *两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_2_1_REGULAR = "^(0[1-9]|1[0-1]){2,}$";
	/*
	 * 任选二
	 *2-胆拖	
	 *01-11任选，至少选择一个胆码，两个以上拖码	
	 *01*020304	
	 *两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 *胆拖投注是指先选取少于单式投注号码个数的号码作为胆码（即每注彩票均包含的号码），
	 *再选取除胆码以外的号码作为拖码，胆码与拖码个数之和须多于单式投注号码个数，由胆码与拖码按该单式投注方式组成多注彩票的投注
	 *胆码拖码都不能重复，拖码不能包含在胆码内
	 */
	protected static final String ELEVEN_BET_2_2_REGULAR = "^(0[1-9]|1[0-1]){1}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * 任选三
	 *0-单式	
	 *01-11任选3个号码，号码不能重复
	 *020304	
	 *两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_3_0_REGULAR = "^(0[1-9]|1[0-1]){3}$";
	/*
	 * 任选三
	 *1-复式	
	 *01-11任选，多于3个号码，可以包含单式	
	 *0203040506	
	 *两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_3_1_REGULAR = "^(0[1-9]|1[0-1]){3,}$";
	/*
	 * 任选三
	 *2-胆拖
	 *
	 *			
	 *两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_3_2_REGULAR = "^(0[1-9]|1[0-1]){1,2}\\*(0[1-9]|0[0-1]){1,}$";
	/*
	 * 任选四
	 * 0-单式	
	 * 01-12任选4个号码		
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_4_0_REGULAR = "^(0[1-9]|1[0-1]){4}$";
	/*
	 * 任选四
	 *1-复式	
	 *01-11任选，多于4个号码		
	 *两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_4_1_REGULAR = "^(0[1-9]|1[0-1]){4,}$";
	/*
	 * 任选四
	 *2-胆拖	
	 *
	 *		
	 *两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_4_2_REGULAR = "^(0[1-9]|1[0-1]){1,3}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * 任选五
	 *0-单式	
	 *01-13任选三个号码		
	 *两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_5_0_REGULAR = "^(0[1-9]|1[0-1]){5}$";
	/*
	 * 任选五
	 *1-复式	
	 *01-11任选，多于5个号码		
	 *两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_5_1_REGULAR = "^(0[1-9]|1[0-1]){5,}$";
	/*
	 * 任选五
	 *2-胆拖	
	 * 
	 * 		
	 * 两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_5_2_REGULAR = "^(0[1-9]|1[0-1]){1,4}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 *任选六
	 * 0-单式	
	 * 01-14任选三个号码		
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_6_0_REGULAR = "^(0[1-9]|1[0-1]){6}$";
	/*
	 * 任选六
	 * 1-复式	
	 * 01-11任选，多于6个号码		
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_6_1_REGULAR = "^(0[1-9]|1[0-1]){6,}$";
	/*
	 *任选六
	 * 2-胆拖	
	 * 
	 * 		
	 * 两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_6_2_REGULAR = "^(0[1-9]|1[0-1]){1,5}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * 任选七
	 * 0-单式	
	 * 01-15任选三个号码		
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_7_0_REGULAR = "^(0[1-9]|1[0-1]){7}$";
	/*
	 * 任选七
	 * 1-复式	
	 * 01-11任选，多于7个号码		
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_7_1_REGULAR = "^(0[1-9]|1[0-1]){7,}$";
	/*
	 * 任选七
	 * 2-胆拖
	 * 
	 * 			
	 * 两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_7_2_REGULAR = "^(0[1-9]|1[0-1]){1,6}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * 任选八
	 * 0-单式	
	 * 01-16任选三个号码		
	 * 
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_8_0_REGULAR = "^(0[1-9]|1[0-1]){8}$";
	/*
	 * 1-复式	
	 * 01-11任选，多于8个号码		
	 * 
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_8_1_REGULAR = "^(0[1-9]|1[0-1]){8,}$";
	/*
	 * 2-胆拖
	 * 
	 * 			
	 * 两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_8_2_REGULAR = "^(0[1-9]|1[0-1]){1,7}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * 选前二直选
	 * 0-单式	
	 * 01-11任选2个号码，有顺序	，号码不可重复
	 * 0305	
	 * 按顺序两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_9_0_REGULAR = "^(0[1-9]|1[0-1]){2}$";
	/*
	 * 选前二直选
	 * 1-复式	
	 * 01-11任选2个以上号码，按不同号码不同顺序组合，号码不可重复
	 * 03050607	
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_9_1_REGULAR = "^(0[1-9]|1[0-1]){2,}$";
	/*
	 * 选前二直选
	 * 2-胆拖
	 * 
	 * 		
	 * 03*030506	
	 * 两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_9_2_REGULAR = "^(0[1-9]|1[0-1]){1}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * 选前二直选
	 * 3-定位复式	
	 * 按顺序，每个位置可选多个号码	
	 * 03*040506	
	 * 两位一个号码，不足两位左补'0'；位置之间用"*"分隔，按顺序分别为第1、2位，每个位置可以多个号码；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_9_3_REGULAR = "^(0[1-9]|1[0-1]){1,}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * 选前三直选
	 * 0-单式	
	 * 01-11任选3个号码	
	 * 030507	
	 * 按顺序两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_10_0_REGULAR = "^(0[1-9]|1[0-1]){3}$";
	/*
	 * 选前三直选
	 * 1-复式	
	 * 01-11任选4个号码	
	 * 03050607	
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_10_1_REGULAR = "^(0[1-9]|1[0-1]){3,}$";
	/*
	 * 选前三直选
	 * 2-胆拖
	 * 		
	 * 03*030506	
	 * 两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_10_2_REGULAR = "^(0[1-9]|1[0-1]){1,2}\\*(0[1-9]|0[0-1]){1,}$";
	/*
	 * 选前三直选
	 * 3-定位复式	
	 * 按顺序，每个位置可选多个号码	
	 * 03*040506*02	
	 * 两位一个号码，不足两位左补'0'；位置之间用"*"分隔，按顺序分别为第1、2、3位，每个位置可以多个号码；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_10_3_REGULAR = "^(0[1-9]|1[0-1]){1,}\\*(0[1-9]|1[0-1]){1,}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * 选前二组选
	 * 0-单式	
	 * 01-11任选2个号码	
	 * 0305	
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_11_0_REGULAR = "^(0[1-9]|1[0-1]){2}$";
	/*
	 * 选前二组选
	 * 1-复式	
	 * 01-11任选多于2个号码	
	 * 030506	
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_11_1_REGULAR = "^(0[1-9]|1[0-1]){2,}$";
	/*
	 * 选前二组选
	 * 2-胆拖	
	 * 	
	 * 01*020304	
	 * 两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_11_2_REGULAR = "^(0[1-9]|1[0-1]){1}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * 选前三组选
	 * 0-单式	
	 * 01-11任选3个号码	
	 * 030506	
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_12_0_REGULAR = "^(0[1-9]|1[0-1]){3}$";
	/*
	 * 选前三组选
	 * 1-复式	
	 * 01-11任选多于3个号码	
	 * 0305060809	
	 * 两位一个号码，不足两位左补'0'；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_12_1_REGULAR = "^(0[1-9]|1[0-1]){3,}$";
	/*
	 * 选前三组选
	 * 2-胆拖		
	 * 0102*030405	
	 * 两位一个号码，不足两位左补'0'；胆码和拖码之间用"*"分隔，胆码在前；最多可以？注，之间用"^"分隔
	 */
	protected static final String ELEVEN_BET_12_2_REGULAR = "^(0[1-9]|1[0-1]){1,2}\\*(0[1-9]|1[0-1]){1,}$";
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 江西福彩
	 */
	/**
	 * 双色球
	 */
	////////////////////////////////////////////////////////////////////////////////////
	protected static final String SSQ_PLATTYPE_NO_ADDITIONAL = "0";//玩法，不追加
	protected static final String SSQ_BETTYPE_SINGLE = "0";//单式
	protected static final String SSQ_BETTYPE_DUPLEX = "1";//复式
	protected static final String SSQ_BETTYPE_DANTUO = "2";//胆拖
	protected static final String SSQ_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	/**
	 * 0-单式
	 * 在01-33中任选6个不重复的号码作为红球，01-16中任选1个号码作为兰球，1-9前面用0补齐。
	 * 重复问题采用另外的方法判断
	 */
	protected static final String SSQ_BET_0_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|3[0-3]){6}\\|(0[1-9]|1[0-6]){1}$";//0-单式
	/**
	 * 1-复式
	 * 在1-33中任选M个不重复的号码作为前区，1-16中任选N个不重复的号码作为后区号码，M>=6并且N>=1
	 * 
	 */
	protected static final String SSQ_BET_1_REGULAR = "^((0[1-9]|1\\d|2\\d|3[0-3]){6,}\\|(0[1-9]|1[0-6]){1,})$";//1-复式
	
	//七乐彩
	protected static final String QLC_PLAYTYPE_NO_ADDITIONAL = "0";//玩法，不追加
	protected static final String QLC_BETTYPE_SINGLE = "0";//单式
	protected static final String QLC_BETTYPE_DUPLEX = "1";//复式
	protected static final String QLC_BETTYPE_DANTUO = "2";//胆拖
	protected static final String QLC_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/**
	 * 01-30中任意选择7个不重复的号码,号码无顺序要求
	 * 
	 */
	protected static final String QLC_BET_0_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|30){7}$";//0-单式
	/**
	 * 01-30个号码任意选择M个不重复的号码进行投注,M>=7且M<=16;复式每7个组合为一个单式投注
	 * 
	 */
	protected static final String QLC_BET_1_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|30){7，16}$";//1-复式
	/**
	 * 01-30中选择；胆码M(1<=M<=6),拖码N(N>=1),M+N >=7;胆码与拖码不能重复
	 */
	protected static final String QLC_BET_2_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|30){1,6}\\*(0[1-9]|1[0-9]|2[0-9]|30){1,}$";//2-胆拖
	
	
	//福彩3D
	protected static final String FC3D_PLAYTYPE_NO_ADDITIONAL = "0";//玩法，不追加
	protected static final String FC3D_BETTYPE_0 = "0";// 0-直选单式
	protected static final String FC3D_BETTYPE_1 = "1";// 1-直选复式
	protected static final String FC3D_BETTYPE_2 = "2";// 2-组选单式
	protected static final String FC3D_BETTYPE_3 = "3";// 3-组选3复式
	protected static final String FC3D_BETTYPE_4 = "4";// 4-组选六复式
	protected static final String FC3D_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	/*
	 * 在0-9共10个数字中任意选择3个数字进行投注，数字可以相同
	 */
	protected static final String FC3D_BET_0_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}$";//0-直选单式
	/*
	 * 
	 * 在0-9共10个数字中任意选择3个或三个以上数字进行投注，不同位置数字可以相同，同一位置的数字不能相同
	 */
	protected static final String FC3D_BET_1_REGULAR = "^(\\d){1,10}\\*(\\d){1,10}\\*(\\d){1,10}$";//1-直选复式
	
	/*
	 *   在0-9共10个数字中选择3个数进行投注，不允许选三个相同的数；投注的三个数中，其中两个相同按组选三处理，三个数各不相同，按组选六处理
	 * 
	 */
	protected static final String FC3D_BET_2_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}$";//2-组选单式
	/*
	 * 在0-9共10个号码中至少选择M个号码（M>=2）进行投注，所选的号码必须不能相同。一位一个号码，号码之间用"*"隔开。多注之间用"^"分隔；
	 */
	protected static final String FC3D_BET_3_REGULAR = "^(\\d\\*){1,9}(\\d){1}$";//3-组选3复式
	/*
	 * 在0-9共10个号码中至少选择M个号码（M>=3）进行投注，所选的号码必须不能相同。
	 * 例如：1*2*3*4形成的组合123、124、134、234即产生4注投注号码。 一位一个号码，号码之间用"*"隔开。多注之间用"^"分隔；1*2*3^2*4*5*6^3*8*7*4
	 */
	protected static final String FC3D_BET_4_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}(\\*\\d){0,}$";//4-组选六复式
	
	//15选5
	protected static final String SWXW_PLAYTYPE_NO_ADDITIONAL = "0";//玩法，不追加
	protected static final String SWXW_BETTYPE_SINGLE = "0";//单式
	protected static final String SWXW_BETTYPE_DUPLEX = "1";//复式
	protected static final String SWXW_BETTYPE_DANTUO = "2";//胆拖
	protected static final String SWXW_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	
	/**
	 * 01-15个数字中选出5个不重复的号码
	 * 
	 */
	protected static final String SWXW_BET_0_REGULAR = "^(0[1-9]|1[0-5]){5}$";//0-单式
	/**
	 * 01-15个数字中选出M个不重复的号码投注；5<=M<=13
	 * 
	 */
	protected static final String SWXW_BET_1_REGULAR = "^(0[1-9]|1[0-5]){5，13}$";//1-复式
	/**
	 * 01-15中选择；胆码M(1<=M<=4), 拖码N(1<=N),且M+N>=5,胆码拖码不能重复
	 */
	protected static final String SWXW_BET_2_REGULAR = "^(0[1-9]|1[0-5]){1,4}\\*(0[1-9]|1[0-5]){1,}$";//2-胆拖
	
	/**
	 * 东方6+1
	 */
	protected static final String DF6J1_PLATTYPE_NO_ADDITIONAL = "0";//玩法，不追加
	protected static final String DF6J1_BETTYPE_SINGLE = "0";//单式
	protected static final String DF6J1_BETTYPE_DUPLEX = "1";//复式
	protected static final String DF6J1_BET_GROUP_SEPARATOR = "\\^";//各注之间的分割符号
	/**
	 * 0-单式
	 * 在0-9共10个数字中选择6个数字作为基本区，按照顺序选择，数字可以重复；在12生肖中选择一个生肖作为生肖区(生肖按照顺序可以用01-12标识)
	 * 重复问题采用另外的方法判断
	 */
	protected static final String DF6J1_BET_0_REGULAR = "^(\\d\\*){5}(\\d){1}\\|(0[1-9]|1[0-2]){1}$";//0-单式
	/**
	 * 1-复式
	 * 基本区：在0-9共10个数字中选择6个及以上数字M(M>=6)进行投注，每个位置必须选择至少一个数字；不同位置的数字可以重复，同一位置的数字不能重复；
     *生肖区：在12生肖中选择1个及以上个生肖N(N>=1)投注,生肖区不能重复,号码不用分割
	 */
	protected static final String DF6J1_BET_1_REGULAR = "^(\\d{1,10}\\*){5}(\\d{1,10}){1}\\|(0[1-9]|1[0-2]){1,})$";//1-复式
	
	/**
	 * 
	 * 根绝规则对目标串进行校验
	 * @param rules 定义的规则
	 * @param targetStr 目标字符串
	 * @return boolean  符合规则返回true 不符合规则返回false
	 * @throws
	 */
	protected boolean checkRules(String rules,String targetStr) {
		boolean result = Pattern.matches(rules, targetStr);
		return result;
	}
	
	/**
	 * 
	 * 判断以len长度指定的子字符串是否在主字符串中重复出现
	 * @param targetStr 由数字或字母组成的字符串
	 * @param len 分割的长度
	 * @return boolean    如果不重复返回true,如果重复返回false
	 * @throws
	 */
	protected boolean checkRepeat(String targetStr,int len){
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>(); 
		boolean result = true;
		try {
			String regu = "\\w{"+len+"}";
			Pattern p = Pattern.compile(regu);
			Matcher m = p.matcher(targetStr);
			while(m.find()) {
				list.add(m.group());
				set.add(m.group());
			}
			if(set.size() < list.size()){
				result = false;
			}
		} catch (RuntimeException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * Title: checkRepeatNum<br>
	 * Description: 判断以len长度指定的子字符串是否在主字符串中重复出现的位置数<br>
	 * @param targetStr
	 * @param len
	 * @return int 0 代表所有位置都不重复，1代表有一个位置重复
	 */
	protected int checkRepeatNum(String targetStr,int len){
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>(); 
		int result = -1;
		try {
			String regu = "\\w{"+len+"}";
			Pattern p = Pattern.compile(regu);
			Matcher m = p.matcher(targetStr);
			while(m.find()) {
				list.add(m.group());
				set.add(m.group());
			}
			result = list.size() - set.size();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * 按照长度拆分字符串为线性列表，不能拆分汉字和特殊符号
	 * @param targetStr 目标字符串
	 * @param len 长度
	 * @return List<String> 拆开的线性列表
	 * @throws 对异常未做捕获
	 */
	protected List<String> splitStrByLen(String targetStr,int len){
		List<String> list = new ArrayList<String>();
		String regu = "\\w{"+len+"}";
		Pattern p = Pattern.compile(regu);
		Matcher m = p.matcher(targetStr);
		while(m.find()) {
			list.add(m.group());
		}
		return list;
	}
	/**
	 * 
	 * 合并线性集合为字符串
	 * @param list
	 * @return String    返回类型
	 * @throws
	 */
	protected String mergeListToStr(List<String> list){
		StringBuffer result = new StringBuffer();
		for(String elem : list){
			result.append(elem);
		}
		return result.toString();
	}
	/**
	 * 
	 * 将线性集合用sign指定符号合并为字符串
	 * @param list 线性列表
	 * @param sign 合并符号
	 * @return String  合并后则字符串
	 * @throws
	 */
	protected String mergeListToStrBySign(List<String> list,String sign){
		StringBuffer result = new StringBuffer();
		boolean flag = false;
		for(String elem : list){
			if(flag){
				result.append(sign);
			}else{
				flag = true;
			}
			result.append(elem);
		}
		return result.toString();
	}
	
	/**
	 * 
	 * 根据拆分符号将字符串拆分为数组
	 * @param targetStr 目标字符串
	 * @param Splitsign 拆分符号
	 * @param isEnforce 是否强制拆分，即如果设置true,则字符串最后若出现拆分符号，则将最后一个拆分符号后也作为一个数组元素
	 * @return String[] 根据拆分符号拆开的字符串
	 * @throws
	 */
	protected String [] splitGroup(String targetStr,String Splitsign,boolean isEnforce){
		if(isEnforce){
			return targetStr.split(Splitsign,-1);
		}else{
			return targetStr.split(Splitsign);
		}
		
	}
	
	/**
	 * 
	 * 将整数前面补0，补0的数据的长度由defLen指定
	 * @param target 要补0的数据
	 * @param defLen 补0后数据的长度
	 * @return String    格式化后的字符串
	 * @throws
	 */
	protected String formatLenByTag(int target,int defLen){
		String formatStr = "%1$0"+defLen+"d";
		return String.format(formatStr,target);
	}
	
	/**
	 * 
	 * 计算子字符串在主字符串中出现的次数
	 * @param mainStr 目标字符串
	 * @param subStr 子字符串
	 * @return  出现的次数
	 */
	protected int checkCharViews(String mainStr,String subStr){
		String regux = "\\b"+subStr+"\\b";
		Pattern p =  Pattern.compile(regux);
		Matcher m = p.matcher(mainStr);
		int num = 0;
		while(m.find()){
			num++;
		}
		
		return num;
	}
	/**
	 * 
	 * 校验胆拖的重复
	 * @param target 要校验的胆拖串
	 * @return boolean 如果不重复返回true 重复返回false
	 * @throws
	 */
	protected boolean checkDanTuoRepeat(String target,int weiLen) {
		boolean checkResult = true;
		String[] danTuoGroup = target.split("\\*");// 此处已经经过校验，包含胆码和拖码，所以数组为2个值
		List<String> dan = splitStrByLen(danTuoGroup[0], weiLen);
		List<String> tuo = splitStrByLen(danTuoGroup[1], weiLen);
		for (String singleDan : dan) {
			if (tuo.contains(singleDan)) {
				checkResult = false;
				break;
			}
		}
		return checkResult;
	}
	/**
	 * 
	 * 将复式投注拆为单式投注，号码是有顺序要求的
	 * @param betCode 01*23*4*56*65*8^01*23*4*56*65*8
	 * @param groupSign 各注之间的拆分符号 是正则拆分，因此要加正则的转义 例如"\\^"标识"^"
	 * @param weiSign 每一注中各个位置的拆分符号 因此要加正则的转义 例如"\\*"标识"*"
	 * @param oneWeiLen 每一个位置的长度
	 * @return List<String> 单式投注的号码串集合
	 * @throws
	 */
	protected List<String> combineDuplex(String betCode,String groupSign,String weiSign,int oneWeiLen){
		List<String> result = new ArrayList<String>();
		String [] groupArr = splitGroup(betCode,groupSign,false);
		for(String group : groupArr){//按照每一注处理
			String [] weiGroup = group.split(weiSign);
			Stack<List<String>> oneStack = new Stack<List<String>>();
			for(int k = weiGroup.length -1 ; k >= 0 ;k--){//倒序入栈
				oneStack.push(splitStrByLen(weiGroup[k],oneWeiLen));
			}
			
			while(oneStack.size() > 1){
				oneStack.push(combineTwo(oneStack.pop(),oneStack.pop(),weiSign.replace("\\", "")));
			}
			result.addAll(oneStack.pop());
		}
		return result;
	}
	/**
	 * 
	 * 将两个线性列表合成一个线性列表，合并方式为first*second
	 * @param first 第一个线性集合
	 * @param second 第二个线性集合
	 * @param weiSign 每一个位置的合并符号 ，不是正则拆分，因此不加正则的转义
	 * @return List<String>    返回的集合size应该为firsr.size*second.size
	 * @throws
	 */
	protected List<String> combineTwo(List<String> first,List<String> second,String weiSign){
		List<String> result = new ArrayList<String>();
		StringBuffer buf = new StringBuffer();
		for(String fistStr : first){
			for(String secondStr : second){
				buf.delete(0, buf.length());//清空缓冲
				buf.append(fistStr);
				buf.append(weiSign);
				buf.append(secondStr);
				result.add(buf.toString());
			}
		}
		return result;
	}
	
	
	/**
	 * 
	 * 检测firstList中的号码在secondList的号码集合中出现的次数
	 * 对secondList中的号码匹配过一次后就不再匹配
	 * @param firstList 
	 * @param secondList 
	 * @return String 出现的次数
	 * @throws
	 */
	protected String hitHaoNum(List<String> firstList,List<String> secondList){
		int hitNum = 0;
		if(firstList == null || secondList == null){
			return null;
		}
		List<String> innerSecondList = new ArrayList<String>(Arrays.asList(new String[secondList.size()]));
		Collections.copy(innerSecondList, secondList);
		for(String one : firstList){
			if(innerSecondList.contains(one)){
				hitNum++;
				innerSecondList.remove(one);
			}
		}
		return String.valueOf(hitNum);
	}
	
	/**
	 * 
	 * 将前区列表和后区列表组合成多个字符串“前去"+sign+"后区”
	 * @param sign 合并符号
	 * @param beforeList 前区列表
	 * @param endList 后区列表
	 * @return List<String> List<“前去"+sign+"后区”>
	 * @throws
	 */
	protected List<String> combineBeforeEnd(List<String> beforeList,List<String> endList,String sign){
		List<String> result = new ArrayList<String>();
		StringBuffer groupBuf = new StringBuffer();
		for(String before : beforeList){
			for(String end : endList){
				groupBuf.delete(0, groupBuf.length());
				groupBuf.append(before);
				groupBuf.append(sign);
				groupBuf.append(end);
				result.add(groupBuf.toString());
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 将胆拖方式的前去或者后区组合成字符串集合,注意只是组合，即无顺序区分
	 * @param oneQu 表示前去后者后区的字符串
	 * @param Totallen  前去或者后区的长度，对于大乐透，前区为5，后区为2,对于生肖乐为2
	 * @param weiLen 按照weiLen长度将字符串拆开
	 * @param sign 胆拖的分割符号 注意特殊字符
	 * @return List<String> 组合后的前去或后区字符串集合
	 * @throws
	 */
	protected List<String> combineDanTuo(String oneQu,int Totallen,int weiLen,String sign){
		List<String> result = new ArrayList<String>();
		StringBuffer buf = new StringBuffer();
		String [] danTuoArr = oneQu.split(sign);
		if(danTuoArr.length == 2){
			String dan = danTuoArr[0];//胆码
			String tuo = danTuoArr[1];//拖码
			int danLen = splitStrByLen(dan,weiLen).size();//胆码长度
			int combineLen = Totallen - danLen;//总长度减去胆码的长度
			List<String> tuoCombine = combineToList(splitStrByLen(tuo,weiLen),combineLen);
			for(String tuoCom : tuoCombine){
				buf.delete(0, buf.length());
				buf.append(dan);
				buf.append(tuoCom);
				result.add(buf.toString());
			}
		}
		return result;
	}
	/**
	 * 
	 * Title: splitBallSalesInfoResult<br>
	 * Description: <br>
	 *            拆分足彩的销售信息<br>
	 *            <br>1,A-AC米兰&B-国际米兰,赛制,时间
	 * @param salesInfo
	 * @return 拆分后的集合
	 */	
	protected Map<Integer, Map<String,String>> splitBallSalesInfoResult(String salesInfo) {
		
		Map<Integer, Map<String,String>> resultMap = new TreeMap<Integer, Map<String,String>>();
		
		String[] groupsArr = salesInfo.split("\\|");//将各个场次分开
		for (String group : groupsArr) {
			TreeMap<String, String> teamMap = new TreeMap<String, String>();
			String[] subContent = group.split(",",-1);//包含场次，比赛队伍，赛制，时间
			Integer Showno = Integer.valueOf(String.valueOf(subContent[0]));
			String[] teamArr = subContent[1].split("&");
			for(String teamkn : teamArr){
				String teamKey = teamkn.substring(0, 1);
				String teamName = teamkn.substring(2, teamkn.length());
				teamMap.put(teamKey, teamName);
			}
			teamMap.put("C", subContent[2]);
			teamMap.put("D", subContent[3]);
			
			resultMap.put(Showno, teamMap);
		}
		
		return resultMap;
	}
	/**
	 * 
	 * Title: mergeBallSalesInfoResult<br>
	 * Description: <br>
	 *            合并足彩的销售信息<br>
	 * @param infoMap
	 * @return 合并后的字符串
	 */
	protected String mergeBallSalesInfoResult(Map<Integer, Map<String,String>> infoMap) {
		StringBuffer salesInfoBuf = new StringBuffer();
		
		TreeMap<Integer, TreeMap<String, String>> salesMap = new TreeMap<Integer, TreeMap<String, String>>();

		for (Map.Entry<Integer, Map<String, String>> entry : infoMap.entrySet()) {
			Integer key = entry.getKey();
			Map<String, String> teamMap = entry.getValue();
			TreeMap<String, String> teamSortMap = new TreeMap<String, String>();
			for (Map.Entry<String, String> teamEntry : teamMap.entrySet()) {
				//teamSortMap.put(teamEntry.getKey()+"-", teamEntry.getValue());
				teamSortMap.put(teamEntry.getKey(), teamEntry.getValue());
			}
			salesMap.put(key, teamSortMap);
		}
		
		for (Map.Entry<Integer, TreeMap<String, String>> entry : salesMap.entrySet()) {
			Integer key = entry.getKey();
			salesInfoBuf.append(key).append(",");
			TreeMap<String, String> teamMap = entry.getValue();			
			if(teamMap.get("A") == null){
				salesInfoBuf.append("A-&");
			}else{
				salesInfoBuf.append("A-" + teamMap.get("A").trim() + "&");
			}
			if(teamMap.get("B") == null){
				salesInfoBuf.append("B-,");
			}else{
				salesInfoBuf.append("B-" + teamMap.get("B").trim() + ",");
			}
			if(teamMap.get("C") == null){
				salesInfoBuf.append(",");
			}else{
				salesInfoBuf.append(teamMap.get("C").trim() + ",");
			}
			if(teamMap.get("D") == null){
				salesInfoBuf.append("|");
			}else{
				salesInfoBuf.append(teamMap.get("D").trim() + "|");
			}
//			
//			String formatStr = "1-2&3-4,5,6";
//			int termCount = teamMap.size();
//			for (Map.Entry<String, String> teamEntry : teamMap.entrySet()) {
//				if(teamEntry.getKey().equals("A")){
//					formatStr = formatStr.replaceAll("1", teamEntry.getKey()).replaceAll("2", teamEntry.getValue());
//					//salesInfoBuf.append(teamEntry.getKey()).append("-").append(teamEntry.getValue()).append("&");
//				}
//				if(teamEntry.getKey().equals("B")){
//					formatStr = formatStr.replaceAll("3", teamEntry.getKey()).replaceAll("4", teamEntry.getValue());
//					//salesInfoBuf.append(teamEntry.getKey()).append("-").append(teamEntry.getValue());
//				}
//				if(teamEntry.getKey().equals("C")){
//					formatStr = formatStr.replaceAll("5", teamEntry.getValue());
//				}
//				if(teamEntry.getKey().equals("D")){
//					formatStr = formatStr.replaceAll("6", teamEntry.getValue());
//				}
//				if(termCount < 4){
//					formatStr = formatStr.replaceAll("6", "");
//				}
//				if(termCount < 3){
//					formatStr = formatStr.replaceAll("5", "");
//				}
//				
//			}
//			salesInfoBuf.append(formatStr);
//			salesInfoBuf.append("|");
		}
		
		salesInfoBuf.deleteCharAt(salesInfoBuf.lastIndexOf("|"));
		
		return salesInfoBuf.toString();
	}
	/**
	 * 
	 * 将字符串数组取combineLen长度的组合
	 * @param targetArr 目标字符串
	 * @param combineLen 长度
	 * @return List<String>  组合后的集合
	 * @throws 
	 */
	protected List<String> combineToList(List<String> target, int combineLen){
		List<String> result= new ArrayList<String>();
		try {
			String [] targetArr = new String[target.size()];
			target.toArray(targetArr);
			List<String[]> innetList = combine(targetArr,combineLen);
			for(String [] sub : innetList){
				result.add(mergeListToStr(Arrays.asList(sub)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * 取组合方法
	 * @param targetArr
	 * @param m
	 * @throws Exception    设定文件
	 * @return List<String[]>    返回类型
	 * @throws
	 */
	private  List<String []> combine(String [] targetArr, int m) throws Exception{
		int n = targetArr.length;
		if (m > n) {
			throw new Exception("错误！数组targetArr中只有" + n + "个元素。" + m + "大于" + n + "!");
		}

		List<String []> result = new ArrayList<String []>();
		
		if(m == n){
			result.add(targetArr);
			return result;
		}

		int[] bs = new int[n];
		for (int i = 0; i < n; i++) {
			bs[i] = 0;
		}
		//初始化
		for (int i = 0; i < m; i++) {
			bs[i] = 1;
		}
		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		//首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
		do {
			sum = 0;
			pos = 0;
			tempFlag = true;
			result.add(combinePrint(bs, targetArr, m));

			for (int i = 0; i < n - 1; i++) {
				if (bs[i] == 1 && bs[i + 1] == 0) {
					bs[i] = 0;
					bs[i + 1] = 1;
					pos = i;
					break;
				}
			}
			//将左边的1全部移动到数组的最左边

			for (int i = 0; i < pos; i++) {
				if (bs[i] == 1) {
					sum++;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (i < sum) {
					bs[i] = 1;
				} else {
					bs[i] = 0;
				}
			}

			//检查是否所有的1都移动到了最右边
			for (int i = n - m; i < n; i++) {
				if (bs[i] == 0) {
					tempFlag = false;
					break;
				}
			}
			if (tempFlag == false) {
				flag = true;
			} else {
				flag = false;
			}

		} while (flag);
		result.add(combinePrint(bs, targetArr, m));

		return result;
	}
	/**
	 * 
	 * (这里用一句话描述这个方法的作用)
	 * @param bs
	 * @param targetArr
	 * @param m
	 * @return String[]    返回类型
	 * @throws
	 */
	private String [] combinePrint(int[] bs, String [] targetArr, int m) {
		String[] result = new String[m];
		int pos = 0;
		for (int i = 0; i < bs.length; i++) {
			if (bs[i] == 1) {
				result[pos] = targetArr[i];
				pos++;
			}
		}
		return result;
	}
	/**
	 * 
	 * 对线性列表取num长度的排列 
	 * @param datas 要排列的线性列表
	 * @param num 排列的长度
	 * @return List<String>  如果num的大于datas的元素个数，将返回null，使用时需要注意空指针
	 * @throws
	 */
	protected List<String> arrange(List<String> datas,int num){
		if(num > datas.size()){
			return null;
		}
		List<String> tmpList = new ArrayList<String>();
		List<String> retList = new ArrayList<String>();
		arrangeSort(datas,tmpList,retList,num);
		return retList;
	}
	/**
	 * 
	 * 对线性列表取num长度的排列
	 * @param datas 要排列的线性列表
	 * @param tmpList 提供一个线性列表供使用
	 * @param retList 返回的排列集合
	 * @param num    取排列的长度
	 * @throws
	 */
	private void arrangeSort(List<String> datas,List<String> tmpList,List<String> retList,int num){
		if (tmpList.size() == num) {
			StringBuffer buf = new StringBuffer();
			for(String one : tmpList){
				buf.append(one);
			}
			retList.add(buf.toString());
			return;
		}
		for(int i = 0; i < datas.size(); i++){
			List<String> newDatas = new ArrayList<String>(datas);
			List<String> newTmpList = new ArrayList<String>(tmpList);
			newTmpList.add(newDatas.get(i));
			newDatas.remove(i);
			arrangeSort(newDatas, newTmpList,retList,num);
		}
	}
	
	
}
