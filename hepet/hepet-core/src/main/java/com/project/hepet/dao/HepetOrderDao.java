package com.project.hepet.dao ;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.project.hepet.model.HepetOrder;
@Component
public class HepetOrderDao extends GenericDAOImpl<HepetOrder> {
	@Override
	public String getNameSpace() {
		return "hepet_order";
	}

	public List<HepetOrder> findOrders(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getNameSpace()+".findOrders", param);
	}

	public HepetOrder findDetail(long orderId, Long customerId) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		param.put("customerId", customerId);
		return this.getSqlSession().selectOne(this.getNameSpace()+".findDetail", param);
	}

	public void addKdInfo(Map<String, Object> param) {
		this.getSqlSession().update(this.getNameSpace()+".addKdInfo" , param);
	}

	public long orderCount(Map<String, Object> param) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".orderCount", param);
	}

	public long allOrderCount(Map<String, Object> params) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".allOrderCount", params);
	}
	
	public double allOrderSum(Map<String, Object> params) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".allOrderSum", params);
	}

	public List<HepetOrder> queryCanCloseOrder(Date date) {
		return this.getSqlSession().selectList(this.getNameSpace()+".queryCanCloseOrder", date);
	}
	
	public List<HepetOrder> queryCanFinishOrder(Date date) {
		return this.getSqlSession().selectList(this.getNameSpace()+".queryCanFinishOrder", date);
	}

	public HepetOrder queryByPayNum(String payNum) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".queryByPayNum", payNum);
	}
}
