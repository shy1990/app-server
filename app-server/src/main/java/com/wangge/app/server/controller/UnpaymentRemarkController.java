package com.wangge.app.server.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.entity.UnpaymentRemark;
import com.wangge.app.server.pojo.message;
import com.wangge.app.server.service.SalesmanService;
import com.wangge.app.server.service.UnpaymentRemarkService;
import com.wangge.app.server.util.LogUtil;

@RestController
@RequestMapping("/v1/ur")
public class UnpaymentRemarkController {
  private static Logger logger = Logger.getLogger(UnpaymentRemarkController.class);
  
  @Value("${app-interface.url}")
  private String interfaceUrl;
  
  @Resource
  private HttpRequestHandler httpRequestHandler;
  
  /**
   * 
  * @Title: createUnpaymentRemark 
  * @Description: TODO(创建未收款报备) 
  * @param @param ur
  * @param @param salesmanId    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @ApiOperation(value="创建未收款报备记录", notes="创建未收款报备记录")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/createRemark",method = RequestMethod.POST)
  public JSONObject createUnpaymentRemark(@RequestBody JSONObject jsons){
    LogUtil.info("创建未收款报备记录, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"ur/createRemark", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
    });
   
  }
  
 
  /**
   * 
  * @Title: getRemarkList 
  * @Description: TODO(根据业务员id查询此业务员的报备情况) 
  * @param @param pageable
  * @param @param salesmanId
  * @param @return    设定文件 
  * @return ResponseEntity<Page<UnpaymentRemark>>    返回类型 
  * @throws
   */
  @ApiOperation(value="根据业务员id查询此业务员的报备列表", notes="根据业务员id查询此业务员的报备列表")
  @ApiImplicitParam(name="jsons",value="jsons",required=true,dataType="JSONObject")
  @RequestMapping(value = "/getRemarkList",method = RequestMethod.POST)
  public  JSONObject getRemarkListBySalesmanId(@RequestBody JSONObject jsons){
    LogUtil.info("根据业务员id查询此业务员的报备列表, jsons="+jsons.toJSONString());
    return httpRequestHandler.exchange(interfaceUrl+"ur/getRemarkList", HttpMethod.POST, null, jsons, new ParameterizedTypeReference<JSONObject>() {
    });
  }

}
