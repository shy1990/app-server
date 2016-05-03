package com.wangge.app.server.pojo;

import java.util.List;
import java.util.Map;



public class HistoryOilRecord extends message {

  /** 
   * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
   * @since JDK 1.8 
   */ 
  private static final long serialVersionUID = 1L;
  
    private int dateDay; //当月日期
    
    private String distance;//距离
    
    private String oilCost;//油补费用
    private List<Object> fatherContent;//父账号内容
    
    private List<Map<Object, Object>> childContents;//子账号内容
    
    private List<Record> oilRecord;     //内容 json

    public int getDateDay() {
      return dateDay;
    }

    public void setDateDay(int dateDay) {
      this.dateDay = dateDay;
    }

    public String getDistance() {
      return distance;
    }

    public void setDistance(String distance) {
      this.distance = distance;
    }

    public String getOilCost() {
      return oilCost;
    }

    public void setOilCost(String oilCost) {
      this.oilCost = oilCost;
    }

    public List<Object> getFatherContent() {
      return fatherContent;
    }

    public void setFatherContent(List<Object> fatherContent) {
      this.fatherContent = fatherContent;
    }

    
  


    public List<Map<Object, Object>> getChildContents() {
      return childContents;
    }

    public void setChildContents(List<Map<Object, Object>> childContents) {
      this.childContents = childContents;
    }

    public List<Record> getOilRecord() {
      return oilRecord;
    }

    public void setOilRecord(List<Record> oilRecord) {
      this.oilRecord = oilRecord;
    }

   

}
