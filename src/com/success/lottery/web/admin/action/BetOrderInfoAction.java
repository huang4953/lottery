/**
 * Title: BetOrderInfoAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-25 ����11:13:34
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.exception.LotteryException;
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

public class BetOrderInfoAction extends ActionSupport implements SessionAware{
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = -2053599861967409948L;
	
	private Map						session;
	
	private int ex_code;
	private String ex_reason;
	/*
	 * �����ʼ������
	 */
	private Map<String,String> planSource;
	
	private Map<String,String> orderStatus;
	
	private Map<String,String> orderWinStatus;
	
	private Map<Integer, String> lotteryList;
	/*
	 * ����ش�����
	 */
	private String accountId;
	private String user_name;
	private String planSourceId;
	private String orderStatusId;
	private String orderWinStatusId;
	private String p_lotteryId;
	private String p_termNo_begin = "0";
	private String begin_date;
	private String end_date;
	private String wincode;
	private String orderid;
	/*
	 * ��ҳ����
	 */
	private String query;
	
	//ÿһҳ�ļ�¼��
	private int					perPageNumber;
	//�ڼ�ҳ
	private int					page;
	
	private PageList 				orderList = new PageList();
	//private PageList 				orderList;

	//��ҳ����ʹ��static�������ڷ�ҳʱ���²�ѯ�����������������⣬����ͬʱ���ı���ҳ��
	//Ŀǰÿ�ζ���ѯ��������
	//�ɿ��ǵĽ��������
	//1-����������session�� 2-�����ݿ�DAO���ж�����������ϴ���ͬ�������ݿ��ѯֱ�ӷ���
	private int totalNumber;
	
	private static int defaultPerPageNumber = 20;
	
	
	
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
	public String betOrderInfoShow(){
		
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
			this.totalNumber = termManager.getbetOrderInfosCount(this
					.getAccountId(), this.getUser_name(), this
					.getP_lotteryId(), this.getP_termNo_begin(), this
					.getPlanSourceId(), this.getOrderStatusId(), this
					.getOrderWinStatusId(), this.getBegin_date(), this
					.getEnd_date());
			retOrderList = termManager.getbetOrderInfoS(this
					.getAccountId(), this.getUser_name(), this
					.getP_lotteryId(), this.getP_termNo_begin(), this
					.getPlanSourceId(), this.getOrderStatusId(), this
					.getOrderWinStatusId(), this.getBegin_date(), this
					.getEnd_date(), this.page, this.perPageNumber);
			/*
			if(this.getQuery() != null){
				System.out.println("*******************0001******************");
				//�����ѯ��ť�ˣ����½��в�ѯ��������ҳ��
				this.totalNumber = termManager.getbetOrderInfosCount(this
						.getAccountId(), this.getUser_name(), this
						.getP_lotteryId(), this.getP_termNo_begin(), this
						.getPlanSourceId(), this.getOrderStatusId(), this
						.getOrderWinStatusId(), this.getBegin_date(), this
						.getEnd_date());
				this.page = 1;
				System.out.println("************perPageNumber****************"+this.perPageNumber);
				retOrderList = termManager.getbetOrderInfoS(this
						.getAccountId(), this.getUser_name(), this
						.getP_lotteryId(), this.getP_termNo_begin(), this
						.getPlanSourceId(), this.getOrderStatusId(), this
						.getOrderWinStatusId(), this.getBegin_date(), this
						.getEnd_date(), this.page, this.perPageNumber);
			} else {
				
					if (this.page == 0) {
						System.out.println("*******************0002******************");
					// ��һ�β�ѯ����
					this.totalNumber = termManager.getbetOrderInfosCount(this
							.getAccountId(), this.getUser_name(), this
							.getP_lotteryId(), this.getP_termNo_begin(), this
							.getPlanSourceId(), this.getOrderStatusId(), this
							.getOrderWinStatusId(), this.getBegin_date(), this
							.getEnd_date());
					this.page = 1;
					if (this.perPageNumber == 0) {
						this.perPageNumber = defaultPerPageNumber;
					}
					retOrderList = termManager.getbetOrderInfoS(this
							.getAccountId(), this.getUser_name(), this
							.getP_lotteryId(), this.getP_termNo_begin(), this
							.getPlanSourceId(), this.getOrderStatusId(), this
							.getOrderWinStatusId(), this.getBegin_date(), this
							.getEnd_date(), this.page, this.perPageNumber);
				} else {
					System.out.println("*******************0003******************");
					// ��ѯ���к����˷�ҳ
					if (this.perPageNumber == 0) {
						this.perPageNumber = defaultPerPageNumber;
					}
					this.totalNumber = termManager.getbetOrderInfosCount(this
							.getAccountId(), this.getUser_name(), this
							.getP_lotteryId(), this.getP_termNo_begin(), this
							.getPlanSourceId(), this.getOrderStatusId(), this
							.getOrderWinStatusId(), this.getBegin_date(), this
							.getEnd_date());
					retOrderList = termManager.getbetOrderInfoS(this
							.getAccountId(), this.getUser_name(), this
							.getP_lotteryId(), this.getP_termNo_begin(), this
							.getPlanSourceId(), this.getOrderStatusId(), this
							.getOrderWinStatusId(), this.getBegin_date(), this
							.getEnd_date(), this.page, this.perPageNumber);
				}
			}
			*/
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
	

	/**
	 * 
	 * Title: updateOrderInfoShow<br>
	 * Description: <br>
	 *            ��ѯ��Ʊʧ�ܵĶ���<br>
	 * @return String
	 */
	public String updateOrderInfoShow(){
		
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
			this.totalNumber = termManager.getbetOrderInfosCount(this
					.getAccountId(), this.getUser_name(), this
					.getP_lotteryId(), this.getP_termNo_begin(), this
					.getPlanSourceId(), "6", this
					.getOrderWinStatusId(), this.getBegin_date(), this
					.getEnd_date());
			retOrderList = termManager.getbetOrderInfoS(this
					.getAccountId(), this.getUser_name(), this
					.getP_lotteryId(), this.getP_termNo_begin(), this
					.getPlanSourceId(), "6", this
					.getOrderWinStatusId(), this.getBegin_date(), this
					.getEnd_date(), this.page, this.perPageNumber);
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
	/**
	 * �����Ʊ���󶩵�
	 * @throws IOException
	 */
	public void updateFailOrder() throws IOException{
		LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
		String flg="����ʧ��";
		try{
			int i=termManager.inputUpdatefailOrder(this.getOrderid(), 99,URLDecoder.decode(this.getWincode(),"UTF-8"));
			if(i!=0)
			flg="����ɹ�";
		
		}catch(LotteryException e){
			e.printStackTrace();
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
		}catch(Exception ex){
			ex.printStackTrace();
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
		}
		ServletActionContext.getResponse().setContentType( "text/html;charset=GBK "); 
		PrintWriter out=ServletActionContext.getResponse().getWriter();
		out.print(flg);
		out.close();
	}
	/**
	 * 
	 * Title: initPageParam<br>
	 * Description: <br>
	 *              <br>��ʼ����ҳ������
	 */
	private void initPageParam(){
		this.setPlanSource(LotteryStaticDefine.planSource);
		this.setOrderStatus(LotteryStaticDefine.orderStatus);
		this.setOrderWinStatus(LotteryStaticDefine.orderWinStatus);
		this.setLotteryList(LotteryStaticDefine.getLotteryList());
	}

	public Map<String, String> getPlanSource() {
		return this.planSource;
	}

	public void setPlanSource(Map<String, String> planSource) {
		this.planSource = planSource;
	}

	public String getPlanSourceId() {
		return planSourceId;
	}

	public void setPlanSourceId(String planSourceId) {
		this.planSourceId = planSourceId;
	}

	public Map<String, String> getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Map<String, String> orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(String orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public Map<String, String> getOrderWinStatus() {
		return orderWinStatus;
	}

	public void setOrderWinStatus(Map<String, String> orderWinStatus) {
		this.orderWinStatus = orderWinStatus;
	}

	public String getOrderWinStatusId() {
		return orderWinStatusId;
	}

	public void setOrderWinStatusId(String orderWinStatusId) {
		this.orderWinStatusId = orderWinStatusId;
	}

	public Map<Integer, String> getLotteryList() {
		return lotteryList;
	}

	public void setLotteryList(Map<Integer, String> lotteryList) {
		this.lotteryList = lotteryList;
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
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPerPageNumber() {
		return perPageNumber;
	}
	public void setPerPageNumber(int perPageNumber) {
		this.perPageNumber = perPageNumber;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
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

	public List<BusBetOrderDomain> getOrderLists() {
		return orderLists;
	}

	public void setOrderLists(List<BusBetOrderDomain> orderLists) {
		this.orderLists = orderLists;
	}

	public Map getSession(){
		return session;
	}

	public void setSession(Map session){
		this.session = session;
	}

	public PageList getOrderList() {
		return orderList;
	}

	public void setOrderList(PageList orderList) {
		this.orderList = orderList;
	}


	public String getWincode() {
		return wincode;
	}


	public void setWincode(String wincode) {
		this.wincode = wincode;
	}


	public String getOrderid() {
		return orderid;
	}


	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

}
