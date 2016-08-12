package com.wangge.app.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.config.http.HttpRequestHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/v1")
public class VisitTaskController {
  @Resource
  private HttpRequestHandler httpRequestHandler;
  @Value("${app-interface.url}")
  private String url;

  /**
   * @param @param  salesman
   * @param @return
   * @return ResponseEntity<List<Visit>>
   * @Description: 获取拜访任务列表
   * @author peter
   * @date 2015年12月11日
   * @version V2.0
   */
  @RequestMapping(value = "/task/{userId}/visitList", method = RequestMethod.POST)
  public ResponseEntity<Object> visitList(@PathVariable("userId") String userId, @RequestBody JSONObject jsons) {
    return httpRequestHandler.exchange(url + "/task/{userId}/visitList", HttpMethod.POST, null, jsons, userId);
  }

  /**
   * @param @param  visitId
   * @param @return
   * @param
   * @return 店铺名，图片链接，坐标，备注，摆放时间,状态(待定)
   * @Description: 拉取一条拜访记录
   * @author Peter
   * @date 2015年12月11日
   * @version V2.0
   * 添加拜访任务
   */
  @RequestMapping(value = "/task/addVisit", method = RequestMethod.POST)
  public ResponseEntity<Object> addVisit(String taskStart, String taskEnd, String rdid, String userid) {
    return httpRequestHandler.exchange(url + "/task/addVisit", HttpMethod.POST, null, taskStart, taskEnd, rdid, userid);
  }


  /**
   * 根据用户选择的拜访或已拜访进行处理
   *
   * @param visitId
   * @return
   */
  @RequestMapping(value = "/task/{visitId}/infoVisit", method = RequestMethod.GET)
  public ResponseEntity<Object> visitInfo(@PathVariable("visitId") String visitId) {
    return httpRequestHandler.exchange(url + "/task/{visitId}/infoVisit", HttpMethod.GET, null, null, visitId);
  }

  /**
   * @param @param  jsons
   * @param @return
   * @return ResponseEntity<Json>
   * @Description: 保存提交的拜访记录
   * @author peter
   * @date 2015年12月11日
   * @version V2.0
   */
  @RequestMapping(value = "/task/{userId}/addVisit", method = RequestMethod.POST)
  public ResponseEntity<Object> addVisit(@PathVariable("userId") String userId, @RequestBody JSONObject jsons) {
    return httpRequestHandler.exchange(url + "/task/{userId}/addVisit", HttpMethod.POST, null, jsons, userId);
  }

}


