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
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.service.OrderService;

@Controller
public class OrderController {
	
	private static final Logger logger = Logger.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/admin/order/orderList")
	@ResponseBody
	public String orderList(HttpServletRequest request , HttpServletResponse response , HttpSession session ,
			@RequestParam(value="pageIndex" , required = true) long pageIndex,
			@RequestParam(value="limit" , required = true) long limit ,
			@RequestParam(value="status" , required = false) String status ){
		//用户账户名和密码验证
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "orderList", orderService.orderList(pageIndex, limit, status));
		JsonUtils.setBody(result, "count", orderService.orderCount(status, null , null ));
		return result.toJSONString();
	}
	
	@RequestMapping(value="/admin/order/addKdInfo")
	@ResponseBody
	public String addKdInfo(HttpServletRequest request , HttpServletResponse response,
			@RequestParam(value="kdNo" , required = true) String kdNo,
			@RequestParam(value="kdName" , required = true) String kdName){
		try{
			orderService.addKdInfo(kdNo, kdName);
	    	return JsonUtils.commonJsonReturn().toJSONString();
    	}catch(Exception e){
			logger.error("addKdInfo error " ,e);
			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
		}
	}
	
}
