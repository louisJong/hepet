package com.project.hepet.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.model.HepetBanner;
import com.project.hepet.model.HepetGoods;
import com.project.hepet.model.HepetGoodsCategory;

public interface GoodsService {
	
	/**
	 * 商品列表
	 * @param pageIndex
	 * @param limit
	 * @param categoryCode 分类代码
	 * @param fetchSj 是否查询上架
	 * @return
	 */
	 List<HepetGoods>  goodsList(long pageIndex , long limit , String categoryCode , int fetchSj , String brandName , String goodsName);

	 /**
	  * 商品总个数
	 * @return
	 */
	long goodsCount(String categoryCode, int fetchSj);
	 
	/**
	 * 商品详情
	 * @param goodsId 商品id
	 * @param fetchMoreDetail 拉取详细描述
	 * @return
	 */
	JSONObject goodsDetail(long goodsId , boolean fetchMoreDetail);
	
	
	/**
	 * 商品分类
	 * @return
	 */
	List<HepetGoodsCategory> goodsCategorys();
	
	void addGoodsCategorys(HepetGoodsCategory goodsCategory);
	
	long addGoods(HepetGoods goods);

	void putSoldOrOff(long goodsId, String userName, int status);
	 
    List<HepetBanner> getSortedBanners();

    HepetGoods goodsAllDetail(long goodsId);
}
