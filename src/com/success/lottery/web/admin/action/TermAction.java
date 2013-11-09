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
	
	//ÿһҳ�ļ�¼��
	private int					perPageNumber;
	//�ڼ�ҳ
	private int					page;
	
	private PageList 				termList = new PageList();

	//��ҳ����ʹ��static�������ڷ�ҳʱ���²�ѯ�����������������⣬����ͬʱ���ı���ҳ��
	//Ŀǰÿ�ζ���ѯ��������
	//�ɿ��ǵĽ��������
	//1-����������session�� 2-�����ݿ�DAO���ж�����������ϴ���ͬ�������ݿ��ѯֱ�ӷ���
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

	// ����ҳ��ʱ
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
				//�����ѯ��ť�ˣ����½��в�ѯ��������ҳ��
				totalNumber = termService.getTermInfoCount(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), this.getP_termNo_end());
				page = 1;
				list = termService.gettermInfo(Integer.parseInt(this.getP_lotteryId()), this.getP_termNo_begin(), this.getP_termNo_end(), page, perPageNumber);
			} else {
				if("0".equals(this.getP_lotteryId())){
					if(page == 0){
						//��һ�β�ѯ����
						totalNumber = termService.getTermInfoCount(-1, null, null);
						page = 1;
						if(perPageNumber == 0){
							perPageNumber = 100;
						}
						list = termService.gettermInfo(-1, null, null, page, perPageNumber);
					}else{
						// ��ѯ���к����˷�ҳ
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
