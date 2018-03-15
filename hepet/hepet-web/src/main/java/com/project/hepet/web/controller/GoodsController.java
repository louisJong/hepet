package com.project.hepet.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.model.HepetGoods;
import com.project.hepet.model.HepetGoodsCategory;
import com.project.hepet.service.GoodsService;

@Controller
public class GoodsController {

	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping("/hepet/goodsCategorys")
	@ResponseBody
	String goodsCategorys(HttpServletRequest request){
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "goodsCategorys" , goodsService.goodsCategorys());
		return result.toJSONString();
	}
	
	@RequestMapping("/hepet/goodsDetail")
	@ResponseBody
	String goodsDetail(HttpServletRequest request ,
			@RequestParam(value="goodsId" , required=true) long goodsId , 
			@RequestParam(value="fetchMoreDetail" , required=false) boolean fetchMoreDetail){
		return goodsService.goodsDetail(goodsId, fetchMoreDetail).toJSONString();
	}
	
	@RequestMapping("/hepet/goodsList")
	@ResponseBody
	String goodsList(HttpServletRequest request ,
			@RequestParam(value="pageIndex" , required=true) long pageIndex ,
			@RequestParam(value="limit" , required=true) long limit ,
			@RequestParam(value="categoryCode" , required=false) String categoryCode){
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "goodsList" , goodsService.goodsList(pageIndex, limit, categoryCode , 1 , null , null));
		JsonUtils.setBody(result, "count" , goodsService.goodsCount(categoryCode , 1 , null , null));
		return result.toJSONString();
	}
	
	
	@RequestMapping("/hepet/mall")
	String mall(ModelMap modelMap , @RequestParam(value="categoryCode" , required=false) String categoryCode){
		List<HepetGoodsCategory> categorys = goodsService.goodsCategorys();
		modelMap.put("categorys", JsonUtils.modelToJsonString(categorys == null? new ArrayList<HepetGoodsCategory>() : categorys));
		modelMap.put("cCindex" , getIndex(categorys , categoryCode ));
		return "list";
	}
	
	private int getIndex(List<HepetGoodsCategory> categorys,
			String categoryCode) {
		if(categorys == null || categorys.isEmpty() || StringUtils.isBlank(categoryCode))
			return 0;
		for(int i = 0 ; i<categorys.size() ; i++){
			if(categoryCode.equals(categorys.get(i).getCode())){
				return i;
			}
		}
		return 0;
	}

	@RequestMapping("/hepet/goodsInfo")
	String goodsInfo(ModelMap modelMap , @RequestParam(value="goodsId" , required=true) long goodsId){
		HepetGoods goods = goodsService.goodsAllDetail(goodsId);
		if(goods==null || 1!=goods.getStatus().intValue()){//未上架
			modelMap.put("info", "商品未上架");
			return "common_result";
		}
		modelMap.put("goodsInfo", goodsService.goodsAllDetail(goodsId));
		modelMap.put("goodsId", goodsId);
		return "goodsinfo";
	}
	
}
