package com.wangge.app.server.pojo;


import java.util.List;




public class HistoryDestOilRecord extends message {

  /** 
   * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
   * @since JDK 1.8 
   */ 
  private static final long serialVersionUID = 1L;
  
    private List<HistoryOilRecord> Content;     //内容 油补记录数组

    public List<HistoryOilRecord> getContent() {
      return Content;
    }

    public void setContent(List<HistoryOilRecord> content) {
      Content = content;
    }

    


    
   



    
    

}
