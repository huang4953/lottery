/**
 * Title: EhandMsgDao.java
 * @Package com.success.lottery.ehand.emsglog.dao
 * Description: TODO(用一句话描述该文件做什么)
 * @author gaoboqin
 * @date 2011-1-25 下午01:58:14
 * @version V1.0
 */
package com.success.lottery.ehand.eterm.dao.impl;

import com.success.lottery.ehand.eterm.dao.interf.BaseSqlMapDao;
import com.success.lottery.ehand.eterm.domain.EhandMsgModel;

/**
 * com.success.lottery.ehand.emsglog.dao
 * EhandMsgDao.java
 * EhandMsgDao
 * (这里用一句话描述这个类的作用)
 * @author gaoboqin
 * 2011-1-25 下午01:58:14
 * 
 */

public class EhandMsgDao extends BaseSqlMapDao{
	
	/**
	 * 
	 * Title: insertEhandMsg<br>
	 * Description: <br>
	 *              <br>记录掌中奕消息
	 * @param eMsg
	 */
	public void insertEhandMsg(EhandMsgModel eMsg){
		this.smcTemplate.insert("ehandmsglog.insertEhandMsg", eMsg);
	}

}
