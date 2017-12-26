package com.project.hepet.dao ;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.project.hepet.model.HepetAdminUser;
@Component
public class HepetAdminUserDao extends GenericDAOImpl<HepetAdminUser> {
	@Override
	public String getNameSpace() {
		return "hepet_admin_user";
	}

	public HepetAdminUser findByUserNameAndPwd(Map<String, String> param) {
		return this.getSqlSession().selectOne(this.getNameSpace()+".findByUserNameAndPwd" , param);
	}
}
