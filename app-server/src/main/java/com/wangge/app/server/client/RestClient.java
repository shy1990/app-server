package com.wangge.app.server.client;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wangge.AppServerApplication;
import com.wangge.common.web.client.HmacRestTemplet;
import com.wangge.common.web.client.HmacRestTemplet.HttpClientOption;

@RestController
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class RestClient {
//	 private final static String url = "http://localhost:8080/";
	
//	 public String test(String content) {
//		 RestTemplate rt=new HmacRestTemplet("zhangsan","zhangsan",HttpClientOption.ENABLE_REDIRECTS);
//		 ResponseEntity<JSONObject> obj=rt.getForEntity(url+"test?test={test}", JSONObject.class, content);
//	        return obj.getBody().toString();
//	 }
	
		@Test
		public void contextLoads() {
			RestTemplate rt=new HmacRestTemplet("zhangsan","zhangsan",HttpClientOption.ENABLE_REDIRECTS);
//			ResponseEntity obj=rt.getForEntity("http://localhost:8080/test?test={test}&str={str}", JSONObject.class, "hello","hi");
			ResponseEntity<JSONObject> obj=rt.getForEntity("http://localhost:8080/pushNewOrder?state={state}&info={info}",  JSONObject.class, "lisi","lisi");
			System.out.println(obj.getBody().toString());
			
		}

}
