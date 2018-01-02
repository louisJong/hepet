package com.project.hepet.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	String login(ModelMap modelMap , HttpSession session ,@RequestParam(value="customerId" , required=true) String customerId ,
			@RequestParam(value="tel" , required=true) String tel ,@RequestParam(value="timeStamp" , required=true)  String timeStamp ,@RequestParam(value="token" , required=true)  String token, @RequestParam(value="sign" , required=true)  String sign){
		JSONObject result = new JSONObject();
		if(!verifySign(customerId , tel , timeStamp , token , sign)){
			modelMap.put("info", "登录错误-验签失败");
			return "common_result";
		}else{
			session.setAttribute("customerId", customerId);
			session.setAttribute("tel", tel);
			session.setAttribute("timeStamp", timeStamp);
			session.setAttribute("token", token);
			result.put("code", 0);
			result.put("token", token);
			result.put("msg", "登录成功");
		}
		return "forward:/hepet/index";
	}
	
	private boolean verifySign(String customerId, String tel, String timeStamp, String token,
			String sign) {
		String salt = "HepetMall2018";
		String sign_ = MD5Util.MD5Encode(customerId + tel + timeStamp + token + salt ).toLowerCase();
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
		String time = new Date().getTime()+"";
		System.out.println("time:"+time);
		System.out.println(MD5Util.MD5Encode("1018217148462"+time+"123HepetMall2018"));
	}
}
