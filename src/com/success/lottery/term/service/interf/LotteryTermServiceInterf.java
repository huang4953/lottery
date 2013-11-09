/**
 * Title: LotteryTermServiceInterf.java
 * @Package com.success.lottery.term.service.interf
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-6 下午04:06:29
 * @version V1.0
 */
package com.success.lottery.term.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;

/**
 * com.success.lottery.term.service.interf
 * LotteryTermServiceInterf.java
 * LotteryTermServiceInterf
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2010-4-6 下午04:06:29
 * 
 */

public interface LotteryTermServiceInterf {
	
	public static final int E_0005_CODE = 300005;
	public static final String E_0005_DESC = "未找到彩种的当前期信息!";
	
	public static final int E_1001_CODE = 301001;
	public static final String E_1001_DESC = "更新彩期状态出错！";
	
	public static final int E_1002_CODE = 301002;
	public static final String E_1002_DESC = "更新彩期销售信息出错！";
	
	public static final int E_1003_CODE = 301003;
	public static final String E_1003_DESC = "更新彩期开奖状态出错！";
	
	public static final int E_1004_CODE = 301004;
	public static final String E_1004_DESC = "获取彩期信息出错！";
	
	public static final int E_1005_CODE = 301005;
	public static final String E_1005_DESC = "获取彩期当前期信息出错！";
	
	public static final int E_1006_CODE = 301006;
	public static final String E_1006_DESC = "获取彩种的所有期数信息出错！";
	
	public static final int E_1007_CODE = 301007;
	public static final String E_1007_DESC = "更新彩期切换时间定义表出错！";
	
	public static final int E_1008_CODE = 301008;
	public static final String E_1008_DESC = "获取彩期切换时间定义出错！";
	
	public static final int E_1009_CODE = 300009;
	public static final String E_1009_DESC = "更新彩期销售信息参数不能为空！";
	
	public static final int E_1010_CODE = 301010;
	public static final String E_1010_DESC = "更新开奖信息出错！";
	
	public static final int E_1011_CODE = 301011;
	public static final String E_1011_DESC = "更新限号信息出错！";
	
	public static final int E_1012_CODE = 301012;
	public static final String E_1012_DESC = "获取限号信息出错！";
	
	public static final int GETTERMCOUNTEXCEPTION = 301013;
	public static final String GETTERMCOUNTEXCEPTION_STR = "条件查询彩期信息数量时出现异常：";
	
	public static final int GETTERMINFOEXCEPTION = 301014;
	public static final String GETTERMINFOEXCEPTION_STR = "条件查询彩期信息时出现异常：";

	
	/**
	 * 
	 * Title: updateTermStatus<br>
	 * Description: <br>
	 *            更新彩期信息的期数状态<br>
	 *            更新前需要校验的先决条件：<br>
	 *            <li>
	 *            <li>
	 * @param lotteryId 彩种id
	 * @param termNo 彩种期数
	 * @param termStatus 期数状态 0-预售期,1-当前期,2-历史期
	 * @return int 更新结果 0 表示没有更新到数据 1..n 表示更新的记录数目
	 * @throws LotteryException 
	 *                         <br>301001 更新时数据库操作发生异常
	 */
	public int updateTermStatus(int lotteryId,String termNo,int termStatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateTermSalesInfo<br>
	 * Description:<br>
	 *             更新彩期销售信息<br>
	 *             根据销售信息集合更新指定彩种和期数的销售信息，销售信息只有足彩才有<br>
	 *             该方法只能是足彩使用，否则将直接返回<br>
	 *             方法未对参数的的有效性做校验，因此该方法不能直接调用。
	 *             <li>
	 *             <li>
	 * @param termInfo
	 * @return int 更新结果 0 表示没有更新到数据 1..n 表示更新的记录数目
	 * @throws LotteryException <br>
	 *                           301002,"更新彩期销售信息出错！"
	 */
	public int updateTermSalesInfo(LotteryTermModel termInfo) throws LotteryException;
	
	/**
	 * 
	 * Title: updateTermWinStatus<br>
	 * Description: <br>
	 *              更新彩期的开奖状态<br>
	 *              更新前需要校验的先决条件：<br>
	 *              <li>
	 *              <li>
	 * @param lotteryId 彩种id
	 * @param termNo 彩种期数 
	 * @param winstatus 
	 *                 <br>开奖状态:
	 *                 <br>1:未开将 2:已开奖 3:正在兑奖 4:兑奖成功 5:兑奖失败
	 *                 <br>6:兑奖成功并开始处理追号 7:正在派奖 8:派奖成功 9:派奖失败
	 * @return 成功更新的记录数
	 * @throws LotteryException<br>
	 *                          301003,"更新彩期开奖状态出错！"                      
	 */
	public int updateTermWinStatus(int lotteryId,String termNo,int winstatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateTermSaleStatus<br>
	 * Description: <br>
	 *            更新彩期的销售状态<br>
	 * @param lotteryId 彩种id
	 * @param termNo 彩种期数 
	 * @param saleStatus 
	 *                   <br>销售状态
	 *                   <br>1:正在销售 2:停止销售 3:暂停销售 4:未到销售时间 5:已经开奖
	 * @return 更新是否成功 true 更新成功 false 更新失败
	 * @throws LotteryException
	 */
	public boolean updateTermSaleStatus(int lotteryId,String termNo,int saleStatus) throws LotteryException;
	
	/**
	 * 
	 * Title: updateTermWinInfo<br>
	 * Description: <br>
	 *            更新彩期的开奖信息<br>
	 *            该方法需要的参数信息需要直接提供，不会在方法内再做处理<br>
	 *            该方法对彩期会做固定的条件限制：彩期状态为2(已售期),销售状态为2(停止销售),开奖状态为1(未开奖)<br>
	 *            会更新状态：销售状态改为5(已经开奖),开奖状态改为2(已经开奖)<br>
	 *            对一个彩种输入开奖信息后就不能再更改了<br>
	 *            该方法要求参数不能为空 ，否则将抛出异常,其中遗漏信息可以为空(足彩没有遗漏)<br>
	 * @param lotteryId 彩种id
	 * @param termNo 彩期
	 * @param lotteryResult 开奖结果
	 * @param lotteryResultPlus 开奖结果顺序
	 * @param salesVolume 当期销量
	 * @param jackpot 累计奖池
	 * @param winResult 奖金结果
	 * @param missCount 遗漏信息,对足彩该项设置为""即可
	 * @return int 更新影响的记录数
	 * @throws LotteryException<br>
	 *                          <br>E_1010_CODE:更新开奖信息出错
	 */
	public int updateTermWinInfo(int lotteryId, String termNo,
			String lotteryResult, String lotteryResultPlus, String salesVolume,
			String jackpot, String winResult, String missCount)
			throws LotteryException;
	/**
	 * 
	 * Title: updateTermLimitNumber<br>
	 * Description: <br>
	 *              <br>更新限号信息，基础操作
	 * @param lotteryId 彩种
	 * @param termNo 彩期
	 * @param limitNumber 限制号码
	 * @return 更新影响的记录数
	 * @throws LotteryException<br>
	 *                          <br>E_1011_CODE:更新限号信息出错
	 */
	public int updateTermLimitNumber(int lotteryId,String termNo,String limitNumber) throws LotteryException;
	
	/**
	 * 
	 * Title: queryTermLimitNumberInfo<br>
	 * Description: <br>
	 *              获取彩种的限号信息<br>
	 * @param lotteryId 彩种id 若为null或空则查所有的彩种
	 * @param begin_term 开始期号 若为null或空 则认为不限制
	 * @param end_term 截至期号 若为null或空 则认为不限制
	 * @param limitNum 限制条数 如为0则认为不限制条数
	 * @return 限号的信息集合
	 * @throws LotteryException<br>
	 *                          <br>E_1012_CODE:获取限号信息出错
	 */
	public List<LotteryTermModel> queryTermLimitNumberInfo(String lotteryId,String begin_term,String end_term,int limitNum) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryTermInfo<br>
	 * Description: <br>
	 *              彩期信息查询<br>
	 *              根据彩种id和彩种期数查询指定的彩期相关信息，彩期的信息包括LotteryTerm表中定义的所有信息<br>
	 *              
	 * @param lotteryId 彩种id
	 * @param termNo 彩种期数
	 * @return LotteryTermModel 
	 *                        <br>彩期信息领域模型，包含lotteryTerm表中的信息以及内含的LotterInfo对象
	 *                        <br>该对象对彩期的开奖结果、奖金结果、销售信息和遗漏信息进行了拆分（把字符串拆分为对应的集合形式）
	 *                        <br>注意该方法返回的对象所包含的信息都是参数termNo指定期数的信息，没有在额外的获取其他期数的信息
	 * @throws LotteryException<br>
	 *                          301004,"获取彩期信息出错！"
	 *                          
	 */
	public LotteryTermModel queryTermInfo(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: queryTermInfoNotSplit<br>
	 * Description: <br>
	 *              <br>获取彩期对象，不对彩期对象做结果的拆分
	 * @param lotteryId
	 * @param termNo
	 * @return
	 * @throws LotteryException
	 */
	public LotteryTermModel queryTermInfoNotSplit(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: queryHaveWinTermInfo<br>
	 * Description: <br>
	 *            <br>获取某一个彩种的已经开奖的已售期信息列表
	 *            <br>该对象对彩期的开奖结果、奖金结果、销售信息和遗漏信息进行了拆分（把字符串拆分为对应的集合形式）
	 * @param lotteryId 彩种
	 * @param limitNum 限制条数
	 * @return List<LotteryTermModel>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:获取彩期信息出错
	 */		
	public List<LotteryTermModel> queryHaveWinTermInfo(int lotteryId,int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryAllLastTermInfo<br>
	 * Description: <br>
	 *              <br>获取所有彩种的最近的开奖信息
	 *              <br>该对象对彩期的开奖结果、奖金结果、销售信息和遗漏信息进行了拆分（把字符串拆分为对应的集合形式）
	 * @return List<LotteryTermModel>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:获取彩期信息出错
	 */
	public List<LotteryTermModel> queryAllLastTermInfo() throws LotteryException;
	/**
	 * 
	 * Title: queryCanInputWinTermNo<br>
	 * Description: <br>
	 *            获取可以输入开奖信息的彩期期号列表，按照期号从小到大的顺序排序<br>
	 * @param lotteryId 彩种
	 * @return List<String> 期号列表
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:获取彩期信息出错
	 */
	public List<String> queryCanInputWinTermNo(int lotteryId) throws LotteryException;
	/**
	 * 
	 * Title: queryCanCashPrizeTermNo<br>
	 * Description: <br>
	 *            获取可以兑奖信的彩期期号列表，按照期号从小到大的顺序排序<br>
	 * @param lotteryId 彩种
	 * @return List<String>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:获取彩期信息出错
	 */
	public List<String> queryCanCashPrizeTermNo(int lotteryId) throws LotteryException;
	
	/**
	 * 查询指定彩种的彩期
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> getTermnos(int lotteryId, int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: queryCanDispatchPrizeTermNo<br>
	 * Description: <br>
	 *            获取可以派奖的彩期期号列表，按照期号从小到大的顺序排序<br>
	 * @param lotteryId 彩种
	 * @return List<String>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:获取彩期信息出错
	 */
	public List<String> queryCanDispatchPrizeTermNo(int lotteryId) throws LotteryException;
	
	/**
	 * 
	 * Title: queryCanSaleTermNos<br>
	 * Description: <br>
	 *              <br>根据彩种id查询能销售的期数列表
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryCanSaleTermNos(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryCanAddTermNo<br>
	 * Description: <br>
	 *              <br>能追号的期号
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryCanAddTermNo(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryCurrentAndNextTermNo<br>
	 * Description: <br>
	 *              <br>查询在售期和未售期的彩期列表
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryCurrentAndNextTermNo(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryLimitTermNo<br>
	 * Description: <br>
	 *              <br>根据彩种获取限号信息不为空和当前期的彩期
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryLimitTermNo(int lotteryId, int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: queryTermnoIsOneOrTwo<br>
	 * Description: <br>
	 *              <br>查询彩期状态为1或者2的彩期列表，目前用于出票子系统
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryTermNoIsOneOrTwo(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryHaveWinTermNos<br>
	 * Description: <br>
	 *              <br>获取已经输入开奖信息的彩期列表，彩期倒序排列
	 * @param lotteryId 彩种id
	 * @param limitNum 限制条数 ，-1表示不限制
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryHaveWinTermNos(int lotteryId, int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryHaveDispathcTermNos<br>
	 * Description: <br>
	 *              <br>获取已经兑奖的彩期列表，彩期倒序排列
	 * @param lotteryId 彩种id
	 * @param limitNum 限制条数 ，-1表示不限制
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryHaveDispathTermNos(int lotteryId, int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: queryLastTermInfo<br>
	 * Description: <br>
	 *              <br>获取指定彩期的上一期信息
	 *              <br>只是把数据查出来，没有对数据做数据的拆分以及
	 * @param lotteryId 彩种
	 * @param termNo 期数
	 * @return LotteryTermModel
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:获取彩期信息出错
	 */
	public LotteryTermModel queryLastTermInfo(int lotteryId,String termNo) throws LotteryException;
	/**
	 * 
	 * Title: queryTermNoInfo<br>
	 * Description: <br>
	 *            根据彩种查询彩种的当前期数信息，该信息需要采用高速缓存<br>
	 *            该方法缓存的对象中应尽可能的少包含信息<br>
	 *            该方法返回的对象中遗漏信息、当期销量、累计奖池、开奖结果、奖金结果设置的是该彩种最近一期开奖结果的数据，使用时要注意<br>
	 * @param lotteryId 彩种id
	 * @return LotteryTermModel
	 * @throws LotteryException<br>
	 *                          301005 获取lotteryid的当前期数信息时数据库出错
	 *                          300005 获取lotteryid的当前期数信息时未找到该条信息
	 */
	public LotteryTermModel queryTermCurrentInfo(int lotteryId) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryTermCurrentInfoBall<br>
	 * Description: <br>
	 *            根据彩种获取彩种的当前期信息列表，该信息缓存<br>
	 *            该方法返回的对象中遗漏信息、当期销量、累计奖池、开奖结果、奖金结果设置的是该彩种最近一期开奖结果的数据，使用时要注意<br>
	 * @param lotteryId 彩种id
	 * @param nextTermNum 要获取的期数
	 * @return List<LotteryTermModel>
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> queryTermCurrentInfo(int lotteryId,int nextTermNum) throws LotteryException;
	
	
	/**
	 * 
	 * Title: queryTermInfoList<br>
	 * Description: <br>
	 *              获取某一彩种的所有期数信息，以列表形式返回<br>
	 *              需要提供分页，目前分页还没有考虑好，因此方法的原型有可能改变<br>
	 * @param lotteryId 彩种id
	 * @param startPageSize 开始条数
	 * @param pageSize 每页条数
	 * @return 包含彩期信息的列表
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> queryTermInfoList(int lotteryId,int startPageSize,int pageSize) throws LotteryException;
	/**
	 * 
	 * Title: queryLastTermNo<br>
	 * Description: <br>
	 *              <br>根据彩种获取已经开奖的和可以开奖的期号
	 * @param lotteryId
	 * @param limitNum 
	 * @return List<String>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:获取彩期信息出错
	 */
	public List<String> queryLastTermNo(int lotteryId,int limitNum) throws LotteryException;
	/**
	 * Title: queryLastTermInfo<br>
	 * Description: <br>
	 *              <br>根据彩种获取已经开奖的和可以开奖的彩期信息
	 *              <br>如果lotteryIds为null或空，则会查询所有的彩种
	 * @param lotteryIds 彩种id列表 
	 * @param begin_term 开始期号 可以为空
	 * @param end_term 截至期号 可以为空
	 * @param limitNums 限制条数集合，内容为<彩种id,限制条数>，如果限制条数填为0或小于0则认为不加条数限制 可以为空 如果为空则认为所有的彩种都不限制条数
	 * @param limitNum 限制条数 对所有彩种有效
	 * @return List<LotteryTermModel>
	 * @throws LotteryException<br>
	 *                          <br>LotteryTermServiceInterf.E_1004_CODE:获取彩期信息出错
	 */
	public List<LotteryTermModel> queryLastTermInfo(List<Integer> lotteryIds,String begin_term,String end_term,Map<Integer, Integer> limitNums,int limitNum) throws LotteryException;
	
	/**
	 * 
	 * Title: queryCanUpdateSaleTermInfo<br>
	 * Description: <br>
	 *              <br>查询可以修改销售信息的彩期信息
	 * @param lotteryIds
	 * @param begin_term
	 * @param end_term
	 * @param limitNums
	 * @param limitNum
	 * @return
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> queryCanUpdateSaleTermInfo(List<Integer> lotteryIds,String begin_term,String end_term,Map<Integer, Integer> limitNums,int limitNum) throws LotteryException;
	/**
	 * 
	 * Title: queryTermLastCashInfo<br>
	 * Description: <br>
	 *              <br>获取彩种最近一期的开奖信息
	 *              <br>对开奖结果，奖金结果、销售信息、遗漏信息做了拆分
	 * @param lotteryId
	 * @return
	 * @throws LotteryException
	 */
	public LotteryTermModel queryTermLastCashInfo(int lotteryId) throws LotteryException;
	
	/**
	 * 根据条件获得彩期信息的条数，用于彩期信息查询分页
	 * @param lotteryId
	 * 		彩种编号，为-1时查询所有彩种
	 * @param startTerm
	 * 		指定彩种编号时有效，开始期号
	 * @param endTerm
	 * 		指定彩种编号时有效，截至期数
	 * @return
	 * 		查询到的彩期信息，如果没有查询到则为0
	 * @throws LotteryException
	 * 		GETTERMCOUNTEXCEPTION
	 */
	public int getTermInfoCount(int lotteryId, String startTerm, String endTerm) throws LotteryException;
	
	/**
	 * 根据条件获得彩期信息，用于彩期信息查询 
	 * @param lotteryId
	 * 		彩种编号，为-1时查询所有彩种 		
	 * @param startTerm
	 * 		指定彩种编号时有效，开始期号
	 * @param endTerm
	 * 		指定彩种编号时有效，截至期数
	 * @param pageIndex
	 * 		查询的页码第几页，用于分页查询
	 * @param perPageNumber
	 * 		每页的记录数
	 * @return
	 * @throws LotteryException
	 */
	public List<LotteryTermModel> gettermInfo(int lotteryId, String startTerm, String endTerm, int pageIndex, int perPageNumber) throws LotteryException;
	/**
	 * 
	 * Title: getCanPrintCondition<br>
	 * Description: <br>
	 *              <br>获取能打印彩票彩期的条件
	 * @return 返回格式为lotteryid=1000001 and betterm='10971') or （lotteryid=1000002 and betterm = '100972'）
	 * @throws LotteryException
	 */
	public String getCanPrintCondition() throws LotteryException;
	
	/**
	 * 按照日期查询多乐彩某天的 已经开奖的彩期
	 * @param date  格式 yyMMdd
	 * @return
	 */
	public List<LotteryTermModel> queryHaveWinTermInfoToDate(String date);
	/**
	 * 
	 * Title: queryDealLine3TermNo<br>
	 * Description: <br>
	 *              <br>根据彩种获取官方止售的彩期
	 * @param lotteryId
	 * @return
	 * @throws LotteryException
	 */
	public List<String> queryDealLine3TermNo(int lotteryId,int limitNum) throws LotteryException;
	
	
}
