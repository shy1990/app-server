package com.wangge.app.server.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;

import com.wangge.app.server.event.afterSignforEvent;
import com.wangge.app.server.service.OilCostRecordService;
/**
 * 
* @ClassName: afterSignforListener
* @Description: TODO(签收监听)
* @author A18ccms a18ccms_gmail_com
* @date 2016年4月16日 下午2:59:48
*
 */
public class afterSignforListener implements ApplicationListener<afterSignforEvent> {
  @Resource
  private OilCostRecordService oilCostRecordService;

  @Override
  public void onApplicationEvent(afterSignforEvent event) {
    oilCostRecordService.addHandshake(event.getUserId(),event.getCoordinates(),event.getIsPrimaryAccount(),event.getChildId(),event.getType(),event.getStorePhone());
  }
  
  

}
