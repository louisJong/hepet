package com.project.hepet.dao;

import java.util.List;

import org.springframework.util.Assert;

import com.project.hepet.model.BaseObject;



public  abstract  class GenericDAOImpl<T extends BaseObject> extends BaseDao{
	
	public abstract String getNameSpace();
	 /**
     * 返回产生的id<br>
     * 插入已提交记录
     */
	public Long insert(T bean){
		this.getSqlSession().insert(this.getNameSpace()+".insert", bean);
		return bean.getId();
	}
	
	 /**
     * 返回受影响的行数<br>
     * 批量插入
     */
	public int batchInsert(List<T> beans){
		return this.getSqlSession().insert(this.getNameSpace()+".batchInsert", beans);
	}
	
	
	/**
     * 根据主键查询一条记录
     */
	public T findById(Long id){
		return this.getSqlSession().selectOne(this.getNameSpace()+".findById", id);
	}
	 /**
     * 返回受影响的行数,慎用
     * 插入已提交记录
     */
	public int update(T bean){
		Assert.isTrue( bean.getId() !=null );
		return this.getSqlSession().update(this.getNameSpace()+".update", bean);
	}
}
