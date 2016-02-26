package com.wangge.app.server.vo;

public class RegistAreaVo {

  private String id ;
  private String name;
  private String coordinates;
  private int colorStatus;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getCoordinates() {
    return coordinates;
  }
  public void setCoordinates(String coordinates) {
    this.coordinates = coordinates;
  }
  public int getColorStatus() {
    return colorStatus;
  }
  public void setColorStatus(int colorStatus) {
    this.colorStatus = colorStatus;
  }
  
}
