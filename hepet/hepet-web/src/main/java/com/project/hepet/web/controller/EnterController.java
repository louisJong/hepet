package com.project.hepet.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.common.utils.LoginDesc;
import com.project.hepet.common.utils.MD5Util;
import com.project.hepet.model.HepetBanner;
import com.project.hepet.model.HepetGoodsCategory;
import com.project.hepet.service.GoodsService;

@Controller
public class EnterController {
	
	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping("/hepet/login")
	@ResponseBody
	JSONObject login(HttpSession session ,@RequestParam(value="customerId" , required=true) String customerId ,
			@RequestParam(value="tel" , required=true) String tel ,@RequestParam(value="timeStamp" , required=true)  String timeStamp , @RequestParam(value="sign" , required=true)  String sign){
		JSONObject result = new JSONObject();
		if(!verifySign(customerId , tel , timeStamp , sign)){
			result.put("code", -1);
			result.put("msg", "签名错误");
		}else{
			String token = UUID.randomUUID().toString().replace("-", "");
			session.setAttribute("customerId", customerId);
			session.setAttribute("tel", tel);
			session.setAttribute("timeStamp", timeStamp);
			session.setAttribute("token", token);
			result.put("code", 0);
			result.put("token", token);
			result.put("msg", "登录成功");
		}
		return result;
	}
	
	private boolean verifySign(String customerId, String tel, String timeStamp,
			String sign) {
		String salt = "HepetMall2018";
		String sign_ = MD5Util.MD5Encode(customerId + tel + timeStamp + salt );
		return sign_.equals(sign);
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
	
	public static void main(String[] args) {
		System.out.println(MD5Util.MD5Encode("10182171484622017-12-31 14:57:23HepetMall2018"));
	}
}
