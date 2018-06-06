package com.project.hepet.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
	String login(ModelMap modelMap , HttpSession session ,@RequestParam(value="customerId" , required=false) String customerId ,
			@RequestParam(value="tel" , required=false) String tel ,@RequestParam(value="timeStamp" , required=false)  String timeStamp ,@RequestParam(value="token" , required=false)  String token, @RequestParam(value="sign" , required=false)  String sign,
			@RequestParam(value="gotoUrl" , required=false)  String gotoUrl){
		JSONObject result = new JSONObject();
		if(!verifySign(customerId , tel , timeStamp , token , sign, gotoUrl)){
			modelMap.put("info", "登录错误-验签失败");
			return "common_result";
		}else{
			if(StringUtils.isEmpty(gotoUrl)) {
				gotoUrl = "/hepet/index";
			}
			session.setAttribute("customerId", customerId);
			session.setAttribute("tel", tel);
			session.setAttribute("timeStamp", timeStamp);
			session.setAttribute("token", token);
			result.put("code", 0);
			result.put("token", token);
			result.put("msg", "登录成功");
			return "forward:"+gotoUrl;
		}
	}
	
	private boolean verifySign(String customerId, String tel, String timeStamp, String token,
			String sign, String gotoUrl) {
		String salt = "HepetMall2018";
		customerId = StringUtils.isEmpty(customerId)?"":customerId;
		tel = StringUtils.isEmpty(tel)?"":tel;
		timeStamp = StringUtils.isEmpty(timeStamp)?"":timeStamp;
		token = StringUtils.isEmpty(token)?"":token;
		gotoUrl = StringUtils.isEmpty(gotoUrl)?"":gotoUrl;
		//TODO gotoUrl  customerId + tel + gotoUrl + timeStamp + token + salt
		String sign_ = MD5Util.MD5Encode(customerId + tel + timeStamp + token + salt).toLowerCase();
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
	
	@RequestMapping("/hepet/index/prolist")
	@ResponseBody
	String indexProList(ModelMap modelMap){
		JSONObject result = goodsService.indexProList();
		return result.toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/my")
	String my(){
		return "my";
	}
	
}
