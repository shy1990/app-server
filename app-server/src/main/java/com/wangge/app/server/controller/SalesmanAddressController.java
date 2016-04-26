package com.wangge.app.server.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.entity.SalesmanAddress;
import com.wangge.app.server.pojo.MessageCustom;
import com.wangge.app.server.pojo.message;
import com.wangge.app.server.service.SalesmanAddressService;

/**
 * 
* @ClassName: SalesmanAddressController
* @Description: TODO()
* @author A18ccms a18ccms_gmail_com
* @date 2016年4月8日 上午11:07:54
*
 */
@RestController
@RequestMapping("/v1/salesmanAddress")
public class SalesmanAddressController {

  @Resource
  private SalesmanAddressService salesmanAddressService;
  
  /**
   * 
  * @Title: getSalesmanAddress 
  * @Description: TODO(获取业务员地址) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<SalesmanAddress>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/getSalesmanAddress", method = RequestMethod.POST)
  public ResponseEntity<MessageCustom> getSalesmanAddress(@RequestBody JSONObject jsons){
    
    return salesmanAddressService.getSalesmanAddress(jsons);
  }
  /**
   * 
  * @Title: addSalesmanAddress 
  * @Description: TODO(添加业务员地址) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<message>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/addSalesmanAddress", method = RequestMethod.POST)
  public ResponseEntity<MessageCustom> addSalesmanAddress(@RequestBody JSONObject jsons){
    return salesmanAddressService.addSalesmanAddress(jsons);
  }
   
}
