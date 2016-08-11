package com.wangge.app.server.controller;



import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.util.LogUtil;

@RestController
@RequestMapping({"/v1/remind"})
public class RemindController {
  
  
  @Value("${app-interface.url}")
  private String interfaceUrl;

  @Resource
  private HttpRequestHandler httpRequestHandler;
  /**
   * 
   * @Description: 订单列表
   * @param @param username
   * @param @return   
   * @return ResponseEntity<JSONObject>  
   * @throws
   * @author changjun
   * @date 2015年10月21日
   */
  @ApiOperation(value="订单列表",notes="订单列表")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
  @RequestMapping(value = "/orderList",method = RequestMethod.POST)
  public JSONObject orderList(@RequestBody  JSONObject json){
    LogUtil.info("订单列表, json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"remind/orderList", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
  }
  /**
   * 
   * @Description: 根据订单号查看订单详情
   * @param @param ordernum
   * @param @return   
   * @return ResponseEntity<JSONObject>  
   * @throws
   * @author changjun
   * @date 2015年10月29日
   */
  @ApiOperation(value="根据订单号查看订单详情",notes="根据订单号查看订单详情")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
  @RequestMapping(value = "/selOrderDetail",method = RequestMethod.POST)
  public JSONObject selOrderDetail(@RequestBody  JSONObject json){
      LogUtil.info("根据订单号查看订单详情, json="+json.toJSONString());
      return httpRequestHandler.exchange(interfaceUrl+"remind/selOrderDetail", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
      });
  }
  /**
   * 
   * @Description:消息通知列表
   * @param @param username
   * @param @return   
   * @return ResponseEntity<JSONObject>  
   * @throws
   * @author changjun
   * @date 2015年10月21日
   */
  @ApiOperation(value="消息通知列表",notes="消息通知列表")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
  @RequestMapping(value = "/newsRemindList",method = RequestMethod.POST)
  public JSONObject newsRemindList(@RequestBody  JSONObject json){
    LogUtil.info("消息通知列表, json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"remind/newsRemindList", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
  }
  /**
   * 
   * @Description: 消息详情
   * @param @param newsId
   * @param @return   
   * @return ResponseEntity<JSONObject>  
   * @throws
   * @author changjun
   * @date 2015年10月29日
   */
  @ApiOperation(value="消息详情",notes="消息详情")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
  @RequestMapping(value = "/newsDetail",method = RequestMethod.POST)
  public JSONObject newsDetail(@RequestBody  JSONObject json){
    LogUtil.info("消息详情, json="+json.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"remind/newsDetail", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
    });
  }
  /**
   * 
   * @Description:  活动通知
   * @param @return   
   * @return ResponseEntity<JSONObject>  
   * @throws
   * @author changjun
   * @date 2015年10月21日
   */
  @ApiOperation(value="活动通知",notes="活动通知")
  @ApiImplicitParam(name="json",value="json",required=true,dataType="JSONObject")
  @RequestMapping(value = "/activiList",method = RequestMethod.POST)
  public JSONObject activiRemind(@RequestBody  JSONObject json){
    //查询正在进行中的活动  不区分已读未读 活动 type=2
      LogUtil.info("活动通知, json="+json.toJSONString());
      return httpRequestHandler.exchange(interfaceUrl+"remind/activiList", HttpMethod.POST, null, json, new ParameterizedTypeReference<JSONObject>() {
      });
  }
  /**
   * 
   * @Description: 活动详情
   * @param @param id
   * @param @return   
   * @return ResponseEntity<JSONObject>  
   * @throws
   * @author changjun
   * @date 2015年10月29日
   */
//  @RequestMapping(value = "/activiDetail",method = RequestMethod.POST)
//  public ResponseEntity<Message> activiDetail(@RequestBody  JSONObject json){
//    String id = json.getString("Id");
//    //根据id查询活动详情
//    Message msg = new Message();
//    msg.setContent("双11店庆");
//    msg.setCreateTime(new Date());
//    return new ResponseEntity<Message>(msg, HttpStatus.OK);
//  }
}
