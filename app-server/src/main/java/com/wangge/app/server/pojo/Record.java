package com.wangge.app.server.pojo;

import com.alibaba.fastjson.JSONArray;



public class Record {

  /** 
   * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
   * @since JDK 1.8 
   */ 
  private static final long serialVersionUID = 1L;
  
    private int type; //类型
    
    private String userId;
    
    private JSONArray content;     //内容 json

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }

    public JSONArray getContent() {
      return content;
    }

    public void setContent(JSONArray content) {
      this.content = content;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    
    
}
