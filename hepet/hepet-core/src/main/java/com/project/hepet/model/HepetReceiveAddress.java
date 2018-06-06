package com.project.hepet.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class HepetReceiveAddress  extends BaseObject {
  /** 用户id */
  private  String  customerId;
  /** 手机号 */
  private  String  tel;
  /** 省市区域 */
  private  String  area;
  /** 收货地址 */
  private  String  address;
  /**  */
  private  Date  updateTime;
  /** 联系人 */
  private  String  contact;
  /** 联系电话 */
  private  String  phone;
  /** 是否最近使用1是0否 */
  private  Integer  recentUse;
  /** 区域信息 */
  private  String  reginInfo;
  /** 用户id */
	public String getCustomerId(){
		return this.customerId;
	}
  /** 用户id */
	public HepetReceiveAddress setCustomerId(String customerId){
		 this.customerId=customerId;
		 return this;
	}
  /** 手机号 */
	public String getTel(){
		return this.tel;
	}
  /** 手机号 */
	public HepetReceiveAddress setTel(String tel){
		 this.tel=tel;
		 return this;
	}
  /** 省市区域 */
	public String getArea(){
		return this.area;
	}
  /** 省市区域 */
	public HepetReceiveAddress setArea(String area){
		 this.area=area;
		 return this;
	}
  /** 收货地址 */
	public String getAddress(){
		return this.address;
	}
  /** 收货地址 */
	public HepetReceiveAddress setAddress(String address){
		 this.address=address;
		 return this;
	}
  /**  */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /**  */
	public HepetReceiveAddress setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
  /** 联系人 */
	public String getContact(){
		return this.contact;
	}
  /** 联系人 */
	public HepetReceiveAddress setContact(String contact){
		 this.contact=contact;
		 return this;
	}
  /** 联系电话 */
	public String getPhone(){
		return this.phone;
	}
  /** 联系电话 */
	public HepetReceiveAddress setPhone(String phone){
		 this.phone=phone;
		 return this;
	}
  /** 是否最近使用1是0否 */
	public Integer getRecentUse(){
		return this.recentUse;
	}
  /** 是否最近使用1是0否 */
	public HepetReceiveAddress setRecentUse(Integer recentUse){
		 this.recentUse=recentUse;
		 return this;
	}
  /** 区域信息 */
	public String getReginInfo(){
		return this.reginInfo;
	}
  /** 区域信息 */
	public HepetReceiveAddress setReginInfo(String reginInfo){
		 this.reginInfo=reginInfo;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
