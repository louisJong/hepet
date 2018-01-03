package com.project.hepet.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.HttpService;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.common.utils.MD5Util;
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
	
	@Value("${hepet.boccfc.api.url}")
	private String BOCCFC_API_URL;

	private static String MERCHANR_ID = "ZY00000002";
	
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

	@Transactional(propagation = Propagation.REQUIRED)
	public JSONObject order(long goodsId , long num , long addId, String tel , Long customerId, String orderNum) {
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
		order.setOrderNum(orderNum);
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
	public JSONObject getPaySmsCode(String tel, Long customerId, String token , long goodsId , String orderNum) {
		HepetGoods goods = goodsDao.findById(goodsId);
		SortedMap<String, Object> param = new TreeMap<String, Object>();
		param.put("transCode", "TS2001");
		param.put("mobile", tel);
		param.put("timeStamp", new Date().getTime()+"");
		param.put("merchantId", MERCHANR_ID);
		param.put("thirdOrderNo", orderNum);
		param.put("transReqNo", UUID.randomUUID().toString().replace("-", ""));
		param.put("amount", (goods.getPrice().multiply(new BigDecimal(100))).longValue());
		JSONObject sendResult =  doTrans(param, token);
		return JsonUtils.commonJsonReturn(sendResult.getString("code"), sendResult.getString("msg"));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public JSONObject pay(long goodsId , String tel , Long customerId , String dynamicPwd , String desc, String token , Long addId, String orderNum) throws Exception {
		JSONObject orderResult = this.order(goodsId, 1, addId, tel, customerId , orderNum);
		if(!JsonUtils.isSuccessCode(orderResult)){
			return orderResult;
		}
		Date now = new Date();
		long orderId = orderResult.getJSONObject("body").getLongValue("id");
		String tradeId = UUID.randomUUID().toString().replace("-", "");
		HepetOrder updateOrder = new HepetOrder();
		updateOrder.setId(orderId);
		updateOrder.setStatus("NOSEND");
		updateOrder.setUpdateTime(now);
		updateOrder.setOrderNum(orderNum);
		updateOrder.setPayNum(tradeId);
		HepetGoods goods = goodsDao.findById(goodsId);
		SortedMap<String, Object> param = new TreeMap<String, Object>();
		param.put("transCode", "TS2002");
		param.put("mobile", tel);
		param.put("timeStamp", new Date().getTime()+"");
		param.put("merchantId", MERCHANR_ID);
		param.put("thirdOrderNo", orderNum);
		param.put("transReqNo", tradeId);
		param.put("amount", (goods.getPrice().multiply(new BigDecimal(100))).longValue());
		param.put("instalPeriod", goods.getPeriod());
		param.put("dynamicPwd", dynamicPwd);
		param.put("comUseType", "9");
		param.put("payDescription", desc);
		param.put("merchantCode", "C00131990010001");
		JSONObject payResult = doTrans(param, token);
		updateOrder.setPayCode(payResult.getString("code"));
		updateOrder.setPayInfo(payResult.getString("msg"));
		updateOrder.setPayTime(now);
		if(!"0000".equals(payResult.getString("code"))){
			updateOrder.setStatus(null);
		}
		int effectCount = orderDao.update(updateOrder);
		orderResult.getJSONObject("head").put("code", payResult.getString("code"));
		orderResult.getJSONObject("head").put("msg", payResult.getString("msg"));
		return orderResult;
	}
	
	private JSONObject doTrans(SortedMap<String, Object> param , String token){
		JSONObject paramJson = new JSONObject(param);
		paramJson.put("sign", MD5Util.MD5Encode(paramJson + token).toUpperCase());
		String resp = null;
		try {
			resp = new HttpService().doPostRequestEntity(BOCCFC_API_URL, paramJson.toJSONString());
		} catch (IOException e) {
			logger.error("doTrans erroe param:" + param , e );
			return JsonUtils.commonJsonReturn("9999", "系统异常");
		}
		return JSONObject.parseObject(resp);
	}

	@Override
	public JSONObject getAvailAmt(String tel, Long customerId, String token) {
		SortedMap<String, Object> param = new TreeMap<String, Object>();
		param.put("transCode", "TS1001");
		param.put("mobile", tel);
		param.put("timeStamp", new Date().getTime()+"");
		param.put("merchantId", MERCHANR_ID);
		return doTrans(param, token);
	}

}
