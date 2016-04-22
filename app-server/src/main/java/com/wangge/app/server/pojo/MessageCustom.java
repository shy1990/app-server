package com.wangge.app.server.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class MessageCustom extends message {
  
  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")
  private Date signTime;
  private int code;
  @JsonInclude(Include.NON_DEFAULT)
  private boolean enable;
  
  public Date getSignTime() {
    return signTime;
  }
  public void setSignTime(Date signTime) {
    this.signTime = signTime;
  }
  public int getCode() {
    return code;
  }
  public void setCode(int code) {
    this.code = code;
  }
  public boolean isEnable() {
    return enable;
  }
  public void setEnable(boolean enable) {
    this.enable = enable;
  }
  
  
  

}
