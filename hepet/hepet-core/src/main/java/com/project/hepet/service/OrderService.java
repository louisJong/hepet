package com.project.hepet.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	List<HepetOrder> orderList(Map<String , Object> params);
	
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
	long orderCount(String status, String tel, Long customerId);
	
	/**
	 * 订单详情
	 * @param orderId
	 * @param customerId 
	 * @return
	 */
	HepetOrder orderDetail(long orderId, Long customerId);
	
	void addKdInfo(String kdNo ,String kdName, long orderId);
	
//	JSONObject getPaySmsCode(String tel , Long customerId, String token, long goodsId, String orderNum) throws Exception;
	
	JSONObject getAvailAmt(String tel , Long customerId) throws Exception;
	
	JSONObject pay(long goodsId , String tel , Long customerId , String desc,Long addId, String orderNum , String tradeId) throws Exception;

	JSONObject pay(long orderId, String tel, Long customerId, String tradeId) throws Exception;

//	void confirmAgain(final Long orderId,final String orderNum , final String tradeId, final String tel , final int times);

	JSONObject queryKdInfo(long orderId , long customerId) throws IOException;
	
	void confirmOrder(long orderId , long customerId);
	
	void cancelOrder(long orderId , long customerId);

	long allOrderCount(Map<String, Object> params);
	
	double allOrderSum(Map<String, Object> params);

	void closeOrder();

	void orderFinish();

	HepetOrder queryByPayNum(String payNum);

	void notifyOrder(Map<String,String> params);

}
