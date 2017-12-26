package com.project.hepet.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.dao.HepetBannerDao;
import com.project.hepet.dao.HepetGoodsCategoryDao;
import com.project.hepet.dao.HepetGoodsDao;
import com.project.hepet.model.HepetBanner;
import com.project.hepet.model.HepetGoods;
import com.project.hepet.model.HepetGoodsCategory;
import com.project.hepet.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	private static final Logger logger = Logger.getLogger(GoodsServiceImpl.class);
	
	@Autowired
	private HepetGoodsCategoryDao goodsCategoryDao;
	
	@Autowired
	private HepetGoodsDao goodsDao;
	
	@Autowired
	private HepetBannerDao bannerDao;
	
	@Override
	public List<HepetGoods> goodsList(long pageIndex, long limit, String categoryCode , int fetchSj) {
		limit = limit==0? 10 : limit;
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("startRow", pageIndex*limit);
		param.put("limit", limit);
		param.put("categoryCode", categoryCode);
		param.put("fetchSj", fetchSj);
		return goodsDao.findGoods(param);
	}

	@Override
	public JSONObject goodsDetail(long goodsId, boolean fetchMoreDetail) {
		HepetGoods goods = null;
		if(!fetchMoreDetail)
			goods = goodsDao.findByIdSimple(goodsId);
		else
			goods = goodsDao.findByIdDetail(goodsId);
		JSONObject result = JsonUtils.commonJsonReturn();
		if( goods==null || 0==goods.getStatus() ){//下架
			return JsonUtils.commonJsonReturn("1001", "商品已下架");
		}
		JsonUtils.setBody(result, "info", goods);
		return result;
	}

	@Override
	public List<HepetGoodsCategory> goodsCategorys() {
		return goodsCategoryDao.findAll();
	}

	@Override
	public void addGoodsCategorys(HepetGoodsCategory goodsCategory) {
		try{
			goodsCategory.setCreateTime(new Date());
			goodsCategory.setStatus(1);
			goodsCategoryDao.insert(goodsCategory);
		}catch(DuplicateKeyException e){
			logger.error("addGoodsCategorys error goodsCategory = "+ JsonUtils.modelToJsonString(goodsCategory) , e);
		}
	}

	@Override
	public long addGoods(HepetGoods goods) {
		goods.setCreateTime(new Date());
		goods.setStatus(0);
		return goodsDao.insert(goods);
	}

	@Override
	public void putSoldOrOff(long goodsId, String operator, int status) {
		HepetGoods updateBean = new HepetGoods();
		updateBean.setId(goodsId);
		updateBean.setStatus(status);
		Date now = new Date();
		if(1==status){
			updateBean.setPutawayOpertor(operator);
			updateBean.setPutawayTime(now);
		}
		else if(2==status){
		  updateBean.setSoldoutOpertor(operator);
		  updateBean.setSoldoutTime(now);
		}
		updateBean.setUpdateTime(now);
		updateBean.setUpdateUser(operator);
		goodsDao.update(updateBean);
	}

	@Override
	public List<HepetBanner> getSortedBanners() {
		return bannerDao.getSortedBanners();
	}

	@Override
	public long goodsCount(String categoryCode) {
		return goodsDao.findGoodsCount(categoryCode);
	}

	@Override
	public HepetGoods goodsAllDetail(long goodsId) {
		return goodsDao.findByIdAllDetail(goodsId);
	}

}
