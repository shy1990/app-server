package com.wangge.app.server.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName: AppInterface <br/>
 * Function: 动态获取app-Interface地址. <br/>
 * date: 2016年7月23日 上午10:41:09 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Component
@ConfigurationProperties(prefix = "app-interface")
public class AppInterface {
  // 暂时不支持
  public static String url;
//  ="http://192.168.2.179:8080/v1/";
  
  public static String getUrl() {
    return url;
  }
  
  public static void setUrl(String url) {
    AppInterface.url = url;
  }
  
  public AppInterface() {
  }
  
}
