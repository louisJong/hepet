package com.project.hepet.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class HepetBanner  extends BaseObject {
  /** banner描述 */
  private  String  bannerDesc;
  /** banner 图片地址 */
  private  String  imgUrl;
  /** banner链接url */
  private  String  bannerLink;
  /**  */
  private  Long  bannerSort;
  /**1启用0禁用 */
  private  Integer  status;
  /**  */
  private  String  createUser;
  /**  */
  private  Date  updateTime;
  /**  */
  private  String  updateUser;
  /** banner描述 */
	public String getBannerDesc(){
		return this.bannerDesc;
	}
  /** banner描述 */
	public HepetBanner setBannerDesc(String bannerDesc){
		 this.bannerDesc=bannerDesc;
		 return this;
	}
  /** banner 图片地址 */
	public String getImgUrl(){
		return this.imgUrl;
	}
  /** banner 图片地址 */
	public HepetBanner setImgUrl(String imgUrl){
		 this.imgUrl=imgUrl;
		 return this;
	}
  /** banner链接url */
	public String getBannerLink(){
		return this.bannerLink;
	}
  /** banner链接url */
	public HepetBanner setBannerLink(String bannerLink){
		 this.bannerLink=bannerLink;
		 return this;
	}
  /**  */
	public Long getBannerSort(){
		return this.bannerSort;
	}
  /**  */
	public HepetBanner setBannerSort(Long bannerSort){
		 this.bannerSort=bannerSort;
		 return this;
	}
  /** 0:启用，1：禁用 */
	public Integer getStatus(){
		return this.status;
	}
  /** 0:启用，1：禁用 */
	public HepetBanner setStatus(Integer status){
		 this.status=status;
		 return this;
	}
  /**  */
	public String getCreateUser(){
		return this.createUser;
	}
  /**  */
	public HepetBanner setCreateUser(String createUser){
		 this.createUser=createUser;
		 return this;
	}
  /**  */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /**  */
	public HepetBanner setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
  /**  */
	public String getUpdateUser(){
		return this.updateUser;
	}
  /**  */
	public HepetBanner setUpdateUser(String updateUser){
		 this.updateUser=updateUser;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
