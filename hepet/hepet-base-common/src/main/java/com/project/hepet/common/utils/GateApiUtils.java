package com.project.hepet.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

public class GateApiUtils {
	
	/**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @param privateKey 商户私钥
     * @return 提交表单HTML文本
     */
    public static String getPayHtml(Map<String, String> sParaTemp, String strMethod, String strButtonName, String privateKey) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp, privateKey);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"paysubmit\" name=\"paysubmit\" action=\"" + PayConfig.pay_gateway_url
                      + "?_input_charset=" + PayConfig.input_charset + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['paysubmit'].submit();</script>");

        return sbHtml.toString();
    }
    
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp, String privateKey) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp, null);
        //生成签名结果
        String mysign = buildRequestMysign(sPara, privateKey);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("signature", mysign);
        sPara.put("signatureType", "RSA");

        return sPara;
    }
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara, String privateKey) {
    	String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
    	System.out.println("sign createLinkString:"+prestr);
        String mysign = RSA.sign(prestr, privateKey, PayConfig.input_charset);
        return mysign;
    }
    
    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray, List<String> ignoreCaseList) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        //添加默认过滤参数
        if(CollectionUtils.isEmpty(ignoreCaseList)){
        	ignoreCaseList = new ArrayList<String>();
        	ignoreCaseList.add("signature");
        	ignoreCaseList.add("signatureType");
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || ignoreCaseList.contains(key)) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
	
	/** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @param sign 比对的签名结果
     * @return 生成的签名结果
     */
	public static boolean signVerify(Map<String, String> params, String sign , String serverPublicKey ) {
    	//过滤空值、signature与signatureType参数
    	Map<String, String> sParaNew = paraFilter(params, null);
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew);
        //获得签名验证结果
        boolean isSign = false;
        if("RSA".equals(params.get("signatureType"))){
        	isSign = RSA.verify(preSignStr, sign, serverPublicKey, PayConfig.input_charset);
        }
        return isSign;
    }
}
