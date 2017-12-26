package com.project.hepet.utils;

import java.security.MessageDigest;

/**
 * @author s10252
 * 
 */
public class MD5Encrypter {

	public static String md5(byte[] input) {
		String md5val = null;
		MessageDigest alg = null;
		try {
			alg = MessageDigest.getInstance("MD5");
			alg.update(input);
			byte[] digest = alg.digest();
			md5val = byte2hex(digest);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return md5val;
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}

	public static void main(String args[]) {
		System.out.println(md5("12345".getBytes()));
	}
}
