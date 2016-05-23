package com.wangge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangge.app.server.entity.MonthPunish;
import com.wangge.app.server.entity.OilParameters;
import com.wangge.app.server.service.MonthPunishService;
import com.wangge.app.server.service.OilParametersService;
import com.wangge.app.server.util.DateUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class OilRecordTest {
  @Resource
  private OilParametersService oilParametersService;
  @Resource
  private MonthPunishService mps;
  
  @Test
  public void test(){
   OilParameters oil = oilParametersService.getOilParameters("370105");
   if(oil != null){
     System.out.println("===================="+oil.getKmOilSubsidy());
   }
   
  }
  @Test
  public void test2(){
     double d =10d;
     Float c = (float)d;
     System.out.println("=====>>>>"+c);
  }
  @Test
  public void test3(){
    
    String moveDate= DateUtil.date2String(DateUtil.moveDate(new Date(),-1));
    System.out.println(moveDate);
  }
 
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
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = GregorianCalendar.getInstance();
    try {
    calendar.setTime(sdf.parse("2004-6-07"));
    } catch (ParseException e) {
        System.out.println(e);
    }     
    System.out.println(calendar.get(Calendar.DATE));
    System.out.println(calendar.get(Calendar.MONTH) + 1);
    System.out.println(calendar.get(Calendar.YEAR));
    Long a = 3789L;
    System.out.println("==========="+a/1000);
  }
  
  @Test
  public void test_(){
    List<MonthPunish> mpl=mps.findByUserIdAndCreateDate("A37010504010","2016-05-17" );
    MonthPunish mp= mpl.get(0);
    System.out.println("test_"+mp.getSeriaNo());
    System.out.println("test_"+mp.getUserId());
    
  }
}

