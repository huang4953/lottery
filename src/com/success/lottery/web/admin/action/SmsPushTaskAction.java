package com.success.lottery.web.admin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.success.admin.user.domain.AdminUser;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.sms.push.model.SmsPushData;
import com.success.lottery.sms.push.model.SmsPushTask;
import com.success.lottery.sms.push.service.SmsPushDataService;
import com.success.lottery.sms.push.service.SmsPushTaskService;
import com.success.lottery.util.LotteryTools;
import com.success.lottery.web.admin.webbean.PageList;
import com.success.utils.ApplicationContextUtils;

public class SmsPushTaskAction extends ActionSupport implements SessionAware {
	private String taskId;
	private long taskStatus;
	private String productId;
	private String serviceId;
	private Date beginTime;
	private Date endTime;
	private Map<Long,String> taskStatusList;
	private Integer lotteryId = 0;
	private String termNo;
	private String p_termNo_begin = "0";
	private String p_termNo_end = "0";
	private String kjgg_str;
	private String zjtz_str;
	private List<String> arr;
	private List<String> zjzt_list;
	private String kjgg_stime;
	private String zjtz_stime;
	private int usercount;
	private Map<Integer, String> lotteryList;
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
    //进入生成短信数据页面
	public String smsindex(){
		SmsPushTaskService smsPushTaskService = ApplicationContextUtils.getService("smsPushTaskService", SmsPushTaskService.class);
		try {
			Map<String,Object> map=smsPushTaskService.selectindex();
			this.setArr((List<String>)map.get("arr"));
			this.setKjgg_stime((String)map.get("ktime"));
			this.setZjtz_stime((String)map.get("ztime"));
			this.setUsercount((Integer)map.get("usercount"));
			
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	//访问页面时的查询
	public String queryAllSmsPushTask() {
		try{
			//下拉框获得彩种
			this.setLotteryList(LotteryStaticDefine.getLotteryList());
			//下拉框获得任务状态
			this.setTaskStatusList(LotteryStaticDefine.taskStautsForSms);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		SmsPushTaskService smsPushTaskService = ApplicationContextUtils.getService("smsPushTaskService", SmsPushTaskService.class);
		try{
			List<SmsPushTask> list = null;
			if(perPageNumber==0){
				perPageNumber = 30;
			}
			if(page == 0){
				//第一次查询设置页数我为1
				page = 1;
			}
			totalNumber = smsPushTaskService.getSmsPushTaskListCount(taskId, taskStatus,
					productId, serviceId, beginTime, endTime, lotteryId, p_termNo_begin,p_termNo_end);
			if(totalNumber<perPageNumber&&totalNumber!=0){
				perPageNumber = totalNumber;
			}
			list = smsPushTaskService.getSmsPushTaskList(taskId, taskStatus, productId,
					serviceId, beginTime, endTime, lotteryId, p_termNo_begin,p_termNo_end, page, perPageNumber);
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

	//查看群发数据列表
	public String  showSmsList(){
		SmsPushTask smsPushTask = null;
		SmsPushTaskService smsPushTaskService = ApplicationContextUtils.getService("smsPushTaskService", SmsPushTaskService.class);
		try {
			smsPushTask = smsPushTaskService.getSmsPushTask(taskId);
			ActionContext aContext = ActionContext.getContext();
			ValueStack valueStack = aContext.getValueStack();
			String lotteryName = null;
			if(null!=smsPushTask.getLotteryId()){
				lotteryName = LotteryTools.getLotteryName(smsPushTask.getLotteryId());
			}
			String taskStatus_str = LotteryStaticDefine.taskStautsForSms.get(smsPushTask.getTaskStatus());
			String beginTime_str  = null;
			String endTime_str = null;
			String taskTime_str  = null;
			String executionTime_str = null;
			if(null!=smsPushTask.getBeginTime()){
				beginTime_str = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getBeginTime());
			}
			if(null!=smsPushTask.getEndTime()){
				endTime_str = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getEndTime());
			}
			if(null!=smsPushTask.getTaskTime()){
				taskTime_str = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getBeginTime());
			}
			if(null!=smsPushTask.getExecutionTime()){
				executionTime_str = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getExecutionTime());
			}
			valueStack.set("smsPushTask", smsPushTask);
			valueStack.set("lotteryName", lotteryName);
			valueStack.set("taskStatus_str", taskStatus_str);
			valueStack.set("beginTime_str", beginTime_str);
			valueStack.set("taskTime_str", taskTime_str);
			valueStack.set("executionTime_str", executionTime_str);
			valueStack.set("endTime_str", endTime_str);
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		
		SmsPushDataService smsPushDataService = ApplicationContextUtils.getService("smsPushDataService", SmsPushDataService.class);
		try{
			List<SmsPushData> list = null;
			if(perPageNumber==0){
				perPageNumber = 30;
			}
			if(page == 0){
				//第一次查询设置页数我为1
				page = 1;
			}
			totalNumber = smsPushDataService.getSmsPushTaskDataCount(taskId);
			if(totalNumber<perPageNumber&&totalNumber!=0){
				perPageNumber = totalNumber;
			}
			list = smsPushDataService.getSmsPushTaskData(taskId, page, perPageNumber);
			termList.setPageNumber(page);
			termList.setPerPage(perPageNumber);
			termList.setFullListSize(totalNumber);
			termList.setList(list);
		}catch(LotteryException e){
			e.printStackTrace();
		}
		return "success";
	}

	//明细查询
	public String showSmsPushTaskDetail() {
		SmsPushTask smsPushTask = null;
		try {
			SmsPushTaskService smsPushTaskService = ApplicationContextUtils.getService("smsPushTaskService", SmsPushTaskService.class);
			smsPushTask = smsPushTaskService.getSmsPushTask(taskId);
			ActionContext aContext = ActionContext.getContext();
			ValueStack valueStack = aContext.getValueStack();
			String lotteryName = null;
			if(null!=smsPushTask.getLotteryId()){
				lotteryName = LotteryTools.getLotteryName(smsPushTask.getLotteryId());
			}
			String taskStatus_str = LotteryStaticDefine.taskStautsForSms.get(smsPushTask.getTaskStatus());
			String beginTime_str  = null;
			String endTime_str = null;
			String taskTime_str  = null;
			String executionTime_str = null;
			if(null!=smsPushTask.getBeginTime()){
				beginTime_str = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getBeginTime());
			}
			if(null!=smsPushTask.getEndTime()){
				endTime_str = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getEndTime());
			}
			if(null!=smsPushTask.getTaskTime()){
				taskTime_str = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getBeginTime());
			}
			if(null!=smsPushTask.getExecutionTime()){
				executionTime_str = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(smsPushTask.getExecutionTime());
			}
			valueStack.set("smsPushTask", smsPushTask);
			valueStack.set("lotteryName", lotteryName);
			valueStack.set("taskStatus_str", taskStatus_str);
			valueStack.set("beginTime_str", beginTime_str);
			valueStack.set("endTime_str", endTime_str);
			valueStack.set("taskTime_str", taskTime_str);
			valueStack.set("executionTime_str", executionTime_str);
		} catch (LotteryException e) {
			e.printStackTrace();
		}
		return "success";
	}
	/**
	 * 添加开奖公告数据
	 */
    public String  sendKJGG(){
    	SmsPushTaskService smsPushTaskService = ApplicationContextUtils.getService("smsPushTaskService", SmsPushTaskService.class);
    	Map<String, Object> map;
    	//取的后台用户
    	AdminUser adminUser=(AdminUser)ServletActionContext.getRequest().getSession().getAttribute("tlt.loginuser");
		try {
			map = smsPushTaskService.selectindex();
			this.setArr((List<String>)map.get("arr"));
			this.setUsercount((Integer)map.get("usercount"));
			if(this.getKjgg_str().equals("1")&&this.getZjtz_str().endsWith("0")){
				this.setKjgg_str(smsPushTaskService.insertSmsPush(adminUser.getUserId()+"",adminUser.getLoginName(),adminUser.getLastLoginIp()));
				this.setZjzt_list(null);
			}
			else if(this.getKjgg_str().equals("0")&&this.getZjtz_str().endsWith("1")){
				this.setZjzt_list(smsPushTaskService.insertSmsPushAll(adminUser.getUserId()+"",adminUser.getLoginName(),adminUser.getLastLoginIp()));
				this.setKjgg_str(null);
			}
			else{
				this.setKjgg_str(smsPushTaskService.insertSmsPush(adminUser.getUserId()+"",adminUser.getLoginName(),adminUser.getLastLoginIp()));
				this.setZjzt_list(smsPushTaskService.insertSmsPushAll(adminUser.getUserId()+"",adminUser.getLoginName(),adminUser.getLastLoginIp()));
			}
			
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "success";
    }
	public PageList getTermList() {
		return termList;
	}


	public void setTermList(PageList termList) {
		this.termList = termList;
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


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public long getTaskStatus() {
		return taskStatus;
	}


	public void setTaskStatus(long taskStatus) {
		this.taskStatus = taskStatus;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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


	public Integer getLotteryId() {
		return lotteryId;
	}


	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}



	public int getTotalNumber() {
		return totalNumber;
	}


	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
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


	public Map<Long, String> getTaskStatusList() {
		return taskStatusList;
	}


	public void setTaskStatusList(Map<Long, String> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}


	public Map<Integer, String> getLotteryList() {
		return lotteryList;
	}


	public void setLotteryList(Map<Integer, String> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public String getTermNo() {
		return termNo;
	}


	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}


	public List<String> getArr() {
		return arr;
	}
	public void setArr(List<String> arr) {
		this.arr = arr;
	}
	public String getKjgg_stime() {
		return kjgg_stime;
	}
	public void setKjgg_stime(String kjgg_stime) {
		this.kjgg_stime = kjgg_stime;
	}
	public String getZjtz_stime() {
		return zjtz_stime;
	}
	public void setZjtz_stime(String zjtz_stime) {
		this.zjtz_stime = zjtz_stime;
	}
	public int getUsercount() {
		return usercount;
	}
	public void setUsercount(int usercount) {
		this.usercount = usercount;
	}
	public String getKjgg_str() {
		return kjgg_str;
	}
	public void setKjgg_str(String kjgg_str) {
		this.kjgg_str = kjgg_str;
	}
	public String getZjtz_str() {
		return zjtz_str;
	}
	public void setZjtz_str(String zjtz_str) {
		this.zjtz_str = zjtz_str;
	}
	public List<String> getZjzt_list() {
		return zjzt_list;
	}
	public void setZjzt_list(List<String> zjzt_list) {
		this.zjzt_list = zjzt_list;
	}

}
