package com.project.hepet.service.impl;

import java.io.ByteArrayInputStream;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.project.hepet.common.utils.JsonUtils;
import com.project.hepet.service.FileService;

@Service("fileServiceImpl")
public class FileServiceImpl implements FileService {
	@Value("${oss.access.key.id}")
	private String access_key_id;
	@Value("${oss.access.secret.key}")
	private String access_secret_key;
	@Value("${oss.returl}")
	private String retUrl;
	@Value("${oss.endpoint}")
	private String endpoint;
	@Value("${oss.bucketName}")
	private String bucketName;
	@Value("${oss.folder.goods}")
	private String folder_goods;

	/**
	 * 生成文件上传路径，文件夹按年、月份分级
	 * @param fileName 文件名
	 * @param folderName 文件目录 eg ： "goods/detail"
	 * @return
	 */
	public String getFolderKey(String fileName, String folderName){
		StringBuffer folderKey = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		folderKey.append(folderName);
		folderKey.append("/");
		folderKey.append(year);
		folderKey.append("/");
		folderKey.append(month);
		folderKey.append("/");
		folderKey.append(fileName);
		return folderKey.toString();
	}
	
	/**
	 * 文件访问地址
	 * 
	 * @param key
	 * @return
	 */
	public String getReturnUrl(String key) {
		return retUrl + "/" + key;
	}

	@Override
	public JSONObject uploadByByte(String fileName, byte[] bytes, String folderName) {
		JSONObject result = JsonUtils.commonJsonReturn();
		String url = null;
		OSSClient ossClient = null;
		try {
			ObjectMetadata objectMeta = new ObjectMetadata();
			objectMeta.setContentLength(bytes.length);
			// 可以在metadata中标记文件类型
			String fileSuffix = fileName.substring(fileName.indexOf('.')+1,fileName.length());
			objectMeta.setContentType("image/"+fileSuffix);
			if(StringUtils.isEmpty(folderName)){
				folderName = folder_goods;
			}
			String key = getFolderKey(fileName, folderName);
			ossClient = new OSSClient(endpoint, access_key_id, access_secret_key);
			ossClient.putObject(bucketName, key, new ByteArrayInputStream(bytes), objectMeta);
			url = getReturnUrl(key);
			result.getJSONObject("body").put("imgUrl", url);
			return result;
		}catch(Exception e){
			result.getJSONObject("head").put("code", "9999");
			result.getJSONObject("head").put("msg", "系统异常");
			return result;
		}finally{
			if(ossClient!=null)
				ossClient.shutdown();
		}
		
	}

}
