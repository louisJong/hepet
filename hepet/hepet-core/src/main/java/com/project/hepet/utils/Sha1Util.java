package com.project.hepet.utils;

import java.security.MessageDigest;

public class Sha1Util {
	
	
	
	private static String byteArrayToHexString(byte b[]) {  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++)  
            resultSb.append(byteToHexString(b[i]));  
  
        return resultSb.toString();  
    }  
  
    private static String byteToHexString(byte b) {  
        int n = b;  
        if (n < 0)  
            n += 256;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }  
  
    public static String Sha1Encode(String origin, String charsetname) {  
        String resultString = null;  
        try {  
            resultString = new String(origin);  
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");  
            if (charsetname == null || "".equals(charsetname))  
                resultString = byteArrayToHexString(sha1.digest(resultString  
                        .getBytes()));  
            else  
                resultString = byteArrayToHexString(sha1.digest(resultString  
                        .getBytes(charsetname)));  
        } catch (Exception exception) {  
        }  
        return resultString;  
    }  
  
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };  
    
    
    public static void main(String[] args){
    	String data = "1";
        System.out.println(data);
        String digest = Sha1Util.Sha1Encode(data, "UTF-8".toLowerCase());
        System.out.println(digest);
    }
}

