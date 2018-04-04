package com.project.hepet.web.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.CommonUtils;
import com.project.hepet.common.utils.GateApiUtils;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.common.utils.LoginDesc;
import com.project.hepet.common.utils.PayConfig;
import com.project.hepet.common.utils.UniqueNoUtils;
import com.project.hepet.model.HepetGoods;
import com.project.hepet.model.HepetOrder;
import com.project.hepet.model.HepetReceiveAddress;
import com.project.hepet.service.AddressService;
import com.project.hepet.service.GoodsService;
import com.project.hepet.service.OrderService;
import com.project.hepet.web.utils.WebUtil;

@Controller
public class OrderController {

	private static final Logger logger = Logger.getLogger(OrderController.class);
	
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
		if(address!=null && StringUtils.isNoneBlank(address.getAddress())){
			address.setAddress(CommonUtils.replaceBlank(address.getAddress()));
		}
		result = goodsService.goodsDetail(goodsId, false);
		HepetGoods goods = (HepetGoods) JsonUtils.getBodyValue(result, "info");
		JSONObject amtJson;
		try {
			amtJson = orderService.getAvailAmt(WebUtil.getTel(request), WebUtil.getCustomerId(request));
		} catch (Exception e) {
			amtJson = new JSONObject();
		}
		Long availAmt = amtJson.getJSONObject("body").getLong("availAmt");
		modelMap.put("address", address);
		modelMap.put("goodsId", goodsId);
		modelMap.put("availAmt", availAmt == null? 0 : (BigDecimal.valueOf(availAmt).divide(new BigDecimal(100))));
		modelMap.put("goods", goods);
		modelMap.put("tel", CommonUtils.getTuoMinTel(WebUtil.getTel(request)));
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
	
//	@LoginDesc
//	@RequestMapping("/hepet/order/getPaySmsCode")
//	@ResponseBody
//	String getPaySmsCode(HttpServletRequest request , HttpSession session , @RequestParam(required=true) long goodsId ){
//		String orderNum = UniqueNoUtils.genOrderNum();
//		session.setAttribute("orderNum", orderNum);
//		try {
//			return orderService.getPaySmsCode(WebUtil.getTel(request), WebUtil.getCustomerId(request) , WebUtil.getToken(request) , goodsId , orderNum).toJSONString();
//		} catch (HttpException e) {
//			return JsonUtils.commonJsonReturn("9999", "支付异常").toJSONString();
//		}
//	}
	
	@LoginDesc
	@RequestMapping("/hepet/order/pay")
	@ResponseBody
	String pay(HttpServletRequest request , @RequestParam(required = true) long goodsId ,
			Long addId , 
			String desc ) throws Exception{
		final String tradeId = UUID.randomUUID().toString().replace("-", "");
		final String orderNum =  UniqueNoUtils.genOrderNum();
		final String tel = WebUtil.getTel(request);
		try{
			JSONObject orderResult = orderService.pay(goodsId , tel, WebUtil.getCustomerId(request), desc , addId , orderNum , tradeId);
			return orderResult.toJSONString();
		}catch(Exception e){
			String msg = "支付异常，请重试";
//			if(e instanceof HttpException){
//				orderService.confirmAgain(null , orderNum , tradeId , tel , 1);
//				msg = "交易处理中,请稍后查看订单状态";
//			}
			return JsonUtils.commonJsonReturn("9999", msg).toJSONString();
		}
		
	}
	
	@RequestMapping("/mall/pay/notifyUrl")
	@ResponseBody
	String notify(HttpServletRequest request ,  @RequestParam String outOrderNo ,
			@RequestParam String tradeStatus ,
			@RequestParam String signature) throws Exception{
		Map<String,String> params = WebUtil.getRequestParamsMap(request);
		if(!GateApiUtils.signVerify(params, signature, PayConfig.serverPublicKey)){
			return "success";
		}
		orderService.notifyOrder(params);
		return "success";
	}
	
	@RequestMapping("/mall/pay/retUrl")
	String retUrl(HttpServletRequest request , ModelMap modelMap ,
			@RequestParam String outOrderNo) throws Exception{
		modelMap.put("outOrderNo", outOrderNo);
		return "status";
	}
	
	@RequestMapping("/mall/pay/result")
	@ResponseBody
	String result(HttpServletRequest request ,  @RequestParam String outOrderNo) throws Exception{
		JSONObject result = JsonUtils.commonJsonReturn();
		long s , e;
		s = e = System.currentTimeMillis();
		int times=0;
		HepetOrder order = null;
		while((e-s)<10*1000){//最多等待10秒
			times++;
			Thread.currentThread();
			Thread.sleep(times*100);
			order = orderService.queryByPayNum(outOrderNo);
			e = System.currentTimeMillis();
			if(!"NOPAY".equals(order.getStatus()))//订单已处理完成
			{
				result.getJSONObject("body").put("result", "payed");
				result.getJSONObject("body").put("id", order.getId());
				return result.toJSONString();
			}
		}
		result.getJSONObject("body").put("result", "paying");
		return result.toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/order/payAgain")
	@ResponseBody
	String payAgain(HttpServletRequest request , @RequestParam(required = true) final long orderId) throws Exception{
		final String tel = WebUtil.getTel(request);
		try{
			JSONObject orderResult = orderService.pay(orderId ,  tel, WebUtil.getCustomerId(request));
			return orderResult.toJSONString();
		}catch(Exception e){
			return JsonUtils.commonJsonReturn("9999", "支付异常，请重试").toJSONString();
		}
	}
	
	@LoginDesc
	@RequestMapping("/hepet/order/kd_query")
	String kd_query(ModelMap model , HttpServletRequest request , @RequestParam(required = true) long orderId){
		JSONArray list = new JSONArray();
		try {
			JSONObject result = orderService.queryKdInfo(orderId ,  WebUtil.getCustomerId(request));
			model.put("kdNo", result.getJSONObject("body").getString("kdNo"));
			model.put("kdName", result.getJSONObject("body").getString("kdName"));
			if(JsonUtils.isSuccessCode(result) && result.getJSONObject("body").getIntValue("status") == 0){
				list = result.getJSONObject("body").getJSONObject("kdInfo").getJSONObject("result").getJSONArray("list");
			}
		}catch(Exception e) {
			logger.error("kd_query error orderId:"+orderId, e);
		}
		model.put("list", list);
		return "express_info";
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
		modelMap.put("tel", CommonUtils.getTuoMinTel(WebUtil.getTel(request)));
		return "order_result";
	}
	
	@LoginDesc
	@ResponseBody
	@RequestMapping(value="/hepet/order/finish" , method = RequestMethod.POST)
	String orderFinish(HttpServletRequest request , long orderId){
		orderService.confirmOrder(orderId, WebUtil.getCustomerId(request));
		return JsonUtils.commonJsonReturn().toJSONString();
	}
	
	@LoginDesc
	@ResponseBody
	@RequestMapping(value="/hepet/order/cancel" , method = RequestMethod.POST)
	String orderCancel(HttpServletRequest request , long orderId){
		orderService.cancelOrder(orderId, WebUtil.getCustomerId(request));
		return JsonUtils.commonJsonReturn().toJSONString();
	}
	
}
