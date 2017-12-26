package com.project.hepet.enums;

public enum DataInterface  implements BaseEnums{
	bzxrxxqry( "bzxrxxqry", "被执行人"),
	dcdyxxqry( "dcdyxxqry", "动产抵押"),
	fyggxxqry( "fyggxxqry", "法院公告"),
	fypjxxqry( "fypjxxqry", "法院判决"),
	gsxwxxqry( "gsxwxxqry", "公司新闻"),
	gqczxxqry( "gqczxxqry", "股权出质"),
	qygsnbqry( "qygsnbqry", "企业工商年报"),
	qyjyycqry( "qyjyycqry", "企业经营异常"),
	qyktggqry( "qyktggqry", "企业开庭公告"),
	qyqsxxqry( "qyqsxxqry", "企业欠税信息"),
	qyzbxxqry( "qyzbxxqry", "企业招投标信息"),
	qyzlxxqry( "qyzlxxqry", "企业专利信息"),
	qyzzzsqry( "qyzzzsqry", "企业资质证书"),
	qyzpxxqry( "qyzpxxqry", "企业族谱信息"),
	qyjgdmqry( "qyjgdmqry", "企业机构代码"),
	rjzzqqry( "rjzzqqry", "软件著作权"),
	qysbxxqry( "qysbxxqry", "商标信息"),
	sxrxxqry( "sxrxxqry", "失信人信息"),
	sfpmxxqry( "sfpmxxqry", "司法拍卖"),
	qyymxxqry( "qyymxxqry", "域名查询"),
	recruitqry( "recruitqry", "招聘信息"),
	qyzzqxxqry( "qyzzqxxqry", "著作权信息"),
	jbdjxxqry( "jbdjxxqry", "基本登记信息"),
	gdxxqry( "gdxxqry", "股东信息"),
	zyryqry( "zyryqry", "主要人员"),
	fzjgqry( "fzjgqry", "分支机构"),
	gsbgxxqry( "gsbgxxqry", "变更信息"),
	qygszcqry( "qygszcqry", "企业工商注册信息"),
	gsxzcfqry( "gsxzcfqry", "企业工商行政处罚信息"),
	qyfrtzqry( "qyfrtzqry", "企业法人对外投资信息"),
	qydjgqry( "qydjgqry", "企业董监高信息"),
	qygsxqqry( "qygsxqqry", "企业工商详情信息"),
	olddetail( "olddetail", "老工商基本信息接口");

	private DataInterface(String code , String desc) {
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
	
	public static DataInterface valueStr(String value){
		for(DataInterface _type : DataInterface.values()){
			if(_type.getCode() .equals( value)){
				return _type;
			}
		}
		return null;
	}
}
