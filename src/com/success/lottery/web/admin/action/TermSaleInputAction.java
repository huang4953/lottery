/**
 * Title: TermSaleInputAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-26 下午11:44:36
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.admin.user.domain.AdminUser;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.service.OperatorLogger;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.LibingUtils;

/**
 * com.success.lottery.web.admin.action
 * TermSaleInputAction.java
 * TermSaleInputAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-26 下午11:44:37
 * 
 */

public class TermSaleInputAction extends ActionSupport implements SessionAware{
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 6855690510887596483L;
	
	private Map			session;
	private String p_lotteryId;
	private String p_termNo_begin = "0";
	private String p_termNo_end = "0";
	
	private String l_lotteryId;
	private String l_termNo;
	
	private String lotteryName;
	
	/*
	 * 
	 */
	private int ex_code;
	private String ex_reason;
	
	/**
	 * 
	 * Title: lotteryBallSaleInput<br>
	 * Description: <br>
	 *              <br>输入销售信息
	 * @return
	 */
	
	public String lotteryBallSaleInput(){
		AdminUser adminUser = (AdminUser) this.getSession().get("tlt.loginuser");
		String dealResult = SUCCESS;
		try{
			Map<Integer, Map<String,String>> saleInfo = this.convertParamToSale();
			
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			if("1300001".equals(this.getL_lotteryId()) || "1300002".equals(this.getL_lotteryId())){
				this.setLotteryName("胜负彩 任选9");
				termManager.inputSaleInfo(this.getL_termNo(),
						this.getXk_time(), this.getGk_time(),
						this.getXz_time(), this.getGz_time(), null, this
								.getXj_time(), this.getGj_time(), saleInfo);
			}else{
				this.setLotteryName(LotteryTools.getLotteryName(Integer.parseInt(this.getL_lotteryId())));
				termManager.inputSaleInfo(Integer.parseInt(this
						.getL_lotteryId()), this.getL_termNo(), this
						.getXk_time(), this.getGk_time(), this.getXz_time(),
						this.getGz_time(), null, this.getXj_time(), this
								.getGj_time(), saleInfo);
			}
			
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
		
		try{
			Map<String, String> param = new HashMap<String, String>();
			param.put("userId", adminUser.getUserId()+"");
			param.put("userName", adminUser.getName());
			param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
			param.put("keyword1", this.getL_lotteryId());
			param.put("keyword2", this.getL_termNo());
			if(ERROR.equals(dealResult)){
				param.put("errorMessage", LotteryTools.getLotteryName(Integer.parseInt(this.getL_lotteryId()))+this.getEx_reason());
				OperatorLogger.log(41002, param);
			}else{
				OperatorLogger.log(40002, param);
			}
		}catch(Exception e){
			e.printStackTrace();//写日志出错不做任何处理
		}
		return dealResult;
	}
	
	/**
	 * 
	 * Title: showInputSaleInfo<br>
	 * Description: <br>
	 *              <br>销售信息输入界面初始化
	 * @return
	 */					
	public String showInputSaleInfo(){
		try{
			this.initPageParam();
			
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			return ERROR;
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			return ERROR;
		}
		return this.l_lotteryId;
	}
	/**
	 * 
	 * Title: initPageParam<br>
	 * Description: <br>
	 *              <br>初始化页面数据，用于修改销售信息
	 * @throws LotteryException
	 * @throws Exception
	 */
	private void initPageParam() throws LotteryException, Exception{
		
		try{
			LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			LotteryTermModel termModel = termService.queryTermInfo(Integer.parseInt(this.getL_lotteryId()), this.getL_termNo());
			
			if("1300001".equals(this.getL_lotteryId()) || "1300002".equals(this.getL_lotteryId())){
				this.setLotteryName("胜负彩 任选9");
			}else{
				this.setLotteryName(termModel.getLotteryName());
			}
			/*
			 * 设置时间设置
			 */
			this.setGk_time(this.convertDate(termModel.getStartTime2()));
			this.setXk_time(this.convertDate(termModel.getStartTime()));
			
			this.setGz_time(this.convertDate(termModel.getDeadLine3()));
			this.setXz_time(this.convertDate(termModel.getDeadLine()));
			
			this.setGj_time(this.convertDate(termModel.getWinLine2()));
			this.setXj_time(this.convertDate(termModel.getWinLine()));
			
			/*
			 * 场次比赛数据
			 */
			String db_sale_info = termModel.getSalesInfo();
			if(StringUtils.isNotEmpty(db_sale_info)){
				Map<Integer, Map<String,String>> saleMap  = LotteryTools.splitSalesInfo(db_sale_info);
				for(Map.Entry<Integer,Map<String,String>> innerMap : saleMap.entrySet()){
					int no = innerMap.getKey();
					Map<String,String> value = innerMap.getValue();
					this.convertSaleToParam(no, value.get("A"), value.get("B"), value.get("C"), value.get("D"));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}
	/**
	 * 
	 * Title: convertParamToSale<br>
	 * Description: <br>
	 *              <br>转换界面数据为销售集合
	 * @return
	 */
	private Map<Integer, Map<String,String>> convertParamToSale(){
		Map<Integer, Map<String,String>> saleMap = new TreeMap<Integer,Map<String,String>>();
		switch (Integer.parseInt(this.getL_lotteryId())) {
		case 1300001:
		case 1300002:
			saleMap.put(1, this.fillMap(this.getGameA1(), this.getGameB1(), this.getGameC1(), this.getGameD1()));
			saleMap.put(2, this.fillMap(this.getGameA2(), this.getGameB2(), this.getGameC2(), this.getGameD2()));
			saleMap.put(3, this.fillMap(this.getGameA3(), this.getGameB3(), this.getGameC3(), this.getGameD3()));
			saleMap.put(4, this.fillMap(this.getGameA4(), this.getGameB4(), this.getGameC4(), this.getGameD4()));
			saleMap.put(5, this.fillMap(this.getGameA5(), this.getGameB5(), this.getGameC5(), this.getGameD5()));
			saleMap.put(6, this.fillMap(this.getGameA6(), this.getGameB6(), this.getGameC6(), this.getGameD6()));
			saleMap.put(7, this.fillMap(this.getGameA7(), this.getGameB7(), this.getGameC7(), this.getGameD7()));
			saleMap.put(8, this.fillMap(this.getGameA8(), this.getGameB8(), this.getGameC8(), this.getGameD8()));
			saleMap.put(9, this.fillMap(this.getGameA9(), this.getGameB9(), this.getGameC9(), this.getGameD9()));
			saleMap.put(10, this.fillMap(this.getGameA10(), this.getGameB10(), this.getGameC10(), this.getGameD10()));
			saleMap.put(11, this.fillMap(this.getGameA11(), this.getGameB11(), this.getGameC11(), this.getGameD11()));
			saleMap.put(12, this.fillMap(this.getGameA12(), this.getGameB12(), this.getGameC12(), this.getGameD12()));
			saleMap.put(13, this.fillMap(this.getGameA13(), this.getGameB13(), this.getGameC13(), this.getGameD13()));
			saleMap.put(14, this.fillMap(this.getGameA14(), this.getGameB14(), this.getGameC14(), this.getGameD14()));
			break;
		case 1300003://6场半全场
			saleMap.put(1, this.fillMap(this.getGameA1(), this.getGameB1(), this.getGameC1(), this.getGameD1()));
			saleMap.put(2, this.fillMap(this.getGameA2(), this.getGameB2(), this.getGameC2(), this.getGameD2()));
			saleMap.put(3, this.fillMap(this.getGameA3(), this.getGameB3(), this.getGameC3(), this.getGameD3()));
			saleMap.put(4, this.fillMap(this.getGameA4(), this.getGameB4(), this.getGameC4(), this.getGameD4()));
			saleMap.put(5, this.fillMap(this.getGameA5(), this.getGameB5(), this.getGameC5(), this.getGameD5()));
			saleMap.put(6, this.fillMap(this.getGameA6(), this.getGameB6(), this.getGameC6(), this.getGameD6()));
			break;
		case 1300004://4场进球彩
			saleMap.put(1, this.fillMap(this.getGameA1(), this.getGameB1(), this.getGameC1(), this.getGameD1()));
			saleMap.put(2, this.fillMap(this.getGameA2(), this.getGameB2(), this.getGameC2(), this.getGameD2()));
			saleMap.put(3, this.fillMap(this.getGameA3(), this.getGameB3(), this.getGameC3(), this.getGameD3()));
			saleMap.put(4, this.fillMap(this.getGameA4(), this.getGameB4(), this.getGameC4(), this.getGameD4()));
			break;
		default:
			break;
		}
		
		return saleMap;
	}
	/**
	 * 
	 * Title: fillMap<br>
	 * Description: <br>
	 *            (这里用一句话描述这个方法的作用)<br>
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	private Map<String,String> fillMap(String a,String b,String c,String d){
		Map<String,String> sale = new TreeMap<String,String>();
		sale.put("A", a);
		sale.put("B", b);
		sale.put("C", c);
		sale.put("D", d);
		return sale;
	}
	/**
	 * 
	 * Title: convertSaleToParam<br>
	 * Description: <br>
	 *              <br>转换销售信息数据为页面显示数据
	 * @param no
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	private void convertSaleToParam(int no,String a,String b,String c,String d){
		switch (no) {
		case 1:
			this.setGameA1(a);
			this.setGameB1(b);
			this.setGameC1(c);
			this.setGameD1(d);
			break;
		case 2:
			this.setGameA2(a);
			this.setGameB2(b);
			this.setGameC2(c);
			this.setGameD2(d);
			break;
		case 3:
			this.setGameA3(a);
			this.setGameB3(b);
			this.setGameC3(c);
			this.setGameD3(d);
			break;
		case 4:
			this.setGameA4(a);
			this.setGameB4(b);
			this.setGameC4(c);
			this.setGameD4(d);
			break;
		case 5:
			this.setGameA5(a);
			this.setGameB5(b);
			this.setGameC5(c);
			this.setGameD5(d);
			break;
		case 6:
			this.setGameA6(a);
			this.setGameB6(b);
			this.setGameC6(c);
			this.setGameD6(d);
			break;
		case 7:
			this.setGameA7(a);
			this.setGameB7(b);
			this.setGameC7(c);
			this.setGameD7(d);
			break;
		case 8:
			this.setGameA8(a);
			this.setGameB8(b);
			this.setGameC8(c);
			this.setGameD8(d);
			break;
		case 9:
			this.setGameA9(a);
			this.setGameB9(b);
			this.setGameC9(c);
			this.setGameD9(d);
			break;
		case 10:
			this.setGameA10(a);
			this.setGameB10(b);
			this.setGameC10(c);
			this.setGameD10(d);
			break;
		case 11:
			this.setGameA11(a);
			this.setGameB11(b);
			this.setGameC11(c);
			this.setGameD11(d);
			break;
		case 12:
			this.setGameA12(a);
			this.setGameB12(b);
			this.setGameC12(c);
			this.setGameD12(d);
			break;
		case 13:
			this.setGameA13(a);
			this.setGameB13(b);
			this.setGameC13(c);
			this.setGameD13(d);
			break;
		case 14:
			this.setGameA14(a);
			this.setGameB14(b);
			this.setGameC14(c);
			this.setGameD14(d);
			break;
		default:
			break;
		}
	}
	
	private String convertDate(Timestamp date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return date == null ? "" : sdf.format(date);
	}

	public String getL_lotteryId() {
		return l_lotteryId;
	}

	public void setL_lotteryId(String id) {
		l_lotteryId = id;
	}

	public String getL_termNo() {
		return l_termNo;
	}

	public void setL_termNo(String no) {
		l_termNo = no;
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
	public String getGj_time() {
		return gj_time;
	}

	public void setGj_time(String gj_time) {
		this.gj_time = gj_time;
	}

	public String getGk_time() {
		return gk_time;
	}

	public void setGk_time(String gk_time) {
		this.gk_time = gk_time;
	}

	public String getGz_time() {
		return gz_time;
	}

	public void setGz_time(String gz_time) {
		this.gz_time = gz_time;
	}

	public String getXj_time() {
		return xj_time;
	}

	public void setXj_time(String xj_time) {
		this.xj_time = xj_time;
	}

	public String getXk_time() {
		return xk_time;
	}

	public void setXk_time(String xk_time) {
		this.xk_time = xk_time;
	}

	public String getXz_time() {
		return xz_time;
	}

	public void setXz_time(String xz_time) {
		this.xz_time = xz_time;
	}

	public String getGameA1() {
		return gameA1;
	}

	public void setGameA1(String gameA1) {
		this.gameA1 = gameA1;
	}

	public String getGameA10() {
		return gameA10;
	}

	public void setGameA10(String gameA10) {
		this.gameA10 = gameA10;
	}

	public String getGameA11() {
		return gameA11;
	}

	public void setGameA11(String gameA11) {
		this.gameA11 = gameA11;
	}

	public String getGameA12() {
		return gameA12;
	}

	public void setGameA12(String gameA12) {
		this.gameA12 = gameA12;
	}

	public String getGameA13() {
		return gameA13;
	}

	public void setGameA13(String gameA13) {
		this.gameA13 = gameA13;
	}

	public String getGameA14() {
		return gameA14;
	}

	public void setGameA14(String gameA14) {
		this.gameA14 = gameA14;
	}

	public String getGameA2() {
		return gameA2;
	}

	public void setGameA2(String gameA2) {
		this.gameA2 = gameA2;
	}

	public String getGameA3() {
		return gameA3;
	}

	public void setGameA3(String gameA3) {
		this.gameA3 = gameA3;
	}

	public String getGameA4() {
		return gameA4;
	}

	public void setGameA4(String gameA4) {
		this.gameA4 = gameA4;
	}

	public String getGameA5() {
		return gameA5;
	}

	public void setGameA5(String gameA5) {
		this.gameA5 = gameA5;
	}

	public String getGameA6() {
		return gameA6;
	}

	public void setGameA6(String gameA6) {
		this.gameA6 = gameA6;
	}

	public String getGameA7() {
		return gameA7;
	}

	public void setGameA7(String gameA7) {
		this.gameA7 = gameA7;
	}

	public String getGameA8() {
		return gameA8;
	}

	public void setGameA8(String gameA8) {
		this.gameA8 = gameA8;
	}

	public String getGameA9() {
		return gameA9;
	}

	public void setGameA9(String gameA9) {
		this.gameA9 = gameA9;
	}

	public String getGameB1() {
		return gameB1;
	}

	public void setGameB1(String gameB1) {
		this.gameB1 = gameB1;
	}

	public String getGameB10() {
		return gameB10;
	}

	public void setGameB10(String gameB10) {
		this.gameB10 = gameB10;
	}

	public String getGameB11() {
		return gameB11;
	}

	public void setGameB11(String gameB11) {
		this.gameB11 = gameB11;
	}

	public String getGameB12() {
		return gameB12;
	}

	public void setGameB12(String gameB12) {
		this.gameB12 = gameB12;
	}

	public String getGameB13() {
		return gameB13;
	}

	public void setGameB13(String gameB13) {
		this.gameB13 = gameB13;
	}

	public String getGameB14() {
		return gameB14;
	}

	public void setGameB14(String gameB14) {
		this.gameB14 = gameB14;
	}

	public String getGameB2() {
		return gameB2;
	}

	public void setGameB2(String gameB2) {
		this.gameB2 = gameB2;
	}

	public String getGameB3() {
		return gameB3;
	}

	public void setGameB3(String gameB3) {
		this.gameB3 = gameB3;
	}

	public String getGameB4() {
		return gameB4;
	}

	public void setGameB4(String gameB4) {
		this.gameB4 = gameB4;
	}

	public String getGameB5() {
		return gameB5;
	}

	public void setGameB5(String gameB5) {
		this.gameB5 = gameB5;
	}

	public String getGameB6() {
		return gameB6;
	}

	public void setGameB6(String gameB6) {
		this.gameB6 = gameB6;
	}

	public String getGameB7() {
		return gameB7;
	}

	public void setGameB7(String gameB7) {
		this.gameB7 = gameB7;
	}

	public String getGameB8() {
		return gameB8;
	}

	public void setGameB8(String gameB8) {
		this.gameB8 = gameB8;
	}

	public String getGameB9() {
		return gameB9;
	}

	public void setGameB9(String gameB9) {
		this.gameB9 = gameB9;
	}

	public String getGameC1() {
		return gameC1;
	}

	public void setGameC1(String gameC1) {
		this.gameC1 = gameC1;
	}

	public String getGameC10() {
		return gameC10;
	}

	public void setGameC10(String gameC10) {
		this.gameC10 = gameC10;
	}

	public String getGameC11() {
		return gameC11;
	}

	public void setGameC11(String gameC11) {
		this.gameC11 = gameC11;
	}

	public String getGameC12() {
		return gameC12;
	}

	public void setGameC12(String gameC12) {
		this.gameC12 = gameC12;
	}

	public String getGameC13() {
		return gameC13;
	}

	public void setGameC13(String gameC13) {
		this.gameC13 = gameC13;
	}

	public String getGameC14() {
		return gameC14;
	}

	public void setGameC14(String gameC14) {
		this.gameC14 = gameC14;
	}

	public String getGameC2() {
		return gameC2;
	}

	public void setGameC2(String gameC2) {
		this.gameC2 = gameC2;
	}

	public String getGameC3() {
		return gameC3;
	}

	public void setGameC3(String gameC3) {
		this.gameC3 = gameC3;
	}

	public String getGameC4() {
		return gameC4;
	}

	public void setGameC4(String gameC4) {
		this.gameC4 = gameC4;
	}

	public String getGameC5() {
		return gameC5;
	}

	public void setGameC5(String gameC5) {
		this.gameC5 = gameC5;
	}

	public String getGameC6() {
		return gameC6;
	}

	public void setGameC6(String gameC6) {
		this.gameC6 = gameC6;
	}

	public String getGameC7() {
		return gameC7;
	}

	public void setGameC7(String gameC7) {
		this.gameC7 = gameC7;
	}

	public String getGameC8() {
		return gameC8;
	}

	public void setGameC8(String gameC8) {
		this.gameC8 = gameC8;
	}

	public String getGameC9() {
		return gameC9;
	}

	public void setGameC9(String gameC9) {
		this.gameC9 = gameC9;
	}

	public String getGameD1() {
		return gameD1;
	}

	public void setGameD1(String gameD1) {
		this.gameD1 = gameD1;
	}

	public String getGameD10() {
		return gameD10;
	}

	public void setGameD10(String gameD10) {
		this.gameD10 = gameD10;
	}

	public String getGameD11() {
		return gameD11;
	}

	public void setGameD11(String gameD11) {
		this.gameD11 = gameD11;
	}

	public String getGameD12() {
		return gameD12;
	}

	public void setGameD12(String gameD12) {
		this.gameD12 = gameD12;
	}

	public String getGameD13() {
		return gameD13;
	}

	public void setGameD13(String gameD13) {
		this.gameD13 = gameD13;
	}

	public String getGameD14() {
		return gameD14;
	}

	public void setGameD14(String gameD14) {
		this.gameD14 = gameD14;
	}

	public String getGameD2() {
		return gameD2;
	}

	public void setGameD2(String gameD2) {
		this.gameD2 = gameD2;
	}

	public String getGameD3() {
		return gameD3;
	}

	public void setGameD3(String gameD3) {
		this.gameD3 = gameD3;
	}

	public String getGameD4() {
		return gameD4;
	}

	public void setGameD4(String gameD4) {
		this.gameD4 = gameD4;
	}

	public String getGameD5() {
		return gameD5;
	}

	public void setGameD5(String gameD5) {
		this.gameD5 = gameD5;
	}

	public String getGameD6() {
		return gameD6;
	}

	public void setGameD6(String gameD6) {
		this.gameD6 = gameD6;
	}

	public String getGameD7() {
		return gameD7;
	}

	public void setGameD7(String gameD7) {
		this.gameD7 = gameD7;
	}

	public String getGameD8() {
		return gameD8;
	}

	public void setGameD8(String gameD8) {
		this.gameD8 = gameD8;
	}

	public String getGameD9() {
		return gameD9;
	}

	public void setGameD9(String gameD9) {
		this.gameD9 = gameD9;
	}
	
	/*
	 * 页面输入的销售信息参数，
	 */
	private String gk_time;//体彩中心开售时间
	private String xk_time;//系统代购开售时间
	private String gz_time;//体彩中心止售时间
	private String xz_time;//系统代购止售时间
	private String gj_time;//体彩中心开奖时间
	private String xj_time;//系统开奖时间
	
	private String gameA1;
	private String gameB1;
	private String gameC1;
	private String gameD1;
	
	private String gameA2;
	private String gameB2;
	private String gameC2;
	private String gameD2;
	
	private String gameA3;
	private String gameB3;
	private String gameC3;
	private String gameD3;

	private String gameA4;
	private String gameB4;
	private String gameC4;
	private String gameD4;

	private String gameA5;
	private String gameB5;
	private String gameC5;
	private String gameD5;

	private String gameA6;
	private String gameB6;
	private String gameC6;
	private String gameD6;

	private String gameA7;
	private String gameB7;
	private String gameC7;
	private String gameD7;

	private String gameA8;
	private String gameB8;
	private String gameC8;
	private String gameD8;

	private String gameA9;
	private String gameB9;
	private String gameC9;
	private String gameD9;

	private String gameA10;
	private String gameB10;
	private String gameC10;
	private String gameD10;

	private String gameA11;
	private String gameB11;
	private String gameC11;
	private String gameD11;

	private String gameA12;
	private String gameB12;
	private String gameC12;
	private String gameD12;

	private String gameA13;
	private String gameB13;
	private String gameC13;
	private String gameD13;

	private String gameA14;
	private String gameB14;
	private String gameC14;
	private String gameD14;



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


	public Map getSession() {
		return session;
	}


	public void setSession(Map session) {
		this.session = session;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

}
