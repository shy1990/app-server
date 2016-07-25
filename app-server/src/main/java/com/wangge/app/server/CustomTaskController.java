package com.wangge.app.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.wangge.app.constant.AppInterface;
import com.wangge.app.server.customTask.entity.CustomMessages;
import com.wangge.app.server.customTask.entity.CustomTask;
import com.wangge.app.server.customTask.server.CustomTaskServer;
import com.wangge.app.server.customTask.server.ImplCustomTaskServe;
import com.wangge.app.server.util.DateUtil;
import com.wangge.app.util.LogUtil;
import com.wangge.app.util.RestTemplateUtil;

@Controller
@RequestMapping(value = "/v1/customTask")
public class CustomTaskController {
  RestTemplate restTemplate = new RestTemplate();
  private String url = AppInterface.url + "customTask/";
  
  // 查询月任务详情
  @RequestMapping(value = "/{salesmanId}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> findAllTask(HttpServletRequest request, @PathVariable String salesmanId,
      Pageable pageRequest) {
    return handleResult(sendRest(salesmanId, "get", null));
  }
  
  /**
   * 跳转单条记录详情
   * 
   * @param customTask
   * @param request
   * @param model
   * @return
   */
  @RequestMapping(value = "/detail/{id}/{salesmanId}", method = RequestMethod.GET)
  public String detail(@PathVariable("id") String customTaskId, @PathVariable("salesmanId") String salesmanId,
      HttpServletRequest request, Model model) {
    ResponseEntity<Map<String, Object>> rEntity = handleResult(
        sendRest("detail/" + customTaskId + "/" + salesmanId, "get", null));
    if (!rEntity.getStatusCode().equals(HttpStatus.OK)) {
      return "远程服务器出错,请与管理员联系";
    }
    model.addAllAttributes(rEntity.getBody());
    return "customTask/detail";
  }
  
  /**
   * 保存新建的消息记录
   * 
   * @param customTask
   * @return
   */
  @RequestMapping(value = "/message", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> createSub(@RequestBody Map<String, Object> params) {
    return handleResult(sendRest("message", "post", params));
  }
  
  /**
   * sendRest:发送RestTemplate的rest请求. <br/>
   * 
   * @author yangqc
   * @param methodUrl
   * @param restType
   * @param params
   * @return
   * @since JDK 1.8
   */
  private ResponseEntity<Map<String, Object>> sendRest(String methodUrl, String restType, Map<String, Object> params) {
    try {
      return RestTemplateUtil.sendRestForMap(restTemplate, restType, url + methodUrl, params);
    } catch (Exception e) {
      LogUtil.error("路径" + url + methodUrl + "---请求方法:" + restType, e);
      Map<String, Object> repMap = new HashMap<>();
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
