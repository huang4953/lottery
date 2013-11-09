package com.success.lottery.term.service.interf;

import java.util.List;
import java.util.Map;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.web.formbean.RaceBean;

public interface ToHtmlInterf {
	/**
	 * 大乐透走势分析生成html的方法
	 * @param list 大乐透的已经开奖的已售期信息列表
	 * @param name 彩种名称
	 * @param enc  编码格式
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	
	/**
	 * 七星彩走势分析生成html的方法
	 * @param list 七星彩的已经开奖的已售期信息列表
	 * @param name 彩种名称
	 * @param enc  编码格式
	 * @return
	 * @throws LotteryException
	 */
	public boolean qxcfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	/**
	 * 排列三走势分析生成html的方法
	 * @param list 排列三的已经开奖的已售期信息列表
	 * @param name 彩种名称
	 * @param enc  编码格式
	 * @return
	 * @throws LotteryException
	 */
	public boolean plsfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	/**
	 * 排列五走势分析生成html的方法
	 * @param list 排列五的已经开奖的已售期信息列表
	 * @param name 彩种名称
	 * @param enc  编码格式
	 * @return
	 * @throws LotteryException
	 */
	public boolean plwfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	
	/**
	 * 大乐透开奖某一期详细信息查询生成html的方法
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltkjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel)throws LotteryException;
	/**
	 * 七星彩开奖某一期详细信息查询生成html的方法
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean qxckjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel)throws LotteryException;
	/**
	 * 胜负彩开奖某一期详细信息查询生成html的方法
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean zckjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel,List<RaceBean> raceList)throws LotteryException;
	
	/**
	 * 排列三开奖某一期详细信息查询生成html的方法
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean plskjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel)throws LotteryException;
	
	/**
	 * 排列五开奖某一期详细信息查询生成html的方法
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean plwkjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,LotteryTermModel othertermModel)throws LotteryException;
	
	/**
	 * 大乐透开奖查询集合页
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltlistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	/**
	 * 七星彩开奖查询集合页
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean qxclistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	
	/**
	 * 排列三开奖查询集合页
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean plslistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	/**
	 * 排列五开奖查询集合页
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean plwlistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	/**
	 * 胜负彩开奖查询集合页
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean zclistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	
	/**
	 * 开奖查询首页
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean kjlistfileSource(String name, String enc, String url,
			Map<Integer,LotteryTermModel> termMap)throws LotteryException;
	
	/**
	 * 数字彩和足彩的下拉列表
	 * @param termName
	 * @param enc
	 * @param url
	 * @param numlist
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltseletfileSource(String termName, String enc, String url,List<String> numlist)throws LotteryException;
	/**
	 * 首页的最近开奖
	 * @param name
	 * @param enc
	 * @param url
	 * @param termMap
	 * @return
	 * @throws LotteryException
	 */
	public boolean kjlistfileSourceOnIndex(String name, String enc, String url,Map<Integer,LotteryTermModel> termMap)
	throws LotteryException;
	
	/**
	 * 大乐透购彩大厅静态页面
	 * @param currentTerm
	 * @param historyTermList
	 * @return
	 * @throws LotteryException
	 */
	public boolean dltzjkaijianSource(String name, String enc, String url,LotteryTermModel currentTerm,List<LotteryTermModel> historyTermList,List<String[]> WinResultList)throws LotteryException;
	
	/**
	 * 七星彩购彩大厅静态页面
	 * @param currentTerm
	 * @param historyTermList
	 * @return
	 * @throws LotteryException
	 */
	public boolean qxczjkaijianSource(String name, String enc, String url,LotteryTermModel currentTerm,List<LotteryTermModel> historyTermList,List<String[]> WinResultList)throws LotteryException;
	
	/**
	 * 排列三购彩大厅静态页面
	 * @param currentTerm
	 * @param historyTermList
	 * @return
	 * @throws LotteryException
	 */
	public boolean plszjkaijianSource(String name, String enc, String url,LotteryTermModel currentTerm,List<LotteryTermModel> historyTermList)throws LotteryException;
	/**
	 * 排列五购彩大厅静态页面
	 * @param currentTerm
	 * @param historyTermList
	 * @return
	 * @throws LotteryException
	 */
	public boolean plwzjkaijianSource(String name, String enc, String url,LotteryTermModel currentTerm,List<LotteryTermModel> historyTermList)throws LotteryException;
	
	/**
	 * 
	 * @param name 文件名称
	 * @param enc  编码格式
	 * @param url  保存路径
	 * @param list 历史期集合 
	 * @return
	 * @throws LotteryException
	 */
	public boolean jq4listfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)
			throws LotteryException;
	
	/**
	 * 四场进球开奖某一期详细信息查询生成html的方法
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean jq4kjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,List<RaceBean> raceList)throws LotteryException;
	
	
	/**
	 * 
	 * @param name 文件名称
	 * @param enc  编码格式
	 * @param url  保存路径
	 * @param list 历史期集合 
	 * @return
	 * @throws LotteryException
	 */
	public boolean zc6listfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)
			throws LotteryException;
	
	/**
	 * 六场半全开奖某一期详细信息查询生成html的方法
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param termModel
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean zc6kjcxfileSource(String name,String enc,String url,LotteryTermModel termModel,List<RaceBean> raceList)throws LotteryException;
	
	/**
	 * 多乐彩下拉列表
	 * @param enc
	 * @param url
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlcseletfileSource(String enc, String url)throws LotteryException;
	/**
	 * 任选基本走势分析生成html的方法
	 * @param list 多乐彩任选基本的已经开奖的已售期信息列表
	 * @param name 彩种名称
	 * @param enc  编码格式
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_rxfileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	/**
	 * 任选前二直选走势分析生成html的方法
	 * @param list 多乐彩任选基本的已经开奖的已售期信息列表
	 * @param name 彩种名称
	 * @param enc  编码格式
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_qe_zhifileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	/**
	 * 任选前三直选走势分析生成html的方法
	 * @param list 多乐彩任选基本的已经开奖的已售期信息列表
	 * @param name 彩种名称
	 * @param enc  编码格式
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_qs_zhifileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	
	/**
	 * 任选前二组选走势分析生成html的方法
	 * @param list 多乐彩任选基本的已经开奖的已售期信息列表
	 * @param name 彩种名称
	 * @param enc  编码格式
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_qe_zufileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;
	/**
	 * 任选前三组选走势分析生成html的方法
	 * @param list 多乐彩任选基本的已经开奖的已售期信息列表
	 * @param name 彩种名称
	 * @param enc  编码格式
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlc_qs_zufileSource(List<LotteryTermModel> list,String name,String enc,String url)throws LotteryException;

	/**
	 * 开奖查询首页
	 * @param name
	 * @param enc
	 * @param url
	 * @param numlist
	 * @param l
	 * @param othertermModel
	 * @return
	 * @throws LotteryException
	 */
	public boolean dlclistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)throws LotteryException;
	
	
	

}
