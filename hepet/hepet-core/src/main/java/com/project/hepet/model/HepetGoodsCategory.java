package com.project.hepet.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class HepetGoodsCategory  extends BaseObject {
  /** 类目名称 */
  private  String  name;
  /** 类目代码 */
  private  String  code;
  /** 更新时间 */
  private  Date  updateTime;
  /** 描述 */
  private  String  descript;
  /** 0无效1有效 */
  private  Integer  status;
  /** 简称 */
  private  String  shotName;
  /** 类目名称 */
	public String getName(){
		return this.name;
	}
  /** 类目名称 */
	public HepetGoodsCategory setName(String name){
		 this.name=name;
		 return this;
	}
  /** 类目代码 */
	public String getCode(){
		return this.code;
	}
  /** 类目代码 */
	public HepetGoodsCategory setCode(String code){
		 this.code=code;
		 return this;
	}
  /** 更新时间 */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /** 更新时间 */
	public HepetGoodsCategory setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
  /** 描述 */
	public String getDescript(){
		return this.descript;
	}
  /** 描述 */
	public HepetGoodsCategory setDescript(String descript){
		 this.descript=descript;
		 return this;
	}
  /** 0无效1有效 */
	public Integer getStatus(){
		return this.status;
	}
  /** 0无效1有效 */
	public HepetGoodsCategory setStatus(Integer status){
		 this.status=status;
		 return this;
	}
  /** 简称 */
	public String getShotName(){
		return this.shotName;
	}
  /** 简称 */
	public HepetGoodsCategory setShotName(String shotName){
		 this.shotName=shotName;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
