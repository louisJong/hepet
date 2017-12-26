package com.project.hepet.model ;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
@SuppressWarnings("serial")
public class HepetGoods  extends BaseObject {
  /** 类目代码 */
  private  String  categoryCode;
  /** 品牌code */
  private  String  brandCode;
  /** 品牌名称 */
  private  String  brandName;
  /** 商品名称 */
  private  String  goodsName;
  /** 副标题 */
  private  String  subDesc;
  /** 列表图片Url */
  private  String  listImgUrl;
  /** 详情页图片Url(多个已逗号分隔) */
  private  String  detailImgUrls;
  /** 商品排序 */
  private  Long  proSort;
  /** 价格 */
  private  BigDecimal  price;
  /** 分期数 */
  private  Long  period;
  /** 每期价格 */
  private  BigDecimal  pricePerPeriod;
  /** 是否需要物流1 需要，0 不需要 */
  private  Integer  isLogistics;
  /** 上架人 */
  private  String  putawayOpertor;
  /** 上架时间 */
  private  Date  putawayTime;
  /** 下架人 */
  private  String  soldoutOpertor;
  /** 下架时间 */
  private  Date  soldoutTime;
  /** 下架描述 */
  private  String  soldoutDesc;
  /** 商品状态1上架，0新建2下架 */
  private  Integer  status;
  /** 限购规则 0不限，1限制 */
  private  Integer  restrictType;
  /** 限购数量 */
  private  Long  restrictNum;
  /** 限购描述 */
  private  String  restrictDesc;
  /** 商品说明 */
  private  String  descript;
  /** 库存 */
  private  Long  stock;
  /** 关于取货 */
  private  String  fetchInfo;
  /** 尺寸描述 */
  private  String  sizeSpec;
  /** 供应方 */
  private  String  source;
  /** 商品tag，如包含多tag，用逗号分割 */
  private  String  tags;
  /** 商品详情 */
  private  String  proDetail;
  /**  */
  private  Date  updateTime;
  /** 创建人 */
  private  String  createUser;
  /** 修改人 */
  private  String  updateUser;
  /** 发货方式 */
  private  String  sendType;
  /** 分成百分比 */
  private  Long  profit;
  /** 销量 */
  private  Long  soldCount;
  /** 产地 */
  private  String  region;
  /** 市场价格 */
  private  BigDecimal  marketPrice;
  /** 类目代码 */
	public String getCategoryCode(){
		return this.categoryCode;
	}
  /** 类目代码 */
	public HepetGoods setCategoryCode(String categoryCode){
		 this.categoryCode=categoryCode;
		 return this;
	}
  /** 品牌code */
	public String getBrandCode(){
		return this.brandCode;
	}
  /** 品牌code */
	public HepetGoods setBrandCode(String brandCode){
		 this.brandCode=brandCode;
		 return this;
	}
  /** 品牌名称 */
	public String getBrandName(){
		return this.brandName;
	}
  /** 品牌名称 */
	public HepetGoods setBrandName(String brandName){
		 this.brandName=brandName;
		 return this;
	}
  /** 商品名称 */
	public String getGoodsName(){
		return this.goodsName;
	}
  /** 商品名称 */
	public HepetGoods setGoodsName(String goodsName){
		 this.goodsName=goodsName;
		 return this;
	}
  /** 副标题 */
	public String getSubDesc(){
		return this.subDesc;
	}
  /** 副标题 */
	public HepetGoods setSubDesc(String subDesc){
		 this.subDesc=subDesc;
		 return this;
	}
  /** 列表图片Url */
	public String getListImgUrl(){
		return this.listImgUrl;
	}
  /** 列表图片Url */
	public HepetGoods setListImgUrl(String listImgUrl){
		 this.listImgUrl=listImgUrl;
		 return this;
	}
  /** 详情页图片Url(多个已逗号分隔) */
	public String getDetailImgUrls(){
		return this.detailImgUrls;
	}
  /** 详情页图片Url(多个已逗号分隔) */
	public HepetGoods setDetailImgUrls(String detailImgUrls){
		 this.detailImgUrls=detailImgUrls;
		 return this;
	}
  /** 商品排序 */
	public Long getProSort(){
		return this.proSort;
	}
  /** 商品排序 */
	public HepetGoods setProSort(Long proSort){
		 this.proSort=proSort;
		 return this;
	}
  /** 价格 */
	public BigDecimal getPrice(){
		return this.price;
	}
  /** 价格 */
	public HepetGoods setPrice(BigDecimal price){
		 this.price=price;
		 return this;
	}
  /** 分期数 */
	public Long getPeriod(){
		return this.period;
	}
  /** 分期数 */
	public HepetGoods setPeriod(Long period){
		 this.period=period;
		 return this;
	}
  /** 每期价格 */
	public BigDecimal getPricePerPeriod(){
		return this.pricePerPeriod;
	}
  /** 每期价格 */
	public HepetGoods setPricePerPeriod(BigDecimal pricePerPeriod){
		 this.pricePerPeriod=pricePerPeriod;
		 return this;
	}
  /** 是否需要物流1 需要，0 不需要 */
	public Integer getIsLogistics(){
		return this.isLogistics;
	}
  /** 是否需要物流1 需要，0 不需要 */
	public HepetGoods setIsLogistics(Integer isLogistics){
		 this.isLogistics=isLogistics;
		 return this;
	}
  /** 上架人 */
	public String getPutawayOpertor(){
		return this.putawayOpertor;
	}
  /** 上架人 */
	public HepetGoods setPutawayOpertor(String putawayOpertor){
		 this.putawayOpertor=putawayOpertor;
		 return this;
	}
  /** 上架时间 */
	public Date getPutawayTime(){
		return this.putawayTime;
	}
  /** 上架时间 */
	public HepetGoods setPutawayTime(Date putawayTime){
		 this.putawayTime=putawayTime;
		 return this;
	}
  /** 下架人 */
	public String getSoldoutOpertor(){
		return this.soldoutOpertor;
	}
  /** 下架人 */
	public HepetGoods setSoldoutOpertor(String soldoutOpertor){
		 this.soldoutOpertor=soldoutOpertor;
		 return this;
	}
  /** 下架时间 */
	public Date getSoldoutTime(){
		return this.soldoutTime;
	}
  /** 下架时间 */
	public HepetGoods setSoldoutTime(Date soldoutTime){
		 this.soldoutTime=soldoutTime;
		 return this;
	}
  /** 下架描述 */
	public String getSoldoutDesc(){
		return this.soldoutDesc;
	}
  /** 下架描述 */
	public HepetGoods setSoldoutDesc(String soldoutDesc){
		 this.soldoutDesc=soldoutDesc;
		 return this;
	}
  /** 商品状态1上架，0新建2下架 */
	public Integer getStatus(){
		return this.status;
	}
  /** 商品状态1上架，0新建2下架 */
	public HepetGoods setStatus(Integer status){
		 this.status=status;
		 return this;
	}
  /** 限购规则 0不限，1限制 */
	public Integer getRestrictType(){
		return this.restrictType;
	}
  /** 限购规则 0不限，1限制 */
	public HepetGoods setRestrictType(Integer restrictType){
		 this.restrictType=restrictType;
		 return this;
	}
  /** 限购数量 */
	public Long getRestrictNum(){
		return this.restrictNum;
	}
  /** 限购数量 */
	public HepetGoods setRestrictNum(Long restrictNum){
		 this.restrictNum=restrictNum;
		 return this;
	}
  /** 限购描述 */
	public String getRestrictDesc(){
		return this.restrictDesc;
	}
  /** 限购描述 */
	public HepetGoods setRestrictDesc(String restrictDesc){
		 this.restrictDesc=restrictDesc;
		 return this;
	}
  /** 商品说明 */
	public String getDescript(){
		return this.descript;
	}
  /** 商品说明 */
	public HepetGoods setDescript(String descript){
		 this.descript=descript;
		 return this;
	}
  /** 库存 */
	public Long getStock(){
		return this.stock;
	}
  /** 库存 */
	public HepetGoods setStock(Long stock){
		 this.stock=stock;
		 return this;
	}
  /** 关于取货 */
	public String getFetchInfo(){
		return this.fetchInfo;
	}
  /** 关于取货 */
	public HepetGoods setFetchInfo(String fetchInfo){
		 this.fetchInfo=fetchInfo;
		 return this;
	}
  /** 尺寸描述 */
	public String getSizeSpec(){
		return this.sizeSpec;
	}
  /** 尺寸描述 */
	public HepetGoods setSizeSpec(String sizeSpec){
		 this.sizeSpec=sizeSpec;
		 return this;
	}
  /** 供应方 */
	public String getSource(){
		return this.source;
	}
  /** 供应方 */
	public HepetGoods setSource(String source){
		 this.source=source;
		 return this;
	}
  /** 商品tag，如包含多tag，用逗号分割 */
	public String getTags(){
		return this.tags;
	}
  /** 商品tag，如包含多tag，用逗号分割 */
	public HepetGoods setTags(String tags){
		 this.tags=tags;
		 return this;
	}
  /** 商品详情 */
	public String getProDetail(){
		return this.proDetail;
	}
  /** 商品详情 */
	public HepetGoods setProDetail(String proDetail){
		 this.proDetail=proDetail;
		 return this;
	}
  /**  */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /**  */
	public HepetGoods setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
  /** 创建人 */
	public String getCreateUser(){
		return this.createUser;
	}
  /** 创建人 */
	public HepetGoods setCreateUser(String createUser){
		 this.createUser=createUser;
		 return this;
	}
  /** 修改人 */
	public String getUpdateUser(){
		return this.updateUser;
	}
  /** 修改人 */
	public HepetGoods setUpdateUser(String updateUser){
		 this.updateUser=updateUser;
		 return this;
	}
  /** 发货方式 */
	public String getSendType(){
		return this.sendType;
	}
  /** 发货方式 */
	public HepetGoods setSendType(String sendType){
		 this.sendType=sendType;
		 return this;
	}
  /** 分成百分比 */
	public Long getProfit(){
		return this.profit;
	}
  /** 分成百分比 */
	public HepetGoods setProfit(Long profit){
		 this.profit=profit;
		 return this;
	}
  /** 销量 */
	public Long getSoldCount(){
		return this.soldCount;
	}
  /** 销量 */
	public HepetGoods setSoldCount(Long soldCount){
		 this.soldCount=soldCount;
		 return this;
	}
  /** 产地 */
	public String getRegion(){
		return this.region;
	}
  /** 产地 */
	public HepetGoods setRegion(String region){
		 this.region=region;
		 return this;
	}
  /** 市场价格 */
	public BigDecimal getMarketPrice(){
		return this.marketPrice;
	}
  /** 市场价格 */
	public HepetGoods setMarketPrice(BigDecimal marketPrice){
		 this.marketPrice=marketPrice;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
