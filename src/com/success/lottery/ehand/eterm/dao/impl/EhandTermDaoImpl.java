/**
 * Title: EhandTermDaoImpl.java
 * @Package com.success.lottery.ehand.eterm.dao.impl
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-19 ����06:29:30
 * @version V1.0
 */
package com.success.lottery.ehand.eterm.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.success.lottery.ehand.eterm.dao.interf.BaseSqlMapDao;
import com.success.lottery.ehand.eterm.domain.EhandTermModel;
import com.success.lottery.term.domain.LotteryTermModel;

/**
 * com.success.lottery.ehand.eterm.dao.impl
 * EhandTermDaoImpl.java
 * EhandTermDaoImpl
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-19 ����06:29:30
 * 
 */

public class EhandTermDaoImpl extends BaseSqlMapDao {
	
	/**
	 * 
	 * Title: getEhandTermInfo<br>
	 * Description: <br>
	 *              <br>���ݲ��ֵĲ��ڻ�ȡ���ڵ���Ϣ
	 * @param lotteryId
	 * @param ehandLotteryId
	 * @param issue
	 * @return
	 */
	public EhandTermModel getEhandTermInfo(int lotteryId,String ehandLotteryId,String issue){
		EhandTermModel params = new EhandTermModel();
		params.setLotteryId(lotteryId);
		params.setEhandLotteryId(ehandLotteryId);
		params.setIssue(issue);
		Object obj = super.smcTemplate.queryForObject("ehandTerm.queryEhandTermInfo", params);
		return (obj!=null)?(EhandTermModel)obj:null;
	}
	/**
	 * 
	 * Title: updateEhandTermInfo<br>
	 * Description: <br>
	 *              <br>���²��ڵ���Ϣ
	 * @param termNodel
	 * @return
	 */
	public int updateEhandTermInfo(EhandTermModel termNodel){
		return super.smcTemplate.update("ehandTerm.updateEhandTermInfo", termNodel);
	}

}
