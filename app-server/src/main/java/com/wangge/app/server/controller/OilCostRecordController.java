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


/**
 * 
* @ClassName: TrackController
* @Description: TODO(油补类)
* @author A18ccms a18ccms_gmail_com
* @date 2016年3月30日 上午10:43:30
*
 */

@RestController
@RequestMapping("/v1/oilCostRecord")
public class OilCostRecordController {
  
  @Value("${app-interface.url}")
  private String interfaceUrl;
    
  @Resource
  private HttpRequestHandler httpRequestHandler;
  
  @ApiOperation(value="代理商上班签到", notes="上班签到")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/workCheck", method = RequestMethod.POST)
  public JSONObject signed(@RequestBody JSONObject jsons){
    LogUtil.info("代理商 上班签到, jsons="+jsons.toJSONString());
   return httpRequestHandler.exchange(interfaceUrl+"oilCostRecord/workCheck", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
  });
  }
  /**
   * 
  * @Title: addHandshake 
  * @Description: TODO(获取昨日油补记录) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<JsonCustom>    返回类型 
  * @throws
   */
  @ApiOperation(value="获取昨日油补记录", notes="昨日油补记录")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/getYesterydayOilRecord",method = RequestMethod.POST)
 public JSONObject yesterdayOilRecord(@RequestBody JSONObject jsons){
    LogUtil.info("获取昨日油补记录, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"oilCostRecord/getYesterydayOilRecord", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
    });
   
 }
  /**
   * 
  * @Title: getHistoryOilRecord 
  * @Description: TODO(历史油补累计) 
  * @param @param jsons    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @ApiOperation(value="历史油补累计", notes="历史油补累计")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value ="/getHistoryOilRecord", method = RequestMethod.POST)
  public JSONObject  getHistoryOilRecord(@RequestBody JSONObject jsons){
    LogUtil.info("历史油补累计, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"oilCostRecord/getHistoryOilRecord", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
    });
  }

  /**
   * 
    * getTodayOilRecord:当日油补记录
    * @author robert 
    * @param jsons
    * @return 
    * @since JDK 1.8
   */
  @ApiOperation(value="当日油补记录", notes="当日油补记录")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/getTodayOilRecord", method = RequestMethod.POST)
  public JSONObject getTodayOilRecord(@RequestBody JSONObject jsons){
    LogUtil.info("当日油补记录, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"oilCostRecord/getTodayOilRecord", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
    });
      
  }
  
  
  /**
   * 
    * getHistoryDestOilRecord:历史油补详情
    * @author Administrator 
    * @param jsons
    * @return 
    * @since JDK 1.8
   */
  @ApiOperation(value="历史油补详情", notes="历史油补详情")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/getHistoryDestOilRecord", method = RequestMethod.POST)
  public JSONObject getHistoryDestOilRecord(@RequestBody JSONObject jsons){
    LogUtil.info("历史油补详情, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"oilCostRecord/getHistoryDestOilRecord", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
    });
  }
  
}
