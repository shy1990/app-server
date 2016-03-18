package com.wangge.app.server.controller;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.OrderSignfor;
import com.wangge.app.server.pojo.MessageCustom;
import com.wangge.app.server.pojo.QueryResult;
import com.wangge.app.server.pojo.message;
import com.wangge.app.server.repositoryimpl.OrderSignforImpl;
import com.wangge.app.server.service.OrderSignforService;

@RestController
@RequestMapping("/v1/remind")
public class OrderSignforController {
  @Resource
  private OrderSignforService orderSignforService;
  
  @Resource
  private OrderSignforImpl osi;
  /**
   * @throws ParseException 
   * @throws NumberFormatException 
   * @throws ParseException 
   * @throws NumberFormatException 
   * 
  * @Title: getOrderSignforList 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<Page<OrderSignfor>>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getBussOrderList" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<QueryResult<OrderSignfor>> getOrderSignforList(@RequestBody JSONObject jsons){
    String pageNo = jsons.getString("pageNumber");
    String pageSize = jsons.getString("pageSize") ;
    String userPhone = jsons.getString("userPhone") ;
    
    QueryResult<OrderSignfor> list = osi.getOrderSignforList(userPhone,pageNo != null ?Integer.parseInt(pageNo)-1:0,pageSize != null ? Integer.parseInt(pageSize) : 10);
    return new ResponseEntity<QueryResult<OrderSignfor>>(list,HttpStatus.OK);
  }
  /**
   * 
  * @Title: bussOrderSignFor 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<message>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/bussOrderSignFor", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<message> bussOrderSignFor(@RequestBody JSONObject jsons){
      String fastMailNo = jsons.getString("fastmailNo");
      String userPhone = jsons.getString("userPhone");
      MessageCustom m = new MessageCustom();
     // List<OrderSignfor> os = orderSignforService.findByFastmailNo(fastMailNo);
      try {
        orderSignforService.updateOrderSignforList(fastMailNo,userPhone);
        m.setMsg("success");
        m.setCode("0");
        m.setCreatTime(new Date());
      } catch (Exception e) {
        e.printStackTrace();
        m.setMsg("false");
      }
      return new ResponseEntity<message>(m,HttpStatus.OK);
  }
  /**
   * @throws ParseException 
   * @throws NumberFormatException 
   * 
  * @Title: getOrderList 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<List<OrderSignfor>>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<QueryResult<OrderSignfor>> getOrderList(@RequestBody JSONObject jsons){
                String userPhone = jsons.getString("userPhone");
                String type = jsons.getString("type");
                String pageNo = jsons.getString("pageNumber");
                String pageSize = jsons.getString("pageSize") ;
                QueryResult<OrderSignfor> qr = osi.getOrderList(userPhone, type, pageNo != null ? Integer.parseInt(pageNo)-1 : 0,pageSize != null ? Integer.parseInt(pageSize) : 10);
    return new ResponseEntity<QueryResult<OrderSignfor>>(qr,HttpStatus.OK);
  }

}
