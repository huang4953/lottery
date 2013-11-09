/**
 * Title: LotteryStaticDefine.java
 * @Package com.success.lottery.business.service
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-21 下午06:37:47
 * @version V1.0
 */
package com.success.lottery.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.success.lottery.util.LotteryTools;

/**
 * com.success.lottery.business.service
 * LotteryStaticDefine.java
 * LotteryStaticDefine
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-21 下午06:37:47
 * 
 */

public class LotteryStaticDefine {
	
	/*
	 * 大乐透
	 */
	public static final int LOTTERY_1000001 = 1000001;//大乐透
	public static final int LOTTERY_1000002 = 1000002;//七星彩
	public static final int LOTTERY_1000003 = 1000003;//排列三
	public static final int LOTTERY_1000004 = 1000004;//排列五
	public static final int LOTTERY_1000005 = 1000005;//生肖乐
	public static final int LOTTERY_1300001 = 1300001;//胜负彩
	public static final int LOTTERY_1300002 = 1300002;//任选九场
	public static final int LOTTERY_1300003 = 1300003;//6场半全场
	public static final int LOTTERY_1300004 = 1300004;//4场进球彩
	public static final int LOTTERY_1200001 = 1200001;//十一运夺金
	public static final int LOTTERY_1200002 = 1200002;//快乐扑克
	
	public static Map<String,String> planSource;
	
	public static Map<String,String> orderStatus;
	
	public static Map<String,String> coopOrderStatus;
	public static Map<Integer,String> coopWinStatus;
	
	public static Map<String,String> cpOrderType;
	
	public static Map<String,String> orderStatusForWeb;
	
	public static Map<String,String> termWinStatus;
	
	public static Map<String,String> orderWinStatus;
	public static Map<String,String> orderWinStatusForWeb;
	
	public static Map<String,String> saleStatus;
	
	public static Map<String,String> termStatus;
	
	public static Map<String,String> ticketStatus;
	
	public static Map<String,String> userStatus;
	
	public static Map<Integer,String> drawStatus;
	
	public static Map<String,String> userSex;
	
	public static Map<String,String> adminStatus;
	
	public static Map<Boolean,String> mobileStatus;
	
	public static Map<Boolean,String> emailStatus;
	
	public static Map<String,String> ticketStatusForQuery;
	
	//彩票出票状态
	public static Map<String,String> ticketStatusForWeb;
	//彩票中奖状态
	public static Map<String,String> ticketWinStatus;
	//短信群发任务状态
	public static Map<Long,String> taskStautsForSms;
	
	//短信群发发送状态
	public static Map<Long,String> sendStatusForSms;
	
	//日志的几个状态
	public static Map<Integer,String> operatorlogLevelStatus;
	public static Map<Integer,String> operatorlogTypeStatus;
	public static Map<Integer,String> operatorlogRankStatus;
	/*
	 * 交易类型转账户操作类型
	 * A:收入，B:支出，C:冻结，D:解冻
	 * 
	 */

	public static Map<Integer,String> transactionToOpType;
	
	public static Map<Integer,String> transactionType;
	
	public static Map<Integer,String> ipsOrderStatus;
	
	public static Map<Integer,String> billOrderStatus;
	
	public static Map<Integer,String> ipsCheckStatus;
	
	public static Map<Integer,String> billCheckStatus;
	
	public static Map<Integer,String> planStatus;
	
	public static Map<Integer,String> planStatusForQuery;//方案状态作为查询条件
	
	public static List<Integer> needLimitLottery;//需要输入限号信息的彩种
	
	public static Map<Integer,String> planType;
	public static Map<Integer,String> planStop;
	public static Map<Integer,String> planOpenType;
	public static Map<Integer,String> coopOrderType;
	
	public static Map<Integer,String> planStatusForWebH;
	
	public static final String open_format = "height=500, width=900, " +
			"alwaysRaised=yes,dependent=yes,location=no, menubar=no, " +
			"toolbar =no, titlebar=no,scrollbars=yes, resizable=yes,status=no";
	
	static {
		planSource = new TreeMap<String,String>();
		planSource.put("0", "WEB");
		planSource.put("1", "SMS");
		planSource.put("2", "WAP");
		planSource.put("3", "KJAVA");
		//orderStatus = new TreeMap<String,String>();
		orderStatus = new LinkedHashMap<String,String>();
		orderStatus.put("0", "未开始");
		orderStatus.put("1", "进行中");
		orderStatus.put("2", "出票中");
		orderStatus.put("3", "订单错误");
		orderStatus.put("4", "出票结束");
		orderStatus.put("5", "出票成功");
		orderStatus.put("6", "出票失败");
		orderStatus.put("8", "兑奖完成");
		orderStatus.put("10", "派奖完成");
		orderStatus.put("11", "订单自动撤销");
		orderStatus.put("12", "订单手动撤销");
		orderStatus.put("13", "追号中");
		orderStatus.put("14", "订单限号撤销");
		coopOrderStatus = new LinkedHashMap<String,String>();
		//合买订单状态,0-进行中，1-系统撤单，2-发起人撤单，3-参与人撤单，4-合买满员生成投注，5-保底冻结，6-保底撤销,7-已经兑奖,8-已派奖
		coopOrderStatus.put("0", "进行中");
		coopOrderStatus.put("1", "系统撤单");
		coopOrderStatus.put("2", "发起人撤单");
		coopOrderStatus.put("3", "参与人撤单");
		coopOrderStatus.put("4", "合买满员生成投注");
		coopOrderStatus.put("5", "保底冻结");
		coopOrderStatus.put("6", "保底撤销");
		coopOrderStatus.put("7", "已经兑奖");
		coopOrderStatus.put("8", "已派奖");
		//合买订单方式,0-发起认购，1-参与认购,2-发起保底,3-发起保底转投注
		cpOrderType = new LinkedHashMap<String,String>();
		cpOrderType.put("0", "发起认购");
		cpOrderType.put("1", "参与认购");
		cpOrderType.put("2", "发起保底");
		cpOrderType.put("3", "发起保底转投注");
		termWinStatus = new TreeMap<String,String>();
		termWinStatus.put("1", "未开奖");
		termWinStatus.put("2", "已开奖");
		termWinStatus.put("3", "正在兑奖");
		termWinStatus.put("4", "兑奖成功");
		termWinStatus.put("7", "正在派奖");
		termWinStatus.put("8", "派奖成功");
		orderWinStatus = new LinkedHashMap<String,String>();
		orderWinStatus.put("0", "未兑奖");
		orderWinStatus.put("1", "未中奖");
		orderWinStatus.put("2", "中小奖");
		orderWinStatus.put("3", "中大奖");
		orderWinStatus.put("99", "失败已退");
		orderWinStatus.put("199", "未中已退");
		orderWinStatus.put("299", "小奖已退");
		orderWinStatus.put("399", "大奖已退");
		
		orderWinStatusForWeb = new TreeMap<String,String>();
		orderWinStatusForWeb.put("0", "----");
		orderWinStatusForWeb.put("1", "未中奖");
		orderWinStatusForWeb.put("2", "中小奖");
		orderWinStatusForWeb.put("3", "中大奖");
		orderWinStatusForWeb.put("99", "----");
		orderWinStatusForWeb.put("199", "未中奖");
		orderWinStatusForWeb.put("299", "中小奖");
		orderWinStatusForWeb.put("399", "中大奖");
		
		saleStatus = new TreeMap<String,String>();
		saleStatus.put("1", "正在销售");
		saleStatus.put("2", "停止销售");
		saleStatus.put("3", "暂停销售");
		saleStatus.put("4", "未到销售时间");
		saleStatus.put("5", "已经开奖");
		termStatus = new TreeMap<String,String>();
		termStatus.put("0", "未售期");
		termStatus.put("1", "在售期");
		termStatus.put("2", "已售期");
		ticketStatus = new TreeMap<String,String>();
		ticketStatus.put("0", "未出票");
		ticketStatus.put("1", "出票中");
		ticketStatus.put("2", "请求打票");
		ticketStatus.put("3", "请求打票失败");
		ticketStatus.put("4", "等待打票结果");
		ticketStatus.put("5", "打票结果未知");
		ticketStatus.put("6", "出票成功");
		ticketStatus.put("7", "出票失败");
		ticketStatus.put("8", "出票超时");
		ticketStatus.put("9", "不能出票");
		
		
		userStatus =  new TreeMap<String, String>();
		userStatus.put("0", "注销");
		userStatus.put("1", "正常");
		userStatus.put("2", "冻结");
		
		drawStatus = new LinkedHashMap<Integer,String>();
		drawStatus.put(0, "待处理");
		drawStatus.put(1, "已通过");
		drawStatus.put(2, "已拒绝");
		drawStatus.put(3, "本金增加");
		drawStatus.put(4, "本金减少");
		
		transactionType = new LinkedHashMap<Integer, String>();
		transactionType.put(11005, "账户增加调整");
		transactionType.put(10001, "账户减少调整");
		transactionType.put(11001, "充值");
		transactionType.put(11002, "派奖");
		transactionType.put(20007, "提现申请");
		transactionType.put(31007, "提现拒绝");
		transactionType.put(30004, "提现完成");
		transactionType.put(10002, "购彩");
		transactionType.put(20002, "追号");
		transactionType.put(31006, "追号撤销");
		transactionType.put(20003, "发起合买冻结");
		transactionType.put(20005, "参与合买冻结");
		transactionType.put(20009, "合买保底冻结");
		transactionType.put(30002, "发起合买投注");
		transactionType.put(30003, "参与合买投注");
		transactionType.put(30006, "合买保底投注");
		transactionType.put(31003, "合买方案撤销");
		transactionType.put(31005, "合买参与撤销");
		transactionType.put(31009, "合买保底解冻");
		transactionType.put(31010, "失败订单退款");
		
		
		transactionToOpType = new HashMap<Integer,String>();
		transactionToOpType.put(11005, "A");//收入
		transactionToOpType.put(11001, "A");
		transactionToOpType.put(11002, "A");
		transactionToOpType.put(31010, "A");
		transactionToOpType.put(10001, "B");//支出
		transactionToOpType.put(30004, "B");
		transactionToOpType.put(10002, "B");
		transactionToOpType.put(30001, "B");
		transactionToOpType.put(30006, "B");
		transactionToOpType.put(30002, "B");
		transactionToOpType.put(30003, "B");
		transactionToOpType.put(20002, "C");//冻结
		transactionToOpType.put(20007, "C");
		transactionToOpType.put(20009, "C");
		transactionToOpType.put(20003, "C");
		transactionToOpType.put(20005, "C");
		transactionToOpType.put(31007, "D");//解冻
		transactionToOpType.put(31006, "D");
		transactionToOpType.put(31009, "D");
		transactionToOpType.put(31005, "D");
		transactionToOpType.put(31003, "D");
		
		ipsOrderStatus = new HashMap<Integer,String>();
		ipsOrderStatus.put(0, "已发送请求到环迅等待结果");
		ipsOrderStatus.put(1, "环迅成功充值成功");
		ipsOrderStatus.put(2, "环迅成功充值失败");
		ipsOrderStatus.put(3, "环迅失败充值失败");
		ipsOrderStatus.put(4, "环迅失败唐路成功");
		ipsOrderStatus.put(5, "不平账处理成功");
		ipsOrderStatus.put(6, "不平账处理失败");
		
		
		billOrderStatus = new HashMap<Integer,String>();
		billOrderStatus.put(0, "已发送请求到环迅等待结果");
		billOrderStatus.put(1, "快钱成功充值成功");
		billOrderStatus.put(2, "快钱成功充值失败");
		billOrderStatus.put(3, "快钱失败充值失败");
		billOrderStatus.put(4, "快钱失败唐路成功");
		billOrderStatus.put(5, "不平账处理成功");
		billOrderStatus.put(6, "不平账处理失败");
		
		ipsCheckStatus = new HashMap<Integer,String>();
		ipsCheckStatus.put(0, "未对账");
		ipsCheckStatus.put(1, "对账平衡");
		ipsCheckStatus.put(2, "可处理不平账");
		ipsCheckStatus.put(3, "其它不平账");
		
		billCheckStatus = new HashMap<Integer,String>();
		billCheckStatus.put(0, "未对账");
		billCheckStatus.put(1, "对账平衡");
		billCheckStatus.put(2, "可处理不平账");
		billCheckStatus.put(3, "其它不平账");
		
		userSex = new HashMap<String,String>();
		userSex.put("0", "男");
		userSex.put("1", "女");
		
		adminStatus = new HashMap<String,String>();
		adminStatus.put("1", "正常");
		
		mobileStatus = new HashMap<Boolean,String>();
		mobileStatus.put(false, "否");
		mobileStatus.put(true, "是");
		
		emailStatus = new HashMap<Boolean,String>();
		emailStatus.put(false, "否");
		emailStatus.put(true, "是");
		
		planStatus = new TreeMap<Integer,String>();
		planStatus.put(0, "进行中");
		planStatus.put(1, "已生成");
		planStatus.put(2, "部分完成");
		planStatus.put(3, "已完成");
		planStatus.put(4, "已撤销");
		
		orderStatusForWeb = new HashMap<String,String>();
		orderStatusForWeb.put("0", "出票中");
		orderStatusForWeb.put("1", "出票中");
		orderStatusForWeb.put("2", "出票中");
		orderStatusForWeb.put("3", "出票失败");
		orderStatusForWeb.put("4", "部分成功");
		orderStatusForWeb.put("6", "出票失败");
		orderStatusForWeb.put("5", "出票成功");
		orderStatusForWeb.put("8", "已兑奖");
		orderStatusForWeb.put("10", "已派奖");
		orderStatusForWeb.put("11", "自动取消");
		orderStatusForWeb.put("12", "手工取消");
		orderStatusForWeb.put("13", "追号中");
		orderStatusForWeb.put("14", "限号取消");
		
		taskStautsForSms = new HashMap<Long,String>();
		taskStautsForSms.put(0L, "未开始");
		taskStautsForSms.put(1L, "发送中");
		taskStautsForSms.put(2L, "发送完成");
		taskStautsForSms.put(3L, "发送过程错误");
		taskStautsForSms.put(4L, "发送超时");
		
		sendStatusForSms = new HashMap<Long, String>();
		sendStatusForSms.put(0L, "未发送");
		sendStatusForSms.put(1L, "已发送");
		sendStatusForSms.put(2L, "需要重发");
		sendStatusForSms.put(3L, "发送超时");
		sendStatusForSms.put(4L, "发送完成");
		sendStatusForSms.put(5L, "发送成功");
		
		ticketStatusForQuery = new HashMap<String,String>();
		ticketStatusForQuery.put("10000", "未出票");
		ticketStatusForQuery.put("10001", "出票中");
		ticketStatusForQuery.put("10002", "出票成功");
		ticketStatusForQuery.put("10003", "出票失败");
		
		ticketStatusForWeb = new HashMap<String,String>();
		ticketStatusForWeb.put("0", "未出票");
		ticketStatusForWeb.put("1", "出票中");
		ticketStatusForWeb.put("2", "出票中");
		ticketStatusForWeb.put("3", "出票中");
		ticketStatusForWeb.put("4", "出票中");
		ticketStatusForWeb.put("5", "出票中");
		ticketStatusForWeb.put("6", "出票成功");
		ticketStatusForWeb.put("7", "出票失败");
		ticketStatusForWeb.put("8", "出票失败");
		ticketStatusForWeb.put("9", "出票失败");
		
		ticketWinStatus = new HashMap<String,String>();
		ticketWinStatus.put("0", "未兑奖");
		ticketWinStatus.put("1", "未中奖");
		ticketWinStatus.put("2", "中小奖");
		ticketWinStatus.put("3", "中大奖");
		
		operatorlogLevelStatus = new HashMap<Integer,String>();
		operatorlogLevelStatus.put(10000, "DEBUG");
		operatorlogLevelStatus.put(20000, "INFO");
		operatorlogLevelStatus.put(30000, "WARN");
		operatorlogLevelStatus.put(40000, "ERROR");
		operatorlogLevelStatus.put(50000, "FATAL");
		operatorlogTypeStatus = new HashMap<Integer,String>();
		operatorlogTypeStatus.put(0, "其他类别");
		operatorlogTypeStatus.put(10000, "后台安全");
		operatorlogTypeStatus.put(20000, "前台安全");
		operatorlogTypeStatus.put(30000, "彩民资金相关");
		operatorlogTypeStatus.put(40000, "业务处理");
		operatorlogRankStatus = new HashMap<Integer,String>();
		operatorlogRankStatus.put(10000, "普通");
		operatorlogRankStatus.put(20000, "必要");
		operatorlogRankStatus.put(30000, "重要");
		operatorlogRankStatus.put(40000, "非常重要");
		
		needLimitLottery = new ArrayList<Integer>();
		needLimitLottery.add(LOTTERY_1000003);
		needLimitLottery.add(LOTTERY_1200001);
		
		planType = new HashMap<Integer,String>();
		planType.put(0, "代购");
		planType.put(1, "合买");
		planStop = new HashMap<Integer,String>();
		planStop.put(0, "不停止");
		planStop.put(1, "停止");
		planOpenType = new HashMap<Integer,String>();
		planOpenType.put(0, "公开");
		planOpenType.put(1, "购买后公开");
		planOpenType.put(2, "截止后公开");
		//0-未兑奖，1-未中奖，2-中小奖，3-中大奖
		coopWinStatus = new HashMap<Integer,String>();
		coopWinStatus.put(0, "未兑奖");
		coopWinStatus.put(1, "未中奖");
		coopWinStatus.put(2, "中小奖");
		coopWinStatus.put(3, "中大奖");
		//合买订单方式,0-发起认购，1-参与认购,2-发起保底,3-发起保底转投注
		coopOrderType = new HashMap<Integer,String>();
		coopOrderType.put(0, "发起认购");
		coopOrderType.put(1, "参与认购");
		coopOrderType.put(2, "发起保底");
		coopOrderType.put(3, "发起保底转投注");
		
		planStatusForWebH = new HashMap<Integer,String>();
		planStatusForWebH.put(900, "进行中");
		planStatusForWebH.put(901, "订单已生成");
		planStatusForWebH.put(902, "已出票");
		planStatusForWebH.put(903, "已出票");
		planStatusForWebH.put(904, "已撤销");
		planStatusForWebH.put(0, "出票中");
		planStatusForWebH.put(1, "出票中");
		planStatusForWebH.put(2, "出票中");
		planStatusForWebH.put(3, "出票失败");
		planStatusForWebH.put(4, "已出票");
		planStatusForWebH.put(5, "已出票");
		planStatusForWebH.put(6, "出票失败");
		planStatusForWebH.put(8, "已兑奖");
		planStatusForWebH.put(10, "已派奖");
		
		planStatusForQuery = new LinkedHashMap<Integer, String>();
		planStatusForQuery.put(100, "进行中");
		planStatusForQuery.put(101, "出票中");
		planStatusForQuery.put(102, "已出票");
		planStatusForQuery.put(103, "已兑奖");
		planStatusForQuery.put(104, "已派奖");
		planStatusForQuery.put(105, "出票失败");
		planStatusForQuery.put(106, "已撤销");
		
	}
	/**
	 * 
	 * Title: getLotteryList<br>
	 * Description: <br>
	 *              <br>获取系统所有启用的彩种
	 * @return Map<Integer,String>
	 */
	public static Map<Integer,String> getLotteryList(){
		Map<Integer,String> result = new TreeMap<Integer,String>();
		Map<Integer,String> tmp = LotteryTools.getLotteryList();
		for(Map.Entry<Integer, String> oneLottery : tmp.entrySet()){
			int lotteryId = oneLottery.getKey();
			String lotteryName = oneLottery.getValue();
			if(LotteryTools.isLotteryStart(lotteryId)){
				result.put(lotteryId, lotteryName);
			}
		}
		return result;
	}
	
	public static Map<Integer, String> getTicketStatusList(){
		Map<Integer,String> result = new TreeMap<Integer,String>();
		result.put(Integer.valueOf(1), "出票中");
		result.put(Integer.valueOf(6), "出票成功");
		result.put(Integer.valueOf(7), "出票失败");
		return result;
	}
	
	public static String getTermDetailLink(String lotteryId,String termNo){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showTermInfoDetail.jhtml?l_lotteryId=").append(lotteryId).append("&l_termNo=")
		.append(termNo).append("','newwindow','").append(open_format).append("')\">详细</a>");
		return sb.toString();
	}
	
	public static String getOrderDetailLink(String orderId,String show_str){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('showOrderInfoDetail.jhtml?orderId=").append(orderId)
		.append("','newwindow','").append(open_format).append("')\">").append(show_str).append("</a>");
		return sb.toString();
	}
	/**
	 * 
	 * Title: getCoopOrderDetailLink<br>
	 * Description: <br>
	 *              <br>
	 * @param planId
	 * @param coopOrderId
	 * @param show_str
	 * @return
	 */
	public static String getCoopOrderDetailLink(String planId,String coopOrderId,String show_str){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('showCoopInfoDetail.jhtml?planId=").append(planId)
		.append("&coopOrderId=").append(coopOrderId)
		.append("','coopInfoDetail','").append(open_format).append("')\">").append(show_str).append("</a>");
		return sb.toString();
	}
	
	public static String getUserStatusDefineMap(String status){
		return userStatus.get(status);
	}
	
	public static String getUserDetailLink(String userIdentify){
		StringBuffer sb = new StringBuffer();
		if(null==userIdentify){
			sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showUserInfoDetail.jhtml?userIdentify=")
			.append("").append("','newwindow','").append(open_format).append("')\"" ).append(">")
			.append("").append("</a>");
		}else{
			sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showUserInfoDetail.jhtml?userIdentify=")
			.append(userIdentify).append("','newwindow','").append(open_format).append("')\"" ).append(">")
			.append(userIdentify).append("</a>");
		}
		return sb.toString();
	}
	
	public static String getUserEditLink(long userId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showUserInfoDetail.jhtml?userIdentify=").append(userId)
		.append("','newwindow','").append(open_format).append("')\">冻结</a>").append("/")
		.append("<a  href=\"#\" onclick=\"javascript:window.open('showUserInfoDetail.jhtml?userIdentify=").append(userId)
		.append("','newwindow','").append(open_format).append("')\">重置密码</a>");
		return sb.toString();
	}

	public static String getAdminEditLink(long userId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('updateAdminUserShow.jhtml?userIdentify=").append(userId)
		.append("','newwindow','").append(open_format).append("')\">修改</a>").append("/")
		.append("<a  href=\"#\" onclick=\"javascript:window.open('changePerPasswordShow.jhtml?userIdentify=").append(userId)
		.append("','newwindow','").append(open_format).append("')\">重置密码</a>");
		return sb.toString();
	}
	
	public static String getOrderDiapatchLink(String planId,String orderId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('dispatchOrderConfig.jhtml?orderId=")
				.append(orderId).append("&planId=").append(planId)
		.append("','newwindow','").append(open_format).append("')\">").append("派奖").append("</a>");
		return sb.toString();
	}
	
	public static String getDaiGouErrLink(String orderId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('daiGouErrOrderConfirm.jhtml?orderId=")
				.append(orderId)
		.append("','daigouerrconfirm','").append(open_format).append("')\">").append("退款").append("</a>");
		return sb.toString();
	}
	
	public static String heMaiGouErrLink(String orderId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"#\" onclick=\"javascript:window.open('heMaiErrOrderConfirm.jhtml?orderId=")
				.append(orderId)
		.append("','hemaierrconfirm','").append(open_format).append("')\">").append("退款").append("</a>");
		return sb.toString();
	}
	//获得群发数据明细的链接
	public static String getSmsPushDataLink(long id){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showSmsPushDataDetail.jhtml?id=")
		.append(id).append("','newwindow','").append(open_format).append("')\"" ).append(">")
		.append("明细").append("</a>");
		return sb.toString();
	}
	//获得群发任务明细的链接
	public static String getSmsPushTaskLink(String taskId){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showSmsPushTaskDetail.jhtml?taskId=")
		.append(taskId).append("','newwindow','").append(open_format).append("')\"" ).append(">")
		.append("明细").append("</a>");
		return sb.toString();
	}
	//获得确定的群发任务明细以及群发数据条目的链接
	public static Object getSmsPushTaskIdLink(String taskId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showSmsList.jhtml?taskId=")
		.append(taskId).append("','newwindow','").append(open_format).append("')\"" ).append(">")
		.append(taskId).append("</a>");
		return sb.toString();
	}
	//在确定的群发任务明细以及群发数据条目的页面中弹出的链接
	public static String getSmsPushTaskDataLink(long id){
		StringBuffer sb = new StringBuffer();
		sb.append("<a  href=\"#\" onclick=\"javascript:window.open('showSmsPushDataDetail.jhtml?id=")
		.append(id).append("','subWind','").append(open_format).append("')\"" ).append(">")
		.append("明细").append("</a>");
		return sb.toString();
	}
	
	//公用明细方法链接、只需要传入链接的URL、链接的的标识、链接的id、链接的名称，父页面弹子页面的方法
	public static String getAllTheLink(String linkPage,String linkTitle,String linkId,String linkName){
		StringBuffer allTheLink = new StringBuffer();
		allTheLink.append("<a href=\"#\" onclick=\"javascript:window.open('").append(linkPage).
			append("?").append(linkTitle).append("=").append(linkId).append("','newwindow','").
			append(open_format).append("')\">").append(linkName).append("</a>");
		return allTheLink.toString();
	}
	
	//公用明细方法链接、只需要传入链接的URL、链接的的标识、链接的id、链接的名称，子页面弹子页面的方法
	public static String getSubAllTheLink(String linkPage,String linkTitle,String linkId,String linkName) {
		StringBuffer allTheSubLink = new StringBuffer();
		allTheSubLink.append("<a href=\"#\" onclick=\"javascript:window.open('").append(linkPage).
			append("?").append(linkTitle).append("=").append(linkId).append("','subWind','").
			append(open_format).append("')\">").append(linkName).append("</a>");
		return allTheSubLink.toString();
	}
	
	public static Map<String,Map<String,String>> convertWinResultForPage(){
		return null;
	}

	

	

	

	

}
