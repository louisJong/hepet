package com.project.hepet.common.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UniqueNoUtils {
	/**
	 * @return
	 */
	public static String genBaseTracId(){
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Random rd = new Random();
		DecimalFormat df = new DecimalFormat("0000");
		int n = rd.nextInt(1000);
		return ymdhms.format(new Date())+df.format(n);
	}
	
	public static String genOrderNum(){
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Random rd = new Random();
		DecimalFormat df = new DecimalFormat("0000");
		int n = rd.nextInt(1000);
		return ymdhms.format(new Date())+df.format(n);
	}
}
