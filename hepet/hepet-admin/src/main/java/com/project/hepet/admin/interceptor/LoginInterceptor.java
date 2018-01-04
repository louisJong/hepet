package com.project.hepet.admin.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.admin.util.WebUtil;
import com.project.hepet.utils.EnvUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
//		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//	    response.setHeader("Access-Control-Max-Age", "3600");
//	    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//	    response.setHeader("Access-Control-Allow-Credentials","true"); //是否允许浏览器携带用户身份信息（cookie）
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		if(EnvUtil.isDev()){
			logger.info("request.getHerder('Origin') : " + request.getHeader("Origin"));
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); 
		}
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    response.setHeader("Access-Control-Max-Age", "3600");
    	response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    	response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,Authorization");
//		if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
//	    	response.setStatus(200);
//	    	return true;
//	    }
		if(request.getServletPath().contains("/login") || request.getServletPath().contains("/logout")){
			return true;
		}
		if(!WebUtil.isLogin(request)){
			JSONObject isLoginJson = new JSONObject();
			JSONObject head = new JSONObject();
			head.put("code", "9900");
			head.put("msg", "未登录，请先登录");
			isLoginJson.put("head", head);
			response.setContentType("text/html; charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(isLoginJson.toJSONString());
			writer.flush();
			return false;
		}
//		if(WebUtil.isNeedChangePwd(request) && !request.getServletPath().contains("/admin/user/modify_pwd")){//需要修改密码
//			JSONObject isLoginJson = new JSONObject();
//			JSONObject head = new JSONObject();
//			head.put("code", "5000");
//			head.put("msg", "180天未修改密码，需要修改密码");
//			isLoginJson.put("head", head);
//			response.setContentType("text/html; charset=utf-8");
//			PrintWriter writer = response.getWriter();
//			writer.write(isLoginJson.toJSONString());
//			writer.flush();
//			return false;
//		}
		return true;
	}
	
}

