package com.wangge.app.server;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.WebUtils;

import com.wangge.app.constant.AppInterface;
import com.wangge.app.util.LogUtil;
import com.wangge.app.util.RestTemplateUtil;
import com.wangge.app.util.StringUtil;

@RestController
@RequestMapping(value = "/v1/monthTask")
public class MonthTaskController {
  RestTemplate restTemplate = new RestTemplate();
  private String url = AppInterface.url + "monthTask/";
  
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
    return handleResult(sendRest("monthTaskMains/" + saleId, "get", null));
  }
  
  // 查询父区域下所有子区域
  @RequestMapping(value = "/regions/parentId/{regionId}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> findRegion(@PathVariable String regionId) {
    return handleResult(sendRest("regions/parentId/" + regionId, "get", null));
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
    return handleResult(sendRest("historydata/allShops", "get", params));
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
    return handleResult(sendRest("MonthTaskSubs", "post", talMap));
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
    return handleResult(sendRest("MonthTaskSubs", "get", searchParams));
  }
  
  @RequestMapping(value = "executions/{shopId}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> findActions(@PathVariable Long shopId) {
    return handleResult(sendRest("executions/" + shopId, "get", null));
  }
  
  /**
   * sendRest:发送RestTemplate的rest请求. <br/>
   * 
   * @author yangqc
   * @param methodUrl
   * @param restType
   * @param params
   * @return
   * @throws Exception 
   * @since JDK 1.8
   */
  private ResponseEntity<Map<String, Object>> sendRest(String methodUrl, String restType, Map<String, Object> params)  {
    try {
      return RestTemplateUtil.sendRestForMap(restTemplate, restType, url + methodUrl, params);
    } catch (Exception e) {
      LogUtil.error("路径"+url + methodUrl+"---请求方法:"+restType, e);
      Map<String,Object> repMap=new HashMap<>();
      repMap.put("msg", "数据服务器异常");
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  /**
   * handleResult:处理返回结果中包含错误信息的情况. <br/>
   * 
   * @author yangqc
   * @param responseEntitu
   * @return
   * @since JDK 1.8
   */
  private ResponseEntity<Map<String, Object>> handleResult(ResponseEntity<Map<String, Object>> responseEntitu) {
    Map<String, Object> remap = responseEntitu.getBody();
    if (null != remap.get("error")) {
      if ("1".equals(remap.get("code"))) {
        return new ResponseEntity<Map<String, Object>>(remap, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<Map<String, Object>>(remap, HttpStatus.NOT_FOUND);
    }
    return responseEntitu;
  }
}
