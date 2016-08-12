package com.wangge.app.server.controller;

import com.wangge.app.server.config.http.HttpRequestHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/v1/task/")
public class TaskController {

  @Resource
  private HttpRequestHandler httpRequestHandler;
  @Value("${app-interface.url}")
  private String url;

  @RequestMapping(value = "/findAllSaojie", method = RequestMethod.POST)
  public ResponseEntity<Object> findAllTask(String userid) {
    return httpRequestHandler.exchange(url + "/findAllSaojie", HttpMethod.POST, null, userid);
  }

  @RequestMapping(value = "/findTaskByUserId", method = RequestMethod.POST)
  public ResponseEntity<Object> findTaskByUserId(String userid) {
    return httpRequestHandler.exchange(url + "/findTaskByUserId", HttpMethod.POST, null, userid);
  }

  @RequestMapping(value = "/upstatus", method = RequestMethod.POST)
  public ResponseEntity<Object> upstatus(String taskid) {
    return httpRequestHandler.exchange(url + "/upstatus", HttpMethod.POST, null, taskid);
  }

}
