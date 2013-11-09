/**
 * Title: LotteryTermDaoImpl.java
 * @Package com.success.lottery.term.dao.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-4-7 ����10:06:01
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
 * (������һ�仰��������������)
 * 
 * @author gaoboqin 2010-4-7 ����10:06:01
 * 
 */

public class LotteryTermDaoImpl extends BaseSqlMapDao {

	/**
	 * 
	 * Title: updateTermStatus<br>
	 * Description: <br>
	 * ���²���״̬<br>
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
	 * ���뿪����Ϣ<br>
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
	 * ����������Ϣ<br>
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
	 * ���²��ڿ���״̬<br>
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
	 * ��ȡ���ֲ�����Ϣ<br>
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
	 * ��ȡ���ֵĵ�ǰ����Ϣ<br>
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
	 * ��ȡ���ֵ����һ�𿪽���Ϣ<br>
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
	 * ��ȡ���ֵĵ�ǰ����Ϣ�б���ĳЩ���ֿ����ж����ǰ��<br>
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
	 * ��ȡ���ֵ�һ����Χ�Ĳ�����Ϣ<br>
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
	 * ��ȡ��һ�ڵĲ�����Ϣ<br>
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
	 * ��ѯ���Կ������ҽ����ɽ����ں��б�<br>
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
	 * ��ȡĳһ�������Ѿ���������������Ϣ�б�<br>
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
	 * ���ݿ������ڲ�ѯ����
	 * 
	 * @param winLine
	 *            ����ʱ����%yyyy-MM-dd%��ʽ
	 * @return
	 */
	public List<LotteryTermModel> queryHaveWinTermInfobytime(String winLine) {
		return super.smcTemplate.queryForList(
				"lotteryTerm.queryHaveWinTermInfobytime", winLine);
	}

	/**
	 * ���ݿ������ڲ�ѯ����
	 * 
	 * @param winLine
	 *            ����ʱ����'yyMMdd%����ʽ
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
	 * �����޺���Ϣ<br>
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
	 * ����������̬��ѯ������Ϣ
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

	// ���泯�޸�
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

	// ���泯�޸�
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
	 * ��ȡ���Դ�Ʊ�Ĳ�������
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getCanPrintCondition() {
		return this.smcTemplate
				.queryForList("lotteryTerm.getCanPrintLotteryTerm");
	}

}
