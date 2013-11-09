/**
 * Title: TermInfoDetailAction.java
 * @Package com.success.lottery.web.admin.action
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-5-24 ����10:27:32
 * @version V1.0
 */
package com.success.lottery.web.admin.action;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.success.lottery.business.service.LotteryStaticDefine;
import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.util.LotteryInfo;
import com.success.lottery.util.LotteryTools;
import com.success.utils.ApplicationContextUtils;

/**
 * com.success.lottery.web.admin.action
 * TermInfoDetailAction.java
 * TermInfoDetailAction
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2010-5-24 ����10:27:32
 * 
 */

public class TermInfoDetailAction extends ActionSupport {

	/**
	 * @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô)
	 */
	private static final long serialVersionUID = 3063171597475992126L;
	
	private String show_error = "SUCESS";
	
	private int ex_code = -10000;
	private String ex_reason;
	
	private String l_lotteryId;
	private String l_termNo;
	
	private String termStatus_name;
	private String saleStatus_name;
	private String winStatus_name;
	
	private Map<Integer, Map<String,String>> ballSaleInfo;
	private Map<String,Map<String,String>> winResult;
	private Map<String,String> missCount;
	
	
	private LotteryTermModel termModel;
	
	
	public String showTermInfoDetail(){
		try{
			LotteryTermServiceInterf termService = ApplicationContextUtils.getService("lotteryTermService", LotteryTermServiceInterf.class);
			LotteryTermModel termModel = termService.queryTermInfo(Integer.parseInt(this.getL_lotteryId()), this.getL_termNo());
			this.setTermModel(termModel);
			this.setTermStatus_name(LotteryStaticDefine.termStatus.get(String.valueOf(termModel.getTermStatus())));
			this.setSaleStatus_name(LotteryStaticDefine.saleStatus.get(""+termModel.getSaleStatus()));
			this.setWinStatus_name(LotteryStaticDefine.termWinStatus.get(""+termModel.getWinStatus()));
			if(StringUtils.isNotEmpty(termModel.getSalesInfo())){
				this.ballSaleInfo = LotteryTools.splitSalesInfo(termModel.getSalesInfo());
			}
			this.winResult = this.convertWinResult(Integer.parseInt(this.getL_lotteryId()), termModel.getLotteryInfo());
			this.missCount = this.convertMissCount(Integer.parseInt(this.getL_lotteryId()), termModel.getLotteryInfo().getMissCountResult());
		}catch(LotteryException e){;
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
	
	
	private Map<String,Map<String,String>> convertWinResult(int lotteryId,LotteryInfo info){
		Map<String,Map<String,String>> result = new LinkedHashMap<String,Map<String,String>>();
		switch (lotteryId) {
		case LotteryStaticDefine.LOTTERY_1000001://����͸
			if(info != null && info.getSuperWinResult() != null){
				String [] keyName = {"","һ�Ƚ�","���Ƚ�","���Ƚ�","�ĵȽ�","��Ƚ�","���Ƚ�","�ߵȽ�","�˵Ƚ�"};
				for(Map.Entry<String, TreeMap<String,String[]>> oneEntry : info.getSuperWinResult().entrySet()){
					String key = oneEntry.getKey();
					int keyInt = Integer.parseInt(key);
					TreeMap<String,String[]> tmpMap = oneEntry.getValue();
					Map<String,String> b_map = new HashMap<String,String>();
					b_map.put("A", tmpMap.get("A")[0]);
					b_map.put("B", tmpMap.get("A")[1]);
					result.put(keyName[keyInt]+"����", b_map);
					if(tmpMap.get("B") != null){
						if((tmpMap.get("B") instanceof String[]) && tmpMap.get("B").length == 2){
							Map<String,String> a_map = new HashMap<String,String>();
							a_map.put("A", tmpMap.get("B")[0]);
							a_map.put("B", tmpMap.get("B")[1]);
							result.put(keyName[keyInt]+"׷��", a_map);
						}
					}
					
				}
			}
			break;
		case LotteryStaticDefine.LOTTERY_1000002://���ǲ�
			if(info != null && info.getSevenColorWinResult() != null){
				result = this.convertWinResult(new String[] {"","һ�Ƚ�","���Ƚ�","���Ƚ�","�ĵȽ�","��Ƚ�","���Ƚ�"},info.getSevenColorWinResult());
			}
			break;
		case LotteryStaticDefine.LOTTERY_1000003://������
			if(info != null && info.getArrangeThreeWinResult() != null){
				result = this.convertWinResult(new String [] {"","����3ֱѡ","����3��ѡ3","����3��ѡ6"},info.getArrangeThreeWinResult());
			}
			break;
		case LotteryStaticDefine.LOTTERY_1000004://������
			if(info != null && info.getArrangeFiveWinResult() != null ){
				Map<String,String[]> tmp = new HashMap<String,String[]>();
				tmp.put("1", info.getArrangeFiveWinResult());
				result = this.convertWinResult(new String [] {"","����5ֱѡ"},tmp);
			}
			break;
		case LotteryStaticDefine.LOTTERY_1000005://��Ф��
			if(info != null && info.getHappyZodiacWinResult() != null){
				Map<String,String[]> tmp = new HashMap<String,String[]>();
				tmp.put("1", info.getHappyZodiacWinResult());
				result = this.convertWinResult(new String [] {"","һ�Ƚ�"},tmp);
			}
			break;
		case LotteryStaticDefine.LOTTERY_1300001://ʤ����
			result = this.convertWinResult(new String[] { "", "һ�Ƚ�","���Ƚ�" }, info == null ? null : info.getWinOrFailWinResult());
			break;
		case LotteryStaticDefine.LOTTERY_1300002://��ѡ�ų�
			if(info != null && info.getArbitry9WinResult() != null){
				Map<String,String[]> tmp = new HashMap<String,String[]>();
				tmp.put("1", info.getArbitry9WinResult());
				result = this.convertWinResult(new String [] {"","һ�Ƚ�"},tmp);
			}
			break;
		case LotteryStaticDefine.LOTTERY_1300003://6����ȫ��
			if(info != null && info.getHalf6WinResult() != null){
				Map<String,String[]> tmp = new HashMap<String,String[]>();
				tmp.put("1", info.getHalf6WinResult());
				result = this.convertWinResult(new String [] {"","һ�Ƚ�"},tmp);
			}
			break;
		case LotteryStaticDefine.LOTTERY_1300004://4�������
			if(info != null && info.getBall4WinResult() != null){
				Map<String,String[]> tmp = new HashMap<String,String[]>();
				tmp.put("1", info.getBall4WinResult());
				result = this.convertWinResult(new String [] {"","һ�Ƚ�"},tmp);
			}
			break;
		case LotteryStaticDefine.LOTTERY_1200001://�������ֲ�
			if(info != null && info.getElevenYunWinResult() != null){
				result = this.convertWinResult(new String[] {"","��ѡһ��1","��ѡ����2","��ѡ����3","��ѡ����4","��ѡ����5","��ѡ����5","��ѡ����5","��ѡ����5","ѡǰ��ֱѡ","ѡǰ��ֱѡ","ѡǰ����ѡ","ѡǰ����ѡ"},info.getElevenYunWinResult());
			}
			break;
		default:
			break;
		}
		return result;
		
	}
	
	private Map<String,Map<String,String>> convertWinResult(String [] keyName,Map<String,String[]> winResult){
	       //������ �����ֱ�Ϊ<ֱѡ��������������������(�ֱ���1,2,3����),[ע��,���]>�����û����3����6��ü���Ԫ�ز�����
			Map<String,Map<String,String>> result = new LinkedHashMap<String,Map<String,String>>();
			if(winResult != null){
				for(Map.Entry<String, String[]> oneEntry : winResult.entrySet()){
					String key = oneEntry.getKey();
					int keyInt = Integer.parseInt(key);
					String[] tmp = oneEntry.getValue();
					if(tmp != null && tmp.length == 2){
						Map<String,String> b_map = new HashMap<String,String>();
						b_map.put("A", tmp[0]);
						b_map.put("B", tmp[1]);
						result.put(keyName[keyInt], b_map);
					}
				}
			}
			return result;
	}
	
	private Map<String,String> convertMissCount(int lotteryId,Map<String,Integer> missCountResult){
		Map<String,String> result = new LinkedHashMap<String,String>();
		
		//if(StringUtils.isNotEmpty(winResult)){
		switch (lotteryId) {
		case LotteryStaticDefine.LOTTERY_1000001:
			if(missCountResult != null){
				StringBuffer sb = new StringBuffer();
				DecimalFormat dFormat = new DecimalFormat("00");
				
				for(int i = 1 ;i < 36;i++){
					String key = dFormat.format(i);
					sb.append("[").append(key).append(":").append(missCountResult.get("H-"+key)).append("],");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.lastIndexOf(","));
				}
				result.put("ǰ��", sb.toString());
				
				sb.delete(0, sb.length());
				for(int i = 1 ; i< 13; i++){
					String key = dFormat.format(i);
					sb.append("[").append(key).append(":").append(missCountResult.get("B-"+key)).append("],");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.lastIndexOf(","));
				}
				result.put("����", sb.toString());
			}
			break;
		case LotteryStaticDefine.LOTTERY_1000002:
			//���ǲ�  P-λ��-���� ������λ��Ϊ1-7,����Ϊ0-9
			if(missCountResult != null){
				for(int i = 1 ; i < 8 ;i++){
					StringBuffer sb = new StringBuffer();
					for(int k = 0 ; k < 10; k++){
						sb.append("[").append(k).append(":").append(missCountResult.get("P-" + i + "-" + k)).append("],");
					}
					if(sb.toString().endsWith(",")){
						sb.deleteCharAt(sb.lastIndexOf(","));
					}
					result.put(String.valueOf(i), sb.toString());
				}
			}
			break;
		case LotteryStaticDefine.LOTTERY_1000003:
			//������ Z-λ��-���� ,Z��ʾֱѡ,λ��Ϊ1-3,����Ϊ0-9;  H-����,H��ʾ��ֵ,����Ϊ1-26
			if(missCountResult != null){
				StringBuffer sb = new StringBuffer();
				for(int i = 1 ; i < 4 ;i++){
					for(int k = 0; k< 10 ;k++){
						sb.append("[").append(k).append(":").append(missCountResult.get("Z-" + i + "-" + k)).append("],");
					}
					if(sb.toString().endsWith(",")){
						sb.deleteCharAt(sb.lastIndexOf(","));
					}
					result.put(String.valueOf(i), sb.toString());
					sb.delete(0, sb.length());
				}
				
				sb.delete(0, sb.length());
				for(int i = 1 ; i < 27; i++){
					sb.append("[").append(i).append(":").append(missCountResult.get("H-" + i)).append("],");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.lastIndexOf(","));
				}
				result.put("��ֵ", sb.toString());
			}
			break;
		case LotteryStaticDefine.LOTTERY_1000004:
			//������ P-λ��-���� ,λ��Ϊ1-5������Ϊ0-9
			if(missCountResult != null){
				StringBuffer sb = new StringBuffer();
				for(int i = 1 ; i < 6 ;i++){
					for(int k = 0; k< 10 ;k++){
						sb.append("[").append(k).append(":").append(missCountResult.get("P-" + i + "-" + k)).append("],");
					}
					if(sb.toString().endsWith(",")){
						sb.deleteCharAt(sb.lastIndexOf(","));
					}
					result.put(String.valueOf(i), sb.toString());
					sb.delete(0, sb.length());
				}
			}
			break;
		case LotteryStaticDefine.LOTTERY_1000005:
			//��Ф�� B-���� ����Ϊ01-12
			if(missCountResult != null){
				StringBuffer sb = new StringBuffer();
				DecimalFormat dFormat = new DecimalFormat("00");
				for(int i = 1 ; i< 13; i++){
					String key = dFormat.format(i);
					sb.append("[").append(key).append(":").append(missCountResult.get("B-"+key)).append("],");
				}
				if(sb.toString().endsWith(",")){
					sb.deleteCharAt(sb.lastIndexOf(","));
				}
				result.put("", sb.toString());
			}
			break;
		case LotteryStaticDefine.LOTTERY_1200001://�������ֲ�
			if(missCountResult != null){
				StringBuffer sb = new StringBuffer();
				DecimalFormat dFormat = new DecimalFormat("00");
				Map<String,String> showPage = new LinkedHashMap<String, String>();
				showPage.put("B-", "��ѡ2-8");
				showPage.put("Z1-", "ǰ1λ");
				showPage.put("Z2-", "ǰ2λ");
				showPage.put("Z3-", "ǰ3λ");
				showPage.put("Q2-", "ǰ2��ѡ");
				showPage.put("Q3-", "ǰ3��ѡ");
				
				for(Map.Entry<String, String> oneShow : showPage.entrySet()){
					for(int i = 1 ; i< 12; i++){
						sb.append("[").append(dFormat.format(i)).append(":").append(missCountResult.get(oneShow.getKey()+dFormat.format(i))).append("],");
					}
					if(sb.toString().endsWith(",")){
						sb.deleteCharAt(sb.lastIndexOf(","));
					}
					result.put(oneShow.getValue(), sb.toString());
					sb.delete(0, sb.length());
				}
			}
			break;
		default:
			break;
		}
		return result;
	}
	
	
	public int getEx_code() {
		return this.ex_code;
	}
	private void setEx_code(int ex_code) {
		this.ex_code = ex_code;
	}
	public String getEx_reason() {
		return this.ex_reason;
	}
	private void setEx_reason(String ex_reason) {
		this.ex_reason = ex_reason;
	}
	public String getL_lotteryId() {
		return this.l_lotteryId;
	}
	public void setL_lotteryId(String id) {
		this.l_lotteryId = id;
	}
	public String getL_termNo() {
		return this.l_termNo;
	}
	public void setL_termNo(String no) {
		this.l_termNo = no;
	}


	public LotteryTermModel getTermModel() {
		return this.termModel;
	}


	public void setTermModel(LotteryTermModel termModel) {
		this.termModel = termModel;
	}


	public String getShow_error() {
		return this.show_error;
	}


	private void setShow_error(String show_error) {
		this.show_error = show_error;
	}


	public String getSaleStatus_name() {
		return this.saleStatus_name;
	}


	public void setSaleStatus_name(String saleStatus_name) {
		this.saleStatus_name = saleStatus_name;
	}


	public String getTermStatus_name() {
		return this.termStatus_name;
	}


	public void setTermStatus_name(String termStatus_name) {
		this.termStatus_name = termStatus_name;
	}


	public String getWinStatus_name() {
		return this.winStatus_name;
	}


	public void setWinStatus_name(String winStatus_name) {
		this.winStatus_name = winStatus_name;
	}
	
	public Map<Integer, Map<String, String>> getBallSaleInfo() {
		return this.ballSaleInfo;
	}

	public void setBallSaleInfo(Map<Integer, Map<String, String>> ballSaleInfo) {
		this.ballSaleInfo = ballSaleInfo;
	}

	public Map<String, Map<String, String>> getWinResult() {
		return this.winResult;
	}

	public void setWinResult(Map<String, Map<String, String>> winResult) {
		this.winResult = winResult;
	}


	public Map<String, String> getMissCount() {
		return this.missCount;
	}


	public void setMissCount(Map<String, String> missCount) {
		this.missCount = missCount;
	}
	
	

}
