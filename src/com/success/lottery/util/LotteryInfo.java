package com.success.lottery.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *  
 * ��Ʊ�����Ϣ�ࡣ
 * 
 * @author qgb
 */
public class LotteryInfo{

	/**
	 * 
	 * Integer: playType
	 * String: playTypeName
	 */
	private Map<Integer, String> lotteryPlayMap;//�淨����
	/**
	 * Integer: playType
	 * Map<Integer, String>: betType, betTypeName
	 */
	private Map<Integer, Map<Integer,String>> lotteryPlayBetMap;//�淨��Ӧ��Ͷע��ʽ����
	
	/**
	 * LotteryTools.getLotteryPlayBetTypeList(int lotteryId,int playType)����
	 * Integer: betType
	 * String: betTypeName
	 */
	private Map<Integer, String> lotteryBetTypeMapByPlayId;//Ͷע��ʽ����
	private String lotteryPlayListStr ;//�淨�����ַ���
	

	//���影��������
	/*
	 * ��������͸��TreeMap<String,TreeMap<String,String[]>>��������˼Ϊ��<һ�Ƚ����˵Ƚ�(������1-8��ʾ),<��������׷�ӽ�,[ע��,���]>>
	 */
	private  TreeMap<String,TreeMap<String,String[]>>  superWinResult;//��������͸��
	/*
	 * ��Ф��,���鳤��Ϊ2��������˼Ϊ��[ע�������]
	 */
	private String [] happyZodiacWinResult;//��Ф��
	
	/*
	 * ���ǲ� ,TreeMap<String,String[]> �����ֱ�Ϊ:<һ�Ƚ������Ƚ�,[ע��,���]>
	 */
	private TreeMap<String,String[]> sevenColorWinResult;
	
	/*
	 * ������ TreeMap<String,String[]> �����ֱ�Ϊ<ֱѡ��������������������(�ֱ���1,2,3����),[ע��,���]>
	 */
	private TreeMap<String,String[]> arrangeThreeWinResult;
	
	/*
	 * ������ ,���鳤��Ϊ2��������˼Ϊ��[ע�������]
	 */
	private String [] arrangeFiveWinResult;
	
	/*
	 * ʤ���� TreeMap<String,String[]> �����ֱ�Ϊ<һ�Ƚ�����Ƚ�,[ע��,���]>
	 */
	private TreeMap<String,String[]> winOrFailWinResult;
	/*
	 * ��ѡ�ų� String [] ����Ϊ[ע��,���]
	 */
	private String [] arbitry9WinResult;
	/*
	 * 6����ȫ��  String [] ����Ϊ[ע��,���]
	 */
	private String [] half6WinResult;
	/*
	 * 4������� String [] ����Ϊ[ע��,���]
	 */ 
	private String [] ball4WinResult;
	/*
	 * ʮһ�˶��<����,[ע��,���]>
	 */
	private LinkedHashMap<String,String[]> elevenYunWinResult;
	/*
	 * �����˿�
	 */
	
	//���忪���������
	/*
	 * ��������͸ ,TreeMap<String,ArrayList<String>> ������Ϊ<ǰȥ|����,<��������>>,����ǰ����1��ʾ��������2��ʾ����λһ�����룬������'0'
	 */
	private TreeMap<String,ArrayList<String>> superLotteryResult;
	/*
	 * ��Ф��,ArrayList<String> ������Ϊ<����>,Ӧ�ð���2�����룬��λһ�����룬������0
	 */
	private ArrayList<String> happyZodiacLotteryResult;
	/*
	 * ���ǲ�,ArrayList<String> ������Ϊ<����>��Ӧ�ð���7������
	 */
	private ArrayList<String> sevenColorLotteryResult;
	/*
	 * ������,ArrayList<String> ������Ϊ<����>��Ӧ�ð���3������
	 */
	private ArrayList<String> arrangeThreeLotteryResult;
	/*
	 * ������,ArrayList<String> ������Ϊ<����>��Ӧ�ð���5������
	 */
	private ArrayList<String> arrangeFiveLotteryResult;
	/*
	 * ʤ����,ArrayList<String> ������Ϊ<����>��Ӧ�ð���14������
	 */
	private ArrayList<String> winOrFailLotteryResult;
	/*
	 * ��ѡ�ų�,ArrayList<String> ������Ϊ<����>��Ӧ�ð���14������
	 */
	private ArrayList<String> arbitry9LotteryResult;
	/*
	 * 6����ȫ��,ArrayList<String> ������Ϊ<����>��Ӧ�ð���12������
	 */
	private ArrayList<String> half6LotteryResult;
	/*
	 * 4�������,ArrayList<String> ������Ϊ<����>��Ӧ�ð���8������
	 */
	private ArrayList<String> ball4LotteryResult;//��Ф��
	/*
	 * ʮһ�˶��,ArrayList<String> ������Ϊ<����>����λһ�����룬���������
	 */
	private ArrayList<String> elevenYunLotteryResult;
	/*
	 * �����˿�,ArrayList<String> ������Ϊ<����>��4λ��һλһ�����룬����Ϊ2-9��A��J��Q��K�����պں�÷��˳��
	 */
	private ArrayList<String> happyPokerLotteryResult;
	
	//������©��Ϣ�������
	/*
	 * ����Ϊ:<key,value> valueΪ��©�Ĵ���
	 * key˵����
	 *����͸ ��ʶ-����,H��ʾǰ��,����Ϊ01-35;B-����,B��ʾ����,����Ϊ01-12
     *��Ф�� B-���� ����Ϊ01-12
     *���ǲ�  P-λ��-���� ������λ��Ϊ1-7,����Ϊ0-9
     *������ Z-λ��-���� ,Z��ʾֱѡ,λ��Ϊ1-3,����Ϊ0-9;  H-����,H��ʾ��ֵ,����Ϊ1-26
     *������ P-λ��-���� ,λ��Ϊ1-5������Ϊ0-9
     *ʮһѡ�� ��©�ɷ�Ϊ12�����ͣ���һ�֣���2����8��B-���룻��һλ��©��Z1-���룻�ڶ�λ��©��Z2-���룻����λ��©��Z3-���룻
     *     ǰ2��ѡ��©��Q2-���룻ǰ3��ѡ��©��Q3-����;����������©,ON-������ż��������©,EN-������С��������©,SN-������
     *     ����������©,BN-����������������©,PN-����������������©,CN-����;���и�����ΧΪ0-5
	 */
	private Map<String,Integer> missCountResult;
	
	
	
	
	
	/**
	 * ���ʤ����������Ϣ
	 */
	Map<Integer, Map<String,String>> winOrFailSaleInfo;
	
	/**
	 * �����ѡ��������Ϣ
	 */
	Map<Integer, Map<String,String>> arbitrary9SaleInfo;
	
	/**
	 * ���������ȫ��������Ϣ
	 */
	Map<Integer, Map<String,String>> half6SaleInfo;
	

	/**
	 * ����ĳ������������Ϣ
	 */
	Map<Integer, Map<String,String>> ball4SaleInfo;

	
	
	private Integer lotteryId;//
	//�淨�б�

    /**
     * �淨��ʽΪ1000001.playList=0,��׷��&0,��ʽ;1,��ʽ;2,����|1,׷��&0,��ʽ;1,��ʽ;2,����<br>
     * ����淨��ʽ�ı䣬����Ҫ�޸ĸ÷���<br>
     * 
     * @param lotteryPlayListStr
     */
	public void setLotteryPlayListStr(String lotteryPlayListStr) {
		this.lotteryPlayListStr = lotteryPlayListStr;//���ô������ļ���ȡ���淨�����ַ���
	}

	public Map<Integer, Map<Integer, String>> getLotteryPlayBetMap() {
		return lotteryPlayBetMap;
	}

	public void setLotteryPlayBetMap(
			Map<Integer, Map<Integer, String>> lotteryPlayBetMap) {
		this.lotteryPlayBetMap = lotteryPlayBetMap;
	}

	public Integer getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(Integer lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Map<Integer, String> getLotteryPlayMap() {
		return lotteryPlayMap;
	}

	public void setLotteryPlayMap(Map<Integer, String> lotteryPlayMap) {
		this.lotteryPlayMap = lotteryPlayMap;
	}

	public String getLotteryPlayListStr() {
		return lotteryPlayListStr;
	}

	public Map<Integer, String> getLotteryBetTypeMapByPlayId() {
		return lotteryBetTypeMapByPlayId;
	}

	public void setLotteryBetTypeMapByPlayId(
			Map<Integer, String> lotteryBetTypeMapByPlayId) {
		this.lotteryBetTypeMapByPlayId = lotteryBetTypeMapByPlayId;
	}

	public TreeMap<String, TreeMap<String, String[]>> getSuperWinResult() {
		return superWinResult;
	}

	public void setSuperWinResult(
			TreeMap<String, TreeMap<String, String[]>> superWinResult) {
		this.superWinResult = superWinResult;
	}

	public String[] getArbitry9WinResult() {
		return arbitry9WinResult;
	}

	public void setArbitry9WinResult(String[] arbitry9WinResult) {
		this.arbitry9WinResult = arbitry9WinResult;
	}

	public String[] getArrangeFiveWinResult() {
		return arrangeFiveWinResult;
	}

	public void setArrangeFiveWinResult(String[] arrangeFiveWinResult) {
		this.arrangeFiveWinResult = arrangeFiveWinResult;
	}

	public TreeMap<String, String[]> getArrangeThreeWinResult() {
		return arrangeThreeWinResult;
	}

	public void setArrangeThreeWinResult(
			TreeMap<String, String[]> arrangeThreeWinResult) {
		this.arrangeThreeWinResult = arrangeThreeWinResult;
	}

	public String[] getBall4WinResult() {
		return ball4WinResult;
	}

	public void setBall4WinResult(String[] ball4WinResult) {
		this.ball4WinResult = ball4WinResult;
	}

	public String[] getHalf6WinResult() {
		return half6WinResult;
	}

	public void setHalf6WinResult(String[] half6WinResult) {
		this.half6WinResult = half6WinResult;
	}

	public String[] getHappyZodiacWinResult() {
		return happyZodiacWinResult;
	}

	public void setHappyZodiacWinResult(String[] happyZodiacWinResult) {
		this.happyZodiacWinResult = happyZodiacWinResult;
	}

	public TreeMap<String, String[]> getSevenColorWinResult() {
		return sevenColorWinResult;
	}

	public void setSevenColorWinResult(TreeMap<String, String[]> sevenColorWinResult) {
		this.sevenColorWinResult = sevenColorWinResult;
	}

	public TreeMap<String, String[]> getWinOrFailWinResult() {
		return winOrFailWinResult;
	}

	public void setWinOrFailWinResult(TreeMap<String, String[]> winOrFailWinResult) {
		this.winOrFailWinResult = winOrFailWinResult;
	}

	public ArrayList<String> getArbitry9LotteryResult() {
		return arbitry9LotteryResult;
	}

	public void setArbitry9LotteryResult(ArrayList<String> arbitry9LotteryResult) {
		this.arbitry9LotteryResult = arbitry9LotteryResult;
	}

	public ArrayList<String> getArrangeFiveLotteryResult() {
		return arrangeFiveLotteryResult;
	}

	public void setArrangeFiveLotteryResult(
			ArrayList<String> arrangeFiveLotteryResult) {
		this.arrangeFiveLotteryResult = arrangeFiveLotteryResult;
	}

	public ArrayList<String> getArrangeThreeLotteryResult() {
		return arrangeThreeLotteryResult;
	}

	public void setArrangeThreeLotteryResult(
			ArrayList<String> arrangeThreeLotteryResult) {
		this.arrangeThreeLotteryResult = arrangeThreeLotteryResult;
	}

	public ArrayList<String> getBall4LotteryResult() {
		return ball4LotteryResult;
	}

	public void setBall4LotteryResult(ArrayList<String> ball4LotteryResult) {
		this.ball4LotteryResult = ball4LotteryResult;
	}

	public ArrayList<String> getElevenYunLotteryResult() {
		return elevenYunLotteryResult;
	}

	public void setElevenYunLotteryResult(ArrayList<String> elevenYunLotteryResult) {
		this.elevenYunLotteryResult = elevenYunLotteryResult;
	}

	public ArrayList<String> getHalf6LotteryResult() {
		return half6LotteryResult;
	}

	public void setHalf6LotteryResult(ArrayList<String> half6LotteryResult) {
		this.half6LotteryResult = half6LotteryResult;
	}

	public ArrayList<String> getHappyPokerLotteryResult() {
		return happyPokerLotteryResult;
	}

	public void setHappyPokerLotteryResult(ArrayList<String> happyPokerLotteryResult) {
		this.happyPokerLotteryResult = happyPokerLotteryResult;
	}

	public ArrayList<String> getHappyZodiacLotteryResult() {
		return happyZodiacLotteryResult;
	}

	public void setHappyZodiacLotteryResult(
			ArrayList<String> happyZodiacLotteryResult) {
		this.happyZodiacLotteryResult = happyZodiacLotteryResult;
	}

	public ArrayList<String> getSevenColorLotteryResult() {
		return sevenColorLotteryResult;
	}

	public void setSevenColorLotteryResult(ArrayList<String> sevenColorLotteryResult) {
		this.sevenColorLotteryResult = sevenColorLotteryResult;
	}
	public ArrayList<String> getWinOrFailLotteryResult() {
		return winOrFailLotteryResult;
	}

	public void setWinOrFailLotteryResult(ArrayList<String> winOrFailLotteryResult) {
		this.winOrFailLotteryResult = winOrFailLotteryResult;
	}

	public TreeMap<String, ArrayList<String>> getSuperLotteryResult() {
		return superLotteryResult;
	}

	public void setSuperLotteryResult(
			TreeMap<String, ArrayList<String>> superLotteryResult) {
		this.superLotteryResult = superLotteryResult;
	}

	public LinkedHashMap<String, String[]> getElevenYunWinResult() {
		return elevenYunWinResult;
	}

	public void setElevenYunWinResult(LinkedHashMap<String, String[]> elevenYunWinResult) {
		this.elevenYunWinResult = elevenYunWinResult;
	}

	public Map<Integer, Map<String, String>> getArbitrary9SaleInfo() {
		return arbitrary9SaleInfo;
	}

	public void setArbitrary9SaleInfo(
			Map<Integer, Map<String, String>> arbitrary9SaleInfo) {
		this.arbitrary9SaleInfo = arbitrary9SaleInfo;
	}

	public Map<Integer, Map<String, String>> getBall4SaleInfo() {
		return ball4SaleInfo;
	}

	public void setBall4SaleInfo(Map<Integer, Map<String, String>> ball4SaleInfo) {
		this.ball4SaleInfo = ball4SaleInfo;
	}

	public Map<Integer, Map<String, String>> getHalf6SaleInfo() {
		return half6SaleInfo;
	}

	public void setHalf6SaleInfo(Map<Integer, Map<String, String>> half6SaleInfo) {
		this.half6SaleInfo = half6SaleInfo;
	}

	public Map<Integer, Map<String, String>> getWinOrFailSaleInfo() {
		return winOrFailSaleInfo;
	}

	public void setWinOrFailSaleInfo(
			Map<Integer, Map<String, String>> winOrFailSaleInfo) {
		this.winOrFailSaleInfo = winOrFailSaleInfo;
	}

	public Map<String, Integer> getMissCountResult() {
		return missCountResult;
	}

	public void setMissCountResult(Map<String, Integer> missCountResult) {
		this.missCountResult = missCountResult;
	}
	
}
