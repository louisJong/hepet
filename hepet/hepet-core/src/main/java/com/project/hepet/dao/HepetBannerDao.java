package com.project.hepet.dao ;
import java.util.List;

import org.springframework.stereotype.Component;

import com.project.hepet.model.HepetBanner;
@Component
public class HepetBannerDao extends GenericDAOImpl<HepetBanner> {
	@Override
	public String getNameSpace() {
		return "hepet_banner";
	}
	 
	public List<HepetBanner> getSortedBanners(){
		return this.getSqlSession().selectList(this.getNameSpace()+".getSortedBanners");
	}
}
