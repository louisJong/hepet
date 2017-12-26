package com.project.hepet.common.utils;
import org.apache.commons.lang3.StringUtils;
/**
 * 私密数据 处理 打*
 * @author fidel
 * @since 2015-11-17
 */
public class SecretConvert {
	
	public static String convertUserName(String userName){
		if( StringUtils.isEmpty(userName) ){
			return userName;
		}else{
			return  "*"+userName.substring(1);
		}
	}
	
	public static String convertMobile(String mobile){
		if( StringUtils.isEmpty(mobile) ){
			return mobile;
		}else{
			return mobile.substring(0, 3)+"****"  +mobile.substring(7);
		}
	}
	
	public static String convertCertNo(String certNo){
		if( StringUtils.isEmpty(certNo) ){
			return certNo;
		}else{
			return certNo.substring(0, 6)+"********"  +certNo.substring(14);
		}
	}
	
	public static String convertCardNum(String cardNum){
		if( StringUtils.isEmpty(cardNum) ){
			return cardNum;
		}else{
			return cardNum.substring(0, 6)+"******"  +cardNum.substring(12);
		}
	}
	
	public static void main(String[] args) {
		String cardNum = "31011234568505190520";
		System.out.println( convertCardNum(cardNum) );
		
		String certNo = "362329198601120614";
		System.out.println( convertCertNo(certNo) );
	}
	
}
