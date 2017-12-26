package com.project.hepet.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.project.hepet.common.utils.JsonUtils;
  
/** 
 * HTTP 请求工具类 
 * 
 * @author : liii 
 * @version : 1.0.0 
 * @date : 2015/7/21 
 * @see : TODO 
 */  
@SuppressWarnings("deprecation")
public class HttpUtil {  
    private static PoolingHttpClientConnectionManager connMgr;  
    private static RequestConfig requestConfig;  
    private static final int MAX_TIMEOUT = 30000;//7000;
  private static HttpRequestRetryHandler httpRequestRetryHandler;
    static {  
    	ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
		// 设置连接池  
        connMgr = new PoolingHttpClientConnectionManager(registry); 
        // 设置连接池大小  
        connMgr.setMaxTotal(200);  
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());  
  
        RequestConfig.Builder configBuilder = RequestConfig.custom();  
        // 设置连接超时  
        configBuilder.setConnectTimeout(MAX_TIMEOUT);  
        // 设置读取超时  
        configBuilder.setSocketTimeout(MAX_TIMEOUT);  
        // 设置从连接池获取连接实例的超时  
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);  
        // 在提交请求之前 测试连接是否可用  
        configBuilder.setStaleConnectionCheckEnabled(true);  
        requestConfig = configBuilder.build();
        
        
        httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃                    
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试                    
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常                    
                    return false;
                }                
                if (exception instanceof InterruptedIOException) {// 超时                    
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达                    
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝                    
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常                    
                    return false;
                }
                 
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {                    
                    return true;
                }
                return false;
            }
        };  
    }  
  
    /** 
     * 发送 GET 请求（HTTP），不带输入数据 
     * @param url 
     * @return 
     */  
    public static String doGet(String url) {  
        return doGet(url, new HashMap<String, Object>());  
    }  
  
    /** 
     * 发送 GET 请求（HTTP），K-V形式 
     * @param url 
     * @param params 
     * @return 
     */  
    public static String doGet(String url, Map<String, Object> params) {  
        String apiUrl = url;  
        StringBuffer param = new StringBuffer();  
        int i = 0;  
        for (String key : params.keySet()) {  
            if (i == 0)  
                param.append("?");  
            else  
                param.append("&");  
            param.append(key).append("=").append(params.get(key));  
            i++;  
        }  
        apiUrl += param;  
        String result = null;  
        HttpClient httpclient = getHttpClient();  
        try {  
            HttpGet httpPost = new HttpGet(apiUrl);  
            HttpResponse response = httpclient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
  
            System.out.println("执行状态码 : " + statusCode);  
  
            HttpEntity entity = response.getEntity();
            if (entity != null) {  
            	result =  EntityUtils.toString(entity, "UTF-8");
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    private static HttpClient getHttpClient() {
		return HttpClients.custom().setConnectionManager(connMgr).setRetryHandler(httpRequestRetryHandler).setDefaultRequestConfig(requestConfig).build();
	}

	/** 
     * 发送 POST 请求（HTTP），不带输入数据 
     * @param apiUrl 
     * @return 
     */  
    public static String doPost(String apiUrl) {  
        return doPost(apiUrl, new HashMap<String, Object>());  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），K-V形式 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     */  
    public static String doPost(String apiUrl, Map<String, Object> params) {  
        HttpClient httpClient = getHttpClient();
        String httpStr = null;  
        HttpPost httpPost = new HttpPost(apiUrl);  
        HttpResponse response = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
            for (Map.Entry<String, Object> entry : params.entrySet()) {  
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));  
            response = httpClient.execute(httpPost);  
            System.out.println(response.toString());  
            HttpEntity entity = response.getEntity();  
            httpStr = EntityUtils.toString(entity, "UTF-8");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
  
    /** 
     * 发送 POST 请求（HTTP），JSON形式 
     * @param apiUrl 
     * @param json json对象 
     * @return 
     */  
    public static String doPost(String apiUrl, Object json) {  
    	HttpClient httpClient = getHttpClient();
        String httpStr = null;  
        HttpPost httpPost = new HttpPost(apiUrl);  
        HttpResponse response = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            StringEntity stringEntity = new StringEntity(JsonUtils.modelToJsonString(json),"UTF-8");//解决中文乱码问题  
            stringEntity.setContentEncoding("UTF-8");  
            stringEntity.setContentType("application/json");  
            httpPost.setEntity(stringEntity);  
            response = httpClient.execute(httpPost);  
            HttpEntity entity = response.getEntity();  
            System.out.println(response.getStatusLine().getStatusCode());  
            httpStr = EntityUtils.toString(entity, "UTF-8");  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
  
    /** 
     * 发送 SSL POST 请求（HTTPS），K-V形式 
     * @param apiUrl API接口URL 
     * @param params 参数map 
     * @return 
     */  
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {  
        HttpClient httpClient = getHttpsClient();//HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        HttpResponse response = null;  
        String httpStr = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());  
            for (Map.Entry<String, Object> entry : params.entrySet()) {  
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry  
                        .getValue().toString());  
                pairList.add(pair);  
            }  
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));  
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode != HttpStatus.SC_OK) {  
                return null;  
            }  
            HttpEntity entity = response.getEntity();  
            if (entity == null) {  
                return null;  
            }  
            httpStr = EntityUtils.toString(entity, "utf-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
  
    private static HttpClient getHttpsClient() {
    	SSLContext ctx = null;
		try {
			ctx = SSLContext.getInstance("TLS");
		} catch (NoSuchAlgorithmException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}  
        X509TrustManager tm = new X509TrustManager() {  
                @Override  
                public void checkClientTrusted(X509Certificate[] chain,  
                        String authType) throws CertificateException {  
                }  
                @Override  
                public void checkServerTrusted(X509Certificate[] chain,  
                        String authType) throws CertificateException {  
                }  
                @Override  
                public X509Certificate[] getAcceptedIssuers() {  
                    return null;  
                }  
        };  
        try {
			ctx.init(null, new TrustManager[]{tm}, null);
		} catch (KeyManagementException e1) {
			e1.printStackTrace();
		}
		return HttpClients.custom().setConnectionManager(connMgr).setRetryHandler(httpRequestRetryHandler).setDefaultRequestConfig(requestConfig)
                .setSSLHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
					}
				})  
                .setSslcontext(ctx).build();
	}

	/** 
     * 发送 SSL POST 请求（HTTPS），JSON形式 
     * @param apiUrl API接口URL 
     * @param json JSON对象 
     * @return 
     */  
    public static String doPostSSL(String apiUrl, Object json) {  
    	HttpClient httpClient = getHttpsClient();//CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();  
        HttpPost httpPost = new HttpPost(apiUrl);  
        HttpResponse response = null;  
        String httpStr = null;  
  
        try {  
            httpPost.setConfig(requestConfig);  
            StringEntity stringEntity = new StringEntity(JsonUtils.modelToJsonString(json),"UTF-8");//解决中文乱码问题  
            stringEntity.setContentEncoding("UTF-8");  
            stringEntity.setContentType("application/json");  
            httpPost.setEntity(stringEntity);  
            response = httpClient.execute(httpPost);  
            int statusCode = response.getStatusLine().getStatusCode();  
            if (statusCode != HttpStatus.SC_OK) {  
                return null;  
            }  
            HttpEntity entity = response.getEntity();  
            if (entity == null) {  
                return null;  
            }  
            httpStr = EntityUtils.toString(entity, "utf-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return httpStr;  
    }  
  
//    /** 
//     * 创建SSL安全连接 
//     * 
//     * @return 
//     */  
//    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {  
//        SSLConnectionSocketFactory sslsf = null;  
//        try {  
//            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
//  
//                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {  
//                    return true;  
//                }  
//            }).build();  
//            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {  
//  
//                @Override  
//                public boolean verify(String arg0, SSLSession arg1) {  
//                    return true;  
//                }  
//  
//                @Override  
//                public void verify(String host, SSLSocket ssl) throws IOException {  
//                }  
//  
//                @Override  
//                public void verify(String host, X509Certificate cert) throws SSLException {  
//                }  
//  
//                @Override  
//                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {  
//                }  
//            });  
//        } catch (GeneralSecurityException e) {  
//            e.printStackTrace();  
//        }  
//        return sslsf;  
//    }  
  
  
    /** 
     * 测试方法 
     * @param args 
     */  
    public static void main(String[] args) throws Exception {  
  
	}
}