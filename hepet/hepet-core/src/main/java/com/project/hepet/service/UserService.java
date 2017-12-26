package com.project.hepet.service;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.model.HepetAdminUser;

public interface UserService {
	
	public JSONObject createAdminUser(HepetAdminUser user);
	
	public JSONObject login(String userName , String loginPwd);

	public JSONObject initLoginPwd(String userName);
	
}
