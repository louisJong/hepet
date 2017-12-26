package com.project.hepet.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.LoginDesc;
import com.project.hepet.utils.EnvUtil;
import com.project.hepet.web.utils.WebUtil;
	
public class LoginInterceptor extends HandlerInterceptorAdapter {
//	private static Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("Access-Control-Allow-Origin"	, "*");
		response.addHeader("Access-Control-Allow-Headers"	, "X-Requested-With");
		if(EnvUtil.isDev() ){
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
	        response.setHeader("Access-Control-Allow-Credentials", "true");
		}
		if(request.getServletPath().contains("/app/enter") || request.getServletPath().contains("/logout")){
			return true;
		}
		if(handler instanceof  HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;	
			LoginDesc loginDesc = hm.getMethodAnnotation(LoginDesc.class);
			if(loginDesc!=null && loginDesc.login() && !WebUtil.isLogin(request) && !EnvUtil.isDev()){
				JSONObject isLoginJson = new JSONObject();
				JSONObject head = new JSONObject();
				head.put("code", "9900");
				head.put("msg", "登陆过期");
				isLoginJson.put("head", head);
				response.setContentType("text/html; charset=utf-8");
				PrintWriter writer = response.getWriter();
				writer.write(isLoginJson.toJSONString());
				writer.flush();
				return false;
			}
		}
		return true;
	}
	
}

