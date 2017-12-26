package com.project.hepet.dao ;
import org.springframework.stereotype.Component;

import com.project.hepet.model.HepetBrand;
@Component
public class HepetBrandDao extends GenericDAOImpl<HepetBrand> {
	@Override
	public String getNameSpace() {
		return "hepet_brand";
	}
}
