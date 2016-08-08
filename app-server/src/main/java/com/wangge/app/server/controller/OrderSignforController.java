package com.wangge.app.server.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.util.LogUtil;

@RestController
@RequestMapping("/v1/remind")
public class OrderSignforController {
  
  
  @Value("${app-interface.url}")
  private String interfaceUrl;
  
  @Resource
  private HttpRequestHandler httpRequestHandler;
 
  /**
   * @throws ParseException 
   * @throws NumberFormatException 
   * @throws ParseException 
   * @throws NumberFormatException 
   * 
  * @Title: getOrderSignforList 
  * @Description: TODO(获取物流单号列表) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<Page<OrderSignfor>>    返回类型 
  * @throws
   */
  @ApiOperation(value="获取物流单号列表", notes="获取物流单号列表")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/getBussOrderList" ,method = RequestMethod.POST)
  public ResponseEntity<Object> getOrderSignforList(@RequestBody JSONObject jsons){
    LogUtil.info("获取物流单号列表, josns="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"remind/getBussOrderList",HttpMethod.POST,null,jsons);
     
  }
  /**
   * 
   * 
  * @Title: bussOrderSignFor 
  * @Description: TODO(业务签收) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<message>    返回类型 
  * @throws
   */
  @ApiOperation(value="代理商揽收快件", notes="代理商揽收快件")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/bussOrderSignFor", method = RequestMethod.POST)
  public ResponseEntity<Object> bussOrderSignFor(@RequestBody JSONObject jsons){
     LogUtil.info("代理商揽收快件, jsons="+jsons.toJSONString());
      //return httpRequestHandler.exchange(interfaceUrl+"remind/bussOrderSignFor",HttpMethod.POST,null,JSONObject.class,jsons);
      return httpRequestHandler.exchange(interfaceUrl+"remind/bussOrderSignFor", HttpMethod.POST, null, jsons);
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
  @ApiOperation(value="获取订单列表", notes="获取订单列表")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
  public ResponseEntity<Object> getOrderList(@RequestBody JSONObject jsons){
    LogUtil.info("获取订单列表, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"remind/getOrderList",HttpMethod.POST,null,jsons);
  }
  /**
   * 
  * @Title: customOrderSign 
  * @Description: TODO(客户签收) 
  * @param @param jsons
  * @param @param orderSignfor
  * @param @return    设定文件 
  * @return ResponseEntity<MessageCustom>    返回类型 
  * @throws
   */
  @ApiOperation(value="客户签收订单", notes="客户签收订单")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value ="/customOrderSign", method = RequestMethod.POST)
  public ResponseEntity<Object> customOrderSign(@RequestBody JSONObject jsons){
    LogUtil.info("客户签收订单, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"remind/customOrderSign",HttpMethod.POST,null,jsons);
  }
 
  /**
   * 
  * @Title: customOrderUnSign 
  * @Description: TODO(客户拒签) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<MessageCustom>    返回类型 
  * @throws
   */
  @ApiOperation(value="客户拒签订单", notes="客户拒签订单")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/customOrderUnSign", method = RequestMethod.POST)
  public ResponseEntity<Object> customOrderUnSign(@RequestBody JSONObject jsons){
    LogUtil.info("客户拒签订单, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"remind/customOrderUnSign",HttpMethod.POST,null, jsons);
  }
  
 
  
  
  /**
   * 
  * @Title: getOrdersByMailNo 
  * @Description: TODO(根据物流单号获取订单列表) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<List<OrderSignfor>>    返回类型 
  * @throws
   */
  @ApiOperation(value="根据物流单号获取订单列表", notes="根据物流单号获取订单列表")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/getOrdersByMailNo", method = RequestMethod.POST)
  public ResponseEntity<Object> getOrdersByMailNo(@RequestBody JSONObject jsons){
    LogUtil.info("根据物流单号获取订单列表, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"remind/getOrdersByMailNo",HttpMethod.POST,null, jsons);
  }
  

}

