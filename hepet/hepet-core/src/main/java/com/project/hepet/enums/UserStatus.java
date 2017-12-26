package com.project.hepet.enums;


public enum UserStatus implements BaseEnums {
	WORK("WORK","状态正常"),
	LOCK("LOCK","锁定"),
	INVID( "INVID" , "不可用");
	
	private UserStatus(String code , String desc) {
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
	
	public static UserStatus valueStr(String value){
		for(UserStatus _type : UserStatus.values()){
			if(_type.getCode() .equals( value)){
				return _type;
			}
		}
		return null;
	}
	
}
