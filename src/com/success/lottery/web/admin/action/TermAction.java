package com.success.lottery.web.admin.action;

import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

public class TermAction extends ActionSupport implements SessionAware{

	private Map						session;
	private String p_lotteryId = "0";
	private String lottery_name;
	private String p_termNo_begin = "0";
	private String p_termNo_end = "0";
	private Map<Integer, String> lotteryList;

	private String query;
	
	//每一页的记录数
	private int					perPageNumber;
	//第几页
	private int					page;
	
	private PageList 				termList = new PageList();

	//总页数，使用static，避免在分页时重新查询总条数，还是有问题，多人同时查会改变总页数
	//目前每次都查询总条数；
	//可考虑的解决方案：
	//1-总条数放入session； 2-在数据库DAO中判断条件如果和上次相同则不做数据库查询直接返回
	private int totalNumber;
	
	public Map getSession(){
		return session;
	}

	public void setSession(Map session){
		this.session = session;
	}

	public String getP_lotteryId(){
		return p_lotteryId;
	}

	public void setP_lotteryId(String pLotteryId){
		p_lotteryId = pLotteryId;
	}

	
	public String getLottery_name(){
		return lottery_name;
	}

	
	public void setLottery_name(String lotteryName){
		lottery_name = lotteryName;
	}

	public String getP_termNo_begin(){
		return p_termNo_begin;
	}

	public void setP_termNo_begin(String pTermNoBegin){
		p_termNo_begin = pTermNoBegin;
	}

	public String getP_termNo_end(){
		return p_termNo_end;
	}

	public void setP_termNo_end(String pTermNoEnd){
		p_termNo_end = pTermNoEnd;
	}

	public Map<Integer, String> getLotteryList(){
		return lotteryList;
	}

	public void setLotteryList(Map<Integer, String> lotteryList){
		this.lotteryList = lotteryList;
	}

	public int getPerPageNumber(){
		return perPageNumber;
	}

	public void setPerPageNumber(int perPageNumber){
		this.perPageNumber = perPageNumber;
	}

	
	
	
	public int getPage(){
		return page;
	}

	
	public void setPage(int page){
		this.page = page;
	}
	
	public PageList getTermList(){
		return termList;
	}

	
	public void setTermList(PageList termList){
		this.termList = termList;
	}

	
	public String getQuery(){
		return query;
	}

	
	public void setQuery(String query){
		this.query = query;
	}

	// 访问页面时
	public String queryAllTerm(){
		try{
			//LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			//this.setLotteryList(termManager.getLotteryList());
			this.setLotteryList(LotteryStaticDefine.getLotteryList());
		}catch(Exception ex){
			ex.printStackTrace();
		}

		LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		try{
			List<LotteryTermModel> list = null;
			if(query != null){
				//点击查询按钮了，重新进行查询，设置总页数
				totalNumber = termService.getTermInfoCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), this.getP_termNo_end());
				page = 1;
				list = termService.gettermInfo(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), this.getP_termNo_end(), page, perPageNumber);
			} else {
				if("0".equals(this.getP_lotteryId())){
					if(page == 0){
						//第一次查询所有
						totalNumber = termService.getTermInfoCount(-1, null, null);
						page = 1;
						if(perPageNumber == 0){
							perPageNumber = 100;
						}
						list = termService.gettermInfo(-1, null, null, page, perPageNumber);
					}else{
						// 查询所有后点击了分页
						if(perPageNumber == 0){
							perPageNumber = 100;
						}
						totalNumber = termService.getTermInfoCount(-1, null, null);
						list = termService.gettermInfo(-1, null, null, page, perPageNumber);
					}
				} else {
					totalNumber = termService.getTermInfoCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), this.getP_termNo_end());
					list = termService.gettermInfo(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), this.getP_termNo_end(), page, perPageNumber);
				}
			}
			termList.setPageNumber(page);
			termList.setPerPage(perPageNumber);
			termList.setFullListSize(totalNumber);
			termList.setList(list);
		}catch(LotteryException e){
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
	}
}
