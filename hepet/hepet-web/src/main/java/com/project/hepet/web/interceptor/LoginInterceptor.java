package com.project.hepet.web.interceptor;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.GateApiUtils;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.common.utils.LoginDesc;
import com.project.hepet.utils.EnvUtil;
import com.project.hepet.web.utils.WebUtil;
	
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("Access-Control-Allow-Origin"	, "*");
		response.addHeader("Access-Control-Allow-Headers"	, "X-Requested-With");
		if(EnvUtil.isDev() ){
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
	        response.setHeader("Access-Control-Allow-Credentials", "true");
		}
		String gotoUrl = request.getRequestURI();
		Map<String, String> params = WebUtil.getRequestParamsMap(request);
		if(gotoUrl.contains("/hepet-web/hepet/user/acctStatus")) {
			gotoUrl = "/hepet/goodsInfo";
		}
		String handlerGotoUrl = WebUtil.gotoUrlHandler(gotoUrl, params, false);
		params.put("gotoUrl", handlerGotoUrl);
		logger.info("请求地址："+gotoUrl+",请求参数："+params);
		
		 //判断是否为ajax请求，默认不是  
        boolean isAjaxRequest = false;  
        if(!StringUtils.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){  
            isAjaxRequest = true;  
        }  
		if(handler instanceof  HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;	
			LoginDesc loginDesc = hm.getMethodAnnotation(LoginDesc.class);
			if(loginDesc!=null && loginDesc.login() && !WebUtil.isLogin(request)){
				response.setContentType("text/html; charset=utf-8");
				String callLoginContent = null;
				if(isAjaxRequest) {
					callLoginContent = GateApiUtils.getAppFuncScript("zylogin", params);
					JSONObject result = JsonUtils.commonJsonReturn("0001", "用户未登录");
					JsonUtils.setBody(result, "funcSc", callLoginContent);
					callLoginContent = result.toJSONString();
				}else {
					callLoginContent = GateApiUtils.getAppFuncHtml("zylogin", params);
				}
				logger.info("callLoginContent:"+callLoginContent);
				PrintWriter writer = response.getWriter();
				writer.write(callLoginContent);
				writer.flush();
				return false;
			}
		}
		return true;
	}
	
}

