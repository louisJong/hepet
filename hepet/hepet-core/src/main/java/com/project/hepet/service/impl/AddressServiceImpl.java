package com.project.hepet.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.dao.HepetReceiveAddressDao;
import com.project.hepet.model.HepetReceiveAddress;
import com.project.hepet.service.AddressService;

@Service("addressServiceImpl")
public class AddressServiceImpl implements AddressService {

	@Autowired
	private HepetReceiveAddressDao addressDao;
	
	@Override
	public JSONObject addAddress(HepetReceiveAddress address) {
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "id", addressDao.insert(address));
		return result;
	}

	@Override
	public JSONObject editAddress(HepetReceiveAddress address) {
		address.setUpdateTime(new Date());
		addressDao.update(address);
		return JsonUtils.commonJsonReturn();
	}

	@Override
	public List<HepetReceiveAddress> addressList(String tel, String customerId) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("tel", tel);
		param.put("customerId", customerId);
		return addressDao.findByTelAndCustomerId(param);
	}

	@Override
	public HepetReceiveAddress getRecentUse(String tel, String customerId) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("tel", tel);
		param.put("customerId", customerId);
		return addressDao.getRecentUse(param);
	}

	@Override
	public HepetReceiveAddress getAddress(String tel, String customerId, long id) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("customerId", customerId);
		param.put("tel", tel);
		return addressDao.getByIdTelCustomerId(param);
	}

	@Override
	public int haseMore(String tel, String customerId) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("customerId", customerId);
		param.put("tel", tel);
		long count = addressDao.queryCount(param);
		return count>1? 1 : 0;
	}

	@Override
	public int setRecentUse(Long addid, String tel, String customerId) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("customerId", customerId);
		param.put("tel", tel);
		param.put("addid", addid);
		return addressDao.setRecentUse(param);
	}
	
	@Override
	public int setUnRecentUse(String tel, String customerId) {
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("customerId", customerId);
		param.put("tel", tel);
		return addressDao.setUnRecentUse(param);
	}

	@Override
	public void addressDelete(long id, String tel, String customerId) {
		Assert.isTrue(id>0 && StringUtils.isNoneBlank(tel) && customerId!=null);
		Map<String , Object> param = new HashMap<String, Object>();
		param.put("customerId", customerId);
		param.put("tel", tel);
		param.put("id", id);
		addressDao.addressDelete(param);
	}
}
