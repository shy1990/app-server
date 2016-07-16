package com.wangge.app.server.count;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.app.server.count.server.OrderCountServer;

@RestController
@RequestMapping("/v1/orderCount")
public class OrderCountController {
  @Autowired
  OrderCountServer orderServer;
  
  private Log log = LogFactory.getLog(this.getClass());
  
  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> getOderCount(@PathVariable String userId, HttpServletRequest request) {
    Map<String, Object> countMap = new HashMap<>();
    try {
      Long t1 = System.nanoTime();
      String day = request.getParameter("countDay") == null ? "" : request.getParameter("countDay");
      countMap = orderServer.getOderCount(userId, day);
      long consumer = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t1);
      System.out.println(String.format("search  took %d ms", consumer));
    } catch (Exception e) {
      e.printStackTrace();
      log.debug(e);
    }
    return new ResponseEntity<Map<String, Object>>(countMap, HttpStatus.OK);
  }
}
