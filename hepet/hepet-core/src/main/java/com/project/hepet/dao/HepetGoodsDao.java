package com.project.hepet.dao ;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.project.hepet.model.HepetGoods;
@Component
public class HepetGoodsDao extends GenericDAOImpl<HepetGoods> {
	@Override
	public String getNameSpace() {
		return "hepet_goods";
	}

	public List<HepetGoods> findGoods(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getNameSpace()+".findGoods" , param);
	}

	public HepetGoods findByIdSimple(long goodsId) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".findByIdSimple", goodsId);
	}

	public HepetGoods findByIdDetail(long goodsId) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".findByIdDetail", goodsId);
	}
	
	/**
	 * @param param
	 * soldNum,id
	 * @return
	 */
	public int addSoldCount(Map<String, Object> param) {
		return this.getSqlSession().update(this.getNameSpace()+".addSoldCount", param);
	}

	public long findGoodsCount(Map<String, Object> param) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".findGoodsCount", param);
	}

	public HepetGoods findByIdAllDetail(long goodsId) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".findByIdAllDetail", goodsId);
	}

	public int deductStock(Map<String, Object> param) {
		return this.getSqlSession().update(this.getNameSpace()+".deductStock", param);
	}
	
	/**
	 * 首页商品列表查询
	 * @param param
	 * @return
	 */
	public List<HepetGoods> indexProList(Map<String, Object> param){
		return this.getSqlSession().selectList(this.getNameSpace()+".indexProList" , param);
	}
	
	/**
	 * app首页商品列表查询
	 * @param param
	 * @return
	 */
	public List<HepetGoods> appIndexProList(Map<String, Object> param){
		return this.getSqlSession().selectList(this.getNameSpace()+".appIndexProList" , param);
	}
}
