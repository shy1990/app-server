package com.wangge.app.server.monthtask.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.wangge.app.server.constant.AppInterface;
import com.wangge.app.server.util.LogUtil;
import com.wangge.app.server.util.RestTemplateUtil;

public class BaseController {
  @Autowired
  RestTemplate restTemplate ;
  public String url = AppInterface.url;
  
  @Autowired
  public AppInterface appInterface;
  
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
  public ResponseEntity<Map<String, Object>> sendRest(String methodUrl, String restType, Map<String, Object> params) {
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
  public ResponseEntity<Map<String, Object>> handleResult(ResponseEntity<Map<String, Object>> responseEntitu) {
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
