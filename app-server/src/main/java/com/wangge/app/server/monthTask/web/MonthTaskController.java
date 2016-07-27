package com.wangge.app.server.monthTask.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping(value = "/v1/monthTask")
public class MonthTaskController extends BaseController{
  private static final String  controllerUrl="monthTask/";
  /**
   * findAllTask: // 查询月任务详情. <br/>
   * 
   * @author yangqc
   * @param saleId
   * @return
   * @since JDK 1.8
   */
  @RequestMapping(value = "/monthTaskMains/{saleId}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> findAllTask(@PathVariable String saleId) {
    return handleResult(sendRest(controllerUrl+"monthTaskMains/" + saleId, "get", null));
  }
  
  // 查询父区域下所有子区域
  @RequestMapping(value = "/regions/parentId/{regionId}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> findRegion(@PathVariable String regionId) {
    return handleResult(sendRest(controllerUrl+"regions/parentId/" + regionId, "get", null));
  }
  
  /**
   * findShopData:查询某区域下某个查询标准的已设置任务和未设置的店铺数据信息 <br/>
   * 
   * @author yangqc
   * @param request
   * @return
   * @since JDK 1.8
   */
  @RequestMapping(value = "/historydata/allShops", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> findShopData(HttpServletRequest request) {
    Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
    return handleResult(sendRest(controllerUrl+"historydata/allShops", "get", params));
  }
  
  /**
   * 新增或取消子任务 saveMonthTaskSub:. <br/>
   * 
   * @author yangqc
   * @param talMap
   * @return
   * @since JDK 1.8
   */
  @RequestMapping(value = "/MonthTaskSubs", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> saveMonthTaskSub(@RequestBody Map<String, Object> talMap) {
    return handleResult(sendRest(controllerUrl+"MonthTaskSubs", "post", talMap));
  }
  
  /**
   * findMonthTaskSub:查询子任务详情. <br/>
   * SC_LK_regionId
   * 
   * @author yangqc
   * @param request
   * @param pageRequest
   * @return
   * @since JDK 1.8
   */
  @RequestMapping(value = "/MonthTaskSubs", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> findMonthTaskSub(HttpServletRequest request, Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, "");
    return handleResult(sendRest(controllerUrl+"MonthTaskSubs", "get", searchParams));
  }
  
  /** 
    * findActions:查询某一店铺月任务执行情况. <br/> 
    * @author yangqc 
    * @param shopId
    * @return 
    * @since JDK 1.8 
    */  
  @RequestMapping(value = "executions/{shopId}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> findActions(@PathVariable Long shopId) {
    return handleResult(sendRest(controllerUrl+"executions/" + shopId, "get", null));
  }
  
 
}
