package com.project.hepet.admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
			String status,
			String goodsName,
			String categoryCode,
			String brandName,
			String orderNum,
			String startTime,
			String endTime,
			String phone) throws ParseException{
		//用户账户名和密码验证
		JSONObject result = JsonUtils.commonJsonReturn();
		Map<String , Object> params = new HashMap<String , Object>();
		params.put("startRow", pageIndex*limit);
		params.put("limit", limit);
		params.put("status", status);
		params.put("goodsName", goodsName);
		params.put("categoryCode", categoryCode);
		params.put("brandName", brandName);
		params.put("orderNum", orderNum);
		params.put("startTime", startTime);
		if(StringUtils.isNotBlank(endTime)){
			params.put("endTime", DateUtils.addDays(new SimpleDateFormat("yyyy-MM-dd").parse(endTime), 1));
		}
		params.put("phone", phone);
		JsonUtils.setBody(result, "orderList", orderService.orderList(params));
		JsonUtils.setBody(result, "count", orderService.allOrderCount(params));
		return result.toJSONString();
	}
	
	@RequestMapping(value="/admin/order/addKdInfo")
	@ResponseBody
	public String addKdInfo(HttpServletRequest request , HttpServletResponse response,
			@RequestParam( required = true) String kdNo,
			@RequestParam( required = true) String kdName,
			@RequestParam( required = true) long orderId){
		try{
			orderService.addKdInfo(kdNo, kdName , orderId);
	    	return JsonUtils.commonJsonReturn().toJSONString();
    	}catch(Exception e){
			logger.error("addKdInfo error " ,e);
			return JsonUtils.commonJsonReturn("9999", "系统异常").toJSONString();
		}
	}
	
}
