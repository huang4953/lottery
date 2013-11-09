package com.success.lottery.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.util.core.LotteryFactory;
import com.success.lottery.util.core.LotteryInterf;
import com.success.lottery.util.core.LotteryUnDefineException;
import com.success.utils.AutoProperties;
/**
 * <p>
 * 获得各个彩种相关信息，彩种信息配置在LotteryType.properties中。string类型信息未找到则返回null。<br>
 * 彩票字符串解析工具，各种投注相关定义的字符串格式，解析相关的定义的字符串格式，返回各种彩票相关信息。<br>
 * 
 * @author gaoboqin
 * 
 */
public class LotteryTools{

	private static Log			logger					= LogFactory.getLog(LotteryTools.class);
	private static String		lotteryConfig			= "com.success.lottery.util.LotteryType";
	private static final String	LOTTERYlIST_SEPARATOR	= "\\|";									// 定义彩种的分隔符
	private static final String	LOTTERYLIST_SUB_KEY		= "LotteryList";
	private static final int	LOTTERY_TYPE_NUM		= 1;										// 周期型数字彩票
	private static final int	LOTTERY_TYPE_FAST		= 2;										// 高频数字彩票
	private static final int	LOTTERY_TYPE_SOCCER		= 3;										// 足彩
	@SuppressWarnings("unused")
	private static final int	LOTTERY_TYPE_COMPETE	= 4;										// 竞彩

	/**
	 * 获得所有彩种编号和名称信息，从LotteryType.properties 的LotteryList字段读取解析<br>
	 * 
	 * @return Map&lt;彩种编号, 彩种名称&gt;
	 */
	public static Map<Integer, String> getLotteryList(){
		Map<Integer, String> lotteryMap = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());
		try{
			String lotteryStr = AutoProperties.getString(LOTTERYLIST_SUB_KEY, "", lotteryConfig);
			String[] lotteryArry = lotteryStr.split(LOTTERYlIST_SEPARATOR);
			for(String str : lotteryArry){
				String[] subLottery = str.split(",");
				lotteryMap.put(Integer.parseInt(subLottery[0]), subLottery[1]);
			}
		}catch(Exception e){
			logger.debug("LotteryTools.getLotteryList occur Exception:" + e.getMessage());
		}
		return lotteryMap;
	}

	/**
	 * 获得所有彩种编号和名称信息，从LotteryType.properties 的LotteryList字段读取解析<br>
	 * 
	 * @param lotteryOrgan
	 *            1-体彩, 2-福彩;目前默认体彩，无论填写什么都返回体彩
	 * @return Map&lt;Integer, String&gt; Map&lt;彩种编号, 彩种名称&gt;
	 */
	public static Map<Integer, String> getLotteryList(int lotteryOrgan){
		Map<Integer, String> retLotteryMap = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());
		try{
			Map<Integer, String> lotteryMap = getLotteryList();
			for(Map.Entry<Integer, String> entry : lotteryMap.entrySet()){
				if(getIntegerCharAt(entry.getKey(), 1) == lotteryOrgan){
					retLotteryMap.put(entry.getKey(), entry.getValue());
				}
				;
			}
		}catch(RuntimeException e){
			logger.debug("LotteryTools.getLotteryList(int lotteryOrgan) occur Exception:" + e.getMessage());
		}
		return retLotteryMap;
	}

	/**
	 * 获得普通数字彩彩种，列表从LotteryType.properties 的LotteryList字段读取解析<br>
	 * 1=数字彩，2=高频彩，3-足彩，4-竞彩;<br>
	 * 
	 * @return Map&lt;Integer, String&gt; Map&lt;彩种编号, 彩种名称&gt;
	 */
	public static Map<Integer, String> getNumberLotteryList(){
		return getLotteryByPos(2, LOTTERY_TYPE_NUM);
	}

	/**
	 * 获得足彩彩种列表，列表从LotteryType.properties 的LotteryList字段读取解析<br>
	 * 1=数字彩，2=高频彩，3-足彩，4-竞彩;<br>
	 * 
	 * @return Map&lt;Integer, String&gt; Map&lt;彩种编号, 彩种名称&gt;
	 */
	public static Map<Integer, String> getSoccerLotteryList(){
		return getLotteryByPos(2, LOTTERY_TYPE_SOCCER);
	}

	/**
	 * 获得高频彩彩种列表，列表从LotteryType.properties 的LotteryList字段读取解析<br>
	 * 1=数字彩，2=高频彩，3-足彩，4-竞彩;<br>
	 * 
	 * @return Map&lt;Integer, String&gt; Map&lt;彩种编号, 彩种名称&gt;
	 */
	public static Map<Integer, String> getFastLotteryList(){
		return getLotteryByPos(2, LOTTERY_TYPE_FAST);
	}

	/**
	 * 通过LotteryId获得彩种中文名称<br>
	 * 
	 * @param lotteryId
	 *            彩种编号
	 * @return String 返回彩种中文名称
	 */
	public static String getLotteryName(int lotteryId){
		return AutoProperties.getString(lotteryId + ".name", null, lotteryConfig);
	}

	/**
	 * 通过LotteryId获得彩种描述<br>
	 * 
	 * @param lotteryId
	 *            彩种编号
	 * @return String 返回彩种简单描述
	 */
	public static String getLotteryDesc(int lotteryId){
		return AutoProperties.getString(lotteryId + ".desc", null, lotteryConfig);
	}

	/**
	 * 通过LotteryId获得彩种类型
	 * 
	 * @param lotteryId
	 * @return int 返回彩种类型，0=未找到该彩种 1=周期型数字彩票 2=高频数字彩票 3=足彩 4=竞彩
	 */
	public static int getLotteryType(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".type", 0, lotteryConfig);
	}

	/**
	 * 根据彩种Id返回玩法信息，从LotteryType.properties 的playList字段读取解析
	 * 
	 * @param lotteryId
	 *            彩种Id
	 * @return LotteryInfo
	 *         <p>
	 *         null - 未找到该彩种信息<br>
	 *         LotteryInfo - 彩种信息类，调用方法:<br>
	 *         Map&lt;Integer, String&gt; getLotteryPlayMap() 返回包含玩法id和玩法名称的集合<br>
	 *         Map&lt;Integer, Map&lt;Integer, String&gt;&gt;
	 *         getLotteryPlayBetMap() 返回包含玩法id对应的投注方式的集合<br>
	 * 
	 */
	private static LotteryInfo getLotteryPlayList(int lotteryId){
		LotteryInfo lotteryInfo = new LotteryInfo();
		String lotteryListStr = AutoProperties.getString(lotteryId + ".playList", null, lotteryConfig);
		lotteryInfo.setLotteryPlayListStr(lotteryListStr);
		lotteryInfo.setLotteryPlayMap(LotteryTools.splitPlayStrForPlayMap(lotteryListStr));// 玩法集合
		lotteryInfo.setLotteryPlayBetMap(LotteryTools.splitPlayStrForPlayBet(lotteryListStr));// 玩法对应的投注方式集合
		return lotteryInfo;
	}

	/**
	 * 根据彩种得到playType集合
	 * 
	 * @param lotteryId
	 * @return playType集合
	 */
	public static Map<Integer, String> getLotteryPlayTypeList(int lotteryId){
		LotteryInfo lotteryInfo = LotteryTools.getLotteryPlayList(lotteryId);
		return lotteryInfo.getLotteryPlayMap();
	}

	/**
	 * 根据彩种id和彩种对应的玩法id获得该玩法对应的投注方式集合<br>
	 * 
	 * @param lotteryId
	 *            彩种Id
	 * @param playType
	 *            玩法id
	 * @return Map&lt;Integer,String&gt; null - 未找到该彩种信息<br>
	 *         Map&lt;Integer,String&gt; ，参数分别为&lt投注方式id，投注方式&gt;<br>
	 */
	public static Map<Integer, String> getLotteryPlayBetTypeList(int lotteryId, int playType){
		LotteryInfo lotteryInfo = LotteryTools.getLotteryPlayList(lotteryId);
		return lotteryInfo.getLotteryPlayBetMap().get(Integer.valueOf(playType));
	}

	/**
	 * 根据彩种id和玩法类型获得玩法名称
	 * @param lotteryId
	 * @param playType
	 * @return
	 */
	public static String getPlayTypeName(int lotteryId, int playType){
		try{
			return getLotteryPlayTypeList(lotteryId).get(Integer.valueOf(playType));
		}catch(Exception e){
			logger.error("getPlayTypeName(" + lotteryId + ", " + playType + ") exception:" + e);
			return null;
		}
	}
	
	public static String getPlayBetName(int lotteryId, int playType, int betType){
		try{
			String playName = getPlayTypeName(lotteryId, playType);
			String betName = getLotteryPlayBetTypeList(lotteryId, playType).get(Integer.valueOf(betType));
			if(lotteryId == 1000001){
				return betName + playName;
			}
			return playName + betName;
		}catch(Exception e){
			logger.error("getPlayBetName(" + lotteryId + ", " + playType + ", " + betType + ") exception:" + e);
			return null;
		}
	}
	
	/**
	 * 通过LotteryId获得彩种限制区域列表<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return List<String> null 未找到该项配置<br>
	 *         List&lt;String&gt;,参数为&lt;区域代码&gt;<br>
	 */
	public static List<String> getLotteryLimitArea(int lotteryId){
		String areaStr = AutoProperties.getString(lotteryId + ".limitArea", null, lotteryConfig);
		return Arrays.asList(areaStr.split(","));
	}

	/**
	 * 得到areaCode所在区域是否允许购买LotteryId的彩种<br>
	 * 
	 * @param lotteryId
	 *            彩种编号
	 * @param areaCode
	 *            所在区域，省份代码
	 * @return boolean true - 允许购买该彩种<br>
	 *         false - 不允许购买该彩种<br>
	 */
	public static boolean isAllowableUse(int lotteryId, String areaCode){
		List<String> areaList = LotteryTools.getLotteryLimitArea(lotteryId);
		boolean isAllow = true;
		if(areaList != null && areaList.contains(areaCode)){
			isAllow = false;
		}
		/*
		 * for(String area : areaList){ isAllow =
		 * (area.equals(areaCode))?false:true; if(!isAllow) break; }
		 */
		return isAllow;
	}

	/**
	 * 根据彩种id获得该彩种的玩法介绍
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return String ：玩法介绍的文本，或玩法介绍的文件地址或链接地址
	 */
	public static String getLotteryRules(int lotteryId){
		return AutoProperties.getString(lotteryId + ".rules", null, lotteryConfig);
	}

	/**
	 * 根据彩种id得到该彩种是否启用<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return boolean true - 该彩种正常启用<br>
	 *         false - 该彩种未启用<br>
	 */
	public static boolean isLotteryStart(int lotteryId){
		return "true".equalsIgnoreCase(AutoProperties.getString(lotteryId + ".start", "false", lotteryConfig));
	}

	/**
	 * <br>
	 * 根据彩种id得到该彩种的销售提前截至分钟数 <br>
	 * 销售提前截至分钟数 :
	 * 比官方销售的截至时间提前的分钟数，官方销售截至时间-本字段=可以购买的截至时间。如1000001.preSaleTimer=30
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return int 销售提前截至分钟数 单位为分钟 ,默认为0
	 */
	public static int getLotteryPreSaleTimer(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".preSaleTimer", 0, lotteryConfig);
	}

	/**
	 * <br>
	 * 根据彩种id得到该彩种的开始销售延迟分钟数 <br>
	 * 比官方销售开始时间延迟的分钟数，在延迟的时间内，方便进行当前彩期设置
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return int 销售开始时间延迟的分钟数 单位为分钟，默认为0
	 */
	public static int getLotteryDelaySaleTimer(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".delaySaleTimer", 0, lotteryConfig);
	}
	/**
	 * 
	 * Title: getLotteryPreDrawTicketTimer<br>
	 * Description: <br>
	 *              <br>出票截至时间的延迟分钟数
	 * @param lotteryId
	 * @return
	 */
	public static int getLotteryPreDrawTicketTimer(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".preDrawTicketTimer", 0, lotteryConfig);
	}
	/**
	 * 
	 * Title: getLotteryPreDrawTicketTimer<br>
	 * Description: <br>
	 *              <br>根据给定的官方出票截止时间，减去系统定义的提前分钟数，得到系统需要的截止出票时间
	 * @param lotteryId
	 * @param basicTime
	 * @return
	 */
	public static String getLotteryPreDrawTicketTimer(int lotteryId,String basicTime,String formatStr){
		String returnDate = basicTime;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			java.util.Calendar Cal=java.util.Calendar.getInstance();
			Cal.setTime(sdf.parse((basicTime)));
			int defineTime = getLotteryPreDrawTicketTimer(lotteryId);
			
			Cal.add(java.util.Calendar.MINUTE, -defineTime);
			returnDate = sdf.format(Cal.getTime());
			
		}catch(Exception e){
			e.printStackTrace();
			returnDate = basicTime;
		}
		return returnDate;
	}

	/**
	 * <br>
	 * 根据彩种id得到该彩种的检查停止销售时间 <br>
	 * 设置检查彩种停止销售时间的表达式，用于定时检查彩种信息中的saleStatus，如超过销售时间则设置该状态为停止销售。格式为Quartz
	 * CronExpression
	 * 
	 * @param lotteryId
	 * @return String
	 */
	public static String getCheckCron(int lotteryId){
		return AutoProperties.getString(lotteryId + ".checkCron", null, lotteryConfig);
	}

	/**
	 * 根据彩种id得到该彩种的出票方式
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return int
	 */
	public static int getLotteryPrintTicketMode(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".printTicketMode", 0, lotteryConfig);
	}

	/**
	 * 根据彩种id得到该彩种的单张彩票打印注数
	 * 
	 * @param lotteryId
	 *            彩票id
	 * @return int
	 */
	public static int getLotteryprintBetCount(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".printBetCount", 0, lotteryConfig);
	}

	/**
	 * 根据彩种id得到开奖方式<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return String <br>
	 *         null 未找到该项配置 <br>
	 *         开奖方式，手动开奖设置为0，自动开奖则设置为开奖处理类
	 */
	public static String getLotteryWinMode(int lotteryId){
		return AutoProperties.getString(lotteryId + ".winMode", null, lotteryConfig);
	}

	/**
	 * 根据彩种id得到开奖数据获取地址<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return String <br>
	 *         null 未找到该项配置 <br>
	 *         开奖数据获取地址，手动录入开奖数据设置为0，自动获取数据则为自动获取数据源url
	 */
	public static String getLotteryWinUrl(int lotteryId){
		return AutoProperties.getString(lotteryId + ".winUrl", null, lotteryConfig);
	}

	/**
	 * 根据彩种id得到开奖数据自动获取的处理类<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return String <br>
	 *         null 未找到该项配置 <br>
	 *         开奖数据自动获取的处理类，手动录入开奖数据设置为0，自动获取数据则为处理数据源url的处理类。当winMode为0时无效
	 */
	public static String getLotteryWinUrlProcessor(int lotteryId){
		return AutoProperties.getString(lotteryId + ".winUrlProcessor", null, lotteryConfig);
	}

	/**
	 * 
	 * Title: getLotteryMoneyLimit<br>
	 * Description: <br>
	 * 根绝彩种id得到该彩种单个订单的限定金额<br>
	 * 
	 * @param lotteryId
	 * @return String <br>
	 *         null 没有找到配置项
	 */
	public static String getLotteryMoneyLimit(int lotteryId){
		return AutoProperties.getString(lotteryId + ".betMoneyLimit", "0", lotteryConfig).trim();
	}

	/**
	 * 
	 * Title: getLotteryMultiMoneyLimit<br>
	 * Description: <br>
	 * 根据彩种id得到该彩种单个订单的限定金额,适用于倍数投注限定<br>
	 * 
	 * @param lotteryId
	 * @return 配置的限定金额
	 */
	public static String getLotteryMultiMoneyLimit(int lotteryId){
		return AutoProperties.getString(lotteryId + ".betMultiMoneyLimit", "0", lotteryConfig).trim();
	}

	/**
	 * 
	 * Title: getLotteryBetNumLimit<br>
	 * Description: <br>
	 * 根据彩种id得到该彩种投注倍数的限制<br>
	 * 
	 * @param lotteryId
	 * @return 配置的倍数限定
	 */
	public static String getLotteryBetNumLimit(int lotteryId){
		return AutoProperties.getString(lotteryId + ".betMultiLimit", "0", lotteryConfig).trim();
	}

	/**
	 * 
	 * <br>
	 * 拆分足彩或竟猜的可投注的相关比赛信息 <br>
	 * 
	 * 返回的结果集合是经过排序的，排序规则为 ： <br>
	 * 对场次按照数字从小到大排序， <br>
	 * 
	 * @param salesInfo
	 *            传入的比赛信息字符串 格式如：1,A-AC米兰&B-国际米兰,赛制,比赛时间|2,A-切尔西&B-阿森纳,赛制,比赛时间
	 * @return Map&lt;Integer,Map&lt;String,String&gt;&gt;<br>
	 *            Map&lt;场次,Map&lt;key,value&gt;&gt;<br>
	 *            key定义为：<br>
	 *            A 表示主场
	 *            B 表示客场
	 *            C 表示赛制
	 *            D 表示比赛时间
	 * 
	 */
	public static Map<Integer, Map<String,String>> splitSalesInfo(String salesInfo){
		int litteryId = 1300001;// 足彩和竞彩的拆分规则都一样，所以可以按照胜负彩拆分
		return LotteryTools.splitSalesInfo(litteryId, salesInfo).getWinOrFailSaleInfo();
	}

	/**
	 * 
	 * Title: splitSalesInfo<br>
	 * Description: <br>
	 * 按照彩种拆分销售信息<br>
	 * 
	 * @param lotteryId
	 * @param salesInfo
	 * @return 包含拆分集合的信息实例
	 */
	private static LotteryInfo splitSalesInfo(int lotteryId, String salesInfo){
		LotteryInfo info = null;
		try{
			info = LotteryFactory.factory(String.valueOf(lotteryId)).splitSalesInfoResult(salesInfo, new LotteryInfo());
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitSalesInfo方法：拆分销售信息出错。" + "lotteryId = " + lotteryId + "salesInfo = " + salesInfo;
			logger.error(errorReason, e);
		}
		return info;
	}

	/**
	 * 
	 * <br>
	 * 合并足彩或竟猜的可投注的相关比赛信息 <br>
	 * 
	 * <br>
	 * 返回的字符串是经过排序的后生成的，排序规则为 ： <br>
	 * 对场次按照数字从小到大排序， <br>
	 * 
	 * @param infoMap
	 *            Map&lt;Integer,Map&lt;String,String&gt;&gt;<br>
	 *            Map&lt;场次,Map&lt;key,value&gt;&gt;<br>
	 *            key定义为：<br>
	 *            A 表示主场
	 *            B 表示客场
	 *            C 表示赛制
	 *            D 表示比赛时间
	 * @return String 返回类型 1,A-AC米兰&B-国际米兰,赛制,比赛时间|2,A-切尔西&B-阿森纳,赛制,比赛时间
	 */
	public static String mergeSalesInfo(Map<Integer, Map<String,String>> infoMap){
		LotteryInfo info = new LotteryInfo();
		int litteryId = 1300001;// 足彩和竞彩的拆分规则都一样，所以可以按照胜负彩合并
		info.setWinOrFailSaleInfo(infoMap);
		return LotteryTools.mergeSalesInfo(litteryId, info);
	}

	/**
	 * 
	 * Title: mergeSalesInfo<br>
	 * Description: <br>
	 * 按照彩种合并销售结果<br>
	 * 
	 * @param lotteryId
	 * @param info
	 * @return 合并后的字符串
	 */
	private static String mergeSalesInfo(int lotteryId, LotteryInfo info){
		String result = "";
		try{
			String innrerResult = LotteryFactory.factory(String.valueOf(lotteryId)).mergeSalesInfoResult(info);
			result = innrerResult != null ? innrerResult.replaceAll("\\r\\n", "").replaceAll("\\t", "") : innrerResult;
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.mergeSalesInfo方法：合并销售结果出错。" + "lotteryId = " + lotteryId;
			logger.error(errorReason, e);
		}
		return result;
	}

	/**
	 * 
	 * 校验投注的号码是否符合投注格式的要求<br>
	 * 
	 * @param lotteryId
	 *            彩种编号
	 * @param playType
	 *            玩法id
	 * @param betType
	 *            投注方式id
	 * @param betString
	 *            投注的号码
	 * @return boolean true 标识校验通过 false 标识校验失败
	 */
	public static boolean checkLotteryBetFormat(int lotteryId, int playType, int betType, String betString){
		boolean result = false;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).checkBetType(String.valueOf(playType), String.valueOf(betType), betString);
		}catch(LotteryUnDefineException e){
			String errorReason = "checkLotteryBetFormat方法： 根据参数未找到相对应的投注方式 " + "lotteryId = " + lotteryId + " playType = " + playType + " betType = " + betType;
			logger.error(errorReason, e);
		}catch(Exception e){
			String errorReason = "LotteryTools.checkLotteryBetFormat方法： 校验投注格式时发生异常 " + "lotteryId = " + lotteryId + " playType = " + playType + " betType = " + betType;
			logger.error(errorReason, e);
		}
		return result;
	}

	/**
	 * 
	 * <br>
	 * 根据彩种id和奖金结果字符串，将奖金结果字符串拆分为LotteryInfo定义的集合 <br>
	 * LotteryInfo定义的集合为： <br>
	 * TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt;
	 * superWinResult;//超级大乐透
	 * ，参数意思为：&lt;一等奖到八等奖(用数字1-8表示),&lt;基本奖(A)或追加奖(B),[注数,金额]&gt;&gt; <br>
	 * String [] happyZodiacWinResult;//生肖乐，数组长度为2，参数意思为：[注数，金额] <br>
	 * TreeMap&lt;String,String[]&gt; sevenColorWinResult;//七星彩
	 * ,数组长度为2，参数分别为:&lt;一等奖到六等奖,[注数,金额]&gt; <br>
	 * TreeMap&lt;String,String[]&gt; arrangeThreeWinResult;//排列三,
	 * 数组长度为2，参数分别为&lt;直选奖、组三奖和组六奖用(分别用1,2,3代表),[注数,金额]&gt; ,返回的集合中有可能不包含组三或组六或者组三和组六都不包含<br>
	 * String [] arrangeFiveWinResult;//排列五 ,数组长度为2，参数意思为：[注数，金额] <br>
	 * TreeMap&lt;String,String[]&gt; winOrFailWinResult;// 胜负彩
	 * TreeMap&lt;String,String[]&gt;,数组长度为2， 参数分别为&lt;一等奖或二等奖,[注数,金额]&gt; <br>
	 * String [] arbitry9WinResult;//任选九场 String [] ,数组长度为2，参数为[注数,金额] <br>
	 * String [] half6WinResult;//6场半全场 String [] ,数组长度为2，参数为[注数,金额] <br>
	 * String [] ball4WinResult;//4场进球彩 String [] ,数组长度为2，参数为[注数,金额] <br>
	 * TreeMap&lt;String,String[]&gt; elevenYunWinResul //十一运夺金
	 * 参数为&lt;奖金级别,[注数,金额]&gt; <br>
	 * //快乐扑克 <br>
	 * 使用时根据返回的LotteryInfo对象，调用其相应集合的get方法即可。 <br>
	 * 例如：TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt; superWinResult =
	 * LotteryTools
	 * .splitWinResult(1000001,"1-A10&A1971731#B4&B1183038|2-A54&12000#B9&B72"
	 * ).getSuperWinResult(); <br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @param winResult
	 *            奖金结果字符串
	 * @return LotteryInfo 彩种信息类
	 */
	public static LotteryInfo splitWinResult(int lotteryId, String winResult){
		LotteryInfo info = null;
		try{
			info = LotteryFactory.factory(String.valueOf(lotteryId)).splitWinResult(winResult, new LotteryInfo());
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitWinResult方法：拆分奖金结果出错。" + "lotteryId = " + lotteryId + "winResult = " + winResult;
			logger.error(errorReason, e);
		}
		return info;
	}
	
	/**
	 * 
	 * <br>
	 * 根据彩种id，将奖金结果集合合并为字符串 <br>
	 * LotteryInfo定义的集合为： <br>
	 * TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt;
	 * superWinResult;//超级大乐透
	 * ，参数意思为：&lt;一等奖到八等奖(用数字1-8表示),&lt;基本奖或追加奖,[注数,金额]&gt;&gt; <br>
	 * String [] happyZodiacWinResult;//生肖乐，数组长度为2，参数意思为：[注数，金额] <br>
	 * TreeMap&lt;String,String[]&gt; sevenColorWinResult;//七星彩
	 * ,数组长度为2，参数分别为:&lt;一等奖到六等奖,[注数,金额]&gt; <br>
	 * TreeMap&lt;String,String[]&gt; arrangeThreeWinResult;//排列三,
	 * 数组长度为2，参数分别为&lt;直选奖、组三奖和组六奖用(分别用1,2,3代表),[注数,金额]&gt; ，如果没有组3或组六则不要设置对应的集合元素<br>
	 * String [] arrangeFiveWinResult;//排列五 ,数组长度为2，参数意思为：[注数，金额] <br>
	 * TreeMap&lt;String,String[]&gt; winOrFailWinResult;// 胜负彩
	 * TreeMap&lt;String,String[]&gt;,数组长度为2， 参数分别为&lt;一等奖或二等奖,[注数,金额]&gt; <br>
	 * String [] arbitry9WinResult;//任选九场 String [] ,数组长度为2，参数为[注数,金额] <br>
	 * String [] half6WinResult;//6场半全场 String [] ,数组长度为2，参数为[注数,金额] <br>
	 * String [] ball4WinResult;//4场进球彩 String [] ,数组长度为2，参数为[注数,金额] <br>
	 * TreeMap&lt;String,String[]&gt; elevenYunWinResul //十一运夺金
	 * 参数为&lt;奖金级别,[注数,金额]&gt; <br>
	 * //快乐扑克 <br>
	 * 使用时实例化LotteryInfo对象，调用其相应集合的set方法即可。 <br>
	 * 例如： <br>
	 * TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt; superWinResult = new
	 * TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt;(); <br>
	 * superWinResult.put(....); <br>
	 * ..... <br>
	 * String superWinResultStr = LotteryTools.mergeWinResult(1000001,new
	 * LotteryInfo().setSuperWinResult(superWinResult)); <br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @param info
	 *            LotteryInfo对象
	 * @return String 合并后的奖金结果字符串
	 */
	public static String mergeWinResult(int lotteryId, LotteryInfo info){
		String result = "";
		try{
			String innrerResult = LotteryFactory.factory(String.valueOf(lotteryId)).mergeWinResult(info);
			result = innrerResult != null ? innrerResult.replaceAll("\\r\\n", "").replaceAll("\\t", "") : innrerResult;
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.mergeWinResult方法：合并奖金结果出错。" + "lotteryId = " + lotteryId;
			logger.error(errorReason, e);
		}
		return result;
	}
	/**
	 * 
	 * Title: splitMissCount<br>
	 * Description: <br>
	 *            拆分遗漏信息,字符串的格式必须严格按照数据格式的定义,遗漏信息集合定义在LotteryInfo中<br>
	 *            <br> 遗漏信息集合的定义:
	 *            <br>参数为:&lt;key,value&gt; value为遗漏的次数
	 *            <br>key说明: 
	 *                <li>大乐透 H-号码,H表示前区,号码为01-35;B-号码,B表示后区,号码为01-12
	 *                <li>生肖乐 B-号码 号码为01-12
	 *                <li>七星彩  P-位置-号码 ，其中位置为1-7,号码为0-9
	 *                <li>排列三 Z-位置-号码 ,Z表示直选,位置为1-3,号码为0-9;  H-号码,H表示和值,号码为1-26
	 *                <li>排列五 P-位置-号码 ,位置为1-5，号码为0-9
	 *                <li>
	 *                
	 * @param lotteryId 彩种id
	 * @param missCount 遗漏信息字符串
	 * @return LotteryInfo
	 */
	public static LotteryInfo splitMissCount(int lotteryId, String missCount){
		LotteryInfo info = null;
		try{
			info = LotteryFactory.factory(String.valueOf(lotteryId)).splitMissCount(missCount, new LotteryInfo());
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitMissCount方法：拆分遗漏信息出错。" + "lotteryId = " + lotteryId + "missCount = " + missCount;
			logger.error(errorReason, e);
		}
		return info;
	}
	/**
	 * 
	 * Title: splitMissCountToMap<br>
	 * Description: <br>
	 *            拆分遗漏信息<br>
	 *            <br> 遗漏信息集合的定义:
	 *            <br>参数为:<key,value> value为遗漏的次数
	 *            <br>key说明: 
	 *                <li>大乐透 H-号码,H表示前区,号码为01-35;B-号码,B表示后区,号码为01-12
	 *                <li>生肖乐 B-号码 号码为01-12
	 *                <li>七星彩  P-位置-号码 ，其中位置为1-7,号码为0-9
	 *                <li>排列三 Z-位置-号码 ,Z表示直选,位置为1-3,号码为0-9;  H-号码,H表示和值,号码为1-26
	 *                <li>排列五 P-位置-号码 ,位置为1-5，号码为0-9
	 * @param lotteryId 彩种
	 * @param missCount 遗漏信息字符串 
	 * @return Map<String,Integer> 如果返回null可能是拆分出错或者该彩种没有遗漏信息
	 */
	public static Map<String,Integer> splitMissCountToMap(int lotteryId, String missCount){
		Map<String,Integer> missCountMap = null;
		try{
			missCountMap = LotteryFactory.factory(String.valueOf(lotteryId)).splitMissCount(missCount, new LotteryInfo()).getMissCountResult();
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitMissCount方法：拆分遗漏信息出错。" + "lotteryId = " + lotteryId + "missCount = " + missCount;
			logger.error(errorReason, e);
		}
		return missCountMap;
	}
	
	
	/**
	 * <p>
	 * <br>
	 * 根据彩种id和开奖结果字符串，将开奖结果字符串拆分为LotteryInfo定义的集合 <br>
	 * LotteryInfo定义的集合为： <br>
	 * TreeMap&lt;String,ArrayList&lt;String&gt;&gt; superLotteryResult;//超级大乐透
	 * ,TreeMap&lt;String,ArrayList&lt;String&gt;&gt;
	 * ，参数为&lt;前去|后区,&lt;号码数组&gt;&gt;,其中前区用1表示，后区用2表示，两位一个号码，不足左补'0' <br>
	 * ArrayList&lt;String&gt;
	 * happyZodiacLotteryResult;//生肖乐,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;,应该包含2个号码，两位一个号码，不足左补0 <br>
	 * ArrayList&lt;String&gt;
	 * sevenColorLotteryResult;//七星彩,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;，应该包含7个号码 <br>
	 * ArrayList&lt;String&gt;
	 * arrangeThreeLotteryResult;//排列三,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;，应该包含3个号码 <br>
	 * ArrayList&lt;String&gt;
	 * arrangeFiveLotteryResult;//排列五,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;，应该包含5个号码 <br>
	 * ArrayList&lt;String&gt;
	 * winOrFailLotteryResult;//胜负彩,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;，应该包含14个号码 <br>
	 * ArrayList&lt;String&gt;
	 * arbitry9LotteryResult;//任选九场,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;，应该包含14个号码 <br>
	 * ArrayList&lt;String&gt;
	 * half6LotteryResult;//6场半全场,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;，应该包含12个号码 <br>
	 * ArrayList&lt;String&gt;
	 * ball4LotteryResult;//4场进球彩,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;，应该包含8个号码 <br>
	 * ArrayList&lt;String&gt;
	 * elevenYunLotteryResult;//十一运夺金,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;，两位一个号码，共五个号码 <br>
	 * ArrayList&lt;String&gt;
	 * happyPokerLotteryResult;//快乐扑克,ArrayList&lt;String&gt;
	 * ，参数为&lt;号码&gt;，4位，一位一个号码，号码为2-9或A、J、Q、K，按照黑红梅方顺序 <br>
	 * 使用时根据返回的LotteryInfo对象，调用其相应集合的get方法即可。 <br>
	 * 例如：TreeMap&lt;String,ArrayList&lt;String&gt;&gt; superLotteryResult =
	 * LotteryTools
	 * .splitLotteryResult(1000001,"0102030405|0102").getSuperLotteryResult();
	 * </p>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @param lotteryResult
	 *            开奖结果字符串
	 * @return LotteryInfo 彩种信息类
	 */
	public static LotteryInfo splitLotteryResult(int lotteryId, String lotteryResult){
		LotteryInfo info = null;
		try{
			info = LotteryFactory.factory(String.valueOf(lotteryId)).splitLotteryResult(lotteryResult, new LotteryInfo());
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitLotteryResult方法：拆分开奖结果出错。" + "lotteryId = " + lotteryId + "lotteryResult = " + lotteryResult;
			logger.error(errorReason, e);
		}
		return info;
	}

	/**
	 * <p>
	 * 根据彩种id，将开奖结果集合合并为字符串 <br>
	 * LotteryInfo定义的集合为： <code>
	 * <br>TreeMap&lt;String,ArrayList&lt;String&gt;&gt; superLotteryResult;//超级大乐透 ,TreeMap&lt;String,ArrayList&lt;String&gt;&gt;&gt; ，参数为&lt;前去|后区,&lt;号码数组&gt;&gt;,其中前区用1表示，后区用2表示，两位一个号码，不足左补'0'
	 * <br>ArrayList&lt;String&gt; happyZodiacLotteryResult;//生肖乐,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;,应该包含2个号码，两位一个号码，不足左补0
	 * <br>ArrayList&lt;String&gt; sevenColorLotteryResult;//七星彩,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;，应该包含7个号码
	 * <br>ArrayList&lt;String&gt; arrangeThreeLotteryResult;//排列三,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;，应该包含3个号码
	 * <br>ArrayList&lt;String&gt; arrangeFiveLotteryResult;//排列五,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;，应该包含5个号码
	 * <br>ArrayList&lt;String&gt; winOrFailLotteryResult;//胜负彩,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;，应该包含14个号码
	 * <br>ArrayList&lt;String&gt; arbitry9LotteryResult;//任选九场,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;，应该包含14个号码
	 * <br>ArrayList&lt;String&gt; half6LotteryResult;//6场半全场,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;，应该包含12个号码
	 * <br>ArrayList&lt;String&gt; ball4LotteryResult;//4场进球彩,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;，应该包含8个号码
	 * <br>ArrayList&lt;String&gt; elevenYunLotteryResult;//十一运夺金,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;，两位一个号码，共五个号码
	 * <br>ArrayList&lt;String&gt; happyPokerLotteryResult;//快乐扑克,ArrayList&lt;String&gt; ，参数为&lt;号码&gt;，4位，一位一个号码，号码为2-9或A、J、Q、K，按照黑红梅方顺序      
	 * <br>使用时实例化LotteryInfo对象，调用其相应集合的set方法即可。
	 * <br>例如：
	 * <br>TreeMap&lt;String,ArrayList&lt;String&gt;&gt; superLotteryResult = new TreeMap&lt;String,ArrayList&lt;String&gt;&gt;();
	 * <br>superLotteryResult.put(....);
	 * <br>.....
	 * <br>String superLotteryResultStr = LotteryTools.mergeWinResult(1000001,new LotteryInfo().setSuperWinResult(superWinResult));
	 * </code>
	 * </p>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @param info
	 *            LotteryInfo对象
	 * @return String 合并后的开奖结果字符串
	 */
	public static String mergeLotteryResult(int lotteryId, LotteryInfo info){
		String result = "";
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).mergeLotteryResult(info);
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.mergeLotteryResult：合并开奖结果出错。" + "lotteryId = " + lotteryId;
			logger.error(errorReason, e);
		}
		return result;
	}

	/**
	 * 
	 * Title: splitTermAllResult<br>
	 * Description: <br>
	 * 拆分彩种的开奖结果、奖金结果、销售信息<br>
	 * 只有足彩和竞彩有销售信息，其他彩种的销售信息没有定义<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @param lotteryResult
	 *            开奖结果
	 * @param winResult
	 *            奖金结果
	 * @param salesInfo
	 *            销售信息
	 * @param missCount 
	 *            遗漏信息
	 * @return 包含拆分集合的信息实例
	 */
	public static LotteryInfo splitTermAllResult(int lotteryId, String lotteryResult, String winResult, String salesInfo,String missCount){
		LotteryInfo info = new LotteryInfo();
		try{
			LotteryInterf lotteryInstance = LotteryFactory.factory(String.valueOf(lotteryId));
			if(lotteryResult != null){
				lotteryInstance.splitLotteryResult(lotteryResult, info);
			}
			if(winResult != null){
				lotteryInstance.splitWinResult(winResult, info);
			}
			if(salesInfo != null){
				lotteryInstance.splitSalesInfoResult(salesInfo, info);
			}
			if(missCount != null){
				lotteryInstance.splitMissCount(missCount, info);
			}
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitTermAllResult方法：拆分彩期结果出错。"
					+ "lotteryId = " + lotteryId + "lotteryResult = "
					+ lotteryResult + "missCount=" + missCount;
			logger.error(errorReason, e);
		}
		return info;
	}

	/**
	 * 
	 * <br>
	 * 对投注号码字符串（betCode）开奖，返回的集合为线性列表，子线性列表包括&lt;单注注码,奖金级别,奖金金额,追加金额&gt;, <br>
	 * 对于没有追加的则该值为0 <br>
	 * 其中对某些彩种奖金级别可能为为空 <br>
	 * 该方法包含校验投注串格式是否正确
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @param playType
	 *            玩法id
	 * @param betType
	 *            投注方式id
	 * @param lotteryResult
	 *            开奖结果
	 * @param winReslut
	 *            奖金结果
	 * @param betCode
	 *            投注号码字符串
	 * @return List&lt;List&lt;String&gt;&gt;
	 *         
	 *         其中对某些彩种奖金级别可能为为空,奖金金额的单位为分<br>
	 *         如果返回null，则有可能是投注串格式不正确或程序出现了错误
	 */
	public static List<PrizeInfo> lotteryPrize(int lotteryId, int playType, int betType, String lotteryResult, String winReslut, String betCode,int betMulti){
		List<PrizeInfo> prizeResult = new ArrayList<PrizeInfo>();;
		List<List<String>> tmpResult = null;
		try{
			/*
			if(!LotteryTools.checkLotteryBetFormat(lotteryId, playType, betType, betCode)){
				return result;
			}*/
			//单注注码,奖金级别,奖金金额,追加金额
			tmpResult = LotteryFactory.factory(String.valueOf(lotteryId))
					.lotteryPrize(String.valueOf(playType),
							String.valueOf(betType), lotteryResult, winReslut,
							betCode);
			if(tmpResult != null && !tmpResult.isEmpty()){
				int betBeiShu = betMulti > 0 ? betMulti : 1;
				int taxPercent = AutoProperties.getInt(lotteryId + ".taxPercent", 0, lotteryConfig);
				int sysBigPrize = AutoProperties.getInt(lotteryId + ".superPrizeMoney", 1000000, lotteryConfig);
				for(List<String> oneCodePrize : tmpResult){
					PrizeInfo oneInfo = new PrizeInfo();
					oneInfo.setWinCode(oneCodePrize.get(0));
					oneInfo.setPrizeLevel(Integer.parseInt(oneCodePrize.get(1)));
					oneInfo.setBasePrize(Long.parseLong(oneCodePrize.get(2))*betBeiShu*100);
					oneInfo.setAddPrize(Long.parseLong(oneCodePrize.get(3))*betBeiShu*100);
					oneInfo.setTaxPercent(taxPercent);
					long totalPrize = oneInfo.getBasePrize() + oneInfo.getAddPrize();
					if(totalPrize > sysBigPrize){//奖金大于1万 要扣税
						long taxPrize = (totalPrize * taxPercent)/100;
						oneInfo.setTaxPrize(taxPrize);
						long aftTaxPrize = (totalPrize * (100 - taxPercent))/100;
						oneInfo.setAftTaxPrize(aftTaxPrize);
					}else{//不用扣税
						oneInfo.setAftTaxPrize(totalPrize);
					}
					prizeResult.add(oneInfo);
				}
			}
		}catch(LotteryUnDefineException e){
			logger.error("对投注号码字符串开奖时未找到相应的玩法或投注方式", e);
			return prizeResult;
		}catch(Exception e){
			logger.error("对投注号码字符串开奖时发生程序错误", e);
			return prizeResult;
		}
		return prizeResult;
	}

	/**
	 * 
	 * Title: lotteryBetToSingle<br>
	 * Description: <br>
	 * 计算投注串包含的注数和金额，投注金额单位为分，可以包含单式投注<br>
	 * 该方法未校验投注串格式是否正确,使用该方法是需要先保证投注格式的正确<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @param playType
	 *            玩法id
	 * @param betType
	 *            投注方式id
	 * @param betCode
	 *            投注号码字符串
	 * @return 返回投注注数的金额，用'#'符号分割，格式为：投注注数#投注金额，如果返回null则表示参数错误或处理出错。
	 */
	public static String lotteryBetToSingle(int lotteryId, int playType, int betType, String betCode){
		String result = null;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).lotterySplit(String.valueOf(playType), String.valueOf(betType), betCode);
		}catch(LotteryUnDefineException e){
			logger.error("拆分投注串时未找到相应的玩法或投注方式", e);
		}catch(Exception e){
			logger.error("拆分投注串时发生程序错误", e);
		}
		return result;
	}

	/**
	 * 
	 * Title: lotteryToSingleList<br>
	 * Description: <br>
	 * 将投注转拆分为单式的集合，投注金额单位为分，可以包含单式投注<br>
	 * 该方法未校验投注串格式是否正确,使用该方法是需要先保证投注格式的正确<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @param playType
	 *            玩法id
	 * @param betType
	 *            投注方式id
	 * @param betCode
	 *            投注号码字符串
	 * @return 返回单式投注的集合,如果程序出现异常则返回null
	 */
	public static List<String> lotteryBetToSingleList(int lotteryId, int playType, int betType, String betCode){
		List<String> result = null;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).lotteryToSingle(String.valueOf(playType), String.valueOf(betType), betCode);
		}catch(LotteryUnDefineException e){
			logger.error("拆分投注串时未找到相应的玩法或投注方式", e);
		}catch(Exception e){
			logger.error("拆分投注串时发生程序错误", e);
		}
		return result;
	}

	/**
	 * Title: lotteryRandomBetCode<br>
	 * Description: <br>
	 * 随机生成某一彩种的单式单倍一注<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @return 返回产生的随机投注注码。返回null或空字符串为产生随机注码失败
	 */
	public static String lotteryRandomBetCode(int lotteryId){
		String result = null;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).lotteryRandomBetCode();
		}catch(Exception e){
			logger.error("获得随机单式一注(" + lotteryId + ")时发生程序错误", e);
		}
		return result;
	}

	/**
	 * 
	 * Title: accountTransactionRemark<br>
	 * Description: <br>
	 * 格式化交易备注信息<br>
	 * 
	 * @param lotteryId
	 *            彩种id
	 * @param termNO
	 *            期数
	 * @param type
	 *            类型 10002：投注当前期，20002:投注追号，31006:追号撤销,11002:派奖，31117:限号追号撤销
	 * @return 转换后的备注信息
	 */
	public static String accountTransactionRemark(int lotteryId, String termNO, int type){
		String remark = "";
		switch(type){
			case 10002:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期购买";
				break;
			case 20002:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "追号";
				break;
			case 31006:
				remark = "撤销原因:中奖后撤销";
				break;
			case 11002:
				remark = LotteryTools.getLotteryName(lotteryId) + "派奖";
				break;
			case 30001:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期购买";
				break;
				//潘祖朝增加的限号取消的撤销原因，直接以String存到数据库中，取的时候直接从数据库中取出
			case 31117:
				remark = "撤销原因:限号撤销";
				break;
			case 20003:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期发起合买冻结";
				break;
			case 20009:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期合买保底冻结";
				break;
			case 31009:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期合买保底撤销";
				break;
			case 30002:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期合买投注";
				break;
			case 20005:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期参与合买冻结";
				break;
			case 31005:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期参与撤销";
				break;
			case 31003:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期方案撤销";
				break;
			case 30006:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "期保底转投注";
				break;
			case 31010:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "出票失败订单退款";
				break;
			default:
				break;
		}
		return remark;
	}

	/**
	 * 
	 * Title: getLotterySuperPrizeMoney<br>
	 * Description: <br>
	 * 获取彩种奖金级别定义<br>
	 * 
	 * @param lotteryId
	 * @return <br>
	 *         null 未定义
	 */
	public static String getLotterySuperPrizeMoney(int lotteryId){
		return AutoProperties.getString(lotteryId + ".superPrizeMoney", "1000000", lotteryConfig);
	}

	/**
	 * 取某一位的值
	 * 
	 * @param intStr
	 * @param pos
	 * @return 转换后的数字
	 */
	private static int getIntegerCharAt(Integer intStr, int pos){
		char[] c = String.valueOf(intStr).toCharArray();
		return Integer.parseInt(String.valueOf(c[pos - 1]));
	}

	/**
	 * 从彩种列表的的彩种项获取符合指定位置的指定标识的Map集合，集合返回的是Hashtable
	 * 
	 * @param pos
	 *            指定位置
	 * @param lotteryOrgan
	 *            指定标识
	 * @return Hashtable<Integer, String>
	 */
	private static Map<Integer, String> getLotteryByPos(int pos, int lotteryOrgan){
		Map<Integer, String> retLotteryMap = new Hashtable<Integer, String>();
		Map<Integer, String> lotteryMap = getLotteryList();
		for(Map.Entry<Integer, String> entry : lotteryMap.entrySet()){
			if(getIntegerCharAt(entry.getKey(), pos) == lotteryOrgan){
				retLotteryMap.put(entry.getKey(), entry.getValue());
			}
			;
		}
		return retLotteryMap;
	}

	/**
	 * 用来根据从配置文件中读取的玩法格式字符串，根据定义的规则拆解为玩法集合，包括玩法id和name<br>
	 * 返回的集合为线程同步的<br>
	 * 
	 * @param playStr
	 * @return Map<Integer,String> 玩法集合 ，参数分别为<玩法id，玩法名称>
	 */
	private static Map<Integer, String> splitPlayStrForPlayMap(String playStr){
		Map<Integer, String> lotteryPlay = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());
		String[] lotteryPlayArr = playStr.split("\\|");// 将各种玩法方式分开
		String[] tmpSub;
		for(String sub : lotteryPlayArr){
			String[] sub1 = sub.split("&");// 将玩法方式与投注方式分开
			tmpSub = sub1[0].split(",");// 将玩法方式和玩法名称分开
			Integer playId = Integer.valueOf(tmpSub[0]);
			if(tmpSub.length < 2){
				lotteryPlay.put(playId, "");// 分别问玩法方式id和name
			}else{
				String playName = tmpSub[1];
				lotteryPlay.put(playId, playName);// 分别问玩法方式id和name
			}
		}
		return lotteryPlay;
	}

	/**
	 * 
	 * 根据从配置文件中读取的玩法格式字符串，根据定义的规则拆解为玩法对应的投注方式集合<br>
	 * 返回的集合为线程同步的<br>
	 * 
	 * @param playStr
	 * @return Map<Integer, Map<Integer,String>> 玩法对应的投注方式集合，参数分别为
	 *         <玩法id，<投注方式id，投注方式名称>>
	 */
	private static Map<Integer, Map<Integer, String>> splitPlayStrForPlayBet(String playStr){
		Map<Integer, Map<Integer, String>> lotteryPlayBetList = Collections.synchronizedSortedMap(new TreeMap<Integer, Map<Integer, String>>());
		String[] lotteryPlayArr = playStr.split("\\|");// 将各种玩法方式分开
		String[] tmpSub;
		for(String sub : lotteryPlayArr){
			String[] sub1 = sub.split("&");// 将玩法方式与投注方式分开
			tmpSub = sub1[0].split(",");// 将玩法方式和玩法名称分开
			Integer playId = Integer.valueOf(tmpSub[0]);
			tmpSub = sub1[1].split(";");// 将各种投注方式分开
			Map<Integer, String> bet = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());
			for(String betPlay : tmpSub){
				String[] subBetPlay = betPlay.split(",");
				bet.put(Integer.valueOf(subBetPlay[0]), subBetPlay[1]);
			}
			lotteryPlayBetList.put(playId, bet);
		}
		return lotteryPlayBetList;
	}

	/**
	 * 获得新的遗漏字符串，遗漏字符串存储规则参考数据状态格式核心定义文档。
	 * @param lotteryId
	 * 		彩种id
	 * @param lotteryResult
	 * 		开奖结果
	 * @param lastMissCount
	 * 		最近一次遗漏
	 * @return
	 * 		新的遗漏字符串
	 */
	public static String getMissCount(int lotteryId, String lotteryResult, String lastMissCount){
		String result = null;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).getMissCount(lotteryResult, lastMissCount);
		}catch(Exception e){
			logger.error("获得遗漏字符串(" + lotteryId + ")时发生程序错误", e);
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WinResult> splitWinResult(int lotteryId, LotteryInfo info){
		List<WinResult> result = null;
		try{
			if(info != null){
				result = new ArrayList<WinResult>();
				switch (lotteryId) {
				case 1000001:
					TreeMap<String,TreeMap<String,String[]>>  superWinResult = info.getSuperWinResult();
					if(superWinResult != null){
						for(Map.Entry<String, TreeMap<String,String[]>> one : superWinResult.entrySet()){
							WinResult win = new WinResult();
							String key = one.getKey();
							Map<String,String[]> value = one.getValue();
							win.setWinId(Integer.parseInt(key));
							win.setWinCount(value.get("A")[0]);
							win.setWinMoney(value.get("A")[1]);
							if(value.get("B") != null){
								win.setWinAddCount(value.get("B")[0]);
								win.setWinAddMoney(value.get("B")[1]);
							}
							result.add(win);
						}
					}
					
					break;
				case 1000005:
					String [] happyZodiacWinResult = info.getHappyZodiacWinResult();
					if(happyZodiacWinResult != null){
						WinResult win = new WinResult();
						win.setWinId(1);
						win.setWinCount(happyZodiacWinResult[0]);
						win.setWinMoney(happyZodiacWinResult[1]);
						result.add(win);
					}
					break;
				case 1000002:
					TreeMap<String,String[]> sevenColorWinResult = info.getSevenColorWinResult();
					if (sevenColorWinResult != null) {
						for (Map.Entry<String, String[]> one : sevenColorWinResult
								.entrySet()) {
							WinResult win = new WinResult();
							win.setWinId(Integer.parseInt(one.getKey()));
							win.setWinCount(one.getValue()[0]);
							win.setWinMoney(one.getValue()[1]);
							result.add(win);
						}
					}
					break;
				case 1000003:
					TreeMap<String,String[]> arrangeThreeWinResult = info.getArrangeThreeWinResult();
					if(arrangeThreeWinResult != null){
						for(Map.Entry<String, String[]> one : arrangeThreeWinResult.entrySet()){
							WinResult win = new WinResult();
							win.setWinId(Integer.parseInt(one.getKey()));
							win.setWinCount(one.getValue()[0]);
							win.setWinMoney(one.getValue()[1]);
							result.add(win);
						}
					}
					
					break;
				case 1000004:
					String [] arrangeFiveWinResult = info.getArrangeFiveWinResult();
					if(arrangeFiveWinResult != null){
						WinResult win = new WinResult();
						win.setWinId(1);
						win.setWinCount(arrangeFiveWinResult[0]);
						win.setWinMoney(arrangeFiveWinResult[1]);
						result.add(win);
					}
					break;
				case 1300001:
					TreeMap<String,String[]> winOrFailWinResult = info.getWinOrFailWinResult();
					if(winOrFailWinResult != null){
						for(Map.Entry<String, String[]> one : winOrFailWinResult.entrySet()){
							WinResult win = new WinResult();
							win.setWinId(Integer.parseInt(one.getKey()));
							win.setWinCount(one.getValue()[0]);
							win.setWinMoney(one.getValue()[1]);
							result.add(win);
						}
					}
					break;
				case 1300002:
					String [] arbitry9WinResult = info.getArbitry9WinResult();
					if(arbitry9WinResult != null){
						WinResult win = new WinResult();
						win.setWinId(1);
						win.setWinCount(arbitry9WinResult[0]);
						win.setWinMoney(arbitry9WinResult[1]);
						result.add(win);
					}
					break;
				case 1300003:
					break;
				case 1300004:
					break;
				case 1200001:
					break;
				case 1200002:
					break;
				default:
					break;
				} 
				Collections.sort(result);
			}
			
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitWinResult方法：拆分奖金结果出错。" + "lotteryId = " + lotteryId ;
			logger.error(errorReason, e);
		}
		return result;
	}
	
	public static List<SalesResult> splitSalesInfo(int lotteryId, LotteryInfo info){
		List<SalesResult> result = null;
		if(info != null){
			result = new ArrayList<SalesResult>();
			Map<Integer, Map<String,String>> SaleInfo = null;
			switch (lotteryId) {
			case 1300001:
				SaleInfo = info.getWinOrFailSaleInfo();
				break;
			case 1300002:
				SaleInfo = info.getArbitrary9SaleInfo();
				break;
			default:
				break;
			}
			if(SaleInfo != null){
				for(Map.Entry<Integer, Map<String,String>> one : SaleInfo.entrySet()){
					SalesResult sale = new SalesResult();
					sale.setSlaesId(one.getKey());
					sale.setHomeTeam(one.getValue().get("A"));
					sale.setAwayTeam(one.getValue().get("B"));
					sale.setLeagueMatch(one.getValue().get("C"));
					sale.setGameDate(one.getValue().get("D"));
					result.add(sale);
				}
			}
			Collections.sort(result);
		}
		return result;
	}
	/**
	 * 
	 * Title: mergeLimitResult<br>
	 * Description: <br>
	 *              <br>限号信息集合合并为数据库定义的格式
	 * @param lotteryId
	 * @param limitInfo
	 * @return
	 */
	public static String mergeLimitResult(int lotteryId,Map<String,String>limitInfo){
		String limitStr = null;
		try{
			if(limitInfo != null){
				switch (lotteryId) {
				case 1000003://排列3
					limitStr = limitInfo.get("1");
					break;
				case 1200001://江西多乐彩
					StringBuffer sb = new StringBuffer();
					for(Map.Entry<String, String> entry : limitInfo.entrySet()){
						sb.append(entry.getKey()).append("-").append(entry.getValue()).append("|");
					}
					if(sb.toString().endsWith("|")){
						sb.deleteCharAt(sb.lastIndexOf("|"));
					}
					limitStr = sb.toString();
					break;
				default:
					break;
				}
			}
		}catch(Exception e){
			String errorReason = "LotteryTools.smergeLimitResult方法：合并限号信息出错。" + "lotteryId = " + lotteryId ;
			logger.error(errorReason, e);
		}
		return limitStr;
	}
	/**
	 * 
	 * Title: splitLimitResult<br>
	 * Description: <br>
	 *              <br>拆分限号字符串
	 * @param lotteryId
	 * @param limitStr
	 * @return
	 */
	public static Map<String,String> splitLimitResult(int lotteryId,String limitStr){
		Map<String,String> result = null;
		try{
			if(StringUtils.isNotEmpty(limitStr)){
				switch (lotteryId) {
				case 1000003:
					result = new TreeMap<String,String>();
					result.put("1", limitStr);
					break;
				case 1200001:
					result = new TreeMap<String,String>();
					String [] limitPool = limitStr.split("\\|");
					if(limitPool != null && limitPool.length != 0){
						for(String limit : limitPool){
							String [] limitOne = limit.split("-");
							if(limitOne != null && limitOne.length == 2){
								result.put(limitOne[0], limitOne[1]);
							}
						}
					}
					break;
				default:
					break;
				}
			}
		}catch(Exception e){
			String errorReason = "LotteryTools.splitLimitResult方法：拆分限号信息出错。" + "lotteryId = " + lotteryId ;
			logger.error(errorReason, e);
		}
		return result;
	}
	/**
	 * 
	 * Title: getLotteryEhand<br>
	 * Description: <br>
	 *              <br>从配置文件获取投注系统的掌中奕系统的彩种对应关系
	 * @return
	 */
	public static Map<Integer, String> getLotteryEhand(){
		Map<Integer, String> lotteryMap = new TreeMap<Integer, String>();
		try{
			String lotteryStr = AutoProperties.getString("LotteryEhand", "", lotteryConfig);
			String[] lotteryArry = lotteryStr.split(LOTTERYlIST_SEPARATOR);
			for(String str : lotteryArry){
				String[] subLottery = str.split(",");
				lotteryMap.put(Integer.parseInt(subLottery[0]), subLottery[1]);
			}
		}catch(Exception e){
			logger.debug("LotteryTools.getLotteryEhand occur Exception:" + e.getMessage());
		}
		return lotteryMap;
	}
	/**
	 * 
	 * Title: getCoopPercent<br>
	 * Description: <br>
	 *              <br>获取各彩种定义的合买允许提成比例
	 * @param lotteryId
	 * @param betType
	 * @return
	 */
	public static String getCoopPercent(int lotteryId,int betType){
		try{
			return AutoProperties.getString(lotteryId+"."+betType, "0-0", lotteryConfig);
		}catch(Exception e){
			return "0-0";
		}
	}
	/**
	 * 
	 * Title: getJoinRevocate<br>
	 * Description: <br>
	 *              <br>获取各彩种合买参与人不能撤销的方案进度
	 * @param lotteryId
	 * @return
	 */
	public static int getJoinRevocate(int lotteryId){
		try{
			return AutoProperties.getInt(String.valueOf(lotteryId)+".joinRevocate", 0, lotteryConfig);
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * 
	 * Title: getCreateRevocate<br>
	 * Description: <br>
	 *              <br>获取各彩种合买发起人不能撤销的方案进度
	 * @param lotteryId
	 * @return
	 */
	public static int getCreateRevocate(int lotteryId){
		try{
			return AutoProperties.getInt(String.valueOf(lotteryId)+".createRevocate", 0, lotteryConfig);
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * 
	 * Title: getSysSequencePre<br>
	 * Description: <br>
	 *              <br>获取系统编号的前缀，用于区分不同的系统编号重复问题
	 * @return
	 */
	public static String getSysSequencePre(){
		try{
			return AutoProperties.getString("LotterySEQUENCE", "W", lotteryConfig);
		}catch(Exception e){
			return "W";
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// System.out.println(LotteryTools.getCheckCron(1000001));
		// Integer t = 100001;
		// Map<Integer,String> tmp =
		// getLotteryPlayList(1000001).getLotteryPlayMap();
		// Map<Integer,String> tmp = Collections.synchronizedSortedMap(new
		// TreeMap<Integer,String>());
		// 测试拆解足彩或竟猜的可投注的相关比赛信息;
		
		 // String tt = "2,B-阿森纳&A-切尔西,赛制1,201004121010|1,B-国际米兰&A-AC米兰,赛制2,201004121010"; 
		 // System.out.println(tt);
		 // System.out.println(splitSalesInfo(tt));
		  //System.out.println(mergeSalesInfo(splitSalesInfo(tt)));
		  //System.out.println
		  //(splitSalesInfo(mergeSalesInfo(splitSalesInfo(tt))));
		/*  
		  Map<Integer,Map<String,String>> resultMap = new HashMap<Integer,Map<String,String>>();
		  
		  HashMap<String,String> map1 = new HashMap<String,String>();
		  map1.put("B", "阿森纳"); 
		  map1.put("A", "切尔西");
		  map1.put("C", "赛制1");
		  map1.put("D", "2010");
		  resultMap.put(2, map1);
		  
		  HashMap<String,String> map2 = new HashMap<String,String>();
		  map2.put("B", "国际米兰");
		  map2.put("A", "AC米兰"); 
		  resultMap.put(1,map2);
		  
		  //System.out.println(resultMap);
		  System.out.println(mergeSalesInfo(resultMap));
		 */
		/*
		 * //测试大乐透投注格式 //单式 String betGroup = "0102030405|0607^0109101112|0809";
		 * 
		 * //复式 String multiBet = "010203040506|0607^0109101112|0809"; boolean
		 * result = checkLotteryBetFormat(1000001, 0, 1, multiBet);
		 * System.out.println("result==="+result);
		 * 
		 * 
		 * //测试大乐透复式转单式
		 * 
		 * List<String> superList = LotteryTools.lotteryBetToSingleList(1000001,
		 * 0, 1, multiBet); for(String bet : superList){
		 * System.out.println("bet:"+bet); }
		 */
		/*
		 * //测试大乐透胆拖 String betGroup = "0102*030405|*020304"; boolean result =
		 * checkLotteryBetFormat(1000001, 0, 2, betGroup);
		 * System.out.println("result==="+result);
		 * 
		 * List<String> superList = LotteryTools.lotteryBetToSingleList(1000001,
		 * 0, 2, betGroup); for(String bet : superList){
		 * System.out.println("bet:"+bet); }
		 */
		// 测试大乐透胆拖转单式
		/*
		 * //测试拆解和合并奖金结果 int lotteryId = 1000001; String winResult =
		 * "1-A10&A1971731#B4&B1183038|2-A54&A12000#B9&B72|3-A54&A10000#B9&B72|4-A54&A9000#B9&B72|5-A54&A6000#B9&B72|6-A54&A5000#B9&B72|7-A54&A4000#B9&B72|8-A54&A2000"
		 * ; LotteryInfo info = splitWinResult(1000001,winResult);
		 * TreeMap<String,TreeMap<String,String[]>> superWin =
		 * info.getSuperWinResult(); for(Map.Entry<String,
		 * TreeMap<String,String[]>> map : superWin.entrySet()){
		 * System.out.println("#########################"); String key =
		 * map.getKey(); System.out.println("key === "+key);
		 * TreeMap<String,String[]> map2 = map.getValue(); for(Map.Entry<String,
		 * String[]> map3 : map2.entrySet()){ String key2 = map3.getKey();
		 * System.out.println("key2 === "+key2); String [] value =
		 * map3.getValue(); System.out.println("value[0] === "+value[0]);
		 * System.out.println("value[1] === "+value[1]); }
		 * System.out.println("#########################"); } LotteryInfo info2
		 * = new LotteryInfo(); info2.setSuperWinResult(superWin); String
		 * backWinResult = mergeWinResult(1000001, info2);
		 * System.out.println("winResult    =:"+winResult);
		 * System.out.println("backWinResult=:"+backWinResult);
		 */
		 //测试大乐透开奖
//		 int lotteryId = 1000001;
//		 int playType = 0;
//		 int betType = 1;
//		 String lotteryResult = "0712263032|0207";
//		 String winResult =
//		 "1-A10&A1971731#B4&B10|2-A54&A12000#B9&B20|3-A54&A10000#B9&B30|4-A54&A9000#B9&B40|5-A54&A6000#B9&B50|6-A54&A5000#B9&B60|7-A54&A4000#B9&B70|8-A54&A2000";
//		 LotteryInfo info = splitWinResult(1000001,winResult);
//		 TreeMap<String,TreeMap<String,String[]>> superWin = info.getSuperWinResult();
				
//		 for(Map.Entry<String, TreeMap<String,String[]>> map :superWin.entrySet()){
//		 System.out.println("#########################");
//		 String key = map.getKey();
//		 System.out.println("key === "+key);
//		 TreeMap<String,String[]> map2 = map.getValue();
//		 for(Map.Entry<String, String[]> map3 : map2.entrySet()){
//		 String key2 = map3.getKey();
//		 System.out.println("key2 === "+key2);
//		 String [] value = map3.getValue();
//		 System.out.println("value[0] === "+value[0]);
//		 System.out.println("value[1] === "+value[1]);
//		 }
//		 System.out.println("#########################");
//		 }
				
//		 LotteryInfo info2 = new LotteryInfo();
//		 info2.setSuperWinResult(superWin);
//		 String backWinResult = mergeWinResult(1000001, info2);
		 //System.out.println("winResult    =:"+winResult);
		 //System.out.println("backWinResult=:"+backWinResult);
//		 String betCode = "07,09,30,34,35|06,07,11";
//		 List<List<String>> result = lotteryPrize(lotteryId,playType,betType,lotteryResult,winResult,betCode);
//				
//		 int [] resultArr = new int[]{0,0,0,0,0,0,0,0,0};
//				
//		 for(List subList : result){
//		 System.out.println("中奖号码:"+subList.get(0));
//		 System.out.println("中奖级别:"+subList.get(1));
//		 System.out.println("中奖金额:"+subList.get(2));
//		 System.out.println("追加金额:"+subList.get(3));
//		 System.out.println("#################################");
//		 resultArr[Integer.parseInt(String.valueOf(subList.get(1)))]++;
//		 }
//		 System.out.println("*********************************");
//				
//		 for(int k = 1;k<resultArr.length;k++){
//		 System.out.println("["+k+"] 等奖 = "+resultArr[k]);
//		 }
		// 测试生肖乐
		// String betCode = "0101^0809";
		// String betCode = "0101";
		// boolean result = checkLotteryBetFormat(1000005, 0, 1, betCode);
		// System.out.println("result==="+result);
		//		
		// List<String> superList = LotteryTools.lotteryBetToSingleList(1000005,
		// 0, 1, betCode);
		// for(String bet : superList){
		// System.out.println("bet:"+bet);
		// }
		// String lotteryResult = "0102";
		// String winResult = "17276&60";
		//		
		// List<List<String>> prizeresult =
		// lotteryPrize(1000005,0,1,lotteryResult,winResult,betCode);
		// int [] resultArr = new int[]{0,0,0,0,0,0,0,0,0};
		//		
		// for(List subList : prizeresult){
		// System.out.println("中奖号码:"+subList.get(0));
		// System.out.println("中奖级别:"+subList.get(1));
		// System.out.println("中奖金额:"+subList.get(2));
		// System.out.println("追加金额:"+subList.get(3));
		// System.out.println("#################################");
		// resultArr[Integer.parseInt(String.valueOf(subList.get(1)))]++;
		// }
		// System.out.println("*********************************");
		//		
		// for(int k = 1;k<resultArr.length;k++){
		// System.out.println("["+k+"] 等奖 = "+resultArr[k]);
		// }
		//		
		// 测试排列3
		// String betCode = "2";
		// boolean result = checkLotteryBetFormat(1000003, 0, 6, betCode);
		// System.out.println("result==="+result);
		// List<String> arr3List = LotteryTools.lotteryBetToSingleList(1000003,
		// 0, 6, betCode);
		// for(String bet : arr3List){
		// System.out.println("bet:"+bet);
		// }
		// String betCode = "1*2*3*4*5";
		// boolean result = checkLotteryBetFormat(1000004, 0, 1, betCode);
		// System.out.println("result==="+result);
		// List<String> arr5List = LotteryTools.lotteryBetToSingleList(1000004,
		// 0, 1, betCode);
		// for(String bet : arr5List){
		// System.out.println("bet:"+bet);
		// }
		// String betCode = "3*1*1*3*3*1*0*3*3*4*4*4*4*4";
		// boolean result = checkLotteryBetFormat(1300002, 0, 1, betCode);
		// System.out.println("result==="+result);
		// List<String> arr5List = LotteryTools.lotteryBetToSingleList(1300002,
		// 0, 1, betCode);
		// for(String bet : arr5List){
		// System.out.println("bet:"+bet);
		// }
		//		
		// String lotteryResult = "31133103333231";
		// String winResult = "9491&1696";
		// List<List<String>> prizeresult =
		// lotteryPrize(1300002,0,1,lotteryResult,winResult,betCode);
		// int [] resultArr = new int[]{0,0,0,0,0,0,0,0,0};
		//		
		// for(List subList : prizeresult){
		// System.out.println("中奖号码:"+subList.get(0));
		// System.out.println("中奖级别:"+subList.get(1));
		// System.out.println("中奖金额:"+subList.get(2));
		// System.out.println("追加金额:"+subList.get(3));
		// System.out.println("#################################");
		// resultArr[Integer.parseInt(String.valueOf(subList.get(1)))]++;
		// }
		// System.out.println("*********************************");
		//		
		// for(int k = 1;k<resultArr.length;k++){
		// System.out.println("["+k+"] 等奖 = "+resultArr[k]);
		// }
		// int lotteryId = 1000003;
		// int playType = 0;
		// int betType = 1;
		// String lotteryResult = "3793744";
		// String winResult =
		// "1-1&5000000|2-25&50|3-25&40|4-25&30|5-25&20|6-25&10";
		// String betCode = "23*7*8*5*7*54*4";
		//		
		// List<List<String>> result =
		// lotteryPrize(lotteryId,playType,betType,lotteryResult,winResult,betCode);
		// int [] resultArr = new int[]{0,0,0,0,0,0,0};
		// for(List subList : result){
		// System.out.println("中奖号码:"+subList.get(0));
		// System.out.println("中奖级别:"+subList.get(1));
		// System.out.println("中奖金额:"+subList.get(2));
		// System.out.println("追加金额:"+subList.get(3));
		// System.out.println("#################################");
		// resultArr[Integer.parseInt(String.valueOf(subList.get(1)))]++;
		// }
		// System.out.println("*********************************");
		//		
		// for(int k = 1;k<resultArr.length;k++){
		// System.out.println("["+k+"] 等奖 = "+resultArr[k]);
		// }
		// //测试大乐透、排列三、排列五机选单式一注
		// for(int i = 0; i < 20; i++){
		// System.out.println(LotteryTools.lotteryRandomBetCode(1000001));
		// }
		//		
		// for(int i = 0; i < 20; i++){
		// System.out.println(LotteryTools.lotteryRandomBetCode(1000003));
		// }
		// for(int i = 0; i < 20; i++){
		// System.out.println(LotteryTools.lotteryRandomBetCode(1000004));
		// }
//		for(int i = 0; i < 20; i++){
//			System.out.println(LotteryTools.lotteryRandomBetCode(1000002));
//		}
		//		
		// System.out.println(LotteryTools.getLotteryPlayTypeList(1000001));
		// System.out.println(LotteryTools.getLotteryPlayBetTypeList(1000001,
		// 0));
		// System.out.println("---------------------------------------");
		// System.out.println(LotteryTools.getLotteryPlayBetTypeList(1000001,
		// 1));
		// System.out.println(LotteryTools.checkLotteryBetFormat(1000001, 0, 0,
		// ""));
		  
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 0, 2));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 1, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 1, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 1, 2));
//		  System.out.println(LotteryTools.getLotteryName(1000002) + LotteryTools.getPlayBetName(1000002, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000002) + LotteryTools.getPlayBetName(1000002, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 2));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 3));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 4));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 5));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 6));
//		  System.out.println(LotteryTools.getLotteryName(1000004) + LotteryTools.getPlayBetName(1000004, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000004) + LotteryTools.getPlayBetName(1000004, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000005) + LotteryTools.getPlayBetName(1000005, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000005) + LotteryTools.getPlayBetName(1000005, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1300001) + LotteryTools.getPlayBetName(1300001, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1300001) + LotteryTools.getPlayBetName(1300001, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1300002) + LotteryTools.getPlayBetName(1300002, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1300002) + LotteryTools.getPlayBetName(1300002, 0, 1));

		
		  String multiBet = "0304*020506070810";
		  boolean result = checkLotteryBetFormat(1200001, 12, 2, multiBet);
		  System.out.println("result==="+result);
		  
	}
}
