package com.wangge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.wangge.app.server.util.JWtoAdrssUtil;

public class OilRecordTest {
  
 
  public static void main(String[] args) {
   // String[] city =  coordinates.split("-");
    /*String lng = "36.73533570584192";
    String lat = "116.99656722742283";
    
   String url="http://api.map.baidu.com/geocoder/v2/?ak=702632E1add3d4953d0f105f27c294b9&callback=renderReverse&location="+lng+","+lat+"&output=json&pois=1";
    String jsonString = JWtoAdrssUtil.getdata(url);
   String jsonstr=jsonString.substring(0,jsonString.length()-1);
   String address = jsonstr.substring(jsonstr.indexOf("city")+6,jsonstr.indexOf("country")-2);
   System.out.println("==================="+address);
   System.out.println("+++++++++++++++++++++++++++++"+jsonstr);*/
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    try {
      Date dateTime = format.parse(format.format(new Date()));
      
      System.out.println("========================"+dateTime);
    //  System.out.println("++++++++++++++++++++++++"+(dateTime-1));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
