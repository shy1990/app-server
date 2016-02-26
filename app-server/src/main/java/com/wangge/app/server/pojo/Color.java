package com.wangge.app.server.pojo;

public enum Color {
  
  gray(1), black(2), green(3),yellow(4);

  private Integer num;

  private Color(Integer num) {
    this.num = num;
  }

  public Integer getNum() {
    return num;
  }

}
