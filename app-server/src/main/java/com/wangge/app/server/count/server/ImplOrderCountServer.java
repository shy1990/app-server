package com.wangge.app.server.count.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.app.server.count.domain.OrderCount;
import com.wangge.app.server.util.DateUtil;

@Service
public class ImplOrderCountServer implements OrderCountServer {
  @Autowired
  OrderCount ordercount;
  
  private Log log = LogFactory.getLog(this.getClass());
  
  @Override
  @Transactional
  public Map<String, Object> getOderCount(String userId, String day) {
    if (day.equals("")) {
      day = DateUtil.date2String(new Date());
      String month = day.substring(0, 7);
      Map<String, Object> countMap = ordercount.countByDayAndMonth(userId, day, month);
      countMap.put("month", Integer.valueOf(month.substring(6, 7)));
      return countMap;
    } else {
      return ordercount.countByDay(userId, day);
    }
  }
  
}
