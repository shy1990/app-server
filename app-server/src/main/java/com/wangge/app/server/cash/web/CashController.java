package com.wangge.app.server.cash.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.config.http.HttpRequestHandler;
import com.wangge.app.server.pojo.CashPart;
import com.wangge.app.server.service.CashService;
import com.wangge.app.server.util.JsonResponse;
import com.wangge.app.server.util.LogUtil;

@RestController
@RequestMapping("/v1/cash")
public class CashController {
 
  @Autowired
  private HttpRequestHandler hrh;
  
  @Value("${app-interface.url}")
  private String APP_INTERFACE_URL;
  
  @Autowired
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
      List<CashPart> cashlist = hrh.get(APP_INTERFACE_URL+"cash/{userId}",new ParameterizedTypeReference<List<CashPart>>(){}, userId);
//          cashService.findByUserId(userId);
      if(CollectionUtils.isNotEmpty(cashlist)){
        cashJson.setResult(cashlist);
        cashJson.setSuccessMsg("操作成功");
      }else{
        cashJson.setResult(null);
        cashJson.setSuccessMsg("未查到相关记录");
      }
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
    }
    return new ResponseEntity<>(cashJson,HttpStatus.OK);
  }
  @RequestMapping(value = "/{userId}",method = RequestMethod.POST)
  public ResponseEntity<JsonResponse<String>> OverCash(@PathVariable("userId") String userId,
      @RequestParam(required=false) String cashIds){
    JsonResponse<String> json=new JsonResponse<>();
    try {
//      ResponseEntity<Object> =hrh.exchange(APP_INTERFACE_URL+"cash/{userId}", HttpMethod.POST, null, null, userId);
      
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
