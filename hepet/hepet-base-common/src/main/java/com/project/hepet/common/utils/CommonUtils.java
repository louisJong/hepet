package com.project.hepet.common.utils;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CommonUtils {
	
	private static Logger logger = Logger.getLogger(CommonUtils.class);
	/** yyyy-MM-dd 默认日期格式 **/
	public static final String dateFormat1="yyyy-MM-dd";
	/** yyyy-MM-dd HH:mm:ss默认日期格式 **/
	public static final String dateFormat2="yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认数字格式 "###,##0.00"
	 */
	public static final  DecimalFormat delformat = new DecimalFormat("###,##0.00");
	
	/**
	 * 线程池
	 * */
	public static final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
	
	/**
	 * 手机卡格式校验
	 * @param cardBind
	 * @return
	 * Boolean
	 */
	public static Boolean checkForMobile(String mobile){
		if(StringUtils.isEmpty(mobile)){
			return false;
		}
		Pattern pMobile = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9])|(14[0-9]))\\d{8}$");  
		Matcher matMobile = pMobile.matcher(mobile);
		if(!matMobile.matches()){
			boolean isValidHkMobile = isValidHKMobile(mobile);
			return false || isValidHkMobile;
		}else {
			return true;
		}
	}

	private static boolean isValidHKMobile(String mobile){
		if(logger.isDebugEnabled()) logger.debug("判断是否满足香港手机号码 mobile="+mobile+",开始");
		if(!StringUtils.isNumeric(mobile)){
			if(logger.isDebugEnabled())  logger.debug("香港手机号码需要都是数字 mobile="+mobile+",开始");
			return false;
		}
		//判断香港
		if(mobile.startsWith("852")){
			if(mobile.length()!=11){
				if(logger.isDebugEnabled())  logger.debug("香港手机号码 需满足 11位长度(含区号852) mobile="+mobile);
				return false;
			}
		}else{
			if(logger.isDebugEnabled())  logger.debug("香港手机号码 需满足 852开头(区号) mobile="+mobile);
			return false;
		}
		return true;
	}
	
	public static Boolean isNecessaryParamsEmpty(String... params){
		if(params == null || params.length == 0){
			return true;
		}
		for(String param : params){
			if(param == null || param.trim().length() == 0){
				return true;
			}
		}
		return false;
	}
	
	public static Boolean isAllParamsEmpty(Object... params){
		if(params == null || params.length == 0){
			return true;
		}
		for(Object param : params){
			if(param != null && param.toString().trim().length()> 0){
				return false;
			}
		}
		return true;
	}
	
	public static Date get235959(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * num为负--N天前的日期;num为正--N天后的日期，0:00:00
	 * 
	 * @param num
	 * @return
	 */
	public static Date getDate00000(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, num);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	 /**
		 * 返回两个时间戳之间相差多少天
		 * @param time1
		 * @param time2
		 * @return time2-time1 所相差的天数
		 */
	    public static int calculateDiffDays(long time1, long time2) {
			SimpleDateFormat timeF1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat timeF2 = new SimpleDateFormat("yyyy-MM-dd");
			int diffDays = 0;
			try {
				Date d1 = timeF1.parse(timeF2.format(new Date(time1))+" 00:00:00");
				Date d2 = timeF1.parse(timeF2.format(new Date(time2))+" 00:00:00");
				diffDays = (int)((d2.getTime()-d1.getTime())/86400000L);
			} catch (Exception e) {
				logger.error("time1="+time1+",time2="+time2,e);
			}
			return diffDays;
	    }
	    
	    /**
	  	 * 返回两个时间 之间相差多少天 , 不足一天算一天
	  	 * @param time1
	  	 * @param time2
	  	 * @return time2-time1 所相差的天数
	  	 */
	    public static  Long calDiffDays(Date d1,Date d2) {
			long deta =  d1.getTime() -  d2.getTime() ;
			//相隔天数
			Long result = null;
			if(deta%(3600 * 1000 * 24)!=0){
				 result = Math.abs(deta/(3600 * 1000 * 24)) +1;
			}else{
				 result = Math.abs(deta/(3600 * 1000 * 24));
			}
			return result.longValue();
		}
	    
	    
	    /**
	  	 * 返回两个时间 之间相差多少天 
	  	 * @param time1
	  	 * @param time2
	  	 * @return time2-time1 所相差的天数
	  	 */
	    public static  Double calDiffDaysWithDouble(Date d1,Date d2) {
			long deta =  d1.getTime() -  d2.getTime() ;
			//相隔天数
			Double result   = Math.abs(deta/(3600 * 1000 * 24d));
			//System.out.println(CommonUtils.dateToString(d1, "yyyy-MM-dd HH:mm:ss:SSS", null)   +","+  CommonUtils.dateToString(d2, "yyyy-MM-dd HH:mm:ss:SSS", null) +",result="+result  );
			return result;
		}
	    
	    /**
	     * 计算两个时间相隔秒数
	     * @param date1
	     * @param date2
	     * @return
	     */
	   public static int calculateDiffSeconds(Date date1,Date date2){  
	        long timeDelta=(date1.getTime()-date2.getTime())/1000;//单位是秒  
	        int secondsDelta=timeDelta>0?(int)timeDelta:(int)Math.abs(timeDelta);  
	        return secondsDelta;  
	    }
	   
	   /**
	     * 计算两个时间相隔秒数
	     * @param date1
	     * @param date2
	     * @return
	     */
	   public static int calculateDiffSeconds(Long time1,Long time2){  
	        long timeDelta=( time1- time2)/1000;//单位是秒  
	        int secondsDelta=timeDelta>0?(int)timeDelta:(int)Math.abs(timeDelta);  
	        return secondsDelta;  
	    }
	
	   /**
	    * @param date
	    * @param format
	    * @param defVal  yyyy-MM-dd 
	    * @return
	    */
	    public static String dateToString(Date date ,String format,String defVal){
	    	if(date==null){
	    		return defVal!=null ? defVal :"";
	    	}
	    	SimpleDateFormat timeF1 = new SimpleDateFormat(  StringUtils.isNotEmpty(format)? format : dateFormat1 );
	    	return timeF1.format(date);
	    }
	    
	    /**
	     * 
	     * @param date
	     * @param format default is  yyyy-MM-dd
	     * @param defVal 默认返回值 default is ""
	     * @return
	     */
	    public static Date stringToDate(String dateStr ,String format){
	    	SimpleDateFormat timeF1 = new SimpleDateFormat(  StringUtils.isNotEmpty(format)? format : dateFormat1 );
	    	try {
				return timeF1.parse(dateStr);
			} catch (Exception e) {
				logger.error("dateStr="+dateStr+",format="+format,e);
				return null;
			}
	    }
	    /**
	     * @param d
	     * @param format 格式 默认###,##0.00
	     * @param defVal 默认返回值
	     * @return
	     */
	    public static String  numberFormat(Double d, String format,String defVal){
	    	if(d == null){
	    		return defVal;
	    	}
	    	if(StringUtils.isEmpty(format)){
	    		return delformat.format(d);
	    	}else{
	    		DecimalFormat df = new DecimalFormat(format);
	    		return df.format(d);
	    	}
	    }
	    /**
	     * @param d
	     * @param format 格式 默认###,##0.00
	     * @param defVal 默认返回值
	     * @return
	     */
	    public static String  numberFormat(Integer d, String format,String defVal){
	    	if(d == null){
	    		return defVal;
	    	}
	    	if(StringUtils.isEmpty(format)){
	    		return delformat.format(d);
	    	}else{
	    		DecimalFormat df = new DecimalFormat(format);
	    		return df.format(d);
	    	}
	    }
	    /**
	     * @param d
	     * @param format 格式 默认###,##0.00
	     * @param defVal 默认返回值
	     * @return
	     */
	    public static String  numberFormat(Long d, String format,String defVal){
	    	if(d == null){
	    		return defVal;
	    	}
	    	if(StringUtils.isEmpty(format)){
	    		return delformat.format(d);
	    	}else{
	    		DecimalFormat df = new DecimalFormat(format);
	    		return df.format(d);
	    	}
	    }
	    
	    public static boolean isChinese(String str){
	    	if(StringUtils.isEmpty(str)){
	    		return false;
	    	}
	    	char[] ch = str.toCharArray();
	    	for(char c :  ch){
	    		if(!isChinese(c)){
	    			return false;
	    		}
	    	}
	    	return true;
	    }
	    
	    public static String trim(String str){
	    	if(StringUtils.isEmpty(str)){
	    		return null;
	    	}
	    	return str.trim();
	    	
	    }
	    private static boolean isChinese(char c) {
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
	                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
	                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
	            return true;
	        }
	        return false;
	    }
	    
	public static void main(String[] args) {
		System.out.println(isChinese("琌椠s"));
	}
	
}
