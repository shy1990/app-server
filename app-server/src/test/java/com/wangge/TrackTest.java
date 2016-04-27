package com.wangge;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.util.HttpUtil;
import com.wangge.app.util.ChainageUtil;

public class TrackTest {

  private static JSONObject createJSONObject() {  
    JSONObject jsonObject = new JSONObject();  
    jsonObject.put("ret", new Integer(0));  
    jsonObject.put("msg", "query");  
    JSONObject dataelem1=new JSONObject();  
    //{"deviceid":"SH01H20130002","latitude":"32.140","longitude":"118.640","speed":"","orientation":""}  
    dataelem1.put("deviceid", "SH01H20130002");  
    dataelem1.put("latitude", "32.140");  
    dataelem1.put("longitude", "118.640");  

    JSONObject dataelem2=new JSONObject();  
    //{"deviceid":"SH01H20130002","latitude":"32.140","longitude":"118.640","speed":"","orientation":""}  
    dataelem2.put("deviceid", "SH01H20130002");  
    dataelem2.put("latitude", "32.140");  
    dataelem2.put("longitude", "118.640");  
      
 // 返回一个JSONArray对象  
    JSONArray jsonArray = new JSONArray();  
      
    jsonArray.add(0, dataelem1);  
    jsonArray.add(1, dataelem2);  
    //jsonObject.element("data", jsonArray);  
    jsonObject.put("list", jsonArray);
      
    return jsonObject;  
} 
  
public static void main(String [] args){ 
/*  String str = "[{'a':'111','b':'222'},{'c':'333','a':'999'}]";
//{"deviceid":"SH01H20130002","latitude":"32.140","longitude":"118.640","speed":"","orientation":""}  
  JSONArray j = JSONArray.parseArray(str);
  SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
  
  JSONObject dataelem1=new JSONObject();  
  
  dataelem1.put("deviceid", "SH01H20130001");  
  dataelem1.put("latitude", "32.140"); 
  dataelem1.put("time", format.format(new Date()));
  JSONObject dataelem2=new JSONObject();  
  dataelem2.put("deviceid", "SH01H20130002");  
  dataelem2.put("latitude", "32.141");  
  dataelem2.put("time", format.format(new Date()));
  JSONObject dataelem3=new JSONObject();  
  dataelem3.put("deviceid", "SH01H20130003");  
  dataelem3.put("latitude", "32.142");  
  dataelem3.put("time", format.format(new Date()));
  JSONArray j2 = new JSONArray();
  j2.add(dataelem1);
  j2.add(dataelem2);
  j2.add(dataelem3);
  j2.addAll(j);
  System.out.println("JSONArray========"+j2.toString());
  */
  
  
 /* String str = "123-456";
  String[] str2 = str.split("-");
  System.out.println(">>>>>>>>>>>>>>>>"+str2[0]+"<<<<<<<<<<<<<<<<<<<<<<"+str2[1]);
  
   Double d = ChainageUtil.GetShortDistance(120.412429, 37.369502, 120.414545, 37.366823);
   
  
   JSONObject  s =  (JSONObject)j.get(j.size()-1);
   String coordinates2 = s.getString("coordinates");
   System.out.println("++++++++++++++++++++"+s);*/
  //JSONObject jsonObject = createJSONObject();//静待  方法，直接通过类名+方法调用  
// 输出jsonobject对象  
//System.out.println("jsonObject：" + jsonObject);  
// 添加JSONArray后的值  


// 根据key返回一个字符串  
//String username = jsonObject.getString("ret");  
//System.out.println("username==>" + username);   

//JSONArray l = jsonObject.getJSONArray("list");
//System.out.println("JSONArray========"+l);
   
 String url ="  http://api.map.baidu.com/direction/v1";
 String param2 = "mode=driving&origin='42.913345','125.681496'&destination='36.73533570586392','116.99656722742283'&origin_region='天桥区'&destination_region='天桥区'&output=json&ak=Cr4PTLm91KMTG9eeYxxYhHFt";
 String param = "mode=driving&origin=36.754358,117.246149&destination=36.729324,117.237405&origin_region=济南&destination_region=历城区&output=json&ak=Cr4PTLm91KMTG9eeYxxYhHFt";

 
 String str = HttpUtil.sendGet(url, param2);
 JSONObject json = (JSONObject) JSONObject.parse(str);
 String s = json.getString("result");
 json = (JSONObject) JSONObject.parse(s);
 String a = json.getString("routes");
 JSONArray routesA = json.parseArray(a);
  // json = (JSONObject) JSONObject.parse(routes);
 
 json = (JSONObject) routesA.get(0);
 
// Double d = ChainageUtil.GetShortDistance(117.246149,36.754358, 117.237405,36.729324);
 System.out.println("+++++++=======++++++++"+json.getString("distance"));
}  
}
