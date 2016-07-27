package com.wangge.app.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {
  @SuppressWarnings("unchecked")
  private static Class<? extends HashMap<String, Object>> mapClassz = (Class<? extends HashMap<String, Object>>) new HashMap<String, Object>()
      .getClass();
  private static Map<String,Class<?>> classMap=new HashMap<>();
  static{
    classMap.put("map", mapClassz);
  }
  /** 
    * sendRest:用RestTemplate进行rest请求的基础方法. <br/> 
    * @author yangqc 
    * @param restTemplate 引入的restTemplate实例
    * @param restType 执行的方法,支持get,post两种
    * @param url  请求url 
    * @param params  请求参数集合
    * @param returnType 返回类型
    * @return  
    * @since JDK 1.8 
    */  
  public static ResponseEntity<?> sendRest(RestTemplate restTemplate, String restType,String url,Map<String,Object>params,String returnType)throws Exception{
    if(restType.equals("get")){
      if(null==params){
        return restTemplate.getForEntity(url, classMap.get(returnType));
      }  
      return restTemplate.getForEntity(url+"?"+StringUtil.joinMap(params, "&"), classMap.get(returnType));
    }
    return restTemplate.postForEntity(url , params, classMap.get(returnType));
  }
  
  /**
   * 
   * 
   * @param methodUrl
   * @param restType
   * @param params
   * @param url
   * @return
   */
  public static  ResponseEntity<Map<String, Object>> sendRest(String methodUrl, String restType, Map<String, Object> params,String url)  {
	    try {
	      return RestTemplateUtil.sendRestForMap(new RestTemplate(), restType, url + methodUrl, params);
	    } catch (Exception e) {
	    	e.getMessage();
	      LogUtil.error("路径"+url + methodUrl+"---请求方法:"+restType, e);
	      Map<String,Object> repMap=new HashMap<>();
	      repMap.put("msg", "数据服务器异常");
	      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
  
  /**
   * 
   * @param restTemplate
   * @param restType
   * @param url
   * @param params
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static ResponseEntity<Map<String,Object>> sendRestForMap(RestTemplate restTemplate, String restType,String url,Map<String,Object>params)throws Exception{
    if(restType.equals("get")){
      if(null==params){
        return (ResponseEntity<Map<String, Object>>) restTemplate.getForEntity(url, classMap.get("map"));
      }  
      return (ResponseEntity<Map<String, Object>>) restTemplate.getForEntity(url+"?"+StringUtil.joinMap(params, "&"), classMap.get("map"));
    }
    return (ResponseEntity<Map<String, Object>>) restTemplate.postForEntity(url , params, classMap.get("map"));
  }
  
  
	
	  
}
