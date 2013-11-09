package com.success.lottery.sms.push.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.account.dao.UserAccountDAO;
import com.success.lottery.account.model.UserAccountModel;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.dao.impl.BetOrderDaoImpl;
import com.success.lottery.order.domain.BetOrderDomain;
import com.success.lottery.sms.push.dao.SmsPushDataDao;
import com.success.lottery.sms.push.dao.SmsPushTaskDao;
import com.success.lottery.sms.push.model.SmsPushData;
import com.success.lottery.sms.push.model.SmsPushTask;
import com.success.lottery.sms.push.service.SmsPushDataService;
import com.success.lottery.sms.push.service.SmsPushTaskService;
import com.success.lottery.term.dao.impl.LotteryTermDaoImpl;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.util.LotterySequence;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.ssmp.SSMPLoader;

public class SmsPushTaskServiceImpl implements SmsPushTaskService{
	private Log logger = LogFactory.getLog(SmsPushTaskServiceImpl.class.getName());
	private SmsPushTaskDao smsPushTaskDao;
	private SmsPushDataDao smsPushDataDao;
	private LotteryTermDaoImpl lotteryTermDao;
	private UserAccountDAO userAccountDAO;
    private BetOrderDaoImpl betOrderDaoImpl;
	private SmsPushDataService smsPushDataService;
	private String resource = "com.success.lottery.sms.sms";

	public SmsPushDataService getSmsPushDataService() {
		return smsPushDataService;
	}

	public void setSmsPushDataService(SmsPushDataService smsPushDataService) {
		this.smsPushDataService = smsPushDataService;
	}

	
	public BetOrderDaoImpl getBetOrderDaoImpl() {
		return betOrderDaoImpl;
	}
	public void setBetOrderDaoImpl(BetOrderDaoImpl betOrderDaoImpl) {
		this.betOrderDaoImpl = betOrderDaoImpl;
	}

	public UserAccountDAO getUserAccountDAO() {
		return userAccountDAO;
	}

	public void setUserAccountDAO(UserAccountDAO userAccountDAO) {
		this.userAccountDAO = userAccountDAO;
	}

	public LotteryTermDaoImpl getLotteryTermDao() {
		return lotteryTermDao;
	}

	public void setLotteryTermDao(LotteryTermDaoImpl lotteryTermDao) {
		this.lotteryTermDao = lotteryTermDao;
	}

	public SmsPushDataDao getSmsPushDataDao() {
		return smsPushDataDao;
	}

	public void setSmsPushDataDao(SmsPushDataDao smsPushDataDao) {
		this.smsPushDataDao = smsPushDataDao;
	}

	public SmsPushTaskDao getSmsPushTaskDao() {
		return smsPushTaskDao;
	}

	public void setSmsPushTaskDao(SmsPushTaskDao smsPushTaskDao) {
		this.smsPushTaskDao = smsPushTaskDao;
	}

	public void insertSmsPushTask(SmsPushTask smsPushTask)
			throws LotteryException {
		this.smsPushTaskDao.insertSmsPushTask(smsPushTask);
	}
	
	@Override
	public String insertSmsPush(String userId,String userName,String ip)throws LotteryException{
		Calendar cal=Calendar.getInstance();
		SmsPushTask smspushtask=new SmsPushTask(); 
		String contextflg="";//返回失败信息
		String sendSummary="";//短信摘要
		String contexttrue="";//返回成功信息
		String stime="",etime="";//开始时间，过期时间
		int count=0;//标识短信群发数据量
		int errorcount=0;//记录错误条数
		String context="体彩最新开奖信息！";//短信内容
		//获得当期的日期，格式 %yyyy-MM-dd%
		String winLine="%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+"%";
		//获得开奖彩期集合	
		List<LotteryTermModel> list=this.getLotteryTermDao().queryHaveWinTermInfobytime(winLine);
		if(list==null||list.size()==0)
		    return "没有开奖短信数据需要添加！";
		Map<String, Object> parameter=new HashMap<String, Object>();
		for (LotteryTermModel lotteryTermModel : list) {
			//判断是否开奖
			if(lotteryTermModel.getWinStatus()==1)
			{
				if(lotteryTermModel.getLotteryId()==1000001)
					contextflg+="超级大乐透第"+lotteryTermModel.getTermNo()+"期尚未开奖，";
				if(lotteryTermModel.getLotteryId()==1000002)
					contextflg+="七星彩第"+lotteryTermModel.getTermNo()+"期尚未开奖，";
				if(lotteryTermModel.getLotteryId()==1000003)
					contextflg+="排列三第"+lotteryTermModel.getTermNo()+"期尚未开奖，";
				if(lotteryTermModel.getLotteryId()==1000004)
					contextflg+="排列五第"+lotteryTermModel.getTermNo()+"期尚未开奖，";
				if(lotteryTermModel.getLotteryId()==1000005)
					contextflg+="生肖乐第"+lotteryTermModel.getTermNo()+"期尚未开奖，";
				if(lotteryTermModel.getLotteryId()==1300001)
					contextflg+="胜负彩第"+lotteryTermModel.getTermNo()+"期尚未开奖，";
				if(lotteryTermModel.getLotteryId()==1300002)
					contextflg+="任选九第"+lotteryTermModel.getTermNo()+"期尚未开奖，";
			}
			//设置发送内容
			if(lotteryTermModel.getLotteryId()==1000001)
			{
				context+="超级大乐透第"+lotteryTermModel.getTermNo()+"期的开奖号码"+lotteryTermModel.getLotteryResult()+"，";
				sendSummary+="超级大乐透第"+lotteryTermModel.getTermNo()+"期、";
			}
			if(lotteryTermModel.getLotteryId()==1000002)
			{
				context+="七星彩第"+lotteryTermModel.getTermNo()+"期的开奖号码"+lotteryTermModel.getLotteryResult()+"，";
				sendSummary+="七星彩第"+lotteryTermModel.getTermNo()+"期、";
			}
			if(lotteryTermModel.getLotteryId()==1000003)
			{
				context+="排列三第"+lotteryTermModel.getTermNo()+"期的开奖号码"+lotteryTermModel.getLotteryResult()+"，";
				sendSummary+="排列三第"+lotteryTermModel.getTermNo()+"期、";
			}
			if(lotteryTermModel.getLotteryId()==1000004)
			{
				context+="排列五第"+lotteryTermModel.getTermNo()+"期的开奖号码"+lotteryTermModel.getLotteryResult()+"，";
				sendSummary+="排列五第"+lotteryTermModel.getTermNo()+"期、";
			}
			if(lotteryTermModel.getLotteryId()==1000005)
			{
				context+="生肖乐第"+lotteryTermModel.getTermNo()+"期的开奖号码"+lotteryTermModel.getLotteryResult()+"，";
				sendSummary+="生肖乐第"+lotteryTermModel.getTermNo()+"期、";
			}
			if(lotteryTermModel.getLotteryId()==1300001)
			{
				context+="胜负彩第"+lotteryTermModel.getTermNo()+"期的开奖号码"+lotteryTermModel.getLotteryResult()+"，";
				sendSummary+="胜负彩第"+lotteryTermModel.getTermNo()+"期、";
			}
			if(lotteryTermModel.getLotteryId()==1300002)
			{
				context+="任选九第"+lotteryTermModel.getTermNo()+"期的开奖号码"+lotteryTermModel.getLotteryResult()+"，";
				sendSummary+="任选九第"+lotteryTermModel.getTermNo()+"期、";
			}
		}
		//如果有未开奖的彩期返回
		if(!"".equals(contextflg)){
			return contextflg.substring(0, contextflg.length()-1)+"。";
		}
		contexttrue=sendSummary.substring(0,sendSummary.length()-1)+"开奖公告数据已添加。";
		sendSummary=sendSummary.substring(0,sendSummary.length()-1)+"开奖公告";
		context=context.substring(0, context.length()-1)+"。更多信息请访问http://www.lottery360.cn。";
  	 //读取配置文件，得到任务信息
  	  	try {
  	  		smspushtask.setTaskStatus(Integer.parseInt(AutoProperties.getString("KJGG.taskStatus","0","com.success.lottery.sms.sms")));
  	  		stime=AutoProperties.getString("KJGG.beginTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+" 09:00:00","com.success.lottery.sms.sms");
  	  		etime=AutoProperties.getString("KJGG.endTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+" 16:00:00","com.success.lottery.sms.sms");
  	  		smspushtask.setProductId(AutoProperties.getString("KJGG.productId","A","com.success.lottery.sms.sms"));
  	  	} catch (MissingResourceException e) {
  	  		logger.error("insertSmsPush Error :", e);
  	  		throw new LotteryException(SMS01EXCEPTION,SMS01EXCEPTION_STR);
  	  	}
  	  	//处理时间
  	  	if(stime.length()==8)
  	  		stime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+stime;
  	  	else if(stime.length()==5)
  	  		stime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+stime+":00";
  	  	if(etime.length()==8)
  	  		etime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+etime;
  	  	else if(etime.length()==5)
  	  		etime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+etime+":00";
  	  	SimpleDateFormat smp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  	  	try {
  	  		smspushtask.setBeginTime(smp.parse(stime));
  	  		smspushtask.setEndTime(smp.parse(etime));
  	  	} catch (ParseException e) {
  		  logger.error("insertSmsPush Error :", e);
  		  throw new LotteryException(SMS02EXCEPTION,SMS02EXCEPTION_STR);
  	  	}
  	  	smspushtask.setSendSummary(sendSummary);
  	  	smspushtask.setServiceId("KJGG");
  	  	smspushtask.setTaskStatus(0L);
  	  	smspushtask.setSendSummary("开奖公告");
  	  	parameter.put("beginTime", "%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+"%");
	  	parameter.put("serviceId","KJGG");
	  	SmsPushTask smsPushTask=this.smsPushTaskDao.querySmsPushTask(parameter);
	  	
	  	//查找需要要发送短信的用户
	  	List<UserAccountModel> userlist=this.getUserAccountDAO().getUserAccountInfoForbindMobileFlag();
	  	LotterySequence  sequence=LotterySequence.getInstatce("SPT");
	  	//获得TsakId
	  	String taskId="";
	  	if(null!=smsPushTask)
	  		taskId=smsPushTask.getTaskId();
	  	else
	  		taskId=sequence.getSequence();
	  	//添加开奖通知任务日志，任务开始
	  	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	  	String date=sf.format( new Date());
		Map<String, String> param=new HashMap<String, String>();
		param.put("keyword2", taskId);
		param.put("keyword1", date);
		param.put("userId",userId);
		param.put("userName",userName);
		param.put("userKey", ip);
		com.success.lottery.operatorlog.service.OperatorLogger.log(40020, param);
	  	smspushtask.setTaskId(taskId);
	  	System.out.println("======================");
	  	System.out.println("开始添加准备数据");
	  	System.out.println("======================");
    	  //添加群发数据
	  	try{	
	  		for (UserAccountModel userAccountModel : userlist) {
	  			SmsPushData smsPushData=new SmsPushData();
	  			smsPushData.setEndTime(smspushtask.getEndTime());
	  			smsPushData.setBeginTime(smspushtask.getBeginTime());
	  			smsPushData.setSendCount(0);
	  			smsPushData.setSendStatus(0);
	  			smsPushData.setServiceId("KJGG");
	  			smsPushData.setSendSummary(sendSummary);
	  			long id=this.getSmsPushDataDao().selectMixId()+1;
	  			smsPushData.setId(id);
	  			smsPushData.setMobilePhone(userAccountModel.getMobilePhone());
	  			smsPushData.setContent(context);
	  			smsPushData.setRemark("");
	  			smsPushData.setReserve("");
	  			smsPushData.setTaskId(taskId);
	  			try {
	  				if(null!=smsPushTask){ 
    				//查询前用户是否添加过
    				SmsPushData data=this.getSmsPushDataDao().selectSmsPushDataByTaskId(smsPushTask.getTaskId(),userAccountModel.getMobilePhone(), "KJGG");
    				if(null==data){
    					this.getSmsPushDataDao().insertSmsPushData(smsPushData);
    					count+=1;
    				}
	  				}else
    				{
    					this.getSmsPushDataDao().insertSmsPushData(smsPushData);
    					count+=1;
    				}
	  			}catch(LotteryException e){
	  				logger.error("insertSmsPush Error :", e);
	  				//单个添加失败
	  				param.put("keyword3", userAccountModel.getMobilePhone());
					param.put("keyword4",userAccountModel.getUserId()+"");
					//记录单个失败
					com.success.lottery.operatorlog.service.OperatorLogger.log(41020, param);
	  				errorcount+=1;
	  			}
	  		}
	  		//成功结束
		  	com.success.lottery.operatorlog.service.OperatorLogger.log(40021, param);
		  	smspushtask.setContent(context);
	  	}catch (Exception e) {
	  	//记录失败结束
	  		String errorMessage="";
	  		for (LotteryTermModel lotteryTermModel : list) {
	  			errorMessage+="彩种："+lotteryTermModel.getLotteryId()+"彩期："+lotteryTermModel.getTermNo();
	  		}
			param.put("errorMessage",errorMessage);
			com.success.lottery.operatorlog.service.OperatorLogger.log(41021, param); 
		}
    	//添加任务
	  	Map<String, String> pa=new HashMap<String, String>();
		pa.put("keyword2", taskId);
		pa.put("keyword1", date);
		pa.put("userId", userId);
		pa.put("userName",userName);
		pa.put("userKey", ip);
	  	try{
	  		if(null==smsPushTask){
	  			this.getSmsPushTaskDao().insertSmsPushTask(smspushtask);
	  			pa.put("message", contexttrue+"共添加："+count+"条数据，添加失败"+errorcount+"条。");
				//关联成功
				com.success.lottery.operatorlog.service.OperatorLogger.log(40022, pa);
	  		}
		}catch (Exception e) {
			//关联失败
			pa.put("errorMessage","中奖通知任务生成失败时间："+date);
			com.success.lottery.operatorlog.service.OperatorLogger.log(41022, pa);
		}
	  	return contexttrue+"共添加："+count+"条数据，添加失败"+errorcount+"条。";
	}
	public void test() throws LotteryException{
		Calendar cal=Calendar.getInstance();
		List<String> arr=new ArrayList<String>();
		String Str_zjgg=this.insertSmsPush("aaa","bbb","sss");
		arr.add(Str_zjgg);
		String context="体彩最新开奖信息！";//短信内容
		//获得当期的日期，格式 %yyyy-MM-dd%
		String winLine="%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+"%";
		//获得开奖彩期集合	
		List<LotteryTermModel> list=this.getLotteryTermDao().queryHaveWinTermInfobytime(winLine);
		for (LotteryTermModel lotteryTermModel : list) {
			arr.add(this.insertSmsPushZJGG(lotteryTermModel.getLotteryId(),lotteryTermModel.getTermNo(),"aaa","bbb","IP"));
		}
		for (String s : arr) {
			System.out.println(s);
		}
	}
	@Override
	public String insertSmsPushZJGG(int lotteryId,String termNo,String userId,String userName,String ip) throws LotteryException {
		

		// TODO Auto-generated method stub
		String returnStr="中奖通知数据已经生成，共生成";//返回数据
		int count=0;
		int errorcount=0;
		Calendar cal=Calendar.getInstance();//日期
		String stime="",etime="",lotteryname="";//开始时间，过期时间
		SmsPushTask smspushTask=null;
		//根据彩中彩期查彩期对象
		LotteryTermModel lotteryModel=new LotteryTermModel();
		lotteryModel.setLotteryId(lotteryId);
		lotteryModel.setTermNo(termNo);
		lotteryModel=this.getLotteryTermDao().queryTermInfo(lotteryModel);
		//判断是否开奖
		if(lotteryModel.getLotteryId()==1000001)
			lotteryname=lotteryModel.getTermNo()+"期超级大乐透";
		if(lotteryModel.getLotteryId()==1000002)
			lotteryname=lotteryModel.getTermNo()+"期七星彩";
		if(lotteryModel.getLotteryId()==1000003)
			lotteryname=lotteryModel.getTermNo()+"期排列三";
		if(lotteryModel.getLotteryId()==1000004)
			lotteryname=lotteryModel.getTermNo()+"期排列五";	
		if(lotteryModel.getLotteryId()==1000005)
			lotteryname=lotteryModel.getTermNo()+"期生肖乐";
		if(lotteryModel.getLotteryId()==1300001)
		    lotteryname=lotteryModel.getTermNo()+"期胜负彩";
		if(lotteryModel.getLotteryId()==1300002)
			lotteryname=lotteryModel.getTermNo()+"期任选九";
		if(lotteryModel.getWinStatus()!=4&&lotteryModel.getWinStatus()!=7&&lotteryModel.getWinStatus()!=8)
			return lotteryname+"尚未兑奖！";
		//判断数据是否已经生成过
		Map<Object,Object> map=new HashMap<Object,Object>();
		map.put("lotteryId",lotteryId);
		map.put("lotteryTerm",termNo);
		map.put("serviceId","ZJTZ");
		smspushTask=this.getSmsPushTaskDao().querySmsPushTashByLottery(map);
		SmsPushTask pushTask=new SmsPushTask();
		//读取属性文件
		try {
			pushTask.setTaskStatus(Integer.parseInt(AutoProperties.getString("ZJTZ.taskStatus","0","com.success.lottery.sms.sms")));
			stime=AutoProperties.getString("ZJTZ.beginTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" 09:00:00","com.success.lottery.sms.sms");
	  	  	etime=AutoProperties.getString("ZJTZ.endTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" 16:00:00","com.success.lottery.sms.sms");
	  	  	pushTask.setProductId(AutoProperties.getString("ZJTZ.productId","B","com.success.lottery.sms.sms"));
	  	  } catch (MissingResourceException e) {
	  		 logger.error("insertSmsPush Error :", e);
			 throw new LotteryException(SMS01EXCEPTION,SMS01EXCEPTION_STR);
	  	  }
	  	 //处理时间
	  	  if(stime.length()==8)
	  		  stime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+stime;
	  	  else if(stime.length()==5)
	  		  stime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+stime+":00";
	  	  if(etime.length()==8)
	  		  etime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+etime;
	 	  else if(etime.length()==5)
	 		  etime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+etime+":00";
	  	  SimpleDateFormat smp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  	  try {
	  		pushTask.setBeginTime(smp.parse(stime));
	  		pushTask.setEndTime(smp.parse(etime));
	  	  } catch (ParseException e) {
			logger.error("insertSmsPush Error :", e);
			throw new LotteryException(SMS02EXCEPTION,SMS02EXCEPTION_STR);
	  	  }
		//根据彩种彩期查询订单列表
		List<BetOrderDomain> list=this.getBetOrderDaoImpl().getWinOrderList(map);
		//获得TsakId
		LotterySequence  sequence=LotterySequence.getInstatce("SPT");
  	  	String taskId="";
  		if(smspushTask==null)
  			taskId=sequence.getSequence();
  		else
  			taskId=smspushTask.getTaskId();
  		
  		//记录日志  中奖通知开始
		Map<String, String> param=new HashMap<String, String>();
		param.put("keyword3", taskId);
		param.put("keyword1",termNo);
		param.put("keyword2",lotteryId+"");
		param.put("userId",userId);
		param.put("userName",userName);
		param.put("userKey", ip);
		com.success.lottery.operatorlog.service.OperatorLogger.log(40023, param);
		try{
			for (BetOrderDomain betOrderDomain : list) {
			//根据用户ID查询用户
			UserAccountModel accountModel=this.getUserAccountDAO().selectUserByUserId(betOrderDomain.getUserId());
			//判断用户是否为有效和包月
			if(betOrderDomain.getWinStatus()==2||betOrderDomain.getWinStatus()==3 || betOrderDomain.getWinStatus()==299||betOrderDomain.getWinStatus()==399)
				if(accountModel.getStatus()==1&&accountModel.isBindMobileFlag()){
					//添加中将通知数据
					SmsPushData smsPushData=new SmsPushData();
					smsPushData.setEndTime(pushTask.getEndTime());
					smsPushData.setBeginTime(pushTask.getBeginTime());
					smsPushData.setSendCount(0);
					smsPushData.setSendStatus(0);
					smsPushData.setServiceId("ZJTZ");
					smsPushData.setMobilePhone(accountModel.getMobilePhone());
					long id=this.getSmsPushDataDao().selectMixId()+1;
					smsPushData.setId(id);
					smsPushData.setSendSummary(lotteryname+"中奖通知");
					smsPushData.setRemark("");
					smsPushData.setReserve("");
					smsPushData.setTaskId(taskId);
					java.text.NumberFormat nf=java.text.NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					double money=betOrderDomain.getPreTaxPrize()/100+betOrderDomain.getPreTaxPrize()%100*0.01;
					//手机彩票服务：恭喜您购买的（期数）期（玩法）中奖MM元！XXXXXX，可发送“TZ，订单编号”查询订单详情。
					String countext="手机彩票服务：恭喜您购买的"+lotteryname+"中奖"+nf.format(money)+"元!中奖订单的编号为"+betOrderDomain.getOrderId()+"，可发送“TZ，订单编号”查询订单详情。";
					smsPushData.setContent(countext);
					if(betOrderDomain.getWinStatus()==2 || betOrderDomain.getWinStatus()==299)
						countext+="本次中奖奖金将直接增加到您的手机投注帐户中。更多信息请拨打_4008096566或访问http://www.lottery360.cn。";
					else
						countext+="请携带您的身份证到省体彩管理中心手机投注办公室领奖！更多信息请拨打4008096566。";
					try {
						if(smspushTask!=null)
						{
							SmsPushData data=this.getSmsPushDataDao().selectSmsPushDataByTaskId(taskId, accountModel.getMobilePhone(), "ZJTZ");
							if(data==null){
								this.getSmsPushDataDao().insertSmsPushData(smsPushData);
								count+=1;
							}
						}
						else{
							this.getSmsPushDataDao().insertSmsPushData(smsPushData);
							count+=1;
						}
					}catch(LotteryException e){
						logger.error("insertSmsPush Error :", e);
						errorcount+=1;
						param.put("keyword3", betOrderDomain.getOrderId());
						param.put("keyword4",accountModel.getUserId()+"");
						//记录单个失败
						com.success.lottery.operatorlog.service.OperatorLogger.log(41023, param);
					}
					
						
				}
			}
		}catch (Exception e) {
			//记录失败结束
			param.put("errorMessage","彩种:"+lotteryId+"第："+termNo);
			com.success.lottery.operatorlog.service.OperatorLogger.log(41024, param);
		}
		pushTask.setTaskId(taskId);
		pushTask.setLotteryId(lotteryId);
		pushTask.setLotteryTerm(termNo);
		pushTask.setServiceId("ZJTZ");
		pushTask.setSendSummary(lotteryname+"中奖通知");
		pushTask.setRemark("");
		pushTask.setReserve("");
		pushTask.setContent(lotteryname+"中奖通知。更多信息请访问http://www.lottery360.cn。");
		//记录成功结束
		param.put("keyword3", taskId);
		com.success.lottery.operatorlog.service.OperatorLogger.log(40024, param);
		//添加中将通知任务
		Map<String, String> pa=new HashMap<String, String>();
		pa.put("keyword3", taskId);
		pa.put("keyword1",termNo);
		pa.put("keyword2",lotteryId+"");
		pa.put("userId",userId);
		pa.put("userName",userName);
		pa.put("userKey", ip);
		try{
			if(smspushTask==null){
				this.getSmsPushTaskDao().insertSmsPushTask(pushTask);
				logger.info("添加短信中奖通知任务成功！！！");
				pa.put("message", lotteryname+returnStr+count+"条数据,添加失败"+errorcount+"条。");
				//关联成功
				com.success.lottery.operatorlog.service.OperatorLogger.log(40025, pa);
			}
		}catch (Exception e) {
			//关联失败
			pa.put("errorMessage","彩种:"+lotteryId+"第："+termNo);
			com.success.lottery.operatorlog.service.OperatorLogger.log(41025, pa);
		}
		
		logger.info(lotteryname+returnStr+count+"条数据,添加失败"+errorcount+"条。");
		return lotteryname+returnStr+count+"条数据,添加失败"+errorcount+"条。";
	}
	@Override
	public List<String> insertSmsPushAll(String userId,String userName,String ip) throws LotteryException {
		List<String> returnlist=new ArrayList<String>();
		Calendar cal=Calendar.getInstance();
		//获得当期的日期，格式 %yyyy-MM-dd%
		String winLine="%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+"%";
		//获得开奖彩期集合	
		List<LotteryTermModel> list=this.getLotteryTermDao().queryHaveWinTermInfobytime(winLine);
		
		for (LotteryTermModel lotteryTermModel : list) {
			returnlist.add(this.insertSmsPushZJGG(lotteryTermModel.getLotteryId(),lotteryTermModel.getTermNo(),userId,userName,ip));
		}
		return returnlist;
	}
	 public static void main(String[] arge)
	    {
	     	SmsPushTaskService sms=ApplicationContextUtils.getService("smsPushTaskService", SmsPushTaskService.class);
	     		
	     				try {
	     					sms.insertSmsPush("aaa","bbb","ip");
				} catch (LotteryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				 TODO Auto-generated catch block
	    }
   @Override
	public SmsPushTask getSmsPushTask(String taskId) throws LotteryException {
		SmsPushTask smsPushTask = null;
		try {
			smsPushTask = this.getSmsPushTaskDao().selectSmsPushTask(taskId);
		} catch (Exception e) {
			throw new LotteryException(SmsPushTaskService.NOTSMSPUSHTASkPER,SmsPushTaskService.NOTSMSPUSHTASkPER_STR+e.toString());
		}
		return smsPushTask;
	}

	@Override
	public List<SmsPushTask> getSmsPushTaskList(String taskId, long taskStatus,
			String productId, String serviseId, Date beginTime,
			Date endTime, Integer lotteryId, String p_termNo_begin, String p_termNo_end,
			int pageIndex, int perPageNumber)throws LotteryException {
		List list = null;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			
			int startNumber = perPageNumber * (pageIndex - 1);
			int endNumber = pageIndex * perPageNumber;
			list = this.getSmsPushTaskDao().selectSmsPushTaskList( taskId,  taskStatus,
					 productId,  serviseId,  beginTimeCovent,
					 endTimeCovent,  lotteryId,  p_termNo_begin, p_termNo_end,  startNumber,
						 endNumber);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryException(SmsPushTaskService.NOTSMSPUSHTASk,SmsPushTaskService.NOTSMSPUSHTASk_STR+e.toString());
		}
		return list;
	}
	@Override
	public int getSmsPushTaskListCount(String taskId, long taskStatus,
			String productId, String serviseId, Date beginTime,
			Date endTime, Integer lotteryId, String p_termNo_begin, String p_termNo_end)
			throws LotteryException {
		int pageCount = 0;
		try {
			Timestamp beginTimeCovent = null;
			Timestamp endTimeCovent = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(beginTime != null){
				beginTimeCovent = Timestamp.valueOf(format.format(beginTime) + " 00:00:00");
			}
			if(endTime != null){
				endTimeCovent = Timestamp.valueOf(format.format(endTime) + " 23:59:59");
			}
			pageCount = this.getSmsPushTaskDao().selectSmsPushTaskListCount(taskId, taskStatus,
					productId, serviseId, beginTimeCovent, endTimeCovent, lotteryId,p_termNo_begin, p_termNo_end);
		} catch (Exception e) {
			throw new LotteryException(SmsPushTaskService.NOTSMSPUSHTASkCOUNT,SmsPushTaskService.NOTSMSPUSHTASkCOUNT_STR+e.toString());
		}
		return pageCount;
	}
	@Override
	public Map<String,Object> selectindex() throws LotteryException {
		List<String> arr=new ArrayList<String>();
		Map<String,Object> returnmap=new HashMap<String, Object>();
		//查询今天开奖的彩期
		Calendar cal=Calendar.getInstance();
		//获得当期的日期，格式 %yyyy-MM-dd%
		String winLine="%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+"%";
		//获得开奖彩期集合	
		List<LotteryTermModel> list=this.getLotteryTermDao().queryHaveWinTermInfobytime(winLine);
		if(list==null||list.size()==0)
			arr.add("今天没有需要开奖的彩种彩期");
		String lotteryname="";
		for (LotteryTermModel lotteryModel : list) {
			//判断是否开奖
			if(lotteryModel.getLotteryId()==1000001)
				lotteryname=lotteryModel.getTermNo()+"期超级大乐透";
			else if(lotteryModel.getLotteryId()==1000002)
				lotteryname=lotteryModel.getTermNo()+"期七星彩";
			else if(lotteryModel.getLotteryId()==1000003)
				lotteryname=lotteryModel.getTermNo()+"期排列三";
			else if(lotteryModel.getLotteryId()==1000004)
				lotteryname=lotteryModel.getTermNo()+"期排列五";	
			else if(lotteryModel.getLotteryId()==1000005)
				lotteryname=lotteryModel.getTermNo()+"期生肖乐";
			else if(lotteryModel.getLotteryId()==1300001)
			    lotteryname=lotteryModel.getTermNo()+"期胜负彩";
			else
				lotteryname=lotteryModel.getTermNo()+"期任选九";
			//判断是否开奖
			//今日hh时mm分开奖,目前尚未（或已经）开奖,目前尚未（或已经）兑奖
			lotteryname+=":今日"+lotteryModel.getWinLine2().getHours()+"时"+lotteryModel.getWinLine2().getMinutes()+"分开奖，";
			
			if(lotteryModel.getWinStatus()==2||lotteryModel.getWinStatus()==3)
				lotteryname+="目前尚已开奖,目前尚未兑奖";
			else if(lotteryModel.getWinStatus()==4||lotteryModel.getWinStatus()==7||lotteryModel.getWinStatus()==8)
			{	
				Map<Object,Object> map=new HashMap<Object,Object>();
				map.put("lotteryId",lotteryModel.getLotteryId());
				map.put("betTerm",lotteryModel.getTermNo());
				List betlist=this.getBetOrderDaoImpl().getWinOrderList(map);
				int ordercount=0;
				if(betlist!=null)
					ordercount=betlist.size();
				//根据彩中彩期查询中奖订单个数
				lotteryname+="目前已经开奖,目前已经兑奖,中奖订单"+ordercount+"条;";
			}else
			{
				lotteryname+="目前尚未开奖,目前尚未兑奖";
			}
			arr.add(lotteryname);
		}
		//找出今天不开奖的彩种
		List<Integer> tlist=new ArrayList<Integer>();
		tlist.add(1000001);
		tlist.add(1000002);
		tlist.add(1000003);
		tlist.add(1000004);
		tlist.add(1000005);
		tlist.add(1300001);
		tlist.add(1300002);
		int count=tlist.size();
		for (LotteryTermModel lotteryModel : list) {
		     for (Iterator it = tlist.iterator(); it.hasNext();) {
				if(lotteryModel.getLotteryId()==Integer.parseInt(it.next().toString()))
					it.remove();
			}
		}
		
		for (Integer termNo : tlist) {
			if(termNo==1000001)
				lotteryname="超级大乐透:该彩种今日不开奖！";
			else if(termNo==1000002)
				lotteryname="七星彩:该彩种今日不开奖！";
			else if(termNo==1000003)
				lotteryname="排列三:该彩种今日不开奖！";
			else if(termNo==1000004)
				lotteryname="排列五:该彩种今日不开奖！";	
			else if(termNo==1000005)
				lotteryname="生肖乐:该彩种今日不开奖！";
			else if(termNo==1300001)
			    lotteryname="胜负彩:该彩种今日不开奖！";
			else
				lotteryname="任选九:该彩种今日不开奖！";
			arr.add(lotteryname);
		}
		returnmap.put("arr",arr);
		//读取配置文件 找到开奖，中奖 通知发送时间
		String ktime=AutoProperties.getString("ZJGG.beginTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" 09:00:00","com.success.lottery.sms.sms");
		String ztime=AutoProperties.getString("ZJTZ.beginTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" 09:00:00","com.success.lottery.sms.sms");
		if(ktime.length()==8)
		    ktime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+ktime;
	  	else if(ktime.length()==5)
	  	    ktime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+ktime+":00";
		if(ztime.length()==8)
			ztime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+ztime;
	  	else if(ztime.length()==5)
	  		ztime=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" "+ztime+":00";
		returnmap.put("ztime", ztime);
		returnmap.put("ktime", ktime);
		//查询有效地包月用户个数
	  	List<UserAccountModel> userlist=this.getUserAccountDAO().getUserAccountInfoForbindMobileFlag();
		if(userlist!=null)
			returnmap.put("usercount", userlist.size());
		else 
			returnmap.put("usercount", 0);
	  	return returnmap;
	}

	@Override
	public int checkTimeoutTask() throws LotteryException {
		try{
			return this.getSmsPushTaskDao().updateTimeoutTask();
		}catch(Exception e){
			throw new LotteryException(SmsPushTaskService.CHECKTIMEOUTEXCEPTION, SmsPushTaskService.CHECKTIMEOUTEXCEPTION_STR + e.toString());
		}
	}

	@Override
	public String findTaskAndExecution() throws LotteryException {
		try{
			SmsPushTask task = this.getSmsPushTaskDao().getPushTaskForUpdate();
			if(task == null){
				return null;
			}else{
				this.getSmsPushTaskDao().updateTaskExecutionStat(task.getTaskId(), 1, true, null);
				//启动发送线程
				String startLine = "thread:PushDataSender-" + task.getTaskId() + ":com.success.lottery.sms.push.service.PushDataSender:" + task.getTaskId() + ":";
				logger.debug("create PushDataSender start line is:" + startLine);
				SSMPLoader.startLine(startLine);
				return "OK";
			}
		}catch(Exception e){
			throw new LotteryException(SmsPushTaskService.FINDTASKTOEXECEXCEPTION, SmsPushTaskService.FINDTASKTOEXECEXCEPTION_STR + e.toString());
		}
	}

	@Override
	public void executeTask(String taskId) throws LotteryException {
		try{
			SmsPushTask task = this.getSmsPushTaskDao().getPushTaskForUpdate(taskId);
			if(task == null || task.getTaskStatus() > 1){
				logger.error("executeTask get a " + task + " task, or taskStatus is " + (task != null ? task.getTaskStatus() + "" : " null") + ".");
				return;
			}
			logger.debug("executeTask get a task(" + task.getTaskId() + ") will be execute is:");
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s taskId		 = " + task.getTaskId());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s taskStatus	 = " + task.getTaskStatus());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s taskSummary	 = " + task.getTaskSummary());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s productId	 = " + task.getProductId());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s serviceId	 = " + task.getServiceId());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s content		 = " + task.getContent());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s sendSummary	 = " + task.getSendSummary());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s beginTime	 = " + task.getBeginTime());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s endTime		 = " + task.getEndTime());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s lotteryId	 = " + task.getLotteryId());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s lotteryTerm	 = " + task.getLotteryTerm());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s taskTime		 = " + task.getTaskTime());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s executionTime = " + task.getExecutionTime());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s executionStat = " + task.getExecutionStat());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s remark		 = " + task.getRemark());
			logger.debug("executeTask get the task(" + task.getTaskId() + ")'s reserve		 = " + task.getReserve());
			boolean finished = false;
			int number = AutoProperties.getInt(task.getServiceId() + ".sendPerNumber", 1, resource);
			while(!finished){
				try{
					if(smsPushDataService.sendPushData(taskId, number) == null){
						finished = true;
					}
				}catch(LotteryException e){
					logger.error("sendPushData occur exception, but the task(" + task.getTaskId() + ") still continue, the exception is:" + e);
				}
			}
			this.getSmsPushTaskDao().updateTaskExecutionStat(taskId, 2, false, "");
			logger.debug("executeTask send all the task(" + task.getTaskId() + ")'s push data, the task is finished.");
		}catch(Exception e){
			logger.error("executeTask(" + taskId + ") occur exception:" + e);
			throw new LotteryException(SmsPushTaskService.FINDTASKTOEXECEXCEPTION, SmsPushTaskService.FINDTASKTOEXECEXCEPTION_STR + e.toString());
		}
	}
}
