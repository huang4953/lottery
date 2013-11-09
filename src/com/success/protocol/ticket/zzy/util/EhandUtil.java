/**
 * Title: EhandUtil.java
 * @Package com.success.protocol.ticket.zzy.util
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-21 上午10:41:05
 * @version V1.0
 */
package com.success.protocol.ticket.zzy.util;

import java.util.HashMap;
import java.util.Map;

import com.success.lottery.util.LotteryTools;
import com.success.utils.AutoProperties;

/**
 * com.success.protocol.ticket.zzy.util
 * EhandUtil.java
 * EhandUtil
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-21 上午10:41:05
 * 
 */

public class EhandUtil {
	
	//定义异常
	public static final int E_01_CODE = 900001;
	public static final String E_01_DESC = "发送请求通讯错误！";
	public static final int E_02_CODE = 900002;
	public static final String E_02_DESC = "发送请求通讯返回但HTTP响应状态不正确！";
	public static final int E_03_CODE = 900003;
	public static final String E_03_DESC = "发送请求通讯正确返回但消息解析错误！";
	public static final int E_04_CODE = 900004;
	public static final String E_04_DESC = "请求参数不正确！";
	public static final int E_05_CODE = 900005;
	public static final String E_05_DESC = "更新数据库发生异常！";
	public static final int E_06_CODE = 90006;
	public static final String E_06_DESC = "更新数据库时未更新到数据！";
	public static final int E_07_CODE = 90007;
	public static final String E_07_DESC = "发送请求返回错误码[CODE],错误原因[MSG]！";
	public static final int E_08_CODE = 90008;
	public static final String E_08_DESC = "程序处理过程发生其他异常！";
	public static final int E_09_CODE = 90009;
	public static final String E_09_DESC = "查询请求正常返回但未查到数据！";
	
	//以下信息在配置文件中定义
	private static String eHandConfig = "com.success.protocol.ticket.zzy.ehand";
	public static String SYS_DEFINE_USERID;//系统和掌中奕约定的用户ID
	public static String SYS_DEFINE_KEY;//系统和掌中奕约定的代理密钥
	public static int reSendNum = 3;//请求的重发次数
	public static long reSendSleep = 5000L;//请求的重发时间间隔，单位为毫秒
	public static String DLC_CASH = "0";//多乐彩是否得到开奖结果后是否直接兑奖，0表示不直接兑奖；1表示直接兑奖
	//掌中奕通知路径定义
	public static String PATH_01;//xml数据流的方式
	public static String PATH_02;//开奖公告通知
	public static String PATH_03;//新期通知
	public static String PATH_04;//期结通知
	//掌中奕请求的路径
	public static String url_50200;//奖期查询
	public static String url_50201;//多乐彩出票
	public static String url_50203;//开奖公告查询
	public static String url_50205;//出票状态查询
	public static String url_50206;//获取中奖数据
	public static String url_50207;//其他玩法出票
	public static String url_50208;//销售情况查询
	public static String url_50209;//奖期状态查询
	
	public static Map<Integer,String> lotteryToZzy;//投注系统和掌中奕系统的彩种对应
	
	public static String commd_50200;
	public static String commd_50201;
	public static String commd_50203;
	public static String commd_50204;
	public static String commd_50205;
	public static String commd_50206;
	public static String commd_50207;
	public static String commd_50208;
	public static String commd_50209;
	public static String commd_90001;//开奖公告通知
	public static String commd_90002;//新期通知
	public static String commd_90003;//期结通知
	
	//系统可以接收的通知消息码
	public static String [] NOTICE_COMMAND;
	
	//收到消息后的返回错误码
	public static final String ERRORCODE00 = "0";//成功收到消息
	public static final String ERRORCODE01 = "1";//成功收到消息消息头结构错误
	public static final String ERRORCODE02 = "2";//消息命令码系统未定义
	public static final String ERRORCODE03 = "3";//userid与约定的不一致
	public static final String ERRORCODE04 = "4";//成功收到消息md5校验错误
	public static final String ERRORCODE05 = "5";//消息处理发生异常
	
	public static String check_md5;
	
	
	static {
		lotteryToZzy = LotteryTools.getLotteryEhand();
		
		commd_50200 = AutoProperties.getString("commd_50200", "50200", eHandConfig);
		commd_50201 = AutoProperties.getString("commd_50201", "50201", eHandConfig);
		commd_50203 = AutoProperties.getString("commd_50203", "50203", eHandConfig);
		commd_50204 = AutoProperties.getString("commd_50204", "50204", eHandConfig);
		commd_50205 = AutoProperties.getString("commd_50205", "50205", eHandConfig);
		commd_50206 = AutoProperties.getString("commd_50206", "50206", eHandConfig);
		commd_50207 = AutoProperties.getString("commd_50207", "50207", eHandConfig);
		commd_50208 = AutoProperties.getString("commd_50208", "50208", eHandConfig);
		commd_50209 = AutoProperties.getString("commd_50209", "50209", eHandConfig);
		commd_90001 = AutoProperties.getString("commd_90001", "90001", eHandConfig);
		commd_90002 = AutoProperties.getString("commd_90002", "90002", eHandConfig);
		commd_90003 = AutoProperties.getString("commd_90003", "90003", eHandConfig);
		
		NOTICE_COMMAND = new String[] {commd_50204,commd_90001,commd_90002,commd_90003};
		
		SYS_DEFINE_USERID = AutoProperties.getString("sys_define_userid", "", eHandConfig);
		SYS_DEFINE_KEY = AutoProperties.getString("sys_define_key", "", eHandConfig);
		
		reSendNum = AutoProperties.getInt("reSendNum", 3, eHandConfig);
		reSendSleep = AutoProperties.getInt("reSendSleep", 5000, eHandConfig);
		
		DLC_CASH = AutoProperties.getString("dlc_cash", "0", eHandConfig);
		
		PATH_01 = AutoProperties.getString("xml_url", "/", eHandConfig);
		PATH_02 = AutoProperties.getString("kj_url", "/accept_code.jsp", eHandConfig);
		PATH_03 = AutoProperties.getString("new_term_url", "/IsusesReceive", eHandConfig);
		PATH_04 = AutoProperties.getString("end_term_url", "/rev3", eHandConfig);
		
		url_50200 = AutoProperties.getString("url_50200", "", eHandConfig);
		url_50201 = AutoProperties.getString("url_50201", "", eHandConfig);
		url_50203 = AutoProperties.getString("url_50203", "", eHandConfig);
		url_50205 = AutoProperties.getString("url_50205", "", eHandConfig);
		url_50206 = AutoProperties.getString("url_50206", "", eHandConfig);
		url_50207 = AutoProperties.getString("url_50207", "", eHandConfig);
		url_50208 = AutoProperties.getString("url_50208", "", eHandConfig);
		url_50209 = AutoProperties.getString("url_50209", "", eHandConfig);
		
		check_md5 = AutoProperties.getString("check_md5", "1", eHandConfig);
	}
	
	/**
	 * 
	 * Title: lotteryToZzy<br>
	 * Description: <br>
	 *              <br>
	 * @param lotteryId
	 * @return
	 */
	public static String lotteryToZzy(int lotteryId){
		return lotteryToZzy.get(lotteryId);
	}
	/**
	 * 
	 * Title: zzyToLottery<br>
	 * Description: <br>
	 *              <br>
	 * @param lotteryId
	 * @return
	 */
	public static int zzyToLottery(String lotteryId){
		int lotteryInt = 0;
		for(Map.Entry<Integer, String> one : lotteryToZzy.entrySet()){
			Integer key = one.getKey();
			String value = one.getValue();
			if(value.equals(lotteryId)){
				lotteryInt = key;
				break;
			}
		}
		return lotteryInt;
	}
	

}
