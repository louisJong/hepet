package com.project.hepet.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
	public List<HepetGoods> goodsList(long pageIndex, long limit, String categoryCode , int fetchSj , String brandName , String goodsName) {
		limit = limit==0? 10 : limit;
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("startRow", pageIndex*limit);
		param.put("limit", limit);
		param.put("brandName", brandName);
		param.put("goodsName", goodsName);
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
			goods = goodsDao.findByIdAllDetail(goodsId);
		JSONObject result = JsonUtils.commonJsonReturn();
		if( goods==null || 1!=goods.getStatus() ){//未上架
			return JsonUtils.commonJsonReturn("1001", "商品未上架");
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
		Date now = new Date();
		goods.setCreateTime(now);
		goods.setUpdateTime(now);
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
	public long goodsCount(String categoryCode , int fetchSj , String brandName , String goodsName) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("categoryCode", categoryCode);
		param.put("fetchSj", fetchSj);
		param.put("brandName", brandName);
		param.put("goodsName", goodsName);
		return goodsDao.findGoodsCount(param);
	}

	@Override
	public HepetGoods goodsAllDetail(long goodsId) {
		return goodsDao.findByIdAllDetail(goodsId);
	}

	@Override
	public JSONObject indexProList() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<HepetGoods> goodsList = goodsDao.indexProList(param);
		JSONObject result = new JSONObject();
		for(HepetGoods goods:goodsList) {
			List<JSONObject> list = new ArrayList<JSONObject>();
			String categoryCode = goods.getCategoryCode();
			if("2".equals(goods.getChooseLevel())) {
				categoryCode = "sytj";
			}
			if(result.containsKey(categoryCode)) {
				list = result.getObject(categoryCode, List.class);
			}
			JSONObject obj = new JSONObject();
			obj.put("id", goods.getId());
			obj.put("categoryCode", goods.getCategoryCode());
			obj.put("goodsName", goods.getBrandName());
			obj.put("chooseReason", goods.getChooseReason());
			obj.put("marketPrice", goods.getMarketPrice());
			obj.put("price", goods.getPrice());
			obj.put("period", goods.getPeriod());
			obj.put("pricePerPeriod", goods.getPricePerPeriod());
			obj.put("listImgUrl", goods.getListImgUrl());
			if(goods.getMarketPrice()!=null&&goods.getPrice()!=null&&goods.getPrice().compareTo(goods.getMarketPrice())<0) {
				obj.put("diffPrice", goods.getMarketPrice().subtract(goods.getPrice()));
			}
			if(goods.getSoldCount()==null ||goods.getStock()==null||goods.getSoldCount()<goods.getStock()) {
				obj.put("isSellOut", false);
			}else {
				obj.put("isSellOut", true);
			}
			list.add(obj);
			result.put(categoryCode, list);
		}
		return result;
	}
	
	
	@Override
	public JSONObject appIndexProList() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<HepetGoods> goodsList = goodsDao.appIndexProList(param);
		if(CollectionUtils.isEmpty(goodsList)) {
			logger.warn("App 首页推荐商品查询为空");
			return null;
		}
		JSONObject result = JsonUtils.commonJsonReturn();
		List<JSONObject> list = new ArrayList<JSONObject>();
		for(HepetGoods goods:goodsList) {
			JSONObject obj = new JSONObject();
			obj.put("id", goods.getId());
			obj.put("categoryCode", goods.getCategoryCode());
			obj.put("goodsName", goods.getBrandName());
			obj.put("chooseReason", goods.getChooseReason());
			obj.put("marketPrice", goods.getMarketPrice());
			obj.put("price", goods.getPrice());
			obj.put("period", goods.getPeriod());
			obj.put("pricePerPeriod", goods.getPricePerPeriod());
			obj.put("listImgUrl", goods.getListImgUrl());
			if(goods.getMarketPrice()!=null&&goods.getPrice()!=null&&goods.getPrice().compareTo(goods.getMarketPrice())<0) {
				obj.put("diffPrice", "直降"+goods.getMarketPrice().subtract(goods.getPrice()));
			}
			if(goods.getSoldCount()==null ||goods.getStock()==null||goods.getSoldCount()<goods.getStock()) {
				obj.put("isSellOut", false);
			}else {
				obj.put("isSellOut", true);
			}
			list.add(obj);
		}
		if(!CollectionUtils.isEmpty(list)&&list.size()>5) {
			JsonUtils.setBody(result, "list", list.subList(0, 5));
		}else {
			JsonUtils.setBody(result, "list", list);
		}
		return result;
	}
	

}
