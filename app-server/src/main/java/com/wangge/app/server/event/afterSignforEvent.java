package com.wangge.app.server.event;

import org.springframework.context.ApplicationEvent;

import com.alibaba.fastjson.JSONObject;
/**
 * 
* @ClassName: afterSignforEvent
* @Description: TODO(签收事件)
* @author A18ccms a18ccms_gmail_com
* @date 2016年4月16日 下午2:31:41
*
 */
public class afterSignforEvent extends ApplicationEvent {


  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;
  private String userId;
  private String coordinates;
  private  int isPrimaryAccount;
  private String  childId;
  private int  type;
  private String storePhone;
  public afterSignforEvent(String userId, String coordinates, int isPrimaryAccount, String childId, int type,String storePhone) {
    super("");
    this.userId = userId;
    this.coordinates = coordinates;
    this.isPrimaryAccount = isPrimaryAccount;
    this.childId = childId;
    this.type = type;
    this.storePhone = storePhone;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getCoordinates() {
    return coordinates;
  }
  public void setCoordinates(String coordinates) {
    this.coordinates = coordinates;
  }
  public int getIsPrimaryAccount() {
    return isPrimaryAccount;
  }
  public void setIsPrimaryAccount(int isPrimaryAccount) {
    this.isPrimaryAccount = isPrimaryAccount;
  }
  public String getChildId() {
    return childId;
  }
  public void setChildId(String childId) {
    this.childId = childId;
  }
  public int getType() {
    return type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public String getStorePhone() {
    return storePhone;
  }
  public void setStorePhone(String storePhone) {
    this.storePhone = storePhone;
  }
  
  

}
