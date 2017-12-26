package com.project.hepet.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.common.utils.LoginDesc;
import com.project.hepet.model.HepetBanner;
import com.project.hepet.model.HepetGoodsCategory;
import com.project.hepet.service.GoodsService;

@Controller
public class EnterController {
	
	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping("/app/enter")
	String addAddress(HttpServletRequest request , HttpSession session ,@RequestParam(value="customerId" , required=true) long customerId ,
			@RequestParam(value="tel" , required=true) String tel ){
		session.setAttribute("customerId", customerId);
		session.setAttribute("tel", tel);
		return "forward:/hepet/index";
	}
	
	@RequestMapping("/hepet/index")
	String index(ModelMap modelMap){
		List<HepetBanner> banners = goodsService.getSortedBanners();
		List<HepetGoodsCategory> categorys = goodsService.goodsCategorys();
		modelMap.put("banners", JsonUtils.modelToJsonString(banners== null? new ArrayList<HepetBanner>() : banners));
		modelMap.put("categorys", JsonUtils.modelToJsonString(categorys == null? new ArrayList<HepetGoodsCategory>() : categorys));
		return "home";
	}
	
	@LoginDesc
	@RequestMapping("/hepet/my")
	String my(){
		return "my";
	}
	
}
