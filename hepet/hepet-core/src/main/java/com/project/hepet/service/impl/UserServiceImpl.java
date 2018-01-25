package com.project.hepet.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.common.utils.MD5Util;
import com.project.hepet.dao.HepetAdminUserDao;
import com.project.hepet.model.HepetAdminUser;
import com.project.hepet.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger  = Logger.getLogger(UserServiceImpl.class);
	
	String pwd_mask = "HePET1212";
	
	@Autowired
	private HepetAdminUserDao userDao;
	
	@Override
	public JSONObject createAdminUser(HepetAdminUser user) {
		try{
			user.setLoginPwd(getEncreptPwd(user.getLoginPwd()));
			userDao.insert(user);
		}catch(DuplicateKeyException e){
			logger.error("userCreate error user = "+ JsonUtils.modelToJsonString(user) , e);
			return JsonUtils.commonJsonReturn("0002" , "用户已存在");
		}
		return JsonUtils.commonJsonReturn();
	}
	
	private String getEncreptPwd(String pwd){
		return MD5Util.MD5Encode(pwd_mask+pwd, "utf-8");
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Util.MD5Encode("HePET1212"+"hepet2017", "utf-8"));
	}

	@Override
	public JSONObject login(String userName, String loginPwd) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", userName);
		param.put("loginPwd", getEncreptPwd(loginPwd));
		HepetAdminUser user = userDao.findByUserNameAndPwd(param);
		if(user==null){
			return JsonUtils.commonJsonReturn("0002" , "用户名或密码错误");
		}
		if(user.getStatus()==0){
			return JsonUtils.commonJsonReturn("0003" , "账户不可用");
		}
		JSONObject result = JsonUtils.commonJsonReturn();
		
		return JsonUtils.setBody(result, "userId", user.getId());
	}

	@Override
	public JSONObject initLoginPwd(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

}
