package com.project.hepet.dao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public   class BaseDao  {
	protected static Logger logger = Logger.getLogger(BaseDao.class);
	
	@Autowired
	private org.mybatis.spring.SqlSessionTemplate sqlSessionWriter;
	
	public org.mybatis.spring.SqlSessionTemplate getSqlSession() {
		return sqlSessionWriter;
	}
	
}
