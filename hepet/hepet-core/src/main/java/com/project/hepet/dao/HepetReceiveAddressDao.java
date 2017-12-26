package com.project.hepet.dao ;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.project.hepet.model.HepetReceiveAddress;
@Component
public class HepetReceiveAddressDao extends GenericDAOImpl<HepetReceiveAddress> {
	@Override
	public String getNameSpace() {
		return "hepet_receive_address";
	}

	public List<HepetReceiveAddress> findByTelAndCustomerId(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getNameSpace()+".findByTelAndCustomerId" , param);
	}

	public HepetReceiveAddress getRecentUse(Map<String, Object> param) {
		return  this.getSqlSession().selectOne(this.getNameSpace()+".getRecentUse", param);
	}

	public HepetReceiveAddress getByIdTelCustomerId(Map<String, Object> param) {
		return  this.getSqlSession().selectOne(this.getNameSpace()+".getByIdTelCustomerId", param);
	}

	public long queryCount(Map<String, Object> param) {
		return  this.getSqlSession().selectOne(this.getNameSpace()+".queryCount", param);
	}

	public int setRecentUse(Map<String, Object> param) {
		return  this.getSqlSession().update(this.getNameSpace()+".setRecentUse", param);
	}

	public int setUnRecentUse(Map<String, Object> param) {
		return  this.getSqlSession().update(this.getNameSpace()+".setUnRecentUse", param);
	}
}
