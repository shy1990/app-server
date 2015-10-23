package com.wangge.app.server.util;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
@SuppressWarnings("deprecation")
public class JPush {
	final static String uri = "http://api.jpush.cn:8800/v2/push";
	final static String uriV3 = "https://api.jpush.cn/v3/push";
	/*final static String app_key = "dfeb118726a94e1db31dcc30";
	final static String secret = "867a719bfa592b9b217a8bcb";*/
	//base64(appKey:masterSecret)
	private static final String app_key = "4fc94e2480cad7414abc3e2a";
	private static final String secret = "52943e21f5912d773e5f5be6";

	
	final static String receiver_type = "5";// 是按imei值发送通知
//	final static String platform = "android,ios";// String 必须
//	final static String platformIOS = "ios";// String 必须
	final static String platformAndroid = "android";// String 必须  "android", "ios", "winphone"。
	final static String msg_type = "1";// 指定是通知
	final static int sendno = 3321;// int 必须


	/**
	 * 消息体
	 * 
	 * @param n_builder_id
	 * @param n_title
	 * @param n_content
	 * @param n_extras
	 * @return
	 */
	public static String getJsonBody2(String message, String context_type,
			String title, String extras) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("context_type", context_type);
		map.put("title", title);
		map.put("extras", extras);
		Gson gson = new Gson();	
		String jsonString = gson.toJson(map);
		return jsonString;
	}
	
	 /** 
     * 消息体 通知
     * @param n_builder_id 
     * @param n_title 
     * @param n_content 
     * @param n_extras 
     * @return 
     */  
	public static String getJsonBody1(String message, String extras) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("n_content", message);
		map.put("extras", extras);
		Gson gson = new Gson();
		String jsonString = gson.toJson(map);
		return jsonString;
	}
	
	public static String getJsonBodyIOS(String message, Map<String, Object> extras) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("n_content", message);
		map.put("n_extras", extras);
		Gson gson = new Gson();
		String jsonString = gson.toJson(map);
		return jsonString;
	}
	
	
	/**
	 * 推送 通知 IOS
	 * @author li_jfeng
	 * @date 2015-5-18 下午04:44:46
	 * @param pushType <推送类型,为空或者null或者registration_id:推送给多个注册ID;all:广播;tag:推送给多个标签（只要在任何一个标签范围内都满足）;tag_and:推送给多个标签（需要同时在多个标签范围内）;>
	 * @param jpushObj <极光推送参数,String或者String[]类型   格式："041e6c3a4be,08056d82579" >
	 * @param message <显示的消息内容>
	 * @param title <标题若为空则使用APP名称>
	 * @param extrasMap <额外字段主要用于业务需要>
	 * @throws ParseException
	 * @throws IOException
	
	public static void sendNotificationV3(String pushType, Object jpushObj, String message, String title, Map<String, Object> extrasMap) throws ParseException, IOException {
		Gson gson = new Gson();
		HttpPost hp = new HttpPost(uriV3);// 请求方法对象
		List<NameValuePair> he = new ArrayList<NameValuePair>();// 请求参数对象
		// 请求头设置
		hp.addHeader("Content-Type", "application/json");
		hp.addHeader("Authorization", "Basic "+(new sun.misc.BASE64Encoder().encode((app_key+":"+secret).getBytes())));
		// 推送平台设置
		he.add(new BasicNameValuePair("platform", platformIOS));
		// 推送类型设置
		Map<String, Object> audienceMap = new HashMap<String, Object>();
		if(null == pushType || "".equals(pushType)){
			pushType = "registration_id";
		}
		if("all".equals(pushType)){
			he.add(new BasicNameValuePair("audience", "all"));
		}else{
			String[] jpushArr = null;
			if(jpushObj instanceof String[]){
				jpushArr = (String[])jpushObj;
			} else if(jpushObj instanceof String){
				jpushArr = ((String)jpushObj).split(",");
			} else {
				throw new ParseException("jpushObj:极光推送参数类型不正确！必须为String或者数组");
			}
			audienceMap.put(pushType, jpushArr);
			he.add(new BasicNameValuePair("audience", gson.toJson(audienceMap)));
		}
		// 推送通知设置
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		Map<String, Object> iosMap = new HashMap<String, Object>();
		notificationMap.put("ios", iosMap);
		iosMap.put("alert", message);
		iosMap.put("extras", extrasMap);
		iosMap.put("title", title);
//		iosMap.put("sound", "default");
//		iosMap.put("badge", "+1");
		he.add(new BasicNameValuePair("notification", gson.toJson(notificationMap)));
		// 推送可选项设置
		Map<String, Object> optionsMap = new HashMap<String, Object>();
		optionsMap.put("apns_production", false);  // true:生产环境 false:开发环境
        he.add(new BasicNameValuePair("options", gson.toJson(optionsMap)));
		hp.setEntity(new UrlEncodedFormEntity(he, HTTP.UTF_8));
		
        HttpClient httpClient = getClient(true);
		HttpResponse hResponse = httpClient.execute(hp);
		hResponse.getStatusLine().getStatusCode();
		HttpEntity entity = hResponse.getEntity();
		String result = EntityUtils.toString(entity);
		System.out.println(result);
	}
	 */
	
	/**
	 * 推送 消息 Android
	 * @author li_jfeng
	 * @date 2015-5-21 下午05:49:28
	 * @param pushType <推送类型,为空或者null或者registration_id:推送给多个注册ID;all:广播;tag:推送给多个标签（只要在任何一个标签范围内都满足）;tag_and:推送给多个标签（需要同时在多个标签范围内）;>
	 * @param jpushObj <极光推送参数 String或着String[]  格式："041e6c3a4be,08056d82579">
	 * @param message <显示的消息内容>
	 * @param title <标题若为空则使用APP名称>
	 * @param extrasMap <额外字段主要用于业务需要>
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void sendMessageV3(String pushType, Object jpushObj, String message, String title, Map<String, Object> extrasMap) throws ParseException, IOException { 
		Gson gson = new Gson();
		HttpPost hp = new HttpPost(uriV3);// 请求方法对象
		List<NameValuePair> he = new ArrayList<NameValuePair>();// 请求参数对象
		// 请求头设置
		hp.addHeader("Content-Type", "application/json");
		hp.addHeader("Authorization", "Basic "+(new sun.misc.BASE64Encoder().encode((app_key+":"+secret).getBytes())));
		// 推送平台设置
		he.add(new BasicNameValuePair("platform", platformAndroid));
		
		// 推送类型设置
		Map<String, Object> audienceMap = new HashMap<String, Object>();
		if(null == pushType || "".equals(pushType)){
			pushType = "registration_id";
		}
		if("all".equals(pushType)){
			he.add(new BasicNameValuePair("audience", "all"));
		}else{
			String[] jpushArr = null;
			if(jpushObj instanceof String[]){
				jpushArr = (String[])jpushObj;
			} else if(jpushObj instanceof String){
				jpushArr = ((String)jpushObj).split(",");
			} else {
				throw new ParseException("jpushObj:极光推送参数类型不正确！必须为String或者数组");
			}
			audienceMap.put(pushType, jpushArr);
			he.add(new BasicNameValuePair("audience", gson.toJson(audienceMap)));
		}
		
		// 推送消息设置
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("msg_content", message);
		messageMap.put("title", title);
		messageMap.put("extras", extrasMap);
        he.add(new BasicNameValuePair("message", gson.toJson(messageMap)));
        
		hp.setEntity(new UrlEncodedFormEntity(he, HTTP.UTF_8));
		
        HttpClient httpClient = getClient(true);
		HttpResponse hResponse = httpClient.execute(hp);
		
		HttpEntity entity = hResponse.getEntity();
		String result = EntityUtils.toString(entity);
		System.out.println(result);
	}
	
	/**
	 * 创建httpClient 
	 * @author li_jfeng
	 * @date 2015-5-18 上午09:01:35
	 * @param isSSL
	 * @return
	 */
	public static HttpClient getClient(boolean isSSL) {

		HttpClient httpClient = new DefaultHttpClient();
		if (isSSL) {
			X509TrustManager xtm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			try {
				SSLContext ctx = SSLContext.getInstance("TLS");
				ctx.init(null, new TrustManager[] { xtm }, null);
				SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
				httpClient.getConnectionManager().getSchemeRegistry().register(
						new Scheme("https", (org.apache.http.conn.scheme.SocketFactory) socketFactory, 443));
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
		return httpClient;
	}
	
	
}
