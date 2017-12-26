package com.project.hepet.utils;

import com.project.hepet.encrypt.sm.SM2Utils;
import com.project.hepet.utils.encrypt.Util;

/**
 * User: dpj
 * Date: 13-10-17
 * Time: 下午3:19
 */
public class SM2Encrypt {
	public final static String UTF_8="UTF-8";
    /**
     * SM2 加密
     *
     * @param sSrc  加密内容 必填 ( 必须为UTF_8)
     * @param pub_key 公钥 必填
     * @return 成功或失败或异常信息
     */
    public static String encrypt(String sSrc, String pub_key) throws Exception {
        if (pub_key == null) {
            return null;
        }
        return SM2Utils.encrypt(Util.hexToByte(pub_key), 
        		sSrc.getBytes("UTF-8"));
    }

    /**
     * SM2 解密
     *
     * @param sSrc  解密密文 必填(HEX字符串)
     * @param pri_key 私钥 必填
     * @return 解密明文 ( 为UTF_8编码集)
     */
    public static String decrypt(String sSrc, String pri_key) throws Exception {
    	// 判断Key是否正确
        if (pri_key == null) {
        	return null;
        }
        return new String(SM2Utils.decrypt(Util.hexToByte(pri_key),
        		Util.hexToByte(sSrc)), "UTF-8");
    }
}

