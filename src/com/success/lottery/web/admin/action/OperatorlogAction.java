package com.success.lottery.web.admin.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.operatorlog.domain.OperationLog;
import com.success.lottery.operatorlog.service.interf.OperatorLogService;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

@SuppressWarnings("serial")
public class OperatorlogAction extends ActionSupport implements SessionAware{
	 private Integer level;
     private Integer type;
     private Integer rank;
     private Long userid;
     private String name;
     private Date beginTime;
     private Date endTime;
     private Map<Integer, String> levelStatusList;
     private Map<Integer, String> typeStatusList;
     private Map<Integer, String> rankStatusList;
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
	@Override
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}

	public String queryAllOperatorlog(){
		try{
			//下拉框获得日志级别
			this.setLevelStatusList(LotteryStaticDefine.operatorlogLevelStatus);
			//下拉框获得日志类别
			this.setTypeStatusList(LotteryStaticDefine.operatorlogTypeStatus);
			//下拉框获得日志等级
			this.setRankStatusList(LotteryStaticDefine.operatorlogRankStatus);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		OperatorLogService operationLogService = ApplicationContextUtils.getService("operatorLogService", OperatorLogService.class);
		try{
			List<OperationLog> list = null;
			if(perPageNumber==0){
				perPageNumber = 30;
			}
			if(page == 0){
				//第一次查询设置页数我为1
				page = 1;
			}
			totalNumber = operationLogService.getAllOperationLogCount(level, type, rank,  userid, name,  null, null, null, null, null, null,beginTime,endTime);
			if(totalNumber<perPageNumber&&totalNumber!=0){
				perPageNumber = totalNumber;
			}
			list = operationLogService.getAllOperationLogList(level, type, rank,  userid, name,  null, null, null, null, null, null,beginTime,endTime, page, perPageNumber);
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Map<Integer, String> getLevelStatusList() {
		return levelStatusList;
	}

	public void setLevelStatusList(Map<Integer, String> levelStatusList) {
		this.levelStatusList = levelStatusList;
	}

	public Map<Integer, String> getTypeStatusList() {
		return typeStatusList;
	}

	public void setTypeStatusList(Map<Integer, String> typeStatusList) {
		this.typeStatusList = typeStatusList;
	}

	public Map<Integer, String> getRankStatusList() {
		return rankStatusList;
	}

	public void setRankStatusList(Map<Integer, String> rankStatusList) {
		this.rankStatusList = rankStatusList;
	}

	public int getPerPageNumber() {
		return perPageNumber;
	}

	public void setPerPageNumber(int perPageNumber) {
		this.perPageNumber = perPageNumber;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageList getTermList() {
		return termList;
	}

	public void setTermList(PageList termList) {
		this.termList = termList;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
}
