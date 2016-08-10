package com.wangge.app.server.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 描述: TODO: 包名: com.wangge.app.util. 作者: barton. 日期: 16-7-23. 项目名称:
 * app-interface 版本: 1.0 JDK: since 1.8
 */
public class StringUtil extends StringUtils {
  /**
   * joinMap:map容器的自定义输出;目的:解决RestTemplate中get方法的url问题  <br/>
   * 简单的测试方法
   * <code>
   * @Test
  public void testMap() {
    Map<String, Object> remap = new HashMap<String, Object>();
    remap.put("sd", "low");
    remap.put("js", "lower");
    System.out.println(joinMap(remap, "&"));
  }
   * </code>
   * 
   * @author yangqc
   * @param map 要格式化输出的Map
   * @param separator风格符
   * @return
   * @since JDK 1.8
   */
  public static String joinMap(Map<?, ?> map, final String separator) {
    String result = "";
    for (Map.Entry<?, ?> entry : map.entrySet()) {
      result += entry.getKey().toString() + "=" + entry.getValue().toString() + separator;
    }
    return result.substring(0, result.length() - separator.length());
  }
  
    
}
