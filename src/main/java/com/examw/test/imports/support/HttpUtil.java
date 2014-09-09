package com.examw.test.imports.support;

import java.io.BufferedReader;
import java.io.IOException; 
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
/**
 * HTTP工具类。
 * @author yangyong.
 * @since 2014-03-01.
 * */
public final class HttpUtil {
	private static final Logger logger = Logger.getLogger(HttpUtil.class);
	/**
	 * 发起http请求获取反馈。
	 * @param connection
	 * 	http链接对象。
	 * @param headers
	 * 	头信息。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @param charsetName
	 * 字符集。
	 * @return
	 * 	反馈结果。
	 * @throws IOException 
	 * */
	public static String sendRequest(HttpURLConnection connection,Map<String, String> headers, String method, String data,String charsetName) throws IOException{
		if(logger.isDebugEnabled()) logger.debug(String.format("发起HTTP请求:[method=>%1$s][data=>%2$s][charsetName=>%3$s]", method, data,charsetName));
		connection.setDoOutput(true);
		connection.setDoInput(true);
		//头信息。
		if(headers != null && headers.size() > 0){
			 for(Entry<String, String> entry : headers.entrySet()){
				 String key = entry.getKey(),value = entry.getValue();
				 if(!StringUtils.isEmpty(key)){
					 connection.addRequestProperty(key, value);
				 }
			 }
		}
		//设置请求方式(GET/POST)
		connection.setRequestMethod(method);
		if(method.equalsIgnoreCase("GET")){
			connection.connect();
		}
		//当有数据需要提交时
		if(!StringUtils.isEmpty(data)){
			OutputStream outputStream = connection.getOutputStream();
			//注意编码格式，防止中文乱码
			outputStream.write(data.getBytes("UTF-8"));
			outputStream.close();
		}
		//将返回的输入流转换成字符串
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),
																																		 (StringUtils.isEmpty(charsetName)? "UTF-8" :charsetName)));
		StringBuilder builder = new StringBuilder();
		String out = null;
		while(!StringUtils.isEmpty(out = bufferedReader.readLine())){
			builder.append(out);
		}
		//释放资源
		bufferedReader.close();
		connection.disconnect();
		if(logger.isDebugEnabled()) logger.debug("反馈数据=>" + builder);
		return builder.toString();
	}
	/**
	 * 发起http请求获取反馈。
	 * @param connection
	 * 	http链接对象。
	 * @param headers
	 * 	头信息。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @return
	 * 	反馈结果。
	 * @throws IOException 
	 * */
	public static String sendRequest(HttpURLConnection connection,Map<String, String> headers, String method, String data) throws IOException{
		return sendRequest(connection, headers, method, data, null);
	}
	/**
	 * 发起https请求获取反馈。
	 * @param x509TrustManager
	 * 	SSL证书。
	 * @param url
	 * 	请求地址。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @param charsetName
	 * 字符集。
	 * @return
	 * 	反馈结果。
	 * @throws IOException
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * */
	public static String sendRequest(X509TrustManager x509TrustManager,String url, String method,String data,String charsetName) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException{
			if(logger.isDebugEnabled()) logger.debug("发起https请求获取反馈信息...");
			if(x509TrustManager == null) return sendRequest(url, method, data);
			//创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {x509TrustManager};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			//从上述SSLContext对象中得到SSLSocketFactory对象。
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			if(logger.isDebugEnabled()) logger.debug("Url=>" + url);
			URL uri = new URL(url);
			HttpsURLConnection connection = (HttpsURLConnection)uri.openConnection();
			connection.setSSLSocketFactory(ssf);
			
			return sendRequest(connection,null, method, data, charsetName);
	}
	/**
	 * 发起https请求获取反馈。
	 * @param x509TrustManager
	 * 	SSL证书。
	 * @param url
	 * 	请求地址。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @return
	 * 	反馈结果。
	 * @throws IOException
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * */
	public static String sendRequest(X509TrustManager x509TrustManager,String url, String method,String data) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException{
		return sendRequest(x509TrustManager, url, method, data, null);
	}
	/**
	 * 发起http请求获取反馈。
	 * @param url
	 * 	请求地址。
	 * @param headers。
	 * 	头信息。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @param charsetName
	 * 字符集。
	 * @return
	 * 	反馈结果。
	 * @throws IOException 
	 * */
	public static String sendRequest(String url,Map<String, String> headers, String method,String data,String charsetName) throws IOException{
		if(logger.isDebugEnabled()) {
			logger.debug(String.format("url=>%1$s", url));
			logger.debug(String.format("method=>%1$s", method));
			logger.debug(String.format("data=>%1$s", data));
			logger.debug(String.format("charsetName=>%1$s", charsetName));
		}
		URL uri = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)uri.openConnection();
		return sendRequest(connection, headers, method, data,charsetName);
	}
	/**
	 * 发起http请求获取反馈。
	 * @param url
	 * 	请求地址。
	 * @param headers。
	 * 	头信息。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @return
	 * 	反馈结果。
	 * @throws IOException 
	 * */
	public static String sendRequest(String url,Map<String, String> headers, String method,String data) throws IOException{
		return sendRequest(url,headers,method,data, null);
	}
	/**
	 * 发起http请求获取反馈。
	 * @param url
	 * 	请求地址。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @param charsetName
	 * 字符集。
	 * @return
	 * 	反馈结果。
	 * @throws IOException 
	 * */
	public static String sendRequest(String url, String method,String data,String charsetName) throws IOException{
		return sendRequest(url,null, method, data,charsetName);
	}
	/**
	 * 发起http请求获取反馈。
	 * @param url
	 * 	请求地址。
	 * @param method
	 * 	请求方式(GET,POST)。
	 * @param data
	 * 	提交数据。
	 * @return
	 * 	反馈结果。
	 * @throws IOException 
	 * */
	public static String sendRequest(String url, String method,String data) throws IOException{
		return sendRequest(url, method, data, null);
	}
}