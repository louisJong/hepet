package com.project.hepet.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;


public class EnvUtil {
	//#dev,test, pre pro
	private static Properties props  = new Properties();
	static{
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("hepet-core.properties");
			try {
				props.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}
	/**
	 * 是否是开发环境	
	 * @return
	 */
	public static boolean  isDev(){
		return "dev".equals( getValue("sys.env") 	) ? true :false;
	}
	
	public static boolean  isSit(){
		return "sit".equals( getValue("sys.env") 	) ? true :false;
	}
	
	public static String  getEnv(){
		return getValue("sys.env");
	}
	public static String getValue(String key ,Object ...params){
		String str = props.getProperty(key);
		return  MessageFormat.format( str, params);
	}
	/**
	 * 是否是生产环境	
	 * @return
	 */
	public static boolean  isPro(){
		return "pro".equals(getValue("sys.env")) ? true :false;
	}
	public static void main(String[] args) {
		System.out.println(isDev());
	}
}
