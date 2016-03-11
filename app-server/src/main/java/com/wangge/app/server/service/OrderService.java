package com.wangge.app.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.Order;
import com.wangge.app.server.entity.Region;
import com.wangge.app.server.repository.OrderRepository;
import com.wangge.app.server.vo.OrderPub;
@Service
public class OrderService {
  @Autowired
  private OrderRepository or;
  
  /**
   * 
   * @Description: 根据地区编码查询所属下的订单
   * @param @param region
   * @param @param page
   * @param @return   
   * @return Page<Order>  
   * @throws
   * @author changjun
   * @date 2015年11月20日
   */
  public List<OrderPub> findByRegion(Region region,Pageable page){
    Page<Order> pOrder=or.findByRegion(region, page);
    List<OrderPub> list = new ArrayList<OrderPub>();
    OrderPub order;//20151105180158329
    if(pOrder!=null && pOrder.getTotalPages()>0){
      for(Order o:pOrder){
        order = new OrderPub();
        order.setTotalPage(pOrder.getTotalPages());
        order.setCreateTime(o.getCreateTime());
        order.setOrderNum(o.getId());
        order.setStatus(o.getStatus().getName());
        order.setUsername(o.getShopName());
        order.setTotalCost(Double.parseDouble(o.getAmount()+""));
        list.add(order);
      }
    }
    return list;
  }

  public Order findOne(String ordernum) {
    return or.findOne(ordernum);
  }
  /**
   * 
   * @Description: 回掉钱包接口
   * @param @param jo
   * * @param @param walletNo 钱包流水号
   * @param @throws Exception   
   * @return void  
   * @throws
   * @author changjun
   * @date 2016年1月7日
   */
  public static String invokWallet(JSONObject jo,String walletNo) throws Exception {
//    String url = "http://115.28.87.182:58081/v1/"; 线上  线下//http://115.28.92.73:58080/v1/
    String url = "http://115.28.87.182:58081/v1/"+walletNo+"/status";
    RestTemplate rest = new RestTemplate();
    Map<String,Object> params = new HashMap<String,Object>();
    params.put("status", "FAILURE");
    try {
      rest.put(url, params);
      return "202";
    } catch (Exception e) {
      e.printStackTrace();
      return "500";
    }
    
//    HttpClient client = HttpClientBuilder.create().build();
//    HttpPut put = new HttpPut(url);
//    put.setHeader("Content-type", "application/json");
//    StringEntity params =new StringEntity(jo.toString());
//    put.setEntity(params);
//    HttpResponse response = client.execute(put);
//    System.out.println("Response Code:"+response.getStatusLine().getStatusCode());
//    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//    StringBuffer result = new StringBuffer();
//    String line = "";
//    while ((line = rd.readLine()) != null) {
//          result.append(line);
//        }
//    return result.toString();
  }
    
}
