package com.project.hepet.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class HepetBrand  extends BaseObject {
  /** 品牌名称 */
  private  String  brandName;
  /** 品牌code */
  private  String  brandCode;
  /** 0无效1有效 */
  private  Integer  status;
  /** 品牌标签 */
  private  String  tags;
  /** logo图片地址 */
  private  String  logoImgUrl;
  /** 品牌图片url */
  private  String  brandImgUrl;
  /**  */
  private  Date  updateTime;
  /** 品牌名称 */
	public String getBrandName(){
		return this.brandName;
	}
  /** 品牌名称 */
	public HepetBrand setBrandName(String brandName){
		 this.brandName=brandName;
		 return this;
	}
  /** 品牌code */
	public String getBrandCode(){
		return this.brandCode;
	}
  /** 品牌code */
	public HepetBrand setBrandCode(String brandCode){
		 this.brandCode=brandCode;
		 return this;
	}
  /** 0无效1有效 */
	public Integer getStatus(){
		return this.status;
	}
  /** 0无效1有效 */
	public HepetBrand setStatus(Integer status){
		 this.status=status;
		 return this;
	}
  /** 品牌标签 */
	public String getTags(){
		return this.tags;
	}
  /** 品牌标签 */
	public HepetBrand setTags(String tags){
		 this.tags=tags;
		 return this;
	}
  /** logo图片地址 */
	public String getLogoImgUrl(){
		return this.logoImgUrl;
	}
  /** logo图片地址 */
	public HepetBrand setLogoImgUrl(String logoImgUrl){
		 this.logoImgUrl=logoImgUrl;
		 return this;
	}
  /** 品牌图片url */
	public String getBrandImgUrl(){
		return this.brandImgUrl;
	}
  /** 品牌图片url */
	public HepetBrand setBrandImgUrl(String brandImgUrl){
		 this.brandImgUrl=brandImgUrl;
		 return this;
	}
  /**  */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /**  */
	public HepetBrand setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
