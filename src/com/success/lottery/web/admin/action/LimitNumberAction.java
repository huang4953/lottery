/**
 * Title: LimitNumberAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-5-24 上午09:52:14
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.admin.user.domain.AdminUser;
import com.success.lottery.business.service.LotteryStaticDefine;
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
 * LimitNumberAction.java
 * LimitNumberAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-5-24 上午09:52:14
 * 
 */

public class LimitNumberAction extends ActionSupport implements SessionAware{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 6148184676231800398L;
	
	private Map			session;
	private int ex_code;
	private String ex_reason;
	private String lotteryId;
	private String termNo;
	//界面限号数据,对排列3用limitNumber1，对多乐彩分别用limitNumber1到limitNumber7表示7个限号池
	private String limitNumber1;
	private String limitNumber2;
	private String limitNumber3;
	private String limitNumber4;
	private String limitNumber5;
	private String limitNumber6;
	private String limitNumber7;

	private String limitNumber_before;
	private String lottery_name;
	
	private List<LotteryTermModel> termLimitInfos;
	private LotteryTermModel termModel;
	
	private Map<Integer, String> lotteryList;
	private String p_lotteryId;
	private String p_termNo_begin = "0";
	private String p_termNo_end = "0";
	
	/**
	 * 
	 * Title: showLimitNumberInfo<br>
	 * Description: <br>
	 *              <br>限号列表显示
	 * @return String
	 */
	public String showLimitNumberInfo(){
		try{
			int limitNum = 10;
			//设置页面参数
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			this.setLotteryList(termManager.getNeedLimitLotteryList());
			if(StringUtils.isNotEmpty(this.convertParam(this.getP_termNo_begin())) || StringUtils.isNotEmpty(this.convertParam(this.p_termNo_end))){
				limitNum = 0;
			}
			
			LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			List<LotteryTermModel> termLimitInfos = termService.queryTermLimitNumberInfo(this.convertParam(this.getP_lotteryId()), this.convertParam(this.getP_termNo_begin()),
					this.convertParam(this.p_termNo_end), limitNum);
			
			/*
			 * 扩充LotteryTermModel对象
			 */
			String tmp_p_lotteryId = this.getP_lotteryId()==null?"0":this.getP_lotteryId();
			if(termLimitInfos != null){
				String reserve = tmp_p_lotteryId+"#"+this.getP_termNo_begin()+"#"+this.getP_termNo_end();
				for(LotteryTermModel oneTerm : termLimitInfos){
					oneTerm.setReserve1(reserve);
				}
			}
			this.setTermLimitInfos(termLimitInfos);
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
		return SUCCESS;
	}
	
	/**
	 * 
	 * Title: showInputLimitInfo<br>
	 * Description: <br>
	 *              <br>限号信息输入界面
	 * @return String
	 */
	public String showInputLimitInfo(){
		try{
			LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			LotteryTermModel termModel = termService.queryTermInfo(Integer.parseInt(this.getLotteryId()), this.getTermNo());
			if(termModel != null){
				Map<String,String> limitPool = LotteryTools.splitLimitResult(Integer.parseInt(this.getLotteryId()), termModel.getLimitNumber());
				if(limitPool != null){
					for(Map.Entry<String, String> entry : limitPool.entrySet()){
						switch (Integer.parseInt(entry.getKey())) {
						case 1:
							this.setLimitNumber1(entry.getValue());
							break;
						case 2:
							this.setLimitNumber2(entry.getValue());
							break;
						case 3:
							this.setLimitNumber3(entry.getValue());
							break;
						case 4:
							this.setLimitNumber4(entry.getValue());
							break;
						case 5:
							this.setLimitNumber5(entry.getValue());
							break;
						case 6:
							this.setLimitNumber6(entry.getValue());
							break;
						case 7:
							this.setLimitNumber7(entry.getValue());
							break;
						default:
							break;
						}
					}
				}
			}
			this.setTermModel(termModel);
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
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String inputLimitInfo(){
		AdminUser adminUser = (AdminUser)this.getSession().get("tlt.loginuser");// pzc add
		String dealResult = SUCCESS;
		String total_limit = "";
		try{
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			total_limit = this.mergeLimitPageData(this.getLotteryId());
			termManager.inputTermLimitNumber(Integer.parseInt(this.getLotteryId()), this.getTermNo(), total_limit);
			this.setLottery_name(LotteryTools.getLotteryName(Integer.parseInt(this.getLotteryId())));
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
		//写日志,写日志失败不抛出异常
		try{
			Map<String, String> param = new HashMap<String, String>();
			param.put("userId", adminUser.getUserId()+"");
			param.put("userName", adminUser.getName());
			param.put("userKey", ServletActionContext.getRequest().getRemoteAddr());
			param.put("keyword1", this.getLotteryId());
			param.put("keyword2", this.getTermNo());
			if(null!=this.getLimitNumber_before()||!"".equals(this.getLimitNumber_before())){
				param.put("keyword3", this.getLimitNumber_before());
			}
			param.put("keyword4",total_limit);
			
			if(SUCCESS.equals(dealResult)){
				OperatorLogger.log(40003, param);
			}else if(ERROR.equals(dealResult)){
				param.put("errorMessage", LotteryTools.getLotteryName(Integer.parseInt(this.getLotteryId()))+this.getEx_reason());
				OperatorLogger.log(41003, param);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dealResult;
	}
	
	private String convertParam(String param){
		return "0".equals(param) ? null : param;
	}
	/**
	 * 
	 * Title: mergeLimitPageData<br>
	 * Description: <br>
	 *              <br>将界面输入的限号信息合并为数据库定义的格式
	 * @param lotteryId
	 * @return
	 */
	private String mergeLimitPageData(String lotteryId){
		String result = null;
		Map<String,String> limitMap = new TreeMap<String,String>();
		if("1000003".equals(lotteryId)){
			if(StringUtils.isNotEmpty(this.getLimitNumber1())){
				limitMap.put("1", this.getLimitNumber1());
				
			}
			result = StringUtils.isNotEmpty(this.getLimitNumber1())?this.getLimitNumber1():null;
		}else if("1200001".equals(lotteryId)){
			if(StringUtils.isNotEmpty(this.getLimitNumber1())){
				limitMap.put("1", this.getLimitNumber1());
			}
			if(StringUtils.isNotEmpty(this.getLimitNumber2())){
				limitMap.put("2", this.getLimitNumber2());
			}
			if(StringUtils.isNotEmpty(this.getLimitNumber3())){
				limitMap.put("3", this.getLimitNumber3());
			}
			if(StringUtils.isNotEmpty(this.getLimitNumber4())){
				limitMap.put("4", this.getLimitNumber4());
			}
			if(StringUtils.isNotEmpty(this.getLimitNumber5())){
				limitMap.put("5", this.getLimitNumber5());
			}
			if(StringUtils.isNotEmpty(this.getLimitNumber6())){
				limitMap.put("6", this.getLimitNumber6());
			}
			if(StringUtils.isNotEmpty(this.getLimitNumber7())){
				limitMap.put("7", this.getLimitNumber7());
			}
		}
		result = LotteryTools.mergeLimitResult(Integer.parseInt(lotteryId), limitMap);
		return result;
	}


	public int getEx_code() {
		return this.ex_code;
	}


	public void setEx_code(int ex_code) {
		this.ex_code = ex_code;
	}


	public String getEx_reason() {
		return this.ex_reason;
	}


	public void setEx_reason(String ex_reason) {
		this.ex_reason = ex_reason;
	}


	public String getLotteryId() {
		return this.lotteryId;
	}


	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	public String getTermNo() {
		return this.termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	
	public String getLottery_name() {
		return this.lottery_name;
	}
	public void setLottery_name(String lottery_name) {
		this.lottery_name = lottery_name;
	}
	
	public LotteryTermModel getTermModel() {
		return this.termModel;
	}
	public void setTermModel(LotteryTermModel termModel) {
		this.termModel = termModel;
	}
	public String getLimitNumber_before() {
		return this.limitNumber_before;
	}
	public void setLimitNumber_before(String limitNumber_before) {
		this.limitNumber_before = limitNumber_before;
	}
	public Map getSession() {
		return session;
	}
	public void setSession(Map session) {
		this.session = session;
	}
	public Map<Integer, String> getLotteryList() {
		return lotteryList;
	}
	public void setLotteryList(Map<Integer, String> lotteryList) {
		this.lotteryList = lotteryList;
	}
	public List<LotteryTermModel> getTermLimitInfos() {
		return termLimitInfos;
	}
	public void setTermLimitInfos(List<LotteryTermModel> termLimitInfos) {
		this.termLimitInfos = termLimitInfos;
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
	public String getP_lotteryId() {
		return p_lotteryId;
	}
	public void setP_lotteryId(String id) {
		p_lotteryId = id;
	}
	public String getLimitNumber1() {
		return limitNumber1;
	}
	public void setLimitNumber1(String limitNumber1) {
		this.limitNumber1 = limitNumber1;
	}
	public String getLimitNumber2() {
		return limitNumber2;
	}
	public void setLimitNumber2(String limitNumber2) {
		this.limitNumber2 = limitNumber2;
	}
	public String getLimitNumber3() {
		return limitNumber3;
	}
	public void setLimitNumber3(String limitNumber3) {
		this.limitNumber3 = limitNumber3;
	}
	public String getLimitNumber4() {
		return limitNumber4;
	}
	public void setLimitNumber4(String limitNumber4) {
		this.limitNumber4 = limitNumber4;
	}
	public String getLimitNumber5() {
		return limitNumber5;
	}
	public void setLimitNumber5(String limitNumber5) {
		this.limitNumber5 = limitNumber5;
	}
	public String getLimitNumber6() {
		return limitNumber6;
	}
	public void setLimitNumber6(String limitNumber6) {
		this.limitNumber6 = limitNumber6;
	}
	public String getLimitNumber7() {
		return limitNumber7;
	}
	public void setLimitNumber7(String limitNumber7) {
		this.limitNumber7 = limitNumber7;
	}
	
	
	

}
