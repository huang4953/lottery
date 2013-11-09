/**
 * Title: LotteryTermDaoImpl.java
 * @Package com.success.lottery.term.dao.impl
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2010-4-7 上午10:06:01
 * @version V1.0
 */
package com.success.lottery.term.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.success.lottery.term.dao.interf.BaseSqlMapDao;
import com.success.lottery.term.domain.LotteryTermModel;

/**
 * com.success.lottery.term.dao.impl LotteryTermDaoImpl.java LotteryTermDaoImpl
 * (这里用一句话描述这个类的作用)
 * 
 * @author gaoboqin 2010-4-7 上午10:06:01
 * 
 */

public class LotteryTermDaoImpl extends BaseSqlMapDao {

	/**
	 * 
	 * Title: updateTermStatus<br>
	 * Description: <br>
	 * 更新彩期状态<br>
	 * 
	 * @param lotteryModel
	 * @return int
	 */
	public int updateTermStatus(LotteryTermModel lotteryModel) {
		return super.smcTemplate.update("lotteryTerm.updateTermStatus",
				lotteryModel);
	}

	/**
	 * 
	 * Title: updateTermWinInfo<br>
	 * Description: <br>
	 * 输入开奖信息<br>
	 * 
	 * @param lotteryModel
	 * @return int
	 */
	public int updateTermWinInfo(LotteryTermModel lotteryModel) {
		return super.smcTemplate.update("lotteryTerm.updateTermWinInfo",
				lotteryModel);
	}

	/**
	 * 
	 * Title: updateTermSalesInfo<br>
	 * Description: <br>
	 * 输入销售信息<br>
	 * 
	 * @param lotteryModel
	 * @return int
	 */
	public int updateTermSalesInfo(LotteryTermModel lotteryModel) {
		return super.smcTemplate.update("lotteryTerm.updateTermSalesInfo",
				lotteryModel);
	}

	/**
	 * 
	 * Title: updateTermWinStatus<br>
	 * Description: <br>
	 * 更新彩期开奖状态<br>
	 * 
	 * @param lotteryModel
	 * @return int
	 */
	public int updateTermWinStatus(LotteryTermModel lotteryModel) {
		return super.smcTemplate.update("lotteryTerm.updateTermWinStatus",
				lotteryModel);
	}

	/**
	 * 
	 * Title: queryTermInfo<br>
	 * Description: <br>
	 * 获取彩种彩期信息<br>
	 * 
	 * @param lotteryModel
	 * @return LotteryTermModel
	 */
	public LotteryTermModel queryTermInfo(LotteryTermModel lotteryModel) {
		Object obj = super.smcTemplate.queryForObject(
				"lotteryTerm.queryTermInfo", lotteryModel);
		return (obj != null) ? (LotteryTermModel) obj : null;
	}

	/**
	 * 
	 * Title: queryTermCurrentInfo<br>
	 * Description: <br>
	 * 获取彩种的当前期信息<br>
	 * 
	 * @param lotteryId
	 * @return LotteryTermModel
	 */
	public LotteryTermModel queryTermCurrentInfo(int lotteryId) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("lotteryId", lotteryId);
		param.put("limitNum", 1);
		Object obj = super.smcTemplate.queryForObject(
				"lotteryTerm.queryTermCurrentInfo", param);
		return (obj != null) ? (LotteryTermModel) obj : null;
	}

	/**
	 * 
	 * Title: queryTermLastCashInfo<br>
	 * Description: <br>
	 * 获取彩种的最近一起开奖信息<br>
	 * 
	 * @param lotteryId
	 * @return LotteryTermModel
	 */
	public LotteryTermModel queryTermLastCashInfo(int lotteryId) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("lotteryId", lotteryId);
		Object obj = super.smcTemplate.queryForObject(
				"lotteryTerm.queryTermLastCashInfo", param);
		return (obj != null) ? (LotteryTermModel) obj : null;
	}

	/**
	 * 
	 * Title: queryTermCurrentInfo<br>
	 * Description: <br>
	 * 获取彩种的当前期信息列表，对某些彩种可能有多个当前期<br>
	 * 
	 * @param lotteryId
	 * @param limitNum
	 * @return List<LotteryTermModel>
	 */
	@SuppressWarnings("unchecked")
	public List<LotteryTermModel> queryTermCurrentInfo(int lotteryId,
			int limitNum) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("lotteryId", lotteryId);
		param.put("limitNum", limitNum <= 0 ? 1 : limitNum);
		return super.smcTemplate.queryForList(
				"lotteryTerm.queryTermCurrentInfo", param);
	}

	/**
	 * 
	 * Title: queryTermInfoList<br>
	 * Description: <br>
	 * 获取彩种的一定范围的彩期信息<br>
	 * 
	 * @param lotteryId
	 * @param startPageSize
	 * @param pageSize
	 * @return List<LotteryTermModel>
	 */
	@SuppressWarnings("unchecked")
	public List<LotteryTermModel> queryTermInfoList(int lotteryId,
			int startPageSize, int pageSize) {
		// return super.getSqlMapClient().queryForPaginatedList("", lotteryId,
		// pageSize);
		return super.smcTemplate.queryForList("lotteryTerm.queryTermInfoList",
				lotteryId, startPageSize - 1, pageSize);

	}

	/**
	 * 
	 * Title: queryLastTermInfo<br>
	 * Description: <br>
	 * 获取上一期的彩期信息<br>
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @return LotteryTermModel
	 */
	public LotteryTermModel queryLastTermInfo(int lotteryId, String termNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		Object obj = super.smcTemplate.queryForObject(
				"lotteryTerm.queryLastOneTermInfo", param);
		return (obj != null) ? (LotteryTermModel) obj : null;
	}

	/**
	 * 
	 * Title: queryCanOpTermNoList<br>
	 * Description: <br>
	 * 查询可以开奖、兑奖、派奖的期号列表<br>
	 * 
	 * @param lotteryId
	 * @param opType
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryCanOpTermNoList(int lotteryId, String opType,
			int limitNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", lotteryId);
		param.put("opType", opType);
		param.put("limitNum", limitNum <= 0 ? null : limitNum);
		return super.smcTemplate.queryForList(
				"lotteryTerm.queryCanOpTermNoList", param);
	}

	/**
	 * 
	 * Title: queryHaveWinTermInfo<br>
	 * Description: <br>
	 * 获取某一个彩种已经开奖的已售期信息列表<br>
	 * 
	 * @param lotteryId
	 * @param limitNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LotteryTermModel> queryHaveWinTermInfo(int lotteryId,
			int limitNum) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("lotteryId", lotteryId);
		param.put("limitNum", limitNum <= 0 ? 1 : limitNum);
		return super.smcTemplate.queryForList(
				"lotteryTerm.queryHaveWinTermInfo", param);
	}

	/**
	 * 根据开奖日期查询彩期
	 * 
	 * @param winLine
	 *            开奖时间以%yyyy-MM-dd%格式
	 * @return
	 */
	public List<LotteryTermModel> queryHaveWinTermInfobytime(String winLine) {
		return super.smcTemplate.queryForList(
				"lotteryTerm.queryHaveWinTermInfobytime", winLine);
	}

	/**
	 * 根据开奖日期查询彩期
	 * 
	 * @param winLine
	 *            开奖时间以'yyMMdd%‘格式
	 * @return
	 */
	public List<LotteryTermModel> queryHaveWinTermInfoToDate(Map map) {
		return super.smcTemplate.queryForList(
				"lotteryTerm.queryHaveWinTermInfoToDate", map);
	}

	/**
	 * 
	 * Title: updateTermLimitNumber<br>
	 * Description: <br>
	 * 更新限号信息<br>
	 * 
	 * @param lotteryId
	 * @param termNo
	 * @param limitNumber
	 * @return int
	 */
	public int updateTermLimitNumber(int lotteryId, String termNo,
			String limitNumber) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", lotteryId);
		param.put("termNo", termNo);
		param.put("limitNumber", limitNumber);
		return super.smcTemplate.update("lotteryTerm.updateTermLimitNumber",
				param);
	}

	/**
	 * 
	 * Title: queryTermInfoByDynamicCondition<br>
	 * Description: <br>
	 * <br>
	 * 根据条件动态查询彩期信息
	 * 
	 * @param lotteryId
	 * @param termStatus
	 * @param saleStatus
	 * @param winStatus
	 * @param limitNum
	 * @return List<LotteryTermModel>
	 */
	@SuppressWarnings("unchecked")
	public List<LotteryTermModel> queryCanOpTermInfoList(int lotteryId,
			String begin_termNo, String end_termNo, int limitNum, String opType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lotteryId", lotteryId);
		params.put("begin_termNo", begin_termNo);
		params.put("end_termNo", end_termNo);
		params.put("limitNum", limitNum <= 0 ? null : limitNum);
		params.put("opType", opType);
		return super.smcTemplate.queryForList(
				"lotteryTerm.queryCanOpTermInfoList", params);
	}

	public List<LotteryTermModel> getLotteryTermInfo(int lotteryId,
			String where, int startNumber, int endNumber) {
		if (endNumber <= startNumber) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", Integer.valueOf(lotteryId));
		param.put("wheres", where);
		param.put("startPageNumber", Integer.valueOf(startNumber));
		param.put("endPageNumber", Integer.valueOf(endNumber - startNumber));
		return (List<LotteryTermModel>) this.smcTemplate.queryForList(
				"lotteryTerm.getLotteryTermInfo", param);
	}

	public int getLotteryTermCount(int lotteryId, String where) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", Integer.valueOf(lotteryId));
		param.put("wheres", where);
		return (Integer) this.smcTemplate.queryForObject(
				"lotteryTerm.getLotteryTermCount", param);
	}

	// 潘祖朝修改
	public List<LotteryTermModel> getLotteryTermInfo(int lotteryId,
			String startTerm, String endTerm, int startNumber, int endNumber) {
		if (endNumber <= startNumber) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", Integer.valueOf(lotteryId));
		param.put("startTerm", startTerm);
		param.put("endTerm", endTerm);
		param.put("startPageNumber", Integer.valueOf(startNumber));
		param.put("endPageNumber", Integer.valueOf(endNumber - startNumber));
		return (List<LotteryTermModel>) this.smcTemplate.queryForList(
				"lotteryTerm.getLotteryTermInfo", param);
	}

	// 潘祖朝修改
	public int getLotteryTermCount(int lotteryId, String startTerm,
			String endTerm) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lotteryId", Integer.valueOf(lotteryId));
		param.put("startTerm", startTerm);
		param.put("endTerm", endTerm);
		return (Integer) this.smcTemplate.queryForObject(
				"lotteryTerm.getLotteryTermCount", param);
	}

	/**
	 * 
	 * Title: getCanPrintCondition<br>
	 * Description: <br>
	 * <br>
	 * 获取可以打票的彩期条件
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getCanPrintCondition() {
		return this.smcTemplate
				.queryForList("lotteryTerm.getCanPrintLotteryTerm");
	}

}
