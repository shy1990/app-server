package com.wangge;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

//git.oschina.net/sylarlove/app-server.git
import com.wangge.common.web.client.HmacRestTemplet;
import com.wangge.common.web.client.HmacRestTemplet.HttpClientOption;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class AppServerApplicationTests {
	@Autowired
//	private RegionRepository rp;

	@Test
	public void contextLoads() {
		RestTemplate rt = new HmacRestTemplet("zhangsan", "zhangsan", HttpClientOption.ENABLE_REDIRECTS);
		ResponseEntity obj = rt.getForEntity("http://localhost:8080/test?test={test}", JSONObject.class, "hello");
		System.out.println(obj.getBody().toString());

	}

}
