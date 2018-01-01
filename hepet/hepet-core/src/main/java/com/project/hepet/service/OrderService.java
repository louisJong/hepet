package com.project.hepet.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.model.HepetOrder;

public interface OrderService {
	
	/**
	 * 订单列表
	 * @param pageIndex
	 * @param limit
	 * @param status 状态
	 * @return
	 */
	List<HepetOrder> orderList(long pageIndex , long limit , String status);
	
	/**
	 * 订单列表
	 * @param pageIndex
	 * @param limit
	 * @param status 状态
	 * @return
	 */
	List<HepetOrder> userOrderList(long pageIndex , long limit , String status , String tel , Long customerId);

	/**
	 * 商品数量
	 * @param status
	 * @param tel
	 * @param customerId
	 * @return
	 */
	long orderCount( String status , String tel , Long customerId);
	
	/**
	 * 订单详情
	 * @param orderId
	 * @param customerId 
	 * @return
	 */
	HepetOrder orderDetail(long orderId, Long customerId);
	
	JSONObject order(long goodsId , long num , long addId, String tel , Long customerId);
	
	void addKdInfo(String kdNo ,String kdName);
	
	JSONObject getPaySmsCode(String tel , Long customerId, String token, long orderId);
	
	JSONObject getAvailAmt(String tel , Long customerId , String token);
	
	JSONObject pay(long orderId , String tel , Long customerId , String dynamicPwd , String desc, String token) throws Exception;

}
