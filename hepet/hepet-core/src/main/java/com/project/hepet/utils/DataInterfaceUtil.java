package com.project.hepet.utils;

import com.alibaba.fastjson.JSONObject;
import com.project.hepet.common.utils.JsonUtils;

public class DataInterfaceUtil {

	public static JSONObject convertJsonResult(JSONObject source , String userName , Boolean permission){
		if(source == null){
			return source;
		}
		JSONObject result = null;
		String sourceCode = source.getString("code");
		if("10000".equals(sourceCode)){
			result = JsonUtils.commonDataInterfaceJsonReturn(userName);
			result.put("body", source.getJSONObject("data"));
		}else{
			result = JsonUtils.commonDataInterfaceJsonReturn(userName, sourceCode, source.getString("message"), permission==null? true : false);
		}
		return result;
	}
}
