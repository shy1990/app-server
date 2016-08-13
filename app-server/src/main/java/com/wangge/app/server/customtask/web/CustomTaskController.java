package com.wangge.app.server.customtask.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wangge.app.server.monthtask.web.BaseController;

@Controller
@RequestMapping(value = "/v1/customTask")
public class CustomTaskController extends BaseController{
  private static final String  controllerUrl ="customTask/";
  
  // 查询月任务详情
  @RequestMapping(value = "/{salesmanId}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> findAllTask(HttpServletRequest request, @PathVariable String salesmanId,
      Pageable pageRequest) {
    return handleResult(sendRest(controllerUrl+salesmanId, "get", null));
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
        sendRest(controllerUrl+"detail/" + customTaskId + "/" + salesmanId, "get", null));
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
    return handleResult(sendRest(controllerUrl+"message", "post", params));
  }
 
}
