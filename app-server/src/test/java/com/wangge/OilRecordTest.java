package com.wangge;

import com.wangge.app.server.entity.Message;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.entity.RegistData;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.jpush.client.JpushClient;
import com.wangge.app.server.repositoryimpl.RegionImpl;
import com.wangge.app.server.service.*;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


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
  @Resource
  private MessageService mr;
  @Resource
  private OrderSignforService orderSignforService;
//  @Test
//  public void testOildCost(){
//
//    String url ="  http://api.map.baidu.com/direction/v1";
//    String param2 = "mode=driving&origin='42.913345','125.681496'&destination='36.73533570586392','116.99656722742283'&origin_region='天桥区'&destination_region='天桥区'&output=json&ak=Cr4PTLm91KMTG9eeYxxYhHFt";
//    String param = "mode=driving&origin=37.51712,121.370936&destination=37.484823,121.368922&origin_region=青岛市&destination_region=青岛市&output=json&ak=Cr4PTLm91KMTG9eeYxxYhHFt";
//    Float mileage =null;
//
//    String str = HttpUtil.sendGet(url, param);
//    JSONObject json = (JSONObject) JSONObject.parse(str);
//    String s = json.getString("result");
//    json = (JSONObject) JSONObject.parse(s);
//    String a = json.getString("routes");
//    JSONArray routesA = json.parseArray(a);
//     // json = (JSONObject) JSONObject.parse(routes);
//
//    json = (JSONObject) routesA.get(0);
//    Float  d = Float.parseFloat(json.getString("distance"));
//    // Float  d = (float)ChainageUtil.GetShortDistance(Double.parseDouble("121.362174") , Double.parseDouble("37.580944"), Double.parseDouble("121.370898"), Double.parseDouble("37.517152"));
//
//   // Double d = ChainageUtil.GetShortDistance(117.246149,36.754358, 117.237405,36.729324);
//     Float distance = Float.parseFloat(String.format("%.2f", d/1000));
//   //  OilParameters oparam =  oilParametersService.getOilParameters("370689");
//     ;//获取油补系数
//
//     mileage = mileage != null ? distance + mileage : distance;//将握手点之间的距离叠加起来
//    System.out.println("+++++++j=======++++++++"+mileage);
//    System.out.println("+++++++j=======++++++++"+d);
//  }
//
//  @Test
//  public void test(){
//    Salesman salesman = salesmanService.findSalesmanbyId("B37018805170");
//    List<String> ids = regionService.findParentIds(salesman.getRegion().getId());
//    System.out.println("size========"+ids.size());
//  }
//  @Test
//  public void testgetUserId(){
//    String userId = salesmanService.findByMobile("15065991531").getId();
//    System.out.println("=========================userId"+userId);
//  }
//  @Test
//  public void test2(){
//     Map<String, String> map = registDataService.getMap("18769727652");
//     System.out.println("+++++++++++++"+map.get("regionId"));
//  }
//  @Test
//  public void test3(){
//    Float  d = (float)ChainageUtil.GetShortDistance(Double.parseDouble("120.421") , Double.parseDouble("36.245946"), Double.parseDouble("120.38495"), Double.parseDouble("36.307558"));
//    System.out.println("========>>>>>>>>>>>>>>>>"+d);
//  }

  //@Test
  public void test4(){
    String jsonString ="  renderReverse&&renderReverse({'status':0,'result':{'location':{'lng':117.60506299999993,'lat':37.63680400404942},'formatted_address':'山东省滨州市阳信县阳城六路','business':'','addressComponent':{'country':'中国','country_code':0,'province':'山东省','city':'滨州市','district':'阳信县','adcode':'371622','street':'阳城六路','street_number':'','direction':'','distance':''},'pois':[{'addr':'阳城六路九龙湖公园西北200米','cp':' ','direction':'北','distance':'223','name':'红黄蓝亲子园山东阳信县春辉园','poiType':'教育培训','point':{'x':117.60577107512457,'y':37.63530617537291},'tag':'教育培训;亲子教育','tel':'','uid':'366a49497bb2898a5bf6bead','zip':''},{'addr':'阳城六路阳光嘉苑小区A区11号楼六号车库','cp':' ','direction':'北','distance':'247','name':'阳信县阳信镇阳光家园副食超市','poiType':'购物','point':{'x':117.60589683789597,'y':37.63516329112724},'tag':'购物','tel':'','uid':'1a2a7d94beed38fecafa5f9a','zip':''},{'addr':'阳城六路567号','cp':' ','direction':'西南','distance':'598','name':'阳信县政府','poiType':'政府机构','point':{'x':117.60986734825008,'y':37.63872817026641},'tag':'政府机构;各级政府','tel':'','uid':'e662275d2cf3f70641d63ec1','zip':''},{'addr':'阳城六路567','cp':' ','direction':'西','distance':'364','name':'阳信县信访局','poiType':'政府机构','point':{'x':117.60810666945052,'y':37.637770884993859},'tag':'政府机构;行政单位','tel':'','uid':'b928749f0b325700400245d6','zip':''},{'addr':'滨州市阳信县阳城六路','cp':' ','direction':'东','distance':'397','name':'阳光嘉苑','poiType':'房地产','point':{'x':117.60154903922768,'y':37.637327957748137},'tag':'房地产;住宅区','tel':'','uid':'2ba15fe517dce6f0f32d8339','zip':''},{'addr':'滨州市阳信县','cp':' ','direction':'北','distance':'481','name':'九龙湖公园','poiType':'旅游景点','point':{'x':117.60638192287137,'y':37.63352724676421},'tag':'旅游景点;公园','tel':'','uid':'e3e1d7bc5b14efce2eb17eb2','zip':''},{'addr':'山东省滨州市阳信县河东一路机关小区50号','cp':' ','direction':'东北','distance':'498','name':'山东滨州阳信机关小区南门久久鸭','poiType':'房地产','point':{'x':117.60241141251726,'y':37.63393447517611},'tag':'房地产;住宅区','tel':'','uid':'ff2249e7b6e2cfadab059d78','zip':''},{'addr':'滨州市阳信县','cp':' ','direction':'西南','distance':'602','name':'阳信县统计局','poiType':'政府机构','point':{'x':117.60984039908478,'y':37.638828184427939},'tag':'政府机构;行政单位','tel':'','uid':'59b8126bc311c7edd23ce36c','zip':''},{'addr':'阳信县其他机关小区南门','cp':' ','direction':'东北','distance':'681','name':'驿站爱车','poiType':'汽车服务','point':{'x':117.6003722590096,'y':37.633670134182597},'tag':'汽车服务;汽车美容','tel':'','uid':'02c0e02c831e12ab9703195c','zip':''},{'addr':'山东省滨州市阳信县河东三路','cp':' ','direction':'西','distance':'866','name':'山东省农村信用合作社','poiType':'金融','point':{'x':117.6127149767167,'y':37.637935196036888},'tag':'金融;信用社','tel':'','uid':'768f035bec17d951b9df8f5e','zip':''}],'poiRegions':[],'sematic_description':'红黄蓝亲子园山东阳信县春辉园北223米','cityCode':235}})";
    System.out.println(jsonString);
    String jsonstr=jsonString.substring(0,jsonString.length()-1);
    System.out.println(jsonstr.indexOf("city"));
    System.out.println(jsonstr.indexOf("district")-2);
    String address = jsonstr.substring(jsonstr.indexOf("city")+6,jsonstr.indexOf("district")-2);
  }


  @Test
  public void test5(){


    /**
     * 【20151029164718792】山东省潍坊市青州市潍坊市青州市|弥河镇慧霞手机店下单成功，
     * 订单商品:标准版Samsung/三星 三星SM-G5308W*1台,标准版Xiaomi/小米 红米 红米note2*2台
     *{"orderNum":"222222222222222","mobiles":"1561069 62989","amount":"10.0","username":"天桥魅族店"}
     */

   String msg="{'orderNum':'20161008103803709','mobiles':'18653807779','amount':'970','username':'泰安市东平县佛山营业厅','skuNum':'1','accNum':'2','memberMobile':'18653807779'}";
    JSONObject json = new JSONObject(msg);
    String mobile = json.getString("mobiles");
    String accCount = json.getString("accNum");
    String ss = json.getString("username");
    int skuNum = Integer.parseInt(json.getString("skuNum"));
    Float amount = Float.parseFloat(json.getString("amount"));//总金额
    Float acutalPrice = Float.parseFloat(json.getString("acutalPrice"));//总金额
    String orderno = json.getString("orderNum");
    Salesman salesman =new Salesman();
    String userId=null;
    Message mes = new Message();
    mes.setChannel(Message.SendChannel.PUSH);
    mes.setType(Message.MessageType.ORDER);
    mes.setSendTime(new Date());
    mes.setContent(msg);
    mes.setReceiver(mobile);
    mr.save(mes);
    if(!json.isNull("memberMobile")){
      String memberMobile=json.getString("memberMobile");
      RegistData registdata=registDataService.findByPhoneNum(memberMobile);
      if(null==registdata){
      }
      List<Salesman> listSalesman= salesmanService.findSaleamanByRegionId(registdata.getRegion().getParent().getId());//通过注册客户信息找到关联区域的业务员。正确推送步骤需要1.业务后台注册数据要和区域统一
      for(Salesman man:listSalesman){
        if(man.getUser().getStatus().ordinal()==0){
          salesman=man;
        }
      }
      mobile=salesman.getMobile();
      userId=salesman.getId();
    }

//    if(ss.contains("市")){
    //     ss = ss.substring(ss.indexOf("市")+1,ss.length());
//    }
    String send = ss+",数量:"+skuNum+",金额:"+amount+",订单号:"+orderno;


    String str = "";
    try {


      if(orderSignforService.existOrder(orderno)){//判断订单是否已经存在，不存在保存
        OrderSignfor o = new OrderSignfor();
        o.setOrderNo(orderno);
        o.setCreatTime(new Date());
        o.setOrderPrice(amount);
        o.setActualPayNum(acutalPrice);//实际金额
        o.setPhoneCount(skuNum);
        o.setOrderStatus(0);
        o.setShopName(ss);
        o.setUserId(userId);
        o.setUserPhone(mobile);
        o.setPartsCount(Integer.parseInt(accCount));
        orderSignforService.saveOrderSignfor(o);
        if(null!=salesmanService.findByMobile(mobile)){
          str = JpushClient.sendOrder("下单通知", send,mobile,json.getString("orderNum"),json.getString("skuNum"),json.getString("accNum"),"0");
        }
      }





    } catch (Exception e) {
      e.printStackTrace();
    }
    if(str.contains("发送失败")){
      //  mr.updateMessageResult(str, mes.getId());
    }
  }
}

