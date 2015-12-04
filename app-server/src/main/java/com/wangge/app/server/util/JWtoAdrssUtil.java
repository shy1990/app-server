package com.wangge.app.server.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;


 


public class JWtoAdrssUtil {
	
	public static String getdata(String url){
		String result = "";
        BufferedReader in = null;
		try {
		System.out.println(url);
		URL getUrl = new URL(url); 
		 HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection(); 
		  // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");
            connection.setRequestMethod("POST");// 提交模式
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally{
		  try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
	}
		return result;
	}
	
	
	
	 public static String getappdata(String rurl,String data){
		 	URL url;
		 	StringBuffer bankXmlBuffer=null;
		try {
			 url = new URL(rurl);
			 bankXmlBuffer = new StringBuffer();  
			 //创建URL连接，提交到银行卡鉴权，获取返回结果  
			 HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			 connection.setRequestMethod("POST");  
			 connection.setDoOutput(true);  
			 connection.setRequestProperty("User-Agent", "directclient");  
			 PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(),"utf-8"));  
			 out.println(data);  
			 out.close();  
			 BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));  
			   
			 String inputLine;  
			   
			 while ((inputLine = in.readLine()) != null) {  
			     bankXmlBuffer.append(inputLine);  
			 }  
			 in.close();  
			 System.out.println(bankXmlBuffer.toString());  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		 
		 return bankXmlBuffer.toString();
	 }
	 //编码转换
		public static String toUtf8String(String s) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c >= 0 && c <= 255) {
					sb.append(c);
				} else {
					byte[] b;
					try {
						b = String.valueOf(c).getBytes("utf-8");
					} catch (Exception ex) {
						// System.out.println(ex);
						b = new byte[0];
					}
					for (int j = 0; j < b.length; j++) {
						int k = b[j];
						if (k < 0)
							k += 256;
						sb.append("%" + Integer.toHexString(k).toUpperCase());
					}
				}
			}
			return sb.toString();
		}
	 
}
