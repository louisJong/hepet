package com.project.hepet.service;

import com.alibaba.fastjson.JSONObject;



/**
 *该接口，主要提供文件的上传、下载至aliyun.oss
 */
public interface FileService {
	
	/**
	 * 字节上传
	 * @param file 文件
	 * @param folderName "goods/detail"
	 * @return
	 */
	public JSONObject uploadByByte(String fileName, byte[] bytes, String folderName);
	
}