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
  private Date creatTime;
  private String code;
  
  public Date getCreatTime() {
    return creatTime;
  }
  public void setCreatTime(Date creatTime) {
    this.creatTime = creatTime;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  
  

}
