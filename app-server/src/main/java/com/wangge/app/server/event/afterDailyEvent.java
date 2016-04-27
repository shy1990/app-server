package com.wangge.app.server.event;

import org.springframework.context.ApplicationEvent;

import com.alibaba.fastjson.JSONObject;
/**
 * 
* @ClassName: afterDailyEvent
* @Description: TODO(业务员 日常 扫街，注册，拜访事件类)
* @author A18ccms a18ccms_gmail_com
* @date 2016年4月16日 下午3:19:40
*
 */
public class afterDailyEvent extends ApplicationEvent {


  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;
  
  private String regionId;
  private String userId;
  private String shopName;
  
  private String coordinates;
  private int isPrimaryAccount;
  private String childId;
  private int type;
  
  public afterDailyEvent(String regionId,String userId,String shopName,String coordinates, int isPrimaryAccount,String childId,int type) {
    super("");
    this.regionId = regionId;
    this.userId = userId;
    this.shopName = shopName;
    this.coordinates = coordinates;
    this.isPrimaryAccount = isPrimaryAccount;
    this.childId = childId;
    this.type = type;
  }


  public String getRegionId() {
    return regionId;
  }

  public String getUserId() {
    return userId;
  }

  public String getShopName() {
    return shopName;
  }

  public String getCoordinates() {
    return coordinates;
  }

  public int getIsPrimaryAccount() {
    return isPrimaryAccount;
  }

  public String getChildId() {
    return childId;
  }

  public int getType() {
    return type;
  }

  

}
