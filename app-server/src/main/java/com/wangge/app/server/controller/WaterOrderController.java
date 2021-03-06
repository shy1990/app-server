package com.wangge.app.server.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.pojo.WaterOrderPart;
import com.wangge.app.server.service.WaterOrderService;
import com.wangge.app.util.JsonResponse;

@RestController
@RequestMapping("/v1/waterOrder")
public class WaterOrderController {
  private static final Logger logger = Logger.getLogger(WaterOrderController.class);
  
  private static final String SEARCH_OPERTOR="sc_";
  
  @Resource
  private WaterOrderService waterOrderService;
 
  /**
   * 结算后流水单号列表
   * @param request
   * @param userId
   * @return
   */
  @RequestMapping(value = "/{userId}" ,method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<JsonResponse<Page<WaterOrderPart>>> getWaterOrderList(@PathVariable("userId") String userId,
      @PageableDefault(page=0,size=10,sort={"payStatus","createDate"},direction=Direction.DESC) Pageable pageable,
      HttpServletRequest request){
    //
    JsonResponse<Page<WaterOrderPart>> waterOrderJson=new JsonResponse<>();
    try {
      List<Order> orders=new ArrayList<>();
      orders.add(new Order(Direction.ASC,"payStatus"));
      orders.add(new Order(Direction.DESC,"createDate"));
      Sort sort=new Sort(orders);
      pageable=new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
      Page<WaterOrderPart> cashlist=waterOrderService.findByUserId(userId,pageable);
      if(cashlist.getContent().size()>0){
        waterOrderJson.setResult(cashlist);
        waterOrderJson.setSuccessMsg("操作成功");
      }else{
        waterOrderJson.setResult(null);
        waterOrderJson.setSuccessMsg("未查到相关记录");
      }
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return new ResponseEntity<>(waterOrderJson,HttpStatus.OK);
  }
  /**
   * 流水单详情
   * @param request
   * @param userId
   * @return
   */
  @RequestMapping(value = "" ,method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<JsonResponse<WaterOrderPart>> getCsahList(@RequestParam(value="seriaNo") String seriaNo,
      HttpServletRequest request){
    //
    JsonResponse<WaterOrderPart> waterOrderJson=new JsonResponse<>();
    try {
      
      WaterOrderPart wop=waterOrderService.findBySerailNo(seriaNo);
      if(wop!=null){
        waterOrderJson.setResult(wop);
        waterOrderJson.setSuccessMsg("操作成功");
      }else{
        waterOrderJson.setResult(null);
        waterOrderJson.setSuccessMsg("未查到相关记录");
      }
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return new ResponseEntity<>(waterOrderJson,HttpStatus.OK);
  }

 
  

}
