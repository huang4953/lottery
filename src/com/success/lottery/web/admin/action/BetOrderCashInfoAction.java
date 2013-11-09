/**
 * Title: BetOrderInfoAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-25 ����11:13:34
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.success.admin.user.domain.AdminUser;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.impl.CashPrizeService;
import com.success.lottery.business.service.interf.CashPrizeInterf;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.business.service.interf.PrizeInnerInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.web.admin.webbean.CashResult;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.action
 * BetOrderInfoAction.java
 * BetOrderInfoAction
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-25 ����11:13:34
 * 
 */

public class BetOrderCashInfoAction extends ActionSupport implements SessionAware{
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = 6529769304632462766L;

	private Map						session;
	
	private int ex_code;
	private String ex_reason;
	/*
	 * �����ʼ������
	 */
	private Map<Integer, String> lotteryList;
	/*
	 * ����ش�����
	 */
	private String p_lotteryId;
	private String p_termNo_begin = "0";
	
	
	/*
	 * ��ҳ����
	 */
	private String query;
	
	//ÿһҳ�ļ�¼��
	private int					perPageNumber;
	//�ڼ�ҳ
	private int					page;
	
	private PageList 				orderList = new PageList();

	//��ҳ����ʹ��static�������ڷ�ҳʱ���²�ѯ�����������������⣬����ͬʱ���ı���ҳ��
	//Ŀǰÿ�ζ���ѯ��������
	//�ɿ��ǵĽ��������
	//1-����������session�� 2-�����ݿ�DAO���ж�����������ϴ���ͬ�������ݿ��ѯֱ�ӷ���
	private int totalNumber;
	
	private static int defaultPerPageNumber = 20;
	
	/*
	 * �ҽ����ҳ����Ҫ�ṩ�Ĳ���
	 */
	
	private String lotteryName;
	
	private String jsonString;
	
	private String cashSession = "";
	
	
	
	
	
	/*
	 * ���Բ���
	 */
	private List<BusBetOrderDomain> orderLists;
	
	/**
	 * 
	 * Title: betOrderInfoShow<br>
	 * Description: <br>
	 *            (������һ�仰�����������������)<br>
	 * @return String
	 */
	public String betOrderCashInfoShow(){
		
		try {
			this.initPageParam();
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			List<BusBetOrderDomain> retOrderList = null;
			if (this.page == 0) {
				this.page = 1;
			}
			if (this.perPageNumber == 0) {
				this.perPageNumber = defaultPerPageNumber;
			}
			this.totalNumber = termManager.getCanCashOrderInfosCount(this.getP_lotteryId(), this.getP_termNo_begin());
			retOrderList = termManager.getCanCashOrderInfoS(this.getP_lotteryId(), this.getP_termNo_begin(), this.page, this.perPageNumber);
			
			this.orderList.setPageNumber(this.page);
			this.orderList.setPerPage(this.perPageNumber);
			this.orderList.setFullListSize(this.totalNumber);
			this.orderList.setList(retOrderList);
		} catch(LotteryException e){
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
	
	public String getNotCompleteCashOrder(){
		int notCompleteOrder = 0;
		try{
			BetPlanOrderServiceInterf orderServer = ApplicationContextUtils.getService("lotteryBetOrderService", BetPlanOrderServiceInterf.class);
			List<Integer> winStatus = new ArrayList<Integer>();
			List<Integer>  orderStatus = new ArrayList<Integer>();
			orderStatus.add(0);
			orderStatus.add(1);
			orderStatus.add(2);
			orderStatus.add(3);
			
			notCompleteOrder = orderServer.queryOrderNumByStatus(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), orderStatus, winStatus);// ���Ƿ�����Ҫȷ�ϳ�Ʊ����Ķ���
		}catch(Exception e){
			e.printStackTrace();
		}
		this.jsonString  = JSONArray.fromObject(notCompleteOrder).toString();
		return "ajaxjson";
	}
	
	@SuppressWarnings("unchecked")
	public String betOrderWait(){
		this.setLotteryName(LotteryTools.getLotteryName(Integer.parseInt(this.getP_lotteryId())));
		
		return SUCCESS;
	}
	/**
	 * 
	 * Title: betOrderAllCash<br>
	 * Description: <br>
	 *              <br>ȫ�������ҽ�����
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String betOrderAllCash(){
		CashResult cashResultBean = new CashResult();
		try{
			HttpServletRequest request = ServletActionContext. getRequest();
			String loginIp = "";
			try{
				loginIp = getIpAddr(request);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			Map<String,String> cashNum = new HashMap<String,String>();
			Map<String,String> dbLog = new HashMap<String,String>();
			AdminUser adminUser = (AdminUser) this.getSession().get("tlt.loginuser");
			dbLog.put("userId", String.valueOf(adminUser.getUserId()));
			dbLog.put("userName", String.valueOf(adminUser.getName()));
			dbLog.put("userKey", loginIp);
			
			cashNum.put("cashTotalNum", "0");//�ҽ�������
			cashNum.put("cashCurNum", "0");//��ǰ�Ѿ��ҽ�����
			cashNum.put("cashPersent", "0");//��ǰ�Ѿ��ҽ�����
			this.getSession().put("tlt.cashNum."+this.getP_lotteryId(), cashNum);
			
			if (StringUtils.isEmpty(this.getP_lotteryId())
					|| StringUtils.isEmpty(this.getP_termNo_begin())
					|| "0".equals(this.getP_lotteryId())
					|| "0".equals(this.getP_termNo_begin())) {
				throw new LotteryException(999999,"�ҽ�ʱ���ֺͲ��ڲ���Ϊ�գ�");
			}
			
			
			cashResultBean.setLotteryName(LotteryTools.getLotteryName(Integer.parseInt(this.getP_lotteryId())));
			/*
			 * ���öҽ������ҽ�
			 */
			CashPrizeInterf cashManager = ApplicationContextUtils.getService("busLotteryCashPrizeService",CashPrizeInterf.class);
			//�ҽ�
			Map<String,String> cashResult = cashManager.cashAutoOrder(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(),cashNum,dbLog);
			/*
			 * ͳ�ƶҽ����
			 */
			if(cashResult != null){
				cashResultBean.setTotal_orders(cashResult.get("total_orders"));
				cashResultBean.setTotal_tz_prize(cashResult.get("total_tz_prize"));
				cashResultBean.setSucess_orders(cashResult.get("sucess_orders"));
				cashResultBean.setSucess_tz_prize(cashResult.get("sucess_tz_prize"));
				cashResultBean.setFail_orders(cashResult.get("fail_orders"));
				cashResultBean.setFail_tz_prize(cashResult.get("fail_tz_prize"));
				cashResultBean.setZj_orders(cashResult.get("zj_orders"));
				cashResultBean.setZj_prize(cashResult.get("zj_prize"));
			}
			//����׷��
			Map<String,String> zhuHaoResult = cashManager.dealNotTicketOrder(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), dbLog);
			/*
			 * ͳ��׷�Ž��
			 */
			if(zhuHaoResult != null){
				cashResultBean.setZh_nextTerm(zhuHaoResult.get("nextTerm"));
				cashResultBean.setZh_total_num(zhuHaoResult.get("total_num"));
				cashResultBean.setZh_sucess_num(zhuHaoResult.get("sucess_num"));
				cashResultBean.setZh_fail_num(zhuHaoResult.get("fail_num"));
				cashResultBean.setZh_sucess_bet_num(zhuHaoResult.get("sucess_bet_num"));
				cashResultBean.setZh_sucess_limit_num(zhuHaoResult.get("sucess_limit_num"));
			}
			//���²���״̬
			Map<String,String> upTermResult = cashManager.updateCashTermStatus(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), dbLog);
			if(upTermResult != null){
				 cashResultBean.setOld_term_status(upTermResult.get("old_status"));
				 cashResultBean.setNew_term_status(upTermResult.get("new_status"));
			}
		}catch(LotteryException e){
			e.printStackTrace();
			cashResultBean.setEx_code(e.getType());
			cashResultBean.setEx_reason(e.getMessage()); 
		}catch(Exception ex){
			ex.printStackTrace();
			cashResultBean.setEx_code(99999);
			cashResultBean.setEx_reason(ex.getMessage());
		}
		this.jsonString = JSONObject.fromObject(cashResultBean).toString();
		return "ajaxjson";
	}
	
	@SuppressWarnings("unchecked")
	public String cashProgress(){
		try{
			this.setCashSession("tlt.cashNum."+this.getP_lotteryId());
			Map<String,Integer> cashNum = (Map)this.getSession().get(this.getCashSession());
			this.jsonString = JSONObject.fromObject(cashNum).toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ajaxjson";
	}
	
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
	/**
	 * 
	 * Title: initPageParam<br>
	 * Description: <br>
	 *              <br>��ʼ����ҳ������
	 */
	private void initPageParam(){
		this.setLotteryList(LotteryStaticDefine.getLotteryList());
	}
	
	public Map<Integer, String> getLotteryList() {
		return this.lotteryList;
	}

	public void setLotteryList(Map<Integer, String> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public String getP_lotteryId() {
		return this.p_lotteryId;
	}

	public void setP_lotteryId(String id) {
		this.p_lotteryId = id;
	}

	public String getP_termNo_begin() {
		return this.p_termNo_begin;
	}

	public void setP_termNo_begin(String no_begin) {
		this.p_termNo_begin = no_begin;
	}
	
	public int getPage() {
		return this.page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPageNumber() {
		return this.perPageNumber;
	}
	public void setPerPageNumber(int perPageNumber) {
		this.perPageNumber = perPageNumber;
	}
	public String getQuery() {
		return this.query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getTotalNumber() {
		return this.totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
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

	public List<BusBetOrderDomain> getOrderLists() {
		return this.orderLists;
	}

	public void setOrderLists(List<BusBetOrderDomain> orderLists) {
		this.orderLists = orderLists;
	}

	public Map getSession(){
		return this.session;
	}

	public void setSession(Map session){
		this.session = session;
	}

	public PageList getOrderList() {
		return this.orderList;
	}

	public void setOrderList(PageList orderList) {
		this.orderList = orderList;
	}

	

	public String getLotteryName() {
		return this.lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getCashSession() {
		return cashSession;
	}

	public void setCashSession(String cashSession) {
		this.cashSession = cashSession;
	}

}
