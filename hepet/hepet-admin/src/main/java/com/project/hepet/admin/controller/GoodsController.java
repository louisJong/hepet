package com.project.hepet.admin.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.admin.util.WebUtil;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.dao.HepetGoodsDao;
import com.project.hepet.model.HepetGoods;
import com.project.hepet.model.HepetGoodsCategory;
import com.project.hepet.service.FileService;
import com.project.hepet.service.GoodsService;

@Controller
public class GoodsController {
	
	private static final Logger logger = Logger.getLogger(GoodsController.class);
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private HepetGoodsDao goodsDao;
	
	@RequestMapping("/hepet/addGoods")
	@ResponseBody
	String addGoods(HttpServletRequest request,
			Long id,
			@RequestParam(value="categoryCode" , required=true) String categoryCode ,
			@RequestParam(value="brandCode" , required=true) String brandCode ,
			@RequestParam(value="brandName" , required=true) String brandName,
			@RequestParam(value="goodsName" , required=true) String goodsName ,
			@RequestParam(value="subDesc" , required=true) String subDesc ,
			@RequestParam(value="listImgUrl" , required=true) String listImgUrl,
			@RequestParam(value="detailImgUrls" , required=true) String detailImgUrls ,
			@RequestParam(value="price" , required=true) String price ,
			@RequestParam(value="period" , required=false) Long period,
			@RequestParam(value="isLogistics" , required=false) Integer isLogistics,
			@RequestParam(value="restrictType" , required=false) Integer restrictType ,
			@RequestParam(value="restrictNum" , required=false) Long restrictNum ,
			@RequestParam(value="restrictDesc" , required=false) String restrictDesc,
			@RequestParam(value="descript" , required=false) String descript,
			@RequestParam(value="stock" , required=false) Long stock,
			@RequestParam(value="fetchInfo" , required=false) String fetchInfo,
			@RequestParam(value="source" , required=false) String source,
			@RequestParam(value="tags" , required=false) String tags,
			@RequestParam(value="proDetail" , required=false) String proDetail,
			@RequestParam(value="sendType" , required=false) String sendType,
			@RequestParam(value="profit" , required=false) Long profit,
			@RequestParam(value="region" , required=false) String region,
			@RequestParam(value="marketPrice" , required=true) String marketPrice){
		period = period == null ? 12L : period;
	    isLogistics = isLogistics == null ? 1 : isLogistics;
	    profit = profit==null ? 10L : profit;
		HepetGoods goods = new HepetGoods();
		goods.setBrandCode(brandCode);
		goods.setDetailImgUrls(detailImgUrls);
		goods.setBrandName(brandName);
		goods.setCategoryCode(categoryCode);
		goods.setDescript(descript);
		goods.setId(id);
		goods.setFetchInfo(fetchInfo).setCreateUser(WebUtil.getUserName(request));
		goods.setGoodsName(goodsName).setIsLogistics(isLogistics).setListImgUrl(listImgUrl).setPricePerPeriod(new BigDecimal(price).divide(new BigDecimal(period), 2 , RoundingMode.CEILING))
		.setPeriod(period).setPrice(new BigDecimal(price)).setProDetail(proDetail).setProfit(profit)
		.setRestrictDesc(restrictDesc).setRestrictNum(restrictNum).setRestrictType(restrictType)
		.setSendType(sendType).setSource(source).setTags(tags).setSubDesc(subDesc).setStock(stock).setRegion(region).setMarketPrice(new BigDecimal(marketPrice))
		.setSoldCount(0l);
		if(id == null){
			goodsService.addGoods(goods);
		}else{
			goodsDao.update(goods);
		}
		JSONObject result = JsonUtils.commonJsonReturn();
		return result.toJSONString();
	}
	
	@RequestMapping("/hepet/putSoldOrOff")
	@ResponseBody
	String putSoldOrOff(HttpServletRequest request,
			@RequestParam(value="goodsId" , required=true) long goodsId,
			@RequestParam(value="status" , required=true) int status){
		goodsService.putSoldOrOff(goodsId , WebUtil.getUserName(request),status);
		JSONObject result = JsonUtils.commonJsonReturn();
		return result.toJSONString();
	}
	
	@RequestMapping("/hepet/addGoodsCategorys")
	@ResponseBody
	String addGoodsCategorys(HttpServletRequest request,
			@RequestParam(value="code" , required=true) String code ,
			@RequestParam(value="name" , required=true) String name ,
			@RequestParam(value="descript" , required=false) String descript
			){
		HepetGoodsCategory goodsCategory = new HepetGoodsCategory();
		goodsCategory.setCode(code);
		goodsCategory.setDescript(descript);
		goodsCategory.setName(name);
		goodsService.addGoodsCategorys(goodsCategory);
		JSONObject result = JsonUtils.commonJsonReturn();
		return result.toJSONString();
	}
	
	@RequestMapping("/hepet/goodsList")
	@ResponseBody
	String goodsList(HttpServletRequest request ,
			@RequestParam(value="pageIndex") long pageIndex ,
			@RequestParam(value="limit") long limit ,
			String categoryCode,
			String goodsName , 
			String brandName){
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "goodsList" , goodsService.goodsList(pageIndex, limit, categoryCode , 0 , brandName , goodsName));
		JsonUtils.setBody(result, "count" , goodsService.goodsCount(categoryCode, 0));
		return result.toJSONString();
	}
	
	@RequestMapping("/hepet/goodsDetail")
	@ResponseBody
	String goodsDetail(HttpServletRequest request ,
			@RequestParam(value="goodsId" , required=true) long goodsId){
		JSONObject result = JsonUtils.commonJsonReturn();
		result.put("info", goodsDao.findById(goodsId));
		return result.toJSONString();
	}
	
	@RequestMapping("/hepet/goodsCategorys")
	@ResponseBody
	String goodsCategorys(HttpServletRequest request){
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "goodsCategorys" , goodsService.goodsCategorys());
		return result.toJSONString();
	}
	
	
	@RequestMapping(value="/hepet/file/upload")
    @ResponseBody
    public String upload(HttpServletRequest request, HttpServletResponse response ) {
		try{
			JSONObject result = JsonUtils.commonJsonReturn();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			if (file!=null && !file.isEmpty()) {
				String extend = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toLowerCase();
				String fileName = UUID.randomUUID().toString().replaceAll("-", "");
				if (fileName.lastIndexOf('.') < 0) {
					fileName = fileName + "." + extend;
				}
				FileItem df = (FileItem)file.getFileItem();
				result = fileService.uploadByByte(fileName, df.get() , null);
			}
			return result.toJSONString();
		}catch(Exception e){
			logger.error( "upload error " , e);
			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
		}
	}
	
}
