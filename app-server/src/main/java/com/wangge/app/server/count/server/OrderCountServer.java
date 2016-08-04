package com.wangge.app.server.count.server;

import java.util.Map;

public interface OrderCountServer {
    /** 
      * getOderCount:(这里用一句话描述这个方法的作用). <br/>      
      * @author yangqc 
      * @param userId
      * @param day
      * @return 
      * @since JDK 1.8 
      */  
    Map<String,Object> getOderCount(String userId, String day);
}
