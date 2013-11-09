package com.success.lottery.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.util.core.LotteryFactory;
import com.success.lottery.util.core.LotteryInterf;
import com.success.lottery.util.core.LotteryUnDefineException;
import com.success.utils.AutoProperties;
/**
 * <p>
 * ��ø������������Ϣ��������Ϣ������LotteryType.properties�С�string������Ϣδ�ҵ��򷵻�null��<br>
 * ��Ʊ�ַ����������ߣ�����Ͷע��ض�����ַ�����ʽ��������صĶ�����ַ�����ʽ�����ظ��ֲ�Ʊ�����Ϣ��<br>
 * 
 * @author gaoboqin
 * 
 */
public class LotteryTools{

	private static Log			logger					= LogFactory.getLog(LotteryTools.class);
	private static String		lotteryConfig			= "com.success.lottery.util.LotteryType";
	private static final String	LOTTERYlIST_SEPARATOR	= "\\|";									// ������ֵķָ���
	private static final String	LOTTERYLIST_SUB_KEY		= "LotteryList";
	private static final int	LOTTERY_TYPE_NUM		= 1;										// ���������ֲ�Ʊ
	private static final int	LOTTERY_TYPE_FAST		= 2;										// ��Ƶ���ֲ�Ʊ
	private static final int	LOTTERY_TYPE_SOCCER		= 3;										// ���
	@SuppressWarnings("unused")
	private static final int	LOTTERY_TYPE_COMPETE	= 4;										// ����

	/**
	 * ������в��ֱ�ź�������Ϣ����LotteryType.properties ��LotteryList�ֶζ�ȡ����<br>
	 * 
	 * @return Map&lt;���ֱ��, ��������&gt;
	 */
	public static Map<Integer, String> getLotteryList(){
		Map<Integer, String> lotteryMap = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());
		try{
			String lotteryStr = AutoProperties.getString(LOTTERYLIST_SUB_KEY, "", lotteryConfig);
			String[] lotteryArry = lotteryStr.split(LOTTERYlIST_SEPARATOR);
			for(String str : lotteryArry){
				String[] subLottery = str.split(",");
				lotteryMap.put(Integer.parseInt(subLottery[0]), subLottery[1]);
			}
		}catch(Exception e){
			logger.debug("LotteryTools.getLotteryList occur Exception:" + e.getMessage());
		}
		return lotteryMap;
	}

	/**
	 * ������в��ֱ�ź�������Ϣ����LotteryType.properties ��LotteryList�ֶζ�ȡ����<br>
	 * 
	 * @param lotteryOrgan
	 *            1-���, 2-����;ĿǰĬ����ʣ�������дʲô���������
	 * @return Map&lt;Integer, String&gt; Map&lt;���ֱ��, ��������&gt;
	 */
	public static Map<Integer, String> getLotteryList(int lotteryOrgan){
		Map<Integer, String> retLotteryMap = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());
		try{
			Map<Integer, String> lotteryMap = getLotteryList();
			for(Map.Entry<Integer, String> entry : lotteryMap.entrySet()){
				if(getIntegerCharAt(entry.getKey(), 1) == lotteryOrgan){
					retLotteryMap.put(entry.getKey(), entry.getValue());
				}
				;
			}
		}catch(RuntimeException e){
			logger.debug("LotteryTools.getLotteryList(int lotteryOrgan) occur Exception:" + e.getMessage());
		}
		return retLotteryMap;
	}

	/**
	 * �����ͨ���ֲʲ��֣��б��LotteryType.properties ��LotteryList�ֶζ�ȡ����<br>
	 * 1=���ֲʣ�2=��Ƶ�ʣ�3-��ʣ�4-����;<br>
	 * 
	 * @return Map&lt;Integer, String&gt; Map&lt;���ֱ��, ��������&gt;
	 */
	public static Map<Integer, String> getNumberLotteryList(){
		return getLotteryByPos(2, LOTTERY_TYPE_NUM);
	}

	/**
	 * �����ʲ����б��б��LotteryType.properties ��LotteryList�ֶζ�ȡ����<br>
	 * 1=���ֲʣ�2=��Ƶ�ʣ�3-��ʣ�4-����;<br>
	 * 
	 * @return Map&lt;Integer, String&gt; Map&lt;���ֱ��, ��������&gt;
	 */
	public static Map<Integer, String> getSoccerLotteryList(){
		return getLotteryByPos(2, LOTTERY_TYPE_SOCCER);
	}

	/**
	 * ��ø�Ƶ�ʲ����б��б��LotteryType.properties ��LotteryList�ֶζ�ȡ����<br>
	 * 1=���ֲʣ�2=��Ƶ�ʣ�3-��ʣ�4-����;<br>
	 * 
	 * @return Map&lt;Integer, String&gt; Map&lt;���ֱ��, ��������&gt;
	 */
	public static Map<Integer, String> getFastLotteryList(){
		return getLotteryByPos(2, LOTTERY_TYPE_FAST);
	}

	/**
	 * ͨ��LotteryId��ò�����������<br>
	 * 
	 * @param lotteryId
	 *            ���ֱ��
	 * @return String ���ز�����������
	 */
	public static String getLotteryName(int lotteryId){
		return AutoProperties.getString(lotteryId + ".name", null, lotteryConfig);
	}

	/**
	 * ͨ��LotteryId��ò�������<br>
	 * 
	 * @param lotteryId
	 *            ���ֱ��
	 * @return String ���ز��ּ�����
	 */
	public static String getLotteryDesc(int lotteryId){
		return AutoProperties.getString(lotteryId + ".desc", null, lotteryConfig);
	}

	/**
	 * ͨ��LotteryId��ò�������
	 * 
	 * @param lotteryId
	 * @return int ���ز������ͣ�0=δ�ҵ��ò��� 1=���������ֲ�Ʊ 2=��Ƶ���ֲ�Ʊ 3=��� 4=����
	 */
	public static int getLotteryType(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".type", 0, lotteryConfig);
	}

	/**
	 * ���ݲ���Id�����淨��Ϣ����LotteryType.properties ��playList�ֶζ�ȡ����
	 * 
	 * @param lotteryId
	 *            ����Id
	 * @return LotteryInfo
	 *         <p>
	 *         null - δ�ҵ��ò�����Ϣ<br>
	 *         LotteryInfo - ������Ϣ�࣬���÷���:<br>
	 *         Map&lt;Integer, String&gt; getLotteryPlayMap() ���ذ����淨id���淨���Ƶļ���<br>
	 *         Map&lt;Integer, Map&lt;Integer, String&gt;&gt;
	 *         getLotteryPlayBetMap() ���ذ����淨id��Ӧ��Ͷע��ʽ�ļ���<br>
	 * 
	 */
	private static LotteryInfo getLotteryPlayList(int lotteryId){
		LotteryInfo lotteryInfo = new LotteryInfo();
		String lotteryListStr = AutoProperties.getString(lotteryId + ".playList", null, lotteryConfig);
		lotteryInfo.setLotteryPlayListStr(lotteryListStr);
		lotteryInfo.setLotteryPlayMap(LotteryTools.splitPlayStrForPlayMap(lotteryListStr));// �淨����
		lotteryInfo.setLotteryPlayBetMap(LotteryTools.splitPlayStrForPlayBet(lotteryListStr));// �淨��Ӧ��Ͷע��ʽ����
		return lotteryInfo;
	}

	/**
	 * ���ݲ��ֵõ�playType����
	 * 
	 * @param lotteryId
	 * @return playType����
	 */
	public static Map<Integer, String> getLotteryPlayTypeList(int lotteryId){
		LotteryInfo lotteryInfo = LotteryTools.getLotteryPlayList(lotteryId);
		return lotteryInfo.getLotteryPlayMap();
	}

	/**
	 * ���ݲ���id�Ͳ��ֶ�Ӧ���淨id��ø��淨��Ӧ��Ͷע��ʽ����<br>
	 * 
	 * @param lotteryId
	 *            ����Id
	 * @param playType
	 *            �淨id
	 * @return Map&lt;Integer,String&gt; null - δ�ҵ��ò�����Ϣ<br>
	 *         Map&lt;Integer,String&gt; �������ֱ�Ϊ&ltͶע��ʽid��Ͷע��ʽ&gt;<br>
	 */
	public static Map<Integer, String> getLotteryPlayBetTypeList(int lotteryId, int playType){
		LotteryInfo lotteryInfo = LotteryTools.getLotteryPlayList(lotteryId);
		return lotteryInfo.getLotteryPlayBetMap().get(Integer.valueOf(playType));
	}

	/**
	 * ���ݲ���id���淨���ͻ���淨����
	 * @param lotteryId
	 * @param playType
	 * @return
	 */
	public static String getPlayTypeName(int lotteryId, int playType){
		try{
			return getLotteryPlayTypeList(lotteryId).get(Integer.valueOf(playType));
		}catch(Exception e){
			logger.error("getPlayTypeName(" + lotteryId + ", " + playType + ") exception:" + e);
			return null;
		}
	}
	
	public static String getPlayBetName(int lotteryId, int playType, int betType){
		try{
			String playName = getPlayTypeName(lotteryId, playType);
			String betName = getLotteryPlayBetTypeList(lotteryId, playType).get(Integer.valueOf(betType));
			if(lotteryId == 1000001){
				return betName + playName;
			}
			return playName + betName;
		}catch(Exception e){
			logger.error("getPlayBetName(" + lotteryId + ", " + playType + ", " + betType + ") exception:" + e);
			return null;
		}
	}
	
	/**
	 * ͨ��LotteryId��ò������������б�<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return List<String> null δ�ҵ���������<br>
	 *         List&lt;String&gt;,����Ϊ&lt;�������&gt;<br>
	 */
	public static List<String> getLotteryLimitArea(int lotteryId){
		String areaStr = AutoProperties.getString(lotteryId + ".limitArea", null, lotteryConfig);
		return Arrays.asList(areaStr.split(","));
	}

	/**
	 * �õ�areaCode���������Ƿ�������LotteryId�Ĳ���<br>
	 * 
	 * @param lotteryId
	 *            ���ֱ��
	 * @param areaCode
	 *            ��������ʡ�ݴ���
	 * @return boolean true - ������ò���<br>
	 *         false - ��������ò���<br>
	 */
	public static boolean isAllowableUse(int lotteryId, String areaCode){
		List<String> areaList = LotteryTools.getLotteryLimitArea(lotteryId);
		boolean isAllow = true;
		if(areaList != null && areaList.contains(areaCode)){
			isAllow = false;
		}
		/*
		 * for(String area : areaList){ isAllow =
		 * (area.equals(areaCode))?false:true; if(!isAllow) break; }
		 */
		return isAllow;
	}

	/**
	 * ���ݲ���id��øò��ֵ��淨����
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return String ���淨���ܵ��ı������淨���ܵ��ļ���ַ�����ӵ�ַ
	 */
	public static String getLotteryRules(int lotteryId){
		return AutoProperties.getString(lotteryId + ".rules", null, lotteryConfig);
	}

	/**
	 * ���ݲ���id�õ��ò����Ƿ�����<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return boolean true - �ò�����������<br>
	 *         false - �ò���δ����<br>
	 */
	public static boolean isLotteryStart(int lotteryId){
		return "true".equalsIgnoreCase(AutoProperties.getString(lotteryId + ".start", "false", lotteryConfig));
	}

	/**
	 * <br>
	 * ���ݲ���id�õ��ò��ֵ�������ǰ���������� <br>
	 * ������ǰ���������� :
	 * �ȹٷ����۵Ľ���ʱ����ǰ�ķ��������ٷ����۽���ʱ��-���ֶ�=���Թ���Ľ���ʱ�䡣��1000001.preSaleTimer=30
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return int ������ǰ���������� ��λΪ���� ,Ĭ��Ϊ0
	 */
	public static int getLotteryPreSaleTimer(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".preSaleTimer", 0, lotteryConfig);
	}

	/**
	 * <br>
	 * ���ݲ���id�õ��ò��ֵĿ�ʼ�����ӳٷ����� <br>
	 * �ȹٷ����ۿ�ʼʱ���ӳٵķ����������ӳٵ�ʱ���ڣ�������е�ǰ��������
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return int ���ۿ�ʼʱ���ӳٵķ����� ��λΪ���ӣ�Ĭ��Ϊ0
	 */
	public static int getLotteryDelaySaleTimer(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".delaySaleTimer", 0, lotteryConfig);
	}
	/**
	 * 
	 * Title: getLotteryPreDrawTicketTimer<br>
	 * Description: <br>
	 *              <br>��Ʊ����ʱ����ӳٷ�����
	 * @param lotteryId
	 * @return
	 */
	public static int getLotteryPreDrawTicketTimer(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".preDrawTicketTimer", 0, lotteryConfig);
	}
	/**
	 * 
	 * Title: getLotteryPreDrawTicketTimer<br>
	 * Description: <br>
	 *              <br>���ݸ����Ĺٷ���Ʊ��ֹʱ�䣬��ȥϵͳ�������ǰ���������õ�ϵͳ��Ҫ�Ľ�ֹ��Ʊʱ��
	 * @param lotteryId
	 * @param basicTime
	 * @return
	 */
	public static String getLotteryPreDrawTicketTimer(int lotteryId,String basicTime,String formatStr){
		String returnDate = basicTime;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			java.util.Calendar Cal=java.util.Calendar.getInstance();
			Cal.setTime(sdf.parse((basicTime)));
			int defineTime = getLotteryPreDrawTicketTimer(lotteryId);
			
			Cal.add(java.util.Calendar.MINUTE, -defineTime);
			returnDate = sdf.format(Cal.getTime());
			
		}catch(Exception e){
			e.printStackTrace();
			returnDate = basicTime;
		}
		return returnDate;
	}

	/**
	 * <br>
	 * ���ݲ���id�õ��ò��ֵļ��ֹͣ����ʱ�� <br>
	 * ���ü�����ֹͣ����ʱ��ı��ʽ�����ڶ�ʱ��������Ϣ�е�saleStatus���糬������ʱ�������ø�״̬Ϊֹͣ���ۡ���ʽΪQuartz
	 * CronExpression
	 * 
	 * @param lotteryId
	 * @return String
	 */
	public static String getCheckCron(int lotteryId){
		return AutoProperties.getString(lotteryId + ".checkCron", null, lotteryConfig);
	}

	/**
	 * ���ݲ���id�õ��ò��ֵĳ�Ʊ��ʽ
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return int
	 */
	public static int getLotteryPrintTicketMode(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".printTicketMode", 0, lotteryConfig);
	}

	/**
	 * ���ݲ���id�õ��ò��ֵĵ��Ų�Ʊ��ӡע��
	 * 
	 * @param lotteryId
	 *            ��Ʊid
	 * @return int
	 */
	public static int getLotteryprintBetCount(int lotteryId){
		return AutoProperties.getInt(lotteryId + ".printBetCount", 0, lotteryConfig);
	}

	/**
	 * ���ݲ���id�õ�������ʽ<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return String <br>
	 *         null δ�ҵ��������� <br>
	 *         ������ʽ���ֶ���������Ϊ0���Զ�����������Ϊ����������
	 */
	public static String getLotteryWinMode(int lotteryId){
		return AutoProperties.getString(lotteryId + ".winMode", null, lotteryConfig);
	}

	/**
	 * ���ݲ���id�õ��������ݻ�ȡ��ַ<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return String <br>
	 *         null δ�ҵ��������� <br>
	 *         �������ݻ�ȡ��ַ���ֶ�¼�뿪����������Ϊ0���Զ���ȡ������Ϊ�Զ���ȡ����Դurl
	 */
	public static String getLotteryWinUrl(int lotteryId){
		return AutoProperties.getString(lotteryId + ".winUrl", null, lotteryConfig);
	}

	/**
	 * ���ݲ���id�õ����������Զ���ȡ�Ĵ�����<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return String <br>
	 *         null δ�ҵ��������� <br>
	 *         ���������Զ���ȡ�Ĵ����࣬�ֶ�¼�뿪����������Ϊ0���Զ���ȡ������Ϊ��������Դurl�Ĵ����ࡣ��winModeΪ0ʱ��Ч
	 */
	public static String getLotteryWinUrlProcessor(int lotteryId){
		return AutoProperties.getString(lotteryId + ".winUrlProcessor", null, lotteryConfig);
	}

	/**
	 * 
	 * Title: getLotteryMoneyLimit<br>
	 * Description: <br>
	 * ��������id�õ��ò��ֵ����������޶����<br>
	 * 
	 * @param lotteryId
	 * @return String <br>
	 *         null û���ҵ�������
	 */
	public static String getLotteryMoneyLimit(int lotteryId){
		return AutoProperties.getString(lotteryId + ".betMoneyLimit", "0", lotteryConfig).trim();
	}

	/**
	 * 
	 * Title: getLotteryMultiMoneyLimit<br>
	 * Description: <br>
	 * ���ݲ���id�õ��ò��ֵ����������޶����,�����ڱ���Ͷע�޶�<br>
	 * 
	 * @param lotteryId
	 * @return ���õ��޶����
	 */
	public static String getLotteryMultiMoneyLimit(int lotteryId){
		return AutoProperties.getString(lotteryId + ".betMultiMoneyLimit", "0", lotteryConfig).trim();
	}

	/**
	 * 
	 * Title: getLotteryBetNumLimit<br>
	 * Description: <br>
	 * ���ݲ���id�õ��ò���Ͷע����������<br>
	 * 
	 * @param lotteryId
	 * @return ���õı����޶�
	 */
	public static String getLotteryBetNumLimit(int lotteryId){
		return AutoProperties.getString(lotteryId + ".betMultiLimit", "0", lotteryConfig).trim();
	}

	/**
	 * 
	 * <br>
	 * �����ʻ򾹲µĿ�Ͷע����ر�����Ϣ <br>
	 * 
	 * ���صĽ�������Ǿ�������ģ��������Ϊ �� <br>
	 * �Գ��ΰ������ִ�С�������� <br>
	 * 
	 * @param salesInfo
	 *            ����ı�����Ϣ�ַ��� ��ʽ�磺1,A-AC����&B-��������,����,����ʱ��|2,A-�ж���&B-��ɭ��,����,����ʱ��
	 * @return Map&lt;Integer,Map&lt;String,String&gt;&gt;<br>
	 *            Map&lt;����,Map&lt;key,value&gt;&gt;<br>
	 *            key����Ϊ��<br>
	 *            A ��ʾ����
	 *            B ��ʾ�ͳ�
	 *            C ��ʾ����
	 *            D ��ʾ����ʱ��
	 * 
	 */
	public static Map<Integer, Map<String,String>> splitSalesInfo(String salesInfo){
		int litteryId = 1300001;// ��ʺ;��ʵĲ�ֹ���һ�������Կ��԰���ʤ���ʲ��
		return LotteryTools.splitSalesInfo(litteryId, salesInfo).getWinOrFailSaleInfo();
	}

	/**
	 * 
	 * Title: splitSalesInfo<br>
	 * Description: <br>
	 * ���ղ��ֲ��������Ϣ<br>
	 * 
	 * @param lotteryId
	 * @param salesInfo
	 * @return ������ּ��ϵ���Ϣʵ��
	 */
	private static LotteryInfo splitSalesInfo(int lotteryId, String salesInfo){
		LotteryInfo info = null;
		try{
			info = LotteryFactory.factory(String.valueOf(lotteryId)).splitSalesInfoResult(salesInfo, new LotteryInfo());
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitSalesInfo���������������Ϣ����" + "lotteryId = " + lotteryId + "salesInfo = " + salesInfo;
			logger.error(errorReason, e);
		}
		return info;
	}

	/**
	 * 
	 * <br>
	 * �ϲ���ʻ򾹲µĿ�Ͷע����ر�����Ϣ <br>
	 * 
	 * <br>
	 * ���ص��ַ����Ǿ�������ĺ����ɵģ��������Ϊ �� <br>
	 * �Գ��ΰ������ִ�С�������� <br>
	 * 
	 * @param infoMap
	 *            Map&lt;Integer,Map&lt;String,String&gt;&gt;<br>
	 *            Map&lt;����,Map&lt;key,value&gt;&gt;<br>
	 *            key����Ϊ��<br>
	 *            A ��ʾ����
	 *            B ��ʾ�ͳ�
	 *            C ��ʾ����
	 *            D ��ʾ����ʱ��
	 * @return String �������� 1,A-AC����&B-��������,����,����ʱ��|2,A-�ж���&B-��ɭ��,����,����ʱ��
	 */
	public static String mergeSalesInfo(Map<Integer, Map<String,String>> infoMap){
		LotteryInfo info = new LotteryInfo();
		int litteryId = 1300001;// ��ʺ;��ʵĲ�ֹ���һ�������Կ��԰���ʤ���ʺϲ�
		info.setWinOrFailSaleInfo(infoMap);
		return LotteryTools.mergeSalesInfo(litteryId, info);
	}

	/**
	 * 
	 * Title: mergeSalesInfo<br>
	 * Description: <br>
	 * ���ղ��ֺϲ����۽��<br>
	 * 
	 * @param lotteryId
	 * @param info
	 * @return �ϲ�����ַ���
	 */
	private static String mergeSalesInfo(int lotteryId, LotteryInfo info){
		String result = "";
		try{
			String innrerResult = LotteryFactory.factory(String.valueOf(lotteryId)).mergeSalesInfoResult(info);
			result = innrerResult != null ? innrerResult.replaceAll("\\r\\n", "").replaceAll("\\t", "") : innrerResult;
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.mergeSalesInfo�������ϲ����۽������" + "lotteryId = " + lotteryId;
			logger.error(errorReason, e);
		}
		return result;
	}

	/**
	 * 
	 * У��Ͷע�ĺ����Ƿ����Ͷע��ʽ��Ҫ��<br>
	 * 
	 * @param lotteryId
	 *            ���ֱ��
	 * @param playType
	 *            �淨id
	 * @param betType
	 *            Ͷע��ʽid
	 * @param betString
	 *            Ͷע�ĺ���
	 * @return boolean true ��ʶУ��ͨ�� false ��ʶУ��ʧ��
	 */
	public static boolean checkLotteryBetFormat(int lotteryId, int playType, int betType, String betString){
		boolean result = false;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).checkBetType(String.valueOf(playType), String.valueOf(betType), betString);
		}catch(LotteryUnDefineException e){
			String errorReason = "checkLotteryBetFormat������ ���ݲ���δ�ҵ����Ӧ��Ͷע��ʽ " + "lotteryId = " + lotteryId + " playType = " + playType + " betType = " + betType;
			logger.error(errorReason, e);
		}catch(Exception e){
			String errorReason = "LotteryTools.checkLotteryBetFormat������ У��Ͷע��ʽʱ�����쳣 " + "lotteryId = " + lotteryId + " playType = " + playType + " betType = " + betType;
			logger.error(errorReason, e);
		}
		return result;
	}

	/**
	 * 
	 * <br>
	 * ���ݲ���id�ͽ������ַ��������������ַ������ΪLotteryInfo����ļ��� <br>
	 * LotteryInfo����ļ���Ϊ�� <br>
	 * TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt;
	 * superWinResult;//��������͸
	 * ��������˼Ϊ��&lt;һ�Ƚ����˵Ƚ�(������1-8��ʾ),&lt;������(A)��׷�ӽ�(B),[ע��,���]&gt;&gt; <br>
	 * String [] happyZodiacWinResult;//��Ф�֣����鳤��Ϊ2��������˼Ϊ��[ע�������] <br>
	 * TreeMap&lt;String,String[]&gt; sevenColorWinResult;//���ǲ�
	 * ,���鳤��Ϊ2�������ֱ�Ϊ:&lt;һ�Ƚ������Ƚ�,[ע��,���]&gt; <br>
	 * TreeMap&lt;String,String[]&gt; arrangeThreeWinResult;//������,
	 * ���鳤��Ϊ2�������ֱ�Ϊ&lt;ֱѡ��������������������(�ֱ���1,2,3����),[ע��,���]&gt; ,���صļ������п��ܲ�������������������������������������<br>
	 * String [] arrangeFiveWinResult;//������ ,���鳤��Ϊ2��������˼Ϊ��[ע�������] <br>
	 * TreeMap&lt;String,String[]&gt; winOrFailWinResult;// ʤ����
	 * TreeMap&lt;String,String[]&gt;,���鳤��Ϊ2�� �����ֱ�Ϊ&lt;һ�Ƚ�����Ƚ�,[ע��,���]&gt; <br>
	 * String [] arbitry9WinResult;//��ѡ�ų� String [] ,���鳤��Ϊ2������Ϊ[ע��,���] <br>
	 * String [] half6WinResult;//6����ȫ�� String [] ,���鳤��Ϊ2������Ϊ[ע��,���] <br>
	 * String [] ball4WinResult;//4������� String [] ,���鳤��Ϊ2������Ϊ[ע��,���] <br>
	 * TreeMap&lt;String,String[]&gt; elevenYunWinResul //ʮһ�˶��
	 * ����Ϊ&lt;���𼶱�,[ע��,���]&gt; <br>
	 * //�����˿� <br>
	 * ʹ��ʱ���ݷ��ص�LotteryInfo���󣬵�������Ӧ���ϵ�get�������ɡ� <br>
	 * ���磺TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt; superWinResult =
	 * LotteryTools
	 * .splitWinResult(1000001,"1-A10&A1971731#B4&B1183038|2-A54&12000#B9&B72"
	 * ).getSuperWinResult(); <br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @param winResult
	 *            �������ַ���
	 * @return LotteryInfo ������Ϣ��
	 */
	public static LotteryInfo splitWinResult(int lotteryId, String winResult){
		LotteryInfo info = null;
		try{
			info = LotteryFactory.factory(String.valueOf(lotteryId)).splitWinResult(winResult, new LotteryInfo());
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitWinResult��������ֽ���������" + "lotteryId = " + lotteryId + "winResult = " + winResult;
			logger.error(errorReason, e);
		}
		return info;
	}
	
	/**
	 * 
	 * <br>
	 * ���ݲ���id�������������Ϻϲ�Ϊ�ַ��� <br>
	 * LotteryInfo����ļ���Ϊ�� <br>
	 * TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt;
	 * superWinResult;//��������͸
	 * ��������˼Ϊ��&lt;һ�Ƚ����˵Ƚ�(������1-8��ʾ),&lt;��������׷�ӽ�,[ע��,���]&gt;&gt; <br>
	 * String [] happyZodiacWinResult;//��Ф�֣����鳤��Ϊ2��������˼Ϊ��[ע�������] <br>
	 * TreeMap&lt;String,String[]&gt; sevenColorWinResult;//���ǲ�
	 * ,���鳤��Ϊ2�������ֱ�Ϊ:&lt;һ�Ƚ������Ƚ�,[ע��,���]&gt; <br>
	 * TreeMap&lt;String,String[]&gt; arrangeThreeWinResult;//������,
	 * ���鳤��Ϊ2�������ֱ�Ϊ&lt;ֱѡ��������������������(�ֱ���1,2,3����),[ע��,���]&gt; �����û����3��������Ҫ���ö�Ӧ�ļ���Ԫ��<br>
	 * String [] arrangeFiveWinResult;//������ ,���鳤��Ϊ2��������˼Ϊ��[ע�������] <br>
	 * TreeMap&lt;String,String[]&gt; winOrFailWinResult;// ʤ����
	 * TreeMap&lt;String,String[]&gt;,���鳤��Ϊ2�� �����ֱ�Ϊ&lt;һ�Ƚ�����Ƚ�,[ע��,���]&gt; <br>
	 * String [] arbitry9WinResult;//��ѡ�ų� String [] ,���鳤��Ϊ2������Ϊ[ע��,���] <br>
	 * String [] half6WinResult;//6����ȫ�� String [] ,���鳤��Ϊ2������Ϊ[ע��,���] <br>
	 * String [] ball4WinResult;//4������� String [] ,���鳤��Ϊ2������Ϊ[ע��,���] <br>
	 * TreeMap&lt;String,String[]&gt; elevenYunWinResul //ʮһ�˶��
	 * ����Ϊ&lt;���𼶱�,[ע��,���]&gt; <br>
	 * //�����˿� <br>
	 * ʹ��ʱʵ����LotteryInfo���󣬵�������Ӧ���ϵ�set�������ɡ� <br>
	 * ���磺 <br>
	 * TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt; superWinResult = new
	 * TreeMap&lt;String,TreeMap&lt;String,String[]&gt;&gt;(); <br>
	 * superWinResult.put(....); <br>
	 * ..... <br>
	 * String superWinResultStr = LotteryTools.mergeWinResult(1000001,new
	 * LotteryInfo().setSuperWinResult(superWinResult)); <br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @param info
	 *            LotteryInfo����
	 * @return String �ϲ���Ľ������ַ���
	 */
	public static String mergeWinResult(int lotteryId, LotteryInfo info){
		String result = "";
		try{
			String innrerResult = LotteryFactory.factory(String.valueOf(lotteryId)).mergeWinResult(info);
			result = innrerResult != null ? innrerResult.replaceAll("\\r\\n", "").replaceAll("\\t", "") : innrerResult;
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.mergeWinResult�������ϲ�����������" + "lotteryId = " + lotteryId;
			logger.error(errorReason, e);
		}
		return result;
	}
	/**
	 * 
	 * Title: splitMissCount<br>
	 * Description: <br>
	 *            �����©��Ϣ,�ַ����ĸ�ʽ�����ϸ������ݸ�ʽ�Ķ���,��©��Ϣ���϶�����LotteryInfo��<br>
	 *            <br> ��©��Ϣ���ϵĶ���:
	 *            <br>����Ϊ:&lt;key,value&gt; valueΪ��©�Ĵ���
	 *            <br>key˵��: 
	 *                <li>����͸ H-����,H��ʾǰ��,����Ϊ01-35;B-����,B��ʾ����,����Ϊ01-12
	 *                <li>��Ф�� B-���� ����Ϊ01-12
	 *                <li>���ǲ�  P-λ��-���� ������λ��Ϊ1-7,����Ϊ0-9
	 *                <li>������ Z-λ��-���� ,Z��ʾֱѡ,λ��Ϊ1-3,����Ϊ0-9;  H-����,H��ʾ��ֵ,����Ϊ1-26
	 *                <li>������ P-λ��-���� ,λ��Ϊ1-5������Ϊ0-9
	 *                <li>
	 *                
	 * @param lotteryId ����id
	 * @param missCount ��©��Ϣ�ַ���
	 * @return LotteryInfo
	 */
	public static LotteryInfo splitMissCount(int lotteryId, String missCount){
		LotteryInfo info = null;
		try{
			info = LotteryFactory.factory(String.valueOf(lotteryId)).splitMissCount(missCount, new LotteryInfo());
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitMissCount�����������©��Ϣ����" + "lotteryId = " + lotteryId + "missCount = " + missCount;
			logger.error(errorReason, e);
		}
		return info;
	}
	/**
	 * 
	 * Title: splitMissCountToMap<br>
	 * Description: <br>
	 *            �����©��Ϣ<br>
	 *            <br> ��©��Ϣ���ϵĶ���:
	 *            <br>����Ϊ:<key,value> valueΪ��©�Ĵ���
	 *            <br>key˵��: 
	 *                <li>����͸ H-����,H��ʾǰ��,����Ϊ01-35;B-����,B��ʾ����,����Ϊ01-12
	 *                <li>��Ф�� B-���� ����Ϊ01-12
	 *                <li>���ǲ�  P-λ��-���� ������λ��Ϊ1-7,����Ϊ0-9
	 *                <li>������ Z-λ��-���� ,Z��ʾֱѡ,λ��Ϊ1-3,����Ϊ0-9;  H-����,H��ʾ��ֵ,����Ϊ1-26
	 *                <li>������ P-λ��-���� ,λ��Ϊ1-5������Ϊ0-9
	 * @param lotteryId ����
	 * @param missCount ��©��Ϣ�ַ��� 
	 * @return Map<String,Integer> �������null�����ǲ�ֳ�����߸ò���û����©��Ϣ
	 */
	public static Map<String,Integer> splitMissCountToMap(int lotteryId, String missCount){
		Map<String,Integer> missCountMap = null;
		try{
			missCountMap = LotteryFactory.factory(String.valueOf(lotteryId)).splitMissCount(missCount, new LotteryInfo()).getMissCountResult();
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitMissCount�����������©��Ϣ����" + "lotteryId = " + lotteryId + "missCount = " + missCount;
			logger.error(errorReason, e);
		}
		return missCountMap;
	}
	
	
	/**
	 * <p>
	 * <br>
	 * ���ݲ���id�Ϳ�������ַ���������������ַ������ΪLotteryInfo����ļ��� <br>
	 * LotteryInfo����ļ���Ϊ�� <br>
	 * TreeMap&lt;String,ArrayList&lt;String&gt;&gt; superLotteryResult;//��������͸
	 * ,TreeMap&lt;String,ArrayList&lt;String&gt;&gt;
	 * ������Ϊ&lt;ǰȥ|����,&lt;��������&gt;&gt;,����ǰ����1��ʾ��������2��ʾ����λһ�����룬������'0' <br>
	 * ArrayList&lt;String&gt;
	 * happyZodiacLotteryResult;//��Ф��,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;,Ӧ�ð���2�����룬��λһ�����룬������0 <br>
	 * ArrayList&lt;String&gt;
	 * sevenColorLotteryResult;//���ǲ�,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;��Ӧ�ð���7������ <br>
	 * ArrayList&lt;String&gt;
	 * arrangeThreeLotteryResult;//������,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;��Ӧ�ð���3������ <br>
	 * ArrayList&lt;String&gt;
	 * arrangeFiveLotteryResult;//������,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;��Ӧ�ð���5������ <br>
	 * ArrayList&lt;String&gt;
	 * winOrFailLotteryResult;//ʤ����,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;��Ӧ�ð���14������ <br>
	 * ArrayList&lt;String&gt;
	 * arbitry9LotteryResult;//��ѡ�ų�,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;��Ӧ�ð���14������ <br>
	 * ArrayList&lt;String&gt;
	 * half6LotteryResult;//6����ȫ��,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;��Ӧ�ð���12������ <br>
	 * ArrayList&lt;String&gt;
	 * ball4LotteryResult;//4�������,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;��Ӧ�ð���8������ <br>
	 * ArrayList&lt;String&gt;
	 * elevenYunLotteryResult;//ʮһ�˶��,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;����λһ�����룬��������� <br>
	 * ArrayList&lt;String&gt;
	 * happyPokerLotteryResult;//�����˿�,ArrayList&lt;String&gt;
	 * ������Ϊ&lt;����&gt;��4λ��һλһ�����룬����Ϊ2-9��A��J��Q��K�����պں�÷��˳�� <br>
	 * ʹ��ʱ���ݷ��ص�LotteryInfo���󣬵�������Ӧ���ϵ�get�������ɡ� <br>
	 * ���磺TreeMap&lt;String,ArrayList&lt;String&gt;&gt; superLotteryResult =
	 * LotteryTools
	 * .splitLotteryResult(1000001,"0102030405|0102").getSuperLotteryResult();
	 * </p>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @param lotteryResult
	 *            ��������ַ���
	 * @return LotteryInfo ������Ϣ��
	 */
	public static LotteryInfo splitLotteryResult(int lotteryId, String lotteryResult){
		LotteryInfo info = null;
		try{
			info = LotteryFactory.factory(String.valueOf(lotteryId)).splitLotteryResult(lotteryResult, new LotteryInfo());
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitLotteryResult��������ֿ����������" + "lotteryId = " + lotteryId + "lotteryResult = " + lotteryResult;
			logger.error(errorReason, e);
		}
		return info;
	}

	/**
	 * <p>
	 * ���ݲ���id��������������Ϻϲ�Ϊ�ַ��� <br>
	 * LotteryInfo����ļ���Ϊ�� <code>
	 * <br>TreeMap&lt;String,ArrayList&lt;String&gt;&gt; superLotteryResult;//��������͸ ,TreeMap&lt;String,ArrayList&lt;String&gt;&gt;&gt; ������Ϊ&lt;ǰȥ|����,&lt;��������&gt;&gt;,����ǰ����1��ʾ��������2��ʾ����λһ�����룬������'0'
	 * <br>ArrayList&lt;String&gt; happyZodiacLotteryResult;//��Ф��,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;,Ӧ�ð���2�����룬��λһ�����룬������0
	 * <br>ArrayList&lt;String&gt; sevenColorLotteryResult;//���ǲ�,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;��Ӧ�ð���7������
	 * <br>ArrayList&lt;String&gt; arrangeThreeLotteryResult;//������,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;��Ӧ�ð���3������
	 * <br>ArrayList&lt;String&gt; arrangeFiveLotteryResult;//������,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;��Ӧ�ð���5������
	 * <br>ArrayList&lt;String&gt; winOrFailLotteryResult;//ʤ����,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;��Ӧ�ð���14������
	 * <br>ArrayList&lt;String&gt; arbitry9LotteryResult;//��ѡ�ų�,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;��Ӧ�ð���14������
	 * <br>ArrayList&lt;String&gt; half6LotteryResult;//6����ȫ��,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;��Ӧ�ð���12������
	 * <br>ArrayList&lt;String&gt; ball4LotteryResult;//4�������,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;��Ӧ�ð���8������
	 * <br>ArrayList&lt;String&gt; elevenYunLotteryResult;//ʮһ�˶��,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;����λһ�����룬���������
	 * <br>ArrayList&lt;String&gt; happyPokerLotteryResult;//�����˿�,ArrayList&lt;String&gt; ������Ϊ&lt;����&gt;��4λ��һλһ�����룬����Ϊ2-9��A��J��Q��K�����պں�÷��˳��      
	 * <br>ʹ��ʱʵ����LotteryInfo���󣬵�������Ӧ���ϵ�set�������ɡ�
	 * <br>���磺
	 * <br>TreeMap&lt;String,ArrayList&lt;String&gt;&gt; superLotteryResult = new TreeMap&lt;String,ArrayList&lt;String&gt;&gt;();
	 * <br>superLotteryResult.put(....);
	 * <br>.....
	 * <br>String superLotteryResultStr = LotteryTools.mergeWinResult(1000001,new LotteryInfo().setSuperWinResult(superWinResult));
	 * </code>
	 * </p>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @param info
	 *            LotteryInfo����
	 * @return String �ϲ���Ŀ�������ַ���
	 */
	public static String mergeLotteryResult(int lotteryId, LotteryInfo info){
		String result = "";
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).mergeLotteryResult(info);
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.mergeLotteryResult���ϲ������������" + "lotteryId = " + lotteryId;
			logger.error(errorReason, e);
		}
		return result;
	}

	/**
	 * 
	 * Title: splitTermAllResult<br>
	 * Description: <br>
	 * ��ֲ��ֵĿ����������������������Ϣ<br>
	 * ֻ����ʺ;�����������Ϣ���������ֵ�������Ϣû�ж���<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @param lotteryResult
	 *            �������
	 * @param winResult
	 *            ������
	 * @param salesInfo
	 *            ������Ϣ
	 * @param missCount 
	 *            ��©��Ϣ
	 * @return ������ּ��ϵ���Ϣʵ��
	 */
	public static LotteryInfo splitTermAllResult(int lotteryId, String lotteryResult, String winResult, String salesInfo,String missCount){
		LotteryInfo info = new LotteryInfo();
		try{
			LotteryInterf lotteryInstance = LotteryFactory.factory(String.valueOf(lotteryId));
			if(lotteryResult != null){
				lotteryInstance.splitLotteryResult(lotteryResult, info);
			}
			if(winResult != null){
				lotteryInstance.splitWinResult(winResult, info);
			}
			if(salesInfo != null){
				lotteryInstance.splitSalesInfoResult(salesInfo, info);
			}
			if(missCount != null){
				lotteryInstance.splitMissCount(missCount, info);
			}
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitTermAllResult��������ֲ��ڽ������"
					+ "lotteryId = " + lotteryId + "lotteryResult = "
					+ lotteryResult + "missCount=" + missCount;
			logger.error(errorReason, e);
		}
		return info;
	}

	/**
	 * 
	 * <br>
	 * ��Ͷע�����ַ�����betCode�����������صļ���Ϊ�����б��������б����&lt;��עע��,���𼶱�,������,׷�ӽ��&gt;, <br>
	 * ����û��׷�ӵ����ֵΪ0 <br>
	 * ���ж�ĳЩ���ֽ��𼶱����ΪΪ�� <br>
	 * �÷�������У��Ͷע����ʽ�Ƿ���ȷ
	 * 
	 * @param lotteryId
	 *            ����id
	 * @param playType
	 *            �淨id
	 * @param betType
	 *            Ͷע��ʽid
	 * @param lotteryResult
	 *            �������
	 * @param winReslut
	 *            ������
	 * @param betCode
	 *            Ͷע�����ַ���
	 * @return List&lt;List&lt;String&gt;&gt;
	 *         
	 *         ���ж�ĳЩ���ֽ��𼶱����ΪΪ��,������ĵ�λΪ��<br>
	 *         �������null�����п�����Ͷע����ʽ����ȷ���������˴���
	 */
	public static List<PrizeInfo> lotteryPrize(int lotteryId, int playType, int betType, String lotteryResult, String winReslut, String betCode,int betMulti){
		List<PrizeInfo> prizeResult = new ArrayList<PrizeInfo>();;
		List<List<String>> tmpResult = null;
		try{
			/*
			if(!LotteryTools.checkLotteryBetFormat(lotteryId, playType, betType, betCode)){
				return result;
			}*/
			//��עע��,���𼶱�,������,׷�ӽ��
			tmpResult = LotteryFactory.factory(String.valueOf(lotteryId))
					.lotteryPrize(String.valueOf(playType),
							String.valueOf(betType), lotteryResult, winReslut,
							betCode);
			if(tmpResult != null && !tmpResult.isEmpty()){
				int betBeiShu = betMulti > 0 ? betMulti : 1;
				int taxPercent = AutoProperties.getInt(lotteryId + ".taxPercent", 0, lotteryConfig);
				int sysBigPrize = AutoProperties.getInt(lotteryId + ".superPrizeMoney", 1000000, lotteryConfig);
				for(List<String> oneCodePrize : tmpResult){
					PrizeInfo oneInfo = new PrizeInfo();
					oneInfo.setWinCode(oneCodePrize.get(0));
					oneInfo.setPrizeLevel(Integer.parseInt(oneCodePrize.get(1)));
					oneInfo.setBasePrize(Long.parseLong(oneCodePrize.get(2))*betBeiShu*100);
					oneInfo.setAddPrize(Long.parseLong(oneCodePrize.get(3))*betBeiShu*100);
					oneInfo.setTaxPercent(taxPercent);
					long totalPrize = oneInfo.getBasePrize() + oneInfo.getAddPrize();
					if(totalPrize > sysBigPrize){//�������1�� Ҫ��˰
						long taxPrize = (totalPrize * taxPercent)/100;
						oneInfo.setTaxPrize(taxPrize);
						long aftTaxPrize = (totalPrize * (100 - taxPercent))/100;
						oneInfo.setAftTaxPrize(aftTaxPrize);
					}else{//���ÿ�˰
						oneInfo.setAftTaxPrize(totalPrize);
					}
					prizeResult.add(oneInfo);
				}
			}
		}catch(LotteryUnDefineException e){
			logger.error("��Ͷע�����ַ�������ʱδ�ҵ���Ӧ���淨��Ͷע��ʽ", e);
			return prizeResult;
		}catch(Exception e){
			logger.error("��Ͷע�����ַ�������ʱ�����������", e);
			return prizeResult;
		}
		return prizeResult;
	}

	/**
	 * 
	 * Title: lotteryBetToSingle<br>
	 * Description: <br>
	 * ����Ͷע��������ע���ͽ�Ͷע��λΪ�֣����԰�����ʽͶע<br>
	 * �÷���δУ��Ͷע����ʽ�Ƿ���ȷ,ʹ�ø÷�������Ҫ�ȱ�֤Ͷע��ʽ����ȷ<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @param playType
	 *            �淨id
	 * @param betType
	 *            Ͷע��ʽid
	 * @param betCode
	 *            Ͷע�����ַ���
	 * @return ����Ͷעע���Ľ���'#'���ŷָ��ʽΪ��Ͷעע��#Ͷע���������null���ʾ��������������
	 */
	public static String lotteryBetToSingle(int lotteryId, int playType, int betType, String betCode){
		String result = null;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).lotterySplit(String.valueOf(playType), String.valueOf(betType), betCode);
		}catch(LotteryUnDefineException e){
			logger.error("���Ͷע��ʱδ�ҵ���Ӧ���淨��Ͷע��ʽ", e);
		}catch(Exception e){
			logger.error("���Ͷע��ʱ�����������", e);
		}
		return result;
	}

	/**
	 * 
	 * Title: lotteryToSingleList<br>
	 * Description: <br>
	 * ��Ͷעת���Ϊ��ʽ�ļ��ϣ�Ͷע��λΪ�֣����԰�����ʽͶע<br>
	 * �÷���δУ��Ͷע����ʽ�Ƿ���ȷ,ʹ�ø÷�������Ҫ�ȱ�֤Ͷע��ʽ����ȷ<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @param playType
	 *            �淨id
	 * @param betType
	 *            Ͷע��ʽid
	 * @param betCode
	 *            Ͷע�����ַ���
	 * @return ���ص�ʽͶע�ļ���,�����������쳣�򷵻�null
	 */
	public static List<String> lotteryBetToSingleList(int lotteryId, int playType, int betType, String betCode){
		List<String> result = null;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).lotteryToSingle(String.valueOf(playType), String.valueOf(betType), betCode);
		}catch(LotteryUnDefineException e){
			logger.error("���Ͷע��ʱδ�ҵ���Ӧ���淨��Ͷע��ʽ", e);
		}catch(Exception e){
			logger.error("���Ͷע��ʱ�����������", e);
		}
		return result;
	}

	/**
	 * Title: lotteryRandomBetCode<br>
	 * Description: <br>
	 * �������ĳһ���ֵĵ�ʽ����һע<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @return ���ز��������Ͷעע�롣����null����ַ���Ϊ�������ע��ʧ��
	 */
	public static String lotteryRandomBetCode(int lotteryId){
		String result = null;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).lotteryRandomBetCode();
		}catch(Exception e){
			logger.error("��������ʽһע(" + lotteryId + ")ʱ�����������", e);
		}
		return result;
	}

	/**
	 * 
	 * Title: accountTransactionRemark<br>
	 * Description: <br>
	 * ��ʽ�����ױ�ע��Ϣ<br>
	 * 
	 * @param lotteryId
	 *            ����id
	 * @param termNO
	 *            ����
	 * @param type
	 *            ���� 10002��Ͷע��ǰ�ڣ�20002:Ͷע׷�ţ�31006:׷�ų���,11002:�ɽ���31117:�޺�׷�ų���
	 * @return ת����ı�ע��Ϣ
	 */
	public static String accountTransactionRemark(int lotteryId, String termNO, int type){
		String remark = "";
		switch(type){
			case 10002:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ڹ���";
				break;
			case 20002:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "׷��";
				break;
			case 31006:
				remark = "����ԭ��:�н�����";
				break;
			case 11002:
				remark = LotteryTools.getLotteryName(lotteryId) + "�ɽ�";
				break;
			case 30001:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ڹ���";
				break;
				//���泯���ӵ��޺�ȡ���ĳ���ԭ��ֱ����String�浽���ݿ��У�ȡ��ʱ��ֱ�Ӵ����ݿ���ȡ��
			case 31117:
				remark = "����ԭ��:�޺ų���";
				break;
			case 20003:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ڷ�����򶳽�";
				break;
			case 20009:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ں��򱣵׶���";
				break;
			case 31009:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ں��򱣵׳���";
				break;
			case 30002:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ں���Ͷע";
				break;
			case 20005:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ڲ�����򶳽�";
				break;
			case 31005:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ڲ��볷��";
				break;
			case 31003:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ڷ�������";
				break;
			case 30006:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "�ڱ���תͶע";
				break;
			case 31010:
				remark = LotteryTools.getLotteryName(lotteryId) + termNO + "��Ʊʧ�ܶ����˿�";
				break;
			default:
				break;
		}
		return remark;
	}

	/**
	 * 
	 * Title: getLotterySuperPrizeMoney<br>
	 * Description: <br>
	 * ��ȡ���ֽ��𼶱���<br>
	 * 
	 * @param lotteryId
	 * @return <br>
	 *         null δ����
	 */
	public static String getLotterySuperPrizeMoney(int lotteryId){
		return AutoProperties.getString(lotteryId + ".superPrizeMoney", "1000000", lotteryConfig);
	}

	/**
	 * ȡĳһλ��ֵ
	 * 
	 * @param intStr
	 * @param pos
	 * @return ת���������
	 */
	private static int getIntegerCharAt(Integer intStr, int pos){
		char[] c = String.valueOf(intStr).toCharArray();
		return Integer.parseInt(String.valueOf(c[pos - 1]));
	}

	/**
	 * �Ӳ����б�ĵĲ������ȡ����ָ��λ�õ�ָ����ʶ��Map���ϣ����Ϸ��ص���Hashtable
	 * 
	 * @param pos
	 *            ָ��λ��
	 * @param lotteryOrgan
	 *            ָ����ʶ
	 * @return Hashtable<Integer, String>
	 */
	private static Map<Integer, String> getLotteryByPos(int pos, int lotteryOrgan){
		Map<Integer, String> retLotteryMap = new Hashtable<Integer, String>();
		Map<Integer, String> lotteryMap = getLotteryList();
		for(Map.Entry<Integer, String> entry : lotteryMap.entrySet()){
			if(getIntegerCharAt(entry.getKey(), pos) == lotteryOrgan){
				retLotteryMap.put(entry.getKey(), entry.getValue());
			}
			;
		}
		return retLotteryMap;
	}

	/**
	 * �������ݴ������ļ��ж�ȡ���淨��ʽ�ַ��������ݶ���Ĺ�����Ϊ�淨���ϣ������淨id��name<br>
	 * ���صļ���Ϊ�߳�ͬ����<br>
	 * 
	 * @param playStr
	 * @return Map<Integer,String> �淨���� �������ֱ�Ϊ<�淨id���淨����>
	 */
	private static Map<Integer, String> splitPlayStrForPlayMap(String playStr){
		Map<Integer, String> lotteryPlay = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());
		String[] lotteryPlayArr = playStr.split("\\|");// �������淨��ʽ�ֿ�
		String[] tmpSub;
		for(String sub : lotteryPlayArr){
			String[] sub1 = sub.split("&");// ���淨��ʽ��Ͷע��ʽ�ֿ�
			tmpSub = sub1[0].split(",");// ���淨��ʽ���淨���Ʒֿ�
			Integer playId = Integer.valueOf(tmpSub[0]);
			if(tmpSub.length < 2){
				lotteryPlay.put(playId, "");// �ֱ����淨��ʽid��name
			}else{
				String playName = tmpSub[1];
				lotteryPlay.put(playId, playName);// �ֱ����淨��ʽid��name
			}
		}
		return lotteryPlay;
	}

	/**
	 * 
	 * ���ݴ������ļ��ж�ȡ���淨��ʽ�ַ��������ݶ���Ĺ�����Ϊ�淨��Ӧ��Ͷע��ʽ����<br>
	 * ���صļ���Ϊ�߳�ͬ����<br>
	 * 
	 * @param playStr
	 * @return Map<Integer, Map<Integer,String>> �淨��Ӧ��Ͷע��ʽ���ϣ������ֱ�Ϊ
	 *         <�淨id��<Ͷע��ʽid��Ͷע��ʽ����>>
	 */
	private static Map<Integer, Map<Integer, String>> splitPlayStrForPlayBet(String playStr){
		Map<Integer, Map<Integer, String>> lotteryPlayBetList = Collections.synchronizedSortedMap(new TreeMap<Integer, Map<Integer, String>>());
		String[] lotteryPlayArr = playStr.split("\\|");// �������淨��ʽ�ֿ�
		String[] tmpSub;
		for(String sub : lotteryPlayArr){
			String[] sub1 = sub.split("&");// ���淨��ʽ��Ͷע��ʽ�ֿ�
			tmpSub = sub1[0].split(",");// ���淨��ʽ���淨���Ʒֿ�
			Integer playId = Integer.valueOf(tmpSub[0]);
			tmpSub = sub1[1].split(";");// ������Ͷע��ʽ�ֿ�
			Map<Integer, String> bet = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());
			for(String betPlay : tmpSub){
				String[] subBetPlay = betPlay.split(",");
				bet.put(Integer.valueOf(subBetPlay[0]), subBetPlay[1]);
			}
			lotteryPlayBetList.put(playId, bet);
		}
		return lotteryPlayBetList;
	}

	/**
	 * ����µ���©�ַ�������©�ַ����洢����ο�����״̬��ʽ���Ķ����ĵ���
	 * @param lotteryId
	 * 		����id
	 * @param lotteryResult
	 * 		�������
	 * @param lastMissCount
	 * 		���һ����©
	 * @return
	 * 		�µ���©�ַ���
	 */
	public static String getMissCount(int lotteryId, String lotteryResult, String lastMissCount){
		String result = null;
		try{
			result = LotteryFactory.factory(String.valueOf(lotteryId)).getMissCount(lotteryResult, lastMissCount);
		}catch(Exception e){
			logger.error("�����©�ַ���(" + lotteryId + ")ʱ�����������", e);
			if(logger.isDebugEnabled()){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static List<WinResult> splitWinResult(int lotteryId, LotteryInfo info){
		List<WinResult> result = null;
		try{
			if(info != null){
				result = new ArrayList<WinResult>();
				switch (lotteryId) {
				case 1000001:
					TreeMap<String,TreeMap<String,String[]>>  superWinResult = info.getSuperWinResult();
					if(superWinResult != null){
						for(Map.Entry<String, TreeMap<String,String[]>> one : superWinResult.entrySet()){
							WinResult win = new WinResult();
							String key = one.getKey();
							Map<String,String[]> value = one.getValue();
							win.setWinId(Integer.parseInt(key));
							win.setWinCount(value.get("A")[0]);
							win.setWinMoney(value.get("A")[1]);
							if(value.get("B") != null){
								win.setWinAddCount(value.get("B")[0]);
								win.setWinAddMoney(value.get("B")[1]);
							}
							result.add(win);
						}
					}
					
					break;
				case 1000005:
					String [] happyZodiacWinResult = info.getHappyZodiacWinResult();
					if(happyZodiacWinResult != null){
						WinResult win = new WinResult();
						win.setWinId(1);
						win.setWinCount(happyZodiacWinResult[0]);
						win.setWinMoney(happyZodiacWinResult[1]);
						result.add(win);
					}
					break;
				case 1000002:
					TreeMap<String,String[]> sevenColorWinResult = info.getSevenColorWinResult();
					if (sevenColorWinResult != null) {
						for (Map.Entry<String, String[]> one : sevenColorWinResult
								.entrySet()) {
							WinResult win = new WinResult();
							win.setWinId(Integer.parseInt(one.getKey()));
							win.setWinCount(one.getValue()[0]);
							win.setWinMoney(one.getValue()[1]);
							result.add(win);
						}
					}
					break;
				case 1000003:
					TreeMap<String,String[]> arrangeThreeWinResult = info.getArrangeThreeWinResult();
					if(arrangeThreeWinResult != null){
						for(Map.Entry<String, String[]> one : arrangeThreeWinResult.entrySet()){
							WinResult win = new WinResult();
							win.setWinId(Integer.parseInt(one.getKey()));
							win.setWinCount(one.getValue()[0]);
							win.setWinMoney(one.getValue()[1]);
							result.add(win);
						}
					}
					
					break;
				case 1000004:
					String [] arrangeFiveWinResult = info.getArrangeFiveWinResult();
					if(arrangeFiveWinResult != null){
						WinResult win = new WinResult();
						win.setWinId(1);
						win.setWinCount(arrangeFiveWinResult[0]);
						win.setWinMoney(arrangeFiveWinResult[1]);
						result.add(win);
					}
					break;
				case 1300001:
					TreeMap<String,String[]> winOrFailWinResult = info.getWinOrFailWinResult();
					if(winOrFailWinResult != null){
						for(Map.Entry<String, String[]> one : winOrFailWinResult.entrySet()){
							WinResult win = new WinResult();
							win.setWinId(Integer.parseInt(one.getKey()));
							win.setWinCount(one.getValue()[0]);
							win.setWinMoney(one.getValue()[1]);
							result.add(win);
						}
					}
					break;
				case 1300002:
					String [] arbitry9WinResult = info.getArbitry9WinResult();
					if(arbitry9WinResult != null){
						WinResult win = new WinResult();
						win.setWinId(1);
						win.setWinCount(arbitry9WinResult[0]);
						win.setWinMoney(arbitry9WinResult[1]);
						result.add(win);
					}
					break;
				case 1300003:
					break;
				case 1300004:
					break;
				case 1200001:
					break;
				case 1200002:
					break;
				default:
					break;
				} 
				Collections.sort(result);
			}
			
		}catch(RuntimeException e){
			String errorReason = "LotteryTools.splitWinResult��������ֽ���������" + "lotteryId = " + lotteryId ;
			logger.error(errorReason, e);
		}
		return result;
	}
	
	public static List<SalesResult> splitSalesInfo(int lotteryId, LotteryInfo info){
		List<SalesResult> result = null;
		if(info != null){
			result = new ArrayList<SalesResult>();
			Map<Integer, Map<String,String>> SaleInfo = null;
			switch (lotteryId) {
			case 1300001:
				SaleInfo = info.getWinOrFailSaleInfo();
				break;
			case 1300002:
				SaleInfo = info.getArbitrary9SaleInfo();
				break;
			default:
				break;
			}
			if(SaleInfo != null){
				for(Map.Entry<Integer, Map<String,String>> one : SaleInfo.entrySet()){
					SalesResult sale = new SalesResult();
					sale.setSlaesId(one.getKey());
					sale.setHomeTeam(one.getValue().get("A"));
					sale.setAwayTeam(one.getValue().get("B"));
					sale.setLeagueMatch(one.getValue().get("C"));
					sale.setGameDate(one.getValue().get("D"));
					result.add(sale);
				}
			}
			Collections.sort(result);
		}
		return result;
	}
	/**
	 * 
	 * Title: mergeLimitResult<br>
	 * Description: <br>
	 *              <br>�޺���Ϣ���Ϻϲ�Ϊ���ݿⶨ��ĸ�ʽ
	 * @param lotteryId
	 * @param limitInfo
	 * @return
	 */
	public static String mergeLimitResult(int lotteryId,Map<String,String>limitInfo){
		String limitStr = null;
		try{
			if(limitInfo != null){
				switch (lotteryId) {
				case 1000003://����3
					limitStr = limitInfo.get("1");
					break;
				case 1200001://�������ֲ�
					StringBuffer sb = new StringBuffer();
					for(Map.Entry<String, String> entry : limitInfo.entrySet()){
						sb.append(entry.getKey()).append("-").append(entry.getValue()).append("|");
					}
					if(sb.toString().endsWith("|")){
						sb.deleteCharAt(sb.lastIndexOf("|"));
					}
					limitStr = sb.toString();
					break;
				default:
					break;
				}
			}
		}catch(Exception e){
			String errorReason = "LotteryTools.smergeLimitResult�������ϲ��޺���Ϣ����" + "lotteryId = " + lotteryId ;
			logger.error(errorReason, e);
		}
		return limitStr;
	}
	/**
	 * 
	 * Title: splitLimitResult<br>
	 * Description: <br>
	 *              <br>����޺��ַ���
	 * @param lotteryId
	 * @param limitStr
	 * @return
	 */
	public static Map<String,String> splitLimitResult(int lotteryId,String limitStr){
		Map<String,String> result = null;
		try{
			if(StringUtils.isNotEmpty(limitStr)){
				switch (lotteryId) {
				case 1000003:
					result = new TreeMap<String,String>();
					result.put("1", limitStr);
					break;
				case 1200001:
					result = new TreeMap<String,String>();
					String [] limitPool = limitStr.split("\\|");
					if(limitPool != null && limitPool.length != 0){
						for(String limit : limitPool){
							String [] limitOne = limit.split("-");
							if(limitOne != null && limitOne.length == 2){
								result.put(limitOne[0], limitOne[1]);
							}
						}
					}
					break;
				default:
					break;
				}
			}
		}catch(Exception e){
			String errorReason = "LotteryTools.splitLimitResult����������޺���Ϣ����" + "lotteryId = " + lotteryId ;
			logger.error(errorReason, e);
		}
		return result;
	}
	/**
	 * 
	 * Title: getLotteryEhand<br>
	 * Description: <br>
	 *              <br>�������ļ���ȡͶעϵͳ��������ϵͳ�Ĳ��ֶ�Ӧ��ϵ
	 * @return
	 */
	public static Map<Integer, String> getLotteryEhand(){
		Map<Integer, String> lotteryMap = new TreeMap<Integer, String>();
		try{
			String lotteryStr = AutoProperties.getString("LotteryEhand", "", lotteryConfig);
			String[] lotteryArry = lotteryStr.split(LOTTERYlIST_SEPARATOR);
			for(String str : lotteryArry){
				String[] subLottery = str.split(",");
				lotteryMap.put(Integer.parseInt(subLottery[0]), subLottery[1]);
			}
		}catch(Exception e){
			logger.debug("LotteryTools.getLotteryEhand occur Exception:" + e.getMessage());
		}
		return lotteryMap;
	}
	/**
	 * 
	 * Title: getCoopPercent<br>
	 * Description: <br>
	 *              <br>��ȡ�����ֶ���ĺ���������ɱ���
	 * @param lotteryId
	 * @param betType
	 * @return
	 */
	public static String getCoopPercent(int lotteryId,int betType){
		try{
			return AutoProperties.getString(lotteryId+"."+betType, "0-0", lotteryConfig);
		}catch(Exception e){
			return "0-0";
		}
	}
	/**
	 * 
	 * Title: getJoinRevocate<br>
	 * Description: <br>
	 *              <br>��ȡ�����ֺ�������˲��ܳ����ķ�������
	 * @param lotteryId
	 * @return
	 */
	public static int getJoinRevocate(int lotteryId){
		try{
			return AutoProperties.getInt(String.valueOf(lotteryId)+".joinRevocate", 0, lotteryConfig);
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * 
	 * Title: getCreateRevocate<br>
	 * Description: <br>
	 *              <br>��ȡ�����ֺ������˲��ܳ����ķ�������
	 * @param lotteryId
	 * @return
	 */
	public static int getCreateRevocate(int lotteryId){
		try{
			return AutoProperties.getInt(String.valueOf(lotteryId)+".createRevocate", 0, lotteryConfig);
		}catch(Exception e){
			return 0;
		}
	}
	/**
	 * 
	 * Title: getSysSequencePre<br>
	 * Description: <br>
	 *              <br>��ȡϵͳ��ŵ�ǰ׺���������ֲ�ͬ��ϵͳ����ظ�����
	 * @return
	 */
	public static String getSysSequencePre(){
		try{
			return AutoProperties.getString("LotterySEQUENCE", "W", lotteryConfig);
		}catch(Exception e){
			return "W";
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// System.out.println(LotteryTools.getCheckCron(1000001));
		// Integer t = 100001;
		// Map<Integer,String> tmp =
		// getLotteryPlayList(1000001).getLotteryPlayMap();
		// Map<Integer,String> tmp = Collections.synchronizedSortedMap(new
		// TreeMap<Integer,String>());
		// ���Բ����ʻ򾹲µĿ�Ͷע����ر�����Ϣ;
		
		 // String tt = "2,B-��ɭ��&A-�ж���,����1,201004121010|1,B-��������&A-AC����,����2,201004121010"; 
		 // System.out.println(tt);
		 // System.out.println(splitSalesInfo(tt));
		  //System.out.println(mergeSalesInfo(splitSalesInfo(tt)));
		  //System.out.println
		  //(splitSalesInfo(mergeSalesInfo(splitSalesInfo(tt))));
		/*  
		  Map<Integer,Map<String,String>> resultMap = new HashMap<Integer,Map<String,String>>();
		  
		  HashMap<String,String> map1 = new HashMap<String,String>();
		  map1.put("B", "��ɭ��"); 
		  map1.put("A", "�ж���");
		  map1.put("C", "����1");
		  map1.put("D", "2010");
		  resultMap.put(2, map1);
		  
		  HashMap<String,String> map2 = new HashMap<String,String>();
		  map2.put("B", "��������");
		  map2.put("A", "AC����"); 
		  resultMap.put(1,map2);
		  
		  //System.out.println(resultMap);
		  System.out.println(mergeSalesInfo(resultMap));
		 */
		/*
		 * //���Դ���͸Ͷע��ʽ //��ʽ String betGroup = "0102030405|0607^0109101112|0809";
		 * 
		 * //��ʽ String multiBet = "010203040506|0607^0109101112|0809"; boolean
		 * result = checkLotteryBetFormat(1000001, 0, 1, multiBet);
		 * System.out.println("result==="+result);
		 * 
		 * 
		 * //���Դ���͸��ʽת��ʽ
		 * 
		 * List<String> superList = LotteryTools.lotteryBetToSingleList(1000001,
		 * 0, 1, multiBet); for(String bet : superList){
		 * System.out.println("bet:"+bet); }
		 */
		/*
		 * //���Դ���͸���� String betGroup = "0102*030405|*020304"; boolean result =
		 * checkLotteryBetFormat(1000001, 0, 2, betGroup);
		 * System.out.println("result==="+result);
		 * 
		 * List<String> superList = LotteryTools.lotteryBetToSingleList(1000001,
		 * 0, 2, betGroup); for(String bet : superList){
		 * System.out.println("bet:"+bet); }
		 */
		// ���Դ���͸����ת��ʽ
		/*
		 * //���Բ��ͺϲ������� int lotteryId = 1000001; String winResult =
		 * "1-A10&A1971731#B4&B1183038|2-A54&A12000#B9&B72|3-A54&A10000#B9&B72|4-A54&A9000#B9&B72|5-A54&A6000#B9&B72|6-A54&A5000#B9&B72|7-A54&A4000#B9&B72|8-A54&A2000"
		 * ; LotteryInfo info = splitWinResult(1000001,winResult);
		 * TreeMap<String,TreeMap<String,String[]>> superWin =
		 * info.getSuperWinResult(); for(Map.Entry<String,
		 * TreeMap<String,String[]>> map : superWin.entrySet()){
		 * System.out.println("#########################"); String key =
		 * map.getKey(); System.out.println("key === "+key);
		 * TreeMap<String,String[]> map2 = map.getValue(); for(Map.Entry<String,
		 * String[]> map3 : map2.entrySet()){ String key2 = map3.getKey();
		 * System.out.println("key2 === "+key2); String [] value =
		 * map3.getValue(); System.out.println("value[0] === "+value[0]);
		 * System.out.println("value[1] === "+value[1]); }
		 * System.out.println("#########################"); } LotteryInfo info2
		 * = new LotteryInfo(); info2.setSuperWinResult(superWin); String
		 * backWinResult = mergeWinResult(1000001, info2);
		 * System.out.println("winResult    =:"+winResult);
		 * System.out.println("backWinResult=:"+backWinResult);
		 */
		 //���Դ���͸����
//		 int lotteryId = 1000001;
//		 int playType = 0;
//		 int betType = 1;
//		 String lotteryResult = "0712263032|0207";
//		 String winResult =
//		 "1-A10&A1971731#B4&B10|2-A54&A12000#B9&B20|3-A54&A10000#B9&B30|4-A54&A9000#B9&B40|5-A54&A6000#B9&B50|6-A54&A5000#B9&B60|7-A54&A4000#B9&B70|8-A54&A2000";
//		 LotteryInfo info = splitWinResult(1000001,winResult);
//		 TreeMap<String,TreeMap<String,String[]>> superWin = info.getSuperWinResult();
				
//		 for(Map.Entry<String, TreeMap<String,String[]>> map :superWin.entrySet()){
//		 System.out.println("#########################");
//		 String key = map.getKey();
//		 System.out.println("key === "+key);
//		 TreeMap<String,String[]> map2 = map.getValue();
//		 for(Map.Entry<String, String[]> map3 : map2.entrySet()){
//		 String key2 = map3.getKey();
//		 System.out.println("key2 === "+key2);
//		 String [] value = map3.getValue();
//		 System.out.println("value[0] === "+value[0]);
//		 System.out.println("value[1] === "+value[1]);
//		 }
//		 System.out.println("#########################");
//		 }
				
//		 LotteryInfo info2 = new LotteryInfo();
//		 info2.setSuperWinResult(superWin);
//		 String backWinResult = mergeWinResult(1000001, info2);
		 //System.out.println("winResult    =:"+winResult);
		 //System.out.println("backWinResult=:"+backWinResult);
//		 String betCode = "07,09,30,34,35|06,07,11";
//		 List<List<String>> result = lotteryPrize(lotteryId,playType,betType,lotteryResult,winResult,betCode);
//				
//		 int [] resultArr = new int[]{0,0,0,0,0,0,0,0,0};
//				
//		 for(List subList : result){
//		 System.out.println("�н�����:"+subList.get(0));
//		 System.out.println("�н�����:"+subList.get(1));
//		 System.out.println("�н����:"+subList.get(2));
//		 System.out.println("׷�ӽ��:"+subList.get(3));
//		 System.out.println("#################################");
//		 resultArr[Integer.parseInt(String.valueOf(subList.get(1)))]++;
//		 }
//		 System.out.println("*********************************");
//				
//		 for(int k = 1;k<resultArr.length;k++){
//		 System.out.println("["+k+"] �Ƚ� = "+resultArr[k]);
//		 }
		// ������Ф��
		// String betCode = "0101^0809";
		// String betCode = "0101";
		// boolean result = checkLotteryBetFormat(1000005, 0, 1, betCode);
		// System.out.println("result==="+result);
		//		
		// List<String> superList = LotteryTools.lotteryBetToSingleList(1000005,
		// 0, 1, betCode);
		// for(String bet : superList){
		// System.out.println("bet:"+bet);
		// }
		// String lotteryResult = "0102";
		// String winResult = "17276&60";
		//		
		// List<List<String>> prizeresult =
		// lotteryPrize(1000005,0,1,lotteryResult,winResult,betCode);
		// int [] resultArr = new int[]{0,0,0,0,0,0,0,0,0};
		//		
		// for(List subList : prizeresult){
		// System.out.println("�н�����:"+subList.get(0));
		// System.out.println("�н�����:"+subList.get(1));
		// System.out.println("�н����:"+subList.get(2));
		// System.out.println("׷�ӽ��:"+subList.get(3));
		// System.out.println("#################################");
		// resultArr[Integer.parseInt(String.valueOf(subList.get(1)))]++;
		// }
		// System.out.println("*********************************");
		//		
		// for(int k = 1;k<resultArr.length;k++){
		// System.out.println("["+k+"] �Ƚ� = "+resultArr[k]);
		// }
		//		
		// ��������3
		// String betCode = "2";
		// boolean result = checkLotteryBetFormat(1000003, 0, 6, betCode);
		// System.out.println("result==="+result);
		// List<String> arr3List = LotteryTools.lotteryBetToSingleList(1000003,
		// 0, 6, betCode);
		// for(String bet : arr3List){
		// System.out.println("bet:"+bet);
		// }
		// String betCode = "1*2*3*4*5";
		// boolean result = checkLotteryBetFormat(1000004, 0, 1, betCode);
		// System.out.println("result==="+result);
		// List<String> arr5List = LotteryTools.lotteryBetToSingleList(1000004,
		// 0, 1, betCode);
		// for(String bet : arr5List){
		// System.out.println("bet:"+bet);
		// }
		// String betCode = "3*1*1*3*3*1*0*3*3*4*4*4*4*4";
		// boolean result = checkLotteryBetFormat(1300002, 0, 1, betCode);
		// System.out.println("result==="+result);
		// List<String> arr5List = LotteryTools.lotteryBetToSingleList(1300002,
		// 0, 1, betCode);
		// for(String bet : arr5List){
		// System.out.println("bet:"+bet);
		// }
		//		
		// String lotteryResult = "31133103333231";
		// String winResult = "9491&1696";
		// List<List<String>> prizeresult =
		// lotteryPrize(1300002,0,1,lotteryResult,winResult,betCode);
		// int [] resultArr = new int[]{0,0,0,0,0,0,0,0,0};
		//		
		// for(List subList : prizeresult){
		// System.out.println("�н�����:"+subList.get(0));
		// System.out.println("�н�����:"+subList.get(1));
		// System.out.println("�н����:"+subList.get(2));
		// System.out.println("׷�ӽ��:"+subList.get(3));
		// System.out.println("#################################");
		// resultArr[Integer.parseInt(String.valueOf(subList.get(1)))]++;
		// }
		// System.out.println("*********************************");
		//		
		// for(int k = 1;k<resultArr.length;k++){
		// System.out.println("["+k+"] �Ƚ� = "+resultArr[k]);
		// }
		// int lotteryId = 1000003;
		// int playType = 0;
		// int betType = 1;
		// String lotteryResult = "3793744";
		// String winResult =
		// "1-1&5000000|2-25&50|3-25&40|4-25&30|5-25&20|6-25&10";
		// String betCode = "23*7*8*5*7*54*4";
		//		
		// List<List<String>> result =
		// lotteryPrize(lotteryId,playType,betType,lotteryResult,winResult,betCode);
		// int [] resultArr = new int[]{0,0,0,0,0,0,0};
		// for(List subList : result){
		// System.out.println("�н�����:"+subList.get(0));
		// System.out.println("�н�����:"+subList.get(1));
		// System.out.println("�н����:"+subList.get(2));
		// System.out.println("׷�ӽ��:"+subList.get(3));
		// System.out.println("#################################");
		// resultArr[Integer.parseInt(String.valueOf(subList.get(1)))]++;
		// }
		// System.out.println("*********************************");
		//		
		// for(int k = 1;k<resultArr.length;k++){
		// System.out.println("["+k+"] �Ƚ� = "+resultArr[k]);
		// }
		// //���Դ���͸�����������������ѡ��ʽһע
		// for(int i = 0; i < 20; i++){
		// System.out.println(LotteryTools.lotteryRandomBetCode(1000001));
		// }
		//		
		// for(int i = 0; i < 20; i++){
		// System.out.println(LotteryTools.lotteryRandomBetCode(1000003));
		// }
		// for(int i = 0; i < 20; i++){
		// System.out.println(LotteryTools.lotteryRandomBetCode(1000004));
		// }
//		for(int i = 0; i < 20; i++){
//			System.out.println(LotteryTools.lotteryRandomBetCode(1000002));
//		}
		//		
		// System.out.println(LotteryTools.getLotteryPlayTypeList(1000001));
		// System.out.println(LotteryTools.getLotteryPlayBetTypeList(1000001,
		// 0));
		// System.out.println("---------------------------------------");
		// System.out.println(LotteryTools.getLotteryPlayBetTypeList(1000001,
		// 1));
		// System.out.println(LotteryTools.checkLotteryBetFormat(1000001, 0, 0,
		// ""));
		  
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 0, 2));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 1, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 1, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000001) + LotteryTools.getPlayBetName(1000001, 1, 2));
//		  System.out.println(LotteryTools.getLotteryName(1000002) + LotteryTools.getPlayBetName(1000002, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000002) + LotteryTools.getPlayBetName(1000002, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 2));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 3));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 4));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 5));
//		  System.out.println(LotteryTools.getLotteryName(1000003) + LotteryTools.getPlayBetName(1000003, 0, 6));
//		  System.out.println(LotteryTools.getLotteryName(1000004) + LotteryTools.getPlayBetName(1000004, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000004) + LotteryTools.getPlayBetName(1000004, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1000005) + LotteryTools.getPlayBetName(1000005, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1000005) + LotteryTools.getPlayBetName(1000005, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1300001) + LotteryTools.getPlayBetName(1300001, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1300001) + LotteryTools.getPlayBetName(1300001, 0, 1));
//		  System.out.println(LotteryTools.getLotteryName(1300002) + LotteryTools.getPlayBetName(1300002, 0, 0));
//		  System.out.println(LotteryTools.getLotteryName(1300002) + LotteryTools.getPlayBetName(1300002, 0, 1));

		
		  String multiBet = "0304*020506070810";
		  boolean result = checkLotteryBetFormat(1200001, 12, 2, multiBet);
		  System.out.println("result==="+result);
		  
	}
}
