package com.wangge;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;







import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.AppServerApplication;
import com.wangge.app.server.config.http.HttpRequestHandler;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class httpTest {
  
  
  @Resource
  private HttpRequestHandler requestHandler;
  
  @Test
  public void test(){
    RestTemplate rest = new RestTemplate();
    MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
    param.add("userPhone", "18660856569");
    
    JSONObject jsonObject = new JSONObject();
    
    jsonObject.put("userPhone", "18660856569");
    
    
    HttpEntity<JSONObject> httpEntity =  new HttpEntity<JSONObject>(param);
    ResponseEntity<Object> responseEntity = rest.exchange("http://192.168.2.153:8080/v1/remind/getBussOrderList", HttpMethod.POST, httpEntity,Object.class); 
    System.out.println(".........>>>>>>>>>>"+responseEntity.getBody());
  }
  
  @Test
  public void test2() {
    JSONObject jsonObject = new JSONObject();//{"salesmanId":"A37130205100","pageNum":"1","pageSize":"10"}
    
    jsonObject.put("userPhone", "18660856569");
    
    MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
    param.add("userPhone", "18660856569");
    
   /* requestHandler.get("http://192.168.2.153:8080/v1/remind/getBussOrderList",
        HttpMethod.POST, jsonObject);*/
    /*JSONObject responseEntity = requestHandler.exchange("http://192.168.2.153:8080/v1/remind/getBussOrderList", HttpMethod.POST, null, jsonObject, JSONObject.class,"");*/
    HttpHeaders headers = new HttpHeaders(); 
	headers.setContentType(MediaType.APPLICATION_JSON);
    JSONObject responseEntitys = requestHandler.exchange("http://192.168.2.151:8080/v1/remind/getBussOrderList", HttpMethod.POST, headers, param,new ParameterizedTypeReference<JSONObject>(){}, param);
   // JSONObject rs=		requestHandler.exchange("http://192.168.2.151:8080/v1/remind/getBussOrderList", HttpMethod.POST, null, jsonObject, new ParameterizedTypeReference<JSONObject>(){});
  
    System.out.println(".........>>>>>>>>>>"+responseEntitys.toJSONString());
  
  }
  
  @Test
  public void test3() {
    JSONObject jsonObject = new JSONObject();//{"salesmanId":"A37130205100","pageNum":"1","pageSize":"10"}
    
    jsonObject.put("salesmanId", "A37130205100");
    jsonObject.put("pageNum", "1");
    jsonObject.put("pageSize", "10");
    
    
    /*requestHandler.get("http://192.168.2.153:8080/v1/remind/getBussOrderList",
        HttpMethod.POST, jsonObject);*/
   // JSONObject responseEntity = requestHandler.exchange("http://192.168.2.153:8080/v1/remind/getBussOrderList", HttpMethod.POST, null, jsonObject,new ParameterizedTypeReference<JSONObject>(){});
    
    JSONObject responseEntitys = requestHandler.exchange("http://192.168.2.153:8080/v1/ur/getRemarkList", HttpMethod.POST, null, jsonObject, new ParameterizedTypeReference<JSONObject>(){});
  
    System.out.println(".........>>>>>>>>>>"+responseEntitys.toJSONString());
  
  }
}