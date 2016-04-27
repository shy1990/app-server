package com.wangge;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate.HttpClientOption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
//git.oschina.net/sylarlove/app-server.git

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppServerApplication.class)
public class AppServerApplicationTests {
	@Autowired
//	private RegionRepository rp;

	@Test
	public void contextLoads() {
//		RestTemplate rt = new HmacRestTemplet("zhangsan", "zhangsan", HttpClientOption.ENABLE_REDIRECTS);
//		ResponseEntity obj = rt.getForEntity("http://localhost:8080/test?test={test}", JSONObject.class, "hello");
//		System.out.println(obj.getBody().toString());

	}
	
	@Test  
    public void testNativeQuery1() {  
		  EntityManagerFactory emf = null;
	        EntityManager em = emf.createEntityManager();   
	        //定义SQL   
	        String sql = "SELECT * FROM SJZAIXIAN.SJ_TB_ADMIN";   
	        //创建原生SQL查询QUERY实例   
	        Query query =  (Query) em.createNativeQuery(sql); 
	        //执行查询，返回的是对象数组(Object[])列表,   
	        //每一个对象数组存的是相应的实体属性   
	        List<Object> objecArraytList = ((javax.persistence.Query) query).getResultList();   
	        for(int i=0;i<objecArraytList.size();i++) {   
	            Object[] obj = (Object[]) objecArraytList.get(i);   
	           //使用obj[0],obj[1],obj[2]...取出属性　
	            System.out.println(obj);        
	        }   
	        em.close();   
	    }

}
