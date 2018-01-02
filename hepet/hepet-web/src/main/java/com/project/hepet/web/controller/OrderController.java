package com.project.hepet.web.controller;

import javax.servlet.http.HttpServletRequest;
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
import com.project.hepet.common.utils.UniqueNoUtils;
import com.project.hepet.model.HepetGoods;
import com.project.hepet.model.HepetReceiveAddress;
import com.project.hepet.service.AddressService;
import com.project.hepet.service.GoodsService;
import com.project.hepet.service.OrderService;
import com.project.hepet.web.utils.WebUtil;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private GoodsService goodsService;
	
	
	@LoginDesc
	@RequestMapping("/hepet/userOrderList")
	@ResponseBody
	String userOrderList(HttpServletRequest request ,
			@RequestParam(value="pageIndex" , required=true) long pageIndex ,
			@RequestParam(value="limit" , required=true) long limit ,
			@RequestParam(value="status" , required=false) String status){
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "orderList", orderService.userOrderList(pageIndex, limit, status, WebUtil.getTel(request), WebUtil.getCustomerId(request)));
		JsonUtils.setBody(result, "count", orderService.orderCount(status, WebUtil.getTel(request), WebUtil.getCustomerId(request)));
		return result.toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/orderDetail")
	@ResponseBody
	String orderDetail(HttpServletRequest request ,
			@RequestParam(value="orderId" , required=true) long orderId ){
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "orderDetail", orderService.orderDetail(orderId , WebUtil.getCustomerId(request)));
		return result.toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/orderConfirm")
	String orderConfirm(ModelMap modelMap , HttpServletRequest request ,
			@RequestParam(value="goodsId" , required=true) long goodsId , 
			@RequestParam(value="num" , required=true) long num , 
			@RequestParam(value="addid" , required=false) Long addid
			){
		JSONObject result = JsonUtils.commonJsonReturn();
		HepetReceiveAddress address = null;
		if(addid!=null){
			address = addressService.getAddress(WebUtil.getTel(request), WebUtil.getCustomerId(request), addid);
		}
		if(address == null){
			address = addressService.getRecentUse(WebUtil.getTel(request), WebUtil.getCustomerId(request));
		}
		result = goodsService.goodsDetail(goodsId, false);
		HepetGoods goods = (HepetGoods) JsonUtils.getBodyValue(result, "info");
		JSONObject amtJson = orderService.getAvailAmt(WebUtil.getTel(request), WebUtil.getCustomerId(request), WebUtil.getToken(request));
		modelMap.put("address", address);
		modelMap.put("goodsId", goodsId);
		modelMap.put("availAmt", amtJson.get("availAmt"));
		modelMap.put("goods", goods);
		modelMap.put("hasMore", addressService.haseMore(WebUtil.getTel(request), WebUtil.getCustomerId(request)));
		return "order";
	}
	
//	@LoginDesc
//	@RequestMapping("/hepet/order/submit")
//	@ResponseBody
//	String submit(HttpServletRequest request ,
//			@RequestParam(value="goodsId" , required=true) long goodsId , 
//			@RequestParam(value="num" , required=false) Long num , 
//			@RequestParam(value="addId" , required=false) Long addId){
//		num = 1l;//FIXME 一期写死一个
//		addressService.setUnRecentUse(WebUtil.getTel(request), WebUtil.getCustomerId(request));
//		addressService.setRecentUse(addId , WebUtil.getTel(request), WebUtil.getCustomerId(request));
//		return orderService.order(goodsId , num , addId,WebUtil.getTel(request), WebUtil.getCustomerId(request)).toJSONString();
//	}
	
	@LoginDesc
	@RequestMapping("/hepet/order/getPaySmsCode")
	@ResponseBody
	String getPaySmsCode(HttpServletRequest request , HttpSession session , @RequestParam(required=true) long goodsId ){
		String orderNum = UniqueNoUtils.genOrderNum();
		session.setAttribute("orderNum", orderNum);
		return orderService.getPaySmsCode(WebUtil.getTel(request), WebUtil.getCustomerId(request) , WebUtil.getToken(request) , goodsId , orderNum).toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/order/pay")
	@ResponseBody
	String pay(HttpServletRequest request , @RequestParam(required = true) long goodsId ,
			@RequestParam(required = true) String dynamicPwd ,
			Long addId , 
			String desc ) throws Exception{
		return orderService.pay(goodsId ,  WebUtil.getTel(request), WebUtil.getCustomerId(request), dynamicPwd, desc , WebUtil.getToken(request) , addId , WebUtil.getOrderNum(request)).toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/myOrders")
	String myOrders(){
		return "orderlist";
	}
	
	@LoginDesc
	@RequestMapping("/hepet/order/result")
	String orderConfirm(HttpServletRequest request , ModelMap modelMap , long orderId){
		modelMap.put("orderInfo", orderService.orderDetail(orderId, WebUtil.getCustomerId(request)));
		return "order_result";
	}
	
}
