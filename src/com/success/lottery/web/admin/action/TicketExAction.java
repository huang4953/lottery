/**
 * Title: TicketExAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-8-5 下午12:48:21
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.domain.BusBetOrderDomain;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.business.service.interf.TicketExDealInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.action
 * TicketExAction.java
 * TicketExAction
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-8-5 下午12:48:21
 * 
 */

public class TicketExAction extends ActionSupport implements SessionAware {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 266990467248154281L;
	private Map session;
	
	private String ticketSequencecs;
	private String ticketStatus;
	private String dealMark;
	
	private BetTicketDomain ticketDomain;
	
	private int ex_code;
	private String ex_reason;
	private String show_error = "SUCESS";
	/**
	 * 
	 * Title: getTicketInfo<br>
	 * Description: <br>
	 *              <br>查新彩票
	 * @return
	 */
	public String getTicketExInfo(){
		try {
			BetTicketServiceInterf ticketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
			this.setTicketDomain(ticketService.getTicket(this.getTicketSequencecs()));
		} catch(LotteryException e){
			e.printStackTrace();
			this.setShow_error("ERROR");
			this.setEx_code(e.getType());
			this.setEx_reason(e.getMessage());
			return ERROR; 
		}catch(Exception ex){
			ex.printStackTrace();
			this.setShow_error("ERROR");
			this.setEx_code(99999);
			this.setEx_reason(ex.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * Title: ticketExDeal<br>
	 * Description: <br>
	 *              <br>更新彩票
	 * @throws IOException
	 */
	public void ticketExDeal(){
		
		String result = "处理成功";
		try{
			TicketExDealInterf ticketExService = ApplicationContextUtils.getService("busLotteryTicketExService", TicketExDealInterf.class);
			ticketExService.ticketExDeal(this.getTicketSequencecs(), Integer.parseInt(this.getTicketStatus()), "", "","", "",  URLDecoder.decode(this.getDealMark(),"UTF-8"), "");
		}catch(LotteryException e){
			e.printStackTrace();
			result = e.getMessage();
		}catch(Exception ex){
			ex.printStackTrace();
			result = "处理失败";
		}
		PrintWriter out = null;
		try {
			ServletActionContext.getResponse().setContentType( "text/html;charset=GBK "); 
			out = ServletActionContext.getResponse().getWriter();
			out.print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(out != null){
					out.close();
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
	}
	

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public String getTicketSequencecs() {
		return ticketSequencecs;
	}

	public void setTicketSequencecs(String ticketSequencecs) {
		this.ticketSequencecs = ticketSequencecs;
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


	public BetTicketDomain getTicketDomain() {
		return ticketDomain;
	}


	public void setTicketDomain(BetTicketDomain ticketDomain) {
		this.ticketDomain = ticketDomain;
	}

	public String getDealMark() {
		return dealMark;
	}

	public void setDealMark(String dealMark) {
		this.dealMark = dealMark;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getShow_error() {
		return show_error;
	}

	public void setShow_error(String show_error) {
		this.show_error = show_error;
	}
	

}
