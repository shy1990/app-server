package com.wangge;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.AppServerApplication;
import com.wangge.app.server.entity.OilParameters;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.repositoryimpl.RegionImpl;
import com.wangge.app.server.service.OilParametersService;
import com.wangge.app.server.service.RegionService;
import com.wangge.app.server.service.RegistDataService;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.util.ChainageUtil;
import com.wangge.app.server.util.HttpUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class OilRecordTest {
  @Resource
  private OilParametersService oilParametersService;
  @Resource
  private RegionService regionService;
  @Resource
  private RegionImpl impl;
  @Resource
  private SalesmanService salesmanService;
  @Resource
  private RegistDataService registDataService;
  @Test
  public void testOildCost(){
    
    String url ="  http://api.map.baidu.com/direction/v1";
    String param2 = "mode=driving&origin='42.913345','125.681496'&destination='36.73533570586392','116.99656722742283'&origin_region='天桥区'&destination_region='天桥区'&output=json&ak=Cr4PTLm91KMTG9eeYxxYhHFt";
    String param = "mode=driving&origin=37.51712,121.370936&destination=37.484823,121.368922&origin_region=青岛市&destination_region=青岛市&output=json&ak=Cr4PTLm91KMTG9eeYxxYhHFt";
    Float mileage =null;
    
    String str = HttpUtil.sendGet(url, param);
    JSONObject json = (JSONObject) JSONObject.parse(str);
    String s = json.getString("result");
    json = (JSONObject) JSONObject.parse(s);
    String a = json.getString("routes");
    JSONArray routesA = json.parseArray(a);
     // json = (JSONObject) JSONObject.parse(routes);
    
    json = (JSONObject) routesA.get(0);
    Float  d = Float.parseFloat(json.getString("distance"));
    // Float  d = (float)ChainageUtil.GetShortDistance(Double.parseDouble("121.362174") , Double.parseDouble("37.580944"), Double.parseDouble("121.370898"), Double.parseDouble("37.517152"));
    
   // Double d = ChainageUtil.GetShortDistance(117.246149,36.754358, 117.237405,36.729324);
     Float distance = Float.parseFloat(String.format("%.2f", d/1000));
   //  OilParameters oparam =  oilParametersService.getOilParameters("370689");
     ;//获取油补系数
     
     mileage = mileage != null ? distance + mileage : distance;//将握手点之间的距离叠加起来
    System.out.println("+++++++j=======++++++++"+mileage);
    System.out.println("+++++++j=======++++++++"+d);
  }
  
  @Test
  public void test(){
    Salesman salesman = salesmanService.findSalesmanbyId("B37018805170");
    List<String> ids = regionService.findParentIds(salesman.getRegion().getId());
    System.out.println("size========"+ids.size());
  }
  @Test
  public void testgetUserId(){
    String userId = salesmanService.findByMobile("15065991531").getId();
    System.out.println("=========================userId"+userId);
  }
  @Test
  public void test2(){
     Map<String, String> map = registDataService.getMap("18769727652");
     System.out.println("+++++++++++++"+map.get("regionId"));
  }
  @Test
  public void test3(){
    Float  d = (float)ChainageUtil.GetShortDistance(Double.parseDouble("120.421") , Double.parseDouble("36.245946"), Double.parseDouble("120.38495"), Double.parseDouble("36.307558"));
    System.out.println("========>>>>>>>>>>>>>>>>"+d);
  }
}

