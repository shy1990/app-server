package com.wangge.app.server.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.wangge.app.server.event.afterDailyEvent;
import com.wangge.app.server.service.OilCostRecordService;
/**
 * 
* @ClassName: afterDailyListener
* @Description: TODO(业务员日常 扫街，注册，拜访监听)
* @author A18ccms a18ccms_gmail_com
* @date 2016年4月16日 下午3:21:41
*
 */
@Component
public class afterDailyListener implements ApplicationListener<afterDailyEvent> {

  @Resource
  private OilCostRecordService oilCostRecordService;
  @Override
  public void onApplicationEvent(afterDailyEvent event) {
  // JSONObject json = (JSONObject)event.;
   oilCostRecordService.addHandshake(event.getRegionId(),event.getUserId(),event.getShopName(),event.getCoordinates(),event.getIsPrimaryAccount(),event.getChildId(),event.getType());
  }

}
