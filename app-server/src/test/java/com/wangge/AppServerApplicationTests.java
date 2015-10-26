package com.wangge;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.wangge.app.server.entity.Salesman;
import com.wangge.app.server.pojo.Json;
import com.wangge.app.server.repository.SalesmanRepository;
import com.wangge.common.entity.User;
import com.wangge.common.web.client.HmacRestTemplet;
import com.wangge.common.web.client.HmacRestTemplet.HttpClientOption;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class AppServerApplicationTests {
	@Resource
	private SalesmanRepository salesmanRepository;
	
	@Test
	public void contextLoads() {
		RestTemplate rt=new HmacRestTemplet("zhangsan","zhangsan",HttpClientOption.ENABLE_REDIRECTS);
		ResponseEntity obj=rt.getForEntity("http://localhost:8080/test?test={test}", JSONObject.class, "hello");
		System.out.println(obj.getBody().toString());
		
	}
	@Test
	public void testQuery(){
		//Salesman user = salesmanRepository.findByPhone("1111111111");
		Json j =new Json();
		Salesman user = salesmanRepository.findByUsername("yewu01");
		
		
		System.out.println(j);
		
		System.out.println("user==========>>"+user.getNickname());
		System.out.println("user==========>>"+user.getUsername());
		System.out.println("user==========>>"+user.getPassword());
		System.out.println("user==========>>"+user.getPhone());
		
	}

}
