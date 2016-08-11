package com.wangge.app.server.config.http;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.wangge.app.server.util.LogUtil;

@Configuration
public class HttpRequestHandler implements InitializingBean {

  @Resource(name = "restTemplate")
  private RestTemplate restTemplate;

  public HttpRequestHandler() {

  }

  public HttpRequestHandler(RestTemplate restTemplate) {
    if (this.restTemplate == null) {
      this.restTemplate = restTemplate;
    }
  }
  

  /**
   * test passed
   * @param url 请求地址
   * @param urlVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],
   * 则解析的url为api/1/2，使用Map参数时，遵循按key匹配
   * @return ResponseEntity
   */
  public ResponseEntity<Object> get(String url, HttpMethod method, Object... urlVariables) throws
   RuntimeException {
    return this.exchange(url, method, null, Object.class, urlVariables);
  }

  /**
   * test passed
   * @param url 请求地址
   * @param headers 请求头
   * @param urlVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],
   * 则解析的url为api/1/2，使用Map参数时，遵循按key匹配
   * @return ResponseEntity
   */
  public ResponseEntity<Object> get(String url, HttpHeaders headers, Object... urlVariables)
   throws RuntimeException {
//        System.out.println(headers);
    return this.exchange(url, HttpMethod.GET, headers, Object.class, urlVariables);
  }

  /**
   * test passed
   * @param url 请求地址
   * @param responseType 返回数据类型。例如: new ParameterizedTypeReference<Bean>(){}
   * @param headers 请求头
   * @param urlVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],
   * 则解析的url为api/1/2，使用Map参数时，遵循按key匹配
   * @return ResponseEntity.getBody()
   */
  public <T> T get(String url, ParameterizedTypeReference<T> responseType, HttpHeaders headers,
                   Object... urlVariables) throws RuntimeException {
    return this.exchange(url, HttpMethod.GET, headers, null, responseType, urlVariables);
  }

  /**
   * test passed
   * @param url 请求地址
   * @param responseType 返回数据类型。例如: new ParameterizedTypeReference<Bean>(){}
   * @param urlVariables url自动匹配替换的参数，如url为api/{a}/{b},参数为["1","2"],
   * 则解析的url为api/1/2，使用Map参数时，遵循按key匹配
   * @return ResponseEntity.getBody()
   */
  public <T> T get(String url, ParameterizedTypeReference<T> responseType, Object...
   urlVariables) throws RuntimeException {
    return this.exchange(url, HttpMethod.GET, null, null, responseType, urlVariables);
  }

  /**
   * responseType确定
   * @param url 请求地址
   * @param method 请求方式
   * @param headers 请求头
   * @param body 请求的数据
   * @param responseType 返回数据类型。例如: new ParameterizedTypeReference<Bean>(){}
   * @param uriVariables url自动匹配替换的参数。如url为api/{a}/{b},参数为["1","2"],
   * 则解析的url为api/1/2，使用Map参数时，遵循按key匹配
   * @return 根据responseType 转化后的结果对象。不包含HTTP请求的其他信息。
   */
  public <T> T exchange(String url, HttpMethod method, HttpHeaders headers, Object body,
                        ParameterizedTypeReference<T> responseType, Object... uriVariables)
   throws RuntimeException {

    Assert.notNull(url, "url不能为空!");
    Assert.notNull(method, "method不能为空!");

    printInfoLog(url, uriVariables, null);

    try {
      HttpEntity<?> requestEntity = new HttpEntity<>(body,headers);
      
      requestEntity = convert(requestEntity);

      if (uriVariables.length == 1 && uriVariables[0] instanceof Map) {
        Map<String, ?> _uriVariables = (Map<String, ?>) uriVariables[0];
        T t = restTemplate.exchange(url, method, requestEntity, responseType,
         _uriVariables).getBody();

        printInfoLog(null, null, t);

        return t;
      }

      T t = restTemplate.exchange(url, method, requestEntity, responseType,
       uriVariables).getBody();

      printInfoLog(null, null, t);

      return t;

    } catch (Exception e) {
      throw new RuntimeException("++++++++++++++HttpRequestHandler Exception" +
       "(使用返回<T> T 的exchange方法):", e);
    }
  }

  /**
   * 没有responseType
   * @param url 请求地址
   * @param method 请求方式
   * @param headers 请求头
   * @param body 请求的数据
   * @param uriVariables url自动匹配替换的参数。如url为api/{a}/{b},参数为["1","2"],
   * 则解析的url为api/1/2，使用Map参数时，遵循按key匹配
   * @return 直接返回responseEntity，包含http状态，http请求头等所有信息
   */
  public ResponseEntity<Object> exchange(String url, HttpMethod method, HttpHeaders headers,
                                         Object body, Object... uriVariables) throws
   RuntimeException {

    Assert.notNull(url, "url不能为空!");
    Assert.notNull(method, "method不能为空!");

    printInfoLog(url, uriVariables, null);

    try {
      HttpEntity<?> requestEntity = new HttpEntity<>(body, headers);

      requestEntity = convert(requestEntity);

      if (uriVariables.length == 1 && uriVariables[0] instanceof Map) {

        Map<String, ?> _uriVariables = (Map<String, ?>) uriVariables[0];

        ResponseEntity<Object> re = restTemplate.exchange(url, method, requestEntity,
         Object.class, _uriVariables);

        printInfoLog(null, null, re.getBody());

        return re;

      }

      ResponseEntity<Object> re = restTemplate.exchange(url, method, requestEntity, Object
       .class, uriVariables);

      printInfoLog(null, null, re.getBody());

      return re;
    } catch (Exception e) {
      throw new RuntimeException("++++++++++++++HttpRequestHandler Exception" +
       "(使用返回ResponseEntity(Object)的exchange方法):", e);
    }

  }

  /**
   * 对bean对象转表单模型做处理
   */
  private static HttpEntity<?> convert(HttpEntity<?> requestEntity) {
    Object body = requestEntity.getBody();
    HttpHeaders headers = requestEntity.getHeaders();

    if (body == null) {
      return requestEntity;
    }
    
    /*if(body instanceof JSONObject){
      return requestEntity;
    }
    
    
    if(body instanceof net.sf.json.JSONObject){
      return requestEntity;
    }*/

    if (body instanceof Map) {
      MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
      Map<String, ?> _body = (Map<String, ?>) body;
      for (String key : _body.keySet()) {
        multiValueMap.add(key, MapUtils.getString(_body, key));
      }

      requestEntity = new HttpEntity<>(multiValueMap, headers);
    }

    if (headers == null || !MediaType.APPLICATION_FORM_URLENCODED.equals(headers
     .getContentType())) {
      return requestEntity;
    }
    
    if (body instanceof String) {
      return requestEntity;
    }

    if (body instanceof Collection) {
      return requestEntity;
    }

    if (body instanceof Map) {
      return requestEntity;
    }

    MultiValueMap<String, Object> formEntity = new LinkedMultiValueMap<>();

    Field[] fields = body.getClass().getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      String name = fields[i].getName();
      String value = null;

      try {
        value = BeanUtils.getProperty(body, name);
      } catch (Exception e) {
        e.printStackTrace();
      }

      formEntity.add(name, value);
    }

    return new HttpEntity<>(formEntity, headers);
  }

  private void printInfoLog(String url, Object requestParams, Object resultObject) {

    if (StringUtils.isNotBlank(url)) {
      LogUtil.info(MessageFormat.format("{0}{1}", "++++++++++++++Request Url:", url));
    }

    if (requestParams != null) {
      LogUtil.info(MessageFormat.format("{0}{1}", "++++++++++++++Request Params:", JSONObject
       .toJSONString(requestParams, true)));
    }

    if (resultObject != null) {
      LogUtil.info(MessageFormat.format("{0}{1}", "++++++++++++++Request Result:", JSONObject
       .toJSONString(resultObject, true)));
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(restTemplate);
  }
}
