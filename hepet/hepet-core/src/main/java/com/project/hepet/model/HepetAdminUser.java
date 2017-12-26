package com.project.hepet.model ;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.Date;
@SuppressWarnings("serial")
public class HepetAdminUser  extends BaseObject {
  /** 用户名 */
  private  String  userName;
  /** 手机 */
  private  String  mobile;
  /** 邮箱地址 */
  private  String  email;
  /** 登陆密码 */
  private  String  loginPwd;
  /** 创建人 */
  private  String  createUser;
  /** 更新时间 */
  private  Date  updateTime;
  /** 状态，1：可用，2：不可用 */
  private  Integer  status;
  /** 用户名 */
	public String getUserName(){
		return this.userName;
	}
  /** 用户名 */
	public HepetAdminUser setUserName(String userName){
		 this.userName=userName;
		 return this;
	}
  /** 手机 */
	public String getMobile(){
		return this.mobile;
	}
  /** 手机 */
	public HepetAdminUser setMobile(String mobile){
		 this.mobile=mobile;
		 return this;
	}
  /** 邮箱地址 */
	public String getEmail(){
		return this.email;
	}
  /** 邮箱地址 */
	public HepetAdminUser setEmail(String email){
		 this.email=email;
		 return this;
	}
  /** 登陆密码 */
	public String getLoginPwd(){
		return this.loginPwd;
	}
  /** 登陆密码 */
	public HepetAdminUser setLoginPwd(String loginPwd){
		 this.loginPwd=loginPwd;
		 return this;
	}
  /** 创建人 */
	public String getCreateUser(){
		return this.createUser;
	}
  /** 创建人 */
	public HepetAdminUser setCreateUser(String createUser){
		 this.createUser=createUser;
		 return this;
	}
  /** 更新时间 */
	public Date getUpdateTime(){
		return this.updateTime;
	}
  /** 更新时间 */
	public HepetAdminUser setUpdateTime(Date updateTime){
		 this.updateTime=updateTime;
		 return this;
	}
  /** 状态，1：可用，2：不可用 */
	public Integer getStatus(){
		return this.status;
	}
  /** 状态，1：可用，2：不可用 */
	public HepetAdminUser setStatus(Integer status){
		 this.status=status;
		 return this;
	}
	public String toString(){
	    return JSON.toJSONString(this,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue);
	}
}
