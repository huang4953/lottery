/**
 * Title: BetOrderShowAction.java
 * @Package com.success.lottery.web.admin.termmanager.action
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-19 ����02:57:10
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.business.service.interf.LotteryManagerInterf;
import com.success.lottery.ehand.ehandserver.bussiness.EhandQueryService;
import com.success.lottery.ehand.ehandserver.bussiness.model.EhandTermBussiModel;
import com.success.lottery.ehand.eterm.domain.EhandTermModel;
import com.success.lottery.ehand.eterm.service.interf.EhangTermServiceInterf;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.order.service.interf.BetPlanOrderServiceInterf;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.LotteryTools;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.termmanager.action
 * BetOrderShowAction.java
 * BetOrderShowAction
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-19 ����02:57:10
 * 
 */

public class TermWinShowAction extends ActionSupport {
	
	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = 4208135624489511920L;
	private int ex_code;
	private String ex_reason;
	private String p_lotteryId;
	private String lottery_name;
	private String p_termNo_begin = "0";
	private String p_termNo_end = "0";
	private Map<Integer, String> lotteryList;
	private List<LotteryTermModel> winInfoList;
	
	/*
	 * ���ӹ����Ĳ���
	 */
	private String l_lotteryId;
	private String l_termNo;
	
	/*
	 * ���忪����Ϣ¼������ʼ������
	 */
	private  ArrayList<String> before_code;
	private  ArrayList<String> end_code;
	
	private String lastTermInfoStr;
	private String lastTermInfoStr_second;
	
	private String begin_time;
	private String end_time;
	
	private Map<Integer, Map<String,String>> saleInfo;
	
	private Map<String,String> gameResult;//����������Ȳ���
	
	private String ehandLotteryResult;
	
	private String jsonString;
	
	private int isBiDui;
	/**
	 * 
	 * Title: checkEhandTermStatus<br>
	 * Description: <br>
	 *              <br>��������ȵĿ�������Ƿ�ͬ��������;0:�Ѿ�ͬ��������1:�����ȵĲ��ڱ����ô����Ҳ�����Ӧ�Ĳ��ֲ���;2:û��ͬ������
	 * @return
	 */
	public String checkEhandTermStatus(){
		int completeStatus = 0;//
		EhandTermModel ehandTermIndo = null;
		try{
			EhangTermServiceInterf eTermServer = ApplicationContextUtils.getService("ehandTermService", EhangTermServiceInterf.class);
			List<Integer> queryLotteryIds = changeElotteryIds(this.getL_lotteryId());
			for(Integer innerLotteryId : queryLotteryIds){
				ehandTermIndo = eTermServer.getEhandTermInfoByBet(innerLotteryId, this.getL_termNo());
				if(ehandTermIndo == null){//Ӧ��ȷ����ϵͳ��ʼ����ʱ�������ȵĲ��ڱ�Ѷ�Ӧ�Ĳ�������Ҳͬ����ȥ��
					completeStatus = 1;
					break;
				}else{
					if(StringUtils.isEmpty(ehandTermIndo.getBonuscode())){//�������ݻ�û��ͬ������
						completeStatus = 2;
						break;
					}
				}
			}
		}catch(Exception e){
			completeStatus = 1;//
		}
		this.jsonString  = JSONArray.fromObject(completeStatus).toString();
		return "ajaxjson";
	}
	
	private List<Integer> changeElotteryIds(String betLotteryId){
		List<Integer> queryLotteryIds = new ArrayList<Integer>();
		if(LotteryStaticDefine.LOTTERY_1000001 == Integer.parseInt(betLotteryId) || LotteryStaticDefine.LOTTERY_1000005 == Integer.parseInt(betLotteryId)){
			//����͸����Ч��
			queryLotteryIds.add(LotteryStaticDefine.LOTTERY_1000001);
			queryLotteryIds.add(LotteryStaticDefine.LOTTERY_1000005);
		}else if(LotteryStaticDefine.LOTTERY_1000003 == Integer.parseInt(betLotteryId) || LotteryStaticDefine.LOTTERY_1000004 == Integer.parseInt(betLotteryId)){
			//����3������5
			queryLotteryIds.add(LotteryStaticDefine.LOTTERY_1000003);
			queryLotteryIds.add(LotteryStaticDefine.LOTTERY_1000004);
		}else if(LotteryStaticDefine.LOTTERY_1300001 == Integer.parseInt(betLotteryId) || LotteryStaticDefine.LOTTERY_1300002 == Integer.parseInt(betLotteryId)){
			//ʤ���ʺ���9��
			queryLotteryIds.add(LotteryStaticDefine.LOTTERY_1300001);
			queryLotteryIds.add(LotteryStaticDefine.LOTTERY_1300002);
		}else{
			//�����ĵ��������Ĳ���
			queryLotteryIds.add(Integer.parseInt(betLotteryId));
		}
		return queryLotteryIds;
	}
	
	/**
	 * 
	 * Title: checkEhandTermKj<br>
	 * Description: <br>
	 *              <br>ͬ�������ȵĲ��ڿ������
	 * @return
	 */
	public String updateEhandTermKj(){
		/*
		 * �����Ȳ��ڱ����Ѿ��п��������,
		 * 0:�Ѿ��õ��˿��������
		 * 1:�����ȵĲ��ڱ����ô����Ҳ�����Ӧ�Ĳ��ֲ���;
		 * 2����������ϵͳͬ�������������
		 */
		int completeStatus = 0;//ͬ���ɹ�
		try{
			EhangTermServiceInterf eTermServer = ApplicationContextUtils.getService("ehandTermService", EhangTermServiceInterf.class);
			List<Integer> queryLotteryIds = changeElotteryIds(this.getL_lotteryId());
			EhandTermModel ehandTermInfo = null;
			
			for(Integer innerLotteryId : queryLotteryIds){
				ehandTermInfo = eTermServer.getEhandTermInfoByBet(innerLotteryId, this.getL_termNo());
				if(ehandTermInfo == null){//Ӧ��ȷ����ϵͳ��ʼ����ʱ�������ȵĲ��ڱ�Ѷ�Ӧ�Ĳ�������Ҳͬ����ȥ��
					completeStatus = 1;
					break;
				}else{
					if(StringUtils.isEmpty(ehandTermInfo.getBonuscode())){//�������ݻ�û��ͬ������
						EhandTermBussiModel eTermBussiModel = null;
						try{
							eTermBussiModel = EhandQueryService.QueryEkjgg50203(ehandTermInfo.getLotteryId(), this.getL_termNo());
							if(eTermBussiModel == null || StringUtils.isEmpty(eTermBussiModel.getBonuscode())){//û�д�������ϵͳȡ����������
								completeStatus = 2;
								break;
							}else{//ȡ�����ݺ���������ȵĲ��ڱ�
								int updateEteamResult = eTermServer.updateEhandInfo(innerLotteryId, null, this.getL_termNo(), eTermBussiModel.getBonuscode(),6);
								if(updateEteamResult <= 0){
									completeStatus = 2;
									break;
								}
							}
						}catch(Exception e){
							e.printStackTrace();
							completeStatus = 2;
							break;
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			completeStatus = 1;//
		}
		
		this.jsonString  = JSONArray.fromObject(completeStatus).toString();
		return "ajaxjson";
	}
	
	public String termWinInfoShow(){
		try{
			int limitNum = 10;
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			this.setLotteryList(termManager.getLotteryList());
			if(StringUtils.isNotEmpty(this.convertParam(this.getP_termNo_begin())) || StringUtils.isNotEmpty(this.convertParam(this.p_termNo_end))){
				limitNum = 0;
			}
			List<LotteryTermModel> innerWinInfo = termManager.getLotteryLastTermInfo(this.convertParam(this.getP_lotteryId()), this.convertParam(this.getP_termNo_begin()),
						this.convertParam(this.p_termNo_end), limitNum);
			/*
			 * ����LotteryTermModel����
			 */
			String tmp_p_lotteryId = this.getP_lotteryId()==null?"0":this.getP_lotteryId();
			if(innerWinInfo != null){
				String reserve = tmp_p_lotteryId+"#"+this.getP_termNo_begin()+"#"+this.getP_termNo_end();
				for(LotteryTermModel oneTerm : innerWinInfo){
					oneTerm.setReserve1(reserve);
				}
			}
			this.setWinInfoList(innerWinInfo);
					
		}catch(LotteryException e){
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
	
	public String showInputWinInfo(){
		int lotteryId = Integer.parseInt(this.getL_lotteryId());
		String termNo = this.getL_termNo();
		try{
			LotteryManagerInterf termManager = ApplicationContextUtils.getService("busLotteryManagerService", LotteryManagerInterf.class);
			System.out.println("ddddddddddddddddddddddddddddd");
			if(LotteryStaticDefine.LOTTERY_1000001 == lotteryId || LotteryStaticDefine.LOTTERY_1000005 == lotteryId){//����͸����Ф��
				this.initEhandLotteryResult(LotteryStaticDefine.LOTTERY_1000001, termNo);
				this.initInputPageSuper(termManager);
				System.out.println("ddddddddddddddddddddddddddddd22222222222222222222222");
			}else if(LotteryStaticDefine.LOTTERY_1000003 == lotteryId || LotteryStaticDefine.LOTTERY_1000004 == lotteryId){//����3��5
				this.initEhandLotteryResult(LotteryStaticDefine.LOTTERY_1000004, termNo);
				this.initInputPageArrange3_5(termManager);
			}else if(LotteryStaticDefine.LOTTERY_1000002 == lotteryId){//���ǲ�
				this.initEhandLotteryResult(LotteryStaticDefine.LOTTERY_1000002, termNo);
				this.initInputPageColor(termManager);
			}else if(LotteryStaticDefine.LOTTERY_1300001 == lotteryId){//ʤ����
				this.initEhandLotteryResult(LotteryStaticDefine.LOTTERY_1300001, termNo);
				this.initInputPageBall(lotteryId,termNo);
			}else if(LotteryStaticDefine.LOTTERY_1300002 == lotteryId){//��ѡ�ų�
				this.initEhandLotteryResult(LotteryStaticDefine.LOTTERY_1300002, termNo);
				this.initInputPageBall(lotteryId,termNo);
			}else if(LotteryStaticDefine.LOTTERY_1200001 == lotteryId){//�������ֲ�
				this.initEhandLotteryResult(LotteryStaticDefine.LOTTERY_1200001, termNo);
				this.initInputPageShiYiXuanWu(termManager);
			}else if(LotteryStaticDefine.LOTTERY_1300003 == lotteryId){//6����ȫ
				this.initEhandLotteryResult(LotteryStaticDefine.LOTTERY_1300003, termNo);
				this.initInputPageBall(lotteryId,termNo);
			}else if(LotteryStaticDefine.LOTTERY_1300004 == lotteryId){//4������
				this.initEhandLotteryResult(LotteryStaticDefine.LOTTERY_1300004, termNo);
				this.initInputPageBall(lotteryId,termNo);
			}
			
		}catch(LotteryException e){
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
		return this.getL_lotteryId();
	}
	/**
	 * 
	 * Title: initEhandLotteryResult<br>
	 * Description: <br>
	 *              <br>���ݲ��ֲ��ڻ�ȡ�����Ȳ��ڵĿ������
	 * @param lotteryId
	 * @param termNo
	 */
	public void initEhandLotteryResult(int lotteryId,String termNo) throws LotteryException{
		try{
			if(this.getIsBiDui() == 1){//�ȶ�
				EhangTermServiceInterf eTermServer = ApplicationContextUtils.getService("ehandTermService", EhangTermServiceInterf.class);
				EhandTermModel ehandTermIndo = eTermServer.getEhandTermInfoByBet(lotteryId, this.getL_termNo());
				
				if(ehandTermIndo != null && StringUtils.isNotEmpty(ehandTermIndo.getBonuscode())){
					this.setEhandLotteryResult(ehandTermIndo.getBonuscode());
				}else{
					throw new Exception();
				}
			}
		}catch(Exception e){
			throw new LotteryException(99999,"��ȡ��Ʊϵͳ�������ʧ��");
		}
	}
	
	/**
	 * 
	 * Title: initInputPageSuper<br>
	 * Description: <br>
	 *              <br>����͸��������ҳ���������
	 * @param termManager
	 * @throws LotteryException
	 */
	private void initInputPageSuper(LotteryManagerInterf termManager) throws LotteryException{
		//����͸
		StringBuffer sb = new StringBuffer();
		LotteryTermModel lastTerm = termManager.getLotteryLastWinInfo(LotteryStaticDefine.LOTTERY_1000001);
		sb.append("��<STRONG style=\"color: red\">").append(lastTerm.getTermNo()).append("</STRONG>��,����ʱ�� ").append(lastTerm.getWinLine()).append(" �������� ").append(lastTerm.getLotteryResult());
		DecimalFormat dFormat = new DecimalFormat("00");
		ArrayList<String> before_code = new ArrayList<String>();
		ArrayList<String> end_code = new ArrayList<String>();
		for(int i = 1; i< 36 ;i++){
			before_code.add(dFormat.format(i));
			if(i <= 12){
				end_code.add(dFormat.format(i));
			}
		}
		this.setBefore_code(before_code);
		this.setEnd_code(end_code);
		this.setLastTermInfoStr(sb.toString());
	}
	/**
	 * 
	 * Title: initUnputPageArrange3_5<br>
	 * Description: <br>
	 *              <br>����3��5��������ҳ���������
	 * @param termManager
	 * @throws LotteryException
	 */
	private void initInputPageArrange3_5(LotteryManagerInterf termManager) throws LotteryException{
		
		StringBuffer sb = new StringBuffer();
		LotteryTermModel lastTerm3 = termManager.getLotteryLastWinInfo(LotteryStaticDefine.LOTTERY_1000003);
		sb.append("����3��<STRONG style=\"color: red\">").append(lastTerm3.getTermNo()).append("</STRONG>��,����ʱ�� ").append(lastTerm3.getWinLine()).append(" �������� ").append(lastTerm3.getLotteryResult());
		this.setLastTermInfoStr(sb.toString());
		
		StringBuffer sb_second = new StringBuffer();
		LotteryTermModel lastTerm5 = termManager.getLotteryLastWinInfo(LotteryStaticDefine.LOTTERY_1000004);
		sb_second.append("����5��<STRONG style=\"color: red\">").append(lastTerm5.getTermNo()).append("</STRONG>��,����ʱ�� ").append(lastTerm5.getWinLine()).append(" �������� ").append(lastTerm5.getLotteryResult());
		this.setLastTermInfoStr_second(sb_second.toString());
		
		ArrayList<String> before_code = new ArrayList<String>();
		for(int i = 0; i< 10 ;i++){
			before_code.add(String.valueOf(i));
		}
		this.setBefore_code(before_code);
	}
	/**
	 * 
	 * Title: initInputPageColor<br>
	 * Description: <br>
	 *              <br>���ǲʿ�������ҳ�����
	 * @param termManager
	 * @throws LotteryException
	 */
	private void initInputPageColor(LotteryManagerInterf termManager) throws LotteryException{
		StringBuffer sb = new StringBuffer();
		LotteryTermModel lastTerm = termManager.getLotteryLastWinInfo(LotteryStaticDefine.LOTTERY_1000002);
		sb.append("���ǲʵ�<STRONG style=\"color: red\">").append(lastTerm.getTermNo()).append("</STRONG>��,����ʱ�� ").append(lastTerm.getWinLine()).append(" �������� ").append(lastTerm.getLotteryResult());
		this.setLastTermInfoStr(sb.toString());
		
		ArrayList<String> before_code = new ArrayList<String>();
		for(int i = 0; i< 10 ;i++){
			before_code.add(String.valueOf(i));
		}
		this.setBefore_code(before_code);
	}
	/**
	 * 
	 * Title: initInputPageBall<br>
	 * Description: <br>
	 *              <br>��ʿ�������ҳ�����
	 * @param termManager
	 * @throws LotteryException
	 */
	private void initInputPageBall(int lotteryId,String termNo) throws LotteryException{
		StringBuffer sb = new StringBuffer();
		LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
		//LotteryTermModel lastTerm = termManager.getLotteryLastWinInfo(lotteryId);
		LotteryTermModel term = termService.queryTermInfo(lotteryId, termNo);
		this.setLottery_name(LotteryTools.getLotteryName(lotteryId));
		this.setBegin_time(term.getStartTime().toString());
		this.setEnd_time(term.getDeadLine().toString());
		this.setLastTermInfoStr(sb.toString());
		this.setSaleInfo(LotteryTools.splitSalesInfo(term.getSalesInfo()));
		
		//this.gameResult = new HashMap<String,String>();
		//this.gameResult.put("3", "");
		
	}
	/**
	 * 
	 * Title: initInputPageShiYiXuanWu<br>
	 * Description: <br>
	 *              <br>����11ѡ5����ҳ���ʼ��
	 * @param termManager
	 * @throws LotteryException
	 */
	private void initInputPageShiYiXuanWu(LotteryManagerInterf termManager) throws LotteryException{
		//�������ֲ�
		StringBuffer sb = new StringBuffer();
		LotteryTermModel lastTerm = termManager.getLotteryLastWinInfo(LotteryStaticDefine.LOTTERY_1200001);
		sb.append("��<STRONG style=\"color: red\">").append(
				(lastTerm == null || lastTerm.getTermNo() == null) ? "" : lastTerm.getTermNo())
				.append("</STRONG>��,����ʱ�� ").append((lastTerm == null || lastTerm.getWinLine()==null)?"":lastTerm.getWinLine())
				.append(" �������� ").append((lastTerm == null || lastTerm.getLotteryResult()==null)?"":lastTerm.getLotteryResult());
		DecimalFormat dFormat = new DecimalFormat("00");
		ArrayList<String> before_code = new ArrayList<String>();
		for(int i = 1; i< 12 ;i++){
			before_code.add(dFormat.format(i));
		}
		this.setBefore_code(before_code);
		this.setLastTermInfoStr(sb.toString());
	}
	
	private String convertParam(String param){
		return "0".equals(param) ? null : param;
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

	public String getP_termNo_end() {
		return p_termNo_end;
	}

	public void setP_termNo_end(String no_end) {
		p_termNo_end = no_end;
	}

	public List<LotteryTermModel> getWinInfoList() {
		return winInfoList;
	}

	public void setWinInfoList(List<LotteryTermModel> winInfoList) {
		this.winInfoList = winInfoList;
	}

	public String getLastTermInfoStr() {
		return lastTermInfoStr;
	}

	public void setLastTermInfoStr(String lastTermInfoStr) {
		this.lastTermInfoStr = lastTermInfoStr;
	}

	public ArrayList<String> getBefore_code() {
		return before_code;
	}

	public void setBefore_code(ArrayList<String> before_code) {
		this.before_code = before_code;
	}

	public ArrayList<String> getEnd_code() {
		return end_code;
	}

	public void setEnd_code(ArrayList<String> end_code) {
		this.end_code = end_code;
	}

	public String getLastTermInfoStr_second() {
		return lastTermInfoStr_second;
	}

	public void setLastTermInfoStr_second(String lastTermInfoStr_second) {
		this.lastTermInfoStr_second = lastTermInfoStr_second;
	}

	public String getL_lotteryId() {
		return l_lotteryId;
	}

	public void setL_lotteryId(String id) {
		l_lotteryId = id;
	}

	public String getL_termNo() {
		return l_termNo;
	}

	public void setL_termNo(String no) {
		l_termNo = no;
	}

	public String getLottery_name() {
		return lottery_name;
	}

	public void setLottery_name(String lottery_name) {
		this.lottery_name = lottery_name;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Map<Integer, Map<String, String>> getSaleInfo() {
		return saleInfo;
	}

	public void setSaleInfo(Map<Integer, Map<String, String>> saleInfo) {
		this.saleInfo = saleInfo;
	}

	public Map<String, String> getGameResult() {
		return gameResult;
	}

	public void setGameResult(Map<String, String> gameResult) {
		this.gameResult = gameResult;
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

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getEhandLotteryResult() {
		return ehandLotteryResult;
	}

	public void setEhandLotteryResult(String ehandLotteryResult) {
		this.ehandLotteryResult = ehandLotteryResult;
	}

	public int getIsBiDui() {
		return isBiDui;
	}

	public void setIsBiDui(int isBiDui) {
		this.isBiDui = isBiDui;
	}
}
