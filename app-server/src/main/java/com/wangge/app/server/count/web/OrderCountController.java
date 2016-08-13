package com.wangge.app.server.count.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.wangge.app.server.monthtask.web.BaseController;


@RestController
@RequestMapping("/v1/orderCount")
public class OrderCountController extends BaseController {
  String controllerUrl = "orderCount/";
  
  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> getOderCount(@PathVariable String userId, HttpServletRequest request) {
    Map<String, Object> params = WebUtils.getParametersStartingWith(request, "");
    return handleResult(sendRest(controllerUrl + userId, "get", params));
  }
  
}
