/**
 * Title: EhandMsgDao.java
 * @Package com.success.lottery.ehand.emsglog.dao
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-1-25 ����01:58:14
 * @version V1.0
 */
package com.success.lottery.ehand.eterm.dao.impl;

import com.success.lottery.ehand.eterm.dao.interf.BaseSqlMapDao;
import com.success.lottery.ehand.eterm.domain.EhandMsgModel;

/**
 * com.success.lottery.ehand.emsglog.dao
 * EhandMsgDao.java
 * EhandMsgDao
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-1-25 ����01:58:14
 * 
 */

public class EhandMsgDao extends BaseSqlMapDao{
	
	/**
	 * 
	 * Title: insertEhandMsg<br>
	 * Description: <br>
	 *              <br>��¼��������Ϣ
	 * @param eMsg
	 */
	public void insertEhandMsg(EhandMsgModel eMsg){
		this.smcTemplate.insert("ehandmsglog.insertEhandMsg", eMsg);
	}

}
