package com.wangge.app.server.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.wangge.app.server.event.afterSalesmanSignforEvent;
import com.wangge.app.server.service.OilCostRecordService;
/**
 * 
* @ClassName: afterSignforListener
* @Description: TODO(签收监听)
* @author A18ccms a18ccms_gmail_com
* @date 2016年4月16日 下午2:59:48
*
 */
@Component
public class afterSalesmanSignforListener implements ApplicationListener<afterSalesmanSignforEvent> {
  @Resource
  private OilCostRecordService oilCostRecordService;

  @Override
  public void onApplicationEvent(afterSalesmanSignforEvent event) {
    oilCostRecordService.addHandshake(event.getUserId(),event.getCoordinates(),event.getIsPrimaryAccount(),event.getChildId(),event.getType());
  }
  
  

}
