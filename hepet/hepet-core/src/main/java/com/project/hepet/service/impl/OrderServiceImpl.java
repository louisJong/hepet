package com.project.hepet.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.exception.BizException;
import com.project.hepet.common.utils.HttpService;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.common.utils.UniqueNoUtils;
import com.project.hepet.dao.HepetGoodsDao;
import com.project.hepet.dao.HepetOrderDao;
import com.project.hepet.dao.HepetReceiveAddressDao;
import com.project.hepet.model.HepetGoods;
import com.project.hepet.model.HepetOrder;
import com.project.hepet.model.HepetReceiveAddress;
import com.project.hepet.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private HepetOrderDao orderDao;
	
	@Autowired
	private HepetGoodsDao goodsDao;
	
	@Autowired
	private HepetReceiveAddressDao addressDao;
	
	private final static String getPaySmsCodeUrl = "http://maoyunhai.6655.la/boccfc/json/getPaySmsCode";
	
	private final static String payUrl = "http://maoyunhai.6655.la/boccfc/json/pay";

	@Override
	public List<HepetOrder> orderList(long pageIndex, long limit, String status) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("startRow", pageIndex*limit);
		param.put("limit", limit);
		param.put("status", status);
		return orderDao.findOrders(param);
	}

	@Override
	public HepetOrder orderDetail(long orderId, Long customerId) {
		return orderDao.findDetail(orderId , customerId);
	}

	@Override
	public JSONObject order(long goodsId , long num , long addId, String tel , Long customerId) {
		Assert.isTrue(num>0);
		HepetGoods goods = goodsDao.findById(goodsId);
		Map<String , Object> AddrParam = new HashMap<String, Object>();
		AddrParam.put("id", addId);
		AddrParam.put("customerId", customerId);
		AddrParam.put("tel", tel);
		HepetReceiveAddress address = addressDao.getByIdTelCustomerId(AddrParam);
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("id", goodsId);
		param.put("num", num);
		if(goods.getStock()!=null){
			int resultCount = goodsDao.deductStock(param);
			if(resultCount == 0){
				return JsonUtils.commonJsonReturn("0002", "库存不足");
			}
		}
		param.put("soldNum", num);
		goodsDao.addSoldCount(param);
		HepetOrder order = new HepetOrder();
		order.setAddress(address.getArea() + " " + address.getAddress());
		order.setBrandCode(goods.getBrandCode());
		order.setBrandName(goods.getBrandName());
		order.setCategoryCode(goods.getCategoryCode());
		order.setContact(address.getContact());
		order.setCustomerId(customerId);
		order.setGoodsId(goodsId);
		order.setGoodsName(goods.getGoodsName());
		order.setNum(num);
		order.setOrderNum(UniqueNoUtils.genOrderNum());
		order.setPeriod(goods.getPeriod());
		order.setPhone(address.getPhone());
		order.setPrice(goods.getPrice());
		order.setProfit(goods.getProfit());
		order.setPricePerPeriod(goods.getPricePerPeriod());
		order.setSubDesc(goods.getSubDesc());
		order.setTel(tel);
		order.setStatus("NOPAY");
		order.setListImgUrl(goods.getListImgUrl());
		order.setCreateTime(new Date());
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "id", orderDao.insert(order));
		return result;
	}

	@Override
	public List<HepetOrder> userOrderList(long pageIndex, long limit,
			String status, String tel, Long customerId) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("startRow", pageIndex*limit);
		param.put("limit", limit);
		param.put("status", status);
		param.put("tel", tel);
		param.put("customerId", customerId);
		return orderDao.findOrders(param);
	}

	@Override
	public void addKdInfo(String kdNo, String kdName) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("kdNo", kdNo);
		param.put("kdName", kdName);
		orderDao.addKdInfo(param);
	}

	@Override
	public long orderCount(String status, String tel, Long customerId) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("status", status);
		param.put("tel", tel);
		param.put("customerId", customerId);
		return orderDao.orderCount(param);
	}

	@Override
	public JSONObject getPaySmsCode(String tel, Long customerId) {
		JSONObject param = new JSONObject();
		param.put("apiPath", "apiGateWay/getPaySmsCode");
		JSONObject requestJson = new JSONObject();
		requestJson.put("customerId", customerId);
		requestJson.put("mobileNo", tel);
		param.put("requestJson", requestJson);
		param.put("appMarket", "apple");
		try {
			String resp = new HttpService().doPostRequestEntity(getPaySmsCodeUrl, param.toJSONString());
			JSONObject result = JsonUtils.commonJsonReturn();
			JsonUtils.setBody(result, "resp", JSON.parse(resp));
			return result;
		} catch (IOException e) {
			logger.error("getPaySmsCode error tel:"+tel+",customerId:"+customerId , e);
			return JsonUtils.commonJsonReturn("9999" , "系统异常");
		}
	}

	@Transactional
	@Override
	public JSONObject pay(long orderId , String tel , Long customerId , String dynamicPwd , String desc) throws Exception {
		HepetOrder order = orderDao.findDetail(orderId , customerId);
		if(order==null ||!"NOPAY".equals(order.getStatus())){
			return JsonUtils.commonJsonReturn("0001" , "参数错误");
		}
		HepetOrder updateOrder = new HepetOrder();
		updateOrder.setId(orderId);
		updateOrder.setStatus("NOSEND");
		updateOrder.setUpdateTime(new Date());
		int effectCount = orderDao.update(updateOrder);
		Assert.isTrue(effectCount!=0, "订单已被支付");
		JSONObject param = new JSONObject();
		param.put("apiPath", "apiGateWay/pay");
		JSONObject requestJson = new JSONObject();
		requestJson.put("customerId", customerId);
		requestJson.put("loanAcctNo", tel);
		requestJson.put("amount", order.getPrice());
		requestJson.put("instalPeriod", order.getPeriod());
		requestJson.put("dynamicPwd", dynamicPwd);
		requestJson.put("payDescription", desc);
		param.put("requestJson", requestJson);
		param.put("appMarket", "apple");
		String resp = new HttpService().doPostRequestEntity(payUrl, param.toJSONString());
		JSONObject result = JSONObject.parseObject(resp);
		if(!"000000".equals(result.getString("returnCode"))){//支付失败
			throw new BizException(result.getString("returnCode"), result.getString("errorMsg"));
		}else{
			result = JsonUtils.commonJsonReturn();
			
		}
		return result;
	}

}
