package com.wangge.app.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OilCostRecord;
import com.wangge.app.server.util.HttpUtil;

public class ChainageUtil {
  static double DEF_PI = 3.14159265359; // PI  
  static double DEF_2PI= 6.28318530712; // 2*PI  
  static double DEF_PI180= 0.01745329252; // PI/180.0  
  static double DEF_R =6370693.5; // radius of earth  
  static SimpleDateFormat formate = new SimpleDateFormat("HH:mm:ss");
  static final String url ="http://api.map.baidu.com/direction/v1";
  static final String ak = "&output=json&ak=Cr4PTLm91KMTG9eeYxxYhHFt";
      static final String mode ="mode=driving";
  
          //计算两点间的距离适用于近距离  
  public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2){  
      double ew1, ns1, ew2, ns2;  
      double dx, dy, dew;  
      double distance;  
      // 角度转换为弧度  
      ew1 = lon1 * DEF_PI180;  
      ns1 = lat1 * DEF_PI180;  
      ew2 = lon2 * DEF_PI180;  
      ns2 = lat2 * DEF_PI180;  
      // 经度差  
      dew = ew1 - ew2;  
      // 若跨东经和西经180 度，进行调整  
      if (dew > DEF_PI)  
      dew = DEF_2PI - dew;  
      else if (dew < -DEF_PI)  
      dew = DEF_2PI + dew;  
      dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)  
      dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)  
      // 勾股定理求斜边长  
      distance = Math.sqrt(dx * dx + dy * dy);  
      return distance;  
  }  
          //适用于远距离  
  public static double GetLongDistance(double lon1, double lat1, double lon2, double lat2){  
      double ew1, ns1, ew2, ns2;  
      double distance;  
      // 角度转换为弧度  
      ew1 = lon1 * DEF_PI180;  
      ns1 = lat1 * DEF_PI180;  
      ew2 = lon2 * DEF_PI180;  
      ns2 = lat2 * DEF_PI180;  
      // 求大圆劣弧与球心所夹的角(弧度)  
      distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);  
      // 调整到[-1..1]范围内，避免溢出  
      if (distance > 1.0)  
           distance = 1.0;  
      else if (distance < -1.0)  
            distance = -1.0;  
      // 求大圆劣弧长度  
      distance = DEF_R * Math.acos(distance);  
      return distance;  
  } 
  
  public static Float createDistance(String param){
    String params = mode+param+ak;
    String str = HttpUtil.sendGet(url, params);
    JSONObject json = (JSONObject) JSONObject.parse(str);
    String s = json.getString("result");
    json = (JSONObject) JSONObject.parse(s);
    String a = json.getString("routes");
    JSONArray routesA = json.parseArray(a);
    json = (JSONObject) routesA.get(0);
    Float distance = Float.parseFloat(json.getString("distance"));
    
    return distance;
  }
  
  
  public static JSONObject createOilRecord(String coordinate,String missName,String regionName,int regionType,int exception){
    JSONObject json = new JSONObject();
    json.put("regionType", regionType);
    json.put("coordinate", coordinate);
    json.put("missName", missName);
    json.put("regionName", regionName);
    json.put("exception", exception);
    json.put("missTime", formate.format(new Date()));
    return json;
  }
  
  public static JSONObject createOilRecord(String regionName,String shopName,String coordinate,String missName,int regionType){
    JSONObject json = new JSONObject();
    json.put("regionType", regionType);
    json.put("coordinate", coordinate);
    json.put("missName", missName);
    json.put("regionName", regionName);
    json.put("shopName", shopName);
    json.put("missTime", formate.format(new Date()));
    return json;
  }
  
  public static JSONObject  createPrimaryYesterydayOilRecord(OilCostRecord o, List<OilCostRecord> childOilRecord){
    JSONArray primaryJsonArray = JSONArray.parseArray(o.getOilRecord().toString());
    JSONObject jsonObject = new JSONObject();
    JSONArray j =new JSONArray();
    Float distance = o.getDistance();
    Float oilCost = o.getOilCost();
    //组装主账号油补json串
      JSONObject primaryJson = new JSONObject();
      primaryJson.put("type", 0);
      primaryJson.put("content", primaryJsonArray);
      j.add(primaryJson);
      //组装子账号json串，计算总的公里数和又不费用
      if(childOilRecord != null && childOilRecord.size() > 0){
        JSONObject childJson = new JSONObject();
        JSONArray childJsonArray = new JSONArray();
        for(OilCostRecord or : childOilRecord){
          JSONArray JsonArray = JSONArray.parseArray(or.getOilRecord());
          childJsonArray.addAll(JsonArray);
          distance =distance+or.getDistance(); 
          oilCost = oilCost + or.getOilCost();
        }
        childJson.put("type", 1);
        childJson.put("content", childJsonArray);
        j.add(childJson);
      }
      
      //组装总的油补记录json串
      jsonObject.put("distance", distance);
      jsonObject.put("oilCost", oilCost);
      jsonObject.put("oilRecord", j);
      return jsonObject;
   
}
  public static JSONObject createChildYesterydayOilRecord(OilCostRecord chilId,
      OilCostRecord primary) {
    JSONArray chilIdJsonArray = JSONArray.parseArray(chilId.getOilRecord());
    JSONObject jsonObject = new JSONObject();
    JSONArray j =new JSONArray();
    Float distance = chilId.getDistance();
    Float oilCost = chilId.getOilCost();
    //组装子账号油补json串
      JSONObject childJson = new JSONObject();
      childJson.put("type", 1);
      childJson.put("content", chilIdJsonArray);
      j.add(childJson);
    //组装主账号json串，计算总的公里数和又不费用
      if(chilId != null){
        JSONObject  primaryJson = new JSONObject();
        JSONArray  primaryJsonArray = JSONArray.parseArray(primary.getOilRecord());
          distance =distance+primary.getDistance(); 
          oilCost = oilCost + primary.getOilCost();
        childJson.put("type", 0);
        childJson.put("content", primaryJsonArray);
        j.add(primaryJson);
      }
      //组装总的油补记录json串
      jsonObject.put("distance", distance);
      jsonObject.put("oilCost", oilCost);
      jsonObject.put("oilRecord", j);
      return jsonObject;
  }
}

