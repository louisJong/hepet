package com.project.hepet.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.model.HepetReceiveAddress;

public interface AddressService {

	JSONObject addAddress(HepetReceiveAddress address);
	
	JSONObject editAddress(HepetReceiveAddress address);
	
	List<HepetReceiveAddress> addressList(String tel , String customerId);
	
	HepetReceiveAddress getRecentUse(String tel , String customerId);
	
	HepetReceiveAddress getAddress(String tel , String customerId , long id);

	int haseMore(String tel, String customerId);

	int setRecentUse(Long addid, String tel, String customerId);

	int setUnRecentUse(String tel, String customerId);

	void addressDelete(long id, String tel, String customerId);
}
