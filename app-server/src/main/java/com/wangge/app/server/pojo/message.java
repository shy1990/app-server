package com.wangge.app.server.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_EMPTY)
public class message implements Serializable{

  private static final long serialVersionUID = 1L;
  private Boolean success;//是否成功
  private String msg;
  private Object obj;
  
  public Boolean getSuccess() {
    return success;
  }
  public void setSuccess(Boolean success) {
    this.success = success;
  }
  public String getMsg() {
    return msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public Object getObj() {
    return obj;
  }
  public void setObj(Object obj) {
    this.obj = obj;
  }
  
  
}
