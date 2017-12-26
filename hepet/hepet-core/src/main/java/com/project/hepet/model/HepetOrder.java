package com.project.hepet.model ;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
@SuppressWarnings("serial")
public class HepetOrder  extends BaseObject {
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
  /** 分成百分比 */
  private  Long  profit;
  /** 购买个数 */
  private  Long  num;
  /** 价格 */
  private  BigDecimal  price;
  /** 分期数 */
  private  Long  period;
  /** 每期价格 */
  private  BigDecimal  pricePerPeriod;
  /** 收货地址 */
  private  String  address;
  /** 联系人 */
  private  String  contact;
  /** 联系电话 */
  private  String  phone;
  /** 快递单号 */
  private  String  kdNo;
  /** 快递公司 */
  private  String  kdName;
  /** 商品状态NOPAY待付款NOSEND待发货NORECEIVE待收货CLOSED已关闭REFUND已退货SUCCESS完成 */
  private  String  status;
  /**  */
  private  Date  updateTime;
  /** 支付时间 */
  private  Date  payTime;
  /** 用户id */
  private  Long  customerId;
  /** 手机号 */
  private  String  tel;
  /** 订单号 */
  private  String  orderNum;
  /** 交易号 */
  private  String  payNum;
  /** 最近交易码 */
  private  String  payCode;
  /** 最近交易描述 */
  private  String  payInfo;
  /** 商品id */
  private  Long  goodsId;
  /** 列表图片Url */
  private  String  listImgUrl;
  /** 类目代码 */
	public String getCategoryCode(){
		return this.categoryCode;
	}
  /** 类目代码 */
	public HepetOrder setCategoryCode(String categoryCode){
		 this.categoryCode=categoryCode;
		 return this;
	}
  /** 品牌code */
	public String getBrandCode(){
		return this.brandCode;
	}
  /** 品牌code */
	public HepetOrder setBrandCode(String brandCode){
		 this.brandCode=brandCode;
		 return this;
	}
  /** 品牌名称 */
	public String getBrandName(){
		return this.brandName;
	}
  /** 品牌名称 */
	public HepetOrder setBrandName(String brandName){
		 this.brandName=brandName;
		 return this;
	}
  /** 商品名称 */
	public String getGoodsName(){
		return this.goodsName;
	}
  /** 商品名称 */
	public HepetOrder setGoodsName(String goodsName){
		 this.goodsName=goodsName;
		 return this;
	}
  /** 副标题 */
	public String getSubDesc(){
		return this.subDesc;
	}
  /** 副标题 */
	public HepetOrder setSubDesc(String subDesc){
		 this.subDesc=subDesc;
		 return this;
	}
  /** 分成百分比 */
	public Long getProfit(){
		return this.profit;
	}
  /** 分成百分比 */
	public HepetOrder setProfit(Long profit){
		 this.profit=profit;
		 return this;
	}
  /** 购买个数 */
	public Long getNum(){
		return this.num;
	}
  /** 购买个数 */
	public HepetOrder setNum(Long num){
		 this.num=num;
		 return this;
	}
  /** 价格 */
	public BigDecimal getPrice(){
		return this.price;
	}
  /** 价格 */
	public HepetOrder setPrice(BigDecimal price){
		 this.price=price;
		 return this;
	}
  /** 分期数 */
	public Long getPeriod(){
		return this.period;
	}
  /** 分期数 */
	public HepetOrder setPeriod(Long period){
		 this.period=period;
		 return this;
	}
  /** 每期价格 */
	public BigDecimal getPricePerPeriod(){
		return this.pricePerPeriod;
	}
  /** 每期价格 */
	public HepetOrder setPricePerPeriod(BigDecimal pricePerPeriod){
		 this.pricePerPeriod=pricePerPeriod;
		 return this;
	}
  /** 收货地址 */
	public String getAddress(){
		return this.address;
	}
  /** 收货地址 */
	public HepetOrder setAddress(String address){
		 this.address=address;
		 return this;
	}
  /** 联系人 */
	public String getContact(){
		return this.contact;
	}
  /** 联系人 */
	public HepetOrder setContact(String contact){
		 this.contact=contact;
		 return this;
	}
  /** 联系电话 */
	public String getPhone(){
		return this.phone;
	}
  /** 联系电话 */
	public HepetOrder setPhone(String phone){
		 this.phone=phone;
		 return this;
	}
  /** 快递单号 */
	public String getKdNo(){
		return this.kdNo;
	}
  /** 快递单号 */
	public HepetOrder setKdNo(String kdNo){
		 this.kdNo=kdNo;
		 return this;
	}
  /** 快递公司 */
	public String getKdName(){
		return this.kdName;
	}
  /** 快递公司 */
	public HepetOrder setKdName(String kdName){
		 this.kdName=kdName;
		 return this;
	}
  /** 商品状态NOPAY待付款NOSEND待发货NORECEIVE待收货CLOSED已关闭REFUND已退货SUCCESS完成 */
	public String getStatus(){
		return this.status;
	}
  /** 商品状态NOPAY待付款NOSEND待发货NORECEIVE待收货CLOSED已关闭REFUND已退货SUCCESS完成 */
	public HepetOrder setStatus(String status){
		 this.status=status;
		 return this;
	}
  /**  */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /**  */
	public HepetOrder setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
  /** 支付时间 */
	public Date getPayTime(){
		return this.payTime;
	}
  /** 支付时间 */
	public HepetOrder setPayTime(Date payTime){
		 this.payTime=payTime;
		 return this;
	}
  /** 用户id */
	public Long getCustomerId(){
		return this.customerId;
	}
  /** 用户id */
	public HepetOrder setCustomerId(Long customerId){
		 this.customerId=customerId;
		 return this;
	}
  /** 手机号 */
	public String getTel(){
		return this.tel;
	}
  /** 手机号 */
	public HepetOrder setTel(String tel){
		 this.tel=tel;
		 return this;
	}
  /** 订单号 */
	public String getOrderNum(){
		return this.orderNum;
	}
  /** 订单号 */
	public HepetOrder setOrderNum(String orderNum){
		 this.orderNum=orderNum;
		 return this;
	}
  /** 交易号 */
	public String getPayNum(){
		return this.payNum;
	}
  /** 交易号 */
	public HepetOrder setPayNum(String payNum){
		 this.payNum=payNum;
		 return this;
	}
  /** 最近交易码 */
	public String getPayCode(){
		return this.payCode;
	}
  /** 最近交易码 */
	public HepetOrder setPayCode(String payCode){
		 this.payCode=payCode;
		 return this;
	}
  /** 最近交易描述 */
	public String getPayInfo(){
		return this.payInfo;
	}
  /** 最近交易描述 */
	public HepetOrder setPayInfo(String payInfo){
		 this.payInfo=payInfo;
		 return this;
	}
  /** 商品id */
	public Long getGoodsId(){
		return this.goodsId;
	}
  /** 商品id */
	public HepetOrder setGoodsId(Long goodsId){
		 this.goodsId=goodsId;
		 return this;
	}
  /** 列表图片Url */
	public String getListImgUrl(){
		return this.listImgUrl;
	}
  /** 列表图片Url */
	public HepetOrder setListImgUrl(String listImgUrl){
		 this.listImgUrl=listImgUrl;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
