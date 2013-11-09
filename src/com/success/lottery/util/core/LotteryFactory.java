package com.success.lottery.util.core;

/**
 * 
 * com.success.lottery.util.core
 * LotteryFactory.java
 * LotteryFactory
 * 实例化彩种处理类工厂类
 * @author gaoboqin
 * 2010-3-28 上午12:10:52
 *
 */
public class LotteryFactory {
	
	private static final String SUPER_LOTTO = "1000001";//超级大乐透
	private static final String HAPPY_ZODIAC = "1000005";//生肖乐
	private static final String SEVEN_COLOR = "1000002";//七星彩
	private static final String ARRANGE_TRHEE = "1000003";//排列三
	private static final String ARRANGE_FIVE = "1000004";//排列五
	private static final String WIN_FAIL = "1300001";//胜负彩
	private static final String ARBITRARY_NINE = "1300002";//任选九场
	private static final String HALE_SIX = "1300003";//6场半全场
	private static final String BALL_FOUR = "1300004";//4场进球彩
	private static final String ELEVEN_YUN = "1200001";//十一运夺金
	private static final String HAPPY_POKER = "1200002";//快乐扑克
	
	public static LotteryInterf factory(String lottery){
		if(lottery.equals(SUPER_LOTTO)){//超级大乐透
			return new SuperLottery();
		}else if(lottery.equals(HAPPY_ZODIAC)){
			return new HappyZodiac();
		}else if(lottery.equals(SEVEN_COLOR)){
			return new SevenColor();
		}else if(lottery.equals(ARRANGE_TRHEE)){
			return new Arrange3Lottery();
		}else if(lottery.equals(ARRANGE_FIVE)){
			return new Arrange5Lottery();
		}else if(lottery.equals(WIN_FAIL)){
			return new WinOrFailLottery();
		}else if(lottery.equals(ARBITRARY_NINE)){
			return new Arbitrary9Lottery();
		}else if(lottery.equals(HALE_SIX)){
			return new Half6Lottery();
		}else if(lottery.equals(BALL_FOUR)){
			return new Ball4Lottery();
		}else if(lottery.equals(ELEVEN_YUN)){
			return new JxDlcLottery();
		}else if(lottery.equals(HAPPY_POKER)){
			return new HappyPoker();
		}else{
			return null;
		}
		
	}

}
