/**
 * Title: TermWinInputAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-21 下午04:48:32
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.success.admin.user.domain.AdminUser;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.util.LotteryTools;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.LibingUtils;

/**
 * com.success.lottery.web.admin.action
 * TermWinInputAction.java
 * TermWinInputAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-21 下午04:48:32
 * 
 */

public class TermWinInputAction extends ActionSupport  implements SessionAware{
	private static Log logger = LogFactory.getLog(TermWinInputAction.class);
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4521694726071730969L;
	
	private Map			session;
	private String p_lotteryId;
	private String p_termNo_begin = "0";
	private String p_termNo_end = "0";
	
	private String lotteryId;
	
	private String termNo;
	private ArrayList<String> b_code;
	private ArrayList<String> e_code;
	private String salesVolume;
	private String salesVolume_second;
	private String jackpot;
	private String jackpot_second;
	
	//定义开奖结果
	private String b_code0;
	private String b_code1;
	private String b_code2;
	private String b_code3;
	private String b_code4;
	private String b_code5;
	private String b_code6;
	private String b_code7;
	private String b_code8;
	private String b_code9;
	private String b_code10;
	private String b_code11;
	private String b_code12;
	private String b_code13;
	
	private String e_code0;
	private String e_code1;
	private String e_code2;
	private String e_code3;
	private String e_code4;
	private String e_code5;
	//定义奖级
	private String win_1_s;private String win_1_j;private String win_2_s;private String win_2_j;
	private String win_3_s;private String win_3_j;private String win_4_s;private String win_4_j;
	private String win_5_s;private String win_5_j;private String win_6_s;private String win_6_j;
	private String win_7_s;private String win_7_j;private String win_8_s;private String win_8_j;
	private String win_9_s;private String win_9_j;private String win_10_s;private String win_10_j;
	private String win_11_s;private String win_11_j;private String win_12_s;private String win_12_j;
	private String win_1_a_s;private String win_1_a_j;private String win_2_a_s;private String win_2_a_j;
	private String win_3_a_s;private String win_3_a_j;private String win_4_a_s;private String win_4_a_j;
	private String win_5_a_s;private String win_5_a_j;private String win_6_a_s;private String win_6_a_j;
	private String win_7_a_s;private String win_7_a_j;private String win_s_1_s;private String win_s_1_j;
	
	/*
	 * 定义成功页面信息
	 */
	private String lotteryName;
	private String lotteryName_second;
	private String hava_add;
	private String lotteryId_second;
	private String lotteryResult;
	private String lotteryReslut_second;
	private Map<String,Map<String,String>> winResult;
	private Map<String,Map<String,String>> winResult_second;
	
	private int ex_code;
	private String ex_reason;
	
	
	
	/**
	 * 
	 * Title: lotterySuperWinInput<br>
	 * Description: <br>
	 *              <br>大乐透开奖信息输入
	 * @return String
	 */
	public String lotterySuperWinInput(){
		String dealResult = SUCCESS;
		try{
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			/*
			 * 组织界面的数据
			 */
			TreeMap<String,ArrayList<String>> lotteryResultS = new TreeMap<String,ArrayList<String>>();
			this.b_code = new ArrayList<String>();
			this.b_code.add(this.getB_code0());
			this.b_code.add(this.getB_code1());
			this.b_code.add(this.getB_code2());
			this.b_code.add(this.getB_code3());
			this.b_code.add(this.getB_code4());
			this.e_code = new ArrayList<String>();
			this.e_code.add(this.getE_code0());
			this.e_code.add(this.getE_code1());
			
			lotteryResultS.put("1", this.getB_code());
			lotteryResultS.put("2", this.getE_code());
			
			//奖金结果 奖金单位为元 参数意思为：<一等奖到八等奖(用数字1-8表示),<基本奖(A)或追加(B)奖,[注数,金额]>>,8等奖无追加因此8等奖可不填写追加的集合元素
			TreeMap<String,TreeMap<String,String[]>> winResultS = this.dealSuperWinResult();
			
			ArrayList<String> lotteryResultH = this.getE_code();
			
			Map<String,String> winResultH = new TreeMap<String,String>();
			winResultH.put("A", this.getWin_s_1_s());
			winResultH.put("B",this.getWin_s_1_j());
			
			/*
			 * 处理成功后页面参数
			 */
			this.setLotteryName(LotteryTools.getLotteryName(LotteryStaticDefine.LOTTERY_1000001));
			this.setLotteryResult("前区:"+this.convertArrToString(this.getB_code())+" 后区:"+this.convertArrToString(this.getE_code()));
			
			this.setWinResult(this.convertSuperWinResult(winResultS));
			this.setHava_add("Y");
			this.setLotteryName_second(LotteryTools.getLotteryName(LotteryStaticDefine.LOTTERY_1000005));
			this.setLotteryReslut_second(this.convertArrToString(this.getE_code()));
			Map<String,Map<String,String>> winResult_second = new HashMap<String,Map<String,String>>();
			winResult_second.put("一等奖", winResultH);
			this.setWinResult_second(winResult_second);
			this.setLotteryId_second(String.valueOf(LotteryStaticDefine.LOTTERY_1000005));
			/*
			 * 写入数据库
			 */
			termManager.inputSuperAndHappyWinInfo(this.getTermNo(), lotteryResultS, this.getSalesVolume(), this.getJackpot(), winResultS,
					lotteryResultH, this.getSalesVolume_second(), winResultH);
			
			dealResult = SUCCESS;
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			dealResult = ERROR;
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			dealResult = ERROR;
		}
		
		this.writeLog(dealResult, this.getTermNo(), String
				.valueOf(LotteryStaticDefine.LOTTERY_1000001), String
				.valueOf(LotteryStaticDefine.LOTTERY_1000005), this
				.getEx_reason());
		return dealResult;
	}
	/**
	 * 
	 * Title: writeLog<br>
	 * Description: <br>
	 *              <br>写数据库日志，写日志错误不再抛出异常
	 * @param defaultResult
	 * @param termNo
	 * @param lotteryId
	 * @param lotteryId2
	 * @param errMsg
	 */
	private void writeLog(String defaultResult,String termNo,String lotteryId,String lotteryId2,String errMsg){
		try{
			AdminUser adminUser = (AdminUser) this.getSession().get("tlt.loginuser");
			Map<String,String> param = new HashMap<String, String>();
			param.put("userId", adminUser.getUserId()+"");
			param.put("userName",adminUser.getName());
			param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
			param.put("keyword2", termNo);
			if(ERROR.equals(defaultResult)){
				param.put("keyword1", lotteryId);
				param.put("errorMessage", errMsg);
				OperatorLogger.log(41001, param);
				if(StringUtils.isNotEmpty(lotteryId2)){
					param.put("keyword1", lotteryId2);
					OperatorLogger.log(41001, param);
				}
				
			}else{
				param.put("keyword1", lotteryId);
				OperatorLogger.log(40001, param);
				if(StringUtils.isNotEmpty(lotteryId2)){
					param.put("keyword1", lotteryId2);
					OperatorLogger.log(40001, param);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Title: lotteryColorWinInput<br>
	 * Description: <br>
	 *              <br>七星才开奖信息输入
	 * @return String
	 */
	public String lotteryColorWinInput(){
		String dealResult = SUCCESS;
		try{
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			/*
			 * 组织界面的数据
			 */
			//开奖结果
			this.b_code = new ArrayList<String>();
			this.b_code.add(this.getB_code0());
			this.b_code.add(this.getB_code1());
			this.b_code.add(this.getB_code2());
			this.b_code.add(this.getB_code3());
			this.b_code.add(this.getB_code4());
			this.b_code.add(this.getB_code5());
			this.b_code.add(this.getB_code6());
			
			Map<String,String> lotteryResult = new TreeMap<String,String>();
			for(int i = 0 ; i < 7; i++){
				lotteryResult.put(String.valueOf(i+1), this.b_code.get(i));
			}
			
			//奖金结果 参数分别为:<一等奖到六等奖(用数字1-6标识),[注数,金额]>
			Map<String,String[]> winResult = this.dealColorWinResult();
			
			/*
			 * 处理成功后页面参数
			 */
			this.setLotteryName(LotteryTools.getLotteryName(LotteryStaticDefine.LOTTERY_1000002));
			this.setLotteryResult(this.convertArrToString(this.getB_code()));
			this.setWinResult(this.convertWinResult(new String[] {"","一等奖","二等奖","三等奖","四等奖","五等奖","六等奖"},winResult));
			/*
			 * 写入数据库
			 */
			termManager.inputSevenColorWinInfo(this.getTermNo(), lotteryResult, this.getSalesVolume(), this.getJackpot(), winResult);
			
			dealResult = SUCCESS;
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			dealResult = ERROR;
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			dealResult = ERROR;
		}
		
		this.writeLog(dealResult, this.getTermNo(), String.valueOf(LotteryStaticDefine.LOTTERY_1000002), null, this.getEx_reason());
		
		return dealResult;
	}
	/**
	 * 
	 * Title: lotteryArrangeWinInput<br>
	 * Description: <br>
	 *              <br>排列3、5开奖信息输入
	 * @return String
	 */
	public String lotteryArrangeWinInput(){
		String dealResult = SUCCESS;
		try{
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			/*
			 * 组织界面的数据
			 */
			//开奖结果
			this.b_code = new ArrayList<String>();
			this.b_code.add(this.getE_code0());
			this.b_code.add(this.getE_code1());
			this.b_code.add(this.getE_code2());
			
			this.e_code = new ArrayList<String>();
			this.e_code.add(this.getE_code0());
			this.e_code.add(this.getE_code1());
			this.e_code.add(this.getE_code2());
			this.e_code.add(this.getE_code3());
			this.e_code.add(this.getE_code4());
			
			Map<String,String> lotteryResult3 = new TreeMap<String,String>();
			for(int i = 0 ; i < 3; i++){
				lotteryResult3.put(String.valueOf(i+1), this.b_code.get(i));
			}
			
			//奖金结果 参数分别为<直选奖、组三奖和组六奖用(分别用1,2,3代表),[注数,金额]>，如果没有组3或组6则该集合元素不设置
			Map<String,String[]> winResult3 = new TreeMap<String,String[]>();
			winResult3.put("1", new String [] {this.getWin_1_s(),this.getWin_1_j()});
			if(StringUtils.isNotEmpty(this.getWin_2_s()) && StringUtils.isNotEmpty(this.getWin_2_j())){
				winResult3.put("2", new String [] {this.getWin_2_s(),this.getWin_2_j()});
			}
			if(StringUtils.isNotEmpty(this.getWin_3_s()) && StringUtils.isNotEmpty(this.getWin_3_j())){
				winResult3.put("3", new String [] {this.getWin_3_s(),this.getWin_3_j()});
			}
			
			
			Map<String,String> lotteryResult5 = new TreeMap<String,String>();
			for(int i = 0 ; i < 5; i++){
				lotteryResult5.put(String.valueOf(i+1), this.e_code.get(i));
			}
			
			Map<String,String> winResult5 = new TreeMap<String,String>();
			winResult5.put("A", this.getWin_s_1_s());
			winResult5.put("B",this.getWin_s_1_j());
		
			
			/*
			 * 处理成功后页面参数
			 */
			this.setLotteryName(LotteryTools.getLotteryName(LotteryStaticDefine.LOTTERY_1000003));
			this.setLotteryResult(this.convertArrToString(this.getB_code()));
			this.setWinResult(this.convertWinResult(new String [] {"","排列3直选","排列3组选6","排列3组选3"},winResult3));
			
			this.setHava_add("Y");
			this.setLotteryName_second(LotteryTools.getLotteryName(LotteryStaticDefine.LOTTERY_1000004));
			this.setLotteryReslut_second(this.convertArrToString(this.getE_code()));
			Map<String,Map<String,String>> winResult_second = new HashMap<String,Map<String,String>>();
			winResult_second.put("排列5直选", winResult5);
			this.setWinResult_second(winResult_second);
			this.setLotteryId_second(String.valueOf(LotteryStaticDefine.LOTTERY_1000004));
			/*
			 * 写入数据库
			 */
			termManager.inputArrange3and5WinInfo(this.getTermNo(), lotteryResult3, this.getSalesVolume(), winResult3, lotteryResult5, this.getSalesVolume_second(), winResult5);
			
			dealResult = SUCCESS;
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			dealResult = ERROR;
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			dealResult = ERROR;
		}
		
		this.writeLog(dealResult, this.getTermNo(), String
				.valueOf(LotteryStaticDefine.LOTTERY_1000003), String
				.valueOf(LotteryStaticDefine.LOTTERY_1000004), this
				.getEx_reason());
		
		return dealResult;
	}
	/**
	 * 
	 * Title: lotteryBallWinInput<br>
	 * Description: <br>
	 *              <br>足彩开奖信息输入
	 * @return String
	 */
	public String lotteryBallWinInput(){
		String dealResult = SUCCESS;
		try {
			LotteryManagerInterf termManager = ApplicationContextUtils
					.getService("busLotteryManagerService",
							LotteryManagerInterf.class);
			/*
			 * 组织界面的数据
			 */
			//开奖结果
			this.b_code = new ArrayList<String>();
			this.b_code.add(this.getB_code0());
			this.b_code.add(this.getB_code1());
			this.b_code.add(this.getB_code2());
			this.b_code.add(this.getB_code3());
			this.b_code.add(this.getB_code4());
			this.b_code.add(this.getB_code5());
			this.b_code.add(this.getB_code6());
			this.b_code.add(this.getB_code7());
			this.b_code.add(this.getB_code8());
			this.b_code.add(this.getB_code9());
			this.b_code.add(this.getB_code10());
			this.b_code.add(this.getB_code11());
			this.b_code.add(this.getB_code12());
			this.b_code.add(this.getB_code13());
			// 奖金结果 参数为:<一等奖(1)二等奖(2),[注数,金额]>
			TreeMap<String, String[]> winResultW = new TreeMap<String, String[]>();
			winResultW.put("1", new String[] { this.getWin_1_s(),
					this.getWin_1_j() });
			winResultW.put("2", new String[] { this.getWin_2_s(),
					this.getWin_2_j() });

			// 奖金结果 奖金单位为元 参数为 <A(注数)B(金额)标识,值>,如:<A,10> <B,200>
			Map<String, String> winResult9 = new TreeMap<String, String>();
			winResult9.put("A", this.getWin_3_s());
			winResult9.put("B", this.getWin_3_j());

			/*
			 * 处理成功后页面参数
			 */
			this.setLotteryName(LotteryTools
					.getLotteryName(LotteryStaticDefine.LOTTERY_1300001));
			
			this.setLotteryResult(this.convertArrToString(this.getB_code()));
			
			this.setWinResult(this.convertWinResult(new String[] { "", "一等奖",
					"二等奖" }, winResultW));

			this.setHava_add("Y");

			this.setLotteryName_second(LotteryTools
					.getLotteryName(LotteryStaticDefine.LOTTERY_1300002));
			
			this.setLotteryReslut_second(this.convertArrToString(this
					.getB_code()));
			
			Map<String, Map<String, String>> winResult_second = new HashMap<String, Map<String, String>>();
			winResult_second.put("一等奖", winResult9);
			this.setWinResult_second(winResult_second);
			/*
			 * 写入数据库
			 */
			termManager.inputWinAndNineWinInfo(this.getTermNo(), this
					.getB_code(), this.getSalesVolume(), this.getJackpot(),
					winResultW, this.getB_code(), this.getSalesVolume_second(),
					this.getJackpot_second(), winResult9);
			
			dealResult = SUCCESS;

		} catch (LotteryException e) {
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			dealResult = ERROR;
		} catch (Exception ex) {
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			dealResult = ERROR;
		}
		
		this.writeLog(dealResult, this.getTermNo(), String
				.valueOf(LotteryStaticDefine.LOTTERY_1300001), String
				.valueOf(LotteryStaticDefine.LOTTERY_1300002), this
				.getEx_reason());
		
		return dealResult;
	}
	/**
	 * 
	 * Title: lotteryJxDlcWinInput<br>
	 * Description: <br>
	 *              <br>江西多乐彩开奖信息输入
	 * @return
	 */
	public String lotteryJxDlcWinInput(){
		String dealResult = SUCCESS;
		try{
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			/*
			 * 组织界面的数据
			 */
			//开奖结果
			this.b_code = new ArrayList<String>();
			this.b_code.add(this.getB_code0());
			this.b_code.add(this.getB_code1());
			this.b_code.add(this.getB_code2());
			this.b_code.add(this.getB_code3());
			this.b_code.add(this.getB_code4());
			
			ArrayList<String> lotteryResult = this.getB_code();
			
			
			//奖金结果 奖金单位为元 参数意思为：
			
			LinkedHashMap<String, String[]> winResult = new LinkedHashMap<String,String[]>();
			winResult.put("1", new String[] {this.getWin_1_s(),this.getWin_1_j()});
			winResult.put("2", new String[] {this.getWin_2_s(),this.getWin_2_j()});
			winResult.put("3", new String[] {this.getWin_3_s(),this.getWin_3_j()});
			winResult.put("4", new String[] {this.getWin_4_s(),this.getWin_4_j()});
			winResult.put("5", new String[] {this.getWin_5_s(),this.getWin_5_j()});
			winResult.put("6", new String[] {this.getWin_6_s(),this.getWin_6_j()});
			winResult.put("7", new String[] {this.getWin_7_s(),this.getWin_7_j()});
			winResult.put("8", new String[] {this.getWin_8_s(),this.getWin_8_j()});
			winResult.put("9", new String[] {this.getWin_9_s(),this.getWin_9_j()});
			winResult.put("10", new String[] {this.getWin_10_s(),this.getWin_10_j()});
			winResult.put("11", new String[] {this.getWin_11_s(),this.getWin_11_j()});
			winResult.put("12", new String[] {this.getWin_12_s(),this.getWin_12_j()});
			
			/*
			 * 处理成功后页面参数
			 */
			this.setLotteryName(LotteryTools.getLotteryName(LotteryStaticDefine.LOTTERY_1200001));
			this.setLotteryResult(this.convertArrToString(this.getB_code()));
			
			this.setWinResult(this.convertWinResult(new String[] {"","任选一中1","任选二中2","任选三中3","任选四中4","任选五中5",
					"任选六中5","任选七中5","任选八中5","选前二直选","选前三直选","选前二组选","选前三组选"},winResult));
			this.setHava_add("N");
			/*
			 * 写入数据库
			 */
			termManager.inputJxDlcWinInfo(this.getTermNo(), lotteryResult, this.getSalesVolume(), this.getJackpot(), winResult);
			
			dealResult = SUCCESS;
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			dealResult = ERROR;
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			dealResult = ERROR;
		}
		
		this.writeLog(dealResult, this.getTermNo(), String.valueOf(LotteryStaticDefine.LOTTERY_1200001), null, this.getEx_reason());
		return dealResult;
	}
	/**
	 * 
	 * Title: lotteryBall6WinInput<br>
	 * Description: <br>
	 *              <br>6场半全场开奖信息录入
	 * @return
	 */
	public String lotteryBall6WinInput(){
		String dealResult = SUCCESS;
		try {
			
			LotteryManagerInterf termManager = ApplicationContextUtils
					.getService("busLotteryManagerService",
							LotteryManagerInterf.class);
			/*
			 * 组织界面的数据
			 */
			
			// 奖金结果 奖金单位为元 参数为 <A(注数)B(金额)标识,值>,如:<A,10> <B,200>
			Map<String, String> winResult = new TreeMap<String, String>();
			winResult.put("A", this.getWin_1_s());
			winResult.put("B", this.getWin_1_j());
			
			//开奖结果
			ArrayList<String> lotteryResult = new ArrayList<String>();
			lotteryResult.add(this.getB_code0());
			lotteryResult.add(this.getE_code0());
			lotteryResult.add(this.getB_code1());
			lotteryResult.add(this.getE_code1());
			lotteryResult.add(this.getB_code2());
			lotteryResult.add(this.getE_code2());
			lotteryResult.add(this.getB_code3());
			lotteryResult.add(this.getE_code3());
			lotteryResult.add(this.getB_code4());
			lotteryResult.add(this.getE_code4());
			lotteryResult.add(this.getB_code5());
			lotteryResult.add(this.getE_code5());
			/*
			 * 处理成功后页面参数
			 */
			this.setLotteryName(LotteryTools
					.getLotteryName(LotteryStaticDefine.LOTTERY_1300003));
			this.setLotteryResult(this.convertArrToString(lotteryResult));
			
			Map<String, Map<String, String>> winResult_second = new HashMap<String, Map<String, String>>();
			winResult_second.put("一等奖", winResult);
			this.setWinResult(winResult_second);
			
			/*
			 * 写入数据库
			 */
			termManager.inputHalfSixWinInfo(this.getTermNo(), lotteryResult, this.getSalesVolume(), this.getJackpot(), winResult);
			
			dealResult = SUCCESS;

		} catch (LotteryException e) {
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			dealResult = ERROR;
		} catch (Exception ex) {
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			dealResult = ERROR;
		}
		
		this.writeLog(dealResult, this.getTermNo(), String.valueOf(LotteryStaticDefine.LOTTERY_1300003), null, this.getEx_reason());
		
		return dealResult;
	}
	/**
	 * 
	 * Title: lotteryBall4WinInput<br>
	 * Description: <br>
	 *              <br>4场进球彩开奖信息录入
	 * @return
	 */
	public String lotteryBall4WinInput(){
		String dealResult = SUCCESS;
		try {
			
			LotteryManagerInterf termManager = ApplicationContextUtils
					.getService("busLotteryManagerService",
							LotteryManagerInterf.class);
			/*
			 * 组织界面的数据
			 */
			
			// 奖金结果 奖金单位为元 参数为 <A(注数)B(金额)标识,值>,如:<A,10> <B,200>
			Map<String, String> winResult = new TreeMap<String, String>();
			winResult.put("A", this.getWin_1_s());
			winResult.put("B", this.getWin_1_j());
			
			//开奖结果
			ArrayList<String> lotteryResult = new ArrayList<String>();
			lotteryResult.add(this.getB_code0());
			lotteryResult.add(this.getE_code0());
			lotteryResult.add(this.getB_code1());
			lotteryResult.add(this.getE_code1());
			lotteryResult.add(this.getB_code2());
			lotteryResult.add(this.getE_code2());
			lotteryResult.add(this.getB_code3());
			lotteryResult.add(this.getE_code3());

			/*
			 * 处理成功后页面参数
			 */
			this.setLotteryName(LotteryTools
					.getLotteryName(LotteryStaticDefine.LOTTERY_1300004));
			this.setLotteryResult(this.convertArrToString(lotteryResult));
			
			Map<String, Map<String, String>> winResult_second = new HashMap<String, Map<String, String>>();
			winResult_second.put("一等奖", winResult);
			this.setWinResult(winResult_second);
			
			/*
			 * 写入数据库
			 */
			termManager.inputBallFourWinInfo(this.getTermNo(), lotteryResult, this.getSalesVolume(), this.getJackpot(), winResult);
			
			dealResult = SUCCESS;

		} catch (LotteryException e) {
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			dealResult = ERROR;
		} catch (Exception ex) {
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			dealResult = ERROR;
		}
		
		this.writeLog(dealResult, this.getTermNo(), String.valueOf(LotteryStaticDefine.LOTTERY_1300004), null, this.getEx_reason());
		
		return dealResult;
	}

	/**
	 * 
	 * Title: dealSuperWinResult<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @return
	 * @throws Exception
	 */
	private TreeMap<String,TreeMap<String,String[]>> dealSuperWinResult() throws Exception{
		TreeMap<String,TreeMap<String,String[]>> winResultS = new TreeMap<String,TreeMap<String,String[]>>();
		
		winResultS.put("1", this.dealSuperWinResult(this.getWin_1_s(), this.getWin_1_j(), this.getWin_1_a_s(), this.getWin_1_a_j()));
		winResultS.put("2", this.dealSuperWinResult(this.getWin_2_s(), this.getWin_2_j(), this.getWin_2_a_s(), this.getWin_2_a_j()));
		winResultS.put("3", this.dealSuperWinResult(this.getWin_3_s(), this.getWin_3_j(), this.getWin_3_a_s(), this.getWin_3_a_j()));
		winResultS.put("4", this.dealSuperWinResult(this.getWin_4_s(), this.getWin_4_j(), this.getWin_4_a_s(), this.getWin_4_a_j()));
		winResultS.put("5", this.dealSuperWinResult(this.getWin_5_s(), this.getWin_5_j(), this.getWin_5_a_s(), this.getWin_5_a_j()));
		winResultS.put("6", this.dealSuperWinResult(this.getWin_6_s(), this.getWin_6_j(), this.getWin_6_a_s(), this.getWin_6_a_j()));
		winResultS.put("7", this.dealSuperWinResult(this.getWin_7_s(), this.getWin_7_j(), this.getWin_7_a_s(), this.getWin_7_a_j()));
		winResultS.put("8", this.dealSuperWinResult(this.getWin_8_s(), this.getWin_8_j(), null, null));
		
		return winResultS;
	}
	/**
	 * 
	 * Title: dealSuperWinResult<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param s
	 * @param j
	 * @param a_s
	 * @param a_j
	 * @return
	 * @throws Exception
	 */
	private TreeMap<String,String[]> dealSuperWinResult(String s,String j,String a_s,String a_j) throws Exception{
		TreeMap<String,String[]> win = new TreeMap<String,String[]>();
		win.put("A", new String [] {s,j});
		if(StringUtils.isNotEmpty(a_s) && StringUtils.isNotEmpty(a_j)){
			win.put("B", new String [] {a_s,a_j});
		}
		return win;
	}
	
	private Map<String,String[]> dealColorWinResult(){
		Map<String,String[]> result = new TreeMap<String,String[]>();
		result.put("1",new String []{this.getWin_1_s(),this.getWin_1_j()});
		result.put("2",new String []{this.getWin_2_s(),this.getWin_2_j()});
		result.put("3",new String []{this.getWin_3_s(),this.getWin_3_j()});
		result.put("4",new String []{this.getWin_4_s(),this.getWin_4_j()});
		result.put("5",new String []{this.getWin_5_s(),this.getWin_5_j()});
		result.put("6",new String []{this.getWin_6_s(),this.getWin_6_j()});
		return result;
	}
	
	private String convertArrToString(List<String> input){
		StringBuffer sb = new StringBuffer();
		if(input != null){
			for (String s : input){
				sb.append(s).append("*");
			}
		}
		if(sb.toString().endsWith("*")){
			sb.deleteCharAt(sb.lastIndexOf("*"));
		}
		return sb.toString();
	}
	/**
	 * 
	 * Title: convertSuperWinResult<br>
	 * Description: <br>
	 *              <br>转换大乐透的奖金结果用于页面显示
	 * @param winResult
	 * @return
	 */
	private Map<String,Map<String,String>> convertSuperWinResult(TreeMap<String,TreeMap<String,String[]>> winResult){
       //奖金结果 奖金单位为元 参数意思为：<一等奖到八等奖(用数字1-8表示),<基本奖(A)或追加(B)奖,[注数,金额]>>,8等奖无追加因此8等奖可不填写追加的集合元素
		Map<String,Map<String,String>> result = new LinkedHashMap<String,Map<String,String>>();
		if(winResult != null){
			String [] keyName = {"","一等奖","二等奖","三等奖","四等奖","五等奖","六等奖","七等奖","八等奖"};
			for(Map.Entry<String, TreeMap<String,String[]>> oneEntry : winResult.entrySet()){
				String key = oneEntry.getKey();
				int keyInt = Integer.parseInt(key);
				TreeMap<String,String[]> tmpMap = oneEntry.getValue();
				Map<String,String> b_map = new HashMap<String,String>();
				b_map.put("A", tmpMap.get("A")[0]);
				b_map.put("B", tmpMap.get("A")[1]);
				result.put(keyName[keyInt]+"基本", b_map);
				if(tmpMap.get("B") != null){
					if((tmpMap.get("B") instanceof String[]) && tmpMap.get("B").length == 2){
						Map<String,String> a_map = new HashMap<String,String>();
						a_map.put("A", tmpMap.get("B")[0]);
						a_map.put("B", tmpMap.get("B")[1]);
						result.put(keyName[keyInt]+"追加", a_map);
					}
				}
				
			}
		}
		return result;
	}
	/**
	 * 
	 * Title: convertWinResult<br>
	 * Description: <br>
	 *              <br>转换奖金结果提供页面使用
	 * @param keyName
	 * @param winResult
	 * @return Map<String,Map<String,String>>
	 */
	private Map<String,Map<String,String>> convertWinResult(String [] keyName,Map<String,String[]> winResult){
	       //奖金结果 参数分别为<直选奖、组三奖和组六奖用(分别用1,2,3代表),[注数,金额]>，如果没有组3或组6则该集合元素不设置
			Map<String,Map<String,String>> result = new LinkedHashMap<String,Map<String,String>>();
			if(winResult != null){
				for(Map.Entry<String, String[]> oneEntry : winResult.entrySet()){
					String key = oneEntry.getKey();
					int keyInt = Integer.parseInt(key);
					String[] tmp = oneEntry.getValue();
					if(tmp != null && tmp.length == 2){
						Map<String,String> b_map = new HashMap<String,String>();
						b_map.put("A", tmp[0]);
						b_map.put("B", tmp[1]);
						result.put(keyName[keyInt], b_map);
					}
				}
			}
			return result;
	}
	
	public String cancelSubmit(){
		return "cancel";
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public ArrayList<String> getB_code() {
		return b_code;
	}

	public void setB_code(ArrayList<String> b_code) {
		this.b_code = b_code;
	}

	public ArrayList<String> getE_code() {
		return e_code;
	}

	public void setE_code(ArrayList<String> e_code) {
		this.e_code = e_code;
	}

	public String getJackpot() {
		return jackpot;
	}

	public void setJackpot(String jackpot) {
		this.jackpot = jackpot;
	}

	public String getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}

	public String getP_lotteryId() {
		return p_lotteryId;
	}

	public void setP_lotteryId(String id) {
		p_lotteryId = id;
	}

	public String getP_termNo_begin() {
		return p_termNo_begin;
	}

	public void setP_termNo_begin(String no_begin) {
		p_termNo_begin = no_begin;
	}

	public String getP_termNo_end() {
		return p_termNo_end;
	}

	public void setP_termNo_end(String no_end) {
		p_termNo_end = no_end;
	}

	public String getWin_1_a_j() {
		return this.win_1_a_j;
	}

	public void setWin_1_a_j(String win_1_a_j) {
		this.win_1_a_j = win_1_a_j;
	}

	public String getWin_1_a_s() {
		return this.win_1_a_s;
	}

	public void setWin_1_a_s(String win_1_a_s) {
		this.win_1_a_s = win_1_a_s;
	}

	public String getWin_1_j() {
		return this.win_1_j;
	}

	public void setWin_1_j(String win_1_j) {
		this.win_1_j = win_1_j;
	}

	public String getWin_1_s() {
		return this.win_1_s;
	}

	public void setWin_1_s(String win_1_s) {
		this.win_1_s = win_1_s;
	}

	public String getWin_2_a_j() {
		return this.win_2_a_j;
	}

	public void setWin_2_a_j(String win_2_a_j) {
		this.win_2_a_j = win_2_a_j;
	}

	public String getWin_2_a_s() {
		return this.win_2_a_s;
	}

	public void setWin_2_a_s(String win_2_a_s) {
		this.win_2_a_s = win_2_a_s;
	}

	public String getWin_2_j() {
		return this.win_2_j;
	}

	public void setWin_2_j(String win_2_j) {
		this.win_2_j = win_2_j;
	}

	public String getWin_2_s() {
		return this.win_2_s;
	}

	public void setWin_2_s(String win_2_s) {
		this.win_2_s = win_2_s;
	}

	public String getWin_3_a_j() {
		return this.win_3_a_j;
	}

	public void setWin_3_a_j(String win_3_a_j) {
		this.win_3_a_j = win_3_a_j;
	}

	public String getWin_3_a_s() {
		return this.win_3_a_s;
	}

	public void setWin_3_a_s(String win_3_a_s) {
		this.win_3_a_s = win_3_a_s;
	}

	public String getWin_3_j() {
		return this.win_3_j;
	}

	public void setWin_3_j(String win_3_j) {
		this.win_3_j = win_3_j;
	}

	public String getWin_3_s() {
		return this.win_3_s;
	}

	public void setWin_3_s(String win_3_s) {
		this.win_3_s = win_3_s;
	}

	public String getWin_4_a_j() {
		return this.win_4_a_j;
	}

	public void setWin_4_a_j(String win_4_a_j) {
		this.win_4_a_j = win_4_a_j;
	}

	public String getWin_4_a_s() {
		return this.win_4_a_s;
	}

	public void setWin_4_a_s(String win_4_a_s) {
		this.win_4_a_s = win_4_a_s;
	}

	public String getWin_4_j() {
		return this.win_4_j;
	}

	public void setWin_4_j(String win_4_j) {
		this.win_4_j = win_4_j;
	}

	public String getWin_4_s() {
		return this.win_4_s;
	}

	public void setWin_4_s(String win_4_s) {
		this.win_4_s = win_4_s;
	}

	public String getWin_5_a_j() {
		return this.win_5_a_j;
	}

	public void setWin_5_a_j(String win_5_a_j) {
		this.win_5_a_j = win_5_a_j;
	}

	public String getWin_5_a_s() {
		return this.win_5_a_s;
	}

	public void setWin_5_a_s(String win_5_a_s) {
		this.win_5_a_s = win_5_a_s;
	}

	public String getWin_5_j() {
		return this.win_5_j;
	}

	public void setWin_5_j(String win_5_j) {
		this.win_5_j = win_5_j;
	}

	public String getWin_5_s() {
		return this.win_5_s;
	}

	public void setWin_5_s(String win_5_s) {
		this.win_5_s = win_5_s;
	}

	public String getWin_6_a_j() {
		return this.win_6_a_j;
	}

	public void setWin_6_a_j(String win_6_a_j) {
		this.win_6_a_j = win_6_a_j;
	}

	public String getWin_6_a_s() {
		return this.win_6_a_s;
	}

	public void setWin_6_a_s(String win_6_a_s) {
		this.win_6_a_s = win_6_a_s;
	}

	public String getWin_6_j() {
		return this.win_6_j;
	}

	public void setWin_6_j(String win_6_j) {
		this.win_6_j = win_6_j;
	}

	public String getWin_6_s() {
		return this.win_6_s;
	}

	public void setWin_6_s(String win_6_s) {
		this.win_6_s = win_6_s;
	}

	public String getWin_7_a_j() {
		return this.win_7_a_j;
	}

	public void setWin_7_a_j(String win_7_a_j) {
		this.win_7_a_j = win_7_a_j;
	}

	public String getWin_7_a_s() {
		return this.win_7_a_s;
	}

	public void setWin_7_a_s(String win_7_a_s) {
		this.win_7_a_s = win_7_a_s;
	}

	public String getWin_7_j() {
		return this.win_7_j;
	}

	public void setWin_7_j(String win_7_j) {
		this.win_7_j = win_7_j;
	}

	public String getWin_7_s() {
		return this.win_7_s;
	}

	public void setWin_7_s(String win_7_s) {
		this.win_7_s = win_7_s;
	}

	public String getWin_8_j() {
		return this.win_8_j;
	}

	public void setWin_8_j(String win_8_j) {
		this.win_8_j = win_8_j;
	}

	public String getWin_8_s() {
		return this.win_8_s;
	}

	public void setWin_8_s(String win_8_s) {
		this.win_8_s = win_8_s;
	}

	public String getWin_s_1_s() {
		return this.win_s_1_s;
	}

	public void setWin_s_1_s(String win_s_1_s) {
		this.win_s_1_s = win_s_1_s;
	}

	public String getWin_s_1_j() {
		return this.win_s_1_j;
	}

	public void setWin_s_1_j(String win_s_1_j) {
		this.win_s_1_j = win_s_1_j;
	}

	public String getHava_add() {
		return hava_add;
	}

	public void setHava_add(String hava_add) {
		this.hava_add = hava_add;
	}

	public String getJackpot_second() {
		return jackpot_second;
	}

	public void setJackpot_second(String jackpot_second) {
		this.jackpot_second = jackpot_second;
	}

	public String getLotteryId_second() {
		return lotteryId_second;
	}

	public void setLotteryId_second(String lotteryId_second) {
		this.lotteryId_second = lotteryId_second;
	}

	public String getLotteryReslut_second() {
		return lotteryReslut_second;
	}

	public void setLotteryReslut_second(String lotteryReslut_second) {
		this.lotteryReslut_second = lotteryReslut_second;
	}

	public String getLotteryResult() {
		return lotteryResult;
	}

	public void setLotteryResult(String lotteryResult) {
		this.lotteryResult = lotteryResult;
	}

	public Map<String, Map<String, String>> getWinResult() {
		return winResult;
	}

	public void setWinResult(Map<String, Map<String, String>> winResult) {
		this.winResult = winResult;
	}

	public Map<String, Map<String, String>> getWinResult_second() {
		return winResult_second;
	}

	public void setWinResult_second(
			Map<String, Map<String, String>> winResult_second) {
		this.winResult_second = winResult_second;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getLotteryName_second() {
		return lotteryName_second;
	}

	public void setLotteryName_second(String lotteryName_second) {
		this.lotteryName_second = lotteryName_second;
	}

	public int getEx_code() {
		return ex_code;
	}

	public void setEx_code(int ex_code) {
		this.ex_code = ex_code;
	}

	public String getEx_reason() {
		return ex_reason;
	}

	public void setEx_reason(String ex_reason) {
		this.ex_reason = ex_reason;
	}

	public String getSalesVolume_second() {
		return salesVolume_second;
	}

	public void setSalesVolume_second(String salesVolume_second) {
		this.salesVolume_second = salesVolume_second;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public String getWin_10_j() {
		return win_10_j;
	}

	public void setWin_10_j(String win_10_j) {
		this.win_10_j = win_10_j;
	}

	public String getWin_10_s() {
		return win_10_s;
	}

	public void setWin_10_s(String win_10_s) {
		this.win_10_s = win_10_s;
	}

	public String getWin_11_j() {
		return win_11_j;
	}

	public void setWin_11_j(String win_11_j) {
		this.win_11_j = win_11_j;
	}

	public String getWin_11_s() {
		return win_11_s;
	}

	public void setWin_11_s(String win_11_s) {
		this.win_11_s = win_11_s;
	}

	public String getWin_12_j() {
		return win_12_j;
	}

	public void setWin_12_j(String win_12_j) {
		this.win_12_j = win_12_j;
	}

	public String getWin_12_s() {
		return win_12_s;
	}

	public void setWin_12_s(String win_12_s) {
		this.win_12_s = win_12_s;
	}

	public String getWin_9_j() {
		return win_9_j;
	}

	public void setWin_9_j(String win_9_j) {
		this.win_9_j = win_9_j;
	}

	public String getWin_9_s() {
		return win_9_s;
	}

	public void setWin_9_s(String win_9_s) {
		this.win_9_s = win_9_s;
	}
	public String getB_code0() {
		return b_code0;
	}
	public void setB_code0(String b_code0) {
		this.b_code0 = b_code0;
	}
	public String getB_code1() {
		return b_code1;
	}
	public void setB_code1(String b_code1) {
		this.b_code1 = b_code1;
	}
	public String getB_code2() {
		return b_code2;
	}
	public void setB_code2(String b_code2) {
		this.b_code2 = b_code2;
	}
	public String getB_code3() {
		return b_code3;
	}
	public void setB_code3(String b_code3) {
		this.b_code3 = b_code3;
	}
	public String getB_code4() {
		return b_code4;
	}
	public void setB_code4(String b_code4) {
		this.b_code4 = b_code4;
	}
	public String getE_code0() {
		return e_code0;
	}
	public void setE_code0(String e_code0) {
		this.e_code0 = e_code0;
	}
	public String getE_code1() {
		return e_code1;
	}
	public void setE_code1(String e_code1) {
		this.e_code1 = e_code1;
	}
	public String getB_code5() {
		return b_code5;
	}
	public void setB_code5(String b_code5) {
		this.b_code5 = b_code5;
	}
	public String getB_code6() {
		return b_code6;
	}
	public void setB_code6(String b_code6) {
		this.b_code6 = b_code6;
	}
	public String getB_code7() {
		return b_code7;
	}
	public void setB_code7(String b_code7) {
		this.b_code7 = b_code7;
	}
	public String getE_code2() {
		return e_code2;
	}
	public void setE_code2(String e_code2) {
		this.e_code2 = e_code2;
	}
	public String getE_code3() {
		return e_code3;
	}
	public void setE_code3(String e_code3) {
		this.e_code3 = e_code3;
	}
	public String getE_code4() {
		return e_code4;
	}
	public void setE_code4(String e_code4) {
		this.e_code4 = e_code4;
	}
	public String getB_code10() {
		return b_code10;
	}
	public void setB_code10(String b_code10) {
		this.b_code10 = b_code10;
	}
	public String getB_code11() {
		return b_code11;
	}
	public void setB_code11(String b_code11) {
		this.b_code11 = b_code11;
	}
	public String getB_code12() {
		return b_code12;
	}
	public void setB_code12(String b_code12) {
		this.b_code12 = b_code12;
	}
	public String getB_code13() {
		return b_code13;
	}
	public void setB_code13(String b_code13) {
		this.b_code13 = b_code13;
	}
	public String getB_code8() {
		return b_code8;
	}
	public void setB_code8(String b_code8) {
		this.b_code8 = b_code8;
	}
	public String getB_code9() {
		return b_code9;
	}
	public void setB_code9(String b_code9) {
		this.b_code9 = b_code9;
	}
	public String getE_code5() {
		return e_code5;
	}
	public void setE_code5(String e_code5) {
		this.e_code5 = e_code5;
	}

	

}
