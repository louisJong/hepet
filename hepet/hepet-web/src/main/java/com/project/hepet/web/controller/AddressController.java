package com.project.hepet.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.CommonUtils;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.common.utils.LoginDesc;
import com.project.hepet.model.HepetReceiveAddress;
import com.project.hepet.service.AddressService;
import com.project.hepet.web.utils.WebUtil;

@Controller
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@LoginDesc
	@RequestMapping("/hepet/addRecAddr")
	@ResponseBody
	String addAddress(HttpServletRequest request , 
			@RequestParam(value="area" , required=true) String area ,
			@RequestParam(value="address" , required=true) String address ,
			@RequestParam(value="contact" , required=true) String contact ,
			@RequestParam(value="phone" , required=true) String phone,
			@RequestParam(value="provinceId" , required=true) String provinceId,
			@RequestParam(value="cityId" , required=true) String cityId,
			@RequestParam(value="areaId" , required=true) String areaId
			){
		HepetReceiveAddress receiveAddress = new HepetReceiveAddress();
		receiveAddress.setAddress(address);
		receiveAddress.setArea(area);
		receiveAddress.setContact(contact);
		receiveAddress.setCreateTime(new Date());
		receiveAddress.setCustomerId(WebUtil.getCustomerId(request));
		receiveAddress.setPhone(phone);
		JSONObject reginInfo = new JSONObject();
		reginInfo.put("provinceId", provinceId);
		reginInfo.put("cityId", cityId);
		reginInfo.put("areaId", areaId);
		receiveAddress.setReginInfo(reginInfo.toJSONString());
		receiveAddress.setTel(WebUtil.getTel(request));
		receiveAddress.setRecentUse(0);
		return addressService.addAddress(receiveAddress).toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/addressList")
	@ResponseBody
	String addressList(HttpServletRequest request){
		JSONObject result = JsonUtils.commonJsonReturn();
		JsonUtils.setBody(result, "addresses", addressService.addressList(WebUtil.getTel(request), WebUtil.getCustomerId(request)));
		return result.toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/address/delete")
	@ResponseBody
	String addressDelete(HttpServletRequest request , @RequestParam long id){
		JSONObject result = JsonUtils.commonJsonReturn();
		addressService.addressDelete(id , WebUtil.getTel(request), WebUtil.getCustomerId(request));
		return result.toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/editRecAddr")
	@ResponseBody
	String editRecAddr(HttpServletRequest request ,
			@RequestParam(value="id" , required=false) long id ,
			@RequestParam(value="area" , required=false) String area ,
			@RequestParam(value="address" , required=false) String address ,
			@RequestParam(value="contact" , required=false) String contact ,
			@RequestParam(value="phone" , required=false) String phone,
			@RequestParam(value="provinceId" , required=false) String provinceId,
			@RequestParam(value="cityId" , required=false) String cityId,
			@RequestParam(value="areaId" , required=false) String areaId){
		if(CommonUtils.isAllParamsEmpty(area , address , contact , phone)){
			return JsonUtils.commonJsonReturn().toJSONString();
		}
		JSONObject result = JsonUtils.commonJsonReturn();
		HepetReceiveAddress receiveAddress = new HepetReceiveAddress();
		receiveAddress.setId(id);
		receiveAddress.setAddress(address);
		receiveAddress.setCustomerId(WebUtil.getCustomerId(request));
		receiveAddress.setTel(WebUtil.getTel(request));
		receiveAddress.setArea(area);
		receiveAddress.setContact(contact);
		receiveAddress.setPhone(phone);
		if(StringUtils.isNotBlank(provinceId)){
			JSONObject reginInfo = new JSONObject();
			reginInfo.put("provinceId", provinceId);
			reginInfo.put("cityId", cityId);
			reginInfo.put("areaId", areaId);
			receiveAddress.setReginInfo(reginInfo.toJSONString());
		}
		addressService.editAddress(receiveAddress);
		return result.toJSONString();
	}
	
	@LoginDesc
	@RequestMapping("/hepet/addAddr")
	String addAddr(ModelMap modelMap ,HttpServletRequest request,
			@RequestParam(value="from" , required=false) String from ,
			@RequestParam(value="curUrl" , required=false) String curUrl,
			@RequestParam(value="id" , required=false) Long id
			){
		modelMap.put("from", from);
		modelMap.put("curUrl", curUrl);
		if(id!=null){
			modelMap.put("id", id);
			modelMap.put("address", addressService.getAddress(WebUtil.getTel(request), WebUtil.getCustomerId(request), id));
		}
		return "add_address";
	}
	
	@LoginDesc
	@RequestMapping("/hepet/addresses")
	String addresses(ModelMap modelMap ,
			@RequestParam(value="from" , required=false) String from ,
			@RequestParam(value="curUrl" , required=false) String curUrl
			){
		modelMap.put("from", from);
		modelMap.put("curUrl", curUrl);
		return "address";
	}
}
