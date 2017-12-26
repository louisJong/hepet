package com.project.hepet.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.model.HepetReceiveAddress;

public interface AddressService {

	JSONObject addAddress(HepetReceiveAddress address);
	
	JSONObject editAddress(HepetReceiveAddress address);
	
	List<HepetReceiveAddress> addressList(String tel , Long customerId);
	
	HepetReceiveAddress getRecentUse(String tel , Long customerId);
	
	HepetReceiveAddress getAddress(String tel , Long customerId , long id);

	int haseMore(String tel, Long customerId);

	int setRecentUse(Long addid, String tel, Long customerId);

	int setUnRecentUse(String tel, Long customerId);
}
