package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;

import com.success.lottery.util.LotteryInfo;
import com.success.lottery.util.LotteryTools;
/**
 * 
 * com.success.lottery.util.core SuperLottery.java SuperLottery ���֡���������͸��������
 * 
 * @author gaoboqin 2010-3-26 ����11:33:04
 * 
 */
public class SuperLottery extends LotteryAbstractTool implements LotteryInterf{

	/*
	 * �����н�����
	 */
	private static Map<String, String>	winRules;
	static{
		winRules = new HashMap<String, String>();
		winRules.put("52", "1");// 1�Ƚ�
		winRules.put("51", "2");// 2�Ƚ�
		winRules.put("50", "3");// 3�Ƚ�
		winRules.put("42", "4");// 4�Ƚ�
		winRules.put("41", "5");// 5�Ƚ�
		winRules.put("32", "6");// 6�Ƚ�
		winRules.put("40", "6");// 6�Ƚ�
		winRules.put("31", "7");// 7�Ƚ�
		winRules.put("22", "7");// 7�Ƚ�
		winRules.put("30", "8");// 8�Ƚ�
		winRules.put("12", "8");// 8�Ƚ�
		winRules.put("21", "8");// 8�Ƚ�
		winRules.put("02", "8");// 8�Ƚ�
	}

	/**
	 * 
	 * ���ݴ���Ĳ����淨id��Ͷע��ʽid��Ͷע�ַ�������У����� У��Ͷע�ַ����Ƿ���ȷ
	 * 
	 * @param playType
	 *            �淨id
	 * @param betType
	 *            Ͷע��ʽid
	 * @param betString
	 *            Ͷע�ַ���
	 * @return boolean true Ͷע�ַ�������Ͷע��ʽ��false Ͷע�ַ���������Ͷע��ʽ
	 * 
	 * @throws LotteryUnDefineException
	 *             ,Exception ���û�ж�Ӧ���淨�Ͷ�Ӧ��Ͷע��ʽ���׳�LotteryUnDefineException
	 */
	public boolean checkBetType(String playType, String betType, String betString) throws LotteryUnDefineException, Exception{
		if(SUPER_PLATTYPE_ADDITIONAL.equals(playType)){
			return checkBet(betType, betString);
		}else if(SUPER_PLATTYPE_NO_ADDITIONAL.equals(playType)){
			return checkBet(betType, betString);
		}else{
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
	}

	/*
	 * (�� Javadoc) Title: mergeWinResult Description: @param info @return
	 * 
	 * @see
	 * com.success.lottery.util.core.LotteryInterf#mergeWinResult(com.success.
	 * lottery.util.LotteryInfo)
	 */
	public String mergeWinResult(LotteryInfo info){
		TreeMap<String, TreeMap<String, String[]>> winResult = info.getSuperWinResult();
		StringBuffer result = new StringBuffer();
		boolean flag = false;
		for(Map.Entry<String, TreeMap<String, String[]>> entry : winResult.entrySet()){
			boolean flag1 = false;
			String key = entry.getKey();
			if(flag){
				result.append("|");
			}else{
				flag = true;
			}
			result.append(key);// ����
			result.append("-");
			TreeMap<String, String[]> baseAdd = entry.getValue();//
			for(Map.Entry<String, String[]> base : baseAdd.entrySet()){
				String key1 = base.getKey();// A
				String[] value = base.getValue();//
				String zhu = key1 + value[0];
				String jiner = key1 + value[1];
				if(flag1){
					result.append("#");
				}else{
					flag1 = true;
				}
				result.append(zhu);
				result.append("&");
				result.append(jiner);
			}
		}
		return result.toString();
	}

	/*
	 * (�� Javadoc) Title: splitWinResult Description: @param winResult @return
	 * 
	 * <һ�Ƚ����˵Ƚ�(������1-8��ʾ),<��������׷�ӽ�,[ע��,���]>>
	 * TreeMap<String,TreeMap<String,String[]>>
	 * 1-A10&A1971731#B4&B1183038|2-A54&12000#B9&B72 8�Ƚ����û��#B9&B72
	 * 
	 * @see
	 * com.success.lottery.util.core.LotteryInterf#splitWinResult(java.lang.String
	 * )
	 */
	public LotteryInfo splitWinResult(String winResult, LotteryInfo info){
		TreeMap<String, TreeMap<String, String[]>> result = new TreeMap<String, TreeMap<String, String[]>>();
		String[] prizeArr = winResult.split("\\|");
		for(String prizes : prizeArr){// ���ս���ֿ�,��Ϊһ�Ƚ���8�Ƚ�
			String[] onePrize = prizes.split("-");
			if(onePrize.length == 2){
				String[] basicAddArr;// ���ջ�����׷�ӷֿ�
				if(onePrize[0].equals("8")){
					basicAddArr = onePrize[1].split("#", -1);
				}else{
					basicAddArr = onePrize[1].split("#");
				}
				TreeMap<String, String[]> res0 = new TreeMap<String, String[]>();
				for(String bas : basicAddArr){
					String[] tmp = bas.split("&");// ����ע���ͽ����ֿ�
					String key = tmp[0].substring(0, 1);
					String value1 = tmp[0].substring(1, tmp[0].length());
					String value2 = tmp[1].substring(1, tmp[1].length());
					String[] res1 = {value1, value2};// ���ע���ͽ��
					res0.put(key, res1);
				}
				result.put(onePrize[0], res0);
			}
		}
		info.setSuperWinResult(result);
		return info;
	}

	/*
	 * (�� Javadoc) Title: mergeLotteryResult Description: 0102030405|0102 @param
	 * info @return
	 * 
	 * @see
	 * com.success.lottery.util.core.LotteryInterf#mergeLotteryResult(com.success
	 * .lottery.util.LotteryInfo)
	 */
	public String mergeLotteryResult(LotteryInfo info){
		TreeMap<String, ArrayList<String>> superLotteryResult = info.getSuperLotteryResult();
		StringBuffer result = new StringBuffer();
		boolean flag = false;
		for(Map.Entry<String, ArrayList<String>> entry : superLotteryResult.entrySet()){
			ArrayList<String> value = entry.getValue();
			if(flag){
				result.append("|");
			}else{
				flag = true;
			}
			result.append(mergeListToStr(value));
		}
		return result.toString();
	}

	/*
	 * (�� Javadoc) Title: splitLotteryResult Description: @param lotteryResult
	 * 
	 * @return
	 * 
	 * @see
	 * com.success.lottery.util.core.LotteryInterf#splitLotteryResult(java.lang
	 * .String)
	 */
	public LotteryInfo splitLotteryResult(String lotteryResult, LotteryInfo info){
		TreeMap<String, ArrayList<String>> superLotteryResult = new TreeMap<String, ArrayList<String>>();
		String[] lotteryArr = lotteryResult.split("\\|");
		if(lotteryArr.length == 2){
			ArrayList<String> before = (ArrayList<String>)splitStrByLen(lotteryArr[0], 2);
			ArrayList<String> end = (ArrayList<String>)splitStrByLen(lotteryArr[1], 2);
			superLotteryResult.put("1", before);
			superLotteryResult.put("2", end);
		}
		info.setSuperLotteryResult(superLotteryResult);
		return info;
	}
	
	/*
	 * (�� Javadoc)
	*Title: splitMissCount
	*Description: 
	* @param missCount
	* @param info
	* @return
	* @see com.success.lottery.util.core.LotteryInterf#splitMissCount(java.lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitMissCount(String missCount, LotteryInfo info) {
		Map<String,Integer> missCountResult = new TreeMap<String,Integer>();
		
		if(missCount != null){
			String [] groups = missCount.split("\\|");
			if(groups.length == 2){
				String [] beforeMiss = groups[0].split(",");
				String [] endMiss = groups[1].split(",");
				//����ǰ��
				for(int i = 0 ; i< beforeMiss.length;i++){
					missCountResult.put("H-"+String.format("%1$02d",i+1), Integer.parseInt(beforeMiss[i]));
				}
				
				//�������
				for(int k = 0 ;k< endMiss.length;k++){
					missCountResult.put("B-"+String.format("%1$02d",k+1), Integer.parseInt(endMiss[k]));
				}
			}
		}
		info.setMissCountResult(missCountResult);
		return info;
	}

	/*
	 * (�� Javadoc)Title: lotteryPrizeDescription: ��������͸���� �������׷�ӷ�ʽ��׷�ӽ��Ϊ0
	 * 
	 * @param playType �淨id
	 * 
	 * @param betType Ͷע��ʽid
	 * 
	 * @param lotteryResult �������
	 * 
	 * @param winReslut ������
	 * 
	 * @param betCode Ͷע�ַ���
	 * 
	 * @return List<List<String>> �н���Ϣ <<��עע��, ���𼶱�,������,׷�ӽ��>>
	 * 
	 * @see
	 * com.success.lottery.util.core.LotteryInterf#lotteryPrize(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<List<String>> lotteryPrize(String playType, String betType, String lotteryResult, String winResult, String betCode) throws LotteryUnDefineException, Exception{
		if(SUPER_PLATTYPE_NO_ADDITIONAL.equals(playType) || SUPER_PLATTYPE_ADDITIONAL.equals(playType)){
			return superPrize(playType, betType, lotteryResult, winResult, betCode);
		}else{
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
	}
	

	/*
	 * (�� Javadoc)Title: lotterySplitDescription:
	 * 
	 * @param playType
	 * 
	 * @param betType
	 * 
	 * @param betCode
	 * 
	 * @return
	 * 
	 * @throws LotteryUnDefineException
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.success.lottery.util.core.LotteryInterf#lotterySplit(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public String lotterySplit(String playType, String betType, String betCode) throws LotteryUnDefineException, Exception{
		long zhuNum = lotteryToSingle(playType, betType, betCode).size();
		return String.valueOf(zhuNum) + ZHUSIGN + String.valueOf(zhuNum * (SUPER_PLATTYPE_ADDITIONAL.equals(playType) ? ADDPRIZE : SINGLEPRIZE));
	}

	/*
	 * (�� Javadoc)Title: lotteryToSingleDescription:
	 * 
	 * @param playType
	 * 
	 * @param betType
	 * 
	 * @param betCode
	 * 
	 * @return
	 * 
	 * @throws LotteryUnDefineException
	 * 
	 * @throws Exception
	 * 
	 * @see
	 * com.success.lottery.util.core.LotteryInterf#lotteryToSingle(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	public List<String> lotteryToSingle(String playType, String betType, String betCode) throws LotteryUnDefineException, Exception{
		List<String> toSingle;
		if(SUPER_PLATTYPE_ADDITIONAL.equals(playType) || SUPER_PLATTYPE_NO_ADDITIONAL.equals(playType)){
			if(SUPER_BETTYPE_SINGLE.equals(betType)){
				toSingle = Arrays.asList(splitGroup(betCode, SUPER_BET_GROUP_SEPARATOR, false));
			}else if(SUPER_BETTYPE_DUPLEX.equals(betType)){
				toSingle = combineDuplex(betCode);
			}else if(SUPER_BETTYPE_DANTUO.equals(betType)){
				toSingle = combineSuperDanTuo(betCode);
			}else{
				throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
			}
		}else{
			throw new LotteryUnDefineException("δ�ҵ��淨��" + playType);
		}
		return toSingle;
	}

	/**
	 * 
	 * Title: superPrize Description: ����Ͷע��ʽ����
	 * 
	 * @param playType
	 *            �淨id
	 * @param betType
	 *            Ͷע��ʽid
	 * @param lotteryResult
	 *            ��������ַ���
	 * @param winResult
	 *            �������ַ���
	 * @param betCode
	 *            Ͷע�����ַ���
	 * @throws LotteryUnDefineException
	 * @throws Exception
	 *             δ�ҵ���Ӧ��Ͷע��ʽ
	 * @return List<List<String>> ��������
	 */
	private List<List<String>> superPrize(String playType, String betType, String lotteryResult, String winResult, String betCode) throws LotteryUnDefineException, Exception{
		if(SUPER_BETTYPE_SINGLE.equals(betType)){
			List<String> singleBet = Arrays.asList(splitGroup(betCode, SUPER_BET_GROUP_SEPARATOR, false));
			return singlePrize(playType, lotteryResult, winResult, singleBet);
		}else if(SUPER_BETTYPE_DUPLEX.equals(betType)){
			return duplexPrize(playType, lotteryResult, winResult, betCode);
		}else if(SUPER_BETTYPE_DANTUO.equals(betType)){
			return danTuoPrize(playType, lotteryResult, winResult, betCode);
		}else{
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
		}
	}

	/**
	 * 
	 * Title: singlePrize Description: �Ե�ʽͶע����
	 * 
	 * @param lotteryResult
	 *            �������
	 * @param winResult
	 *            ������
	 * @param singleBet
	 *            ��ʽͶעע�뼯��
	 * @return List<List<String>> <<��עע��, ���𼶱�,������>>
	 * @throws
	 */
	private List<List<String>> singlePrize(String playType, String lotteryResult, String winResult, List<String> singleBet){
		List<List<String>> prizeResult;// = new ArrayList<List<String>>();
		TreeMap<String, ArrayList<String>> lotteryResultMap = splitLotteryResult(lotteryResult, new LotteryInfo()).getSuperLotteryResult();// ����������
		TreeMap<String, TreeMap<String, String[]>> winResultMap = splitWinResult(winResult, new LotteryInfo()).getSuperWinResult();
		prizeResult = hitHaoMa(playType, singleBet, lotteryResultMap, winResultMap);
		return prizeResult;
	}

	/**
	 * 
	 * Title: duplexPrize Description: ��ʽͶע����
	 * 
	 * @param lotteryResult
	 * @param winResult
	 * @param betCode
	 * @return List<List<String>> ��������
	 * @throws
	 */
	private List<List<String>> duplexPrize(String playType, String lotteryResult, String winResult, String betCode){
		List<String> duplexToSingle = combineDuplex(betCode);
		return singlePrize(playType, lotteryResult, winResult, duplexToSingle);
	}

	private List<List<String>> danTuoPrize(String playType, String lotteryResult, String winResult, String betCode){
		List<String> danTuoToSingle = combineSuperDanTuo(betCode);
		return singlePrize(playType, lotteryResult, winResult, danTuoToSingle);
	}

	/**
	 * 
	 * Title: hitHaomao Description: ����ʽͶע���϶ҽ������صļ���ֻ�����н��ĺ����Լ��н��ļ���ͽ���
	 * 
	 * @param singleBet
	 * @param lotteryResultMap
	 * @param winResultMap
	 * @return List<List<String>> ��������
	 * @throws
	 */
	private List<List<String>> hitHaoMa(String playType, List<String> singleBet, TreeMap<String, ArrayList<String>> lotteryResultMap, TreeMap<String, TreeMap<String, String[]>> winResultMap){
		// Map<String,String> result = new HashMap<String,String>();
		List<List<String>> result = new ArrayList<List<String>>();
		ArrayList<String> beforeHao = lotteryResultMap.get("1");
		ArrayList<String> endHao = lotteryResultMap.get("2");
		for(String single : singleBet){
			String[] singleArr = single.split("\\|");
			List<String> beforeQu = splitStrByLen(singleArr[0], 2);
			List<String> endQu = splitStrByLen(singleArr[1], 2);
			String hitNum = hitHaoNum(beforeQu, beforeHao) + hitHaoNum(endQu, endHao);
			String zhongJangJiBie = winRules.get(hitNum);
			if(zhongJangJiBie != null){
				List<String> innerList = new ArrayList<String>();
				innerList.add(single);
				innerList.add(zhongJangJiBie);
				String jinEr = (winResultMap.get(zhongJangJiBie).get("A"))[1];// ȡ������
				innerList.add(jinEr);
				String addJinEr = "0";
				if(SUPER_PLATTYPE_ADDITIONAL.equals(playType) && !zhongJangJiBie.equals("8")){// 8�Ƚ�û��׷��
					addJinEr = (winResultMap.get(zhongJangJiBie).get("B"))[1];// ȡ׷�ӽ�
				}
				innerList.add(addJinEr);
				result.add(innerList);
			}
		}
		return result;
	}

	/**
	 * 
	 * Title: combineDuplex Description: ��һ����ʽͶע���ַ������Ϊ����Ͷע�ļ���
	 * 
	 * @param lotteryResult
	 *            ��ʽͶע���ַ��� 010203040523|060711^08091011122425|080901
	 * @return List<String> List<0102030405|0607>
	 * @throws
	 */
	private List<String> combineDuplex(String betCode){
		List<String> result = new ArrayList<String>();
		List<String[]> group = splitBetCode(betCode);
		for(String[] subGroup : group){
			List<String> combineBefore = new ArrayList<String>();
			List<String> combineEnd = new ArrayList<String>();
			if(subGroup.length == 2){
				List<String> beforeQu = splitStrByLen(subGroup[0], 2);
				List<String> endQu = splitStrByLen(subGroup[1], 2);
				combineBefore = combineToList(beforeQu, 5);
				combineEnd = combineToList(endQu, 2);
			}
			result.addAll(combineBeforeEnd(combineBefore, combineEnd, "|"));
		}
		return result;
	}

	/**
	 * 
	 * Title: combineDanTuo Description: ������Ͷע���ַ������Ϊ��ʽͶע�ļ���
	 * 
	 * @param lotteryResult
	 *            ����Ͷע�ַ���
	 * @return List<String> List<0102030405|0607>
	 * @throws
	 */
	private List<String> combineSuperDanTuo(String betCode){
		List<String> result = new ArrayList<String>();
		List<String[]> group = splitBetCode(betCode);// �ֿ�Ͷע��Ϊע,[010203*040506]
		// [01*0203]
		for(String[] subGroup : group){// ѭ�����е�ע,subGroup��ʾĳһס
			if(subGroup.length == 2){
				String before = subGroup[0];
				String end = subGroup[1];
				List<String> beforeList = combineDanTuo(before, 5, 2, "\\*");// ǰȥ��Ϻ�ļ���
				List<String> endList = combineDanTuo(end, 2, 2, "\\*");// ������Ϻ�ļ���
				List<String> singleList = combineBeforeEnd(beforeList, endList, "|");
				result.addAll(singleList);
			}
		}
		return result;
	}

	/**
	 * 
	 * Title: splitBetCode Description: ���Ͷע��Ϊ����
	 * 010203040523|060711^08091011122425|080901
	 * 
	 * @param lotteryResult
	 *            Ͷע�� 010203040523|060711^08091011122425|080901
	 * @return List<String[]> List<[010203040523,060711]>
	 * @throws
	 */
	private List<String[]> splitBetCode(String betCode){
		List<String[]> result = new ArrayList<String[]>();
		String[] betGroupByNum = splitGroup(betCode, SUPER_BET_GROUP_SEPARATOR, false);
		for(String group : betGroupByNum){
			String[] qu = group.split("\\|");
			result.add(qu);
		}
		return result;
	}

	/**
	 * 
	 * Title: checkBet Description: У��Ͷע��ʽ�Ƿ���ȷ
	 * 
	 * @param betType
	 *            Ͷע��ʽ
	 * @param betString
	 *            Ͷע�ַ���
	 * @return boolean true ��ʶƥ�� false ��ʾ��ƥ��
	 * @throws LotteryUnDefineException
	 *             ,Exception ���û�ж�Ӧ���淨�Ͷ�Ӧ��Ͷע��ʽ���׳�LotteryUnDefineException
	 */
	private boolean checkBet(String betType, String betString) throws LotteryUnDefineException, Exception{
		if(SUPER_BETTYPE_SINGLE.equals(betType)){
			return checkSingle(betString);
		}else if(SUPER_BETTYPE_DUPLEX.equals(betType)){
			return checkDuplex(betString);
		}else if(SUPER_BETTYPE_DANTUO.equals(betType)){
			return checkDanTuo(betString);
		}else{
			throw new LotteryUnDefineException("δ�ҵ�Ͷע��ʽ��" + betType);
		}
	}

	/**
	 * 
	 * Title: checkSingle Description: У�鵥ʽ
	 * 
	 * @param betString
	 * @return boolean ��������
	 * @throws
	 */
	private boolean checkSingle(String betString){
		boolean checkResult = true;
		String[] betGroupByNum = splitGroup(betString, SUPER_BET_GROUP_SEPARATOR, true);
		for(String group : betGroupByNum){
			if(!checkRules(SUPER_BET_0_REGULAR, group)){
				checkResult = false;
				break;
			}else{
				if(!isRightByQu(group)){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}

	/**
	 * 
	 * Title: checkDuplex Description: У�鸴ʽ ,��У���ʽ���ظ�
	 * 
	 * @param betString
	 * @return boolean ��������
	 * @throws
	 */
	private boolean checkDuplex(String betString){
		boolean checkResult = true;
		String[] betGroupByNum = splitGroup(betString, SUPER_BET_GROUP_SEPARATOR, true);
		for(String group : betGroupByNum){
			if(!checkRules(SUPER_BET_1_REGULAR, group)){
				checkResult = false;
				break;
			}else{
				if(!isRightByQu(group)){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}

	/**
	 * 
	 * Title: checkDanTuo Description: У�鵨�� ��У���ʽ���ظ�������+��������
	 * 
	 * @param betString
	 * @return boolean ��������
	 * @throws
	 */
	private boolean checkDanTuo(String betString){
		boolean checkResult = true;
		String[] betGroupByNum = splitGroup(betString, SUPER_BET_GROUP_SEPARATOR, true);
		for(String group : betGroupByNum){
			if(!checkRules(SUPER_BET_2_REGULAR, group)){// ��ʽ���ϸ�
				checkResult = false;
				break;
			}else if(!checkDanTuoRepeat(group)){// У���Ƿ��ظ�,����ǰ���ͺ���
				checkResult = false;
				break;
			}else if(!checkDanTuoTotalNum(group)){// У�������Ƿ�
				checkResult = false;
				break;
			}
		}
		return checkResult;
	}

	/**
	 * 
	 * Title: checkDanTuoRepeat Description: У�����벻���ڵ����г��ֹ�������ǰ���ͺ���
	 * 
	 * @param target
	 * @return boolean ��������
	 */
	public boolean checkDanTuoRepeat(String target){
		boolean checkResult = true;
		String[] zoneGroup = target.split("\\|");
		for(String zone : zoneGroup){// �������ֿ�,ǰ���ͺ�����У�����һ��
			String[] danTuoGroup = zone.split("\\*");// �˴��Ѿ�����У�飬ǰȥ�ͺ�����������������룬��������Ϊ2��ֵ
			List<String> dan = splitStrByLen(danTuoGroup[0], 2);
			List<String> tuo = splitStrByLen(danTuoGroup[1], 2);
			for(String singleTuo : tuo){
				if(dan.contains(singleTuo)){
					checkResult = false;
					break;
				}
			}
		}
		return checkResult;
	}

	/**
	 * 
	 * Title: checkDanTuoTotalNum Description: У�鵨��+��������Ҫ�ڹ涨�ķ�Χ��
	 * 
	 * @param target
	 * @return boolean ��������
	 * @throws
	 */
	private boolean checkDanTuoTotalNum(String target){
		boolean checkResult = true;
		String[] zoneGroup = target.split("\\|");
		// ǰ��
		String[] beforeDanTuoGroup = zoneGroup[0].split("\\*");// �˴��Ѿ�����У�飬ǰȥ�ͺ�����������������룬��������Ϊ2��ֵ
		List<String> beforeDan = splitStrByLen(beforeDanTuoGroup[0], 2);
		List<String> beforeTuo = splitStrByLen(beforeDanTuoGroup[1], 2);
		if((beforeDan.size() + beforeTuo.size()) < 5){
			checkResult = false;
			return checkResult;
		}
		// ����
		String[] afterDanTuoGroup = zoneGroup[1].split("\\*");// �˴��Ѿ�����У�飬ǰȥ�ͺ�����������������룬��������Ϊ2��ֵ
		List<String> afterDan = splitStrByLen(afterDanTuoGroup[0], 2);
		List<String> afterTuo = splitStrByLen(afterDanTuoGroup[1], 2);
		if((afterDan.size() + afterTuo.size()) < 2){
			checkResult = false;
			return checkResult;
		}
		return checkResult;
	}

	/**
	 * 
	 * Title: isRightByQu Description: �ֱ�У��ǰȥ�ͺ�����Ͷע�����Ƿ��ظ�
	 * 
	 * @param target
	 *            ҪУ���Ͷע���루��ĳһ��Ͷע���룬���Ƕ����
	 * @return boolean true У��ϸ�false У�鲻�ϸ�
	 * @throws
	 */
	private boolean isRightByQu(String target){
		boolean checkRepeat = true;
		/*
		 * if(idNeedCheck(true)){ return true; }
		 */
		String[] quGroup = target.split("\\|");
		for(String group : quGroup){
			if(!checkRepeat(group, 2)){
				checkRepeat = false;
				break;
			}
		}
		return checkRepeat;
	}

	@SuppressWarnings("unused")
	private boolean idNeedCheck(boolean flag){
		return flag;
	}

	public String lotteryRandomBetCode(){
		String[] reds = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"};
		String[] blues = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		String result = "";
		String split = "";
		Random random = new Random();
		int i = 0;
		while(i < 5){
			int pos = random.nextInt(35);
			if(split.indexOf(pos + ",") >= 0){
				continue;
			}else{
				split = split + pos + ",";
				result = result + reds[pos];
				i++;
			}
		}
		result = result + "|";
		split = "";
		i = 0;
		while(i < 2){
			int pos = random.nextInt(12);
			if(split.indexOf(pos + ",") >= 0){
				continue;
			}else{
				split = split + pos + ",";
				result = result + blues[pos];
				i++;
			}
		}
		return result;
	}

	public String mergeSalesInfoResult(LotteryInfo info){
		// TODO �Զ����ɷ������
		return null;
	}

	/*
	 * (�� Javadoc)Title: splitSalesInfoResultDescription:
	 * 
	 * @param salesInfo
	 * 
	 * @param info
	 * 
	 * @return
	 * 
	 * @see
	 * com.success.lottery.util.core.LotteryInterf#splitSalesInfoResult(java.
	 * lang.String, com.success.lottery.util.LotteryInfo)
	 */
	public LotteryInfo splitSalesInfoResult(String salesInfo, LotteryInfo info){
		return info;
	}

	@Override
	public String getMissCount(String lotteryResult, String lastMissCount){
		if(lotteryResult == null || lotteryResult.length() != 15){
			return null;
		}
		if(lotteryResult.split("\\|") == null || lotteryResult.split("\\|").length != 2){
			return null;
		}
		int h1 = Integer.parseInt(lotteryResult.substring(0, 2)) - 1;
		int h2 = Integer.parseInt(lotteryResult.substring(2, 4)) - 1;
		int h3 = Integer.parseInt(lotteryResult.substring(4, 6)) - 1;
		int h4 = Integer.parseInt(lotteryResult.substring(6, 8)) - 1;
		int h5 = Integer.parseInt(lotteryResult.substring(8, 10)) - 1;
		int digit[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		String bMissCount = null;
		if(StringUtils.isBlank(lastMissCount)){
			bMissCount = new HappyZodiac().getMissCount(lotteryResult.substring(11, 15), null);
			if(bMissCount == null){
				return null;
			}
			for(int i = 0; i < 35; i++){
				if(i == h1 || i == h2 || i == h3 || i == h4 || i == h5){
					digit[i] = 0;
				}else{
					digit[i]++;
				}
			}
		}else{
			String[] ss = lastMissCount.split("\\|");
			if(ss == null || ss.length != 2){
				return null;
			}
			if(ss[1].split(",") == null || ss[1].split(",").length != 12){
				return null;
			}else{
				bMissCount = new HappyZodiac().getMissCount(lotteryResult.substring(11, 15), ss[1]);
			}
			if(bMissCount == null){
				return null;
			}
			if(ss[0].split(",") == null || ss[0].split(",").length != 35){
				return null;
			}
			for(int i = 0; i < 35; i++){
				if(i == h1 || i == h2 || i == h3 || i == h4 || i == h5){
					digit[i] = 0;
				}else{
					digit[i] = Integer.parseInt(ss[0].split(",")[i]);
					digit[i]++;
				}
			}
		}
		String result = "";
		for(int i = 0; i < 35; i++){
			result = result + digit[i] + ",";
		}
		result = result.substring(0, result.length() - 1) + "|" + bMissCount;
		return result;
	}

	public static void main(String[] args){
		 SuperLottery superLottery = new SuperLottery();
		 // System.out.println(superLottery.checkDanTuoRepeat("*020304"));
		
		 for(int i = 0; i < 500; i++){
			 String betCode = superLottery.lotteryRandomBetCode();
			 System.out.println(betCode);
		 }
//		 String lastMissCount = superLottery.getMissCount(betCode, null);
//		 System.out.println(lastMissCount);
//		 for(int i = 0; i < 500; i++){
//		 betCode = superLottery.lotteryRandomBetCode();
//		 lastMissCount = superLottery.getMissCount(betCode, lastMissCount);
//		 System.out.println(betCode);
//		 System.out.println(lastMissCount);
//		 }
		 
//		 Map<Integer, Map<String, String>> map = new TreeMap<Integer, Map<String, String>>();
//		 Map<String, String> inMap = new TreeMap<String, String>();
//		 
//		 inMap.put("A", "ϣ��(u23)");
//		 inMap.put("B", "����(u23)");
//		 inMap.put("C", "");
//		 inMap.put("D", "");
//		 map.put(Integer.valueOf(1), inMap);
//		 
//		 inMap.clear();
//		 inMap.put("A", "����(u23)");
//		 inMap.put("B", "ī����(u23)");
//		 inMap.put("C", "");
//		 inMap.put("D", "");
//		 map.put(Integer.valueOf(2), inMap);
//		 
//		 System.out.println(LotteryTools.mergeSalesInfo(map));
		 
		 
	}

	@Override
	public boolean isLimitBet(String playType,String betType, String limitNumber, String betCode) {
		// TODO Auto-generated method stub
		return false;
	}
}
