package com.success.lottery.web.admin.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import com.success.admin.user.domain.AdminUser;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.ticket.domain.BetTicketDomain;
import com.success.lottery.ticket.domain.WinOrderTicketDomain;
import com.success.lottery.ticket.service.interf.BetSplitterService;
import com.success.lottery.ticket.service.interf.BetTicketServiceInterf;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

public class TicketAction extends ActionSupport implements SessionAware{

	private 		Log logger = LogFactory.getLog(TicketAction.class.getName());
	private Map	<Object, Object>					session;
	private String					p_lotteryId	= "0";
	private String					p_termNo	= "";
	private Map<Integer, String>	lotteryList;
	private Map<Integer, String>	ticketStatusList;
	private String					query;
	private String					filesubmit;
	// 每一页的记录数
	private int						perPageNumber;
	// 第几页
	private int						page;
	private PageList				ticketList	= new PageList();
	private PageList				fileList = new PageList();
	private PageList				winTicketList = new PageList();
	
	private int						totalNumber;

	private String jsonString;
	
	private String fileName;
	
	private String ticketSequence;
	
	private InputStream inputStream;

	private int p_ticketStatus = -1;
	
	private List<String> ticketSequenceList;
	private int confirmFlag;
	private String confirmMsg;

	private String orderId;
	
	
	public String ticketCreate(){
		try{
			this.setLotteryList(LotteryStaticDefine.getLotteryList());
		}catch(Exception ex){
			ex.printStackTrace();
		}

		BetTicketServiceInterf ticketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		logger.debug("ticketCreate query, lotteryId=" + this.getP_lotteryId() + ", termNo=" + this.getP_termNo());
		try{
			List<BetTicketDomain> list = null;
			if(query != null){
				//点击查询按钮了，重新进行查询，设置总页数
				totalNumber = ticketService.getCreateFileTicketesCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo());
				page = 1;
				list = ticketService.getCreateFileTicketes(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), page, perPageNumber);
			} else {
				if(page == 0){
					page = 1;
				}
				if(perPageNumber == 0){
					perPageNumber = 30;
				}
				totalNumber = ticketService.getCreateFileTicketesCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo());
				list = ticketService.getCreateFileTicketes(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), page, perPageNumber);
			}
			ticketList.setPageNumber(page);
			ticketList.setPerPage(perPageNumber);
			ticketList.setFullListSize(totalNumber);
			ticketList.setList(list);
		}catch(LotteryException e){
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public String createFile(){
//		try{
//			this.setLotteryList(LotteryStaticDefine.getLotteryList());
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
		
		BetSplitterService ticketService = ApplicationContextUtils.getService("qhtcBetSplitterService", BetSplitterService.class);
		logger.debug("createFile query, lotteryId=" + this.getP_lotteryId() + ", termNo=" + this.getP_termNo());
		String rs = null;
		try{
			AdminUser adminUser = (AdminUser)this.session.get("tlt.loginuser");
			rs = ticketService.createPrintFile(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), adminUser.getLoginName());
		}catch(LotteryException e){
			e.printStackTrace();
			rs = e.toString();
		}
		if(rs == null){
			this.jsonString = "{\"msg\" : \"ok\"}";
		} else {
			this.jsonString = "{\"msg\" : \"" + rs + "\"}";
		}
		return "ajaxjson";
	}

	public String ticketFileDownload(){
		try{
			this.setLotteryList(LotteryStaticDefine.getLotteryList());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		BetTicketServiceInterf ticketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		logger.debug("ticketFileDownload query, lotteryId=" + this.getP_lotteryId() + ", termNo=" + this.getP_termNo());
		try{
			List<BetTicketDomain> list = null;
			if(query != null){
				//点击查询按钮了，重新进行查询，设置总页数
				totalNumber = ticketService.getTicketFilesCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo());
				page = 1;
				list = ticketService.getTicketFiles(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), page, perPageNumber);
			} else {
				if(page == 0){
					page = 1;
				}
				if(perPageNumber == 0){
					perPageNumber = 30;
				}
				totalNumber = ticketService.getTicketFilesCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo());
				list = ticketService.getTicketFiles(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), page, perPageNumber);
			}
			fileList.setPageNumber(page);
			fileList.setPerPage(perPageNumber);
			fileList.setFullListSize(totalNumber);
			fileList.setList(list);
		}catch(LotteryException e){
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public String download(){
		try{
			BetTicketServiceInterf ticketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
			BetSplitterService qhtcService = ApplicationContextUtils.getService("qhtcBetSplitterService", BetSplitterService.class);
	
			String ticketData = ticketService.getTicket(ticketSequence).getTicketData();
			try{
				this.setFileName(new String(ticketData.getBytes(), "ISO8859-1"));
			}catch(UnsupportedEncodingException e){
				this.setFileName(ticketData);
			}
			this.setInputStream(qhtcService.getPrintFileInputStream(ticketData));
			return "success";
		}catch(LotteryException e){
			e.printStackTrace();
			return "failed";
		}		
	}
	
	public String ticketConfirm(){
		try{
			this.setLotteryList(LotteryStaticDefine.getLotteryList());
			this.setTicketStatusList(LotteryStaticDefine.getTicketStatusList());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		BetTicketServiceInterf ticketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		logger.debug("ticketConfirm query, lotteryId=" + this.getP_lotteryId() + ", termNo=" + this.getP_termNo() + ", ticketStatus=" + p_ticketStatus);
		try{
			List<BetTicketDomain> list = null;
			if(query != null){
				//点击查询按钮了，重新进行查询，设置总页数
				if(p_ticketStatus == 0){
					p_ticketStatus = -1;
				}
				totalNumber = ticketService.getConfirmTicketesCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), p_ticketStatus);
				page = 1;
				list = ticketService.getConfirmTicketes(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), p_ticketStatus, page, perPageNumber);
			} else {
				if(page == 0){
					page = 1;
				}
				if(perPageNumber == 0){
					perPageNumber = 30;
				}
				if(p_ticketStatus == 0){
					p_ticketStatus = -1;
				}
				totalNumber = ticketService.getConfirmTicketesCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), p_ticketStatus);
				list = ticketService.getConfirmTicketes(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), p_ticketStatus, page, perPageNumber);
			}
			ticketList.setPageNumber(page);
			ticketList.setPerPage(perPageNumber);
			ticketList.setFullListSize(totalNumber);
			ticketList.setList(list);
		}catch(LotteryException e){
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}

	public String confirmTicket() throws UnsupportedEncodingException{
		int rc = -1;
		String rs = null;
		this.setConfirmMsg(URLDecoder.decode(this.getConfirmMsg(), "UTF-8"));
		BetSplitterService ticketService = ApplicationContextUtils.getService("qhtcBetSplitterService", BetSplitterService.class);
		try{
			AdminUser adminUser = (AdminUser)this.session.get("tlt.loginuser");
			rc = ticketService.confirmTicket(this.getTicketSequenceList(), this.getConfirmFlag(), this.getConfirmMsg(), adminUser.getLoginName());
		}catch(LotteryException e){
			rs = e.toString();
			logger.debug(e);
		}
		
		if(rs == null){
			this.jsonString = "{\"msg\" : \"ok\", \"count\" : \"" + rc + "\", \"data\" : \"" + this.getConfirmMsg() + "\"}";
		} else {
			this.jsonString = "{\"msg\" : \"" + rs + "\"}";
		}
		return "ajaxjson";
	}

	public String ticketWinQuery(){
		try{
			this.setLotteryList(LotteryStaticDefine.getLotteryList());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		BetTicketServiceInterf ticketService = ApplicationContextUtils.getService("lotteryBetTicketService", BetTicketServiceInterf.class);
		logger.debug("ticketWinQuery query, lotteryId=" + this.getP_lotteryId() + ", termNo=" + this.getP_termNo() + ", orderId=" + this.getOrderId());
		try{
			List<WinOrderTicketDomain> list = null;
			if(query != null){
				//点击查询按钮了，重新进行查询，设置总页数
				if(orderId == null || "".equals(orderId.trim())){
					orderId = null;
				}
				totalNumber = ticketService.getWinTicketesCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), this.getOrderId());
				page = 1;
				list = ticketService.getWinTicketes(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), this.getOrderId(), page, perPageNumber);
			} else {
				if(page == 0){
					page = 1;
				}
				if(perPageNumber == 0){
					perPageNumber = 30;
				}
				if(orderId == null || "".equals(orderId.trim())){
					orderId = null;
				}
				totalNumber = ticketService.getWinTicketesCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), this.getOrderId());
				list = ticketService.getWinTicketes(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo(), this.getOrderId(), page, perPageNumber);
			}
			winTicketList.setPageNumber(page);
			winTicketList.setPerPage(perPageNumber);
			winTicketList.setFullListSize(totalNumber);
			winTicketList.setList(list);
		}catch(LotteryException e){
			logger.debug(e);
			return "failed";
		}
		return "success";
	}
	
	public Map<Object, Object> getSession(){
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

	
	public String getP_termNo(){
		return p_termNo;
	}

	
	public void setP_termNo(String pTermNo){
		p_termNo = pTermNo;
	}

	
	public Map<Integer, String> getLotteryList(){
		return lotteryList;
	}

	
	public void setLotteryList(Map<Integer, String> lotteryList){
		this.lotteryList = lotteryList;
	}

	
	public String getQuery(){
		return query;
	}

	
	public void setQuery(String query){
		this.query = query;
	}

	
	public String getFilesubmit(){
		return filesubmit;
	}

	
	public void setFilesubmit(String filesubmit){
		this.filesubmit = filesubmit;
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

	
	public PageList getTicketList(){
		return ticketList;
	}

	
	public void setTicketList(PageList ticketList){
		this.ticketList = ticketList;
	}

	
	public int getTotalNumber(){
		return totalNumber;
	}

	
	public void setTotalNumber(int totalNumber){
		this.totalNumber = totalNumber;
	}

	
	public String getJsonString(){
		return jsonString;
	}

	
	public void setJsonString(String jsonString){
		this.jsonString = jsonString;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	
	public String getTicketSequence(){
		return ticketSequence;
	}

	
	public void setTicketSequence(String ticketSequence){
		this.ticketSequence = ticketSequence;
	}

	
	public InputStream getInputStream(){
		return inputStream;
	}

	
	public void setInputStream(InputStream inputStream){
		this.inputStream = inputStream;
	}

	
	
	
	public PageList getFileList(){
		return fileList;
	}

	
	public void setFileList(PageList fileList){
		this.fileList = fileList;
	}

	
	public int getP_ticketStatus(){
		return p_ticketStatus;
	}

	
	public void setP_ticketStatus(int pTicketStatus){
		p_ticketStatus = pTicketStatus;
	}

	
	public Map<Integer, String> getTicketStatusList(){
		return ticketStatusList;
	}

	
	public void setTicketStatusList(Map<Integer, String> ticketStatusList){
		this.ticketStatusList = ticketStatusList;
	}

	
	
	
	public List<String> getTicketSequenceList(){
		return ticketSequenceList;
	}

	
	public void setTicketSequenceList(List<String> ticketSequenceList){
		this.ticketSequenceList = ticketSequenceList;
	}

	
	public int getConfirmFlag(){
		return confirmFlag;
	}

	
	public void setConfirmFlag(int confirmFlag){
		this.confirmFlag = confirmFlag;
	}

	
	public String getConfirmMsg(){
		return confirmMsg;
	}

	
	public void setConfirmMsg(String confirmMsg){
		this.confirmMsg = confirmMsg;
	}

	
	
	
	public String getOrderId(){
		return orderId;
	}

	
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	
	
	
	public PageList getWinTicketList(){
		return winTicketList;
	}

	
	public void setWinTicketList(PageList winTicketList){
		this.winTicketList = winTicketList;
	}

	public static void main(String[] args){
		List<String> list = new ArrayList<String>();
		list.add("111");
		list.add("222");
		list.add("333");
		list.add("444");
		list.add("555");
		list.add("666");
		System.out.println(list.get(0));
		list.add(0, "444");
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i));
		}
	}
}
