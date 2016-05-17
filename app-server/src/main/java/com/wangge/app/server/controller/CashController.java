package com.wangge.app.server.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.entity.Cash;
import com.wangge.app.server.pojo.CashPart;
import com.wangge.app.server.service.CashService;
import com.wangge.app.util.JsonResponse;
import com.wangge.app.util.JsonResponse.Status;

@RestController
@RequestMapping("/v1/cash")
public class CashController {
  
  private static final Logger logger = Logger.getLogger(CashController.class);
  @Resource
  private CashService cashService;
 
  /**
   * 现金订单购物车
   * @param request
   * @param userId
   * @return
   */
  @RequestMapping(value = "/{userId}" ,method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<JsonResponse<List<CashPart>>> getCsahList(HttpServletRequest request,
      @PathVariable("userId") String userId){
    //
    JsonResponse<List<CashPart>> cashJson=new JsonResponse<>();
    try {
      
      List<CashPart> cashlist=cashService.findByUserId(userId);
      if(cashlist.size()>0){
        cashJson.setResult(cashlist);
        cashJson.setSuccessMsg("操作成功");
      }else{
        cashJson.setResult(null);
        cashJson.setSuccessMsg("未查到相关记录");
      }
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return new ResponseEntity<>(cashJson,HttpStatus.OK);
  }
  @RequestMapping(value = "/{userId}",method = RequestMethod.POST)
  public ResponseEntity<JsonResponse<String>> OverCash(@PathVariable("userId") String userId,
      @RequestParam(required=false) String cashIds){
    JsonResponse<String> json=new JsonResponse<>();
    try {
      boolean msg=cashService.cashToWaterOrder(userId);
      if(msg){
        json.setSuccessMsg("结算成功");
        return new ResponseEntity<>(json,HttpStatus.OK);
      }
        json.setErrorMsg("操作失败");
      
    } catch (Exception e) {
      // TODO: handle exception
    }
    return new ResponseEntity<>(json,HttpStatus.OK);
  }
 
  

}
