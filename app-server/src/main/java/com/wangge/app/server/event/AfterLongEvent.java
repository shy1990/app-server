package com.wangge.app.server.event;

import org.springframework.context.ApplicationEvent;

public class AfterLongEvent extends ApplicationEvent {

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;
  
  private String userId;
  private int isPrimaryAccount;

  public AfterLongEvent(String userId, int isPrimaryAccount) {
    super("");
    this.userId = userId;
    this.isPrimaryAccount = isPrimaryAccount;
  }
  
 
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }


  public int getIsPrimaryAccount() {
    return isPrimaryAccount;
  }


  public void setIsPrimaryAccount(int isPrimaryAccount) {
    this.isPrimaryAccount = isPrimaryAccount;
  }

}
