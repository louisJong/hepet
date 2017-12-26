package com.project.hepet.common.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
/**
 * 基于 commons-httpclient-3.1
 * @author fidel.zhou
 */
public class HttpService {
	private static Logger logger = Logger.getLogger(HttpService.class);
	private HttpClient httpClient;
	private MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager;
	public HttpService() {
		//logger.info("\n HttpServiceImpl init");
		multiThreadedHttpConnectionManager = new MultiThreadedHttpConnectionManager();
		//连接超时
		multiThreadedHttpConnectionManager.getParams().setConnectionTimeout(10 * 1000);
		// 数据等待超时
		multiThreadedHttpConnectionManager.getParams().setSoTimeout(5*60*1000);
		// super.getParams().setStaleCheckingEnabled(true);
		// 最大连接数 ##每个主机的最大并行链接数 默认是2
		multiThreadedHttpConnectionManager.getParams().setDefaultMaxConnectionsPerHost(50);
		// 最大活动连接数，##客户端总并行链接最大数，默认为20
		multiThreadedHttpConnectionManager.getParams().setMaxTotalConnections(512);
		httpClient = new HttpClient(multiThreadedHttpConnectionManager);
	}
	public String doGet(String url,String ...responseEncode) throws IOException {
		logger.debug(url);
		StringBuffer responseBody = new StringBuffer();
		InputStream in = null;
		GetMethod getMethod = null;
		BufferedInputStream bin = null;
		try {
			 String charset = (responseEncode!=null && responseEncode.length==1) ? responseEncode[0]:"UTF-8";
			getMethod = new GetMethod(url);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + getMethod.getStatusLine());
				logger.error("HttpServiceImpl#doGet(String url)"
						+ "  Please check your provided http address! 发生致命的异常，可能是协议不对或者返回的内容有问题");
				throw new IOException(getMethod.getStatusLine().toString());
			}
			in = getMethod.getResponseBodyAsStream();
			bin = new BufferedInputStream(in);
			byte [] _b = new byte[1024*10];
			int length = 0;
			while((length = bin.read(_b))>0){
				responseBody.append(new String(_b,0,length,charset));
			}
		} finally {
			// 释放连接
			try {
				if(getMethod!=null){
					getMethod.releaseConnection();
				}
				if (in != null) {
					in.close();
				}
				if(bin!=null){
					bin.close();
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return responseBody.toString();
	}
	
	public String doGet(String url ,Header[] headers , String ...responseEncode) throws IOException {
		logger.debug(url);
		StringBuffer responseBody = new StringBuffer();
		InputStream in = null;
		GetMethod getMethod = null;
		BufferedInputStream bin = null;
		try {
			 String charset = (responseEncode!=null && responseEncode.length==1) ? responseEncode[0]:"UTF-8";
			getMethod = new GetMethod(url);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			for(Header header : headers){
				getMethod.addRequestHeader(header);
			}
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + getMethod.getStatusLine());
				logger.error("HttpServiceImpl#doGet(String url)"
						+ "  Please check your provided http address! 发生致命的异常，可能是协议不对或者返回的内容有问题");
				throw new IOException(getMethod.getStatusLine().toString());
			}
			in = getMethod.getResponseBodyAsStream();
			bin = new BufferedInputStream(in);
			byte [] _b = new byte[1024*10];
			int length = 0;
			while((length = bin.read(_b))>0){
				responseBody.append(new String(_b,0,length,charset));
			}
		} finally {
			// 释放连接
			try {
				if(getMethod!=null){
					getMethod.releaseConnection();
				}
				if (in != null) {
					in.close();
				}
				if(bin !=null){
					bin.close();
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return responseBody.toString();
	}
	
	public String doPost(String url,Map<String, String> map,String ...responseEncode) throws IOException {
		PostMethod postMethod = null;
		InputStream in = null;
		StringBuffer responseBody = new StringBuffer();
		BufferedInputStream bin = null;
		try{
			 String charset = (responseEncode!=null && responseEncode.length==1) ? responseEncode[0]:"UTF-8";
			 postMethod = new PostMethod(url);
			 postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			NameValuePair[] data = new NameValuePair[map.size()];
	        @SuppressWarnings("rawtypes")
			Iterator it = map.entrySet().iterator();
	        int i = 0;
	        while (it.hasNext()) {
	            @SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) it.next();
	            Object key = entry.getKey();
	            Object value = entry.getValue();
	            if(value!=null){
	            	data[i] = new NameValuePair(key.toString(), value.toString());
	            }else{
	            	data[i] = new NameValuePair(key.toString(), "");
	            }
	            i++;
	        }
	        // 将表单的 值放入postMethod中
	        postMethod.setRequestBody(data);
	     // 执行postMethod
            int statusCode = httpClient.executeMethod(postMethod);// httpclient对于要求接受后继服务的请求，等待返回
            // 象post和put等不能自动处理转发
            // 301或者302
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 从 头中取出转向的地址
                Header locationHeader = postMethod .getResponseHeader("location");
                String location = null;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    System.out .println("The page was redirected to:" + location);
                } else {
                    System.out.println("Location field value is null");
                }
            }
			in = postMethod.getResponseBodyAsStream();
			bin = new BufferedInputStream(in);
			byte [] _b = new byte[1024*10];
			int length = 0;
			while((length = bin.read(_b))>0){
				responseBody.append(new String(_b,0,length,charset));
			}
		}catch(Exception e){
			logger.error("url="+url+",map="+map,e);
			throw e;
		}finally{
			// 释放连接
			try {
				if(postMethod!=null){
					postMethod.releaseConnection();
				}
				if(in!=null){
					in.close();
				}
				if(bin!=null){
					bin.close();
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return responseBody.toString();
	}
	
	/**
	 * 返回明文响应
	 * @param url
	 * @param datagram
	 * @param responseEncode
	 * @return
	 * @throws Exception
	 */
	public String doPostRequestEntity(String url,RSADatagram datagram,String ...responseEncode) throws Exception {
		String resp = null;
		try{
			resp = this.doPostRequestEntity(url, datagram.encrypt(), responseEncode);
			if(logger.isDebugEnabled())  logger.debug("resp="+resp);
			datagram.verifySign(resp);
			if(logger.isDebugEnabled())  logger.debug("datagram.getPlaintext()="+datagram.getPlaintext());
			return datagram.getPlaintext();			
		}catch(Exception e){
			logger.error("url="+url+",resp="+resp,e);
			throw e;
		}
	}
	
	
	/**
	 * use StringRequestEntity(data,null,null)
	 * @param url
	 * @param data
	 * @param responseEncode
	 * @return
	 * @throws IOException
	 */
	public String doPostRequestEntity(String url,String data,String ...responseEncode) throws IOException {
		PostMethod postMethod = null;
		StringBuffer responseBody = new StringBuffer();
		InputStream in = null;
		BufferedInputStream bin = null;
		try{
			logger.debug("url="+url+",data="+data);
			String charset = (responseEncode!=null && responseEncode.length==1) ? responseEncode[0]:"UTF-8";
			 postMethod = new PostMethod(url);
			 postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			 RequestEntity re = new StringRequestEntity(data,null,null);
	        postMethod.setRequestEntity(re);
	     // 执行postMethod
            int statusCode = httpClient.executeMethod(postMethod);// httpclient对于要求接受后继服务的请求，等待返回
            // 象post和put等不能自动处理转发
            // 301或者302
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 从 头中取出转向的地址
                Header locationHeader = postMethod .getResponseHeader("location");
                String location = null;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    logger.debug("The page was redirected to:" + location);
                } else {
                	logger.debug("Location field value is null");
                }
            }
			in = postMethod.getResponseBodyAsStream();
			bin = new BufferedInputStream(in);
			byte [] _b = new byte[1024*10];
			int length = 0;
			while((length = bin.read(_b))>0){
				responseBody.append(new String(_b,0,length,charset));
			}
		}catch(Exception e){
			logger.error("url="+url+",data="+data,e);
			throw e;
		}finally{
			// 释放连接
			try {
				if(postMethod!=null){
					postMethod.releaseConnection();
				}
				if(in!=null){
					in.close();
				}
				if(bin!=null){
					bin.close();
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return responseBody.toString();
	}
}
