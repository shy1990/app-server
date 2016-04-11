package com.wangge.app.server.pojo;

public class JsonCustom extends Json{

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;
  
  private String childName;
  
  private int isPrimaryAccount;
  
  private String childId;


  public String getChildName() {
    return childName;
  }

  public void setChildName(String childName) {
    this.childName = childName;
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
  
  

}
