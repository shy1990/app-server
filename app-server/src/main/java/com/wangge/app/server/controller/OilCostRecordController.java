package com.wangge.app.server.controller;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.pojo.JsonCustom;
import com.wangge.app.server.pojo.MessageCustom;
import com.wangge.app.server.pojo.message;
import com.wangge.app.server.service.OilCostRecordService;


/**
 * 
* @ClassName: TrackController
* @Description: TODO(油补类)
* @author A18ccms a18ccms_gmail_com
* @date 2016年3月30日 上午10:43:30
*
 */

@RestController
@RequestMapping("/oilCostRecord")
public class OilCostRecordController {
  
  private static final Logger logger =  LoggerFactory.getLogger(OilCostRecordController.class); // NOPMD by Administrator on 16-3-30 下午5:43
  @Resource
  private OilCostRecordService trackService;
  
  
  @RequestMapping(value = "/workCheck", method = RequestMethod.POST)
  public ResponseEntity<MessageCustom> signed(@RequestBody JSONObject jsons){
  //  message m = trackService.addHandshake(jsons);
   // ResponseEntity<message> ms = trackService.signed(jsons);
    
    return trackService.signed(jsons);
  }
  /**
   * 
  * @Title: addHandshake 
  * @Description: TODO(添加一个握手点) 
  * @param @param jsons
  * @param @return    设定文件 
  * @return ResponseEntity<JsonCustom>    返回类型 
  * @throws
   */
 /* @RequestMapping(name = "/addHandshake", method = RequestMethod.POST)
  public ResponseEntity<JsonCustom> addHandshake(@RequestBody JSONObject jsons){
    JsonCustom json = new JsonCustom();
   try {
     trackService.addHandshake(jsons);
     json.setMsg("保存成功！");
  } catch (Exception e) {
    // TODO: handle exception
    e.printStackTrace();
    if(logger.isErrorEnabled()){
      logger.error("addHandshake() error  curr :" + e);
    }
    json.setMsg("保存失败！");
  }
    return new ResponseEntity<JsonCustom>(json,HttpStatus.CREATED);
  }*/

}
