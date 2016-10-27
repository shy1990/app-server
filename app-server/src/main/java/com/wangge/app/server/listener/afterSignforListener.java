package com.wangge.app.server.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
@Component
public class afterSignforListener implements ApplicationListener<afterSignforEvent> {
  @Resource
  private OilCostRecordService oilCostRecordService;

  @Override
  public void onApplicationEvent(afterSignforEvent event) {
    try {
		oilCostRecordService.addHandshake(event.getUserId(),event.getCoordinates(),event.getIsPrimaryAccount(),event.getChildId(),event.getType(),event.getStorePhone());
	} catch (Exception e) {
		throw new  RuntimeException("记录油补异常   "+ e.getMessage());
	}
  }
  
  

}
