package com.success.lottery.ehand.eterm.dao.interf;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class BaseSqlMapDao extends SqlMapClientDaoSupport {

	protected SqlMapClientTemplate smcTemplate = this.getSqlMapClientTemplate();

	public BaseSqlMapDao() {
	}

}
