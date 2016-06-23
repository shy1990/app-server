package com.wangge.app.server.listener;

import javax.annotation.Resource;



import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.event.AfterLongEvent;
import com.wangge.app.server.service.OilCostRecordService;
@Component
public class AfterLoginListener implements ApplicationListener<AfterLongEvent>{
  
  @Resource
  private OilCostRecordService oilCostRecordService;
  @Override
  public void onApplicationEvent(AfterLongEvent event) {
   JSONObject obj = new JSONObject();
    obj.put("userId", event.getUserId());
    obj.put("isPrimaryAccount", event.getIsPrimaryAccount());
    oilCostRecordService.signed(obj);
  }

}
