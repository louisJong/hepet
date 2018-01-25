package com.project.hepet.dao ;
import java.util.List;

import org.springframework.stereotype.Component;

import com.project.hepet.model.HepetGoodsCategory;
@Component
public class HepetGoodsCategoryDao extends GenericDAOImpl<HepetGoodsCategory> {
	@Override
	public String getNameSpace() {
		return "hepet_goods_category";
	}

	public List<HepetGoodsCategory> findAll() {
		return this.getSqlSession().selectList(this.getNameSpace()+".findAll");
	}
}
