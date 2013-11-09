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
		String contextflg="";//����ʧ����Ϣ
		String sendSummary="";//����ժҪ
		String contexttrue="";//���سɹ���Ϣ
		String stime="",etime="";//��ʼʱ�䣬����ʱ��
		int count=0;//��ʶ����Ⱥ��������
		int errorcount=0;//��¼��������
		String context="������¿�����Ϣ��";//��������
		//��õ��ڵ����ڣ���ʽ %yyyy-MM-dd%
		String winLine="%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+"%";
		//��ÿ������ڼ���	
		List<LotteryTermModel> list=this.getLotteryTermDao().queryHaveWinTermInfobytime(winLine);
		if(list==null||list.size()==0)
		    return "û�п�������������Ҫ��ӣ�";
		Map<String, Object> parameter=new HashMap<String, Object>();
		for (LotteryTermModel lotteryTermModel : list) {
			//�ж��Ƿ񿪽�
			if(lotteryTermModel.getWinStatus()==1)
			{
				if(lotteryTermModel.getLotteryId()==1000001)
					contextflg+="��������͸��"+lotteryTermModel.getTermNo()+"����δ������";
				if(lotteryTermModel.getLotteryId()==1000002)
					contextflg+="���ǲʵ�"+lotteryTermModel.getTermNo()+"����δ������";
				if(lotteryTermModel.getLotteryId()==1000003)
					contextflg+="��������"+lotteryTermModel.getTermNo()+"����δ������";
				if(lotteryTermModel.getLotteryId()==1000004)
					contextflg+="�������"+lotteryTermModel.getTermNo()+"����δ������";
				if(lotteryTermModel.getLotteryId()==1000005)
					contextflg+="��Ф�ֵ�"+lotteryTermModel.getTermNo()+"����δ������";
				if(lotteryTermModel.getLotteryId()==1300001)
					contextflg+="ʤ���ʵ�"+lotteryTermModel.getTermNo()+"����δ������";
				if(lotteryTermModel.getLotteryId()==1300002)
					contextflg+="��ѡ�ŵ�"+lotteryTermModel.getTermNo()+"����δ������";
			}
			//���÷�������
			if(lotteryTermModel.getLotteryId()==1000001)
			{
				context+="��������͸��"+lotteryTermModel.getTermNo()+"�ڵĿ�������"+lotteryTermModel.getLotteryResult()+"��";
				sendSummary+="��������͸��"+lotteryTermModel.getTermNo()+"�ڡ�";
			}
			if(lotteryTermModel.getLotteryId()==1000002)
			{
				context+="���ǲʵ�"+lotteryTermModel.getTermNo()+"�ڵĿ�������"+lotteryTermModel.getLotteryResult()+"��";
				sendSummary+="���ǲʵ�"+lotteryTermModel.getTermNo()+"�ڡ�";
			}
			if(lotteryTermModel.getLotteryId()==1000003)
			{
				context+="��������"+lotteryTermModel.getTermNo()+"�ڵĿ�������"+lotteryTermModel.getLotteryResult()+"��";
				sendSummary+="��������"+lotteryTermModel.getTermNo()+"�ڡ�";
			}
			if(lotteryTermModel.getLotteryId()==1000004)
			{
				context+="�������"+lotteryTermModel.getTermNo()+"�ڵĿ�������"+lotteryTermModel.getLotteryResult()+"��";
				sendSummary+="�������"+lotteryTermModel.getTermNo()+"�ڡ�";
			}
			if(lotteryTermModel.getLotteryId()==1000005)
			{
				context+="��Ф�ֵ�"+lotteryTermModel.getTermNo()+"�ڵĿ�������"+lotteryTermModel.getLotteryResult()+"��";
				sendSummary+="��Ф�ֵ�"+lotteryTermModel.getTermNo()+"�ڡ�";
			}
			if(lotteryTermModel.getLotteryId()==1300001)
			{
				context+="ʤ���ʵ�"+lotteryTermModel.getTermNo()+"�ڵĿ�������"+lotteryTermModel.getLotteryResult()+"��";
				sendSummary+="ʤ���ʵ�"+lotteryTermModel.getTermNo()+"�ڡ�";
			}
			if(lotteryTermModel.getLotteryId()==1300002)
			{
				context+="��ѡ�ŵ�"+lotteryTermModel.getTermNo()+"�ڵĿ�������"+lotteryTermModel.getLotteryResult()+"��";
				sendSummary+="��ѡ�ŵ�"+lotteryTermModel.getTermNo()+"�ڡ�";
			}
		}
		//�����δ�����Ĳ��ڷ���
		if(!"".equals(contextflg)){
			return contextflg.substring(0, contextflg.length()-1)+"��";
		}
		contexttrue=sendSummary.substring(0,sendSummary.length()-1)+"����������������ӡ�";
		sendSummary=sendSummary.substring(0,sendSummary.length()-1)+"��������";
		context=context.substring(0, context.length()-1)+"��������Ϣ�����http://www.lottery360.cn��";
  	 //��ȡ�����ļ����õ�������Ϣ
  	  	try {
  	  		smspushtask.setTaskStatus(Integer.parseInt(AutoProperties.getString("KJGG.taskStatus","0","com.success.lottery.sms.sms")));
  	  		stime=AutoProperties.getString("KJGG.beginTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+" 09:00:00","com.success.lottery.sms.sms");
  	  		etime=AutoProperties.getString("KJGG.endTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+" 16:00:00","com.success.lottery.sms.sms");
  	  		smspushtask.setProductId(AutoProperties.getString("KJGG.productId","A","com.success.lottery.sms.sms"));
  	  	} catch (MissingResourceException e) {
  	  		logger.error("insertSmsPush Error :", e);
  	  		throw new LotteryException(SMS01EXCEPTION,SMS01EXCEPTION_STR);
  	  	}
  	  	//����ʱ��
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
  	  	smspushtask.setSendSummary("��������");
  	  	parameter.put("beginTime", "%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+"%");
	  	parameter.put("serviceId","KJGG");
	  	SmsPushTask smsPushTask=this.smsPushTaskDao.querySmsPushTask(parameter);
	  	
	  	//������ҪҪ���Ͷ��ŵ��û�
	  	List<UserAccountModel> userlist=this.getUserAccountDAO().getUserAccountInfoForbindMobileFlag();
	  	LotterySequence  sequence=LotterySequence.getInstatce("SPT");
	  	//���TsakId
	  	String taskId="";
	  	if(null!=smsPushTask)
	  		taskId=smsPushTask.getTaskId();
	  	else
	  		taskId=sequence.getSequence();
	  	//��ӿ���֪ͨ������־������ʼ
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
	  	System.out.println("��ʼ���׼������");
	  	System.out.println("======================");
    	  //���Ⱥ������
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
    				//��ѯǰ�û��Ƿ���ӹ�
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
	  				//�������ʧ��
	  				param.put("keyword3", userAccountModel.getMobilePhone());
					param.put("keyword4",userAccountModel.getUserId()+"");
					//��¼����ʧ��
					com.success.lottery.operatorlog.service.OperatorLogger.log(41020, param);
	  				errorcount+=1;
	  			}
	  		}
	  		//�ɹ�����
		  	com.success.lottery.operatorlog.service.OperatorLogger.log(40021, param);
		  	smspushtask.setContent(context);
	  	}catch (Exception e) {
	  	//��¼ʧ�ܽ���
	  		String errorMessage="";
	  		for (LotteryTermModel lotteryTermModel : list) {
	  			errorMessage+="���֣�"+lotteryTermModel.getLotteryId()+"���ڣ�"+lotteryTermModel.getTermNo();
	  		}
			param.put("errorMessage",errorMessage);
			com.success.lottery.operatorlog.service.OperatorLogger.log(41021, param); 
		}
    	//�������
	  	Map<String, String> pa=new HashMap<String, String>();
		pa.put("keyword2", taskId);
		pa.put("keyword1", date);
		pa.put("userId", userId);
		pa.put("userName",userName);
		pa.put("userKey", ip);
	  	try{
	  		if(null==smsPushTask){
	  			this.getSmsPushTaskDao().insertSmsPushTask(smspushtask);
	  			pa.put("message", contexttrue+"����ӣ�"+count+"�����ݣ����ʧ��"+errorcount+"����");
				//�����ɹ�
				com.success.lottery.operatorlog.service.OperatorLogger.log(40022, pa);
	  		}
		}catch (Exception e) {
			//����ʧ��
			pa.put("errorMessage","�н�֪ͨ��������ʧ��ʱ�䣺"+date);
			com.success.lottery.operatorlog.service.OperatorLogger.log(41022, pa);
		}
	  	return contexttrue+"����ӣ�"+count+"�����ݣ����ʧ��"+errorcount+"����";
	}
	public void test() throws LotteryException{
		Calendar cal=Calendar.getInstance();
		List<String> arr=new ArrayList<String>();
		String Str_zjgg=this.insertSmsPush("aaa","bbb","sss");
		arr.add(Str_zjgg);
		String context="������¿�����Ϣ��";//��������
		//��õ��ڵ����ڣ���ʽ %yyyy-MM-dd%
		String winLine="%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+"%";
		//��ÿ������ڼ���	
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
		String returnStr="�н�֪ͨ�����Ѿ����ɣ�������";//��������
		int count=0;
		int errorcount=0;
		Calendar cal=Calendar.getInstance();//����
		String stime="",etime="",lotteryname="";//��ʼʱ�䣬����ʱ��
		SmsPushTask smspushTask=null;
		//���ݲ��в��ڲ���ڶ���
		LotteryTermModel lotteryModel=new LotteryTermModel();
		lotteryModel.setLotteryId(lotteryId);
		lotteryModel.setTermNo(termNo);
		lotteryModel=this.getLotteryTermDao().queryTermInfo(lotteryModel);
		//�ж��Ƿ񿪽�
		if(lotteryModel.getLotteryId()==1000001)
			lotteryname=lotteryModel.getTermNo()+"�ڳ�������͸";
		if(lotteryModel.getLotteryId()==1000002)
			lotteryname=lotteryModel.getTermNo()+"�����ǲ�";
		if(lotteryModel.getLotteryId()==1000003)
			lotteryname=lotteryModel.getTermNo()+"��������";
		if(lotteryModel.getLotteryId()==1000004)
			lotteryname=lotteryModel.getTermNo()+"��������";	
		if(lotteryModel.getLotteryId()==1000005)
			lotteryname=lotteryModel.getTermNo()+"����Ф��";
		if(lotteryModel.getLotteryId()==1300001)
		    lotteryname=lotteryModel.getTermNo()+"��ʤ����";
		if(lotteryModel.getLotteryId()==1300002)
			lotteryname=lotteryModel.getTermNo()+"����ѡ��";
		if(lotteryModel.getWinStatus()!=4&&lotteryModel.getWinStatus()!=7&&lotteryModel.getWinStatus()!=8)
			return lotteryname+"��δ�ҽ���";
		//�ж������Ƿ��Ѿ����ɹ�
		Map<Object,Object> map=new HashMap<Object,Object>();
		map.put("lotteryId",lotteryId);
		map.put("lotteryTerm",termNo);
		map.put("serviceId","ZJTZ");
		smspushTask=this.getSmsPushTaskDao().querySmsPushTashByLottery(map);
		SmsPushTask pushTask=new SmsPushTask();
		//��ȡ�����ļ�
		try {
			pushTask.setTaskStatus(Integer.parseInt(AutoProperties.getString("ZJTZ.taskStatus","0","com.success.lottery.sms.sms")));
			stime=AutoProperties.getString("ZJTZ.beginTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" 09:00:00","com.success.lottery.sms.sms");
	  	  	etime=AutoProperties.getString("ZJTZ.endTime",cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)+1<10?"0"+(cal.get(Calendar.DATE)+1):cal.get(Calendar.DATE)+1)+" 16:00:00","com.success.lottery.sms.sms");
	  	  	pushTask.setProductId(AutoProperties.getString("ZJTZ.productId","B","com.success.lottery.sms.sms"));
	  	  } catch (MissingResourceException e) {
	  		 logger.error("insertSmsPush Error :", e);
			 throw new LotteryException(SMS01EXCEPTION,SMS01EXCEPTION_STR);
	  	  }
	  	 //����ʱ��
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
		//���ݲ��ֲ��ڲ�ѯ�����б�
		List<BetOrderDomain> list=this.getBetOrderDaoImpl().getWinOrderList(map);
		//���TsakId
		LotterySequence  sequence=LotterySequence.getInstatce("SPT");
  	  	String taskId="";
  		if(smspushTask==null)
  			taskId=sequence.getSequence();
  		else
  			taskId=smspushTask.getTaskId();
  		
  		//��¼��־  �н�֪ͨ��ʼ
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
			//�����û�ID��ѯ�û�
			UserAccountModel accountModel=this.getUserAccountDAO().selectUserByUserId(betOrderDomain.getUserId());
			//�ж��û��Ƿ�Ϊ��Ч�Ͱ���
			if(betOrderDomain.getWinStatus()==2||betOrderDomain.getWinStatus()==3 || betOrderDomain.getWinStatus()==299||betOrderDomain.getWinStatus()==399)
				if(accountModel.getStatus()==1&&accountModel.isBindMobileFlag()){
					//����н�֪ͨ����
					SmsPushData smsPushData=new SmsPushData();
					smsPushData.setEndTime(pushTask.getEndTime());
					smsPushData.setBeginTime(pushTask.getBeginTime());
					smsPushData.setSendCount(0);
					smsPushData.setSendStatus(0);
					smsPushData.setServiceId("ZJTZ");
					smsPushData.setMobilePhone(accountModel.getMobilePhone());
					long id=this.getSmsPushDataDao().selectMixId()+1;
					smsPushData.setId(id);
					smsPushData.setSendSummary(lotteryname+"�н�֪ͨ");
					smsPushData.setRemark("");
					smsPushData.setReserve("");
					smsPushData.setTaskId(taskId);
					java.text.NumberFormat nf=java.text.NumberFormat.getInstance();
					nf.setGroupingUsed(false);
					double money=betOrderDomain.getPreTaxPrize()/100+betOrderDomain.getPreTaxPrize()%100*0.01;
					//�ֻ���Ʊ���񣺹�ϲ������ģ��������ڣ��淨���н�MMԪ��XXXXXX���ɷ��͡�TZ��������š���ѯ�������顣
					String countext="�ֻ���Ʊ���񣺹�ϲ�������"+lotteryname+"�н�"+nf.format(money)+"Ԫ!�н������ı��Ϊ"+betOrderDomain.getOrderId()+"���ɷ��͡�TZ��������š���ѯ�������顣";
					smsPushData.setContent(countext);
					if(betOrderDomain.getWinStatus()==2 || betOrderDomain.getWinStatus()==299)
						countext+="�����н�����ֱ�����ӵ������ֻ�Ͷע�ʻ��С�������Ϣ�벦��_4008096566�����http://www.lottery360.cn��";
					else
						countext+="��Я���������֤��ʡ��ʹ��������ֻ�Ͷע�칫���콱��������Ϣ�벦��4008096566��";
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
						//��¼����ʧ��
						com.success.lottery.operatorlog.service.OperatorLogger.log(41023, param);
					}
					
						
				}
			}
		}catch (Exception e) {
			//��¼ʧ�ܽ���
			param.put("errorMessage","����:"+lotteryId+"�ڣ�"+termNo);
			com.success.lottery.operatorlog.service.OperatorLogger.log(41024, param);
		}
		pushTask.setTaskId(taskId);
		pushTask.setLotteryId(lotteryId);
		pushTask.setLotteryTerm(termNo);
		pushTask.setServiceId("ZJTZ");
		pushTask.setSendSummary(lotteryname+"�н�֪ͨ");
		pushTask.setRemark("");
		pushTask.setReserve("");
		pushTask.setContent(lotteryname+"�н�֪ͨ��������Ϣ�����http://www.lottery360.cn��");
		//��¼�ɹ�����
		param.put("keyword3", taskId);
		com.success.lottery.operatorlog.service.OperatorLogger.log(40024, param);
		//����н�֪ͨ����
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
				logger.info("��Ӷ����н�֪ͨ����ɹ�������");
				pa.put("message", lotteryname+returnStr+count+"������,���ʧ��"+errorcount+"����");
				//�����ɹ�
				com.success.lottery.operatorlog.service.OperatorLogger.log(40025, pa);
			}
		}catch (Exception e) {
			//����ʧ��
			pa.put("errorMessage","����:"+lotteryId+"�ڣ�"+termNo);
			com.success.lottery.operatorlog.service.OperatorLogger.log(41025, pa);
		}
		
		logger.info(lotteryname+returnStr+count+"������,���ʧ��"+errorcount+"����");
		return lotteryname+returnStr+count+"������,���ʧ��"+errorcount+"����";
	}
	@Override
	public List<String> insertSmsPushAll(String userId,String userName,String ip) throws LotteryException {
		List<String> returnlist=new ArrayList<String>();
		Calendar cal=Calendar.getInstance();
		//��õ��ڵ����ڣ���ʽ %yyyy-MM-dd%
		String winLine="%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+"%";
		//��ÿ������ڼ���	
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
		//��ѯ���쿪���Ĳ���
		Calendar cal=Calendar.getInstance();
		//��õ��ڵ����ڣ���ʽ %yyyy-MM-dd%
		String winLine="%"+cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1<10?("0"+(cal.get(Calendar.MONTH)+1)):cal.get(Calendar.MONTH)+1)+"-"+(cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE))+"%";
		//��ÿ������ڼ���	
		List<LotteryTermModel> list=this.getLotteryTermDao().queryHaveWinTermInfobytime(winLine);
		if(list==null||list.size()==0)
			arr.add("����û����Ҫ�����Ĳ��ֲ���");
		String lotteryname="";
		for (LotteryTermModel lotteryModel : list) {
			//�ж��Ƿ񿪽�
			if(lotteryModel.getLotteryId()==1000001)
				lotteryname=lotteryModel.getTermNo()+"�ڳ�������͸";
			else if(lotteryModel.getLotteryId()==1000002)
				lotteryname=lotteryModel.getTermNo()+"�����ǲ�";
			else if(lotteryModel.getLotteryId()==1000003)
				lotteryname=lotteryModel.getTermNo()+"��������";
			else if(lotteryModel.getLotteryId()==1000004)
				lotteryname=lotteryModel.getTermNo()+"��������";	
			else if(lotteryModel.getLotteryId()==1000005)
				lotteryname=lotteryModel.getTermNo()+"����Ф��";
			else if(lotteryModel.getLotteryId()==1300001)
			    lotteryname=lotteryModel.getTermNo()+"��ʤ����";
			else
				lotteryname=lotteryModel.getTermNo()+"����ѡ��";
			//�ж��Ƿ񿪽�
			//����hhʱmm�ֿ���,Ŀǰ��δ�����Ѿ�������,Ŀǰ��δ�����Ѿ����ҽ�
			lotteryname+=":����"+lotteryModel.getWinLine2().getHours()+"ʱ"+lotteryModel.getWinLine2().getMinutes()+"�ֿ�����";
			
			if(lotteryModel.getWinStatus()==2||lotteryModel.getWinStatus()==3)
				lotteryname+="Ŀǰ���ѿ���,Ŀǰ��δ�ҽ�";
			else if(lotteryModel.getWinStatus()==4||lotteryModel.getWinStatus()==7||lotteryModel.getWinStatus()==8)
			{	
				Map<Object,Object> map=new HashMap<Object,Object>();
				map.put("lotteryId",lotteryModel.getLotteryId());
				map.put("betTerm",lotteryModel.getTermNo());
				List betlist=this.getBetOrderDaoImpl().getWinOrderList(map);
				int ordercount=0;
				if(betlist!=null)
					ordercount=betlist.size();
				//���ݲ��в��ڲ�ѯ�н���������
				lotteryname+="Ŀǰ�Ѿ�����,Ŀǰ�Ѿ��ҽ�,�н�����"+ordercount+"��;";
			}else
			{
				lotteryname+="Ŀǰ��δ����,Ŀǰ��δ�ҽ�";
			}
			arr.add(lotteryname);
		}
		//�ҳ����첻�����Ĳ���
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
				lotteryname="��������͸:�ò��ֽ��ղ�������";
			else if(termNo==1000002)
				lotteryname="���ǲ�:�ò��ֽ��ղ�������";
			else if(termNo==1000003)
				lotteryname="������:�ò��ֽ��ղ�������";
			else if(termNo==1000004)
				lotteryname="������:�ò��ֽ��ղ�������";	
			else if(termNo==1000005)
				lotteryname="��Ф��:�ò��ֽ��ղ�������";
			else if(termNo==1300001)
			    lotteryname="ʤ����:�ò��ֽ��ղ�������";
			else
				lotteryname="��ѡ��:�ò��ֽ��ղ�������";
			arr.add(lotteryname);
		}
		returnmap.put("arr",arr);
		//��ȡ�����ļ� �ҵ��������н� ֪ͨ����ʱ��
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
		//��ѯ��Ч�ذ����û�����
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
				//���������߳�
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
