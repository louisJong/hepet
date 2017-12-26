package com.project.hepet.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
public class SignUtil {
	
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters,String Key){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v );  
            }  
        }
        sb.append("_" + Key);  
        String sign = Sha1Util.Sha1Encode(sb.toString(), characterEncoding).toLowerCase();  
        return sign; 
	}

}
