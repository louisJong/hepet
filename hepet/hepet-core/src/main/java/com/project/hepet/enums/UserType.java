package com.project.hepet.enums;


public enum UserType implements BaseEnums {
	EMP_MAIN("MAIN","企业主账户"),
	EMP_SUB("SUB","企业子账户"),
	INTER_USER("INTER","接口用户"),
	ADMIN( "ADMIN" , "超级管理员");
	
	private UserType(String code , String desc) {
		this.code = code;
		this.desc = desc;
	}
	private String code;
	private String desc;
	@Override
	public String getCode() {
		return this.code;
	}
	@Override
	public String getDesc() {
		return this.desc;
	}
	
	public static UserType valueStr(String value){
		for(UserType _type : UserType.values()){
			if(_type.getCode() .equals( value)){
				return _type;
			}
		}
		return null;
	}
	
}
