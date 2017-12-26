package com.project.hepet.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.admin.util.WebUtil;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.service.UserService;

@Controller
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
//	@RequestMapping(value="/admin/login/img_code")
//	@ResponseBody
//	public void getImgCode(HttpServletRequest request , HttpServletResponse response , HttpSession session) throws IOException{
//		String verifyCode = VerifyCodeUtils.outputVerifyImage(200, 80, response.getOutputStream(), 4);
//		session.setAttribute("imgVerifyCode", verifyCode);
//	}
	
//	@RequestMapping(value="/admin/login/imgCode/verify")
//	@ResponseBody
//	public JSONObject verifyImgCode(HttpServletRequest request , HttpServletResponse response , HttpSession session , @RequestParam(value="imgverify" , required = true) String custVerifyCode){
//		JSONObject result = JsonUtils.commonJsonReturn();
//		String sessionCode = (String)session.getAttribute("imgVerifyCode");
//		if(!StringUtils.equalsIgnoreCase(custVerifyCode, sessionCode)){
//			return JsonUtils.commonJsonReturn("0001", "验证码错误");
//		}
//		session.removeAttribute("imgVerifyCode");
//		return result;
//	}
	
	
	@RequestMapping(value="/admin/user/login")
	@ResponseBody
	public String userLogin(HttpServletRequest request , HttpServletResponse response , HttpSession session ,
			@RequestParam(value="username" , required = true) String userName,
			@RequestParam(value="password" , required = true) String loginPwd){
		//用户账户名和密码验证
		JSONObject loginResult = userService.login(userName, loginPwd);
		if(JsonUtils.equalDefSuccCode(loginResult)){
			session.setAttribute("userId", JsonUtils.getBodyValue(loginResult, "userId"));
			session.setAttribute("userName", userName);
		}
		return loginResult.toJSONString();
	}
	
	@RequestMapping(value="/admin/user/logout")
	@ResponseBody
	public String userLogout(HttpServletRequest request , HttpServletResponse response){
		try{
	    	WebUtil.logOut(request);
	    	return JsonUtils.commonJsonReturn().toJSONString();
    	}catch(Exception e){
			logger.error("logout error " ,e);
			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
		}
	}
	
}
