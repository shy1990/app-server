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
  @JsonInclude(Include.NON_DEFAULT)
  private boolean enable;
  
  private int exception;
  @JsonInclude(Include.NON_EMPTY)
  private String status;
  
  public Date getSignTime() {
    return signTime;
  }
  public void setSignTime(Date signTime) {
    this.signTime = signTime;
  }
  public boolean isEnable() {
    return enable;
  }
  public void setEnable(boolean enable) {
    this.enable = enable;
  }
  public int getException() {
    return exception;
  }
  public void setException(int exception) {
    this.exception = exception;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  

}

