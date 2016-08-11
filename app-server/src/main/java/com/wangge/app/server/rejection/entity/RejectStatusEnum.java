package com.wangge.app.server.rejection.entity;

public enum RejectStatusEnum {
  overTime("0","已超时"),sendBack("1","待确认"),recGoods("2","已收货");

  private String name;
  private String value;
  RejectStatusEnum(String value, String name){
    this.name=name;
    this.value=value;
  }
  public String getName(){
    return name;
  }
  public String getValue(){
    return value;
  }
}
