package com.wangge.app.server.pojo;

import java.util.List;



public class TodayOilRecord extends message {

  /** 
   * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
   * @since JDK 1.8 
   */ 
  private static final long serialVersionUID = 1L;
  
    private String oilCostYesterday; //昨日油补费用
    
    private String oilCostMonth;     //当月累计费用
    
    
    private List<Record> oilRecord;     //内容 json

    public String getOilCostYesterday() {
      return oilCostYesterday;
    }


    public void setOilCostYesterday(String oilCostYesterday) {
      this.oilCostYesterday = oilCostYesterday;
    }


    public String getOilCostMonth() {
      return oilCostMonth;
    }


    public void setOilCostMonth(String oilCostMonth) {
      this.oilCostMonth = oilCostMonth;
    }


    public List<Record> getOilRecord() {
      return oilRecord;
    }


    public void setOilRecord(List<Record> oilRecord) {
      this.oilRecord = oilRecord;
    }




    
    

}
